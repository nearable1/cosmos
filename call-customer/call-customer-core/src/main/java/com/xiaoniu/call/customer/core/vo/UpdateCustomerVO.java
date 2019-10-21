package com.xiaoniu.call.customer.core.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 广告请求对象
 *
 * @author Sunju
 * @date 2019/1/13
 */
@Data
@ToString
public class UpdateCustomerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    @NotNull(message = "昵称不能为空")
    private String nickName;

    /**
     * 头像
     */
    @NotNull(message = "头像不能为空")
    private String avatarAddress;

    /**
     * 签名
     */
    private String signature;
}
