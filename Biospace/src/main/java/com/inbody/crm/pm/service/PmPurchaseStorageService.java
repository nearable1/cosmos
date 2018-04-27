/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.ListUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.pm.dao.PmPurchaseOrdDao;
import com.inbody.crm.pm.dao.PmPurchaseOrdDtlDao;
import com.inbody.crm.pm.dao.SmSnInfoDao;
import com.inbody.crm.pm.dao.SmStorageInfoDao;
import com.inbody.crm.pm.dao.SmWarehouseInfoDao;
import com.inbody.crm.pm.domain.PmPurchaseStorage;
import com.inbody.crm.pm.domain.PmPurchaseStorageExcel;
import com.inbody.crm.pm.entity.PmPurchaseOrd;
import com.inbody.crm.pm.entity.PmPurchaseOrdDtl;
import com.inbody.crm.pm.entity.SmSnInfo;
import com.inbody.crm.pm.entity.SmStorageInfo;
import com.inbody.crm.pm.entity.SmWarehouseInfo;

/**
 * 采购入库Service
 * @author yangj
 * @version 2017-07-24
 */
@Service
public class PmPurchaseStorageService {

    @Autowired
    private PmPurchaseOrdDao pmPurchaseOrdDao;

    @Autowired
    private PmPurchaseOrdDtlDao pmPurchaseOrdDtlDao;

    @Autowired
    private SmStorageInfoDao smStorageInfoDao;

    @Autowired
    private SmWarehouseInfoDao smWarehouseInfoDao;

    @Autowired
    private SmSnInfoDao smSnInfoDao;

    @Autowired
    private CommonService commonService;

    /**
     * 取得需要入库的采购明细信息
     * 
     * @param purchaseNo
     *            采购订单号
     * 
     */
    public PmPurchaseOrd getPurchaseStorageInfo(String purchaseNo) {

		PmPurchaseOrd seachPurOrd = new PmPurchaseOrd();
		seachPurOrd.setPurchaseNo(purchaseNo);
		PmPurchaseOrd pmPurchaseOrd = pmPurchaseOrdDao
				.getStoragePurchaseOrd(seachPurOrd);

		if (pmPurchaseOrd != null) {
			List<PmPurchaseOrdDtl> purStorageList = pmPurchaseOrdDtlDao
					.findStoragePurDtlList(new PmPurchaseOrdDtl(purchaseNo));
			pmPurchaseOrd.setPmPurchaseOrdDtlList(purStorageList);
		}

		return pmPurchaseOrd;
	}

	/**
	 * 保存采购入库信息
	 * 
	 * @param pmPurchaseStorage
	 * 
	 */
	@Transactional(readOnly = false)
	public void saveStorageInfo(String purchaseNo,
			List<PmPurchaseStorage> inputPurStorageList) {

		// 采购订单情报取得
		PmPurchaseOrd dbPurchaseOrd = getPurchaseStorageInfo(purchaseNo);
		if (dbPurchaseOrd == null) {
			throw new ServiceException("保存失败：数据不整合！");
		}

		// 审批未完成时
        if (!StringUtils.equals(dbPurchaseOrd.getWorkflowStatus(),
                CommonConstants.WORKFLOW_STATUS_50)) {
            throw new ServiceException("保存失败：采购订单审批未完成！");
        }

		// 数据完整性checked
		if (ListUtils.size(inputPurStorageList) == 0
				|| ListUtils.size(dbPurchaseOrd.getPmPurchaseOrdDtlList()) == 0) {
			throw new ServiceException("保存失败：数据不整合！");
		}

		// 将db采购明细list转换为map
		Map<String, PmPurchaseOrdDtl> dbPurDtlMap = ListUtils.convertListToMap(
				dbPurchaseOrd.getPmPurchaseOrdDtlList(), "id");

		// 入库信息check
		//int line = 0;
		for (PmPurchaseStorage input : inputPurStorageList) {
			//line = line + 1;
			// 输入库存信息所对应的采购明细信息取得
			PmPurchaseOrdDtl dbPurDtl = dbPurDtlMap.get(input.getId());
			// 画面输入数据与Db数据整合check
			if (dbPurDtl == null) {
				throw new ServiceException("保存失败：数据不整合！");
			}

//			// 本次到货数量、库房、入库日期都为空，忽略本条记录
//			if (input.getArrivalNum() == null
//					&& StringUtils.isEmptyNull(input.getWarehouse())
//					&& input.getEntryDate() == null) {
//				continue;
//			}

			// 输入完整性check
			// 库房或者入库日期不为空，则本次到货数量不能为空
			if (!StringUtils.isEmptyNull(input.getWarehouse())
					|| input.getEntryDate() != null) {
				if (input.getArrivalNum() == null) {
					throw new ServiceException("保存失败：明细行" + dbPurDtl.getLineNo() + "，物料号"
							+ dbPurDtl.getMaterialNo() + "，本次到货数量不能为空！");
				}
			}

			// 本次到货数量check
			// 未到货数量
			int unArrivalNum = dbPurDtl.getNum() - dbPurDtl.getAlArrivalNum();
			if (this.defaultInt(input.getArrivalNum()) > unArrivalNum) {
				throw new ServiceException("保存失败：明细行" + dbPurDtl.getLineNo() + "，物料号"
						+ dbPurDtl.getMaterialNo() + "，本次到货数量不能大于"
						+ unArrivalNum + "!");
			}

			// 入库日期check
			if (input.getEntryDate() != null) {
                if (DateUtils.compareDate(input.getEntryDate(),
                        DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH)) > 0) {
                    throw new ServiceException("保存失败：明细行" + dbPurDtl.getLineNo() + "，物料号"
                            + dbPurDtl.getMaterialNo() + "，入库日期不能晚于当前日期！");
                }
			}
		}

