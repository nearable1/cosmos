package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * @author xiangxianjin
 * @ Description：业务提示枚举
 * @date 2019-04-17 16:32
 */
public enum WebConfigBusinessEnum implements IEnum<String> {
    ILLEGAL_TOKEN("50008", "非法的token"),
    LOGIN_INVALID("50009", "账号不存在或密码错误"),
    USER_EXSIT("50010", "登录账号名已存在"),
    LOGIN_OTHER("50012", "其他客户端登录了"),
    LOGIN_LOCK("50013", "账号已被锁定,请联系管理员"),
    TOKEN_EXPIRE("50014", "Token过期了"),
    LOGIN_ERROR("50015", "信安校验失败，请联系信安部门"),
    LOGIN_CODE_ERROR("50016", "验证码校验失败"),
    LOGIN_USER_IS_NULL("50017", "账号不存在"),
    NO_CHANNEL("50018", "没有配置该渠道");
    ;
    private final String value;

    private final String desc;

    WebConfigBusinessEnum(String value, String desc) {
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
