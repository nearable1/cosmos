/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.ListUtils;
import com.inbody.crm.common.utils.NumberToCNUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportFileDtl;
import com.inbody.crm.common.utils.excel.ExportFileHead;
import com.inbody.crm.common.utils.excel.FileExportUtil;
import com.inbody.crm.modules.act.dao.ActDao;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.modules.sys.utils.DictUtils;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.rm.dao.ImInvoiceDao;
import com.inbody.crm.rm.dao.ImInvoiceDtlDao;
import com.inbody.crm.rm.dao.RmQuotaSelectDao;
import com.inbody.crm.rm.dao.RmQuotationDao;
import com.inbody.crm.rm.dao.RmQuotationDtlDao;
import com.inbody.crm.rm.dao.RmRepairInfoDao;
import com.inbody.crm.rm.dao.SmMatInfoDao;
import com.inbody.crm.rm.dao.SmSnInfoDao;
import com.inbody.crm.rm.dao.SmStorageInfoDao;
import com.inbody.crm.rm.dao.SmWarehouseInfoDao;
import com.inbody.crm.rm.domain.QuotaSelect;
import com.inbody.crm.rm.domain.RmQuotationDtlExcel;
import com.inbody.crm.rm.domain.RmRepairInfoEx;
import com.inbody.crm.rm.entity.CmCustomerInfo;
import com.inbody.crm.rm.entity.ImInvoice;
import com.inbody.crm.rm.entity.ImInvoiceDtl;
import com.inbody.crm.rm.entity.RmQuotation;
import com.inbody.crm.rm.entity.RmQuotationDtl;
import com.inbody.crm.rm.entity.SmMatInfo;
import com.inbody.crm.rm.entity.SmSnInfo;
import com.inbody.crm.rm.entity.SmStorageInfo;
import com.inbody.crm.rm.entity.SmWarehouseInfo;

/**
 * 报价单Service
 * 
 * @author yangj
 * @version 2017-08-16
 */
@Service
@Transactional(readOnly = true)
public class RmQuotationService {

	@Value("${quotaTemplatePath}")
	private String quotaTemplatePath;
	
	private final String TEMPLATE_NAME_01 = "维修预报价单.xls";
	
	private final String TEMPLATE_NAME_02 = "维修终报价单.xls";
	
	private final String EXPORT_FILE_NAME_01 = "预报价单";
	
	private final String EXPORT_FILE_NAME_02 = "维修合同";
	
	private final String EXPORT_FILE_SUFIX = ".xls";

    @Autowired
    private RmQuotationDao rmQuotationDao;

    @Autowired
    private RmQuotationDtlDao rmQuotationDtlDao;

    @Autowired
    private ImInvoiceDao imInvoiceDao;

    @Autowired
    private ImInvoiceDtlDao imInvoiceDtlDao;

    @Autowired
    private SmWarehouseInfoDao smWarehouseInfoDao;

    @Autowired
    private SmStorageInfoDao smStorageInfoDao;

    @Autowired
    private SmMatInfoDao smMatInfoDao;

    @Autowired
    private SmSnInfoDao smSnInfoDao;

    @Autowired
    private RmRepairInfoDao rmRepairInfoDao;
    
    @Autowired
    private RmQuotaSelectDao rmQuotaSelectDao;

    @Autowired
    private AttachmentsService attachmentsService;

    @Autowired
    private CommonService commonService;

	@Autowired
	private ActTaskService actTaskService;

    @Autowired
    protected ActDao actDao;

    /**
     * 根据id取得报价单信息
     */
    public RmQuotation get(String id) {
        // 报价单信息取得
        RmQuotation quotation = rmQuotationDao.getQuotaById(id);
        // 报价单为空
        if (quotation == null) {
            throw new ServiceException("报价单信息得失败，报价单不存在！");
        }

        // 报价单明细取得
        List<RmQuotationDtl> quotaDtlList = rmQuotationDtlDao.getQuotaDtlListById(id);
        // 附件信息取得
        List<CoAttachments> attachmentsList = attachmentsService
                .getAttachmentList(quotation.getQuotationNo());

        // 最终报价单时
        if (StringUtils.equals(quotation.getQuotationType(),
                CommonConstants.QUOTATION_TYPE_2)) {
            // 报价单总金额计算
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (RmQuotationDtl qtDtl : quotaDtlList) {
                totalAmount = StringUtils.add(totalAmount, qtDtl.getActAmount());
            }
            // 报价单明细总金额
            quotation.setTotalAmount(totalAmount);
            // 报价单明细默认发票税金 = 开票金额/1.17*0.17
            quotation.setDefaultTax(
                    totalAmount.divide(new BigDecimal(1.17), 2, BigDecimal.ROUND_HALF_UP)
                            .multiply(new BigDecimal(0.17)));
        }

        // 报价单明细是否保内默认设定
        quotation.setDefaultWarranty(getDefaultWarranty(quotation.getRepairType()));
        // 报价单对应的sn设定
        quotation.setSnNo(quotation.getSnNo());
        quotation.setQuotationDtlList(quotaDtlList);
        quotation.setAttachmentsList(attachmentsList);
        return quotation;
    }

    /**
     * 最终报价单信息取得
     * 
     * @param id
     *            最终报价单id
     * @param repairNo
     *            维修编号
     * @return 最终报价单信息
     */
    public RmQuotation getFinalQuotation(String id, String repairNo, String snNo) {

        // id不为空
        if (!StringUtils.isBlank(id)) {
            return this.get(id);
        }

        // 维修编号为空
        if (StringUtils.isBlank(repairNo)) {
            throw new ServiceException("最终报价单信息取得失败，维修编号不能为空！");
        }

        // 根据维修编号查找预报价单id
        String preId = rmQuotationDao.getPreQuotaIdByRepairNo(repairNo);
        // 预报价单存在
        if (!StringUtils.isBlank(preId)) {
            RmQuotation finalQuota = this.get(preId);
            if (finalQuota != null) {
                // 附件信息清空
                finalQuota.setAttachmentsList(null);
                // 报价单信息
                finalQuota.setId(null);
                finalQuota.setQuotationType(CommonConstants.QUOTATION_TYPE_2);
                finalQuota.setSnNo(snNo);
                finalQuota.setQuotationNo(null);
                // 报价单明细信息
                List<RmQuotationDtl> quotaDtlList = finalQuota.getQuotationDtlList();
                // 报价单明细id信息清空
                for (RmQuotationDtl qDtl : quotaDtlList) {
                    qDtl.setId(null);
                }
                return finalQuota;
            }
        }

        return null;
    }

    /**
     * 报价单发票信息取得
     * 
     * @param quotaId
     *            最终报价单id
     * @param procBusinessId
     *            发票流程业务id（发票id）
     * 
     * @return 最终报价单发票信息
     */
    public RmQuotation getQuotationInvoice(String quotaId, String procBusinessId) {
        // id不为空
        if (StringUtils.isBlank(quotaId)) {
            throw new ServiceException("报价单发票信息取得失败，数据不整合！");
        }

        // 最终报价单信息取得（不含发票）
        RmQuotation quota = this.get(quotaId);
        if (quota == null) {
            throw new ServiceException("报价单发票信息取得失败！");
        }

        // 发票头信息取得
        ImInvoice invoice = null;
        // 如果发票流程业务id不为空
        if (!StringUtils.isBlank(procBusinessId)) {
            invoice = imInvoiceDao.getInvoiceById(procBusinessId);
        } else {
            // 否则取创建时间最晚的发票
            invoice = imInvoiceDao.getLastInvoiceByQuotaNo(quota.getQuotationNo());
        }

        // 发票信息存在
        if (invoice != null) {
            // 是否可以进行发票申请：默认是
            boolean canInvoiceApply = true;
            // 根据报价单编号取得发票明细一览
            List<ImInvoiceDtl> ivcDtlList = imInvoiceDtlDao
                    .getInvoiceDtlByQuotaId(quotaId);

            // 发票明细组初始化
            List<List<ImInvoiceDtl>> invoiceDtlListGroup = Lists.newArrayList();
            // 发明细存在，对发票明细根据发票id进行分组
            if (ListUtils.size(ivcDtlList) > 0) {
                // 发明明细遍历
                String curIvcId = ivcDtlList.get(0).getAppId();
                // 红票区分
                String curRedCd = ivcDtlList.get(0).getIfRed();
                List<ImInvoiceDtl> ivcDtlListItem = Lists.newArrayList();
                for (ImInvoiceDtl ivcDtl : ivcDtlList) {

                    // 是否可以进行发票申请判断
                    if (!StringUtils.equals(ivcDtl.getWorkflowStatus(),
                            CommonConstants.WORKFLOW_STATUS_50)) {
                        canInvoiceApply = false;
                    }

                    if (!StringUtils.equals(curIvcId, ivcDtl.getAppId())
                            || !StringUtils.equals(curRedCd, ivcDtl.getIfRed())) {
                        invoiceDtlListGroup.add(ivcDtlListItem);
                        curIvcId = ivcDtl.getAppId();
                        curRedCd = ivcDtl.getIfRed();
                        ivcDtlListItem = Lists.newArrayList();
                    }
                    ivcDtlListItem.add(ivcDtl);
                }
                
                if (ListUtils.size(ivcDtlListItem) > 0) {
                    invoiceDtlListGroup.add(ivcDtlListItem);
                }
            }

            // 发票信息
            quota.setInvoice(invoice);
            // 发票明细信息取得
            quota.setInvoiceDtlListGroup(invoiceDtlListGroup);
            // 是否可以进行发票申请
            quota.setCanInvoiceApply(canInvoiceApply);

        } else {
            // 发票信息
            quota.setInvoice(new ImInvoice());
            // 发票明细组
            List<List<ImInvoiceDtl>> invoiceDtlListGroup = Lists.newArrayList();
            quota.setInvoiceDtlListGroup(invoiceDtlListGroup);
            // 是否可以进行发票申请：是
            quota.setCanInvoiceApply(true);
        }

        return quota;
    }

