package com.xiaoniu.call.api.gateway.core;

import com.xiaoniu.architecture.commons.core.configuration.XiaoNiuCommonsCoreConfiguration;
import com.xiaoniu.architecture.commons.reactive.configuration.ReactiveConfiguration;
import com.xiaoniu.call.customer.api.configuration.CustomerFeignApiConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 应用程序启动类
 *
 * @author SteveGuo
 * @date 2018-08-09 14:49 PM
 */
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.xiaoniu.call.api.gateway.core",
        exclude = {ErrorWebFluxAutoConfiguration.class})
@Import({ReactiveConfiguration.class, XiaoNiuCommonsCoreConfiguration.class, CustomerFeignApiConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
