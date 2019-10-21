package com.xiaoniu.call.video.core.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.bo.AudioChangeWeightBO;
import com.xiaoniu.call.video.api.bo.AudioOnlineOfflineBO;
import com.xiaoniu.call.video.api.bo.AudioPageBO;
import com.xiaoniu.call.video.api.enums.AudioBusinessEnum;
import com.xiaoniu.call.video.api.vo.AudioVO;
import com.xiaoniu.call.video.core.autoconfigure.AudioCDNProperties;
import com.xiaoniu.call.video.core.constants.BusinessConstants;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.dto.AudioDTO;
import com.xiaoniu.call.video.core.entity.Audio;
import com.xiaoniu.call.video.core.enums.AudioCategoryEnum;
import com.xiaoniu.call.video.core.service.AudioWebService;
import com.xiaoniu.call.video.core.service.VideoWebService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 音频管理端业务实现
 *
 * @author liuyinkai
 * @date 2019-08-02
 */
@Service
@Log4j2
public class AudioWebServiceImpl implements AudioWebService {

    /**
     * 过审状态
     */
    private static final Integer CHECK_PASS = 1;

    /**
     * 不过审核状态
     */
    private static final Integer CHECK_NOT_PASS = 2;

    @Autowired
    private AudioCDNProperties audioCDNProperties;

    @Autowired
    private VideoWebService videoWebService;

    /**
     * 音频管理端业务
     *
     * @param audioPageBO
     * @return
     */
    @Override
    public PageResult<AudioVO> pageList(AudioPageBO audioPageBO) {
        Criteria criteria = Criteria.where("releaseTime").gte(audioPageBO.getStartTime().getTime()).lte(audioPageBO.getEndTime().getTime());
        String audioNumber = audioPageBO.getAudioNumber();
        String title = audioPageBO.getTitle();
        Integer audioType = audioPageBO.getAudioType();
        Integer check = audioPageBO.getCheck();
        Integer audioSource = audioPageBO.getAudioSource();
        String categoryNumber = audioPageBO.getCategoryNumber();
        String tag = audioPageBO.getTag();
        Boolean status = audioPageBO.getStatus();
        String sortField = audioPageBO.getSortField();
        if (StringUtils.isNotEmpty(audioNumber)) {
            criteria.and("audioNumber").is(audioNumber);
        }
        if (StringUtils.isNotEmpty(title)) {
            criteria.and("title").regex(".*?" + title + ".*?");
        }
        if (null != audioType) {
            criteria.and("audioType").is(audioType);
        }
        if (null != check) {
            criteria.and("check").is(check);
        }
        if (null != audioSource) {
            criteria.and("audioSource").is(audioSource);
        }
        if (null != categoryNumber) {
            criteria.and("categoryNumber").is(categoryNumber);
        }
        if (StringUtils.isNotEmpty(tag)) {
            criteria.and("tags").is(tag);
        }
        if (null != status) {
            criteria.and("status").is(status);
        }
        Sort sort = null;
        if (StringUtils.isNotEmpty(sortField)) {
            sort = Sort.by(Sort.Direction.DESC, sortField);
        } else {
            Sort sort2 = new Sort(Sort.Direction.ASC, "createTime");
            sort = new Sort(Sort.Direction.DESC, "weight").and(sort2);
        }
        Page<AudioVO> audioVOPage = MongodbRepository.findByPage(new Query(criteria), PageRequest.of(audioPageBO.getPageIndex() - 1, audioPageBO.getPageSize(), sort), AudioVO.class, BusinessConstants.AUDIO_COLLECTION_NAME);
        List<AudioVO> audioVOS = audioVOPage.getContent();
        audioVOS.forEach(audioVO -> {
            String cdn = audioCDNProperties.getCdn().get(audioVO.getAudioSource());
            audioVO.setAudioCover(cdn + audioVO.getAudioCover());
            audioVO.setAudioAddress(cdn + audioVO.getAudioAddress());
            // 铃声设置率
            audioVO.setRingtoneSettingRate(videoWebService.settingRateFormat(audioVO.getSetRingToneNumber(), audioVO.getRealListenNumber()));
        });

        PageResult<AudioVO> audioVOPageResult = new PageResult<>();
        audioVOPageResult.setTotal((int) audioVOPage.getTotalElements());
        audioVOPageResult.setTotalPages(audioVOPage.getTotalPages());
        audioVOPageResult.setRows(audioVOS);
        if (StringUtils.isNotBlank(sortField) && !sortField.equals("title")) {
            // 根据设置率排序
            List<AudioVO> audioVOSSorttedList = sortSettingRate(audioVOS, sortField);
            audioVOPageResult.setRows(audioVOSSorttedList);
        }
        return audioVOPageResult;
    }

