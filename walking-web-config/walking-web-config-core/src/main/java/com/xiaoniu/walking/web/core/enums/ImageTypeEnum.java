package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;
import com.xiaoniu.architecture.lending.commons.core.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 图片枚举
 *
 * @author lihoujing
 * @date 2019/7/16 16:09
 */
public enum ImageTypeEnum implements IEnum<String> {
    /**图片类型*/
    FILE__BMP("bmp", "image/bmp"),
    FILE__GIF("gif", "image/gif"),
    FILE__JPEG("jpeg", "image/jpeg"),
    FILE__JPG("jpg", "image/jpeg"),
    FILE__PNG("png", "image/jpeg"),
    FILE__BLOB("blob", "image/jpg")
    ;
    private final String value;

    private final String desc;

    ImageTypeEnum(String value, String desc) {
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

    private static Map<String, ImageTypeEnum> mappings = new HashMap<>(16);

    static {
        Arrays.stream(values()).forEachOrdered((tradeType) -> {
            mappings.put(tradeType.getValue(), tradeType);
        });
    }

    /**
     * get enum by value
     *
     * @param value enumValue
     * @return FileContentTypeEnum
     */
    public static ImageTypeEnum resolve(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.toLowerCase();
        }
        return mappings.get(value);
    }
}
