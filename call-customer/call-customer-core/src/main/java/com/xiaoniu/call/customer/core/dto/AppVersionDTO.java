package com.xiaoniu.call.customer.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AppVersionDTO implements Serializable {

    private static final long serialVersionUID = 6446804685011174869L;

    /**
     * 版本号
     */
    private String versionNumber;

    /**
     * 是否弹窗(false:否,true:是)
     */
    private Boolean isPopup;

    /**
     * 是否强更
     */
    private Boolean isStronger;

    /**
     * 更新描述
     */
    private String changeDesc;

    /**
     * 更新下载地址
     */
    private String downloadUrl;
}