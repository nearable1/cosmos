package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.lending.commons.web.helper.HeaderHelper;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.api.vo.BottomIconVO;
import com.xiaoniu.walking.web.core.bo.BottomIconPageBO;
import com.xiaoniu.walking.web.core.bo.BottonIconBO;
import com.xiaoniu.walking.web.core.helper.CompareHelper;
import com.xiaoniu.walking.web.core.mapper.auto.BottomIconMapper;
import com.xiaoniu.walking.web.core.mapper.auto.BottomIconMarketMapper;
import com.xiaoniu.walking.web.core.mapper.ext.BottomIconExtMapper;
import com.xiaoniu.walking.web.core.mapper.ext.BottomIconMarketExtMapper;
import com.xiaoniu.walking.web.core.model.auto.BottomIcon;
import com.xiaoniu.walking.web.core.model.auto.BottomIconMarket;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.service.BottomIconService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lihoujing
 * @date 2019/6/24 20:27
 */
@Service
public class BottomIconServiceImpl implements BottomIconService {

    private static final String INDEX = "_";

    @Autowired
    private BottomIconExtMapper bottomIconExtMapper;
    @Autowired
    private BottomIconMapper bottomIconMapper;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private BottomIconMarketExtMapper bottomIconMarketExtMapper;
    @Autowired
    private BottomIconMarketMapper bottomIconMarketMapper;



    /**
     * 获取底部icon列表
     *
     * @return
     */
    @Override
    public List<BottomIconVO> getBottomIconList() {

        Integer appName = HeaderHelper.getAppName();
        String market = HeaderHelper.getMarket();
        String appVersion = HeaderHelper.getAppVersion();
        String subMarket = market.substring(0, market.contains(INDEX) ? market.indexOf(INDEX) : market.length());

        List<Integer> iconId = bottomIconMarketExtMapper.getBottomIconMarketList(subMarket);
        //查询数据库
        List<BottomIconVO> iconList = bottomIconExtMapper.getBottomIconList(appName, iconId);
        List<BottomIconVO> resultList = iconList.parallelStream()
                .filter(e -> CompareHelper.isVersionMatch(e.getRangeVersion(), appVersion, e.getAppVersion()))
                .collect(Collectors.toList());

        resultList.sort(Comparator.comparingInt(BottomIconVO::getPosition));
        return resultList;
    }


    /**
     * 后台查询底部icon列表
     *
     * @param bottomIconBO
     * @return
     */
    @Override
    public PageResult<BottomIconVO> queryList(BottomIconPageBO bottomIconBO) {
        return pageRepository.selectPaging(BottomIconExtMapper.class, "selectIconByPage", bottomIconBO);
    }


    /**
     * 保存底部icon
     * @param bottomIconBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveIcon(BottonIconBO bottomIconBO) {
        bottomIconBO.setCreateMan(getSysUser().getUserId());
        bottomIconBO.setCreateTime(new Date());
        //入bottom_icon表
        int count = bottomIconExtMapper.insertBottomIcon(bottomIconBO);
        //入bottom_icon_market表
        insertBottomMarket(bottomIconBO);
        return count;
    }



    /**
     * 更新底部icon
     * @param bottomIconBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateIcon(BottonIconBO bottomIconBO) {
        //删除，添加BottomIconMarket
        dealBottomIconMarket(bottomIconBO);
        //更新bottomIcon
        BottomIcon bottomIcon = new BottomIcon();
        bottomIcon.setModifyMan(getSysUser().getUserId());
        bottomIcon.setModifyTime(new Date());
        BeanUtils.copyProperties(bottomIconBO, bottomIcon);
        return bottomIconMapper.updateByPrimaryKey(bottomIcon);
    }


    /**
     * 处理要更新的market
     *
     * @param bottomIconBO
     */
    private void dealBottomIconMarket(BottonIconBO bottomIconBO) {
        Integer iconId = bottomIconBO.getId();
        List<String> paramList = Arrays.asList(bottomIconBO.getMarket());
        //构建market参数对象列表
        List<BottomIconMarket> paramBOList = new ArrayList<>();
        paramList.forEach(e -> {
            BottomIconMarket market = new BottomIconMarket();
            market.setIconId(iconId);
            market.setMarket(e);
            paramBOList.add(market);
        });
        //查询market DB列表
        List<BottomIconMarket> dbBOList = bottomIconMarketExtMapper.selectList(iconId);
        List<String> dbList = dbBOList.parallelStream().map(BottomIconMarket::getMarket).collect(Collectors.toList());

        //待删除的列表
        List<BottomIconMarket> deleteList = dbBOList.parallelStream()
                .filter(e -> !paramList.contains(e.getMarket()))
                .collect(Collectors.toList());
        //待添加的列表
        List<BottomIconMarket> insertList = paramBOList.parallelStream()
                .filter(e -> !dbList.contains(e.getMarket()))
                .collect(Collectors.toList());
        //db操作
        deleteList.forEach(e -> bottomIconMarketMapper.delete(e));
        insertList.forEach(e -> bottomIconMarketMapper.insertSelective(e));
    }


    /**
     * 根据对象删除
     * @param bottomIcon
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteIcon(BottonIconBO bottomIcon) {
        Arrays.asList(bottomIcon.getMarket()).forEach(e ->{
            BottomIconMarket bottomIconMarket = new BottomIconMarket();
            bottomIconMarket.setMarket(e);
            bottomIconMarket.setIconId(bottomIcon.getId());
            bottomIconMarketMapper.delete(bottomIconMarket);
        });
        return bottomIconMapper.deleteByPrimaryKey(bottomIcon.getId());
    }


    @Override
    public List<BottomIconMarket> getBottomIconMarket(Integer iconId) {
        BottomIconMarket bottomIconMarket = new BottomIconMarket();
        bottomIconMarket.setIconId(iconId);
        return bottomIconMarketMapper.select(bottomIconMarket);
    }


    /**
     * 新增关系表记录
     * bottom_icon_market
     * @param bottonIconBO
     */
    public void insertBottomMarket(BottonIconBO bottonIconBO){
        String[] market = bottonIconBO.getMarket();
        Arrays.asList(market).forEach(e ->{
            BottomIconMarket bottomIconMarket = new BottomIconMarket();
            bottomIconMarket.setIconId(bottonIconBO.getId());
            bottomIconMarket.setMarket(e);
            bottomIconMarketMapper.insertSelective(bottomIconMarket);
        });
    }



    /**
     * 从shiro中获取当前登录人
     * @return
     */
    private SysUser getSysUser(){
        Subject subject = SecurityUtils.getSubject();
        return (SysUser) subject.getPrincipal();
    }


}
