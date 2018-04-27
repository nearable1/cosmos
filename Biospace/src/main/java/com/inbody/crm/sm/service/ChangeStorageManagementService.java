package com.inbody.crm.sm.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.sm.dao.OutStorageManagementDao;
import com.inbody.crm.sm.dao.SmStorageInfoManagementDao;
import com.inbody.crm.sm.dao.SmWarehouseInfoManagementDao;
import com.inbody.crm.sm.dao.SoOrderDtlManagementDao;
import com.inbody.crm.sm.entity.OutStorageManagement;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;

@Service
@Transactional(readOnly = true)
public class ChangeStorageManagementService {

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private OutStorageManagementDao outStorageManagementDao;

	@Autowired
	private SmStorageInfoManagementDao smStorageInfoManagementDao;

	@Autowired
	private SoOrderDtlManagementDao soOrderDtlManagementDao;

	@Autowired
	private SmWarehouseInfoManagementDao smWarehouseInfoManagementDao;

	@Autowired
	private OutStorageManagementService outStorageManagementService;

	@Autowired
	private CommonService commonService;

	public List<Map<String, String>> selectByOrderId(String orderNo) {
		try {
			return soOrderDtlManagementDao.selectByOrderId(orderNo);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Map<String, String>> smStorageAppDtl(String id) {
		try {
			return soOrderDtlManagementDao.smStorageAppDtl(id);
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
		commonService.flowProcess(osm.getAct(), ActUtils.SM_CHANGE,
				CommonConstants.SCRAP_CHANGE, osm.getWorkflowStatus());

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
				String outStorageId = CommonConstants.STORAGE_TYPE_25
						+ commonService.getNextSequence(
								CommonConstants.STORAGE_TYPE_25,
								CommonConstants.TRANSACTION_NUMBER_STORAGE, 8);
				smStorageInfoManagement.preInsert();
				smStorageInfoManagement.setStorageId(outStorageId);
				smStorageInfoManagement
						.setStorageType(CommonConstants.STORAGE_TYPE_25);
				smStorageInfoManagement.setStorageApplyId(map.get("ID"));
				smStorageInfoManagementDao
						.insertBySelectOrder(smStorageInfoManagement);
				map.put("storageId", outStorageId);
				map.put("name", smStorageInfoManagement.getCreateBy().getId());
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				map.put("data", dateFormat.format(now));
				map.put("inStockStatus", CommonConstants.IN_STOCK_STATUS_2);
				// SmWarehouseInfoManagement smWarehouseInfoManagement = new
				// SmWarehouseInfoManagement();
				// smWarehouseInfoManagement.setStorageId(outStorageId);
				// smWarehouseInfoManagement.setSnNo(map.get("SN_NO"));
				// smWarehouseInfoManagement.setMaterialNo(map.get("MATERIAL_NO"));
				// smWarehouseInfoManagement.setWarehouse(String.valueOf(map.get("WAREHOUSE")));
				// smWarehouseInfoManagement.setNum(String.valueOf(map.get("NUM")));
				// updateWarehouse(smWarehouseInfoManagement);
				updateSmSnNo(map);

			}
		}
	}

	@Transactional(readOnly = false)
	public void addProcess(String id) {
		String procInsId = actTaskService.startProcess(ActUtils.SM_CHANGE[0],
				ActUtils.SM_CHANGE[1], id, CommonConstants.SCRAP_CHANGE);
		// 完成第一个任务，进入申请中状态
		actTaskService.completeFirstTask(procInsId);
	}

	@Transactional(readOnly = false)
	public void updateSmSnNo(Map<String, String> map) {

		smWarehouseInfoManagementDao.updateSnNo(map);
		smWarehouseInfoManagementDao.updateNewSnNo(map);
//		map.put("inStockStatus", "2");
		// SmWarehouseInfoManagement smWarehouseInfoManagement
		smWarehouseInfoManagementDao.updateStockBySnNo(map);
	}

	@Transactional(readOnly = false)
	public void add(OutStorageManagement outStorageManagement,
			OutStorageManagement osm, HttpServletRequest request, String but, String newAdd) {

//		String[] index = request.getParameterValues("index");
//		String[] ckb = request.getParameterValues("ckb");
//		String[] snNo = request.getParameterValues("newSnNo");
//		String[] productionDate = request.getParameterValues("productionDate");
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
		if (but.equals("0")) {
			outStorageManagement
					.setStorageType(CommonConstants.STORAGE_TYPE_25);
			outStorageManagement
					.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
			outStorageManagementService.addBySmStorageApp(outStorageManagement);
		}
		// 出入库详细信息操作

		if (but.equals("1")) {
			// 等于1,添加审批时的SN
			//for (int i = 0; i < index.length; i++) {
			for (Map<String, String> maps : outStorageManagement.getSmStorageAppDtl()) {

//				outStorageManagement.setId(index[i]);
//				outStorageManagement.setLendingDateTo(productionDate[i].trim());
//				outStorageManagement.setNewSnNo(snNo[i]);
				outStorageManagement.setId(maps.get("ID"));
				outStorageManagement.setLendingDateTo(maps.get("LENDING_DATE_TO"));
				outStorageManagement.setNewSnNo(maps.get("NEW_SN_NO"));
				outStorageManagement.getUpdateBy();
				outStorageManagementService.update(outStorageManagement);
			}
		} else if (but.equals("0")) {
			// for (int i = 0; i < ckb.length; i++) {
			for (String index : outStorageManagement.getSelectedList()) {
				int i = Integer.parseInt(index);
				Map<String, String> maps = outStorageManagement.getSmStorageAppDtl().get(i);

				// 等于0，为出库申请 需要添加申请出入库的合同号
//				Integer num = Integer.valueOf(ckb[i]);
//				outStorageManagement.setOrderNo(list.get(num).get("orderNo"));
//				outStorageManagement.setMaterialNo(list.get(num).get(
//						"materialNo"));
//				outStorageManagement.setOrderNo(list.get(num).get("orderNo"));
//				outStorageManagement.setLineNo(String.valueOf(list.get(num)
//						.get("lineNo")));
//				outStorageManagement.setWarehouse(String.valueOf(list.get(num)
//						.get("warehouse")));
				outStorageManagement.setOrderNo(maps.get("ORDER_NO"));
				outStorageManagement.setMaterialNo(maps.get("MATERIAL_NO"));
				outStorageManagement.setLineNo(maps.get("LINE_NO"));
				outStorageManagement.setWarehouse(maps.get("WAREHOUSE"));
//				if (outStorageManagement.getWarehouse().equals("null")
//						|| outStorageManagement.getWarehouse() == ""
//						|| outStorageManagement.getWarehouse() == null) {
//					outStorageManagement.setWarehouse("0");
//				}
//				outStorageManagement.setSnNo(list.get(num).get("snNo"));
				outStorageManagement.setSnNo(maps.get("SN_NO"));
				outStorageManagement.setAppId(tpId);
				// outStorageManagement.setWarehouse("0");
				outStorageManagement.preInsert();
				outStorageManagementService
						.addByOutStorageManagement(outStorageManagement);
			}
		} else if (but.equals("3")) {
			//if (!newAdd.equals("3")) {
			if (StringUtils.equals(newAdd, "0")) {
				outStorageManagementService.deleteDtlAppId(osm.getId());
				//for (int i = 0; i < ckb.length; i++) {
				for (String index : outStorageManagement.getSelectedList()) {
					int i = Integer.parseInt(index);
					Map<String, String> maps = outStorageManagement.getSmStorageAppDtl().get(i);

//					Integer num = Integer.valueOf(ckb[i]);
//					outStorageManagement.setOrderNo(list.get(num)
//							.get("orderNo"));
//					outStorageManagement.setMaterialNo(list.get(num).get(
//							"materialNo"));
//					outStorageManagement.setOrderNo(list.get(num)
//							.get("orderNo"));
//					outStorageManagement.setLineNo(String.valueOf(list.get(num)
//							.get("lineNo")));
//					outStorageManagement.setWarehouse(String.valueOf(list.get(
//							num).get("warehouse")));
//					outStorageManagement.setSnNo(list.get(num).get("snNo"));
					outStorageManagement.setOrderNo(maps.get("ORDER_NO"));
					outStorageManagement.setMaterialNo(maps.get("MATERIAL_NO"));
					outStorageManagement.setLineNo(maps.get("LINE_NO"));
					outStorageManagement.setWarehouse(maps.get("WAREHOUSE"));
					outStorageManagement.setSnNo(maps.get("SN_NO"));
					outStorageManagement.setAppId(osm.getId());
					outStorageManagement.preInsert();
					outStorageManagementService
							.addByOutStorageManagement(outStorageManagement);
				}
			}
		}

		// 启动流程
		osm.getAct().setComment(request.getParameter("comment"));
		if (but.equals("0")) {
			OutStorageManagement m = new OutStorageManagement();
			m.setWorkflowStatus(CommonConstants.WORKFLOW_STATUS_10);
			m.setId(tpId);
			this.addProcess(tpId);
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
			if (outStorageManagement.getSmStorageAppDtl() == null || outStorageManagement.getSmStorageAppDtl().size() == 0) {
				message.add("请至少选择一条库存数据操作！");
			}
		}
		
		if (but.equals("1")) {
			Map<String, Integer> snMap = new HashMap<String, Integer>();
			int index = 1;
			for (Map<String, String> maps : outStorageManagement.getSmStorageAppDtl()) {

				String snNo = maps.get("NEW_SN_NO");
				String materialNo = maps.get("MATERIAL_NO");
				if (!StringUtils.isEmptyNull(snNo)) {

					if (snMap.containsKey(snNo)) {
						message.add("第" + index + "行的S/N（" + snNo + "）与第" + snMap.get(snNo) + "行重复！");
					} else {
						snMap.put(snNo, index);
					}

					OutStorageManagement outStorageManagementSearch = new OutStorageManagement();
					outStorageManagementSearch.setMaterialNo(materialNo);
					outStorageManagementSearch.setSnNo(snNo);
					if (!outStorageManagementService.snNoYseOrNo(outStorageManagementSearch)) {
						message.add("SN（" + snNo + "）已借出或被占用！");
					}
				}
				index++;
			}
		}
		
		StringBuilder sbMessage = new StringBuilder();
		if (message.size() > 0) {
			int index = 1;
			for (String str : message) {
				
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
