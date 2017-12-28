package com.nssol_sh.entity.wx.view.common.approveview.msi;

import java.util.List;
import java.util.Map;

/**
 * 案件系统信息
 * 
 * @author he.jiaqi
 *
 */
public class MatterSystemInfoModel {
	/**
	 * 申请代理标签
	 */
	public String applyActFlag;

	/**
	 * 申请权限人代码
	 */
	public String applyAuthUserCode;

	/**
	 * 申请权限人名
	 */
	public String applyAuthUserName;

	/**
	 * 申请基准日
	 */
	public String applyBaseDate;

	/**
	 * 申请日
	 */
	public String applyDate;

	/**
	 * 申请执行人代码
	 */
	public String applyExecuteUserCode;

	/**
	 * 申请执行人名
	 */
	public String applyExecuteUserName;

	/**
	 * 归档日期
	 */
	public String archiveDate;

	/**
	 * 申请人部门CD
	 */
	public String authOrgzCode;

	/**
	 * 申请人部门名称
	 */
	public String authOrgzName;

	/**
	 * 流程ID
	 */
	public String flowId;

	/**
	 * 流程名称
	 */
	public String flowName;

	/**
	 * 流程版本
	 */
	public String flowVersionId;

	/**
	 * 案件完了日期
	 */
	public String matterCplDate;

	/**
	 * 案件完了状态
	 */
	public String matterEndStatus;

	/**
	 * 案件名
	 */
	public String matterName;

	/**
	 * 案件号
	 */
	public String matterNumber;

	/**
	 * 优先级
	 */
	public String priorityLevel;

	/**
	 * 状态
	 */
	public String status;

	/**
	 * 系统案件号
	 */
	public String systemMatterId;

	/**
	 * 用户数据号
	 */
	public String userDataId;

	/**
	 * 节点编号
	 */
	public String nodeId;

	/**
	 * 是否可处理
	 */
	public boolean isPossibleToProcess;

	/**
	 * 是否可见
	 */
	public boolean isVisible;

	/**
	 * 可退回节点一览
	 */
	public List<NodeToSendBackModel> nodesToSendBack;

	/**
	 * 可处理用户一览
	 */
	public List<AuthUserAndOrgzModel> authUserAndOrgzModelList;

	/**
	 * 案件履历一览
	 */
	public List<MatterHistoryModel> modelLatesterInfo;

	/**
	 * 可处理种别一览
	 */
	public Map<String, String> processType;

	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @return the applyActFlag
	 */
	public String getApplyActFlag() {
		return applyActFlag;
	}

	/**
	 * @return the applyAuthUserCode
	 */
	public String getApplyAuthUserCode() {
		return applyAuthUserCode;
	}

	/**
	 * @return the applyAuthUserName
	 */
	public String getApplyAuthUserName() {
		return applyAuthUserName;
	}

	/**
	 * @return the applyBaseDate
	 */
	public String getApplyBaseDate() {
		return applyBaseDate;
	}

	/**
	 * @return the applyDate
	 */
	public String getApplyDate() {
		return applyDate;
	}

	/**
	 * @return the applyExecuteUserCode
	 */
	public String getApplyExecuteUserCode() {
		return applyExecuteUserCode;
	}

	/**
	 * @return the applyExecuteUserName
	 */
	public String getApplyExecuteUserName() {
		return applyExecuteUserName;
	}

	/**
	 * @return the archiveDate
	 */
	public String getArchiveDate() {
		return archiveDate;
	}

	/**
	 * @return the authOrgzCode
	 */
	public String getAuthOrgzCode() {
		return authOrgzCode;
	}

	/**
	 * @return the authOrgzName
	 */
	public String getAuthOrgzName() {
		return authOrgzName;
	}

	/**
	 * @return the flowId
	 */
	public String getFlowId() {
		return flowId;
	}

	/**
	 * @return the flowName
	 */
	public String getFlowName() {
		return flowName;
	}

	/**
	 * @return the flowVersionId
	 */
	public String getFlowVersionId() {
		return flowVersionId;
	}

	/**
	 * @return the matterCplDate
	 */
	public String getMatterCplDate() {
		return matterCplDate;
	}

	/**
	 * @return the matterEndStatus
	 */
	public String getMatterEndStatus() {
		return matterEndStatus;
	}

	/**
	 * @return the matterName
	 */
	public String getMatterName() {
		return matterName;
	}

	/**
	 * @return the matterNumber
	 */
	public String getMatterNumber() {
		return matterNumber;
	}

	/**
	 * @return the priorityLevel
	 */
	public String getPriorityLevel() {
		return priorityLevel;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the systemMatterId
	 */
	public String getSystemMatterId() {
		return systemMatterId;
	}

	/**
	 * @return the userDataId
	 */
	public String getUserDataId() {
		return userDataId;
	}

	/**
	 * @return the isPossibleToProcess
	 */
	public boolean getIsPossibleToProcess() {
		return isPossibleToProcess;
	}

	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @return the nodesToSendBack
	 */
	public List<NodeToSendBackModel> getNodesToSendBack() {
		return nodesToSendBack;
	}

	/**
	 * @return the authUserAndOrgzModelList
	 */
	public List<AuthUserAndOrgzModel> getAuthUserAndOrgzModelList() {
		return authUserAndOrgzModelList;
	}

	/**
	 * @return the modelLatesterInfo
	 */
	public List<MatterHistoryModel> getModelLatesterInfo() {
		return modelLatesterInfo;
	}

	/**
	 * @return the processType
	 */
	public Map<String, String> getProcessType() {
		return processType;
	}
}
