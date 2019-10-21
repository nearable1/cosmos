package com.xiaoniu.walking.web.core.enums;
import java.util.Objects;

/**
 * @ Description：短信发送状态
 * @author shenguoqing
 */
public enum VerifyCodeResultEnum {
    // 成功
    SEND_SUCCESS("200", "验证码获取成功")
    // 失败
    ,SEND_FAILURE("500", "验证码获取失败")
    // 配置不完整
    ,CONFIG_INCOMPLETE("201", "验证码相关配置不完整")
    // 验证码每小时只能获取五次
    ,TRANSFINITE_H("202", "验证码每小时只能获取五次")
    //  验证码日发送次数已达到上限
    ,TRANSFINITE_D("203", "验证码日发送次数已达到上限")
    //  用户已注册
    ,OLD_USER("204", "用户已注册")
    ;
    /**
     * 短信状态码
     */
    private String value;
    /**
     * 短信描述
     */
    private String desc;

    public void setValue(String value) {
        this.value = value;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    VerifyCodeResultEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    public boolean matches(Integer value) {
        if(null == value) {
            return false;
        }
        return Objects.equals(getValue(), value);
    }

    public String getDesc() {
        return desc;
    }
    public String getValue() {
        return value;
    }

}
