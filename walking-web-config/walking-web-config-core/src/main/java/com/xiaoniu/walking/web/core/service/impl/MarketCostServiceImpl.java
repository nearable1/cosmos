package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.bo.MarketCostBO;
import com.xiaoniu.walking.web.core.mapper.ext.AdManagementExtMapper;
import com.xiaoniu.walking.web.core.mapper.ext.MarketCostExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.model.ext.AdManagementExt;
import com.xiaoniu.walking.web.core.model.ext.MarketCostExt;
import com.xiaoniu.walking.web.core.service.MarketCostService;
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
public class MarketCostServiceImpl implements MarketCostService {


    @Autowired
    private MarketCostExtMapper marketCostExtMapper;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageResult<MarketCostExt> getAllByPage(MarketCostBO marketCostBO) {
        PageResult<MarketCostExt> selectByPage = pageRepository.selectPaging(MarketCostExtMapper.class, "getAllByPage", marketCostBO);
        return selectByPage;
    }

    @Override
    public MarketCostExt getInfoById(Integer id) {
        MarketCostExt infoById = marketCostExtMapper.getInfoById(id);
        return infoById;
    }

    @Override
    public int insertInfo(MarketCostBO marketCostBO) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser)subject.getPrincipal();
        marketCostBO.setCreateMan(user.getUserId());
        marketCostBO.setModifyMan(user.getUserId());
        int i = marketCostExtMapper.insertInfo(marketCostBO);
        return i;
    }

    @Override
    public int updateInfo(MarketCostBO marketCostBO) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        marketCostBO.setModifyTime(new Date());
        marketCostBO.setModifyMan(user.getUserId());
        int i = marketCostExtMapper.updateInfo(marketCostBO);
        return i;
    }

    @Override
    public int deleteInfo(Integer id) {
        int i = marketCostExtMapper.deleteInfo(id);
        return i;
    }

}
