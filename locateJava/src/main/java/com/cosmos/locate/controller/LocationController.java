package com.cosmos.locate.controller;

import com.alibaba.fastjson.JSON;
import com.cosmos.locate.entity.Location;
import com.cosmos.locate.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    @RequestMapping(value="find",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String find(@RequestParam("phone") String phone) {

        Location result = locationService.findByPhone(phone);

        return JSON.toJSONString(result);
    }

    @RequestMapping(value="save",produces="text/html;charset=UTF-8")
    @ResponseBody
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
}
