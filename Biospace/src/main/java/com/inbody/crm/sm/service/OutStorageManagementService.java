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

import com.google.common.collect.Maps;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.sm.dao.OutStorageManagementDao;
import com.inbody.crm.sm.dao.SmStorageInfoManagementDao;
import com.inbody.crm.sm.dao.SmWarehouseInfoManagementDao;
import com.inbody.crm.sm.entity.OutStorageManagement;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;
import com.inbody.crm.sm.entity.SmWarehouseInfoManagement;

@Service
@Transactional(readOnly = true)
public class OutStorageManagementService {

	@Autowired
	private OutStorageManagementDao outStorageManagementDao;

	@Autowired
	private SmStorageInfoManagementDao smStorageInfoManagementDao;

	@Autowired
	private SmWarehouseInfoManagementDao smWarehouseInfoManagementDao;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private CommonService commonService;

	public List<Map<String, String>> loadWarehouseList(String qureyMaterialNo) {
		try {
			return outStorageManagementDao.loadWarehouseList(qureyMaterialNo);
		} catch (Exception e) {

			Map<String, String> map = new HashMap<String, String>();
			map.put("erro", "添加失败咯" + e);
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			ls.add(map);
			return ls;
		}

	}

	@Transactional(readOnly = false)
	public void deleteDtlAppId(String id) {
		try {
			smStorageInfoManagementDao.deleteDtlAppId(id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional(readOnly = false)
	public void deleteDtlId(String id) {
		try {
			smStorageInfoManagementDao.deleteDtlId(id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Map<String, String>> selectByAppId(String id) {
		try {
			return smStorageInfoManagementDao.selectById(id);
		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("erro", "添加失败咯" + e);
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			ls.add(map);
			return ls;
		}

	}

	public List<Map<String, String>> smStorageApp(String id) {
		try {
			return outStorageManagementDao.smStorageApp(id);
		} catch (Exception e) {

			Map<String, String> map = new HashMap<String, String>();
			map.put("erro", "添加失败咯" + e);
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			ls.add(map);
			return ls;
		}
	}

	public List<Map<String, String>> smStorageAppDtl(String id) {
		try {
			return outStorageManagementDao.smStorageAppDtl(id);
		} catch (Exception e) {

			Map<String, String> map = new HashMap<String, String>();
			map.put("erro", "添加失败咯" + e);
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			ls.add(map);
			return ls;
		}
	}

	@Transactional(readOnly = false)
	public void addByOutStorageManagement(
			OutStorageManagement outStorageManagement) {
		try {
			outStorageManagementDao
					.addByOutStorageManagement(outStorageManagement);
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}

	@Transactional(readOnly = false)
	public void addBySmStorageApp(OutStorageManagement outStorageManagement) {
		try {
			outStorageManagementDao.addBySmStorageApp(outStorageManagement);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional(readOnly = false)
	public void addProcess(String id) {
		String procInsId = actTaskService.startProcess(ActUtils.SM_STORAGE[0],
				ActUtils.SM_STORAGE[1], id, CommonConstants.OUT_STORAGE_APPLY);
		// 完成第一个任务，进入申请中状态
		actTaskService.completeFirstTask(procInsId);
	}

	@Transactional(readOnly = false)
	public void update(OutStorageManagement outStorageManagement) {
		try {
			outStorageManagementDao.update(outStorageManagement);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional(readOnly = false)
	public void updateStorageApp(OutStorageManagement outStorageManagement) {
		try {
			SmStorageInfoManagement smStorageInfoManagement = new SmStorageInfoManagement();
			smStorageInfoManagement.setId(outStorageManagement.getId());
			smStorageInfoManagement.setNewRemarks(outStorageManagement
					.getNewRemarks());
			smStorageInfoManagement.setCustomerName(outStorageManagement
					.getCustomerId());
			smStorageInfoManagement.setLendingType(outStorageManagement
					.getLendingType());
			smStorageInfoManagement.setIndustry(outStorageManagement
					.getIndustry());
			smStorageInfoManagement.setLendingDateFrom(outStorageManagement
					.getLendingDateFrom());
			smStorageInfoManagement.preUpdate();
			smStorageInfoManagementDao
					.updateSmStorageAppById(smStorageInfoManagement);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public boolean snNoYseOrNo(OutStorageManagement outStorageManagement) {
		try {
			Integer sn = outStorageManagementDao.selectBySnNo(
					outStorageManagement).size();
			if (sn != 0)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public List<Map<String, String>> selectSnNoBySn(
			OutStorageManagement outStorageManagement) {
		try {

			return outStorageManagementDao.selectBySnNo(outStorageManagement);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Map<String, String>> selectByWid(String Id) {
		try {

			return smWarehouseInfoManagementDao.selectByWid(Id);
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional(readOnly = false)
	public void savaMc(OutStorageManagement osm) {
		osm.getAct().setBusinessId(osm.getId());
		commonService.flowProcess(osm.getAct(), ActUtils.SM_STORAGE,
				CommonConstants.OUT_STORAGE_APPLY, osm.getWorkflowStatus());
		// SmStorageInfoManagement ss = new SmStorageInfoManagement();
		// ss.setId(osm.getId());
		// ss.setWorkflowStatus(osm.getWorkflowStatus());
		// smStorageInfoManagementDao.updateWorkflowStatusById(ss);
	}

	@Transactional(readOnly = false)
	public void updateProcess(OutStorageManagement osm, String but) {
		// actTaskService.claim(osm.getAct().getTaskId(), UserUtils
		// .getUser().getLoginName());

		// osm.getAct().setComment(
		// ("yes".equals(osm.getAct().getFlag()) ? "[重申] "
		// : "[销毁] ") + osm.getAct().getComment());
		// 完成流程任务
		Map<String, Object> vars = Maps.newHashMap();
		// 节点审批通过
		if (but.equals("1")) {
			// 通过审批
			vars.put("pass", but);

			actTaskService.complete(osm.getAct().getTaskId(), osm.getAct()
					.getProcInsId(), osm.getAct().getComment(),
					CommonConstants.OUT_STORAGE_APPLY, vars);

			if (actTaskService.getProcIns(osm.getAct().getProcInsId()) != null) {
				ActUtils.updateWorkflowStatus(ActUtils.SM_STORAGE,
						CommonConstants.WORKFLOW_STATUS_20, osm.getId());
			} else {
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				list = outStorageManagementDao.smStorageAppDtl(osm.getId());
				for (Map<String, String> map : list) {
					SmStorageInfoManagement smStorageInfoManagement = new SmStorageInfoManagement();
					String outStorageId = CommonConstants.STORAGE_TYPE_22
							+ commonService.getNextSequence(
									CommonConstants.STORAGE_TYPE_22,
									CommonConstants.TRANSACTION_NUMBER_STORAGE,
									8);
					smStorageInfoManagement.preInsert();
					smStorageInfoManagement.setStorageId(outStorageId);
					smStorageInfoManagement
							.setStorageType(CommonConstants.STORAGE_TYPE_22);
					smStorageInfoManagement.setStorageApplyId(map.get("ID"));
					smStorageInfoManagementDao
							.insertBySelect(smStorageInfoManagement);
					SmWarehouseInfoManagement smWarehouseInfoManagement = new SmWarehouseInfoManagement();

					smWarehouseInfoManagement.setInStockStatus(String
							.valueOf(CommonConstants.IN_STOCK_STATUS_3));
					smWarehouseInfoManagement.setStorageId(outStorageId);
					smWarehouseInfoManagement.setSnNo(map.get("SN_NO"));
					smWarehouseInfoManagement.setMaterialNo(map
							.get("MATERIAL_NO"));
					smWarehouseInfoManagement.setWarehouse(String.valueOf(map
							.get("WAREHOUSE")));
					smWarehouseInfoManagement.setNum(String.valueOf(map
							.get("NUM")));
					smStorageInfoManagement.preInsert();
					smStorageInfoManagementDao
							.insertLendingBySelectOrder(smStorageInfoManagement);

					updateWarehouse(smWarehouseInfoManagement);
				}

			}

		}
		// 退回审批

		// else if (StringUtils.equals(osm.getWorkflowStatus(),
		// CommonConstants.WORKFLOW_STATUS_40)) {
		// // 任务撤回
		// actTaskService.taskBack(osm.getAct().getProcInsId(), vars);
		// ActUtils.updateWorkflowStatus(ActUtils.SM_STORAGE,
		// CommonConstants.WORKFLOW_STATUS_40, osm.getId());
		// } else if (StringUtils.equals(osm.getWorkflowStatus(),
		// CommonConstants.WORKFLOW_STATUS_10)) {
		// // 再申请
		// // 更新
		// //save(purOrd);
		// actTaskService.complete(osm.getAct().getTaskId(), osm
		// .getAct().getProcInsId(), null, null, null);
		// }
	}

	@Transactional(readOnly = false)
	public void updateWarehouse(
			SmWarehouseInfoManagement smWarehouseInfoManagement) {
		if (smWarehouseInfoManagement.getSnNo() != null
				&& !smWarehouseInfoManagement.getSnNo().equals("")
				&& smWarehouseInfoManagement.getSnNo().length() != 0) {
			// SN存在直接修改库存状态
			smWarehouseInfoManagement.preUpdate();
			smWarehouseInfoManagementDao.updateStock(smWarehouseInfoManagement);
		} else {
			List<Map<String, String>> list = smWarehouseInfoManagementDao
					.selectByWarehouseAndMaterialNo(smWarehouseInfoManagement);
			// 先扣除原物料在添加

			// Integer occuptionNo
			// =Integer.parseInt(list.get(0).get("OCCUPATION_NO"));
			Integer toNum = Integer
					.parseInt(smWarehouseInfoManagement.getNum());
			Integer num = Integer.parseInt(String.valueOf(list.get(0)
					.get("NUM")));
			Integer newNum = num - toNum;
			if (newNum < 0) {
				return;
			}
			smWarehouseInfoManagement.setNum(newNum.toString());
			smWarehouseInfoManagement.setId(list.get(0).get("ID"));
			smWarehouseInfoManagement.preUpdate();
			smWarehouseInfoManagementDao.updateByNum(smWarehouseInfoManagement);
			if (list.size() == 1 && null != list) {
				smWarehouseInfoManagement.setNewId(smWarehouseInfoManagement
						.getId());
				smWarehouseInfoManagement.preInsert();
				smWarehouseInfoManagement.setNum(toNum.toString());
				smWarehouseInfoManagementDao
						.insertBySelect(smWarehouseInfoManagement);
			} else {
				Integer num1 = Integer.parseInt(String.valueOf(list.get(1).get(
						"NUM")))
						+ toNum;
				smWarehouseInfoManagement.setNum(num1.toString());
				smWarehouseInfoManagement.setId(list.get(1).get("ID"));
				smWarehouseInfoManagement.preUpdate();
				smWarehouseInfoManagementDao
						.updateByNum(smWarehouseInfoManagement);
			}
			// 添加一行新的占用物料
		}
	}

	@Transactional(readOnly = false)
	public void add(OutStorageManagement outStorageManagement,
			OutStorageManagement osm, HttpServletRequest request, String but,
			String opt) {

		// 获取表单数据
		String smsId = null;
//		String[] index = request.getParameterValues("index");
//		String[] wId = request.getParameterValues("wId");
//		String[] lendingDateTos = request.getParameterValues("lendingDateTo");
//		String[] contactses = request.getParameterValues("contacts");
//		String[] telephones = request.getParameterValues("telephone");
//		String[] addresses = request.getParameterValues("address");
//		String[] snNos = request.getParameterValues("snNo");
//		String[] toNums = request.getParameterValues("toNum");
//		String[] accessoriesRemarkses = request
//				.getParameterValues("accessoriesRemarks");
		// 生成用户ID
		String comment = request.getParameter("comment");
		if (!StringUtils.isEmptyNull(comment)) {
			osm.getAct().setComment(request.getParameter("comment"));
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		if (!but.equals("0") && !but.equals("4")) {
		if (but.equals("3")) {
			outStorageManagement.getUpdateBy();
			outStorageManagement.setId(osm.getId());
			this.updateStorageApp(outStorageManagement);
		}
//		outStorageManagement.preInsert();

//		outStorageManagement.setDate(df.format(
//				outStorageManagement.getCreateDate()).toString());

		osm.setWorkflowStatus(opt);
		outStorageManagement.setWorkflowStatus(opt);
		// 退回操作
		if (but.equals("2") || but.equals("4") || but.equals("5")) {
			this.savaMc(osm);
			return;
		}
		// 添加操作
		if (but.equals("0")) {
			outStorageManagement.preInsert();

			outStorageManagement.setDate(df.format(
					outStorageManagement.getCreateDate()).toString());
			this.addBySmStorageApp(outStorageManagement);

			smsId = outStorageManagement.getId();
			outStorageManagement.setAppId(smsId);
		}
		if (but.equals("3")) {
			this.deleteDtlAppId(osm.getId());
		}
//		smsId = outStorageManagement.getId();
//		outStorageManagement.setAppId(smsId);
		if (but.equals("3")) {
			outStorageManagement.setAppId(osm.getId());
		}
		// 循环添加用户数据
//		for (int i = 0; i < index.length; i++) {
		for (Map<String, String> maps : outStorageManagement.getSmStorageAppDtl()) {
			// int index1 = -1;
			// index1 = index(index[i]);
//			List<Map<String, String>> osmList = new ArrayList<Map<String, String>>();
//			outStorageManagement.preInsert();
//			String warehouse = "";
//			if (but.equals("0") || but.equals("3")) {
////				osmList = this.selectByWid(index[i]);
//				osmList = this.selectByWid(maps.get("ID"));
//				if (osmList.size() != 0) {
//					outStorageManagement.setMaterialNo(osmList.get(0).get(
//							"MATERIAL_NO"));
//					warehouse += String
//							.valueOf(osmList.get(0).get("WAREHOUSE"));
//				}
//			} else {
				// index1 = index1(index[i]);
//				osmList = this.selectByWid(wId[i]);
//				osmList = this.selectByWid(maps.get("wId"));
//				if (osmList.size() != 0) {
//					outStorageManagement.setMaterialNo(osmList.get(0).get(
//							"MATERIAL_NO"));
//					warehouse = String.valueOf(osmList.get(0).get("WAREHOUSE"));
//				}
//			}
//			outStorageManagement.setWarehouse(warehouse);
//			outStorageManagement.setLendingDateTo(lendingDateTos[i]);
//			outStorageManagement.setContacts(contactses[i]);
//			outStorageManagement.setTelephone(telephones[i]);
//			outStorageManagement.setAddress(addresses[i]);
//			outStorageManagement.setSnNo(snNos[i]);
//			if (toNums != null && toNums.length != 0)
//				outStorageManagement.setNum(toNums[i]);
//			else
//				outStorageManagement.setNum("1");
//			outStorageManagement.setAccessoriesRemarks(accessoriesRemarkses[i]);
			if (but.equals("0") || but.equals("3")) {
				outStorageManagement.setMaterialNo(maps.get("MATERIAL_NO"));
				outStorageManagement.setWarehouse(maps.get("WAREHOUSE"));
				outStorageManagement.setLendingDateTo(maps.get("LENDING_DATE_TO"));
				outStorageManagement.setContacts(maps.get("CONTACTS_NAME"));
				outStorageManagement.setTelephone(maps.get("TELEPHONE"));
				outStorageManagement.setAddress(maps.get("ADDRESS"));
				outStorageManagement.setNum(maps.get("NUM"));
				outStorageManagement.setAccessoriesRemarks(maps.get("ACCESSORIES_REMARKS"));
				// 添加数据
				outStorageManagement.preInsert();
				this.addByOutStorageManagement(outStorageManagement);
			// 添加SN
			} else if (but.equals("1")) {
//				String setid = index[i];
				if (!StringUtils.isEmptyNull(maps.get("SN_NO"))) {

					String setid = maps.get("ID");
					outStorageManagement.setId(setid);
					outStorageManagement.setSnNo(maps.get("SN_NO"));
					outStorageManagement.preUpdate();
					// if(outStorageManagementService.snNoYseOrNo(outStorageManagement))
					this.update(outStorageManagement);
				}
			}
		}

		if (but.equals("3")) {
			osm.setWorkflowStatus(opt);
			this.savaMc(osm);
		} else if (!but.equals("0")) {
			this.updateProcess(osm, but);
		} else if (but.equals("0")) {
			// 启动流程
			this.addProcess(smsId);
		}
	}
	
	public String addValidator(OutStorageManagement outStorageManagement, String but) {
		List<String> message = new ArrayList<String>();

		int number = 1;
		if (but.equals("0") || but.equals("3")) {
			if (outStorageManagement.getSmStorageAppDtl() == null || outStorageManagement.getSmStorageAppDtl().size() == 0) {
				message.add("请至少选择一条库存数据操作！");
			} else {
				String lendingDateFrom = outStorageManagement.getLendingDateFrom();
				for (Map<String, String> maps : outStorageManagement.getSmStorageAppDtl()) {
					String lendingDateTo = maps.get("LENDING_DATE_TO");
					
					if (DateUtils.compareDate(lendingDateFrom, lendingDateTo, "yyyy-MM-dd") > 0) {
						message.add("第" + number + "行的借出到期日早于借出日期！");
					}

					number++;
				}
			}
		}
		
		if (but.equals("1")) {
			Map<String, Integer> snMap = new HashMap<String, Integer>();
			for (Map<String, String> maps : outStorageManagement.getSmStorageAppDtl()) {
				String snNo = maps.get("SN_NO");
				String materialNo = maps.get("MATERIAL_NO");
				String warehouse = maps.get("WAREHOUSE");
				if (!StringUtils.isEmptyNull(snNo)) {

					if (snMap.containsKey(snNo)) {
						message.add("第" + number + "行的S/N（" + snNo + "）与第" + snMap.get(snNo) + "行重复！");
					} else {
						snMap.put(snNo, number);
					}

					OutStorageManagement outStorageManagementSearch = new OutStorageManagement();
					outStorageManagementSearch.setMaterialNo(materialNo);
					outStorageManagementSearch.setWarehouse(warehouse);
					outStorageManagementSearch.setSnNo(snNo);
					if (!this.snNoYseOrNo(outStorageManagementSearch)) {
						message.add("SN（" + snNo + "）校验失败！");
					}
				}
				
				number++;
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
