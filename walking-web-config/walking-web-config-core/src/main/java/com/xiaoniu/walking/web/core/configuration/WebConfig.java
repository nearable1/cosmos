package com.xiaoniu.walking.web.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoniu.architecture.commons.core.util.DateUtils;
import com.xiaoniu.architecture.lending.commons.web.converter.StringToEnumConverterFactory;
import com.xiaoniu.architecture.lending.commons.web.interceptor.HttpContextInterceptor;
import com.xiaoniu.architecture.lending.commons.web.interceptor.ParameterInterceptor;
import com.xiaoniu.walking.web.core.interceptor.CommonInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiangxianjin
 * @date: 2019/4/9 17:37
 * @description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        UrlBasedCorsConfigurationSource corsConfigurationSource =
//                CorsHelper.getCorsConfigurationSource(CorsHelper.getCorsConfiguration(), null);
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new CorsFilter(corsConfigurationSource));
//        filterRegistrationBean.setOrder(0);
//        return filterRegistrationBean;
//    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor()).order(0);
        registry.addInterceptor(new HttpContextInterceptor()).order(1);
        registry.addInterceptor(new ParameterInterceptor()).order(2);
    }



    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat smt = new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN);
        objectMapper.setDateFormat(smt);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        //设置中文编码格式
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        return mappingJackson2HttpMessageConverter;
    }
}
