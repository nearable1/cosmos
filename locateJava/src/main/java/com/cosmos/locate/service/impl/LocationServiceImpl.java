package com.cosmos.locate.service.impl;

import com.cosmos.locate.dao.LocationDao;
import com.cosmos.locate.entity.Location;
import com.cosmos.locate.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationDao dao;
    @Override
    public Location findByPhone(String phone) {
        return dao.findByPhone(phone);
    }

    @Transactional
    @Override
    public int saveOrUpdate(String phone, String longitude, String latitude) {
        int result = 0;
        Location loc = dao.findByPhone(phone);
        if(loc==null) {
            dao.insert(phone,longitude, latitude);
            result = 1;
        }else if (loc!=null) {
            dao.update(phone,longitude, latitude);
            result = 1;
        }
        return result;
    }
}
