package com.inbody.crm.sm.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.sm.entity.OutStorageManagement;
import com.inbody.crm.sm.entity.SmWarehouseInfoManagement;
import com.inbody.crm.sm.service.OutStorageManagementService;
import com.inbody.crm.sm.service.ScrapStorageManagementService;

@Controller
@RequestMapping(value = "${adminPath}/sm/sm007")
public class ScrapStorageManagementController {

	@Autowired
	private ScrapStorageManagementService scrapStorageManagementService;

	private SmWarehouseInfoManagement swim = new SmWarehouseInfoManagement();

	@Autowired
	private OutStorageManagementService outStorageManagementService;

//	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

//	private List<Map<String, String>> smStorageAppDtl = new ArrayList<Map<String, String>>();

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = { "list", "" })
	public ModelAndView list(
			SmWarehouseInfoManagement smWarehouseInfoManagement,
			HttpServletRequest request, HttpServletResponse response) {
//		list = scrapStorageManagementService
//				.selectAll(smWarehouseInfoManagement);
		ModelAndView mav = new ModelAndView("sd/sm/sm007");
		mav.addObject("viewStatus",
				commonService.getProcViewStatus(null, null, true));
		smWarehouseInfoManagement.preInsert();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// List<Map<String,String>> lm = extract(0);
		mav.addObject("date", dateFormat.format(now));
		mav.addObject("swifm", smWarehouseInfoManagement);
		// mav.addObject("list", list);
		return mav;
	}

//	@RequestMapping(value = "queryByMaterial")
//	@ResponseBody
//	public List<Map<String, String>> queryByMaterial(String qureyMaterialNo)
//			throws UnsupportedEncodingException {
//		String str = new String(qureyMaterialNo.getBytes("iso8859-1"), "utf-8");
//		List<Map<String, String>> lm = new ArrayList<Map<String, String>>();
//		// if(!addYseOrNo(str))
//		// return lm;
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).get("materialNo").equals(str))
//				lm.add(list.get(i));
//		}
//		return lm;
//	}

