package com.xiaoniu.call.video.core.service;

import com.xiaoniu.call.docking.response.laowang.VideoCategoryResponse;
import com.xiaoniu.call.video.core.dto.LaoWangVideoDTO;
import com.xiaoniu.call.video.core.vo.LaoWangVideoVO;

import java.util.List;

/**
 * 老王视频业务
 *
 * @author wuwen
 * @date 2019-07-06 13:52
 */
public interface LaoWangService {

    /**
     * 老王视频列表
     *
     * @param laoWangVideoVO
     * @return
     */
    List<LaoWangVideoDTO> videoList(LaoWangVideoVO laoWangVideoVO);

    /**
     * 播放视频
     *
     * @param videoNumber
     */
    void playVideo(String videoNumber);

    /**
     * 分类列表
     *
     * @return
     */
    List<VideoCategoryResponse> categoryList();
}
