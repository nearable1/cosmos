package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * banner状态枚举
 *
 * @author lihoujing
 * @date 2019/6/5 10:56
 */
public enum OsBannerStateEnum implements IEnum<Integer>{
    /**1-开启*/
    OPEN(1, "开启"),
    /**2-关闭*/
    CLOSE(2, "关闭"),
            ;
    private final Integer value;

    private final String desc;

    OsBannerStateEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public boolean matches(Integer value) {
        if (null == value) {
            return false;
        }
        return Objects.equals(getValue(), value);
    }

    @Override
    public boolean matches(IEnum<Integer> iEnum) {
        if (null == iEnum) {
            return false;
        }
        return matches(iEnum.getValue());
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
