package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;
import com.xiaoniu.architecture.lending.commons.core.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author luoyanchong
 * @ Description：文件类型枚举
 * @date 2019-04-27 16:49
 */
public enum FileContentTypeEnum implements IEnum<String> {
    FILE__BMP("bmp", "image/bmp"),
    FILE__GIF("gif", "image/gif"),
    FILE__JPEG("jpeg", "image/jpeg"),
    FILE__JPG("jpg", "image/jpeg"),
    FILE__PNG("png", "image/jpeg"),
    FILE__HTML("html", "text/html"),
    FILE__TXT("txt", "text/plain"),
    FILE__VSD("vsd", "application/vnd.visio"),
    FILE__PPT("ppt", "application/vnd.ms-powerpoint"),
    FILE__PPTX("pptx", "application/vnd.ms-powerpoint"),
    FILE__DOC("doc", "application/msword"),
    FILE__DOCX("docx", "application/msword"),
    FILE__BLOB("blob", "image/jpg"),
    FILE__XML("xml", "text/xml"),
    FILE__APK("apk", "text/html");
    private final String value;

    private final String desc;

    FileContentTypeEnum(String value, String desc) {
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

    private static Map<String, FileContentTypeEnum> mappings = new HashMap<>(16);

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
    public static FileContentTypeEnum resolve(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.toLowerCase();
        }
        return mappings.get(value);
    }
}
