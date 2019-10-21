package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.bo.SysRoleBO;
import com.xiaoniu.walking.web.core.mapper.auto.SysRoleMapper;
import com.xiaoniu.walking.web.core.mapper.ext.SysRoleExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysRole;
import com.xiaoniu.walking.web.core.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author: lihoujing
 * @date: 2019/4/15 9:34
 * @description: 角色信息实现类
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {


    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private PageRepository pageRepository;


    @Override
    public SysRole findByRoleId(String roleCode) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode(roleCode);
        return sysRoleMapper.selectOne(sysRole);
    }

    @Override
    public PageResult<SysRole> findBySysRole(SysRoleBO sysRoleBO) {
        return pageRepository.selectPaging(SysRoleExtMapper.class,"selectSysRoleListWithConditionByPage",sysRoleBO);
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.selectAll();
    }

    @Override
    public int saveRoleInfo(SysRole sysRole) {
        return sysRoleMapper.insertSelective(sysRole);
    }

    @Override
    public int updateRoleInfo(SysRole sysRole) {
        return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public int deleteRoleInfo(SysRole sysRole) {
        return sysRoleMapper.delete(sysRole);
    }
}
