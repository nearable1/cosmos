package com.xiaoniu.call.api.gateway.core.filter;

import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.spring.boot.autoconfigure.security.access.AccessProperties;
import com.xiaoniu.call.api.gateway.core.support.FilterPathMatcherHelper;
import com.xiaoniu.call.api.gateway.core.support.ServerWebExchangeHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Reactive Http 黑名单拦截器
 *
 * @author wuwen
 * @date 2019/04/18
 */
@Component
@Log4j2
public class BlacklistRequestUrlsFilter implements GlobalFilter, Ordered {

    @Autowired
    private AccessProperties accessProperties;

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 3;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String rawPath = exchange.getRequest().getURI().getRawPath();
        //log.info("BlacklistUrisFilter --> rawPath:{}", rawPath);

        if (null != accessProperties.getBlackListUrls()) {
            List<String> blacklistUrls = accessProperties.getBlackListUrls().get(HeaderHelper.getAppName());
            if (CollectionUtils.isNotEmpty(blacklistUrls)) {
                boolean anyMatch = FilterPathMatcherHelper.anyMatch(blacklistUrls, rawPath);
                if (anyMatch) {
                    return ServerWebExchangeHelper.writeWith(exchange, ResultCodeEnum.ILLEGAL_REQUEST_AGENT);
                }
            }
        }
        return chain.filter(exchange);
    }
}
