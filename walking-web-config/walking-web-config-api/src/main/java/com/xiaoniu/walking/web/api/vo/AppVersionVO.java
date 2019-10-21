package com.xiaoniu.walking.web.api.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class AppVersionVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4280256578989510491L;

	/**
     * 主键
     */
    private Integer appVersionId;

    /**
     * app名称：1-来这花，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     */
    private Integer appName;

    /**
     * 更新地址
     */
    private String updateUrl;

    /**
     * 是否强制更新：1-是；2-否
     */
    private Integer forceUpdate;

    /**
     * 更新描述
     */
    private String updateDescription;

    /**
     * app版本号
     */
    private String appVersion;

    /**
     * 客户端版本码
     */
    private String appVersionCode;

    /**
     * 客户端MD5校验码
     */
    private String encryptCode;

//    /**
//     * 开始时间
//     */
//    private Date startTime;
//
//    /**
//     * 结束时间
//     */
//    private Date endTime;

    /**
     * app版本号 Patch
     */
    private String AppVersionPatch;

    /**
     * 补丁更新地址
     */
    private String updateUrlPatch;

    /**
     * 客户端版本码Patch
     */
    private String appVersionCodePatch;

    /**
     * 客户端MD5校验码Patch
     */
    private String encryptCodePatch;

//    /**
//     * 开始时间Patch
//     */
//    private Date startTimePatch;
//
//    /**
//     * 结束时间Patch
//     */
//    private Date endTimePatch;



}