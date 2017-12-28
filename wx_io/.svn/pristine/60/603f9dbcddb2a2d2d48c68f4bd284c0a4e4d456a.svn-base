package com.nssol_sh.util.tools.wechat.qy.api.excutor.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.util.tools.http.HttpUtil;
import com.nssol_sh.util.tools.properties.CnfPropsUtil;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.APIExcutor;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.getuserinfo.GetUserInfoByCodeParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.getuserinfo.GetUserInfoByCodeResultModel;

/**
 * 根据code获取成员信息API
 *
 * @author he.jiaqi
 *
 */
public class GetUserInfoByCode extends APIExcutor {

	/**
	 * 执行参数
	 */
	private GetUserInfoByCodeParamsModel _params;

	/**
	 * 执行参数设定
	 */
	public GetUserInfoByCode setParams(GetUserInfoByCodeParamsModel params) {
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
	public GetUserInfoByCodeResultModel run() {

		// 设置调用参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 令牌
		params.add(new BasicNameValuePair("access_token", _params.accesstoken));

		// code
		params.add(new BasicNameValuePair("code", _params.code));

		// API的URL
		String url = CnfPropsUtil.getProperty("wx.io.qyapi.url.user.getuserinfo");

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
	protected GetUserInfoByCodeResultModel analysisResult(String s) {
		// 返回值
		GetUserInfoByCodeResultModel result = new GetUserInfoByCodeResultModel();

		// 转换类型
		result = JSON.parseObject(s, GetUserInfoByCodeResultModel.class);

		// 返回
		return result;
	}
}