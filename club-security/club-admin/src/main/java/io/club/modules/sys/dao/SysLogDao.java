package io.club.modules.sys.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.club.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 系统日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-08 10:40:56
 */
public interface SysLogDao extends BaseMapper<SysLogEntity> {
    Double today(@Param("typedate") String param);
    Double recentMonth(@Param("typedate")String param);
    Double recentWeek(@Param("typedate")String param);
    Double thisMonth(@Param("typedate")String param);
}
