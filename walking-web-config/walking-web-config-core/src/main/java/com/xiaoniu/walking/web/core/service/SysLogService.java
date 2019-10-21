package com.xiaoniu.walking.web.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.SysLogBO;
import com.xiaoniu.walking.web.core.model.auto.SysLog;
import com.xiaoniu.walking.web.core.model.ext.SysLogExt;

/**
 * 系统 用户-角色-菜单-权限
 *
 * @author: xiangxianjin
 * @date: 2019/3/27 18:25
 * @description:
 */
public interface SysLogService {

    /**
     * 保存日志信息
     *
     * @param sysLog
     * @return
     */
    int saveSysLog(SysLog sysLog);

    /**
     * 查询系统操作日志信息
     *
     * @param sysLogBO
     * @return
     */
    PageResult<SysLogExt> querySysLogByPage(SysLogBO sysLogBO);

}
