package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.AddRedisKeyBO;
import com.xiaoniu.call.video.api.bo.DeleteRedisKeyBO;
import com.xiaoniu.call.video.api.vo.RedisVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 音频管理端业务
 *
 * @author liuyinkai
 * @date 2019-08-07
 */
public interface RedisManagementWebService {

    /**
     * query
     *
     * @param key
     * @return
     */
    PageResult<RedisVO> queryRedis(String key, String redisType);

    /**
     * delete
     *
     * @param deleteRedisKeyBO
     */
    void deleteKey(@RequestBody DeleteRedisKeyBO deleteRedisKeyBO);

    /**
     * 新增缓存
     */
    void addRedis(@RequestBody AddRedisKeyBO addRedisKeyBO);
}
