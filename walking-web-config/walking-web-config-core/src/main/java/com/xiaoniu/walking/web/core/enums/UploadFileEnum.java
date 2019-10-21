package com.xiaoniu.walking.web.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author luoyanchong
 * @ Description：文件上传枚举
 * @date 2019-04-27 16:40
 */
public enum UploadFileEnum implements IEnum<String> {
    UPLOAD_EXCEPTION("50050", "文件上传异常！"),
    UPLOAD_NOT_EMPTY("50051", "文件不能为空！"),
    UPLOAD_TYPE_ERROR("50052", "文件格式不支持！"),
    UPLOAD_SIZE_ERROR("50053", "超出文件限制大小！"),;
    private final String value;

    private final String desc;

    UploadFileEnum(String value, String desc) {
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

    private static Map<String, UploadFileEnum> mappings = new HashMap<>(16);

    static {
        Arrays.stream(values()).forEachOrdered((tradeType) -> {
            mappings.put(tradeType.getValue(), tradeType);
        });
    }

    /**
     * get enum by value
     *
     * @param value enumValue
     * @return UploadFileEnum
     */
    public static UploadFileEnum resolve(String value) {
        return mappings.get(value);
    }
}
