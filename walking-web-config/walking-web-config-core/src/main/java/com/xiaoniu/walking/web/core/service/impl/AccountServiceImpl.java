package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.bo.WkUserBO;
import com.xiaoniu.walking.web.core.mapper.ext.WkUserExtMapper;
import com.xiaoniu.walking.web.core.service.AccountService;
import com.xiaoniu.walking.web.core.vo.WkUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 白名单管理
 *
 * @author liuyinkai
 * @date 20190919
 */
@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    private WkUserExtMapper wkUserExtMapper;

    @Autowired
    private PageRepository pageRepository;


    @Override
    public PageResult<WkUserVO> getAccountList(WkUserBO wkUserBO) {
        PageResult<WkUserVO> selectByPage = pageRepository.selectPaging(WkUserExtMapper.class, "selectByPage", wkUserBO);
        return selectByPage;
    }

}
