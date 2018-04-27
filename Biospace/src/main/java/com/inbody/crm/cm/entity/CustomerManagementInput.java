package com.inbody.crm.cm.entity;


import org.springframework.web.multipart.MultipartFile;

import com.inbody.crm.common.persistence.DataEntity;

public class CustomerManagementInput extends DataEntity<CustomerManagementInput>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7890148992092573182L;
	
	
	private String assid;
	
	/** 附件文件 */
    public MultipartFile[] files;

	public String getAssid() {
		return assid;
	}

	public void setAssid(String assid) {
		this.assid = assid;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	
    
}
