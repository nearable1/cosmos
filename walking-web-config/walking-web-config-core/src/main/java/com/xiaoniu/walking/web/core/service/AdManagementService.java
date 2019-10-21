package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.AdManagementBO;
import com.xiaoniu.walking.web.core.model.ext.AdManagementExt;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ad管理
 *
 * @author liuyinkai
 * @date 20190919
 */
public interface AdManagementService {

        /**
         * 查询
         *
         * @param adManagementBO
         * @return
         */
        PageResult<AdManagementExt> getAdManagement(@RequestBody AdManagementBO adManagementBO);

        /**
         * 根据主键查找
         *
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
