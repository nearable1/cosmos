package com.xiaoniu.walking.web.core.model.auto;

import javax.persistence.*;

@Table(name = "sys_user_role")
public class SysUserRole {
    /**
     * 主键编号
     */
    @Id
    private Integer id;

    /**
     * 系统登录账号
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 角色编号
     */
    @Column(name = "role_code")
    private String roleCode;

    /**
     * 获取主键编号
     *
     * @return id - 主键编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键编号
     *
     * @param id 主键编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取系统登录账号
     *
     * @return user_id - 系统登录账号
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置系统登录账号
     *
     * @param userId 系统登录账号
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取角色编号
     *
     * @return role_code - 角色编号
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * 设置角色编号
     *
     * @param roleCode 角色编号
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}