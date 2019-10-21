package com.xiaoniu.walking.web.api.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * 敏感词类型枚举
 *
 * @author lihoujing
 * @date 2019/7/11 20:35
 */
public enum SensitiveTypeEnum implements IEnum<Integer> {

    /**1-全部*/
    ALL(1, "全部"),
    /**2-评论敏感词*/
    COMMENT(2, "评论敏感词"),
    ;
    private final Integer value;

    private final String desc;

    SensitiveTypeEnum(Integer value, String desc) {
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
