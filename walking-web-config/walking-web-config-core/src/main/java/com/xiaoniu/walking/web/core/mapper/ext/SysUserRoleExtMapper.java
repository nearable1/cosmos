package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.core.model.auto.SysUserRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;
import java.util.Set;

/**
 * @author: xiangxianjin
 * @date: 2019/3/29 17:37
 * @description:
 */
public interface SysUserRoleExtMapper extends Mapper<SysUserRole> {

    /**
     * 查询角色
     * @param userId
     * @return
     */
    Set<String> findRoles(@Param(value = "userId") String userId);

    /**
     * 查询权限
     * @param roles
     * @return
     */
    Set<String> findPermissions(Map<String, Object> roles);

    /**
     * 更新用户角色信息
     * @param sysUserRole
     * @return
     */
    int updateUserRoleInfo(SysUserRole sysUserRole);

}
