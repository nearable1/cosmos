package com.xiaoniu.call.customer.core.service;

import com.xiaoniu.call.customer.core.dto.CustomerInfoDTO;
import com.xiaoniu.call.customer.core.dto.LoginResponseDTO;
import com.xiaoniu.call.customer.core.vo.LoginVO;
import com.xiaoniu.call.customer.core.vo.UpdateCustomerVO;

public interface CustomerService {
    LoginResponseDTO login(LoginVO param);

    void logout();

    CustomerInfoDTO info();

    void updateCustomerInfo(UpdateCustomerVO param);

    Boolean checkToken(Long customerId, String accessToken);
}
