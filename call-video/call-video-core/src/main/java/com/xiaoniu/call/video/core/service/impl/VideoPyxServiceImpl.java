package com.xiaoniu.call.video.core.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.enums.VideoBusinessEnum;
import com.xiaoniu.call.video.core.autoconfigure.VideoCDNProperties;
import com.xiaoniu.call.video.core.constants.BusinessConstants;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.dto.VideoPyxDTO;
import com.xiaoniu.call.video.core.entity.VideoPyxCollection;
import com.xiaoniu.call.video.core.entity.Video_pyx;
import com.xiaoniu.call.video.core.mapper.VideoPyxCollectionMapper;
import com.xiaoniu.call.video.core.service.VideoPyxService;
import com.xiaoniu.call.video.core.vo.VideoPyxVO;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 视频业务
 *
 * @author wuwen
 * @date 2019-07-02 17:00
 */
@Service
@Log4j2
public class VideoPyxServiceImpl implements VideoPyxService {

    @Autowired
    private VideoPyxCollectionMapper videoCollectionMapper;

    @Autowired
    private VideoCDNProperties videoCDNProperties;

    @Autowired
    private PageRepository pageRepository;

    @Value("${video.oss.suffix:?x-oss-process=image/format,webp/quality,Q_100}")
    private String ossSuffix;


    /**
     * 根据分类编号查询最新视频
     *
     * @param videoVO
     * @return
     */
    @Override
    public PageResult<VideoPyxDTO> homePopularVideo(VideoPyxVO videoVO) {
        String classifiedVideoPoolKey = null;
        String classifiedDiffVideoPoolKey = null;
        Set<String> randomVideoNumbers = null;
        String videoType = StringUtils.isEmpty(videoVO.getVideoType()) ? "1" : videoVO.getVideoType();
        String deviceId = HeaderHelper.getDeviceId();
        try {
            PageRequest pageRequest = PageRequest.of(0, videoVO.getPageSize(), Sort.by(Sort.Direction.DESC, "weight", "createTime"));
            // 总池
            String videoPoolKey = String.format(RedisConstants.VIDEO_PYX_POOL, videoType);
            // 用户观看池
            classifiedVideoPoolKey = String.format(RedisConstants.USER_PYX_VIDEO, videoType, deviceId);
            // 用户视频观看差集池
            classifiedDiffVideoPoolKey = String.format(RedisConstants.USER_PYX_VIDEO_DIFF, videoType, deviceId);

            // 将用户观看池与大池做差集，存入用户视频差集，从差集池获取随机10条视频数据
            RedisRepository.sdiffStore(videoPoolKey, classifiedDiffVideoPoolKey, classifiedVideoPoolKey);
            randomVideoNumbers = RedisRepository.srandomMembers(classifiedDiffVideoPoolKey, videoVO.getPageSize());
            int randomNumber = videoVO.getPageSize();
            if (CollectionUtils.isNotEmpty(randomVideoNumbers)) {
                randomNumber = videoVO.getPageSize() - randomVideoNumbers.size();
            }

            // 视频不足从大池获取视频
            Set<String> allVideoClassifiedPool = randomNumber > 0 ? RedisRepository.srandomMembers(videoPoolKey, randomNumber) : new HashSet<>();
            allVideoClassifiedPool.addAll(randomVideoNumbers);
            PageResult<VideoPyxDTO> result = pageSelectVideo(Query.query(Criteria.where("videoNumber").in(allVideoClassifiedPool).and("status").is(true)), pageRequest);

            // 返回视频不足，从大池补充数据
            if (result.getTotal() != videoVO.getPageSize()) {
                randomNumber = videoVO.getPageSize() - result.getTotal();
                Set<String> addHomeVideoPool = RedisRepository.srandomMembers(videoPoolKey, randomNumber);
                allVideoClassifiedPool.addAll(addHomeVideoPool);
                return pageSelectVideo(Query.query(Criteria.where("videoNumber").in(allVideoClassifiedPool).and("status").is(true)), pageRequest);
            }
            return result;
        } finally {
            if (CollectionUtils.isNotEmpty(randomVideoNumbers)) {
                try {
                    // 删除差集池
                    RedisRepository.del(classifiedDiffVideoPoolKey);

                    // 已展示视频存入用户视频观看池
                    if (RedisRepository.exists(classifiedVideoPoolKey)) {
                        RedisRepository.sadd(classifiedVideoPoolKey, randomVideoNumbers.toArray(new String[]{}));
                    } else {
                        RedisRepository.sadd(classifiedVideoPoolKey, RedisConstants.USER_VIDEO_POOL_EXPIRED, randomVideoNumbers.toArray(new String[]{}));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("[皮一下查询最新视频]添加用户={}已观看视频异常，异常信息={}", deviceId, e.getMessage());
                }
            }
        }
    }


    /**
     * 视频详情接口
     *
     * @param videoNumber
     * @return
     */
    @Override
    public VideoPyxDTO detail(String videoNumber) {
        VideoPyxDTO videoDTO = MongodbRepository.findByName(new Query(Criteria.where("videoNumber").is(videoNumber)), VideoPyxDTO.class, BusinessConstants.VIDEO_PYX_COLLECTION_NAME);
        if (null == videoDTO) {
            throw new BusinessException(VideoBusinessEnum.VIDEO_DOES_NOT_EXIST);
        }
        int result = videoCollectionMapper.selectCountByDeviceIdAndVideoNumber(HeaderHelper.getDeviceId(), videoDTO.getVideoNumber());
        videoDTO.setLikeState(result > 0);

        String cdn = videoCDNProperties.getCdn().get(0);
        videoDTO.setVideoCover(cdn + videoDTO.getVideoCover());
        videoDTO.setVideoAddress(cdn + videoDTO.getVideoAddress());
        // 观看，收藏，转发=随机数加上真实人数
        videoDTO.setView(videoDTO.getView() + (videoDTO.getViewReal() != null ? videoDTO.getViewReal() : 0));
        videoDTO.setCollectionNumber(videoDTO.getCollectionNumber() + (videoDTO.getCollectionNumberReal() != null
                ? videoDTO.getCollectionNumberReal() : 0));
        videoDTO.setForwardNumber(videoDTO.getForwardNumber() + (videoDTO.getForwardNumberReal() != null
                ? videoDTO.getForwardNumberReal() : 0));
        return videoDTO;
    }


    /**
     * 分页查询数据
     *
     * @param query
     * @param pageRequest
     * @return
     */
    private PageResult<VideoPyxDTO> pageSelectVideo(Query query, PageRequest pageRequest) {
        PageRequest newPageRequest = pageRequest;
        if (pageRequest.getPageNumber() > 0) {
            newPageRequest = PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), pageRequest.getSort());
        }
        Page<VideoPyxDTO> videoPage = MongodbRepository.findByPage(query, newPageRequest, VideoPyxDTO.class, BusinessConstants.VIDEO_PYX_COLLECTION_NAME);
        List<VideoPyxDTO> videoDTOS = videoPage.getContent();
        String deviceId = HeaderHelper.getDeviceId();
        videoDTOS.forEach(videoDTO -> {
            int result = videoCollectionMapper.selectCountByDeviceIdAndVideoNumber(deviceId, videoDTO.getVideoNumber());
            videoDTO.setLikeState(result > 0);

            String cdn = videoCDNProperties.getCdn().get(0);
            videoDTO.setVideoCover(cdn + videoDTO.getVideoCover());
            videoDTO.setVideoAddress(cdn + videoDTO.getVideoAddress());
            // 观看，收藏，转发=随机数加上真实人数
            videoDTO.setView(videoDTO.getView() + (videoDTO.getViewReal() != null ? videoDTO.getViewReal() : 0));
            videoDTO.setCollectionNumber(videoDTO.getCollectionNumber() + (videoDTO.getCollectionNumberReal() != null
                    ? videoDTO.getCollectionNumberReal() : 0));
            videoDTO.setForwardNumber(videoDTO.getForwardNumber() + (videoDTO.getForwardNumberReal() != null
                    ? videoDTO.getForwardNumberReal() : 0));
        });

