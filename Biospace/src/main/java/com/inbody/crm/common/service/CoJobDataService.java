/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.service;

import java.util.List;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.CoJobData;
import com.inbody.crm.common.persistence.CoJobDataDao;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.modules.sys.entity.User;

/**
 * 履历Service
 * 
 * @author yangj
 * @version 2017-10-24
 */
@Service
@Transactional(readOnly = true)
public class CoJobDataService extends BaseService {

    @Autowired
    private CoJobDataDao coJobDataDao;

    /**
     * 履历一览取得
     * 
     * @param page
     *            分页信息
     * @param searchParam
     *            分页查询信息
     * @return 履历一览信息
     */
    public Page<CoJobData> findPurPage(Page<CoJobData> page, CoJobData searchParam) {

        searchParam.setPage(page);
        List<CoJobData> pageList = coJobDataDao.findPageList(searchParam);
        page.setList(pageList);
        return page;
    }
    
    /**
     * 保存履历数据
     * 
     * @param coJobData
     *            需要保存的履历信息
     * @param method
     *            执行方法：自手、手动
     * @return 保存后履历信息
     */
    @Transactional(readOnly = false)
    public CoJobData saveJobData(CoJobData coJobData, String method) {

        // 如果为手动
        if (StringUtils.equals(method, CommonConstants.GEN_METHOD_2)) {
            coJobData.preInsert();
        } else {
            // 否则，自动
            User user = new User();
            user.setId("Admin");
            coJobData.setCreateBy(user);
        }

        coJobData.preInsert();
        coJobDataDao.insert(coJobData);

        return coJobData;
    }

}