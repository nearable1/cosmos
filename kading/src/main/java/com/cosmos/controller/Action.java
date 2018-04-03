package com.cosmos.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {	
			URL url = new URL("https://api.weixin.qq.com/sns/jscode2session?"
					+ "appid=wx24637ac470fd8876&"
					+ "secret=8bfdfb8663f88494a99ca96297115306&"
					+ "&js_code=003rSBZo0v4Z8p1x040p0CdIZo0rSBZR"+"&grant_type=authorization_code");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
			conn.setRequestMethod("POST");//修改发送方式  
			//{"session_key":"JiaiVZBrHkG\/dE0oQyc54A==","openid":"o1PkD5k6brfGPpdtAd4C0dRvRLDQ"}
            conn.setRequestProperty("Content-Type",  
                    "application/x-www-form-urlencoded");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setUseCaches(false);  
            conn.setDoOutput(true);  
  
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
 
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		model.addAttribute("in",in);
		return "index";
	}
}
