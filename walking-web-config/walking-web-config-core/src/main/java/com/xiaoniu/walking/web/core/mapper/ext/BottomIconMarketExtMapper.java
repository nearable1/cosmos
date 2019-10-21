package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.core.model.auto.BottomIconMarket;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BottomIconMarketExtMapper extends Mapper<BottomIconMarket> {


    List<Integer> getBottomIconMarketList(String market);



    List<BottomIconMarket> selectList(Integer iconId);
}