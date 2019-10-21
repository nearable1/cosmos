package com.xiaoniu.call.customer.core.vo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 广告请求对象
 *
 * @author Sunju
 * @date 2019/1/13
 */
@Data
@ToString
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 微信OpenId
     */
    @NotNull(message = "openId不能为空")
    private String openId;

    /**
     * 账户类型
     */
    @NotNull(message = "账户类型不能为空")
    private Integer accountType;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatarAddress;
}
