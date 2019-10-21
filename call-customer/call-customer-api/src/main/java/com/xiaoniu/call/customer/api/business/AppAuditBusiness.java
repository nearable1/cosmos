package com.xiaoniu.call.customer.api.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.AccountManageDTO;
import com.xiaoniu.call.customer.api.dto.AppAuditDTO;
import com.xiaoniu.call.customer.api.dto.ManagerDTO;
import com.xiaoniu.call.customer.api.dto.UserLoginDTO;
import com.xiaoniu.call.customer.api.vo.AccountManagerVO;
import com.xiaoniu.call.customer.api.vo.AppAuditVO;
import com.xiaoniu.call.customer.api.vo.ManagerVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "customer", contextId = "appAuditBusiness")
public interface AppAuditBusiness {

    /**
     * 管理员管理
     *
     * @param managerVO
     * @return
     */
    @PostMapping(value = "/appAuditList")
    PageResult<AppAuditDTO> appAuditList(@RequestBody AppAuditVO managerVO);

    /**
     * 管理员新增
     *
     * @param managerVO
     * @return
     */
    @PutMapping(value = "/saveAppAudit")
    void saveAppAudit(@RequestBody AppAuditVO managerVO);

    /**
     * 管理员启停用
     *
     * @param managerVO
     * @return
     */
    @PutMapping(value = "/updateAppAudit")
    void updateAppAudit(@RequestBody AppAuditVO managerVO);

    /**
     * 管理员删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteAppAudit")
    void deleteAppAudit(@RequestParam("id") Long id);

    /**
     * 渠道列表
     *
     * @return
     */
    @PostMapping(value = "/appAudit/channel")
    String channel();

    /**
     * 版本列表
     *
     * @return
     */
    @PostMapping(value = "/appAudit/version")
    String version();

    /**
     * 获取字典值
     *
     * @param bigCode
     * @param smallCode
     * @return
     */
    @PostMapping(value = "/appAudit/dic")
    String dic(@RequestParam("bigCode") String bigCode, @RequestParam("smallCode") String smallCode);

    /**
     * 刷新过审配置到redis
     *
     * @return
     */
    @PostMapping(value = "/appAudit/refresh")
    void refresh();
}
