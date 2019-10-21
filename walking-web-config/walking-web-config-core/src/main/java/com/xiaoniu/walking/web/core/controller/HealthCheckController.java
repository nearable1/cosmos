package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 健康检查
 *
 * @author chenguohua
 * @date 2019年5月14日17:26:49
 */
@RestController
@RequestMapping("/")
public class HealthCheckController {
    /**
     * 运维监控
     *
     * @return
     */
    @GetMapping("/")
    public ResultBean monitor() {
        return ResultBean.format();
    }
}
