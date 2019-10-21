package com.xiaoniu.call.video.core.mapper;

import com.xiaoniu.call.video.api.bo.AudioClassificationBO;
import com.xiaoniu.call.video.api.dto.AudioClassificationWebDTO;
import com.xiaoniu.call.video.core.dto.AudioClassificationDTO;
import com.xiaoniu.call.video.core.entity.AudioClassification;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.common.Mapper;

import javax.validation.Valid;
import java.util.List;

public interface AudioClassificationMapper extends Mapper<AudioClassification> {

    List<AudioClassificationDTO> selectValidList(@Param("appName") int appName, @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);

    List<AudioClassificationWebDTO> selectWebList(@Param("appName") Integer appName);

    /**
     * 插入音频分类
     *
     * @return
     */
    int insertAudioClassification(AudioClassificationBO audioClassificationBO);

    /**
     * 重复校验
     *
     * @param audioClassificationBO
     * @return
     */
    AudioClassification checkRepetition(AudioClassificationBO audioClassificationBO);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteCategory(Integer id);

    /**
     * 更新
     *
     * @param audioClassificationBO
     * @return
     */
    int updateCategory(AudioClassificationBO audioClassificationBO);
}