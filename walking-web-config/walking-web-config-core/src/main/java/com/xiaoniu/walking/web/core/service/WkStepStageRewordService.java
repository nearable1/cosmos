package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.WkStepStageRewordBO;
import com.xiaoniu.walking.web.core.model.ext.WkStepStageRewordExt;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 阶段奖励管理
 *
 * @author liuyinkai
 * @date 20190919
 */
public interface WkStepStageRewordService {

        /**
         * 查询
         *
         * @param wkStepStageRewordBO
         * @return
         */
        PageResult<WkStepStageRewordExt> getManagement(@RequestBody WkStepStageRewordBO wkStepStageRewordBO);

        /**
         * 根据主键查找
         *
         * @param id
         * @return
         */
        WkStepStageRewordExt getInfoById(Integer id);

        /**
         * insert
         *
         * @param wkStepStageRewordBO
         * @return
         */
        int rewordInsert(WkStepStageRewordBO wkStepStageRewordBO);

        /**
         * update
         *
         * @param wkStepStageRewordBO
         * @return
         */
        int rewordUpdate(WkStepStageRewordBO wkStepStageRewordBO);

        /**
         * 删除
         *
         * @param id
         * @return
         */
        int rewordDelete(int id);


}
