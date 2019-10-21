package com.xiaoniu.walking.web.core.shiro;

import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.walking.web.api.constants.RedisCons;
import com.xiaoniu.walking.web.core.enums.SysUserEnum;
import com.xiaoniu.walking.web.core.enums.WebConfigBusinessEnum;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 认证Realm实现
 * @author xiangxianjin
 * @date 2019年3月29日 17点17分
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    /**
     * 授权(接口保护，验证接口调用权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser)principals.getPrimaryPrincipal();
        // 用户权限列表，根据用户拥有的权限标识与如 @permission标注的接口对比，决定是否可以调用接口
        Set<String> rolesSet = sysUserService.findRoles(user.getUserId());

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("roles", rolesSet);
        Set<String> permsSet = sysUserService.findPermissions(parameters);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(rolesSet);
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        // 根据accessToken，查询用户token信息
        String json = RedisRepository.get(String.format(RedisCons.SYS_USER_TOKEN, token));
        if(StringUtils.isEmpty(json)){
            throw new BusinessException(WebConfigBusinessEnum.TOKEN_EXPIRE.getValue(), WebConfigBusinessEnum.TOKEN_EXPIRE.getDesc());
        }
        // 查询用户信息
        SysUser user = sysUserService.findByUserId(JSONUtils.parseObject(json, SysUser.class).getUserId());
        // 账号被锁定
        if(SysUserEnum.LOCK.matches(user.getStatus())){
            throw new BusinessException(WebConfigBusinessEnum.LOGIN_LOCK.getValue(), WebConfigBusinessEnum.LOGIN_LOCK.getDesc());
        }
        //刷新TOKEN
        RedisRepository.set(String.format(RedisCons.SYS_USER_TOKEN, token), json, RedisCons.SYS_USER_TOKEN_EXPIRE);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token, getName());
        return info;
    }
}
