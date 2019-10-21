package com.xiaoniu.call.video.core.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.AddRedisKeyBO;
import com.xiaoniu.call.video.api.bo.DeleteRedisKeyBO;
import com.xiaoniu.call.video.api.business.RedisManagementWebBusiness;
import com.xiaoniu.call.video.api.vo.RedisVO;
import com.xiaoniu.call.video.core.service.RedisManagementWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存管理
 *
 * @author liuyinkai
 * @date 2019-08-07
 */
@RestController
public class RedisManagementWebBusinessImpl implements RedisManagementWebBusiness {


    @Autowired
    private RedisManagementWebService redisManagementWebService;


    /**
     * query
     *
     * @param key
     */
    @Override
    public PageResult<RedisVO> queryRedis(String key, String redisType) {
        return redisManagementWebService.queryRedis(key, redisType);
    }

    /**
     * delete
     *
     * @param deleteRedisKeyBO
     */
    @Override
    public void deleteKey(DeleteRedisKeyBO deleteRedisKeyBO) {
        redisManagementWebService.deleteKey(deleteRedisKeyBO);
    }

    /**
     * inert
     *
     * @param addRedisKeyBO
     */
    @Override
    public void addRedis(AddRedisKeyBO addRedisKeyBO) {
        redisManagementWebService.addRedis(addRedisKeyBO);
    }


}
