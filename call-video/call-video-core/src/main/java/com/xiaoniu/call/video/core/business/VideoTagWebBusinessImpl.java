package com.xiaoniu.call.video.core.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.business.VideoTagWebBusiness;
import com.xiaoniu.call.video.api.dto.VideoTagWebDTO;
import com.xiaoniu.call.video.api.vo.VideoTagPageVO;
import com.xiaoniu.call.video.core.service.VideoTagWebService;
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
public class VideoTagWebBusinessImpl implements VideoTagWebBusiness {


    @Autowired
    private VideoTagWebService videoTagWebService;

    /**
     * 分页查询视频
     *
     * @return
     */
    @PostMapping("/videoTag/page")
    @Override
    public PageResult<VideoTagWebDTO> pageList(@Valid @RequestBody VideoTagPageVO videoPageVO) {
        return videoTagWebService.pageList(videoPageVO);
    }

    /**
     * 新增
     *
     * @return
     */
    @PostMapping("/videoTag/save")
    @Override
    public void saveVideoTag(@Valid @RequestBody VideoTagWebDTO videoPageVO) {
        videoTagWebService.insert(videoPageVO);
    }

    /**
     * 删除
     *
     */
    @DeleteMapping("/videoTag/delete")
    @Override
    public void deleteVideoTag(@Valid @RequestBody VideoTagWebDTO videoPageVO) {
        videoTagWebService.delete(videoPageVO);
    }

    /**
     * 修改
     *
     * @return
     */
    @PutMapping("/videoTag/update")
    @Override
    public void updateVideoTag(@Valid @RequestBody VideoTagWebDTO videoPageVO) {
        videoTagWebService.update(videoPageVO);
    }
}
