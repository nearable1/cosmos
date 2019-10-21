package com.xiaoniu.call.customer.core.service;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.xiaoniu.call.customer.core.vo.AsmVO;
import com.xiaoniu.call.customer.core.vo.DicVO;

public interface DicService {
    Map<String, String> query(DicVO param);

    JSONArray asm(AsmVO param);

    void reportAsmLog(AsmVO param, Integer reportType);
}
