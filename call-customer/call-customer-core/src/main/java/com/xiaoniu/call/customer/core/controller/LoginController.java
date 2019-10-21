package com.xiaoniu.call.customer.core.controller;

import com.xiaoniu.call.customer.core.dto.CustomerInfoDTO;
import com.xiaoniu.call.customer.core.dto.LoginResponseDTO;
import com.xiaoniu.call.customer.core.service.CustomerService;
import com.xiaoniu.call.customer.core.vo.LoginVO;
import com.xiaoniu.call.customer.core.vo.UpdateCustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class LoginController {

    @Autowired
    private CustomerService customerService;

    /**
     * 登录
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginVO param) {
        return customerService.login(param);
    }

    /**
     * 注销
     */
    @DeleteMapping(value = "/logout")
    public void logout() {
        customerService.logout();
    }

    /**
     * 个人信息
     *
     * @return
     */
    @GetMapping(value = "/info")
    public CustomerInfoDTO info() {
        return customerService.info();
    }

    /**
     * 更新
     *
     * @return
     */
    @PutMapping(value = "/update")
    public void update(@RequestBody @Valid UpdateCustomerVO param) {
        customerService.updateCustomerInfo(param);
    }
}
