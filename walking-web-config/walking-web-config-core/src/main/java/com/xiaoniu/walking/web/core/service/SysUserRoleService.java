package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.walking.web.core.model.auto.SysUserRole;

import java.util.List;

/**
 * 分配用户角色
 * @author: lihoujing
 * @date: 2019/4/15
 * @description:
 */
public interface SysUserRoleService {


    /**
     * 根据用户id查询
     * @param userId
     * @return
     */
    List<SysUserRole> findByUserId(String userId);

    /**
     * 保存用户角色信息
     * @param sysUserRole
     * @return
     */
    int saveUserRoleInfo(SysUserRole sysUserRole);

    /**
     * 更新用户角色信息
     * @param sysUserRole
     * @return
     */
    int updateUserRoleInfo(SysUserRole sysUserRole);


    /**
     * 处理用户角色表信息
     * @param
     * @return
     */
    void dealUserRoleInfo(List<SysUserRole> delList,List<SysUserRole> insertList);
}