		// 入库信息保存
		for (PmPurchaseStorage input : inputPurStorageList) {
			// 输入库存信息所对应的采购明细信息取得
			PmPurchaseOrdDtl dbPurDtl = dbPurDtlMap.get(input.getId());

			// 已到货数量 = 数量，忽略
			if (this.defaultInt(dbPurDtl.getAlArrivalNum()) == this
					.defaultInt(dbPurDtl.getNum())) {
				continue;
			}

			// 画面.本次到货数量=db.本次到货数量、画面.库房=db.库房、画面.入库日期=db.入库日期，忽略本条记录
			if (this.defaultInt(input.getArrivalNum()) == this
					.defaultInt(dbPurDtl.getArrivalNum())
					&& StringUtils.equals(
							StringUtils.defaultString(input.getWarehouse()),
							StringUtils.defaultString(dbPurDtl.getWarehouse()))
					&& this.equalsDate(input.getEntryDate(),
							dbPurDtl.getEntryDate())) {
				continue;
			}

            // 采购明细表信息更新
            // 采购明细表信息更新排他处理
            if (DateUtils.compareDate(input.getUpdateDate(),
                    dbPurDtl.getUpdateDate()) != 0) {
                throw new ServiceException("保存失败：数据已经被更改，请取得最新数据后再操作。");
            }

			// 本次到货数量
			dbPurDtl.setArrivalNum(this.defaultInt(input.getArrivalNum()));
			// 库房
			if (StringUtils.isEmptyNull(input.getWarehouse())) {
				dbPurDtl.setWarehouse(null);
			} else {
				dbPurDtl.setWarehouse(input.getWarehouse());
			}
			// 入库日期
			dbPurDtl.setEntryDate(input.getEntryDate());

			dbPurDtl.preUpdate();
			pmPurchaseOrdDtlDao.update(dbPurDtl);
		}
	}

	/**
	 * 取得采购入库明细下载excel的数据
	 */
	public List<PmPurchaseStorageExcel> getPurStorageExportData(
			String purchaseNo) {

		PmPurchaseOrd sqlParmaPur = new PmPurchaseOrd();
		sqlParmaPur.setPurchaseNo(purchaseNo);
		PmPurchaseOrd pmPurchaseOrd = pmPurchaseOrdDao
				.getStoragePurchaseOrd(sqlParmaPur);

		if (pmPurchaseOrd != null) {
			List<PmPurchaseOrdDtl> purStorageList = pmPurchaseOrdDtlDao
					.findStoragePurDtlList(new PmPurchaseOrdDtl(purchaseNo));
			pmPurchaseOrd.setPmPurchaseOrdDtlList(purStorageList);
		} else {
			return Lists.newArrayList();
		}

		PmPurchaseStorageExcel storageExcel = null;
		List<PmPurchaseStorageExcel> storageExcelList = Lists.newArrayList();
		for (PmPurchaseOrdDtl purDtl : pmPurchaseOrd.getPmPurchaseOrdDtlList()) {
			// 是否有sn
			if (StringUtils.equals(purDtl.getIfSn(), CommonConstants.YES)) {
				
				if (purDtl.getArrivalNum() == null
						|| purDtl.getArrivalNum() == 0) {
					continue;
				}

				// 有,拆行
				for (int i = 0; i < purDtl.getArrivalNum(); i++) {

					storageExcel = new PmPurchaseStorageExcel();
					storageExcel.setId(purDtl.getId());
					storageExcel.setPurchaseNo(purDtl.getPurchaseNo());
					storageExcel.setPurchaseType(pmPurchaseOrd
							.getPurchaseType());
					storageExcel.setIfFoc(purDtl.getIfFoc());
					storageExcel.setMaterialNo(purDtl.getMaterialNo());
					storageExcel.setMaterialName(purDtl.getMaterialName());

                    // 虚拟sn生成
                    if (StringUtils.equals(purDtl.getIfVirtualSn(),
                            CommonConstants.YES)) {
                        String virtualSn = "ut"
                                + DateUtils.getDate("yyyyMM")
                                + commonService.getNextSequence(
                                        CommonConstants.TRANSACTION_VIRTUAL_SN,
                                        DateUtils.getDate("yyyyMM"), 2);
                        storageExcel.setSn(virtualSn);
                    }

					storageExcel.setProductionDate("");
					storageExcelList.add(storageExcel);
				}
			}
		}

		return storageExcelList;
	}

    /**
     * 导入采购订单入库信息
     * 
     * @param updateDate
     *            采购订单更新日时
     * @param inputStorageList
     *            采购订单入库输入信息
     * @param purchaseNo
     *            采购订单号
     * @param importList
     *            采购入库导入excel信息
     */
	@Transactional(readOnly = false)
    public void confirmPurStorage(Date updateDate, List<PmPurchaseStorage> inputStorageList,
            String purchaseNo, List<PmPurchaseStorageExcel> importList) {

		// 采购订单信息取得
		PmPurchaseOrd pmPurchaseOrd = getPurchaseStorageInfo(purchaseNo);
		if (pmPurchaseOrd == null
				|| StringUtils.isEmptyNull(pmPurchaseOrd.getPurchaseNo())) {
			throw new ServiceException("入库失败：入库采购订单" + purchaseNo + "不存在！");
		}

        // 是否需要上传excel
        boolean isUpload = false;
        for (PmPurchaseOrdDtl purDtl : pmPurchaseOrd.getPmPurchaseOrdDtlList()) {
            if (purDtl.getArrivalNum() > 0) {
                if (StringUtils.equals(purDtl.getIfSn(), CommonConstants.YES)) {
                    isUpload = true;
                    break;
                }
            }
        }

        // 需要上传入库excel时
        if (isUpload) {
            // check导入的excel数据
            checkImportData(purchaseNo, importList);
        }

		// 将导入excel数据按id分组
        Map<String, List<PmPurchaseStorageExcel>> groupImportMap = new HashMap<String, List<PmPurchaseStorageExcel>>();
		for (PmPurchaseStorageExcel excelItem : importList) {
			List<PmPurchaseStorageExcel> storageGroupList = groupImportMap
					.get(excelItem.getId());
			if (storageGroupList == null) {
				storageGroupList = Lists.newArrayList();
			}
			storageGroupList.add(excelItem);
			groupImportMap.put(excelItem.getId(), storageGroupList);
		}
		
		// 导入数据与采购明细数关联check
		checkImportAndPurDtl(groupImportMap, pmPurchaseOrd.getPmPurchaseOrdDtlList());
		
		// 入库信息保存及更新
		for (PmPurchaseOrdDtl purDtl : pmPurchaseOrd.getPmPurchaseOrdDtlList()) {
			// 已到货数量 = 数量，忽略
			if (this.defaultInt(purDtl.getAlArrivalNum()) == this
					.defaultInt(purDtl.getNum())) {
				continue;
			}
			
			// 本次到货数量、库房、入库日期都为空，忽略本条记录
			if (this.defaultInt(purDtl.getArrivalNum()) == 0
					&& StringUtils.isEmptyNull(purDtl.getWarehouse())
					&& purDtl.getEntryDate() == null) {
				continue;
			}
			
			// 本次到货数量为空或0时，报错
			if (purDtl.getArrivalNum() == null || purDtl.getArrivalNum() == 0) {
				throw new ServiceException("入库失败：物料" + purDtl.getMaterialNo()
						+ "的本次到货数量未填写，请填写并保存！");
			}

			// 库房不能为空
			if (StringUtils.isEmptyNull(purDtl.getWarehouse())) {
				throw new ServiceException("入库失败：物料" + purDtl.getMaterialNo()
						+ "的库房未填写，请填写并保存！");
			}

			// 入库日期不能为空
			if (purDtl.getEntryDate() == null) {
				throw new ServiceException("入库失败：物料" + purDtl.getMaterialNo()
						+ "的入库日期未填写，请填写并保存！");
			}

            // 将画面输入采购入库信息明细转换为map
            Map<String, PmPurchaseStorage> inputStorageMap = ListUtils
                    .convertListToMap(inputStorageList, "id");

            // 采购明细信息更新排他处理
            PmPurchaseStorage inputStorage = inputStorageMap.get(purDtl.getId());
            if (inputStorage == null) {
                throw new ServiceException("入库失败：数据不整合！");
            }
            if (DateUtils.compareDate(inputStorage.getUpdateDate(),
                    purDtl.getUpdateDate()) != 0) {
                throw new ServiceException("入库失败：数据已经被更改，请取得最新数据后再操作。");
            }

            // 出入库履历编号取得
            String storageId = CommonConstants.STORAGE_TYPE_11 + commonService
                    .getNextSequence(CommonConstants.TRANSACTION_NUMBER_STORAGE,
                            CommonConstants.STORAGE_TYPE_11, 8);

            // sn入库
            if (StringUtils.equals(purDtl.getIfSn(), CommonConstants.YES)) {

                // 采购明细物料对应的sn信息list
                List<PmPurchaseStorageExcel> strgGroupList = groupImportMap
                        .get(purDtl.getId());

				// 出入库信息、sn管理、库存信息插入
				for (PmPurchaseStorageExcel excelData : strgGroupList) {
					// 出入库信息插入
					SmStorageInfo smStorageInfo = new SmStorageInfo();
					// 出入库履历编号
					smStorageInfo.setStorageId(storageId);
					// 出入库类型
					smStorageInfo.setStorageType(CommonConstants.STORAGE_TYPE_11);
					// 采购订单号
					smStorageInfo.setPurchaseNo(purDtl.getPurchaseNo());
					// sn
					smStorageInfo.setSnNo(excelData.getSn());
					// 物料号
					smStorageInfo.setMaterialNo(purDtl.getMaterialNo());
					// 数量
					smStorageInfo.setNum(1);
					// 库房
					smStorageInfo.setWarehouse(purDtl.getWarehouse());
					// 处理日期
					smStorageInfo.setProcessDate(new Date());
					// 负责人
					smStorageInfo.setResponsiblePersonId(UserUtils.getUser().getId());

					smStorageInfo.preInsert();
					smStorageInfoDao.insert(smStorageInfo);

					// sn管理信息插入
					SmSnInfo smSnInfo = new SmSnInfo();
					// S/N
					smSnInfo.setSnNo(excelData.getSn());
					// 物料号
					smSnInfo.setMaterialNo(purDtl.getMaterialNo());
					// 出入库履历编号
					smSnInfo.setStorageId(storageId);
					// 库房
					smSnInfo.setWarehouse(purDtl.getWarehouse());
					// 生产日期
					smSnInfo.setProductionDate(DateUtils.parseDate(excelData.getProductionDate()));
					// 入库日期
					smSnInfo.setEntryDate(purDtl.getEntryDate());
					// 机器类型
					smSnInfo.setMachineType(pmPurchaseOrd.getPurchaseType());
					// 机器状态
					smSnInfo.setStatus(CommonConstants.MACHINE_STATUS_1);
					// 采购订单号
					smSnInfo.setPurchaseNo(purDtl.getPurchaseNo());
					
					smSnInfo.preInsert();
					smSnInfoDao.insert(smSnInfo);

					// 库存信息插入
					SmWarehouseInfo smWarehouseInfo = new SmWarehouseInfo();
					// 出入库履历编号
					smWarehouseInfo.setStorageId(storageId);
					// S/N
					smWarehouseInfo.setSnNo(excelData.getSn());
					// 物料号
					smWarehouseInfo.setMaterialNo(purDtl.getMaterialNo());
					// 库房
					smWarehouseInfo.setWarehouse(purDtl.getWarehouse());
					// 在库状态
					smWarehouseInfo.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);
					// 数量
					smWarehouseInfo.setNum(1);
                    // 占用数量
                    smWarehouseInfo.setOccupationNo(0);

					smWarehouseInfo.preInsert();
					smWarehouseInfoDao.insert(smWarehouseInfo);
				}
            } else {

                // 出入库信息插入
                SmStorageInfo smStorageInfo = new SmStorageInfo();
                // 出入库履历编号
                smStorageInfo.setStorageId(storageId);
                // 出入库类型
                smStorageInfo.setStorageType(CommonConstants.STORAGE_TYPE_11);
                // 采购订单号
                smStorageInfo.setPurchaseNo(purDtl.getPurchaseNo());
                // 物料号
                smStorageInfo.setMaterialNo(purDtl.getMaterialNo());
                // 数量
                smStorageInfo.setNum(purDtl.getArrivalNum());
                // 库房
                smStorageInfo.setWarehouse(purDtl.getWarehouse());
                // 处理日期
                smStorageInfo.setProcessDate(new Date());
                // 负责人
                smStorageInfo.setResponsiblePersonId(UserUtils.getUser().getId());

                smStorageInfo.preInsert();
                smStorageInfoDao.insert(smStorageInfo);

                // 库存信息更新
                // DB库存信息查询
                SmWarehouseInfo searchWh = new SmWarehouseInfo();
                // 物料号
                searchWh.setMaterialNo(purDtl.getMaterialNo());
                // 库房
                searchWh.setWarehouse(purDtl.getWarehouse());
                // 在库状态在库
                searchWh.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);

                // 库存信息取得
                List<SmWarehouseInfo> dbWarehouseInfoList = smWarehouseInfoDao
                        .getNonSnStockInfo(searchWh);

                // 非sn库存信息整合性check
                if (ListUtils.size(dbWarehouseInfoList) > 1) {
                    throw new ServiceException("入库失败：DB数据不整合，非S/N物料"
                            + purDtl.getMaterialNo() + "，对应的库存信息记录不唯一！");
                }

                // 库存信息不存在，insert库存信息
                if (ListUtils.size(dbWarehouseInfoList) == 0) {
                    // 库存信息插入
                    SmWarehouseInfo smWarehouseInfo = new SmWarehouseInfo();
                    // 出入库履历编号
                    smWarehouseInfo.setStorageId(storageId);
                    // 物料号
                    smWarehouseInfo.setMaterialNo(purDtl.getMaterialNo());
                    // 库房
                    smWarehouseInfo.setWarehouse(purDtl.getWarehouse());
                    // 在库状态
                    smWarehouseInfo.setInStockStatus(CommonConstants.IN_STOCK_STATUS_1);
                    // 数量
                    smWarehouseInfo.setNum(purDtl.getArrivalNum());
                    // 占用数量
                    smWarehouseInfo.setOccupationNo(0);

                    smWarehouseInfo.preInsert();
                    smWarehouseInfoDao.insert(smWarehouseInfo);

                } else {
                    // 库存信息存在，更新库存信息
                    SmWarehouseInfo dbWarehouseInfo = dbWarehouseInfoList.get(0);
                    // 出入库履历编号
                    dbWarehouseInfo.setStorageId(storageId);
                    // 数量
                    dbWarehouseInfo.setNum(defaultInt(dbWarehouseInfo.getNum())
                            + defaultInt(purDtl.getArrivalNum()));

                    dbWarehouseInfo.preUpdate();
                    smWarehouseInfoDao.update(dbWarehouseInfo);
                }
            }

			// 采购明细信息更新
			// 已到货数量
			purDtl.setAlArrivalNum(purDtl.getAlArrivalNum() + purDtl.getArrivalNum());
			// 本次到货数量
			purDtl.setArrivalNum(0);
			// 库房
			purDtl.setWarehouse(null);
			// 入库日期
			purDtl.setEntryDate(null);

			purDtl.preUpdate();
			pmPurchaseOrdDtlDao.update(purDtl);
		}

        // 采购订单的到货状态更新
        // 采购总数量
        int totalNum = 0;
        // 总的已到货数
        int totalAlArrNum = 0;
        // 采购总数和总的已到货数量计算
        for (PmPurchaseOrdDtl purDtl : pmPurchaseOrd.getPmPurchaseOrdDtlList()) {
            totalNum = totalNum + purDtl.getNum();
            totalAlArrNum = totalAlArrNum + purDtl.getAlArrivalNum();
        }

        String arrivalStatus = CommonConstants.ARRIVAL_STATUS_2;
        // 总数量等于已到货总数，采购订单到货状态为已到货
        if (totalNum == totalAlArrNum) {
            arrivalStatus = CommonConstants.ARRIVAL_STATUS_2;

            // 已到货总数小于采购总数 且大于0，采购订单到货状态为部分到货
        } else if (totalAlArrNum < totalNum && totalAlArrNum > 0) {
            arrivalStatus = CommonConstants.ARRIVAL_STATUS_1;

            // 否则，未到货
        } else {
            arrivalStatus = CommonConstants.ARRIVAL_STATUS_0;
        }

        // 到货状态变更时，更新采购订单到化状态
        if (!StringUtils.equals(arrivalStatus, pmPurchaseOrd.getArrivalStatus())) {
            // 采购订单表更新排他处理
            if (DateUtils.compareDate(updateDate, pmPurchaseOrd.getUpdateDate()) != 0) {
                throw new ServiceException("入库失败：数据已经被更改，请取得最新数据后再操作。");
            }

            pmPurchaseOrd.setArrivalStatus(arrivalStatus);
            pmPurchaseOrd.preUpdate();

            pmPurchaseOrdDao.update(pmPurchaseOrd);
        }
    }

	/**
	 * check导入的excel数据
	 * 
	 * @param purchaseNo
	 *            采购订单号
	 * @param importList
	 *            导入excel数据
	 */
	public void checkImportData(String purchaseNo,
			List<PmPurchaseStorageExcel> importList) {

		// 导入数据check
		// check sn是否重复
		List<String> snList = Lists.newArrayList();

		// 数据行
		int docLine = 2;
		// check失败消息
		StringBuilder failureMsg = new StringBuilder();
	    // 导入数据迭代器
		Iterator<PmPurchaseStorageExcel> imptIt = importList.iterator();
		// 导入数据迭代
		while (imptIt.hasNext()) {
			docLine = docLine + 1;
			// 导入数据
			PmPurchaseStorageExcel importData = imptIt.next();
			
			// sn和生产日期都未来填的情况下，删除
			if (StringUtils.isEmptyNull(importData.getSn())
					&& StringUtils.isEmptyNull(importData.getProductionDate())) {
				imptIt.remove();
				continue;
			} else {
				// sn与生产日期未同时填写，报错
				if (StringUtils.isEmptyNull(importData.getSn())
						|| StringUtils.isEmptyNull(importData.getProductionDate())) {
					failureMsg.append("<br/>第" + docLine + "行：S/N和生产日期必须同时输入");
				}

                // 生产日期格式check
                if (!StringUtils.isBlank(importData.getProductionDate())
                        && this.parseDate(importData.getProductionDate()) == null) {
                    failureMsg.append("<br/>第" + docLine + "行：生产日期不是正确的年月日日期！");
                }
			}
			
			// check sn是否重复
			for (String sn : snList) {
				if (StringUtils.equals(sn, importData.getSn())) {
					failureMsg.append("<br/>第" + docLine + "行：S/N（" + sn + "）重复！");
					break;
				}
			}
			snList.add(importData.getSn());
			
			// ID是否存在
			if (StringUtils.isEmptyNull(importData.getId())) {
				failureMsg.append("<br/>第" + docLine + "行：ID不能为空！");
				// throw new ServiceException("上传数据失败：ID（采购订单号）不能为空！");
			}
			
			// 采购订单号是否存在
			if (StringUtils.isEmptyNull(importData.getPurchaseNo())) {
				failureMsg.append("<br/>第" + docLine + "行：采购订单号不能为空！");
				//throw new ServiceException("上传数据失败：ID（采购订单号）不能为空！");
			}
			
			// 采购订单号是否一致
			if (!StringUtils.equals(purchaseNo, importData.getPurchaseNo())) {
				failureMsg.append("<br/>第" + docLine + "行：采购订单号不一致！");
				// throw new ServiceException("上传数据失败：ID（采购订单号）不一致！");
			}

			// check sn是否存在
			if (smSnInfoDao.findCountBySnNo(importData.getSn()) > 0){
				failureMsg.append("<br/>第" + docLine + "行：SN号码已经存在！");
			}
		}

        // 验证完后，如果导入数据全部被忽略，表示未填写任何数据
        if (ListUtils.size(importList) == 0) {
            failureMsg.append("<br/>入库信息未填写！");
        }

		// 验证消息长度大于0，返回错误消息
		if (failureMsg.length() > 0) {
			failureMsg.insert(0, "导入Excel数据失败：");
			throw new ServiceException(failureMsg.toString());
		}
	}
	
	/**
	 * 导入数据与采购明细数关联check
	 * 
	 * @param groupImportMap
	 *            导入excel分组数据
	 * @param purDtlList
	 *            DB采购订单明细数据
	 */
	public void checkImportAndPurDtl(
			Map<String, List<PmPurchaseStorageExcel>> groupImportMap,
			List<PmPurchaseOrdDtl> purDtlList) {
		// 将db采购明细list转换为map
		Map<String, PmPurchaseOrdDtl> purDtlMap = ListUtils.convertListToMap(
				purDtlList, "id");

		// 导入物料及数量check
		for (Map.Entry<String, List<PmPurchaseStorageExcel>> entry : groupImportMap
				.entrySet()) {
			PmPurchaseOrdDtl purDtl = purDtlMap.get(entry.getKey());
			if (purDtl == null) {
				throw new ServiceException("入库失败：数据不整合！");
			} else {
				if (!StringUtils.equals(purDtl.getIfSn(), CommonConstants.YES)) {
					throw new ServiceException("入库失败：数据不整合！");
				}
			}

			// 本次到货数量为空或0时，报错
			if (purDtl.getArrivalNum() == null || purDtl.getArrivalNum() == 0) {
				throw new ServiceException("入库失败：物料" + purDtl.getMaterialNo()
						+ "的本次到货数量未填写，请填写并保存！");
			}

			// 数量check
			if (entry.getValue().size() != purDtl.getArrivalNum().intValue()) {
				throw new ServiceException("入库失败：物料" + purDtl.getMaterialNo()
						+ "的入库数量不等于本次到货数量！");
			}
		}
	}

	/**
	 * 给整型付上默认值
	 */
	private int defaultInt(Integer num) {
		if (num == null) {
			return 0;
		} else {
			return num.intValue();
		}
	}

	/**
	 * 比较两个日期是否相同
	 * 
	 * @param date1
	 *            比较日期1
	 * @param date2
	 *            比较日期2
	 * @return true：相同，false：不同
	 */
	private boolean equalsDate(Date date1, Date date2) {

		if (date1 == null && date2 == null) {
			return true;
		}

		if (date1 == null || date2 == null) {
			return false;
		}

		if (date1.getTime() == date2.getTime()) {
			return true;
		} else {
			return false;
		}
	}

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd", "yyyy-M-d", "yyyy/M/d", "yyyy.M.d" }
     */
    private Date parseDate(String str) {

        String[] parsePatterns = { "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd", "yyyy-M-d", "yyyy/M/d", "yyyy.M.d" };

        if (str == null) {
            return null;
        }
        try {
            return DateUtils.parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }
}