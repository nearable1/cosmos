/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ee.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 员工评价Entity
 * @author 11
 * @version 2017-11-01
 */
public class EeEmpEvaluation extends DataEntity<EeEmpEvaluation> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String appId;		// 申请ID
	private String evaluateType;		// 评价人类型 : 代码
	private String describe1;		// 描述项一
	private String describe2;		// 描述项二
	private String describe3;		// 描述项三
	private String describe4;		// 描述项四
	private String evaluate1;		// 评价一
	private String evaluate2;		// 评价二
	private String evaluate3;		// 评价三
	private String evaluate4;		// 评价四
	private String evaluate5;		// 评价五
	private String evaluate6;		// 评价六
	private String evaluate7;		// 评价七
	private String evaluate8;		// 评价八
	private String newRemarks;		// 备注说明
	private String average;		// 平均分
	
	public EeEmpEvaluation() {
		super();
	}

	public EeEmpEvaluation(String id){
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
	
	@Length(min=1, max=5, message="评价人类型 : 代码长度必须介于 1 和 5 之间")
	public String getEvaluateType() {
		return evaluateType;
	}

	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}
	
	@Length(min=0, max=1000, message="描述项一长度必须介于 0 和 1000 之间")
	public String getDescribe1() {
		return describe1;
	}

	public void setDescribe1(String describe1) {
		this.describe1 = describe1;
	}
	
	@Length(min=0, max=1000, message="描述项二长度必须介于 0 和 1000 之间")
	public String getDescribe2() {
		return describe2;
	}

	public void setDescribe2(String describe2) {
		this.describe2 = describe2;
	}
	
	@Length(min=0, max=1000, message="描述项三长度必须介于 0 和 1000 之间")
	public String getDescribe3() {
		return describe3;
	}

	public void setDescribe3(String describe3) {
		this.describe3 = describe3;
	}
	
	@Length(min=0, max=1000, message="描述项四长度必须介于 0 和 1000 之间")
	public String getDescribe4() {
		return describe4;
	}

	public void setDescribe4(String describe4) {
		this.describe4 = describe4;
	}
	
	@Length(min=0, max=3, message="评价一长度必须介于 0 和 3 之间")
	public String getEvaluate1() {
		return evaluate1;
	}

	public void setEvaluate1(String evaluate1) {
		this.evaluate1 = evaluate1;
	}
	
	@Length(min=0, max=3, message="评价二长度必须介于 0 和 3 之间")
	public String getEvaluate2() {
		return evaluate2;
	}

	public void setEvaluate2(String evaluate2) {
		this.evaluate2 = evaluate2;
	}
	
	@Length(min=0, max=3, message="评价三长度必须介于 0 和 3 之间")
	public String getEvaluate3() {
		return evaluate3;
	}

	public void setEvaluate3(String evaluate3) {
		this.evaluate3 = evaluate3;
	}
	
	@Length(min=0, max=3, message="评价四长度必须介于 0 和 3 之间")
	public String getEvaluate4() {
		return evaluate4;
	}

	public void setEvaluate4(String evaluate4) {
		this.evaluate4 = evaluate4;
	}
	
	@Length(min=0, max=3, message="评价五长度必须介于 0 和 3 之间")
	public String getEvaluate5() {
		return evaluate5;
	}

	public void setEvaluate5(String evaluate5) {
		this.evaluate5 = evaluate5;
	}
	
	@Length(min=0, max=3, message="评价六长度必须介于 0 和 3 之间")
	public String getEvaluate6() {
		return evaluate6;
	}

	public void setEvaluate6(String evaluate6) {
		this.evaluate6 = evaluate6;
	}
	
	@Length(min=0, max=3, message="评价七长度必须介于 0 和 3 之间")
	public String getEvaluate7() {
		return evaluate7;
	}

	public void setEvaluate7(String evaluate7) {
		this.evaluate7 = evaluate7;
	}
	
	@Length(min=0, max=3, message="评价八长度必须介于 0 和 3 之间")
	public String getEvaluate8() {
		return evaluate8;
	}

	public void setEvaluate8(String evaluate8) {
		this.evaluate8 = evaluate8;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
	@Length(min=0, max=5, message="平均分长度必须介于 0 和 5 之间")
	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}
	
}