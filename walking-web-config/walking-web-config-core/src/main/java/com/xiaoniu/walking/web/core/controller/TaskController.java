package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.activity.api.bo.WkTaskBO;
import com.xiaoniu.walking.activity.api.business.WalkingActivityFeignBusiness;
import com.xiaoniu.walking.activity.api.vo.WkTaskVO;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 任务管理Controller
 *
 * @author shenguoqing
 * @date 2019/9/24
 */
@RestController
@RequestMapping("/walkingwebapi/task")
@Log4j2
public class TaskController {

    @Autowired
    private WalkingActivityFeignBusiness walkingActivityFeignBusiness;

    /**
     * 查询列表信息
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ActionLogger(moduleName = " 任务管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam Integer pageIndex,
                                @RequestParam Integer pageSize,
                                @RequestParam(required = false) Integer appName) {
        PageResult<WkTaskVO> list = walkingActivityFeignBusiness.taskList(pageIndex, pageSize,appName);
        return ResultBean.format(list);
    }

    /**
     * 新增
     *
     * @param wkTaskBO
     * @return
     */
    @PostMapping("/save")
    @ActionLogger(moduleName = " 任务新增", actionType = ActionLoggerCons.QUERY)
    public ResultBean saveTask(@Valid @RequestBody WkTaskBO wkTaskBO) {
        return walkingActivityFeignBusiness.saveTask(wkTaskBO);
    }
    /**
     * 修改
     *
     * @param wkTaskBO
     * @return
     */
    @PutMapping("/update")
    @ActionLogger(moduleName = " 任务更新", actionType = ActionLoggerCons.QUERY)
    public ResultBean updateTask(@Valid @RequestBody WkTaskBO wkTaskBO) {
        return walkingActivityFeignBusiness.updateTask(wkTaskBO);
    }
}
