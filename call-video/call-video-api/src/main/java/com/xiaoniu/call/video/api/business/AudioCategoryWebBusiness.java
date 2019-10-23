package com.xiaoniu.call.video.api.business;

import com.xiaoniu.call.video.api.bo.AudioClassificationBO;
import com.xiaoniu.call.video.api.bo.DeleteCategoryBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 音频分类对内接口
 *
 * @author liuyinkai
 * @date 2019-08-06
 */
@FeignClient(name = "audioCategoryWebBusiness", value = "video")
public interface AudioCategoryWebBusiness {

    /**
     * 新增
     *
     * @return
     */
    @PostMapping("/saveAudioCategory")
    void saveAudioCategory(@Valid @RequestBody AudioClassificationBO audioClassificationBO);

    /**
     * 删除
     *
     * @param deleteCategoryBO
     */
    @PostMapping("/deleteCategory")
    void deleteCategory(@Valid @RequestBody DeleteCategoryBO deleteCategoryBO);

    /**
     * 编辑
     *
     * @return
     */
    @PostMapping("/updateAudioCategory")
    void updateAudioCategory(@Valid @RequestBody AudioClassificationBO audioClassificationBO);
}
