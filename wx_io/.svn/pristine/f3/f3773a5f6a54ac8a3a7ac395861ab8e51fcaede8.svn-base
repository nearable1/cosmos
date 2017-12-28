package com.nssol_sh.service.wx.view.cmn.wkf.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.entity.wx.view.common.wkf.process.ProcApiParamsModel;
import com.nssol_sh.service.wx.view.common.route.RouteAnaliyzerService;
import com.nssol_sh.util.constants.HttpConstant;
import com.nssol_sh.util.constants.ProgramConstant;
import com.nssol_sh.util.tools.http.HttpUtil;

/**
 * 审批请求处理逻辑
 */
@Service
public class ApproveService {

	/**
	 * HTTP操作类
	 */
	@Autowired
	private HttpUtil _http;

	/**
	 * URL获取类
	 */
	@Autowired
	private RouteAnaliyzerService _routeAnaliyzerService;

	/**
	 * 项目公用令牌
	 */
	@Value("${wx.io.ychips.internal_token}")
	private String _token;

	/**
	 * 财务节点工作流
	 */
	@Value("${wx.io.ychips.workflow.flowid}")
	private String accountFlowId;

	/**
	 * 【财务支付】审批节点
	 */
	@Value("${wx.io.ychips.workflow.account.nodeid}")
	private String accountNodeId;

	/**
	 * 用户CD
	 */
	private static final String PARAM_KEY_USER_CD = "usercd";

	/**
	 * TenantID
	 */
	private static final String PARAM_KEY_TENANT_ID = "tenantid";

	/**
	 * 处理权限人编码
	 */
	private static final String PARAM_KEY_AUTH_USER_CD = "aucd";

	/**
	 * 处理执行人编码
	 */
	private static final String PARAM_KEY_EXECUTE_USER_CD = "eucd";

	/**
	 * 处理部门编码
	 */
	private static final String PARAM_KEY_DEPARTMENT_CD = "dcd";

	/**
	 * 用户数据代码
	 */
	private static final String PARAM_KEY_USER_DATA_ID = "udid";

	/**
	 * 系统案件号
	 */
	private static final String PARAM_KEY_SYSTEM_MATTER_ID = "smid";

	/**
	 * 节点代码
	 */
	private static final String PARAM_KEY_SYSTEM_NODE_ID = "nodeid";

	/**
	 * 备注
	 */
	private static final String PARAM_KEY_COMMENT = "comment";

	/**
	 * 用户数据
	 */
	private static final String PARAM_KEY_DATA = "data";

	/**
	 * 有财务支付
	 */
	private static final String HAS_ACCOUNT_FLAG = "1";

	/**
	 * 发送审批通过请求
	 * 
	 * @param procApiParamsModel
	 *            处理API所需参数
	 * @return API执行结果
	 */
	public String approve(ProcApiParamsModel procApiParamsModel) {
		// 设置调用参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 财务节点 无财务数据不提交数据
		if (isAccNode(procApiParamsModel.flowid, procApiParamsModel.nodeid)
				&& !HAS_ACCOUNT_FLAG.equals(procApiParamsModel.accountingFlag)) {

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("finEmptyFlag", true);
			data.put("error", true);
			// 提前返回数据
			return JSON.toJSONString(data);
		}
		// 令牌
		params.add(new BasicNameValuePair(HttpConstant.HTTP_INTERNAL_TOKEN, _token));

		// 请求路径
		String strUrl = _routeAnaliyzerService.getWkfProcessURL(ProgramConstant.WKF_CMN_ID_APPROVE,
				procApiParamsModel.sf);

		// 请求审批API
		String resultJsonStr = _http.doHttpPostString(strUrl, params, this.paramToApiPostBody(procApiParamsModel));

		// 返回处理结果
		return resultJsonStr;
	}

	/**
	 * 财务支付数据是否为空
	 * 
	 * @param 审批nodeId
	 * @param 审批flowId
	 * @param 审批accountingFlag
	 * @return true：有财务节点及数据，正常提交审批数据
	 */
	public boolean isAccNode(String nodeId, String flowId) {
		// 财务支付数据Flag初始化
		boolean hasAccFlag = false;
		// 处理获取配置文件-流程ID
		String[] getAccountFlowId = accountFlowId.split("/");
		// 处理获取配置文件-节点ID
		String[] getAccountNodeId = accountNodeId.split("/");
		// 根据flowId 判断是否为有财务节点的流程
		for (int i = 0; i < getAccountFlowId.length; i++) {
			if (flowId.equals(getAccountFlowId[i])) {
				// nodeID 继续判断
				for (int j = 0; j < getAccountNodeId.length; j++) {
					// 财务支付流程且是财务节点
					if (nodeId.equals(getAccountNodeId[i])) {
						hasAccFlag = true;
						break;
					}
				}
			}
		}
		return hasAccFlag;
	}

	/**
	 * 请求参数转换为API请求体
	 * 
	 * @param procApiParamsModel
	 *            处理API所需参数
	 * @return 请求体字符串
	 * 
	 */
	private String paramToApiPostBody(ProcApiParamsModel procApiParamsModel) {

		// 返回值
		String result = "";

		// 用户CD
		result += PARAM_KEY_USER_CD + "=" + procApiParamsModel.usercd;

		// TenantID
		result += "&" + PARAM_KEY_TENANT_ID + "=" + procApiParamsModel.tenantid;

		// 处理权限人编码
		result += "&" + PARAM_KEY_AUTH_USER_CD + "=" + procApiParamsModel.aucd;

		// 处理执行人编码
		result += "&" + PARAM_KEY_EXECUTE_USER_CD + "=" + procApiParamsModel.eucd;

		// 处理部门编码
		result += "&" + PARAM_KEY_DEPARTMENT_CD + "=" + procApiParamsModel.dcd;

		// 用户数据代码
		result += "&" + PARAM_KEY_USER_DATA_ID + "=" + procApiParamsModel.udid;

		// 系统案件号
		result += "&" + PARAM_KEY_SYSTEM_MATTER_ID + "=" + procApiParamsModel.smid;

		// 节点代码
		result += "&" + PARAM_KEY_SYSTEM_NODE_ID + "=" + procApiParamsModel.nodeid;

		// 备注
		result += "&" + PARAM_KEY_COMMENT + "=" + procApiParamsModel.comment;

		// 用户数据
		result += "&" + PARAM_KEY_DATA + "=" + procApiParamsModel.data;
		// 返回
		return result;
	}

}
