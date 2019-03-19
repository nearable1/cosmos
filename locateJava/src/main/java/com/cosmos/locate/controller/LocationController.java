package com.cosmos.locate.controller;

import com.alibaba.fastjson.JSON;
import com.cosmos.locate.entity.Location;
import com.cosmos.locate.service.LocationService;
import com.cosmos.locate.utils.HttpRequestUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    @RequestMapping(value="find",produces="text/html;charset=UTF-8")
    public String find(@RequestParam("phone") String phone) {

        Location result = locationService.findByPhone(phone);

        return JSON.toJSONString(result);
    }

    @RequestMapping(value="save",produces="text/html;charset=UTF-8")
    public String saveOrUpdate(@RequestParam("phone") String phone,
                            @RequestParam("longitude") String longitude,
                               @RequestParam("latitude") String latitude) {
        int result = 0;

        result = locationService.saveOrUpdate(phone, longitude, latitude);
        if(result==0) {
            return "fail";
        }else {
            return "success";
        }
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
