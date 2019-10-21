package com.xiaoniu.call.video.core.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.bo.VideoSettingRatePageBO;
import com.xiaoniu.call.video.api.dto.VideoWebDTO;
import com.xiaoniu.call.video.api.vo.*;
import com.xiaoniu.call.video.core.autoconfigure.VideoCDNProperties;
import com.xiaoniu.call.video.core.constants.BusinessConstants;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.entity.Video;
import com.xiaoniu.call.video.core.enums.AppNameEnum;
import com.xiaoniu.call.video.core.enums.VideoTypeEnum;
import com.xiaoniu.call.video.core.mapper.VideSettingRatesMapper;
import com.xiaoniu.call.video.core.service.VideoWebService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视频管理端业务实现
 *
 * @author wuwen
 * @date 2019-07-15 21:16
 */
@Service
@Log4j2
public class VideoWebServiceImpl implements VideoWebService {

    @Value("${video.oss.suffix:?x-oss-process=image/format,webp/quality,Q_100}")
    private String ossSuffix;

    @Autowired
    private VideoCDNProperties videoCDNProperties;

    @Autowired
    private VideSettingRatesMapper videSettingRatesMapper;

    @Autowired
    private PageRepository pageRepository;

    /**
     * 最来电引导视频
     */
    @Value("${video.zuilaidian_teach_video_number}")
    private String ZUILAIDIAN_TEACHVIDEONUMBER;

    /**
     * 动态壁纸引导视频
     */
    @Value("${video.dongtaibizhi_teach_video_number}")
    private String DONGTAIBIZHI_TEACHVIDEONUMBER;

    /**
     * 爱来电引导视频
     */
    @Value("${video.ailaidian_teach_video_number}")
    private String AILAIDIAN_TEACHVIDEONUMBER;

    /**
     * 铃声多多引导视频
     */
    @Value("${video.lingshengduoduo_teach_video_number}")
    private String LINGSHENGDUODUO_TEACHVIDEONUMBER;

