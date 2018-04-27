/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.ps.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.Encodes;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.utils.excel.ImportExcel;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.service.DictService;
import com.inbody.crm.modules.sys.utils.DictUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.pm.dao.SmMatInfoDao;
import com.inbody.crm.pm.entity.SmMatInfo;
import com.inbody.crm.ps.dao.PsAgreementDao;
import com.inbody.crm.ps.dao.PsApplyPriceDao;
import com.inbody.crm.ps.dao.PsApplyPriceDtlDao;
import com.inbody.crm.ps.entity.PsAgreement;
import com.inbody.crm.ps.entity.PsApplyPrice;
import com.inbody.crm.ps.entity.PsApplyPriceDtl;
import com.inbody.crm.ps.entity.PsApplyPriceDtlExport;

/**
 * 主子表生成Service
 * @author Nssol
 * @version 2017-07-20
 */
@Service
@Transactional(readOnly = true)
public class PsApplyPriceService extends CrudService<PsApplyPriceDao, PsApplyPrice> {

	@Autowired
	private PsApplyPriceDtlDao psApplyPriceDtlDao;
	
	@Autowired
	private SmMatInfoDao smMatInfoDao;
	
	@Autowired
	private DictService dictService;
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CommonService commonService;
	
	public PsApplyPrice get(String id) {
		PsApplyPrice psApplyPrice = super.get(id);
		psApplyPrice.setPsApplyPriceDtlList(psApplyPriceDtlDao.findList(new PsApplyPriceDtl(psApplyPrice)));
		psApplyPrice.setCreateBy(UserUtils.get(psApplyPrice.getCreateBy().getId()));
		return psApplyPrice;
	}
	
	public List<PsApplyPrice> findList(PsApplyPrice psApplyPrice) {
		return super.findList(psApplyPrice);
	}
	
	public Page<PsApplyPrice> findPage(Page<PsApplyPrice> page, PsApplyPrice psApplyPrice) {
		return super.findPage(page, psApplyPrice);
	}
	
	public Page<PsApplyPriceDtl> findPage(Page<PsApplyPriceDtl> page, PsApplyPriceDtl psApplyPriceDtl) {
		psApplyPriceDtl.setPage(page);
		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", dataScopeFilter(user));
		psApplyPriceDtl.setSqlMap(uerMap);
		page.setList(psApplyPriceDtlDao.searchList(psApplyPriceDtl));
		return page;
	}
	
	public void exportSearchResult(PsApplyPriceDtl psApplyPriceDtl, HttpServletResponse response) throws IOException{
		String fileName = "价格导出一览"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//		XSSFWorkbook wb = new XSSFWorkbook();
//		Sheet sheet = wb.createSheet();
//		
//		Row headerRow = sheet.createRow(0);
//		headerRow.createCell(0).setCellValue("物料号");
//		headerRow.createCell(1).setCellValue("物料名称");
//		headerRow.createCell(2).setCellValue("价格体系");
//		headerRow.createCell(3).setCellValue("协议方");
//		headerRow.createCell(4).setCellValue("地区");
//		headerRow.createCell(5).setCellValue("行业");
//		headerRow.createCell(6).setCellValue("单价");
//		headerRow.createCell(7).setCellValue("申请人");
//		headerRow.createCell(8).setCellValue("有效开始日");
//		headerRow.createCell(9).setCellValue("有效终止日");
		
		User user = UserUtils.getUser();
		Map<String, String> uerMap = new HashMap<String, String>();
		uerMap.put("dataScope", dataScopeFilter(user));
		psApplyPriceDtl.setSqlMap(uerMap);
		List<PsApplyPriceDtl> list = psApplyPriceDtlDao.searchList(psApplyPriceDtl);
//		for(int i=0; i < list.size(); i++){
//			Row dataRow = sheet.createRow(i+1);
//			PsApplyPriceDtl obj = list.get(i);
//			dataRow.createCell(0).setCellValue(obj.getMaterialNo());
//			dataRow.createCell(1).setCellValue(obj.getMaterialName());
//			dataRow.createCell(2).setCellValue(obj.getPriceSystemName());
//			dataRow.createCell(3).setCellValue(obj.getCutomerName());
//			dataRow.createCell(4).setCellValue(obj.getRegionName());
//			dataRow.createCell(5).setCellValue(obj.getIndustryName());
//			dataRow.createCell(6).setCellValue(obj.getUnitPrice());
//			dataRow.createCell(7).setCellValue(obj.getCreateBy().getName());
//			dataRow.createCell(8).setCellValue(obj.getStartDays());
//			dataRow.createCell(9).setCellValue(obj.getEndDays());
//		}
//		
//		response.reset();
//        response.setContentType("application/octet-stream; charset=utf-8");
//        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
//		wb.write(response.getOutputStream());
//		response.getOutputStream().close();
		
		new ExportExcel("价格一览", PsApplyPriceDtlExport.class).setDataList(list).write(response, fileName).dispose();
	}
	
