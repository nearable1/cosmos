package com.xiaoniu.call.customer.core.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

/**
 * 字典请求对象
 *
 * @author Sunju
 * @date 2019/1/13
 */
@Data
@ToString
public class DicVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典大类
     */
    @NotNull(message = "字典大类不能为空")
    private String dicCode;

    /**
     * 字典小类
     */
    private String code;

}
