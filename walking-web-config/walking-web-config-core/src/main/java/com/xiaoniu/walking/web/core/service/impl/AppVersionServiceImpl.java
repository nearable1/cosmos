package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.commons.core.util.DateUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.walking.web.api.vo.AppVersionVO;
import com.xiaoniu.walking.web.core.bo.AppVersionBO;
import com.xiaoniu.walking.web.core.bo.QueryAppVersionBO;
import com.xiaoniu.walking.web.api.constants.RedisCons;
import com.xiaoniu.walking.web.core.mapper.ext.AppVersionExpMapper;
import com.xiaoniu.walking.web.core.mapper.ext.AppVersionPatchExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.model.ext.AppVersionExt;
import com.xiaoniu.walking.web.core.model.ext.AppVersionPatchExt;
import com.xiaoniu.walking.web.core.service.AppVersionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionExpMapper appVersionExpMapper;

    @Autowired
    private AppVersionPatchExtMapper appVersionPatchExtMapper;

    @Autowired
    private PageRepository pageRepository;
    /**
     * 查询列表
     *
     * @param appVersionBO
     * @return
     */
    @Override
    public PageResult<AppVersionExt> findAppVersionByPage(AppVersionBO appVersionBO) {
        PageResult<AppVersionExt> appVersionExtPageResult = pageRepository.selectPaging(AppVersionExpMapper.class, "findAppVersionByPage", appVersionBO);
        for (AppVersionExt appVersionExt : appVersionExtPageResult.getRows()) {
            appVersionExt.setStartTimeStr(Objects.isNull(appVersionExt.getStartTime()) ? "" : DateUtils.formatDateTime(appVersionExt.getStartTime()));
            appVersionExt.setEndTimeStr(Objects.isNull(appVersionExt.getEndTime()) ? "" : DateUtils.formatDateTime(appVersionExt.getEndTime()));
            appVersionExt.setCreateTimeStr(Objects.isNull(appVersionExt.getCreateTime()) ? "" : DateUtils.formatDateTime(appVersionExt.getCreateTime()));
            appVersionExt.setModifyTimeStr(Objects.isNull(appVersionExt.getModifyTime()) ? "" : DateUtils.formatDateTime(appVersionExt.getModifyTime()));
        }
        return appVersionExtPageResult;
    }

    /**
     * add
     *
     * @param appVersionExt
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertAppVersion(@RequestBody AppVersionExt appVersionExt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            appVersionExt.setStartTime(Objects.isNull(appVersionExt.getStartTimeStr()) ? null : format.parse(appVersionExt.getStartTimeStr().replace("Z", " UTC")));
            appVersionExt.setEndTime(Objects.isNull(appVersionExt.getEndTimeStr()) ? null : format.parse(appVersionExt.getEndTimeStr().replace("Z", " UTC")));
        } catch (Exception exception) {
            exception.getMessage();
        }
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        appVersionExt.setCreateMan(user.getUserId());
        appVersionExt.setCreateTime(new Date());
        appVersionExt.setModifyTime(new Date());
        appVersionExt.setModifyMan(user.getUserId());
        RedisRepository.del(String.format(RedisCons.SYS_OS_VERSION, appVersionExt.getAppName(), appVersionExt.getAppType()));
        int count =  appVersionExpMapper.insertAppVersion(appVersionExt);
        return count;
    }

    /**
     * update
     *
     * @param appVersionExt
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAppVersion(@RequestBody AppVersionExt appVersionExt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            appVersionExt.setStartTime(Objects.isNull(appVersionExt.getStartTimeStr()) ? null : format.parse(appVersionExt.getStartTimeStr().replace("Z", " UTC")));
        } catch (Exception exception) {
            exception.getMessage();
        }
        try {
            appVersionExt.setEndTime(Objects.isNull(appVersionExt.getEndTimeStr()) ? null : format.parse(appVersionExt.getEndTimeStr().replace("Z", " UTC")));
        } catch (Exception exception) {
            exception.getMessage();
        }

        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        appVersionExt.setModifyTime(new Date());
        appVersionExt.setModifyMan(user.getUserId());
        RedisRepository.del(String.format(RedisCons.SYS_OS_VERSION, appVersionExt.getAppName(), appVersionExt.getAppType()));
        int count = appVersionExpMapper.updateAppVersion(appVersionExt);
        return count;
    }

    /**
     * delete
     *
     * @param appVersionId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAppVersionByAppVersionId(Integer appVersionId) {
        AppVersionExt appVersionExt = this.queryByAppVersionId(appVersionId);
        RedisRepository.del(String.format(RedisCons.SYS_OS_VERSION, appVersionExt.getAppName(), appVersionExt.getAppType()));
        int count = appVersionExpMapper.deleteAppVersionByAppVersionId(appVersionId);
        if (count > 0) {
            appVersionPatchExtMapper.deleteAppVersionPatchByAppVersionId(appVersionId);
        }
        return count;
    }

    /**
     * 查询app更新
     * @param queryAppVersionBO
     * @return
     */
    @Override
    public AppVersionVO getAppVersion(QueryAppVersionBO queryAppVersionBO) {
        //查询数据库
        AppVersionVO appVersionVO = appVersionExpMapper.getAppVersion(queryAppVersionBO);
        if (Objects.nonNull(appVersionVO)) {

            AppVersionPatchExt appVersionPatch = appVersionPatchExtMapper.getAppVersionPatch(appVersionVO.getAppVersionId());
            if (Objects.nonNull(appVersionPatch)) {
                appVersionVO.setAppVersionPatch(appVersionPatch.getAppVersion());
                appVersionVO.setUpdateUrlPatch(appVersionPatch.getUpdateUrl());
                appVersionVO.setAppVersionCodePatch(appVersionPatch.getAppVersionCode());
                appVersionVO.setEncryptCodePatch(appVersionPatch.getEncryptCode());
            }
        }
        return appVersionVO;
    }

    /**
     * 根据主键查询
     * @param appVersionId
     * @return
     */
    @Override
    public AppVersionExt queryByAppVersionId(Integer appVersionId) {
        return appVersionExpMapper.queryByAppVersionId(appVersionId);
    }
}
