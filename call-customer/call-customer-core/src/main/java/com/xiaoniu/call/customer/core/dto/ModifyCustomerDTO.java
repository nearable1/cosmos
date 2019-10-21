package com.xiaoniu.call.customer.core.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ModifyCustomerDTO {
    private Long customerId;

    private String nickName;

    private String avatarAddress;

    private String signature;
}
