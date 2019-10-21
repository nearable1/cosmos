package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.WkStepStageRewordBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.ext.WkStepStageRewordExt;
import com.xiaoniu.walking.web.core.service.WkStepStageRewordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author :LiuYinkai
 * @date :2019-09-21 15:51.
 */
@RestController
@RequestMapping("/walkingwebapi/stage-reword-management")
@Log4j2
public class StageRewordController {

    @Autowired
    private WkStepStageRewordService wkStepStageRewordService;

    /**
     * 查询列表信息
     *
     * @param wkStepStageRewordBO
     * @return
     */
    @PostMapping("/get-list")
    @ActionLogger(moduleName = "阶段奖励管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@Valid @RequestBody WkStepStageRewordBO wkStepStageRewordBO) {
        PageResult<WkStepStageRewordExt> management = wkStepStageRewordService.getManagement(wkStepStageRewordBO);
        return ResultBean.format(management);
    }

    /**
     * 新增
     *
     * @param wkStepStageRewordBO
     * @return
     */
    @PostMapping("/reword-insert")
    @ActionLogger(moduleName = " 阶段奖励管理", actionType = ActionLoggerCons.ADD)
    public int rewordInsert(@RequestBody WkStepStageRewordBO wkStepStageRewordBO) {
        int i = wkStepStageRewordService.rewordInsert(wkStepStageRewordBO);
        return i;
    }

    /**
     * 编辑
     *
     * @param wkStepStageRewordBO
     * @return
     */
    @PostMapping("/reword-update")
    @ActionLogger(moduleName = " 阶段奖励管理", actionType = ActionLoggerCons.MODIFY)
    public int rewordUpdate(@RequestBody WkStepStageRewordBO wkStepStageRewordBO) {
        int i = wkStepStageRewordService.rewordUpdate(wkStepStageRewordBO);
        return i;
    }

    /**
     * 删除
     *
     * @param wkStepStageRewordBO
     * @return
     */
    @PostMapping("/reword-delete")
    @ActionLogger(moduleName = "阶段奖励管理", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteReword(@RequestBody WkStepStageRewordBO wkStepStageRewordBO) {

        int i =  wkStepStageRewordService.rewordDelete(wkStepStageRewordBO.getId());
        return ResultBean.format(i);
    }
}
