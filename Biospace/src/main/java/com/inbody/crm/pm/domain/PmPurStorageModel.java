package com.inbody.crm.pm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class PmPurStorageModel implements Serializable {

	private static final long serialVersionUID = -3729772683181877577L;

	private List<PmPurchaseStorage> storagePurList = Lists.newArrayList();
	
	/** 更新日时 */
	private Date updateDate;

	public List<PmPurchaseStorage> getStoragePurList() {
		return storagePurList;
	}

	public void setStoragePurList(List<PmPurchaseStorage> storagePurList) {
		this.storagePurList = storagePurList;
	}

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
