package com.xiaoniu.call.customer.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xiaoniu.call.customer.core.mapper.CustomerManagerMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.web.context.HttpContextHolder;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.commons.web.util.RequestUtils;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.customer.core.cons.CustomerCons;
import com.xiaoniu.call.customer.core.cons.RedisCons;
import com.xiaoniu.call.customer.core.dto.CustomerInfoDTO;
import com.xiaoniu.call.customer.core.dto.LoginResponseDTO;
import com.xiaoniu.call.customer.core.entity.Customer;
import com.xiaoniu.call.customer.core.entity.CustomerExt;
import com.xiaoniu.call.customer.core.entity.CustomerLoginLog;
import com.xiaoniu.call.customer.core.mapper.CustomerExtMapper;
import com.xiaoniu.call.customer.core.mapper.CustomerMapper;
import com.xiaoniu.call.customer.core.service.CustomerService;
import com.xiaoniu.call.customer.core.vo.LoginVO;
import com.xiaoniu.call.customer.core.vo.UpdateCustomerVO;
import com.xiaoniu.platform.worker.sdk.proxy.IdWorkerProxy;

/**
 * 用户服务
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerExtMapper customerExtMapper;

    /**
     * 登录失效时长（30天）
     */
    @Value("${customer.login.expire.second:2592000}")
    private Long loginExpireTimeSec;

    /**
     * 登录
     *
     * @param param
     * @return
     */
    @Override
    public LoginResponseDTO login(LoginVO param) {
        List<CustomerInfoDTO> customerList;
        LoginResponseDTO returnVO = new LoginResponseDTO();
        if (StringUtils.isNotEmpty(param.getOpenId()) && param.getAccountType() != null) {
            // 微信登录
            customerList = customerMapper.selectByOpenIdAndType(param.getOpenId(), param.getAccountType());
        } else {
            throw new BusinessException(ResultCodeEnum.CONSTRAINT_VIOLATIONS_EXCEPTION);
        }
        if (CollectionUtils.isEmpty(customerList)) {
            // 查不到该openId对应的用户信息，进入注册流程
            Customer customer = saveCustomer(param.getOpenId(), param.getAccountType(), param.getNickName(),
                    param.getAvatarAddress());
            if (customer != null) {
                int result = saveCustomerExt(customer.getId());
                if (result > 0) {
                    // 保存登录日志
                    saveCustomerLoginLog(customer.getId(),
                            CustomerCons.ACCOUNT_TYPE_MAPPING.get(param.getAccountType()));
                    returnVO.setAccessToken(genAccessToken(customer.getId()));
                    returnVO.setAvatarAddress(customer.getAvatarAddress());
                    returnVO.setCustomerId(customer.getId());
                    returnVO.setNickName(customer.getNickname());
                    returnVO.setLevel(0);
                    return returnVO;
                }
            }
            throw new BusinessException(ResultCodeEnum.NONE_APP_ACCOUNT_DATA);
        } else {
            // 登录
            CustomerInfoDTO customer = customerList.get(0);
            saveCustomerLoginLog(customer.getCustomerId(), CustomerCons.ACCOUNT_TYPE_MAPPING.get(customer.getAccountType()));
            returnVO.setAccessToken(genAccessToken(customer.getCustomerId()));
            returnVO.setAvatarAddress(customer.getAvatarAddress());
            returnVO.setCustomerId(customer.getCustomerId());
            returnVO.setNickName(customer.getNickName());
            returnVO.setLevel(customer.getLevel() == null ? 0 : customer.getLevel());
            RedisRepository.del(RedisCons.CUSTOMER_INFO + customer.getCustomerId());
            return returnVO;
        }
    }

    /**
     * 注销
     */
    @Override
    public void logout() {
        RedisRepository.hdel(RedisCons.CUSTOMER + HeaderHelper.getCustomerId(), RedisCons.CUSTOMER_ACCESS_TOKEN);
        RedisRepository.del(RedisCons.CUSTOMER_INFO + HeaderHelper.getCustomerId());
    }

    /**
     * 用户信息
     * 
     * @return
     */
    @Override
    public CustomerInfoDTO info() {
        String json = RedisRepository.get(RedisCons.CUSTOMER_INFO + HeaderHelper.getCustomerId());
        if (StringUtils.isNotEmpty(json)) {
            CustomerInfoDTO customerInfoVO = JSONUtils.parseObject(json, CustomerInfoDTO.class);
            return customerInfoVO;
        } else {
            CustomerInfoDTO customer = customerMapper.selectByCustomerId(Long.valueOf(HeaderHelper.getCustomerId()));
            if (customer != null) {
                CustomerInfoDTO customerInfoVO = new CustomerInfoDTO();
                customerInfoVO.setAvatarAddress(customer.getAvatarAddress());
                customerInfoVO.setCustomerId(customer.getCustomerId());
                customerInfoVO.setNickName(customer.getNickName());
                customerInfoVO.setSignature(customer.getSignature());
                customerInfoVO.setLevel(customer.getLevel() == null ? 0 : customer.getLevel());
                RedisRepository.set(RedisCons.CUSTOMER_INFO + HeaderHelper.getCustomerId(),
                        JSONUtils.toJSONString(customerInfoVO), 864000);
                return customerInfoVO;
            }
            throw new BusinessException(ResultCodeEnum.NONE_APP_ACCOUNT_DATA);
        }
    }

    /**
     * 更新用户信息
     *
     * @param param
     */
    @Override
    public void updateCustomerInfo(UpdateCustomerVO param) {
        Customer customer = customerMapper.selectByPrimaryKey(HeaderHelper.getCustomerId());
        customer.setId(Long.valueOf(HeaderHelper.getCustomerId()));
        customer.setNickname(param.getNickName());
        customer.setAvatarAddress(param.getAvatarAddress());
        customer.setSignature(param.getSignature());
        int result = customerMapper.updateByPrimaryKeySelective(customer);
        RedisRepository.del(RedisCons.CUSTOMER_INFO + HeaderHelper.getCustomerId());
        if (result == 0) {
            throw new BusinessException(ResultCodeEnum.NONE_APP_ACCOUNT_DATA);
        }
    }

    /**
     * 验证accessToken
     *
     * @param accessToken
     * @param customerId
     * @return
     */
    @Override
    public Boolean checkToken(Long customerId, String accessToken) {
        String redisAccessToken = RedisRepository.hget(RedisCons.CUSTOMER + customerId,
                RedisCons.CUSTOMER_ACCESS_TOKEN);
        return accessToken.equals(redisAccessToken);
    }

    /**
     * 保存用户主表信息
     * 
     * @param openId
     * @param accountType
     * @param nickName
     * @param avatar
     * @return
     */
    public Customer saveCustomer(String openId, Integer accountType, String nickName, String avatar) {
        Customer customer = new Customer();
        customer.setId(IdWorkerProxy.generateId());
        customer.setAccountType(accountType);
        customer.setUsername(openId);
        customer.setNickname(nickName);
        customer.setAvatarAddress(avatar);
        int result = customerMapper.insertSelective(customer);
        return result > 0 ? customer : null;
    }

    /**
     * 保存用户拓展表信息
     *
     * @param customerId
     * @return
     */
    public int saveCustomerExt(Long customerId) {
        CustomerExt customerExt = new CustomerExt();
        customerExt.setAppVersion(HeaderHelper.getAppVersion());
        customerExt.setCustomerId(customerId);
        customerExt.setDeviceCode(HeaderHelper.getDeviceId());
        customerExt.setRegisterChannel(HeaderHelper.getMarket());
        customerExt.setDownloadChannel(HeaderHelper.getMarket());
        customerExt.setSystemType(HeaderHelper.getOsVersion());
        customerExt.setPhoneModel(HeaderHelper.getPhoneModel());
        customerExt.setSource(HeaderHelper.getRequestAgent());
        customerExt.setIpAddress(RequestUtils.getRemoteAddr(HttpContextHolder.INSTANCE.getRequest()));
        return customerExtMapper.insertSelective(customerExt);
    }

    /**
     * 保存登录日志
     * 
     * @param customerId
     * @return
     */
    public void saveCustomerLoginLog(Long customerId, Integer accountType) {
        // 更新登录时间
        Date now = new Date();
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setLastLoginTime(now);
        customerMapper.updateByPrimaryKeySelective(customer);

        CustomerLoginLog customerLoginLog = new CustomerLoginLog();
        customerLoginLog.setId(IdWorkerProxy.generateId());
        customerLoginLog.setCustomerId(customerId);
        customerLoginLog.setRequestId(HeaderHelper.getRequestId());
        customerLoginLog.setSource(accountType);
        customerLoginLog.setPhoneModel(HeaderHelper.getPhoneModel());
        customerLoginLog.setAppDownloadChannel(HeaderHelper.getMarket());
        customerLoginLog.setDeviceCode(HeaderHelper.getDeviceId());
        customerLoginLog.setLoginIpAddress(RequestUtils.getRemoteAddr(HttpContextHolder.INSTANCE.getRequest()));
        customerLoginLog.setCreateTime(now);
        MongodbRepository.save(customerLoginLog);
    }

    /**
     * 构造访问令牌
     *
     * @param customerId
     * @return
     */
    public String genAccessToken(Long customerId) {
        String sessionId = UUID.randomUUID().toString();
        RedisRepository.hset(RedisCons.CUSTOMER + customerId, RedisCons.CUSTOMER_ACCESS_TOKEN, sessionId);
        return sessionId;
    }
}
