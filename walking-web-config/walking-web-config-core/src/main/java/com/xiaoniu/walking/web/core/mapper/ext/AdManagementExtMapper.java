package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.core.bo.AdManagementBO;
import com.xiaoniu.walking.web.core.model.auto.AdManagement;
import com.xiaoniu.walking.web.core.model.ext.AdManagementExt;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author liuyinkai
 */
public interface AdManagementExtMapper extends Mapper<AdManagement> {
    /**
     * 查询
     *
     * @param adManagementBO
     * @return
     */
    List<AdManagementExt> getAdManagement(AdManagementBO adManagementBO);

    /**
     * 根据主键查找
     * @param id
     * @return
     */
    AdManagementExt getInfoById(Integer id);

    /**
     * insert
     *
     * @param adManagementBO
     * @return
     */
    int adInsert(AdManagementBO adManagementBO);

    /**
     * update
     *
     * @param adManagementBO
     * @return
     */
    int adUpdate(AdManagementBO adManagementBO);

    /**
     * 位置重复校验
     *
     * @param position
     * @return
     */
    int checkPosition(String position);
}