/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.utils.DictUtils;
import com.inbody.crm.sd.dao.AtAgentTargetDao;
import com.inbody.crm.sd.domain.AtAgentTargetModal;
import com.inbody.crm.sd.domain.AtAgentTargetSearch;
import com.inbody.crm.sd.entity.AtAgentTarget;

/**
 * 合同信息录入Service
 * @author zhanglulu
 * @version 2017-08-22
 */
@Service
@Transactional(readOnly = true)
public class AtAgentTargetService {

	@Autowired
	private AtAgentTargetDao atAgentTargetDao;

	/**
	 * 查询结果取得
	 * 
	 * @param page
	 * @param atAgentTargetSearch
	 * @return
	 */
	public Page<AtAgentTargetSearch> findAtAgentTargetComparePage(Page<AtAgentTargetSearch> page,
			AtAgentTargetSearch atAgentTargetSearch) {
		page.setPageSize(1);
		atAgentTargetSearch.setPage(page);
		
		// 依据检索条件取得代理商明细
		List<AtAgentTargetSearch> atAgentTargetSearchList = atAgentTargetDao.findCompareAtAgentTargetPageList(atAgentTargetSearch);
		
		// 取得阶段明细
		if (atAgentTargetSearchList != null && atAgentTargetSearchList.size() > 0) {
			
			AtAgentTarget scAtAgentTarget = null;
			List<AtAgentTarget> atAgentTargetDtl = null;
			
			for (AtAgentTargetSearch searchAtAgentTarget : atAgentTargetSearchList) {
				
				scAtAgentTarget = new AtAgentTarget();
				scAtAgentTarget.setCustomerId(searchAtAgentTarget.getCustomerId());
				scAtAgentTarget.setAgreementId(searchAtAgentTarget.getAgreementId());
				scAtAgentTarget.setTargetType(searchAtAgentTarget.getTargetType());
				/*scAtAgentTarget.setSqlMap(atAgentTargetSearch.getSqlMap());*/
				
				// 取得目标明细
				atAgentTargetDtl = atAgentTargetDao.getCompareAtAgentTargetDtl(scAtAgentTarget);
				
				Map<String, List<AtAgentTarget>> atAgentTargetList = new LinkedHashMap<String, List<AtAgentTarget>>();
				List<AtAgentTarget> atAgentTargetDtlList = null;
				
				Map<String, BigDecimal> periodTotalAmount = new LinkedHashMap<String, BigDecimal>();
				BigDecimal totalAmount = BigDecimal.ZERO;
				
				Map<String, BigDecimal> periodTrackTotalAmount = new LinkedHashMap<String, BigDecimal>();
				BigDecimal trackTotalAmount = BigDecimal.ZERO;
				
				if (atAgentTargetDtl != null && atAgentTargetDtl.size() > 0) {
					
					for (AtAgentTarget atAgentTarget : atAgentTargetDtl) {

						if (atAgentTarget.getTotalAmount() != null) {

							totalAmount = new BigDecimal(atAgentTarget.getTotalAmount());
						} else {
							totalAmount = BigDecimal.ZERO;
						}
						
						if (atAgentTarget.getTrackAmount() != null) {

							trackTotalAmount = new BigDecimal(atAgentTarget.getTrackAmount());
						} else {
							trackTotalAmount = BigDecimal.ZERO;
						}

						if (!atAgentTargetList.containsKey(atAgentTarget.getPeriod())) {
							atAgentTargetDtlList = new ArrayList<AtAgentTarget>();
							
							if (StringUtils.equals(searchAtAgentTarget.getTargetType(), CommonConstants.TARGET_TYPE_1)) {
								
								periodTotalAmount.put(atAgentTarget.getPeriod(), totalAmount);
								periodTrackTotalAmount.put(atAgentTarget.getPeriod(), trackTotalAmount);
							}
						} else {
							
							if (StringUtils.equals(searchAtAgentTarget.getTargetType(), CommonConstants.TARGET_TYPE_1)) {
//								periodTotalAmount.put(atAgentTarget.getPeriod(), totalAmount.add(periodTotalAmount.get(atAgentTarget.getPeriod())));
								periodTrackTotalAmount.put(atAgentTarget.getPeriod(), trackTotalAmount.add(periodTrackTotalAmount.get(atAgentTarget.getPeriod())));
							}
						}
						
						atAgentTargetDtlList.add(atAgentTarget);
						atAgentTargetList.put(atAgentTarget.getPeriod(), atAgentTargetDtlList);
					}
					searchAtAgentTarget.setPeriodList(atAgentTargetList);
					
					searchAtAgentTarget.setPeriodTotalAmount(periodTotalAmount);
					
					searchAtAgentTarget.setPeriodTrackTotalAmount(periodTrackTotalAmount);

					searchAtAgentTarget.setListSize(atAgentTargetDtl.size() + atAgentTargetList.size());
				}
			}
		}
		
		page.setList(atAgentTargetSearchList);

		return page;
	}

