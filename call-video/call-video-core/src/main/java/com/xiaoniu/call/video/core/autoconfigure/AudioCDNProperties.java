package com.xiaoniu.call.video.core.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 音频配置
 *
 * @author liuyinkai
 * @date 2019-07-26
 */
@Data
@RefreshScope
@Component("audioCDNProperties")
@ConfigurationProperties(prefix = AudioCDNProperties.AUDIO_PREFIX)
public class AudioCDNProperties {

    public static final String AUDIO_PREFIX = "audio.source";

    /**
     * 音频源cdn域名配置
     */
    private Map<Integer, String> cdn;
}
