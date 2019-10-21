package com.xiaoniu.call.api.gateway.core.controller;

import com.xiaoniu.architecture.commons.api.ApiResultBean;
import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.reactive.ReactiveRequestUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 全局默认降级路径
 *
 * @author guoqiang
 * @date 2018-09-11 11:35 PM
 */
@RestController
public class GlobalFallbackController {

    @RequestMapping("/fallback")
    public ApiResultBean fallback(ServerHttpRequest serverHttpRequest) {
        return ApiResultBean.failure(ReactiveRequestUtils.getRequestId(serverHttpRequest.getHeaders()),
                ResultCodeEnum.REQUEST_TIMEOUT.getCode(),
                ResultCodeEnum.REQUEST_TIMEOUT.getDescription());
    }
}