    /**
     * @param videoPageVO
     * @return
     */
    @Override
    public PageResult<VideoWebDTO> pageList(VideoPageVO videoPageVO) {

        Criteria criteria = Criteria.where("releaseTime").gte(videoPageVO.getStartTime().getTime()).lte(videoPageVO.getEndTime().getTime());
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

        Page<VideoWebDTO> videoDTOPage = MongodbRepository.findByPage(new Query(criteria), PageRequest.of(videoPageVO.getPageIndex() - 1, videoPageVO.getPageSize(), sort), VideoWebDTO.class, BusinessConstants.VIDEO_COLLECTION_NAME);
        List<VideoWebDTO> videoWebDTOS = videoDTOPage.getContent();
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
                for (String ss : videoWebDTO.getVideoTypeTags()){
                    sb.append(VideoTypeEnum.resolve(Integer.valueOf(ss)).getDesc()).append(",");
                }
            }
            videoWebDTO.setVideoTypeTagsStr(sb.toString());
        });

        PageResult<VideoWebDTO> videoPageResult = new PageResult<>();
        videoPageResult.setTotal((int) videoDTOPage.getTotalElements());
        videoPageResult.setTotalPages(videoDTOPage.getTotalPages());
        videoPageResult.setRows(videoWebDTOS);
        if (StringUtils.isNotBlank(sortField) && !sortField.equals("title")) {
            // 根据设置率排序
            List<VideoWebDTO> videoWebDTOSorttedList = sortSettingRate(videoWebDTOS, sortField);
            videoPageResult.setRows(videoWebDTOSorttedList);
        }
        return videoPageResult;
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
     * 计算设置率
     *
     * @param settingNum
     * @param playNum
     * @return
     */
    @Override
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
     * 修改视频数据
     *
     * @param videoWebDTO
     */
    @Override
    public void update(VideoWebDTO videoWebDTO) {

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

    @Override
    public void delete(String videoNumber) {
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
     * @param ids
     */
    @Override
    public void batchDelete(String[] ids) {
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

    @Override
    public void batchUpdateType(String[] ids, String categoryNumber) {
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

    @Override
    public void batchUpdateTag(String[] ids, List<String> tags) {
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
     * 添加视频到视频池
     *
     * @param videoNumber
     */
    @Override
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
     * 查看新手教学视频
     *
     * @param teachingVideoPageVO
     * @return
     */
    @Override
    public PageResult<VideoWebDTO> getTeachingVideo(TeachingVideoPageVO teachingVideoPageVO) {
        Criteria criteria = new Criteria();
        if (teachingVideoPageVO.getAppName().equals(AppNameEnum.ZUILAIDIAN.getValue())) {
            // 查询固定的视频信息
            criteria = Criteria.where("videoNumber").is(ZUILAIDIAN_TEACHVIDEONUMBER);
        } else if (teachingVideoPageVO.getAppName().equals(AppNameEnum.DONGTAIBIZHI.getValue())) {
            criteria = Criteria.where("videoNumber").is(DONGTAIBIZHI_TEACHVIDEONUMBER);
        } else if (teachingVideoPageVO.getAppName().equals(AppNameEnum.AILAIDIAN.getValue())) {
            criteria = Criteria.where("videoNumber").is(AILAIDIAN_TEACHVIDEONUMBER);
        } else if (teachingVideoPageVO.getAppName().equals(AppNameEnum.LINGSHENGDUODUO.getValue())) {
            criteria = Criteria.where("videoNumber").is(LINGSHENGDUODUO_TEACHVIDEONUMBER);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "weight", "createTime");
        Page<VideoWebDTO> videoDTOPage = MongodbRepository.findByPage(new Query(criteria), PageRequest.of(teachingVideoPageVO.getPageIndex() - 1,
                teachingVideoPageVO.getPageSize(), sort), VideoWebDTO.class, BusinessConstants.VIDEO_COLLECTION_NAME);

        List<VideoWebDTO> videoWebDTOS = videoDTOPage.getContent();
        videoWebDTOS.forEach(videoDTO -> {
            String cdn = videoCDNProperties.getCdn().get(0);
            videoDTO.setVideoCover(cdn + videoDTO.getVideoCover());
            videoDTO.setVideoAddress(cdn + videoDTO.getVideoAddress());
            videoDTO.setGifAddress(cdn + videoDTO.getGifAddress() + ossSuffix);
            videoDTO.setAudioAddress(cdn + videoDTO.getAudioAddress());
        });
        PageResult<VideoWebDTO> videoPageResult = new PageResult<>();
        videoPageResult.setTotal((int) videoDTOPage.getTotalElements());
        videoPageResult.setTotalPages(videoDTOPage.getTotalPages());
        videoPageResult.setRows(videoWebDTOS);
        return videoPageResult;
    }

    /**修改新手教学视频
     *
     * @return
     */
    @Override
    public void updateTeachingVideo(UpdateTeachingVideoVO updateTeachingVideoVO) {
        // 更新固定视频
        if (updateTeachingVideoVO.getAppName().equals(AppNameEnum.ZUILAIDIAN.getValue())) {
            updateTeachingVideoVO.setVideoNumber(ZUILAIDIAN_TEACHVIDEONUMBER);
        } else if (updateTeachingVideoVO.getAppName().equals(AppNameEnum.DONGTAIBIZHI.getValue())) {
            updateTeachingVideoVO.setVideoNumber(DONGTAIBIZHI_TEACHVIDEONUMBER);
        } else if (updateTeachingVideoVO.getAppName().equals(AppNameEnum.AILAIDIAN.getValue())) {
            updateTeachingVideoVO.setVideoNumber(AILAIDIAN_TEACHVIDEONUMBER);
        } else if (updateTeachingVideoVO.getAppName().equals(AppNameEnum.LINGSHENGDUODUO.getValue())) {
            updateTeachingVideoVO.setVideoNumber(LINGSHENGDUODUO_TEACHVIDEONUMBER);
        }

        Update update = new Update();
        update.set("title", updateTeachingVideoVO.getTitle());
        update.set("videoCover", updateTeachingVideoVO.getVideoCover().substring(updateTeachingVideoVO.getVideoCover().indexOf("zuilaidian"), updateTeachingVideoVO.getVideoCover().length()));
        String gif = updateTeachingVideoVO.getGifAddress().substring(updateTeachingVideoVO.getGifAddress().indexOf("zuilaidian"), updateTeachingVideoVO.getGifAddress().length());
        if(gif.contains("?")) {
            update.set("gifAddress", gif.substring(0, gif.indexOf("?")));
        } else {
            update.set("gifAddress", gif);
        }
        update.set("videoAddress", updateTeachingVideoVO.getVideoAddress().substring(updateTeachingVideoVO.getVideoAddress().indexOf("zuilaidian"), updateTeachingVideoVO.getVideoAddress().length()));
        update.set("audioAddress", updateTeachingVideoVO.getAudioAddress().substring(updateTeachingVideoVO.getAudioAddress().indexOf("zuilaidian"), updateTeachingVideoVO.getAudioAddress().length()));
        update.set("updateTime", System.currentTimeMillis());

        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(updateTeachingVideoVO.getVideoNumber())), update, Video.class);
        if (updateResult.getModifiedCount() < 1) {
            throw new RuntimeException(String.format("修改视频数据失败，影响行数={}", updateResult.getModifiedCount()));
        }
    }

    /**
     * 获取音视频cdn url
     *
     * @return
     */
    @Override
    public String getVideoAudioApolloConfig() {
        return videoCDNProperties.getCdn().get(0);
    }

    /**
     * 查询设置率排行
     *
     * @param videoSettingRatePageBO
     * @return
     */
    @Override
    public PageResult<VideoSettingRatesVO> pageList(VideoSettingRatePageBO videoSettingRatePageBO) {
        PageResult<VideoSettingRatesVO> pageList = pageRepository.selectPaging(VideSettingRatesMapper.class, "pageList", videoSettingRatePageBO);
        List<VideoSettingRatesVO> rows = pageList.getRows();
        for (VideoSettingRatesVO vs : rows){
            vs.setCallShowSettingRate(vs.getCallShowSettingRate().multiply(new BigDecimal("100")));
            vs.setWallpaperSettingRate(vs.getWallpaperSettingRate().multiply(new BigDecimal("100")));
            vs.setRingtoneSettingRate(vs.getRingtoneSettingRate().multiply(new BigDecimal("100")));
        }
        pageList.setRows(rows);
        return pageList;
    }


}
