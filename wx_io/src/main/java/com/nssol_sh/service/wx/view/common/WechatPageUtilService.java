package com.nssol_sh.service.wx.view.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nssol_sh.util.tools.wechat.qy.api.QyAPICaller;
import com.nssol_sh.util.tools.wechat.qy.cache.TokenTicketCacher;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.UserModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.get.GetUserParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.getuserinfo.GetUserInfoByCodeParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.getuserinfo.GetUserInfoByCodeResultModel;

/**
 * 微信页面操作类
 * 
 * @author liu.yigeng
 *
 */
@Service("wx.veiw.common.service")
public class WechatPageUtilService {
	/**
	 * 页面跳转状态信息
	 */
	@Value("${wx.io.oauth.state}")
	private String _state;

	/**
	 * 微信浏览器特征
	 */
	@Value("${wx.io.browser.user_agent}")
	private String _wechatUserAgent;

	/**
	 * 企业ID
	 */
	@Value("${wx.io.corp_id}")
	private String _corpid;

	/**
	 * 应用程序代码
	 */
	@Value("${wx.io.ychips.app.workflow.id}")
	private int _appid;

	/**
	 * TenantID与微信部门对照表
	 */
	@Value("${wx.io.ychips.dept_tenant}")
	private String _tenantIdsDept;

	/**
	 * 不检查的密钥（测试用）
	 */
	@Value("${wx.io.no_check_token}")
	private String _noCheckToken;

	/**
	 * 是否是微信访问
	 * 
	 * @param request
	 *            请求对象
	 * @return 是否为微信访问
	 */
	public boolean isWechatAccess(HttpServletRequest request) {
		// 如果有User Agent
		if (!StringUtils.isEmpty(request.getHeader("user-agent"))) {
			// 判断是否是微信的User Agent
			if (request.getHeader("user-agent").toLowerCase().indexOf(_wechatUserAgent) > -1) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 是否是从微信跳转
	 * 
	 * @param state
	 *            状态信息
	 * @return 是否是从微信跳转
	 */
	public boolean isOauthRedirect(String state) {
		// 如果状态信息不为空，且等于常量
		if (!StringUtils.isEmpty(state) && state.equals(_state)) {
			return true;
		}

		return false;
	}

	/**
	 * 根据code获取用户ID
	 * 
	 * @param code
	 *            微信OAUTH的code
	 * @return 用户ID
	 */
	public String getUserIdByCode(String code) {
		// 如果code不为空
		if (!StringUtils.isEmpty(code)) {
			// 获取用户ID所需要的参数
			GetUserInfoByCodeParamsModel params = new GetUserInfoByCodeParamsModel();

			// 令牌保存类
			TokenTicketCacher ttc = TokenTicketCacher.getInstance();

			// 令牌
			params.accesstoken = ttc.getAccessToken(this._corpid, this._appid);

			// code
			params.code = code;

			// 根据code换取用户ID
			GetUserInfoByCodeResultModel apiResult = QyAPICaller.getUserInfoByCode(params);

			// 如果调用成功
			if (apiResult.errcode == 0) {
				return apiResult.UserId;
			}
		}

		return null;
	}

	/**
	 * 根据用户代码获取用户信息
	 * 
	 * @param userCd
	 *            用户代码
	 * 
	 * @return 用户对象
	 */
	public UserModel getUserInfoByUserCd(String userCd) {

		// 如果用户代码不为空
		if (!StringUtils.isEmpty(userCd)) {
			// 获取用户ID所需要的参数
			GetUserParamsModel params = new GetUserParamsModel();

			// 令牌保存类
			TokenTicketCacher ttc = TokenTicketCacher.getInstance();

			// 令牌
			params.accesstoken = ttc.getAccessToken(this._corpid, this._appid);

			// 用户代码
			params.userid = userCd;

			// 根据code换取用户ID
			UserModel apiResult = QyAPICaller.getUser(params);

			// 如果调用成功
			if (apiResult.errcode == 0) {
				return apiResult;
			}
		}

		return null;
	}

	/**
	 * 根据企业微信部门ID获得TenantID
	 * 
	 * @param deptIds
	 *            部门所属一览
	 * @return TenantID
	 */
	public String getTenantIdByDeptIds(List<Integer> deptIds) {
		String result = "";

		// 如果数据有效
		if (deptIds != null && deptIds.size() > 0) {
			// Tenant Map
			Map<Integer, String> tenantMap = new HashMap<Integer, String>();

			// 解析常量
			String[] tenants = _tenantIdsDept.split("&");

			// 遍历Tenant
			if (tenants != null && tenants.length > 0) {
				for (String temp : tenants) {

					// 用:分割
					String[] tad = temp.split(":");

					// 如果格式无误
					if (tad != null && tad.length >= 2) {

						// 键值对设定
						tenantMap.put(Integer.valueOf(tad[1]), tad[0]);
					}
				}
			}

			// 遍历
			for (int i : deptIds) {
				// 获得TenantID
				if (!StringUtils.isEmpty(tenantMap.get(i))) {
					result = tenantMap.get(i);
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 是否需要页面检查判断
	 * 
	 * @param request
	 *            请求对象
	 * @return 是否需要页面检查
	 */
	public boolean needPageCheck(HttpServletRequest request) {
		// 返回值
		boolean result = true;
		if (!StringUtils.isEmpty(_noCheckToken) && !StringUtils.isEmpty(request.getParameter("nc_token"))
				&& _noCheckToken.equals(request.getParameter("nc_token"))) {
			result = false;
		}

		return result;
	}
}
