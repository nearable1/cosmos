package com.xiaoniu.call.customer.api.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AsmConfigDataDTO {
    private String id;

    private String name;

    private String data;

    private String remark;

    private Date createTime;

    private Date updateTime;

}
