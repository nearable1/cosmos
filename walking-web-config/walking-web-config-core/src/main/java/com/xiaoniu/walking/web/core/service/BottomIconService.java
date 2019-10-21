package com.xiaoniu.walking.web.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.api.vo.BottomIconVO;
import com.xiaoniu.walking.web.core.bo.BottomIconPageBO;
import com.xiaoniu.walking.web.core.bo.BottonIconBO;
import com.xiaoniu.walking.web.core.model.auto.BottomIconMarket;

import java.util.List;

/**
 * 底部icon
 *
 * @author lihoujing
 * @date 2019/6/24 20:19
 */
public interface BottomIconService {


    /**
     * 获取底部icon列表
     *
     * @return
     */
    List<BottomIconVO> getBottomIconList();


    /**
     * 后台查询底部icon列表
     *
     * @param bottomIconBO
     * @return
     */
    PageResult<BottomIconVO> queryList(BottomIconPageBO bottomIconBO);

    /**
     * 保存底部icon
     * @param bottomIconBO
     * @return
     */
    int saveIcon(BottonIconBO bottomIconBO);

    /**
     * 更新底部icon
     * @param bottomIconBO
     * @return
     */
    int updateIcon(BottonIconBO bottomIconBO);

    /**
     * 根据对象删除
     * @param bottomIcon
     * @return
     */
    int deleteIcon(BottonIconBO bottomIcon);


    /**
     * 获取BottomIconMarket列表
     *
     * @param iconId
     * @return
     */
    List<BottomIconMarket> getBottomIconMarket(Integer iconId);
}
