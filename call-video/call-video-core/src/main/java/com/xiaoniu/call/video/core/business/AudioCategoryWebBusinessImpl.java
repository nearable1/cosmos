package com.xiaoniu.call.video.core.business;

import com.xiaoniu.call.video.api.bo.AudioClassificationBO;
import com.xiaoniu.call.video.api.bo.DeleteCategoryBO;
import com.xiaoniu.call.video.api.business.AudioCategoryWebBusiness;
import com.xiaoniu.call.video.core.service.AudioClassificationWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 音频分类管理端
 *
 * @author liuyinkai
 * @date 2019-08-06
 */
@RestController
public class AudioCategoryWebBusinessImpl implements AudioCategoryWebBusiness {

    @Autowired
    private AudioClassificationWebService audioClassificationWebService;

    /**
     * add
     *
     * @param audioClassificationBO
     */
    @Override
    public void saveAudioCategory(@Valid AudioClassificationBO audioClassificationBO) {
        audioClassificationWebService.insertAudioClassification(audioClassificationBO);
    }

    /**
     * delete
     *
     * @param deleteCategoryBO
     */
    @Override
    public void deleteCategory(@Valid DeleteCategoryBO deleteCategoryBO) {
        audioClassificationWebService.deleteCategory(deleteCategoryBO.getId());
    }

    /**
     * update
     *
     * @param audioClassificationBO
     */
    @Override
    public void updateAudioCategory(@Valid AudioClassificationBO audioClassificationBO) {
        audioClassificationWebService.updateCategory(audioClassificationBO);
    }
}
