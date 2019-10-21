package com.xiaoniu.call.customer.api.vo;

import java.io.Serializable;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BigDictQueryVO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String bigCode;

    /**
     * 名称
     */
    private String bigName;

    /**
     * 状态
     */
    private Integer bigStatus;

    /**
     * 备注
     */
    private String bigRemark;

}
