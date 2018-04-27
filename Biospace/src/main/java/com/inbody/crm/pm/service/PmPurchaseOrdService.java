/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.pm.dao.PmPurInvoiceDao;
import com.inbody.crm.pm.dao.PmPurchaseOrdDao;
import com.inbody.crm.pm.dao.PmPurchaseOrdDtlDao;
import com.inbody.crm.pm.dao.RmQuotationDtlDao;
import com.inbody.crm.pm.dao.SmMatInfoDao;
import com.inbody.crm.pm.domain.PmAccPurchaseOrdDtlExcel;
import com.inbody.crm.pm.domain.PmPurchaseOrdDtlExcel;
import com.inbody.crm.pm.domain.PmPurchaseOrdExcel;
import com.inbody.crm.pm.domain.PmPurchaseOrdSearch;
import com.inbody.crm.pm.entity.PmPurInvoice;
import com.inbody.crm.pm.entity.PmPurchaseOrd;
import com.inbody.crm.pm.entity.PmPurchaseOrdDtl;
import com.inbody.crm.pm.entity.RmQuotationDtl;
import com.inbody.crm.pm.entity.SmMatInfo;

/**
 * 采购订单Service
 * @author yangj
 * @version 2017-07-24
 */
@Service
@Transactional(readOnly = true)
public class PmPurchaseOrdService extends
        CrudService<PmPurchaseOrdDao, PmPurchaseOrd> {

    @Autowired
    private PmPurchaseOrdDtlDao pmPurchaseOrdDtlDao;

    @Autowired
    private SmMatInfoDao smMatInfoDao;

    @Autowired
    private PmPurInvoiceDao pmPurInvoiceDao;

    @Autowired
    private RmQuotationDtlDao rmQuotationDtlDao;

    @Autowired
    private CommonService commonService;

    public PmPurchaseOrd get(String id) {
        PmPurchaseOrd pmPurchaseOrd = super.get(id);
        // 采购订单取得check
        if (pmPurchaseOrd == null
                || StringUtils.isEmptyNull(pmPurchaseOrd.getPurchaseNo())) {
            throw new ServiceException("采购订单取得失败！");
        }

        // 采购明细取得
        pmPurchaseOrd.setPmPurchaseOrdDtlList(pmPurchaseOrdDtlDao
                .findList(new PmPurchaseOrdDtl(pmPurchaseOrd.getPurchaseNo())));
        // 配件采购的场合，foc采购明细取得
        if (StringUtils.equals(pmPurchaseOrd.getPurchaseType(),
                CommonConstants.PURCHASE_TYPE_2)) {
            pmPurchaseOrd.setPmFocPurchaseOrdDtlList(pmPurchaseOrdDtlDao
                    .findFocList(new PmPurchaseOrdDtl(pmPurchaseOrd
                            .getPurchaseNo())));
        }
        // 采购发票取得
        pmPurchaseOrd.setPmPurInvoiceList(pmPurInvoiceDao
                .findList(new PmPurInvoice(pmPurchaseOrd.getPurchaseNo())));
        // 创建者
        pmPurchaseOrd.setCreateName(UserUtils.get(
                pmPurchaseOrd.getCreateBy().getId()).getName());
        // 创建日期
        pmPurchaseOrd.setCreateDate(pmPurchaseOrd.getCreateDate());

        // 采购明细未到货数量计算
        for (PmPurchaseOrdDtl purOrdDtl : pmPurchaseOrd
                .getPmPurchaseOrdDtlList()) {
            // 未到货数量 = 数量 -已到货数量
            purOrdDtl.setUnarrivalNum(purOrdDtl.getNum()
                    - purOrdDtl.getAlArrivalNum());
        }

        // 配件采购的场合，foc采购明细未到货数量计算
        if (StringUtils.equals(pmPurchaseOrd.getPurchaseType(),
                CommonConstants.PURCHASE_TYPE_2)) {
            for (PmPurchaseOrdDtl focPurOrdDtl : pmPurchaseOrd
                    .getPmFocPurchaseOrdDtlList()) {
                // 未到货数量 = 数量 -已到货数量
                focPurOrdDtl.setUnarrivalNum(focPurOrdDtl.getNum()
                        - focPurOrdDtl.getAlArrivalNum());
            }
        }

        return pmPurchaseOrd;
    }

    /**
     * 采购订单一览取得
     * 
     * @param page
     * @param pmPurchaseOrdSearch
     * @return
     */
    public Page<PmPurchaseOrdSearch> findPurPage(
            Page<PmPurchaseOrdSearch> page,
            PmPurchaseOrdSearch pmPurchaseOrdSearch) {

        pmPurchaseOrdSearch.setPage(page);
        List<PmPurchaseOrdSearch> purSearchList = pmPurchaseOrdDtlDao
                .findPageList(pmPurchaseOrdSearch);
        page.setList(purSearchList);
        return page;
    }

    /**
     * 机器采购订单保存
     */
    @Transactional(readOnly = false)
    public String mcSave(PmPurchaseOrd purOrd) {

		// 机器采购订单申请发起
		// 临时保存、申请、或是再申请时执行订单信息保存
		if (StringUtils.isBlank(purOrd.getAct().getProcInsId())
				|| StringUtils.equals(purOrd.getWorkflowStatus(),
						CommonConstants.WORKFLOW_STATUS_10)
				|| StringUtils.equals(purOrd.getWorkflowStatus(),
						CommonConstants.WORKFLOW_STATUS_60)) {
			// 保存采购订单
			save(purOrd);
		}

		// 流程流转
		purOrd.getAct().setBusinessId(purOrd.getId());
		commonService.flowProcess(purOrd.getAct(), ActUtils.PD_MC_PURCHASE,
				CommonConstants.PROCESS_TITLE_MC_PURCHASE,
				purOrd.getWorkflowStatus());
		
		return purOrd.getId();
	}

    /**
     * 配件采购订单保存
     */
    @Transactional(readOnly = false)
    public String acSave(PmPurchaseOrd acPurOrd) {

        // 配件采购订单申请发起
        // 临时保存、申请、或是再申请时执行订单信息保存
        if (StringUtils.isBlank(acPurOrd.getAct().getProcInsId())
                || StringUtils.equals(acPurOrd.getWorkflowStatus(),
                        CommonConstants.WORKFLOW_STATUS_10)
                || StringUtils.equals(acPurOrd.getWorkflowStatus(),
                        CommonConstants.WORKFLOW_STATUS_60)) {
            // 保存采购订单
            save(acPurOrd);
        }

        // 流程流转
        acPurOrd.getAct().setBusinessId(acPurOrd.getId());
        commonService.flowProcess(acPurOrd.getAct(), ActUtils.PD_AC_PURCHASE,
                CommonConstants.PROCESS_TITLE_AC_PURCHASE,
                acPurOrd.getWorkflowStatus());

        // 配件采购订单删除时
        if (StringUtils.equals(acPurOrd.getWorkflowStatus(),
                CommonConstants.WORKFLOW_STATUS_70)) {

            // foc采购明细取得
            List<PmPurchaseOrdDtl> focPurOrdDtlList = pmPurchaseOrdDtlDao
                    .findFocList(new PmPurchaseOrdDtl(acPurOrd.getPurchaseNo()));

            // 采购的报价单明细行采购标志重置为未采购
            for (PmPurchaseOrdDtl focPurDtl : focPurOrdDtlList) {
                // 报价单明细，是否采购更新为否
                RmQuotationDtl qtnDtl = new RmQuotationDtl();
                qtnDtl.setQuotationNo(focPurDtl.getQuotationNo());
                qtnDtl.setLineNo(focPurDtl.getQutLineNo());
                // 是否采购：否
                qtnDtl.setIfPurchase(CommonConstants.NO);
                qtnDtl.preUpdate();
                rmQuotationDtlDao.updatePurchaseStatus(qtnDtl);
            }
        }

        return acPurOrd.getId();
    }

	/**
	 * 采购订单保存
	 */
	@Transactional(readOnly = false)
	public void save(PmPurchaseOrd purOrd) {

		PmPurchaseOrd dbPurOrd = null;
		if (!StringUtils.isEmptyNull(purOrd.getId())) {
			// 更新前采购订单取得
			dbPurOrd = this.get(purOrd.getId());

            // 排他性验证，更新日时不相等，数据已被更改
            if (DateUtils.compareDate(purOrd.getUpdateDate(),
                    dbPurOrd.getUpdateDate()) != 0) {
                throw new ServiceException("保存失败：数据已经被更改，请取得最新数据后再操作。");
            }
		}

		// 配件采购的场合, 根据画面指定的foc记录，取得对应的采购明细数据
		this.setFocPurDtlData(purOrd);
		// 数据关联验证
		this.purchaseValidator(purOrd, dbPurOrd);
		// 采购订单保存
		this.savePurchase(purOrd, dbPurOrd);
		// 采购明细保存
		this.savePurchaseDtl(purOrd, dbPurOrd);
		// 配件采购时，foc采购明细保存
		if (StringUtils.equals(purOrd.getPurchaseType(),
				CommonConstants.PURCHASE_TYPE_2)) {
			this.saveFocPurchaseDtl(purOrd, dbPurOrd);
		}
		// 采购发票保存
		this.saveInvoiceDtl(purOrd, dbPurOrd);
	}

	/**
	 * 采购订单数据逻辑验证及关联验证
	 * 
	 * @param purchaseOrd
	 *            画面采购订单数据
	 * @param dbPurchaseOrd
	 *            DB中更新前的采购订单数据
	 */
	private void purchaseValidator(PmPurchaseOrd purchaseOrd, PmPurchaseOrd dbPurchaseOrd) {
		// 数据关联验证
		// 采购明细list
		List<PmPurchaseOrdDtl> purDtlList = purchaseOrd.getPmPurchaseOrdDtlList();
		// foc采购明细list
		List<PmPurchaseOrdDtl> focPurDtlList = purchaseOrd.getPmFocPurchaseOrdDtlList();
		// 采购发票list
		List<PmPurInvoice> purInvoiceList = purchaseOrd.getPmPurInvoiceList();

		// 采购明细check
		// 机器采购的场合
		if (StringUtils.equals(purchaseOrd.getPurchaseType(),
				CommonConstants.PURCHASE_TYPE_1)) {
			if (purDtlList == null || purDtlList.size() == 0) {
				throw new ServiceException("保存失败：请填写采购明细。");
			}

			// 配件采购的场合
		} else if (StringUtils.equals(purchaseOrd.getPurchaseType(),
				CommonConstants.PURCHASE_TYPE_2)) {
			if ((purDtlList == null || purDtlList.size() == 0)
					&& (focPurDtlList == null || focPurDtlList.size() == 0)) {
				throw new ServiceException("保存失败：请填写采购明细。");
			}
		}

		// 采购明细中总未付数量
		Integer totalUnpayNum = 0;
		// 数量合计
		int numCount = 0;
		// 采购明细物料不能重复
		List<String> matNoList = Lists.newArrayList();
		for (PmPurchaseOrdDtl dtl : purDtlList) {
			for (String matNo : matNoList) {
				if (StringUtils.equals(matNo, dtl.getMaterialNo())) {
					throw new ServiceException("保存失败：物料不能重复。");
				}
			}
			matNoList.add(dtl.getMaterialNo());

			// 物料存在check
			SmMatInfo smMatInfoParam = new SmMatInfo();
			smMatInfoParam.setMaterialNo(dtl.getMaterialNo());
			SmMatInfo smMatInfo = smMatInfoDao
					.findByMaterialAndType(smMatInfoParam);
			if (smMatInfo == null
					|| StringUtils.isEmptyNull(smMatInfo.getMaterialNo())) {
				throw new ServiceException("保存失败：物料号为" + dtl.getMaterialNo()
						+ "的物料不存在。");
			}

//			// 机器采购的场合，验证物料是否为机器
//			if (StringUtils.equals(purchaseOrd.getPurchaseType(),
//					CommonConstants.PURCHASE_TYPE_1)
//					&& !StringUtils.equals(smMatInfo.getMaterialType(),
//							CommonConstants.MATERIAL_TYPE_1)) {
//				throw new ServiceException("保存失败：物料号为" + dtl.getMaterialNo()
//						+ "的物料不是机器。");
//			}
//			
//			// 配件采购的场合，验证物料是否为配件
//			if (StringUtils.equals(purchaseOrd.getPurchaseType(),
//					CommonConstants.PURCHASE_TYPE_2)
//					&& !StringUtils.equals(smMatInfo.getMaterialType(),
//							CommonConstants.MATERIAL_TYPE_2)) {
//				throw new ServiceException("保存失败：物料号为" + dtl.getMaterialNo()
//						+ "的物料不是配件。");
//			}

			// 采购明细中未付数量大于数量
			if (dtl.getUnpayNum() != null && dtl.getUnpayNum() > dtl.getNum()) {
				throw new ServiceException("保存失败：物料号为" + dtl.getMaterialNo()
						+ "的采购明细行未付数量不能大于采购数量。");
			}

			if (dtl.getUnpayNum() == null) {
				totalUnpayNum = totalUnpayNum + dtl.getNum();
			} else {
				totalUnpayNum = totalUnpayNum + dtl.getUnpayNum();
			}
			numCount = numCount + dtl.getNum();
		}
		
		// db关联check
		if (dbPurchaseOrd != null) {
			// 变更或删除采购明细check
			// db订单明细转换成map
			Map<String, PmPurchaseOrdDtl> dbPurOrdDtlMap = convertListToMap(
					dbPurchaseOrd.getPmPurchaseOrdDtlList(), "materialNo");
			
			for (PmPurchaseOrdDtl dtl : purDtlList) {
				// 已经到货时，数量不能小于到货数量
				PmPurchaseOrdDtl dbDtl = dbPurOrdDtlMap.get(dtl
						.getMaterialNo());
				if (dbDtl != null) {
					if (dbDtl.getAlArrivalNum() > 0
							&& dtl.getNum() < dbDtl.getAlArrivalNum()) {
						// 物料被变更，出错
						throw new ServiceException("保存失败：物料号为"
								+ dtl.getMaterialNo() + "的物料采购数量不能小于已到货数量！");
					} else {
						dbPurOrdDtlMap.remove(dtl.getMaterialNo());
					}
				}
			}

			// 已有到货的采购明细不能被删除
			for (PmPurchaseOrdDtl delOrdDtl : dbPurOrdDtlMap.values()) {
				if (delOrdDtl.getAlArrivalNum() > 0) {
					throw new ServiceException("保存失败：物料" + delOrdDtl.getMaterialNo() + "已有到货，不能删除！");
				}
			}
			
			// 删除foc采购明细行check
			if (StringUtils.equals(purchaseOrd.getPurchaseType(),
					CommonConstants.PURCHASE_TYPE_2)) {
				// db foc采购明细转换成map
				Map<String, PmPurchaseOrdDtl> dbFocPurOrdDtlMap = convertListToMap(
						dbPurchaseOrd.getPmFocPurchaseOrdDtlList(), "materialNo");

				for (PmPurchaseOrdDtl focDtl : focPurDtlList) {
					PmPurchaseOrdDtl dbFocDtl = dbFocPurOrdDtlMap.get(focDtl
							.getMaterialNo());
					if (dbFocDtl != null) {
						dbFocPurOrdDtlMap.remove(focDtl.getMaterialNo());
					}
				}

				// 已有到货的采购明细不能被删除
				for (PmPurchaseOrdDtl delFocOrdDtl : dbFocPurOrdDtlMap.values()) {
					if (delFocOrdDtl.getAlArrivalNum() > 0) {
						throw new ServiceException("保存失败：FOC采购物料"
								+ delFocOrdDtl.getMaterialNo() + "已有到货，不能删除！");
					}
				}
			}
		}

		// 支付状态check
		// 配件采购，采购明细为空时，支付状态不等于已支付
		if (purDtlList == null || purDtlList.size() == 0) {
			if (!StringUtils.equals(purchaseOrd.getPaymentStatus(),
					CommonConstants.PAYMENT_STATUS_2)) {
				throw new ServiceException("保存失败：支付状态与未付数量不匹配，请修改！");
			}
		}

        // 采购数量大于0
        if (numCount > 0) {
            // 未付数量 = 采购数量 且 支付状态不等于未支付
            if (totalUnpayNum == numCount
                    && !StringUtils.equals(purchaseOrd.getPaymentStatus(),
                            CommonConstants.PAYMENT_STATUS_0)) {
                throw new ServiceException("保存失败：支付状态与未付数量不匹配，请修改！");

                // 未付数量 > 0 且 未付数量 < 采购数量 且 支付状态不等于部分支付
            } else if (totalUnpayNum > 0
                    && totalUnpayNum < numCount
                    && !StringUtils.equals(purchaseOrd.getPaymentStatus(),
                            CommonConstants.PAYMENT_STATUS_1)) {
                throw new ServiceException("保存失败：支付状态与未付数量不匹配，请修改！");

                // 未付数量 = 0 且 支付状态不等于已支付
            } else if (totalUnpayNum == 0
                    && !StringUtils.equals(purchaseOrd.getPaymentStatus(),
                            CommonConstants.PAYMENT_STATUS_2)) {
                throw new ServiceException("保存失败：支付状态与未付数量不匹配，请修改！");
            }
        }

		// 采购发票不能重复
		List<String> invoiceNoList = Lists.newArrayList();
		for (PmPurInvoice ivc : purInvoiceList) {
			for (String ivcNo : invoiceNoList) {
				if (StringUtils.equals(ivcNo, ivc.getInvoiceNo())) {
					throw new ServiceException("保存失败：发票号码不能重复。");
				}
			}
			invoiceNoList.add(ivc.getInvoiceNo());
		}
	}

	/**
	 * 采购订单保存
	 * 
	 * @param purchaseOrd
	 *        画面采购订单数据
	 * @param dbPurchaseOrd
	 *        DB中更新前的采购订单数据
	 */
	@Transactional(readOnly = false)
	public void savePurchase(PmPurchaseOrd purchaseOrd, PmPurchaseOrd dbPurchaseOrd) {
		// 订单号是否存在
		if (StringUtils.isEmptyNull(purchaseOrd.getPurchaseNo())) {
            // 订单号取得（流水号日循环）
            String purchaseNo = DateUtils.getDate("yy-MM-dd")
                    + commonService.getNextSequence(
                            CommonConstants.TRANSACTION_NUMBER_PURCHASE,
                            DateUtils.getDate("yyMMdd"), 2);
			// 订单号
			purchaseOrd.setPurchaseNo(purchaseNo);
			// 到货状态
			purchaseOrd.setArrivalStatus(CommonConstants.ARRIVAL_STATUS_0);
			super.save(purchaseOrd);
		} else {

			// 更新采购订单
			// 供应商
			dbPurchaseOrd.setSupplierId(purchaseOrd.getSupplierId());
			// 支付状态
			dbPurchaseOrd.setPaymentStatus(purchaseOrd.getPaymentStatus());
			// 到货状态
			dbPurchaseOrd.setArrivalStatus(calculateArrivalStatus(purchaseOrd,
					dbPurchaseOrd));
			// 备注
			dbPurchaseOrd.setNewRemarks(purchaseOrd.getNewRemarks());
			// AX订单号
			dbPurchaseOrd.setAxNo(purchaseOrd.getAxNo());
			super.save(dbPurchaseOrd);
		}
	}
	
	/**
	 * 采购明细保存
	 * 
	 * @param purchaseOrd
	 *        画面采购订单数据
	 * @param dbPurchaseOrd
	 *        DB中更新前的采购订单数据
	 */
	@Transactional(readOnly = false)
	public void savePurchaseDtl(PmPurchaseOrd purchaseOrd,
			PmPurchaseOrd dbPurchaseOrd) {
		// 行号
		int lineNo = 0;
		// 更新/登录判断
		if (dbPurchaseOrd == null) {
			// 采购明细登录处理
			for (PmPurchaseOrdDtl purOrdDtl : purchaseOrd
					.getPmPurchaseOrdDtlList()) {
				// 明细行号自增
				lineNo = lineNo + 1;
				// 采购订单号
				purOrdDtl.setPurchaseNo(purchaseOrd.getPurchaseNo());
				// 行号
				purOrdDtl.setLineNo(lineNo);
				// 是否foc
				purOrdDtl.setIfFoc(CommonConstants.NO);
				// 未付数量，为空时，默认为数量
				if (purOrdDtl.getUnpayNum() == null) {
					purOrdDtl.setUnpayNum(purOrdDtl.getNum());
				}
				// 总金额计算
				purOrdDtl.setTotalAmount(purOrdDtl.getUnitPrice()
						.multiply(new BigDecimal(purOrdDtl.getNum())));
				// 未付金额计算
				purOrdDtl.setUnpayAmount(purOrdDtl.getUnitPrice()
						.multiply(new BigDecimal(purOrdDtl.getUnpayNum())));
				// 已到货数量
				purOrdDtl.setAlArrivalNum(0);
				// 本次到货数量
				purOrdDtl.setArrivalNum(0);
				purOrdDtl.setDelFlag(PmPurchaseOrdDtl.DEL_FLAG_NORMAL);

				purOrdDtl.preInsert();
				pmPurchaseOrdDtlDao.insert(purOrdDtl);
			}
		} else {
			
			// db订单明细转换成map
			Map<String, PmPurchaseOrdDtl> dbPurOrdDtlMap = convertListToMap(
					dbPurchaseOrd.getPmPurchaseOrdDtlList(), "materialNo");
			
			// DB采购明细
			PmPurchaseOrdDtl dbPurOrdDtl = null;
			// 画面采购明细行迭代
			for (PmPurchaseOrdDtl purOrdDtl : purchaseOrd
					.getPmPurchaseOrdDtlList()) {
				// 明细行号自增
				lineNo = lineNo + 1;
				// 画面采购明细对应的DB采购明细数据取得
				dbPurOrdDtl = dbPurOrdDtlMap.get(purOrdDtl.getMaterialNo());

				// DB采购明细为空:登录，不为空:更新
				if (dbPurOrdDtl == null) {
					// 采购订单号
					purOrdDtl.setPurchaseNo(dbPurchaseOrd.getPurchaseNo());
					// 行号
					purOrdDtl.setLineNo(lineNo);
					// 是否foc
					purOrdDtl.setIfFoc(CommonConstants.NO);
					// 总金额计算
					purOrdDtl.setTotalAmount(purOrdDtl.getUnitPrice().multiply(
							new BigDecimal(purOrdDtl.getNum())));
					// 未付数量，为空时，默认为数量
					if (purOrdDtl.getUnpayNum() == null) {
						purOrdDtl.setUnpayNum(purOrdDtl.getNum());
					}
					// 未付金额计算
					purOrdDtl.setUnpayAmount(purOrdDtl.getUnitPrice().multiply(
							new BigDecimal(purOrdDtl.getUnpayNum())));
					// 已到货数量
					purOrdDtl.setAlArrivalNum(0);
					// 本次到货数量
					purOrdDtl.setArrivalNum(0);
					// 删除flg
					purOrdDtl.setDelFlag(PmPurchaseOrdDtl.DEL_FLAG_NORMAL);

					purOrdDtl.preInsert();
					pmPurchaseOrdDtlDao.insert(purOrdDtl);
				} else {

					dbPurOrdDtlMap.remove(purOrdDtl.getMaterialNo());
					// 行号
					dbPurOrdDtl.setLineNo(lineNo);
					// 数量
					dbPurOrdDtl.setNum(purOrdDtl.getNum());
					// 单价
					dbPurOrdDtl.setUnitPrice(purOrdDtl.getUnitPrice());
					// 总金额计算
					dbPurOrdDtl.setTotalAmount(purOrdDtl.getUnitPrice()
							.multiply(new BigDecimal(purOrdDtl.getNum())));
					// 未付数量，为空时，默认为数量
					if (purOrdDtl.getUnpayNum() == null) {
						dbPurOrdDtl.setUnpayNum(purOrdDtl.getNum());
					} else {
						dbPurOrdDtl.setUnpayNum(purOrdDtl.getUnpayNum());
					}
					// 未付金额计算
					dbPurOrdDtl.setUnpayAmount(dbPurOrdDtl.getUnitPrice()
							.multiply(new BigDecimal(dbPurOrdDtl.getUnpayNum())));

					dbPurOrdDtl.preUpdate();
					pmPurchaseOrdDtlDao.update(dbPurOrdDtl);
				}
			}
			
			// 删除需要删除的采购明细行
			for (PmPurchaseOrdDtl delOrdDtl : dbPurOrdDtlMap.values()) {
				pmPurchaseOrdDtlDao.delete(delOrdDtl);
			}
		}
	}
	
	/**
	 * Foc采购明细保存
	 * 
	 * @param purchaseOrd
	 *        画面采购订单数据
	 * @param dbPurchaseOrd
	 *        DB中更新前的采购订单数据
	 */
	@Transactional(readOnly = false)
	public void saveFocPurchaseDtl(PmPurchaseOrd purchaseOrd,
			PmPurchaseOrd dbPurchaseOrd) {
		// 行号
		int lineNo = 0;
		// 更新/登录判断
		if (dbPurchaseOrd == null) {
			// Foc采购明细登录处理
			for (PmPurchaseOrdDtl focPurOrdDtl : purchaseOrd
					.getPmFocPurchaseOrdDtlList()) {
				// 明细行号自增
				lineNo = lineNo + 1;
				// 采购订单号
				focPurOrdDtl.setPurchaseNo(purchaseOrd.getPurchaseNo());
				// 行号
				focPurOrdDtl.setLineNo(lineNo);

				focPurOrdDtl.preInsert();
				pmPurchaseOrdDtlDao.insert(focPurOrdDtl);

				// 报价单明细，是否采购更新为中是
				RmQuotationDtl qtnDtl = focPurOrdDtl.getRmQuotationDtl();
				// 是否采购：是
				qtnDtl.setIfPurchase(CommonConstants.YES);
				qtnDtl.preUpdate();
				rmQuotationDtlDao.update(qtnDtl);
			}
		} else {

			// db订单明细转换成map
			Map<String, PmPurchaseOrdDtl> dbFocPurOrdDtlMap = convertListToMap(
					dbPurchaseOrd.getPmFocPurchaseOrdDtlList(), "id");

			// DBFoc采购明细
			PmPurchaseOrdDtl dbFocPurOrdDtl = null;
			// 画面Foc采购明细行迭代
			for (PmPurchaseOrdDtl focPurOrdDtl : purchaseOrd
					.getPmFocPurchaseOrdDtlList()) {
				// 明细行号自增
				lineNo = lineNo + 1;
				// 画面采购明细对应的DB采购明细数据取得
				dbFocPurOrdDtl = dbFocPurOrdDtlMap.get(focPurOrdDtl.getId());

				// DB采购明细为空:登录，不为空:更新
				if (dbFocPurOrdDtl == null) {
					// 采购订单号
					focPurOrdDtl.setPurchaseNo(dbPurchaseOrd.getPurchaseNo());
					// 行号
					focPurOrdDtl.setLineNo(lineNo);

					focPurOrdDtl.preInsert();
					pmPurchaseOrdDtlDao.insert(focPurOrdDtl);

					// 报价单明细，是否采购更新为中是
					RmQuotationDtl qtnDtl = focPurOrdDtl.getRmQuotationDtl();
					// 是否采购：是
					qtnDtl.setIfPurchase(CommonConstants.YES);
					qtnDtl.preUpdate();
					rmQuotationDtlDao.update(qtnDtl);
				} else {

					dbFocPurOrdDtlMap.remove(focPurOrdDtl.getId());
					// 行号
					dbFocPurOrdDtl.setLineNo(lineNo);

					dbFocPurOrdDtl.preUpdate();
					pmPurchaseOrdDtlDao.update(dbFocPurOrdDtl);
				}
			}

			// 删除需要删除的foc采购明细行
			for (PmPurchaseOrdDtl delFocPurOrdDtl : dbFocPurOrdDtlMap.values()) {
				pmPurchaseOrdDtlDao.delete(delFocPurOrdDtl);

				// 报价单明细，是否采购更新为否
				RmQuotationDtl qtnDtl = new RmQuotationDtl();
				qtnDtl.setQuotationNo(delFocPurOrdDtl.getQuotationNo());
				qtnDtl.setLineNo(delFocPurOrdDtl.getQutLineNo());
				// 是否采购：是
				qtnDtl.setIfPurchase(CommonConstants.NO);
				qtnDtl.preUpdate();
				rmQuotationDtlDao.updatePurchaseStatus(qtnDtl);
			}
		}
	}
	
	/**
	 * 发票明细保存
	 * 
	 * @param purchaseOrd
	 *        画面采购订单数据
	 * @param dbPurchaseOrd
	 *        DB中更新前的采购订单数据
	 */
	@Transactional(readOnly = false)
	public void saveInvoiceDtl(PmPurchaseOrd purchaseOrd,
			PmPurchaseOrd dbPurchaseOrd) {

		// 行号
		int lineNo = 0;
		// 更新/登录判断
		if (dbPurchaseOrd == null) {
			// 发票明细登录处理
			for (PmPurInvoice purInvoice : purchaseOrd.getPmPurInvoiceList()) {
				// 明细行号自增
				lineNo = lineNo + 1;
				// 采购订单号
				purInvoice.setPurchaseNo(purchaseOrd.getPurchaseNo());
				// 行号
				purInvoice.setLineNo2(lineNo);
				// 删除flag
				purInvoice.setDelFlag(PmPurInvoice.DEL_FLAG_NORMAL);

				purInvoice.preInsert();
				pmPurInvoiceDao.insert(purInvoice);
			}
		} else {
			
			// db发票明细转换成map
			Map<String, PmPurInvoice> dbPurInvoiceMap = convertListToMap(
					dbPurchaseOrd.getPmPurInvoiceList(), "invoiceNo");
			
			// DB发票明细
			PmPurInvoice dbPurInvoice = null;
			// 画面发票明细行迭代
			for (PmPurInvoice purInvoice : purchaseOrd
					.getPmPurInvoiceList()) {
				// 明细行号自增
				lineNo = lineNo + 1;
				// 画面发票明细对应的DB发票明细数据取得
				dbPurInvoice = dbPurInvoiceMap.get(purInvoice.getInvoiceNo());

				// DB发票明细为空:登录，不为空:更新
				if (dbPurInvoice == null) {
					// 采购订单号
					purInvoice.setPurchaseNo(dbPurchaseOrd.getPurchaseNo());
					// 行号
					purInvoice.setLineNo2(lineNo);
					// 删除flag
					purInvoice.setDelFlag(PmPurInvoice.DEL_FLAG_NORMAL);

					purInvoice.preInsert();
					pmPurInvoiceDao.insert(purInvoice);
				} else {

					dbPurInvoiceMap.remove(purInvoice.getInvoiceNo());

					// 行号
					dbPurInvoice.setLineNo2(lineNo);
					// 发票日期
					dbPurInvoice.setInvoiceDate(purInvoice.getInvoiceDate());
					// 删除flag
					dbPurInvoice.setDelFlag(PmPurInvoice.DEL_FLAG_NORMAL);

					dbPurInvoice.preUpdate();
					pmPurInvoiceDao.update(dbPurInvoice);
				}
			}
			
			// 删除需要删除的发票明细行
			for (PmPurInvoice delPurInvoice : dbPurInvoiceMap.values()) {
				pmPurInvoiceDao.delete(delPurInvoice);
			}
		}
	}

	/**
	 * 配件采购的场合, 根据画面指定的foc记录，取得对应的采购明细数据
	 * 
	 * @param purchaseOrd
	 *        画面采购订单数据
	 */
	@Transactional(readOnly = false)
	private void setFocPurDtlData(PmPurchaseOrd purchaseOrd) {
		// 配件采购的场合
		if (StringUtils.equals(purchaseOrd.getPurchaseType(),
				CommonConstants.PURCHASE_TYPE_2)) {
			for (PmPurchaseOrdDtl focPurDtl : purchaseOrd
					.getPmFocPurchaseOrdDtlList()) {
                // foc采购明细id为空时, 为新增foc采购明细
                if (StringUtils.isBlank(focPurDtl.getId())) {
                    // 新增foc采购明细时，报价单明细ID不能为空
                    if (StringUtils.isBlank(focPurDtl.getQuotationDtlId())) {
                        throw new ServiceException("保存失败：数据不整合！");
                    }
					// 报价单配件信息取得
					RmQuotationDtl qtnDtl = rmQuotationDtlDao.get(focPurDtl
							.getQuotationDtlId());

					if (qtnDtl == null) {
						throw new ServiceException("保存失败：DB数据不整合！");
					}
					// 报价单编号
					focPurDtl.setQuotationNo(qtnDtl.getQuotationNo());
					// 报价单行号
					focPurDtl.setQutLineNo(qtnDtl.getLineNo());
					// 物料号
					focPurDtl.setMaterialNo(qtnDtl.getMaterialNo());
					// 是否foc
					focPurDtl.setIfFoc(CommonConstants.YES);
					// 数量
					focPurDtl.setNum(qtnDtl.getNum());
					// 单价
					focPurDtl.setUnitPrice(qtnDtl.getUnitPrice());
					// 总金额
					focPurDtl.setTotalAmount(qtnDtl.getTotalAmount());
					// 未付数量
					focPurDtl.setUnpayNum(0);
					// 未付金额
					focPurDtl.setUnpayAmount(BigDecimal.ZERO);
					// 已到货数量
					focPurDtl.setAlArrivalNum(0);
					// 本次到货数量
					focPurDtl.setArrivalNum(0);
					// 删除flag
					focPurDtl.setDelFlag(PmPurchaseOrdDtl.DEL_FLAG_NORMAL);

					// 报价单明细信息
					focPurDtl.setRmQuotationDtl(qtnDtl);

					// 更新用采购明细取得
				} else {

					// 采购明细取得
					PmPurchaseOrdDtl purOrdDtl = pmPurchaseOrdDtlDao
							.get(focPurDtl.getId());

					if (purOrdDtl == null) {
						throw new ServiceException("保存失败：DB数据不整合！");
					}

					// 报价单明细信息取得
					RmQuotationDtl selQtnDtl = new RmQuotationDtl();
					selQtnDtl.setQuotationNo(purOrdDtl.getQuotationNo());
					selQtnDtl.setLineNo(purOrdDtl.getQutLineNo());
					
					List<RmQuotationDtl> resultQtnDtlLst = rmQuotationDtlDao
							.getBySelective(selQtnDtl);
					
					if (resultQtnDtlLst == null || resultQtnDtlLst.size() == 0) {
						throw new ServiceException("保存失败：DB数据不整合！");
					}

					// 报价单明细信息
					purOrdDtl.setRmQuotationDtl(resultQtnDtlLst.get(0));

					focPurDtl = purOrdDtl;
				}
			}
		}
	}

	/**
	 * 更新时，采购订单到货状态计算
	 * 
	 * @param purchaseOrd
	 *            画面采购订单数据
	 * @param dbPurchaseOrd
	 *            DB中更新前的采购订单数据
	 * 
	 * @return 采购订单到货状态
	 */
	public String calculateArrivalStatus(PmPurchaseOrd purOrd,
			PmPurchaseOrd dbPurOrd) {

		// 数量合计
		int numCount = 0;
		// 已经到货数量合计
		int alArrivalNumCount = 0;
		// 到货状态默认值设定
		String arrivalStatus = CommonConstants.ARRIVAL_STATUS_0;

		// db订单明细转换成map
		Map<String, PmPurchaseOrdDtl> dbPurOrdDtlMap = convertListToMap(
				dbPurOrd.getPmPurchaseOrdDtlList(), "materialNo");

		// 采购明细
		PmPurchaseOrdDtl dbDtl = null;
		for (PmPurchaseOrdDtl purOrdDtl : purOrd.getPmPurchaseOrdDtlList()) {
			dbDtl = dbPurOrdDtlMap.get(purOrdDtl.getMaterialNo());
			if (dbDtl != null) {
				alArrivalNumCount = alArrivalNumCount + dbDtl.getAlArrivalNum();
			}
			numCount = numCount + purOrdDtl.getNum();
		}

		// foc采购明细
		if (StringUtils.equals(purOrd.getPurchaseType(),
				CommonConstants.PURCHASE_TYPE_2)) {
			for (PmPurchaseOrdDtl focPurOrdDtl : purOrd
					.getPmFocPurchaseOrdDtlList()) {
				numCount = numCount + focPurOrdDtl.getNum();
				if (focPurOrdDtl.getAlArrivalNum() == null) {
					alArrivalNumCount = alArrivalNumCount + 0;
				} else {
					alArrivalNumCount = alArrivalNumCount
							+ focPurOrdDtl.getAlArrivalNum();
				}
			}
		}

		// 到货状态
		if (alArrivalNumCount == 0) {
			arrivalStatus = CommonConstants.ARRIVAL_STATUS_0;
		} else if (alArrivalNumCount > 0 && alArrivalNumCount < numCount) {
			arrivalStatus = CommonConstants.ARRIVAL_STATUS_1;
		} else {
			arrivalStatus = CommonConstants.ARRIVAL_STATUS_2;
		}

		return arrivalStatus;
	}

	/**
	 * 采购明细导出数据取得
	 */
	public List<PmPurchaseOrdDtlExcel> exportPurchaseOrdDtl(String purchaseNo) {

		List<PmPurchaseOrdDtl> purDtlList = pmPurchaseOrdDtlDao
				.findList(new PmPurchaseOrdDtl(purchaseNo));

		PmPurchaseOrdDtlExcel purDtlExcel = null;
		List<PmPurchaseOrdDtlExcel> purDtlExcelList = Lists.newArrayList();
		NumberFormat nf = new DecimalFormat("#,##0.00");
		for (PmPurchaseOrdDtl purDtl : purDtlList) {
			purDtlExcel = new PmPurchaseOrdDtlExcel();
			purDtlExcel.setMaterialNo(purDtl.getMaterialNo());
			purDtlExcel.setMaterialName(purDtl.getMaterialName());
			purDtlExcel.setNum(purDtl.getNum());
            if (purDtl.getUnitPrice() == null) {
                purDtlExcel.setUnitPrice("");
            } else {
                purDtlExcel.setUnitPrice(nf.format(purDtl.getUnitPrice()));
            }
            if (purDtl.getTotalAmount() == null) {
                purDtlExcel.setTotalAmount("");
            } else {
                purDtlExcel.setTotalAmount(nf.format(purDtl.getTotalAmount()));
            }
			purDtlExcelList.add(purDtlExcel);
		}

		return purDtlExcelList;
	}
	
	/**
	 * 配件采购明细导出数据取得
	 */
	public List<PmAccPurchaseOrdDtlExcel> exportAccPurchaseOrdDtl(String purchaseNo) {

		List<PmPurchaseOrdDtl> accPurDtlList = pmPurchaseOrdDtlDao
				.findAccPurDtlExportList(new PmPurchaseOrdDtl(purchaseNo));

		PmAccPurchaseOrdDtlExcel accPurDtlExcel = null;
		List<PmAccPurchaseOrdDtlExcel> purDtlExcelList = Lists.newArrayList();
		NumberFormat nf = new DecimalFormat("#,##0.00");
		for (PmPurchaseOrdDtl accPurDtl : accPurDtlList) {
			accPurDtlExcel = new PmAccPurchaseOrdDtlExcel();
			accPurDtlExcel.setMaterialNo(StringUtils.defaultString(accPurDtl.getMaterialNo()));
			accPurDtlExcel.setModel(StringUtils.defaultString(accPurDtl.getModel()));
			accPurDtlExcel.setAccessoryName(StringUtils.defaultString(accPurDtl.getMaterialName()));
			accPurDtlExcel.setNum(String.valueOf(accPurDtl.getNum()));
			if (StringUtils.equals(accPurDtl.getIfFoc(), CommonConstants.YES)) {
				accPurDtlExcel.setPaymentType("FOC");
			} else {
				accPurDtlExcel.setPaymentType("Paid");
			}
            if (accPurDtl.getUnitPrice() == null) {
                accPurDtlExcel.setUnitPrice("");
            } else {
                accPurDtlExcel.setUnitPrice(nf.format(accPurDtl.getUnitPrice()));
            }
            if (accPurDtl.getTotalAmount() == null) {
                accPurDtlExcel.setTotalAmount("");
            } else {
                accPurDtlExcel.setTotalAmount(nf.format(accPurDtl.getTotalAmount()));
            }
			accPurDtlExcel.setRepairNo(StringUtils.defaultString(accPurDtl.getRepairNo()));
			purDtlExcelList.add(accPurDtlExcel);
		}

		return purDtlExcelList;
	}
	
	/**
	 * 导出采购订单一览用采购订单数据取得
	 * 
	 * @param pmPurchaseOrdSearch
	 *            导出采购订单一览查询条件
	 * @return 采购订单一览
	 */
	public List<PmPurchaseOrdExcel> exportPurchaseOrdList(
			PmPurchaseOrdSearch pmPurchaseOrdSearch) {
		List<PmPurchaseOrdSearch> purSearchList = pmPurchaseOrdDtlDao
				.findPageList(pmPurchaseOrdSearch);
		
		PmPurchaseOrdExcel purExcel = null;
		List<PmPurchaseOrdExcel> purExcelList = Lists.newArrayList();
		NumberFormat nf = new DecimalFormat("#,##0.00");
		for (PmPurchaseOrdSearch pur : purSearchList) {
			purExcel = new PmPurchaseOrdExcel();
			purExcel.setPurchaseNo(pur.getPurchaseNo());
			purExcel.setPurchaseType(pur.getPurchaseType());
			purExcel.setIfFoc(pur.getIfFoc());
			purExcel.setPurStatus(pur.getPurStatus());
			purExcel.setSupplierName(pur.getSupplierName());
			purExcel.setPaymentStatus(pur.getPaymentStatus());
			purExcel.setArrivalStatus(pur.getArrivalStatus());
			purExcel.setMaterialNo(pur.getMaterialNo());
			purExcel.setMaterialName(pur.getMaterialName());
            if (pur.getUnitPrice() == null) {
                purExcel.setUnitPrice("");
            } else {
                purExcel.setUnitPrice(nf.format(pur.getUnitPrice()));
            }
			purExcel.setNum(String.valueOf(pur.getNum()));
			purExcel.setUnpayNum(String.valueOf(pur.getUnpayNum()));
            if (pur.getUnpayAmount() == null) {
                purExcel.setUnpayAmount("");
            } else {
                purExcel.setUnpayAmount(nf.format(pur.getUnpayAmount()));
            }
			purExcel.setAlArrivalNum(String.valueOf(pur.getAlArrivalNum()));
			purExcel.setUnarrivalNum(String.valueOf(pur.getUnarrivalNum()));
			purExcel.setInvoiceNo(StringUtils.defaultString(pur.getInvoiceNo()));
			purExcel.setAxNo(StringUtils.defaultString(pur.getAxNo()));
			purExcelList.add(purExcel);
		}
		return purExcelList;
	}
	
	/**
	 * 取得没有被采购的报价单明细列表
	 */
	public List<RmQuotationDtl> getNoPuredQtnDtlList() {
		return rmQuotationDtlDao.findNoPuredQtnDtlList(new RmQuotationDtl());
	}
	

	/**
	 * 将对象List转换为map
	 * 
	 * @param list
	 *            对象List
	 * @param keyProperty
	 *            作为map key值的对象属性名,必须为String类型
	 * @return map对象
	 */
	private <T> Map<String, T> convertListToMap(List<T> list, String keyProperty) {

		Map<String, T> map = new HashMap<String, T>();
		if (list == null || list.size() == 0
				|| StringUtils.isEmptyNull(keyProperty)) {
			return map;
		}

		try {
			// 将属性的首字符大写，方便构造get方法
			keyProperty = keyProperty.substring(0, 1).toUpperCase()
					+ keyProperty.substring(1);
			for (T item : list) {
				Method m = item.getClass().getMethod("get" + keyProperty);
				// 调用getter方法获取属性值
				String keyvalue = (String) m.invoke(item);
				if (StringUtils.isEmptyNull(keyvalue)) {
					continue;
				}
				map.put(keyvalue, item);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return map;
	}
}