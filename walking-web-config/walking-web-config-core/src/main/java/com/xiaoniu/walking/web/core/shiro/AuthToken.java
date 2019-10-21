package com.xiaoniu.walking.web.core.shiro;

import org.apache.shiro.authc.AuthenticationToken;
/**
 * 自定义 token 对象
 * @author xiangxianjin
 * @date 2019年3月29日 17点17分
 */
public class AuthToken implements AuthenticationToken {
	private static final long serialVersionUID = 1L;
    
    private String token;

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
    public String getPrincipal() {
        return getToken();
    }

    @Override
    public Object getCredentials() {
        return getToken();
    }
}