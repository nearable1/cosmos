package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.vo.RedisVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :LiuYinkai
 * @date :2019-08-22 17:16.
 */
public class QueryRedisStrategyStringService implements QueryRedisStrategyService{
    @Override
    public PageResult<RedisVO> doOperation(String key, String redisType) {
        List<RedisVO> redisVOList = new ArrayList<>();
        RedisVO redisVO = new RedisVO();
        PageResult<RedisVO> redisVOPageResult = new PageResult<>();
        if (RedisRepository.exists(key)) {

            String value = RedisRepository.get(key);
            String time = String.valueOf(RedisRepository.ttl(key));
            redisVO.setKey(key);
            redisVO.setValue(value);
            redisVO.setTime(time);
            redisVOList.add(redisVO);
            redisVOPageResult.setRows(redisVOList);
        }
        return redisVOPageResult;
    }
}
