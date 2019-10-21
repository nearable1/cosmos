package com.xiaoniu.walking.web.core.constants;

/**
 * 数据缓存常量类
 *
 * @author wuwen
 * @date 2019-07-02 19:30
 */
public class RedisConstants {

    /**
     * 避免全量拷贝导致的内存飙升问题，此key弃用，新key USER_VIDEO_NEW_CLASSIFIED
     * 用户分类视频池
     * USER_VIDEO:CLASSIFIED:分类编号:用户设备编号
     */
    @Deprecated
    public final static String USER_VIDEO_CLASSIFIED = "USER_VIDEO:CLASSIFIED:%s:%s";



    /**
     * 推荐池改大池过程中发现生产问题，此key弃用，改用USER_VIDEO_NEW_CATEGORY_HOME
     *
     */
    @Deprecated
    public final static String USER_VIDEO_CATEGORY_HOME = "USER_VIDEO:CATEGORY_HOME:%s:%s";

    /**
     * APP
     */
    public static final String APP = "APP";

    /**
     * 视频来源
     */
    public static final String APP_VIDEO_SOURCE = "VIDEO_SOURCE";

    /**
     * 视频来源
     */
    public static final String APP_VIDEO_CATEGORY = "VIDEO_CATEGORY";

    /**
     * 视频分类池
     * VIDEO_POOL:分类编号
     */
    public final static String VIDEO_POOL_CLASSIFIED = "VIDEO_POOL:%s";

    /**
     * 所有首页分业务视频池（业务类型：来电秀=1，壁纸秀=2，屏保秀/锁屏=3）
     */
    public final static String VIDEO_POOL_CATEGORY_HOME = "VIDEO_POOL:CATEGORY_HOME:%s";

    /**
     * 小视频池
     */
    public final static String VIDEO_POOL_SMALL = "VIDEO_POOL:SMALL";

    /**
     * 视频标签redis缓存
     */
    public final static String VIDEO_TAG_MAP = "VIDEO_TAG_MAP";

    /**
     * 视频标签redis缓存
     */
    public final static String VIDEO_TAG = "VIDEO_TAG";
}
