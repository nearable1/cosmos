package com.xiaoniu.call.api.gateway.core.filter;

import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.spring.boot.autoconfigure.security.access.AccessProperties;
import com.xiaoniu.call.api.gateway.core.support.FilterPathMatcherHelper;
import com.xiaoniu.call.api.gateway.core.support.ServerWebExchangeHelper;
import com.xiaoniu.call.customer.api.business.CustomerBusiness;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证过滤器
 *
 * @author guoqiang
 * @date 2018-09-10 5:32 PM
 */
@Component
@Log4j2
public class AuthenticationFilter implements GlobalFilter, Ordered {

//    @Autowired
//    private AccessProperties accessProperties;
//
//    @Autowired
//    private CustomerBusiness customerBusiness;

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String rawPath = exchange.getRequest().getURI().getRawPath();

//        boolean noneMatch = FilterPathMatcherHelper.noneMatch(accessProperties.getIgnoreHeaderAndTokenUrls(), rawPath);
//        if (noneMatch) {
//            HeaderHelper.checkCustomerIdAndAccessToken();
//            boolean result = customerBusiness.checkToken(Long.valueOf(HeaderHelper.getCustomerId()), HeaderHelper.getAccessToken());
//            if (!result) {
//                return ServerWebExchangeHelper.writeWith(exchange, new BusinessException(ResultCodeEnum.ILLEGAL_ACCESS_TOKEN, "AccessToken" + "校验不通过"));
//            }
//        }
        return chain.filter(exchange);
    }

}
