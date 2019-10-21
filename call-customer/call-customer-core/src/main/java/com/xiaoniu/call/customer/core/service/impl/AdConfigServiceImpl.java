package com.xiaoniu.call.customer.core.service.impl;

import com.xiaoniu.architecture.commons.core.util.BeanCopierUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.customer.api.dto.AdConfigDTO;
import com.xiaoniu.call.customer.api.vo.AdConfigVO;
import com.xiaoniu.call.customer.core.cons.RedisCons;
import com.xiaoniu.call.customer.core.entity.AdConfig;
import com.xiaoniu.call.customer.core.mapper.AdConfigMapper;
import com.xiaoniu.call.customer.core.service.AdConfigService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdConfigServiceImpl implements AdConfigService {

    @Autowired
    private AdConfigMapper adConfigMapper;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageResult<AdConfigDTO> list(AdConfigVO appAuditVO) {
        PageResult<AdConfigDTO> pageResult = pageRepository.selectPaging(AdConfigMapper.class, "selectListByPage", appAuditVO);
        return pageResult;
    }

    @Override
    public void save(AdConfigVO appAuditVO) {
        AdConfig appAudit = BeanCopierUtils.copy(appAuditVO, AdConfig.class);
        adConfigMapper.insertSelective(appAudit);
        RedisRepository.hset(RedisCons.AD_CONFIG_KEY, String.valueOf(appAudit.getAppName()), String.valueOf(appAudit.getAdType()));

    }

    @Override
    public void update(AdConfigVO appAuditVO) {
        AdConfig appAudit = BeanCopierUtils.copy(appAuditVO, AdConfig.class);
        AdConfig oldAdConfig = adConfigMapper.selectByPrimaryKey(appAuditVO.getId());
        // 删除缓存
        if (Objects.nonNull(oldAdConfig)) {
            RedisRepository.hdel(RedisCons.AD_CONFIG_KEY, String.valueOf(oldAdConfig.getAppName()));
        }
        adConfigMapper.updateByPrimaryKeySelective(appAudit);
        RedisRepository.hset(RedisCons.AD_CONFIG_KEY, String.valueOf(appAudit.getAppName()), String.valueOf(appAudit.getAdType()));
    }

    @Override
    public void delete(Long id) {
        // 查询过审项名字
        AdConfig oldAdConfig = adConfigMapper.selectByPrimaryKey(id);
        // 删除缓存
        if (Objects.nonNull(oldAdConfig)) {
            RedisRepository.hdel(RedisCons.AD_CONFIG_KEY, String.valueOf(oldAdConfig.getAppName()));
        }
        // 删除数据库
        adConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public String getAdConfig(Integer appName, String defaultValue) {
        String key = RedisCons.AD_CONFIG_KEY;
        if (!RedisRepository.hexists(key, String.valueOf(appName))) {
            // 不存在该键返回默认值
            return defaultValue;
        }

        String value = RedisRepository.hget(key, String.valueOf(appName));
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public void refresh() {
        AdConfigVO appAuditVO = new AdConfigVO();
        appAuditVO.setPageSize(200);
        PageResult<AdConfigDTO> pageResult;
        int i = 1;
        while (true) {
            appAuditVO.setPageIndex(i++);
            appAuditVO.setPageSize(200);
            pageResult = pageRepository.selectPaging(AdConfigMapper.class, "selectListByPage", appAuditVO);
            if (CollectionUtils.isEmpty(pageResult.getRows())) {
                return;
            }
            for (AdConfigDTO dto : pageResult.getRows()) {
                RedisRepository.hset(RedisCons.AD_CONFIG_KEY, dto.getAppName(), dto.getAdType());
            }
        }
    }
}