    /**
     * 报价单信息保存
     * 
     * @param input
     *            报价单画面输入信息
     * @return quotationId 保存成功后的报价单id
     */
    @Transactional(readOnly = false)
    public RmQuotation save(RmQuotation input) {

        RmQuotation dbQuota = null;
        if (!StringUtils.isBlank(input.getId())) {
            // 更新前报价单取得
            dbQuota = this.get(input.getId());

            // 排他性验证，更新日时不相等，数据已被更改
            if (DateUtils.compareDate(input.getUpdateDate(),
                    dbQuota.getUpdateDate()) != 0) {
                throw new ServiceException("保存失败：数据已经被更改，请取得最新数据后再操作。");
            }
        }

        // 报价单信息保存
        RmQuotation rmQuotation = this.saveQuota(input, dbQuota);
        // 报价单明细信息保存（未占用且未出库时才能保存明细）
        if ((StringUtils.equals(rmQuotation.getIfOccupy(), CommonConstants.NO)
                || StringUtils.isBlank(rmQuotation.getIfOccupy()))
                && (StringUtils.equals(rmQuotation.getIfOut(), CommonConstants.NO)
                        || StringUtils.isBlank(rmQuotation.getIfOut()))) {
            this.saveQuotaDtl(input, dbQuota);
        }
        // 报价单附件信息保存
        this.saveAttachmentsFile(rmQuotation.getQuotationNo(), input.getFiles());

        return rmQuotation;
    }

    /**
     * 报价单信息保存
     * 
     * @param inputQuota
     *            报价单画面输入信息
     * @param dbQuota
     *            更新前db报价信息
     * 
     * @return 报价单信息
     */
    @Transactional(readOnly = false)
    public RmQuotation saveQuota(RmQuotation inputQuota, RmQuotation dbQuota) {

        // 报价单信息id
        String quotationId = inputQuota.getId();
        // 报价单id为空时，创建报价单
        if (StringUtils.isBlank(quotationId)) {
            // 保存报价单
            // 报价单编号生成
            if (StringUtils.equals(inputQuota.getQuotationType(),
                    CommonConstants.QUOTATION_TYPE_1)) {
                // 预报价单编号
                inputQuota.setQuotationNo(inputQuota.getRepairNo() + "_01");
            } else if (StringUtils.equals(inputQuota.getQuotationType(),
                    CommonConstants.QUOTATION_TYPE_2)) {
                // 最终报价单编号
                inputQuota.setQuotationNo(inputQuota.getRepairNo() + "_02");
                // 开票状态
                inputQuota.setInvoiceStatus(CommonConstants.INVOICE_STATUS_10);
            }
            // 报价单负责人所在组
            inputQuota.setOrganize(UserUtils.get(inputQuota.getResponsiblePersonId())
                    .getOffice().getId());
            // 是否占用
            inputQuota.setIfOccupy(CommonConstants.NO);
            // 是否出库
            inputQuota.setIfOut(CommonConstants.NO);
            inputQuota.preInsert();
            rmQuotationDao.insert(inputQuota);

            return inputQuota;

            // 更新报价单
        } else {

            // 报价负责人
            dbQuota.setResponsiblePersonId(inputQuota.getResponsiblePersonId());
            // 报价单负责人所在组
            dbQuota.setOrganize(UserUtils.get(inputQuota.getResponsiblePersonId())
                    .getOffice().getId());
            // 报价日期
            dbQuota.setQuoteDate(inputQuota.getQuoteDate());
            // 客户id
            dbQuota.setCustomerId(inputQuota.getCustomerId());
            // 联系人
            dbQuota.setContactsName(inputQuota.getContactsName());
            // 电话
            dbQuota.setTelephone(inputQuota.getTelephone());
            // 地址
            dbQuota.setAddress(inputQuota.getAddress());
            // 最终报价单时
            if (StringUtils.equals(dbQuota.getQuotationType(),
                    CommonConstants.QUOTATION_TYPE_2)) {
                // 收款日期
                dbQuota.setActDateFrom(inputQuota.getActDateFrom());
                // 收款状态
                dbQuota.setReceiveStatus(inputQuota.getReceiveStatus());
                // 发票抬头
                dbQuota.setInvoiceTitle(inputQuota.getInvoiceTitle());
                // 折扣率
                dbQuota.setDepositRate(inputQuota.getDepositRate());
            }
            // 备注
            dbQuota.setNewRemarks(inputQuota.getNewRemarks());
            dbQuota.preUpdate();
            rmQuotationDao.update(dbQuota);

            // 报价单id返回
            return dbQuota;
        }
    }

    /**
     * 报价单明细行保存
     * 
     * @param inputQuota
     *            报价单画面输入信息
     * @param dbQuota
     *            更新前db报价信息
     */
    @Transactional(readOnly = false)
    public void saveQuotaDtl(RmQuotation inputQuota, RmQuotation dbQuota) {

        // db报价单信息无时，insert
        if (dbQuota == null) {
            // 报价单明细保存
            // 行号
            int lineNo = 0;
            for (RmQuotationDtl quotaDtl : inputQuota.getQuotationDtlList()) {
                // 明细行号自增
                lineNo = lineNo + 1;
                // 报价单明细行号
                quotaDtl.setLineNo(String.valueOf(lineNo));
                // 报价单编号
                quotaDtl.setQuotationNo(inputQuota.getQuotationNo());
                // 维修编号
                quotaDtl.setRepairNo(inputQuota.getRepairNo());
                // 是否采购
                quotaDtl.setIfPurchase(CommonConstants.NO);

                // 报价单明细保存
                updateQuotaDtl(quotaDtl, null,
                        inputQuota.getQuotationType(), inputQuota.getDepositRate());
            }

            // 更新报价单明细
        } else {

            // 行号
            int lineNo = 0;
            // 报价单明细更新
            Map<String, RmQuotationDtl> dbQuotaDtlMap = ListUtils
                    .convertListToMap(dbQuota.getQuotationDtlList(), "id");
            for (RmQuotationDtl inputDtl : inputQuota.getQuotationDtlList()) {
                // 明细行号自增
                lineNo = lineNo + 1;
                // 报价单明细行号
                inputDtl.setLineNo(String.valueOf(lineNo));

                // 画面输入报价单明细id为空，新添加
                if (StringUtils.isBlank(inputDtl.getId())) {
                    // 报价单编号
                    inputDtl.setQuotationNo(dbQuota.getQuotationNo());
                    // 维修编号
                    inputDtl.setRepairNo(dbQuota.getRepairNo());
                    // 是否采购
                    inputDtl.setIfPurchase(CommonConstants.NO);
                    // 插入报价单明细
                    updateQuotaDtl(inputDtl, null, dbQuota.getQuotationType(), inputQuota.getDepositRate());

                    // 更新时
                } else {
                    RmQuotationDtl dbQuotaDtl = dbQuotaDtlMap.get(inputDtl.getId());
                    // db报价单明细不存在
                    if (dbQuotaDtl == null) {
                        throw new ServiceException("保存失败：数据不整合！");
                    }

                    // 更新报价单明细
                    updateQuotaDtl(inputDtl, dbQuotaDtl, dbQuota.getQuotationType(), inputQuota.getDepositRate());

                    // 更新完成后删除map中的db报价单数据
                    dbQuotaDtlMap.remove(dbQuotaDtl.getId());
                }
            }

            // 删除需要删除的报价单明细
            for (RmQuotationDtl delQuotaDtl : dbQuotaDtlMap.values()) {
                rmQuotationDtlDao.delete(delQuotaDtl);
            }
        }
    }

