package com.xiaoniu.walking.web.core.controller;


import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.SysRoleBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.auto.SysRole;
import com.xiaoniu.walking.web.core.service.SysRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lihoujing
 * @date: 2019/4/15 9:34
 * @description: 角色信息
 */
@RestController
@RequestMapping("/walkingwebapi/role")
public class RoleInfoController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * String roleCode
     * @return
     */
    @GetMapping("/list")
    @ActionLogger(moduleName = "角色信息", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String roleCode){
        SysRoleBO sysRoleBO = new SysRoleBO();
        if (pageIndex != null) {
            sysRoleBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            sysRoleBO.setPageSize(pageSize);
        }

        if(!StringUtils.isEmpty(roleCode)){
            sysRoleBO.setRoleCode(roleCode);
        }
        PageResult<SysRole> sysRoles = sysRoleService.findBySysRole(sysRoleBO);
        return ResultBean.format(sysRoles);

    }

    /**
     * 保存角色信息
     * @param sysRole
     * @return
     */
    @PostMapping("/sysrole")
    @ActionLogger(moduleName = "角色信息", actionType = ActionLoggerCons.ADD)
    public ResultBean saveRoleInfo(SysRole sysRole){
        sysRoleService.saveRoleInfo(sysRole);
        return ResultBean.format(sysRole);
    }

    /**
     * 更新角色信息
     * @param sysRole
     * @return
     */
    @PutMapping("/sysrole")
    @ActionLogger(moduleName = "角色信息", actionType = ActionLoggerCons.MODIFY)
    public ResultBean editRoleInfo(SysRole sysRole){
        sysRoleService.updateRoleInfo(sysRole);
        return ResultBean.format();

    }


    /**
     * 删除角色信息
     * @param sysRole
     */
    @DeleteMapping("/sysrole")
    @ActionLogger(moduleName = "角色信息",actionType = "删除")
    public ResultBean deleteUserInfo(SysRole sysRole){
        sysRoleService.deleteRoleInfo(sysRole);
        return ResultBean.format(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getDescription());
    }


}
