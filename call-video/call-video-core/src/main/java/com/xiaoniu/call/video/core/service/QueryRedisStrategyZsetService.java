package com.xiaoniu.call.video.core.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.vo.RedisVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author :LiuYinkai
 * @date :2019-08-22 17:16.
 */
public class QueryRedisStrategyZsetService implements QueryRedisStrategyService{
    @Override
    public PageResult<RedisVO> doOperation(String key, String redisType) {
        List<RedisVO> redisVOList = new ArrayList<>();
        RedisVO redisVO = new RedisVO();
        PageResult<RedisVO> redisVOPageResult = new PageResult<>();
        if (RedisRepository.exists(key)) {
                Set<String> strings = RedisRepository.zRange(key, 0, -1);
                if (Objects.nonNull(strings)) {
                    redisVO.setKey(key);
                    redisVO.setValue(JSONUtils.toJSONString(strings));
                    redisVOList.add(redisVO);
                    redisVOPageResult.setRows(redisVOList);
                }
            }
        return redisVOPageResult;
    }
}
