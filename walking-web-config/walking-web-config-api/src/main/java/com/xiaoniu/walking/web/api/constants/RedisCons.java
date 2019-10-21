package com.xiaoniu.walking.web.api.constants;

/**
 * @author: xiangxianjin
 * @date: 2019/3/29 18:41
 * @description:
 */
public class RedisCons {

    /**
     * 登录用户token
     */
    public static final String SYS_USER_TOKEN = "SYS_USER_TOKEN:%s";

    /**
     * 登录超时时间=30分钟
     */
    public static final int SYS_USER_TOKEN_EXPIRE = 1800;



    /**
     * 打折数据字典
     */
    public static final String SYS_DICT_DISCONUT = "SYS_DICT_DISCONUT:%s";


    /**
     * 打折数据字典超时时间=1天
     */
    public static final int SYS_DICT_DISCONUT_EXPIRE = 60*60*24;

    /**
     * 数据营销综合管理后台登录验证码
     */
    public static final String USER_LOGIN_CODE = "mb_user:web_config_login_code:";

    /**
     * 数据营销综合管理后台登录用户不存在
     */
    public static final String WEB_CONFIG_LOGIN_USER_NULL = "mb_user:web_config_login_user_null:";


    /**
     * banner缓存
     */
    public static final String SYS_OS_BANNER = "SYS_CACHE_BANNER:%s";


    /**
     * banner过期时间=1天
     */
    public static final int SYS_OS_BANNER_EXPIRE = 86400;


    /**
     * icon缓存
     */
    public static final String SYS_OS_ICON = "SYS_CACHE_ICON:%s";


    /**
     * icon过期时间=7天
     */
    public static final int SYS_OS_ICON_EXPIRE = 86400 * 7;


    /**
     * APP更新缓存
     */
    public static final String SYS_OS_VERSION = "SYS_OS_VERSION:%s:%s:";


}
