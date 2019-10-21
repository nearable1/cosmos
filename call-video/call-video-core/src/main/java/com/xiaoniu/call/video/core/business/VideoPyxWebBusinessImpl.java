package com.xiaoniu.call.video.core.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.business.VideoPyxWebBusiness;
import com.xiaoniu.call.video.api.dto.VideoPyxWebDTO;
import com.xiaoniu.call.video.api.vo.VideoPyxPageVO;
import com.xiaoniu.call.video.core.service.VideoPyxReviewWebService;
import com.xiaoniu.call.video.core.service.VideoPyxWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 视频管理端
 *
 * @author wuwen
 * @date 2019-07-16 10:38
 */
@RestController
@RequestMapping("/pyx")
public class VideoPyxWebBusinessImpl implements VideoPyxWebBusiness {


    @Autowired
    private VideoPyxWebService videoWebService;

    @Autowired
    private VideoPyxReviewWebService videoReviewWebService;

    /**
     * 分页查询视频
     *
     * @param videoPageVO
     * @return
     */
    @Override
    @PostMapping("/page")
    public PageResult<VideoPyxWebDTO> pageList(@Valid @RequestBody VideoPyxPageVO videoPageVO) {
        return videoWebService.pageList(videoPageVO);
    }

    /**
     * 修改视频数据
     *
     * @param videoWebDTO
     * @param videoNumber
     */
    @Override
    @PutMapping("/{videoNumber}")
    public void updateVideo(@Valid @RequestBody VideoPyxWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber) {
        videoWebDTO.setVideoNumber(videoNumber);
        videoWebService.update(videoWebDTO);
    }

    /**
     * 批量上线
     *
     * @param ids
     */
    @Override
    public void batchOnline(String[] ids) {
        videoWebService.batchOnline(ids);
    }

    /**
     * 批量下线
     *
     * @param ids
     */
    @Override
    public void batchOffline(String[] ids) {
        videoWebService.batchOffline(ids);
    }

    /**
     * 删除视频数据
     */
    @DeleteMapping("/delete/{videoNumber}")
    @Override
    public void deleteVideo(@PathVariable("videoNumber") String videoNumber) {
        videoWebService.delete(videoNumber);
    }

    /**
     * 批量删除
     *
     * @param
     */
    @PostMapping("/batchDelete")
    @Override
    public void batchDelete(@RequestParam("ids") String[] ids) {
        videoWebService.batchDelete(ids);
    }

    /**
     * 分页查询审核视频
     *
     * @return
     */
    @PostMapping("/review/page")
    @Override
    public PageResult<VideoPyxWebDTO> reviewPageList(@Valid @RequestBody VideoPyxPageVO videoPageVO) {
        return videoReviewWebService.pageList(videoPageVO);
    }

    /**
     * 修改审核视频数据
     */
    @PutMapping("/review/{videoNumber}")
    @Override
    public void updateReviewVideo(@Valid @RequestBody VideoPyxWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber) {
        videoWebDTO.setVideoNumber(videoNumber);
        videoReviewWebService.update(videoWebDTO);
    }

    /**
     * 删除审核视频数据
     */
    @DeleteMapping("/review/delete/{videoNumber}")
    @Override
    public void deleteReviewVideo(@PathVariable("videoNumber") String videoNumber) {
        videoReviewWebService.delete(videoNumber);
    }

    /**
     * 审核批量上线
     *
     * @param
     */
    @PostMapping("/review/batchOnline")
    @Override
    public void reviewBatchOnline(@RequestParam("ids") String[] ids) {
        videoReviewWebService.batchOnline(ids);
    }

    /**
     * 审核批量下线
     *
     * @param
     */
    @PostMapping("/review/batchDelete")
    @Override
    public void reviewBatchDelete(@RequestParam("ids") String[] ids) {
        videoReviewWebService.batchDelete(ids);
    }
}
