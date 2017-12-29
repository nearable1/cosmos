package com.nssol_sh.util.tools.wechat.qy.api.excutor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.util.tools.http.HttpUtil;
import com.nssol_sh.util.tools.properties.CnfPropsUtil;
import com.nssol_sh.util.tools.wechat.qy.cache.TokenTicketCacher;
import com.nssol_sh.util.tools.wechat.qy.model.api.gettoken.GetTokenParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.gettoken.GetTokenResultModel;

/**
 * 企业号获得令牌API
 *
 * @author liu.yigeng
 *
 */
public class GetToken extends APIExcutor {

	/**
	 * 执行参数
	 */
	private GetTokenParamsModel _params;

	/**
	 * 执行参数设定
	 */
	public GetToken setParams(GetTokenParamsModel params) {
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
	public GetTokenResultModel run() {

		// 设置调用参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 企业Id
		params.add(new BasicNameValuePair("corpid", _params.corpid));

		// 管理组的凭证密钥
		params.add(new BasicNameValuePair("corpsecret", _params.corpsecret));

		// API的URL
		String url = CnfPropsUtil.getProperty("wx.io.qyapi.url.gettoken");

		// HTTP操作对象
		HttpUtil hu = new HttpUtil();

		// 调用API
		String resString = hu.doGet(url, params);

		// 令牌缓存器
		TokenTicketCacher ttc = TokenTicketCacher.getInstance();

		// 请求次数累加
		ttc.requestTimesCum();

		// 解析API返回值
		return this.analysisResult(resString);
	}

	/**
	 * 解析返回值
	 *
	 * @return 返回值模块
	 */
	@Override
	protected GetTokenResultModel analysisResult(String s) {
		// 返回值
		GetTokenResultModel result = new GetTokenResultModel();

		// 转换类型
		result = JSON.parseObject(s, GetTokenResultModel.class);

		// 返回
		return result;
	}
}