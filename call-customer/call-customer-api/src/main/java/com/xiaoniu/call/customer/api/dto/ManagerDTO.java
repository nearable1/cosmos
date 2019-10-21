package com.xiaoniu.call.customer.api.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ManagerDTO {
    private String id;

    private String customerId;

    private Integer accountType;

    private Date createTime;

    private String nickname;

    private String username;

    private Integer level;

    private Integer state;
}
