/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.entity;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 附件Entity
 * @author yangj
 * @version 2017-09-08
 */
public class CoAttachments extends DataEntity<CoAttachments> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例
	private String assId;		// 关联ID : 附件关联的业务数据id
	private String lineNo;		// 行号
	private String address;		// 地址
	private String newRemarks;		// 备注说明
	private String fileName;		// 文件名
	private String fileType;		// 文件类型
	
	public CoAttachments() {
		super();
	}

	public CoAttachments(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=64, message="关联ID : 附件关联的业务数据id长度必须介于 1 和 64 之间")
	public String getAssId() {
		return assId;
	}

	public void setAssId(String assId) {
		this.assId = assId;
	}
	
	@Length(min=1, max=2, message="行号长度必须介于 1 和 2 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=1, max=300, message="地址长度必须介于 1 和 300 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=1, max=300, message="备注说明长度必须介于 1 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
	@Length(min=0, max=300, message="文件名长度必须介于 0 和 300 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=50, message="文件类型长度必须介于 0 和 50 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}