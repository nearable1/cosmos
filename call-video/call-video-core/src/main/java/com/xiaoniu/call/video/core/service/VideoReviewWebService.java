package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.dto.VideoWebDTO;
import com.xiaoniu.call.video.api.vo.VideoPageVO;

/**
 * 视频管理端业务
 *
 * @author wuwen
 * @date 2019-07-15 16:36
 */
public interface VideoReviewWebService {

    /**
     * 分页查询视频
     *
     * @return
     */
    PageResult<VideoWebDTO> pageList(VideoPageVO videoPageVO);

    /**
     * 修改视频数据
     */
    void update(VideoWebDTO videoWebDTO);

    /**
     * 删除视频数据
     */
    void delete(String videoNumber);

    /**
     * 批量上线
     *
     * @param ids
     */
    void batchOnline(String[] ids);

    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(String[] ids);
}
