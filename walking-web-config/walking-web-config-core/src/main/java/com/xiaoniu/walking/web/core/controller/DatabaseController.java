package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.core.util.DateUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.DatabaseBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.auto.Database;
import com.xiaoniu.walking.web.core.service.DatabaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据库管理Controller
 *
 * @author chenguohua
 * @date 2019/04/23 10:26
 */
@RestController
@RequestMapping("/walkingwebapi/database")
@Log4j2
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/list")
    @ActionLogger(moduleName = "数据库管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                DatabaseBO databaseBO) {
        if (pageIndex != null) {
            databaseBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            databaseBO.setPageSize(pageSize);
        }
        PageResult<Database> databases = databaseService.queryDatabaseByPage(databaseBO);

        for (Database database : databases.getRows()) {
            for (int i = 0; i < databases.getRows().size(); i++) {
                database.setCreateTimeStr(DateUtils.formatDateTime(database.getCreateTime()));
            }
        }

        return ResultBean.format(databases);
    }


}
