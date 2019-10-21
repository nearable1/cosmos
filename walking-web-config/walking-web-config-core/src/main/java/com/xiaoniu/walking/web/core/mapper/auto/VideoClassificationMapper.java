package com.xiaoniu.walking.web.core.mapper.auto;


import com.xiaoniu.walking.web.core.dto.VideoClassificationDTO;
import com.xiaoniu.walking.web.core.dto.VideoClassificationWebDTO;
import com.xiaoniu.walking.web.core.model.auto.VideoClassification;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoClassificationMapper extends Mapper<VideoClassification> {

    List<VideoClassificationDTO> selectValidList();

    List<VideoClassificationWebDTO> selectList();

    String selectMaximumNumber();
}