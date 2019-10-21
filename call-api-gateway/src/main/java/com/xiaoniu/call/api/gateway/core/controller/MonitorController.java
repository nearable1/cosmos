package com.xiaoniu.call.api.gateway.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用的监控接口 - (运维用)
 *
 * @author duanjun
 * @date 2019-02-18
 */
@RestController
@RequestMapping(value = "/callapigateway")
public class MonitorController {

    /**
     * @return
     */
    @GetMapping("/monitor")
    @ResponseBody
    public ResultBean monitor() {
        return ResultBean.format();
    }
}