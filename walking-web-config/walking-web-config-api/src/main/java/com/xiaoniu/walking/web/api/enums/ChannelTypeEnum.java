package com.xiaoniu.walking.web.api.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * @author luoyanchong
 * @ Description：放款回调渠道类型
 * @date 2019-03-14 16:32
 */
public enum ChannelTypeEnum implements IEnum<Integer> {
    CHANNEL_UNKONW(0, "未知渠道"),
    CHANNEL_LENDING(1, "来这花"),
    CHANNEL_OTHER(2, "其它渠道"),
    ;
    private final Integer value;

    private final String desc;

    ChannelTypeEnum(Integer value, String desc) {
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
