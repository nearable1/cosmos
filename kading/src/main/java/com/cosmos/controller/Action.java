package com.cosmos.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cosmos.service.UserService;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
	//根据种类获取音乐文件
	@RequestMapping(value="getRunData.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getRunData(Model model) {
		HashMap<String, String> map = new HashMap<String, String>();

		try {	
//			URL url = new URL("https://api.weixin.qq.com/sns/jscode2session?"
//					+ "appid=wx24637ac470fd8876&"
//					+ "secret=8bfdfb8663f88494a99ca96297115306&"
//					+ "&js_code=003rSBZo0v4Z8p1x040p0CdIZo0rSBZR"+"&grant_type=authorization_code");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
//			conn.setRequestMethod("POST");//修改发送方式  
			//{"session_key":"JiaiVZBrHkG\/dE0oQyc54A==","openid":"o1PkD5k6brfGPpdtAd4C0dRvRLDQ"} 
  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("session_key", "JiaiVZBrHkG\\/dE0oQyc54A==");
		map.put("openid", "o1PkD5k6brfGPpdtAd4C0dRvRLDQ");
		return JSON.toJSONString(map);
	}
}
