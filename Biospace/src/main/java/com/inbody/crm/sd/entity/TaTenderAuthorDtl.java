/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 招标授权Entity
 * @author qidd
 * @version 2017-10-19
 */
public class TaTenderAuthorDtl extends DataEntity<TaTenderAuthorDtl> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private TaTenderAuthor authorization;		// 招标编号 父类
	private String authorizationNo;		// 招标编号 父类
	private String appId;		// 招标编号 父类
	private String lineNo;		// 行号
	private String materialNo;		// 物料号
	private String model;		// 物料号
	
	public TaTenderAuthorDtl() {
		super();
	}

	public TaTenderAuthorDtl(String id){
		super(id);
	}

	public TaTenderAuthorDtl(TaTenderAuthor authorization){
		this.authorization = authorization;
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	public TaTenderAuthor getAuthorization() {
		return authorization;
	}

	public void setAuthorization(TaTenderAuthor authorization) {
		this.authorization = authorization;
	}

	@Length(min=1, max=50, message="招标编号长度必须介于 1 和 50 之间")
	public String getAuthorizationNo() {
		return authorizationNo;
	}

	public void setAuthorizationNo(String authorizationNo) {
		this.authorizationNo = authorizationNo;
	}
	
	@Length(min=1, max=2, message="行号长度必须介于 1 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=1, max=50, message="物料号长度必须介于 1 和 50 之间")
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}