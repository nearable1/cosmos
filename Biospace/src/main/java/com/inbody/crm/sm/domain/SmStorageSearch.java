package com.inbody.crm.sm.domain;

import java.util.Map;

import com.inbody.crm.common.persistence.DataEntity;

public class SmStorageSearch extends DataEntity<SmStorageSearch> {

    private static final long serialVersionUID = 7943377209045631698L;

    /** 物料类型 */
    private String materialType;

    /** 物料号 */
    private String materialNo;

    /** 物料名称 */
    private String materialName;

    /** 物料型号 */
    private String model;

    /** 总数量 */
    private Integer totalNum;

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

    /** 各仓库库存信息 */
    private Map<String, Integer> whMap;

    /** 显示库存不足记录 */
    private boolean showLowStock;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
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

    public Map<String, Integer> getWhMap() {
        return whMap;
    }

    public void setWhMap(Map<String, Integer> whMap) {
        this.whMap = whMap;
    }

    public boolean getShowLowStock() {
        return showLowStock;
    }

    public void setShowLowStock(boolean showLowStock) {
        this.showLowStock = showLowStock;
    }
}
