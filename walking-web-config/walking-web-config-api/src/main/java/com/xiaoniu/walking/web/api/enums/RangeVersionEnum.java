package com.xiaoniu.walking.web.api.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * 版本范围枚举
 *
 * @author lihoujing
 * @date 2019/9/29 15:07
 */
public enum RangeVersionEnum implements IEnum<Integer> {
    /**版本范围枚举*/
    RANGE_1(1, "大于"),
    RANGE_2(2, "大于等于"),
    RANGE_3(3, "等于"),
    RANGE_4(4, "小于"),
    RANGE_5(5, "小于等于"),
    RANGE_6(6, "不等于"),
    ;
    private final Integer value;

    private final String desc;

    @Override
    public Integer getValue() {
        return value;
    }
    @Override
    public String getDesc() {
        return desc;
    }

    RangeVersionEnum(Integer value, String desc) {
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
}
