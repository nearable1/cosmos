package com.xiaoniu.call.customer.core.mapper;

import com.xiaoniu.call.customer.api.dto.AsmConfigDataDTO;
import com.xiaoniu.call.customer.api.vo.AsmConfigDataVO;
import com.xiaoniu.call.customer.api.vo.AsmConfigVO;
import com.xiaoniu.call.customer.core.entity.AsmConfigData;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AsmConfigDataMapper extends Mapper<AsmConfigData> {
    List<AsmConfigDataDTO> selectListByPage(AsmConfigDataVO asmConfigDataVO);
}