package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.dto.VideoClassificationWebDTO;
import com.xiaoniu.walking.web.core.dto.VideoTagWebDTO;
import com.xiaoniu.walking.web.core.dto.VideoWebDTO;
import com.xiaoniu.walking.web.core.model.auto.VideoTag;
import com.xiaoniu.walking.web.core.vo.VideoPageVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 视频对内接口
 *
 * @author wuwen
 * @date 2019-07-15 16:58
 */
public interface VideoService {

    /**
     * 分页查询视频
     *
     * @return
     */
    PageResult<VideoWebDTO> pageList(@Valid @RequestBody VideoPageVO videoPageVO);

    /**
     * 修改视频数据
     */
    void updateVideo(@Valid @RequestBody VideoWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber);

    /**
     * 查询列表
     *
     * @return
     */
    List<VideoClassificationWebDTO> videoClassification();

    /**
     * 修改
     *
     * @param videoClassificationWebDTO
     */
    void updateVideoClassification(@Valid @RequestBody VideoClassificationWebDTO videoClassificationWebDTO);

    /**
     * 添加
     *
     * @param videoClassificationWebDTO
     */
    void insertVideoClassification(@Valid @RequestBody VideoClassificationWebDTO videoClassificationWebDTO);

    /**
     * 查询列表
     *
     * @return
     */
    List<VideoTag> videoTag();

    /**
     * 修改
     *
     * @param videoTagWebDTO
     */
    void updateVideoTag(@Valid @RequestBody VideoTagWebDTO videoTagWebDTO);

    /**
     * 添加
     *
     * @param videoTagWebDTO
     */
    void insertVideoTag(@Valid @RequestBody VideoTagWebDTO videoTagWebDTO);

    /**
     * 批量上线
     *
     * @param
     */
    void batchOnline(@RequestParam("ids") String[] ids);

    /**
     * 批量下线
     *
     * @param
     */
    void batchOffline(@RequestParam("ids") String[] ids);

    /**
     * 删除视频数据
     */
    void deleteVideo(@PathVariable("videoNumber") String videoNumber);

    /**
     * 批量下线
     *
     * @param
     */
    @PostMapping("/batchDelete")
    void batchDelete(@RequestParam("ids") String[] ids);

    /**
     * 分页查询视频
     *
     * @return
     */
    PageResult<VideoWebDTO> reviewPageList(@Valid @RequestBody VideoPageVO videoPageVO);

    /**
     * 修改视频数据
     */
    void updateReviewVideo(@Valid @RequestBody VideoWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber);

    /**
     * 删除视频数据
     */
    void deleteReviewVideo(@PathVariable("videoNumber") String videoNumber);

    /**
     * 批量上线
     *
     * @param
     */
    void reviewBatchOnline(@RequestParam("ids") String[] ids);

    /**
     * 批量下线
     *
     * @param
     */
    void reviewBatchDelete(@RequestParam("ids") String[] ids);

    /**
     * 批量分类
     *
     * @param
     */
    void batchUpdateType(@RequestParam("ids") String[] ids, @RequestParam("categoryNumber") String categoryNumber);

    /**
     * 批量标签
     *
     * @param
     */
    void batchUpdateTag(@RequestParam("ids") String[] ids, @RequestParam("tags") List<String> tags);


}
