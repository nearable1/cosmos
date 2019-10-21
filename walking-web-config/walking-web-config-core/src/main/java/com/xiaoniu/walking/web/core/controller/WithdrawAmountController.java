package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.walking.account.api.bo.WithdrawAmountBO;
import com.xiaoniu.walking.account.api.bo.WithdrawAmountPageBO;
import com.xiaoniu.walking.account.api.business.WalkingBackFeignBusiness;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

/**
 * 提现金额配置
 *
 * @author lihoujing
 * @date 2019/9/21 13:30
 */
@RestController
@RequestMapping("/walkingwebapi/amount")
@Log4j2
public class WithdrawAmountController {

    @Autowired
    private WalkingBackFeignBusiness walkingBackFeignBusiness;


    @GetMapping("/list")
    @ActionLogger(moduleName = "提现金额列表", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                WithdrawAmountPageBO withdrawAmountPageBO) {
        if (pageIndex != null) {
            withdrawAmountPageBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            withdrawAmountPageBO.setPageSize(pageSize);
        }

        return walkingBackFeignBusiness.getBackWithdrawAmountList(withdrawAmountPageBO);
    }

    @PostMapping("/save")
    @ActionLogger(moduleName = "增加提现配置", actionType = ActionLoggerCons.ADD)
    public ResultBean saveWithdrawAmount(@RequestBody WithdrawAmountBO withdrawAmountBO) {
        withdrawAmountBO.setWithdrawAmountId(StringUtils.generateUUID());
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(Objects.nonNull(sysUser)){
            withdrawAmountBO.setCreateMan(sysUser.getRealName());
        }
        withdrawAmountBO.setCreateTime(new Date());
        return walkingBackFeignBusiness.saveBackWithdrawAmount(withdrawAmountBO);
    }

    @PostMapping("/edit")
    @ActionLogger(moduleName = "修改提现配置", actionType = ActionLoggerCons.MODIFY)
    public ResultBean updateWithdrawAmount(@RequestBody WithdrawAmountBO withdrawAmountBO) {
        return walkingBackFeignBusiness.editBackWithdrawAmount(withdrawAmountBO);
    }

    @GetMapping("/delete")
    @ActionLogger(moduleName = "删除提现配置", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteWithdrawAmount(String withdrawAmountId) {
        return walkingBackFeignBusiness.deleteBackWithdrawAmount(withdrawAmountId);
    }


}
