package com.inbody.crm.sm.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.sm.entity.OutStorageManagement;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;
import com.inbody.crm.sm.entity.SmWarehouseInfoManagement;
import com.inbody.crm.sm.service.MoveStorageManagementService;
import com.inbody.crm.sm.service.OtherStorageManagementService;

@Controller
@RequestMapping(value = "${adminPath}/sm/sm006")
public class MoveStorageManagementController {

	@Autowired
	private OtherStorageManagementService otherStorageManagementService;

	@Autowired
	private MoveStorageManagementService moveStorageManagementService;

//	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	@RequestMapping(value = { "list", "" })
	public ModelAndView list(OutStorageManagement outStorageManagement,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("sd/sm/sm006");
//		SmWarehouseInfoManagement sw = new SmWarehouseInfoManagement();
		outStorageManagement.preInsert();
//		list = otherStorageManagementService.selectAll(sw);
		// List<Map<String,String>> lm = extract(0);
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		mav.addObject("date", dateFormat.format(now));
		mav.addObject("user", outStorageManagement);
//		mav.addObject("list", list);
		return mav;
	}

	/*@RequestMapping(value = "queryByMaterial")
	@ResponseBody
	public List<Map<String, String>> queryByMaterial(String qureyMaterialNo)
			throws UnsupportedEncodingException {
		String str = new String(qureyMaterialNo.getBytes("iso8859-1"), "utf-8");
		List<Map<String, String>> lm = new ArrayList<Map<String, String>>();
		// if(!addYseOrNo(str))
		// return lm;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("materialNo").equals(str))
				lm.add(list.get(i));
		}
		return lm;
	}*/

	/*@RequestMapping(value = "loadByIndex")
	@ResponseBody
	public Map<String, String> loadByIndex(int index) {
		Map<String, String> map = list.get(index);
		return map;
	}*/

	@RequestMapping(value = "Add")
	public @ResponseBody Map<String, Object> Add(SmStorageInfoManagement smStorageInfoManagement,
			HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			
			String message = moveStorageManagementService.addValidator(smStorageInfoManagement);

			if (StringUtils.isEmptyNull(message)) {
				moveStorageManagementService.add(smStorageInfoManagement);

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
//		String[] index = request.getParameterValues("index");
//		String[] num = request.getParameterValues("num");
//		String[] toWarehouse = request.getParameterValues("toWarehouse1");
//		smStorageInfoManagement.preInsert();
//		try {
//			for (int i = 0; i < index.length; i++) {
//				int index1 = index(index[i]);
//				smStorageInfoManagement.setId(index[i]);
//				smStorageInfoManagement.setMaterialNo(list.get(index1).get(
//						"materialNo"));
//				String warehouse = String.valueOf(list.get(index1).get(
//						"warehouse"));
//				smStorageInfoManagement.setWarehouse(warehouse);
//				smStorageInfoManagement.setSnNo(list.get(index1).get("snNo"));
//				String toNum = String.valueOf(list.get(index1).get("num"));
//				smStorageInfoManagement.setNum(num[i]);
//				moveStorageManagementService.outOfStorage(
//						smStorageInfoManagement, toWarehouse[i], toNum);
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}

		return jsonMap;

	}

	@RequestMapping(value = "queryBySnNo")
	@ResponseBody
	public List<Map<String, String>> queryBySnNo(
			SmWarehouseInfoManagement smWarehouseInfoManagement)
			throws UnsupportedEncodingException {
		try {
			return otherStorageManagementService
					.selectAll(smWarehouseInfoManagement);

		} catch (Exception e) {
			return null;
		}
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

//	public List<String> ls = new ArrayList<String>();

//	public boolean addYseOrNo(String qureyMaterialNo) {
//		for (int i = 0; i < ls.size(); i++) {
//			if (ls.get(i).equals(qureyMaterialNo))
//				return false;
//		}
//		ls.add(qureyMaterialNo);
//		return true;
//	}
}
