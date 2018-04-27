package com.inbody.crm.sm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.BeanUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.sm.dao.SmStorageInfoManagementDao;
import com.inbody.crm.sm.dao.SmWarehouseInfoManagementDao;
import com.inbody.crm.sm.entity.SmStorageInfoManagement;
import com.inbody.crm.sm.entity.SmWarehouseInfoManagement;

@Service
@Transactional(readOnly = true)
public class MoveStorageManagementService {

	@Autowired
	private SmStorageInfoManagementDao smStorageInfoManagementDao;
	@Autowired
	private CommonService commonService;
	@Autowired
	private SmWarehouseInfoManagementDao smWarehouseInfoManagementDao;

	@Transactional(readOnly = false)
	public void outOfStorage(SmStorageInfoManagement smStorageInfoManagement,
			String warehouse, String num) {
		try {
			String id = smStorageInfoManagement.getId();
			String outStorageId = CommonConstants.STORAGE_TYPE_23
					+ commonService.getNextSequence(
							CommonConstants.STORAGE_TYPE_23,
							CommonConstants.TRANSACTION_NUMBER_STORAGE, 8);
			smStorageInfoManagement.preInsert();
			smStorageInfoManagement
					.setStorageType(CommonConstants.STORAGE_TYPE_23);
			smStorageInfoManagement.setAccessoriesRemarks("移库出库");
			smStorageInfoManagement.setStorageId(outStorageId);
			smStorageInfoManagementDao.insert(smStorageInfoManagement);
			// smStorageInfoManagementDao.update(smStorageInfoManagement);

			smStorageInfoManagement.preInsert();
			smStorageInfoManagement.setWarehouse(warehouse);
			String storageId = CommonConstants.STORAGE_TYPE_15
					+ commonService.getNextSequence(
							CommonConstants.STORAGE_TYPE_15,
							CommonConstants.TRANSACTION_NUMBER_STORAGE, 8);
			smStorageInfoManagement
					.setStorageType(CommonConstants.STORAGE_TYPE_15);
			smStorageInfoManagement.setAccessoriesRemarks("移库入库");
			smStorageInfoManagement.setStorageId(storageId);
			smStorageInfoManagementDao.insert(smStorageInfoManagement);
			smStorageInfoManagement.setId(id);
			updateSwInfo(smStorageInfoManagement, warehouse, num);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Transactional(readOnly = false)
	public void updateSwInfo(SmStorageInfoManagement smStorageInfoManagement,
			String warehouse, String num) {
		SmWarehouseInfoManagement sim = new SmWarehouseInfoManagement();
		List<Map<String, String>> ls;
		// 是否存在SN，存在直接修改
		if (smStorageInfoManagement.getSnNo() != null
				&& !smStorageInfoManagement.getSnNo().equals("")
				&& smStorageInfoManagement.getSnNo().length() != 0) {

			sim.preUpdate();
			sim.setSnNo(smStorageInfoManagement.getSnNo());
			sim.setId(smStorageInfoManagement.getId());
			sim.setWarehouse(warehouse);
			sim.setStorageId(smStorageInfoManagement.getStorageId());
			smWarehouseInfoManagementDao.updateBySn(sim);
			smWarehouseInfoManagementDao.updateSnBySn(sim);
		} else {
			try {
				// 修改原物料数量
				sim.preUpdate();
				sim.setId(smStorageInfoManagement.getId());
				Integer outNum = Integer.parseInt(num)
						- Integer.parseInt(smStorageInfoManagement.getNum());
				sim.setNum(outNum.toString());
				sim.setStorageId(smStorageInfoManagement.getStorageId());
				smWarehouseInfoManagementDao.update(sim);
				// 查询是否在库
				SmWarehouseInfoManagement wm = new SmWarehouseInfoManagement();
				wm.setWarehouse(warehouse);
				wm.setMaterialNo(smStorageInfoManagement.getMaterialNo());
				sim.setWarehouse(warehouse);
				sim.setMaterialNo(smStorageInfoManagement.getMaterialNo());
				ls = smWarehouseInfoManagementDao.selectByWarehouse(wm);
				// 存在直接修改、不存在添加
				if (ls.size() != 0 && null != ls) {
					sim.setStorageId(smStorageInfoManagement.getStorageId());
					SmWarehouseInfoManagement smWarehouseInfoManagement = new SmWarehouseInfoManagement();
					BeanUtils.populate(smWarehouseInfoManagement, ls.get(0));

					Integer numm = Integer.parseInt(String
							.valueOf(smWarehouseInfoManagement.getNum()))
							+ Integer.parseInt(String
									.valueOf(smStorageInfoManagement.getNum()));
					smWarehouseInfoManagement.setNum(numm.toString());
					sim.setId(ls.get(0).get("ID"));
					smWarehouseInfoManagement.preUpdate();
					smWarehouseInfoManagementDao
							.update(smWarehouseInfoManagement);
				} else {
					// BeanUtils.populate(sim,ls.get(0));
					sim.preInsert();
					sim.setInStockStatus("1");
					sim.setNum(smStorageInfoManagement.getNum());
					sim.setWarehouse(warehouse);
					sim.setStorageId(smStorageInfoManagement.getStorageId());
					sim.setOccupationNo("0");
					smWarehouseInfoManagementDao.insert(sim);
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
	}

	@Transactional(readOnly = false)
	public void add(SmStorageInfoManagement smStorageInfoManagement) {

//		String[] index = request.getParameterValues("index");
//		String[] num = request.getParameterValues("num");
//		String[] toWarehouse = request.getParameterValues("toWarehouse1");
		smStorageInfoManagement.preInsert();

		for (Map<String, String> maps : smStorageInfoManagement.getSmStorageAppDtl()) {
//		for (int i = 0; i < index.length; i++) {
//			int index1 = index(index[i], list);
//			smStorageInfoManagement.setId(index[i]);
//			smStorageInfoManagement.setMaterialNo(list.get(index1).get(
//					"materialNo"));
//			String warehouse = String
//					.valueOf(list.get(index1).get("warehouse"));
//			smStorageInfoManagement.setWarehouse(warehouse);
//			smStorageInfoManagement.setSnNo(list.get(index1).get("snNo"));
//			String toNum = String.valueOf(list.get(index1).get("num"));
//			smStorageInfoManagement.setNum(num[i]);
//			this.outOfStorage(smStorageInfoManagement,
//					toWarehouse[i], toNum);
			smStorageInfoManagement.setId(maps.get("ID"));
			smStorageInfoManagement.setMaterialNo(maps.get("MATERIAL_NO"));
			String warehouse = String
					.valueOf(maps.get("WAREHOUSE"));
			smStorageInfoManagement.setWarehouse(warehouse);
			smStorageInfoManagement.setSnNo(maps.get("SN_NO"));
			String toNum = String.valueOf(maps.get("NUM"));
			smStorageInfoManagement.setNum(maps.get("TO_NUM"));
			this.outOfStorage(smStorageInfoManagement,
					maps.get("TO_WAREHOUSE"), toNum);
		}
	}
	
	public String addValidator(SmStorageInfoManagement smStorageInfoManagement) {
		List<String> message = new ArrayList<String>();

		if (smStorageInfoManagement.getSmStorageAppDtl() == null || smStorageInfoManagement.getSmStorageAppDtl().size() == 0) {
			message.add("请至少选择一条库存数据操作！");
		}

		int number = 1;
		for (Map<String, String> maps : smStorageInfoManagement.getSmStorageAppDtl()) {

			String toWarehouse = maps.get("TO_WAREHOUSE");
			String warehouse = maps.get("WAREHOUSE");
			if (StringUtils.equals(toWarehouse, warehouse)) {
				message.add("第" + number + "行的当前库房与移动后库房相同！");
			}
			
			number++;
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

//	public int index(String index, List<Map<String, String>> list) {
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).get("id").equals(index))
//				return i;
//		}
//		return -1;
//	}
}
