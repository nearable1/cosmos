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
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.sm.dao.OutStorageManagementDao;
import com.inbody.crm.sm.dao.SmStorageInfoManagementDao;
import com.inbody.crm.sm.dao.SmWarehouseInfoManagementDao;
import com.inbody.crm.sm.entity.OutStorageManagement;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;
import com.inbody.crm.sm.entity.SmWarehouseInfoManagement;

@Service
@Transactional(readOnly = true)
public class OtherStorageManagementService {

	@Autowired
	private OutStorageManagementDao outStorageManagementDao;

	@Autowired
	private SmWarehouseInfoManagementDao smWarehouseInfoManagementDao;

	@Autowired
	private SmStorageInfoManagementDao smStorageInfoManagementDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private OutStorageManagementService outStorageManagementService;

	// 查询所有物料号
	public List<Map<String, String>> selectAll(
			SmWarehouseInfoManagement smWarehouseInfoManagement) {
		try {
			return smWarehouseInfoManagementDao
					.selectAll(smWarehouseInfoManagement);
		} catch (Exception e) {

			Map<String, String> map = new HashMap<String, String>();
			map.put("erro", "添加失败咯" + e);
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			ls.add(map);
			return ls;
		}
	}

	// 查询出入库申请
	public List<Map<String, String>> smStorageApp(String id) {
		try {
			return outStorageManagementDao.smStorageApp(id);
		} catch (Exception e) {

			Map<String, String> map = new HashMap<String, String>();
			map.put("erro", "查询失败咯" + e);
			List<Map<String, String>> ls = new ArrayList<Map<String, String>>();
			ls.add(map);
			return ls;
		}
	}

	// 查询出入库明细
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

	// public void addProcess(OutStorageManagement osm)
	// {
	// osm.getAct().setBusinessId(osm.getId());
	// commonService.flowProcess(osm.getAct(), ActUtils.SM_SCRAP,
	// CommonConstants.SCRAP_STORAGE,
	// osm.getWorkflowStatus());
	// }
	//

