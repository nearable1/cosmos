package com.xiaoniu.call.customer.core.service.impl;

import com.xiaoniu.architecture.commons.core.util.DateUtils;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.call.customer.api.dto.ManagerDTO;
import com.xiaoniu.call.customer.api.dto.UserLoginDTO;
import com.xiaoniu.call.customer.api.vo.ManagerVO;
import com.xiaoniu.call.customer.core.entity.CustomerLoginLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.call.customer.api.dto.AccountManageDTO;
import com.xiaoniu.call.customer.api.vo.AccountManagerVO;
import com.xiaoniu.call.customer.core.mapper.CustomerMapper;
import com.xiaoniu.call.customer.core.service.AccountManageService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountManageServiceImpl implements AccountManageService {

    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageResult<AccountManageDTO> accountManageList(AccountManagerVO accountManagerVO) {
        PageResult<AccountManageDTO> pageResult = pageRepository.selectPaging(CustomerMapper.class, "selectListByPage", accountManagerVO);
        return pageResult;
    }

    @Override
    public PageResult<UserLoginDTO> userLoginLoglist(AccountManagerVO accountManagerVO) {
        PageRequest pageRequest = PageRequest.of(accountManagerVO.getPageIndex(), accountManagerVO.getPageSize(), Sort.by(Sort.Direction.DESC, "createTime"));
        Criteria criteria = new Criteria();
        if (accountManagerVO.getId() != null) {
            criteria.and("customerId").is(accountManagerVO.getId());
        }
        if (accountManagerVO.getAccountType() != null && accountManagerVO.getAccountType() != 0) {
            criteria.and("source").is(accountManagerVO.getAccountType());
        }
        if (StringUtils.isNotEmpty(accountManagerVO.getStartTime())) {
            criteria.and("createTime").gte(dateToISODate(accountManagerVO.getStartTime()));
        }
        if (StringUtils.isNotEmpty(accountManagerVO.getEndTime())) {
            criteria.and("createTime").lte(dateToISODate(accountManagerVO.getEndTime()));
        }
        Page<CustomerLoginLog> page = MongodbRepository.findByPage(new Query(criteria), pageRequest, CustomerLoginLog.class);
        List<CustomerLoginLog> logs = page.getContent();
        List<UserLoginDTO> dtos = new ArrayList<>();
        logs.forEach(log -> {
            UserLoginDTO dto = new UserLoginDTO();
            dto.setAccountType(log.getSource());
            dto.setCreateTime(log.getCreateTime());
            dto.setId(String.valueOf(log.getCustomerId()));
            dto.setLoginIpAddress(log.getLoginIpAddress());
            dtos.add(dto);
        });

        PageResult<UserLoginDTO> videoPageResult = new PageResult<>();
        videoPageResult.setTotal((int) page.getTotalElements());
        videoPageResult.setTotalPages(page.getTotalPages());
        videoPageResult.setRows(dtos);
        return videoPageResult;
    }

    @Override
    public PageResult<ManagerDTO> managerList(ManagerVO accountManagerVO) {
        PageResult<ManagerDTO> pageResult = pageRepository.selectPaging(CustomerMapper.class, "selectManagerByPage", accountManagerVO);
        return pageResult;
    }

    public static Date dateToISODate(String dateStr) {
        //T代表后面跟着时间，Z代表UTC统一时间
        Date date = DateUtils.parseDate(dateStr);
        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        String isoDate = format.format(date);
        try {
            return format.parse(isoDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
