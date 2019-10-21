package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.MarketCostBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.model.ext.MarketCostExt;
import com.xiaoniu.walking.web.core.service.MarketCostService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;


/**
 * 市场成本Controller
 *
 * @author liuyinkai
 * @date 2019/9/20
 */
@RestController
@RequestMapping("/walkingwebapi/market-cost")
@Log4j2
public class MarketCostController {

    @Autowired
    private MarketCostService marketCostService;

    /**
     * 查询列表信息
     *
     * @param marketCostBO
     * @return
     */
    @PostMapping("/get-market-cost-list")
    @ActionLogger(moduleName = "市场成本", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(@Valid @RequestBody MarketCostBO marketCostBO) {
        PageResult<MarketCostExt> marketCostExtPageResult = marketCostService.getAllByPage(marketCostBO);
        return ResultBean.format(marketCostExtPageResult);
    }


    /**
     * 新增
     *
     * @param marketCostBO
     * @return
     */
    @PostMapping("/insert-market-cost")
    @ActionLogger(moduleName = " 市场成本", actionType = ActionLoggerCons.ADD)
    @Transactional
    public int adInsert(@RequestBody MarketCostBO marketCostBO) {
        int i = marketCostService.insertInfo(marketCostBO);
        return i;
    }

    /**
     * 编辑
     *
     * @param marketCostBO
     * @return
     */
    @PostMapping("/update-market-cost")
    @ActionLogger(moduleName = " 市场成本", actionType = ActionLoggerCons.MODIFY)
    public int adUpdate(@RequestBody MarketCostBO marketCostBO) {
        int i = marketCostService.updateInfo(marketCostBO);
        return i;
    }

    /**
     * 删除
     *
     * @param marketCostBO
     * @return
     */
    @PostMapping("/delete-market-cost")
    @ActionLogger(moduleName = "市场成本", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteReword(@RequestBody MarketCostBO marketCostBO) {

        int i = marketCostService.deleteInfo(marketCostBO.getId());
        return ResultBean.format(i);
    }

    /**
     * 市场成本模板下载
     * @param response
     * @param request
     */
    @GetMapping("/get-template")
    @ActionLogger(moduleName = "市场成本", actionType = ActionLoggerCons.DOWNLOAD)
    public void getTemplate(HttpServletResponse response, HttpServletRequest request) {
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/OCTET-STREAM");
            String filedisplay = "市场成本模板下载.xlsx";
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(filedisplay.getBytes(), "iso-8859-1"));
            XSSFSheet sheet = wb.createSheet("市场成本模板");
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
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
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
            cell1.setCellValue("日期（格式如：2018/9/13）");
            cell1.setCellStyle(cellStyle);

            XSSFCell cell2 = row.createCell(1);
            cell2.setCellValue("渠道号");
            cell2.setCellStyle(cellStyle);

            XSSFCell cell3 = row.createCell(2);
            cell3.setCellValue("负责人");
            cell3.setCellStyle(cellStyle);

            XSSFCell cell4 = row.createCell(3);
            cell4.setCellValue("账面消耗）");
            cell4.setCellStyle(cellStyle);

            XSSFCell cell5 = row.createCell(4);
            cell5.setCellValue("广告点击数");
            cell5.setCellStyle(cellStyle);

            XSSFCell cell6 = row.createCell(5);
            cell6.setCellValue("安装数");
            cell6.setCellStyle(cellStyle);

            XSSFCell cell7 = row.createCell(6);
            cell7.setCellValue("返点");
            cell7.setCellStyle(cellStyle);




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
