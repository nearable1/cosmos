package com.xiaoniu.call.video.core.enums;

import com.xiaoniu.architecture.commons.api.IBusinessEnum;
import com.xiaoniu.architecture.commons.api.IEnum;

/**
 * 视频业务枚举
 *
 * @author wuwen
 * @date 2019-07-02 15:56
 */
public enum RedisBusinessEnum implements IBusinessEnum<String> {
    REDIS_EXIST("R1000", "缓存已存在", "缓存已存在"),
    FIELD_NOT_EXIST("R1001", "域不存在", "域不存在"),
    SCORE_NOT_EXIST("R1002", "分不存在", "分不存在"),
    ;

    private String value;
    private String desc;
    private String businessDesc;

    RedisBusinessEnum(String value, String desc, String businessDesc) {
        this.value = value;
        this.desc = desc;
        this.businessDesc = businessDesc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getBusinessDesc() {
        return businessDesc;
    }

    @Override
    public boolean matches(String value) {
        return this.value.equals(value);
    }

    @Override
    public boolean matches(IEnum<String> iEnum) {
        if (null == iEnum) {
            return false;
        }
        return matches(iEnum.getValue());
    }
}
