package com.xiaoniu.call.api.gateway.core.filter.factory;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 抽象Uri网关过滤器工厂
 *
 * @author guoqiang
 * @date 2018-09-10 8:45 PM
 */
@Log4j2
public abstract class AbstractUriGatewayFilterFactory extends AbstractGatewayFilterFactory<AbstractUriGatewayFilterFactory.Config> {

    public static final String URI_KEY = "uri";

    public AbstractUriGatewayFilterFactory(){
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(URI_KEY);
    }

    public static class Config {

        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

}
