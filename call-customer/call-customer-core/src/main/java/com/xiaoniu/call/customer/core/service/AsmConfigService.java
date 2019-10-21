package com.xiaoniu.call.customer.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.AsmConfigDTO;
import com.xiaoniu.call.customer.api.dto.AsmConfigDataDTO;
import com.xiaoniu.call.customer.api.dto.AsmConfigLogDTO;
import com.xiaoniu.call.customer.api.vo.AsmConfigDataVO;
import com.xiaoniu.call.customer.api.vo.AsmConfigLogVO;
import com.xiaoniu.call.customer.api.vo.AsmConfigVO;

/**
 * @ClassName DataDictService
 * @Author
 * @Date 2019/1/2 18:39
 * @Version 1.0
 * @Description TODO
 */
public interface AsmConfigService {
    PageResult<AsmConfigDTO> queryAsmConfigList(AsmConfigVO asmConfigVO);

    PageResult<AsmConfigDataDTO> queryAsmConfigDataList(AsmConfigDataVO asmConfigDataVO);

    PageResult<AsmConfigLogDTO> queryAsmConfigLogList(AsmConfigLogVO asmConfigVO);

    void saveAsmConfig(AsmConfigVO asmConfigVO);

    void updateAsmConfig(AsmConfigVO asmConfigVO);

    void deleteAsmConfig(Integer id);

    void saveAsmConfigData(AsmConfigDataVO asmConfigDataVO);

    void updateAsmConfigData(AsmConfigDataVO asmConfigDataVO);

    void deleteAsmConfigData(Integer id);

    void updateAsmConfigLog(AsmConfigLogVO asmConfigDataVO);

    void deleteAsmConfigLog(Integer id);
}
