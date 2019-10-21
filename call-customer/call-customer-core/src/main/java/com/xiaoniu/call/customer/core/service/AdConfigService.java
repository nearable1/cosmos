package com.xiaoniu.call.customer.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.AdConfigDTO;
import com.xiaoniu.call.customer.api.dto.AppAuditDTO;
import com.xiaoniu.call.customer.api.vo.AdConfigVO;
import com.xiaoniu.call.customer.api.vo.AppAuditVO;

public interface AdConfigService {

    PageResult<AdConfigDTO> list(AdConfigVO appAuditVO);

    void save(AdConfigVO appAuditVO);

    void update(AdConfigVO appAuditVO);

    void delete(Long id);

    String getAdConfig(Integer appName, String defaultValue);

    void refresh();
}
