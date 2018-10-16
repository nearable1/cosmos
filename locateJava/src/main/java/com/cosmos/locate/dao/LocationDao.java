package com.cosmos.locate.dao;

import com.cosmos.locate.entity.Location;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LocationDao {
    // MyBatis 的注解
    @Select("select * from location where phone=#{phone}")
    Location findByPhone(@Param("phone")String phone);

    @Update("update location set longitude=#{longitude},latitude=#{latitude} where phone=#{phone};")
    int update(@Param("phone") String phone, @Param("longitude") String longitude, @Param("latitude") String latitude);

    @Insert("insert into location(phone,longitude, latitude) values(#{phone}, #{longitude}, #{latitude});")
    int insert(@Param("phone") String phone, @Param("longitude") String longitude, @Param("latitude") String latitude);

}
