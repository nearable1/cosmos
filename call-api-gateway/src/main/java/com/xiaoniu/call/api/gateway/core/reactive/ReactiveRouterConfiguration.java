package com.xiaoniu.call.api.gateway.core.reactive;

import com.xiaoniu.call.api.gateway.core.support.GatewayConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * Reactive路由配置
 *
 * @author SteveGuo
 * @date 2018-07-04 15:58
 */
//@Configuration
public class ReactiveRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(GlobalFallbackHandler globalFallbackHandler) {
        return RouterFunctions.route(GET(GatewayConstants.DEFAULT_GLOBAL_FALLBACK_URI)
                        .and(accept(MediaType.APPLICATION_JSON_UTF8)), globalFallbackHandler::getFallbackResult);
    }

}
