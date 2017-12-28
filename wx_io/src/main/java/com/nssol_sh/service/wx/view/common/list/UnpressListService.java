package com.nssol_sh.service.wx.view.common.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nssol_sh.entity.wx.view.common.approveview.ai.AccountInfoModel;
import com.nssol_sh.entity.wx.view.common.wkf.user.list.RequestCheckResultModel;
import com.nssol_sh.entity.wx.view.common.wkf.user.list.RequestParamsModel;
import com.nssol_sh.entity.wx.view.common.wkf.user.list.UnprocessNodeResultModel;
import com.nssol_sh.entity.wx.view.common.wkf.user.list.UnprocessViewModel;
import com.nssol_sh.service.wx.view.common.WechatPageUtilService;
import com.nssol_sh.util.common.CommonUtil;
import com.nssol_sh.util.constants.HttpConstant;
import com.nssol_sh.util.constants.wechat.qy.QYConstant;
import com.nssol_sh.util.tools.http.HttpUtil;
import com.nssol_sh.util.tools.http.RequestUtil;
import com.nssol_sh.util.tools.http.ResponseUtil;
import com.nssol_sh.util.tools.wechat.qy.cache.UserTokenCacher;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.UserModel;

import net.arnx.jsonic.JSON;

/**
 * 获取未处理页面数据
 *
 * @author he.jiaqi
 *
 */
@Service("wx.veiw.common.list.service")
public class UnpressListService {
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
	private static final String REQUEST_KEY_TENANT_ID = "tenantid";

	/**
	 * 常量-用户编码
	 */
	private static final String REQUEST_KEY_USER_CD = "usercd";

	/**
	 * 常量-起始行数
	 */
	private static final String REQUEST_KEY_OFFSET = "offset";

	/**
	 * 常量-取得件数
	 */
	private static final String REQUEST_KEY_COUNT = "count";

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
	 * @return 未处理案件
	 * @throws Exception
	 */
	public UnprocessViewModel getListData(String output, String uri) throws Exception {
		// 返回值
		UnprocessViewModel result = null;

		// 设置调用参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 令牌
		params.add(new BasicNameValuePair(HttpConstant.HTTP_INTERNAL_TOKEN, _token));

		// 调用HTTP请求
		String resultJsonStr = http.doHttpPostString(uri, params, output);

		// 如果返回JSON字符串不为空
		if (!StringUtils.isEmpty(resultJsonStr)) {

			// 解析数据
			UnprocessNodeResultModel unrm = JSON.decode(resultJsonStr, UnprocessNodeResultModel.class);

			// 转换
			result = this.createViewObject(unrm);
		} else {
			// 初始化返回值
			result = new UnprocessViewModel();

			// 设置错误标志位
			result.error = true;

			// 通用错误
			result.err_msg = QYConstant.API_REQUEST_ERR_MSG_CMN;
		}

		// 返回数据
		return result;
	}

	/**
	 * 转换为页面模块
	 * 
	 * @param unrm
	 *            API取得数据
	 * @return 页面表示数据
	 */
	private UnprocessViewModel createViewObject(UnprocessNodeResultModel unrm) {
		// 返回值
		UnprocessViewModel result = new UnprocessViewModel();

		// 是否错误
		result.error = unrm.error;

		// 错误信息
		result.err_msg = unrm.err_msg;

		// 如果有数据
		if (unrm.data != null) {
			// 用户信息
			result.accountInfo = unrm.data.accountInfo;

			// 列表
			result.unprocessNodeList = unrm.data.unprocessNodeList;

			// 总行数
			result.total = unrm.data.total;

			// 取得行数
			result.count = unrm.data.count;
		} else {
			// 初始化帐号信息
			result.accountInfo = new AccountInfoModel();

			// 默认中文
			result.accountInfo.localId = "zh_CN";
		}
		// 返回
		return result;
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

		// 起始行数
		result += "&" + REQUEST_KEY_OFFSET + "=" + rpm.offset;

		// 起始行数
		result += "&" + REQUEST_KEY_COUNT + "=" + rpm.count;

		// 返回
		return result;
	}

	/**
	 * 检查请求并返回参数
	 * 
	 * @param request
	 *            请求对象
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

					// 获取用户部门信息
					if (!StringUtils.isEmpty(result.rpm.usercd)) {

						UserModel um = this._wechatService.getUserInfoByUserCd(result.rpm.usercd);

						// 如果用户信息不为空
						if (um != null) {
							// 获得TenantID
							result.rpm.tenantid = this._wechatService.getTenantIdByDeptIds(um.department);

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

}
