package com.xiaoniu.walking.web.core.service.impl;


import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.walking.web.core.constants.RedisConstants;
import com.xiaoniu.walking.web.core.model.auto.Video;
import com.xiaoniu.walking.web.core.service.VideoWebService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * 视频管理端业务实现
 *
 * @author wuwen
 * @date 2019-07-15 21:16
 */
@Service
@Log4j2
public class VideoWebServiceImpl implements VideoWebService {


    /**
     * 添加视频到视频池
     *
     * @param videoNumber
     */
    @Override
    public void addVideoPool(String videoNumber) {
        Video video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), Video.class);
        if (video != null) {
            if (video.getVideoTypeTags().contains("1")) {
                // 首页视频才添加到池中
                if (CollectionUtils.isNotEmpty(video.getVideoDbTags())) {
                    // 首页总池添加
                    for (String tags : video.getVideoDbTags()) {
                        RedisRepository.sadd(String.format(RedisConstants.VIDEO_POOL_CATEGORY_HOME, tags), videoNumber);
                    }

                    // 分类总池添加
                    RedisRepository.sadd(String.format(RedisConstants.VIDEO_POOL_CLASSIFIED, video.getCategoryNumber()), videoNumber);
                }
            }
            if (video.getVideoTypeTags().contains("3")) {
                // 小视频池
                RedisRepository.sadd(RedisConstants.VIDEO_POOL_SMALL, videoNumber);
            }
        }
    }

}
