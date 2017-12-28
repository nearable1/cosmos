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
import com.nssol_sh.util.tools.wechat.qy.model.api.APIResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.delete.DeleteUserParamsModel;

/**
 * 删除成员API
 *
 * @author he.jiaqi
 *
 */
public class DeleteUser extends APIExcutor {

	/**
	 * 执行参数
	 */
	private DeleteUserParamsModel _params;

	/**
	 * 执行参数设定
	 */
	public DeleteUser setParams(DeleteUserParamsModel params) {
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
	public APIResultModel run() {

		// 设置调用参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 令牌
		params.add(new BasicNameValuePair("access_token", _params.accesstoken));

		// 成员ID
		params.add(new BasicNameValuePair("userid", _params.userid));

		// API的URL
		String url = CnfPropsUtil.getProperty("wx.io.qyapi.url.user.deleteuser");

		// 如果是测试的情况
		if (!StringUtils.isEmpty(_params.test) && "true".equals(_params.test)) {
			url = CnfPropsUtil.getProperty("wx.io.qyapi.test.url.user.deleteuser");
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
	protected APIResultModel analysisResult(String s) {
		// 返回值
		APIResultModel result = new APIResultModel();

		// 转换类型
		result = JSON.parseObject(s, APIResultModel.class);

		// 返回
		return result;
	}
}