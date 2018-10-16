package com.cosmos.locate.service;

import com.cosmos.locate.entity.Location;
import org.apache.ibatis.annotations.Insert;

public interface LocationService {
    Location findByPhone(String phone);
    int saveOrUpdate(String phone, String longitude, String latitude);
}