    /**
     * 报价单明细行更新
     * 
     * @param dtl
     *            报价单明细画面数据
     * @param dbDtl
     *            报价单明细db数据
     * @param quotationType
     *            报价单类型
     */
    @Transactional(readOnly = false)
    public void updateQuotaDtl(RmQuotationDtl dtl, RmQuotationDtl dbDtl,
            String quotationType, String depositRate) {

        // insert
        if (StringUtils.isBlank(dtl.getId())) {

            // 明细为配件时
            if (StringUtils.equals(dtl.getItemType(),
                    CommonConstants.QUOTATION_DTL_ITEM_TYPE_1)) {

                // 配件标准价格取得
                BigDecimal standardPrice = this.getAccStandardPrice(dtl.getMaterialNo());

//                // 标准价取得为空
//                if (standardPrice == null) {
//                    throw new ServiceException(
//                            "保存失败：物料号" + dtl.getMaterialNo() + "，标准价取得失败！");
//                }

                // 单价设为标准价
                dtl.setUnitPrice(standardPrice);
                // 总金额=单价 * 数量
                if (standardPrice != null) {
                    dtl.setTotalAmount(standardPrice.multiply(new BigDecimal(dtl.getNum())));
                }

                // 明细为服务时
            } else if (StringUtils.equals(dtl.getItemType(),
                    CommonConstants.QUOTATION_DTL_ITEM_TYPE_2)) {
                // 库房
                dtl.setWarehouse(null);
                // 新sn为空
                dtl.setNewSnNo(null);
                // 单价为空
                dtl.setUnitPrice(null);
                // 数量为空
                dtl.setNum(null);
                // 总金额为空
                dtl.setTotalAmount(null);
            }

            // 预报价单时
            if (StringUtils.equals(quotationType, CommonConstants.QUOTATION_TYPE_1)) {
                // 旧sn为空
                dtl.setSnNo(null);
            }

            // 最终报价单时
            if (StringUtils.equals(quotationType, CommonConstants.QUOTATION_TYPE_2)) {
                
            	if (StringUtils.equals(dtl.getIfWarranty(), CommonConstants.NO)) {

                    if (StringUtils.isEmptyNull(depositRate)) {

                        // 实际金额
                    	dtl.setActAmount(dtl.getTotalAmount());
                    } else {

                    	BigDecimal bDepositRate = new BigDecimal(depositRate);
                        // 实际金额
                    	dtl.setActAmount(StringUtils.divide(StringUtils.multiply(dtl.getTotalAmount(), bDepositRate), new BigDecimal("100"), 0, BigDecimal.ROUND_UP));
                    }
            	} else {
            		dtl.setActAmount(BigDecimal.ZERO);
            	}
            }

            dtl.preInsert();
            rmQuotationDtlDao.insert(dtl);

            // 更新处理
        } else {

            // 分类
            dbDtl.setItemType(dtl.getItemType());
            // 物料号
            dbDtl.setMaterialNo(dtl.getMaterialNo());

            // 明细为配件时
            if (StringUtils.equals(dtl.getItemType(),
                    CommonConstants.QUOTATION_DTL_ITEM_TYPE_1)) {

                // 库房
                dbDtl.setWarehouse(dtl.getWarehouse());
                // 配件标准价格取得
                BigDecimal standardPrice = this.getAccStandardPrice(dtl.getMaterialNo());
//                // 标准价取得为空
//                if (standardPrice == null) {
//                    throw new ServiceException(
//                            "保存失败：物料号" + dtl.getMaterialNo() + "，标准价取得失败！");
//                }

                // 新sn
                dbDtl.setNewSnNo(dtl.getNewSnNo());
                // 单价
                dbDtl.setUnitPrice(standardPrice);
                // 数量
                dbDtl.setNum(dtl.getNum());
                // 金额
                if (standardPrice != null) {
                    dbDtl.setTotalAmount(
                            standardPrice.multiply(new BigDecimal(dtl.getNum())));
                } else {
                    dbDtl.setTotalAmount(null);
                }

                // 明细为服务时
            } else if (StringUtils.equals(dtl.getItemType(),
                    CommonConstants.QUOTATION_DTL_ITEM_TYPE_2)) {
                // 库房
                dbDtl.setWarehouse(null);
                // 新sn为空
                dbDtl.setNewSnNo(null);
                // 单价为空
                dbDtl.setUnitPrice(null);
                // 数量为空
                dbDtl.setNum(null);
                // 金额为空
                dbDtl.setTotalAmount(null);
            }
            // 是否保内
            dbDtl.setIfWarranty(dtl.getIfWarranty());
            // 最终报价单时
            if (StringUtils.equals(quotationType, CommonConstants.QUOTATION_TYPE_2)) {
                // 旧sn为空
                dbDtl.setSnNo(dtl.getSnNo());
                
                if (StringUtils.equals(dtl.getIfWarranty(), CommonConstants.NO)) {

                    if (StringUtils.isEmptyNull(depositRate)) {

                        // 实际金额
                        dbDtl.setActAmount(dbDtl.getTotalAmount());
                    } else {

                    	BigDecimal bDepositRate = new BigDecimal(depositRate);
                        // 实际金额
                        dbDtl.setActAmount(StringUtils.divide(StringUtils.multiply(dbDtl.getTotalAmount(), bDepositRate), new BigDecimal("100"), 0, BigDecimal.ROUND_UP));
                    }
                } else {
            		dbDtl.setActAmount(BigDecimal.ZERO);
            	}
            }

            dbDtl.preUpdate();
            rmQuotationDtlDao.update(dbDtl);
        }
    }

    /**
     * 验证报价单信息
     * 
     * @param input
     *            画面输入报价单信息
     * @return 验证消息
     */
    public List<String> validQuotaData(RmQuotation input) {

        List<String> messages = new ArrayList<String>();

        // 排他性验证，更新日时不相等，数据已被更改
        if (DateUtils.compareDate(input.getUpdateDate(),
                input.getUpdateDate()) != 0) {
            messages.add("数据已经被更改，请取得最新数据后再操作。");
            return messages;
        }

        // 报价日期不能晚于当前日期
        if (DateUtils.compareDate(input.getQuoteDate(),
                DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH)) > 0) {
            messages.add("报价日期不能晚于当前日期。");
        }

        // 报价单明细行check
        // 报价单明细不能为空
        if (ListUtils.size(input.getQuotationDtlList()) == 0) {
            messages.add("请填写至少一条报价单明细。");
            return messages;
        }

        // 报价单出库或占用时明细行不保存，所以明细行不验证
        if (StringUtils.isBlank(input.getId()) || !StringUtils.isBlank(input.getId())
                && (StringUtils.equals(input.getIfOccupy(), CommonConstants.NO)
                        || StringUtils.isBlank(input.getIfOccupy()))
                && (StringUtils.equals(input.getIfOut(), CommonConstants.NO)
                        || StringUtils.isBlank(input.getIfOut()))) {

            // 明细行已check的新sn list
            List<String> checkedNewSnList = Lists.newArrayList();
            // 明细行
            for (RmQuotationDtl quotaDtl : input.getQuotationDtlList()) {
                // 物料存在check
                SmMatInfo smMatInfo = smMatInfoDao
                        .getMaterialInfoByNo(quotaDtl.getMaterialNo());
                if (smMatInfo == null || StringUtils.isBlank(smMatInfo.getMaterialNo())) {
                    messages.add("报价单明细行物料号为" + quotaDtl.getMaterialNo() + "的物料不存在。");
                }

                // 明细行为配件时
                if (StringUtils.equals(quotaDtl.getItemType(),
                        CommonConstants.QUOTATION_DTL_ITEM_TYPE_1)) {

                    // 所在库房不能为空
                    if (StringUtils.isBlank(quotaDtl.getWarehouse())) {
                        messages.add(
                                "报价单明细行物料号为" + quotaDtl.getMaterialNo() + "的物料所在库房不能为空。");
                    }

                    // 物料存在sn时
                    if (StringUtils.equals(smMatInfo.getIfSn(), CommonConstants.YES)) {
                        // 新sn为空
                        if (StringUtils.isBlank(quotaDtl.getNewSnNo())) {
                            messages.add("报价单明细行物料号为" + quotaDtl.getMaterialNo()
                                    + "的物料新S/N不能为空。");
                        }

                        // sn物料数量必须等于1
                        if (quotaDtl.getNum() == null
                                || quotaDtl.getNum().intValue() != 1) {
                            messages.add("报价单明细行物料号为" + quotaDtl.getMaterialNo()
                                    + "的物料数量不等于1。");
                        }

                        // 新sn是否重复check
                        for (String checkedNewSn : checkedNewSnList) {
                            // 新sn重复
                            if (StringUtils.equals(checkedNewSn, quotaDtl.getNewSnNo())) {
                                messages.add("报价单明细行物料号为" + quotaDtl.getMaterialNo()
                                        + "的物料新S/N重复。");
                            }
                        }

                        // 新sn是否存在check
                        SmSnInfo searchSn = new SmSnInfo();
                        searchSn.setSnNo(quotaDtl.getNewSnNo());
                        searchSn.setMaterialNo(quotaDtl.getMaterialNo());
                        int sncount = smSnInfoDao.getSnCountByNo(searchSn);
                        if (sncount == 0) {
                            messages.add("报价单明细行物料号为" + quotaDtl.getMaterialNo()
                                    + "的物料对应的新S/N为" + quotaDtl.getNewSnNo() + "的配件不存在。");
                        }

                        // 新sn验证通过、将新sn添加到已验证新sn list
                        checkedNewSnList.add(quotaDtl.getNewSnNo());
                    }

                    // 配件数量check
                    if (quotaDtl.getNum() == null) {
                        messages.add(
                                "报价单明细行物料号为" + quotaDtl.getMaterialNo() + "的物料数量不能为空。");
                    }
                }
            }
        }

        if (messages.size() > 0) {
            messages.add(0, "保存失败：");
        }

