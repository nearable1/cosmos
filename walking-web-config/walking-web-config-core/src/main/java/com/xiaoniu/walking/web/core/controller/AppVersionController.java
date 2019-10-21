package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.AppVersionBO;
import com.xiaoniu.walking.web.core.bo.AppVersionPatchBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.ext.AppVersionExt;
import com.xiaoniu.walking.web.core.model.ext.AppVersionPatchExt;
import com.xiaoniu.walking.web.core.service.AppVersionPatchService;
import com.xiaoniu.walking.web.core.service.AppVersionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * APP更新管理Controller
 *
 * @author liuyinkai
 * @date 2019年6月24日15：18
 */
@RestController
@RequestMapping("/walkingwebapi/app-version")
@Log4j2
public class AppVersionController {

    @Autowired
    AppVersionService appVersionService;

    @Autowired
    AppVersionPatchService appVersionPatchService;

    /**
     * 查询APP更新信息
     * @param pageIndex
     * @param pageSize
     * @param appVersionBO
     * @return
     */
    @GetMapping("/get-app-version")
    @ActionLogger(moduleName = " APP更新管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                AppVersionBO appVersionBO) {
        if (pageIndex != null) {
            appVersionBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            appVersionBO.setPageSize(pageSize);
        }
        if (Objects.nonNull(appVersionBO.getTimes())) {
            appVersionBO.setSTime(appVersionBO.getTimes()[0]);
            appVersionBO.setETime(appVersionBO.getTimes()[1]);
        }
        PageResult<AppVersionExt> appVersionPageResult = appVersionService.findAppVersionByPage(appVersionBO);
        return ResultBean.format(appVersionPageResult);
    }

    /**
     * 新增APP更新信息
     * @param appVersionExt
     * @return
     */
    @PostMapping("/save-app-version")
    @ActionLogger(moduleName = " APP更新管理", actionType = ActionLoggerCons.ADD)
    public ResultBean addAppVersion(@RequestBody AppVersionExt appVersionExt) {
        int i =  appVersionService.insertAppVersion(appVersionExt);
        return ResultBean.format(i);
    }

    /**
     * 修改APP更新信息
     * @param appVersionExt
     * @return
     */
    @PostMapping("/edit-app-version")
    @ActionLogger(moduleName = " APP更新管理", actionType = ActionLoggerCons.MODIFY)
    public ResultBean modifyAppVersion(@RequestBody AppVersionExt appVersionExt) {
        int i =  appVersionService.updateAppVersion(appVersionExt);
        return ResultBean.format(i);
    }

    /**
     * 删除
     *
     * @param appVersionId
     * @return
     */
    @DeleteMapping("/delete-app-version")
    @ActionLogger(moduleName = "banner删除", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteAppVersion(Integer appVersionId) {

        int i =  appVersionService.deleteAppVersionByAppVersionId(appVersionId);
        return ResultBean.format(i);
    }

    /**
     * 查询APP更新bud信息
     * @param pageIndex
     * @param pageSize
     * @param appVersionPatchBO
     * @return
     */
    @GetMapping("/query-patch-list")
    @ActionLogger(moduleName = " APP更新管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryPatchList(@RequestParam(required = false) Integer pageIndex,
                                     @RequestParam(required = false) Integer pageSize,
                                     AppVersionPatchBO appVersionPatchBO) {
        if (pageIndex != null) {
            appVersionPatchBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            appVersionPatchBO.setPageSize(pageSize);
        }

        PageResult<AppVersionPatchExt> appVersionPageResult = appVersionPatchService.findAppVersionPatchByPage(appVersionPatchBO);
        return ResultBean.format(appVersionPageResult);
    }

    /**
     * 新增APP更新补丁
     * @param appVersionPatchExt
     * @return
     */
    @PostMapping("/save-patch-list")
    @ActionLogger(moduleName = " APP更新管理", actionType = ActionLoggerCons.ADD)
    public ResultBean addAppVersionPatch(@RequestBody AppVersionPatchExt appVersionPatchExt) {
        int i =  appVersionPatchService.insertAppVersionPatch(appVersionPatchExt);
        return ResultBean.format(i);
    }

    /**
     * 删除patch
     *
     * @param appVersionPatchId
     * @return
     */
    @DeleteMapping("/delete-patch")
    @ActionLogger(moduleName = "banner删除", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteAppVersionPatch(Integer appVersionPatchId) {

        int i =  appVersionPatchService.deleteAppVersionPatchByAppVersionPatchId(appVersionPatchId);
        return ResultBean.format(i);
    }

    /**
     * 修改APP更新信息
     * @param appVersionPatchExt
     * @return
     */
    @PostMapping("/edit-patch")
    @ActionLogger(moduleName = " 补丁更新管理", actionType = ActionLoggerCons.MODIFY)
    public ResultBean modifyAppVersionPatch(@RequestBody AppVersionPatchExt appVersionPatchExt) {
        int i =  appVersionPatchService.updateAppVersionPatch(appVersionPatchExt);
        return ResultBean.format(i);
    }
}
