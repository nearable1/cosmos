package com.xiaoniu.call.customer.core.controller;

import com.xiaoniu.call.customer.core.dto.AppVersionDTO;
import com.xiaoniu.call.customer.core.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app配置接口
 *
 * @author wuwen
 * @date 2019-07-10 17:13
 */
@RestController
@RequestMapping(value = "/config")
public class AppConfigController {

    @Autowired
    private AppVersionService appVersionService;

    @GetMapping("/version")
    public AppVersionDTO version() {
        return appVersionService.getMaxAppVersionConfig();
    }
}
