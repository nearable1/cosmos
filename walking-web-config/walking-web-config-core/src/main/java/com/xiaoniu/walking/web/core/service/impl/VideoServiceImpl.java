package com.xiaoniu.walking.web.core.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.walking.web.core.configuration.VideoCDNProperties;
import com.xiaoniu.walking.web.core.constants.BusinessConstants;
import com.xiaoniu.walking.web.core.constants.RedisConstants;
import com.xiaoniu.walking.web.core.dto.VideoClassificationWebDTO;
import com.xiaoniu.walking.web.core.dto.VideoTagWebDTO;
import com.xiaoniu.walking.web.core.dto.VideoWebDTO;
import com.xiaoniu.walking.web.core.enums.VideoTypeEnum;
import com.xiaoniu.walking.web.core.enums.VideoWebBusinessEnum;
import com.xiaoniu.walking.web.core.mapper.auto.VideoClassificationMapper;
import com.xiaoniu.walking.web.core.mapper.auto.VideoTagMapper;
import com.xiaoniu.walking.web.core.model.auto.Video;
import com.xiaoniu.walking.web.core.model.auto.VideoClassification;
import com.xiaoniu.walking.web.core.model.auto.VideoTag;
import com.xiaoniu.walking.web.core.model.auto.Video_review;
import com.xiaoniu.walking.web.core.service.VideoService;
import com.xiaoniu.walking.web.core.service.VideoWebService;
import com.xiaoniu.walking.web.core.vo.VideoPageVO;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 视频管理端
 *
 * @author wuwen
 * @date 2019-07-16 10:38
 */
@Log4j2
@Service
public class VideoServiceImpl implements VideoService {


    @Autowired
    private VideoWebService videoWebService;

    @Autowired
    private VideoTagMapper videoTagMapper;

    @Autowired
    private VideoCDNProperties videoCDNProperties;

