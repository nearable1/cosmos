package com.xiaoniu.walking.web.core.helper;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * 跨域帮助类
 *
 * @author guozhengshui
 * @date 2018-11-07 10:32
 */
public class CorsHelper {

    /**
     * 获取CorsConfiguration
     * @return
     */
    public static CorsConfiguration getCorsConfiguration(){
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setMaxAge(18000L);
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
     * 获取UrlBasedCorsConfigurationSource
     * @param corsConfiguration
     * @param corsUris
     * @return
     */
    public static UrlBasedCorsConfigurationSource getCorsConfigurationSource(final CorsConfiguration corsConfiguration,
                                                                             List<String> corsUris){
        Assert.notNull(corsConfiguration, "CorsConfiguration不能为空");
        if(CollectionUtils.isEmpty(corsUris)) {
            corsUris = Arrays.asList("/**");
        }
        final UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsUris.parallelStream().forEach(corsUri -> corsConfigurationSource.registerCorsConfiguration(corsUri, corsConfiguration));
        return corsConfigurationSource;
    }

    /**
     * 获取UrlBasedCorsConfigurationSource
     * @param corsConfiguration
     * @param corsUris
     * @return
     */
    public static org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource getReactiveCorsConfigurationSource(final CorsConfiguration corsConfiguration,
                                                                             List<String> corsUris){
        Assert.notNull(corsConfiguration, "CorsConfiguration不能为空");
        if(CollectionUtils.isEmpty(corsUris)) {
            corsUris = Arrays.asList("/**");
        }
        final org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource corsConfigurationSource = new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
        corsUris.parallelStream().forEach(corsUri -> corsConfigurationSource.registerCorsConfiguration(corsUri, corsConfiguration));
        return corsConfigurationSource;
    }

}
