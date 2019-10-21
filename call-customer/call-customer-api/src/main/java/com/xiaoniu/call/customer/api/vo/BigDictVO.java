package com.xiaoniu.call.customer.api.vo;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BigDictVO {
    /**
     * 字典值ID
     */
    private Long bigId;

    /**
     * 编码
     */
    private String bigCode;

    /**
     * 名称
     */
    private String bigName;

    /**
     * 排序
     */
    private Integer bigSort;

    /**
     * 创建时间
     */
    private Date bigCreateTime;

    /**
     * 创建人
     */
    private String bigCreateMan;

    /**
     * 修改时间
     */
    private Date bigModifyTime;

    /**
     * 修改人
     */
    private String bigModifyMan;

    /**
     * 备注
     */
    private String bigRemark;

    /**
     * 状态1:启用；2-禁止
     */
    private Integer bigState;
    private String bigStateVal;

}
