package com.xiaoniu.call.customer.api.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 认证Feign接口层配置
 */
@Configuration
@EnableFeignClients(basePackages = "com.xiaoniu.call.customer.api")
public class CustomerFeignApiConfiguration {

}