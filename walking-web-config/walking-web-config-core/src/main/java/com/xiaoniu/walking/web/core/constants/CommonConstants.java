package com.xiaoniu.walking.web.core.constants;

/**
 * @author luoyanchong
 * @ Description：通用常量
 * @date 2019-04-26 16:12
 */
public class CommonConstants {

    /**
     * 文件类型区分标识
     */
    public static final String FILE_SPLIT_SYMBOL = ".";

    /**
     * 文件等级阶梯
     */
    public static final long FILE_LEVEL = 1024;
    /**
     * 密码的正则验证
     */
    public static final String REGULAR_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";

    /**
     * 重置用户密码
     */
    public static final String RESET_USER_PASSWORD = "123456";

    /**
     * 验证码状态为开启
     */
    public static final String LOGIN_CODE_IS_SHOW_SWITCH = "true";

    /**
     * oss类型
     */
    public static final Integer VIDEO_AUDIO_BUCKET_TYPE = 1;
}
