package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.core.bo.WkStepStageRewordBO;
import com.xiaoniu.walking.web.core.model.auto.AdManagement;
import com.xiaoniu.walking.web.core.model.ext.WkStepStageRewordExt;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author liuyinkai
 */
public interface WkStepStageRewordExtMapper extends Mapper<AdManagement> {
    /**
     * 查询
     *
     * @param wkStepStageRewordBO
     * @return
     */
    List<WkStepStageRewordExt> getManagement(@RequestBody WkStepStageRewordBO wkStepStageRewordBO);

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