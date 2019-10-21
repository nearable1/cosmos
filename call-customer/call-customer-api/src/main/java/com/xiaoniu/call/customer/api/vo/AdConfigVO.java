package com.xiaoniu.call.customer.api.vo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class AdConfigVO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    private Long id;


    /**
     * app
     */
    private Integer appName;

    /**
     * app
     */
    private Integer adType;
}