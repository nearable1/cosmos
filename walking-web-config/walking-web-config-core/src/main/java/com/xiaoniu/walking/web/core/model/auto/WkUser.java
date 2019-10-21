package com.xiaoniu.walking.web.core.model.auto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WkUser {
    private String userId;

    private Byte userType;

    private String phone;

    private Integer appName;

    private String nickname;

    private String userAvatar;

    private String openId;

    private Integer userState;

    private Date lastLoginTime;

    private String deviceId;

    private String market;

    private Integer source;

    private Integer osVersion;

    private String appVersion;

    private String remark;

    private Date createTime;

    private Date modifyTime;

}