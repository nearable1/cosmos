package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.core.util.DateUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.api.vo.SysDictVO;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.SysDictBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.ext.SysDictExt;
import com.xiaoniu.walking.web.core.service.SystemDictionaryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统字典管理Controller
 *
 * @author chenguohua
 */
@RestController
@RequestMapping("/walkingwebapi/dictionary")
@Log4j2
public class SystemDictionaryController {

    @Autowired
    private SystemDictionaryService systemDictionaryService;

    @GetMapping("/list")
    @ActionLogger(moduleName = "系统字典大类管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                SysDictBO sysdictBO) {
        PageResult<SysDictExt> sysDictExts = systemDictionaryService.query(sysdictBO);
        for (SysDictExt sysDictExt : sysDictExts.getRows()) {
            for (int i = 0; i < sysDictExts.getRows().size(); i++) {
                if (sysDictExt.getUpdateDate() == null) {
                    sysDictExt.setUpdateDateStr("-");
                } else {
                    sysDictExt.setUpdateDateStr(DateUtils.formatDateTime(sysDictExt.getUpdateDate()));
                }
                sysDictExt.setCreateDateStr(DateUtils.formatDateTime(sysDictExt.getCreateDate()));
            }
        }
        return ResultBean.format(sysDictExts);
    }

    @PostMapping("/sysdictionary")
    @ActionLogger(moduleName = "系统字典大类管理", actionType = ActionLoggerCons.ADD)
    public ResultBean insertParent(SysDictExt sysDictExt) {
        systemDictionaryService.insertParent(sysDictExt);
        return ResultBean.format(sysDictExt);
    }

    @PutMapping("/sysdictionary")
    @ActionLogger(moduleName = "系统字典大类管理", actionType = ActionLoggerCons.MODIFY)
    public ResultBean updateParent(SysDictExt sysDictExt) {
        systemDictionaryService.updateParent(sysDictExt);
        return ResultBean.format(sysDictExt);
    }

    @DeleteMapping("/sysdictionary")
    @ActionLogger(moduleName = "系统字典大类管理", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteById(SysDictExt sysDictExt) {
        systemDictionaryService.deleteByParentId(sysDictExt);
        return ResultBean.format(sysDictExt);
    }

    @GetMapping("/query-children-list")
    @ActionLogger(moduleName = "系统字典小类管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryChildrenList(Long parentId) {
        List<SysDictExt> sysDictExts = systemDictionaryService.getSysDictNodesById(parentId);
        return ResultBean.format(sysDictExts);
    }

    @PostMapping("/save-children-list")
    @ActionLogger(moduleName = "系统字典小类管理", actionType = ActionLoggerCons.ADD)
    public ResultBean saveChildrenList(SysDictExt sysDictExt) {
        return ResultBean.format(systemDictionaryService.saveChildren(sysDictExt));
    }

    @PutMapping("/edit-children-list")
    @ActionLogger(moduleName = "系统字典小类管理", actionType = ActionLoggerCons.MODIFY)
    public ResultBean editChildrenList(SysDictExt sysDictExt) {
        return ResultBean.format(systemDictionaryService.editChildrenList(sysDictExt));
    }

    @GetMapping("/{type}")
    @ActionLogger(moduleName = "系统字典预加载", actionType = ActionLoggerCons.QUERY)
    public ResultBean getSysDictNodesByType(@PathVariable String type) {
        List<SysDictVO> sysDict = systemDictionaryService.getSysDictNodesByType(type);
        return ResultBean.format(sysDict);
    }

}
