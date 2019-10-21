package com.xiaoniu.call.api.gateway.core.filter;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author guoqiang
 */
public class ResponseAdapter implements ClientHttpResponse {

    private final Flux<DataBuffer> flux;
    private final HttpHeaders headers;

    public ResponseAdapter(Publisher<? extends DataBuffer> body, HttpHeaders headers) {
        this.headers = headers;
        if (body instanceof Flux) {
            flux = (Flux) body;
        } else {
            flux = ((Mono) body).flux();
        }
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return flux;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public HttpStatus getStatusCode() {
        return null;
    }

    @Override
    public int getRawStatusCode() {
        return 0;
    }

    @Override
    public MultiValueMap<String, ResponseCookie> getCookies() {
        return null;
    }

}

