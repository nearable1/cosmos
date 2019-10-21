package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Objects;

/**
 * @author :LiuYinkai
 * @date :2019-09-20 20:10.
 */
public enum AdPositionEnum implements IEnum<Integer> {

    RANDOM_BUBBLE(1, "随机气泡"),
    EXCHANGE_BUBBLE(2, "步数兑换气泡"),
    STAGE_REWARD(3, "领取阶段性奖励"),
    KANKAN_LIST(4, "看看列表广告位"),
    KANKAN_DETAIL(5, "看看详情页广告"),
    SIGN_IN(6, "签到"),
    WATCH_VIDEO(7, "看福利视频"),
    MY_CENTER_BOTTOM(8, "个人中心底部"),

    ;
    private final Integer value;

    private final String desc;

    @Override
    public Integer getValue() {
        return value;
    }
    @Override
    public String getDesc() {
        return desc;
    }

    AdPositionEnum(Integer value, String desc) {
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
}
