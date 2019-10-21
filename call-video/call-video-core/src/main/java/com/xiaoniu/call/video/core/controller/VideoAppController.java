package com.xiaoniu.call.video.core.controller;

import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.docking.response.laowang.VideoCategoryResponse;
import com.xiaoniu.call.video.core.dto.LaoWangVideoDTO;
import com.xiaoniu.call.video.core.dto.VideoClassificationDTO;
import com.xiaoniu.call.video.core.dto.VideoDTO;
import com.xiaoniu.call.video.core.dto.VideoTagDTO;
import com.xiaoniu.call.video.core.service.CustomerVideoService;
import com.xiaoniu.call.video.core.service.LaoWangService;
import com.xiaoniu.call.video.core.service.VideoService;
import com.xiaoniu.call.video.core.service.VideoTagService;
import com.xiaoniu.call.video.core.vo.LaoWangVideoVO;
import com.xiaoniu.call.video.core.vo.VideoLikeVO;
import com.xiaoniu.call.video.core.vo.VideoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 视频接口
 *
 * @author wuwen
 * @date 2019-07-02 17:01
 */
@RestController
public class VideoAppController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private CustomerVideoService customerVideoService;

    @Autowired
    private VideoTagService videoTagService;

    @Autowired
    private LaoWangService laoWangService;

    /**
     * 首页热门视频
     *
     * @return
     */
    @PostMapping("/home-popular")
    public PageResult<VideoDTO> pageHomePopularVideo(@RequestBody @Valid VideoVO videoVO) {
        return videoService.homePopularVideo(videoVO);
    }

    /**
     * 首页最新视频
     *
     * @param videoVO
     * @return
     */
    @PostMapping("/home-latest")
    public PageResult<VideoDTO> homeLatestVideo(@RequestBody @Valid VideoVO videoVO) {
        return videoService.homeLatestVideo(videoVO);
    }

    /**
     * 首页分类列表
     *
     * @return
     */
    @GetMapping("/home-category")
    public List<VideoClassificationDTO> homeCategoryList() {
        return videoService.homeCategoryList();
    }

    /**
     * 首页标签和TOP4视频
     *
     * @return
     */
    @GetMapping("/tags")
    public List<VideoTagDTO> selectListAndVideo() {
        return videoTagService.selectListAndVideo();
    }

    /**
     * 探索最新视频
     *
     * @param videoVO
     * @return
     */
    @PostMapping("/explore-latest")
    public PageResult<VideoDTO> exploreLatestVideo(@RequestBody @Valid VideoVO videoVO) {
        return videoService.exploreLatestVideo(videoVO);
    }

    /**
     * 小视频
     *
     * @param videoVO
     * @return
     */
    @PostMapping("/small")
    public PageResult<VideoDTO> smallVideoList(@RequestBody @Valid VideoVO videoVO) {
        return videoService.smallVideoList(videoVO);
    }

    /**
     * 根据分类编号查询最新视频
     *
     * @param videoVO
     * @return
     */
    @PostMapping("/{categoryNumber}/latest")
    public PageResult<VideoDTO> selectLatestVideoListByCategoryNumber(@RequestBody @Valid VideoVO videoVO, @PathVariable("categoryNumber") String categoryNumber) {
        videoVO.setCategoryNumber(categoryNumber);
        return videoService.selectLatestVideoListByCategoryNumber(videoVO);
    }

    /**
     * 根据标签查询视频
     *
     * @param tagNumber
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/{tagNumber}/tag/{pageIndex}/{pageSize}")
    public PageResult<VideoDTO> selectListByTags(@PathVariable("tagNumber") String tagNumber, @PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize) {
        return videoService.selectListByTags(tagNumber, pageIndex, pageSize);
    }

    /**
     * 根据标签查询视频
     *
     * @param tagNumber
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/{tagNumber}/tag/{pageIndex}/{pageSize}/{webp}")
    public PageResult<VideoDTO> selectListByTags(@PathVariable("tagNumber") String tagNumber, @PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize, @PathVariable("webp") Boolean webp) {
        return videoService.selectListByTags(tagNumber, pageIndex, pageSize, webp);
    }

    /**
     * 视频详情接口
     *
     * @param videoNumber
     * @return
     */
    @GetMapping("/{videoNumber}/detail")
    public VideoDTO detail(@PathVariable("videoNumber") String videoNumber) {
        return videoService.detail(videoNumber);
    }

    /**
     * 视频喜欢
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/like")
    public void like(@PathVariable("videoNumber") String videoNumber) {
        customerVideoService.like(HeaderHelper.getDeviceId(), videoNumber);
    }

    /**
     * 取消视频喜欢
     *
     * @param videoNumber
     */
    @DeleteMapping("/{videoNumber}/cancel-like")
    public void cancelLike(@PathVariable("videoNumber") String videoNumber) {
        customerVideoService.cancelLike(HeaderHelper.getDeviceId(), videoNumber);
    }

    /**
     * 视频分享
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/share")
    public void share(@PathVariable("videoNumber") String videoNumber) {
        customerVideoService.share(videoNumber);
    }

    /**
     * 视频播放
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/broadcast")
    public void broadcast(@PathVariable("videoNumber") String videoNumber) {
        customerVideoService.broadcast(videoNumber);
    }

    /**
     * 探索视频播放
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/explore-play")
    public void exploreVideoPlayback(@PathVariable("videoNumber") String videoNumber) {
        customerVideoService.exploreVideoPlayback(videoNumber);
    }

    /**
     * 设置来电壁纸
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/caller-wallpaper")
    public void setCallerWallpaper(@PathVariable("videoNumber") String videoNumber) {
        customerVideoService.setCallerWallpaper(videoNumber);
    }

    /**
     * 设置来电铃声
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/ringtones")
    public void setRingtones(@PathVariable("videoNumber") String videoNumber) {
        customerVideoService.setRingtones(videoNumber);
    }


    /**
     * 我喜欢的视频
     *
     * @param videoLikeVO
     * @return
     */
    @PostMapping("/favorite")
    public PageResult<VideoDTO> favorite(@RequestBody @Valid VideoLikeVO videoLikeVO) {
        return videoService.favorite(videoLikeVO);
    }

    /**
     * 我喜欢的视频数量
     * @return
     */
    @GetMapping("/favorite-number")
    public Integer favoriteNumber(){
        return videoService.favoriteNumber(HeaderHelper.getDeviceId());
    }

    /**
     * 老王视频列表
     *
     * @param laoWangVideoVO
     * @return
     */
    @PostMapping("/lao-wang")
    public List<LaoWangVideoDTO> videoList(@RequestBody @Valid LaoWangVideoVO laoWangVideoVO) {
        return laoWangService.videoList(laoWangVideoVO);
    }

    /**
     * 播放视频
     *
     * @param videoNumber
     */
    @PutMapping("/lao-wang/{videoNumber}/play")
    public void playVideo(@PathVariable("videoNumber") String videoNumber) {
        laoWangService.playVideo(videoNumber);
    }


    /**
     * 分类列表
     *
     * @return
     */
    @GetMapping("/lao-wang/category")
    public List<VideoCategoryResponse> categoryList(){
        return laoWangService.categoryList();
    }


    /**
     * 设置壁纸秀
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/wallpaper")
    public void wallpaper(@PathVariable("videoNumber") String videoNumber) {
        customerVideoService.setWallpaper(videoNumber);
    }

    /**
     * 设置屏保秀
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/screensaver")
    public void screensaver(@PathVariable("videoNumber") String videoNumber) {
        customerVideoService.setScreensaver(videoNumber);
    }

    /**
     * 设置彩铃秀
     *
     * @param videoNumber
     */
    @PutMapping("/{videoNumber}/ringtoneshow")
    public void ringtoneshow(@PathVariable("videoNumber") String videoNumber) {
        customerVideoService.setRingtoneShow(videoNumber);
    }

    /**
     * 首页来电秀，壁纸秀，屏保秀接口
     *
     * @param videoVO
     */
    @PostMapping("/home-video")
    public PageResult<VideoDTO> indexVideo(@RequestBody @Valid VideoVO videoVO) {
        return videoService.indexVideo(videoVO);
    }

    /**
     * 标签集合
     *
     * @return
     */
    @PostMapping(value = "/tag-list")
    public Map<String, String> tagList() {
        return videoTagService.tagMap();
    }
}
