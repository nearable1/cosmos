package com.xiaoniu.call.video.core.service.impl;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.enums.VideoBusinessEnum;
import com.xiaoniu.call.video.api.enums.VideoTypeEnum;
import com.xiaoniu.call.video.core.autoconfigure.VideoCDNProperties;
import com.xiaoniu.call.video.core.constants.BusinessConstants;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.dto.VideoClassificationDTO;
import com.xiaoniu.call.video.core.dto.VideoDTO;
import com.xiaoniu.call.video.core.mapper.VideoClassificationMapper;
import com.xiaoniu.call.video.core.mapper.VideoCollectionMapper;
import com.xiaoniu.call.video.core.service.VideoService;
import com.xiaoniu.call.video.core.vo.VideoLikeVO;
import com.xiaoniu.call.video.core.vo.VideoVO;

import lombok.extern.log4j.Log4j2;

/**
 * 视频业务
 *
 * @author wuwen
 * @date 2019-07-02 17:00
 */
@Service
@Log4j2
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoClassificationMapper videoClassificationMapper;

    @Autowired
    private VideoCollectionMapper videoCollectionMapper;

    @Autowired
    private VideoCDNProperties videoCDNProperties;

    @Autowired
    private PageRepository pageRepository;

    @Value("${video.oss.suffix:?x-oss-process=image/format,webp/quality,Q_100}")
    private String ossSuffix;

    /**
     * 首页热门视频
     *
     * @param videoVO
     * @return
     */
    @Override
    public PageResult<VideoDTO> homePopularVideo(VideoVO videoVO) {
        if (Objects.equals(HeaderHelper.getAppName(), 1) && BusinessConstants.INDEX_V1_VERSION.contains(HeaderHelper.getAppVersion())) {
            return homeVideo(videoVO, Sort.by(Sort.Direction.DESC, "weight", "createTime"));
        } else {
            return indexVideo(videoVO);
        }
    }

    /**
     * 首页最新视频
     *
     * @param videoVO
     * @return
     */
    @Override
    public PageResult<VideoDTO> homeLatestVideo(VideoVO videoVO) {
        return homeVideo(videoVO, Sort.by(Sort.Direction.DESC, "releaseTime"));
    }

    /**
     * 首页视频查询逻辑
     *
     * @param videoVO
     * @param sort
     * @return
     */
    private PageResult<VideoDTO> homeVideo(VideoVO videoVO, Sort sort) {
        String userVideoHomeKey = null;
        String userVideoHomeAddKey = null;
        Set<String> randomUserVideo = null;
        String deviceId = HeaderHelper.getDeviceId();
        try {
            //判断用户是否有自己的首页池子数据
            userVideoHomeKey = String.format(RedisConstants.USER_VIDEO_HOME, deviceId);
            userVideoHomeAddKey = String.format(RedisConstants.USER_VIDEO_HOME_ADD, deviceId);
            if (!RedisRepository.exists(userVideoHomeKey) && StringUtils.isEmpty(RedisRepository.get(userVideoHomeAddKey))) {
                Set<String> homeVideoPool = RedisRepository.smembers(RedisConstants.VIDEO_POOL_OPERATION_HOME);
                if (CollectionUtils.isNotEmpty(homeVideoPool)) {
                    RedisRepository.sadd(userVideoHomeKey, RedisConstants.USER_VIDEO_POOL_EXPIRED, homeVideoPool.toArray(new String[]{}));
                    // 设置用户池添加标记，三天内不再给这个用户加推荐池
                    RedisRepository.set(userVideoHomeAddKey, "1", RedisConstants.USER_VIDEO_POOL_EXPIRED);
                }
            }

            //从自身首页池子随机获取全部数据，未满20条从大池拿
            randomUserVideo = RedisRepository.srandomMembers(userVideoHomeKey, videoVO.getPageSize());
            int randomNumber = videoVO.getPageSize();
            if (CollectionUtils.isNotEmpty(randomUserVideo)) {
                randomNumber = videoVO.getPageSize() - randomUserVideo.size();
            }

            //从首页所有视频池随机获取视频数量
            Set<String> allHomeVideoPool = RedisRepository.srandomMembers(RedisConstants.VIDEO_POOL_HOME, randomNumber);
            allHomeVideoPool.addAll(randomUserVideo);
            return pageSelectVideo(videoVO.getWebp(), Query.query(Criteria.where("videoNumber").in(allHomeVideoPool).and("status").is(true)), PageRequest.of(0, videoVO.getPageSize(), sort));
        } finally {
            if (CollectionUtils.isNotEmpty(randomUserVideo)) {
                try {
                    RedisRepository.srem(userVideoHomeKey, randomUserVideo.toArray(new String[]{}));
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("[首页视频]删除用户={}已观看视频异常，异常信息={}", deviceId, e.getMessage());
                }
            }
        }
    }

    /**
     * 首页分类列表
     *
     * @return
     */
    @Override
    public List<VideoClassificationDTO> homeCategoryList() {

        String value = RedisRepository.get(RedisConstants.VIDEO_CLASSIFICATION);
        if (StringUtils.isNotBlank(value)) {
            return JSONUtils.parseObject(value, new TypeReference<List<VideoClassificationDTO>>() {
            });
        }

        List<VideoClassificationDTO> videoClassificationDTOS = videoClassificationMapper.selectValidList();
        if (CollectionUtils.isNotEmpty(videoClassificationDTOS)) {
            RedisRepository.set(RedisConstants.VIDEO_CLASSIFICATION, JSONUtils.toJSONString(videoClassificationDTOS));
        }
        return videoClassificationDTOS;
    }

    /**
     * 探索最新视频
     *
     * @param videoVO
     * @return
     */
    @Override
    public PageResult<VideoDTO> exploreLatestVideo(VideoVO videoVO) {
        Criteria criteria = Criteria.where("videoTypeTags").is(VideoTypeEnum.EXPLORATION_VIDEO.getValue()).and("status").is(true);
        Set<String> exploringPlay = RedisRepository.smembers(String.format(RedisConstants.EXPLORING_PLAY, HeaderHelper.getDeviceId()));
        if (CollectionUtils.isNotEmpty(exploringPlay)) {
            criteria.and("videoNumber").nin(exploringPlay);
        }

        return pageSelectVideo(videoVO.getWebp(), Query.query(criteria),
                PageRequest.of(videoVO.getPageIndex(), videoVO.getPageSize(), Sort.by(Sort.Direction.DESC, "weight", "createTime")));
    }

    /**
     * 小视频列表
     *
     * @param videoVO
     * @return
     */
    @Override
    public PageResult<VideoDTO> smallVideoList(VideoVO videoVO) {
        return pageSelectVideo(videoVO.getWebp(), Query.query(Criteria.where("videoNumber").in(RedisRepository.srandomMembers(RedisConstants.VIDEO_POOL_SMALL, videoVO.getPageSize())).and("status").is(true)),
                PageRequest.of(0, videoVO.getPageSize(), Sort.by(Sort.Direction.DESC, "weight", "createTime")));
    }

    /**
     * 根据分类编号查询最新视频
     *
     * @param videoVO
     * @return
     */
    @Override
    public PageResult<VideoDTO> selectLatestVideoListByCategoryNumber(VideoVO videoVO) {
        String classifiedVideoPoolKey = null;
        String classifiedDiffVideoPoolKey = null;
        Set<String> randomVideoNumbers = null;
        String categoryNumber = videoVO.getCategoryNumber();
        String deviceId = HeaderHelper.getDeviceId();
        try {
            // 总池
            String videoPoolKey = String.format(RedisConstants.VIDEO_POOL_CLASSIFIED, categoryNumber);
            // 用户观看池
            classifiedVideoPoolKey = String.format(RedisConstants.USER_VIDEO_NEW_CLASSIFIED, categoryNumber, deviceId);
            // 用户视频观看差集池
            classifiedDiffVideoPoolKey = String.format(RedisConstants.USER_VIDEO_DIFF_CLASSIFIED, categoryNumber, deviceId);

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
            return pageSelectVideo(videoVO.getWebp(), Query.query(Criteria.where("videoNumber").in(allVideoClassifiedPool).and("status").is(true)), PageRequest.of(0, videoVO.getPageSize(), Sort.by(Sort.Direction.DESC, "weight", "createTime")));
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
                    log.error("[根据分类编号查询最新视频]添加用户={}已观看视频异常，异常信息={}", deviceId, e.getMessage());
                }
            }
        }
    }

    /**
     * 根据标签查询视频
     *
     * @param tags
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<VideoDTO> selectListByTags(String tags, int pageIndex, int pageSize) {
        return pageSelectVideo(false, Query.query(Criteria.where("tags").is(tags).and("status").is(true).and("videoTypeTags").is("1")),
                PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "weight", "createTime")));
    }

    /**
     * 根据标签查询视频
     *
     * @param tags
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<VideoDTO> selectListByTags(String tags, int pageIndex, int pageSize, Boolean webp) {
        return pageSelectVideo(webp, Query.query(Criteria.where("tags").is(tags).and("status").is(true).and("videoTypeTags").is("1")),
                PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "weight", "createTime")));
    }

    /**
     * 视频详情接口
     *
     * @param videoNumber
     * @return
     */
    @Override
    public VideoDTO detail(String videoNumber) {
        VideoDTO videoDTO = MongodbRepository.findByName(new Query(Criteria.where("videoNumber").is(videoNumber)), VideoDTO.class, BusinessConstants.VIDEO_COLLECTION_NAME);
        if (null == videoDTO) {
            throw new BusinessException(VideoBusinessEnum.VIDEO_DOES_NOT_EXIST);
        }
        int result = videoCollectionMapper.selectCountByDeviceIdAndVideoNumber(HeaderHelper.getDeviceId(), videoDTO.getVideoNumber());
        videoDTO.setLikeState(result > 0);

        String cdn = videoCDNProperties.getCdn().get(0);
        videoDTO.setVideoCover(cdn + videoDTO.getVideoCover());
        videoDTO.setVideoAddress(cdn + videoDTO.getVideoAddress());
        videoDTO.setGifAddress(cdn + videoDTO.getGifAddress());
        videoDTO.setAudioAddress(cdn + videoDTO.getAudioAddress());
        // 观看，收藏，转发=随机数加上真实人数
        videoDTO.setView(videoDTO.getView() + (videoDTO.getViewReal() != null ? videoDTO.getViewReal() : 0));
        videoDTO.setCollectionNumber(videoDTO.getCollectionNumber() + (videoDTO.getCollectionNumberReal() != null
                ? videoDTO.getCollectionNumberReal() : 0));
        videoDTO.setForwardNumber(videoDTO.getForwardNumber() + (videoDTO.getForwardNumberReal() != null
                ? videoDTO.getForwardNumberReal() : 0));
        return videoDTO;
    }


    /**
     * 我喜欢的视频
     *
     * @param videoLikeVO
     * @return
     */
    @Override
    public PageResult<VideoDTO> favorite(VideoLikeVO videoLikeVO) {
        videoLikeVO.setDeviceId(HeaderHelper.getDeviceId());

        PageResult<VideoDTO> videoDTOPageResult = new PageResult<>();
        PageResult<String> pageResult = pageRepository.selectPaging(VideoCollectionMapper.class, "selectListByDeviceIdByPage", videoLikeVO);

        videoDTOPageResult.setPageIndex(pageResult.getPageIndex());
        videoDTOPageResult.setPageSize(pageResult.getPageSize());
        videoDTOPageResult.setCurrentCount(pageResult.getCurrentCount());
        videoDTOPageResult.setTotalPages(pageResult.getTotalPages());
        videoDTOPageResult.setFirst(pageResult.isFirst());
        videoDTOPageResult.setLast(pageResult.isLast());
        List<String> rows = pageResult.getRows();
        if (CollectionUtils.isEmpty(rows)) {
            return videoDTOPageResult;
        }

        List<VideoDTO> videoDTOS = new ArrayList<>();
        rows.forEach(row -> {
            VideoDTO videoDTO = MongodbRepository.findByName(new Query(Criteria.where("videoNumber").is(row)), VideoDTO.class, BusinessConstants.VIDEO_COLLECTION_NAME);
            if (null != videoDTO) {
                String cdn = videoCDNProperties.getCdn().get(0);
                videoDTO.setVideoCover(cdn + videoDTO.getVideoCover());
                videoDTO.setVideoAddress(cdn + videoDTO.getVideoAddress());
                if (Objects.equals(videoLikeVO.getWebp(), Boolean.TRUE)) {
                    videoDTO.setGifAddress(cdn + videoDTO.getGifAddress() + ossSuffix);
                } else {
                    videoDTO.setGifAddress(cdn + videoDTO.getGifAddress());
                }

                videoDTO.setAudioAddress(cdn + videoDTO.getAudioAddress());

                // 观看，收藏，转发=随机数加上真实人数
                videoDTO.setView(videoDTO.getView() + (videoDTO.getViewReal() != null ? videoDTO.getViewReal() : 0));
                videoDTO.setCollectionNumber(videoDTO.getCollectionNumber() + (videoDTO.getCollectionNumberReal() != null
                        ? videoDTO.getCollectionNumberReal() : 0));
                videoDTO.setForwardNumber(videoDTO.getForwardNumber() + (videoDTO.getForwardNumberReal() != null
                        ? videoDTO.getForwardNumberReal() : 0));
                videoDTOS.add(videoDTO);
            }
        });
        videoDTOPageResult.setRows(videoDTOS);
        return videoDTOPageResult;
    }

    /**
     * 我喜欢的视频数量
     *
     * @param deviceId
     * @return
     */
    @Override
    public Integer favoriteNumber(String deviceId) {
        return videoCollectionMapper.selectCountByDeviceId(deviceId);
    }

    /**
     * 首页分业务视频-来电秀、壁纸秀、屏保秀
     *
     * @param videoVO
     * @return
     */
    @Override
    public PageResult<VideoDTO> indexVideo(VideoVO videoVO) {
        String userVideoHomeKey = null;
        String diffVideoHomeKey = null;
        String videoHomeKey = null;
        Set<String> randomUserVideo = null;
        String deviceId = HeaderHelper.getDeviceId();
        try {
            //判断用户是否有自己的首页分业务池子数据
            userVideoHomeKey = String.format(RedisConstants.USER_VIDEO_NEW_CATEGORY_HOME, videoVO.getCategoryNumber(), deviceId);
            diffVideoHomeKey = String.format(RedisConstants.USER_VIDEO_DIFF_CATEGORY_HOME, videoVO.getCategoryNumber(), deviceId);
            videoHomeKey = String.format(RedisConstants.VIDEO_POOL_CATEGORY_HOME, videoVO.getCategoryNumber());
            Sort sort = Sort.by(Sort.Direction.DESC, "weight", "createTime");

            // 将用户视频池与大池做差集，存入用户视频差集，从差集池获取视频数据
            RedisRepository.sdiffStore(videoHomeKey, diffVideoHomeKey, userVideoHomeKey);
            randomUserVideo = RedisRepository.srandomMembers(diffVideoHomeKey, videoVO.getPageSize());
            int randomNumber = videoVO.getPageSize();
            if (CollectionUtils.isNotEmpty(randomUserVideo)) {
                randomNumber = videoVO.getPageSize() - randomUserVideo.size();
            }

            // 若视频不足，从大池池随机获取视频
            Set<String> allHomeVideoPool = randomNumber > 0 ? RedisRepository.srandomMembers(videoHomeKey, randomNumber) : new HashSet<>();
            allHomeVideoPool.addAll(randomUserVideo);
            PageResult<VideoDTO> result = pageSelectVideo(Query.query(Criteria.where("videoNumber").in(allHomeVideoPool).and("status").is(true)
                    .and("videoDbTags").is(videoVO.getCategoryNumber())), PageRequest.of(0, videoVO.getPageSize(), sort));

            // 返回视频不足，从大池补充数据
            if (result.getTotal() != videoVO.getPageSize()) {
                randomNumber = videoVO.getPageSize() - result.getTotal();
                Set<String> addHomeVideoPool = RedisRepository.srandomMembers(videoHomeKey, randomNumber);
                allHomeVideoPool.addAll(addHomeVideoPool);
                return pageSelectVideo(Query.query(Criteria.where("videoNumber").in(allHomeVideoPool).and("status").is(true)
                        .and("videoDbTags").is(videoVO.getCategoryNumber())), PageRequest.of(0, videoVO.getPageSize(), sort));
            }
            return result;
        } finally {
            // 删除差集池
            RedisRepository.del(diffVideoHomeKey);

            if (CollectionUtils.isNotEmpty(randomUserVideo)) {
                try {
                    // 已展示视频存入用户视频观看池
                    if (RedisRepository.exists(userVideoHomeKey)) {
                        RedisRepository.sadd(userVideoHomeKey, randomUserVideo.toArray(new String[]{}));
                    } else {
                        RedisRepository.sadd(userVideoHomeKey, RedisConstants.USER_VIDEO_POOL_EXPIRED, randomUserVideo.toArray(new String[]{}));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("[首页视频]添加用户={}已观看视频异常，异常信息={}", deviceId, e.getMessage());
                }
            }
        }
    }

    /**
     * 分页查询数据
     *
     * @param query
     * @param pageRequest
     * @return
     */
    private PageResult<VideoDTO> pageSelectVideo(Boolean webp, Query query, PageRequest pageRequest) {
        PageRequest newPageRequest = pageRequest;
        if (pageRequest.getPageNumber() > 0) {
            newPageRequest = PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), pageRequest.getSort());
        }
        Page<VideoDTO> videoPage = MongodbRepository.findByPage(query, newPageRequest, VideoDTO.class, BusinessConstants.VIDEO_COLLECTION_NAME);
        List<VideoDTO> videoDTOS = videoPage.getContent();
        String deviceId = HeaderHelper.getDeviceId();
        videoDTOS.forEach(videoDTO -> {
            int result = videoCollectionMapper.selectCountByDeviceIdAndVideoNumber(deviceId, videoDTO.getVideoNumber());
            videoDTO.setLikeState(result > 0);

            String cdn = videoCDNProperties.getCdn().get(0);
            videoDTO.setVideoCover(cdn + videoDTO.getVideoCover());
            videoDTO.setVideoAddress(cdn + videoDTO.getVideoAddress());
            if (Objects.equals(webp, Boolean.TRUE)) {
                videoDTO.setGifAddress(cdn + videoDTO.getGifAddress() + ossSuffix);
            } else {
                videoDTO.setGifAddress(cdn + videoDTO.getGifAddress());
            }

            videoDTO.setAudioAddress(cdn + videoDTO.getAudioAddress());

            // 观看，收藏，转发=随机数加上真实人数
            videoDTO.setView(videoDTO.getView() + (videoDTO.getViewReal() != null ? videoDTO.getViewReal() : 0));
            videoDTO.setCollectionNumber(videoDTO.getCollectionNumber() + (videoDTO.getCollectionNumberReal() != null
                    ? videoDTO.getCollectionNumberReal() : 0));
            videoDTO.setForwardNumber(videoDTO.getForwardNumber() + (videoDTO.getForwardNumberReal() != null
                    ? videoDTO.getForwardNumberReal() : 0));
        });

        PageResult<VideoDTO> videoPageResult = new PageResult<>();
        videoPageResult.setTotal((int) videoPage.getTotalElements());
        videoPageResult.setTotalPages(videoPage.getTotalPages());
        videoPageResult.setRows(videoDTOS);
        return videoPageResult;
    }

    /**
     * 分页查询数据
     *
     * @param query
     * @param pageRequest
     * @return
     */
    private PageResult<VideoDTO> pageSelectVideo(Query query, PageRequest pageRequest) {
        Page<VideoDTO> videoPage = MongodbRepository.findByPage(query, pageRequest, VideoDTO.class, BusinessConstants.VIDEO_COLLECTION_NAME);
        List<VideoDTO> videoDTOS = videoPage.getContent();
        String deviceId = HeaderHelper.getDeviceId();
        videoDTOS.forEach(videoDTO -> {
            int result = videoCollectionMapper.selectCountByDeviceIdAndVideoNumber(deviceId, videoDTO.getVideoNumber());
            videoDTO.setLikeState(result > 0);

            String cdn = videoCDNProperties.getCdn().get(0);
            videoDTO.setVideoCover(cdn + videoDTO.getVideoCover());
            videoDTO.setVideoAddress(cdn + videoDTO.getVideoAddress());
            videoDTO.setGifAddress(cdn + videoDTO.getGifAddress() + ossSuffix);
            videoDTO.setAudioAddress(cdn + videoDTO.getAudioAddress());

            // 观看，收藏，转发=随机数加上真实人数
            videoDTO.setView(videoDTO.getView() + (videoDTO.getViewReal() != null ? videoDTO.getViewReal() : 0));
            videoDTO.setCollectionNumber(videoDTO.getCollectionNumber() + (videoDTO.getCollectionNumberReal() != null
                    ? videoDTO.getCollectionNumberReal() : 0));
            videoDTO.setForwardNumber(videoDTO.getForwardNumber() + (videoDTO.getForwardNumberReal() != null
                    ? videoDTO.getForwardNumberReal() : 0));
        });

        PageResult<VideoDTO> videoPageResult = new PageResult<>();
        videoPageResult.setTotal((int) videoPage.getTotalElements());
        videoPageResult.setTotalPages(videoPage.getTotalPages());
        videoPageResult.setRows(videoDTOS);
        return videoPageResult;
    }
}