    /**
     * 根据设置率排序
     *
     * @param list  排序集合
     * @param sortName  排序字段名
     * @return
     */
    public List<AudioVO> sortSettingRate(List<AudioVO> list, String sortName) {
        if (!CollectionUtils.isNotEmpty(list)) {
          return null;
        }
        if (sortName.equals(BusinessConstants.RINGTONE_SETTING_RATE)) {
            return list.stream().sorted(Comparator.comparing(AudioVO::getRingtoneSettingRate).reversed()).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 修改视频数据
     *
     * @param audioVO
     */
    @Override
    public void update(AudioVO audioVO) {
        // 编辑之前要确保当前歌曲编码不能在要修改的分类中存在
        Sort sort = new Sort(Sort.Direction.DESC, "weight");
        Page<AudioDTO> audioPage = MongodbRepository.findByPage(Query.query(Criteria.where("audioNumber").is(audioVO.getAudioNumber()).and("categoryNumber").is(audioVO.getTempCategoryNumber())),
                PageRequest.of(0, 1, sort),
                AudioDTO.class, BusinessConstants.AUDIO_COLLECTION_NAME);
        List<AudioDTO> audioDTOS = audioPage.getContent();
        if (audioDTOS.size() > 0) {
            throw new BusinessException(AudioBusinessEnum.AUDIO_CATEGORY_REPEAT);
        }

        Update update = new Update();
        Field[] fields = audioVO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(audioVO);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(String.format("获取字段值失败=%s", e.getMessage()));
            }

            if (!"audioNumber".equals(name) && !"serialVersionUID".equals(name) && StringUtils.isNotEmpty(name) && null != value) {
                update.set(name, value);
            }
        }

        update.set("updateTime", System.currentTimeMillis());
        update.set("categoryNumber", audioVO.getTempCategoryNumber());
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("audioNumber").is(audioVO.getAudioNumber()).and("categoryNumber").is(audioVO.getCategoryNumber())), update, Audio.class);
        if (updateResult.getModifiedCount() < 1) {
            throw new RuntimeException(String.format("修改音频数据失败，影响行数={}", updateResult.getModifiedCount()));
        }
    }


    /**
     * 批量上线
     *
     * @param ids
     */
    @Override
    public void audioBatchOnline(String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("操作上线数据不能为空");
        }
        List<String> list = Arrays.asList(ids);
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        update.set("status", true);
        for (String id : list) {

            UpdateResult updateResult = MongodbRepository.updateAllByClazz(new Query(Criteria.where("audioNumber").is(id)), update, Audio.class);
            if (updateResult.getModifiedCount() < 1) {
                throw new RuntimeException(String.format("修改音频上线数据失败，影响行数={}", updateResult.getModifiedCount()));
            }
        }

    }

    /**
     * 批量下线
     *
     * @param ids
     */
    @Override
    public void audioBatchOffline(String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("操作下线数据不能为空");
        }
        List<String> list = Arrays.asList(ids);
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        update.set("status", false);
        for (String id : list) {

            UpdateResult updateResult = MongodbRepository.updateAllByClazz(new Query(Criteria.where("audioNumber").is(id)), update, Audio.class);
            if (updateResult.getModifiedCount() < 1) {
                throw new RuntimeException(String.format("修改音频下线数据失败，影响行数={}", updateResult.getModifiedCount()));
            }
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void audioBatchDelete(String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("操作删除数据不能为空");
        }
        List<String> list = Arrays.asList(ids);
        for (String id : list) {
            MongodbRepository.delete(new Query(Criteria.where("audioNumber").is(id)), Audio.class);
            // 删除所有分类缓存下的该歌曲
            for (AudioCategoryEnum ace : AudioCategoryEnum.values()) {
               RedisRepository.srem(String.format(RedisConstants.AUDIO_POOL_CLASSIFIED, ace.getDesc()),id);
            }
        }
    }

    /**
     * 批量过审
     *
     * @param
     */
    @Override
    public void audioBatchCheckPass(String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("操作过审数据不能为空");
        }
        List<String> list = Arrays.asList(ids);
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        update.set("check", CHECK_PASS);
        for (String id : list) {
            UpdateResult updateResult = MongodbRepository.updateAllByClazz(new Query(Criteria.where("audioNumber").is(id)), update, Audio.class);
            if (updateResult.getModifiedCount() < 1) {
                throw new RuntimeException(String.format("修改音频过审数据失败，影响行数={}", updateResult.getModifiedCount()));
            }
        }
    }

    /**
     * 批量过审失败
     * @param ids
     */
    @Override
    public void audioBatchCheckNotPass(String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("操作过审失败数据不能为空");
        }
        List<String> list = Arrays.asList(ids);
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        update.set("check", CHECK_NOT_PASS);
        for (String id : list) {
            UpdateResult updateResult = MongodbRepository.updateAllByClazz(new Query(Criteria.where("audioNumber").is(id)), update, Audio.class);
            if (updateResult.getModifiedCount() < 1) {
                throw new RuntimeException(String.format("修改音频过审数据失败，影响行数={}", updateResult.getModifiedCount()));
            }
        }
    }

    /**
     * 权重更新
     *
     * @param audioChangeWeightBO
     */
    @Override
    public void audioChangeWeight(AudioChangeWeightBO audioChangeWeightBO) {
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        update.set("weight", audioChangeWeightBO.getWeight());
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("audioNumber").is(audioChangeWeightBO.getAudioNumber())
                .and("categoryNumber").is(audioChangeWeightBO.getCategoryNumber())), update, Audio.class);
        if (updateResult.getModifiedCount() < 1) {
            throw new RuntimeException(String.format("修改音频权重数据失败，影响行数={}", updateResult.getModifiedCount()));
        }
    }

    /**
     * 单行上下线
     *
     * @param audioOnlineOfflineBO
     */
    @Override
    public void singleOnlineOffline(AudioOnlineOfflineBO audioOnlineOfflineBO) {

        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        update.set("status", audioOnlineOfflineBO.getStatus());
        UpdateResult updateResult = MongodbRepository.updateAllByClazz(new Query(Criteria.where("audioNumber").
                is(audioOnlineOfflineBO.getAudioNumber())), update, Audio.class);
        if (updateResult.getModifiedCount() < 1) {
            throw new RuntimeException(String.format("修改音频上下线数据失败，影响行数={}，操作音频编号={}, 上下线类型={}",
                    updateResult.getModifiedCount(), audioOnlineOfflineBO.getAudioNumber(), audioOnlineOfflineBO.getStatus()));
        }

    }



}
