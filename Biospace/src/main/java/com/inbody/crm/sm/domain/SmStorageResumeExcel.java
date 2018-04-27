package com.inbody.crm.sm.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

public class SmStorageResumeExcel {

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

    /** 仓库数量 */
    private Integer wh_10;

    /** 办公室数量 */
    private Integer wh_20;

    /** 北京数量 */
    private Integer wh_30;

    /** 广州数量 */
    private Integer wh_40;

    /** 成都数量 */
    private Integer wh_50;

    /** 长春数量 */
    private Integer wh_60;

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

    /** 未到货数量 */
    private Integer notArrivedNum;

    @ExcelField(title = "物料号", type = 1, align = 1, sort = 1)
    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    @ExcelField(title = "物料名称", type = 1, align = 1, sort = 2)
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @ExcelField(title = "本周进货", type = 1, align = 3, sort = 3)
    public Integer getWeekPurchasedNum() {
        return weekPurchasedNum;
    }

    public void setWeekPurchasedNum(Integer weekPurchasedNum) {
        this.weekPurchasedNum = weekPurchasedNum;
    }

    @ExcelField(title = "本周销售", type = 1, align = 3, sort = 4)
    public Integer getWeekSalesNum() {
        return weekSalesNum;
    }

    public void setWeekSalesNum(Integer weekSalesNum) {
        this.weekSalesNum = weekSalesNum;
    }

    @ExcelField(title = "在库数量", type = 1, align = 3, sort = 5)
    public Integer getInStockNum() {
        return inStockNum;
    }

    public void setInStockNum(Integer inStockNum) {
        this.inStockNum = inStockNum;
    }

    @ExcelField(title = "仓库", type = 1, align = 3, sort = 6)
    public Integer getWh_10() {
        return wh_10;
    }

    public void setWh_10(Integer wh_10) {
        this.wh_10 = wh_10;
    }

    @ExcelField(title = "办公室", type = 1, align = 3, sort = 7)
    public Integer getWh_20() {
        return wh_20;
    }

    public void setWh_20(Integer wh_20) {
        this.wh_20 = wh_20;
    }

    @ExcelField(title = "北京", type = 1, align = 3, sort = 8)
    public Integer getWh_30() {
        return wh_30;
    }

    public void setWh_30(Integer wh_30) {
        this.wh_30 = wh_30;
    }

    @ExcelField(title = "广州", type = 1, align = 3, sort = 9)
    public Integer getWh_40() {
        return wh_40;
    }

    public void setWh_40(Integer wh_40) {
        this.wh_40 = wh_40;
    }

    @ExcelField(title = "成都", type = 1, align = 3, sort = 10)
    public Integer getWh_50() {
        return wh_50;
    }

    public void setWh_50(Integer wh_50) {
        this.wh_50 = wh_50;
    }

    @ExcelField(title = "长春", type = 1, align = 3, sort = 11)
    public Integer getWh_60() {
        return wh_60;
    }

    public void setWh_60(Integer wh_60) {
        this.wh_60 = wh_60;
    }

    @ExcelField(title = "可用数量", type = 1, align = 3, sort = 12)
    public Integer getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Integer availableNum) {
        this.availableNum = availableNum;
    }

    @ExcelField(title = "报价单占用", type = 1, align = 3, sort = 13)
    public Integer getQuotaOccupyNum() {
        return quotaOccupyNum;
    }

    public void setQuotaOccupyNum(Integer quotaOccupyNum) {
        this.quotaOccupyNum = quotaOccupyNum;
    }

    @ExcelField(title = "已开票未发货", type = 1, align = 3, sort = 14)
    public Integer getInvoicedUndeliveredNum() {
        return invoicedUndeliveredNum;
    }

    public void setInvoicedUndeliveredNum(Integer invoicedUndeliveredNum) {
        this.invoicedUndeliveredNum = invoicedUndeliveredNum;
    }

    @ExcelField(title = "不在库数量", type = 1, align = 3, sort = 15)
    public Integer getOutStockNum() {
        return outStockNum;
    }

    public void setOutStockNum(Integer outStockNum) {
        this.outStockNum = outStockNum;
    }

    @ExcelField(title = "借出数量", type = 1, align = 3, sort = 16)
    public Integer getLendingNum() {
        return lendingNum;
    }

    public void setLendingNum(Integer lendingNum) {
        this.lendingNum = lendingNum;
    }

    @ExcelField(title = "已发货未开票", type = 1, align = 3, sort = 17)
    public Integer getUnInvoicedDeliveredNum() {
        return unInvoicedDeliveredNum;
    }

    public void setUnInvoicedDeliveredNum(Integer unInvoicedDeliveredNum) {
        this.unInvoicedDeliveredNum = unInvoicedDeliveredNum;
    }

    @ExcelField(title = "未到货数量", type = 1, align = 3, sort = 18)
    public Integer getNotArrivedNum() {
        return notArrivedNum;
    }

    public void setNotArrivedNum(Integer notArrivedNum) {
        this.notArrivedNum = notArrivedNum;
    }

}
