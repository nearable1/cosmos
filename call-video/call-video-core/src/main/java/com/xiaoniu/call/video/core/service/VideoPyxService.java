package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.core.dto.VideoClassificationDTO;
import com.xiaoniu.call.video.core.dto.VideoDTO;
import com.xiaoniu.call.video.core.dto.VideoPyxDTO;
import com.xiaoniu.call.video.core.vo.VideoLikeVO;
import com.xiaoniu.call.video.core.vo.VideoPyxVO;
import com.xiaoniu.call.video.core.vo.VideoVO;

import java.util.List;

/**
 * 视频业务
 *
 * @author wuwen
 * @date 2019-07-02 16:54
 */
public interface VideoPyxService {

    /**
     * 首页热门视频
     *
     * @return
     */
    PageResult<VideoPyxDTO> homePopularVideo(VideoPyxVO videoVO);

    /**
     * 视频详情接口
     *
     * @param videoNumber
     * @return
     */
    VideoPyxDTO detail(String videoNumber);

    void like(String deviceId, String videoNumber);

    void cancelLike(String deviceId, String videoNumber);

    void share(String videoNumber);

    void broadcast(String videoNumber);
}
