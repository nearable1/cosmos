package com.xiaoniu.walking.web.core.vo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WkUserVO extends DefaultPageParameter implements Serializable {
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