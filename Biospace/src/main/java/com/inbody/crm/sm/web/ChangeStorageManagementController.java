package com.inbody.crm.sm.web;

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

import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.Role;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.sm.entity.OutStorageManagement;
import com.inbody.crm.sm.service.ChangeStorageManagementService;
import com.inbody.crm.sm.service.OutStorageManagementService;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;

//import com.inbody.crm.sm.service.OutStorageManagementService;

@Controller
@RequestMapping(value = "${adminPath}/sm/sm009")
public class ChangeStorageManagementController extends BaseController {

	@Autowired
	private ChangeStorageManagementService changeStorageManagementService;

	@Autowired
	private OutStorageManagementService outStorageManagementService;

//	private List<Map<String, String>> smStorageAppDtl = new ArrayList<Map<String, String>>();

	@Autowired
	private CommonService commonService;

//	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private OutStorageManagement osm = new OutStorageManagement();

//	private String messgeaStr = "换货申请操作成功";

	@RequestMapping(value = { "list", "" })
	public ModelAndView list(OutStorageManagement outStorageManagement,
			String messgae, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("sd/sm/sm009");
		mav.addObject("viewStatus",
				commonService.getProcViewStatus(null, null, true));
		outStorageManagement.preInsert();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		mav.addObject("date", dateFormat.format(now));
		mav.addObject("osm", outStorageManagement);
		// list = outStorageManagementService.loadWarehouseList();
		// List<Map<String,String>> lm = extract(0);
		// mav.addObject("list", lm);
		return mav;
	}

//	@RequestMapping(value = "message")
//	public ModelAndView message(OutStorageManagement outStorageManagement,
//			String messgae, HttpServletRequest request,
//			HttpServletResponse response) {
//		ModelAndView mav = new ModelAndView("sd/sm/sm009");
//		mav.addObject("viewStatus",
//				commonService.getProcViewStatus(null, null, true));
//		outStorageManagement.preInsert();
//		Date now = new Date();
//		boolean bl = true;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		mav.addObject("date", dateFormat.format(now));
//		mav.addObject("osm", outStorageManagement);
//		mav.addObject("message", bl);
//		// list = outStorageManagementService.loadWarehouseList();
//		// List<Map<String,String>> lm = extract(0);
//		// mav.addObject("list", lm);
//		return mav;
//	}

