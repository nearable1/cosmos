package com.xiaoniu.call.video.core.service.impl;

import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.call.video.core.constants.BusinessConstants;
import com.xiaoniu.call.video.core.entity.VideoClassification;
import com.xiaoniu.call.video.core.mapper.VideoClassificationMapper;
import com.xiaoniu.call.video.core.service.VideoClassificationWebService;
import com.xiaoniu.call.video.api.dto.VideoClassificationWebDTO;
import com.xiaoniu.call.video.api.enums.VideoWebBusinessEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 视频分类
 *
 * @author wuwen
 * @date 2019-07-16 11:24
 */
@Service
@Log4j2
public class VideoClassificationWebServiceImpl implements VideoClassificationWebService {

    @Autowired
    private VideoClassificationMapper videoClassificationMapper;

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public List<VideoClassificationWebDTO> selectList() {
        return videoClassificationMapper.selectList();
    }

    /**
     * 修改
     *
     * @param videoClassificationWebDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(VideoClassificationWebDTO videoClassificationWebDTO) {
        VideoClassification videoClassification = new VideoClassification();
        videoClassification.setId(videoClassificationWebDTO.getId());
        videoClassification.setCategoryName(videoClassificationWebDTO.getCategoryName());
        videoClassification.setCategoryIcon(videoClassificationWebDTO.getCategoryIcon());
        videoClassification.setWeight(videoClassificationWebDTO.getWeight());
        videoClassification.setStatus(videoClassificationWebDTO.getStatus());
        videoClassification.setOperator(videoClassificationWebDTO.getOperator());
        int rows = videoClassificationMapper.updateByPrimaryKeySelective(videoClassification);
        if (rows != 1) {
            throw new BusinessException(VideoWebBusinessEnum.VIDEO_CLASSIFICATION_MODIFICATION_FAILED);
        }
    }

    /**
     * 添加
     *
     * @param videoClassificationWebDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(VideoClassificationWebDTO videoClassificationWebDTO) {
        String maxCategoryNumber = videoClassificationMapper.selectMaximumNumber();
        if (StringUtils.isNotEmpty(maxCategoryNumber)) {
            int value = Integer.valueOf(StringUtils.remove(maxCategoryNumber, BusinessConstants.VIDEO_CLASSIFICATION_PREFIX)) + 1;
            maxCategoryNumber = BusinessConstants.VIDEO_CLASSIFICATION_PREFIX + StringUtils.leftPad(String.valueOf(value), 3, "0");
        } else {
            maxCategoryNumber = BusinessConstants.VIDEO_CLASSIFICATION_PREFIX + "001";
        }

        VideoClassification videoClassification = new VideoClassification();
        videoClassification.setCategoryNumber(maxCategoryNumber);
        videoClassification.setCategoryName(videoClassificationWebDTO.getCategoryName());
        videoClassification.setCategoryIcon(videoClassificationWebDTO.getCategoryIcon());
        videoClassification.setWeight(videoClassificationWebDTO.getWeight());
        videoClassification.setStatus(videoClassificationWebDTO.getStatus());
        videoClassification.setOperator(videoClassificationWebDTO.getOperator());
        videoClassificationMapper.insertSelective(videoClassification);
    }
}
