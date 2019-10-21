package com.xiaoniu.call.customer.api.enums;

import com.xiaoniu.architecture.commons.api.IBusinessEnum;
import com.xiaoniu.architecture.commons.api.IEnum;

public enum CustomerResultEnum implements IBusinessEnum<String> {

    ;

    private String value;

    private String desc;

    private String businessDesc;


    CustomerResultEnum(String value, String desc, String businessDesc) {
        this.value = value;
        this.desc = desc;
        this.businessDesc = businessDesc;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }


    @Override
    public String getBusinessDesc() {
        return this.businessDesc;
    }

    @Override
    public boolean matches(String value) {
        return this.value.equals(value);
    }

    @Override
    public boolean matches(IEnum<String> iEnum) {
        if (null == iEnum) {
            return false;
        }
        return matches(iEnum.getValue());
    }
}
