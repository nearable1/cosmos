package com.xiaoniu.walking.web.core.shiro;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限控制
 * @author xiangxianjin
 */
@Configuration
@Log4j2
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 自定义 OAuth2Filter 过滤器，替代默认的过滤器
        Map<String, Filter> filters = new HashMap<>();
        filters.put("myAuth", new MyAuthFilter());
        shiroFilterFactoryBean.setFilters(filters);
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/**/config/get-sys-by-value", "anon");
        filterChainDefinitionMap.put("/**/static/**", "anon");
        filterChainDefinitionMap.put("/**/portal/login", "anon");
        filterChainDefinitionMap.put("/**/config/account", "anon");
        filterChainDefinitionMap.put("/**/upload", "anon");
        filterChainDefinitionMap.put("/**/config/goods-list", "anon");
        filterChainDefinitionMap.put("/**/config/goods-detail", "anon");
        filterChainDefinitionMap.put("/**/config/sys-dict-list", "anon");
        filterChainDefinitionMap.put("/**/monitor", "anon");
        filterChainDefinitionMap.put("/**/os-banner-list", "anon");
        filterChainDefinitionMap.put("/**/config/credit-list", "anon");
        filterChainDefinitionMap.put("/**/config/sys-credit", "anon");
        filterChainDefinitionMap.put("/**/portal/query-code-is-show", "anon");
        filterChainDefinitionMap.put("/**/portal/get_valid_code", "anon");
        filterChainDefinitionMap.put("/**/bottom-icon-list", "anon");
        filterChainDefinitionMap.put("/**/get-app-version", "anon");
        filterChainDefinitionMap.put("/**/config/hot-search-list", "anon");
        filterChainDefinitionMap.put("/**/config/dict-list", "anon");
        filterChainDefinitionMap.put("/**/config/get-img-info", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //filterChainDefinitionMap.put("/**/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //filterChainDefinitionMap.put("/**", "authc");
        //<!-- 自定义权限过滤器 -->
        filterChainDefinitionMap.put("/**", "myAuth");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        //shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }
}
