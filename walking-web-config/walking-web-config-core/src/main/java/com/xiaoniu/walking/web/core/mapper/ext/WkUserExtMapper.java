package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.core.bo.WkUserBO;
import com.xiaoniu.walking.web.core.mapper.BaseMapper;
import com.xiaoniu.walking.web.core.model.auto.WkUser;
import com.xiaoniu.walking.web.core.vo.WkUserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WkUserExtMapper extends BaseMapper<WkUser, String> {

    List<WkUserVO> selectByPage(WkUserBO wkUserBO);
}