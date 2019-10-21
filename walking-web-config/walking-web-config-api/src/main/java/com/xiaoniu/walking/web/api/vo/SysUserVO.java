package com.xiaoniu.walking.web.api.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统用户信息
 * @author xiangxianjin
 */
@Getter
@Setter
@NoArgsConstructor
public class SysUserVO implements Serializable {

    /**
     * 系统登录账号
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像地址
     */
    private String headImageUrl;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 性别：0-女，1-男
     */
    private Integer sex;

    /**
     * 登录标识
     */
    private String token;

}