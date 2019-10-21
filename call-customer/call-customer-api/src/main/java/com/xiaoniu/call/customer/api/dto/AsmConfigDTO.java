package com.xiaoniu.call.customer.api.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AsmConfigDTO {
    private String id;

    private String rom;

    private String api;

    private String model;

    private String manufacturer;

    private Long asmDataId;

    private Date modifyTime;

    private Date createTime;

}
