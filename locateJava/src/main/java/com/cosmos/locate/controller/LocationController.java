package com.cosmos.locate.controller;

import com.cosmos.locate.service.LocationService;
import com.cosmos.locate.utils.SecretUtils;
import com.cosmos.locate.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private UrlUtils urlString;

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

        System.out.println("url:"+url);
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
