package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.dto.VideoTagWebDTO;
import com.xiaoniu.call.video.api.vo.VideoTagPageVO;

import java.util.List;

/**
 * 视频标签
 *
 * @author wuwen
 * @date 2019-07-16 14:02
 */
public interface VideoTagWebService {

    List<VideoTagWebDTO> selectList();

    List<VideoTagWebDTO> selectListAll();

    void insert(VideoTagWebDTO videoTagWebDTO);

    void update(VideoTagWebDTO videoTagWebDTO);

    PageResult<VideoTagWebDTO> pageList(VideoTagPageVO videoPageVO);

    void delete(VideoTagWebDTO videoTagWebDTO);
}
