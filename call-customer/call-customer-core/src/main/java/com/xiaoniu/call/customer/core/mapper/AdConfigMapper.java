package com.xiaoniu.call.customer.core.mapper;

import com.xiaoniu.call.customer.api.dto.AdConfigDTO;
import com.xiaoniu.call.customer.api.dto.AppAuditDTO;
import com.xiaoniu.call.customer.api.vo.AdConfigVO;
import com.xiaoniu.call.customer.api.vo.AppAuditVO;
import com.xiaoniu.call.customer.core.entity.AdConfig;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AdConfigMapper extends Mapper<AdConfig> {
    List<AdConfigDTO> selectListByPage(AdConfigVO adConfigVO);
}