package com.xiaoniu.call.api.gateway.core.filter.factory;

import com.xiaoniu.architecture.commons.api.ApiResultBean;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 包装响应体网关过滤器工厂
 *
 * @author guoqiang
 * @date 2018-09-10 6:19 PM
 */
//@Component
@Log4j2
public class WrapResponseBodyGatewayFilterFactory extends AbstractUriGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.defer(() -> {
            log.info("config:{}", config.getUri());
            ServerHttpRequest request = exchange.getRequest();
            String rawPath = request.getURI().getRawPath();
            log.info("rawPath:{}", rawPath);
            ServerHttpResponse response = exchange.getResponse();
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            InputStream inputStream = dataBufferFactory.allocateBuffer().asInputStream();
            String originalBody = "";
            try {
                originalBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("获取原始报文出错：{}", e.getMessage());
            }
            DataBufferUtils.release(dataBufferFactory.allocateBuffer());
            log.info("originalBody:{}", originalBody);
            String wrapBody = JSONUtils.toJSONString(ApiResultBean.success(HeaderHelper.getRequestId(), originalBody));
            log.info("wrapBody:{}", wrapBody);
            Flux<DataBuffer> flux = Flux.just(stringBuffer(dataBufferFactory, wrapBody));
            return response.writeWith(flux);
        }));
    }


    protected DataBuffer stringBuffer(DataBufferFactory dataBufferFactory, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = dataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }
}
