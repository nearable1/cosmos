package com.xiaoniu.walking.web.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 运维监控
 *
 * @author chenguohua
 * @date 2019年5月14日17:26:49
 */
@RestController
@RequestMapping("/walkingwebapi")
public class OperationalMonitorController {
    /**
     * 运维监控
     *
     * @return
     */
    @GetMapping("/monitor")
    public String monitor() {
        String result = "200";
        return result;
    }
}
