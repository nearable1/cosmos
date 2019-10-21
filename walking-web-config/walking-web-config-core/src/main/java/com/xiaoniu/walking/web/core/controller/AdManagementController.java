package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.AdManagementBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.auto.AdManagement;
import com.xiaoniu.walking.web.core.model.ext.AdManagementExt;
import com.xiaoniu.walking.web.core.service.AdManagementService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;


/**
 * 广告管理Controller
 *
 * @author liuyinkai
 * @date 2019/9/20
 */
@RestController
@RequestMapping("/walkingwebapi/ad-management")
@Log4j2
public class AdManagementController {

    @Autowired
    private AdManagementService adManagementService;

    /**
     * 查询列表信息
     *
     * @param adManagementBO
     * @return
     */
    @PostMapping("/get-ad-management-list")
    @ActionLogger(moduleName = "广告管理", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@Valid @RequestBody AdManagementBO adManagementBO) {
        PageResult<AdManagementExt> adManagement = adManagementService.getAdManagement(adManagementBO);
        return ResultBean.format(adManagement);
    }

    /**
     * 新增
     *
     * @param adManagementBO
     * @return
     */
    @PostMapping("/ad-insert")
    @ActionLogger(moduleName = " 广告管理", actionType = ActionLoggerCons.ADD)
    @Transactional
    public int adInsert(@RequestBody AdManagementBO adManagementBO) {
        int i = adManagementService.adInsert(adManagementBO);
        if (i > 0 && adManagementBO.getState() == 1) {
            AdManagement adManagement = new AdManagement();
            BeanUtils.copyProperties(adManagementBO, adManagement);
            MongodbRepository.save(adManagement);
        }
        return i;
    }

    /**
     * 编辑
     *
     * @param adManagementBO
     * @return
     */
    @PostMapping("/ad-update")
    @ActionLogger(moduleName = " 广告管理", actionType = ActionLoggerCons.MODIFY)
    public int adUpdate(@RequestBody AdManagementBO adManagementBO) {
        int i = adManagementService.adUpdate(adManagementBO);
        if (i > 0) {
            if (adManagementBO.getState() == 1) {
                AdManagement position = MongodbRepository.findByClazz(new Query(Criteria.where("position").is(adManagementBO.getTempPosition())), AdManagement.class);
                if (Objects.nonNull(position)) {
                    // 更新
                    AdManagement adManagement = new AdManagement();
                    BeanUtils.copyProperties(adManagementBO, adManagement);
                    Update update = new Update();
                    update.set("codeName", adManagement.getCodeName());
                    update.set("codeId", adManagement.getCodeId());
                    update.set("source", adManagement.getSource());
                    update.set("appName", adManagement.getAppName());
                    update.set("version", adManagement.getVersion());
                    update.set("channel", adManagement.getChannel());
                    update.set("position", adManagement.getPosition());
                    update.set("state", adManagement.getState());
                    update.set("channel", adManagement.getChannel());
                    update.set("kankanAdIntervalNumber", adManagement.getKankanAdIntervalNumber());
                    update.set("rangeVersion", adManagement.getRangeVersion());
                    MongodbRepository.updateByClazz(new Query(Criteria.where("position").is(adManagementBO.getTempPosition())), update, AdManagement.class);
                } else {
                    // 新增
                    AdManagement adManagement = new AdManagement();
                    BeanUtils.copyProperties(adManagementBO, adManagement);
                    MongodbRepository.save(adManagement);
                }
            }else {
                MongodbRepository.delete(new Query(Criteria.where("position").is(adManagementBO.getTempPosition())), AdManagement.class);
            }
        }
        return i;
    }

    /**
     * 查询已拥有的位置
     * @return
     */
    @GetMapping("/check-position")
    @ActionLogger(moduleName = "广告管理",actionType = ActionLoggerCons.QUERY)
    public int getPosition(String position){
        int i = adManagementService.checkPosition(position);
        return i;
    }
}
