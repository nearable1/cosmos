package com.xiaoniu.call.video.core.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.enums.AudioBusinessEnum;
import com.xiaoniu.call.video.core.autoconfigure.AudioCDNProperties;
import com.xiaoniu.call.video.core.bo.AudioBO;
import com.xiaoniu.call.video.core.bo.AudioClassificationQueryBO;
import com.xiaoniu.call.video.core.bo.SearchAudioBO;
import com.xiaoniu.call.video.core.constants.BusinessConstants;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.dto.AudioClassificationDTO;
import com.xiaoniu.call.video.core.dto.AudioDTO;
import com.xiaoniu.call.video.core.entity.Audio;
import com.xiaoniu.call.video.core.enums.AudioBehaviorTypeEnum;
import com.xiaoniu.call.video.core.mapper.AudioClassificationMapper;
import com.xiaoniu.call.video.core.service.AudioBehaviorService;
import com.xiaoniu.call.video.core.service.AudioService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 音频业务
 *
 * @author liuyinkai
 * @date 2019-07-28
 */
@Service
@Log4j2
public class AudioServiceImpl implements AudioService {

    /**
     * 过审音频标识
     */
    public final static Integer AUDIO_CHECK_PASS = 1;

    @Autowired
    private AudioClassificationMapper audioClassificationMapper;

    @Autowired
    private AudioCDNProperties audioCDNProperties;

    @Autowired
    private AudioBehaviorService audioBehaviorService;

    /**
     * 音频分类列表
     *
     * @return
     */
    @Override
    public List<AudioClassificationDTO> audioCategoryList(AudioClassificationQueryBO audioClassificationQueryBO) {
        // 查询包名
        Integer appName = HeaderHelper.getAppName();
        // 分页参数
        int pageIndex = audioClassificationQueryBO.getPageIndex();
        int pageSize = audioClassificationQueryBO.getPageSize();
        // 查询分类信息
        List<AudioClassificationDTO> audioClassificationDTOS = audioClassificationMapper.selectValidList(appName, pageIndex, pageSize);
        // 查询出每个分类的前三首歌曲
        if (CollectionUtils.isNotEmpty(audioClassificationDTOS)) {
            Set<String> audioClassifiedPool = new HashSet<>();
            Sort sort = null;
            Sort sort2 = new Sort(Sort.Direction.ASC, "createTime");
            sort = new Sort(Sort.Direction.DESC, "weight").and(sort2);
            for (AudioClassificationDTO ac : audioClassificationDTOS) {
                audioClassifiedPool = RedisRepository.smembers(String.format(RedisConstants.AUDIO_POOL_CLASSIFIED, ac.getCategoryNumber()));
                Page<AudioDTO> audioPage = MongodbRepository.findByPage(Query.query(Criteria.where("audioNumber").in(audioClassifiedPool).and("check").is(AUDIO_CHECK_PASS).and("status").is(true).and("categoryNumber").is(ac.getCategoryNumber())),
                        PageRequest.of(0, 3, sort),
                        AudioDTO.class, BusinessConstants.AUDIO_COLLECTION_NAME);
                List<AudioDTO> audioDTOS = audioPage.getContent();

                for (AudioDTO audio : audioDTOS) {
                    String cdn = audioCDNProperties.getCdn().get(audio.getAudioSource());
                    audio.setAudioCover(cdn + audio.getAudioCover());
                    audio.setAudioAddress(cdn + audio.getAudioAddress());
                    audio.setShowListenNumber(audio.getRealListenNumber() + audio.getVirtualListenNumber());
                }
                ac.setAudioInfo(audioDTOS);
                audioClassifiedPool.clear();
            }

        }
        return audioClassificationDTOS;
    }

    /**
     * 根据分类编号查询最新音频
     *
     * @param audioBO
     * @return
     */
    @Override
    public PageResult<AudioDTO> selectLatestAudioListByCategoryNumber(AudioBO audioBO) {
        String categoryNumber = audioBO.getCategoryNumber();
        log.info("【根据分类编号查询音频】查询分类={}", categoryNumber);
        Set<String> allAudioClassifiedPool = RedisRepository.smembers(String.format(RedisConstants.AUDIO_POOL_CLASSIFIED, categoryNumber));
        Sort sort = null;
        Sort sort2 = new Sort(Sort.Direction.ASC, "createTime");
        sort = new Sort(Sort.Direction.DESC, "weight").and(sort2);
        return pageSelectAudio(Query.query(Criteria.where("audioNumber").in(allAudioClassifiedPool).and("categoryNumber").is(categoryNumber).and("check").is(AUDIO_CHECK_PASS).and("status").is(true)),
                PageRequest.of(audioBO.getPageIndex(), audioBO.getPageSize(), sort));
    }

