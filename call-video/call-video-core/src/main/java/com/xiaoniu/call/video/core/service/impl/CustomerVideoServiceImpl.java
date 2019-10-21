package com.xiaoniu.call.video.core.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.enums.VideoBusinessEnum;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.entity.Video;
import com.xiaoniu.call.video.core.entity.VideoCollection;
import com.xiaoniu.call.video.core.enums.VideoBehaviorTypeEnum;
import com.xiaoniu.call.video.core.mapper.VideoCollectionMapper;
import com.xiaoniu.call.video.core.service.CustomerVideoService;
import com.xiaoniu.call.video.core.service.VideoBehaviorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 客户视频操作
 *
 * @author wuwen
 * @date 2019-07-02 13:55
 */
@Service
@Log4j2
public class CustomerVideoServiceImpl implements CustomerVideoService {

    @Autowired
    private VideoCollectionMapper videoCollectionMapper;

    @Autowired
    private VideoBehaviorService videoBehaviorService;

    /**
     * 视频喜欢
     *
     * @param deviceId
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void like(String deviceId, String videoNumber) {
        int rows = videoCollectionMapper.selectCountByDeviceIdAndVideoNumber(deviceId, videoNumber);
        if (rows > 0) {
            throw new BusinessException(VideoBusinessEnum.ALREADY_LIKE_VIDEO);
        }

        VideoCollection videoCollection = new VideoCollection();
        videoCollection.setDeviceId(deviceId);
        videoCollection.setVideoNumber(videoNumber);
        Date currentTime = new Date();
        videoCollection.setUpdateTime(currentTime);
        videoCollection.setCreateTime(currentTime);
        videoCollectionMapper.insertSelective(videoCollection);

        videoBehaviorService.inster(videoNumber, VideoBehaviorTypeEnum.NUMBER_OF_FAVORITES);

        Query query = new Query(Criteria.where("videoNumber").is(videoNumber));
        Update update = new Update().inc("collectionNumberReal", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(query, update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[喜欢视频]增加视频={}喜欢数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.LIKE_VIDEO_FAILURE);
        }
    }

    /**
     * 取消视频喜欢
     *
     * @param deviceId
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelLike(String deviceId, String videoNumber) {
        int rows = videoCollectionMapper.selectCountByDeviceIdAndVideoNumber(deviceId, videoNumber);
        if (rows < 1) {
            throw new BusinessException(VideoBusinessEnum.CANCEL_VIDEO_LIKE);
        }

        int modifyCount = videoCollectionMapper.deleteByDeviceIdAndVideoNumber(deviceId, videoNumber);
        if (modifyCount != 1) {
            log.error("[喜欢视频]刪除deviceId={}喜欢的视频={}失败，数据影响行数={}", videoNumber, videoNumber, modifyCount);
            throw new BusinessException(VideoBusinessEnum.DELETE_LIKE_RECORD_FAILURE);
        }

        videoBehaviorService.inster(videoNumber, VideoBehaviorTypeEnum.CANCEL_COLLECTION);

        Query query = new Query(Criteria.where("videoNumber").is(videoNumber));
        Update update = new Update().inc("collectionNumberReal", -1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(query, update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[喜欢视频]减少视频={}喜欢数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.VIDEO_LIKE_NUMBER_REDUCTION_FAILURE);
        }
    }

    /**
     * 视频分享
     *
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void share(String videoNumber) {
        videoBehaviorService.inster(videoNumber, VideoBehaviorTypeEnum.FORWARDING_NUMBER);

        Update update = new Update().inc("forwardNumberReal", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[喜欢视频]增加视频={}分享数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.VIDEO_SHARING_FAILED);
        }
    }

    /**
     * 视频播放
     *
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void broadcast(String videoNumber) {
        videoBehaviorService.inster(videoNumber, VideoBehaviorTypeEnum.VIEWS);

        Update update = new Update().inc("viewReal", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[喜欢视频]增加视频={}播放数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.VIDEO_PLAYBACK_FAILED);
        }
    }

    /**
     * 探索视频播放
     *
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exploreVideoPlayback(String videoNumber) {
        RedisRepository.sadd(String.format(RedisConstants.EXPLORING_PLAY, HeaderHelper.getDeviceId()), RedisConstants.EXPLORING_PLAY_EXPIRED, videoNumber);

        videoBehaviorService.inster(videoNumber, VideoBehaviorTypeEnum.VIEWS);

        Update update = new Update().inc("viewReal", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[喜欢视频]增加视频={}播放数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.VIDEO_PLAYBACK_FAILED);
        }
    }

    /**
     * 设置来电秀
     *
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setCallerWallpaper(String videoNumber) {
        videoBehaviorService.inster(videoNumber, VideoBehaviorTypeEnum.SET_AS_THE_NUMBER_OF_CALLS);

        Update update = new Update().inc("callNumber", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[喜欢视频]增加视频={}设为来电秀数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.SET_CALLER_FAILED);
        }
    }

    /**
     * 设置来电铃声
     *
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRingtones(String videoNumber) {
        videoBehaviorService.inster(videoNumber, VideoBehaviorTypeEnum.SET_TO_TROUBLE);

        Update update = new Update().inc("alarmNumber", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[喜欢视频]增加视频={}设为闹钟数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.SET_CALLER_RINGTONE_FAILED);
        }
    }

    /**
     * 设置壁纸秀
     *
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setWallpaper(String videoNumber) {
        videoBehaviorService.inster(videoNumber, VideoBehaviorTypeEnum.SET_WALLPAPER);

        Update update = new Update().inc("wallpaperNumber", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[喜欢视频]增加视频={}设为壁纸失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.SET_WALLPAPER_FAILED);
        }
    }

    /**
     * 设置屏保秀
     *
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setScreensaver(String videoNumber) {
        videoBehaviorService.inster(videoNumber, VideoBehaviorTypeEnum.SET_SCREENSAVER);

        Update update = new Update().inc("screenSaverNumber", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[喜欢视频]增加视频={}设为屏保失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.SET_SCREENSAVER_FAILED);
        }
    }

    /**
     * 设置彩铃秀
     *
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRingtoneShow(String videoNumber) {
        videoBehaviorService.inster(videoNumber, VideoBehaviorTypeEnum.SET_RINGTONESHOW);

        Update update = new Update().inc("ringtoneShowNumber", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[喜欢视频]增加视频={}设为彩铃秀失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.SET_RINGTONESHOW_FAILED);
        }
    }
}
