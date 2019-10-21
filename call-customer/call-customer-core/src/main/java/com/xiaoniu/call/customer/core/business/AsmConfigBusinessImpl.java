package com.xiaoniu.call.customer.core.business;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.business.AsmConfigBusiness;
import com.xiaoniu.call.customer.api.dto.AsmConfigDTO;
import com.xiaoniu.call.customer.api.dto.AsmConfigDataDTO;
import com.xiaoniu.call.customer.api.dto.AsmConfigLogDTO;
import com.xiaoniu.call.customer.api.vo.AsmConfigDataVO;
import com.xiaoniu.call.customer.api.vo.AsmConfigLogVO;
import com.xiaoniu.call.customer.api.vo.AsmConfigVO;
import com.xiaoniu.call.customer.core.service.AsmConfigService;

import javax.validation.Valid;

@Controller
@ResponseBody
public class AsmConfigBusinessImpl implements AsmConfigBusiness {

    @Autowired
    private AsmConfigService asmConfigService;


    @Override
    @PostMapping("/queryAsmConfigList")
    public PageResult<AsmConfigDTO> queryAsmConfigList(@Valid @RequestBody AsmConfigVO asmConfigVO) {
        return asmConfigService.queryAsmConfigList(asmConfigVO);
    }

    @Override
    @PostMapping("/queryAsmConfigDataList")
    public PageResult<AsmConfigDataDTO> queryAsmConfigDataList(@Valid @RequestBody AsmConfigDataVO asmConfigDataVO) {
        return asmConfigService.queryAsmConfigDataList(asmConfigDataVO);
    }

    @Override
    @PostMapping("/queryAsmConfigLogList")
    public PageResult<AsmConfigLogDTO> queryAsmConfigLogList(@Valid @RequestBody AsmConfigLogVO asmConfigVO) {
        return asmConfigService.queryAsmConfigLogList(asmConfigVO);
    }

    @Override
    @PostMapping("/saveAsmConfig")
    public void saveAsmConfig(@Valid @RequestBody AsmConfigVO asmConfigVO) {
        asmConfigService.saveAsmConfig(asmConfigVO);
    }

    @Override
    @PutMapping("/updateAsmConfig")
    public void updateAsmConfig(@Valid @RequestBody AsmConfigVO asmConfigVO) {
        asmConfigService.updateAsmConfig(asmConfigVO);
    }

    @Override
    @DeleteMapping("/deleteAsmConfig")
    public void deleteAsmConfig(@RequestParam("id") Integer id) {
        asmConfigService.deleteAsmConfig(id);
    }

    @Override
    @PostMapping("/saveAsmConfigData")
    public void saveAsmConfigData(@Valid @RequestBody AsmConfigDataVO asmConfigDataVO) {
        asmConfigService.saveAsmConfigData(asmConfigDataVO);
    }

    @Override
    @PutMapping("/updateAsmConfigData")
    public void updateAsmConfigData(@Valid @RequestBody AsmConfigDataVO asmConfigDataVO) {
        asmConfigService.updateAsmConfigData(asmConfigDataVO);
    }

    @Override
    @DeleteMapping("/deleteAsmConfigData")
    public void deleteAsmConfigData(@RequestParam("id") Integer id) {
        asmConfigService.deleteAsmConfigData(id);
    }

    @Override
    @PutMapping("/updateAsmConfigLog")
    public void updateAsmConfigLog(@Valid @RequestBody AsmConfigLogVO asmConfigDataVO) {
        asmConfigService.updateAsmConfigLog(asmConfigDataVO);
    }

    @Override
    @DeleteMapping("/deleteAsmConfigLog")
    public void deleteAsmConfigLog(@RequestParam("id")Integer id) {
        asmConfigService.deleteAsmConfigLog(id);
    }
}
