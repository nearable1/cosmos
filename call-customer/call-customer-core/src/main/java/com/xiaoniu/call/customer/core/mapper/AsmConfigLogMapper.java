package com.xiaoniu.call.customer.core.mapper;

import java.util.List;

import com.xiaoniu.call.customer.api.dto.AsmConfigLogDTO;
import com.xiaoniu.call.customer.api.vo.AsmConfigLogVO;
import com.xiaoniu.call.customer.core.entity.AsmConfigLog;

import tk.mybatis.mapper.common.Mapper;

public interface AsmConfigLogMapper extends Mapper<AsmConfigLog> {
    List<AsmConfigLogDTO> selectListByPage(AsmConfigLogVO asmConfigDataVO);
}