package com.xiaoniu.call.video.core.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.call.video.api.dto.VideoPyxWebDTO;
import com.xiaoniu.call.video.api.vo.VideoPyxPageVO;
import com.xiaoniu.call.video.core.autoconfigure.VideoCDNProperties;
import com.xiaoniu.call.video.core.constants.BusinessConstants;
import com.xiaoniu.call.video.core.entity.Video_pyx;
import com.xiaoniu.call.video.core.entity.Video_pyx_review;
import com.xiaoniu.call.video.core.service.VideoPyxReviewWebService;
import com.xiaoniu.call.video.core.service.VideoPyxWebService;
import lombok.extern.log4j.Log4j2;
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
import java.util.List;

/**
 * 视频管理端业务实现
 *
 * @author wuwen
 * @date 2019-07-15 21:16
 */
@Service
@Log4j2
public class VideoPyxReviewWebServiceImpl implements VideoPyxReviewWebService {

    @Autowired
    private VideoCDNProperties videoCDNProperties;

    @Autowired
    private VideoPyxWebService videoWebService;

    /**
     * @param videoPageVO
     * @return
     */
    @Override
    public PageResult<VideoPyxWebDTO> pageList(VideoPyxPageVO videoPageVO) {

        Criteria criteria = Criteria.where("createTime").gte(videoPageVO.getStartTime().getTime())
                .lte(videoPageVO.getEndTime().getTime());
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

        Page<VideoPyxWebDTO> videoDTOPage = MongodbRepository.findByPage(new Query(criteria),
                PageRequest.of(videoPageVO.getPageIndex() - 1, videoPageVO.getPageSize(), sort), VideoPyxWebDTO.class,
                BusinessConstants.VIDEO_PYX_REVIEW_COLLECTION_NAME);
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

            if (!"videoNumber".equals(name) && !"serialVersionUID".equals(name) && StringUtils.isNotEmpty(name)
                    && null != value) {
                update.set(name, value);
            }
        }

        update.set("updateTime", System.currentTimeMillis());
        UpdateResult updateResult = MongodbRepository.updateByClazz(
                new Query(Criteria.where("videoNumber").is(videoWebDTO.getVideoNumber())), update, Video_pyx_review.class);
        if (updateResult.getModifiedCount() < 1) {
            throw new RuntimeException(String.format("修改视频数据失败，影响行数={}", updateResult.getModifiedCount()));
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
        for (String id : ids) {
            Video_pyx video = MongodbRepository.findByName(new Query(Criteria.where("videoNumber").is(id)), Video_pyx.class,
                    BusinessConstants.VIDEO_PYX_REVIEW_COLLECTION_NAME);
            try {
                MongodbRepository.save(video);
                // 上线加入池
                videoWebService.addVideoPool(id);
            } catch (Exception e) {
                log.error("视频审核上线异常，id:{}，error:{}", id, e);
            }

            MongodbRepository.delete(new Query(Criteria.where("videoNumber").is(id)), Video_pyx_review.class);
        }
    }

    @Override
    public void delete(String videoNumber) {
        if (StringUtils.isEmpty(videoNumber)) {
            throw new RuntimeException("删除审核数据不能为空");
        }
        MongodbRepository.delete(new Query(Criteria.where("videoNumber").is(videoNumber)), Video_pyx_review.class);
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
            MongodbRepository.delete(new Query(Criteria.where("videoNumber").is(id)), Video_pyx_review.class);
        }
    }


}
