package com.xiaoniu.call.customer.core.cons;

import java.util.HashMap;
import java.util.Map;

public final class CustomerCons {
    public static Map<String, String> OSS_PATH_MAPPING = new HashMap<>();
    public static Map<Integer, Integer> ACCOUNT_TYPE_MAPPING = new HashMap<>();
    static {
        OSS_PATH_MAPPING.put("1", "avatar");

        ACCOUNT_TYPE_MAPPING.put(1, 6);
        ACCOUNT_TYPE_MAPPING.put(2, 5);
    }
}
