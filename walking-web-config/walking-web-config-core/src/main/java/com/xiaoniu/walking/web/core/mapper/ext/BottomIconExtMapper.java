package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.api.vo.BottomIconVO;
import com.xiaoniu.walking.web.core.bo.BottonIconBO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BottomIconExtMapper extends Mapper<BottomIconVO> {



    int insertBottomIcon(BottonIconBO bottonIconBO);


    List<BottomIconVO> getBottomIconList(@Param("appName") Integer appName, @Param("list") List list);



}