	public List<Map<String, String>> selectByWid(String Id) {
		try {

			return smWarehouseInfoManagementDao.selectByWid(Id);
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional(readOnly = false)
	public void udpate(OutStorageManagement outStorageManagement) {
		try {
			outStorageManagementDao.update(outStorageManagement);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional(readOnly = false)
	public void addProcess(SmWarehouseInfoManagement osm) {
		osm.getAct().setBusinessId(osm.getId());
		commonService.flowProcess(osm.getAct(), ActUtils.SM_OTHIERT,
				CommonConstants.OTHER_STORAGE, osm.getWorkflowStatus());

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
				String outStorageId = CommonConstants.STORAGE_TYPE_26
						+ commonService.getNextSequence(
								CommonConstants.STORAGE_TYPE_26,
								CommonConstants.TRANSACTION_NUMBER_STORAGE, 8);
				smStorageInfoManagement.preInsert();
				smStorageInfoManagement.setStorageId(outStorageId);
				smStorageInfoManagement
						.setStorageType(CommonConstants.STORAGE_TYPE_26);
				smStorageInfoManagement.setStorageApplyId(map.get("ID"));
				smStorageInfoManagementDao
						.insertBySelect(smStorageInfoManagement);
				SmWarehouseInfoManagement smWarehouseInfoManagement = new SmWarehouseInfoManagement();
				smWarehouseInfoManagement.setStorageId(outStorageId);
				try {
					if (map.get("SN_NO") != "" && map.get("SN_NO") != null) {
						smWarehouseInfoManagement.setSnNo(map.get("SN_NO"));

					} else {
						smWarehouseInfoManagement.setSnNo("");

					}
				} catch (Exception e) {
					smWarehouseInfoManagement.setSnNo("");

				}
				smWarehouseInfoManagement.setStorageId(outStorageId);

				smWarehouseInfoManagement.setInStockStatus(String
						.valueOf(CommonConstants.IN_STOCK_STATUS_2));
				smWarehouseInfoManagement.setMaterialNo(map.get("MATERIAL_NO"));
				smWarehouseInfoManagement.setWarehouse(String.valueOf(map
						.get("WAREHOUSE")));
				smWarehouseInfoManagement
						.setNum(String.valueOf(map.get("NUM")));
				// smStorageInfoManagementDao.insert(smStorageInfoManagement);
				updateWarehouse(smWarehouseInfoManagement);
				// updateWarehouse(smWarehouseInfoManagement);

				// updateSmSnNo(map);
			}
		}

	}

	@Transactional(readOnly = false)
	public void updateWarehouse(
			SmWarehouseInfoManagement smWarehouseInfoManagement) {
		if (!smWarehouseInfoManagement.getSnNo().equals("")
				&& smWarehouseInfoManagement.getSnNo() != null
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
			smWarehouseInfoManagement.preUpdate();
			smWarehouseInfoManagement.setNum(newNum.toString());
			smWarehouseInfoManagement.setId(list.get(0).get("ID"));
			smWarehouseInfoManagementDao.updateByNum(smWarehouseInfoManagement);
			// if(list.size()==1)
			// {
			// smWarehouseInfoManagement.setNewId(smWarehouseInfoManagement.getId());
			// smWarehouseInfoManagement.preInsert();
			// smWarehouseInfoManagementDao.insertBySelect(smWarehouseInfoManagement);
			// }else if(list.size()>1)
			// {
			// Integer num1 =
			// Integer.parseInt(String.valueOf(list.get(1).get("NUM")))+Integer.parseInt(smWarehouseInfoManagement.getNum());
			// smWarehouseInfoManagement.setNum(num1.toString());
			// smWarehouseInfoManagement.setId(list.get(1).get("ID"));
			// smWarehouseInfoManagementDao.updateByNum(smWarehouseInfoManagement);
			// }
			// 添加一行新的占用物料
		}
	}

	@Transactional(readOnly = false)
	public void add(OutStorageManagement outStorageManagement,
			SmWarehouseInfoManagement swim, HttpServletRequest request,
			String but, String opt) {
//		String[] index = request.getParameterValues("index");
//		String[] toNum = request.getParameterValues("addNum");
		String appId = "";
		swim.getAct().setComment(request.getParameter("comment"));
		if (but.equals("5")) {
			swim.setWorkflowStatus(opt);
			this.addProcess(swim);
			return;
		}
		if (but.equals("4") || but.equals("2")) {
			swim.setWorkflowStatus(opt);
			this.addProcess(swim);
			return;
		}
		if (but.equals("3")) {
			outStorageManagementService.deleteDtlAppId(swim.getId());
		}
		// 提交出库申请
		if (but.equals("0")) {
			outStorageManagement.preInsert();
			appId += outStorageManagement.getId();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			outStorageManagement
					.setStorageType(CommonConstants.STORAGE_TYPE_26);
			// outStorageManagement.setLendingType(CommonConstants.STORAGE_TYPE_26);
			outStorageManagement.setWorkflowStatus(opt);
			outStorageManagement.setDate(df.format(
					outStorageManagement.getCreateDate()).toString());
			this.addBySmStorageApp(outStorageManagement);
			outStorageManagement.setAppId(appId);
			swim = new SmWarehouseInfoManagement();
			swim.setId(appId);
		}

		if (!but.equals("0") && !but.equals("4") && !but.equals("2")) {
			outStorageManagement.getUpdateBy();
			outStorageManagement.setId(swim.getId());
			outStorageManagementService.updateStorageApp(outStorageManagement);
		}

//		for (int i = 0; i < index.length; i++) {
		for (Map<String, String> maps : outStorageManagement.getSmStorageAppDtl()) {
			outStorageManagement.preInsert();
//			List<Map<String, String>> osmList = this.selectByWid(index[i]);
			List<Map<String, String>> osmList = this.selectByWid(maps.get("ID"));
			outStorageManagement.setMaterialNo(osmList.get(0)
					.get("MATERIAL_NO"));
			String warehouse = String.valueOf(osmList.get(0).get("WAREHOUSE"));
			outStorageManagement.setWarehouse(warehouse);
			try {
				if (osmList.get(0).get("SN_NO") != null
						&& osmList.get(0).get("SN_NO") != "") {
					outStorageManagement.setSnNo(String.valueOf(osmList.get(0)
							.get("SN_NO")));
				} else {
					outStorageManagement.setSnNo(String.valueOf(""));
				}
			} catch (Exception e) {

			}
//			outStorageManagement.setNum(toNum[i]);
			outStorageManagement.setNum(maps.get("TO_NUM"));
			// 添加出货明细为1修改
			if (but.equals("0")) {
				outStorageManagement.setWorkflowStatus(opt);
				this.addByOutStorageManagement(outStorageManagement);
			} else if (but.equals("1")) {
				// outStorageManagement.setId(index[i]);
				// otherStorageManagementService.addByOutStorageManagement(outStorageManagement);
			} else if (but.equals("3")) {
				outStorageManagement.setAppId(swim.getId());
				outStorageManagement.setWorkflowStatus(opt);
				this.addByOutStorageManagement(outStorageManagement);
			}

		}
		// 不等于0修改流程
		swim.setWorkflowStatus(opt);
		this.addProcess(swim);
	}

	public String addValidator(OutStorageManagement outStorageManagement, String but) {
		List<String> message = new ArrayList<String>();
		
		if (but.equals("0") || but.equals("3")) {
			if (outStorageManagement.getSmStorageAppDtl() == null || outStorageManagement.getSmStorageAppDtl().size() == 0) {
				message.add("请至少选择一条库存数据操作！");
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
