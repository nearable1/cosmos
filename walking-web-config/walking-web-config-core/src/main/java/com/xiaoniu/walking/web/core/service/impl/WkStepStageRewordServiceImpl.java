package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.bo.WkStepStageRewordBO;
import com.xiaoniu.walking.web.core.mapper.ext.AdManagementExtMapper;
import com.xiaoniu.walking.web.core.mapper.ext.WkStepStageRewordExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.model.ext.AdManagementExt;
import com.xiaoniu.walking.web.core.model.ext.WkStepStageRewordExt;
import com.xiaoniu.walking.web.core.service.WkStepStageRewordService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 阶段奖励管理
 *
 * @author liuyinkai
 * @date 20190919
 */
@Service
public class WkStepStageRewordServiceImpl implements WkStepStageRewordService {

    @Autowired
    private WkStepStageRewordExtMapper wkStepStageRewordExtMapper;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageResult<WkStepStageRewordExt> getManagement(WkStepStageRewordBO wkStepStageRewordBO) {
        PageResult<WkStepStageRewordExt> selectByPage = pageRepository.selectPaging(WkStepStageRewordExtMapper.class, "getManagement", wkStepStageRewordBO);
        return selectByPage;
    }

    @Override
    public WkStepStageRewordExt getInfoById(Integer id) {
        WkStepStageRewordExt infoById = wkStepStageRewordExtMapper.getInfoById(id);
        return infoById;
    }

    @Override
    public int rewordInsert(WkStepStageRewordBO wkStepStageRewordBO) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        wkStepStageRewordBO.setCreateMan(user.getUserId());
        wkStepStageRewordBO.setModifyMan(user.getUserId());
        int i = wkStepStageRewordExtMapper.rewordInsert(wkStepStageRewordBO);
        return i;
    }

    @Override
    public int rewordUpdate(WkStepStageRewordBO wkStepStageRewordBO) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        wkStepStageRewordBO.setModifyTime(new Date());
        wkStepStageRewordBO.setModifyMan(user.getUserId());
        int i = wkStepStageRewordExtMapper.rewordUpdate(wkStepStageRewordBO);
        return i;
    }

    @Override
    public int rewordDelete(int id) {
        int i = wkStepStageRewordExtMapper.rewordDelete(id);
        return i;
    }
}
