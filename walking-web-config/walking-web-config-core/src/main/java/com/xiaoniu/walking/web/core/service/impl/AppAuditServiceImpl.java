package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.walking.web.api.constants.RedisCons;
import com.xiaoniu.walking.web.core.service.AppAuditService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author :LiuYinkai
 * @date :2019-09-25 11:00.
 */
@Service
public class AppAuditServiceImpl implements AppAuditService {

    @Override
    public String dic(String bigCode, String smallCode) {
        return RedisRepository.hget(bigCode, smallCode);
    }

}
