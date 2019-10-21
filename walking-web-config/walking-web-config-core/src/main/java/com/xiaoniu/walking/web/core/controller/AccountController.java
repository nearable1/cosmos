package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.WkUserBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.service.AccountService;
import com.xiaoniu.walking.web.core.vo.WkUserVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 账号管理Controller
 *
 * @author liuyinkai
 * @date 2019/9/19
 */
@RestController
@RequestMapping("/walkingwebapi/account-management")
@Log4j2
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 查询列表信息
     *
     * @param wkUserBO
     * @return
     */
    @PostMapping("/get-account-management-list")
    @ActionLogger(moduleName = " 账号管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@Valid @RequestBody WkUserBO wkUserBO) {
        PageResult<WkUserVO> accountList = accountService.getAccountList(wkUserBO);
        return ResultBean.format(accountList);
    }

}