        PageResult<VideoPyxDTO> videoPageResult = new PageResult<>();
        videoPageResult.setTotal((int) videoPage.getTotalElements());
        videoPageResult.setTotalPages(videoPage.getTotalPages());
        videoPageResult.setRows(videoDTOS);
        return videoPageResult;
    }

    /**
     * 视频喜欢
     *
     * @param deviceId
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void like(String deviceId, String videoNumber) {
        int rows = videoCollectionMapper.selectCountByDeviceIdAndVideoNumber(deviceId, videoNumber);
        if (rows > 0) {
            throw new BusinessException(VideoBusinessEnum.ALREADY_LIKE_VIDEO);
        }

        VideoPyxCollection videoCollection = new VideoPyxCollection();
        videoCollection.setDeviceId(deviceId);
        videoCollection.setVideoNumber(videoNumber);
        videoCollectionMapper.insertSelective(videoCollection);

        Query query = new Query(Criteria.where("videoNumber").is(videoNumber));
        Update update = new Update().inc("collectionNumberReal", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(query, update, Video_pyx.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[皮一下喜欢视频]增加视频={}喜欢数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.LIKE_VIDEO_FAILURE);
        }
    }

    /**
     * 取消视频喜欢
     *
     * @param deviceId
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelLike(String deviceId, String videoNumber) {
        int rows = videoCollectionMapper.selectCountByDeviceIdAndVideoNumber(deviceId, videoNumber);
        if (rows < 1) {
            throw new BusinessException(VideoBusinessEnum.CANCEL_VIDEO_LIKE);
        }

        int modifyCount = videoCollectionMapper.deleteByDeviceIdAndVideoNumber(deviceId, videoNumber);
        if (modifyCount != 1) {
            log.error("[皮一下喜欢视频]刪除deviceId={}喜欢的视频={}失败，数据影响行数={}", videoNumber, videoNumber, modifyCount);
            throw new BusinessException(VideoBusinessEnum.DELETE_LIKE_RECORD_FAILURE);
        }

        Query query = new Query(Criteria.where("videoNumber").is(videoNumber));
        Update update = new Update().inc("collectionNumberReal", -1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(query, update, Video_pyx.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[皮一下喜欢视频]减少视频={}喜欢数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.VIDEO_LIKE_NUMBER_REDUCTION_FAILURE);
        }
    }

    /**
     * 视频分享
     *
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void share(String videoNumber) {
        Update update = new Update().inc("forwardNumberReal", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, Video_pyx.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[皮一下分享视频]增加视频={}分享数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.VIDEO_SHARING_FAILED);
        }
    }

    /**
     * 视频播放
     *
     * @param videoNumber
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void broadcast(String videoNumber) {
        Update update = new Update().inc("viewReal", 1);
        UpdateResult updateResult = MongodbRepository.updateByClazz(new Query(Criteria.where("videoNumber").is(videoNumber)), update, Video_pyx.class);
        if (updateResult.getModifiedCount() < 1) {
            log.error("[皮一下播放视频]增加视频={}播放数失败，数据影响行数={}", videoNumber, updateResult.getModifiedCount());
            throw new BusinessException(VideoBusinessEnum.VIDEO_PLAYBACK_FAILED);
        }
    }
}