    @Autowired
    private VideoClassificationMapper videoClassificationMapper;
    /**
     * 分页查询视频
     *
     * @param videoPageVO
     * @return
     */
    @Override
    public PageResult<VideoWebDTO> pageList(@Valid @RequestBody VideoPageVO videoPageVO) {
        Criteria criteria = new Criteria();
        if (videoPageVO.getStartTime() != null && videoPageVO.getEndTime() != null) {

            criteria = Criteria.where("releaseTime").gte(videoPageVO.getStartTime().getTime()).lte(videoPageVO.getEndTime().getTime());
        }
        criteria = Criteria.where("releaseTime").lte(new Date().getTime());

        String videoNumber = videoPageVO.getVideoNumber();
        String title = videoPageVO.getTitle();
        Integer videoType = videoPageVO.getVideoType();
        Integer videoSource = videoPageVO.getVideoSource();
        String categoryNumber = videoPageVO.getCategoryNumber();
        String tag = videoPageVO.getTag();
        Boolean status = videoPageVO.getStatus();
        String sortField = videoPageVO.getSortField();
        String videoDbTags = videoPageVO.getVideoDbTags();
        String videoTypeTags = videoPageVO.getVideoTypeTags();
        if (StringUtils.isNotEmpty(videoNumber)) {
            criteria.and("videoNumber").is(videoNumber);
        }
        if (StringUtils.isNotEmpty(title)) {
            criteria.and("title").regex(".*?" + title + ".*?");
        }
        if (null != videoType) {
            criteria.and("videoType").is(videoType);
        }
        if (null != videoSource) {
            criteria.and("videoSource").is(videoSource);
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
        if (StringUtils.isNotEmpty(videoDbTags)) {
            criteria.and("videoDbTags").is(videoDbTags);
        }
        if (StringUtils.isNotEmpty(videoPageVO.getVideoDbOperTags())) {
            criteria.and("videoDbOperTags").is(videoPageVO.getVideoDbOperTags());
        }
        if (StringUtils.isNotEmpty(videoTypeTags)) {
            criteria.and("videoTypeTags").is(videoTypeTags);
        }
        Sort sort = null;
        if (StringUtils.isNotEmpty(sortField)) {
            sort = Sort.by(Sort.Direction.DESC, sortField);
        } else {
            sort = Sort.by(Sort.Direction.DESC, "weight", "createTime");
        }

        Page<Video> videoPage = MongodbRepository.findByPage(new Query(criteria), PageRequest.of(videoPageVO.getPageIndex() - 1,
                videoPageVO.getPageSize(), sort), Video.class);
        List<Video> content = videoPage.getContent();
        if (content.size() < 1) {
            log.info("【分页查询视频】==============MongoDB查询结果为空");
        }
        List<VideoWebDTO> videoWebDTOS = new ArrayList<>();
        for (Video video : content) {
            VideoWebDTO videoWebDTO = new VideoWebDTO();
            BeanUtils.copyProperties(video, videoWebDTO);
            videoWebDTOS.add(videoWebDTO);
        }
        videoWebDTOS.forEach(videoWebDTO -> {
            String cdn = videoCDNProperties.getCdn().get(0);
            videoWebDTO.setVideoCover(cdn + videoWebDTO.getVideoCover());
            videoWebDTO.setVideoAddress(cdn + videoWebDTO.getVideoAddress());
            videoWebDTO.setGifAddress(cdn + videoWebDTO.getGifAddress());
            videoWebDTO.setAudioAddress(cdn + videoWebDTO.getAudioAddress());
            // 来电秀设置率
            videoWebDTO.setCallShowSettingRate(settingRateFormat(videoWebDTO.getCallNumber(), videoWebDTO.getViewReal()));
            // 壁纸设置率
            videoWebDTO.setWallpaperSettingRate(settingRateFormat(videoWebDTO.getWallpaperNumber(), videoWebDTO.getViewReal()));
            // 铃声设置率
            videoWebDTO.setRingtoneSettingRate(settingRateFormat(videoWebDTO.getRingtoneShowNumber(), videoWebDTO.getViewReal()));
            // 导出翻译字段
            StringBuilder sb = new StringBuilder();
            if (videoWebDTO.getVideoTypeTags() != null && videoWebDTO.getVideoTypeTags().size() > 0) {
                for (String ss : videoWebDTO.getVideoTypeTags()) {
                    sb.append(VideoTypeEnum.resolve(Integer.valueOf(ss)).getDesc()).append(",");
                }
            }
            videoWebDTO.setVideoTypeTagsStr(sb.toString());
        });

        PageResult<VideoWebDTO> videoPageResult = new PageResult<>();
        videoPageResult.setTotal((int) videoPage.getTotalElements());
        videoPageResult.setTotalPages(videoPage.getTotalPages());
        videoPageResult.setRows(videoWebDTOS);
        if (StringUtils.isNotBlank(sortField) && !sortField.equals("title")) {
            // 根据设置率排序
            List<VideoWebDTO> videoWebDTOSorttedList = sortSettingRate(videoWebDTOS, sortField);
            videoPageResult.setRows(videoWebDTOSorttedList);
        }
        return videoPageResult;
    }

    /**
     * 计算设置率
     *
     * @param settingNum
     * @param playNum
     * @return
     */
    public String settingRateFormat(Integer settingNum, Integer playNum) {
        if (settingNum == null || settingNum == 0) {
            return "0.00%";
        } else if (playNum == null || playNum == 0) {
            return "0.00%";
        }
        // 转换成浮点型
        float settingNumF = (float) settingNum;
        float playNumF = (float) playNum;
        // 百分数实例
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        String format = nt.format(settingNumF / playNumF);
        return format;
    }

    /**
     * 根据设置率排序
     *
     * @param sortName  排序字段名
     * @return
     */
    public List<VideoWebDTO> sortSettingRate(List<VideoWebDTO> list, String sortName) {

        if (sortName.equals(BusinessConstants.RINGTONE_SETTING_RATE)) {
            return list.stream().sorted(Comparator.comparing(VideoWebDTO::getRingtoneSettingRate).reversed()).collect(Collectors.toList());

        } else if (sortName.equals(BusinessConstants.CALLSHOW_SETTING_RATE)) {
            return list.stream().sorted(Comparator.comparing(VideoWebDTO::getCallShowSettingRate).reversed()).collect(Collectors.toList());

        } else if (sortName.equals(BusinessConstants.WALLPAPER_SETTING_RATE)) {
            return list.stream().sorted(Comparator.comparing(VideoWebDTO::getWallpaperSettingRate).reversed()).collect(Collectors.toList());
        }

        return null;
    }

    /**
     * 修改视频数据
     *
     * @param videoWebDTO
     * @param videoNumber
     */
    @Override
    @PutMapping("/{videoNumber}")
    public void updateVideo(@Valid @RequestBody VideoWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber) {
        videoWebDTO.setVideoNumber(videoNumber);
        if (StringUtils.isNotEmpty(videoWebDTO.getVideoCover())) {
            videoWebDTO.setVideoCover(videoWebDTO.getVideoCover().substring(videoWebDTO.getVideoCover().indexOf("zuilaidian"), videoWebDTO.getVideoCover().length()));
        }
        Update update = new Update();
        Field[] fields = videoWebDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(videoWebDTO);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(String.format("获取字段值失败=%s", e.getMessage()));
            }

            if (!"videoNumber".equals(name) && !"serialVersionUID".equals(name) && StringUtils.isNotEmpty(name) && null != value) {
                update.set(name, value);
            }
        }

