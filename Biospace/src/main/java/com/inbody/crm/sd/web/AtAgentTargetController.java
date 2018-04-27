/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.Encodes;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.DictUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.sd.domain.AtAgentTargetModal;
import com.inbody.crm.sd.domain.AtAgentTargetSearch;
import com.inbody.crm.sd.entity.AtAgentTarget;
import com.inbody.crm.sd.service.AtAgentTargetService;

/**
 * 代理商目标Controller
 * @author zhanglulu
 * @version 2017-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sd/atAgentTarget")
public class AtAgentTargetController extends BaseController {

	@Autowired
	private AtAgentTargetService atAgentTargetService;

	/**
	 * 代理商目标与业绩对比查询一览取得
	 */
	@RequiresPermissions("sd:atAgentTarget:view")
	@RequestMapping(value = "compare/list")
	public String compareList(AtAgentTargetSearch atAgentTargetSearch,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user, "sd:atAgentTarget:view"));
		atAgentTargetSearch.setSqlMap(uerMap);
		
		if (StringUtils.isEmptyNull(atAgentTargetSearch.getIfNew())) {

			atAgentTargetSearch.setIfNew(CommonConstants.YES);
		}

		// list数据取得
		Page<AtAgentTargetSearch> page = atAgentTargetService.findAtAgentTargetComparePage(
				new Page<AtAgentTargetSearch>(request, response),
				atAgentTargetSearch);

		model.addAttribute("page", page);
		model.addAttribute("atAgentTargetSearch", atAgentTargetSearch);
		
		return "sd/at/at002";
	}

	/**
	 * 代理商目标与业绩对比查询一览取得
	 */
	@RequiresPermissions("sd:atAgentTarget:view")
	@RequestMapping(value = "list")
	public String list(AtAgentTargetSearch atAgentTargetSearch,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user, "sd:atAgentTarget:view"));
		atAgentTargetSearch.setSqlMap(uerMap);
		
		if (StringUtils.isEmptyNull(atAgentTargetSearch.getIfNew())) {

			atAgentTargetSearch.setIfNew(CommonConstants.YES);
		}

		// list数据取得
		Page<AtAgentTargetSearch> page = atAgentTargetService.findAtAgentTargetPage(
				new Page<AtAgentTargetSearch>(request, response),
				atAgentTargetSearch);

		model.addAttribute("page", page);
		model.addAttribute("atAgentTargetSearch", atAgentTargetSearch);
		
		return "sd/at/at001";
	}

	@RequiresPermissions("sd:atAgentTarget:edit")
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getMaterialInfos(String agreementId) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		AtAgentTargetModal AtAgentTargetModal = atAgentTargetService.getAtAgentTargetDtl(agreementId);

		jsonMap.put("atAgentTarget", AtAgentTargetModal);
		return jsonMap;
	}
	
	/**
	 * 目标制定保存
	 * @param atAgentTarget
	 * @return
	 */
	@RequestMapping(value = "saveAtAgentTarget", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveAtAgentTarget(AtAgentTargetModal atAgentTarget,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			String message = atAgentTargetService.atAgentTargetInfoValidator(atAgentTarget);
			
			if (StringUtils.isEmptyNull(message)) {

				atAgentTargetService.saveAtAgentTarget(atAgentTarget);
			} else {
				jsonMap.put("success", false);
				jsonMap.put("message", message);
				return jsonMap;
			}
		} catch (ServiceException e) {
			jsonMap.put("success", false);
			jsonMap.put("message", "目标制定失败！");
			return jsonMap;
		}

		jsonMap.put("success", true);
		return jsonMap;
	}
	
	/**
	 * 代理商目标业绩导出
	 * 
	 * @param atAgentTargetSearch
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("sd:atAgentTarget:view")
    @RequestMapping(value = "compare/export")
	public String compareExport(AtAgentTargetSearch atAgentTargetSearch,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", BaseService.dataScopeFilter(user, "sd:atAgentTarget:view"));
		atAgentTargetSearch.setSqlMap(uerMap);

		try {
			String fileName = "代理商目标业绩_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<AtAgentTargetSearch> atAgentTargetList = atAgentTargetService.getAtAgentTargetList(atAgentTargetSearch);

			this.writeStringToExcel(response, fileName, atAgentTargetList);
			
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "代理商目标业绩一览！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sd/atAgentTarget/compare/list?repage";
	}
	
	/**
	 * 往excel文件中写入字符串(合并单元格)
	 * 
	 * @param fileName
	 * @param dtlMap
	 * 
	 * @return excel工作簿
	 */
	private void writeStringToExcel(HttpServletResponse response, String fileName, List<AtAgentTargetSearch> atAgentTargetList) {
		
		// 工作薄对象
		SXSSFWorkbook wb = new SXSSFWorkbook(500);
		// 工作表对象
		Sheet sheet = wb.createSheet("Export");
		// 样式列表
		Map<String, CellStyle> styles = createStyles(wb);
		// 当前行号
		int rownum = 0;
		
		// 头信息标题
		String[] headers = {"代理商", "协议开始期间", "目标类型", "阶段", "型号（目标/业绩）", "台数（目标/业绩）", "金额目标", "金额业绩", "完成率"};
		List<String> headerList = Lists.newArrayList(headers);
		
		createHeader(sheet, headerList, rownum++, styles);
		
		for (AtAgentTargetSearch atAgentTargetSearch : atAgentTargetList) {
			
			rownum = createDtlData(atAgentTargetSearch, sheet, wb, rownum++, styles);
		}
		
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
        try {
			wb.write(response.getOutputStream());
			wb.dispose();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * 创建表格样式
	 * @param wb 工作薄对象
	 * @return 样式列表
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put("data3", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		return styles;
	}
	
	/**
	 * 创建标题行
	 * @param sheet 工作表对象
	 * @param headerList 标题列表
	 * @param rownum 行号
	 * @param styles 样式列表
	 */
	private void createHeader(Sheet sheet, List<String> headerList, int rownum, Map<String, CellStyle> styles) {

		Row headerRow = sheet.createRow(rownum);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			cell.setCellValue(headerList.get(i));
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {  
			int colWidth = sheet.getColumnWidth(i)*2;
	        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
		}
	}
	
	/**
	 * 创建数据行
	 * @param sheet 工作表对象
	 * @param headerList 标题列表
	 * @param rownum 行号
	 * @param styles 样式列表
	 */
	private int createDtlData(AtAgentTargetSearch atAgentTargetSearch ,Sheet sheet, Workbook wb, int rownum, Map<String, CellStyle> styles) {
		
		int index = rownum;
		String customerName = atAgentTargetSearch.getCustomerName();
		String validityDateFrom = DateUtils.formatDate(atAgentTargetSearch.getValidityDateFrom(), "yyyy-MM");
		String targetType = atAgentTargetSearch.getTargetType();

		if (atAgentTargetSearch.getPeriodList() == null || atAgentTargetSearch.getPeriodList().isEmpty()) {

			Row row = sheet.createRow(index);

			addCell(row, 0, customerName, 3, styles, wb);
			addCell(row, 1, validityDateFrom, 3, styles, wb);
			addCell(row, 2, DictUtils.getDictLabel(targetType, "DM0018", ""), 3, styles, wb);
			addCell(row, 3, null, 3, styles, wb);
			addCell(row, 4, null, 3, styles, wb);
			addCell(row, 5, null, 3, styles, wb);
			addCell(row, 6, null, 3, styles, wb);
			addCell(row, 7, null, 3, styles, wb);
			addCell(row, 8, null, 3, styles, wb);
			
			index++;
		
		} else {

			int startRow = index;
			int lastRow = index;
			
			Map<String, BigDecimal> periodTotalAmount = atAgentTargetSearch.getPeriodTotalAmount();
			Map<String, BigDecimal> periodTrackTotalAmount = atAgentTargetSearch.getPeriodTrackTotalAmount();
			for (Map.Entry<String, List<AtAgentTarget>> entry : atAgentTargetSearch.getPeriodList().entrySet()) {

				int startPeriodRow = index;
				int lastPeriodRow = index;

				for (AtAgentTarget atAgentTarget : entry.getValue()) {

					Row row = sheet.createRow(index);

					addCell(row, 0, customerName, 3, styles, wb);
					addCell(row, 1, validityDateFrom, 3, styles, wb);
					addCell(row, 2, DictUtils.getDictLabel(targetType, "DM0018", ""), 3, styles, wb);
					addCell(row, 3, DictUtils.getDictLabel(entry.getKey(), "DM0046", ""), 3, styles, wb);
					
					addCell(row, 4, StringUtils.defaultString(atAgentTarget.getModel()) + "/" + StringUtils.defaultString(atAgentTarget.getTrackModel()), 3, styles, wb);
					addCell(row, 5, StringUtils.defaultString(atAgentTarget.getNum()) + "/" + StringUtils.defaultString(atAgentTarget.getTrackNum()), 3, styles, wb);

					addCell(row, 6, periodTotalAmount.get(entry.getKey()), 3, styles, wb);
					
					addCell(row, 7, atAgentTarget.getTrackAmount(), 3, styles, wb);
					
					if (StringUtils.equals(targetType, CommonConstants.TARGET_TYPE_2)) {
						addCell(row, 8, StringUtils.division(atAgentTarget.getTrackNum(), atAgentTarget.getNum()), 3, styles, wb);
					} else if (StringUtils.equals(targetType, CommonConstants.TARGET_TYPE_1)) {
						addCell(row, 8, StringUtils.division(periodTrackTotalAmount.get(entry.getKey()), periodTotalAmount.get(entry.getKey())), 3, styles, wb);
					}
					
					index++;
					lastPeriodRow++;
					lastRow++;
				}

				sheet.addMergedRegion(new CellRangeAddress(startPeriodRow,
						lastPeriodRow-1, 3, 3));
				sheet.addMergedRegion(new CellRangeAddress(startPeriodRow,
						lastPeriodRow-1, 6, 6));
				if (StringUtils.equals(targetType, CommonConstants.TARGET_TYPE_1)) {

					sheet.addMergedRegion(new CellRangeAddress(startPeriodRow,
							lastPeriodRow-1, 8, 8));
				}
				
			}

			sheet.addMergedRegion(new CellRangeAddress(startRow,
					lastRow-1, 0, 0));
			sheet.addMergedRegion(new CellRangeAddress(startRow,
					lastRow-1, 1, 1));
			sheet.addMergedRegion(new CellRangeAddress(startRow,
					lastRow-1, 2, 2));
		}

		return index;
	}
	
	private void addCell(Row row, int column, Object val, int align, Map<String, CellStyle> styles, Workbook wb) {
		Cell cell = row.createCell(column);
		String cellFormatString = "@";
		try {
			if(val == null){
				cell.setCellValue("");
			}else{
				if(val instanceof String) {
					cell.setCellValue((String) val);
				}else if(val instanceof Integer) {
					cell.setCellValue((Integer) val);
					cellFormatString = "0";
				}else if(val instanceof Long) {
					cell.setCellValue((Long) val);
					cellFormatString = "0";
				}else if(val instanceof Double) {
					cell.setCellValue((Double) val);
					cellFormatString = "0.00";
				}else if(val instanceof Float) {
					cell.setCellValue((Float) val);
					cellFormatString = "0.00";
				}else if(val instanceof BigDecimal) {
					cell.setCellValue(val.toString());
					cellFormatString = "0.00";
				}else if(val instanceof Date) {
					cell.setCellValue((Date) val);
					cellFormatString = "yyyy-MM-dd HH:mm";
				}else {
					cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
						"fieldtype."+val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val));
				}
			}

				CellStyle style = styles.get("data_column_"+column);
				if (style == null){
					style = wb.createCellStyle();
					style.cloneStyleFrom(styles.get("data"+(align>=1&&align<=3?align:"")));
			        style.setDataFormat(wb.createDataFormat().getFormat(cellFormatString));
					styles.put("data_column_" + column, style);
				}
				cell.setCellStyle(style);
		} catch (Exception ex) {
			cell.setCellValue(val.toString());
		}
	}
}