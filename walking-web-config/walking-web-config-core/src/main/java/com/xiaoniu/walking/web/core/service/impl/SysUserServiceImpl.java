package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.bo.UserInfoBO;
import com.xiaoniu.walking.web.core.enums.SysUserEnum;
import com.xiaoniu.walking.web.core.mapper.auto.SysUserMapper;
import com.xiaoniu.walking.web.core.mapper.ext.SysUserExtMapper;
import com.xiaoniu.walking.web.core.mapper.ext.SysUserRoleExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * 系统 用户-角色-菜单-权限
 * @author: xiangxianjin
 * @date: 2019/3/27 18:25
 * @description:
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserRoleExtMapper sysUserRoleExtMapper;

    @Autowired
    private PageRepository pageRepository;

    /**
     * 查询系统用户账号
     *
     * @param userId
     * @return
     */
    @Override
    public SysUser findByUserId(String userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        return sysUserMapper.selectOne(sysUser);
    }

    /**
     * 查询角色
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> findRoles(String userId) {
        return sysUserRoleExtMapper.findRoles(userId);
    }

    /**
     * 查询权限
     *
     * @param roles
     * @return
     */
    @Override
    public Set<String> findPermissions(Map<String, Object> roles) {
        return sysUserRoleExtMapper.findPermissions(roles);
    }

    /**
     * 冻结用户
     *
     * @param id
     * @return
     */
    @Override
    public int lockSysUser(Integer id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setStatus(SysUserEnum.LOCK.getValue());
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    /**
     * 保存用户信息
     * @param sysUser
     * @return
     */
    @Override
    public int saveSysUser(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public PageResult<SysUser> findBySysUser(UserInfoBO userInfoBO) {
        return pageRepository.selectPaging(SysUserExtMapper.class, "selectListWithConditionByPage", userInfoBO);
    }


    @Override
    public int updateSysUser(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public int deleteSysUser(SysUser sysUser) {
        return sysUserMapper.delete(sysUser);
    }


}
