package com.nssol_sh.entity.wx.view.common.approveview.msi;

/**
 * 可退回节点实体类
 * 
 * @author liu.yigeng
 *
 */
public class NodeToSendBackModel {
	/**
	 * 节点ID
	 */
	public String nodeId;

	/**
	 * 父节点ID
	 */
	public String parentNodeId;

	/**
	 * 节点名
	 */
	public String nodeName;

	/**
	 * 父节点类型
	 */
	public String parentNodeType;

	/**
	 * 节点类型
	 */
	public String nodeType;

	/**
	 * 处理种别
	 */
	public String processType;

	/**
	 * 处理种别名
	 */
	public String processTypeName;

	/**
	 * 处理者编码
	 */
	public String authUserCode;

	/**
	 * 处理者名称
	 */
	public String authUserName;

	/**
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * @return the processTypeName
	 */
	public String getProcessTypeName() {
		return processTypeName;
	}

	/**
	 * @return the authUserCode
	 */
	public String getAuthUserCode() {
		return authUserCode;
	}

	/**
	 * @return the authUserName
	 */
	public String getAuthUserName() {
		return authUserName;
	}

	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @return the parentNodeId
	 */
	public String getParentNodeId() {
		return parentNodeId;
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @return the parentNodeType
	 */
	public String getParentNodeType() {
		return parentNodeType;
	}

	/**
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}
}
