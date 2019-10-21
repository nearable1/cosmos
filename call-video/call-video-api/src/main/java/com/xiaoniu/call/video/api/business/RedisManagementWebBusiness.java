package com.xiaoniu.call.video.api.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.AddRedisKeyBO;
import com.xiaoniu.call.video.api.bo.DeleteRedisKeyBO;
import com.xiaoniu.call.video.api.vo.RedisVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 缓存管理对内接口
 *
 * @author liuyinkai
 * @date 2019-08-07
 */
@FeignClient(contextId = "redisManagementWebBusiness", value = "video")
public interface RedisManagementWebBusiness {

    /**
     * 新增
     *
     * @return
     */
    @GetMapping("/query-redis")
    PageResult<RedisVO> queryRedis(@RequestParam("key") String key, @RequestParam("redisType") String redisType);

    /**
     * 删除缓存
     */
    @PutMapping("/delete-key")
    void deleteKey(@RequestBody DeleteRedisKeyBO deleteRedisKeyBO);

    /**
     * 新增缓存
     */
    @PostMapping("/add-redis")
    void addRedis(@Valid @RequestBody AddRedisKeyBO addRedisKeyBO);
}
