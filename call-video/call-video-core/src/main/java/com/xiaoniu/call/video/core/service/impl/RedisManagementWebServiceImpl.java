package com.xiaoniu.call.video.core.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.video.api.bo.AddRedisKeyBO;
import com.xiaoniu.call.video.api.bo.DeleteRedisKeyBO;
import com.xiaoniu.call.video.api.vo.RedisVO;
import com.xiaoniu.call.video.core.entity.QueryRedisStrategyContext;
import com.xiaoniu.call.video.core.enums.RedisBusinessEnum;
import com.xiaoniu.call.video.core.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 音频管理端业务实现
 *
 * @author liuyinkai
 * @date 2019-08-02
 */
@Service
@Log4j2
public class RedisManagementWebServiceImpl implements RedisManagementWebService {

    /**
     * string类型的缓存
     */
    public final static String REDIS_DATA_TYPE_STRING = "string";

    /**
     * hash类型的缓存
     */
    public final static String REDIS_DATA_TYPE_HASH = "hash";

    /**
     * set类型的缓存
     */
    public final static String REDIS_DATA_TYPE_SET = "set";

    /**
     * zset类型的缓存
     */
    public final static String REDIS_DATA_TYPE_ZSET = "zset";

    /**
     * list类型的缓存
     */
    public final static String REDIS_DATA_TYPE_LIST = "list";

    /**
     * query
     *
     * @param key
     * @param redisType
     * @return
     */
    @Override
    public PageResult<RedisVO> queryRedis(String key, String redisType) {

        if (REDIS_DATA_TYPE_STRING.equals(redisType)) {
            QueryRedisStrategyContext string = new QueryRedisStrategyContext(new QueryRedisStrategyStringService());
            return string.executeStrategy(key, redisType);

        } else if (REDIS_DATA_TYPE_HASH.equals(redisType)) {
            QueryRedisStrategyContext hash = new QueryRedisStrategyContext(new QueryRedisStrategyHashService());
            return hash.executeStrategy(key , redisType);

        } else if (REDIS_DATA_TYPE_SET.equals(redisType)) {
            QueryRedisStrategyContext set = new QueryRedisStrategyContext(new QueryRedisStrategySetService());
            return set.executeStrategy(key , redisType);

        } else if (REDIS_DATA_TYPE_ZSET.equals(redisType)) {
            QueryRedisStrategyContext zset = new QueryRedisStrategyContext(new QueryRedisStrategyZsetService());
            return zset.executeStrategy(key , redisType);

        } else if (REDIS_DATA_TYPE_LIST.equals(redisType)) {
            QueryRedisStrategyContext list = new QueryRedisStrategyContext(new QueryRedisStrategyListService());
            return list.executeStrategy(key , redisType);
        }

        return null;
    }

    /**
     * 删除缓存键
     *
     * @param deleteRedisKeyBO
     */
    @Override
    public void deleteKey(DeleteRedisKeyBO deleteRedisKeyBO) {
        boolean del = RedisRepository.del(deleteRedisKeyBO.getKey());
        if(!del){
            log.info("【删除缓存】删除失败，键名={}", deleteRedisKeyBO.getKey());
        }
    }

    /**
     * 添加新的缓存
     *
     * @param addRedisKeyBO
     */
    @Override
    public void addRedis(AddRedisKeyBO addRedisKeyBO) {
        if (REDIS_DATA_TYPE_STRING.equals(addRedisKeyBO.getRedisType())) {
            if (RedisRepository.exists(addRedisKeyBO.getKey())) {
                throw new BusinessException(RedisBusinessEnum.REDIS_EXIST);
            }
            if (addRedisKeyBO.getTime() != null) {
                RedisRepository.set(addRedisKeyBO.getKey(), addRedisKeyBO.getValue(), addRedisKeyBO.getTime());
            } else {
                RedisRepository.set(addRedisKeyBO.getKey(), addRedisKeyBO.getValue());
            }
        } else if (REDIS_DATA_TYPE_HASH.equals(addRedisKeyBO.getRedisType())) {
            if (!StringUtils.isNotBlank(addRedisKeyBO.getField())) {
                throw new BusinessException(RedisBusinessEnum.FIELD_NOT_EXIST);
            }
            RedisRepository.hset(addRedisKeyBO.getKey(), addRedisKeyBO.getField(), addRedisKeyBO.getValue());

        } else if (REDIS_DATA_TYPE_SET.equals(addRedisKeyBO.getRedisType())) {
            List<String> list = Arrays.asList(addRedisKeyBO.getValue().trim().split(","));
            if (list.size() > 0) {
                Set<String> set = new HashSet<>(list);
                if (addRedisKeyBO.getTime() != null) {
                    set.forEach(s -> {
                        RedisRepository.sadd(addRedisKeyBO.getKey(), addRedisKeyBO.getTime(), s.trim());
                    });
                } else {
                    set.forEach(s -> {
                        RedisRepository.sadd(addRedisKeyBO.getKey(), s);
                    });
                }
            }

        } else if (REDIS_DATA_TYPE_ZSET.equals(addRedisKeyBO.getRedisType())) {
            if (addRedisKeyBO.getScore() == null) {
                throw new BusinessException(RedisBusinessEnum.SCORE_NOT_EXIST);
            }
            if (addRedisKeyBO.getTime() != null) {
                RedisRepository.zadd(addRedisKeyBO.getKey(), addRedisKeyBO.getValue(), addRedisKeyBO.getScore(), addRedisKeyBO.getTime());
            } else {
                RedisRepository.zadd(addRedisKeyBO.getKey(), addRedisKeyBO.getValue(), addRedisKeyBO.getScore());
            }
        } else if (REDIS_DATA_TYPE_LIST.equals(addRedisKeyBO.getRedisType())) {
            List<String> list = Arrays.asList(addRedisKeyBO.getValue().trim().split(","));
            list.forEach(ll->{
                RedisRepository.lpush(addRedisKeyBO.getKey(), ll.trim());
            });
        }
    }


}
