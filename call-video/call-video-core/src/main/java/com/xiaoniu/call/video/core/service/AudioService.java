package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.core.bo.AudioBO;
import com.xiaoniu.call.video.core.bo.AudioClassificationQueryBO;
import com.xiaoniu.call.video.core.bo.SearchAudioBO;
import com.xiaoniu.call.video.core.dto.AudioClassificationDTO;
import com.xiaoniu.call.video.core.dto.AudioDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * 音频相关接业务
 *
 * @author liuyinkai
 * @date 2019-07-25
 */
public interface AudioService {


    /**
     * 音频分类列表
     *
     * @return
     */
    List<AudioClassificationDTO> audioCategoryList(AudioClassificationQueryBO audioClassificationQueryBO);

    /**
     * 根据分类编号查询最新视频
     *
     * @param audioBO
     * @return
     */
    PageResult<AudioDTO> selectLatestAudioListByCategoryNumber(AudioBO audioBO);

    /**
     * 搜索音频
     *
     * @param searchAudioBO
     * @return
     */
    List<AudioDTO> searchAudioByKeyWord(SearchAudioBO searchAudioBO);

    /**
     * 根据标签查询排行榜歌曲
     *
     * @param tags
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageResult<AudioDTO> selectAudioRankingListByTag(String tags, int pageIndex, int pageSize);

    /**
     * 音频播放
     *
     * @param audioNumber
     */
    void playAudio(String audioNumber);

    /**
     * 音频设置铃声
     *
     * @param audioNumber
     */
    void audioSetRingtone(String audioNumber);

    /**
     * 查询mangoDB
     *
     * @param query
     * @param pageRequest
     * @return
     */
    PageResult<AudioDTO> pageSelectAudio(Query query, PageRequest pageRequest);

}
