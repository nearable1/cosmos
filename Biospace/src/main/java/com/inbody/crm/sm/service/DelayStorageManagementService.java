package com.inbody.crm.sm.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.sm.dao.OutStorageManagementDao;
import com.inbody.crm.sm.dao.SmStorageInfoManagementDao;
import com.inbody.crm.sm.dao.SmWarehouseInfoManagementDao;
import com.inbody.crm.sm.entity.OutStorageManagement;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;

@Service
@Transactional(readOnly = true)
public class DelayStorageManagementService {

	@Autowired
	private SmWarehouseInfoManagementDao smWarehouseInfoManagementDao;

	@Autowired
	private SmStorageInfoManagementDao smStorageInfoManagementDao;

	@Autowired
	OutStorageManagementService outStorageManagementService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private OutStorageManagementDao outStorageManagementDao;

	// 查询所有物料号
	public List<Map<String, String>> selectAll(
			SmStorageInfoManagement smStorageInfoManagement) {
		try {
			return smStorageInfoManagementDao
					.selectByResponsiblePersonId(smStorageInfoManagement);
		} catch (Exception e) {

			Map<String, String> map = new HashMap<String, String>();
			map.put("erro", "添加失败咯" + e);
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			ls.add(map);
			return ls;
		}
	}

	public List<Map<String, String>> selectBySsiInfo(String Id) {
		try {
			return smStorageInfoManagementDao.selectBySsiId(Id);
		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("erro", "添加失败咯" + e);
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			ls.add(map);
			return ls;
		}
	}

	public List<Map<String, String>> selectBySsiInfoId(String Id) {
		try {
			return smStorageInfoManagementDao.selectBySsiInfoId(Id);
		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("erro", "添加失败咯" + e);
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			ls.add(map);
			return ls;
		}
	}

	@Transactional(readOnly = false)
	public void addProcess(OutStorageManagement osm) {
		osm.getAct().setBusinessId(osm.getId());
		commonService.flowProcess(osm.getAct(), ActUtils.SM_DELAY,
				CommonConstants.DELAY_STORAGE, osm.getWorkflowStatus());
		List<Map<String, String>> smStorageApp = outStorageManagementDao
				.smStorageApp(osm.getId());
		String yesOrNo = "";
		if (smStorageApp.size() > 0) {
			yesOrNo = smStorageApp.get(0).get("WORKFLOW_STATUS");
		}

		if (yesOrNo.equals("50")) {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			list = outStorageManagementDao.smStorageAppDtl(osm.getId());
			for (Map<String, String> map : list) {
				SmStorageInfoManagement smStorageInfoManagement = new SmStorageInfoManagement();
				// String outStorageId = CommonConstants.STORAGE_TYPE_22+
				// commonService.getNextSequence(CommonConstants.STORAGE_TYPE_22,
				// CommonConstants.TRANSACTION_NUMBER_STORAGE, 8);
				smStorageInfoManagement.preInsert();
				// smStorageInfoManagement.setStorageId(outStorageId);
				smStorageInfoManagement.setLendingDateTo(String.valueOf(map
						.get("EXTEND_DATE_TO")));
				smStorageInfoManagement.setStorageId(map.get("PROC_INS_ID"));
				smStorageInfoManagement.setStorageApplyId(map.get("ID"));
				smStorageInfoManagement.setPurchaseNo(map.get("PURCHASE_NO"));
				smStorageInfoManagementDao
						.insertInfoByInfoId(smStorageInfoManagement);

				smStorageInfoManagementDao
						.updateByStorageId(smStorageInfoManagement);

			}
		}

	}
	
