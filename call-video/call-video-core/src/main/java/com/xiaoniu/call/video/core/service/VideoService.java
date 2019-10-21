package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.core.dto.VideoClassificationDTO;
import com.xiaoniu.call.video.core.dto.VideoDTO;
import com.xiaoniu.call.video.core.vo.VideoLikeVO;
import com.xiaoniu.call.video.core.vo.VideoVO;

import java.util.List;

/**
 * 视频业务
 *
 * @author wuwen
 * @date 2019-07-02 16:54
 */
public interface VideoService {

    /**
     * 首页热门视频
     *
     * @return
     */
    PageResult<VideoDTO> homePopularVideo(VideoVO videoVO);

    /**
     * 首页最新视频
     *
     * @param videoVO
     * @return
     */
    PageResult<VideoDTO> homeLatestVideo(VideoVO videoVO);

    /**
     * 首页分类列表
     *
     * @return
     */
    List<VideoClassificationDTO> homeCategoryList();

    /**
     * 探索最新视频
     *
     * @param videoVO
     * @return
     */
    PageResult<VideoDTO> exploreLatestVideo(VideoVO videoVO);

    /**
     * 小视频列表
     *
     * @param videoVO
     * @return
     */
    PageResult<VideoDTO> smallVideoList(VideoVO videoVO);

    /**
     * 根据分类编号查询最新视频
     *
     * @param videoVO
     * @return
     */
    PageResult<VideoDTO> selectLatestVideoListByCategoryNumber(VideoVO videoVO);

    /**
     * 视频详情接口
     *
     * @param videoNumber
     * @return
     */
    VideoDTO detail(String videoNumber);

    /**
     * 我喜欢的视频
     *
     * @param videoLikeVO
     * @return
     */
    PageResult<VideoDTO> favorite(VideoLikeVO videoLikeVO);

    /**
     * 我喜欢的视频数量
     *
     * @param deviceId
     * @return
     */
    Integer favoriteNumber(String deviceId);

    /**
     * 根据标签查询视频
     *
     * @param tags
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageResult<VideoDTO> selectListByTags(String tags, int pageIndex, int pageSize);

    /**
     * 根据标签查询视频
     *
     * @param tags
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageResult<VideoDTO> selectListByTags(String tags, int pageIndex, int pageSize, Boolean webp);

    /**
     * 首页视频-来电秀、壁纸秀、屏保秀
     *
     * @return
     */
    PageResult<VideoDTO> indexVideo(VideoVO videoVO);
}
