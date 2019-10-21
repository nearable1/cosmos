package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

/**
 * @ Description：验证码类型枚举
 * @author liuyinkai
 */
public enum CodeTypeEnum implements IEnum<Integer> {

    /**
     * 数据营销综合管理后台登录验证码
     */
    WEB_CONFIG_LOGIN_CODE(8,"数据营销综合管理后台登录验证码")
    ;
    /**
     * 结果状态码
     */
    private Integer value;
    /**
     * 结果描述
     */
    private String desc;



    CodeTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public boolean matches(Integer value) {
        if(null == value) {
            return false;
        }
        return getValue().intValue() == value.intValue();
    }

    @Override
    public boolean matches(IEnum<Integer> valueBean) {
        if(null == valueBean) {
            return false;
        }
        return matches(valueBean.getValue());
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
