package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.core.util.DateUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.SysLogBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.ext.SysLogExt;
import com.xiaoniu.walking.web.core.service.SysLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 系统操作日志管理Controller
 *
 * @author chenguohua
 * @date 2019/04/23 10:26
 */
@RestController
@RequestMapping("/walkingwebapi/sys-log")
@Log4j2
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/list")
    @ActionLogger(moduleName = "系统操作日志", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                SysLogBO sysLogBO) {

        if (pageIndex != null) {
            sysLogBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            sysLogBO.setPageSize(pageSize);
        }

        if (Objects.nonNull(sysLogBO.getTimes())) {
            sysLogBO.setSTime(sysLogBO.getTimes()[0]);
            sysLogBO.setETime(sysLogBO.getTimes()[1]);
        }

        PageResult<SysLogExt> sysLogExtPageResult = sysLogService.querySysLogByPage(sysLogBO);

        for (SysLogExt sysLogExt : sysLogExtPageResult.getRows()) {
            sysLogExt.setCreateDateStr(DateUtils.formatDateTime(sysLogExt.getCreateTime()));
        }
        return ResultBean.format(sysLogExtPageResult);
    }


}
