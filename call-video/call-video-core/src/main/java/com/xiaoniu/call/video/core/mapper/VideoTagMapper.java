package com.xiaoniu.call.video.core.mapper;

import com.xiaoniu.call.video.core.dto.VideoTagDTO;
import com.xiaoniu.call.video.core.entity.VideoTag;
import com.xiaoniu.call.video.api.dto.VideoTagWebDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoTagMapper extends Mapper<VideoTag> {

    List<VideoTagDTO> selectValidList();

    List<VideoTagWebDTO> selectList();

    List<VideoTagWebDTO> selectListAll();

    String selectMaximumNumber();
}