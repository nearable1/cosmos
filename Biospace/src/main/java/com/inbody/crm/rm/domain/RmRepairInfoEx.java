/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.rm.entity.RmRepairInfo;

/**
 * 维修查询维修信息
 * 
 * @author yangj
 * @version 2017-08-16
 */
public class RmRepairInfoEx extends RmRepairInfo {

    private static final long serialVersionUID = -1614795382744562112L;

    /** 最终报价单配件名称 */
    private String materialName;

    /** 样机生产日期 */
    private Date prototypeProductionDate;

    /** 预报价单id */
    private String preQuotaId;

    /** 最终报价单id */
    private String finalQuotaId;

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getPrototypeProductionDate() {
        return prototypeProductionDate;
    }

    public void setPrototypeProductionDate(Date prototypeProductionDate) {
        this.prototypeProductionDate = prototypeProductionDate;
    }

    public String getPreQuotaId() {
        return preQuotaId;
    }

    public void setPreQuotaId(String preQuotaId) {
        this.preQuotaId = preQuotaId;
    }

    public String getFinalQuotaId() {
        return finalQuotaId;
    }

    public void setFinalQuotaId(String finalQuotaId) {
        this.finalQuotaId = finalQuotaId;
    }

}