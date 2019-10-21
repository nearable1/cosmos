package com.xiaoniu.walking.web.core.helper;

import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.walking.web.api.enums.RangeVersionEnum;

/**
 * 版本是否匹配帮助类
 *
 * @author lihoujing
 * @date 2019/9/29 15:06
 */
public class CompareHelper {


    /**
     * 比较版本
     *
     * @param rangeVersion
     * @param version1
     * @param version2
     * @return
     */
    public static boolean isVersionMatch(Integer rangeVersion, String version1, String version2){

        int compareVersion = StringUtils.compareVersion(version1, version2);
        return (RangeVersionEnum.RANGE_1.matches(rangeVersion) && compareVersion > 0) ||
               (RangeVersionEnum.RANGE_2.matches(rangeVersion) && compareVersion >= 0) ||
               (RangeVersionEnum.RANGE_3.matches(rangeVersion) && compareVersion == 0) ||
               (RangeVersionEnum.RANGE_4.matches(rangeVersion) && compareVersion < 0) ||
               (RangeVersionEnum.RANGE_5.matches(rangeVersion) && compareVersion <= 0) ||
               (RangeVersionEnum.RANGE_6.matches(rangeVersion) && compareVersion != 0);
    }
}
