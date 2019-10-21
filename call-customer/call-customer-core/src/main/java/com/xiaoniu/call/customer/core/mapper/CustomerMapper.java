package com.xiaoniu.call.customer.core.mapper;

import com.xiaoniu.call.customer.api.dto.AccountManageDTO;
import com.xiaoniu.call.customer.api.dto.ManagerDTO;
import com.xiaoniu.call.customer.api.vo.AccountManagerVO;
import com.xiaoniu.call.customer.api.vo.ManagerVO;
import com.xiaoniu.call.customer.core.dto.CustomerInfoDTO;
import com.xiaoniu.call.customer.core.entity.Customer;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CustomerMapper extends Mapper<Customer> {
    List<Customer> selectByOpenId(@Param("openId") String openId, @Param("accountType") Integer accountType);

    List<AccountManageDTO> selectListByPage(AccountManagerVO accountManagerVO);

    List<CustomerInfoDTO> selectByOpenIdAndType(@Param("openId") String openId, @Param("accountType") Integer accountType);

    CustomerInfoDTO selectByCustomerId(@Param("customerId") Long customerId);

    List<ManagerDTO> selectManagerByPage(ManagerVO ManagerVO);
}