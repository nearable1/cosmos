package com.xiaoniu.call.api.gateway.core.filter;

import com.xiaoniu.architecture.commons.api.ApiResultBean;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.commons.web.util.PathMatchUtils;
import com.xiaoniu.architecture.spring.boot.autoconfigure.security.access.AccessProperties;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultClientResponse;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * 包装响应体过滤器
 *
 * @author guoqiang
 * @date 2018-09-11 11:53 AM
 */
//@Component
@Log4j2
public class WrapResponseBodyFilter implements GlobalFilter, Ordered {

    @Autowired
    private AccessProperties accessProperties;

    @Override
    public int getOrder() {
        return -2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String rawPath = exchange.getRequest().getURI().getRawPath();
        log.info("WrapResponseBodyFilter --> rawPath:{}", rawPath);
        if (PathMatchUtils.noneMatch(rawPath, accessProperties.getNoneWrapResponseBodyUris())) {
            return chain.filter(exchange.mutate()
                    .response(new ServerHttpResponseWrapper(exchange.getResponse(), exchange))
                    .build());
        }
        return chain.filter(exchange);
        //return chain.filter(exchange).then(Mono.defer(() -> getBodyMono(exchange)));
    }

    private class ServerHttpResponseWrapper extends ServerHttpResponseDecorator {

        private final ServerWebExchange exchange;

        public ServerHttpResponseWrapper(ServerHttpResponse delegate, ServerWebExchange exchange) {
            super(delegate);
            this.exchange = exchange;
        }

        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            Flux<String> wrapBodyFlux = getDefaultClientResponse(body)
                    .body(BodyExtractors.toDataBuffers())
                    .buffer()
                    .map(dataBuffers -> getWrapBody(decodeDataBuffer(dataBuffers)))
                    .defaultIfEmpty(getWrapResponseBody(null));

            BodyInserter bodyInserter = BodyInserters.fromPublisher(wrapBodyFlux, String.class);
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, getDelegate().getHeaders());

            return bodyInserter.insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        Flux<DataBuffer> messageBody = outputMessage.getBody();
                        HttpHeaders headers = getDelegate().getHeaders();
                        if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
                            messageBody = messageBody.doOnNext(data -> headers.setContentLength(data.readableByteCount()));
                        }
                        return getDelegate().writeWith(messageBody);
                    }));
        }

        @Override
        public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
            return writeWith(Flux.from(body).flatMapSequential(p -> p));
        }

        private DefaultClientResponse getDefaultClientResponse(Publisher<? extends DataBuffer> body) {
            MediaType originalResponseContentType = exchange.getAttribute("original_response_content_type");
            HttpHeaders httpHeaders = new HttpHeaders();
            if(null == originalResponseContentType) {
                originalResponseContentType = MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE);
                exchange.getResponse().getHeaders().setContentType(originalResponseContentType);
            }
            httpHeaders.setContentType(originalResponseContentType);

            ResponseAdapter responseAdapter = new ResponseAdapter(body, httpHeaders);
            return new DefaultClientResponse(responseAdapter, ExchangeStrategies.withDefaults());
        }
    }

    private String getWrapBody(String originalBody) {
        log.info("originalBody:{}", originalBody);
        String wrapBody = originalBody;
        if (StringUtils.isNoneBlank(wrapBody)) {
            if (!PathMatchUtils.isErrorResult(wrapBody)) {
                wrapBody = getWrapResponseBody("#DATA#").replace("\"#DATA#\"", wrapBody);
            }
        } else {
            wrapBody = getWrapResponseBody(null);
        }

        log.info("wrapBody:{}", wrapBody);
        return wrapBody;
    }

    private String getWrapBody(DataBuffer dataBuffer) {
        String originalBody = decodeDataBuffer(dataBuffer);
        return getWrapBody(originalBody);
    }

    private String getWrapResponseBody(String o) {
        return JSONUtils.toJSONString(ApiResultBean.success(HeaderHelper.getRequestId(), o));
    }

    private Mono<? extends Void> getBodyMono(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        Flux<DataBuffer> originalDataBuffer = Flux.just(dataBufferFactory.allocateBuffer());

        Flux<DataBuffer> wrapDataBuffer = originalDataBuffer;
        String rawPath = exchange.getRequest().getURI().getRawPath();
        log.info("WrapResponseBodyFilter --> rawPath:{}", rawPath);
        if (PathMatchUtils.noneMatch(rawPath, accessProperties.getNoneWrapResponseBodyUris())) {
            wrapDataBuffer = originalDataBuffer
                    .publishOn(Schedulers.single())
                    .flatMap(dataBuffer -> Flux.just(getDataBuffer(dataBufferFactory, getWrapBody(dataBuffer))))
                    .defaultIfEmpty(getDataBuffer(dataBufferFactory, getWrapResponseBody(null)));
        }
        return response.writeWith(wrapDataBuffer);
    }

    private static String decodeDataBuffer(List<DataBuffer> dataBufferList){
        StringBuilder builder = new StringBuilder();
        dataBufferList.stream().forEach(dataBuffer -> builder.append(decodeDataBuffer(dataBuffer)));
        return builder.toString();
    }

    private static String decodeDataBuffer(DataBuffer dataBuffer){
        CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
        DataBufferUtils.release(dataBuffer);
        return charBuffer.toString();
    }

    private DataBuffer getDataBuffer(DataBufferFactory dataBufferFactory, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = dataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

}
