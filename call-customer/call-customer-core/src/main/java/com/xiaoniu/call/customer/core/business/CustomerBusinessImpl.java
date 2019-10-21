package com.xiaoniu.call.customer.core.business;

import com.xiaoniu.call.customer.api.business.CustomerBusiness;
import com.xiaoniu.call.customer.core.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class CustomerBusinessImpl implements CustomerBusiness {

    @Autowired
    private CustomerService customerService;

    @Override
    @PostMapping(value = "/checkToken")
    public Boolean checkToken(@RequestParam(value = "customerId", required = true) Long customerId,
                              @RequestParam(value = "accessToken", required = true) String accessToken) {
        return customerService.checkToken(customerId, accessToken);
    }
}
