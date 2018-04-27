/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.domain;

import com.inbody.crm.common.utils.excel.annotation.ExcelField;

/**
 * 报价单明细Excel
 * 
 * @author yangj
 * @version 2017-10-13
 */
public class RmQuotationDtlExcel {

    private String itemType; // 报价项目分类 : 代码
    private String materialNo; // 物料号
    private String materialName; // 物料名
    private String newSnNo; // 新SN
    private String newProductionDate; // 新sn所对就的生产日期
    private String unitPrice; // 单价
    private Integer num; // 数量
    private String totalAmount; // 总金额
    private String ifWarranty; // 是否保内
    private String actAmount; // 实际金额
    private String snNo; // SN
    private String productionDate; // sn所对就的生产日期

    @ExcelField(title = "分类", type = 1, align = 1, sort = 1, dictType = "DM0031")
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @ExcelField(title = "物料号", type = 1, align = 1, sort = 2)
    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    @ExcelField(title = "物料名称", type = 1, align = 1, sort = 3)
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @ExcelField(title = "新S/N", type = 1, align = 1, sort = 4)
    public String getNewSnNo() {
        return newSnNo;
    }

    public void setNewSnNo(String newSnNo) {
        this.newSnNo = newSnNo;
    }

    @ExcelField(title = "生产日期", type = 1, align = 1, sort = 5)
    public String getNewProductionDate() {
        return newProductionDate;
    }

    public void setNewProductionDate(String newProductionDate) {
        this.newProductionDate = newProductionDate;
    }

    @ExcelField(title = "单价", type = 1, align = 3, sort = 6)
    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    @ExcelField(title = "数量", type = 1, align = 3, sort = 7)
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @ExcelField(title = "金额", type = 1, align = 3, sort = 8)
    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @ExcelField(title = "是否保内", type = 1, align = 1, sort = 9, dictType = "yes_no")
    public String getIfWarranty() {
        return ifWarranty;
    }

    public void setIfWarranty(String ifWarranty) {
        this.ifWarranty = ifWarranty;
    }

    @ExcelField(title = "最终金额", type = 1, align = 3, sort = 10)
    public String getActAmount() {
        return actAmount;
    }

    public void setActAmount(String actAmount) {
        this.actAmount = actAmount;
    }

    @ExcelField(title = "旧S/N", type = 1, align = 1, sort = 11)
    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    @ExcelField(title = "生产日期", type = 1, align = 1, sort = 12)
    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }
}