package com.xiaoniu.call.customer.core.service.impl;

import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.call.customer.core.dto.AppVersionDTO;
import com.xiaoniu.call.customer.core.entity.AppVersion;
import com.xiaoniu.call.customer.core.mapper.AppVersionMapper;
import com.xiaoniu.call.customer.core.service.AppVersionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * app版本更新业务
 *
 * @author wuwen
 * @date 2019-07-10 17:12
 */
@Service
@Log4j2
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    /**
     * 获取app版本更新
     *
     * @return
     */
    @Override
    public AppVersionDTO getMaxAppVersionConfig() {
        Integer osVersion = HeaderHelper.getOsVersion();
        String version = HeaderHelper.getAppVersion();
        String market = HeaderHelper.getMarket();
        AppVersion appVersion = appVersionMapper.getMaxAppVersionConfig(osVersion, version, market);
        if (null == appVersion) {
            appVersion = appVersionMapper.selectValidVersionUpdateByAPPVersionNumber(osVersion, version);
        }

        AppVersionDTO appVersionDTO = null;
        if (null != appVersion) {
            appVersionDTO = new AppVersionDTO();
            appVersionDTO.setVersionNumber(appVersion.getVersionNumber());
            appVersionDTO.setChangeDesc(appVersion.getChangeDesc());
            appVersionDTO.setIsPopup(appVersion.getIsPopup());
            appVersionDTO.setIsStronger(appVersion.getIsForcedUpdate().indexOf(version) > -1);
            appVersionDTO.setDownloadUrl(appVersion.getDownloadUrl());
        }
        return appVersionDTO;
    }
}

