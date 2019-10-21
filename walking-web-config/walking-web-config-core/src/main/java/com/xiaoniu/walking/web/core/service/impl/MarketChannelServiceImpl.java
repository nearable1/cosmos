package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.bo.MarketChannelBO;
import com.xiaoniu.walking.web.core.mapper.ext.AdManagementExtMapper;
import com.xiaoniu.walking.web.core.mapper.ext.MarketChannelExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.model.ext.AdManagementExt;
import com.xiaoniu.walking.web.core.model.ext.MarketChannelExt;
import com.xiaoniu.walking.web.core.service.MarketChannelService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 白名单管理
 *
 * @author liuyinkai
 * @date 20190919
 */
@Service
public class MarketChannelServiceImpl implements MarketChannelService {


    @Autowired
    private MarketChannelExtMapper marketChannelExtMapper;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageResult<MarketChannelExt> getAllByPage(MarketChannelBO marketChannelBO) {
        PageResult<MarketChannelExt> selectByPage = pageRepository.selectPaging(MarketChannelExtMapper.class, "getAllByPage", marketChannelBO);
        return selectByPage;
    }

    @Override
    public MarketChannelExt getInfoById(Integer id) {
        MarketChannelExt infoById = marketChannelExtMapper.getInfoById(id);
        return infoById;
    }

    @Override
    public int insertInfo(MarketChannelBO marketChannelBO) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser)subject.getPrincipal();
        marketChannelBO.setCreateMan(user.getUserId());
        marketChannelBO.setModifyMan(user.getUserId());
        int i = marketChannelExtMapper.insertInfo(marketChannelBO);
        return i;
    }

    @Override
    public int updateInfo(MarketChannelBO marketChannelBO) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        marketChannelBO.setModifyTime(new Date());
        marketChannelBO.setModifyMan(user.getUserId());
        int i = marketChannelExtMapper.updateInfo(marketChannelBO);
        return i;
    }

    @Override
    public int deleteInfo(Integer id) {
        int i = marketChannelExtMapper.deleteInfo(id);
        return i;
    }

}
