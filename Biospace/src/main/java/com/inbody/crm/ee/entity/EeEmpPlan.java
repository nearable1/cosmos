/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ee.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 计划明细Entity
 * @author 11
 * @version 2017-10-30
 */
public class EeEmpPlan extends DataEntity<EeEmpPlan> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String appId;		// 申请ID
	private String lineNo;		// 行号
	private String showArea;		// 显示区域
	private String displayItem1;		// 显示项一
	private String displayItem2;		// 显示项二
	private String displayItem3;		// 显示项三
	private String displayItem4;		// 显示项四
	private String displayItem5;		// 显示项五
	private String displayItem6;		// 显示项六
	private String displayItem7;		// 显示项七
	private String displayItem8;		// 显示项八
	
	public EeEmpPlan() {
		super();
	}

	public EeEmpPlan(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=64, message="申请ID长度必须介于 1 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=1, max=2, message="行号长度必须介于 1 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=1, max=5, message="显示区域长度必须介于 1 和 5 之间")
	public String getShowArea() {
		return showArea;
	}

	public void setShowArea(String showArea) {
		this.showArea = showArea;
	}
	
	@Length(min=0, max=100, message="显示项一长度必须介于 0 和 100 之间")
	public String getDisplayItem1() {
		return displayItem1;
	}

	public void setDisplayItem1(String displayItem1) {
		this.displayItem1 = displayItem1;
	}
	
	@Length(min=0, max=100, message="显示项二长度必须介于 0 和 100 之间")
	public String getDisplayItem2() {
		return displayItem2;
	}

	public void setDisplayItem2(String displayItem2) {
		this.displayItem2 = displayItem2;
	}
	
	@Length(min=0, max=100, message="显示项三长度必须介于 0 和 100 之间")
	public String getDisplayItem3() {
		return displayItem3;
	}

	public void setDisplayItem3(String displayItem3) {
		this.displayItem3 = displayItem3;
	}
	
	@Length(min=0, max=100, message="显示项四长度必须介于 0 和 100 之间")
	public String getDisplayItem4() {
		return displayItem4;
	}

	public void setDisplayItem4(String displayItem4) {
		this.displayItem4 = displayItem4;
	}
	
	@Length(min=0, max=100, message="显示项五长度必须介于 0 和 100 之间")
	public String getDisplayItem5() {
		return displayItem5;
	}

	public void setDisplayItem5(String displayItem5) {
		this.displayItem5 = displayItem5;
	}
	
	@Length(min=0, max=100, message="显示项六长度必须介于 0 和 100 之间")
	public String getDisplayItem6() {
		return displayItem6;
	}

	public void setDisplayItem6(String displayItem6) {
		this.displayItem6 = displayItem6;
	}
	
	@Length(min=0, max=100, message="显示项七长度必须介于 0 和 100 之间")
	public String getDisplayItem7() {
		return displayItem7;
	}

	public void setDisplayItem7(String displayItem7) {
		this.displayItem7 = displayItem7;
	}
	
	@Length(min=0, max=100, message="显示项八长度必须介于 0 和 100 之间")
	public String getDisplayItem8() {
		return displayItem8;
	}

	public void setDisplayItem8(String displayItem8) {
		this.displayItem8 = displayItem8;
	}
	
}