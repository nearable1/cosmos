package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.OsBannerBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.ext.OsBannerExt;
import com.xiaoniu.walking.web.core.service.OsBannerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 运营Banner配置
 *@author chenguohua
 *@date 2019/5/22 11:50
 */
@RestController
@RequestMapping("/walkingwebapi/os-banner")
@Log4j2
public class OsBannerController {

    @Autowired
    private OsBannerService osBannerService;

    @GetMapping("/list")
    @ActionLogger(moduleName = "banner管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                OsBannerBO osBannerBO) {
        if (pageIndex != null) {
            osBannerBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            osBannerBO.setPageSize(pageSize);
        }

        if (Objects.nonNull(osBannerBO.getTimes())) {
            osBannerBO.setSTime(osBannerBO.getTimes()[0]);
            osBannerBO.setETime(osBannerBO.getTimes()[1]);
        }

        PageResult<OsBannerExt> os_bannerPageResult = osBannerService.findOsBannerByPage(osBannerBO);
        return ResultBean.format(os_bannerPageResult);
    }

    @PostMapping("/saveBanner")
    @ActionLogger(moduleName = "banner增加", actionType = ActionLoggerCons.ADD)
    public ResultBean saveBanner(OsBannerExt osBannerExt) {

        int i =  osBannerService.insertOsBanner(osBannerExt);
        return ResultBean.format(i);
    }

    @PutMapping("/updateBanner")
    @ActionLogger(moduleName = "banner修改", actionType = ActionLoggerCons.MODIFY)
    public ResultBean updateBanner(OsBannerExt osBannerExt) {

        int i =  osBannerService.updateOsBanner(osBannerExt);
        return ResultBean.format(i);
    }

    @DeleteMapping("/deleteBanner")
    @ActionLogger(moduleName = "banner删除", actionType = ActionLoggerCons.DELETE)
    public ResultBean updateBanner(Integer bannerId) {

        int i =  osBannerService.deleteOsBannerByBannerId(bannerId);
        return ResultBean.format(i);
    }
}
