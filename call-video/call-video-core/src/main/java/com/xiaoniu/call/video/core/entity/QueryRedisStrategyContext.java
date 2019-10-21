package com.xiaoniu.call.video.core.entity;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.vo.RedisVO;
import com.xiaoniu.call.video.core.service.QueryRedisStrategyService;

/**
 * @author :LiuYinkai
 * @date :2019-08-22 17:20.
 */
public class QueryRedisStrategyContext  {

    private QueryRedisStrategyService queryRedisStrategyService;

    public QueryRedisStrategyContext(QueryRedisStrategyService queryRedisStrategyService) {
        this.queryRedisStrategyService = queryRedisStrategyService;
    }

    public PageResult<RedisVO> executeStrategy(String key, String redisType){
        return queryRedisStrategyService.doOperation(key, redisType);
    }
}
