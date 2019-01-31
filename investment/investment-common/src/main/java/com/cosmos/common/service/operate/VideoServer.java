package com.cosmos.common.service.operate;


import com.cosmos.common.entity.pojo.Comments;
import com.cosmos.common.entity.pojo.Videos;
import com.cosmos.common.utils.PagedResult;

import java.util.List;

public interface VideoServer {

    /**
     * 视频信息保存
     * @param videos
     */
    String saveVideo(Videos videos);

    /**
     * 数据修改
     * @param videoid
     * @param coverPath
     */
    void updateVideo(String videoid, String coverPath);

    /**
     * 分页查询列表
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult getAllVideo(Videos videos, Integer isSaveRecord, Integer page, Integer pageSize);

    /**
     * 热搜词全查询
     * @return
     */
    List<String> getHotWorde();

    /**
     * 用户喜欢累加
     * @param userId
     * @param videoId
     * @param videoCreaterId
     */
    void userLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * 用户不喜欢累减
     * @param userId
     * @param videoId
     */
    void userUnLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * 用户收藏累加
     * @param userId
     * @param videoId
     */
    void userKeepVideo(String userId, String videoId);

    /**
     * 用户取消收藏累减
     * @param userId
     * @param videoId
     */
    void userUnKeepVideo(String userId, String videoId);



    /**
     * 用户关注的作品
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);

    /**
     * 用户留言
     * @param comments
     */
    void seaveCommnet(Comments comments);

    /**
     * 用户留言查询
     * @param videoId
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult getVideoComment(String videoId, Integer page, Integer pageSize);
}
