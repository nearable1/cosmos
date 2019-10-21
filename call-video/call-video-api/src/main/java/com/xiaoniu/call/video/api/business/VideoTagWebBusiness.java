package com.xiaoniu.call.video.api.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.AudioClassificationBO;
import com.xiaoniu.call.video.api.bo.DeleteCategoryBO;
import com.xiaoniu.call.video.api.dto.VideoTagWebDTO;
import com.xiaoniu.call.video.api.dto.VideoWebDTO;
import com.xiaoniu.call.video.api.vo.VideoPageVO;
import com.xiaoniu.call.video.api.vo.VideoTagPageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 音频分类对内接口
 *
 * @author liuyinkai
 * @date 2019-08-06
 */
@FeignClient(contextId = "videoTagWebBusiness", value = "video")
public interface VideoTagWebBusiness {

    /**
     * 分页查询视频
     *
     * @return
     */
    @PostMapping("/videoTag/page")
    PageResult<VideoTagWebDTO> pageList(@Valid @RequestBody VideoTagPageVO videoPageVO);

    /**
     * 新增
     *
     * @return
     */
    @PostMapping("/videoTag/save")
    void saveVideoTag(@Valid @RequestBody VideoTagWebDTO videoPageVO);

    /**
     * 删除
     *
     */
    @DeleteMapping("/videoTag/delete")
    void deleteVideoTag(@Valid @RequestBody VideoTagWebDTO videoPageVO);

    /**
     * 修改
     *
     * @return
     */
    @PutMapping("/videoTag/update")
    void updateVideoTag(@Valid @RequestBody VideoTagWebDTO videoPageVO);
}
