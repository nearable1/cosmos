package com.xiaoniu.call.customer.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.AppAuditDTO;
import com.xiaoniu.call.customer.api.vo.AppAuditVO;

public interface AppAuditService {

    PageResult<AppAuditDTO> list(AppAuditVO appAuditVO);

    void save(AppAuditVO appAuditVO);

    void update(AppAuditVO appAuditVO);

    void delete(Long id);

    String getAppAuditValue(Integer appName, String auditItem, String channel, String version, String defaultValue);

    void refresh();
}
