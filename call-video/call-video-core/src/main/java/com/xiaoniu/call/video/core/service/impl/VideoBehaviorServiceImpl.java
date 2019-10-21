package com.xiaoniu.call.video.core.service.impl;

import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.call.video.core.entity.Video;
import com.xiaoniu.call.video.core.entity.VideoBehavior;
import com.xiaoniu.call.video.core.enums.VideoBehaviorTypeEnum;
import com.xiaoniu.call.video.core.mapper.VideoBehaviorMapper;
import com.xiaoniu.call.video.core.service.VideoBehaviorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 视频行为记录
 *
 * @author wuwen
 * @date 2019-07-18 22:32
 */
@Service
@Log4j2
public class VideoBehaviorServiceImpl implements VideoBehaviorService {

    @Autowired
    private VideoBehaviorMapper videoBehaviorMapper;

    /**
     * 添加数据
     *
     * @param videoNumber
     * @param videoBehaviorType
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void inster(String videoNumber, VideoBehaviorTypeEnum videoBehaviorType) {
        Video video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), Video.class);
        if (null == video) {
            return;
        }

        VideoBehavior videoBehavior = new VideoBehavior();
        videoBehavior.setVideoNumber(videoNumber);
        videoBehavior.setTitle(video.getTitle());
        videoBehavior.setVideoType(video.getVideoType());
        videoBehavior.setType(videoBehaviorType.getValue());
        videoBehavior.setTags(JSONUtils.toJSONString(video.getTags()));
        videoBehavior.setCategoryNumber(video.getCategoryNumber());
        videoBehavior.setVideoSource(video.getVideoSource());
        videoBehavior.setWeight(video.getWeight());
        videoBehaviorMapper.insertSelective(videoBehavior);
    }
}
