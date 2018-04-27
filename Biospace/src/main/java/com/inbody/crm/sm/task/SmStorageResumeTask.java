/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sm.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.sm.service.SmStorageResumeService;

/**
 * 库存履历自动运行任务
 * 
 * @author yangj
 * @version 2017-10-24
 */
public class SmStorageResumeTask {

    private static final Logger logger = LoggerFactory
            .getLogger(SmStorageResumeTask.class);

    @Autowired
    private SmStorageResumeService storageResumeService;

    /**
     * 库存履历excel生成自动执行
     */
    public void execute() {
        logger.info("库存履历导出任务开始。");

        try {
            String filename = storageResumeService
                    .genStorageReport(CommonConstants.GEN_METHOD_1);
            logger.info("库存履历文件：" + filename);
        } catch (Exception e) {
            logger.error("库存履历导出任务运行异常", e);
            e.printStackTrace();
        }

        logger.info("库存履历导出任务结束。");
    }
}