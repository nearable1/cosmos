package com.xiaoniu.call.video.core.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.core.autoconfigure.VideoCDNProperties;
import com.xiaoniu.call.video.core.constants.BusinessConstants;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.dto.VideoDTO;
import com.xiaoniu.call.video.core.dto.VideoTagDTO;
import com.xiaoniu.call.video.core.mapper.VideoCollectionMapper;
import com.xiaoniu.call.video.core.mapper.VideoTagMapper;
import com.xiaoniu.call.video.core.service.VideoTagService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 视频标签
 *
 * @author wuwen
 * @date 2019-07-04 19:33
 */
@Service
@Log4j2
public class VideoTagServiceImpl implements VideoTagService {

    @Autowired
    private VideoTagMapper videoTagMapper;

    @Autowired
    private VideoCDNProperties videoCDNProperties;

    @Autowired
    private VideoCollectionMapper videoCollectionMapper;

    /**
     * 查询所属列表和视频
     *
     * @return
     */
    @Override
    public List<VideoTagDTO> selectListAndVideo() {

        String value = RedisRepository.get(RedisConstants.VIDEO_TAG);
        List<VideoTagDTO> videoTagDTOS = null;
        if (StringUtils.isNotBlank(value)) {
            videoTagDTOS = JSONUtils.parseObject(value, new TypeReference<List<VideoTagDTO>>() {
            });

            videoTagDTOS.forEach(videoTagDTO -> {
                videoTagDTO.getVideos().forEach(videoDTO -> {
                    int rows = videoCollectionMapper.selectCountByDeviceIdAndVideoNumber(HeaderHelper.getDeviceId(), videoDTO.getVideoNumber());
                    videoDTO.setLikeState(rows > 0);
                });
            });

            return videoTagDTOS;
        }

        videoTagDTOS = videoTagMapper.selectValidList();
        videoTagDTOS.forEach(videoTagDTO -> {
            Query query = Query.query(Criteria.where("tags").is(videoTagDTO.getTagNumber()).and("status").is(true).and("videoTypeTags").is("1"));
            PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "weight", "createTime"));
            Page<VideoDTO> result = MongodbRepository.findByPage(query, pageRequest, VideoDTO.class, BusinessConstants.VIDEO_COLLECTION_NAME);
            result.getContent().forEach(videoDTO -> {
                String cdn = videoCDNProperties.getCdn().get(0);
                videoDTO.setVideoCover(cdn + videoDTO.getVideoCover());
                videoDTO.setVideoAddress(cdn + videoDTO.getVideoAddress());
                videoDTO.setGifAddress(cdn + videoDTO.getGifAddress());
                videoDTO.setAudioAddress(cdn + videoDTO.getAudioAddress());
            });
            videoTagDTO.setVideos(result.getContent());
        });

        // 标签视频少于3个，剔除该标签
        videoTagDTOS = videoTagDTOS.stream().filter(videoTagDTO -> videoTagDTO.getVideos().size() >= 3).collect(Collectors.toList());
        RedisRepository.set(RedisConstants.VIDEO_TAG, JSONUtils.toJSONString(videoTagDTOS), RedisConstants.VIDEO_TAG_EXPIRED);
        return videoTagDTOS;
    }

    @Override
    public Map<String, String> tagMap() {
        Map<String, String> tagMap = RedisRepository.hgetall(RedisConstants.VIDEO_TAG_MAP);
        if (tagMap == null || tagMap.isEmpty()) {
            List<VideoTagDTO> videoTagDTOS = videoTagMapper.selectValidList();
            if (CollectionUtils.isNotEmpty(videoTagDTOS)) {
                tagMap = new HashMap<>();
                for (VideoTagDTO videoTagDTO : videoTagDTOS) {
                    tagMap.put(videoTagDTO.getTagNumber(), videoTagDTO.getTagName());
                    RedisRepository.hset(RedisConstants.VIDEO_TAG_MAP, videoTagDTO.getTagNumber(), videoTagDTO.getTagName());
                }
            }
        }
        return tagMap;
    }
}
