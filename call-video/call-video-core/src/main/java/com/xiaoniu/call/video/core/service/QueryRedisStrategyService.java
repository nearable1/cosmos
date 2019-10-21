package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.vo.RedisVO;

/**
 * @author :LiuYinkai
 * @date :2019-08-22 17:14.
 */
public interface QueryRedisStrategyService {

    PageResult<RedisVO> doOperation(String key, String redisType);
}
