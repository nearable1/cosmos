package com.xiaoniu.walking.web.core.model.auto;

import javax.persistence.*;

@Table(name = "sys_role_permission")
public class SysRolePermission {
    /**
     * 主键编号
     */
    @Id
    private Integer id;

    /**
     * 角色编码
     */
    @Column(name = "role_code")
    private String roleCode;

    /**
     * 权限编号
     */
    @Column(name = "permission_id")
    private Integer permissionId;

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
     * 获取角色编码
     *
     * @return role_code - 角色编码
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * 设置角色编码
     *
     * @param roleCode 角色编码
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * 获取权限编号
     *
     * @return permission_id - 权限编号
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * 设置权限编号
     *
     * @param permissionId 权限编号
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}