	@Transactional(readOnly = false)
	public void add(OutStorageManagement outStorageManagement,
			OutStorageManagement osm, HttpServletRequest request, String but, String newAdd) {

//		String[] index = request.getParameterValues("index");
//		String[] ckb = request.getParameterValues("ckb");
		// String [] snNo = request.getParameterValues("newSnNo");
//		String[] extendDateTo = request.getParameterValues("extendDateTo");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!but.equals("0") && !but.equals("4") && !but.equals("2")) {
			outStorageManagement.getUpdateBy();
			outStorageManagement.setId(osm.getId());
			outStorageManagementService.updateStorageApp(outStorageManagement);
		}
		// 生成出入库ID
		outStorageManagement.preInsert();
		String tpId = outStorageManagement.getId();
		outStorageManagement.setDate(df.format(
				outStorageManagement.getCreateDate()).toString());
		// 添加出入库申请
		// 出入库详细信息操作
		if (but.equals("0")) {
//			for (int i = 0; i < ckb.length; i++) {
			int i = 0;
			for (String index : outStorageManagement.getSelectedList()) {
				// 等于0，为出库申请 需要添加申请出入库的合同号
//				Integer num = Integer.valueOf(ckb[i]);
				int num = Integer.parseInt(index);
				Map<String, String> smStorageAppDtl = outStorageManagement.getSmStorageAppDtl().get(num);
//				Map<String, String> map = this
//						.selectBySsiInfo(index[num]).get(0);
				Map<String, String> map = this
						.selectBySsiInfo(smStorageAppDtl.get("ID")).get(0);
				if (i == 0) {
					outStorageManagement.setStorageType(map
							.get("STORAGE_TYPE"));
					outStorageManagement
							.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
					outStorageManagement.setLendingType(map
							.get("LENDING_TYPE"));
					outStorageManagement.setCustomerId(map
							.get("CUSTOMER_ID"));
					outStorageManagement.setIndustry(map
							.get("INDUSTRY"));
					outStorageManagement.setLendingDateFrom(String.valueOf(map
							.get("LENDING_DATE_FROM")));
					// outStorageManagement.setNewRemarks(String.valueOf(map.get("NEW_REMARKS")));
					outStorageManagementService
							.addBySmStorageApp(outStorageManagement);
				}
//				outStorageManagement.setPurchaseNo(index[num]);
				outStorageManagement.setPurchaseNo(smStorageAppDtl.get("ID"));
				outStorageManagement.setOrderNo(map
						.get("ORDER_NO"));
				outStorageManagement.setLineNo(String.valueOf(map
						.get("LINE_NO")));
				outStorageManagement.setSnNo(map.get("SN_NO"));
				outStorageManagement.setMaterialNo(map
						.get("MATERIAL_NO"));
				//outStorageManagement.setNum(String.valueOf(map.get("NUM")));
				outStorageManagement.setNum(String.valueOf(smStorageAppDtl.get("NUM")));
				outStorageManagement.setWarehouse(String.valueOf(map
						.get("WAREHOUSE")));
				outStorageManagement.setAddress(map
						.get("ADDRESS"));
				outStorageManagement.setContacts(map
						.get("CONTACTS_NAME"));
				outStorageManagement.setTelephone(map
						.get("TELEPHONE"));
				outStorageManagement.setAccessoriesRemarks(map
						.get("ACCESSORIES_REMARKS"));
				outStorageManagement.setLendingDateTo(String.valueOf(map
						.get("LENDING_DATE_TO")));
//				outStorageManagement.setExtendDateTo(String
//						.valueOf(extendDateTo[num]));
				outStorageManagement.setExtendDateTo(smStorageAppDtl.get("EXTEND_DATE_TO"));
				outStorageManagement.setAppId(tpId);
				// outStorageManagement.setWarehouse("0");
				outStorageManagement.preInsert();
				outStorageManagementService
						.addByOutStorageManagement(outStorageManagement);
				
				i++;
			}
		} else if (but.equals("3")) {

			outStorageManagementService.deleteDtlAppId(osm.getId());
//			for (int i = 0; i < ckb.length; i++) {
			for (String index : outStorageManagement.getSelectedList()) {
				int num = Integer.parseInt(index);
				Map<String, String> smStorageAppDtl = outStorageManagement.getSmStorageAppDtl().get(num);
//				Map<String, String> map = this
//						.selectBySsiInfo(ckb[i]).get(0);
//				outStorageManagement.setPurchaseNo(ckb[i]);
				Map<String, String> map = this.selectBySsiInfo(smStorageAppDtl.get("ID")).get(0);
				outStorageManagement.setPurchaseNo(smStorageAppDtl.get("ID"));
				outStorageManagement.setOrderNo(map
						.get("ORDER_NO"));
				outStorageManagement.setLineNo(String.valueOf(map
						.get("LINE_NO")));
				outStorageManagement.setSnNo(map.get("SN_NO"));
				outStorageManagement.setMaterialNo(map
						.get("MATERIAL_NO"));
//				outStorageManagement.setNum(map.get("NUM"));
				outStorageManagement.setNum(String.valueOf(smStorageAppDtl.get("NUM")));
				outStorageManagement.setWarehouse(String.valueOf(map
						.get("WAREHOUSE")));
				outStorageManagement.setAddress(map
						.get("ADDRESS"));
				outStorageManagement.setContacts(map
						.get("CONTACTS_NAME"));
				outStorageManagement.setTelephone(map
						.get("TELEPHONE"));
				outStorageManagement.setAccessoriesRemarks(map
						.get("ACCESSORIES_REMARKS"));
				outStorageManagement.setLendingDateTo(String.valueOf(map
						.get("LENDING_DATE_TO")));
//				outStorageManagement.setExtendDateTo(String
//						.valueOf(extendDateTo[i]));
				outStorageManagement.setExtendDateTo(smStorageAppDtl.get("EXTEND_DATE_TO"));
				outStorageManagement.setAppId(osm.getId());
				// outStorageManagement.setWarehouse("0");
				outStorageManagement.preInsert();
				outStorageManagementService
						.addByOutStorageManagement(outStorageManagement);
			}

		}

