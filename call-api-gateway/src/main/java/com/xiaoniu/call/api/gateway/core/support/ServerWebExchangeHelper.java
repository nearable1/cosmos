package com.xiaoniu.call.api.gateway.core.support;

import com.xiaoniu.architecture.commons.api.ApiResultBean;
import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.core.context.SpringContextHolder;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.reactive.ReactiveI18nProxy;
import com.xiaoniu.architecture.commons.reactive.context.ReactiveHttpContextHelper;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * ServerWebExchange帮助类
 *
 * @author guoqiang
 * @date 2018-09-27 8:01 PM
 */
public class ServerWebExchangeHelper {

    /**
     * 获取contentType
     *
     * @param exchange
     * @return
     */
    public static MediaType getContentType(ServerWebExchange exchange) {
        Object originalResponseContentType = exchange.getAttribute("original_response_content_type");

        if (null == originalResponseContentType) {
            return MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE);
        }

        MediaType destResponseContentType;
        if (originalResponseContentType instanceof String) {
            destResponseContentType = MediaType.valueOf((String) originalResponseContentType);
        } else {
            destResponseContentType = (MediaType) originalResponseContentType;
        }

        return destResponseContentType;
    }

    /**
     * 获取失败JSON值
     *
     * @param resultCodeEnum
     * @return
     */
    public static String getFailureJsonValue(ResultCodeEnum resultCodeEnum) {
        return getFailureJsonValue(resultCodeEnum.getCode(), resultCodeEnum.getDescription());
    }

    /**
     * 获取失败JSON值
     *
     * @param businessException
     * @return
     */
    public static String getFailureJsonValue(BusinessException businessException) {
        return getFailureJsonValue(businessException.getCode(), businessException.getMessage());
    }

    /**
     * 获取失败JSON值
     *
     * @param message
     * @return
     */
    public static String getFailureJsonValue(String code, String message) {
        if (SpringContextHolder.getApplicationContext().containsBean("reactiveI18nProxy")) {
            message = ReactiveI18nProxy.getMessage(ReactiveHttpContextHelper.getLanguage(), code);
        }
        return JSONUtils.toJSONString(ApiResultBean.failure(HeaderHelper.getRequestId(), code, message));
    }

    /**
     * 获取Flux数据缓冲
     *
     * @param dataBufferFactory
     * @param value
     * @return
     */
    public static Flux<DataBuffer> getFluxDataBuffer(DataBufferFactory dataBufferFactory, String value) {
        return Flux.just(getDataBuffer(dataBufferFactory, value));
    }

    /**
     * 获取数据缓冲工厂
     *
     * @param dataBufferFactory
     * @param value
     * @return
     */
    public static DataBuffer getDataBuffer(DataBufferFactory dataBufferFactory, String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = dataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }


    /**
     * 写入响应报文
     *
     * @param exchange
     * @param resultCodeEnum
     * @return
     */
    public static Mono<Void> writeWith(ServerWebExchange exchange, ResultCodeEnum resultCodeEnum) {
        return writeWith(exchange, getFailureJsonValue(resultCodeEnum));
    }

    /**
     * 写入响应报文
     *
     * @param exchange
     * @param businessException
     * @return
     */
    public static Mono<Void> writeWith(ServerWebExchange exchange, BusinessException businessException) {
        return writeWith(exchange, getFailureJsonValue(businessException));
    }

    /**
     * 写入响应报文
     *
     * @param exchange
     * @param value
     * @return
     */
    public static Mono<Void> writeWith(ServerWebExchange exchange, String value) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(getContentType(exchange));
        return response.writeWith(getFluxDataBuffer(response.bufferFactory(), value));
    }

}
