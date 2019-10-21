package com.xiaoniu.call.api.gateway.core.filter;

import com.xiaoniu.architecture.commons.core.factory.ThreadPoolTaskExecutorFactory;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.call.api.gateway.core.domain.RequestLog;
import com.xiaoniu.call.api.gateway.core.filter.reactive.RecorderServerHttpRequestDecorator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 请求日志过滤器
 *
 * @author guoqiang
 * @date 2018-09-10 6:07 PM
 */
@Component
@Log4j2
public class RequestLogFilter implements GlobalFilter, Ordered {

	@Value("${api.gateway.request-logger.enabled:false}")
	private boolean apiSecurityLoggerEnabled;

	@Override
	public int getOrder() {
		return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 3;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if(apiSecurityLoggerEnabled) {
			AtomicReference<String> responseBodyRef = new AtomicReference<>();
			try {
				ServerHttpRequest request = new RecorderServerHttpRequestDecorator(exchange.getRequest());
				ServerHttpResponse response = createServerHttpResponse(exchange.getResponse(), responseBodyRef);
				ServerWebExchange newExchange = exchange.mutate().request(request).response(response).build();
				long startTime = System.currentTimeMillis();
				return chain.filter(newExchange).then(Mono.fromRunnable(() -> {
					long totalTime = System.currentTimeMillis() - startTime;
					if (hasBody(request.getMethod())) {
						request.getBody().buffer().subscribe(dataBuffers -> handleData(dataBuffers, request, responseBodyRef.get(), totalTime));
					} else {
						handleData(null, request, responseBodyRef.get(), totalTime);
					}
				}));
			} catch (Exception e) {
				log.info(" <------ 记录日志内容出错: {}", ExceptionUtils.getStackTrace(e));
			}
		}
		return chain.filter(exchange);
	}

	private static void handleData(List<DataBuffer> dataBufferList, ServerHttpRequest request, String responseBody, long totalTime) {
		String rawPath = request.getURI().getRawPath();
		String requestHeader = JSONUtils.toJSONString(request.getHeaders());
		String requestParam;
		if (dataBufferList != null) {
			StringBuilder sb = decodeDataBuffer(dataBufferList);
			requestParam = sb.toString();
		} else {
			requestParam = JSONUtils.toJSONString(request.getQueryParams());
		}
		// 异步记录到MongoDB
		RequestLog requestLog = new RequestLog(rawPath, requestHeader, requestParam, responseBody, totalTime);
		log.info(" <------ 记录日志内容: {}", requestLog);
		asyncSave(HeaderHelper.getRequestId(), requestLog);

	}

	private static StringBuilder decodeDataBuffer(List<DataBuffer> dataBufferList) {
		StringBuilder sb = new StringBuilder();
		dataBufferList.stream().forEach(dataBuffer -> sb.append(decodeDataBuffer(dataBuffer)));
		return sb;
	}

	private static String decodeDataBuffer(DataBuffer dataBuffer) {
		CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
		DataBufferUtils.release(dataBuffer);
		return charBuffer.toString();
	}

	private ServerHttpResponse createServerHttpResponse(ServerHttpResponse originalResponse, AtomicReference<String> bodyRef) {
		DataBufferFactory bufferFactory = originalResponse.bufferFactory();
		return new ServerHttpResponseDecorator(originalResponse) {
			@Override
			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
				if (body instanceof Flux) {
					Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
					return super.writeWith(fluxBody.map(dataBuffer -> {
						byte[] content = new byte[dataBuffer.readableByteCount()];
						dataBuffer.read(content);
						DataBufferUtils.release(dataBuffer);
						String bodyStr = new String(content, Charset.forName("UTF-8"));
						bodyRef.set(bodyStr);
						return bufferFactory.wrap(content);
					}));
				}
				return super.writeWith(body);
			}
		};
	}

	private static void asyncSave(String requestId, RequestLog requestLog) {
		try {
			ThreadPoolTaskExecutorFactory.getThreadPoolTaskExecutor().submit(() -> log.info(requestLog));
		} catch (Exception e) {
			log.error(" <----- 保存日志发生错误, 请求流水号:{}, 异常堆栈：{}", requestId, ExceptionUtils.getStackTrace(e));
		}
	}

	private static boolean hasBody(HttpMethod method) {
		return (method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH);
	}

}
