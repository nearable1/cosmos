package com.xiaoniu.walking.web.core.controller;


import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.RoleBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.auto.SysRole;
import com.xiaoniu.walking.web.core.model.auto.SysUserRole;
import com.xiaoniu.walking.web.core.service.SysRoleService;
import com.xiaoniu.walking.web.core.service.SysUserRoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: lihoujing
 * @date: 2019/4/11 15:26
 * @description:
 */
@RestController
@RequestMapping("/walkingwebapi/user/role")
@Log4j2
public class UserRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 查询所有的角色名称列表
     * @return
     */
    @GetMapping("/list")
    @ActionLogger(moduleName = "所有角色",actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(){
        List<SysRole> sysRoles = sysRoleService.findAll();
        return ResultBean.format(sysRoles);
    }


    /**
     * 查询已拥有的角色
     * @return
     */
    @GetMapping("/selectedList")
    @ActionLogger(moduleName = "拥有角色",actionType = ActionLoggerCons.QUERY)
    public ResultBean getSelectedList(String userId){
        Set<String> sysRoles = new HashSet<>();
        List<SysUserRole> sysUserRoles = sysUserRoleService.findByUserId(userId);
        sysUserRoles.stream().forEach(roleCode ->
            sysRoles.add(roleCode.getRoleCode())
        );
        return ResultBean.format(sysRoles);
    }

    /**
     * 分配用户权限
     * @param
     */
    @PostMapping("/auth")
    @ActionLogger(moduleName = "角色分配",actionType = ActionLoggerCons.MODIFY)
    public ResultBean assignRole(RoleBO roleBo){

        //查询出已有的角色列表
        List<SysUserRole> dbRoles = sysUserRoleService.findByUserId(roleBo.getUserId());
        List<String> paramList = new ArrayList<>();
        paramList.addAll(Arrays.asList(roleBo.getRoleCode()));
        List<SysUserRole> paramRoleList = new ArrayList<>();
        paramList.forEach(paramRoleCode -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(roleBo.getUserId());
            sysUserRole.setRoleCode(paramRoleCode);
            paramRoleList.add(sysUserRole);
        });

        List<String> dbRoleList = dbRoles.stream().map(SysUserRole::getRoleCode).collect(Collectors.toList());
        //待删除的列表
        List<SysUserRole> deleteList = dbRoles.stream()
                .filter(e -> !paramList.contains(e.getRoleCode()))
                .collect(Collectors.toList());
        //待添加的列表
        List<SysUserRole> insertList = paramRoleList.stream()
                .filter(e -> !dbRoleList.contains(e.getRoleCode()))
                .collect(Collectors.toList());

        sysUserRoleService.dealUserRoleInfo(deleteList,insertList);
        return ResultBean.format();
    }

}
