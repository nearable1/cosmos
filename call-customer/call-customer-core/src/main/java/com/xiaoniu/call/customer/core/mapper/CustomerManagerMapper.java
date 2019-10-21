package com.xiaoniu.call.customer.core.mapper;

import com.xiaoniu.call.customer.core.entity.CustomerManager;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CustomerManagerMapper extends Mapper<CustomerManager> {
    List<CustomerManager> selectByCustomerId(@Param("customerId") Long customerId);
}