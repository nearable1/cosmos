package com.xiaoniu.call.customer.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.customer.core.cons.RedisCons;
import com.xiaoniu.call.customer.core.entity.Customer;
import com.xiaoniu.call.customer.core.entity.CustomerManager;
import com.xiaoniu.call.customer.core.mapper.CustomerManagerMapper;
import com.xiaoniu.call.customer.core.mapper.CustomerMapper;
import com.xiaoniu.call.customer.core.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private CustomerManagerMapper customerManagerMapper;

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public void saveManager(Long uid) {
        Customer customer = customerMapper.selectByPrimaryKey(uid);
        if (customer == null) {
            throw new BusinessException(ResultCodeEnum.DATABASE_UPDATE_FAILURE.getCode(), "该用户不存在");
        }
        List<CustomerManager> list = customerManagerMapper.selectByCustomerId(uid);
        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ResultCodeEnum.DATABASE_UPDATE_FAILURE.getCode(), "该管理员已存在");
        }

        CustomerManager customerManager = new CustomerManager();
        customerManager.setCustomerId(uid);
        customerManager.setLevel(1);
        customerManager.setState(1);
        customerManagerMapper.insertSelective(customerManager);

        // 删除用户缓存
        RedisRepository.del((RedisCons.CUSTOMER_INFO + customer.getId()));
    }

    @Override
    public void enableManager(Long id, Boolean status) {
        CustomerManager customerManagerExist = customerManagerMapper.selectByPrimaryKey(id);
        if (customerManagerExist == null) {
            throw new BusinessException(ResultCodeEnum.DATABASE_UPDATE_FAILURE.getCode(), "记录不存在");
        }
        CustomerManager customerManager = new CustomerManager();
        customerManager.setId(id);
        if (status) {
            customerManager.setState(1);
        } else {
            customerManager.setState(0);
        }
        customerManagerMapper.updateByPrimaryKeySelective(customerManager);

        // 删除用户缓存
        RedisRepository.del((RedisCons.CUSTOMER_INFO + customerManagerExist.getCustomerId()));
    }

    @Override
    public void deleteManager(Long id) {
        CustomerManager customerManagerExist = customerManagerMapper.selectByPrimaryKey(id);
        if (customerManagerExist == null) {
            throw new BusinessException(ResultCodeEnum.DATABASE_UPDATE_FAILURE.getCode(), "记录不存在");
        }
        customerManagerMapper.deleteByPrimaryKey(id);

        // 删除用户缓存
        RedisRepository.del((RedisCons.CUSTOMER_INFO + customerManagerExist.getCustomerId()));
    }
}
