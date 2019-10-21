package com.xiaoniu.call.customer.api.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.AccountManageDTO;
import com.xiaoniu.call.customer.api.dto.ManagerDTO;
import com.xiaoniu.call.customer.api.dto.UserLoginDTO;
import com.xiaoniu.call.customer.api.vo.AccountManagerVO;
import com.xiaoniu.call.customer.api.vo.ManagerVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "customer", contextId = "accountManageBusiness")
public interface AccountManageBusiness {

    /**
     * 账号管理
     *
     * @param accountManagerVO
     * @return
     */
    @PostMapping(value = "/accountManagelist")
    PageResult<AccountManageDTO> accountManagelist(@RequestBody AccountManagerVO accountManagerVO);

    /**
     * 用户登录日志
     *
     * @param accountManagerVO
     * @return
     */
    @PostMapping(value = "/customerLoginLoglist")
    PageResult<UserLoginDTO> customerLoginLoglist(@RequestBody AccountManagerVO accountManagerVO);

    /**
     * 管理员管理
     *
     * @param managerVO
     * @return
     */
    @PostMapping(value = "/managerList")
    PageResult<ManagerDTO> managerList(@RequestBody ManagerVO managerVO);

    /**
     * 管理员新增
     *
     * @param uid
     * @return
     */
    @PutMapping(value = "/saveManager")
    void saveManager(@RequestParam("uid") Long uid);

    /**
     * 管理员启停用
     *
     * @param id
     * @return
     */
    @PutMapping(value = "/enableManager")
    void enableManager(@RequestParam("id") Long id, @RequestParam("status") Boolean status);

    /**
     * 管理员删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteManager")
    void deleteManager(@RequestParam("id") Long id);
}