		// 启动流程
		osm.getAct().setComment(request.getParameter("comment"));
		if (but.equals("0")) {
			OutStorageManagement m = new OutStorageManagement();
			m.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
			m.setId(tpId);
			this.addProcess(m);
		}
		if (but.equals("1")) {
			osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_20);
			this.addProcess(osm);
		}
		if (but.equals("2")) {
			osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_30);
			this.addProcess(osm);
		}
		if (but.equals("3")) {
			osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
			this.addProcess(osm);
		}
		if (but.equals("4")) {
			osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_40);
			this.addProcess(osm);
		}
		if (but.equals("5")) {
			osm.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_70);
			this.addProcess(osm);
		}
	}

	
	public String addValidator(OutStorageManagement outStorageManagement, String but) {
		List<String> message = new ArrayList<String>();

		if (but.equals("0") || but.equals("3")) {
			if (outStorageManagement.getSelectedList() == null || outStorageManagement.getSelectedList().size() == 0) {
				message.add("请至少选择一条库存数据操作！");
			}

			for (String index : outStorageManagement.getSelectedList()) {
				int i = Integer.parseInt(index);
				
				Map<String, String> smStorageAppDtl = outStorageManagement.getSmStorageAppDtl().get(i);
				String lendingDateTo = smStorageAppDtl.get("LENDING_DATE_TO");
				String extendDateTo = smStorageAppDtl.get("EXTEND_DATE_TO");
				if (DateUtils.compareDate(extendDateTo, lendingDateTo, "yyyy-MM-dd") < 1) {
					message.add("第" + (i+1) + "行的借出延长日输入错误！");
				}
			}
		}

		StringBuilder sbMessage = new StringBuilder();
		if (message.size() > 0) {
			for (String str : message) {
				int index = 1;
				
				if (index == message.size()) {
					sbMessage.append(str);
				} else {
					sbMessage.append(str).append("<br/>");
				}
				
				index++;
			}
		} else {
			return null;
		}
		
		return sbMessage.toString();
	}
}
