package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * 修改密码返回结果枚举
 * @author chenguohua
 * @date 2019年4月28日15:13:05
 */

public enum PasswordVerifyEnum implements IEnum<String> {

    NEW_PASSWORD_OLD_PASSWORD_ERROR("1", "新密码不能与老密码一样"),
    PASSWORD_FORMAT_ERROR("2", "密码格式不正确"),
    OLD_PASSWORD_ERROR("3", "原密码不正确"),
    PASSWORD_ERROR("4", "两次输入密码不一致"),
    PASSWORD_UPDATE_ERROR("5", "修改密码失败"),
    ;
    private final String value;

    private final String desc;

    PasswordVerifyEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public boolean matches(String value) {
        if (null == value) {
            return false;
        }
        return Objects.equals(getValue(), value);
    }

    @Override
    public boolean matches(IEnum<String> iEnum) {
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
    public String getValue() {
        return value;
    }
}
