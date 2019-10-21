package com.xiaoniu.walking.web.core.interceptor;

import com.xiaoniu.walking.web.core.constants.ShiroCons;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: xiangxianjin
 * @date: 2019/3/29 17:37
 * @description: 解決跨域問題
 */
@Component
@Log4j2
public class CommonInterceptor implements HandlerInterceptor {

    /**
     * 后台直接访问的地址统一开始路径
     */
    private static final String WEB_API = "/walkingwebapi";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if(request.getRequestURI().indexOf(WEB_API) > -1){
            response.addHeader("Access-Control-Allow-Origin", "*");
        }else {
            log.info("feign服务间调用不需要设置允许跨域");
        }
        if (ShiroCons.OPTIONS.equals(request.getMethod())) {
            //允许跨域,不能放在postHandle内
            response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization, X-Auth-Token");
        }
        return true;
    }
}
