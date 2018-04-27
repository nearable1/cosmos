package com.inbody.crm.sm.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.sm.entity.OutStorageManagement;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;
import com.inbody.crm.sm.service.DelayStorageManagementService;
import com.inbody.crm.sm.service.OutStorageManagementService;

@Controller
@RequestMapping(value = "${adminPath}/sm/sm010")
public class DelayStorageManagementController {

	@Autowired
	private CommonService commonService;

	@Autowired
	private DelayStorageManagementService delayStorageManagementService;

//	private List<Map<String, String>> smStorageAppDtl = new ArrayList<Map<String, String>>();

	@Autowired
	OutStorageManagementService outStorageManagementService;
//	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private OutStorageManagement osm = new OutStorageManagement();

	@RequestMapping(value = { "list", "" })
	public ModelAndView list(OutStorageManagement outStorageManagement,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("sd/sm/sm010");
		mav.addObject("viewStatus",
				commonService.getProcViewStatus(null, null, true));
		SmStorageInfoManagement ss = new SmStorageInfoManagement();
		ss.preInsert();
//		list = delayStorageManagementService.selectAll(ss);
		List<Map<String, String>> list = delayStorageManagementService.selectAll(ss);
		outStorageManagement.setSmStorageAppDtl(list);
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		mav.addObject("smStorageInfo", list);
		mav.addObject("outStorageDtl", outStorageManagement);
		mav.addObject("date", dateFormat.format(now));
		mav.addObject("user", ss);
		return mav;

	}

	@RequestMapping(value = "add")
	public @ResponseBody Map<String, Object> add(OutStorageManagement outStorageManagement, String but,
			HttpServletRequest request, HttpServletResponse response,
			String newAdd) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
/*		try {
			delayStorageManagementService.add(outStorageManagement, osm,
					request, but, list, newAdd);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}*/
		try {
			
			String message = delayStorageManagementService.addValidator(outStorageManagement, but);

			if (StringUtils.isEmptyNull(message)) {
				delayStorageManagementService.add(outStorageManagement, osm, 
						request, but, newAdd);

				jsonMap.put("success", true);
				jsonMap.put("message", null);
			} else {
				jsonMap.put("success", false);
				jsonMap.put("message", message);
			}
		} catch (Exception e) {
			jsonMap.put("success", false);
			jsonMap.put("message", e.getMessage());
		}
		return jsonMap;
//		String[] index = request.getParameterValues("index");
//		String[] ckb = request.getParameterValues("ckb");
//		// String [] snNo = request.getParameterValues("newSnNo");
//		String[] extendDateTo = request.getParameterValues("extendDateTo");
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		if (!but.equals("0") && !but.equals("4") && !but.equals("2")) {
//			outStorageManagement.getUpdateBy();
//			outStorageManagement.setId(osm.getId());
//			outStorageManagementService.updateStorageApp(outStorageManagement);
//		}
//		// 生成出入库ID
//		outStorageManagement.preInsert();
//		String tpId = outStorageManagement.getId();
//		outStorageManagement.setDate(df.format(
//				outStorageManagement.getCreateDate()).toString());
//		// 添加出入库申请
//		// 出入库详细信息操作
//		if (but.equals("0")) {
//			for (int i = 0; i < ckb.length; i++) {
//				// 等于0，为出库申请 需要添加申请出入库的合同号
//
//				Integer num = Integer.valueOf(ckb[i]);
//				Map<String, String> map = delayStorageManagementService
//						.selectBySsiInfo(index[num]).get(0);
//				if (i == 0) {
//					outStorageManagement.setStorageType(String.valueOf(map
//							.get("STORAGE_TYPE")));
//					outStorageManagement
//							.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
//					outStorageManagement.setLendingType(String.valueOf(map
//							.get("LENDING_TYPE")));
//					outStorageManagement.setCustomerId(String.valueOf(map
//							.get("CUSTOMER_ID")));
//					outStorageManagement.setIndustry(String.valueOf(map
//							.get("INDUSTRY")));
//					outStorageManagement.setLendingDateFrom(String.valueOf(map
//							.get("LENDING_DATE_FROM")));
//					// outStorageManagement.setNewRemarks(String.valueOf(map.get("NEW_REMARKS")));
//					outStorageManagementService
//							.addBySmStorageApp(outStorageManagement);
//				}
//				outStorageManagement.setPurchaseNo(index[num]);
//				outStorageManagement.setOrderNo(String.valueOf(map
//						.get("ORDER_NO")));
//				outStorageManagement.setLineNo(String.valueOf(map
//						.get("LINE_NO")));
//				outStorageManagement.setSnNo(String.valueOf(map.get("SN_NO")));
//				outStorageManagement.setMaterialNo(String.valueOf(map
//						.get("MATERIAL_NO")));
//				outStorageManagement.setNum(String.valueOf(map.get("NUM")));
//				outStorageManagement.setWarehouse(String.valueOf(map
//						.get("WAREHOUSE")));
//				outStorageManagement.setAddress(String.valueOf(map
//						.get("ADDRESS")));
//				outStorageManagement.setContacts(String.valueOf(map
//						.get("CONTACTS_NAME")));
//				outStorageManagement.setTelephone(String.valueOf(map
//						.get("TELEPHONE")));
//				outStorageManagement.setAccessoriesRemarks(String.valueOf(map
//						.get("ACCESSORIES_REMARKS")));
//				outStorageManagement.setLendingDateTo(String.valueOf(map
//						.get("LENDING_DATE_TO")));
//				outStorageManagement.setExtendDateTo(String
//						.valueOf(extendDateTo[num]));
//				outStorageManagement.setAppId(tpId);
//				// outStorageManagement.setWarehouse("0");
//				outStorageManagement.preInsert();
//				outStorageManagementService
//						.addByOutStorageManagement(outStorageManagement);
//			}
//		} else if (but.equals("3")) {
//
//			outStorageManagementService.deleteDtlAppId(osm.getId());
//			for (int i = 0; i < ckb.length; i++) {
//				Map<String, String> map = delayStorageManagementService
//						.selectBySsiInfo(ckb[i]).get(0);
//				outStorageManagement.setPurchaseNo(ckb[i]);
//				outStorageManagement.setOrderNo(String.valueOf(map
//						.get("ORDER_NO")));
//				outStorageManagement.setLineNo(String.valueOf(map
//						.get("LINE_NO")));
//				outStorageManagement.setSnNo(String.valueOf(map.get("SN_NO")));
//				outStorageManagement.setMaterialNo(String.valueOf(map
//						.get("MATERIAL_NO")));
//				outStorageManagement.setNum(String.valueOf(map.get("NUM")));
//				outStorageManagement.setWarehouse(String.valueOf(map
//						.get("WAREHOUSE")));
//				outStorageManagement.setAddress(String.valueOf(map
//						.get("ADDRESS")));
//				outStorageManagement.setContacts(String.valueOf(map
//						.get("CONTACTS_NAME")));
//				outStorageManagement.setTelephone(String.valueOf(map
//						.get("TELEPHONE")));
//				outStorageManagement.setAccessoriesRemarks(String.valueOf(map
//						.get("ACCESSORIES_REMARKS")));
//				outStorageManagement.setLendingDateTo(String.valueOf(map
//						.get("LENDING_DATE_TO")));
//				outStorageManagement.setExtendDateTo(String
//						.valueOf(extendDateTo[i]));
//				outStorageManagement.setAppId(osm.getId());
//				// outStorageManagement.setWarehouse("0");
//				outStorageManagement.preInsert();
//				outStorageManagementService
//						.addByOutStorageManagement(outStorageManagement);
//			}
//
//		}
//
//		// 启动流程
//		osm.getAct().setComment(request.getParameter("comment"));
//		if (but.equals("0")) {
//			OutStorageManagement m = new OutStorageManagement();
//			m.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
//			m.setId(tpId);
//			delayStorageManagementService.addProcess(m);
//		}
//		if (but.equals("1")) {
//			osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_20);
//			delayStorageManagementService.addProcess(osm);
//		}
//		if (but.equals("2")) {
//			osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_30);
//			delayStorageManagementService.addProcess(osm);
//		}
//		if (but.equals("3")) {
//			osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
//			delayStorageManagementService.addProcess(osm);
//		}
//		if (but.equals("4")) {
//			osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_40);
//			delayStorageManagementService.addProcess(osm);
//		}
//		if (but.equals("5")) {
//			osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_70);
//			delayStorageManagementService.addProcess(osm);
//		}

	}

