package com.cosmos.modules.controller;

import com.cosmos.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @Autowired
    public RedisOperator redis;

    //用户redis回话
    public static final String USER_REDIS_SESSION = "user-redis-session";

    //虚拟目录
    public static final String FILE_SPACE = "H:\\wx_user_resource";

    //ffmpeg文件所在位置
    public static final String ffmpegEXE = "H:\\wx_user_resource\\ffmpeg\\bin\\ffmpeg.exe";

    //数据显示页数
    public static final Integer PAGE_SIZE = 5;
}
