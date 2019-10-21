package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.walking.web.core.mapper.auto.SysRolePermissionMapper;
import com.xiaoniu.walking.web.core.model.auto.SysRolePermission;
import com.xiaoniu.walking.web.core.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: lihoujing
 * @date: 2019/4/19
 * @description: 角色权限实现类
 */
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRolePermission> findBySysRolePermission(String roleCode) {
        SysRolePermission sysRolePermission = new SysRolePermission();
        sysRolePermission.setRoleCode(roleCode);
        return sysRolePermissionMapper.select(sysRolePermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dealRolePermissionInfo(List<SysRolePermission> deleteList, List<SysRolePermission> insertList) {
        deleteList.forEach(e -> sysRolePermissionMapper.delete(e));
        insertList.forEach(e -> sysRolePermissionMapper.insert(e));
    }
}
