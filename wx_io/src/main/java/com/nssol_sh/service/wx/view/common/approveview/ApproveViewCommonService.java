package com.nssol_sh.service.wx.view.common.approveview;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewModel;
import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewResultModel;
import com.nssol_sh.entity.wx.view.common.approveview.RequestCheckResultModel;
import com.nssol_sh.entity.wx.view.common.approveview.RequestParamsModel;
import com.nssol_sh.entity.wx.view.common.approveview.msi.AuthUserAndOrgzModel;
import com.nssol_sh.entity.wx.view.common.approveview.msi.MatterHistoryModel;
import com.nssol_sh.entity.wx.view.common.approveview.msi.NodeToSendBackModel;
import com.nssol_sh.entity.wx.view.common.approveview.msi.StampInfoModel;
import com.nssol_sh.service.wx.view.common.WechatPageUtilService;
import com.nssol_sh.util.common.CommonUtil;
import com.nssol_sh.util.constants.HttpConstant;
import com.nssol_sh.util.constants.wechat.qy.QYConstant;
import com.nssol_sh.util.tools.bean.BeanMapUtil;
import com.nssol_sh.util.tools.http.HttpUtil;
import com.nssol_sh.util.tools.http.RequestUtil;
import com.nssol_sh.util.tools.http.ResponseUtil;
import com.nssol_sh.util.tools.wechat.qy.cache.UserTokenCacher;

import net.arnx.jsonic.JSON;

/**
 * 获取审批页面数据
 *
 * @author liu.yigeng
 *
 */
@Service("wx.veiw.common.approveview.service")
public class ApproveViewCommonService {
	/**
	 * 注入HTTP请求对象
	 */
	@Autowired
	private HttpUtil http;

	/**
	 * 注入微信逻辑对象
	 */
	@Autowired
	private WechatPageUtilService _wechatService;

	/**
	 * 项目公用令牌
	 */
	@Value("${wx.io.ychips.internal_token}")
	private String _token;

	/**
	 * 用户Cd关系映射获取
	 */
	@Value("${wx.io.ychips.user_mapping}")
	private String _mappingUser;

	/**
	 * 用户令牌的键名
	 */
	@Value("${wx.io.cookie.key.user_token}")
	private String _userTokenKey;

	/**
	 * cookies过期时间
	 */
	@Value("${wx.io.cookie.user_token_expires_time}")
	private String _expiresTime;

	/**
	 * 常量-TenantID
	 */
	public static final String REQUEST_KEY_TENANT_ID = "tenantid";

	/**
	 * 常量-用户编码
	 */
	public static final String REQUEST_KEY_USER_CD = "usercd";

	/**
	 * 常量-系统案件号
	 */
	public static final String REQUEST_KEY_SYSTEM_MATTER_ID = "smid";

	/**
	 * 常量-用户数据号
	 */
	public static final String REQUEST_KEY_USER_DATA_ID = "udid";

	/**
	 * 常量-节点号
	 */
	public static final String REQUEST_KEY_USER_NODE_ID = "nodeid";

	/**
	 * 常量-案件状态保留
	 */
	private static final String MATTER_STATUS_RESERVEWAIT = "reservewait";

	/**
	 * 常量-处理种别-承认
	 */
	private static final String PROCESS_TYPE_APPROVE = "4";

	/**
	 * 常量-处理种别-承认
	 */
	private static final String PROCESS_TYPE_APPROVE_S = "approve";

	/**
	 * 常量-处理种别-拒绝
	 */
	private static final String PROCESS_TYPE_DENY = "6";

	/**
	 * 常量-处理种别-拒绝
	 */
	private static final String PROCESS_TYPE_DENY_S = "deny";

	/**
	 * 常量-处理种别-保留
	 */
	private static final String PROCESS_TYPE_RESERVE = "7";

	/**
	 * 常量-处理种别-保留
	 */
	private static final String PROCESS_TYPE_RESERVE_S = "reserve";

	/**
	 * 常量-处理种别-保留取消
	 */
	private static final String PROCESS_TYPE_CLEARRESERVE = "8";

