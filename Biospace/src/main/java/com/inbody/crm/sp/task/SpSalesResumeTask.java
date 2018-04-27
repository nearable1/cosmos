/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.sp.service.SpSalesResumeService;

/**
 * 销售计划履历自动运行任务
 * 
 * @author yangj
 * @version 2017-10-24
 */
public class SpSalesResumeTask {

    private static final Logger logger = LoggerFactory.getLogger(SpSalesResumeTask.class);

    @Autowired
    private SpSalesResumeService salesResumeService;

    /**
     * 销售履历excel自动运行任务执行方法
     */
    public void execute() {
        logger.info("销售计划履历导出任务开始。");
        logger.info("执行月:" + DateUtils.getDate("yyyy-MM"));

        try {
            String filename = salesResumeService.genSalesReport(null);
            logger.info("销售计划履历文件：" + filename);
        } catch (Exception e) {
            logger.error("销售计划履历导出任务运行异常", e);
            e.printStackTrace();
        }

        logger.info("销售计划履历导出任务结束。");
    }
}