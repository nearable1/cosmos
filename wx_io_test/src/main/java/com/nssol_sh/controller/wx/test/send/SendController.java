package com.nssol_sh.controller.wx.test.send;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.cache.RequestCacher;
import com.nssol_sh.entity.wx.test.message.SendResult;
import com.nssol_sh.util.log.RequestUtils;

@Controller
public class SendController implements Serializable {

	/**
	 * 发送信息
	 */
	private static final long serialVersionUID = 1L;

	@RequestMapping(value = "/cgi-bin/message/send")
	@ResponseBody
	public String messageSend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获得请求体
        String body = RequestUtils.getPostBodyString(request);

		// 返回结果
		SendResult result = new SendResult();

		result.errcode = 99999;
		result.errmsg = "API request error.";
		result.invalidparty = "";
		result.invalidtag = "";
		result.invaliduser = "";

		// 如果获取的值为空
		if (!StringUtils.isEmpty(body)) {

			Map<String, Object> jsonObj = (Map<String, Object>) JSON.parse(body);

			if (jsonObj != null && !StringUtils.isEmpty(jsonObj.get("msgtype"))) {
				// 图文
				RequestCacher.getInstance().setMessage(jsonObj);

				result.errcode = 0;
				result.errmsg = "ok";
			}
		}

		return JSON.toJSONString(result);
	}
}
