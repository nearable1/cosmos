package com.cosmos.modules.controller;

import com.cosmos.common.service.imp.operate.BgmServiceImpl;
import com.cosmos.common.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "背景音乐接口",tags = {"背景音乐controller"})
@RequestMapping("/bgm")
public class BgmController {

    @Autowired
    private BgmServiceImpl bgmService;

    @ApiOperation(value = "背景音乐",notes = "背景音乐接口")
    @PostMapping("/list")
    public IMoocJSONResult list(){
        return IMoocJSONResult.ok(bgmService.quweryBgmList());
    }

}
