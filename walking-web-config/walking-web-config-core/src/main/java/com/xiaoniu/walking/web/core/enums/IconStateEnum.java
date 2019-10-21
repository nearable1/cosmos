package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * 底部icon状态枚举
 * @author lihoujing
 * @date 2019/6/25 11:27
 */
public enum IconStateEnum implements IEnum<Integer> {

    /**1-有效*/
    VALID(1, "有效"),
    /**2-有效*/
    INVALID(2, "无效"),


    ;
    private final Integer value;

    private final String desc;

    IconStateEnum(Integer value, String desc) {
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
