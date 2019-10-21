package com.xiaoniu.call.video.api.enums;

import com.xiaoniu.architecture.commons.api.IBusinessEnum;
import com.xiaoniu.architecture.commons.api.IEnum;

/**
 * 视频业务枚举
 *
 * @author wuwen
 * @date 2019-07-02 15:56
 */
public enum VideoWebBusinessEnum implements IBusinessEnum<String> {
    VIDEO_CLASSIFICATION_MODIFICATION_FAILED("VW1000", "视频分类修改视频，影响行数不等于1", "视频分类修改视频，影响行数不等于1"),
    VIDEO_TAG_MODIFICATION_FAILED("VW1001", "视频标签修改视频，影响行数不等于1", "视频标签修改视频，影响行数不等于1"),
    VIDEO_TAG_DELETE_FAILED("VW1002", "视频标签有视频正在使用，无法删除", "视频标签有视频正在使用，无法删除")
    ;

    private String value;
    private String desc;
    private String businessDesc;

    VideoWebBusinessEnum(String value, String desc, String businessDesc) {
        this.value = value;
        this.desc = desc;
        this.businessDesc = businessDesc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getBusinessDesc() {
        return businessDesc;
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
