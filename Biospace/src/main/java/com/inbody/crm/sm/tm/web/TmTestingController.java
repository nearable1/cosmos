/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sm.tm.web;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inbody.crm.common.entity.SnInfo;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.DictUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.sm.tm.entity.TmTesting;
import com.inbody.crm.sm.tm.entity.TmTestingDtl;
import com.inbody.crm.sm.tm.service.TmTestingService;

/**
 * 主子表Controller
 * @author yangj
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/tm/tmTesting")
public class TmTestingController extends BaseController {

	@Autowired
	private TmTestingService tmTestingService;
	@Autowired
	private CommonService commonService;
    @Autowired
    private AttachmentsService attachmentsService;
	
	@RequestMapping(value = {"list", ""})
	public String list(TmTesting tmTesting, HttpServletRequest request, HttpServletResponse response, Model model) {
//		User user = UserUtils.getUser();
//		Map<String, String> uerMap = new HashMap<String, String>();
//		uerMap.put("dataScope", BaseService.dataScopeFilter(user));
//		tmTesting.setSqlMap(uerMap);
		
		if(!StringUtils.isEmptyNull(tmTesting.getIfPrimeProblemSearch())){
			tmTesting.setIfPrimeProblemSearch("1");
		}
		Page<TmTesting> page = null;
		if(StringUtils.isEmptyNull(tmTesting.getIfFullResultSearch())){
			page = tmTestingService.findPageLatest(new Page<TmTesting>(request, response), tmTesting); 
		}else{
			tmTesting.setIfFullResultSearch("1");
			page = tmTestingService.findPage(new Page<TmTesting>(request, response), tmTesting); 
		}
		model.addAttribute("page", page);
		return "inbody/sm/tm/tm001";
	}

	/**
	 * 导出检测记录数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping(value = "export")
    public String exportFile(TmTesting tmTesting, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			User user = UserUtils.getUser();
			Map<String, String> uerMap = new HashMap<String, String>();
			uerMap.put("dataScope", BaseService.dataScopeFilter(user));
			tmTesting.setSqlMap(uerMap);
			
			String fileName = "检测记录_"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            if(!StringUtils.isEmptyNull(tmTesting.getIfPrimeProblemSearch())){
    			tmTesting.setIfPrimeProblemSearch("1");
    		}
            List<TmTesting> testingList = null;
    		if(StringUtils.isEmptyNull(tmTesting.getIfFullResultSearch())){
    			testingList = tmTestingService.findListLatest(tmTesting); 
    		}else{
    			testingList = tmTestingService.findList(tmTesting); 
    		}
    		for(TmTesting testing :testingList){
    			testing.setTestingDateStr(DateUtils.formatDate(testing.getTestingDate(), "yyyy-MM-dd"));
    			testing.setProductionDateStr(DateUtils.formatDate(testing.getProductionDate(), "yyyy-MM-dd"));
    		}
    		new ExportExcel("检测记录", TmTesting.class).setDataList(testingList).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出检测记录失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/tm/tmTesting/list?repage";
    }
	
	@RequiresPermissions("tm:tmTesting:view")
	@RequestMapping(value = "form")
	public String form(TmTesting tmTesting, Model model) {
		if(StringUtils.isNotBlank(tmTesting.getId())){
			tmTesting = tmTestingService.get(tmTesting.getId());
		}
		User user = tmTesting.getCurrentUser();
		if(StringUtils.isEmptyNull(tmTesting.getCreatePersonId())){
			tmTesting.setCreatePersonId(user.getId());
			tmTesting.setCreatePersonName(user.getName());
		}
		if(StringUtils.isEmptyNull(tmTesting.getTestingPersonId())){
			tmTesting.setTestingPersonId(user.getId());
		}
//		if(StringUtils.isEmptyNull(tmTesting.getCheckPersonId())){
//			tmTesting.setCheckPersonId(user.getId());
//		}
		if(tmTesting.getTestingDate() == null){
			tmTesting.setTestingDate(new Date());
		}
		if(StringUtils.isEmptyNull(tmTesting.getId())){
			List<SnInfo> snInfoList = tmTestingService.getSnInfo(tmTesting.getSnNo(), tmTesting.getMaterialNo());
			if(snInfoList != null && snInfoList.size() > 0){
				SnInfo snInfo = snInfoList.get(0);
				TmTestingDtl testingDtl = new TmTestingDtl(); 
				testingDtl.setLineNo("1");
				testingDtl.setMaterialNo(snInfo.getMaterialNo());
				testingDtl.setSnNo(snInfo.getSnNo());

				testingDtl.setMachineType(snInfo.getMachineType());
				testingDtl.setStatus(snInfo.getStatus());
				testingDtl.setProductionDate(snInfo.getProductionDate());
				testingDtl.setSnVersion(snInfo.getSnVersion());
				
				tmTesting.getTmTestingDtlList().add(testingDtl);
			}
		}
		for(TmTestingDtl dtl:tmTesting.getTmTestingDtlList()){
			dtl.setMachineTypeName(DictUtils.getDictLabel(dtl.getMachineType(), "DM0032", ""));
			dtl.setStatusValue(DictUtils.getDictLabel(dtl.getStatus(), "DM0033", ""));
			dtl.setProductionDateValue(DateUtils.formatDate(dtl.getProductionDate()));
		}
		tmTesting.setPrimeProblemComment(tmTesting.getNewRemarks());
		model.addAttribute("tmTesting", tmTesting);
		return "inbody/sm/tm/tm002";
	}

	@RequiresPermissions("tm:tmTesting:edit")
	@ResponseBody
	@RequestMapping(value = "checkFormData")
	public Map<String,String> checkFormData(TmTesting tmTesting,Model model) {
		Map<String,String> messageList = Maps.newHashMap();
		Boolean hasError = false;
		List<String> list =Lists.newArrayList();
		
		TmTesting entity = null;
		if (StringUtils.isNotBlank(tmTesting.getId())){
			entity = tmTestingService.get(tmTesting.getId());
			if(entity.getUpdateDate().compareTo(tmTesting.getUpdateDate())!=0){
				list.add("该检测记录已经被其他人更新了。");
			}
		}
		
		if(!StringUtils.isEmptyNull(tmTesting.getIfPrimeProblem()) && "on".equals(tmTesting.getIfPrimeProblem())){
			if(StringUtils.isEmptyNull(tmTesting.getPrimeProblemComment())){
				list.add("选择初期不良时，请填写说明。");
			}
		}

		List<TmTestingDtl> detailList = Lists.newArrayList();		// 子表列表
		List<String> snList = Lists.newArrayList();		// 检查sn是否重复用
		if(tmTesting.getTmTestingDtlList().size() == 0){ 
			list.add("检测记录明细至少需要录入一条。");
		}else{
			for(TmTestingDtl testingDetail :tmTesting.getTmTestingDtlList()){
				if("1".equals(testingDetail.getDelFlag())){
					continue;
				}
				detailList.add(testingDetail);
				
				if(StringUtils.isEmptyNull(testingDetail.getMaterialNo()) || StringUtils.isEmptyNull(testingDetail.getSnNo())){
					//如果物料号没有录入，认为是无效行。
					list.add("第"+testingDetail.getLineNo()+"行，物料号和SN必须输入。");
				}
				
				if(StringUtils.isNotBlank(testingDetail.getSnNo())){
					boolean isInsert = true;
					for(String snNo :snList){
						if(StringUtils.equals(testingDetail.getSnNo(), snNo)){
							isInsert = false;
							list.add("第"+testingDetail.getLineNo()+"行，SN号已经存在。");
						}
					}
					if(isInsert){
						snList.add(testingDetail.getSnNo());
					}
				}
			}
			
			if(detailList.size() == 0){ 
				list.add("检测记录明细至少需要录入一条。");
			}
		}
		if(snList.size() > 0){
			for(String snNo :snList){
				int count =tmTestingService.getSnWarehouseInfo(snNo);
				if(count <= 0){
					list.add("S/N（"+snNo+"）对应的机器目前没有库存。");
				}
			}
		}
		if(list.size() > 0){
			hasError = true;
		}
		if(hasError){
			StringBuilder sb = new StringBuilder();
			for (String message : list){
				sb.append(message).append(list.size()>1?"<br/>":"");
			}
			messageList.put("error", "true");
			messageList.put("errorMessage", sb.toString());
		}

		return messageList;
	}

	@ResponseBody
	@RequestMapping(value = "ajaxsave")
	public Map<String, Object>  save(TmTesting tmTesting) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
        Calendar c = Calendar.getInstance();
        if(!StringUtils.isEmptyNull(tmTesting.getIfPrimeProblem()) && "on".equals(tmTesting.getIfPrimeProblem())){
        	tmTesting.setIfPrimeProblem("1");
		}else{
			tmTesting.setIfPrimeProblem("0");
		}
		if(StringUtils.isEmptyNull(tmTesting.getTestingNo())){
			String code=commonService.getNextSequence("TM",5);
			String testingNo = "TM_"+c.get(Calendar.YEAR)+"_"+code;
			tmTesting.setTestingNo(testingNo);
		}
		tmTesting.setNewRemarks(tmTesting.getPrimeProblemComment());
		tmTestingService.save(tmTesting);

		if("1".equals(tmTesting.getIfPrimeProblem())){
			resultMap.put("url", "/rm/repair/form?snNo="+tmTesting.getTmTestingDtlList().get(0).getSnNo()+"&repairType=4");
		}else{
			resultMap.put("url", "/tm/tmTesting/form?id="+tmTesting.getId());
		}
        resultMap.put("success", true);
		return resultMap;
	}

	/**
	 * 取得SN信息
	 * @param snNo
	 *        S/N
	 * @return SN信息
	 */
	@RequestMapping(value = "getSnInfo", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getSnInfo(String snNo,String materialNo) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<SnInfo> snInfoList = tmTestingService.getSnInfo(snNo, materialNo);
		if(snInfoList == null ||snInfoList.size() == 0){
			jsonMap.put("error", "true");
			jsonMap.put("errorMessage", "输入的SN No不存在。");
		}else{
			SnInfo snInfo = snInfoList.get(0);
			snInfo.setMachineTypeName(DictUtils.getDictLabel(snInfo.getMachineType(), "DM0032", ""));
			snInfo.setStatusValue(DictUtils.getDictLabel(snInfo.getStatus(), "DM0033", ""));
			snInfo.setProductionDateValue(DateUtils.formatDate(snInfo.getProductionDate()));
			jsonMap.put("snInfo", snInfo);
		}
		
		return jsonMap;
	}

	@RequiresPermissions("tm:tmTesting:edit")
    @RequestMapping(value = "delete/file/{fileId}")
    @ResponseBody
    public Map<String, Object> deleteFile(@PathVariable String fileId) {
        String delFileName = attachmentsService.deleteFile(fileId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", "文件：" + delFileName + "删除成功！");
        resultMap.put("success", new Boolean(true));
        return resultMap;
    }
    
    @RequestMapping(value = "download/file/{fileId}")
    public void downLoadFile(@PathVariable String fileId, HttpServletRequest request,
            HttpServletResponse response) {
        attachmentsService.downloadFile(fileId, request, response);
    }
}