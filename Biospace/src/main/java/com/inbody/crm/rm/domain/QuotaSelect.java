/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inbody.crm.common.persistence.DataEntity;

/**
 * 报价单录选择框
 * 
 * @author yangj
 * @version 2017-09-26
 */
public class QuotaSelect extends DataEntity<QuotaSelect> {

    private static final long serialVersionUID = -1233801128750485322L;

    private String id;
    private String text;
    /** 物料单价 */
    private String unitPrice;
    /** 数量 */
    private Integer num;
    /** 可用数量 */
    private Integer availableQuantity;
    /** 安全库存 */
    private Integer safetyStock;
    /** 物料类型 */
    private String type;
    /** 是否有sn */
    private String ifSn;
    /** sn生产日期 */
    private Date productionDate;
    /** 库房名 */
    private String warehouseName;
    /** 库房 */
    private String warehouse;
    /** sn */
    private String snNo;
    /** 查询关键字 */
    private String kw;
    /** 物料号 */
    private String materialNo;
    /** 原物料号 */
    private String oldMaterialNo;
    /** 原物料库存数量 */
    private Integer oldMaterialNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(Integer safetyStock) {
        this.safetyStock = safetyStock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIfSn() {
        return ifSn;
    }

    public void setIfSn(String ifSn) {
        this.ifSn = ifSn;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getOldMaterialNo() {
        return oldMaterialNo;
    }

    public void setOldMaterialNo(String oldMaterialNo) {
        this.oldMaterialNo = oldMaterialNo;
    }

    public Integer getOldMaterialNum() {
        return oldMaterialNum;
    }

    public void setOldMaterialNum(Integer oldMaterialNum) {
        this.oldMaterialNum = oldMaterialNum;
    }

}