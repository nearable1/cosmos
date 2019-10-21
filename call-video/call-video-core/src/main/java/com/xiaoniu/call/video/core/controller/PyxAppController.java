package com.xiaoniu.call.video.core.controller;

import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.docking.response.laowang.VideoCategoryResponse;
import com.xiaoniu.call.video.core.dto.*;
import com.xiaoniu.call.video.core.service.*;
import com.xiaoniu.call.video.core.vo.LaoWangVideoVO;
import com.xiaoniu.call.video.core.vo.VideoLikeVO;
import com.xiaoniu.call.video.core.vo.VideoPyxVO;
import com.xiaoniu.call.video.core.vo.VideoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 皮一下接口
 *
 * @author sunju
 * @date 2019-07-02 17:01
 */
@RestController
@RequestMapping("/pyx")
public class PyxAppController {

    @Autowired
    private VideoPyxService videoService;

    /**
     * 首页热门视频
     *
     * @return
     */
    @PostMapping("/home")
    public PageResult<VideoPyxDTO> pageHomePopularVideo(@RequestBody @Valid VideoPyxVO videoVO) {
        return videoService.homePopularVideo(videoVO);
    }

    /**
     * 视频详情接口
     *
     * @param videoNumber
     * @return
     */
    @GetMapping("/{videoNumber}/detail")
    public VideoPyxDTO detail(@PathVariable("videoNumber") String videoNumber) {
        return videoService.detail(videoNumber);
    }

    /**
     * 视频喜欢
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/like")
    public void like(@PathVariable("videoNumber") String videoNumber) {
        videoService.like(HeaderHelper.getDeviceId(), videoNumber);
    }

    /**
     * 取消视频喜欢
     *
     * @param videoNumber
     */
    @DeleteMapping("/{videoNumber}/cancel-like")
    public void cancelLike(@PathVariable("videoNumber") String videoNumber) {
        videoService.cancelLike(HeaderHelper.getDeviceId(), videoNumber);
    }

    /**
     * 视频分享
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/share")
    public void share(@PathVariable("videoNumber") String videoNumber) {
        videoService.share(videoNumber);
    }

    /**
     * 视频播放
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/broadcast")
    public void broadcast(@PathVariable("videoNumber") String videoNumber) {
        videoService.broadcast(videoNumber);
    }

}
