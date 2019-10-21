package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.walking.account.api.bo.BackWithdrawBO;
import com.xiaoniu.walking.account.api.bo.WithdrawRecordPageBO;
import com.xiaoniu.walking.account.api.business.WalkingBackFeignBusiness;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Optional;

/**
 * 提现记录
 *
 * @author lihoujing
 * @date 2019/9/20 16:45
 */
@RestController
@RequestMapping("/walkingwebapi/withdraw")
@Log4j2
public class WithdrawRecordController {

    @Autowired
    private WalkingBackFeignBusiness walkingBackFeignBusiness;


    @GetMapping("/list")
    @ActionLogger(moduleName = "提现列表", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@RequestParam(required = false) Integer pageIndex,
                                @RequestParam(required = false) Integer pageSize,
                                WithdrawRecordPageBO withdrawRecordPageBO) {
        if (pageIndex != null) {
            withdrawRecordPageBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            withdrawRecordPageBO.setPageSize(pageSize);
        }

        return walkingBackFeignBusiness.getBackWithdrawRecordList(withdrawRecordPageBO);
    }



    @GetMapping("/pass")
    @ActionLogger(moduleName = "审核通过", actionType = ActionLoggerCons.MODIFY)
    public ResultBean checkPassWithdrawRecord(BackWithdrawBO backWithdrawBO){
        backWithdrawBO.setAuditMan(getSysUser().getUserId());
        return walkingBackFeignBusiness.agreeWithdraw(backWithdrawBO);
    }


    @GetMapping("/reject")
    @ActionLogger(moduleName = "审核驳回", actionType = ActionLoggerCons.MODIFY)
    public ResultBean checkRejectWithdrawRecord(BackWithdrawBO backWithdrawBO){
        backWithdrawBO.setAuditMan(getSysUser().getUserId());
        return walkingBackFeignBusiness.disagreeWithdraw(backWithdrawBO);
    }



    private SysUser getSysUser(){
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return Optional.ofNullable(sysUser).orElse(new SysUser());
    }


    /**
     * 提现记录模板下载
     * @param response
     * @param request
     */
    @GetMapping("/get-template")
    @ActionLogger(moduleName = "提现记录", actionType = ActionLoggerCons.DOWNLOAD)
    public void getTemplate(HttpServletResponse response, HttpServletRequest request) {
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/OCTET-STREAM");
            String filedisplay = "提现记录模板下载.xlsx";
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(filedisplay.getBytes(), "iso-8859-1"));
            XSSFSheet sheet = wb.createSheet("提现记录模板");
            //设置生成表格大小
            for (int i = 0; i < 6; i++) {
                Integer  width = 8000;
                if(i > 2){
                    width = 7000;
                }
                sheet.setColumnWidth(i, width);
            }
            sheet.setDefaultRowHeightInPoints(20);
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle = wb.createCellStyle();
            //颜色
//            cellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            //cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

            XSSFFont font = wb.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 12);
            cellStyle.setFont(font);
            XSSFRow row = sheet.createRow(0);
            XSSFCell cell1 = row.createCell(0);
            cell1.setCellValue("提现记录编号");
            cell1.setCellStyle(cellStyle);

            XSSFCell cell2 = row.createCell(1);
            cell2.setCellValue("提现时间（例如：2019-09-30 10:12:36）");
            cell2.setCellStyle(cellStyle);

            XSSFCell cell3 = row.createCell(2);
            cell3.setCellValue("支付宝流水号");
            cell3.setCellStyle(cellStyle);

            XSSFCell cell4 = row.createCell(3);
            cell4.setCellValue("状态（成功/失败）");
            cell4.setCellStyle(cellStyle);

            XSSFCell cell5 = row.createCell(4);
            cell5.setCellValue("失败原因");
            cell5.setCellStyle(cellStyle);



            try {
                OutputStream out = response.getOutputStream();
                wb.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        } finally {
        }
    }

}
