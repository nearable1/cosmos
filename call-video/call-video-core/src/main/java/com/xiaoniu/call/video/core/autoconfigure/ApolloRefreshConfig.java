package com.xiaoniu.call.video.core.autoconfigure;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Apollo刷新配置
 *
 * @author guozhengshui
 * @date 2018-09-18 18:29
 */
@Component
public class ApolloRefreshConfig {

    private static final Logger LOGGER = LogManager.getLogger(ApolloRefreshConfig.class);

    @Autowired
    private RefreshScope refreshScope;

    @Autowired
    private VideoCDNProperties videoCDNProperties;

    @Autowired
    private AudioCDNProperties audioCDNProperties;


    @ApolloConfigChangeListener({"business.properties"})
    public void onChange(ConfigChangeEvent changeEvent) {
        if (changeEvent.changedKeys().parallelStream().anyMatch(
                changedKey -> changedKey.startsWith(VideoCDNProperties.VIDEO_PREFIX))) {
            LOGGER.info("before refresh videoCDNProperties：{}", videoCDNProperties);
            refreshScope.refresh("videoCDNProperties");
            LOGGER.info("after refresh videoCDNProperties：{}", videoCDNProperties);
        }
        if (changeEvent.changedKeys().parallelStream().anyMatch(
                changedKey -> changedKey.startsWith(AudioCDNProperties.AUDIO_PREFIX))) {
            LOGGER.info("before refresh audioCDNProperties：{}", audioCDNProperties);
            refreshScope.refresh("audioCDNProperties");
            LOGGER.info("after refresh audioCDNProperties：{}", audioCDNProperties);
        }

    }
}
