package com.xiaoniu.walking.web.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.MenuBO;
import com.xiaoniu.walking.web.core.bo.SysPermissionBO;
import com.xiaoniu.walking.web.core.model.auto.SysPermission;

import java.util.List;


/**
 * @author: lihoujing
 * @date: 2019/4/15 13:34
 * @description: 权限信息
 */
public interface SysPermissionService {

        /**
         * 查询权限信息
         * @param sysPermissionBO
         * @return SysPermission
         */
        PageResult<MenuBO> findBySysPermission(SysPermissionBO sysPermissionBO);


        /**
         * 保存权限信息
         * @param sysPermission
         * @return
         */
        int savePermissionInfo(SysPermission sysPermission);

        /**
         * 更新权限信息
         * @param sysPermission
         * @return
         */
        int updatePermissionInfo(SysPermission sysPermission);

        /**
         * 删除权限信息
         * @param sysPermission
         * @return
         */
        int deletePermissionInfo(SysPermission sysPermission);


        /**
         * 根据角色编号查询所有菜单信息（标记是否选中）
         * @param roleCode
         * @return
         */
        List<MenuBO> findSysPermission(String roleCode);

        /**
         * 查询所有子权限列表
         * @param parentId 父级权限编号
         * @return
         */
        List<SysPermission> findChildPermission(String parentId);


}
