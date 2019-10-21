package com.xiaoniu.call.video.core.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.VideoSettingRatePageBO;
import com.xiaoniu.call.video.api.business.VideoWebBusiness;
import com.xiaoniu.call.video.api.dto.VideoClassificationWebDTO;
import com.xiaoniu.call.video.api.dto.VideoTagWebDTO;
import com.xiaoniu.call.video.api.dto.VideoWebDTO;
import com.xiaoniu.call.video.api.vo.TeachingVideoPageVO;
import com.xiaoniu.call.video.api.vo.UpdateTeachingVideoVO;
import com.xiaoniu.call.video.api.vo.VideoPageVO;
import com.xiaoniu.call.video.api.vo.VideoSettingRatesVO;
import com.xiaoniu.call.video.core.service.VideoClassificationWebService;
import com.xiaoniu.call.video.core.service.VideoReviewWebService;
import com.xiaoniu.call.video.core.service.VideoTagWebService;
import com.xiaoniu.call.video.core.service.VideoWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 视频管理端
 *
 * @author wuwen
 * @date 2019-07-16 10:38
 */
@RestController
public class VideoWebBusinessImpl implements VideoWebBusiness {


    @Autowired
    private VideoWebService videoWebService;

    @Autowired
    private VideoClassificationWebService videoClassificationWebService;

    @Autowired
    private VideoTagWebService videoTagWebService;

    @Autowired
    private VideoReviewWebService videoReviewWebService;

    /**
     * 分页查询视频
     *
     * @param videoPageVO
     * @return
     */
    @Override
    @PostMapping("/page")
    public PageResult<VideoWebDTO> pageList(@Valid @RequestBody VideoPageVO videoPageVO) {
        return videoWebService.pageList(videoPageVO);
    }

    /**
     * 修改视频数据
     *
     * @param videoWebDTO
     * @param videoNumber
     */
    @Override
    @PutMapping("/{videoNumber}")
    public void updateVideo(@Valid @RequestBody VideoWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber) {
        videoWebDTO.setVideoNumber(videoNumber);
        videoWebService.update(videoWebDTO);
    }

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    @GetMapping("/classification")
    public List<VideoClassificationWebDTO> videoClassification() {
        return videoClassificationWebService.selectList();
    }

    /**
     * 修改
     *
     * @param videoClassificationWebDTO
     */
    @Override
    @PutMapping("/classification")
    public void updateVideoClassification(@Valid @RequestBody VideoClassificationWebDTO videoClassificationWebDTO) {
        videoClassificationWebService.update(videoClassificationWebDTO);
    }

    /**
     * 添加
     *
     * @param videoClassificationWebDTO
     */
    @Override
    @PostMapping("/classification")
    public void insertVideoClassification(@Valid @RequestBody VideoClassificationWebDTO videoClassificationWebDTO) {
        videoClassificationWebService.insert(videoClassificationWebDTO);
    }

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public List<VideoTagWebDTO> videoTag() {
        return videoTagWebService.selectListAll();
    }

    /**
     * 修改
     *
     * @param videoTagWebDTO
     */
    @Override
    public void updateVideoTag(@Valid @RequestBody VideoTagWebDTO videoTagWebDTO) {
        videoTagWebService.update(videoTagWebDTO);
    }

    /**
     * 添加
     *
     * @param videoTagWebDTO
     */
    @Override
    public void insertVideoTag(@Valid @RequestBody VideoTagWebDTO videoTagWebDTO) {
        videoTagWebService.insert(videoTagWebDTO);
    }

    /**
     * 批量上线
     *
     * @param ids
     */
    @Override
    public void batchOnline(String[] ids) {
        videoWebService.batchOnline(ids);
    }

    /**
     * 批量下线
     *
     * @param ids
     */
    @Override
    public void batchOffline(String[] ids) {
        videoWebService.batchOffline(ids);
    }

    /**
     * 删除视频数据
     */
    @DeleteMapping("/delete/{videoNumber}")
    @Override
    public void deleteVideo(@PathVariable("videoNumber") String videoNumber) {
        videoWebService.delete(videoNumber);
    }

    /**
     * 批量删除
     *
     * @param
     */
    @PostMapping("/batchDelete")
    @Override
    public void batchDelete(@RequestParam("ids") String[] ids) {
        videoWebService.batchDelete(ids);
    }


    /**
     * 分页查询审核视频
     *
     * @return
     */
    @PostMapping("/review/page")
    @Override
    public PageResult<VideoWebDTO> reviewPageList(@Valid @RequestBody VideoPageVO videoPageVO) {
        return videoReviewWebService.pageList(videoPageVO);
    }

    /**
     * 修改审核视频数据
     */
    @PutMapping("/review/{videoNumber}")
    @Override
    public void updateReviewVideo(@Valid @RequestBody VideoWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber) {
        videoWebDTO.setVideoNumber(videoNumber);
        videoReviewWebService.update(videoWebDTO);
    }

    /**
     * 删除审核视频数据
     */
    @DeleteMapping("/review/delete/{videoNumber}")
    @Override
    public void deleteReviewVideo(@PathVariable("videoNumber") String videoNumber) {
        videoReviewWebService.delete(videoNumber);
    }

    /**
     * 审核批量上线
     *
     * @param
     */
    @PostMapping("/review/batchOnline")
    @Override
    public void reviewBatchOnline(@RequestParam("ids") String[] ids) {
        videoReviewWebService.batchOnline(ids);
    }

    /**
     * 审核批量下线
     *
     * @param
     */
    @PostMapping("/review/batchDelete")
    @Override
    public void reviewBatchDelete(@RequestParam("ids") String[] ids) {
        videoReviewWebService.batchDelete(ids);
    }

    /**
     * 批量分类
     *
     * @param
     */
    @PostMapping("/batchUpdateType")
    @Override
    public void batchUpdateType(@RequestParam("ids") String[] ids, @RequestParam("categoryNumber") String categoryNumber) {
        videoWebService.batchUpdateType(ids, categoryNumber);
    }

    /**
     * 批量标签
     *
     * @param
     */
    @PostMapping("/batchUpdateTag")
    @Override
    public void batchUpdateTag(@RequestParam("ids") String[] ids, @RequestParam("tags") List<String> tags) {
        videoWebService.batchUpdateTag(ids, tags);
    }

    /**
     * 查看新手教学视频
     *
     * @param teachingVideoPageVO
     * @return
     */
    @Override
    public PageResult<VideoWebDTO> getTeachingVideo(@Valid TeachingVideoPageVO teachingVideoPageVO) {
        return videoWebService.getTeachingVideo(teachingVideoPageVO);
    }

    @Override
    public void updateTeachingVideo(@Valid UpdateTeachingVideoVO updateTeachingVideoVO) {
        videoWebService.updateTeachingVideo(updateTeachingVideoVO);
    }

    /**
     * 获取音视频cdn url
     *
     * @return
     */
    @Override
    public String getVideoAudioApolloConfig() {
        return videoWebService.getVideoAudioApolloConfig();
    }

    /**
     * 查询设置率排行
     *
     * @param videoSettingRatePageBO
     * @return
     */
    @Override
    public PageResult<VideoSettingRatesVO> pageList(VideoSettingRatePageBO videoSettingRatePageBO) {
        return videoWebService.pageList(videoSettingRatePageBO);
    }
}
