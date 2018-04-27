/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.inbody.crm.common.persistence.DataEntity;

/**
 * 履历Entity
 * 
 * @author yangj
 * @version 2017-10-24
 */
public class CoJobData extends DataEntity<CoJobData> {

    private static final long serialVersionUID = 1L;
    private String procInsId; // 流程实例
    private String type; // 类型 : 代码
    private String assId; // 关联ID : 附件关联的业务数据id
    private String fileName; // 文件名
    private Date expTurnoverMonth;
    private String method;
    private String executor; // 执行人
    private Date executeDateFrom; // 执行日开始
    private Date executorDateTo; // 执行日结束

    public CoJobData() {
        super();
    }

    public CoJobData(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "流程实例长度必须介于 0 和 64 之间")
    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    @Length(min = 1, max = 5, message = "类型 : 代码长度必须介于 1 和 5 之间")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min = 1, max = 64, message = "关联ID : 附件关联的业务数据id长度必须介于 1 和 64 之间")
    public String getAssId() {
        return assId;
    }

    public void setAssId(String assId) {
        this.assId = assId;
    }

    @Length(min = 1, max = 300, message = "文件名长度必须介于 1 和 300 之间")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getExpTurnoverMonth() {
        return expTurnoverMonth;
    }

    public void setExpTurnoverMonth(Date expTurnoverMonth) {
        this.expTurnoverMonth = expTurnoverMonth;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public Date getExecuteDateFrom() {
        return executeDateFrom;
    }

    public void setExecuteDateFrom(Date executeDateFrom) {
        this.executeDateFrom = executeDateFrom;
    }

    public Date getExecutorDateTo() {
        return executorDateTo;
    }

    public void setExecutorDateTo(Date executorDateTo) {
        this.executorDateTo = executorDateTo;
    }

}