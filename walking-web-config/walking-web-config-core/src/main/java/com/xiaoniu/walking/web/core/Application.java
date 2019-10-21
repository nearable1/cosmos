package com.xiaoniu.walking.web.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 应用程序启动类
 * @author xiangxianjin
 * @date 2019-03-27 14:49 PM
 */

@EnableFeignClients
@EnableDiscoveryClient
@EnableAsync
@MapperScan(basePackages = "com.xiaoniu.walking.web.core.mapper")
@SpringBootApplication(scanBasePackages = "com.xiaoniu.walking")
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }

}
