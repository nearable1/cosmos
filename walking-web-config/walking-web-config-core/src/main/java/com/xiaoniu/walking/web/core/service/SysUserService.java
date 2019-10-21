package com.xiaoniu.walking.web.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.UserInfoBO;
import com.xiaoniu.walking.web.core.model.auto.SysUser;

import java.util.Map;
import java.util.Set;

/**
 * 系统 用户-角色-菜单-权限
 * @author: xiangxianjin
 * @date: 2019/3/27 18:25
 * @description:
 */
public interface SysUserService {

    /**
     * 查询系统用户账号
     * @param userId
     * @return
     */
    SysUser findByUserId(String userId);

    /**
     * 查询角色
     * @param userId
     * @return
     */
    Set<String> findRoles(String userId);

    /**
     * 查询权限
     * @param roles
     * @return
     */
    Set<String> findPermissions(Map<String, Object> roles);

    /**
     * 冻结用户
     * @param id
     * @return
     */
    int lockSysUser(Integer id);


    /**
     * 保存用户信息
     * @param sysUser
     * @return
     */
    int saveSysUser(SysUser sysUser);


    /**
     * 查询所有用户信息
     * @param userInfoBO
     * @return
     */
    PageResult<SysUser> findBySysUser(UserInfoBO userInfoBO);


    /**
     * 更新用户信息
     * @param sysUser
     * @return
     */
    int updateSysUser(SysUser sysUser);


    /**
     * 删除用户信息
     * @param sysUser
     * @return
     */
    int deleteSysUser(SysUser sysUser);
}
