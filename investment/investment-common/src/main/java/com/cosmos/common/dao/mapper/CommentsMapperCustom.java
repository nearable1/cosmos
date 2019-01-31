package com.cosmos.common.dao.mapper;


import com.cosmos.common.entity.pojo.Comments;
import com.cosmos.common.entity.pojo.vo.CommentsVo;
import com.cosmos.common.utils.MyMapper;

import java.util.List;

public interface CommentsMapperCustom extends MyMapper<Comments> {

    List<CommentsVo> queryComments(String videoId);
}