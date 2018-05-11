/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.goods.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 商品管理Entity
 * @author liuyigeng
 * @version 2018-05-07
 */
public class Goods extends DataEntity<Goods> {
	
	private static final long serialVersionUID = 1L;
	private String typeid;		// 类型id
	private String typename;
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	private String sellerid;		// 商家id
	private String price;		// 价格
	private String count;		// 剩余数量
	private String discount;		// 折扣价
	private String purchased;		// 已购买数量
	private String liked;		// 被收藏数
	private String picture;		// 图片
	private String size;		// 尺寸
	
	public Goods() {
		super();
	}

	public Goods(String id){
		super(id);
	}

	@Length(min=0, max=64, message="类型id长度必须介于 0 和 64 之间")
	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	
	@Length(min=0, max=64, message="商家id长度必须介于 0 和 64 之间")
	public String getSellerid() {
		return sellerid;
	}

	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}
	
	@Length(min=0, max=64, message="价格长度必须介于 0 和 64 之间")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=32, message="剩余数量长度必须介于 0 和 32 之间")
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	@Length(min=0, max=64, message="折扣价长度必须介于 0 和 64 之间")
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	@Length(min=0, max=32, message="已购买数量长度必须介于 0 和 32 之间")
	public String getPurchased() {
		return purchased;
	}

	public void setPurchased(String purchased) {
		this.purchased = purchased;
	}
	
	@Length(min=0, max=32, message="被收藏数长度必须介于 0 和 32 之间")
	public String getLiked() {
		return liked;
	}

	public void setLiked(String liked) {
		this.liked = liked;
	}
	
	@Length(min=0, max=64, message="图片长度必须介于 0 和 64 之间")
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Length(min=0, max=64, message="尺寸长度必须介于 0 和 64 之间")
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}