//	@RequestMapping(value = "loadByIndex")
//	@ResponseBody
//	public Map<String, String> loadByIndex(int index) {
//
//		Map<String, String> map = list.get(index);
//		return map;
//	}

	@RequestMapping(value = "Add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> Add(OutStorageManagement outStorageManagement,
			HttpServletRequest request, HttpServletResponse response, String but)
			throws ParseException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			
			String message = scrapStorageManagementService.addValidator(outStorageManagement, but);

			if (StringUtils.isEmptyNull(message)) {

				String opt = getWorkflowStatusByOpt(but);
				scrapStorageManagementService.add(outStorageManagement, swim, request, but, opt);

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
		// String[] index = request.getParameterValues("index");
		// String[] toNum = request.getParameterValues("addNum");
		// String appId = "";
		// swim.getAct().setComment(request.getParameter("comment"));
		// if (but.equals("5")) {
		// swim.setWorkflowStatus(getWorkflowStatusByOpt(but));
		// scrapStorageManagementService.addProcess(swim);
		// return null;
		// }
		// if (but.equals("4") || but.equals("2")) {
		// swim.setWorkflowStatus(getWorkflowStatusByOpt(but));
		// scrapStorageManagementService.addProcess(swim);
		// return null;
		// }
		// if (but.equals("3")) {
		// outStorageManagementService.deleteDtlAppId(swim.getId());
		// }
		// try {
		// // 提交出库申请
		// if (but.equals("0")) {
		// outStorageManagement.preInsert();
		// appId += outStorageManagement.getId();
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// outStorageManagement
		// .setStorageType(CommonConstants.STORAGE_TYPE_24);
		// //
		// outStorageManagement.setLendingType(CommonConstants.STORAGE_TYPE_24);
		// outStorageManagement
		// .setWorkflowStatus(getWorkflowStatusByOpt(but));
		// outStorageManagement.setDate(df.format(
		// outStorageManagement.getCreateDate()).toString());
		// scrapStorageManagementService
		// .addBySmStorageApp(outStorageManagement);
		// outStorageManagement.setAppId(appId);
		// swim = new SmWarehouseInfoManagement();
		// swim.setId(appId);
		// }
		//
		// if (!but.equals("0") && !but.equals("4") && !but.equals("2")) {
		// outStorageManagement.getUpdateBy();
		// outStorageManagement.setId(swim.getId());
		// outStorageManagementService
		// .updateStorageApp(outStorageManagement);
		// }
		//
		// for (int i = 0; i < index.length; i++) {
		// outStorageManagement.preInsert();
		// List<Map<String, String>> osmList = scrapStorageManagementService
		// .selectByWid(index[i]);
		// outStorageManagement.setMaterialNo(osmList.get(0).get(
		// "MATERIAL_NO"));
		// String warehouse = String.valueOf(osmList.get(0).get(
		// "WAREHOUSE"));
		// outStorageManagement.setWarehouse(warehouse);
		// try {
		// if (osmList.get(0).get("SN_NO") != null
		// && osmList.get(0).get("SN_NO") != "") {
		// outStorageManagement.setSnNo(String.valueOf(osmList
		// .get(0).get("SN_NO")));
		// } else {
		// outStorageManagement.setSnNo(String.valueOf(""));
		// }
		// } catch (Exception e) {
		// outStorageManagement.setSnNo(String.valueOf(""));
		//
		// }
		// outStorageManagement.setNum(toNum[i]);
		// // 添加出货明细为1修改
		// if (but.equals("0")) {
		// outStorageManagement
		// .setWorkflowStatus(getWorkflowStatusByOpt(but));
		// scrapStorageManagementService
		// .addByOutStorageManagement(outStorageManagement);
		// } else if (but.equals("1")) {
		// // outStorageManagement.setId(index[i]);
		// //
		// scrapStorageManagementService.addByOutStorageManagement(outStorageManagement);
		// } else if (but.equals("3")) {
		// outStorageManagement.setAppId(swim.getId());
		// outStorageManagement
		// .setWorkflowStatus(getWorkflowStatusByOpt(but));
		// scrapStorageManagementService
		// .addByOutStorageManagement(outStorageManagement);
		// }
		//
		// }
		//
		// } catch (Exception e) {
		// throw new RuntimeException(e);
		// }
		// // 不等于0修改流程
		// swim.setWorkflowStatus(getWorkflowStatusByOpt(but));
		// scrapStorageManagementService.addProcess(swim);
//		return null;

	}

	@RequestMapping(value = "eaa")
	public ModelAndView eaa(
			SmWarehouseInfoManagement smWarehouseInfoManagement,
			HttpServletRequest request, HttpServletResponse response) {
		swim = SerializationUtils.clone(smWarehouseInfoManagement);
		ModelAndView mav = new ModelAndView("sd/sm/sm007");
		List<Map<String, String>> smStorageApp = scrapStorageManagementService
				.smStorageApp(smWarehouseInfoManagement.getId());

		mav.addObject("viewStatus", commonService.getProcViewStatus(
				swim.getAct(), swim.getId(), true));
//		list = scrapStorageManagementService
//				.selectAll(smWarehouseInfoManagement);
		// List<Map<String,String>> lm = extract(0);
		List<Map<String, String>> smStorageAppDtl = scrapStorageManagementService
				.smStorageAppDtl(smWarehouseInfoManagement.getId());
		// mav.addObject("list", lm);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		mav.addObject("date",
				myFmt.format(smStorageApp.get(0).get("CREATE_DATE")));
		try {
			smStorageApp.get(0).put(
					"name",
					UserUtils.get(
							smStorageApp.get(0).get("RESPONSIBLE_PERSON_ID"))
							.getName());
		} catch (Exception e) {

		}
		mav.addObject("user1", smWarehouseInfoManagement);
		mav.addObject("smStorageApp", smStorageApp);
		OutStorageManagement smStorageAppDtls = new OutStorageManagement();
		smStorageAppDtls.setSmStorageAppDtl(smStorageAppDtl);
//		mav.addObject("smStorageAppDtl", smStorageAppDtl);
		mav.addObject("outStorageDtl", smStorageAppDtls);
		return mav;

	}

//	public List<Map<String, String>> extract(int num) {
//		num *= 10;
//		List<Map<String, String>> lm = new ArrayList<Map<String, String>>();
//
//		for (int i = 0; i < 10; i++) {
//			if (num < list.size()) {
//				lm.add(list.get(i));
//			}
//		}
//		return lm;
//
//	}

//	public int index(String index) {
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).get("id").equals(index))
//				return i;
//		}
//		return -1;
//	}

	/**
	 * 根据操作按钮取得工作流状态
	 * 
	 * @param opt
	 *            操作按钮
	 * @return 工作流状态
	 */
	private String getWorkflowStatusByOpt(String opt) {
		if (StringUtils.equals(opt, "0") || StringUtils.equals(opt, "0")) {
			return CommonConstants.WORKFLOW_STATUS_10;
		} else if (StringUtils.equals(opt, "临时保存")) {
			return CommonConstants.WORKFLOW_STATUS_60;
		} else if (StringUtils.equals(opt, "1")) {
			return CommonConstants.WORKFLOW_STATUS_20;
		} else if (StringUtils.equals(opt, "2")) {
			return CommonConstants.WORKFLOW_STATUS_30;
		} else if (StringUtils.equals(opt, "3")) {
			return CommonConstants.WORKFLOW_STATUS_10;
		} else if (StringUtils.equals(opt, "4")) {
			return CommonConstants.WORKFLOW_STATUS_40;
		} else if (StringUtils.equals(opt, "5")) {
			return CommonConstants.WORKFLOW_STATUS_70;
		} else {
			return "";

		}
	}

}
