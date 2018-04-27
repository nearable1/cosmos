/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.SnInfo;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.ListUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.rm.dao.RmRepairInfoDao;
import com.inbody.crm.rm.domain.RmRepairInfoEx;
import com.inbody.crm.rm.domain.RmRepairInput;
import com.inbody.crm.rm.domain.RmRepairListExcel;
import com.inbody.crm.rm.domain.RmRepairListSearch;
import com.inbody.crm.rm.domain.RmRepairSnInfo;
import com.inbody.crm.rm.domain.RmRepairSnSearch;
import com.inbody.crm.rm.entity.RmRepairInfo;

/**
 * 单表生成Service
 * 
 * @author yangj
 * @version 2017-08-16
 */
@Service
@Transactional(readOnly = true)
public class RmRepairService {

	@Autowired
	private RmRepairInfoDao rmRepairInfoDao;

	@Autowired
	private CommonService commonService;

    /**
     * 维修sn信息查询信息取得
     * 
     * @param page
     * @param search
     * @return
     */
    public Page<RmRepairSnSearch> findRepairSnList(Page<RmRepairSnSearch> page, RmRepairSnSearch search) {

        search.setPage(page);

        // 合同信息查询
        if (StringUtils.equals(search.getSearchType(), CommonConstants.REPAIR_SN_SEARCH_TYPE_1)) {

            page.setList(rmRepairInfoDao.getRepairOrderInfo(search));

        } else if (StringUtils.equals(search.getSearchType(), CommonConstants.REPAIR_SN_SEARCH_TYPE_2)) {

            // 维修信息查询
            page.setList(rmRepairInfoDao.getRepairInfo(search));

        } else if (StringUtils.equals(search.getSearchType(), CommonConstants.REPAIR_SN_SEARCH_TYPE_3)) {

            // 最新检测信息查询
            page.setList(rmRepairInfoDao.getTestingInfo(search));

        }

        return page;
    }

    /**
     * 维修录入信息取得
     * 
     * @param snNo
     *            sn编号
     * @param id
     *            维修记录id
     * 
     * @return 维修录入信息
     */
    public RmRepairInput getRepairInput(String snNo, String id) {
        // 维修录入信息
        RmRepairInput repairInput = new RmRepairInput();

        // 维修信息
        RmRepairInfoEx repairInfo = null;
        // 维修记录id为空
        if (StringUtils.isBlank(id)) {
            // 新建维修信息
            repairInfo = new RmRepairInfoEx();
            repairInfo.setCreateBy(UserUtils.getUser());
            repairInfo.setSnNo(snNo);
            // 报修日=当前日期
            repairInfo.setAskRepairDate(new Date());
            // 负责工程师
            repairInfo.setResponsiblePersonId(UserUtils.getUser().getId());

        } else {
            // 根据维修记录id取得维修信息
            repairInfo = rmRepairInfoDao.getRepairInfoById(id);
            // 维修记录存在check
            if (repairInfo == null) {
                throw new ServiceException("维修信息取得失败：指定的维修信息不存在！");
            }
        }

        // 历史维修记录及信息设定
        this.setSnInfoAndHis(repairInfo.getSnNo(), repairInput);

        // 历史维修记录存在 且为新建维修记录录入时
        if (ListUtils.size(repairInput.getRepairHisList()) > 0
                && StringUtils.isBlank(id)) {
            // 最近的历史维修记录取得
            RmRepairInfoEx lastRepairHis = repairInput.getRepairHisList().get(0);
            // 客户信息取得
            repairInfo.setRepairCusName(lastRepairHis.getRepairCusName());
            // 联系人
            repairInfo.setContactsName(lastRepairHis.getContactsName());
            // 电话
            repairInfo.setTelephone(lastRepairHis.getTelephone());
            // 地址
            repairInfo.setAddress(lastRepairHis.getAddress());
        }

        // 维修信息设定
        repairInput.setRepairInfo(repairInfo);

        return repairInput;
    }

	/**
	 * 设置维修录入信息的sn信息及其历史维修记录
	 * 
	 * @param snNo     sn编号
	 * @param repairInput 维修录入信息
	 * 
	 */
	public void setSnInfoAndHis(String snNo, RmRepairInput repairInput) {
		// 维修录入信息
		if (repairInput == null) {
			repairInput = new RmRepairInput();
		}

        // sn信息取得
        RmRepairSnInfo repairSnInfo = rmRepairInfoDao.getRepairSnInfo(snNo);
        // 历史维修记录查询
        List<RmRepairInfoEx> repairHisList = rmRepairInfoDao
                .getRepairHistory(snNo);

		repairInput.setSnInfo(repairSnInfo);
		repairInput.setRepairHisList(repairHisList);
	}

