package com.xiaoniu.call.video.core.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.core.util.BeanCopierUtils;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.docking.api.LaoWangApi;
import com.xiaoniu.call.docking.request.laowang.VideoListRequest;
import com.xiaoniu.call.docking.response.laowang.VideoCategoryResponse;
import com.xiaoniu.call.docking.response.laowang.VideoListDataListResponse;
import com.xiaoniu.call.docking.response.laowang.VideoListDataResponse;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.dto.LaoWangVideoDTO;
import com.xiaoniu.call.video.core.entity.LaoWangVideo;
import com.xiaoniu.call.video.core.service.LaoWangService;
import com.xiaoniu.call.video.core.util.RandomUtils;
import com.xiaoniu.call.video.core.vo.LaoWangVideoVO;
import com.xiaoniu.call.video.api.enums.VideoBusinessEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 老王视频业务
 *
 * @author wuwen
 * @date 2019-07-06 14:25
 */
@Service
@Log4j2
public class LaoWangServiceImpl implements LaoWangService {

    /**
     * 老王视频列表
     *
     * @param laoWangVideoVO
     * @return
     */
    @Override
    public List<LaoWangVideoDTO> videoList(LaoWangVideoVO laoWangVideoVO) {
        String lastVideoId = laoWangVideoVO.getLastVideoId();
        String typeNumber = laoWangVideoVO.getTypeNumber();
        String deviceId = HeaderHelper.getDeviceId();
        if (StringUtils.isEmpty(lastVideoId)) {
            lastVideoId = RedisRepository.hget(String.format(RedisConstants.LAO_WANG_LAST_ID, deviceId), typeNumber);
        }

        VideoListRequest videoListRequest = new VideoListRequest();
        videoListRequest.setToken(token());
        videoListRequest.setLastId(lastVideoId);
        videoListRequest.setPageSize(laoWangVideoVO.getPageSize());
        videoListRequest.setCategoryCode(laoWangVideoVO.getTypeNumber());
        VideoListDataResponse videoListDataResponse = LaoWangApi.videoList(videoListRequest);
        List<VideoListDataListResponse> list = videoListDataResponse.getList();
        if (CollectionUtils.isEmpty(list)) {
            // 查老王接口无数据，清空lastId缓存
            RedisRepository.hdel(String.format(RedisConstants.LAO_WANG_LAST_ID, deviceId), typeNumber);
            return null;
        }

        List<LaoWangVideoDTO> laoWangVideoDTOS = new ArrayList<>();
        String cdn = videoListDataResponse.getCdn();
        list.forEach(video -> {
            LaoWangVideoDTO laoWangVideoDTO = BeanCopierUtils.copy(video, LaoWangVideoDTO.class);
            //判断视频链接是否由http开头
            if (!video.getUrl().startsWith("http")) {
                laoWangVideoDTO.setUrl(new StringBuffer(cdn).append("/").append(laoWangVideoDTO.getUrl()).toString());
                laoWangVideoDTO.setCoverImage(new StringBuffer(cdn).append("/").append(laoWangVideoDTO.getCoverImage()).toString());
            }

            String videoId = video.getVideoId();
            Integer views = queryPlayCount(videoId);
            if (null == views) {
                views = RandomUtils.random(10000, 30000);
                saveLaoWangVideo(videoId, views);
            }
            laoWangVideoDTO.setWatchedTimes(views);
            laoWangVideoDTOS.add(laoWangVideoDTO);
        });

        if (!Objects.equals(list.size(), laoWangVideoVO.getPageSize())) {
            // 老王返回数据数与分页数不相符，清空lastId缓存
            RedisRepository.hdel(String.format(RedisConstants.LAO_WANG_LAST_ID, deviceId), typeNumber);
        } else if (StringUtils.isNotEmpty(lastVideoId)) {
            // 老王返回数据与分页数相符，且lastId不为空，表示当前非最后一页，设置lastId缓存
            RedisRepository.hset(String.format(RedisConstants.LAO_WANG_LAST_ID, deviceId), typeNumber, lastVideoId, RedisConstants.LAO_WANG_LAST_ID_EXPIRED);
        }

        return laoWangVideoDTOS;
    }

    /**
     * 播放视频
     *
     * @param videoNumber
     */
    @Override
    public void playVideo(String videoNumber) {
        Update update = new Update().inc("views", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, LaoWangVideo.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[老王视频]增加视频={}播放数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.LAO_WANG_VIDEO_PLAYBACK_FAILED);
        }
    }

    /**
     * 分类列表
     *
     * @return
     */
    @Override
    public List<VideoCategoryResponse> categoryList() {
        return LaoWangApi.videoCategory(token());
    }

    private void saveLaoWangVideo(String videoId, Integer views) {
        long currentTime = System.currentTimeMillis();
        LaoWangVideo laoWangVideo = new LaoWangVideo();
        laoWangVideo.setVideoNumber(videoId);
        laoWangVideo.setViews(views);
        laoWangVideo.setUpdateTime(currentTime);
        laoWangVideo.setCreateTime(currentTime);
        try {
            MongodbRepository.save(laoWangVideo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[老王视频]初始化异常，异常信息={}", e.getMessage());
        }
    }

    private Integer queryPlayCount(String videoNumber) {
        LaoWangVideo laoWangVideo = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), LaoWangVideo.class);
        if (null != laoWangVideo) {
            return laoWangVideo.getViews();
        }

        return null;
    }

    private String token() {
        String token = RedisRepository.get(RedisConstants.LAO_WANG_TOKEN);
        if (StringUtils.isNotEmpty(token)) {
            return token;
        }

        token = LaoWangApi.login();
        RedisRepository.set(RedisConstants.LAO_WANG_TOKEN, token, RedisConstants.LAO_WANG_TOKEN_EXPIRED);
        return token;
    }
}
