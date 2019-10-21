package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.walking.account.api.bo.MoneyAccountPageBO;
import com.xiaoniu.walking.account.api.bo.WkAccountBO;
import com.xiaoniu.walking.account.api.business.WalkingBackFeignBusiness;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 客户账户控制器
 *
 * @author lihoujing
 * @date 2019/9/19 16:46
 */
@RestController
@RequestMapping("/walkingwebapi/account")
@Log4j2
public class MoneyAccountController {



    @Autowired
    private WalkingBackFeignBusiness walkingBackFeignBusiness;


    @GetMapping("/list")
    @ActionLogger(moduleName = "账户管理列表", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                MoneyAccountPageBO moneyAccountBO) {
        if (pageIndex != null) {
            moneyAccountBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            moneyAccountBO.setPageSize(pageSize);
        }

        return walkingBackFeignBusiness.getBackAccountList(moneyAccountBO);
    }



    @PostMapping("/update")
    @ActionLogger(moduleName = "修改账户", actionType = ActionLoggerCons.MODIFY)
    public ResultBean updateMoneyAccount(@RequestBody WkAccountBO wkAccountBO){
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        wkAccountBO.setModifyMan(sysUser.getUserId());
        return walkingBackFeignBusiness.updateBackAccount(wkAccountBO);
    }



}
