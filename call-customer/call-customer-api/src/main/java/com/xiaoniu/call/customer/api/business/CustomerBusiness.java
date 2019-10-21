package com.xiaoniu.call.customer.api.business;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "customer",contextId = "customerBusiness")
public interface CustomerBusiness {

    @PostMapping(value = "/checkToken")
    Boolean checkToken(@RequestParam(value = "customerId", required = true) Long customerId,
            @RequestParam(value = "accessToken", required = true) String accessToken);
}
