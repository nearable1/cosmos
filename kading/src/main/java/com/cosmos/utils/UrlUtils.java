package com.cosmos.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlUtils {
	public String getDataFromUrl(String url) {
		try {	
			URL urlString = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlString.openConnection(); 
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
}
