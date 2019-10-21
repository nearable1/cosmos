package com.xiaoniu.call.customer.api.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.AsmConfigDTO;
import com.xiaoniu.call.customer.api.dto.AsmConfigDataDTO;
import com.xiaoniu.call.customer.api.dto.AsmConfigLogDTO;
import com.xiaoniu.call.customer.api.vo.AsmConfigDataVO;
import com.xiaoniu.call.customer.api.vo.AsmConfigLogVO;
import com.xiaoniu.call.customer.api.vo.AsmConfigVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName AsmConfigBusiness
 * @Author
 * @Date 2019/1/2 18:39
 * @Version 1.0
 * @Description TODO
 */
@FeignClient(value = "customer", contextId = "asmConfigBusiness")
public interface AsmConfigBusiness {

    @PostMapping("/queryAsmConfigList")
    PageResult<AsmConfigDTO> queryAsmConfigList(@Valid @RequestBody AsmConfigVO asmConfigVO);

    @PostMapping("/queryAsmConfigDataList")
    PageResult<AsmConfigDataDTO> queryAsmConfigDataList(@Valid @RequestBody AsmConfigDataVO asmConfigDataVO);

    @PostMapping("/queryAsmConfigLogList")
    PageResult<AsmConfigLogDTO> queryAsmConfigLogList(@Valid @RequestBody AsmConfigLogVO asmConfigVO);

    @PostMapping("/saveAsmConfig")
    void saveAsmConfig(@Valid @RequestBody AsmConfigVO asmConfigVO);

    @PutMapping("/updateAsmConfig")
    void updateAsmConfig(@Valid @RequestBody AsmConfigVO asmConfigVO);

    @DeleteMapping("/deleteAsmConfig")
    void deleteAsmConfig(@RequestParam("id") Integer id);

    @PostMapping("/saveAsmConfigData")
    void saveAsmConfigData(@Valid @RequestBody AsmConfigDataVO asmConfigDataVO);

    @PutMapping("/updateAsmConfigData")
    void updateAsmConfigData(@Valid @RequestBody AsmConfigDataVO asmConfigDataVO);

    @DeleteMapping("/deleteAsmConfigData")
    void deleteAsmConfigData(@RequestParam("id") Integer id);

    @PutMapping("/updateAsmConfigLog")
    void updateAsmConfigLog(@Valid @RequestBody AsmConfigLogVO asmConfigVO);

    @DeleteMapping("/deleteAsmConfigLog")
    void deleteAsmConfigLog(@RequestParam("id") Integer id);

}