	/**
	 * 将上传的文件数据临时保存到数据库
	 * @param psApplyPrice
	 */
	@Transactional(readOnly = false)
	public void saveTemp(PsApplyPrice psApplyPrice){
		deletePrevTemp();
		if (StringUtils.isBlank(psApplyPrice.getId())){
			psApplyPrice.preInsert();
			dao.insert(psApplyPrice);
			saveDetail(psApplyPrice);
		}
		else{
			psApplyPrice.preUpdate();
			dao.update(psApplyPrice);
			dao.deleteDetails(psApplyPrice);
			saveDetail(psApplyPrice);
		}
	}
	
	/**
	 * 删除当前用户之前上传的未提交数据
	 */
	@Transactional(readOnly = false)
	private void deletePrevTemp(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			List<PsApplyPrice> tempList = dao.findTempList(user.getId());
			for(PsApplyPrice ps : tempList){
				dao.deleteDetails(ps);
				dao.delete(ps);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void doprocess(PsApplyPrice psApplyPrice) {
		PsApplyPrice ps = dao.get(psApplyPrice.getId());
		if(psApplyPrice.getWorkflowStatus().equals(CommonConstants.WORKFLOW_STATUS_10)
				&& (ps.getProcInsId() == null || ps.getProcInsId().equals(""))){
			psApplyPrice.preUpdate();
			dao.update(psApplyPrice);
			
			// 启动流程
			String procInsId = actTaskService.startProcess(ActUtils.PD_PRICE_APPLY[0], ActUtils.PD_PRICE_APPLY[1], 
					psApplyPrice.getId(), DEFAULT_TITLE);
			actTaskService.completeFirstTask(procInsId);
		}else{
			process(psApplyPrice);
		}
	}
	
	@Transactional(readOnly = false)
	public void save(PsApplyPrice psApplyPrice) {
		// 申请发起
		if (StringUtils.isBlank(psApplyPrice.getId())){
			psApplyPrice.preInsert();
			dao.insert(psApplyPrice);
			saveDetail(psApplyPrice);
			// 启动流程
			String procInsId = actTaskService.startProcess(ActUtils.PD_PRICE_APPLY[0], ActUtils.PD_PRICE_APPLY[1], 
					psApplyPrice.getId(), DEFAULT_TITLE);
			actTaskService.completeFirstTask(procInsId);
		}
		else{
			psApplyPrice.preUpdate();
			dao.update(psApplyPrice);
			dao.deleteDetails(psApplyPrice);
			saveDetail(psApplyPrice);

//			psApplyPrice.getAct().setComment("[再申请]"
//					+ psApplyPrice.getAct().getComment()==null?"":psApplyPrice.getAct().getComment());
//			
//			// 完成流程任务
//			Map<String, Object> vars = Maps.newHashMap();
//			vars.put("pass", psApplyPrice.getAct().getFlag());
//			actTaskService.complete(psApplyPrice.getAct().getTaskId(), psApplyPrice.getAct().getProcInsId(), 
//					psApplyPrice.getAct().getComment(), DEFAULT_TITLE, vars);
			process(psApplyPrice);
		}
	}
	
	/**
	 * 共通流程处理
	 * @param psApplyPrice
	 */
	@Transactional(readOnly = false)
	public void process(PsApplyPrice psApplyPrice) {
		psApplyPrice.preUpdate();
		dao.update(psApplyPrice);//只更新业务主表数据，不更新流程状态
		
		psApplyPrice.getAct().setBusinessId(psApplyPrice.getId());
		commonService.flowProcess(psApplyPrice.getAct(), ActUtils.PD_PRICE_APPLY,
				DEFAULT_TITLE,
				psApplyPrice.getWorkflowStatus());
	}
	
	private static final String DEFAULT_TITLE = "价格申请";
	
	@Transactional(readOnly = false)
	private void saveDetail(PsApplyPrice psApplyPrice){
		for (PsApplyPriceDtl psApplyPriceDtl : psApplyPrice.getPsApplyPriceDtlList()){
//			if (psApplyPriceDtl.getId() == null){
//				continue;
//			}
//			if (PsApplyPriceDtl.DEL_FLAG_NORMAL.equals(psApplyPriceDtl.getDelFlag())){
//				if (StringUtils.isBlank(psApplyPriceDtl.getId())){
					psApplyPriceDtl.setAppId(psApplyPrice);
					psApplyPriceDtl.preInsert();
					if(StringUtils.isBlank(psApplyPriceDtl.getRegion()))
						psApplyPriceDtl.setRegion(null);
					psApplyPriceDtlDao.insert(psApplyPriceDtl);
//				}else{
//					psApplyPriceDtl.preUpdate();
//					psApplyPriceDtlDao.update(psApplyPriceDtl);
//				}
//			}else{
//				psApplyPriceDtlDao.delete(psApplyPriceDtl);
//			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(PsApplyPrice psApplyPrice) {
		super.delete(psApplyPrice);
		psApplyPriceDtlDao.delete(new PsApplyPriceDtl(psApplyPrice));
	}
	
	private static final String MATERIAL_CODE_TITLE = "物料Code";
	private static final String INDUSTRY_TITLE = "行业";
	private static final String REGION_TITLE = "地区";
	private static final String UNIT_PRICE_TITLE = "单价";
	private static final String AGREEMENT_ID_TITLE = "协议编号";
	private static final String[][] EXCEL_TITLE = {
		{"代理商Code",MATERIAL_CODE_TITLE,INDUSTRY_TITLE,REGION_TITLE,UNIT_PRICE_TITLE,AGREEMENT_ID_TITLE},
		{MATERIAL_CODE_TITLE,INDUSTRY_TITLE,REGION_TITLE,UNIT_PRICE_TITLE},
		{"最终客户Code",MATERIAL_CODE_TITLE,UNIT_PRICE_TITLE,AGREEMENT_ID_TITLE},
		{MATERIAL_CODE_TITLE,INDUSTRY_TITLE,REGION_TITLE,UNIT_PRICE_TITLE},
		{"经销商Code",MATERIAL_CODE_TITLE,INDUSTRY_TITLE,UNIT_PRICE_TITLE,AGREEMENT_ID_TITLE},
		{MATERIAL_CODE_TITLE,UNIT_PRICE_TITLE}
		};
	private static final String[] PRICE_SYSTEM_NAME = {"代理","一般直销","协议直销","一般经销","协议经销","配件"};
	private static final String agreePriceSystem = "1|3|5";
	private static final String PRICE_SYSTEM_CODE = "DM0020";
	private static final String INDUSTRY_CODE = "DM0002";
	private static final String REGION_CODE = "DM0049";
	
	@Autowired
	private PsAgreementDao psAgreementDao;
	
	/**
	 * 解析上传的excel数据
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public void excelAnalyse(MultipartFile file,PsApplyPrice psApplyPrice) throws Exception{
		ImportExcel ei = new ImportExcel(file, 0, 0);
		String psName = ei.getSheetName();
		String ps = DictUtils.getDictValue(psName, PRICE_SYSTEM_CODE, null);
		if(ps == null)	throw new Exception("未识别的模板文件(sheet名称无法解析)！");
		String[] pstitle = EXCEL_TITLE[Integer.parseInt(ps)-1];
		List<String> titles = ei.getTitleList();
		if(!isSame(titles,pstitle))	throw new Exception("未识别的模板文件(头行解析错误:头行信息必须与sheet名称匹配且模板sheet名称不可更改)！");
		List<PsApplyPriceDtl> list = ei.getDataListByTitle(PsApplyPriceDtl.class,titles);
		List<String> dtlList = new ArrayList<String>();
		
		int row = 1;
		StringBuilder errorMsg = new StringBuilder();
		for (PsApplyPriceDtl dtl : list){
			row++;
			dtl.setPriceSystemName(psName);
			if(!StringUtils.isBlank(dtl.getRegionName()))
				dtl.setRegion(DictUtils.getDictValue(dtl.getRegionName(), REGION_CODE, null));
			if(!StringUtils.isBlank(dtl.getIndustryName()))
				dtl.setIndustry(DictUtils.getDictValue(dtl.getIndustryName(), INDUSTRY_CODE, null));
			
			//如果标题包含协议方，则协议方不能为空
			if(titles.contains(AGREEMENT_ID_TITLE)){
				if(dtl.getAgreementId() != null){
					PsAgreement agr = psAgreementDao.get(dtl.getAgreementId());
					if(agr != null){
						dtl.setStartDays(DateUtils.formatDate(agr.getValidityDateFrom()));
						dtl.setEndDays(DateUtils.formatDate(agr.getValidityDateTo()));
						dtl.setCutomerName(psAgreementDao.getCustomerName(agr.getAgreementPatiesId()));
					}else{
						errorMsg.append("第"+row+"行:协议方id无法识别，请重新下载模板。");
						errorMsg.append("<br/>");
					}
				}else{
					errorMsg.append("第"+row+"行:协议方id不能为空，请重新下载模板。");
					errorMsg.append("<br/>");
				}
			}
			SmMatInfo mt = smMatInfoDao.getSmMatInfoByNo(dtl.getMaterialNo());
			//物料不能为空
			if(mt == null){
				errorMsg.append("第"+row+"行:物料填写不正确或不能为空。");
				errorMsg.append("<br/>");
			}else{
				dtl.setMaterialName(mt.getMaterialName()+" "+mt.getModel());
				
//				if(ps.equals("1"))//如果是代理
//				{
//					if(isOutOfAgencyTarget(dtl.getAgreementId(),mt))
//					{
//						errorMsg.append("第"+row+"行:物料"+mt.getMaterialNo()+"不属于当前代理商数量目标中的物料，当物料为套餐和机器时，必须符合对应代理商数量目标中的物料。");
//					}
//				}
				//配件模式下只允许输入配件；非配件模式不允许输入配件
//				if(ps.equals("6") && !mt.getMaterialType().equals("2")){
//					errorMsg.append("第"+row+"行:物料"+mt.getMaterialNo()+"类型不是配件，不能在配件模式中输入。");
//					errorMsg.append("<br/>");
//				}
//				else if(!ps.equals("6") && mt.getMaterialType().equals("2")){
//					errorMsg.append("第"+row+"行:物料"+mt.getMaterialNo()+"类型是配件，不能在非配件模式中输入。");
//					errorMsg.append("<br/>");
//				}
			}
			//直销和经销的地区、行业非必填
			if(titles.contains(REGION_TITLE) && !ps.equals("2") && !ps.equals("4"))
				if(StringUtils.isBlank(dtl.getRegion())){
					errorMsg.append("第"+row+"行:地区填写不正确或不能为空。");
					errorMsg.append("<br/>");
				}
			if(titles.contains(INDUSTRY_TITLE) && !ps.equals("2") && !ps.equals("4"))
				if(StringUtils.isBlank(dtl.getIndustry())){
					errorMsg.append("第"+row+"行:行业填写不正确或不能为空。");
					errorMsg.append("<br/>");
				}
			//单价不能为空
			if(titles.contains(UNIT_PRICE_TITLE))
				if(StringUtils.isBlank(dtl.getUnitPrice())){
					errorMsg.append("第"+row+"行:单价不能为空。");
					errorMsg.append("<br/>");
				}else{
					try {
						Double.valueOf(dtl.getUnitPrice().trim());
					} catch (Exception e) {
						errorMsg.append("第"+row+"行:单价数值填写不正确。");
						errorMsg.append("<br/>");
					}
				}
			
			String key = dtl.getAgreementId()+"&"+dtl.getMaterialNo()+"&"+dtl.getRegionName()+"&"+dtl.getIndustryName()+"&"+dtl.getAgreementPatiesId();
			//数据不能重复
			if(dtlList.contains(key)){
				errorMsg.append("第"+row+"行:数据重复输入。");
				errorMsg.append("<br/>");
			}else{
				dtlList.add(key);
			}
		}
		String msg = errorMsg.toString();
		if(!StringUtils.isBlank(msg)){
			throw new Exception(msg);
		}
		
		psApplyPrice.setPriceSystem(ps);
		psApplyPrice.setPsApplyPriceDtlList(list);
		psApplyPrice.setCreateBy(UserUtils.getUser());
	}
	
	/**
	 * 如果是上传代理的话，需要额外进行以下的验证
	 * 根据协议期间以及代理商找到该代理商的目标，如果当年目标是金额目标，则型号任意录入
	 * 如果当年目标是数量目标的话，则型号（仅显示物料类型=套餐和机器的）需要验证是在目标中录入过的型号才能进行价格申请
	 * @param dtl
	 * @param mat
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean isOutOfAgencyTarget(String agreementId, SmMatInfo mat)
	{
		//如果物料属于套餐或机器，则取代理商数量目标的所有物料进行比较
		if(mat.getMaterialType().equals("1") || mat.getMaterialType().equals("3"))
		{
			List<String> matList = psApplyPriceDtlDao.findAgentTargetMaterial(agreementId);
			//如果没有数量目标，则默认符合条件，否则判断当前物料是否在数量目标中
			if(matList.size() == 0 || matList.contains(mat.getMaterialNo()))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;//如果物料不是套餐和机器，始终返回false
		}
	}
	
	/**
	 * 判断上传文件的标题头是否和标准的标题头相符合
	 * @param file
	 * @param template
	 * @return
	 */
	private boolean isSame(List<String> file, String[] template){
		if(file.size() == template.length)
		{
			for(String str : template){
				if(!file.contains(str)){
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 根据用户选择的价格体系导出Excel模板
	 * @param response
	 * @param psApplyPrice
	 * @throws IOException
	 */
	public void exportTemplateExcel(HttpServletResponse response, PsApplyPrice psApplyPrice) throws IOException,Exception{
		String ps = psApplyPrice.getSelectPriceSystem();
		
		String[] title = EXCEL_TITLE[Integer.parseInt(ps)-1];
		String fileName = "价格申请模板"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
		String sheetName = PRICE_SYSTEM_NAME[Integer.parseInt(ps)-1];
		
		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet(sheetName);
		Map<String, CellStyle> styles = createStyles(wb);
		Row headerRow = sheet.createRow(0);
		Row dataRow = sheet.createRow(1);
		for(int i=0; i < title.length; i++){
			if(agreePriceSystem.contains(ps)){
				Cell headCell = headerRow.createCell(i);
				Cell dataCell = dataRow.createCell(i);
				if(i==0 || i==title.length-1){
					headCell.setCellStyle(styles.get("greyheader"));
					dataCell.setCellStyle(styles.get("greydata"));
					if(i==0)
						dataCell.setCellValue(psApplyPrice.getProtocols());
					else
						dataCell.setCellValue(psApplyPrice.getStartdays());
				}else{
					headCell.setCellStyle(styles.get("header"));
					dataCell.setCellStyle(styles.get("data"));
				}
				headCell.setCellValue(title[i]);
			} else{
				Cell headCell = headerRow.createCell(i);
				headCell.setCellStyle(styles.get("header"));
				headCell.setCellValue(title[i]);
			}
			if(title[i].equals(INDUSTRY_TITLE)){
				String[] industryList = dictService.findValueListByType(INDUSTRY_CODE);
				setValidationData(sheet,1,1000,i,i,industryList);
			}else if(title[i].equals(REGION_TITLE)){
				String[] regionList = dictService.findValueListByType(REGION_CODE);
				setValidationData(sheet,1,1000,i,i,regionList);
			}
			//sheet.autoSizeColumn(i);
		}
		//如果是备件，则物料信息sheet页只导出备件，否则只导出非备件
		boolean isPart = ps.equals("6");
		createMaterialSheet(wb, isPart);
		
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
		wb.write(response.getOutputStream());
		response.getOutputStream().close();
	}
	
	/**
	 * 导出物料信息到sheet中
	 * 如果是备件，则物料信息sheet页只导出备件，否则只导出非备件
	 * @param wb
	 * @param isPart是否备件
	 */
	private void createMaterialSheet(XSSFWorkbook wb, boolean isPart)
	{
		Sheet sheet = wb.createSheet("MATERIAL_INFO");
		SmMatInfo smMatInfo = new SmMatInfo();
		List<SmMatInfo> list = null;
		if(isPart)
			list = smMatInfoDao.getPartList(smMatInfo);
		else
			list = smMatInfoDao.getNoPartList(smMatInfo);
		Row headerRow = sheet.createRow(0);
		Cell titleCell1 = headerRow.createCell(0);
		titleCell1.setCellValue("物料编号");
		Cell titleCell2 = headerRow.createCell(1);
		titleCell2.setCellValue("物料类型");
		Cell titleCell3 = headerRow.createCell(2);
		titleCell3.setCellValue("产品名称");
		Cell titleCell4 = headerRow.createCell(3);
		titleCell4.setCellValue("型号");
		for(int i=0;i<list.size();i++)
		{
			Row dataRow = sheet.createRow(i+1);
			Cell dataCell1 = dataRow.createCell(0);
			dataCell1.setCellValue(list.get(i).getMaterialNo());
			Cell dataCell2 = dataRow.createCell(1);
			dataCell2.setCellValue(DictUtils.getDictLabel(list.get(i).getMaterialType(), "DM0058", "未知"));
			Cell dataCell3 = dataRow.createCell(2);
			dataCell3.setCellValue(list.get(i).getMaterialName());
			Cell dataCell4 = dataRow.createCell(3);
			dataCell4.setCellValue(list.get(i).getModel());
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
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		styles.put("greydata", style);
		
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
//		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("header"));
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		styles.put("greyheader", style);
		
		return styles;
	}
	
	/** 
     * 添加数据有效性检查. 
     * @param sheet 要添加此检查的Sheet 
     * @param firstRow 开始行 
     * @param lastRow 结束行 
     * @param firstCol 开始列 
     * @param lastCol 结束列 
     * @param explicitListValues 有效性检查的下拉列表 
     */  
    public void setValidationData(Sheet sheet, int firstRow,  int lastRow,  
            int firstCol,  int lastCol,String[] explicitListValues){  
        if (firstRow < 0 || lastRow < 0 || firstCol < 0 || lastCol < 0 || lastRow < firstRow || lastCol < firstCol) {  
            return;
        }  
        if (sheet instanceof XSSFSheet) {  
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet);  
            XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper  
                    .createExplicitListConstraint(explicitListValues);  
            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);  
            XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);  
            validation.setSuppressDropDownArrow(true);  
            validation.setShowErrorBox(true);  
            sheet.addValidationData(validation);  
        } else if(sheet instanceof HSSFSheet){  
            CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);  
            DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(explicitListValues);  
            DataValidation validation = new HSSFDataValidation(addressList, dvConstraint);  
            validation.setSuppressDropDownArrow(true);  
            validation.setShowErrorBox(true);  
            sheet.addValidationData(validation);  
        }  
    }
}