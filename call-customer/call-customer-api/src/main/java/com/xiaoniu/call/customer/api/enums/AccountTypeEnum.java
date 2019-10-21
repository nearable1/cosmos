package com.xiaoniu.call.customer.api.enums;

public enum AccountTypeEnum {
    WECHAT(2,"微信"),
    QQ(1,"QQ");
    private Integer code;
    private String name;

    AccountTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
