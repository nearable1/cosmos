package com.xiaoniu.call.video.core.enums;

import com.xiaoniu.architecture.commons.api.IEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 音频分类
 *
 * @author liuyinkai
 * @date 2019-07-25 17:26
 */
public enum AudioCategoryEnum implements IEnum<Integer> {

    AC001(1, "AC001"),
    AC002(2, "AC002"),
    AC003(3, "AC003"),
    AC004(4, "AC004"),
    AC005(5, "AC005"),
    AC006(6, "AC006"),
    AC007(7, "AC007"),
    AC008(8, "AC008"),
    AC009(9, "AC009"),
    AC010(10, "AC010"),
    AC011(11, "AC011"),
    AC012(12, "AC012"),
    AC013(13, "AC013"),
    AC014(12, "AC014"),
    AC015(13, "AC015"),
    ;
    private Integer value;
    private String desc;


    AudioCategoryEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getValue() {
        return value;
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

    private static Map<Integer, AudioCategoryEnum> mapping = new HashMap<>();

    static {
        Arrays.stream(AudioCategoryEnum.values()).forEach(videoTypeEnum -> mapping.put(videoTypeEnum.getValue(), videoTypeEnum));
    }

    public static AudioCategoryEnum resolve(int value) {
        return mapping.get(value);
    }
}
