package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.commons.core.util.DateUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.walking.web.core.bo.AppVersionPatchBO;
import com.xiaoniu.walking.web.api.constants.RedisCons;
import com.xiaoniu.walking.web.core.mapper.ext.AppVersionPatchExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.model.ext.AppVersionPatchExt;
import com.xiaoniu.walking.web.core.service.AppVersionPatchService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * 运营banner配置管理
 *
 * @author chenguohua
 * @date 2019年5月22日12:10:25
 */
@Service
public class AppVersionPatchServiceImpl implements AppVersionPatchService {

    @Autowired
    private AppVersionPatchExtMapper appVersionPatchExtMapper;

    @Autowired
    private PageRepository pageRepository;

    /**
     * 查询列表
     *
     * @param appVersionPatchBO
     * @return
     */
    @Override
    public PageResult<AppVersionPatchExt> findAppVersionPatchByPage(AppVersionPatchBO appVersionPatchBO) {
        PageResult<AppVersionPatchExt> appVersionPatchExtPageResult = pageRepository.selectPaging(AppVersionPatchExtMapper.class, "findPatchByPage", appVersionPatchBO);
        for (AppVersionPatchExt appVersionPatchExt : appVersionPatchExtPageResult.getRows()) {
            appVersionPatchExt.setStartTimeStr(Objects.isNull(appVersionPatchExt.getStartTime()) ? "" : DateUtils.formatDateTime(appVersionPatchExt.getStartTime()));
            appVersionPatchExt.setEndTimeStr(Objects.isNull(appVersionPatchExt.getEndTime()) ? "" : DateUtils.formatDateTime(appVersionPatchExt.getEndTime()));
            appVersionPatchExt.setCreateTimeStr(Objects.isNull(appVersionPatchExt.getCreateTime()) ? "" : DateUtils.formatDateTime(appVersionPatchExt.getCreateTime()));
            appVersionPatchExt.setModifyTimeStr(Objects.isNull(appVersionPatchExt.getModifyTime()) ? "" : DateUtils.formatDateTime(appVersionPatchExt.getModifyTime()));
        }
        return appVersionPatchExtPageResult;
    }

    /**
     * add
     *
     * @param appVersionPatchExt
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertAppVersionPatch(AppVersionPatchExt appVersionPatchExt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            appVersionPatchExt.setStartTime(Objects.isNull(appVersionPatchExt.getStartTimeStr()) ? null : format.parse(appVersionPatchExt.getStartTimeStr().replace("Z", " UTC")));
            appVersionPatchExt.setEndTime(Objects.isNull(appVersionPatchExt.getEndTimeStr()) ? null : format.parse(appVersionPatchExt.getEndTimeStr().replace("Z", " UTC")));
        } catch (Exception exception) {
            exception.getMessage();
        }
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        appVersionPatchExt.setCreateMan(user.getUserId());
        appVersionPatchExt.setCreateTime(new Date());
        appVersionPatchExt.setModifyTime(new Date());
        appVersionPatchExt.setModifyMan(user.getUserId());
        RedisRepository.del(String.format(RedisCons.SYS_OS_VERSION, appVersionPatchExt.getAppName(), appVersionPatchExt.getAppType()));
        return appVersionPatchExtMapper.insertAppVersionPatch(appVersionPatchExt);
    }

    /**
     * update
     *
     * @param appVersionPatchExt
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAppVersionPatch(AppVersionPatchExt appVersionPatchExt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            appVersionPatchExt.setStartTime(Objects.isNull(appVersionPatchExt.getStartTimeStr()) ? null : format.parse(appVersionPatchExt.getStartTimeStr().replace("Z", " UTC")));
        } catch (Exception exception) {
            exception.getMessage();
        }
        try {
            appVersionPatchExt.setEndTime(Objects.isNull(appVersionPatchExt.getEndTimeStr()) ? null : format.parse(appVersionPatchExt.getEndTimeStr().replace("Z", " UTC")));
        } catch (Exception exception) {
            exception.getMessage();
        }

        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        appVersionPatchExt.setModifyTime(new Date());
        appVersionPatchExt.setModifyMan(user.getUserId());
        RedisRepository.del(String.format(RedisCons.SYS_OS_VERSION, appVersionPatchExt.getAppName(), appVersionPatchExt.getAppType()));
        return appVersionPatchExtMapper.updateAppVersionPatch(appVersionPatchExt);
    }

    /**
     * delete
     *
     * @param appVersionPatchId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAppVersionPatchByAppVersionPatchId(Integer appVersionPatchId) {
        AppVersionPatchExt appVersionPatchExt = this.queryByAppVersionPatchId(appVersionPatchId);
        RedisRepository.del(String.format(RedisCons.SYS_OS_VERSION, appVersionPatchExt.getAppName(), appVersionPatchExt.getAppType()));
        return appVersionPatchExtMapper.deleteAppVersionPatchByAppVersionPatchId(appVersionPatchId);
    }

    /**
     * 根据主键查询
     * @param appVersionPatchId
     * @return
     */
    public AppVersionPatchExt queryByAppVersionPatchId(Integer appVersionPatchId) {
        return appVersionPatchExtMapper.queryByAppVersionPatchId(appVersionPatchId);
    }

}
