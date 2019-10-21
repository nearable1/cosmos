package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class WkUserBO extends DefaultPageParameter implements Serializable {

    private Date startTime;

    private Date endTime;

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