package com.xiaoniu.call.video.api.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.VideoSettingRatePageBO;
import com.xiaoniu.call.video.api.dto.VideoClassificationWebDTO;
import com.xiaoniu.call.video.api.dto.VideoTagWebDTO;
import com.xiaoniu.call.video.api.dto.VideoWebDTO;
import com.xiaoniu.call.video.api.vo.TeachingVideoPageVO;
import com.xiaoniu.call.video.api.vo.UpdateTeachingVideoVO;
import com.xiaoniu.call.video.api.vo.VideoPageVO;
import com.xiaoniu.call.video.api.vo.VideoSettingRatesVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 视频对内接口
 *
 * @author wuwen
 * @date 2019-07-15 16:58
 */
@FeignClient(value = "video")
public interface VideoWebBusiness {

    /**
     * 分页查询视频
     *
     * @return
     */
    @PostMapping("/page")
    PageResult<VideoWebDTO> pageList(@Valid @RequestBody VideoPageVO videoPageVO);

    /**
     * 修改视频数据
     */
    @PutMapping("/{videoNumber}")
    void updateVideo(@Valid @RequestBody VideoWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber);

    /**
     * 查询列表
     *
     * @return
     */
    @GetMapping("/classification")
    List<VideoClassificationWebDTO> videoClassification();

    /**
     * 修改
     *
     * @param videoClassificationWebDTO
     */
    @PutMapping("/classification")
    void updateVideoClassification(@Valid @RequestBody VideoClassificationWebDTO videoClassificationWebDTO);

    /**
     * 添加
     *
     * @param videoClassificationWebDTO
     */
    @PostMapping("/classification")
    void insertVideoClassification(@Valid @RequestBody VideoClassificationWebDTO videoClassificationWebDTO);

    /**
     * 查询列表
     *
     * @return
     */
    @GetMapping("/tag")
    List<VideoTagWebDTO> videoTag();

    /**
     * 修改
     *
     * @param videoTagWebDTO
     */
    @PutMapping("/tag")
    void updateVideoTag(@Valid @RequestBody VideoTagWebDTO videoTagWebDTO);

    /**
     * 添加
     *
     * @param videoTagWebDTO
     */
    @PostMapping("/tag")
    void insertVideoTag(@Valid @RequestBody VideoTagWebDTO videoTagWebDTO);

    /**
     * 批量上线
     *
     * @param
     */
    @PostMapping("/batchOnline")
    void batchOnline(@RequestParam("ids") String[] ids);

    /**
     * 批量下线
     *
     * @param
     */
    @PostMapping("/batchOffline")
    void batchOffline(@RequestParam("ids") String[] ids);

    /**
     * 删除视频数据
     */
    @DeleteMapping("/delete/{videoNumber}")
    void deleteVideo(@PathVariable("videoNumber") String videoNumber);

    /**
     * 批量下线
     *
     * @param
     */
    @PostMapping("/batchDelete")
    void batchDelete(@RequestParam("ids") String[] ids);

    /**
     * 分页查询视频
     *
     * @return
     */
    @PostMapping("/review/page")
    PageResult<VideoWebDTO> reviewPageList(@Valid @RequestBody VideoPageVO videoPageVO);

    /**
     * 修改视频数据
     */
    @PutMapping("/review/{videoNumber}")
    void updateReviewVideo(@Valid @RequestBody VideoWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber);

    /**
     * 删除视频数据
     */
    @DeleteMapping("/review/delete/{videoNumber}")
    void deleteReviewVideo(@PathVariable("videoNumber") String videoNumber);

    /**
     * 批量上线
     *
     * @param
     */
    @PostMapping("/review/batchOnline")
    void reviewBatchOnline(@RequestParam("ids") String[] ids);

    /**
     * 批量下线
     *
     * @param
     */
    @PostMapping("/review/batchDelete")
    void reviewBatchDelete(@RequestParam("ids") String[] ids);

    /**
     * 批量分类
     *
     * @param
     */
    @PostMapping("/batchUpdateType")
    void batchUpdateType(@RequestParam("ids") String[] ids, @RequestParam("categoryNumber") String categoryNumber);

    /**
     * 批量标签
     *
     * @param
     */
    @PostMapping("/batchUpdateTag")
    void batchUpdateTag(@RequestParam("ids") String[] ids, @RequestParam("tags") List<String> tags);

    /**
     * 查看新手教学视频
     *
     * @return
     */
    @PostMapping("/getTeachingVideo")
    PageResult<VideoWebDTO> getTeachingVideo(@Valid @RequestBody TeachingVideoPageVO teachingVideoPageVO);

    /**修改新手教学视频
     *
     * @return
     */
    @PostMapping("/updateTeachingVideo")
    void updateTeachingVideo(@Valid @RequestBody UpdateTeachingVideoVO updateTeachingVideoVO);

    /**
     * 获取音视频cdn url
     *
     * @return
     */
    @PostMapping("/getVideoAudioApolloConfig")
    String getVideoAudioApolloConfig();

    /**
     * 分页查询音频分类
     *
     * @return
     */
    @PostMapping("/getSettingRatesRank")
    PageResult<VideoSettingRatesVO> pageList(@Valid @RequestBody VideoSettingRatePageBO videoSettingRatePageBO);
}
