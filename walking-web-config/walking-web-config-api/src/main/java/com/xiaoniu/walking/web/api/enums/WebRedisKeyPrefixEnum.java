package com.xiaoniu.walking.web.api.enums;

/**
 * Copyright （c）2019, xiaoniu . All rights reserved.
 * <p>
 * walking-web redis的key枚举
 *
 * @author len.song
 * @date 2019/09/20
 */
public enum WebRedisKeyPrefixEnum {
    DICT_CHILDREN_LISTS_BY_PARENT("WkWeb:Dict_Child_List:%s","父类ID下的所有子类列表")

    ;


    private String value;
    private String desc;

    WebRedisKeyPrefixEnum(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public static WebRedisKeyPrefixEnum fromValue(String value) {
        for (WebRedisKeyPrefixEnum redisKey : WebRedisKeyPrefixEnum.values()) {
            if (redisKey.getValue().equals(value)) {
                return redisKey;
            }
        }
        return null;
    }

    public static String getDescByValue(String value) {
        for(WebRedisKeyPrefixEnum redisKey : WebRedisKeyPrefixEnum.values()) {
            if (redisKey.getValue().equals(value)) {
                return redisKey.getDesc();
            }
        }
        return "";
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
