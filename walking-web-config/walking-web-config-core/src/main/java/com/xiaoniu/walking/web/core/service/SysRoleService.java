package com.xiaoniu.walking.web.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.SysRoleBO;
import com.xiaoniu.walking.web.core.model.auto.SysRole;

import java.util.List;

/**
 * @author: lihoujing
 * @date: 2019/4/15 9:34
 * @description: 角色信息
 */
public interface SysRoleService {


    /**
     * 查询角色信息
     * @param roleCode
     * @return SysRole
     */
    SysRole findByRoleId(String roleCode);



    /**
     * 查询角色
     * @param sysRoleBO
     * @return
     */
    PageResult<SysRole> findBySysRole(SysRoleBO sysRoleBO);

    /**
     * 查询所有的角色
     * @return
     */
    List<SysRole> findAll();


    /**
     * 保存角色信息
     * @param sysRole
     * @return int
     */
    int saveRoleInfo(SysRole sysRole);

    /**
     * 更新角色信息
     * @param sysRole
     * @return
     */
    int updateRoleInfo(SysRole sysRole);


    /**
     * 删除角色信息
     * @param sysRole
     * @return
     */
    int deleteRoleInfo(SysRole sysRole);
}
