package com.xiaoniu.call.customer.core.service;

import com.xiaoniu.call.customer.core.dto.AppVersionDTO;

/**
 * app版本更新
 *
 * @author wuwen
 * @date 2019-07-10 17:12
 */
public interface AppVersionService {

    /**
     * 获取app版本更新
     *
     * @return
     */
    AppVersionDTO getMaxAppVersionConfig();
}
