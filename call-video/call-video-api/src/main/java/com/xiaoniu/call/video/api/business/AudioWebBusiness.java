package com.xiaoniu.call.video.api.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.AudioChangeWeightBO;
import com.xiaoniu.call.video.api.bo.AudioClassificationPageBO;
import com.xiaoniu.call.video.api.bo.AudioOnlineOfflineBO;
import com.xiaoniu.call.video.api.bo.AudioPageBO;
import com.xiaoniu.call.video.api.dto.AudioClassificationWebDTO;
import com.xiaoniu.call.video.api.vo.AudioVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 音频对内接口
 *
 * @author liuyinkai
 * @date 2019-07-30
 */
@FeignClient(name = "audioWebBusiness", value = "video")
public interface AudioWebBusiness {

    /**
     * 分页查询音频
     *
     * @return
     */
    @PostMapping("/getAudioList")
    PageResult<AudioVO> audioPageList(@Valid @RequestBody AudioPageBO audioPageBO);

    /**
     * 修改音频数据
     */
    @PutMapping("/{audioNumber}/updateAdudio")
    void updateAudio(@Valid @RequestBody AudioVO audioVO, @PathVariable("audioNumber") String audioNumber);

    /**
     * 分页查询音频分类
     *
     * @return
     */
    @PostMapping("/getAudioCategoryList")
    PageResult<AudioClassificationWebDTO> pageList(@Valid @RequestBody AudioClassificationPageBO audioClassificationPageBO);

    /**
     * 批量上线
     *
     * @param
     */
    @PostMapping("/audioBatchOnline")
    void audioBatchOnline(@RequestParam("ids") String[] ids);

    /**
     * 批量下线
     *
     * @param
     */
    @PostMapping("/audioBatchOffline")
    void audioBatchOffline(@RequestParam("ids") String[] ids);

    /**
     * 批量删除
     *
     * @param
     */
    @PostMapping("/audioBatchDelete")
    void audioBatchDelete(@RequestParam("ids") String[] ids);

    /**
     * 批量过审
     *
     * @param
     */
    @PostMapping("/audioBatchCheckPass")
    void audioBatchCheckPass(@RequestParam("ids") String[] ids);

    /**
     * 批量过审失败
     *
     * @param
     */
    @PostMapping("/audioBatchCheckNotPass")
    void audioBatchCheckNotPass(@RequestParam("ids") String[] ids);

    /**
     * 权重更改
     *
     * @param
     */
    @PostMapping("/audioChangeWeight")
    void audioChangeWeight(@RequestBody @Valid AudioChangeWeightBO audioChangeWeightBO);

    /**
     * 单行上下线
     */
    @PutMapping("/{audioNumber}/singleOnlineOffline")
    void singleOnlineOffline(@Valid @RequestBody AudioOnlineOfflineBO audioOnlineOfflineBO, @PathVariable("audioNumber") String audioNumber);

}
