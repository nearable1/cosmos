package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.MarketChannelBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.ext.MarketChannelExt;
import com.xiaoniu.walking.web.core.service.MarketChannelService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 市场渠道Controller
 *
 * @author liuyinkai
 * @date 2019/9/24
 */
@RestController
@RequestMapping("/walkingwebapi/market-channel")
@Log4j2
public class MarketChannelController {

    @Autowired
    private MarketChannelService marketChannelService;

    @Autowired
    private PageRepository pageRepository;

    /**
     * 查询列表信息
     *
     * @param marketChannelBO
     * @return
     */
    @PostMapping("/get-market-channel-list")
    @ActionLogger(moduleName = "市场成本", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@Valid @RequestBody MarketChannelBO marketChannelBO) {
        PageResult<MarketChannelExt> marketChannelExtPageResult = marketChannelService.getAllByPage(marketChannelBO);
        return ResultBean.format(marketChannelExtPageResult);
    }


    /**
     * 新增
     *
     * @param marketChannelBO
     * @return
     */
    @PostMapping("/insert-market-channel")
    @ActionLogger(moduleName = " 市场成本", actionType = ActionLoggerCons.ADD)
    @Transactional
    public int adInsert(@RequestBody MarketChannelBO marketChannelBO) {
        int i = marketChannelService.insertInfo(marketChannelBO);
        return i;
    }

    /**
     * 编辑
     *
     * @param marketChannelBO
     * @return
     */
    @PostMapping("/update-market-channel")
    @ActionLogger(moduleName = " 市场成本", actionType = ActionLoggerCons.MODIFY)
    public int adUpdate(@RequestBody MarketChannelBO marketChannelBO) {
        int i = marketChannelService.updateInfo(marketChannelBO);
        return i;
    }

    /**
     * 删除
     *
     * @param marketChannelBO
     * @return
     */
    @PostMapping("/delete-market-channel")
    @ActionLogger(moduleName = "市场成本", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteReword(@RequestBody MarketChannelBO marketChannelBO) {

        int i = marketChannelService.deleteInfo(marketChannelBO.getId());
        return ResultBean.format(i);
    }


}
