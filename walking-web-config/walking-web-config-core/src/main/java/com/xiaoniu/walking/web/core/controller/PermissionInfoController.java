package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.MenuBO;
import com.xiaoniu.walking.web.core.bo.RolePermissionBO;
import com.xiaoniu.walking.web.core.bo.SysPermissionBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.auto.SysPermission;
import com.xiaoniu.walking.web.core.model.auto.SysRolePermission;
import com.xiaoniu.walking.web.core.service.SysPermissionService;
import com.xiaoniu.walking.web.core.service.SysRolePermissionService;
import com.xiaoniu.walking.web.core.utils.TreeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: lihoujing
 * @date: 2019/4/15 13:34
 * @description: 权限信息
 */
@RestController
@RequestMapping("/walkingwebapi/permission")
@Log4j2
public class PermissionInfoController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     *查询权限列表
     * SysPermissionBO sysPermissionBO
     * @return
     */
    @GetMapping("/list")
    @ActionLogger(moduleName = "权限信息", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                SysPermissionBO sysPermissionBO){

        if (pageIndex != null) {
            sysPermissionBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            sysPermissionBO.setPageSize(pageSize);
        }
        PageResult<MenuBO> sysPermissions = sysPermissionService.findBySysPermission(sysPermissionBO);
        return ResultBean.format(sysPermissions);
    }

    /**
     * 保存权限信息
     * @param sysPermission
     * @return
     */
    @PostMapping("/syspermission")
    @ActionLogger(moduleName = "权限信息", actionType = ActionLoggerCons.ADD)
    public ResultBean saveRoleInfo(SysPermission sysPermission){
        sysPermissionService.savePermissionInfo(sysPermission);
        return ResultBean.format(sysPermission);
    }

    /**
     * 更新权限信息
     * @param sysPermission
     * @return
     */
    @PutMapping("/syspermission")
    @ActionLogger(moduleName = "权限信息", actionType = ActionLoggerCons.MODIFY)
    public ResultBean editRoleInfo(SysPermission sysPermission){
        sysPermissionService.updatePermissionInfo(sysPermission);
        return ResultBean.format(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getDescription());

    }


    /**
     * 删除权限信息
     * @param sysPermission
     */
    @DeleteMapping("/syspermission")
    @ActionLogger(moduleName = "权限信息", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteUserInfo(SysPermission sysPermission){
        sysPermissionService.deletePermissionInfo(sysPermission);
        return ResultBean.format(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getDescription());
    }




    /**
     * 获取所有菜单信息
     * @return
     */
    @GetMapping("/role-permission")
    @ActionLogger(moduleName = "权限菜单",actionType = "查询")
    public ResultBean permissionList(String roleCode){
        List<MenuBO> sysPermissionList = sysPermissionService.findSysPermission(roleCode);
        //树形结构数据生成
        List<MenuBO> result = TreeUtils.parseMenuTree(sysPermissionList);
        Set<Integer> set = new HashSet<>();
        sysPermissionList.stream()
                .filter(e -> e.getChecked()==1)
                .forEach(e -> {
                    set.add(e.getPermissionId());
                    if(set.contains(e.getParentId())){
                        set.remove(e.getParentId());
                    }
                });
        RolePermissionBO permissionBO = new RolePermissionBO();
        permissionBO.setData(result);
        permissionBO.setId(set);
        return ResultBean.format(permissionBO);
    }


    /**
     * 角色分配菜单
     * @return
     */
    @PostMapping("/auth")
    @ActionLogger(moduleName = "权限分配",actionType = "分配菜单")
    public ResultBean assignMenus(String roleCode,String[] permissionId){

        //查询出已有的菜单
        List<SysRolePermission> dbPermissions = sysRolePermissionService.findBySysRolePermission(roleCode);
        List<String> dbList = dbPermissions.stream().map(e -> String.valueOf(e.getPermissionId())).collect(Collectors.toList());

        List<String> paramList = new ArrayList<>();
        paramList.addAll(Arrays.asList(permissionId));
        List<SysRolePermission> paramPermissionList = new ArrayList<>();
        paramList.forEach(permission ->{
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleCode(roleCode);
            sysRolePermission.setPermissionId(Integer.parseInt(permission));
            paramPermissionList.add(sysRolePermission);
        });

        //待删除的列表
        List<SysRolePermission> deleteList = dbPermissions.stream()
                .filter(e -> !paramList.contains(String.valueOf(e.getPermissionId())))
                .collect(Collectors.toList());
        //待添加的列表
        List<SysRolePermission> insertList = paramPermissionList.stream()
                .filter(e -> !dbList.contains(String.valueOf(e.getPermissionId())))
                .collect(Collectors.toList());

        sysRolePermissionService.dealRolePermissionInfo(deleteList,insertList);
        return ResultBean.format();
    }



}
