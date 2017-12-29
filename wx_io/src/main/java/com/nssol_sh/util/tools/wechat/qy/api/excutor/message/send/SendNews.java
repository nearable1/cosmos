package com.nssol_sh.util.tools.wechat.qy.api.excutor.message.send;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.util.tools.http.HttpUtil;
import com.nssol_sh.util.tools.properties.CnfPropsUtil;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.APIExcutor;
import com.nssol_sh.util.tools.wechat.qy.model.api.message.send.MsgSendResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.message.send.sendnews.SendNewsParamsModel;

/**
 * 发送图文信息API
 *
 * @author liu.yigeng
 *
 */
public class SendNews extends APIExcutor {

	/**
	 * 执行参数
	 */
	private SendNewsParamsModel _params;

	/**
	 * 执行参数设定
	 */
	public SendNews setParams(SendNewsParamsModel params) {
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
	public MsgSendResultModel run() {
		// 设置调用参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// 企业Id
		params.add(new BasicNameValuePair("access_token", _params.accesstoken));

		// API的URL
		String url = CnfPropsUtil.getProperty("wx.io.qyapi.url.message.send");

		// 如果是测试的情况
		if (!StringUtils.isEmpty(_params.test) && "true".equals(_params.test)) {
			url = CnfPropsUtil.getProperty("wx.io.qyapi.test.url.message.send");
		}

		// 固定消息类型为文本
		_params.message.msgtype = "news";

		// 输出值
		String output = JSON.toJSONString(_params.message);

		// HTTP操作对象
		HttpUtil hu = new HttpUtil();

		// 调用API
		String resString = hu.doPost(url, params, output);
		// 解析API返回值
		return this.analysisResult(resString);
	}

	/**
	 * 解析返回值
	 *
	 * @return 返回值模块
	 */
	@Override
	protected MsgSendResultModel analysisResult(String s) {
		// 返回值
		MsgSendResultModel result = new MsgSendResultModel();

		// 转换类型
		result = JSON.parseObject(s, MsgSendResultModel.class);

		// 返回
		return result;
	}
}