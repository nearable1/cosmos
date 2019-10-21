package com.xiaoniu.call.video.core.service.impl;

import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.AudioClassificationBO;
import com.xiaoniu.call.video.api.bo.AudioClassificationPageBO;
import com.xiaoniu.call.video.api.dto.AudioClassificationWebDTO;
import com.xiaoniu.call.video.core.entity.AudioClassification;
import com.xiaoniu.call.video.core.mapper.AudioClassificationMapper;
import com.xiaoniu.call.video.core.service.AudioClassificationWebService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 视频管理端业务实现
 *
 * @author liuyinkai
 * @date 2019-07-30
 */
@Service
@Log4j2
public class AudioClassificationWebServiceImpl implements AudioClassificationWebService {

    @Autowired
    private AudioClassificationMapper audioClassificationMapper;

    /**
     * 分页查询音频分类
     *
     * @return
     */
    @Override
    public PageResult<AudioClassificationWebDTO> pageList(AudioClassificationPageBO audioClassificationPageBO) {
        List<AudioClassificationWebDTO> audioClassificationWebDTOS = audioClassificationMapper.selectWebList(audioClassificationPageBO.getAppName());
        PageResult<AudioClassificationWebDTO> audioClassificationWebDTOPageResult = new PageResult<>();
        audioClassificationWebDTOPageResult.setRows(audioClassificationWebDTOS);
        return audioClassificationWebDTOPageResult;
    }

    /**
     * 插入音频分类
     *
     * @return
     */
    @Override
    public int insertAudioClassification(AudioClassificationBO audioClassificationBO) {

        AudioClassification audioClassification = audioClassificationMapper.checkRepetition(audioClassificationBO);
        if (Objects.nonNull(audioClassification)) {
            throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM, "音频分类已存在");
        }
        int flag = audioClassificationMapper.insertAudioClassification(audioClassificationBO);
        if (flag > 0) {
            log.info("【添加音频分类】添加音频分类成功,分类编号={}", audioClassificationBO.getCategoryNumber());
        } else {
            throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM, "添加音频分类失败");
        }
        return flag;
    }

    /**
     * delete
     *
     * @param id
     */
    @Override
    public void deleteCategory(Integer id) {
        audioClassificationMapper.deleteCategory(id);

    }

    /**
     * update
     *
     * @param audioClassificationBO
     * @return
     */
    @Override
    public int updateCategory(AudioClassificationBO audioClassificationBO) {
        AudioClassification audioClassification = audioClassificationMapper.checkRepetition(audioClassificationBO);
        if (Objects.nonNull(audioClassification)) {
            throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM, "音频分类已存在");
        }
        audioClassificationBO.setUpdateTime(new Date());
        int flag = audioClassificationMapper.updateCategory(audioClassificationBO);
        if (flag > 0) {
            log.info("【更新音频分类】更新音频分类成功,分类编号={}", audioClassificationBO.getCategoryNumber());
        } else {
            throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM, "更新音频分类失败");
        }
        return flag;
    }


}
