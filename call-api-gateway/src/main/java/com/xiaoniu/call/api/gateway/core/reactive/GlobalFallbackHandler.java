package com.xiaoniu.call.api.gateway.core.reactive;

import com.xiaoniu.architecture.commons.api.ApiResultBean;
import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.reactive.ReactiveRequestUtils;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 全局默认降级解析器
 *
 * @author guoqiang
 * @date 2018-09-21 6:20 PM
 */
//@Service
public class GlobalFallbackHandler {

    public Mono<ServerResponse> getFallbackResult(ServerRequest serverRequest){
        String requestId = ReactiveRequestUtils.getRequestId(serverRequest.headers().asHttpHeaders());
        return Mono.just(ApiResultBean.failure(requestId, ResultCodeEnum.REQUEST_TIMEOUT.getCode(),
                ResultCodeEnum.REQUEST_TIMEOUT.getDescription()))
                .flatMap(apiResultBean -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(BodyInserters.fromObject(apiResultBean)));
    }

}
