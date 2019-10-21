package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.bo.MenuBO;
import com.xiaoniu.walking.web.core.bo.SysPermissionBO;
import com.xiaoniu.walking.web.core.mapper.auto.SysPermissionMapper;
import com.xiaoniu.walking.web.core.mapper.ext.SysPermissionExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysPermission;
import com.xiaoniu.walking.web.core.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author: lihoujing
 * @date: 2019/4/15 13:34
 * @description: 权限信息
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysPermissionExtMapper sysPermissionExtMapper;
    @Autowired
    private PageRepository pageRepository;


    @Override
    public PageResult<MenuBO> findBySysPermission(SysPermissionBO sysPermissionBO) {
        return pageRepository.selectPaging(SysPermissionExtMapper.class,"selectSysPermissionListWithConditionByPage",sysPermissionBO);
    }


    @Override
    public int savePermissionInfo(SysPermission sysPermission) {
        return sysPermissionMapper.insertSelective(sysPermission);
    }

    @Override
    public int updatePermissionInfo(SysPermission sysPermission) {
        return sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
    }

    @Override
    public int deletePermissionInfo(SysPermission sysPermission) {
        return sysPermissionMapper.delete(sysPermission);
    }

    @Override
    public List<MenuBO> findSysPermission(String roleCode) {
        return sysPermissionExtMapper.findSysPermission(roleCode);
    }

    @Override
    public List<SysPermission> findChildPermission(String parentId) {
        return null;
    }
}
