package com.xiaoniu.walking.web.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.DatabaseBO;
import com.xiaoniu.walking.web.core.model.auto.Database;

/**
 * 数据库管理
 *
 * @author: chenguohua
 * @date: 2019/04/20 11:36
 * @description:
 */
public interface DatabaseService {


    /**
     * 根据数据库名称和表名称进行分页查询
     *
     * @param databaseBO
     * @return
     */
    PageResult<Database> queryDatabaseByPage(DatabaseBO databaseBO);


}
