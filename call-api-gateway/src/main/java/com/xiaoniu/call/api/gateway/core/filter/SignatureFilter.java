package com.xiaoniu.call.api.gateway.core.filter;

import com.xiaoniu.architecture.commons.api.Header;
import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.core.signature.SignatureUtils;
import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.spring.boot.autoconfigure.security.access.AccessProperties;
import com.xiaoniu.architecture.spring.boot.autoconfigure.security.app.AppAccount;
import com.xiaoniu.architecture.spring.boot.autoconfigure.security.app.AppAccountProperties;
import com.xiaoniu.call.api.gateway.core.support.FilterPathMatcherHelper;
import com.xiaoniu.call.api.gateway.core.support.ServerWebExchangeHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 签名过滤器
 *
 * @author guoqiang
 * @date 2018-09-10 4:03 PM
 */
@Component
@Log4j2
public class SignatureFilter implements GlobalFilter, Ordered {

    private static final List<String> IGNORE_PROPERTIES;
    static {
        IGNORE_PROPERTIES = new ArrayList<>();
        IGNORE_PROPERTIES.add(StringUtils.convertDelimited2Hump(Header.NameEnum.CUSTOMER_ID.getName(), "-"));
        IGNORE_PROPERTIES.add(StringUtils.convertDelimited2Hump(Header.NameEnum.ACCESS_TOKEN.getName(), "-"));
    }

//    @Autowired
//    private AccessProperties accessProperties;
//    @Autowired
//    private AppAccountProperties appAccountProperties;

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String rawPath = exchange.getRequest().getURI().getRawPath();
        //log.info("SignatureFilter --> rawPath:{}", rawPath);

//        boolean noneMatch = FilterPathMatcherHelper.noneMatch(accessProperties.getNoneCheckHeaderUris(), rawPath);
//        if(noneMatch){
//            HeaderHelper.checkHeader();
//            Integer requestAgent = HeaderHelper.getRequestAgent();
//            Header.RequestAgentEnum requestAgentEnum = Header.RequestAgentEnum.resolve(requestAgent);
//            if(null == requestAgentEnum) {
//                log.error("APP传进来的requestAgent:{}不在允许服务端允许范围内", HeaderHelper.getRequestAgent());
//                return ServerWebExchangeHelper.writeWith(exchange, ResultCodeEnum.ILLEGAL_REQUEST_AGENT);
//            }
//            String key = requestAgentEnum.name().toLowerCase() + "-" + HeaderHelper.getAppName();
//            AppAccount appAccount = appAccountProperties.getAppAcount(key);
//            if(null == appAccount || StringUtils.isBlank(appAccount.getAppId())
//                    || StringUtils.isBlank(appAccount.getAppSecret())) {
//                return ServerWebExchangeHelper.writeWith(exchange, ResultCodeEnum.NONE_APP_ACCOUNT_DATA);
//            }
//            if(!appAccount.getAppId().equals(HeaderHelper.getAppId())) {
//                log.error("APP传进来的appId：{}与服务端appId：{}不一致", HeaderHelper.getAppId(), appAccount.getAppId());
//                return ServerWebExchangeHelper.writeWith(exchange, ResultCodeEnum.ILLEGAL_APP_ID);
//            }
//            String stringToSign = HeaderHelper.getAppId() + HeaderHelper.getTimestamp();
//            String targetSign = SignatureUtils.hashByHmacSHA256(stringToSign, appAccount.getAppSecret());
//            if(!targetSign.equals(HeaderHelper.getSign())) {
//                log.error("校验AppId：{}，时间戳：{}的签名：{}失败，服务器生成的签名是：{}", HeaderHelper.getAppId(),
//                        HeaderHelper.getTimestamp(), HeaderHelper.getSign(), targetSign);
//                return ServerWebExchangeHelper.writeWith(exchange, ResultCodeEnum.ILLEGAL_SIGNATURE);
//            }
//        }
        return chain.filter(exchange);
    }



}