	/**
	 * 常量-处理种别-保留取消
	 */
	private static final String PROCESS_TYPE_CLEARRESERVE_S = "reservecancel";

	/**
	 * 常量-处理种别-退回
	 */
	private static final String PROCESS_TYPE_SENDBACK = "10";

	/**
	 * 常量-处理种别-退回
	 */
	private static final String PROCESS_TYPE_SENDBACK_S = "sendback";

	/**
	 * cookie存在时间
	 */
	private static final int COOKIE_AGE = 1800;

	/**
	 * 获取审批页面数据获取方法
	 * 
	 * @param output
	 *            POST体
	 * @param uri
	 *            请求路径
	 * @return
	 * @throws Exception
	 */
	public ApproveViewResultModel getApproveData(String output, String uri) throws Exception {
		// 返回值
		ApproveViewResultModel result = null;

		// 设置调用参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 令牌
		params.add(new BasicNameValuePair(HttpConstant.HTTP_INTERNAL_TOKEN, _token));

		// 调用HTTP请求
		String resultJsonStr = http.doHttpPostString(uri, params, output);

		// 如果返回JSON字符串不为空
		if (!StringUtils.isEmpty(resultJsonStr)) {

			// 解析数据
			result = JSON.decode(resultJsonStr, ApproveViewResultModel.class);
		} else {

			// 初始化返回值
			result = new ApproveViewResultModel();

			// 设置错误标志位
			result.error = true;

			// 通用错误
			result.error_msg = QYConstant.API_REQUEST_ERR_MSG_CMN;
		}

		// 返回数据
		return result;
	}

