/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.gym.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 健身知识Entity
 * @author liuyigeng
 * @version 2018-05-02
 */
public class Gym extends DataEntity<Gym> {
	
	private static final long serialVersionUID = 1L;
	private String videoUrl;		// 视频路径
	private String videoName;		// 视频名称
	private String sellerId;		// 商家id
	private String articalName;		// 文章名称
	private String pictureUrl;		// 图片路径
	private String content;		// 内容
	private String coverUrl;		// 封面路径
	
	public Gym() {
		super();
	}

	public Gym(String id){
		super(id);
	}

	@Length(min=0, max=64, message="视频路径长度必须介于 0 和 64 之间")
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	@Length(min=0, max=64, message="视频名称长度必须介于 0 和 64 之间")
	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	
	@Length(min=0, max=64, message="商家id长度必须介于 0 和 64 之间")
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
	@Length(min=0, max=64, message="文章名称长度必须介于 0 和 64 之间")
	public String getArticalName() {
		return articalName;
	}

	public void setArticalName(String articalName) {
		this.articalName = articalName;
	}
	
	@Length(min=0, max=64, message="图片路径长度必须介于 0 和 64 之间")
	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	@Length(min=0, max=500, message="内容长度必须介于 0 和 500 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=64, message="封面路径长度必须介于 0 和 64 之间")
	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	
}