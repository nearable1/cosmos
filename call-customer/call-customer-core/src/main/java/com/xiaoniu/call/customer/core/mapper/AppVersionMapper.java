package com.xiaoniu.call.customer.core.mapper;

import com.xiaoniu.call.customer.core.entity.AppVersion;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AppVersionMapper extends Mapper<AppVersion> {

    AppVersion getMaxAppVersionConfig(@Param("appType") Integer appType, @Param("versionNumber") String versionNumber, @Param("channel") String channel);

    AppVersion selectValidVersionUpdateByAPPVersionNumber(@Param("appType") Integer appType, @Param("versionNumber") String versionNumber);
}