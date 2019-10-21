package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.VideoSettingRatePageBO;
import com.xiaoniu.call.video.api.dto.VideoWebDTO;
import com.xiaoniu.call.video.api.vo.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 视频管理端业务
 *
 * @author wuwen
 * @date 2019-07-15 16:36
 */
public interface VideoWebService {

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
     * 批量上线
     *
     * @param ids
     */
    void batchOnline(String[] ids);

    /**
     * 批量上线
     *
     * @param ids
     */
    void batchOffline(String[] ids);

    /**
     * 删除视频数据
     */
    void delete(String videoNumber);


    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(String[] ids);

    /**
     * 批量分类
     *
     * @param
     */
    void batchUpdateType(String[] ids, String categoryNumber);

    /**
     * 批量标签
     *
     * @param
     */
    void batchUpdateTag(String[] ids, List<String> tags);

    void addVideoPool(String videoNumber);

    /**
     * 查看新手教学视频
     *
     * @return
     */
    PageResult<VideoWebDTO> getTeachingVideo(TeachingVideoPageVO teachingVideoPageVO);

    /**修改新手教学视频
     *
     * @return
     */
    void updateTeachingVideo(UpdateTeachingVideoVO updateTeachingVideoVO);

    /**
     * 获取音视频cdn url
     *
     * @return
     */
    String getVideoAudioApolloConfig();

    /**
     * 计算设置率
     *
     * @param settingNum
     * @param playNum
     * @return
     */
    String settingRateFormat(Integer settingNum, Integer playNum);

    /**
     * 查询设置率排行
     *
     * @param videoSettingRatePageBO
     * @return
     */
    PageResult<VideoSettingRatesVO> pageList(@Valid @RequestBody VideoSettingRatePageBO videoSettingRatePageBO);
}
