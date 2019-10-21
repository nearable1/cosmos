package com.xiaoniu.call.customer.core.mapper;

import com.xiaoniu.call.customer.api.vo.AsmConfigVO;
import com.xiaoniu.call.customer.core.dto.AsmConfigDTO;
import com.xiaoniu.call.customer.core.entity.AsmConfig;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AsmConfigMapper extends Mapper<AsmConfig> {
    List<AsmConfigDTO> selectByParam(@Param("rom") String rom, @Param("api") String api, @Param("manufacturer") String manufacturer, @Param("model") String model);

    List<com.xiaoniu.call.customer.api.dto.AsmConfigDTO> selectListByPage(AsmConfigVO asmConfigVO);

    List<AsmConfig> selectByAsmDataId(@Param("asmDataId") Integer asmDataId);
}