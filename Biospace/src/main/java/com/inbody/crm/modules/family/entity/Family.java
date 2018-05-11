/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.family.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 商品类型Entity
 * @author liuyigeng
 * @version 2018-05-05
 */
public class Family extends DataEntity<Family> {
	
	private static final long serialVersionUID = 1L;
	private String typeName;		// 类别名称
	private String sellerId;		// 商家id
	
	public Family() {
		super();
	}

	public Family(String id){
		super(id);
	}

	@Length(min=1, max=64, message="类别名称长度必须介于 1 和 64 之间")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Length(min=1, max=64, message="商家id长度必须介于 1 和 64 之间")
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
}