	/**
	 * 保存维修录入信息
	 * 
	 * @param repairInput 维修录入信息
	 * 
	 */
	@Transactional(readOnly = false)
	public String saveRepairInput(RmRepairInput repairInput) {

		// sn号码不存在
		if (StringUtils.isBlank(repairInput.getRepairInfo().getSnNo())) {
			throw new ServiceException("保存失败：数据不整合！");
		}

		// check sn号码是否在DB中存在
//		if (rmRepairInfoDao.getRepairSnCount(repairInput.getRepairInfo()
//				.getSnNo()) == 0) {
//			throw new ServiceException("保存失败：维修物件不存在！");
//		}
		
		SnInfo snInfo = rmRepairInfoDao.getSnInfo(repairInput.getRepairInfo().getSnNo());
		if (snInfo == null) {
			throw new ServiceException("保存失败：维修物件不存在！");
		} else {
			if (!StringUtils.equals(snInfo.getLbSnNo(), repairInput.getSnInfo().getLbSnNo())) {
				snInfo.setLbSnNo(repairInput.getSnInfo().getLbSnNo());
				
				snInfo.preUpdate();
				rmRepairInfoDao.updateSnInfo(snInfo);
			}
		}

		// 维修记录id
		String id = "";
		// 维修信息取得
		RmRepairInfo repairInfo = repairInput.getRepairInfo();

		// 处理方式为"返厂"并且替换样机"有"时，样机信息必须填写
		if (StringUtils.equals(repairInfo.getRepairMethod(),
				CommonConstants.REPAIR_METHOD_4)
				&& StringUtils.equals(repairInfo.getIfPrototype(),
						CommonConstants.YES)
				&& !StringUtils.isBlank(repairInfo.getPrototypeSnNo())) {
			RmRepairInfoEx ptyinfo = rmRepairInfoDao.getPrototypeInfo(repairInfo
					.getPrototypeSnNo());
			if (ptyinfo == null || StringUtils.isBlank(ptyinfo.getSnNo())) {
				throw new ServiceException("保存失败：替换机样不存在！");
			}
		}

		// 维修编号为空，insert
		if (StringUtils.isBlank(repairInfo.getId())) {

			// 维修编号取得
			String repairNo = DateUtils.getDate("yyMMdd")
					+ commonService.getNextSequence(
							CommonConstants.TRANSACTION_REPAIR, DateUtils.getDate("yyyy"), 4);
			// 维修编号设定
			repairInfo.setRepairNo(repairNo);
			// 报修方式
			repairInfo.setRepairWay(blank2Null(repairInfo.getRepairWay()));
			// 处理方式
			repairInfo.setRepairMethod(blank2Null(repairInfo.getRepairMethod()));
			// 故障大分类
			repairInfo.setFaultType(blank2Null(repairInfo.getFaultType()));
			// 故障小分类
			repairInfo.setFaultType2(blank2Null(repairInfo.getFaultType2()));
			// 处理状态
			repairInfo.setRepairStatus(blank2Null(repairInfo.getRepairStatus()));
			// 咨询问题分类
			repairInfo.setConsultType(blank2Null(repairInfo.getConsultType()));

			repairInfo.preInsert();
			rmRepairInfoDao.insert(repairInfo);
			
			id = repairInfo.getId();

		} else {
			// 更新维修信息
			RmRepairInfo dbRepairInfo = rmRepairInfoDao.get(repairInfo.getId());

			// 更新验证
			if (dbRepairInfo == null) {
				throw new ServiceException("保存失败：需要更新的维修信息记录不存在！");
			}

			// 更新日时比较
			if (DateUtils.compareDate(repairInfo.getUpdateDate(),
					dbRepairInfo.getUpdateDate()) != 0) {
				throw new ServiceException("保存失败：数据已经被更改！");
			}

			// 报价日期
			dbRepairInfo.setAskRepairDate(repairInfo.getAskRepairDate());
			// 维修分类
			dbRepairInfo.setRepairType(blank2Null(repairInfo.getRepairType()));
			// 单位名称
			dbRepairInfo.setRepairCusName(repairInfo.getRepairCusName());
			// 联系人
			dbRepairInfo.setContactsName(repairInfo.getContactsName());
			// 电话
			dbRepairInfo.setTelephone(repairInfo.getTelephone());
			// 地址
			dbRepairInfo.setAddress(repairInfo.getAddress());
			// 问题描述
			dbRepairInfo.setIssueDescribe(repairInfo.getIssueDescribe());
			// 报修方式
			dbRepairInfo.setRepairWay(blank2Null(repairInfo.getRepairWay()));
			// 负责工程师
			dbRepairInfo.setResponsiblePersonId(repairInfo
					.getResponsiblePersonId());
			// 备注
			dbRepairInfo.setNewRemarks(repairInfo.getNewRemarks());
			// 处理方式
			dbRepairInfo.setRepairMethod(blank2Null(repairInfo
					.getRepairMethod()));
			// 故障大分类
			dbRepairInfo.setFaultType(blank2Null(repairInfo.getFaultType()));
			// 故障小分类
			dbRepairInfo.setFaultType2(blank2Null(repairInfo.getFaultType2()));
			// 处理日期
			dbRepairInfo.setProcessDate(repairInfo.getProcessDate());
			// 处理状态
			dbRepairInfo.setRepairStatus(blank2Null(repairInfo
					.getRepairStatus()));
			// 处理状态说明
			dbRepairInfo.setStatusRemarks(repairInfo.getStatusRemarks());

			// 咨询时
			if (StringUtils.equals(repairInfo.getRepairMethod(),
					CommonConstants.REPAIR_METHOD_1)) {
				// 咨询问题分类
				dbRepairInfo.setConsultType(blank2Null(repairInfo
						.getConsultType()));
				// 情况确认
				dbRepairInfo.setIssueDetail(repairInfo.getIssueDetail());

				// 上门
			} else if (StringUtils.equals(repairInfo.getRepairMethod(),
					CommonConstants.REPAIR_METHOD_2)) {

				// 情况确认
				dbRepairInfo.setIssueDetail(repairInfo.getIssueDetail());
				// 处理内容
				dbRepairInfo.setProcessingContent(repairInfo
						.getProcessingContent());

				// 远程
			} else if (StringUtils.equals(repairInfo.getRepairMethod(),
					CommonConstants.REPAIR_METHOD_3)) {

				// 情况确认
				dbRepairInfo.setIssueDetail(repairInfo.getIssueDetail());
				// 处理内容
				dbRepairInfo.setProcessingContent(repairInfo
						.getProcessingContent());

				// 返厂
			} else if (StringUtils.equals(repairInfo.getRepairMethod(),
					CommonConstants.REPAIR_METHOD_4)) {

				// 情况确认
				dbRepairInfo.setIssueDetail(repairInfo.getIssueDetail());
				// 处理内容
				dbRepairInfo.setProcessingContent(repairInfo
						.getProcessingContent());
				// 维修机到货时间
				dbRepairInfo.setMaintenanceDateFrom(repairInfo
						.getMaintenanceDateFrom());
				// 维修机出训时间
				dbRepairInfo.setMaintenanceDateTo(repairInfo
						.getMaintenanceDateTo());
				// 是否有替代样机
				dbRepairInfo.setIfPrototype(repairInfo.getIfPrototype());

				// 替代样机有
				if (StringUtils.equals(repairInfo.getIfPrototype(),
						CommonConstants.YES)) {
					// 样机sn
					dbRepairInfo
							.setPrototypeSnNo(repairInfo.getPrototypeSnNo());
					// 样机发出时间
					dbRepairInfo.setPrototypeDateFrom(repairInfo
							.getPrototypeDateFrom());
					// 样机返回时间
					dbRepairInfo.setPrototypeDateTo(repairInfo
							.getPrototypeDateTo());
				} else {
					// 样机sn
					dbRepairInfo.setPrototypeSnNo(null);
					// 样机发出时间
					dbRepairInfo.setPrototypeDateFrom(null);
					// 样机返回时间
					dbRepairInfo.setPrototypeDateTo(null);
				}
			}

			// 咨询、上门、远程时
			if (StringUtils.equals(repairInfo.getRepairMethod(),
					CommonConstants.REPAIR_METHOD_1)
					|| StringUtils.equals(repairInfo.getRepairMethod(),
							CommonConstants.REPAIR_METHOD_2)
					|| StringUtils.equals(repairInfo.getRepairMethod(),
							CommonConstants.REPAIR_METHOD_3)) {

				if (StringUtils.equals(repairInfo.getRepairMethod(),
						CommonConstants.REPAIR_METHOD_1)) {
					// 处理内容
					dbRepairInfo.setProcessingContent(null);
				}

				if (StringUtils.equals(repairInfo.getRepairMethod(),
						CommonConstants.REPAIR_METHOD_2)
						|| StringUtils.equals(repairInfo.getRepairMethod(),
								CommonConstants.REPAIR_METHOD_3)) {
					// 咨询问题分类
					dbRepairInfo.setConsultType(null);
				}

				// 维修机到货时间
				dbRepairInfo.setMaintenanceDateFrom(null);
				// 维修机出训时间
				dbRepairInfo.setMaintenanceDateTo(null);
				// 是否有替代样机
				dbRepairInfo.setIfPrototype(null);
				// 样机sn
				dbRepairInfo.setPrototypeSnNo(null);
				// 样机发出时间
				dbRepairInfo.setPrototypeDateFrom(null);
				// 样机返回时间
				dbRepairInfo.setPrototypeDateTo(null);

			} else if (StringUtils.equals(repairInfo.getRepairMethod(),
					CommonConstants.REPAIR_METHOD_4)) {
				// 咨询问题分类
				dbRepairInfo.setConsultType(null);
			}

			dbRepairInfo.preUpdate();
			rmRepairInfoDao.update(dbRepairInfo);

			id = dbRepairInfo.getId();
		}
		
		return id;
	}
	