	/**
	 * 查询结果取得
	 * 
	 * @param page
	 * @param atAgentTargetSearch
	 * @return
	 */
	public Page<AtAgentTargetSearch> findAtAgentTargetPage(Page<AtAgentTargetSearch> page,
			AtAgentTargetSearch atAgentTargetSearch) {
		atAgentTargetSearch.setPage(page);
		
		// 依据检索条件取得代理商明细
		List<AtAgentTargetSearch> atAgentTargetSearchList = atAgentTargetDao.findAtAgentTargetPageList(atAgentTargetSearch);
		
		// 取得阶段明细
		if (atAgentTargetSearchList != null && atAgentTargetSearchList.size() > 0) {
			
			AtAgentTarget scAtAgentTarget = null;
			List<AtAgentTarget> atAgentTargetDtl = null;
			
			for (AtAgentTargetSearch searchAtAgentTarget : atAgentTargetSearchList) {
				
				if (!StringUtils.isEmptyNull(searchAtAgentTarget.getTargetType())) {

					scAtAgentTarget = new AtAgentTarget();
					scAtAgentTarget.setCustomerId(searchAtAgentTarget.getCustomerId());
					scAtAgentTarget.setAgreementId(searchAtAgentTarget.getAgreementId());
					scAtAgentTarget.setTargetType(searchAtAgentTarget.getTargetType());
					
					// 取得目标明细
					atAgentTargetDtl = atAgentTargetDao.getAtAgentTargetDtl(scAtAgentTarget);
					
					Map<String, List<AtAgentTarget>> atAgentTargetList = new LinkedHashMap<String, List<AtAgentTarget>>();
					List<AtAgentTarget> atAgentTargetDtlList = null;
					
					Map<String, BigDecimal> periodTotalAmount = new LinkedHashMap<String, BigDecimal>();
					BigDecimal totalAmount = BigDecimal.ZERO;
					
					if (atAgentTargetDtl != null && atAgentTargetDtl.size() > 0) {
						
						for (AtAgentTarget atAgentTarget : atAgentTargetDtl) {

							if (atAgentTarget.getTotalAmount() != null) {

								totalAmount = new BigDecimal(atAgentTarget.getTotalAmount());
							} else {
								totalAmount = BigDecimal.ZERO;
							}

							if (!atAgentTargetList.containsKey(atAgentTarget.getPeriod())) {
								atAgentTargetDtlList = new ArrayList<AtAgentTarget>();
								
								if (StringUtils.equals(searchAtAgentTarget.getTargetType(), CommonConstants.TARGET_TYPE_1)) {
									
									periodTotalAmount.put(atAgentTarget.getPeriod(), totalAmount);
								}
							} else {
								
								if (StringUtils.equals(searchAtAgentTarget.getTargetType(), CommonConstants.TARGET_TYPE_1)) {
									periodTotalAmount.put(atAgentTarget.getPeriod(), totalAmount.add(periodTotalAmount.get(atAgentTarget.getPeriod())));
								}
							}
							
							atAgentTargetDtlList.add(atAgentTarget);
							atAgentTargetList.put(atAgentTarget.getPeriod(), atAgentTargetDtlList);
						}
						searchAtAgentTarget.setPeriodList(atAgentTargetList);
						
						searchAtAgentTarget.setPeriodTotalAmount(periodTotalAmount);

						searchAtAgentTarget.setListSize(atAgentTargetDtl.size() + atAgentTargetList.size());
					}
				}
			}
		}
		
		page.setList(atAgentTargetSearchList);

		return page;
	}

