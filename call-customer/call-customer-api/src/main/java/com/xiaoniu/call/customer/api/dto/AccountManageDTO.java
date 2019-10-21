package com.xiaoniu.call.customer.api.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AccountManageDTO {
    private String id;

    private Integer accountType;

    private Date createTime;

    private String phoneModel;

    private String registerChannel;

    private Date lastLoginTime;


}
