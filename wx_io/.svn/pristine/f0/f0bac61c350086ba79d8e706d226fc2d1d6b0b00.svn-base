package com.nssol_sh.controller.wx.api.qy.message.send;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.util.constants.wechat.qy.QYConstant;
import com.nssol_sh.util.tools.wechat.qy.api.QyAPICaller;
import com.nssol_sh.util.tools.wechat.qy.cache.TokenTicketCacher;
import com.nssol_sh.util.tools.wechat.qy.model.api.APIResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.message.send.MsgSendResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.message.send.sendnews.NewsMessageModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.message.send.sendnews.SendNewsParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.message.send.sendtext.SendTextParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.message.send.sendtext.TextMessageModel;

/**
 * 用户相关API
 * 
 * @author he.jiaqi
 *
 */
@Controller
public class MessageSendApisController {
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
	 * 应用的凭证密钥
	 */
	@Value("${wx.io.ychips.app.workflow.secret}")
	private String _corpsecret;

	/**
	 * 内部通信令牌
	 */
	@Value("${wx.io.ychips.internal_token}")
	private String _internalToken;

	/**
	 * 发送文本信息
	 * 
	 * @param token
	 *            内部令牌
	 * @param 文本信息
	 *            文本信息JSON字符串
	 * @return 执行结果
	 */
	@RequestMapping(value = "/wx/api/qy/message/send/text", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendText(@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "messageinfo", required = true) String messageinfo,
			@RequestParam(value = "test", required = false) String test) {

		// 检查令牌
		if (_internalToken.equals(token)) {

			// 文本信息
			TextMessageModel mm = JSON.parseObject(messageinfo, TextMessageModel.class);

			// 令牌保存类
			TokenTicketCacher ttc = TokenTicketCacher.getInstance();

			// 获取令牌
			String accessToken = ttc.getAccessToken(this._corpid, this._appid);

			// 发送文本信息参数
			SendTextParamsModel pm = new SendTextParamsModel();

			// 设定令牌
			pm.accesstoken = accessToken;

			// 设定文本信息
			pm.message = mm;

			// 测试标签
			pm.test = test;

			// 调用发送文本信息API
			MsgSendResultModel rm = QyAPICaller.sendTextMessage(pm);
			return JSON.toJSONString(rm);
		} else {
			// 返回公共错误
			APIResultModel arm = new APIResultModel(QYConstant.API_REQUEST_ERR_CD_CMN,
					QYConstant.API_REQUEST_ERR_MSG_CMN);
			return JSON.toJSONString(arm);
		}
	}

	/**
	 * 发送图文信息
	 * 
	 * @param token
	 *            内部令牌
	 * @param 文本信息
	 *            图文信息JSON字符串
	 * @return 执行结果
	 * @throws UnsupportedEncodingException
	 *             列外
	 */
	@RequestMapping(value = "/wx/api/qy/message/send/news", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendNews(@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "test", required = false) String test,
			@RequestParam(value = "messageinfo", required = true) String messageinfo)
					throws UnsupportedEncodingException {

		// 检查令牌
		if (_internalToken.equals(token)) {

			// 文本信息
			NewsMessageModel mm = JSON.parseObject(messageinfo, NewsMessageModel.class);

			// 令牌保存类
			TokenTicketCacher ttc = TokenTicketCacher.getInstance();

			// 获取令牌
			String accessToken = ttc.getAccessToken(this._corpid, this._appid);

			// 发送图文信息参数
			SendNewsParamsModel pm = new SendNewsParamsModel();

			// 设定令牌
			pm.accesstoken = accessToken;

			// 测试标签
			pm.test = test;

			// 设定图文信息
			pm.message = mm;

			// 图文消息URL解码
			if (pm.message != null && pm.message.news != null && pm.message.news.articles != null
					&& pm.message.news.articles.size() > 0) {
				// 遍历URL
				for (int i = 0; i < pm.message.news.articles.size(); i++) {
					// 如果URL不为空
					String urlTmp = pm.message.news.articles.get(i).url;
					if (!StringUtils.isEmpty(urlTmp)) {
						// Base64解码
						pm.message.news.articles.get(i).url = new String(Base64.decodeBase64(urlTmp));

					}
				}
			}

			// 调用发送图文信息API
			MsgSendResultModel rm = QyAPICaller.sendNewsMessage(pm);

			return JSON.toJSONString(rm);
		} else {
			// 返回公共错误
			APIResultModel arm = new APIResultModel(QYConstant.API_REQUEST_ERR_CD_CMN,
					QYConstant.API_REQUEST_ERR_MSG_CMN);
			return JSON.toJSONString(arm);
		}
	}
}
