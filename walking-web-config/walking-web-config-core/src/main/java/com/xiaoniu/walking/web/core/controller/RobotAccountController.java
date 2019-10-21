package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.walking.account.api.bo.RobotAccountPageBO;
import com.xiaoniu.walking.account.api.bo.WkAccountBO;
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
 * @author lihoujing
 * @date 2019/9/22 17:37
 */
@RestController
@RequestMapping("/walkingwebapi/robot")
@Log4j2
public class RobotAccountController {

    @Autowired
    private WalkingBackFeignBusiness walkingBackFeignBusiness;


    @GetMapping("/list")
    @ActionLogger(moduleName = "机器人账户管理列表", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                RobotAccountPageBO robotAccountPageBO) {
        if (pageIndex != null) {
            robotAccountPageBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            robotAccountPageBO.setPageSize(pageSize);
        }

        return walkingBackFeignBusiness.getBackRobotAccountList(robotAccountPageBO);
    }


    @GetMapping("/delete")
    @ActionLogger(moduleName = "删除机器人账户", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteRobotAccount(String userId){
        return walkingBackFeignBusiness.deleteBackRobotAccount(userId);
    }


    @PostMapping("/save")
    @ActionLogger(moduleName = "新增机器人账户", actionType = ActionLoggerCons.ADD)
    public ResultBean saveRobotAccount(@RequestBody WkAccountBO wkAccountBO){
        return walkingBackFeignBusiness.saveBackRobotAccount(wkAccountBO);
    }


    @PostMapping("/edit")
    @ActionLogger(moduleName = "修改机器人账户", actionType = ActionLoggerCons.MODIFY)
    public ResultBean updateRobotAccount(@RequestBody WkAccountBO wkAccountBO){
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(Objects.nonNull(sysUser)){
            wkAccountBO.setModifyMan(sysUser.getRealName());
        }
        wkAccountBO.setModifyTime(new Date());
        return walkingBackFeignBusiness.updateBackRobotAccount(wkAccountBO);
    }


}
