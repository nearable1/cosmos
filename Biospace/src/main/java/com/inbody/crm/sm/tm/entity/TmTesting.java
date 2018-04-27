/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sm.tm.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import java.util.List;

import com.google.common.collect.Lists;
import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.DataEntity;
import com.inbody.crm.common.utils.excel.annotation.ExcelField;
import com.inbody.crm.modules.sys.entity.User;

/**
 * 主子表Entity
 * @author yangj
 * @version 2017-09-20
 */
public class TmTesting extends DataEntity<TmTesting> {
	
	private static final long serialVersionUID = 1L;
	
	// 检索用字段
	private Date testingDateFromSearch;		// 检测日期From
	private Date testingDateToSearch;		// 检测日期To
	private String snNoSearch;		// S/N
	private String materialNoSearch;		// 型号
	private String statusSearch;		// 当前机器状态
	private String machineTypeSearch;		// 类型
	private String testingTypeSearch;		// 检测方式
	private String testingResultSearch;		// 检测结果
	private String additionalInstructionsSearch;		// 附件说明
	private String ifPrimeProblemSearch;		// 仅抽取初期不良
	private String ifFullResultSearch;		// 显示全部检测记录
	
	private String procInsId;		// 流程实例
	private String testingNo;		// 检测编号
	private String testingType;		// 检测方式
	private String machineType;		// 机器类型
	private Date testingDate;		// 检测日期
	private String testingDateStr;		// 检测日期
	private Date stockingDate;		// 备货日期
	private String createPersonId;		// testing_person_id
	private String createPersonName;
	private String testingPersonId;		// testing_person_id
	private String testingPersonName;
	private String checkPersonId;		// check_person_id
	private String testingResult;		// 检测结果
	private String ifPrimeProblem;		// 是否初期不良
	private String newRemarks;		// 备注说明
	private String primeProblemComment;
	
	private String lineNo;		// 行号
	private String materialNo;		// 物料号
	private String snNo;		// SN
	private Date productionDate;		// 生产日期
	private String productionDateStr;		// 生产日期
	private String status;		// 机器状态
	private String ifPackingGood;		// 包装外观完好
	private String ifOutlookGood;		// 仪器外观完好
	private String additionalInstructions;		// 附加说明分类
	private String detailNewRemarks;		// 备注说明
	private String model;		// 型号
	
	private List<TmTestingDtl> tmTestingDtlList = Lists.newArrayList();		// 子表列表
	private List<User> userList = Lists.newArrayList();

    /** 附件信息 */
    private List<CoAttachments> attachmentsList;
    /** 附件文件 */
    private MultipartFile[] files;
    
	public TmTesting() {
		super();
	}

	public TmTesting(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=1, max=15, message="检测编号 : 流水(8)长度必须介于 1 和 15 之间")
	@ExcelField(title="检测编号", align=1, sort=70)
	public String getTestingNo() {
		return testingNo;
	}

	public void setTestingNo(String testingNo) {
		this.testingNo = testingNo;
	}
	
	@Length(min=1, max=5, message="检测方式 : 代码长度必须介于 1 和 5 之间")
	public String getTestingType() {
		return testingType;
	}

	public void setTestingType(String testingType) {
		this.testingType = testingType;
	}
	
	@ExcelField(title="检测日期", align=1, sort=90)
	public String getTestingDateStr() {
		return testingDateStr;
	}

	public void setTestingDateStr(String testingDateStr) {
		this.testingDateStr = testingDateStr;
	}

	@NotNull(message="检测日期不能为空")
	public Date getTestingDate() {
		return testingDate;
	}

	public void setTestingDate(Date testingDate) {
		this.testingDate = testingDate;
	}
	
	public Date getStockingDate() {
		return stockingDate;
	}

	public void setStockingDate(Date stockingDate) {
		this.stockingDate = stockingDate;
	}
	
	@Length(min=0, max=100, message="testing_person_id长度必须介于 0 和 100 之间")
	public String getTestingPersonId() {
		return testingPersonId;
	}

	public void setTestingPersonId(String testingPersonId) {
		this.testingPersonId = testingPersonId;
	}
	
	@Length(min=0, max=100, message="check_person_id长度必须介于 0 和 100 之间")
	public String getCheckPersonId() {
		return checkPersonId;
	}

	public void setCheckPersonId(String checkPersonId) {
		this.checkPersonId = checkPersonId;
	}
	
	@Length(min=1, max=5, message="检测结果 : 代码长度必须介于 1 和 5 之间")
	@ExcelField(title="检测结果", align=1, sort=80, dictType="DM0025")
	public String getTestingResult() {
		return testingResult;
	}

	public void setTestingResult(String testingResult) {
		this.testingResult = testingResult;
	}
	
	@Length(min=0, max=1, message="是否初期不良长度必须介于 0 和 1 之间")
	@ExcelField(title="初期不良", align=1, sort=110, dictType="yes_no")
	public String getIfPrimeProblem() {
		return ifPrimeProblem;
	}

	public void setIfPrimeProblem(String ifPrimeProblem) {
		this.ifPrimeProblem = ifPrimeProblem;
	}
	
