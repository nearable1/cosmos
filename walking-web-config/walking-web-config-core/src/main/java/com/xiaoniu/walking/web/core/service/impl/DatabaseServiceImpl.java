package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.bo.DatabaseBO;
import com.xiaoniu.walking.web.core.mapper.ext.DatabaseExtMapper;
import com.xiaoniu.walking.web.core.model.auto.Database;
import com.xiaoniu.walking.web.core.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 数据库管理
 *
 * @author: chenguohua
 * @date: 2019/04/20 11:36
 * @description:
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {


    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageResult<Database> queryDatabaseByPage(DatabaseBO databaseBO) {
        return pageRepository.selectPaging(DatabaseExtMapper.class, "queryDatabaseByPage", databaseBO);
    }

}
