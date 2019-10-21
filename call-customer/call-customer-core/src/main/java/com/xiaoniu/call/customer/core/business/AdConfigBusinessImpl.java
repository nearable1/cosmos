package com.xiaoniu.call.customer.core.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.business.AdConfigBusiness;
import com.xiaoniu.call.customer.api.business.AppAuditBusiness;
import com.xiaoniu.call.customer.api.dto.AdConfigDTO;
import com.xiaoniu.call.customer.api.vo.AdConfigVO;
import com.xiaoniu.call.customer.core.service.AdConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class AdConfigBusinessImpl implements AdConfigBusiness {

    @Autowired
    private AdConfigService adConfigService;

    /**
     * 管理员管理
     *
     * @param managerVO
     * @return
     */
    @PostMapping(value = "/adConfigList")
    @Override
    public PageResult<AdConfigDTO> adConfigList(@RequestBody AdConfigVO managerVO) {
        return adConfigService.list(managerVO);
    }

    /**
     * 管理员新增
     *
     * @param managerVO
     * @return
     */
    @PutMapping(value = "/saveAdConfig")
    @Override
    public void saveAdConfig(@RequestBody AdConfigVO managerVO) {
        adConfigService.save(managerVO);
    }

    /**
     * 管理员启停用
     *
     * @param managerVO
     * @return
     */
    @PutMapping(value = "/updateAdConfig")
    @Override
    public void updateAdConfig(@RequestBody AdConfigVO managerVO) {
        adConfigService.update(managerVO);
    }

    /**
     * 管理员删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteAdConfig")
    @Override
    public void deleteAdConfig(@RequestParam("id") Long id) {
        adConfigService.delete(id);
    }

    /**
     * 刷新过审配置到redis
     *
     * @return
     */
    @PostMapping(value = "/adConfig/refresh")
    @Override
    public void refresh() {
        adConfigService.refresh();
    }
}
