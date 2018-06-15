package com.cosmos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cosmos.utils.SecretUtils;
import com.cosmos.utils.UrlUtils;

@Controller
public class Action {
//	@Autowired
//	private UserService us;
	
	UrlUtils urlString = new UrlUtils();
	
	@RequestMapping(value="index.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String index() {
		System.out.println("hello world");
		return "hello";
	}
	
	//ªÒ»°rundata
	@RequestMapping(value="getRunData.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getRunData(@RequestParam(value="js_code") String js_code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?"
				+ "appid=wx2369783e0c957bad&"
				+ "secret=35c7402e658b21674d856dc734a1d928"
				+ "&js_code="+js_code+"&grant_type=authorization_code";
		
		String result = urlString.postDataFromUrl(url);
		
		return result;
	}
	
	@RequestMapping(value="decodeRunData.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String decodeRunData(@RequestParam(value="encryptedData") String encryptedData,
			@RequestParam(value="iv") String iv,
			@RequestParam(value="session_key") String sessionKey) {
		
		String appId = "wx2369783e0c957bad";
		String result = null;
		try {
			//encryptedData, iv, appId, sessionKey
			result = SecretUtils.AES128CBCdecrypt(encryptedData, iv, appId, sessionKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