	/**
	 * 根据协议ID取得目标明细
	 * 
	 * @param agreementId
	 * @return
	 */
	public AtAgentTargetModal getAtAgentTargetDtl(String agreementId) {
		
		// 依据检索条件取得代理商明细
		AtAgentTargetSearch atAgentTargetSearch = atAgentTargetDao.getCmAgreementInfo(agreementId);
		
		AtAgentTargetModal atAgentTargetModal = null;
		
		// 取得阶段明细
		if (atAgentTargetSearch != null) {
			
			atAgentTargetModal = new AtAgentTargetModal();
			atAgentTargetModal.setAgreementPatiesId(atAgentTargetSearch.getCustomerId());
			atAgentTargetModal.setAgreementId(atAgentTargetSearch.getAgreementId());
			atAgentTargetModal.setAgreementPatiesName(atAgentTargetSearch.getCustomerName());
			atAgentTargetModal.setValidityDateFrom(atAgentTargetSearch.getValidityDateFrom());
			
			AtAgentTarget scAtAgentTarget = null;
			List<AtAgentTarget> atAgentTargetDtl = null;

			scAtAgentTarget = new AtAgentTarget();
			scAtAgentTarget.setCustomerId(atAgentTargetSearch.getCustomerId());
			scAtAgentTarget.setAgreementId(atAgentTargetSearch.getAgreementId());
			
			// 取得目标明细
			atAgentTargetDtl = atAgentTargetDao.getAtAgentTargetDtl(scAtAgentTarget);
			
			if (atAgentTargetDtl != null && atAgentTargetDtl.size() > 0) {
				atAgentTargetModal.setTargetType(atAgentTargetDtl.get(0).getTargetType());
				atAgentTargetModal.setAtAgentTargetDtlList(atAgentTargetDtl);
			}
		}
		
		return atAgentTargetModal;
	}

