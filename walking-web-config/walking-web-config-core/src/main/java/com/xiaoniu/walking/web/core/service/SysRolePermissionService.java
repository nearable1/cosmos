package com.xiaoniu.walking.web.core.service;

import com.xiaoniu.walking.web.core.model.auto.SysRolePermission;

import java.util.List;
/**
 * @author: lihoujing
 * @date: 2019/4/19
 * @description: 角色权限信息
 */
public interface SysRolePermissionService {


    /**
     * 查询所有权限信息
     * @param roleCode
     * @return
     */
    List<SysRolePermission> findBySysRolePermission(String roleCode);


    /**
     * 角色分配权限
     * @param deleteList 删除list
     * @param insertList 新增list
     * @return int
     */
    void dealRolePermissionInfo(List<SysRolePermission> deleteList,List<SysRolePermission> insertList);


}
