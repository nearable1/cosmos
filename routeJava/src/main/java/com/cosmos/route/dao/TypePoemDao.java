package com.cosmos.route.dao;

import com.cosmos.route.entity.TypePoem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TypePoemDao {
    // MyBatis 的注解
    @Select("select * from type_poem where type=#{type}")
    List<TypePoem> findByType(@Param("type")int type);

    @Select("select `describe` from nature where id=#{id}")
    String findByTypeId(@Param("id")int id);
}