	@RequestMapping(value = "query")
	@ResponseBody
	public List<Map<String, String>> query(String orderNo) {
		List<Map<String, String>> list = changeStorageManagementService.selectByOrderId(String
				.valueOf(orderNo));
		return list;
	}

//	@RequestMapping(value = "fromStr")
//	public String fromStr(Model model) {
//		this.addMessage(model, messgeaStr);
//		return "inbody/sm/sm009";
//	}

//	@RequestMapping(value = "delete")
//	public void delete() {
//		osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_70);
//		changeStorageManagementService.addProcess(osm);
//	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(OutStorageManagement outStorageManagement, String but,
			HttpServletRequest request, HttpServletResponse response,
			String newAdd) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			
			String message = changeStorageManagementService.addValidator(outStorageManagement, but);

			if (StringUtils.isEmptyNull(message)) {

				changeStorageManagementService.add(outStorageManagement, osm, request, but, newAdd);

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
		// String [] index = request.getParameterValues("index");
		// String [] ckb = request.getParameterValues("ckb");
		// String [] snNo = request.getParameterValues("newSnNo");
		// String [] productionDate =
		// request.getParameterValues("productionDate");
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// if(!but.equals("0")&&!but.equals("4")&&!but.equals("2")) {
		// outStorageManagement.getUpdateBy();
		// outStorageManagement.setId(osm.getId());
		// outStorageManagementService.updateStorageApp(outStorageManagement);
		// }
		// //生成出入库ID
		// outStorageManagement.preInsert();
		// String tpId =outStorageManagement.getId();
		// outStorageManagement.setDate(df.format(outStorageManagement.getCreateDate()).toString());
		// //添加出入库申请
		// if(but.equals("0")) {
		// outStorageManagement.setStorageType(CommonConstants.STORAGE_TYPE_25);
		// outStorageManagement.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
		// outStorageManagementService.addBySmStorageApp(outStorageManagement);
		// }
		// //出入库详细信息操作
		//
		// if(but.equals("1"))
		// {
		// //等于1,添加审批时的SN
		// for(int i =0;i<index.length;i++)
		// {
		//
		// outStorageManagement.setId(index[i]);
		// outStorageManagement.setLendingDateTo(productionDate[i].trim());
		// outStorageManagement.setNewSnNo(snNo[i]);
		// outStorageManagement.getUpdateBy();
		// outStorageManagementService.update(outStorageManagement);
		// }
		// }
		// else if(but.equals("0"))
		// {
		// for(int i =0;i<ckb.length;i++)
		// {
		// //等于0，为出库申请 需要添加申请出入库的合同号
		// Integer num = Integer.valueOf(ckb[i]);
		// outStorageManagement.setOrderNo(list.get(num).get("orderNo"));
		// outStorageManagement.setMaterialNo(list.get(num).get("materialNo"));
		// outStorageManagement.setOrderNo(list.get(num).get("orderNo"));
		// outStorageManagement.setLineNo(String.valueOf(list.get(num).get("lineNo")));
		// outStorageManagement.setWarehouse(String.valueOf(list.get(num).get("warehouse")));
		// if(outStorageManagement.getWarehouse().equals("null")||outStorageManagement.getWarehouse()==""||outStorageManagement.getWarehouse()==null)
		// {
		// outStorageManagement.setWarehouse("0");
		// }
		// outStorageManagement.setSnNo(list.get(num).get("snNo"));
		// outStorageManagement.setAppId(tpId);
		// //outStorageManagement.setWarehouse("0");
		// outStorageManagement.preInsert();
		// outStorageManagementService.addByOutStorageManagement(outStorageManagement);
		// }
		// }else if(but.equals("3"))
		// {
		// if(!newAdd.equals("3"))
		// {
		// outStorageManagementService.deleteDtlAppId(osm.getId());
		// for(int i =0;i<ckb.length;i++)
		// {
		// Integer num = Integer.valueOf(ckb[i]);
		// outStorageManagement.setOrderNo(list.get(num).get("orderNo"));
		// outStorageManagement.setMaterialNo(list.get(num).get("materialNo"));
		// outStorageManagement.setOrderNo(list.get(num).get("orderNo"));
		// outStorageManagement.setLineNo(String.valueOf(list.get(num).get("lineNo")));
		// outStorageManagement.setWarehouse(String.valueOf(list.get(num).get("warehouse")));
		// outStorageManagement.setSnNo(list.get(num).get("snNo"));
		// outStorageManagement.setAppId(osm.getId());
		// outStorageManagement.preInsert();
		// outStorageManagementService.addByOutStorageManagement(outStorageManagement);
		// }
		// }
		// }
		//
		//
		// //启动流程
		// osm.getAct().setComment(request.getParameter("comment"));
		// if(but.equals("0"))
		// {
		// OutStorageManagement m = new OutStorageManagement();
		// m.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
		// m.setId(tpId);
		// changeStorageManagementService.addProcess(tpId);
		// }
		//
		// if(but.equals("1"))
		// {
		// osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_20);
		// changeStorageManagementService.addProcess(osm);
		// }
		// if(but.equals("2"))
		// {
		// osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_30);
		// changeStorageManagementService.addProcess(osm);
		// }
		// if(but.equals("3"))
		// {
		// osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
		// changeStorageManagementService.addProcess(osm);
		// }
		// if(but.equals("4"))
		// {
		// osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_40);
		// changeStorageManagementService.addProcess(osm);
		// }
		// if(but.equals("5"))
		// {
		// osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_70);
		// changeStorageManagementService.addProcess(osm);
		// }

	}

	/**
	 * 流程初始化
	 * **/
	@RequestMapping(value = "eaa")
	public ModelAndView eaa(OutStorageManagement outStorageManagement,
			HttpServletRequest request, HttpServletResponse response) {
		osm = SerializationUtils.clone(outStorageManagement);
		ModelAndView mav = new ModelAndView("sd/sm/sm009");

		// 获取审批按钮
		Map<String, Boolean> but = commonService.getProcViewStatus(
				outStorageManagement.getAct(), outStorageManagement.getId(),
				true);
		mav.addObject("viewStatus", but);

		// 获取审批申请主表数据
		List<Map<String, String>> smStorageApp = outStorageManagementService
				.smStorageApp(outStorageManagement.getId());
		// 审批申请详情
		// smStorageAppDtl=outStorageManagementService.smStorageAppDtl(outStorageManagement.getId());
		// 日期格式化
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		outStorageManagement.setDate(myFmt.format(smStorageApp.get(0).get(
				"CREATE_DATE")));
		try {
			// 获取负责人
			smStorageApp.get(0).put(
					"name",
					UserUtils.get(
							smStorageApp.get(0).get("RESPONSIBLE_PERSON_ID"))
							.getName());
			outStorageManagement.setCreateBy(UserUtils.get(smStorageApp.get(0)
					.get("RESPONSIBLE_PERSON_ID")));
		} catch (Exception e) {
		}
		boolean endApprover = false;
		OutStorageManagement ii = new OutStorageManagement();
		ii.preInsert();
		for (Role role : ii.getCreateBy().getRoleList()) {
//			if (role.getEnname().equals("crm-16")) {
			if (role.getEnname().equals("crm_28")) {
				endApprover = true;
			}
		}
		mav.addObject("endApprover", endApprover);
		mav.addObject("date",
				myFmt.format(smStorageApp.get(0).get("CREATE_DATE")));
		mav.addObject("osm", outStorageManagement);
//		smStorageAppDtl = changeStorageManagementService
//				.smStorageAppDtl(outStorageManagement.getId());
		List<Map<String, String>> smStorageAppDtl = changeStorageManagementService
				.smStorageAppDtl(outStorageManagement.getId());
		mav.addObject("smStorageApp", smStorageApp);
		OutStorageManagement smStorageAppDtls = new OutStorageManagement();
		smStorageAppDtls.setSmStorageAppDtl(smStorageAppDtl);
		mav.addObject("outStorageDtl", smStorageAppDtls);
//		mav.addObject("smStorageAppDtl", smStorageAppDtl);
		return mav;
	}

}
