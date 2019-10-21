package com.xiaoniu.call.video.core.mapper;

import com.xiaoniu.call.video.core.dto.VideoDTO;
import com.xiaoniu.call.video.core.entity.VideoCollection;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoCollectionMapper extends Mapper<VideoCollection> {

    int selectCountByDeviceIdAndVideoNumber(@Param("deviceId") String deviceId, @Param("videoNumber") String videoNumber);

    int deleteByDeviceIdAndVideoNumber(@Param("deviceId") String deviceId, @Param("videoNumber") String videoNumber);

    List<String> selectListByDeviceIdByPage(@Param("deviceId") String deviceId);

    int selectCountByDeviceId(@Param("deviceId") String deviceId);
}