	@Length(min=0, max=300, message="备注说明长度必须介于 0 和 300 之间")
	public String getNewRemarks() {
		return newRemarks;
	}

	public void setNewRemarks(String newRemarks) {
		this.newRemarks = newRemarks;
	}
	
	public List<TmTestingDtl> getTmTestingDtlList() {
		return tmTestingDtlList;
	}

	public void setTmTestingDtlList(List<TmTestingDtl> tmTestingDtlList) {
		this.tmTestingDtlList = tmTestingDtlList;
	}

	public Date getTestingDateFromSearch() {
		return testingDateFromSearch;
	}

	public void setTestingDateFromSearch(Date testingDateFromSearch) {
		this.testingDateFromSearch = testingDateFromSearch;
	}

	public Date getTestingDateToSearch() {
		return testingDateToSearch;
	}

	public void setTestingDateToSearch(Date testingDateToSearch) {
		this.testingDateToSearch = testingDateToSearch;
	}

	public String getSnNoSearch() {
		return snNoSearch;
	}

	public void setSnNoSearch(String snNoSearch) {
		this.snNoSearch = snNoSearch;
	}

	public String getMaterialNoSearch() {
		return materialNoSearch;
	}

	public void setMaterialNoSearch(String materialNoSearch) {
		this.materialNoSearch = materialNoSearch;
	}

	public String getStatusSearch() {
		return statusSearch;
	}

	public void setStatusSearch(String statusSearch) {
		this.statusSearch = statusSearch;
	}

	public String getMachineTypeSearch() {
		return machineTypeSearch;
	}

	public void setMachineTypeSearch(String machineTypeSearch) {
		this.machineTypeSearch = machineTypeSearch;
	}

	public String getTestingTypeSearch() {
		return testingTypeSearch;
	}

	public void setTestingTypeSearch(String testingTypeSearch) {
		this.testingTypeSearch = testingTypeSearch;
	}

	public String getTestingResultSearch() {
		return testingResultSearch;
	}

	public void setTestingResultSearch(String testingResultSearch) {
		this.testingResultSearch = testingResultSearch;
	}

	public String getAdditionalInstructionsSearch() {
		return additionalInstructionsSearch;
	}

	public void setAdditionalInstructionsSearch(String additionalInstructionsSearch) {
		this.additionalInstructionsSearch = additionalInstructionsSearch;
	}

	public String getIfPrimeProblemSearch() {
		return ifPrimeProblemSearch;
	}

	public void setIfPrimeProblemSearch(String ifPrimeProblemSearch) {
		this.ifPrimeProblemSearch = ifPrimeProblemSearch;
	}

	public String getIfFullResultSearch() {
		return ifFullResultSearch;
	}

	public void setIfFullResultSearch(String ifFullResultSearch) {
		this.ifFullResultSearch = ifFullResultSearch;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	@ExcelField(title="物料号", align=1, sort=10)
	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	@ExcelField(title="S/N", align=1, sort=30)
	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	@ExcelField(title="生产日期", align=1, sort=40)
	public String getProductionDateStr() {
		return productionDateStr;
	}

	public void setProductionDateStr(String productionDateStr) {
		this.productionDateStr = productionDateStr;
	}

	@ExcelField(title="类型", align=1, sort=50, dictType="DM0032")
	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	@ExcelField(title="当前状态", align=1, sort=60, dictType="DM0033")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIfPackingGood() {
		return ifPackingGood;
	}

	public void setIfPackingGood(String ifPackingGood) {
		this.ifPackingGood = ifPackingGood;
	}

	public String getIfOutlookGood() {
		return ifOutlookGood;
	}

	public void setIfOutlookGood(String ifOutlookGood) {
		this.ifOutlookGood = ifOutlookGood;
	}

	@ExcelField(title="附加说明", align=1, sort=120, dictType="DM0038")
	public String getAdditionalInstructions() {
		return additionalInstructions;
	}

	public void setAdditionalInstructions(String additionalInstructions) {
		this.additionalInstructions = additionalInstructions;
	}

	@ExcelField(title="详细说明", align=1, sort=130)
	public String getDetailNewRemarks() {
		return detailNewRemarks;
	}

	public void setDetailNewRemarks(String detailNewRemarks) {
		this.detailNewRemarks = detailNewRemarks;
	}

	@ExcelField(title="型号", align=1, sort=20)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(String createPersonId) {
		this.createPersonId = createPersonId;
	}

	public String getPrimeProblemComment() {
		return primeProblemComment;
	}

	public void setPrimeProblemComment(String primeProblemComment) {
		this.primeProblemComment = primeProblemComment;
	}

	public String getCreatePersonName() {
		return createPersonName;
	}

	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}

	public List<CoAttachments> getAttachmentsList() {
		return attachmentsList;
	}

	public void setAttachmentsList(List<CoAttachments> attachmentsList) {
		this.attachmentsList = attachmentsList;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	@ExcelField(title="检测人", align=1, sort=100)
	public String getTestingPersonName() {
		return testingPersonName;
	}

	public void setTestingPersonName(String testingPersonName) {
		this.testingPersonName = testingPersonName;
	}
}