package com.xiaoniu.call.customer.core.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginResponseDTO {
    private Long customerId;

    private String nickName;

    private String avatarAddress;

    private String signature;

    private String accessToken;

    private Integer level;
}
