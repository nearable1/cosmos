package com.xiaoniu.call.video.core.constants;

/**
 * 数据缓存常量类
 *
 * @author wuwen
 * @date 2019-07-02 19:30
 */
public class RedisConstants {

    /**
     * 视频分类redis缓存
     */
    public final static String VIDEO_CLASSIFICATION = "VIDEO_CLASSIFICATION";

    /**
     * 探索视频播放记录
     * EXPLORING_PLAY:设备编号
     */
    public final static String EXPLORING_PLAY = "EXPLORING_PLAY:%s";

    /**
     * 探索视频播放记录过期时间
     */
    public final static int EXPLORING_PLAY_EXPIRED = 3 * 24 * 60 * 60;

    /**
     * 老王视频lastId缓存
     * LAO_WANG_LAST_ID:deviceId
     */
    public final static String LAO_WANG_LAST_ID = "LAO_WANG_LAST_ID:%s";

    /**
     * 老王视频lastId缓存过期时间
     */
    public final static int LAO_WANG_LAST_ID_EXPIRED = 3 * 24 * 60 * 60;

    /**
     * 老王视频访问TOKEN
     */
    public final static String LAO_WANG_TOKEN = "LAO_WANG_TOKEN";

    /**
     * 老王视频访问TOKEN过期时间(110分钟)
     */
    public final static int LAO_WANG_TOKEN_EXPIRED = 110 * 60;

    /**
     * 视频标签redis缓存
     */
    public final static String VIDEO_TAG = "VIDEO_TAG";

    /**
     * 视频标签redis缓存过期时间
     */
    public final static int VIDEO_TAG_EXPIRED = 24 * 60 * 60;

    /**
     * 所有首页视频池
     */
    public final static String VIDEO_POOL_HOME = "VIDEO_POOL:HOME";

    /**
     * 小视频池
     */
    public final static String VIDEO_POOL_SMALL = "VIDEO_POOL:SMALL";

    /**
     * 视频分类池
     * VIDEO_POOL:分类编号
     */
    public final static String VIDEO_POOL_CLASSIFIED = "VIDEO_POOL:%s";

    /**
     * 运营首页视频池
     */
    public final static String VIDEO_POOL_OPERATION_HOME = "VIDEO_POOL:HOME_OPERATION";

    /**
     * 运营视频分类池
     * VIDEO_POOL:OPERATION_CLASSIFIED:分类编号
     */
    public final static String VIDEO_POOL_OPERATION_CLASSIFIED = "VIDEO_POOL:OPERATION_CLASSIFIED:%s";

    /**
     * 用户首页视频池
     * USER_VIDEO:HOME:设备号
     */
    public final static String USER_VIDEO_HOME = "USER_VIDEO:HOME:%s";

    /**
     * 避免全量拷贝导致的内存飙升问题，此key弃用，新key USER_VIDEO_NEW_CLASSIFIED
     * 用户分类视频池
     * USER_VIDEO:CLASSIFIED:分类编号:用户设备编号
     */
    @Deprecated
    public final static String USER_VIDEO_CLASSIFIED = "USER_VIDEO:CLASSIFIED:%s:%s";

    /**
     * 用户分类视频池
     * USER_VIDEO:CLASSIFIED:分类编号:用户设备编号
     */
    public final static String USER_VIDEO_NEW_CLASSIFIED = "USER_VIDEO:NEW_CLASSIFIED:%s:%s";

    /**
     * 用户分类视频差集池
     * USER_VIDEO:CLASSIFIED:分类编号:用户设备编号
     */
    public final static String USER_VIDEO_DIFF_CLASSIFIED = "USER_VIDEO:DIFF_CLASSIFIED:%s:%s";

    /**
     * 用户视频池过期时间
     */
    public final static int USER_VIDEO_POOL_EXPIRED = 3 * 24 * 60 * 60;

    /**
     * 用户首页视频池加入标记
     * USER_VIDEO:HOME_ADD:
     */
    public final static String USER_VIDEO_HOME_ADD = "USER_VIDEO:HOME_ADD:%s";


    /**
     * 音频分类池
     */
    public final static String AUDIO_POOL_CLASSIFIED = "AUDIO_POOL:%s";

    /**
     * 推荐池改大池过程中发现生产问题，此key弃用，改用USER_VIDEO_NEW_CATEGORY_HOME
     *
     */
    @Deprecated
    public final static String USER_VIDEO_CATEGORY_HOME = "USER_VIDEO:CATEGORY_HOME:%s:%s";

    /**
     * 用户首页分类视频池（业务类型：来电秀=1，壁纸秀=2，屏保秀/锁屏=3）
     * USER_VIDEO:CATEGORY_HOME:业务类型:设备号
     */
    public final static String USER_VIDEO_NEW_CATEGORY_HOME = "USER_VIDEO:NEW_CATEGORY_HOME:%s:%s";

    /**
     * 所有首页分业务视频池（业务类型：来电秀=1，壁纸秀=2，屏保秀/锁屏=3）
     */
    public final static String VIDEO_POOL_CATEGORY_HOME = "VIDEO_POOL:CATEGORY_HOME:%s";

    /**
     * 运营首页分业务视频池（业务类型：来电秀=1，壁纸秀=2，屏保秀=3）
     */
    public final static String VIDEO_POOL_OPERATION_CATEGORY_HOME = "VIDEO_POOL:CATEGORY_HOME_OPERATION:%s";

    /**
     * 视频标签redis缓存
     */
    public final static String VIDEO_TAG_MAP = "VIDEO_TAG_MAP";

    /**
     * 用户视频差集池
     */
    public final static String USER_VIDEO_DIFF_CATEGORY_HOME = "USER_VIDEO:DIFF_CATEGORY_HOME:%s:%s";

    /**
     * 视频设置率定时任务
     */
    public final static String TIMER_TASK_VIDEO_SETTING_RATES = "TIMER_TASK:VIDEO_SETTING_RATES";

    /**
     * 用户首页分类锁屏
     */
    public final static String USER_VIDEO_LOCK_HOME = "USER_VIDEO_LOCK:NEW_CATEGORY_HOME:%s:%s";

    /**
     * 用户锁屏视频差集池
     */
    public final static String USER_VIDEO_LOCK_DIFF_HOME = "USER_VIDEO_LOCK:DIFF_CATEGORY_HOME:%s:%s";



    /**
     * 皮一下视频分类池
     * VIDEO_PYX_POOL:分类编号(1:推荐)
     *
     */
    public final static String VIDEO_PYX_POOL = "VIDEO_PYX_POOL:%s";

    /**
     * 皮一下用户分类视频池
     * USER_VIDEO:CLASSIFIED:分类编号:用户设备编号
     */
    public final static String USER_PYX_VIDEO = "USER_PYX_VIDEO:%s:%s";

    /**
     * 皮一下用户分类视频差集池
     * USER_VIDEO:CLASSIFIED:分类编号:用户设备编号
     */
    public final static String USER_PYX_VIDEO_DIFF = "USER_PYX_VIDEO_DIFF:%s:%s";
}
