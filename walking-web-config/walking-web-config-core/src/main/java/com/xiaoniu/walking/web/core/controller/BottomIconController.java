package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.api.vo.BottomIconVO;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.BottomIconPageBO;
import com.xiaoniu.walking.web.core.bo.BottonIconBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.auto.BottomIconMarket;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.service.BottomIconService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lihoujing
 * @date 2019/6/25 13:54
 */
@RestController
@RequestMapping("/walkingwebapi/bottom-icon")
@Log4j2
public class BottomIconController {

    @Autowired
    private BottomIconService bottomIconService;

    @GetMapping("/list")
    @ActionLogger(moduleName = "底部icon管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                BottomIconPageBO bottomIconBO) {
        if (pageIndex != null) {
            bottomIconBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            bottomIconBO.setPageSize(pageSize);
        }
        PageResult<BottomIconVO> pageResult = bottomIconService.queryList(bottomIconBO);
        return ResultBean.format(pageResult);
    }

    @PostMapping("/saveIcon")
    @ActionLogger(moduleName = "底部icon增加", actionType = ActionLoggerCons.ADD)
    public ResultBean saveIcon(BottonIconBO bottomIconBO) {
        return ResultBean.format(bottomIconService.saveIcon(bottomIconBO));
    }

    @PutMapping("/updateIcon")
    @ActionLogger(moduleName = "底部icon修改", actionType = ActionLoggerCons.MODIFY)
    public ResultBean updateIcon(BottonIconBO bottomIconBO) {
        return ResultBean.format(bottomIconService.updateIcon(bottomIconBO));
    }

    @GetMapping("/deleteIcon")
    @ActionLogger(moduleName = "底部icon删除", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteIcon(BottonIconBO bottomIconBO) {
        int count = bottomIconService.deleteIcon(bottomIconBO);
        return ResultBean.format(count);
    }




    @GetMapping("/market")
    @ActionLogger(moduleName = "获取渠道", actionType = ActionLoggerCons.QUERY)
    public ResultBean getMarket(Integer iconId) {
        List<BottomIconMarket> iconMarket = bottomIconService.getBottomIconMarket(iconId);
        List<String> market = iconMarket.parallelStream()
                .distinct()
                .map(BottomIconMarket::getMarket)
                .collect(Collectors.toList());
        return ResultBean.format(market);
    }



    /**
     * 从shiro中获取当前登录人
     * @return
     */
    public SysUser getSysUser(){
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        return user;
    }

}
