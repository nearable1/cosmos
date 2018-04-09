package com.cosmos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cosmos.service.UserService;
import com.cosmos.utils.SecretUtils;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
	//根据种类获取音乐文件
	@RequestMapping(value="getRunData.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getRunData(@RequestParam(value="js_code") String js_code) {
		HashMap<String, String> map = new HashMap<String, String>();

		try {	
			URL url = new URL("https://api.weixin.qq.com/sns/jscode2session?"
					+ "appid=wx24637ac470fd8876&"
					+ "secret=d3d4668c301717c18f77f58e1e1e2b8e"
					+ "&js_code="+js_code+"&grant_type=authorization_code");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
			conn.setRequestMethod("POST");//修改发送方式  
			//{"session_key":"JiaiVZBrHkG\/dE0oQyc54A==","openid":"o1PkD5k6brfGPpdtAd4C0dRvRLDQ"} 
			conn.setRequestProperty("Content-Type",  
                    "application/x-www-form-urlencoded");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setUseCaches(false);  
            conn.setDoOutput(true);  
  
            // 获取响应状态  
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {  
                return "";  
            }  
            // 获取响应内容体  
            String line, result = "";  
            BufferedReader in = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream(), "utf-8"));  
            while ((line = in.readLine()) != null) {  
                result += line + "\n";  
            }  
            //"session_key":"nG5oPueAhQchSdYesE52bQ==","openid":"o1PkD5k6brfGPpdtAd4C0dRvRLDQ"
            in.close();  
            return result;  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}
	
	@RequestMapping(value="decodeRunData.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String decodeRunData(@RequestParam(value="encryptedData") String encryptedData,
			@RequestParam(value="iv") String iv,
			@RequestParam(value="appid") String appId,
			@RequestParam(value="session_key") String sessionKey) {
		
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