        update.set("updateTime", System.currentTimeMillis());

        // 同步大池推荐池数据
        Document document = update.getUpdateObject().get("$set", Document.class);
        if (document.containsKey("status")) {
            UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoWebDTO.getVideoNumber())), update, Video.class);
            if (updateResult.getModifiedCount() < 1) {
                throw new RuntimeException(String.format("修改视频数据失败，影响行数={}", updateResult.getModifiedCount()));
            }

            Boolean status = document.getBoolean("status");
            if (status) {
                // 上线
                addVideoPool(videoWebDTO.getVideoNumber());
            } else {
                // 下线
                // 查询该对象
                Video video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoWebDTO.getVideoNumber())), Video.class);
                // 删除首页视频池
                delVideoPool(videoWebDTO.getVideoNumber(), video);
            }
        } else {
            // 编辑视频数据
            Video oldVideo = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoWebDTO.getVideoNumber())), Video.class);
            UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoWebDTO.getVideoNumber())), update, Video.class);
            if (updateResult.getModifiedCount() < 1) {
                throw new RuntimeException(String.format("修改视频数据失败，影响行数={}", updateResult.getModifiedCount()));
            }
            if (oldVideo.getStatus()) {
                syncVideoPool(oldVideo, videoWebDTO);
            }
        }
    }

    /**
     * 添加视频到视频池
     *
     * @param videoNumber
     */
    public void addVideoPool(String videoNumber) {
        Video video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), Video.class);
        if (video != null) {
            if (video.getVideoTypeTags().contains("1")) {
                // 首页视频才添加到池中
                if (CollectionUtils.isNotEmpty(video.getVideoDbTags())) {
                    // 首页总池添加
                    for (String tags : video.getVideoDbTags()) {
                        RedisRepository.sadd(String.format(RedisConstants.VIDEO_POOL_CATEGORY_HOME, tags), videoNumber);
                    }

                    // 分类总池添加
                    RedisRepository.sadd(String.format(RedisConstants.VIDEO_POOL_CLASSIFIED, video.getCategoryNumber()), videoNumber);
                }
            }
            if (video.getVideoTypeTags().contains("3")) {
                // 小视频池
                RedisRepository.sadd(RedisConstants.VIDEO_POOL_SMALL, videoNumber);
            }
        }
    }

    /**
     * 删除首页视频池
     *
     * @param videoNumber
     */
    public void delVideoPool(String videoNumber, Video video) {
        if (video != null) {
            if (CollectionUtils.isNotEmpty(video.getVideoDbTags())) {
                // 首页总池删除
                for (String tags : video.getVideoDbTags()) {
                    RedisRepository.srem(String.format(RedisConstants.VIDEO_POOL_CATEGORY_HOME, tags), videoNumber);
                }
            }
            // 分类总池删除
            RedisRepository.srem(String.format(RedisConstants.VIDEO_POOL_CLASSIFIED, video.getCategoryNumber()), videoNumber);

            // 小视频池删除
            RedisRepository.srem(RedisConstants.VIDEO_POOL_SMALL, videoNumber);
        }

    }

    /**
     * 同步来电、壁纸、屏保推荐池和大池
     *
     * @param videoWebDTO
     */
    public void syncVideoPool(Video oldVideo, VideoWebDTO videoWebDTO) {
        // 首页总池数据清除
        if (CollectionUtils.isNotEmpty(oldVideo.getVideoDbTags())) {
            for (String tags : oldVideo.getVideoDbTags()) {
                RedisRepository.srem(String.format(RedisConstants.VIDEO_POOL_CATEGORY_HOME, tags), oldVideo.getVideoNumber());
            }
        }

        // 分类总池删除
        if (!StringUtils.equals(oldVideo.getCategoryNumber(), videoWebDTO.getCategoryNumber())) {
            RedisRepository.srem(String.format(RedisConstants.VIDEO_POOL_CLASSIFIED, oldVideo.getCategoryNumber()), oldVideo.getVideoNumber());
        }

        // 小视频池删除
        if (oldVideo.getVideoTypeTags().contains("3") && !videoWebDTO.getVideoTypeTags().contains("3")) {
            RedisRepository.srem(RedisConstants.VIDEO_POOL_SMALL, oldVideo.getVideoNumber());
        }

        // 首页总池，分类总池同步
        if (videoWebDTO.getVideoTypeTags().contains("1")) {
            if (CollectionUtils.isNotEmpty(videoWebDTO.getVideoDbTags())) {
                // 首页总池视频数据添加
                for (String tags : videoWebDTO.getVideoDbTags()) {
                    RedisRepository.sadd(String.format(RedisConstants.VIDEO_POOL_CATEGORY_HOME, tags), videoWebDTO.getVideoNumber());
                }

                // 分类总池添加
                RedisRepository.sadd(String.format(RedisConstants.VIDEO_POOL_CLASSIFIED, videoWebDTO.getCategoryNumber()), videoWebDTO.getVideoNumber());
            }
        }

        // 小视频池同步
        if (videoWebDTO.getVideoTypeTags().contains("3")) {
            RedisRepository.sadd(RedisConstants.VIDEO_POOL_SMALL, videoWebDTO.getVideoNumber());
        }
    }

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    @GetMapping("/classification")
    public List<VideoClassificationWebDTO> videoClassification() {
        return videoClassificationMapper.selectList();
    }

    /**
     * 修改
     *
     * @param videoClassificationWebDTO
     */
    @Override
    @PutMapping("/classification")
    public void updateVideoClassification(@Valid @RequestBody VideoClassificationWebDTO videoClassificationWebDTO) {
        VideoClassification videoClassification = new VideoClassification();
        videoClassification.setId(videoClassificationWebDTO.getId());
        videoClassification.setCategoryName(videoClassificationWebDTO.getCategoryName());
        videoClassification.setCategoryIcon(videoClassificationWebDTO.getCategoryIcon());
        videoClassification.setWeight(videoClassificationWebDTO.getWeight());
        videoClassification.setStatus(videoClassificationWebDTO.getStatus());
        videoClassification.setOperator(videoClassificationWebDTO.getOperator());
        int rows = videoClassificationMapper.updateByPrimaryKeySelective(videoClassification);
        if (rows != 1) {
            throw new BusinessException(VideoWebBusinessEnum.VIDEO_CLASSIFICATION_MODIFICATION_FAILED);
        }
    }

    /**
     * 添加
     *
     * @param videoClassificationWebDTO
     */
    @Override
    @PostMapping("/classification")
    public void insertVideoClassification(@Valid @RequestBody VideoClassificationWebDTO videoClassificationWebDTO) {
        String maxCategoryNumber = videoClassificationMapper.selectMaximumNumber();
        if (StringUtils.isNotEmpty(maxCategoryNumber)) {
            int value = Integer.valueOf(StringUtils.remove(maxCategoryNumber, BusinessConstants.VIDEO_CLASSIFICATION_PREFIX)) + 1;
            maxCategoryNumber = BusinessConstants.VIDEO_CLASSIFICATION_PREFIX + StringUtils.leftPad(String.valueOf(value), 3, "0");
        } else {
            maxCategoryNumber = BusinessConstants.VIDEO_CLASSIFICATION_PREFIX + "001";
        }

        VideoClassification videoClassification = new VideoClassification();
        videoClassification.setCategoryNumber(maxCategoryNumber);
        videoClassification.setCategoryName(videoClassificationWebDTO.getCategoryName());
        videoClassification.setCategoryIcon(videoClassificationWebDTO.getCategoryIcon());
        videoClassification.setWeight(videoClassificationWebDTO.getWeight());
        videoClassification.setStatus(videoClassificationWebDTO.getStatus());
        videoClassification.setOperator(videoClassificationWebDTO.getOperator());
        videoClassificationMapper.insertSelective(videoClassification);
    }

    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public List<VideoTag> videoTag() {
        List<VideoTag> videoTags = videoTagMapper.selectListAll();
        return videoTags;
    }

    /**
     * 修改
     *
     * @param videoTagWebDTO
     */
    @Override
    public void updateVideoTag(@Valid @RequestBody VideoTagWebDTO videoTagWebDTO) {
        VideoTag videoTag = new VideoTag();
        videoTag.setId(videoTagWebDTO.getId());
        videoTag.setTagName(videoTagWebDTO.getTagName());
        videoTag.setWeights(videoTagWebDTO.getWeights());
        videoTag.setStatus(videoTagWebDTO.getStatus());
        videoTag.setUpdateTime(new Date());
        int rows = videoTagMapper.updateByPrimaryKeySelective(videoTag);
        if (rows != 1) {
            throw new BusinessException(VideoWebBusinessEnum.VIDEO_TAG_MODIFICATION_FAILED);
        }
        RedisRepository.del(RedisConstants.VIDEO_TAG);
    }

    /**
     * 添加
     *
     * @param videoTagWebDTO
     */
    @Override
    public void insertVideoTag(@Valid @RequestBody VideoTagWebDTO videoTagWebDTO) {
        String maxTagNumber = videoTagMapper.selectMaximumNumber();
        if (StringUtils.isNotEmpty(maxTagNumber)) {
            int value = Integer.valueOf(StringUtils.remove(maxTagNumber, BusinessConstants.VIDEO_TAG_PREFIX)) + 1;
            maxTagNumber = BusinessConstants.VIDEO_TAG_PREFIX + StringUtils.leftPad(String.valueOf(value), 3, "0");
        } else {
            maxTagNumber = "VT001";
        }

        VideoTag videoTag = new VideoTag();
        videoTag.setTagNumber(maxTagNumber);
        videoTag.setTagName(videoTagWebDTO.getTagName());
        videoTag.setWeights(videoTagWebDTO.getWeights());
        videoTag.setStatus(1);
        int rows = videoTagMapper.insertSelective(videoTag);
        if (rows != 1) {
            throw new BusinessException(VideoWebBusinessEnum.VIDEO_TAG_MODIFICATION_FAILED);
        }
        RedisRepository.del(RedisConstants.VIDEO_TAG);
    }

    /**
     * 批量上线
     *
     * @param ids
     */
    @Override
    public void batchOnline(String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("操作上线数据不能为空");
        }
        List<String> list = Arrays.asList(ids);
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        update.set("status", "true");
        for (String id : list) {

            UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(id)), update, Video.class);
            if (updateResult.getModifiedCount() < 1) {
                throw new RuntimeException(String.format("修改视频上线数据失败，影响行数={}", updateResult.getModifiedCount()));
            }
            addVideoPool(id);
        }
    }

    /**
     * 批量下线
     *
     * @param ids
     */
    @Override
    public void batchOffline(String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("操作下线数据不能为空");
        }
        List<String> list = Arrays.asList(ids);
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        update.set("status", "false");
        for (String id : list) {
            // 查询该对象
            Video video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(id)), Video.class);
            UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(id)), update, Video.class);
            if (updateResult.getModifiedCount() < 1) {
                throw new RuntimeException(String.format("修改视频下线数据失败，影响行数={}", updateResult.getModifiedCount()));
            }
            // 删除首页视频池
            delVideoPool(id, video);
        }
    }

    /**
     * 删除视频数据
     */
    @DeleteMapping("/delete/{videoNumber}")
    @Override
    public void deleteVideo(@PathVariable("videoNumber") String videoNumber) {
        if (StringUtils.isEmpty(videoNumber)) {
            throw new RuntimeException("删除审核数据不能为空");
        }
        // 查询该对象
        Video video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), Video.class);
        MongodbRepository.delete(new Query(Criteria.where("videoNumber").is(videoNumber)), Video.class);
        // 删除首页视频池
        delVideoPool(videoNumber, video);
    }

    /**
     * 批量删除
     *
     * @param
     */
    @PostMapping("/batchDelete")
    @Override
    public void batchDelete(@RequestParam("ids") String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("删除审核数据不能为空");
        }
        for (String id : ids) {
            // 查询该对象
            Video video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(id)), Video.class);
            MongodbRepository.delete(new Query(Criteria.where("videoNumber").is(id)), Video.class);
            // 删除首页视频池
            delVideoPool(id, video);
        }
    }


    /**
     * 分页查询审核视频
     *
     * @return
     */
    @PostMapping("/review/page")
    @Override
    public PageResult<VideoWebDTO> reviewPageList(@Valid @RequestBody VideoPageVO videoPageVO) {
        Criteria criteria = Criteria.where("createTime").gte(videoPageVO.getStartTime().getTime())
                .lte(videoPageVO.getEndTime().getTime());
        String videoNumber = videoPageVO.getVideoNumber();
        String title = videoPageVO.getTitle();
        Integer videoType = videoPageVO.getVideoType();
        Integer videoSource = videoPageVO.getVideoSource();
        String categoryNumber = videoPageVO.getCategoryNumber();
        String tag = videoPageVO.getTag();
        Boolean status = videoPageVO.getStatus();
        String sortField = videoPageVO.getSortField();
        String videoDbTags = videoPageVO.getVideoDbTags();
        String videoTypeTags = videoPageVO.getVideoTypeTags();
        if (StringUtils.isNotEmpty(videoNumber)) {
            criteria.and("videoNumber").is(videoNumber);
        }
        if (StringUtils.isNotEmpty(title)) {
            criteria.and("title").regex(".*?" + title + ".*?");
        }
        if (null != videoType) {
            criteria.and("videoType").is(videoType);
        }
        if (null != videoSource) {
            criteria.and("videoSource").is(videoSource);
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
        if (StringUtils.isNotEmpty(videoDbTags)) {
            criteria.and("videoDbTags").is(videoDbTags);
        }
        if (StringUtils.isNotEmpty(videoTypeTags)) {
            criteria.and("videoTypeTags").is(videoTypeTags);
        }
        Sort sort = null;
        if (StringUtils.isNotEmpty(sortField)) {
            sort = Sort.by(Sort.Direction.DESC, sortField);
        } else {
            sort = Sort.by(Sort.Direction.DESC, "weight", "createTime");
        }

        Page<VideoWebDTO> videoDTOPage = MongodbRepository.findByPage(new Query(criteria),
                PageRequest.of(videoPageVO.getPageIndex() - 1, videoPageVO.getPageSize(), sort), VideoWebDTO.class);
        List<VideoWebDTO> videoWebDTOS = videoDTOPage.getContent();
        videoWebDTOS.forEach(videoWebDTO -> {
            String cdn = videoCDNProperties.getCdn().get(0);
            videoWebDTO.setVideoCover(cdn + videoWebDTO.getVideoCover());
            videoWebDTO.setVideoAddress(cdn + videoWebDTO.getVideoAddress());
            videoWebDTO.setGifAddress(cdn + videoWebDTO.getGifAddress());
            videoWebDTO.setAudioAddress(cdn + videoWebDTO.getAudioAddress());
        });

        PageResult<VideoWebDTO> videoPageResult = new PageResult<>();
        videoPageResult.setTotal((int) videoDTOPage.getTotalElements());
        videoPageResult.setTotalPages(videoDTOPage.getTotalPages());
        videoPageResult.setRows(videoWebDTOS);
        return videoPageResult;
    }

    /**
     * 修改审核视频数据
     */
    @PutMapping("/review/{videoNumber}")
    @Override
    public void updateReviewVideo(@Valid @RequestBody VideoWebDTO videoWebDTO, @PathVariable("videoNumber") String videoNumber) {
        videoWebDTO.setVideoNumber(videoNumber);
        Update update = new Update();
        Field[] fields = videoWebDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(videoWebDTO);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(String.format("获取字段值失败=%s", e.getMessage()));
            }

            if (!"videoNumber".equals(name) && !"serialVersionUID".equals(name) && StringUtils.isNotEmpty(name)
                    && null != value) {
                update.set(name, value);
            }
        }

        update.set("updateTime", System.currentTimeMillis());
        UpdateResult updateResult = MongodbRepository.updateByClazz(
                new Query(Criteria.where("videoNumber").is(videoWebDTO.getVideoNumber())), update, Video_review.class);
        if (updateResult.getModifiedCount() < 1) {
            throw new RuntimeException(String.format("修改视频数据失败，影响行数={}", updateResult.getModifiedCount()));
        }
    }

    /**
     * 删除审核视频数据
     */
    @DeleteMapping("/review/delete/{videoNumber}")
    @Override
    public void deleteReviewVideo(@PathVariable("videoNumber") String videoNumber) {
        if (StringUtils.isEmpty(videoNumber)) {
            throw new RuntimeException("删除审核数据不能为空");
        }
        MongodbRepository.delete(new Query(Criteria.where("videoNumber").is(videoNumber)), Video_review.class);
    }

    /**
     * 审核批量上线
     *
     * @param
     */
    @PostMapping("/review/batchOnline")
    @Override
    public void reviewBatchOnline(@RequestParam("ids") String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("操作上线数据不能为空");
        }
        for (String id : ids) {
            Video video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(id)), Video.class);
            try {
                MongodbRepository.save(video);
                // 上线加入池
                videoWebService.addVideoPool(id);
            } catch (Exception e) {
                log.error("视频审核上线异常，id:{}，error:{}", id, e);
            }

            MongodbRepository.delete(new Query(Criteria.where("videoNumber").is(id)), Video_review.class);
        }
    }

    /**
     * 审核批量下线
     *
     * @param
     */
    @PostMapping("/review/batchDelete")
    @Override
    public void reviewBatchDelete(@RequestParam("ids") String[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("删除审核数据不能为空");
        }
        for (String id : ids) {
            MongodbRepository.delete(new Query(Criteria.where("videoNumber").is(id)), Video_review.class);
        }
    }

    /**
     * 批量分类
     *
     * @param
     */
    @PostMapping("/batchUpdateType")
    @Override
    public void batchUpdateType(@RequestParam("ids") String[] ids, @RequestParam("categoryNumber") String categoryNumber) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("批量分类数据不能为空");
        }
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        update.set("categoryNumber", categoryNumber);

        List<Video> oldVideos = MongodbRepository.findListByClazz(new Query(Criteria.where("videoNumber").in(ids).and("status").is(true)), Video.class);
        UpdateResult updateResult = MongodbRepository.updateAllByClazz(new Query(Criteria.where("videoNumber").in(ids)), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            throw new RuntimeException(String.format("修改视频分类数据失败，影响行数={}", updateResult.getModifiedCount()));
        }

        // 同步分类池
        syncCategoryPool(oldVideos, categoryNumber);
    }

    /**
     * 批量修改分类同步分类池数据，当新分类与旧分类不一致时需要同步
     *
     * @param oldList
     * @param categoryNumber
     */
    public void syncCategoryPool(List<Video> oldList, String categoryNumber) {
        if (CollectionUtils.isEmpty(oldList)) {
            return;
        }
        for (Video video : oldList) {
            if (!StringUtils.equals(video.getCategoryNumber(), categoryNumber) && video.getStatus()) {
                // 分类总池删除
                RedisRepository.srem(String.format(RedisConstants.VIDEO_POOL_CLASSIFIED, video.getCategoryNumber()), video.getVideoNumber());

                // 分类总池添加
                if (CollectionUtils.isNotEmpty(video.getVideoDbTags()) && video.getVideoTypeTags().contains("1")) {
                    RedisRepository.sadd(String.format(RedisConstants.VIDEO_POOL_CLASSIFIED, categoryNumber), video.getVideoNumber());
                }
            }
        }
    }

    /**
     * 批量标签
     *
     * @param
     */
    @PostMapping("/batchUpdateTag")
    @Override
    public void batchUpdateTag(@RequestParam("ids") String[] ids, @RequestParam("tags") List<String> tags) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new RuntimeException("批量分类数据不能为空");
        }
        Update update = new Update();
        update.set("updateTime", System.currentTimeMillis());
        update.set("tags", tags);

        UpdateResult updateResult = MongodbRepository.updateAllByClazz(new Query(Criteria.where("videoNumber").in(ids)), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            throw new RuntimeException(String.format("修改视频分类数据失败，影响行数={}", updateResult.getModifiedCount()));
        }
    }

}