        return messages;
    }

    /**
     * 报价单发票申请流程流转
     * 
     * @param input
     *            报价单画面输入信息
     * @return 报价单业务id
     */
    @Transactional(readOnly = false)
    public String flowInvoiceApplyProc(RmQuotation input) {

        // 机器采购订单申请发起
        // 发票id
        String invoiceId = "";
        // 画面操作所对应的WorkflowStatus
        String workflowStatus = input.getInvoice().getWorkflowStatus();
        // 申请、或是再申请时执行订单信息保存
        if (StringUtils.isBlank(input.getAct().getProcInsId()) || StringUtils
                .equals(workflowStatus, CommonConstants.WORKFLOW_STATUS_10)) {
            // 保存报价单发票信息
            invoiceId = saveInvoice(input);
        } else {
            invoiceId = input.getInvoice().getId();
        }

        // 流程流转
        input.getAct().setBusinessId(invoiceId);
        input.getAct().setMainBusinessId(input.getId());
        commonService.flowProcess(input.getAct(), ActUtils.PD_QUOTA_INVOICE,
                CommonConstants.PROCESS_TITLE_QUOTA_INVOICE, workflowStatus);

        // 审批完成时，报价单开票状态更新
//        if (StringUtils.equals(workflowStatus, CommonConstants.WORKFLOW_STATUS_50)) {
        if (StringUtils.equals(workflowStatus, CommonConstants.WORKFLOW_STATUS_20)) {
        	if (actTaskService.getProcIns(input.getAct().getProcInsId()) == null) {
            // 发票总金额取得
            BigDecimal invoiceTotalAmount = imInvoiceDtlDao
                    .getInvoiceTotalAmountByAppid(invoiceId);

            // 报价单信息取得
            RmQuotation quotation = rmQuotationDao.get(input.getId());
            // 报价单信息取得
            if (quotation == null) {
                throw new ServiceException("报价单开票状态更新失败：报价单信息不存在。");
            }

            // 报价单明细总金额取得
//            BigDecimal quotaTotalAmount = getQuotaTotalAmount(
//                    quotation.getQuotationDtlList());
            BigDecimal quotaTotalAmount = rmQuotationDtlDao.getQuotaDtlTotalAmount(input.getId());
            if (quotaTotalAmount == null) {
            	quotaTotalAmount = BigDecimal.ZERO;
            }

            // 报价单发票总金额为0
            if (quotaTotalAmount.compareTo(invoiceTotalAmount) > 0) {
                // 未开票
                quotation.setInvoiceStatus(CommonConstants.INVOICE_STATUS_10);
            } else {
                // 已开票
                quotation.setInvoiceStatus(CommonConstants.INVOICE_STATUS_30);
            }
            quotation.preUpdate();
            rmQuotationDao.update(quotation);
        	}
        }

        return input.getId();
    }

    /**
     * 报价单发票信息保存
     * 
     * @param input
     *            报价单画面输入信息
     * @return 发票管理记录id
     */
    @Transactional(readOnly = false)
    public String saveInvoice(RmQuotation input) {

        // 报价单信息验证
        if (input == null || StringUtils.isBlank(input.getId())) {
            throw new ServiceException("保存失败：数据不整合。");
        }

        // 报价单及其明细信息取得
        RmQuotation dbQuota = this.get(input.getId());

        ImInvoice inputInvoice = input.getInvoice();
        // 发票管理记录id
        String invoiceId = inputInvoice.getId();
        // 发票信息保存
        // 发票id为空时，insert
        if (StringUtils.isBlank(invoiceId)) {
            // 报价单编号
            inputInvoice.setOrderNo(dbQuota.getQuotationNo());
            inputInvoice.preInsert();
            imInvoiceDao.insert(inputInvoice);

        } else {
            // 否则更新
            ImInvoice dbInvoice = imInvoiceDao.get(inputInvoice.getId());
            // 发票类型
            dbInvoice.setInvoiceType(inputInvoice.getInvoiceType());
            // 开票抬头
            dbInvoice.setInvoiceTitle(inputInvoice.getInvoiceTitle());
            // 纳税人识别号
            dbInvoice.setTaxpayerIdentNo(inputInvoice.getTaxpayerIdentNo());
            // 开户行
            dbInvoice.setDepositBank(inputInvoice.getDepositBank());
            // 银行账号
            dbInvoice.setBankAccount(inputInvoice.getBankAccount());
            // 地址
            dbInvoice.setInvoiceAddress(inputInvoice.getInvoiceAddress());
            // 电话
            dbInvoice.setTelephone(inputInvoice.getTelephone());
            // 取票方式
            dbInvoice.setTicketMethod(inputInvoice.getTicketMethod());
            // 收件人
            dbInvoice.setRecipients(inputInvoice.getRecipients());
            // 联系电话
            dbInvoice.setRepTelephone(inputInvoice.getRepTelephone());
            // 收件地址
            dbInvoice.setAddress(inputInvoice.getAddress());
            // 开票说明
            dbInvoice.setNewRemarks(inputInvoice.getNewRemarks());
            dbInvoice.preUpdate();
            imInvoiceDao.update(dbInvoice);
        }

        // 发票明细信息保存
        // 画面输入发票明细信息取得
        List<ImInvoiceDtl> inputInvoiceDtlList = input.getInvoiceDtlList();
        // 报价单明细行取得
        List<RmQuotationDtl> dbQuotaDtlList = dbQuota.getQuotationDtlList();

        // 发票id为空时，insert
        if (StringUtils.isBlank(invoiceId)) {
            // 发票行号
            int lineNo = 0;
            // 循环insert
            for (ImInvoiceDtl inputIvcDtl : inputInvoiceDtlList) {
                // 按报价单明细拆行
                for (RmQuotationDtl dbQuotaDtl : dbQuotaDtlList) {
                    lineNo = lineNo + 1;
                    ImInvoiceDtl nIvcDtl = new ImInvoiceDtl();
                    // 发票类型
                    nIvcDtl.setInvoiceType(inputInvoice.getInvoiceType());
                    // 申请id
                    nIvcDtl.setAppId(inputInvoice.getId());
                    // 发票来源：报价单
                    nIvcDtl.setInvoiceSource(CommonConstants.INVOICE_SOURCE_20);
                    // 报价单编号
                    nIvcDtl.setOrderNo(dbQuota.getQuotationNo());
                    // 是否红票
                    if (StringUtils.equals(inputIvcDtl.getIfRed(), CommonConstants.YES)) {
                        // 红票是
                        nIvcDtl.setIfRed(CommonConstants.YES);
                        // 发票金额
                        nIvcDtl.setInvoiceAmount(dbQuotaDtl.getActAmount()
                                .multiply(BigDecimal.valueOf(-1)));
                        // 税金
                        nIvcDtl.setTax(dbQuotaDtl.getActAmount()
                                .divide(new BigDecimal(1.17), 2, BigDecimal.ROUND_HALF_UP)
                                .multiply(new BigDecimal(0.17)).multiply(BigDecimal.valueOf(-1)));
                        // 数量
                        if (dbQuotaDtl.getNum() == null) {
                            nIvcDtl.setNum(-1);
                        } else {
                            nIvcDtl.setNum(dbQuotaDtl.getNum() * -1);
                        }
                    } else {
                        // 红票否
                        nIvcDtl.setIfRed(CommonConstants.NO);
                        // 发票金额
                        nIvcDtl.setInvoiceAmount(dbQuotaDtl.getActAmount());
                        // 税金
                        nIvcDtl.setTax(dbQuotaDtl.getActAmount()
                                .divide(new BigDecimal(1.17), 2, BigDecimal.ROUND_HALF_UP)
                                .multiply(new BigDecimal(0.17)));
                        // 数量
                        if (dbQuotaDtl.getNum() == null) {
                            nIvcDtl.setNum(1);
                        } else {
                            nIvcDtl.setNum(dbQuotaDtl.getNum());
                        }
                    }
                    // 开票日期
                    nIvcDtl.setInvoiceDate(inputIvcDtl.getInvoiceDate());
                    // 发票号码
                    nIvcDtl.setInvoiceNo(inputIvcDtl.getInvoiceNo());
                    // 报价单行号
                    nIvcDtl.setLineNo(dbQuotaDtl.getLineNo());
                    // 发票行号
                    nIvcDtl.setLineNo2(String.valueOf(lineNo));
                    // 快递公司
                    nIvcDtl.setExpressCompany(inputIvcDtl.getExpressCompany());
                    // 快递编号
                    nIvcDtl.setExpressNo(inputIvcDtl.getExpressNo());
                    nIvcDtl.preInsert();
                    imInvoiceDtlDao.insert(nIvcDtl);
                }
            }

        } else {
            // 否则更新
            // db更新前发票明细取得
            List<ImInvoiceDtl> dbIvcDtlList = imInvoiceDtlDao
                    .getInvoiceDtlByAppid(invoiceId);

            // 将db发票明细转为map
            Map<String, ImInvoiceDtl> dbIvcDtlMap = ListUtils
                    .convertListToMap(dbIvcDtlList, "id");

            // 根据发票id删除所有
            // imInvoiceDtlDao.deleteInvoiceDtlByAppid(invoiceId);
            // 发票行号
            int lineNo = 0;
            // 画面输入发票明细循环
            for (ImInvoiceDtl inputIvcDtl : inputInvoiceDtlList) {

                // 发票明细id存在，表示不需拆行明细,更新
                if (!StringUtils.isBlank(inputIvcDtl.getId())) {
                    // 行号自增
                    lineNo = lineNo + 1;
                    // 发票行号更新
                    ImInvoiceDtl dbIvcDtl = dbIvcDtlMap.get(inputIvcDtl.getId());
                    dbIvcDtl.setLineNo2(String.valueOf(lineNo));
                    dbIvcDtl.preUpdate();
                    imInvoiceDtlDao.update(dbIvcDtl);

                    // map删除不需拆行明细行
                    dbIvcDtlMap.remove(inputIvcDtl.getId());
                } else {
                    // id无,需拆行
                    // 按报价单明细拆行
                    for (RmQuotationDtl dbQuotaDtl : dbQuotaDtlList) {
                        // 行号自增
                        lineNo = lineNo + 1;
                        ImInvoiceDtl nIvcDtl = new ImInvoiceDtl();
                        // 发票类型
                        nIvcDtl.setInvoiceType(inputInvoice.getInvoiceType());
                        // 申请id
                        nIvcDtl.setAppId(inputInvoice.getId());
                        // 发票来源：报价单
                        nIvcDtl.setInvoiceSource(CommonConstants.INVOICE_SOURCE_20);
                        // 报价单编号
                        nIvcDtl.setOrderNo(dbQuota.getQuotationNo());
                        // 是否红票
                        if (StringUtils.equals(inputIvcDtl.getIfRed(),
                                CommonConstants.YES)) {
                            // 红票是
                            nIvcDtl.setIfRed(CommonConstants.YES);
                            // 发票金额
                            nIvcDtl.setInvoiceAmount(dbQuotaDtl.getActAmount()
                                    .multiply(BigDecimal.valueOf(-1)));
                            // 税金
                            nIvcDtl.setTax(dbQuotaDtl.getActAmount()
                                    .divide(new BigDecimal(1.17), 2, BigDecimal.ROUND_HALF_UP)
                                    .multiply(new BigDecimal(0.17)).multiply(BigDecimal.valueOf(-1)));
                            // 数量
                            if (dbQuotaDtl.getNum() == null) {
                                nIvcDtl.setNum(-1);
                            } else {
                                nIvcDtl.setNum(dbQuotaDtl.getNum() * -1);
                            }
                        } else {
                            // 红票否
                            nIvcDtl.setIfRed(CommonConstants.NO);
                            // 发票金额
                            nIvcDtl.setInvoiceAmount(dbQuotaDtl.getActAmount());
                            // 税金
                            nIvcDtl.setTax(dbQuotaDtl.getActAmount()
                                    .divide(new BigDecimal(1.17), 2, BigDecimal.ROUND_HALF_UP)
                                    .multiply(new BigDecimal(0.17)));
                            // 数量
                            if (dbQuotaDtl.getNum() == null) {
                                nIvcDtl.setNum(1);
                            } else {
                                nIvcDtl.setNum(dbQuotaDtl.getNum());
                            }
                        }
                        // 开票日期
                        nIvcDtl.setInvoiceDate(inputIvcDtl.getInvoiceDate());
                        // 发票号码
                        nIvcDtl.setInvoiceNo(inputIvcDtl.getInvoiceNo());
                        // 报价单行号
                        nIvcDtl.setLineNo(dbQuotaDtl.getLineNo());
                        // 发票行号
                        nIvcDtl.setLineNo2(String.valueOf(lineNo));
                        // 快递公司
                        nIvcDtl.setExpressCompany(inputIvcDtl.getExpressCompany());
                        // 快递编号
                        nIvcDtl.setExpressNo(inputIvcDtl.getExpressNo());
                        nIvcDtl.preInsert();
                        imInvoiceDtlDao.insert(nIvcDtl);
                    }
                }
            }

            // 删除需要删除的发票明细删除
            for (ImInvoiceDtl delIvcDtl : dbIvcDtlMap.values()) {
                imInvoiceDtlDao.delete(delIvcDtl);
            }
        }

        return inputInvoice.getId();
    }
    
    /**
     * 报价单发票信息编辑（具有发票编辑权限保存时调用）
     * 
     * @param input
     *            报价单画面输入信息
     * @return 发票管理记录id
     */
    @Transactional(readOnly = false)
    public void editInvoice(RmQuotation input) {
        // 报价单信息验证
        if (input == null || StringUtils.isBlank(input.getId())) {
            throw new ServiceException("保存失败：数据不整合。");
        }

        // 画面输入发票头信息
        ImInvoice inputInvoice = input.getInvoice();

        // 发票头信息更新（最后申请的发票头信息）
        ImInvoice dbInvoice = imInvoiceDao.get(inputInvoice.getId());
        if (dbInvoice == null) {
            throw new ServiceException("保存失败：数据不整合。");
        }
//        // 发票类型
//        dbInvoice.setInvoiceType(inputInvoice.getInvoiceType());
        // 开票抬头
        dbInvoice.setInvoiceTitle(inputInvoice.getInvoiceTitle());
        // 纳税人识别号
        dbInvoice.setTaxpayerIdentNo(inputInvoice.getTaxpayerIdentNo());
        // 开户行
        dbInvoice.setDepositBank(inputInvoice.getDepositBank());
        // 银行账号
        dbInvoice.setBankAccount(inputInvoice.getBankAccount());
        // 地址
        dbInvoice.setInvoiceAddress(inputInvoice.getInvoiceAddress());
        // 电话
        dbInvoice.setTelephone(inputInvoice.getTelephone());
        // 取票方式
        dbInvoice.setTicketMethod(inputInvoice.getTicketMethod());
        // 收件人
        dbInvoice.setRecipients(inputInvoice.getRecipients());
        // 联系电话
        dbInvoice.setRepTelephone(inputInvoice.getRepTelephone());
        // 收件地址
        dbInvoice.setAddress(inputInvoice.getAddress());
        // 开票说明
        dbInvoice.setNewRemarks(inputInvoice.getNewRemarks());
        dbInvoice.preUpdate();
        imInvoiceDao.update(dbInvoice);

        // 发票明细信息更新
        // db更新前发票明细取得
        List<ImInvoiceDtl> dbIvcDtlList = imInvoiceDtlDao
                .getInvoiceDtlByQuotaId(input.getId());

        // 将db发票明细转为map
        Map<String, ImInvoiceDtl> dbIvcDtlMap = ListUtils.convertListToMap(dbIvcDtlList,
                "id");

        // 画面输入发票明细循环
        for (ImInvoiceDtl inputIvcDtl : input.getInvoiceDtlList()) {
            // 发票行号更新
            ImInvoiceDtl dbIvcDtl = dbIvcDtlMap.get(inputIvcDtl.getId());
            if (dbIvcDtl == null) {
                throw new ServiceException("保存失败：数据不整合。");
            }

            // 发票类型
            dbIvcDtl.setInvoiceType(inputInvoice.getInvoiceType());
            // 税金
            dbIvcDtl.setTax(inputIvcDtl.getTax());
            // 开票日期
            dbIvcDtl.setInvoiceDate(inputIvcDtl.getInvoiceDate());
            // 发票号码
            dbIvcDtl.setInvoiceNo(inputIvcDtl.getInvoiceNo());
            // 快递编号
            dbIvcDtl.setExpressNo(inputIvcDtl.getExpressNo());
            // 快递公司
            dbIvcDtl.setExpressCompany(inputIvcDtl.getExpressCompany());

            dbIvcDtl.preUpdate();
            imInvoiceDtlDao.update(dbIvcDtl);
        }
    }

    /**
     * 验证报价单发票信息
     * 
     * @param input
     *            报价单画面输入发票信息
     * @return 验证消息
     */
    public List<String> validInvoiceInfo(RmQuotation input) {

        List<String> messages = new ArrayList<String>();

        // 报价单信息验证
        if (input == null || StringUtils.isBlank(input.getId())) {
            messages.add("保存失败：数据不整合。");
            return messages;
        }

        // 至少需要一行发票明细
        if (ListUtils.size(input.getInvoiceDtlList()) == 0) {
            messages.add("保存失败：请填写发票明细信息。");
            return messages;
        }

        // 报价单明细总金额取得
        BigDecimal quotaAmount = rmQuotationDtlDao.getQuotaDtlTotalAmount(input.getId());
        if (quotaAmount == null) {
            quotaAmount = BigDecimal.ZERO;
        }

        // 已经申请发票明细总金额取得
        BigDecimal applyedTotalAmt = imInvoiceDtlDao
                .getQuotaApplyedInvoiceTotalAmount(input.getId());
        if (applyedTotalAmt == null) {
            applyedTotalAmt = BigDecimal.ZERO;
        }

//        RmQuotation dbQuota = this.get(input.getId());
//        // db报价单明细总金额
//        BigDecimal quotaAmount = dbQuota.getTotalAmount();

        // 未完成申请的发票
        ImInvoice unfinishInvoice = imInvoiceDao.getUnfinishedApplyInvoice(input.getId());

        // 发票管理记录id
        String invoiceId = input.getInvoice().getId();
        // 发票id为空时，表示新增发票申请
        if (StringUtils.isBlank(invoiceId)) {

            // 是否存在未完成申请
            if (unfinishInvoice != null) {
                messages.add("保存失败：已经存在未完成发票申请，申请完成后才可再进行发票申请。");
                return messages;
            }

            // 新增发票申请明细总金额
            BigDecimal ivcAmount = BigDecimal.ZERO;
            for (ImInvoiceDtl iIvcDtl : input.getInvoiceDtlList()) {
                if (StringUtils.equals(CommonConstants.YES, iIvcDtl.getIfRed())) {
                    ivcAmount = ivcAmount.add(quotaAmount.multiply(new BigDecimal(-1)));
                } else {
                    ivcAmount = ivcAmount.add(quotaAmount);
                }

                // 开票日期不能大于当前日期
                if (DateUtils.compareDate(iIvcDtl.getInvoiceDate(),
                        DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH)) > 0) {
                    messages.add("发票明细行，开票日期不能晚于当前日期。");
                }
            }

            // 总金额 = 新申请发票金额 + 已申请发票金额
            BigDecimal total = applyedTotalAmt.add(ivcAmount);

            // 总金额（包括已经审批完毕和申请中的所有行）<=明细行的金额合计
            if (total.compareTo(quotaAmount) > 0) {
                messages.add("发票明细行开票总金额不能大于报价单明细总金额。");
            }
        } else {

            // 未完成申请发票为空，表示编辑发票信息
            if (unfinishInvoice == null) {
                List<ImInvoiceDtl> dbIvcDtlList = imInvoiceDtlDao
                        .getInvoiceDtlByQuotaId(input.getId());

                // 将db发票明细转为map
                Map<String, ImInvoiceDtl> dbIvcDtlMap = ListUtils
                        .convertListToMap(dbIvcDtlList, "id");

                // 编辑明细行验证
                for (ImInvoiceDtl iIvcDtl : input.getInvoiceDtlList()) {

                    // 明细行id在db不存在
                    if (StringUtils.isBlank(iIvcDtl.getId())) {
                        messages.add("发票编辑时，不能添加发票明细。");
                        break;
                    }

                    ImInvoiceDtl dbIvcDtl = dbIvcDtlMap.get(iIvcDtl.getId());
                    if (dbIvcDtl == null) {
                        messages.add("数据不整合，被更新的发票明细信息不存在。");
                        break;
                    }

                    // 开票日期不能大于当前日期
                    if (DateUtils.compareDate(iIvcDtl.getInvoiceDate(),
                            DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH)) > 0) {
                        messages.add("发票明细行，开票日期不能晚于当前日期。");
                    }
                }

                // 发票id=未完成申请发票id，表示修改申请中发票
            } else if (StringUtils.equals(invoiceId, unfinishInvoice.getId())) {
                // db更新前发票明细取得
                List<ImInvoiceDtl> dbIvcDtlList = imInvoiceDtlDao
                        .getInvoiceDtlByAppid(invoiceId);

                // 将db发票明细转为map
                Map<String, ImInvoiceDtl> dbIvcDtlMap = ListUtils
                        .convertListToMap(dbIvcDtlList, "id");

                // 开票总金额
                BigDecimal ivcAmount = BigDecimal.ZERO;
                for (ImInvoiceDtl iIvcDtl : input.getInvoiceDtlList()) {

                    // 发票明细id存在,既存行
                    if (!StringUtils.isBlank(iIvcDtl.getId())) {
                        ImInvoiceDtl dbIvcDtl = dbIvcDtlMap.get(iIvcDtl.getId());
                        if (dbIvcDtl == null) {
                            messages.add("数据不整合。");
                        }
                        ivcAmount = ivcAmount.add(dbIvcDtl.getInvoiceAmount());
                    } else {
                        // 新增行
                        // 是否红票
                        if (StringUtils.equals(CommonConstants.YES, iIvcDtl.getIfRed())) {
                            ivcAmount = ivcAmount.add(quotaAmount.multiply(new BigDecimal(-1)));
                        } else {
                            ivcAmount = ivcAmount.add(quotaAmount);
                        }

                        // 开票日期不能大于当前日期
                        if (DateUtils.compareDate(iIvcDtl.getInvoiceDate(),
                                DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH)) > 0) {
                            messages.add("发票明细行，开票日期不能晚于当前日期。");
                        }
                    }
                }
                
                // 总金额 = 新申请发票金额 + 已申请发票金额
                BigDecimal total = applyedTotalAmt.add(ivcAmount);

                // 总金额（包括已经审批完毕和申请中的所有行）<=明细行的金额合计
                if (total.compareTo(quotaAmount) > 0) {
                    messages.add("发票明细行开票总金额不能大于报价单明细总金额。");
                }
            }
        }

        if (messages.size() > 0) {
            messages.add(0, "保存失败：");
        }

        return messages;
    }

    /**
     * 开票客户信息取得
     * 
     * @param customerId
     *            客户id
     * @return 开票客户信息
     */
    public CmCustomerInfo getCustomerInfo(String customerId) {
        return rmQuotationDao.getCustomerInfo(customerId);
    }

    /**
     * 附件保存
     * 
     * @param quotationNo
     *            报价单画面输入信息
     * @param files
     *            报价单附件文件
     */
    @Transactional(readOnly = false)
    public void saveAttachmentsFile(String quotationNo, MultipartFile[] files) {
        // 报价单附件保存
        if (files != null && files.length > 0) {
            attachmentsService.uploadFileList(quotationNo,
                    CommonConstants.ATTACHMENT_TYPE_QUOTATION, files);
        }
    }

    /**
     * 取得配件标准价格
     * 
     * @param materialNo
     *            配件物料号
     * @return 配件标准价格
     */
    public BigDecimal getAccStandardPrice(String materialNo) {
        return rmQuotationDao.getAccStandardPrice(materialNo);
    }

    /**
     * 通过搜索关键字或者类型模糊查询报价单物料信息
     * 
     * @param page
     *            分页器
     * @param qMap
     *            查询参数
     * 
     * @return 物料信息List
     */
    public Page<QuotaSelect> getQuotaMaterialDictList(Page<QuotaSelect> page, String type,
            String kw, String snNo) {
        QuotaSelect q = new QuotaSelect();
        q.setKw(kw);
        q.setType(type);
        q.setSnNo(snNo);
        q.setPage(page);
        
        StringBuilder sbType = new StringBuilder();
        sbType.append(CommonConstants.MATERIAL_TYPE_2);
        sbType.append(",");
        sbType.append(CommonConstants.MATERIAL_TYPE_7);

        // 配件时
        if (StringUtils.equals(type, sbType.toString())) {
            return page.setList(rmQuotaSelectDao.getQuotaMaterialDictList(q));

            // 服务时
        } else if (StringUtils.equals(type, CommonConstants.MATERIAL_TYPE_6)) {
            return page.setList(rmQuotaSelectDao.getQuotaServiceDictList(q));

        } else {
            return page;
        }
    }

    /**
     * 通过搜索关键字或者物料号模糊查询报价单新sn信息
     * 
     * @param page
     *            分页器
     * @param kw
     *            搜索关键字
     * @param materialNo
     *            物料号
     * @param warehouse
     *            库房
     * 
     * @return 物料指定库房对应的sn信息List
     */
    public Page<QuotaSelect> getQuotaMatSnNoDictList(Page<QuotaSelect> page, String kw,
            String materialNo, String warehouse) {
        QuotaSelect q = new QuotaSelect();
        q.setKw(kw);
        q.setMaterialNo(materialNo);
        q.setWarehouse(warehouse);
        q.setPage(page);

        // 配件时
        return page.setList(rmQuotaSelectDao.getQuotaNewSnDictList(q));
    }

    /**
     * 取得sn物料的生产日期
     * 
     * @param snNo
     *            样机sn
     * 
     * @return sn生产日期信息
     */
    public QuotaSelect getSnProductionDate(String snNo) {

        Date productionDate = rmQuotationDao.getSnProductionDate(snNo);

        QuotaSelect qs = new QuotaSelect();
        qs.setProductionDate(productionDate);
        return qs;
    }

    /**
     * 预报价单库存占用/取消占用操作
     * 
     * @param preQuotaId
     *            预报价单id
     * @param type
     *            操作类型：1，占用，0，取消占用
     * 
     * @return 预报价单信息
     */
    @Transactional(readOnly = false)
    public RmQuotation inventoryOccupancy(String preQuotaId, String type) {

        // 预报价信息取得
        RmQuotation quotation = this.get(preQuotaId);
        // 报价单明细
        List<RmQuotationDtl> quotaDtlList = quotation.getQuotationDtlList();

        // 报价单明细行为空
        if (ListUtils.size(quotaDtlList) == 0) {
            // 库存占用时
            if (StringUtils.equals(type, CommonConstants.OCCUPANCY_TYPE_1)) {
                throw new ServiceException("占用配件库存失败：报价单明细不存在。");
            } else {
                // 库存取消时
                throw new ServiceException("取消配件库存失败：报价单明细不存在。");
            }
        }

        // 占用库存时，check附件是否上传
        if (StringUtils.equals(type, CommonConstants.OCCUPANCY_TYPE_1)
                && ListUtils.size(quotation.getAttachmentsList()) == 0) {
            throw new ServiceException("占用配件库存失败：占用库存需要上传附件。");
        }

        // 报价单明细对应的库存信息取得
        List<SmWarehouseInfo> warehouseInfoList = smWarehouseInfoDao
                .getQuotaDtlInventory(preQuotaId);

        // 将库存信息转换为map
        Map<String, SmWarehouseInfo> warehouseInfoMap = ListUtils
                .convertListToMap(warehouseInfoList, "quotaDtlId");

        // 是否占用
        boolean isOccupancy = false;

        // 库存占用时
        if (StringUtils.equals(type, CommonConstants.OCCUPANCY_TYPE_1)) {
            // 明细行循环
            for (RmQuotationDtl quotaDtl : quotaDtlList) {

                // 服务时，无数量，不占用
                if (StringUtils.equals(quotaDtl.getItemType(),
                        CommonConstants.QUOTATION_DTL_ITEM_TYPE_2)) {
                    continue;
                }

                // 明细行对应的库存信息取得
                SmWarehouseInfo wh = warehouseInfoMap.get(quotaDtl.getId());
                
                // sn占用
                if (StringUtils.equals(quotaDtl.getIfSn(), CommonConstants.YES)) {
                    // 库存信息为空
                    if (wh == null) {
                        throw new ServiceException(
                                "占用配件库存失败：物料" + quotaDtl.getMaterialName() + "，新s/n" + quotaDtl.getNewSnNo() +"，没有可占用的库存。");
                    }

                    // 可用数量取得
                    int availableNum = defaultInt(wh.getNum())
                            - defaultInt(wh.getOccupationNo());
                    // 可用数量小于数量时
                    if (availableNum < quotaDtl.getNum()) {
                        throw new ServiceException(
                                "占用配件库存失败：物料" + quotaDtl.getMaterialName() + "，新s/n" + quotaDtl.getNewSnNo() +"，已经被占用。");
                    }
                } else {

                    // 库存信息为空
                    if (wh == null) {
                        throw new ServiceException(
                                "占用配件库存失败：物料" + quotaDtl.getMaterialName() + "，没有可占用的库存。");
                    }

                    // 可用数量取得
                    int availableNum = defaultInt(wh.getNum())
                            - defaultInt(wh.getOccupationNo());
                    // 可用数量小于数量时
                    if (availableNum < quotaDtl.getNum()) {
                        throw new ServiceException(
                                "占用配件库存失败：物料" + quotaDtl.getMaterialName() + "可用库存小于占用数量。");
                    }
                }

                // 库存占用
                wh.setOccupationNo(defaultInt(wh.getOccupationNo()) + quotaDtl.getNum());
                wh.preUpdate();
                smWarehouseInfoDao.update(wh);

                isOccupancy = true;
            }

            // 有占用报价单信息更新
            if (isOccupancy) {
                // 报价单信息取得
                quotation.setIfOccupy(CommonConstants.YES);
                quotation.preUpdate();
                rmQuotationDao.update(quotation);
            }

        } else {
            // 库存取消时
            // 明细行循环
            for (RmQuotationDtl quotaDtl : quotaDtlList) {
                // 服务时，无数量，不占用
                if (StringUtils.equals(quotaDtl.getItemType(),
                        CommonConstants.QUOTATION_DTL_ITEM_TYPE_2)) {
                    continue;
                }

                // 明细行对应的库存信息取得
                SmWarehouseInfo wh = warehouseInfoMap.get(quotaDtl.getId());
                // 库存信息为空,无需取消占用
                if (wh == null) {
                    continue;
                }

                // 占用数量小于数量时
                if (defaultInt(wh.getOccupationNo()) < quotaDtl.getNum()) {
                    throw new ServiceException("取消配件库存失败：物料" + quotaDtl.getMaterialName()
                            + "当前已占用库存数量小于取消数量。");
                }

                // 库存占用
                wh.setOccupationNo(defaultInt(wh.getOccupationNo()) - quotaDtl.getNum());
                wh.preUpdate();
                smWarehouseInfoDao.update(wh);

                isOccupancy = true;
            }

            // 有占用报价单信息更新
            if (isOccupancy) {
                // 报价单信息取得
                quotation.setIfOccupy(CommonConstants.NO);
                quotation.preUpdate();
                rmQuotationDao.update(quotation);
            }
        }

        return quotation;
    }

    /**
     * 最终报价单确认出库
     * 
     * @param finalQuotaId
     *            最终报价单id
     * 
     * @return 最终报价单信息
     */
    @Transactional(readOnly = false)
    public RmQuotation finalQuotaOutstock(String finalQuotaId) {

        // 是否存在取消预报价单配件库存占用
        boolean isPreCancle = false;
        // 是否存在终报价单出库
        boolean isFinalOut = false;
        // 待更新库存信息list
        Map<String, SmWarehouseInfo> updateWareHouseMap = new HashMap<String, SmWarehouseInfo>();
        // 最终报价单信息取得
        RmQuotation quotation = this.get(finalQuotaId);

        // 根据维修编号查找预报价单id
        String preId = rmQuotationDao.getPreQuotaIdByRepairNo(quotation.getRepairNo());
        // 预报价单信息取得
        RmQuotation preQuotation = rmQuotationDao.get(preId);
        // 预报价单不为空 且预报价单占用了库存
        if (preQuotation != null
                && StringUtils.equals(preQuotation.getIfOccupy(), CommonConstants.YES)) {
            // 预报价单占用库存取消
            // 预报价单明细取得
            List<RmQuotationDtl> preQuotaDtlList = rmQuotationDtlDao
                    .getQuotaDtlListById(preId);

            // 预报价单明细对应的库存信息取得
            List<SmWarehouseInfo> warehouseInfoList = smWarehouseInfoDao
                    .getQuotaDtlInventory(preId);

            // 将库存信息转换为map
            Map<String, SmWarehouseInfo> warehouseInfoMap = ListUtils
                    .convertListToMap(warehouseInfoList, "quotaDtlId");

            // 明细行循环
            for (RmQuotationDtl quotaDtl : preQuotaDtlList) {
                // 服务时，无数量，不占用
                if (StringUtils.equals(quotaDtl.getItemType(),
                        CommonConstants.QUOTATION_DTL_ITEM_TYPE_2)) {
                    continue;
                }

                // 明细行对应的库存信息取得
                SmWarehouseInfo wh = warehouseInfoMap.get(quotaDtl.getId());
                // 库存信息为空,无需取消占用
                if (wh == null) {
                    continue;
                }

                // 占用数量小于数量时
                if (defaultInt(wh.getOccupationNo()) < quotaDtl.getNum()) {
                    throw new ServiceException("报价单配件出库失败：预报价单配件占用库存取消失败，预报价单物料"
                            + quotaDtl.getMaterialName() + "的当前已占用库存数量小于取消数量。");
                }

                // 占用库存取消
                wh.setOccupationNo(defaultInt(wh.getOccupationNo()) - quotaDtl.getNum());

                // 更新库存信息map添加
                updateWareHouseMap.put(wh.getId(), wh);

                isPreCancle = true;
            }
        }

        // 最终报价单明细
        List<RmQuotationDtl> finalQuotaDtlList = quotation.getQuotationDtlList();

        // 最终报价单明细对应的库存信息取得
        List<SmWarehouseInfo> finalWarehouseInfoList = smWarehouseInfoDao
                .getQuotaDtlInventory(finalQuotaId);

        // 将最终报价单库存信息转换为map
        Map<String, SmWarehouseInfo> finalWarehouseMap = ListUtils
                .convertListToMap(finalWarehouseInfoList, "quotaDtlId");

        // 最终报价单明细行循环出库
        for (RmQuotationDtl finalQuotaDtl : finalQuotaDtlList) {
            // 服务时，无需出库
            if (StringUtils.equals(finalQuotaDtl.getItemType(),
                    CommonConstants.QUOTATION_DTL_ITEM_TYPE_2)) {
                continue;
            }

            // 旧sn必须输入
            if (StringUtils.equals(finalQuotaDtl.getIfSn(), CommonConstants.YES)) {
                if (StringUtils.isBlank(finalQuotaDtl.getSnNo())) {
                    throw new ServiceException("报价单配件出库失败：物料"
                            + finalQuotaDtl.getMaterialName() + "的旧S/N必须输入，请输入并保存后再操作。");
                }
            }

            // 最终报价单明细行对应的db库存信息取得
            SmWarehouseInfo finalWh = finalWarehouseMap.get(finalQuotaDtl.getId());
            // 库存信息为空
            if (finalWh == null) {
                throw new ServiceException("报价单配件出库失败：明细行" + finalQuotaDtl.getLineNo()
                        + "物料" + finalQuotaDtl.getMaterialName() + "，没有库存可出库。");
            }

            // 首先取预报价单明细取消的库存信息
            SmWarehouseInfo cancleWh = updateWareHouseMap.get(finalWh.getId());
            // 不存在预报价单取消库存信息
            if (cancleWh != null) {
                // 则取得终报价单库存信息
                finalWh = cancleWh;
            }

            // 可用数量取得
            int availableNum = defaultInt(finalWh.getNum())
                    - defaultInt(finalWh.getOccupationNo());
            // 可用数量小于出库数量时
            if (availableNum < finalQuotaDtl.getNum()) {
                throw new ServiceException(
                        "报价单配件出库失败：物料" + finalQuotaDtl.getMaterialName() + "可用库存小于出库数量。");
            }

            // 配件出库
            finalWh.setNum(defaultInt(finalWh.getNum()) - finalQuotaDtl.getNum());

            // 出库后的库存信息存入待更新map
            updateWareHouseMap.put(finalWh.getId(), finalWh);

            isFinalOut = true;
        }

        // 库存信息更新
        for (SmWarehouseInfo whInfo : updateWareHouseMap.values()) {
            whInfo.preUpdate();
            smWarehouseInfoDao.update(whInfo);
        }

        // 处理日期
        Date processDate = new Date();
        // 出入库履历编号取得
        String storageId = CommonConstants.STORAGE_TYPE_21 + commonService
                .getNextSequence(CommonConstants.TRANSACTION_NUMBER_STORAGE,
                        CommonConstants.STORAGE_TYPE_21, 8);
        // 最终报价单明细行出库
        for (RmQuotationDtl finalQuotaDtl : finalQuotaDtlList) {
            // 服务时，无需出库
            if (StringUtils.equals(finalQuotaDtl.getItemType(),
                    CommonConstants.QUOTATION_DTL_ITEM_TYPE_2)) {
                continue;
            }

            // 待更新出库信息创建
            SmStorageInfo storageInfo = new SmStorageInfo();
            // 出入库履历编号
            storageInfo.setStorageId(storageId);
            // 出入库类型
            storageInfo.setStorageType(CommonConstants.STORAGE_TYPE_21);
            // 报价单编号
            storageInfo.setOrderNo(quotation.getQuotationNo());
            // sn
            storageInfo.setSnNo(finalQuotaDtl.getNewSnNo());
            // 物料号
            storageInfo.setMaterialNo(finalQuotaDtl.getMaterialNo());
            // 数量
            storageInfo.setNum(finalQuotaDtl.getNum());
            // 库房
            storageInfo.setWarehouse(finalQuotaDtl.getWarehouse());
            // 处理日期
            storageInfo.setProcessDate(processDate);
            // 负责人
            storageInfo.setResponsiblePersonId(quotation.getResponsiblePersonId());
            storageInfo.preInsert();
            smStorageInfoDao.insert(storageInfo);
        }

        // 预报价单取消库存占用
        if (isPreCancle) {
            preQuotation.setIfOccupy(CommonConstants.NO);
            preQuotation.preUpdate();
            rmQuotationDao.update(preQuotation);
        }

        // 终报价单出库
        // if (isFinalOut) {
            quotation.setIfOut(CommonConstants.YES);
            quotation.preUpdate();
            rmQuotationDao.update(quotation);
        //}

        return quotation;
    }

    /**
     * 取得报价单维修信息
     * 
     * @param repairNo
     *            维修编号
     * 
     * @return 报价单维修信息
     */
    public RmRepairInfoEx getQuotaRepairInfo(String repairNo) {
        RmRepairInfoEx repairInfo = rmRepairInfoDao.getRepairInfoByNo(repairNo);
        if (repairInfo == null) {
            throw new ServiceException("报价单对应的维修信息取得失败。");
        }
        return repairInfo;
    }

    /**
     * 导出报价单明细excel数据取得
     * 
     * @param quotaId
     *            报价单id
     * @return 报价单明细excel导出数据
     */
    public List<RmQuotationDtlExcel> exportQuotaDtlList(String quotaId) {
        // 报价单明细取得
        List<RmQuotationDtl> quotaDtlList = rmQuotationDtlDao
                .getQuotaDtlListById(quotaId);

        RmQuotationDtlExcel quotaDtlExcel = null;
        List<RmQuotationDtlExcel> quotaDtlExcelList = Lists.newArrayList();
        NumberFormat nf = new DecimalFormat("#,##0.00");
        for (RmQuotationDtl quotaDtl : quotaDtlList) {
            quotaDtlExcel = new RmQuotationDtlExcel();
            // 分类
            quotaDtlExcel.setItemType(quotaDtl.getItemType());
            // 物料号
            quotaDtlExcel.setMaterialNo(quotaDtl.getMaterialNo());
            // 物料名
            quotaDtlExcel.setMaterialName(quotaDtl.getMaterialName());
            // 新sn
            quotaDtlExcel.setNewSnNo(quotaDtl.getNewSnNo());
            // 新sn生产日期
            if (quotaDtl.getNewProductionDate() == null) {
                quotaDtlExcel.setNewProductionDate("");
            } else {
                quotaDtlExcel.setNewProductionDate(
                        DateUtils.formatDate(quotaDtl.getNewProductionDate()));
            }
            // 单价
            if (quotaDtl.getUnitPrice() == null) {
                quotaDtlExcel.setUnitPrice("");
            } else {
                quotaDtlExcel.setUnitPrice(nf.format(quotaDtl.getUnitPrice()));
            }
            // 数量
            quotaDtlExcel.setNum(quotaDtl.getNum());
            // 金额
            if (quotaDtl.getTotalAmount() == null) {
                quotaDtlExcel.setTotalAmount("");
            } else {
                quotaDtlExcel.setTotalAmount(nf.format(quotaDtl.getTotalAmount()));
            }
            // 是否保内
            quotaDtlExcel.setIfWarranty(quotaDtl.getIfWarranty());
            // 实际金额
            if (quotaDtl.getActAmount() == null) {
                quotaDtlExcel.setActAmount("");
            } else {
                quotaDtlExcel.setActAmount(nf.format(quotaDtl.getActAmount()));
            }
            // sn
            quotaDtlExcel.setSnNo(quotaDtl.getSnNo());
            // sn生产日期
            if (quotaDtl.getProductionDate() == null) {
                quotaDtlExcel.setProductionDate("");
            } else {
                quotaDtlExcel.setProductionDate(
                        DateUtils.formatDate(quotaDtl.getProductionDate()));
            }
            quotaDtlExcelList.add(quotaDtlExcel);
        }
        return quotaDtlExcelList;
    }

    /**
     * 取得int设置默认值
     */
    public Integer defaultInt(Integer value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    /**
     * 报价单是否保内默认值取得
     */
    public String getDefaultWarranty(String repairType) {
        // 维修分类为保内
        if (StringUtils.equals(repairType, CommonConstants.REPAIR_TYPE_1)) {
            // 默认是否保内：是
            return CommonConstants.YES;

            // 维修分类为保外
        } else if (StringUtils.equals(repairType, CommonConstants.REPAIR_TYPE_2)) {
            // 默认是否保内：否
            return CommonConstants.NO;

            // 维修分类为初期不良
        } else if (StringUtils.equals(repairType, CommonConstants.REPAIR_TYPE_4)) {
            // 默认是否保内：是
            return CommonConstants.YES;
        } else {

            return null;
        }
    }

    /**
     * 报价单明细总金额计算
     */
    public BigDecimal getQuotaTotalAmount(List<RmQuotationDtl> quotaDtlList) {
        // 报价单总金额计算
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (RmQuotationDtl qtDtl : quotaDtlList) {
            totalAmount = StringUtils.add(totalAmount, qtDtl.getActAmount());
        }
        // 报价单明细总金额
        return totalAmount;
    }

	// 根据模板输出合同报价单
	public void exportQuota01(HttpServletResponse response, String quotaId) {
		RmQuotation rmQuotation = rmQuotationDao.getQuotaById(quotaId);
		BeanMap singleBean = BeanMap.create(new ExportFileHead());
		
		singleBean.put("kehumingcheng", rmQuotation.getCustomerName());
		singleBean.put("fukuanfangshi", DictUtils.getDictLabel("1", "DM0057", ""));
		singleBean.put("baojiaren", UserUtils.getUser().getName());
		singleBean.put("baojiariqi", DateUtils.getDate("yyyy年MM月dd日"));
		
		String model = rmQuotationDao.getQuotaSnModelByRepairNo(rmQuotation.getRepairNo());

        // 报价单明细取得
        List<RmQuotationDtl> quotaDtlList = rmQuotationDtlDao.getQuotaDtlListById(quotaId);
		
		List<BeanMap> multiBeans = new ArrayList<BeanMap>();
		BeanMap multiBean = null;

		BigDecimal total = BigDecimal.ZERO;
		int index = 1;
        NumberFormat nf = new DecimalFormat("#,##0.00");

        for (RmQuotationDtl quotaDtl : quotaDtlList) {
			
			multiBean = BeanMap.create(new ExportFileDtl());

			multiBean.put("xuhao", Integer.toString(index++));
			multiBean.put("chanpinmingcheng", quotaDtl.getMaterialNameForExport());
//			multiBean.put("xinghao", quotaDtl.getModelForExport());
			if (!StringUtils.isEmptyNull(quotaDtl.getModelForExport())) {
				multiBean.put("xinghao", quotaDtl.getModelForExport());
			} else {
				multiBean.put("xinghao", model);
			}
            // 单价
            if (quotaDtl.getUnitPrice() == null) {
            	multiBean.put("danjia", "");
            } else {
            	multiBean.put("danjia", nf.format(quotaDtl.getUnitPrice()));
            }
            // 实际金额
            if (quotaDtl.getNum() == null) {
            	multiBean.put("shuliang", "");
            } else {
            	multiBean.put("shuliang", quotaDtl.getNum().toString());
            }
            // 实际金额
            if (quotaDtl.getActAmount() == null) {
            	multiBean.put("zongjia", "");
            } else {
            	multiBean.put("zongjia", nf.format(quotaDtl.getActAmount()));
            }
			
			total = StringUtils.add(total, quotaDtl.getActAmount());

			multiBeans.add(multiBean);
        }

		singleBean.put("heji", "合计（含税）￥" + nf.format(total));
		singleBean.put("daxieheji", "（合计大写：" + NumberToCNUtils.number2CNMontrayUnit(total) + "）");

		Workbook workbook = FileExportUtil.wrokbookWithTemplate(quotaTemplatePath + TEMPLATE_NAME_01, singleBean, multiBeans);
		String fileName = EXPORT_FILE_NAME_01 + "_" + rmQuotation.getRepairNo() + EXPORT_FILE_SUFIX;
		
		FileExportUtil.write(response, workbook, fileName);
    }

	// 根据模板输出合同报价单
	public void exportQuota02(HttpServletResponse response, String quotaId) {
		RmQuotation rmQuotation = rmQuotationDao.getQuotaById(quotaId);
		BeanMap singleBean = BeanMap.create(new ExportFileHead());
		
		singleBean.put("weixiubianhao", rmQuotation.getRepairNo());
		singleBean.put("baojiaren", UserUtils.getUser().getName());
		singleBean.put("baojiariqi", DateUtils.getDate("yyyy年MM月dd日"));
		
		singleBean.put("fuzeren", rmQuotation.getContactsName());
		singleBean.put("dizhi", rmQuotation.getAddress());
		singleBean.put("dianhua", rmQuotation.getTelephone());
		
		String model = rmQuotationDao.getQuotaSnModelByRepairNo(rmQuotation.getRepairNo());

        // 报价单明细取得
        List<RmQuotationDtl> quotaDtlList = rmQuotationDtlDao.getQuotaDtlListById(quotaId);
		
		List<BeanMap> multiBeans = new ArrayList<BeanMap>();
		BeanMap multiBean = null;

		BigDecimal total = BigDecimal.ZERO;
		int index = 1;
        NumberFormat nf = new DecimalFormat("#,##0.00");

        for (RmQuotationDtl quotaDtl : quotaDtlList) {
			
			multiBean = BeanMap.create(new ExportFileDtl());

			multiBean.put("xuhao", Integer.toString(index++));
			multiBean.put("chanpinmingcheng", quotaDtl.getMaterialNameForExport());
//			multiBean.put("xinghao", quotaDtl.getModelForExport());
			if (!StringUtils.isEmptyNull(quotaDtl.getModelForExport())) {
				multiBean.put("xinghao", quotaDtl.getModelForExport());
			} else {
				multiBean.put("xinghao", model);
			}
            // 单价
            if (quotaDtl.getUnitPrice() == null) {
            	multiBean.put("danjia", "");
            } else {
            	multiBean.put("danjia", nf.format(quotaDtl.getUnitPrice()));
            }
            // 实际金额
            if (quotaDtl.getNum() == null) {
            	multiBean.put("shuliang", "");
            } else {
            	multiBean.put("shuliang", quotaDtl.getNum().toString());
            }
            // 实际金额
            if (quotaDtl.getActAmount() == null) {
            	multiBean.put("zongjia", "");
            } else {
            	multiBean.put("zongjia", nf.format(quotaDtl.getActAmount()));
            }
			
			total = StringUtils.add(total, quotaDtl.getActAmount());

			multiBeans.add(multiBean);
        }

		singleBean.put("heji", "合计（含税）￥" + nf.format(total));
		singleBean.put("daxieheji", "（合计大写：" + NumberToCNUtils.number2CNMontrayUnit(total) + "）");

		Workbook workbook = FileExportUtil.wrokbookWithTemplate(quotaTemplatePath + TEMPLATE_NAME_02, singleBean, multiBeans);
		String fileName = EXPORT_FILE_NAME_02 + "_" + rmQuotation.getRepairNo() + EXPORT_FILE_SUFIX;
		
		FileExportUtil.write(response, workbook, fileName);
    }
}