    /**
     * 搜索音频
     *
     * @param searchAudioBO
     * @return
     */
    @Override
    public List<AudioDTO> searchAudioByKeyWord(SearchAudioBO searchAudioBO) {
        log.info("【搜索音频】搜索关键词={}", searchAudioBO.getKeyWord());
        Criteria criteria = Criteria.where("title").regex(".*?\\" + searchAudioBO.getKeyWord() + ".*").and("check").is(AUDIO_CHECK_PASS).and("status").is(true);
        Sort sort = null;
        Sort sort2 = new Sort(Sort.Direction.ASC, "createTime");
        sort = new Sort(Sort.Direction.DESC, "weight").and(sort2);
        Page <AudioDTO> audioPage = MongodbRepository.findByPage(Query.query(criteria), PageRequest.of(0, 200,
                sort), AudioDTO.class, BusinessConstants.AUDIO_COLLECTION_NAME);
        List<AudioDTO> audioDTOS = audioPage.getContent();
        // 过滤集合中重复的音频
        audioDTOS = audioDTOS.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(AudioDTO::getAudioNumber))), ArrayList::new));
        // 取集合前N条数据返回
        List<AudioDTO> rankList = audioDTOS.subList(0, searchAudioBO.getPageSize());
        rankList.forEach(audioDTO -> {
            String cdn = audioCDNProperties.getCdn().get(audioDTO.getAudioSource());
            audioDTO.setAudioCover(cdn + audioDTO.getAudioCover());
            audioDTO.setAudioAddress(cdn + audioDTO.getAudioAddress());
            audioDTO.setShowListenNumber(audioDTO.getRealListenNumber() + audioDTO.getVirtualListenNumber());
        });
        log.info("【搜索音频】搜索关键词={}, 搜索到的条数={}", searchAudioBO.getKeyWord(), rankList.size());
        return rankList;
    }

    /**
     * 根据标签查询排行榜歌曲
     *
     * @param tags
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<AudioDTO> selectAudioRankingListByTag(String tags, int pageIndex, int pageSize) {
        log.info("【查询彩铃排行榜】搜索标签={}", tags);
        Sort sort = null;
        Sort sort2 = new Sort(Sort.Direction.ASC, "createTime");
        sort = new Sort(Sort.Direction.DESC, "weight").and(sort2);
        return pageSelectAudio(Query.query(Criteria.where("tags").is(tags).and("check").is(AUDIO_CHECK_PASS).and("status").is(true)),
                PageRequest.of(pageIndex, pageSize, sort));
    }

    /**
     * 播放音频
     *
     * @param audioNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void playAudio(String audioNumber) {
        log.info("【播放音频】音频编码={}", audioNumber);
        audioBehaviorService.recordAudioBehavior(audioNumber, AudioBehaviorTypeEnum.PLAY);
        // 我平台真实播放数+1
        Update update = new Update().inc("realListenNumber", 1);
        UpdateResult updateResult = MongodbRepository.updateAllByClazz(new Query(Criteria.where("audioNumber").is(audioNumber)), update, Audio.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("【播放音频】要增加音频播放数的音频={}, 增加播放数失败，数据影响行数={}", audioNumber, updateResult.getModifiedCount());
            throw new BusinessException(AudioBusinessEnum.INCREASE_AUDIO_PLAY_NUM_FAIL);
        }
    }

    /**
     * 音频设置铃声
     *
     * @param audioNumber
     */
    @Override
    public void audioSetRingtone(String audioNumber) {
        log.info("【音频设置铃声】音频编码={}", audioNumber);
        audioBehaviorService.recordAudioBehavior(audioNumber, AudioBehaviorTypeEnum.SET_TO_RINGTONE);

        Update update = new Update().inc("setRingToneNumber", 1);
        UpdateResult updateResult = MongodbRepository.updateAllByClazz(new Query(Criteria.where("audioNumber").is(audioNumber)), update, Audio.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("【音频设置铃声】要增加设置彩铃数的音频={}, 增加设置数失败，数据影响行数={}", audioNumber, updateResult.getModifiedCount());
            throw new BusinessException(AudioBusinessEnum.INCREASE_SET_RINGTONE_NUM_FAIL);
        }
    }

    /**
     * 分页查询数据
     *
     * @param query
     * @param pageRequest
     * @return
     */
    @Override
    public PageResult<AudioDTO> pageSelectAudio(Query query, PageRequest pageRequest) {
        Page<AudioDTO> audioPage = MongodbRepository.findByPage(query, pageRequest, AudioDTO.class, BusinessConstants.AUDIO_COLLECTION_NAME);
        List<AudioDTO> audioDTOS = audioPage.getContent();
        log.info("【音频统一分页查询MongoDB】搜索到的条数={}", audioDTOS.size());
        audioDTOS.forEach(audioDTO -> {
            String cdn = audioCDNProperties.getCdn().get(audioDTO.getAudioSource());
            if (StringUtils.isEmpty(cdn)) {
                log.error("【音频统一分页查询MongoDB】未找到Apollo的CDN配置, 音频来源号={}", audioDTO.getAudioSource());
            }
            audioDTO.setAudioCover(cdn + audioDTO.getAudioCover());
            audioDTO.setAudioAddress(cdn + audioDTO.getAudioAddress());
            // 前端展示的播放数 = realListenNumber + virtualListenNumber
            audioDTO.setShowListenNumber(audioDTO.getRealListenNumber() + audioDTO.getVirtualListenNumber());
        });

        PageResult<AudioDTO> audioDTOPageResult = new PageResult<>();
        audioDTOPageResult.setTotal((int) audioPage.getTotalElements());
        audioDTOPageResult.setTotalPages(audioPage.getTotalPages());
        audioDTOPageResult.setRows(audioDTOS);
        return audioDTOPageResult;
    }
}
