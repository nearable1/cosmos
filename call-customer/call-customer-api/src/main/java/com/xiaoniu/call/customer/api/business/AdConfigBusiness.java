package com.xiaoniu.call.customer.api.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.AdConfigDTO;
import com.xiaoniu.call.customer.api.dto.AppAuditDTO;
import com.xiaoniu.call.customer.api.vo.AdConfigVO;
import com.xiaoniu.call.customer.api.vo.AppAuditVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "customer", contextId = "adConfigBusiness")
public interface AdConfigBusiness {

    /**
     * 管理员管理
     *
     * @param managerVO
     * @return
     */
    @PostMapping(value = "/adConfigList")
    PageResult<AdConfigDTO> adConfigList(@RequestBody AdConfigVO managerVO);

    /**
     * 管理员新增
     *
     * @param managerVO
     * @return
     */
    @PutMapping(value = "/saveAdConfig")
    void saveAdConfig(@RequestBody AdConfigVO managerVO);

    /**
     * 管理员启停用
     *
     * @param managerVO
     * @return
     */
    @PutMapping(value = "/updateAdConfig")
    void updateAdConfig(@RequestBody AdConfigVO managerVO);

    /**
     * 管理员删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteAdConfig")
    void deleteAdConfig(@RequestParam("id") Long id);

    /**
     * 刷新过审配置到redis
     *
     * @return
     */
    @PostMapping(value = "/adConfig/refresh")
    void refresh();
}
