package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.DeleteWhiteBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.auto.WhiteList;
import com.xiaoniu.walking.web.core.model.ext.AppVersionExt;
import com.xiaoniu.walking.web.core.model.ext.WhiteListExt;
import com.xiaoniu.walking.web.core.service.WhiteListService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 白名单Controller
 *
 * @author liuyinkai
 * @date 2019/9/19
 */
@RestController
@RequestMapping("/walkingwebapi/white-list")
@Log4j2
public class WhiteListController {

    @Autowired
    private WhiteListService whiteListService;
    /**
     * 查询列表信息
     *
     * @param whiteList
     * @return
     */
    @PostMapping("/get-white-list")
    @ActionLogger(moduleName = " 白名单管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@Valid @RequestBody WhiteList whiteList) {

        PageResult<WhiteListExt> whiteListExtPageResult = whiteListService.findByPage(whiteList);

        return ResultBean.format(whiteListExtPageResult);
    }

    /**
     * 新增
     *
     * @param whiteList
     * @return
     */
    @PostMapping("/insert-white")
    @ActionLogger(moduleName = " 白名单管理", actionType = ActionLoggerCons.ADD)
    public ResultBean addWhite(@RequestBody WhiteList whiteList) {
        int i =  whiteListService.insertWhiteList(whiteList);
        return ResultBean.format(i);
    }


    /**
     * 更新
     *
     * @param whiteList
     * @return
     */
    @PostMapping("/update-white")
    @ActionLogger(moduleName = " 白名单管理", actionType = ActionLoggerCons.MODIFY)
    public ResultBean modifyWhite(@RequestBody WhiteList whiteList) {
        int i =  whiteListService.updateWhiteList(whiteList);
        return ResultBean.format(i);
    }

    /**
     * 删除
     *
     * @param deleteWhiteBO
     * @return
     */
    @PostMapping("/delete-white")
    @ActionLogger(moduleName = "白名单管理", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteWhite(@Valid @RequestBody DeleteWhiteBO deleteWhiteBO) {

        int i =  whiteListService.deleteById(deleteWhiteBO.getId());
        return ResultBean.format(i);
    }

}
