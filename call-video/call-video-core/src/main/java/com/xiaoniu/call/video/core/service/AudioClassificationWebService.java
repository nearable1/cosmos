package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.AudioClassificationBO;
import com.xiaoniu.call.video.api.bo.AudioClassificationPageBO;
import com.xiaoniu.call.video.api.dto.AudioClassificationWebDTO;

/**
 * 音频管理端业务
 *
 * @author liuyinkai
 * @date 2019-07-30
 */
public interface AudioClassificationWebService {

    /**
     * 分页查询音频分类
     *
     * @return
     */
    PageResult<AudioClassificationWebDTO> pageList(AudioClassificationPageBO audioClassificationPageBO);

    /**
     * 插入音频分类
     *
     * @return
     */
    int insertAudioClassification(AudioClassificationBO audioClassificationBO);

    /**
     * 删除
     *
     * @param id
     */
    void deleteCategory(Integer id);

    /**
     * 更新
     *
     * @param audioClassificationBO
     * @return
     */
    int updateCategory(AudioClassificationBO audioClassificationBO);

}
