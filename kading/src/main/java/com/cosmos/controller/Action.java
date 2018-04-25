package com.cosmos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cosmos.service.UserService;
import com.cosmos.utils.SecretUtils;
import com.cosmos.utils.UrlUtils;

@Controller
public class Action {
	@Autowired
	private UserService us;
	
	UrlUtils urlString = new UrlUtils();
	
	//ªÒ»°rundata
	@RequestMapping(value="getRunData.html",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getRunData(@RequestParam(value="js_code") String js_code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?"
				+ "appid=wx24637ac470fd8876&"
				+ "secret=d3d4668c301717c18f77f58e1e1e2b8e"
				+ "&js_code="+js_code+"&grant_type=authorization_code";
		
		String result = urlString.postDataFromUrl(url);
		
		return result;
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
	
	@RequestMapping(value="/v3/geocode/regeo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getRegeo(@RequestParam(value="key") String key,
			@RequestParam(value="location") String location,
			@RequestParam(value="extensions") String extensions,
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion) {
		
		String result = null;
		
		String url = "https://restapi.amap.com/v3/geocode/regeo?"
				+ "key="+key
				+ "&location="+location
				+ "&extensions="+extensions
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion;
		
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
	@RequestMapping(value="/v3/weather/weatherInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWeatherInfo(@RequestParam(value="key") Object key,
			@RequestParam(value="city") Object city,
			@RequestParam(value="extensions") Object extensions,
			@RequestParam(value="s") Object s,
			@RequestParam(value="platform") Object platform,
			@RequestParam(value="appname") Object appname,
			@RequestParam(value="sdkversion") Object sdkversion,
			@RequestParam(value="logversion") Object logversion) {
		
		String result = null;
		
		String url = "https://restapi.amap.com/v3/weather/weatherInfo?"
				+ "key="+key
				+ "&city="+city
				+ "&extensions="+extensions
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion;
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
	@RequestMapping(value="/v3/place/around",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getAround(@RequestParam(value="key") String key,
			@RequestParam(value="location") String location,
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion) {
		
		String result = null;
		
		String url = "https://restapi.amap.com/v3/place/around?"
				+ "key="+key
				+ "&location="+location
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion;
		System.out.println("around:"+url);
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
//	@RequestMapping(value="/v3/staticmap",produces="text/html;charset=UTF-8")
//	@ResponseBody
//	public String getStaticmap(@RequestParam(value="location") String location,
//			@RequestParam(value="zoom") String zoom,
//			@RequestParam(value="size") String size,
//			@RequestParam(value="scale") String scale,
//			@RequestParam(value="markers") String markers,
//			@RequestParam(value="labels") String labels,
//			@RequestParam(value="paths") String paths,
//			@RequestParam(value="traffic") String traffic) {
//		
//		String result = null;
//		
//		String url = "https://restapi.amap.com/v3/staticmap?"
//				+ "location="+location
//				+ "&zoom="+zoom
//				+ "&size="+size
//				+ "&scale="+scale
//				+ "&markers="+markers
//				+ "&labels="+labels
//				+ "&paths="+paths
//				+ "&traffic="+traffic;
//		
//		result = urlString.getDataFromUrl(url);
//		
//		return result;
//	}
	
	@RequestMapping(value="/v3/assistant/inputtips",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getInputtips(@RequestParam(value="key") String key,
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion) {
		
		String result = null;
		
		String url = "https://restapi.amap.com/v3/assistant/inputtips?"
				+ "key="+key
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion;
		System.out.println("url:"+url);
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
	@RequestMapping(value="/v3/direction/driving",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getDriving(@RequestParam(value="key") String key,
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion) {
		
		String result = null;
		
		String url = "https://restapi.amap.com/v3/direction/driving?"
				+ "key="+key
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion;
		
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
	@RequestMapping(value="/v3/direction/walking",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWalking(@RequestParam(value="key") String key,
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion) {
		
		String result = null;
		
		String url = "https://restapi.amap.com/v3/direction/walking?"
				+ "key="+key
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion;
		
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
	@RequestMapping(value="/v3/direction/transit/integrated",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getIntegrated(@RequestParam(value="key") String key,
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion) {
		
		String result = null;
		
		String url = "https://restapi.amap.com/v3/direction/transit/integrated?"
				+ "key="+key
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion;
		
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
	@RequestMapping(value="/v4/direction/bicycling",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getBicycling(@RequestParam(value="key") String key,
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion) {
		
		String result = null;
		
		String url = "https://restapi.amap.com/v4/direction/bicycling?"
				+ "key="+key
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion;
		
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
}
