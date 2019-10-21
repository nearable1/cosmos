package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.api.vo.SysDictVO;
import com.xiaoniu.walking.web.core.bo.AdManagementBO;
import com.xiaoniu.walking.web.core.mapper.ext.AdManagementExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.model.ext.AdManagementExt;
import com.xiaoniu.walking.web.core.service.AdManagementService;
import com.xiaoniu.walking.web.core.service.SystemDictionaryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * ad管理
 *
 * @author liuyinkai
 * @date 20190919
 */
@Service
public class AdManagementServiceImpl implements AdManagementService {


    @Autowired
    private AdManagementExtMapper adManagementExtMapper;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private SystemDictionaryService systemDictionaryService;

    public static final String AD_POSITION = "adPosition";

    @Override
    public PageResult<AdManagementExt> getAdManagement(AdManagementBO adManagementBO) {
        PageResult<AdManagementExt> selectByPage = pageRepository.selectPaging(AdManagementExtMapper.class, "getAdManagement", adManagementBO);
        List<SysDictVO> sysDictExts = systemDictionaryService.getSysDictNodesByType(AD_POSITION);
        for (AdManagementExt adManagementExt : selectByPage.getRows()) {
            for (SysDictVO sysDictExt : sysDictExts) {
                if (sysDictExt.getValue().equals(adManagementExt.getPosition())) {
                    adManagementExt.setPositionStr(sysDictExt.getLabel());
                }
            }
        }
        return selectByPage;
    }


    @Override
    public AdManagementExt getInfoById(Integer id) {
        AdManagementExt infoById = adManagementExtMapper.getInfoById(id);
        return infoById;
    }

    /**
     * insert
     *
     * @param adManagementBO
     * @return
     */
    @Override
    public int adInsert(AdManagementBO adManagementBO) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        adManagementBO.setCreateMan(user.getUserId());
        adManagementBO.setModifyMan(user.getUserId());
        return adManagementExtMapper.adInsert(adManagementBO);
    }

    /**
     * update
     *
     * @param adManagementBO
     * @return
     */
    @Override
    public int adUpdate(AdManagementBO adManagementBO) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        adManagementBO.setModifyTime(new Date());
        adManagementBO.setModifyMan(user.getUserId());
        return adManagementExtMapper.adUpdate(adManagementBO);
    }

    @Override
    public int checkPosition(String position) {
        int i = adManagementExtMapper.checkPosition(position);
        return i;
    }
}
