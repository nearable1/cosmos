package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * 用户状态
 * @author lihoujing
 * @date 2019/5/16 21:19
 */
public enum UserCertStatusEnum implements IEnum<Integer> {

    /**1-待审核 */
    WATTING(1, "待审核"),
    /**2-认证通过*/
    PASS(2, "认证通过"),
    /**3-认证未通过*/
    FAIL(3, "认证未通过")
            ;
    private final Integer value;

    private final String desc;

    UserCertStatusEnum(Integer value, String desc) {
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
