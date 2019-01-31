package com.cosmos.common.dao.mapper;

import com.cosmos.common.entity.pojo.Videos;
import com.cosmos.common.entity.pojo.vo.VideosVo;
import com.cosmos.common.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperSutom extends MyMapper<Videos> {

    //用户和视频连表查询
    List<VideosVo> queryAllVideoVo(@Param("videoDesc") String videoDesc,
                                   @Param("userId") String userId);

    /**
     * 喜欢数量累加
     * @param videoId
     */
    void addVideoLikeCount(String videoId);

    /**
     * 喜欢数量累减
     * @param videoId
     */
    void reduceVideoLikeCount(String videoId);

    /**
     * 用户关注作品展示
     * @param userId
     * @return
     */
    List<VideosVo> queryMyKeepVideos(String userId);
}