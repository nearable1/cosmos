package com.xiaoniu.walking.web.core.model.auto;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库管理
 * @author chenguohua
 * @date 2019/04/20 11：19
 */
public class Database implements Serializable {
    private static final long serialVersionUID = 3921752637944679694L;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.TABLE_CATALOG
     *
     * @mbggenerated
     */
    private String tableCatalog;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.TABLE_SCHEMA
     *
     * @mbggenerated
     */
    private String tableSchema;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.TABLE_NAME
     *
     * @mbggenerated
     */
    private String tableName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.TABLE_TYPE
     *
     * @mbggenerated
     */
    private String tableType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.ENGINE
     *
     * @mbggenerated
     */
    private String engine;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.VERSION
     *
     * @mbggenerated
     */
    private Long version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.ROW_FORMAT
     *
     * @mbggenerated
     */
    private String rowFormat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.TABLE_ROWS
     *
     * @mbggenerated
     */
    private Long tableRows;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.AVG_ROW_LENGTH
     *
     * @mbggenerated
     */
    private Long avgRowLength;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.DATA_LENGTH
     *
     * @mbggenerated
     */
    private Long dataLength;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.MAX_DATA_LENGTH
     *
     * @mbggenerated
     */
    private Long maxDataLength;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.INDEX_LENGTH
     *
     * @mbggenerated
     */
    private Long indexLength;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.DATA_FREE
     *
     * @mbggenerated
     */
    private Long dataFree;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.AUTO_INCREMENT
     *
     * @mbggenerated
     */
    private Long autoIncrement;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.UPDATE_TIME
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.CHECK_TIME
     *
     * @mbggenerated
     */
    private Date checkTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.TABLE_COLLATION
     *
     * @mbggenerated
     */
    private String tableCollation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.CHECKSUM
     *
     * @mbggenerated
     */
    private Long checksum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.CREATE_OPTIONS
     *
     * @mbggenerated
     */
    private String createOptions;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TABLES.TABLE_COMMENT
     *
     * @mbggenerated
     */
    private String tableComment;

