package io.kading.modules.sys.controller;

import net.sf.json.JSONObject;
import io.kading.common.utils.HttpRequestUtils;
import io.kading.common.utils.SecretUtils;
import io.kading.common.utils.UrlUtils;
import io.kading.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
public class Action {

	@Autowired
	private UserService us;

	@Autowired
	private UrlUtils urlString;

	@RequestMapping(value="/v3/getRunData")
	public JSONObject getRunData(@RequestParam(value="js_code") String js_code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?"
				+ "appid=wx24637ac470fd8876&"
				+ "secret=d3d4668c301717c18f77f58e1e1e2b8e"
				+ "&js_code="+js_code+"&grant_type=authorization_code";

		JSONObject result = HttpRequestUtils.httpGet(url);
		return result;
	}
	
	@RequestMapping(value="/v3/decodeRunData")
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
	
	@RequestMapping(value="/v3/assistant/inputtips")
	public JSONObject getInputtips(@RequestParam(value="s") String s,
							   @RequestParam(value="platform") String platform,
							   @RequestParam(value="appname") String appname,
							   @RequestParam(value="sdkversion") String sdkversion,
							   @RequestParam(value="logversion") String logversion,
								   @RequestParam(value="location") String location,
								   @RequestParam(value="keywords") String keywords,
								   @RequestParam(value="city") String city) {
		
		String url = "https://restapi.amap.com/v3/assistant/inputtips?"
				+ "key="+"11627b48952cfcc6e18b900c50d7731f"
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion
				+ "&location="+location
				+ "&keywords="+keywords
				+ "&city="+city;

		JSONObject result = HttpRequestUtils.httpGet(url);
		return result;
	}

	@RequestMapping(value="/v3/place/around")
	public JSONObject getPoiAround(@RequestParam(value="location") String location,
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion) {
		
		String url = "https://restapi.amap.com/v3/place/around?"
				+ "key="+"11627b48952cfcc6e18b900c50d7731f"
				+ "&location="+location
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion;
		JSONObject result = HttpRequestUtils.httpGet(url);
		return result;
	}
	
	@RequestMapping(value="/v3/weather/weatherInfo")
	public JSONObject getWeather(
			@RequestParam(value="city") String city,
			@RequestParam(value="extensions") String extensions,
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion) {
		
		String url = "https://restapi.amap.com/v3/weather/weatherInfo?"
				+ "key="+"11627b48952cfcc6e18b900c50d7731f"
				+ "&city="+city
				+ "&extensions="+extensions
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion;

		JSONObject result = HttpRequestUtils.httpGet(url);
		return result;
	}

	@RequestMapping(value="/v3/direction/driving")
	public JSONObject getDrivingRoute(
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion,
			@RequestParam(value="origin") String origin,
			@RequestParam(value="destination") String destination) {

		String url = "https://restapi.amap.com/v3/direction/driving?"
				+ "key="+"11627b48952cfcc6e18b900c50d7731f"
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion
				+ "&origin="+origin
				+ "&destination="+destination;

		JSONObject result = HttpRequestUtils.httpGet(url);
		return result;
	}

	@RequestMapping(value="/v3/direction/walking")
	public JSONObject getWalkingRoute(
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion,
			@RequestParam(value="origin") String origin,
			@RequestParam(value="destination") String destination) {

		String url = "https://restapi.amap.com/v3/direction/walking?"
				+ "key="+"11627b48952cfcc6e18b900c50d7731f"
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion
				+ "&origin="+origin
				+ "&destination="+destination;

		JSONObject result = HttpRequestUtils.httpGet(url);
		return result;
	}

	@RequestMapping(value="/v3/direction/transit/integrated")
	public JSONObject getTransitRoute(
			@RequestParam(value="s") String s,
			@RequestParam(value="platform") String platform,
			@RequestParam(value="appname") String appname,
			@RequestParam(value="sdkversion") String sdkversion,
			@RequestParam(value="logversion") String logversion,
			@RequestParam(value="origin") String origin,
			@RequestParam(value="destination") String destination,
			@RequestParam(value="city") String city) {

		String url = "https://restapi.amap.com/v3/direction/transit/integrated?"
				+ "key="+"11627b48952cfcc6e18b900c50d7731f"
				+ "&s="+s
				+ "&platform="+platform
				+ "&appname="+appname
				+ "&sdkversion="+sdkversion
				+ "&logversion="+logversion
				+ "&origin="+origin
				+ "&destination="+destination
				+ "&city="+city;

		JSONObject result = HttpRequestUtils.httpGet(url);
		return result;
	}
	
}
