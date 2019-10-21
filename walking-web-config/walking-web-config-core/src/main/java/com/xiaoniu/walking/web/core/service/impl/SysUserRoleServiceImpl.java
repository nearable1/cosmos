package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.walking.web.core.mapper.auto.SysUserRoleMapper;
import com.xiaoniu.walking.web.core.mapper.ext.SysUserRoleExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysUserRole;
import com.xiaoniu.walking.web.core.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分配用户角色
 * @author: lihoujing
 * @date: 2019/4/15
 * @description:
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserRoleExtMapper sysUserRoleExtMapper;

    @Override
    public List<SysUserRole> findByUserId(String userId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        return sysUserRoleMapper.select(sysUserRole);
    }

    @Override
    public int saveUserRoleInfo(SysUserRole sysUserRole) {
        return sysUserRoleMapper.insert(sysUserRole);
    }

    @Override
    public int updateUserRoleInfo(SysUserRole sysUserRole) {
        return sysUserRoleExtMapper.updateUserRoleInfo(sysUserRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dealUserRoleInfo(List<SysUserRole> delList, List<SysUserRole> insertList) {
        delList.forEach(e -> sysUserRoleMapper.delete(e));
        insertList.forEach(e -> sysUserRoleMapper.insert(e));
    }
}
