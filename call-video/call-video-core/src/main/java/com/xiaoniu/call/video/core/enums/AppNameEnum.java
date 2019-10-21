package com.xiaoniu.call.video.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

/**
 * app名称
 *
 * @author liuyinkai
 * @date 2019-08-20
 */
public enum AppNameEnum implements IEnum<Integer> {
    ZUILAIDIAN(1, "最来电"),
    DONGTAIBIZHI(2, "动态壁纸"),
    AILAIDIAN(3, "爱来电"),
    LINGSHENGDUODUO(4, "铃声多多"),
    ;

    private Integer value;
    private String desc;

    AppNameEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    @Override
    public boolean matches(Integer value) {
        return this.value.equals(value);
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