package com.xiaoniu.call.api.gateway.core.filter;

import com.xiaoniu.architecture.commons.reactive.context.ReactiveHttpContextHolder;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Reactive Http 上下文拦截器
 *
 * @author SteveGuo
 * @date 2017/12/04
 */
@Component
@Log4j2
public class ReactiveHttpContextFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 4;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //log.info("ReactiveHttpContextFilter::pre");
        ReactiveHttpContextHolder.INSTANCE.putRequestHeader(exchange.getRequest());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //log.info("ReactiveHttpContextFilter::post");
                ReactiveHttpContextHolder.INSTANCE.removeRequestHeader();
        }));
    }

}
