/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sp.service;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.CoJobDataService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.sp.dao.SpSalesResumeDao;
import com.inbody.crm.sp.domain.SpSalesResume;
import com.inbody.crm.sp.domain.SpSalesResumeExcel;

/**
 * 销售计划履历Service
 * 
 * @author yangj
 * @version 2017-10-24
 */
@Service
@Transactional(readOnly = true)
public class SpSalesResumeService {

    @Autowired
    private SpSalesResumeDao spSalesResumeDao;

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
     * 根据成交月条件生成销售履历excel
     * 
     * @param expTurnoverMonth
     *            成交月
     * 
     * @return 生成文件名
     */
    @Transactional(readOnly = false)
    public String genSalesReport(Date expTurnoverMonth) throws Exception {

        // 生成方式
        String genMethod = CommonConstants.GEN_METHOD_1;
        // 成交月为空，当前成交月
        if (expTurnoverMonth == null) {
            // 成交月为空，自动生成
            genMethod = CommonConstants.GEN_METHOD_1;
            expTurnoverMonth = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        } else {
            // 成交月不为空，自动生成
            genMethod = CommonConstants.GEN_METHOD_2;
        }

        // 文件名生成
        String fileName = DateUtils.getDate("yyyyMMdd")
                + commonService.getNextSequence(CommonConstants.TRANSACTION_SALES_REPORT,
                        DateUtils.getDate("yyMMdd"), 3)
                + "_销售计划.xlsx";

        // 文件信息保存
        CoAttachments attInfo = attachmentsService.saveAttFileInfo(
                DateUtils.getDate("yyyyMMdd"), fileName,
                CommonConstants.ATTACHMENT_TYPE_SD);

        // 履历信息保存
        CoJobData jobData = new CoJobData();
        // 成交月
        jobData.setExpTurnoverMonth(expTurnoverMonth);
        // 关联id(文件id)
        jobData.setAssId(attInfo.getId());
        // 文件名
        jobData.setFileName(fileName);
        // 生成方式，手动、自动
        jobData.setMethod(genMethod);
        // 履历类型 : 销售计划
        jobData.setType(CommonConstants.JOB_DATA_TYPE_1);

        // 执行保存
        jobData = coJobDataService.saveJobData(jobData, genMethod);

        // 生成数据取得
        List<SpSalesResume> exportList = spSalesResumeDao
                .getSalesResumeExcelData(expTurnoverMonth);

        SpSalesResumeExcel excel = null;
        List<SpSalesResumeExcel> excelList = Lists.newArrayList();
        // 导出数据格式化
        NumberFormat numFmt = new DecimalFormat("#,##0.00");
        for (SpSalesResume item : exportList) {
            excel = new SpSalesResumeExcel();
            // 组
            excel.setOrganize(item.getOrganize());
            // 销售员
            excel.setSaler(item.getSaler());
            // 代理商/经销商
            excel.setAgent(item.getAgent());
            // 最终客户
            excel.setEndCustomer(item.getEndCustomer());
            // 型号
            excel.setModel(item.getModel());
            // 数量
            excel.setNum(item.getNum());
            // 单价
            if (item.getUnitPrice() == null) {
                excel.setUnitPrice("");
            } else {
                excel.setUnitPrice(numFmt.format(item.getUnitPrice()));
            }
            // 总额
            if (item.getTotalAmount() == null) {
                excel.setTotalAmount("");
            } else {
                excel.setTotalAmount(numFmt.format(item.getTotalAmount()));
            }
            // 预计成交率
            if (item.getExpTurnover() == null) {
                excel.setExpTurnover("");
            } else {
                excel.setExpTurnover(item.getExpTurnover() + "%");
            }
            // 合同状态
            if (StringUtils.equals(item.getIfContractGeneration(), CommonConstants.YES)) {
                if (StringUtils.equals(item.getOrderStatus(),
                        CommonConstants.WORKFLOW_STATUS_50)) {
                    excel.setOrderStatus("已生成已审批");
                } else {
                    excel.setOrderStatus("已生成未审批");
                }
            } else {
                excel.setOrderStatus("未生成");
            }
            // 开票状态
            excel.setInvoiceStatus(item.getInvoiceStatus());
            // 商机备注
            excel.setNewRemarks(item.getNewRemarks());
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
        new ExportExcel(null, SpSalesResumeExcel.class).setDataList(excelList)
                .writeFile(fullFileName).dispose();

        return fileName;
    }
}