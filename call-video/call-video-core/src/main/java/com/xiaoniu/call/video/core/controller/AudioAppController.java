package com.xiaoniu.call.video.core.controller;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.core.bo.AudioBO;
import com.xiaoniu.call.video.core.bo.AudioClassificationQueryBO;
import com.xiaoniu.call.video.core.bo.SearchAudioBO;
import com.xiaoniu.call.video.core.dto.AudioClassificationDTO;
import com.xiaoniu.call.video.core.dto.AudioDTO;
import com.xiaoniu.call.video.core.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 音频相关接口
 *
 * @author liuyinkai
 * @date 2019-07-25
 */
@RestController
public class AudioAppController {

    @Autowired
    private AudioService audioService;

    /**
     * 首页分类列表
     *
     * @return
     */
    @PostMapping("/audio-category")
    public List<AudioClassificationDTO> homeCategoryList(@RequestBody @Valid AudioClassificationQueryBO audioClassificationQueryBO) {
        return audioService.audioCategoryList(audioClassificationQueryBO);
    }

    /**
     * 根据分类编号查询最新音频
     *
     * @param audioBO
     * @return
     */
    @PostMapping("/{categoryNumber}/query-category")
    public PageResult<AudioDTO> selectLatestVideoListByCategoryNumber(@RequestBody @Valid AudioBO audioBO, @PathVariable("categoryNumber") String categoryNumber) {
        audioBO.setCategoryNumber(categoryNumber);
        return audioService.selectLatestAudioListByCategoryNumber(audioBO);
    }

    /**
     * 搜索音频
     *
     * @param searchAudioBO
     * @return
     */
    @PostMapping("/search-audio")
    public List<AudioDTO> searchAudio(@RequestBody @Valid SearchAudioBO searchAudioBO) {
        return audioService.searchAudioByKeyWord(searchAudioBO);
    }

    /**
     * 根据标签查询排行榜歌曲
     *
     * @param tagNumber 标签码
     * @param pageIndex 页码
     * @param pageSize  页量
     * @return
     */
    @GetMapping("/{tagNumber}/audio-tag/{pageIndex}/{pageSize}")
    public PageResult<AudioDTO> selectAudioRankingListByTag(@PathVariable("tagNumber") String tagNumber, @PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize) {
        return audioService.selectAudioRankingListByTag(tagNumber, pageIndex, pageSize);
    }

    /**
     * 音频播放
     *
     * @param audioNumber
     */
    @PutMapping("/{audioNumber}/play")
    public void playAudio(@PathVariable("audioNumber") String audioNumber) {
        audioService.playAudio(audioNumber);
    }

    /**
     * 音频设置铃声
     *
     * @param audioNumber
     */
    @PutMapping("/{audioNumber}/setRingtone")
    public void setRingtones(@PathVariable("audioNumber") String audioNumber) {
        audioService.audioSetRingtone(audioNumber);
    }
}
