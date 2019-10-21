package com.xiaoniu.call.customer.api.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserLoginDTO {
    private String id;

    private Integer accountType;

    private Date createTime;

    private String loginIpAddress;
}
