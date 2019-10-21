package com.xiaoniu.call.customer.core.service.impl;

import com.xiaoniu.architecture.commons.core.util.BeanCopierUtils;
import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.customer.api.dto.AppAuditDTO;
import com.xiaoniu.call.customer.api.vo.AppAuditVO;
import com.xiaoniu.call.customer.core.cons.RedisCons;
import com.xiaoniu.call.customer.core.entity.AppAudit;
import com.xiaoniu.call.customer.core.mapper.AppAuditMapper;
import com.xiaoniu.call.customer.core.service.AppAuditService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AppAuditServiceImpl implements AppAuditService {

    @Autowired
    private AppAuditMapper auditMapper;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageResult<AppAuditDTO> list(AppAuditVO appAuditVO) {
        PageResult<AppAuditDTO> pageResult = pageRepository.selectPaging(AppAuditMapper.class, "selectListByPage", appAuditVO);
        return pageResult;
    }

    @Override
    public void save(AppAuditVO appAuditVO) {
        AppAudit appAudit = BeanCopierUtils.copy(appAuditVO, AppAudit.class);
        auditMapper.insertSelective(appAudit);
        RedisRepository.hset(RedisCons.APP_AUDIT_KEY + appAudit.getAppName() + ":" + appAudit.getAuditItem(), appAudit.getChannel(), appAudit.getVersion());
    }

    @Override
    @Transactional
    public void update(AppAuditVO appAuditVO) {
        AppAudit appAudit = BeanCopierUtils.copy(appAuditVO, AppAudit.class);
        AppAuditDTO appAuditDTO = auditMapper.selectById(appAuditVO.getId());
        // 删除缓存
        if (Objects.nonNull(appAuditDTO)) {
            RedisRepository.hdel(RedisCons.APP_AUDIT_KEY + appAuditDTO.getAppName() + ":" + appAuditDTO.getAuditItem(), appAuditDTO.getChannel());
        }
        auditMapper.updateByPrimaryKeySelective(appAudit);
        RedisRepository.hset(RedisCons.APP_AUDIT_KEY + appAudit.getAppName() + ":" +appAudit.getAuditItem(), appAudit.getChannel(), appAudit.getVersion());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 查询过审项名字
        AppAuditDTO appAuditDTO = auditMapper.selectById(id);
        // 删除缓存
        if (Objects.nonNull(appAuditDTO)) {
            RedisRepository.hdel(RedisCons.APP_AUDIT_KEY + appAuditDTO.getAppName() + ":" + appAuditDTO.getAuditItem(), appAuditDTO.getChannel());
        }
        // 删除数据库
        auditMapper.deleteByPrimaryKey(id);
    }

    @Override
    public String getAppAuditValue(Integer appName, String auditItem, String channel, String version, String defaultValue) {
        String key = RedisCons.APP_AUDIT_KEY + appName + ":" + auditItem;
        String value = RedisRepository.hget(key, channel);

        if (!RedisRepository.hexists(key, channel)) {
            // 不存在该键返回默认值
            return defaultValue;
        }

        // 为了精准匹配，前后加逗号再查找
        value = new StringBuilder().append(",").append(value).append(",").toString();
        version = new StringBuilder().append(",").append(version).append(",").toString();
        if (value.contains(version)) {
            return "true";
        }
        return "false";
    }

    @Override
    public void refresh() {
        AppAuditVO appAuditVO = new AppAuditVO();
        appAuditVO.setPageSize(200);
        PageResult<AppAuditDTO> pageResult;
        int i = 1;
        while (true) {
            appAuditVO.setPageIndex(i++);
            appAuditVO.setPageSize(200);
            pageResult = pageRepository.selectPaging(AppAuditMapper.class, "selectListByPage", appAuditVO);
            if (CollectionUtils.isEmpty(pageResult.getRows())) {
                return;
            }
            for (AppAuditDTO dto : pageResult.getRows()) {
                RedisRepository.hset(RedisCons.APP_AUDIT_KEY + dto.getAppName() + ":" + dto.getAuditItem(), dto.getChannel(), dto.getVersion());
            }
        }
    }
}
