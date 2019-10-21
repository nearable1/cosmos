package com.xiaoniu.walking.web.core.bo;

import lombok.ToString;

import java.io.Serializable;

/**
 * @author: lihoujing
 * @date: 2019/4/11 15:26
 * @description:
 */
@ToString
public class RoleBO implements Serializable {
    /**
     * 用户登录账号
     */
    private String userId;

    /**
     * 角色编号数组
     */
    private String[] roleCode;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String[] getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String[] roleCode) {
        this.roleCode = roleCode;
    }
}
