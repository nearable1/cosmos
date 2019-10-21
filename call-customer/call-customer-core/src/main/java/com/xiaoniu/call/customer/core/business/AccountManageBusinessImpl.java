package com.xiaoniu.call.customer.core.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.business.AccountManageBusiness;
import com.xiaoniu.call.customer.api.dto.AccountManageDTO;
import com.xiaoniu.call.customer.api.dto.ManagerDTO;
import com.xiaoniu.call.customer.api.dto.UserLoginDTO;
import com.xiaoniu.call.customer.api.vo.AccountManagerVO;
import com.xiaoniu.call.customer.api.vo.ManagerVO;
import com.xiaoniu.call.customer.core.service.AccountManageService;
import com.xiaoniu.call.customer.core.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class AccountManageBusinessImpl implements AccountManageBusiness {

    @Autowired
    AccountManageService accountManageService;

    @Autowired
    ManagerService managerService;

    @Override
    @PostMapping(value = "/accountManagelist")
    public PageResult<AccountManageDTO> accountManagelist(@RequestBody AccountManagerVO accountManagerVO) {
        return accountManageService.accountManageList(accountManagerVO);
    }

    @Override
    @PostMapping(value = "/customerLoginLoglist")
    public PageResult<UserLoginDTO> customerLoginLoglist(@RequestBody AccountManagerVO accountManagerVO) {
        return accountManageService.userLoginLoglist(accountManagerVO);
    }

    @Override
    @PostMapping(value = "/managerList")
    public PageResult<ManagerDTO> managerList(@RequestBody ManagerVO managerVO) {
        return accountManageService.managerList(managerVO);
    }


    /**
     * 管理员新增
     *
     * @param uid
     * @return
     */
    @PutMapping(value = "/saveManager")
    @Override
    public void saveManager(@RequestParam("uid") Long uid) {
        managerService.saveManager(uid);
    }

    /**
     * 管理员启停用
     *
     * @param id
     * @return
     */
    @PutMapping(value = "/enableManager")
    @Override
    public void enableManager(@RequestParam("id") Long id, @RequestParam("status") Boolean status) {
        managerService.enableManager(id, status);
    }

    /**
     * 管理员删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteManager")
    @Override
    public void deleteManager(@RequestParam("id") Long id) {
        managerService.deleteManager(id);
    }
}