	/**
	 * 获取样机信息
	 * 
	 * @param typSn
	 *            样机sn
	 * 
	 * @return 样机信息
	 */
	public RmRepairInfoEx getPrototypeInfo(String typSn) {

		RmRepairInfoEx ptyInfo = rmRepairInfoDao.getPrototypeInfo(typSn);
		return ptyInfo;

	}

	/**
	 * 维修记录管理分页数据查询
	 * 
	 * @param page
	 *            分页信息
	 * @param search
	 *            查询条件信息
	 * 
	 * @return 维修信息分页list
	 */
	public Page<RmRepairListSearch> findRepairPageList(
			Page<RmRepairListSearch> page, RmRepairListSearch search) {

		search.setPage(page);
		List<RmRepairListSearch> list = rmRepairInfoDao
				.getRepairInfoList(search);
		page.setList(list);
		return page;
	}

    /**
     * 维修记录管理excel导出数据取得
     * 
     * @param search
     *            查询条件信息
     * 
     * @return 维修记录管理excel导出数据list
     */
    public List<RmRepairListExcel> exportRepairList(RmRepairListSearch search) {

        List<RmRepairListSearch> list = rmRepairInfoDao.getRepairInfoExportList(search);

        RmRepairListExcel repairExcel = null;
        List<RmRepairListExcel> repairExcelList = Lists.newArrayList();
        NumberFormat nf = new DecimalFormat("#,##0.00");
        for (RmRepairListSearch item : list) {
            repairExcel = new RmRepairListExcel();
            // 机器S/N
            repairExcel.setSnNo(item.getSnNo());
            // 机器型号
            repairExcel.setModel(item.getMcModel());
            // 生产日期
            if (item.getProductionDate() == null) {
                repairExcel.setProductionDate("");
            } else {
                repairExcel.setProductionDate(
                        DateUtils.formatDate(item.getProductionDate()));
            }
            // 安装日期
            if (item.getActualInstallDate() == null) {
                repairExcel.setActualInstallDate("");
            } else {
                repairExcel.setActualInstallDate(
                        DateUtils.formatDate(item.getActualInstallDate()));
            }
            // 单位名称
            repairExcel.setRepairCusName(item.getRepairCusName());
            // 报修日期
            if (item.getAskRepairDate() == null) {
                repairExcel.setAskRepairDate("");
            } else {
                repairExcel
                        .setAskRepairDate(DateUtils.formatDate(item.getAskRepairDate()));
            }
            // 维修分类
            repairExcel.setRepairType(item.getRepairType());
            // 处理方式
            repairExcel.setRepairMethod(item.getRepairMethod());
            // 情况确认
            repairExcel.setIssueDetail(item.getIssueDetail());
            // 处理内容
            repairExcel.setProcessingContent(item.getProcessingContent());
            // 最终使用配件
            repairExcel.setAcMaterialName(item.getAcMaterialName());
            // 数量
            repairExcel.setNum(item.getNum());
            // 处理状态
            repairExcel.setRepairStatus(item.getRepairStatus());
            // 开票状态
            repairExcel.setInvoiceStatus(item.getInvoiceStatus());
            // 收款状态
            repairExcel.setReceiveStatus(item.getReceiveStatus());
            // 最终报价单金额
            if (item.getAmount() == null) {
                repairExcel.setTotalAmount("");
            } else {
                repairExcel.setTotalAmount(nf.format(item.getAmount()));
            }
            // 负责工程师
            repairExcel.setEngineer(item.getEngineer());
            // 是否占用
            if (StringUtils.isBlank(item.getIfOccupy())) {
                repairExcel.setIfOccupy(CommonConstants.NO);
            } else {
                repairExcel.setIfOccupy(item.getIfOccupy());
            }

            repairExcelList.add(repairExcel);
        }

        return repairExcelList;
    }

	/**
	 * code值如果为空转换为null
	 * 
	 * @param code
	 */
	private String blank2Null(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		} else {
			return code;
		}
	}

	/**
	 * 取得SN信息
	 * 
	 * @param snNo     sn编号
	 * 
	 */
	public SnInfo getSnInfo(String snNo) {
		// 维修录入信息
		if (snNo == null) {
			return new SnInfo();
		}

        // sn信息取得
		SnInfo snInfo = rmRepairInfoDao.getSnInfo(snNo);

		if (snInfo == null) {
			return new SnInfo();
		}
		
		return snInfo;
	}
}