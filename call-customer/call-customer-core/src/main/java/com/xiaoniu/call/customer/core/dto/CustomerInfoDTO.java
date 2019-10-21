package com.xiaoniu.call.customer.core.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CustomerInfoDTO implements Serializable {
    private Long customerId;

    private String nickName;

    private String avatarAddress;

    private String signature;

    private Integer level = 0;

    private Integer accountType;
}
