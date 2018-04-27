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
	
	@RequestMapping(value="/place/v2/search",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getSearch(@RequestParam(value="query") String query,
			@RequestParam(value="scope") String scope,
			@RequestParam(value="filter") String filter,
			@RequestParam(value="coord_type") String coord_type,
			@RequestParam(value="page_size") String page_size,
			@RequestParam(value="page_num") String page_num,
			@RequestParam(value="output") String output,
			@RequestParam(value="ak") String ak,
			@RequestParam(value="sn") String sn,
			@RequestParam(value="timestamp") String timestamp,
			@RequestParam(value="radius") String radius,
			@RequestParam(value="ret_coordtype") String ret_coordtype,
			@RequestParam(value="location") String location) {
		
		String result = null;
		
		String url = "https://api.map.baidu.com/place/v2/search?"
				+ "query="+query
				+ "&scope="+scope
				+ "&filter="+filter
				+ "&coord_type="+coord_type
				+ "&page_size="+page_size
				+ "&page_num="+page_num
				+ "&output="+output
				+ "&ak="+ak
				+ "&sn="+sn
				+ "&timestamp="+timestamp
				+ "&radius="+radius
				+ "&ret_coordtype="+ret_coordtype
				+ "&location="+location;
		
		System.out.println("url"+url);
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
	@RequestMapping(value="/place/v2/suggestion",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getSuggestion(@RequestParam(value="query") Object query,
			@RequestParam(value="region") Object region,
			@RequestParam(value="city_limit") Object city_limit,
			@RequestParam(value="output") Object output,
			@RequestParam(value="ak") Object ak,
			@RequestParam(value="sn") Object sn,
			@RequestParam(value="timestamp") Object timestamp,
			@RequestParam(value="ret_coordtype") Object ret_coordtype) {
		
		String result = null;
		
		String url = "https://api.map.baidu.com/place/v2/suggestion?"
				+ "query="+query
				+ "&region="+region
				+ "&city_limit="+city_limit
				+ "&output="+output
				+ "&ak="+ak
				+ "&sn="+sn
				+ "&timestamp="+timestamp
				+ "&ret_coordtype="+ret_coordtype;
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
	@RequestMapping(value="/geocoder/v2",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getV2(@RequestParam(value="ret_coordtype") String coordtype,
			@RequestParam(value="pois") String pois,
			@RequestParam(value="output") String output,
			@RequestParam(value="ak") String ak,
			@RequestParam(value="sn") String sn,
			@RequestParam(value="timestamp") String timestamp,
			@RequestParam(value="ret_coordtype") String ret_coordtype,
			@RequestParam(value="location") String location) {
		
		String result = null;
		
		String url = "https://api.map.baidu.com/geocoder/v2?"
				+ "ret_coordtype="+ret_coordtype
				+ "&pois="+pois
				+ "&output="+output
				+ "&ak="+ak
				+ "&sn="+sn
				+ "&timestamp="+timestamp
				+ "&ret_coordtype="+ret_coordtype
				+ "&location="+location;
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
	@RequestMapping(value="/telematics/v3/weather",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWeather(
			@RequestParam(value="coord_type") String coord_type,
			@RequestParam(value="output") String output,
			@RequestParam(value="ak") String ak,
			@RequestParam(value="sn") String sn,
			@RequestParam(value="timestamp") String timestamp,
			@RequestParam(value="location") String location) {
		
		String result = null;
		
		String url = "https://api.map.baidu.com/telematics/v3/weather?"
				+ "coord_type="+coord_type
				+ "&output="+output
				+ "&ak="+ak
				+ "&sn="+sn
				+ "&timestamp="+timestamp
				+ "&location="+location;
		
		result = urlString.getDataFromUrl(url);
		
		return result;
	}
	
}
