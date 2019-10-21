package com.xiaoniu.call.customer.api.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class SmallDictVO {

    //添加0,修改1
    private String smallDictType;

    /**
     * 字典值选择项ID
     */
    private Long smallId;

    /**
     * 字典值编码
     */
    private String bigDicCode;

    /**
     * 字典选项编码
     */
    private String smallCode;

    /**
     * 名称
     */
    private String smallName;

    /**
     * 值
     */
    private String smallValue;
    private String[] smallVal;
    private Integer valueType;//数据类型：1：数组类型，2字符串类型

    /**
     * 排序
     */
    private Integer smallSort;

    /**
     * 创建时间
     */
    private Date smallCreateTime;

    /**
     * 创建人
     */
    private String smallCreateMan;

    /**
     * 修改时间
     */
    private Date smallModifyTime;

    /**
     * 修改人
     */
    private String smallModifyMan;

    /**
     * 备注
     */
    private String smallRemark;

    /**
     * 状态1:启用；2-禁止
     */
    private Integer smallState;
    private String smallStateVal;

}
