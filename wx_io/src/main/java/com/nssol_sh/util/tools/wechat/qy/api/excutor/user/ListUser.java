package com.nssol_sh.util.tools.wechat.qy.api.excutor.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.util.tools.http.HttpUtil;
import com.nssol_sh.util.tools.properties.CnfPropsUtil;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.APIExcutor;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.list.ListUserParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.list.ListUserResultModel;

/**
 * 获取成员列表API
 *
 * @author he.jiaqi
 *
 */
public class ListUser extends APIExcutor {

	/**
	 * 执行参数
	 */
	private ListUserParamsModel _params;

	/**
	 * 执行参数设定
	 */
	public ListUser setParams(ListUserParamsModel params) {
		// 参数设定
		this._params = params;

		return this;
	}

	/**
	 * 执行API
	 * 
	 * @return 返回值模块
	 */
	@Override
	public ListUserResultModel run() {

		// 设置调用参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 令牌
		params.add(new BasicNameValuePair("access_token", _params.accesstoken));

		// 部门ID
		params.add(new BasicNameValuePair("department_id", _params.department_id));

		// 是否递归子部门
		params.add(new BasicNameValuePair("fetch_child", _params.fetch_child));

		// API的URL
		String url = CnfPropsUtil.getProperty("wx.io.qyapi.url.user.listuser");

		// 如果是测试的情况
		if (!StringUtils.isEmpty(_params.test) && "true".equals(_params.test)) {
			url = CnfPropsUtil.getProperty("wx.io.qyapi.test.url.user.listuser");
		}

		// HTTP操作对象
		HttpUtil hu = new HttpUtil();

		// 调用API
		String resString = hu.doGet(url, params);

		// 解析API返回值
		return this.analysisResult(resString);
	}

	/**
	 * 解析返回值
	 *
	 * @return 返回值模块
	 */
	@Override
	protected ListUserResultModel analysisResult(String s) {
		// 返回值
		ListUserResultModel result = new ListUserResultModel();

		// 转换类型
		result = JSON.parseObject(s, ListUserResultModel.class);

		// 返回
		return result;
	}
}