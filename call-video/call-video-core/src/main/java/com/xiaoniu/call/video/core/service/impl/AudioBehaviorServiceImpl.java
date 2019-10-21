package com.xiaoniu.call.video.core.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.call.video.core.entity.Audio;
import com.xiaoniu.call.video.core.entity.AudioBehavior;
import com.xiaoniu.call.video.core.enums.AudioBehaviorTypeEnum;
import com.xiaoniu.call.video.core.mapper.AudioBehaviorMapper;
import com.xiaoniu.call.video.core.service.AudioBehaviorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 音频行为记录
 *
 * @author liuyinkai
 * @date 2019-07-25
 */
@Service
@Log4j2
public class AudioBehaviorServiceImpl implements AudioBehaviorService {

    @Autowired
    private AudioBehaviorMapper audioBehaviorMapper;

    /**
     * 添加数据
     *
     * @param audioNumber
     * @param audioBehaviorTypeEnum
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordAudioBehavior(String audioNumber, AudioBehaviorTypeEnum audioBehaviorTypeEnum) {
        Audio audio = MongodbRepository.findByClazz(new Query(Criteria.where("audioNumber").is(audioNumber)), Audio.class);
        if (null == audio) {
            log.error("【音频行为记录】MongoDB未查找到该音频编号={}", audioNumber);
            return;
        }

        AudioBehavior audioBehavior = new AudioBehavior();
        audioBehavior.setAudioNumber(audioNumber);
        audioBehavior.setTitle(audio.getTitle());
        audioBehavior.setType(audioBehaviorTypeEnum.getValue());
        audioBehavior.setCategoryNumber(audio.getCategoryNumber());
        audioBehavior.setAudioSource(audio.getAudioSource());
        audioBehavior.setWeight(audio.getWeight());
        int i = audioBehaviorMapper.insertSelective(audioBehavior);
        if ( i < 1) {
            log.error("【音频行为记录】记录音频行为失败,失败参数={}", JSONUtils.toJSONString(audioBehavior));
        }
    }
}
