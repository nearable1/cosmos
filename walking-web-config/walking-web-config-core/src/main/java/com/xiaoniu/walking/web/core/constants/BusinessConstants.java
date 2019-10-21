package com.xiaoniu.walking.web.core.constants;


import com.xiaoniu.walking.web.core.model.auto.Video;

import java.util.HashSet;
import java.util.Set;

/**
 * 业务常量
 *
 * @author wuwen
 * @date 2019-07-05 18:16
 */
public class BusinessConstants {

    /**
     * 视频MongoDB集合名称
     */
    public final static String VIDEO_COLLECTION_NAME = Video.class.getSimpleName().toLowerCase();

    public final static String VIDEO_CLASSIFICATION_PREFIX = "VC";

    public final static String VIDEO_TAG_PREFIX = "VT";

    public final static int HOME_OPERATION_RANDOM_NUMBER = 5;

    public final static int HOME_ALL_RANDOM_NUMBER = 5;

    public final static int NUMBER_OF_RANDOM_OPERATIONS = 3;

    public final static int CLASSIFY_ALL_RANDOM_NUMBERS = 7;



    /**
     * 视频审核MongoDB集合名称
     */
    public final static String VIDEO_REVIEW_COLLECTION_NAME = "video_review";

    public static Set<String> INDEX_V1_VERSION = new HashSet<>();
    static {
        INDEX_V1_VERSION.add("1.0.0");
        INDEX_V1_VERSION.add("1.0.1");
    }

    /**
     * 查看新手教学视频 videoNumber = 0000000000000
     */
    public final static String TEACHING_VIDEO_NUMBER = "0000000000001";

    /**
     * 彩铃设置率
     */
    public final static String RINGTONE_SETTING_RATE = "ringtoneSettingRate";

    /**
     * 来电秀设置率
     */
    public final static String CALLSHOW_SETTING_RATE = "callShowSettingRate";

    /**
     * 壁纸设置率
     */
    public final static String WALLPAPER_SETTING_RATE = "wallpaperSettingRate";

}
