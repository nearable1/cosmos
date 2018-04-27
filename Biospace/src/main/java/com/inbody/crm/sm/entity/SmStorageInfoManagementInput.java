package com.inbody.crm.sm.entity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.inbody.crm.common.persistence.DataEntity;

public class SmStorageInfoManagementInput extends DataEntity<SmStorageInfoManagementInput>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5539449020275314892L;

	private String userId;
	private String id;
	
	/** 附件文件 */
    private MultipartFile[] files;
	
    private String  actualInstallDateStr;
    
    private String installPerson;

    private String warrantyDateTo;
    
    private Date updateDate;
    private String snNo;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String getActualInstallDateStr() {
		return actualInstallDateStr;
	}

	public void setActualInstallDateStr(String actualInstallDateStr) {
		this.actualInstallDateStr = actualInstallDateStr;
	}

	public String getInstallPerson() {
		return installPerson;
	}

	public void setInstallPerson(String installPerson) {
		this.installPerson = installPerson;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public String getWarrantyDateTo() {
		return warrantyDateTo;
	}

	public void setWarrantyDateTo(String warrantyDateTo) {
		this.warrantyDateTo = warrantyDateTo;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
