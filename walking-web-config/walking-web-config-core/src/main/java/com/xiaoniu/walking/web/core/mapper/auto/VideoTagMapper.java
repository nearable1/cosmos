package com.xiaoniu.walking.web.core.mapper.auto;


import com.xiaoniu.walking.web.core.dto.VideoTagDTO;
import com.xiaoniu.walking.web.core.dto.VideoTagWebDTO;
import com.xiaoniu.walking.web.core.model.auto.VideoTag;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoTagMapper extends Mapper<VideoTag> {

    List<VideoTagDTO> selectValidList();

    List<VideoTagWebDTO> selectList();

    List<VideoTag> selectListAll();

    String selectMaximumNumber();
}