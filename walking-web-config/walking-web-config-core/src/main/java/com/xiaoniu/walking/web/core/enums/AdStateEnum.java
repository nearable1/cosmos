package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * @author :LiuYinkai
 * @date :2019-09-20 20:10.
 */
public enum AdStateEnum implements IEnum<Integer> {
    /**广告开关状态*/
    OPEN(1, "开启"),
    CLOSE(2, "关闭"),
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

    AdStateEnum(Integer value, String desc) {
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
