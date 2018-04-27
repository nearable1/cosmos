/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 维修查询维修信息
 * 
 * @author yangj
 * @version 2017-08-16
 */
public class RmRepairListSearch extends DataEntity<RmRepairListSearch> {

	private static final long serialVersionUID = -1614795382744562112L;

	/** 维修编号 */
	private String repairNo;

	/** sn */
	private String snNo;

	/** 机器型号 */
	private String mcModel;

	/** 配件型号 */
	private String acModel;

	/** 单位名称 */
	private String repairCusName;

	/** 负责工程师id */
	private String responsiblePersonId;

	/** 维修分类 */
	private String repairType;

	/** 处理状态 */
	private String repairStatus;

	/** 处理方式 */
	private String repairMethod;

	/** 收款情况 */
	private String receiveStatus;

	/** 开票情况 */
	private String invoiceStatus;

	/** 报修日起（查询条件） */
	private Date askRepairDateFrom;

	/** 报修日止（查询条件） */
	private Date askRepairDateTo;
	
	/** 报修日（查询结果） */
	private Date askRepairDate;

	/** 生产日期 */
	private Date productionDate;

	/** 安装日期 */
	private Date actualInstallDate;

	/** 问题描述 */
	private String issueDescribe;

	/** 情况确认 */
	private String issueDetail;

	/** 处理内容 */
	private String processingContent;

	/** 最终使用配件 */
	private String acMaterialName;

	/** 最终报价单号 */
    private String quotationId;

	/** 最终报价单号 */
	private String quotationNo;
	
    /** 最终报价单是否出库 */
    private String ifOut;
	
	/** 数量 */
	private String num;
	
	/** 最终报价单金额 */
	private BigDecimal amount;
	
	/** 工程师 */
	private String engineer;

    /** 预报价单是否占用 */
    private String ifOccupy;

	public String getRepairNo() {
		return repairNo;
	}

	public void setRepairNo(String repairNo) {
		this.repairNo = repairNo;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public String getMcModel() {
		return mcModel;
	}

	public void setMcModel(String mcModel) {
		this.mcModel = mcModel;
	}

	public String getAcModel() {
		return acModel;
	}

	public void setAcModel(String acModel) {
		this.acModel = acModel;
	}

	public String getRepairCusName() {
		return repairCusName;
	}

	public void setRepairCusName(String repairCusName) {
		this.repairCusName = repairCusName;
	}

	public String getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(String responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public String getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}

	public String getRepairMethod() {
		return repairMethod;
	}

	public void setRepairMethod(String repairMethod) {
		this.repairMethod = repairMethod;
	}

	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Date getAskRepairDateFrom() {
		return askRepairDateFrom;
	}

	public void setAskRepairDateFrom(Date askRepairDateFrom) {
		this.askRepairDateFrom = askRepairDateFrom;
	}

	public Date getAskRepairDateTo() {
		return askRepairDateTo;
	}

	public void setAskRepairDateTo(Date askRepairDateTo) {
		this.askRepairDateTo = askRepairDateTo;
	}

    public Date getAskRepairDate() {
        return askRepairDate;
    }

    public void setAskRepairDate(Date askRepairDate) {
        this.askRepairDate = askRepairDate;
    }

    public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Date getActualInstallDate() {
		return actualInstallDate;
	}

	public void setActualInstallDate(Date actualInstallDate) {
		this.actualInstallDate = actualInstallDate;
	}

	public String getIssueDescribe() {
		return issueDescribe;
	}

	public void setIssueDescribe(String issueDescribe) {
		this.issueDescribe = issueDescribe;
	}

	public String getIssueDetail() {
		return issueDetail;
	}

	public void setIssueDetail(String issueDetail) {
		this.issueDetail = issueDetail;
	}

	public String getProcessingContent() {
		return processingContent;
	}

	public void setProcessingContent(String processingContent) {
		this.processingContent = processingContent;
	}

	public String getAcMaterialName() {
		return acMaterialName;
	}

	public void setAcMaterialName(String acMaterialName) {
		this.acMaterialName = acMaterialName;
	}

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public String getIfOut() {
        return ifOut;
    }

    public void setIfOut(String ifOut) {
        this.ifOut = ifOut;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getIfOccupy() {
        return ifOccupy;
    }

    public void setIfOccupy(String ifOccupy) {
        this.ifOccupy = ifOccupy;
    }

}