package com.xiaoniu.walking.web.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

public interface AppAuditService {

    /**
     * 获取字典值
     *
     * @param bigCode
     * @param smallCode
     * @return
     */
    String dic(@RequestParam("bigCode") String bigCode, @RequestParam("smallCode") String smallCode);


}
