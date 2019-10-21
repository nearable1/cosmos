package com.xiaoniu.call.video.core.mapper;

import com.xiaoniu.call.video.core.dto.VideoClassificationDTO;
import com.xiaoniu.call.video.core.entity.VideoClassification;
import com.xiaoniu.call.video.api.dto.VideoClassificationWebDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoClassificationMapper extends Mapper<VideoClassification> {

    List<VideoClassificationDTO> selectValidList();

    List<VideoClassificationWebDTO> selectList();

    String selectMaximumNumber();
}