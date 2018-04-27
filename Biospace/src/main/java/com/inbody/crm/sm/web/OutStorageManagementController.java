package com.inbody.crm.sm.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.entity.Role;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.sm.entity.OutStorageManagement;
import com.inbody.crm.sm.service.OutStorageManagementService;

@Controller
@RequestMapping(value = "${adminPath}/sm/sm001")
public class OutStorageManagementController {

	@Autowired
	private OutStorageManagementService outStorageManagementService;

	private OutStorageManagement osm = new OutStorageManagement();

	private List<Map<String, String>> smStorageAppDtl = new ArrayList<Map<String, String>>();

//	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	@Autowired
	private CommonService commonService;

	@RequestMapping(value = { "list", "" })
	public ModelAndView list(OutStorageManagement outStorageManagement,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("sd/sm/sm001");
		mav.addObject("viewStatus",
				commonService.getProcViewStatus(null, null, true));
//		list = outStorageManagementService.loadWarehouseList();
		outStorageManagement.preInsert();
		// List<Role> role = UserUtils.getRoleList();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// List<Map<String,String>> lm = extract(0);
//		mav.addObject("list", list);
		mav.addObject("user", outStorageManagement);
		mav.addObject("date", dateFormat.format(now));
		return mav;
	}

	@RequestMapping(value = "queryByMaterial")
	@ResponseBody
	public List<Map<String, String>> queryByMaterial(String qureyMaterialNo)
			throws UnsupportedEncodingException {
		try {
			return outStorageManagementService.loadWarehouseList(qureyMaterialNo);

		} catch (Exception e) {
			return null;
		}
//		String str = new String(qureyMaterialNo.getBytes("iso8859-1"), "utf-8");
//		List<Map<String, String>> lm = new ArrayList<Map<String, String>>();
//		// if(!addYseOrNo(qureyMaterialNo))
//		// return lm;
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).get("material").equals(str))
//				lm.add(list.get(i));
//		}
//		return lm;
	}

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
		// //获取表单数据
		// String smsId;
		// String [] index = request.getParameterValues("index");
		// String [] wId = request.getParameterValues("wId");
		// String [] lendingDateTos =
		// request.getParameterValues("lendingDateTo");
		// String [] contactses = request.getParameterValues("contacts");
		// String [] telephones = request.getParameterValues("telephone");
		// String [] addresses = request.getParameterValues("address");
		// String [] snNos = request.getParameterValues("snNo");
		// String [] toNums = request.getParameterValues("toNum");
		// String [] accessoriesRemarkses =
		// request.getParameterValues("accessoriesRemarks");
		// //生成用户ID
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// if(!but.equals("0")&&!but.equals("4")) {
		// outStorageManagement.getUpdateBy();
		// outStorageManagement.setId(osm.getId());
		// outStorageManagementService.updateStorageApp(outStorageManagement);
		// }
		// outStorageManagement.preInsert();
		//
		// outStorageManagement.setDate(df.format(outStorageManagement.getCreateDate()).toString());
		try {
			
			String message = outStorageManagementService.addValidator(outStorageManagement, but);

			if (StringUtils.isEmptyNull(message)) {

				String opt = getWorkflowStatusByOpt(but);
				outStorageManagementService.add(outStorageManagement, osm, request, but, opt);

				jsonMap.put("success", true);
				jsonMap.put("message", null);
			} else {
				jsonMap.put("success", false);
				jsonMap.put("message", message);
			}
			// osm.setWorkflowStatus(getWorkflowStatusByOpt(but));
			// outStorageManagement.setWorkflowStatus(getWorkflowStatusByOpt(but));
			// //退回操作
			// if(but.equals("2")||but.equals("4")||but.equals("5"))
			// {
			// outStorageManagementService.savaMc(osm);
			// return null;
			// }
			// //添加操作
			// if(but.equals("0")) {
			// outStorageManagementService.addBySmStorageApp(outStorageManagement);
			// }
			// if(but.equals("3"))
			// {
			// outStorageManagementService.deleteDtlAppId(osm.getId());
			// }
			// smsId= outStorageManagement.getId();
			// outStorageManagement.setAppId(smsId);
			// if(but.equals("3"))
			// {
			// outStorageManagement.setAppId(osm.getId());
			// }
			// //循环添加用户数据
			// for(int i =0;i<index.length;i++)
			// {
			// //int index1 = -1;
			// //index1 = index(index[i]);
			// List<Map<String,String>> osmList = new
			// ArrayList<Map<String,String>>();
			// outStorageManagement.preInsert();
			// String warehouse="" ;
			// if(but.equals("0")||but.equals("3")) {
			// osmList = outStorageManagementService.selectByWid(index[i]);
			// if(osmList.size()!=0) {
			// outStorageManagement.setMaterialNo(osmList.get(0).get("MATERIAL_NO"));
			// warehouse +=String.valueOf(osmList.get(0).get("WAREHOUSE"));
			// }
			// }else{
			// //index1 = index1(index[i]);
			// osmList = outStorageManagementService.selectByWid(wId[i]);
			// if(osmList.size()!=0) {
			// outStorageManagement.setMaterialNo(osmList.get(0).get("MATERIAL_NO"));
			// warehouse = String.valueOf(osmList.get(0).get("WAREHOUSE"));
			// }
			// }
			// outStorageManagement.setWarehouse(warehouse);
			// outStorageManagement.setLendingDateTo(lendingDateTos[i]);
			// outStorageManagement.setContacts(contactses[i]);
			// outStorageManagement.setTelephone(telephones[i]);
			// outStorageManagement.setAddress(addresses[i]);
			// outStorageManagement.setSnNo(snNos[i]);
			// if(toNums!=null&&toNums.length!=0)
			// outStorageManagement.setNum(toNums[i]);
			// else
			// outStorageManagement.setNum("1");
			// outStorageManagement.setAccessoriesRemarks(accessoriesRemarkses[i]);
			// if(but.equals("0")||but.equals("3"))
			// //添加数据
			// outStorageManagementService.addByOutStorageManagement(outStorageManagement);
			// //添加SN
			// else if(but.equals("1")){
			// String setid = index[i];
			// outStorageManagement.setId(setid);
			// //if(outStorageManagementService.snNoYseOrNo(outStorageManagement))
			// outStorageManagementService.update(outStorageManagement);
			// }
			// }
		} catch (Exception e) {
			jsonMap.put("success", false);
			jsonMap.put("message", e.getMessage());
		}
		// if(but.equals("3"))
		// {
		// osm.setWorkflowStatus(getWorkflowStatusByOpt(but));
		// outStorageManagementService.savaMc(osm);
		// }
		// else if(!but.equals("0"))
		// {
		// osm.getAct().setComment(request.getParameter("comment"));
		// outStorageManagementService.updateProcess(osm, but);
		// return null;
		// }else if(but.equals("0")) {
		// // 启动流程
		// outStorageManagementService.addProcess(smsId);
		// return null;
		// }
		return jsonMap;

	}

	@RequestMapping(value = "eaa")
	public ModelAndView eaa(OutStorageManagement outStorageManagement,
			HttpServletRequest request, HttpServletResponse response) {
		osm = SerializationUtils.clone(outStorageManagement);
		ModelAndView mav = new ModelAndView("sd/sm/sm001");
		mav.addObject("viewStatus", commonService.getProcViewStatus(
				outStorageManagement.getAct(), outStorageManagement.getId(),
				true));
//		list = outStorageManagementService.loadWarehouseList();
		// List<Map<String,String>> lm = extract(0);
		List<Map<String, String>> smStorageApp = outStorageManagementService
				.smStorageApp(outStorageManagement.getId());
		smStorageAppDtl = outStorageManagementService
				.smStorageAppDtl(outStorageManagement.getId());
		outStorageManagement.setWorkflowStatus(String.valueOf(smStorageApp.get(
				0).get("WORKFLOW_STATUS")));
		// Map<String,Boolean> viewStatus = getViewStatus(outStorageManagement);
		try {
			mav.addObject(
					"charge",
					outStorageManagementService.selectByAppId(osm.getId()).get(
							0));
		} catch (Exception e) {
			mav.addObject("charge", null);
		}
		outStorageManagement.preInsert();
		boolean endApprover = false;
		for (Role role : outStorageManagement.getCreateBy().getRoleList()) {
//			if (role.getEnname().equals("crm-16")) {
			if (role.getEnname().equals("crm_28")) {
				endApprover = true;
			}
		}
		mav.addObject("endApprover", endApprover);

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
		mav.addObject("date", outStorageManagement.getDate());
		mav.addObject("user1", outStorageManagement);
		mav.addObject("smStorageApp", smStorageApp);
//		mav.addObject("smStorageAppDtl", smStorageAppDtl);
		OutStorageManagement smStorageAppDtls = new OutStorageManagement();
		smStorageAppDtls.setSmStorageAppDtl(smStorageAppDtl);
		mav.addObject("contentForm", smStorageAppDtls);
		return mav;

	}

