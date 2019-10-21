package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.walking.account.api.bo.BackWithdrawBO;
import com.xiaoniu.walking.account.api.business.WalkingBackFeignBusiness;
import com.xiaoniu.walking.web.api.bo.FileUploadBO;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.MarketCostBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.enums.WithdrawStateEnum;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.service.FileUploadService;
import com.xiaoniu.walking.web.core.service.MarketCostService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.POIXMLException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 文件上传Controller
 *
 * @author xiangxianjin
 * @date 2019/05/16 10:26
 */
@RestController
@RequestMapping("/walkingwebapi/file")
@Log4j2
public class UploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private MarketCostService marketCostService;

    @Autowired
    private WalkingBackFeignBusiness walkingBackFeignBusiness;

    /**
     * 轻音号正则校验
     * app_id正则
     */
    private static Pattern APPID_PATTERN = Pattern.compile("^\\d{4,10}$");

    @PostMapping("/upload")
    @ActionLogger(moduleName = "上传文件", actionType = ActionLoggerCons.UPLOAD)
    public ResultBean queryList(FileUploadBO fileUploadBO) {
        return fileUploadService.upload(fileUploadBO);
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    /**
     * 导入消息记录
     * @throws IOException
     */
    @PostMapping("/upload-excel")
    @ActionLogger(moduleName = "上传文件", actionType = ActionLoggerCons.UPLOAD)
    public Map<String, Object> messageRecord(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> result = new HashMap<>();
        XSSFRow row = null;
        XSSFWorkbook wb=null;
        try {
            wb = new XSSFWorkbook(file.getInputStream());
        } catch (POIXMLException e1) {
            LOGGER.error("文件导入发生异常，文件名：{}，错误原因：{}", file.getName(), e1.getMessage());
            result.put("message","文件格式有误，请导入.xlsx类型文件");
            result.put("code","2000");
            return result;
        }
        try {
            XSSFSheet xs = wb.getSheetAt(0);
            if (xs == null) {
                result.put("code", 2005);
                result.put("message", "文件类容格式有误");
                return result;
            }
            Integer rowNumTotal =  xs.getPhysicalNumberOfRows();
            LOGGER.info("开始导入文件：{}，导入数量：{}", file.getName(), rowNumTotal);
            String errorMessage = "";
            Integer maxNum = 10000;
            if(rowNumTotal > maxNum){
                result.put("code", 2004);
                result.put("message", "导入数量超出限制，支持最大导入数据" + maxNum + "条");
                return result;
            }
            List<MarketCostBO> list = new ArrayList<>();
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            for (int rowNum = 1; rowNum < xs.getPhysicalNumberOfRows(); rowNum++) {
                row = xs.getRow(rowNum);
                MarketCostBO marketCostBO = new MarketCostBO();
                XSSFCell OneCell = row.getCell(0);
                String dateStr = "";
                if (row.getCell(0).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                    HSSFDateUtil.isCellDateFormatted(row.getCell(0));
                    dateStr = format.format(HSSFDateUtil.getJavaDate(OneCell.getNumericCellValue())).toString();
                }

//                marketCostBO.setCastDateStr(getValue(row.getCell(0)));
                marketCostBO.setCastDate(format.parse(dateStr));
                marketCostBO.setChannel(getValue(row.getCell(1)));
                marketCostBO.setPrincipal(getValue(row.getCell(2)));
                marketCostBO.setAccountConsume(new BigDecimal(getValue(row.getCell(3))));
                marketCostBO.setAdClickNum(Integer.valueOf(getValue(row.getCell(4))));
                marketCostBO.setInstallNum(Integer.valueOf(getValue(row.getCell(5))));
                marketCostBO.setAntiPoint(String.valueOf(getValue(row.getCell(6))));
                list.add(marketCostBO);
            }
            int successNum = 0;
            if (CollectionUtils.isNotEmpty(list)) {
                int flag = 0;
                for (MarketCostBO marketCostBO : list) {
                    flag = marketCostService.insertInfo(marketCostBO);
                    if (flag > 0) {
                        successNum ++;
                    }
                }
            }
            result.put("code", 200);
            result.put("message", "成功导入"+successNum+"条");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("文件导入发生异常，文件名：{}，错误原因：{}", file.getName(), e.getMessage());
            result.put("code", 2005);
            result.put("message", "文件导入发生异常，错误原因："+e.getMessage());
        }
        return result;
    }

    private static String getValue(XSSFCell xssfCell) {
        if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            DecimalFormat df = new DecimalFormat("0");
            return String.valueOf(df.format(xssfCell.getNumericCellValue()));
        } else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
            return String.valueOf(xssfCell.getStringCellValue());
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }

    private SysUser getSysUser(){
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return Optional.ofNullable(sysUser).orElse(new SysUser());
    }

    /**
     * 导入支付宝批量提现记录
     * @throws IOException
     */
    @PostMapping("/upload-excel-alipay")
    @ActionLogger(moduleName = "上传文件", actionType = ActionLoggerCons.UPLOAD)
    public Map<String, Object> uploadExcelAlipay(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> result = new HashMap<>();
        XSSFRow row = null;
        XSSFWorkbook wb=null;
        try {
            wb = new XSSFWorkbook(file.getInputStream());
        } catch (POIXMLException e1) {
            LOGGER.error("文件导入发生异常，文件名：{}，错误原因：{}", file.getName(), e1.getMessage());
            result.put("message","文件格式有误，请导入.xlsx类型文件");
            result.put("code","2000");
            return result;
        }
        try {
            XSSFSheet xs = wb.getSheetAt(0);
            if (xs == null) {
                result.put("code", 2005);
                result.put("message", "文件类容格式有误");
                return result;
            }
            Integer rowNumTotal =  xs.getPhysicalNumberOfRows();
            LOGGER.info("开始导入文件：{}，导入数量：{}", file.getName(), rowNumTotal);
            String errorMessage = "";
            Integer maxNum = 10000;
            if(rowNumTotal > maxNum){
                result.put("code", 2004);
                result.put("message", "导入数量超出限制，支持最大导入数据" + maxNum + "条");
                return result;
            }
            List<BackWithdrawBO> list = new ArrayList<>();
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            String dateStr = "";

            for (int rowNum = 1; rowNum < xs.getPhysicalNumberOfRows(); rowNum++) {
                row = xs.getRow(rowNum);
                BackWithdrawBO backWithdrawBO = new BackWithdrawBO();
                backWithdrawBO.setWithdrawRecordId(getValue(row.getCell(0)));
                backWithdrawBO.setPayDate(getValue(row.getCell(1)));
                backWithdrawBO.setOrderId(getValue(row.getCell(2)));
                backWithdrawBO.setSuccess(true);
                if (WithdrawStateEnum.WITHDRAW_STATE_FAIL.matches(getValue(row.getCell(3)))){
                    backWithdrawBO.setSuccess(false);
                }
                if ((row.getCell(4))!=null){
                    backWithdrawBO.setSubMsg(getValue(row.getCell(4)));
                }
                backWithdrawBO.setAuditMan(getSysUser().getUserId());
                list.add(backWithdrawBO);
            }

            int successNum = 0;
            int failNum = 0;
            if (CollectionUtils.isNotEmpty(list)) {
                for (BackWithdrawBO backWithdrawBO : list) {
                    ResultBean resultBean = walkingBackFeignBusiness.agreeWithdraw(backWithdrawBO);
                    if (WithdrawStateEnum.BACK_RESULT_SUCCESS.matches(resultBean.getData().toString())) {
                        successNum ++;
                        continue;
                    }
                    failNum++;
                }
            }
            result.put("code", 200);
            result.put("message", "成功导入"+successNum+"条，失败："+failNum+"条数");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("文件导入发生异常，文件名：{}，错误原因：{}", file.getName(), e.getMessage());
            result.put("code", 2005);
            result.put("message", "文件导入发生异常，错误原因："+e.getMessage());
        }
        return result;
    }
}
