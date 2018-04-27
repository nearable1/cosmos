package com.inbody.crm.sm.domain;

import java.util.Date;

/**
 * 库存履历
 * 
 * @author yangj
 * @version 2017-10-24
 */
public class SmStorageResume {

    /** 物料类型 */
    private String materialType;

    /** 物料号 */
    private String materialNo;

    /** 物料名称 */
    private String materialName;

    /** 本周进货 */
    private Integer weekPurchasedNum;

    /** 本周销售 */
    private Integer weekSalesNum;

    /** 在库数量 */
    private Integer inStockNum;

    /** 各仓库数量 */
    private String warehouseNum;

    /** 已检数量 */
    private Integer testedNum;

    /** 可用数量 */
    private Integer availableNum;

    /** 报价单占用数量 */
    private Integer quotaOccupyNum;

    /** 已开票未发货数量 */
    private Integer invoicedUndeliveredNum;

    /** 不在库数量 */
    private Integer outStockNum;

    /** 借出数量 */
    private Integer lendingNum;

    /** 已发货未开票数量 */
    private Integer unInvoicedDeliveredNum;

    /** 安全库存数量 */
    private Integer safetyStockNum;

    /** 未到货数量 */
    private Integer notArrivedNum;

    /** 库存履历期间开始日期 */
    private Date startResumeDate;

    /** 库存履历期间结束日期 */
    private Date endResumeDate;

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getWeekPurchasedNum() {
        return weekPurchasedNum;
    }

    public void setWeekPurchasedNum(Integer weekPurchasedNum) {
        this.weekPurchasedNum = weekPurchasedNum;
    }

    public Integer getWeekSalesNum() {
        return weekSalesNum;
    }

    public void setWeekSalesNum(Integer weekSalesNum) {
        this.weekSalesNum = weekSalesNum;
    }

    public Integer getInStockNum() {
        return inStockNum;
    }

    public void setInStockNum(Integer inStockNum) {
        this.inStockNum = inStockNum;
    }

    public String getWarehouseNum() {
        return warehouseNum;
    }

    public void setWarehouseNum(String warehouseNum) {
        this.warehouseNum = warehouseNum;
    }

    public Integer getTestedNum() {
        return testedNum;
    }

    public void setTestedNum(Integer testedNum) {
        this.testedNum = testedNum;
    }

    public Integer getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Integer availableNum) {
        this.availableNum = availableNum;
    }

    public Integer getQuotaOccupyNum() {
        return quotaOccupyNum;
    }

    public void setQuotaOccupyNum(Integer quotaOccupyNum) {
        this.quotaOccupyNum = quotaOccupyNum;
    }

    public Integer getInvoicedUndeliveredNum() {
        return invoicedUndeliveredNum;
    }

    public void setInvoicedUndeliveredNum(Integer invoicedUndeliveredNum) {
        this.invoicedUndeliveredNum = invoicedUndeliveredNum;
    }

    public Integer getOutStockNum() {
        return outStockNum;
    }

    public void setOutStockNum(Integer outStockNum) {
        this.outStockNum = outStockNum;
    }

    public Integer getLendingNum() {
        return lendingNum;
    }

    public void setLendingNum(Integer lendingNum) {
        this.lendingNum = lendingNum;
    }

    public Integer getUnInvoicedDeliveredNum() {
        return unInvoicedDeliveredNum;
    }

    public void setUnInvoicedDeliveredNum(Integer unInvoicedDeliveredNum) {
        this.unInvoicedDeliveredNum = unInvoicedDeliveredNum;
    }

    public Integer getSafetyStockNum() {
        return safetyStockNum;
    }

    public void setSafetyStockNum(Integer safetyStockNum) {
        this.safetyStockNum = safetyStockNum;
    }

    public Integer getNotArrivedNum() {
        return notArrivedNum;
    }

    public void setNotArrivedNum(Integer notArrivedNum) {
        this.notArrivedNum = notArrivedNum;
    }

    public Date getStartResumeDate() {
        return startResumeDate;
    }

    public void setStartResumeDate(Date startResumeDate) {
        this.startResumeDate = startResumeDate;
    }

    public Date getEndResumeDate() {
        return endResumeDate;
    }

    public void setEndResumeDate(Date endResumeDate) {
        this.endResumeDate = endResumeDate;
    }

}