//	@RequestMapping(value = "snNoYseOrNo")
//	@ResponseBody
//	public boolean snNoYseOrNo(String but, HttpServletRequest request,
//			HttpServletResponse response) {
//		if (but.equals("0") || but.equals("3")) {
//			return true;
//		}
//		// String [] index = request.getParameterValues("index");
//		String[] snNos = request.getParameterValues("snNo");
//		for (int i = 0; i < snNos.length; i++) {
//
//			// int index1 = index1(index[i]);
//			OutStorageManagement outStorageManagement = new OutStorageManagement();
//			outStorageManagement.setMaterialNo(smStorageAppDtl.get(i).get(
//					"MATERIAL_NO"));
//			outStorageManagement.setWarehouse(String.valueOf(smStorageAppDtl
//					.get(i).get("WAREHOUSE")));
//			outStorageManagement.setSnNo(snNos[i]);
//			if (!outStorageManagementService.snNoYseOrNo(outStorageManagement)
//					&& snNos[i] != "" && snNos[i] != null) {
//				return false;
//			}
//
//		}
//
//		return true;
//	}

	@RequestMapping(value = "selectSnNoBySn")
	@ResponseBody
	public Map<String, String> selectSnNoBySn(String snNo,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> str = new HashMap<String, String>();
		OutStorageManagement outStorageManagement = new OutStorageManagement();
		outStorageManagement.setSnNo(snNo);
		List<Map<String, String>> ls = outStorageManagementService
				.selectSnNoBySn(outStorageManagement);
		if (ls.size() != 0) {
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
			str.put("str", myFmt.format(ls.get(0).get("PRODUCTION_DATE")));
			str.put("materialNo", ls.get(0).get("MATERIAL_NO"));
		}

		return str;
	}

//	public List<Map<String, String>> extract(int num) {
//		num = 100;
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

//	public List<String> ls = new ArrayList<String>();

//	public boolean addYseOrNo(String qureyMaterialNo) {
//		for (int i = 0; i < ls.size(); i++) {
//			if (ls.get(i).equals(qureyMaterialNo))
//				return false;
//		}
//		ls.add(qureyMaterialNo);
//		return true;
//	}

//	public int index(String index) {
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).get("id").equalsIgnoreCase(index))
//				return i;
//		}
//		return -1;
//	}

//	public int index1(String index) {
//
//		for (int i = 0; i < smStorageAppDtl.size(); i++) {
//			String getid = smStorageAppDtl.get(i).get("wId");
//			if (getid.equals(index))
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
