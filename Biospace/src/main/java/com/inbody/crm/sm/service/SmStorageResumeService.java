/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sm.service;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.entity.CoJobData;
import com.inbody.crm.common.mapper.JsonMapper;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.CoJobDataService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.sm.dao.SmStorageSearchDao;
import com.inbody.crm.sm.domain.SmStorageResume;
import com.inbody.crm.sm.domain.SmStorageResumeExcel;

/**
 * 销售计划履历Service
 * 
 * @author yangj
 * @version 2017-10-24
 */
@Service
@Transactional(readOnly = true)
public class SmStorageResumeService {
    
    @Autowired
    private SmStorageSearchDao smStorageSearchDao;

    @Autowired
    private CoJobDataService coJobDataService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private AttachmentsService attachmentsService;

    /**
     * 销售计划履历画面初期数据取得
     */
    public Page<CoJobData> getResumeList(Page<CoJobData> page, CoJobData searchParam) {
        return coJobDataService.findPurPage(page, searchParam);
    }

    /**
     * 生成库存履历excel
     * 
     * @param mothod
     *            生成区分：手动\自动
     * 
     * @return 生成文件名
     */
    @Transactional(readOnly = false)
    public String genStorageReport(String mothod) throws Exception {

        // 文件名生成
        String fileName = DateUtils.getDate("yyyyMMdd")
                + commonService.getNextSequence(CommonConstants.TRANSACTION_STORAGE_REPORT,
                        DateUtils.getDate("yyMMdd"), 3)
                + "_库存履历.xlsx";

        // 文件信息保存
        CoAttachments attInfo = attachmentsService.saveAttFileInfo(
                DateUtils.getDate("yyyyMMdd"), fileName,
                CommonConstants.ATTACHMENT_TYPE_MM);

        // 履历信息保存
        CoJobData jobData = new CoJobData();
        // 关联id(文件id)
        jobData.setAssId(attInfo.getId());
        // 文件名
        jobData.setFileName(fileName);
        // 生成方式，手动、自动
        jobData.setMethod(mothod);
        // 履历类型 : 销售计划
        jobData.setType(CommonConstants.JOB_DATA_TYPE_2);

        // 执行保存
        jobData = coJobDataService.saveJobData(jobData, mothod);

        // 上周四日期取得
        Date startDate = DateUtils.truncate(this.getLastThursdayDate(),
                Calendar.DAY_OF_MONTH);
        Date entDate = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

        // 库存履历查询参数设定
        SmStorageResume qParam = new SmStorageResume();
        // 查询期间开始日期
        qParam.setStartResumeDate(startDate);
        // 查询期间结日期
        qParam.setEndResumeDate(entDate);

        // 生成数据取得
        List<SmStorageResume> exportList = smStorageSearchDao.findStorageResume(qParam);

        // json解析实例
        JsonMapper jsonMapperIns = JsonMapper.getInstance();
        SmStorageResumeExcel excel = null;
        List<SmStorageResumeExcel> excelList = Lists.newArrayList();
        for (SmStorageResume item : exportList) {
            excel = new SmStorageResumeExcel();

            // 各仓库库存数据解析
            jsonMapperIns.update(
                    "{" + StringUtils.defaultString(item.getWarehouseNum()) + "}", excel);

            // 物料号
            excel.setMaterialNo(item.getMaterialNo());
            // 物料名称
            excel.setMaterialName(item.getMaterialName());
            // 本周进货
            excel.setWeekPurchasedNum(item.getWeekPurchasedNum());
            // 本周销售
            excel.setWeekSalesNum(item.getWeekSalesNum());
            // 在库数量
            excel.setInStockNum(item.getInStockNum());
            // 可用数量
            excel.setAvailableNum(item.getAvailableNum());
            // 报价单占用
            excel.setQuotaOccupyNum(item.getQuotaOccupyNum());
            // 已开票未发货
            excel.setInvoicedUndeliveredNum(item.getInvoicedUndeliveredNum());
            // 不在库数量
            excel.setOutStockNum(item.getOutStockNum());
            // 借出数量
            excel.setLendingNum(item.getLendingNum());
            // 已发货未开票
            excel.setUnInvoicedDeliveredNum(item.getUnInvoicedDeliveredNum());
            // 未到货数量
            excel.setNotArrivedNum(item.getNotArrivedNum());
            excelList.add(excel);
        }

        // 根据附件信息生成完整文件名（含路径）
        String fullFileName = attachmentsService.getFileFullPathByAtt(attInfo);

        // 生成文件路径
        File file = new File(StringUtils.replace(fullFileName, attInfo.getId(), ""));
        if (!file.exists()) {
            file.mkdirs();
        }

        // 履历文件生成
        new ExportExcel(null, SmStorageResumeExcel.class).setDataList(excelList)
                .writeFile(fullFileName).dispose();

        return fileName;
    }

    /**
     * 获取上个周四日期
     */
    public Date getLastThursdayDate() {
        // 当前时间取得
        Calendar calendar = Calendar.getInstance();
        // 当前时间是否等于周四
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        return calendar.getTime();
    }
}