	@RequestMapping(value = "eaa")
	public ModelAndView eaa(OutStorageManagement outStorageManagement,
			HttpServletRequest request, HttpServletResponse response) {
		osm = SerializationUtils.clone(outStorageManagement);
		ModelAndView mav = new ModelAndView("sd/sm/sm010");
		mav.addObject("viewStatus", commonService.getProcViewStatus(
				outStorageManagement.getAct(), outStorageManagement.getId(),
				true));
		List<Map<String, String>> smStorageApp = outStorageManagementService
				.smStorageApp(outStorageManagement.getId());
		List<Map<String, String>> smStorageAppDtl = outStorageManagementService
				.smStorageAppDtl(outStorageManagement.getId());
		List<Map<String, String>> dsList = new ArrayList<Map<String, String>>();
		for (Map<String, String> map : smStorageAppDtl) {
			Map<String, String> maps = delayStorageManagementService
					.selectBySsiInfoId(map.get("PROC_INS_ID")).get(0);
//			maps.put("extendDateTo", String.valueOf(map.get("EXTEND_DATE_TO")));
			maps.put("extendDateTo", String.valueOf(map.get("EXTEND_DATE_TO")));
			maps.put("extendReason", map.get("EXTEND_REASON"));
			maps.put("num", String.valueOf(map.get("NUM")));
			dsList.add(maps);
		}
		outStorageManagement.preInsert();
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		outStorageManagement.setDate(myFmt.format(smStorageApp.get(0).get(
				"CREATE_DATE")));
		try {
			smStorageApp.get(0).put(
					"name",
					UserUtils.get(
							smStorageApp.get(0).get("RESPONSIBLE_PERSON_ID"))
							.getName());
		} catch (Exception e) {
		}
		mav.addObject("date",
				myFmt.format(smStorageApp.get(0).get("CREATE_DATE")));
		mav.addObject("user", outStorageManagement);
		mav.addObject("smStorageApp", smStorageApp);
		mav.addObject("smStorageAppDtl", smStorageAppDtl);
		outStorageManagement.setSmStorageAppDtl(dsList);
//		mav.addObject("smStorageInfo", dsList);
		mav.addObject("outStorageDtl", outStorageManagement);
		return mav;

	}

}
