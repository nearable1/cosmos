package com.xiaoniu.call.customer.api.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class AdConfigDTO {
    /**
     * 主键编号
     */
    private Long id;

    private String appName;

    private String adType;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}