package com.xiaoniu.call.video.core.mapper;

import com.xiaoniu.call.video.core.entity.VideoPyxCollection;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface VideoPyxCollectionMapper extends Mapper<VideoPyxCollection> {
    int selectCountByDeviceIdAndVideoNumber(@Param("deviceId") String deviceId, @Param("videoNumber") String videoNumber);

    int deleteByDeviceIdAndVideoNumber(@Param("deviceId") String deviceId, @Param("videoNumber") String videoNumber);
}