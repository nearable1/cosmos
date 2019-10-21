package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.core.model.auto.AdManagementMarket;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AdManagementMarketExtMapper extends Mapper<AdManagementMarket> {


    /**
     * 获取关联的market
     *
     * @param managementId
     * @return
     */
    List<AdManagementMarket> getManagementMarketList(Integer managementId);



    List<AdManagementMarket> selectList(Integer managementId);
}