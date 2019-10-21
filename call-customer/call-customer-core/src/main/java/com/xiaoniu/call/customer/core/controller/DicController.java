package com.xiaoniu.call.customer.core.controller;

import com.alibaba.fastjson.JSONArray;
import com.xiaoniu.architecture.commons.web.http.HeaderHelper;
import com.xiaoniu.call.customer.core.service.AdConfigService;
import com.xiaoniu.call.customer.core.service.AppAuditService;
import com.xiaoniu.call.customer.core.service.DicService;
import com.xiaoniu.call.customer.core.vo.AsmVO;
import com.xiaoniu.call.customer.core.vo.DicVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务监控API
 * 
 * @author conly.wang 2018-05-18
 */
@RestController
@RequestMapping(value = "/dic")
@Log4j2
public class DicController {

    @Autowired
    private DicService dicService;

    @Autowired
    private AppAuditService appAuditService;

    @Autowired
    private AdConfigService adConfigService;

    @Value("${app.ad.default:true}")
    private String defaultAdValue;

    @Value("${app.tab.default:true}")
    private String defaultTabValue;

    @Value("${app.share_like.default:true}")
    private String defaultShareLikeValue;

    @Value("${app.ad.default.type:1}")
    private String defaultAdType;

    /**
     * 数据字典查询接口
     *
     * @param dicVO
     * @return
     */
    @PostMapping(value = "/query")
    public Map<String, String> query(@RequestBody @Valid DicVO dicVO) {
        return dicService.query(dicVO);
    }

    /**
     * ASM配置
     *
     * @param dicVO
     * @return
     */
    @PostMapping(value = "/asm")
    public JSONArray asm(@RequestBody @Valid AsmVO dicVO) {
        return dicService.asm(dicVO);
    }

    /**
     * ASM配置失败上报
     *
     * @param dicVO
     * @return
     */
    @PostMapping(value = "/asm/report")
    public void asmReport(@RequestBody @Valid AsmVO dicVO) {
        dicService.reportAsmLog(dicVO, 1);
    }

    /**
     * 数据字典查询接口
     *
     * @param
     * @return
     */
    @GetMapping(value = "/index")
    public Map<String, String> query() {
        DicVO dicVO = new DicVO();
        dicVO.setDicCode("INDEX");

        // 首页数据
        Map<String, String> indexMap = dicService.query(dicVO);


        if (indexMap == null) {
            indexMap = new HashMap<>();
        }

        // 广告字典
        indexMap.put("adShow", appAuditService.getAppAuditValue(HeaderHelper.getAppName(),"AD", HeaderHelper.getMarket(), HeaderHelper.getAppVersion(), defaultAdValue));

        // Tab字典
        String tabValue = appAuditService.getAppAuditValue(HeaderHelper.getAppName(),"TAB", HeaderHelper.getMarket(), HeaderHelper.getAppVersion(), defaultTabValue);
        indexMap.put("RECOMMEND", tabValue);
        indexMap.put("VIDEO", tabValue);

        // 分享和喜欢按钮
        indexMap.put("shareAndLike", appAuditService.getAppAuditValue(HeaderHelper.getAppName(),"SHARE_LIKE", HeaderHelper.getMarket(), HeaderHelper.getAppVersion(), defaultShareLikeValue));

        // 广告类型
        indexMap.put("adType", adConfigService.getAdConfig(HeaderHelper.getAppName(), defaultAdType));

        return indexMap;
    }
}
