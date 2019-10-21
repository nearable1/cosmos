package com.xiaoniu.call.customer.api.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class AppAuditDTO {
    /**
     * 主键编号
     */
    private Long id;

    /**
     * 过审项
     */
    private String auditItem;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 版本
     */
    private String version;

    /**
     * 状态（1=启用，0=关闭）
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    private String appName;

}