    /**
     * 创建时间string
     */
    private String createTimeStr;

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.TABLE_CATALOG
     *
     * @return the value of TABLES.TABLE_CATALOG
     *
     * @mbggenerated
     */
    public String getTableCatalog() {
        return tableCatalog;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.TABLE_CATALOG
     *
     * @param tableCatalog the value for TABLES.TABLE_CATALOG
     *
     * @mbggenerated
     */
    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog == null ? null : tableCatalog.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.TABLE_SCHEMA
     *
     * @return the value of TABLES.TABLE_SCHEMA
     *
     * @mbggenerated
     */
    public String getTableSchema() {
        return tableSchema;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.TABLE_SCHEMA
     *
     * @param tableSchema the value for TABLES.TABLE_SCHEMA
     *
     * @mbggenerated
     */
    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema == null ? null : tableSchema.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.TABLE_NAME
     *
     * @return the value of TABLES.TABLE_NAME
     *
     * @mbggenerated
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.TABLE_NAME
     *
     * @param tableName the value for TABLES.TABLE_NAME
     *
     * @mbggenerated
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.TABLE_TYPE
     *
     * @return the value of TABLES.TABLE_TYPE
     *
     * @mbggenerated
     */
    public String getTableType() {
        return tableType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.TABLE_TYPE
     *
     * @param tableType the value for TABLES.TABLE_TYPE
     *
     * @mbggenerated
     */
    public void setTableType(String tableType) {
        this.tableType = tableType == null ? null : tableType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.ENGINE
     *
     * @return the value of TABLES.ENGINE
     *
     * @mbggenerated
     */
    public String getEngine() {
        return engine;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.ENGINE
     *
     * @param engine the value for TABLES.ENGINE
     *
     * @mbggenerated
     */
    public void setEngine(String engine) {
        this.engine = engine == null ? null : engine.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.VERSION
     *
     * @return the value of TABLES.VERSION
     *
     * @mbggenerated
     */
    public Long getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.VERSION
     *
     * @param version the value for TABLES.VERSION
     *
     * @mbggenerated
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.ROW_FORMAT
     *
     * @return the value of TABLES.ROW_FORMAT
     *
     * @mbggenerated
     */
    public String getRowFormat() {
        return rowFormat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.ROW_FORMAT
     *
     * @param rowFormat the value for TABLES.ROW_FORMAT
     *
     * @mbggenerated
     */
    public void setRowFormat(String rowFormat) {
        this.rowFormat = rowFormat == null ? null : rowFormat.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.TABLE_ROWS
     *
     * @return the value of TABLES.TABLE_ROWS
     *
     * @mbggenerated
     */
    public Long getTableRows() {
        return tableRows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.TABLE_ROWS
     *
     * @param tableRows the value for TABLES.TABLE_ROWS
     *
     * @mbggenerated
     */
    public void setTableRows(Long tableRows) {
        this.tableRows = tableRows;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.AVG_ROW_LENGTH
     *
     * @return the value of TABLES.AVG_ROW_LENGTH
     *
     * @mbggenerated
     */
    public Long getAvgRowLength() {
        return avgRowLength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.AVG_ROW_LENGTH
     *
     * @param avgRowLength the value for TABLES.AVG_ROW_LENGTH
     *
     * @mbggenerated
     */
    public void setAvgRowLength(Long avgRowLength) {
        this.avgRowLength = avgRowLength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.DATA_LENGTH
     *
     * @return the value of TABLES.DATA_LENGTH
     *
     * @mbggenerated
     */
    public Long getDataLength() {
        return dataLength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.DATA_LENGTH
     *
     * @param dataLength the value for TABLES.DATA_LENGTH
     *
     * @mbggenerated
     */
    public void setDataLength(Long dataLength) {
        this.dataLength = dataLength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.MAX_DATA_LENGTH
     *
     * @return the value of TABLES.MAX_DATA_LENGTH
     *
     * @mbggenerated
     */
    public Long getMaxDataLength() {
        return maxDataLength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.MAX_DATA_LENGTH
     *
     * @param maxDataLength the value for TABLES.MAX_DATA_LENGTH
     *
     * @mbggenerated
     */
    public void setMaxDataLength(Long maxDataLength) {
        this.maxDataLength = maxDataLength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.INDEX_LENGTH
     *
     * @return the value of TABLES.INDEX_LENGTH
     *
     * @mbggenerated
     */
    public Long getIndexLength() {
        return indexLength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.INDEX_LENGTH
     *
     * @param indexLength the value for TABLES.INDEX_LENGTH
     *
     * @mbggenerated
     */
    public void setIndexLength(Long indexLength) {
        this.indexLength = indexLength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.DATA_FREE
     *
     * @return the value of TABLES.DATA_FREE
     *
     * @mbggenerated
     */
    public Long getDataFree() {
        return dataFree;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.DATA_FREE
     *
     * @param dataFree the value for TABLES.DATA_FREE
     *
     * @mbggenerated
     */
    public void setDataFree(Long dataFree) {
        this.dataFree = dataFree;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.AUTO_INCREMENT
     *
     * @return the value of TABLES.AUTO_INCREMENT
     *
     * @mbggenerated
     */
    public Long getAutoIncrement() {
        return autoIncrement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.AUTO_INCREMENT
     *
     * @param autoIncrement the value for TABLES.AUTO_INCREMENT
     *
     * @mbggenerated
     */
    public void setAutoIncrement(Long autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.CREATE_TIME
     *
     * @return the value of TABLES.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.CREATE_TIME
     *
     * @param createTime the value for TABLES.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.UPDATE_TIME
     *
     * @return the value of TABLES.UPDATE_TIME
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.UPDATE_TIME
     *
     * @param updateTime the value for TABLES.UPDATE_TIME
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.CHECK_TIME
     *
     * @return the value of TABLES.CHECK_TIME
     *
     * @mbggenerated
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.CHECK_TIME
     *
     * @param checkTime the value for TABLES.CHECK_TIME
     *
     * @mbggenerated
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.TABLE_COLLATION
     *
     * @return the value of TABLES.TABLE_COLLATION
     *
     * @mbggenerated
     */
    public String getTableCollation() {
        return tableCollation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.TABLE_COLLATION
     *
     * @param tableCollation the value for TABLES.TABLE_COLLATION
     *
     * @mbggenerated
     */
    public void setTableCollation(String tableCollation) {
        this.tableCollation = tableCollation == null ? null : tableCollation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.CHECKSUM
     *
     * @return the value of TABLES.CHECKSUM
     *
     * @mbggenerated
     */
    public Long getChecksum() {
        return checksum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.CHECKSUM
     *
     * @param checksum the value for TABLES.CHECKSUM
     *
     * @mbggenerated
     */
    public void setChecksum(Long checksum) {
        this.checksum = checksum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.CREATE_OPTIONS
     *
     * @return the value of TABLES.CREATE_OPTIONS
     *
     * @mbggenerated
     */
    public String getCreateOptions() {
        return createOptions;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.CREATE_OPTIONS
     *
     * @param createOptions the value for TABLES.CREATE_OPTIONS
     *
     * @mbggenerated
     */
    public void setCreateOptions(String createOptions) {
        this.createOptions = createOptions == null ? null : createOptions.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TABLES.TABLE_COMMENT
     *
     * @return the value of TABLES.TABLE_COMMENT
     *
     * @mbggenerated
     */
    public String getTableComment() {
        return tableComment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TABLES.TABLE_COMMENT
     *
     * @param tableComment the value for TABLES.TABLE_COMMENT
     *
     * @mbggenerated
     */
    public void setTableComment(String tableComment) {
        this.tableComment = tableComment == null ? null : tableComment.trim();
    }
}