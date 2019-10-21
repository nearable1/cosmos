package com.xiaoniu.call.api.gateway.core.filter;

import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * @author guozhengshui
 * @date 2019-06-08 18:58
 */
@Log4j2
@Component
public class CorsResponseHeaderFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		// 指定此过滤器位于NettyWriteResponseFilter之后
		// 即待处理完响应体后接着处理响应头
		return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return chain.filter(exchange).then(Mono.defer(() -> {
			exchange.getResponse().getHeaders().entrySet().stream()
					.filter(kv -> (kv.getValue() != null && kv.getValue().size() > 1)
							&& ((kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)
							|| kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS))))
					.peek(kv -> log.info("header contains multiple values: {}", JSONUtils.toJSONString(kv)))
					.forEach(kv -> kv.setValue(Collections.singletonList(kv.getValue().get(0))));

			return chain.filter(exchange);
		}));
	}
}