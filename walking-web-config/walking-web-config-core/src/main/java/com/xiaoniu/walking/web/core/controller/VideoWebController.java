package com.xiaoniu.walking.web.core.controller;

import com.alibaba.fastjson.JSON;
import com.xiaoniu.architecture.commons.api.ApiResultBean;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.constants.RedisConstants;
import com.xiaoniu.walking.web.core.dto.*;
import com.xiaoniu.walking.web.core.service.AppAuditService;
import com.xiaoniu.walking.web.core.service.VideoService;
import com.xiaoniu.walking.web.core.vo.VideoPageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视频管理端接口
 *
 * @author wuwen
 * @date 2019-07-16 10:51
 */
@RestController
@RequestMapping("/walkingwebapi/video")
public class VideoWebController {

    @Autowired
    private AppAuditService appAuditService;

    @Autowired
    private VideoService videoService;

    /**
     * 分页查询视频
     *
     * @return
     */
    @PostMapping("/page")
    @ActionLogger(moduleName = "视频查询", actionType = ActionLoggerCons.QUERY)
    public PageResult<VideoWebDTO> pageList(@Valid @RequestBody VideoPageVO videoPageVO) {
        return videoService.pageList(videoPageVO);
    }

    /**
     * 修改视频数据
     */
    @PutMapping("/{videoNumber}")
    @ActionLogger(moduleName = "视频修改", actionType = ActionLoggerCons.MODIFY)
    public ApiResultBean update(@Valid @RequestBody VideoWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber) {
        videoService.updateVideo(videoWebDTO, videoNumber);
        return ApiResultBean.success(com.xiaoniu.architecture.commons.core.util.StringUtils.generateUUID(), "");
    }

    @GetMapping({"/classification"})
    @ActionLogger(moduleName = "视频分类", actionType = ActionLoggerCons.QUERY)
    public List<VideoClassificationWebDTO> videoClassification() {
        return videoService.videoClassification();
    }

    @PutMapping({"/classification"})
    @ActionLogger(moduleName = "视频分类修改", actionType = ActionLoggerCons.MODIFY)
    public ApiResultBean updateVideoClassification(@Valid @RequestBody VideoClassificationWebDTO videoClassificationWebDTO) {
        videoService.updateVideoClassification(videoClassificationWebDTO);
        return ApiResultBean.success(com.xiaoniu.architecture.commons.core.util.StringUtils.generateUUID(), "");
    }

    @PostMapping({"/classification"})
    @ActionLogger(moduleName = "视频分类新增", actionType = ActionLoggerCons.ADD)
    public ApiResultBean insertVideoClassification(@Valid @RequestBody VideoClassificationWebDTO videoClassificationWebDTO) {
        videoService.insertVideoClassification(videoClassificationWebDTO);
        return ApiResultBean.success(com.xiaoniu.architecture.commons.core.util.StringUtils.generateUUID(), "");
    }

    @GetMapping({"/tag"})
    @ActionLogger(moduleName = "视频分类标签列表", actionType = ActionLoggerCons.QUERY)
    public List<KeyValueDTO> videoTag() {
        return videoService.videoTag().stream().map(item->new KeyValueDTO(item.getTagNumber(), item.getTagName())).collect(Collectors.toList());
    }

    @PutMapping({"/tag"})
    @ActionLogger(moduleName = "视频分类标签修改", actionType = ActionLoggerCons.MODIFY)
    public void updateVideoTag(@Valid @RequestBody VideoTagWebDTO videoTagWebDTO) {
        videoService.updateVideoTag(videoTagWebDTO);
    }

    @PostMapping({"/tag"})
    @ActionLogger(moduleName = "视频分类标签新增", actionType = ActionLoggerCons.ADD)
    public void insertVideoTag(@Valid @RequestBody VideoTagWebDTO videoTagWebDTO) {
        videoService.insertVideoTag(videoTagWebDTO);
    }

    /**
     * 批量上线
     *
     * @param batchOperateIdsDto
     */
    @PostMapping({"/batchOnline"})
    @ActionLogger(moduleName = "视频分类标签批量上线", actionType = ActionLoggerCons.MODIFY)
    public ApiResultBean batchOnline(@RequestBody BatchOperateIdsDto batchOperateIdsDto) {
        videoService.batchOnline(batchOperateIdsDto.getIds());
        return ApiResultBean.success(com.xiaoniu.architecture.commons.core.util.StringUtils.generateUUID(), "");
    }

