package com.xiaoniu.walking.web.core.shiro;

import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.walking.web.core.constants.ShiroCons;
import com.xiaoniu.walking.web.core.enums.WebConfigBusinessEnum;
import com.xiaoniu.walking.web.core.helper.LoginHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Oauth2过滤器
 * @author xiangxianjin
 * @date 2019年3月29日 17点17分
 */
@Log4j2
public class MyAuthFilter extends AuthenticatingFilter {

    /**
     * 登录页面访问地址
     */
    private static final String LOGIN_URL = "/login";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求token
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            return null;
        }
        return new AuthToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //待鉴权
        //Always return true if the request's method is OPTIONS
        if (request instanceof HttpServletRequest) {
            String method =  ((HttpServletRequest) request).getMethod();
            if (ShiroCons.OPTIONS.equals(method.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            //去登录页
            log.warn("用户未登录，请去登录!");
            LoginHelper.toLoginPage((HttpServletResponse) response, WebConfigBusinessEnum.TOKEN_EXPIRE);
            return false;
        }
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if(((HttpServletRequest)request).getRequestURI().indexOf(LOGIN_URL) < 0){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json; charset=utf-8");
            //去登录页
            log.warn("用户登录失败，请去登录!");
            LoginHelper.toLoginPage((HttpServletResponse) response, WebConfigBusinessEnum.ILLEGAL_TOKEN);
        }
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        // 从header中获取token
        String token = httpRequest.getHeader(ShiroCons.TOKEN);
        // 如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter(ShiroCons.TOKEN);
        }
        return token;
    }

}
