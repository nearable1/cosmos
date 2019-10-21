package com.xiaoniu.call.video.core.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.AudioChangeWeightBO;
import com.xiaoniu.call.video.api.bo.AudioClassificationPageBO;
import com.xiaoniu.call.video.api.bo.AudioOnlineOfflineBO;
import com.xiaoniu.call.video.api.bo.AudioPageBO;
import com.xiaoniu.call.video.api.business.AudioWebBusiness;
import com.xiaoniu.call.video.api.dto.AudioClassificationWebDTO;
import com.xiaoniu.call.video.api.vo.AudioVO;
import com.xiaoniu.call.video.core.service.AudioClassificationWebService;
import com.xiaoniu.call.video.core.service.AudioWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 音频管理端
 *
 * @author liuyinkai
 * @date 2019-07-30
 */
@RestController
public class AudioWebBusinessImpl implements AudioWebBusiness {


    @Autowired
    private AudioClassificationWebService audioClassificationWebService;

    @Autowired
    private AudioWebService audioWebService;

    /**
     * 分页查询音频
     *
     * @return
     */
    @PostMapping("/getAudioList")
    @Override
    public PageResult<AudioVO> audioPageList(@Valid AudioPageBO audioPageBO) {
        return audioWebService.pageList(audioPageBO);
    }

    /**
     * 修改视频数据
     */
    @Override
    public void updateAudio(@Valid AudioVO audioVO, String audioNumber) {
        audioVO.setAudioNumber(audioNumber);
        audioWebService.update(audioVO);
    }


    /**
     * 分页查询视频
     *
     * @param audioClassificationPageBO
     * @return
     */
    @PostMapping("/getAudioCategoryList")
    @Override
    public PageResult<AudioClassificationWebDTO> pageList(@Valid AudioClassificationPageBO audioClassificationPageBO) {
        return audioClassificationWebService.pageList(audioClassificationPageBO);
    }

    /**
     * 批量上线
     *
     * @param
     */
    @Override
    public void audioBatchOnline(String[] ids) {
        audioWebService.audioBatchOnline(ids);
    }

    /**
     * 批量下线
     *
     * @param
     */
    @Override
    public void audioBatchOffline(String[] ids) {
        audioWebService.audioBatchOffline(ids);
    }

    /**
     * 批量删除
     *
     * @param
     */
    @Override
    public void audioBatchDelete(String[] ids) {
        audioWebService.audioBatchDelete(ids);
    }

    /**
     * 批量过审
     *
     * @param ids
     */
    @Override
    public void audioBatchCheckPass(String[] ids) {
        audioWebService.audioBatchCheckPass(ids);
    }

    /**
     * 批量过审失败
     *
     * @param ids
     */
    @Override
    public void audioBatchCheckNotPass(String[] ids) {
        audioWebService.audioBatchCheckNotPass(ids);
    }

    /**
     * 权重更改
     *
     * @param audioChangeWeightBO
     */
    @Override
    public void audioChangeWeight(@RequestBody @Valid AudioChangeWeightBO audioChangeWeightBO) {
        audioWebService.audioChangeWeight(audioChangeWeightBO);
    }

    /**
     * 单行上下线
     * @param audioOnlineOfflineBO
     * @param audioNumber
     */
    @Override
    public void singleOnlineOffline(@Valid AudioOnlineOfflineBO audioOnlineOfflineBO, String audioNumber) {
        audioOnlineOfflineBO.setAudioNumber(audioNumber);
        audioWebService.singleOnlineOffline(audioOnlineOfflineBO);
    }

}
