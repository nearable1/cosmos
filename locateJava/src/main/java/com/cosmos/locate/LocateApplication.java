package com.cosmos.locate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.cosmos.locate.dao")
public class LocateApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 设置启动类,用于独立tomcat运行的入口
        return builder.sources(LocateApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(LocateApplication.class, args);
    }
}
