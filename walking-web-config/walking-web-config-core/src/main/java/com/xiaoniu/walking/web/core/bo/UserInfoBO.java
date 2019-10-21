package com.xiaoniu.walking.web.core.bo;

import java.util.Set;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: xiangxianjin
 * @date: 2019/4/11 01:37
 * @description: 用户信息
 *  * @author xiangxianjin
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserInfoBO extends DefaultPageParameter {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5732896286913468897L;
	/**
     * 系统登录账号
     */
    private String userId;
    /**
     * 系统登录真实姓名
     */
    private String name;
    /**
     * 头像
     */
    private String avatar;
    /**
     * status状态
     */
    private String status;
    /**
     * introduction
     */
    private String introduction;
    /**
     * roles
     */
    private Set<String> roles;

}
