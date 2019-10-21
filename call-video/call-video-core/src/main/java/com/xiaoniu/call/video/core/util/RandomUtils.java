package com.xiaoniu.call.video.core.util;

import java.util.Random;

/**
 * 随机数工具类
 *
 * @author wuwen
 * @date 2019-07-06 15:04
 */
public class RandomUtils {

    public static int random(int min, int max) {
        return new Random().ints(min, (max + 1)).findFirst().getAsInt();
    }
}
