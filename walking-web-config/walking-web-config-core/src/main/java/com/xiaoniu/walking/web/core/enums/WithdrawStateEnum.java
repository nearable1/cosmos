package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * @author chenguohua
 * @ Description：业务提示枚举
 * @date 支付宝批量提现状态
 */
public enum WithdrawStateEnum implements IEnum<String> {
    WITHDRAW_STATE_FAIL("失败", "支付宝审核失败"),
    WITHDRAW_STATE_SUCCESS("成功", "支付宝审核成功"),
    BACK_RESULT_SUCCESS("true", "后台返回成功"),
    ;
    private final String value;

    private final String desc;

    WithdrawStateEnum(String value, String desc) {
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
