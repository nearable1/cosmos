package com.xiaoniu.call.video.api.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.VideoSettingRatePageBO;
import com.xiaoniu.call.video.api.dto.VideoClassificationWebDTO;
import com.xiaoniu.call.video.api.dto.VideoPyxWebDTO;
import com.xiaoniu.call.video.api.dto.VideoTagWebDTO;
import com.xiaoniu.call.video.api.dto.VideoWebDTO;
import com.xiaoniu.call.video.api.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 视频对内接口
 *
 * @author wuwen
 * @date 2019-07-15 16:58
 */
@FeignClient(contextId = "videoPyxWebBusiness", value = "video")
@RequestMapping("/pyx")
public interface VideoPyxWebBusiness {

    /**
     * 分页查询视频
     *
     * @return
     */
    @PostMapping("/page")
    PageResult<VideoPyxWebDTO> pageList(@Valid @RequestBody VideoPyxPageVO videoPageVO);

    /**
     * 修改视频数据
     */
    @PutMapping("/{videoNumber}")
    void updateVideo(@Valid @RequestBody VideoPyxWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber);

    /**
     * 批量上线
     *
     * @param
     */
    @PostMapping("/batchOnline")
    void batchOnline(@RequestParam("ids") String[] ids);

    /**
     * 批量下线
     *
     * @param
     */
    @PostMapping("/batchOffline")
    void batchOffline(@RequestParam("ids") String[] ids);

    /**
     * 删除视频数据
     */
    @DeleteMapping("/delete/{videoNumber}")
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
    @PostMapping("/review/page")
    PageResult<VideoPyxWebDTO> reviewPageList(@Valid @RequestBody VideoPyxPageVO videoPageVO);

    /**
     * 修改视频数据
     */
    @PutMapping("/review/{videoNumber}")
    void updateReviewVideo(@Valid @RequestBody VideoPyxWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber);

    /**
     * 删除视频数据
     */
    @DeleteMapping("/review/delete/{videoNumber}")
    void deleteReviewVideo(@PathVariable("videoNumber") String videoNumber);

    /**
     * 批量上线
     *
     * @param
     */
    @PostMapping("/review/batchOnline")
    void reviewBatchOnline(@RequestParam("ids") String[] ids);

    /**
     * 批量下线
     *
     * @param
     */
    @PostMapping("/review/batchDelete")
    void reviewBatchDelete(@RequestParam("ids") String[] ids);
}
