package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.walking.account.api.bo.GoldFlowPageBO;
import com.xiaoniu.walking.account.api.business.WalkingBackFeignBusiness;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 金币明细
 *
 * @author lihoujing
 * @date 2019/9/19 23:11
 */
@RestController
@RequestMapping("/walkingwebapi/gold")
@Log4j2
public class GoldFlowController {


    @Autowired
    private WalkingBackFeignBusiness walkingBackFeignBusiness;


    @GetMapping("/list")
    @ActionLogger(moduleName = "金币列表", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                GoldFlowPageBO goldFlowPageBO) {
        if (pageIndex != null) {
            goldFlowPageBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            goldFlowPageBO.setPageSize(pageSize);
        }

        return walkingBackFeignBusiness.getBackGoldFlowList(goldFlowPageBO);
    }



    @GetMapping("/view")
    @ActionLogger(moduleName = "查看金币", actionType = ActionLoggerCons.QUERY)
    public ResultBean viewGoldFlow(String goldFlowId){
        return walkingBackFeignBusiness.getBackGoldFlowDetail(goldFlowId);
    }



}
