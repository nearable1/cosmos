package com.xiaoniu.walking.web.api.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 订单Feign接口层配置
 *
 * @author SteveGuo
 * @date 2017-12-25 14:25 M
 */
@Configuration
@EnableFeignClients(basePackages = "com.xiaoniu.walking.web.api")
public class WebApiConfiguration {
}
