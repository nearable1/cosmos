package com.xiaoniu.call.video.core.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.dto.VideoPyxWebDTO;
import com.xiaoniu.call.video.api.vo.VideoPyxPageVO;
import com.xiaoniu.call.video.core.autoconfigure.VideoCDNProperties;
import com.xiaoniu.call.video.core.constants.BusinessConstants;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.entity.Video_pyx;
import com.xiaoniu.call.video.core.service.VideoPyxWebService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
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
import java.util.List;
import java.util.Objects;

/**
 * 视频管理端业务实现
 *
 * @author wuwen
 * @date 2019-07-15 21:16
 */
@Service
@Log4j2
public class VideoPyxWebServiceImpl implements VideoPyxWebService {

    @Autowired
    private VideoCDNProperties videoCDNProperties;

    /**
     * @param videoPageVO
     * @return
     */
    @Override
    public PageResult<VideoPyxWebDTO> pageList(VideoPyxPageVO videoPageVO) {

        Criteria criteria = Criteria.where("releaseTime").gte(videoPageVO.getStartTime().getTime()).lte(videoPageVO.getEndTime().getTime());
        String videoNumber = videoPageVO.getVideoNumber();
        String title = videoPageVO.getTitle();
        Integer videoType = videoPageVO.getVideoType();
        Integer videoSource = videoPageVO.getVideoSource();
        Boolean status = videoPageVO.getStatus();
        String sortField = videoPageVO.getSortField();
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
        if (null != status) {
            criteria.and("status").is(status);
        }
        Sort sort = null;
        if (StringUtils.isNotEmpty(sortField)) {
            sort = Sort.by(Sort.Direction.DESC, sortField);
        } else {
            sort = Sort.by(Sort.Direction.DESC, "weight", "createTime");
        }

        Page<VideoPyxWebDTO> videoDTOPage = MongodbRepository.findByPage(new Query(criteria), PageRequest.of(videoPageVO.getPageIndex() - 1, videoPageVO.getPageSize(), sort), VideoPyxWebDTO.class, BusinessConstants.VIDEO_PYX_COLLECTION_NAME);
        List<VideoPyxWebDTO> videoWebDTOS = videoDTOPage.getContent();
        videoWebDTOS.forEach(videoWebDTO -> {
            String cdn = videoCDNProperties.getCdn().get(0);
            videoWebDTO.setVideoCover(cdn + videoWebDTO.getVideoCover());
            videoWebDTO.setVideoAddress(cdn + videoWebDTO.getVideoAddress());
        });

        PageResult<VideoPyxWebDTO> videoPageResult = new PageResult<>();
        videoPageResult.setTotal((int) videoDTOPage.getTotalElements());
        videoPageResult.setTotalPages(videoDTOPage.getTotalPages());
        videoPageResult.setRows(videoWebDTOS);
        return videoPageResult;
    }


    /**
     * 修改视频数据
     *
     * @param videoWebDTO
     */
    @Override
    public void update(VideoPyxWebDTO videoWebDTO) {

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
            UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoWebDTO.getVideoNumber())), update, Video_pyx.class);
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
                Video_pyx video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoWebDTO.getVideoNumber())), Video_pyx.class);
                // 删除首页视频池
                delVideoPool(videoWebDTO.getVideoNumber(), video);
            }
        } else {
            // 编辑视频数据
            Video_pyx oldVideo = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoWebDTO.getVideoNumber())), Video_pyx.class);
            UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoWebDTO.getVideoNumber())), update, Video_pyx.class);
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

            UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(id)), update, Video_pyx.class);
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
            Video_pyx video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(id)), Video_pyx.class);
            UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(id)), update, Video_pyx.class);
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
        Video_pyx video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), Video_pyx.class);
        MongodbRepository.delete(new Query(Criteria.where("videoNumber").is(videoNumber)), Video_pyx.class);
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
            Video_pyx video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(id)), Video_pyx.class);
            MongodbRepository.delete(new Query(Criteria.where("videoNumber").is(id)), Video_pyx.class);
            // 删除首页视频池
            delVideoPool(id, video);
        }
    }

    /**
     * 同步来电、壁纸、屏保推荐池和大池
     *
     * @param videoWebDTO
     */
    public void syncVideoPool(Video_pyx oldVideo, VideoPyxWebDTO videoWebDTO) {

        // 分类总池删除
        if (!Objects.equals(oldVideo.getVideoType(), videoWebDTO.getVideoType())) {
            RedisRepository.srem(String.format(RedisConstants.VIDEO_PYX_POOL, oldVideo.getVideoType()), oldVideo.getVideoNumber());
        }

        // 分类总池添加
        RedisRepository.sadd(String.format(RedisConstants.VIDEO_PYX_POOL, videoWebDTO.getVideoType()), videoWebDTO.getVideoNumber());
    }

    /**
     * 删除首页视频池
     *
     * @param videoNumber
     */
    public void delVideoPool(String videoNumber, Video_pyx video) {
        if (video != null) {
            // 分类总池删除
            RedisRepository.srem(String.format(RedisConstants.VIDEO_PYX_POOL, video.getVideoType()), videoNumber);
        }
    }


    /**
     * 添加视频到视频池
     *
     * @param videoNumber
     */
    @Override
    public void addVideoPool(String videoNumber) {
        Video_pyx video = MongodbRepository.findByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), Video_pyx.class);
        if (video != null) {
            // 分类总池添加
            RedisRepository.sadd(String.format(RedisConstants.VIDEO_PYX_POOL, video.getVideoType()), videoNumber);
        }
    }


}
