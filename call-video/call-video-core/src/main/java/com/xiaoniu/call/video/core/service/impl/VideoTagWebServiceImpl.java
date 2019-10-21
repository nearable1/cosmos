package com.xiaoniu.call.video.core.service.impl;

import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.dto.VideoTagWebDTO;
import com.xiaoniu.call.video.api.enums.VideoWebBusinessEnum;
import com.xiaoniu.call.video.api.vo.VideoTagPageVO;
import com.xiaoniu.call.video.core.constants.BusinessConstants;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.entity.Video;
import com.xiaoniu.call.video.core.entity.VideoTag;
import com.xiaoniu.call.video.core.mapper.VideoTagMapper;
import com.xiaoniu.call.video.core.service.VideoTagWebService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 视频标签
 *
 * @author wuwen
 * @date 2019-07-16 14:05
 */
@Service
@Log4j2
public class VideoTagWebServiceImpl implements VideoTagWebService {

    @Autowired
    private VideoTagMapper videoTagMapper;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public List<VideoTagWebDTO> selectList() {
        return videoTagMapper.selectList();
    }

    @Override
    public List<VideoTagWebDTO> selectListAll() {
        return videoTagMapper.selectListAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(VideoTagWebDTO videoTagWebDTO) {
        String maxTagNumber = videoTagMapper.selectMaximumNumber();
        if (StringUtils.isNotEmpty(maxTagNumber)) {
            int value = Integer.valueOf(StringUtils.remove(maxTagNumber, BusinessConstants.VIDEO_TAG_PREFIX)) + 1;
            maxTagNumber = BusinessConstants.VIDEO_TAG_PREFIX + StringUtils.leftPad(String.valueOf(value), 3, "0");
        } else {
            maxTagNumber = "VT001";
        }

        VideoTag videoTag = new VideoTag();
        videoTag.setTagNumber(maxTagNumber);
        videoTag.setTagName(videoTagWebDTO.getTagName());
        videoTag.setWeights(videoTagWebDTO.getWeights());
        videoTag.setStatus(1);
        int rows = videoTagMapper.insertSelective(videoTag);
        if (rows != 1) {
            throw new BusinessException(VideoWebBusinessEnum.VIDEO_TAG_MODIFICATION_FAILED);
        }
        RedisRepository.del(RedisConstants.VIDEO_TAG);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(VideoTagWebDTO videoTagWebDTO) {
        VideoTag videoTag = new VideoTag();
        videoTag.setId(videoTagWebDTO.getId());
        videoTag.setTagName(videoTagWebDTO.getTagName());
        videoTag.setWeights(videoTagWebDTO.getWeights());
        videoTag.setStatus(videoTagWebDTO.getStatus());
        videoTag.setUpdateTime(new Date());
        int rows = videoTagMapper.updateByPrimaryKeySelective(videoTag);
        if (rows != 1) {
            throw new BusinessException(VideoWebBusinessEnum.VIDEO_TAG_MODIFICATION_FAILED);
        }
        RedisRepository.del(RedisConstants.VIDEO_TAG);
    }

    @Override
    public PageResult<VideoTagWebDTO> pageList(VideoTagPageVO videoPageVO) {
        PageResult<VideoTagWebDTO> pageResult = pageRepository.selectPaging(VideoTagMapper.class, "selectListAll", videoPageVO);
        return pageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(VideoTagWebDTO videoTagWebDTO) {
        VideoTag videoTag = videoTagMapper.selectByPrimaryKey(videoTagWebDTO.getId());
        if (videoTag != null) {
            Criteria criteria = Criteria.where("tags").is(videoTag.getTagNumber());
            long count = MongodbRepository.countByClazz(new Query(criteria), Video.class);
            if (count > 0) {
                throw new BusinessException(VideoWebBusinessEnum.VIDEO_TAG_DELETE_FAILED);
            }
            int rows = videoTagMapper.deleteByPrimaryKey(videoTagWebDTO.getId());
            if (rows != 1) {
                throw new BusinessException(VideoWebBusinessEnum.VIDEO_TAG_MODIFICATION_FAILED);
            }
            RedisRepository.del(RedisConstants.VIDEO_TAG);
        }
    }

}