	/**
	 * API对象生成器
	 * 
	 * @param API执行类
	 * @return API对象
	 */
	@SuppressWarnings("unchecked")
	public <T extends ApproveViewModel> T createViewObject(ApproveViewResultModel avrm, Class<T> c) {

		// 返回值
		T result = null;
		// 生成对象
		try {
			// 生成View对象
			result = (T) Class.forName(c.getName()).newInstance();

			// API调用是否有错
			result.error = avrm.error;

			// 错误信息
			result.err_msg = avrm.error_msg;

			// 如果API返回结果为出错
			if (!avrm.error) {

				// 系统信息赋值
				result.matterSystemInfo = avrm.data.matterSystemInfo;

				// 账户信息赋值
				result.accountInfo = avrm.data.accountInfo;

				// 审批权限者信息转换为Base64字符串
				result.authUserAndOrgzListJsonBase64 = this.AuthUserInfo2Base64(result);

				// 检查处理方式
				result.matterSystemInfo.processType = this.checkProcessType(result.matterSystemInfo.processType,
						result.matterSystemInfo.status);

				// 退回节点一览
				result.matterSystemInfo.nodesToSendBack = this.mergeHistoryToSBNodes(
						result.matterSystemInfo.nodesToSendBack, result.matterSystemInfo.modelLatesterInfo);

				// 设置用户信息属性
				Field f = result.getClass().getDeclaredField("matterUserInfo");

				// 设置属性可编辑
				f.setAccessible(true);

				// 将用户信息Map转换成Bean后赋值给属性
				f.set(result, BeanMapUtil.map2Bean(avrm.data.matterUserInfo, f.getType()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 合并案件履历与退回节点一览
	 * 
	 * @param ntsbms
	 *            退回节点一览
	 * @param mhms
	 *            案件履历一览
	 */
	private List<NodeToSendBackModel> mergeHistoryToSBNodes(List<NodeToSendBackModel> ntsbms,
			List<MatterHistoryModel> mhms) {

		// 两个列表都有值
		if (ntsbms != null && ntsbms.size() > 0 && mhms != null && mhms.size() > 0) {
			// 遍历退回节点一览
			for (NodeToSendBackModel ntsbm : ntsbms) {

				// 遍历履历
				for (int i = mhms.size() - 1; i >= 0; i--) {

					// 如果节点相同
					if (ntsbm.nodeId.equals(mhms.get(i).nodeId)) {
						// 处理者CD
						ntsbm.authUserCode = mhms.get(i).authUserCode;

						// 处理者名
						ntsbm.authUserName = mhms.get(i).authUserName;

						// 处理种别
						ntsbm.processType = mhms.get(i).processType;

						// 处理种别名
						ntsbm.processTypeName = mhms.get(i).processTypeName;

						break;
					}
				}
			}
		}

		return ntsbms;
	}

	/**
	 * 检查处理种别
	 * 
	 * @param pt
	 *            处理种别
	 * @param status
	 *            当前案件状态
	 * @return 处理后的处理种别
	 */
	private Map<String, String> checkProcessType(Map<String, String> pt, String status) {

		// 返回值
		Map<String, String> result = new TreeMap<String, String>();

		// 如果Map不为空
		if (pt != null) {
			// 遍历处理种别
			for (Map.Entry<String, String> entry : pt.entrySet()) {
				// 如果key是以下处理种别的一种
				// 承认
				if (PROCESS_TYPE_APPROVE.equals(entry.getKey())) {
					result.put(PROCESS_TYPE_APPROVE_S, entry.getValue());
				}

				// 拒绝
				if (PROCESS_TYPE_DENY.equals(entry.getKey())) {
					result.put(PROCESS_TYPE_DENY_S, entry.getValue());
				}

				// 退回
				if (PROCESS_TYPE_SENDBACK.equals(entry.getKey())) {
					result.put(PROCESS_TYPE_SENDBACK_S, entry.getValue());
				}

				// 保留
				if (PROCESS_TYPE_RESERVE.equals(entry.getKey()) && !MATTER_STATUS_RESERVEWAIT.equals(status)) {
					result.put(PROCESS_TYPE_RESERVE_S, entry.getValue());
				}

				// 保留取消
				if (PROCESS_TYPE_CLEARRESERVE.equals(entry.getKey()) && MATTER_STATUS_RESERVEWAIT.equals(status)) {
					result.put(PROCESS_TYPE_CLEARRESERVE_S, entry.getValue());
				}
			}

		}

		// MATTER_STATUS_RESERVEWAIT
		return result;

	}

	/**
	 * 将审批人，审批人部门，印章等信息转换成Base64
	 * 
	 * @param avModel
	 *            页面实体对象
	 * @return 权限用户信息Base64转换后的字符串
	 * @throws UnsupportedEncodingException
	 *             字符串编码转换错误
	 */
	private String AuthUserInfo2Base64(ApproveViewModel avModel) throws UnsupportedEncodingException {

		// 印章信息如果为null则作为空Array
		if (avModel.matterSystemInfo.authUserAndOrgzModelList != null
				&& avModel.matterSystemInfo.authUserAndOrgzModelList.size() > 0) {
			// 判断印章信息
			for (AuthUserAndOrgzModel auom : avModel.matterSystemInfo.authUserAndOrgzModelList) {
				if (auom.stampInfo == null) {
					auom.stampInfo = new ArrayList<StampInfoModel>();
				}
			}
		}

		// 转换成JSON字符串
		String strJson = JSON.encode(avModel.matterSystemInfo.authUserAndOrgzModelList);

		// 返回Base64字符串
		return Base64.encodeBase64String(strJson.getBytes(HttpConstant.HTTP_DEFAULT_CHARSET));
	}

	/**
	 * 请求对象参数转换成
	 * 
	 * @param request
	 *            请求对象
	 */
	public RequestParamsModel requestParams2Bean(HttpServletRequest request) {

		// 返回实体
		return RequestUtil.requestParams2Bean(request, RequestParamsModel.class);
	}

	/**
	 * 请求参数转换为API请求体
	 */
	public String paramToApiPostBody(RequestParamsModel rpm) {

		// 返回值
		String result = "";

		// 用户ID
		result += REQUEST_KEY_USER_CD + "=" + rpm.usercd;

		// TenantID
		result += "&" + REQUEST_KEY_TENANT_ID + "=" + rpm.tenantid;

		// 系统案件号
		result += "&" + REQUEST_KEY_SYSTEM_MATTER_ID + "=" + rpm.smid;

		// 用户数据号
		result += "&" + REQUEST_KEY_USER_DATA_ID + "=" + rpm.udid;

		// 节点ID
		result += "&" + REQUEST_KEY_USER_NODE_ID + "=" + rpm.nodeid;

		// 返回
		return result;
	}

	/**
	 * 检查请求并返回参数
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @return 检查结果
	 */
	public RequestCheckResultModel checkRequestAndReturn(HttpServletRequest request, HttpServletResponse response) {
		// 返回值
		RequestCheckResultModel result = new RequestCheckResultModel();

		// 错误初始化
		result.error = true;

		// 是否需要检查
		boolean needPageCheck = this._wechatService.needPageCheck(request);

		// 检查是否为微信访问
		if (!needPageCheck || this._wechatService.isWechatAccess(request)) {

			// 请求参数转换
			result.rpm = this.requestParams2Bean(request);

			// 用户令牌存储类
			UserTokenCacher utc = UserTokenCacher.getInstance();

			// 获得cookie
			String userToken = RequestUtil.getCookieValue(request, _userTokenKey);

			// 从cookie中取得用户代码（如果没有则为过期用户）
			Map<String, Object> tokenValues = utc.checkToken(userToken);
			if (tokenValues != null) {
				// 设置用户CD
				result.rpm.usercd = (String) tokenValues.get(UserTokenCacher.KEY_USER_CD);

				// 设置TenantID
				result.rpm.tenantid = (String) tokenValues.get(UserTokenCacher.KEY_TENANT_ID);
			}

			// 如果未得到用户代码
			if (StringUtils.isEmpty(result.rpm.usercd)) {
				// 检查是否为OAUTH跳转
				if (this._wechatService.isOauthRedirect(result.rpm.state)) {

					// 获得访问用户号
					result.rpm.usercd = this._wechatService.getUserIdByCode(result.rpm.code);

					// 设定用户令牌
					if (!StringUtils.isEmpty(result.rpm.usercd)) {
						// 获得cookie值
						String cookieValue = utc.setToken(result.rpm.usercd, result.rpm.tenantid);

						// cookies过期时间（秒）
						int expiresTime = COOKIE_AGE;

						// 获得Cookie时间
						if (!StringUtils.isEmpty(this._expiresTime)) {
							// 算出秒数
							expiresTime = Integer.valueOf(this._expiresTime) * 60;
						}

						// 向响应中设定cookie
						ResponseUtil.setCookie(response, this._userTokenKey, cookieValue, expiresTime);
					}
				}
			}

			// 检查请求参数必须
			if (result.rpm.checkRequired()) {
				if (!StringUtils.isEmpty(_mappingUser)) {
					String replaceStr = CommonUtil.ReplaceUserCdByMapping(result.rpm.usercd, _mappingUser, true);
					if (!StringUtils.isEmpty(replaceStr)) {
						result.rpm.usercd = replaceStr;
					}
				}

				// 无错设定
				result.error = false;
			}
		}

		return result;
	}

	/**
	 * 从Map的List中取得名称
	 * 
	 * @param code
	 *            键
	 * @param list
	 *            键值列表
	 * @return 名称
	 */
	public String getNameFromList(String code, List<Map<String, Object>> list) {
		// 返回值
		String result = "";

		// 如果list有值
		if (list != null && list.size() > 0) {
			// 如果code为空
			if (StringUtils.isEmpty(code)) {
				for (Map<String, Object> map : list) {
					// 如果存在选中项
					if (map.containsKey("selected")
							&& ("true".equals(map.get("selected")) || (Boolean) map.get("selected"))) {
						// 返回选中项
						result = String.valueOf(map.get("label"));
						break;
					}
				}
			} else {
				// 如果code不为空
				for (Map<String, Object> map : list) {
					// 如果code等于list中的键
					if (code.equals(String.valueOf(map.get("value")))) {
						// 返回选中项
						result = String.valueOf(map.get("label"));
						break;
					}
				}
			}
		}

		// 返回
		return result;
	}
}
