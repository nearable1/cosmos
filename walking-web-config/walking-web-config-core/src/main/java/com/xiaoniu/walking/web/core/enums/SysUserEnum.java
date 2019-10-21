package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * @author luoyanchong
 * @ Description：用户状态
 * @date 2019-03-14 16:32
 */
public enum SysUserEnum implements IEnum<Integer> {
    NORMAL(1, "正常"),
    LOCK(2, "账号已被锁定,请联系管理员"),
    INVALID(3, "账号不存在或密码错误")
    ;
    private final Integer value;

    private final String desc;

    SysUserEnum(Integer value, String desc) {
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