    /**
     * 批量下线
     *
     * @param batchOperateIdsDto
     */
    @PostMapping({"/batchOffline"})
    @ActionLogger(moduleName = "视频分类标签批量下线", actionType = ActionLoggerCons.MODIFY)
    public ApiResultBean batchOffline(@RequestBody BatchOperateIdsDto batchOperateIdsDto) {
        videoService.batchOffline(batchOperateIdsDto.getIds());
        return ApiResultBean.success(com.xiaoniu.architecture.commons.core.util.StringUtils.generateUUID(), "");
    }
    /**
     * 修改视频数据
     */
    @DeleteMapping("/delete/{videoNumber}")
    @ActionLogger(moduleName = "视频分类标签删除", actionType = ActionLoggerCons.DELETE)
    public ApiResultBean delete(@PathVariable("videoNumber") String videoNumber) {
        videoService.deleteVideo(videoNumber);
        return ApiResultBean.success(com.xiaoniu.architecture.commons.core.util.StringUtils.generateUUID(), "");
    }

    /**
     * 批量删除
     *
     * @param batchOperateIdsDto
     */
    @PostMapping({"/batchDelete"})
    @ActionLogger(moduleName = "视频分类标签批量删除", actionType = ActionLoggerCons.DELETE)
    public ApiResultBean batchDelete(@RequestBody BatchOperateIdsDto batchOperateIdsDto) {
        videoService.batchDelete(batchOperateIdsDto.getIds());
        return ApiResultBean.success(com.xiaoniu.architecture.commons.core.util.StringUtils.generateUUID(), "");
    }

    /**
     * 批量修改分类
     *
     * @param batchUpdateVideoDTO
     */
    @PutMapping({"/batchUpdateType"})
    @ActionLogger(moduleName = "视频分类标签批量分类", actionType = ActionLoggerCons.MODIFY)
    public ApiResultBean batchUpdateType(@RequestBody BatchUpdateVideoDTO batchUpdateVideoDTO) {
        videoService.batchUpdateType(batchUpdateVideoDTO.getIds(), batchUpdateVideoDTO.getCategoryNumber());
        return ApiResultBean.success(com.xiaoniu.architecture.commons.core.util.StringUtils.generateUUID(), "");
    }

    /**
     * 批量修改标签
     *
     * @param batchUpdateVideoDTO
     */
    @PutMapping({"/batchUpdateTag"})
    @ActionLogger(moduleName = "视频分类标签批量标签", actionType = ActionLoggerCons.MODIFY)
    public ApiResultBean batchUpdateTag(@RequestBody BatchUpdateVideoDTO batchUpdateVideoDTO) {
        videoService.batchUpdateTag(batchUpdateVideoDTO.getIds(), batchUpdateVideoDTO.getTags());
        return ApiResultBean.success(com.xiaoniu.architecture.commons.core.util.StringUtils.generateUUID(), "");
    }

    /**
     * 视频来源
     *
     * @return
     */
    @GetMapping({"/options"})
    @ActionLogger(moduleName = "下拉标签列表", actionType = ActionLoggerCons.QUERY)
    public VideoKeyValueDTO options() {
        VideoKeyValueDTO videoKeyValueDTO = new VideoKeyValueDTO();

        // 视频来源
        String value = appAuditService.dic(RedisConstants.APP, RedisConstants.APP_VIDEO_SOURCE);
        if (StringUtils.isNotEmpty(value)) {
            videoKeyValueDTO.setVideoSource(JSON.parseArray(JSON.parse(value).toString(), KeyValueDTO.class));
        }

        // 视频分类
        String categroy = appAuditService.dic(RedisConstants.APP, RedisConstants.APP_VIDEO_CATEGORY);
        if (StringUtils.isNotEmpty(categroy)) {
            videoKeyValueDTO.setCategory(JSON.parseArray(JSON.parse(categroy).toString(), KeyValueDTO.class));
        }

        // 视频标签
        videoKeyValueDTO.setTags(videoService.videoTag().stream().map(item->new KeyValueDTO(item.getTagNumber(), item.getTagName())).collect(Collectors.toList()));
        return videoKeyValueDTO;
    }


}
