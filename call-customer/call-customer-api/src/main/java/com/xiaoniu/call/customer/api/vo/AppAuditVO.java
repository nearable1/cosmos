package com.xiaoniu.call.customer.api.vo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class AppAuditVO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    private Long id;

    /**
     * 过审项
     */
    private String auditItem;

    /**
     * 过审项 后台修改前的值
     */
    private String auditItemTemp;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 版本
     */
    private String version;

    /**
     * app
     */
    private Integer appName;

    /**
     * 备注
     */
    private String remark;

}