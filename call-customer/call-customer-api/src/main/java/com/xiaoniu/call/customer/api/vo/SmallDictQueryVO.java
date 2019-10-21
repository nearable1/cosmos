package com.xiaoniu.call.customer.api.vo;

import java.io.Serializable;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SmallDictQueryVO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String smallCode;

    /**
     * 名称
     */
    private String smallName;

    /**
     * 状态
     */
    private Integer smallStatus;

    /**
     * 备注
     */
    private String smallRemark;

    private String bigDicCode;

}
