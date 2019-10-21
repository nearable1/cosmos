package com.xiaoniu.walking.web.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 视频配置
 *
 * @author wuwen
 * @date 2019-07-04
 */
@Data
@RefreshScope
@Component("videoCDNProperties")
@ConfigurationProperties(prefix = VideoCDNProperties.VIDEO_PREFIX)
public class VideoCDNProperties {

    public static final String VIDEO_PREFIX = "video.source";

    /**
     * 视频源cdn域名配置
     */
    private Map<Integer, String> cdn;
}
