package com.xiaoniu.call.video.core.quartz;

import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.enums.VideoBusinessEnum;
import com.xiaoniu.call.video.core.constants.RedisConstants;
import com.xiaoniu.call.video.core.entity.Video;
import com.xiaoniu.call.video.core.entity.VideoSettingRates;
import com.xiaoniu.call.video.core.service.VideoSettingRatesService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 视频设置率 定时任务
 *
 * @author :LiuYinkai
 * @date :2019-08-27 11:18.
 */
@Component
@Log4j2
public class VideoSettingRateScheduler {

//    public static final String LOCK_VALUE = "locking";

    @Autowired
    private VideoSettingRatesService videoSettingRatesService;
    /**
     * 每周一凌晨一点
     */
    @Scheduled(cron = "0 0 1 ? * MON")
//    @Scheduled(cron = "0 */2 * * * ?")
    public void run (){
        // redis 锁，防止多个集群执行
        String lock = RedisRepository.get(RedisConstants.TIMER_TASK_VIDEO_SETTING_RATES);
        String ip = "";
        try {
            InetAddress address = InetAddress.getLocalHost();
            ip = "ip:" + address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            log.error("获取服务器ip异常, 异常信息={}", e.getMessage());
        }
        if (!StringUtils.isNotEmpty(lock)) {

            // 上锁
            RedisRepository.set(RedisConstants.TIMER_TASK_VIDEO_SETTING_RATES, ip, 60*60*24);
            Long startTime = System.currentTimeMillis();
            log.info("【视频设置率-定时任务】======>> 开始，start_time={} ", startTime);
            //查询所有数据
            List<Video> listByClazz = MongodbRepository.findListByClazz(new Query(), Video.class);
            if (!CollectionUtils.isNotEmpty(listByClazz)) {
                throw new BusinessException(VideoBusinessEnum.VIDEO_DOES_NOT_EXIST);
            }

//        // 测试2000条数据  耗时47秒
//        List<Video> listByClazz = Lists.newArrayList();
//        for (int i = 0; i < 2000; i++){
//            listByClazz.add(listByClazz1.get(i));
//        }

            //去重
            listByClazz = listByClazz.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Video::getVideoNumber))), ArrayList::new));
            VideoSettingRates videoSettingRate = new VideoSettingRates();
            if (CollectionUtils.isNotEmpty(listByClazz)) {
                listByClazz.forEach(v -> {
                    videoSettingRate.setVideoNumber(v.getVideoNumber());
                    videoSettingRate.setCallShowSettingRate(settingRateFormat2(v.getCallNumber(), v.getViewReal()));
                    videoSettingRate.setWallpaperSettingRate(settingRateFormat2(v.getWallpaperNumber(), v.getViewReal()));
                    videoSettingRate.setRingtoneSettingRate(settingRateFormat2(v.getRingtoneShowNumber(), v.getViewReal()));
                    // 判断是否存在
                    VideoSettingRates videoSettingRateTemp = videoSettingRatesService.findByVideoNumber(v.getVideoNumber());
                    if (Objects.nonNull(videoSettingRateTemp)) {
                        // 更新
                        int updateStatus = videoSettingRatesService.updateByVideoNumber(videoSettingRate);
                    } else {
                        // 插入
                        int insertStatus = videoSettingRatesService.insertNew(videoSettingRate);
                    }
                });
            }

            Long endTime = System.currentTimeMillis();
            log.info("【视频设置率-定时任务】 <<====== 结束 end_time = {}，耗时 = {}", endTime, endTime - startTime);
        } else {
            log.info("【视频设置率-定时任务】 ====== 本次执行失败, 已有机器正在执行, 机器地址={} ", ip);
        }

    }

    /**
     * 计算设置率
     *
     * @param settingNum
     * @param playNum
     * @return
     */
    public float settingRateFormat(Integer settingNum, Integer playNum) {
        if (settingNum == null || settingNum == 0) {
            return 0.00f;
        } else if (playNum == null || playNum == 0) {
            return 0.00f;
        }
        // 转换成浮点型
        float settingNumF = (float) settingNum;
        float playNumF = (float) playNum;
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        return Float.valueOf(decimalFormat.format(settingNumF / playNumF));

    }

    /**
     * 计算设置率2
     *
     * @param settingNum
     * @param playNum
     * @return
     */
    public BigDecimal settingRateFormat2(Integer settingNum, Integer playNum) {
        if (settingNum == null || settingNum == 0) {
            return new BigDecimal("0.00");
        } else if (playNum == null || playNum == 0) {
            return new BigDecimal("0.00");
        }
        // 转换成浮点型
        float settingNumF = (float) settingNum;
        float playNumF = (float) playNum;
//        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        return new BigDecimal(settingNumF/playNumF).setScale(2, BigDecimal.ROUND_UP);
    }

}
