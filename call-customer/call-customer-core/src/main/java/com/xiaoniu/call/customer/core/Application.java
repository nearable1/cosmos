package com.xiaoniu.call.customer.core;

import com.xiaoniu.architecture.commons.cloud.configuration.CloudConfiguration;
import com.xiaoniu.architecture.commons.web.configuration.WebMvcConfig;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 应用程序启动类
 *
 * @author SteveGuo
 * @date 2018-08-09 14:49 PM
 */
@MapperScan("com.xiaoniu.call.customer.core.mapper")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xiaoniu.call.customer.core"})
@Import({
        CloudConfiguration.class,
        WebMvcConfig.class
})
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);

    }
}
