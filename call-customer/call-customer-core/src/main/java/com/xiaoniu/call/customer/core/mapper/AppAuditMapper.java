package com.xiaoniu.call.customer.core.mapper;

import com.xiaoniu.call.customer.api.dto.AppAuditDTO;
import com.xiaoniu.call.customer.api.vo.AppAuditVO;
import com.xiaoniu.call.customer.core.entity.AppAudit;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AppAuditMapper extends Mapper<AppAudit> {
    List<AppAuditDTO> selectListByPage(AppAuditVO appAuditVO);

    /**
     * 根据逐渐查询
     *
     * @param id
     * @return
     */
    AppAuditDTO selectById(Long id);
}