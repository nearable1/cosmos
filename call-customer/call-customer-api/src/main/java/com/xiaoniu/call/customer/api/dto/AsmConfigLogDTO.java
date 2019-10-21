package com.xiaoniu.call.customer.api.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AsmConfigLogDTO {
    private String id;

    private String rom;

    private String api;

    private String model;

    private String manufacturer;

    private Date createTime;

}
