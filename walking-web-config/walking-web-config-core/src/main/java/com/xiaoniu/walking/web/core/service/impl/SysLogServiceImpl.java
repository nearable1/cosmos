package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.bo.SysLogBO;
import com.xiaoniu.walking.web.core.mapper.auto.SysLogMapper;
import com.xiaoniu.walking.web.core.mapper.ext.SysLogExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysLog;
import com.xiaoniu.walking.web.core.model.ext.SysLogExt;
import com.xiaoniu.walking.web.core.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 系统 用户-角色-菜单-权限
 *
 * @author: xiangxianjin
 * @date: 2019/3/27 18:25
 * @description:
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private PageRepository pageRepository;

    /**
     * 保存日志信息
     *
     * @param sysLog
     * @return
     */
    @Override
    public int saveSysLog(SysLog sysLog) {
        sysLog.setCreateTime(new Date());
        return sysLogMapper.insert(sysLog);
    }

    /**
     * 查询系统操作日志信息
     *
     * @param sysLogBO
     * @return
     */
    @Override
    public PageResult<SysLogExt> querySysLogByPage(SysLogBO sysLogBO) {
        return pageRepository.selectPaging(SysLogExtMapper.class, "querySysLogByPage", sysLogBO);
    }
}
