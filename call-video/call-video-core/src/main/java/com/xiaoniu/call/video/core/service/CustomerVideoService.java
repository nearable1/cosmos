package com.xiaoniu.call.video.core.service;

/**
 * 客户视频操作
 *
 * @author wuwen
 * @date 2019-07-02 10:23
 */
public interface CustomerVideoService {

    /**
     * 视频喜欢
     *
     * @param deviceId
     * @param videoNumber
     */
    void like(String deviceId, String videoNumber);

    /**
     * 取消视频喜欢
     *
     * @param deviceId
     * @param videoNumber
     */
    void cancelLike(String deviceId, String videoNumber);

    /**
     * 视频分享
     *
     * @param videoNumber
     */
    void share(String videoNumber);

    /**
     * 视频播放
     *
     * @param videoNumber
     */
    void broadcast(String videoNumber);

    /**
     * 探索视频播放
     *
     * @param videoNumber
     */
    void exploreVideoPlayback(String videoNumber);

    /**
     * 设置来电壁纸
     *
     * @param videoNumber
     */
    void setCallerWallpaper(String videoNumber);

    /**
     * 设置来电铃声
     *
     * @param videoNumber
     */
    void setRingtones(String videoNumber);

    /**
     * 设置壁纸秀
     *
     * @param videoNumber
     */
    void setWallpaper(String videoNumber);

    /**
     * 设置屏保秀
     *
     * @param videoNumber
     */
    void setScreensaver(String videoNumber);

    /**
     * 设置彩铃秀
     *
     * @param videoNumber
     */
    void setRingtoneShow(String videoNumber);
}