	/**
	 * 整合性验证
	 */
	public String atAgentTargetInfoValidator(AtAgentTargetModal atAgentTargetModal) {
		
		List<String> message = new ArrayList<String>();

		String targetType = atAgentTargetModal.getTargetType();
//		Map<String, String> periodMap = new HashMap<String, String>();
		Map<String, Map<String, String>> periodMap = new HashMap<String, Map<String, String>>();
		Map<String, String> periodMaterialNo = null;
		if (StringUtils.equals(targetType, CommonConstants.TARGET_TYPE_1)) {
			for (AtAgentTarget atAgentTarget : atAgentTargetModal.getAtAgentTargetDtlList()) {
				if (periodMap.containsKey(atAgentTarget.getPeriod())) {
					message.add("阶段" + DictUtils.getDictLabel(atAgentTarget.getPeriod(), "DM0046", "") + "的目标制定重复。");
				} else {
//					periodMap.put(atAgentTarget.getPeriod(), atAgentTarget.getPeriod());
					periodMap.put(atAgentTarget.getPeriod(), null);
				}
			}
		} else if (StringUtils.equals(targetType, CommonConstants.TARGET_TYPE_2)) {
			for (AtAgentTarget atAgentTarget : atAgentTargetModal.getAtAgentTargetDtlList()) {

				if (periodMap.containsKey(atAgentTarget.getPeriod())) {
					periodMaterialNo = periodMap.get(atAgentTarget.getPeriod());
					if (periodMaterialNo.containsKey(atAgentTarget.getMaterialNo())) {

						message.add("阶段" + DictUtils.getDictLabel(atAgentTarget.getPeriod(), "DM0046", "") + "的型号" + atAgentTarget.getMaterialNo() + "目标制定重复。");
					} else {
						periodMaterialNo.put(atAgentTarget.getMaterialNo(), atAgentTarget.getMaterialNo());
						periodMap.put(atAgentTarget.getPeriod(), periodMaterialNo);
					}
				} else {

					periodMaterialNo = new HashMap<String, String>();
					periodMaterialNo.put(atAgentTarget.getMaterialNo(), atAgentTarget.getMaterialNo());

//					periodMap.put(atAgentTarget.getPeriod(), atAgentTarget.getPeriod());
					periodMap.put(atAgentTarget.getPeriod(), periodMaterialNo);
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

	/**
	 * 代理商目标制定
	 */
	@Transactional(readOnly = false)
	public void saveAtAgentTarget(AtAgentTargetModal atAgentTargetModal) {
		
		String agreementId = atAgentTargetModal.getAgreementId();
		String customerId = atAgentTargetModal.getAgreementPatiesId();
		String targetType = atAgentTargetModal.getTargetType();
		
		AtAgentTarget delAtAgentTarget = new AtAgentTarget();
		delAtAgentTarget.setAgreementId(agreementId);
		delAtAgentTarget.setCustomerId(customerId);
		
		atAgentTargetDao.deleteInfo(delAtAgentTarget);
		
		AtAgentTarget insAtAgentTarget = null;
		for (AtAgentTarget atAgentTarget : atAgentTargetModal.getAtAgentTargetDtlList()) {
			insAtAgentTarget = new AtAgentTarget();
			
			insAtAgentTarget.setAgreementId(agreementId);
			insAtAgentTarget.setCustomerId(customerId);
			insAtAgentTarget.setTargetType(targetType);
			
			insAtAgentTarget.setPeriod(atAgentTarget.getPeriod());
			if (StringUtils.equals(targetType, CommonConstants.TARGET_TYPE_1)) {

				insAtAgentTarget.setTotalAmount(atAgentTarget.getTotalAmount());
			} else if (StringUtils.equals(targetType, CommonConstants.TARGET_TYPE_2)) {

				insAtAgentTarget.setMaterialNo(atAgentTarget.getMaterialNo());
				insAtAgentTarget.setNum(atAgentTarget.getNum());
			}
			
			insAtAgentTarget.preInsert();
			atAgentTargetDao.insert(insAtAgentTarget);
			
		}
	}

	/**
	 * 导出结果取得
	 * 
	 * @param atAgentTargetSearch
	 * @return
	 */
	public List<AtAgentTargetSearch> getAtAgentTargetList(AtAgentTargetSearch atAgentTargetSearch) {
		
		// 依据检索条件取得代理商明细
		List<AtAgentTargetSearch> atAgentTargetSearchList = atAgentTargetDao.findCompareAtAgentTargetPageList(atAgentTargetSearch);
		
		// 取得阶段明细
		if (atAgentTargetSearchList != null && atAgentTargetSearchList.size() > 0) {
			
			AtAgentTarget scAtAgentTarget = null;
			List<AtAgentTarget> atAgentTargetDtl = null;
			
			for (AtAgentTargetSearch searchAtAgentTarget : atAgentTargetSearchList) {
				
				scAtAgentTarget = new AtAgentTarget();
				scAtAgentTarget.setCustomerId(searchAtAgentTarget.getCustomerId());
				scAtAgentTarget.setAgreementId(searchAtAgentTarget.getAgreementId());
				scAtAgentTarget.setTargetType(searchAtAgentTarget.getTargetType());
				
				// 取得目标明细
				atAgentTargetDtl = atAgentTargetDao.getCompareAtAgentTargetDtl(scAtAgentTarget);
				
				Map<String, List<AtAgentTarget>> atAgentTargetList = new LinkedHashMap<String, List<AtAgentTarget>>();
				List<AtAgentTarget> atAgentTargetDtlList = null;
				
				Map<String, BigDecimal> periodTotalAmount = new LinkedHashMap<String, BigDecimal>();
				BigDecimal totalAmount = BigDecimal.ZERO;
				
				Map<String, BigDecimal> periodTrackTotalAmount = new LinkedHashMap<String, BigDecimal>();
				BigDecimal trackTotalAmount = BigDecimal.ZERO;
				
				if (atAgentTargetDtl != null && atAgentTargetDtl.size() > 0) {
					
					for (AtAgentTarget atAgentTarget : atAgentTargetDtl) {

						if (atAgentTarget.getTotalAmount() != null) {

							totalAmount = new BigDecimal(atAgentTarget.getTotalAmount());
						} else {
							totalAmount = BigDecimal.ZERO;
						}
						
						if (atAgentTarget.getTrackAmount() != null) {

							trackTotalAmount = new BigDecimal(atAgentTarget.getTrackAmount());
						} else {
							trackTotalAmount = BigDecimal.ZERO;
						}

						if (!atAgentTargetList.containsKey(atAgentTarget.getPeriod())) {
							atAgentTargetDtlList = new ArrayList<AtAgentTarget>();
							
							if (StringUtils.equals(searchAtAgentTarget.getTargetType(), CommonConstants.TARGET_TYPE_1)) {
								
								periodTotalAmount.put(atAgentTarget.getPeriod(), totalAmount);
								periodTrackTotalAmount.put(atAgentTarget.getPeriod(), trackTotalAmount);
							}
						} else {
							
							if (StringUtils.equals(searchAtAgentTarget.getTargetType(), CommonConstants.TARGET_TYPE_1)) {
//								periodTotalAmount.put(atAgentTarget.getPeriod(), totalAmount.add(periodTotalAmount.get(atAgentTarget.getPeriod())));
								periodTrackTotalAmount.put(atAgentTarget.getPeriod(), trackTotalAmount.add(periodTrackTotalAmount.get(atAgentTarget.getPeriod())));
							}
						}
						
						atAgentTargetDtlList.add(atAgentTarget);
						atAgentTargetList.put(atAgentTarget.getPeriod(), atAgentTargetDtlList);
					}
					searchAtAgentTarget.setPeriodList(atAgentTargetList);
					
					searchAtAgentTarget.setPeriodTotalAmount(periodTotalAmount);
					
					searchAtAgentTarget.setPeriodTrackTotalAmount(periodTrackTotalAmount);

					searchAtAgentTarget.setListSize(atAgentTargetDtl.size() + atAgentTargetList.size());
				}
			}
		}

		return atAgentTargetSearchList;
	}
}