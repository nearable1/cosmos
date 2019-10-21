package com.xiaoniu.walking.web.core.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: xiangxianjin
 * @date: 2019/4/9 17:37
 * @description: 登录对象
 *  * @author xiangxianjin
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginUser implements Serializable {
    /**
     * 系统登录账号
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 登录验证码
     */
    private String code;

}
