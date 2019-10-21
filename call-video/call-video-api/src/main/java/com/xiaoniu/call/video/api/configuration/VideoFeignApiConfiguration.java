package com.xiaoniu.call.video.api.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 视频Feign接口层配置
 */
@Configuration
@EnableFeignClients(basePackages = "com.xiaoniu.call.video.api")
public class VideoFeignApiConfiguration {

}