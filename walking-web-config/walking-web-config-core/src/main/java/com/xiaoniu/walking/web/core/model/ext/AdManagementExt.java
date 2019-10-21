package com.xiaoniu.walking.web.core.model.ext;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "ad_management")
public class AdManagementExt extends DefaultPageParameter implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 广告位名称
     */
    @Column(name = "code_name")
    private String codeName;

    /**
     * 广告代码位ID
     */
    @Column(name = "code_id")
    private String codeId;

    /**
     * 投放类型
     */
    private Integer source;

    /**
     * 选择应用
     */
    @Column(name = "app_name")
    private Integer appName;

    /**
     * 作用版本号
     */
    private String version;

    /**
     * 作用渠道号
     */
    private String channel;

    /**
     * 广告位置
     */
    private String position;

    /**
     * 看看广告间隔
     */
    private String kankanAdIntervalNumber;

    public String getKankanAdIntervalNumber() {
        return kankanAdIntervalNumber;
    }

    public void setKankanAdIntervalNumber(String kankanAdIntervalNumber) {
        this.kankanAdIntervalNumber = kankanAdIntervalNumber;
    }

    /**
     * 开关：1-开启 2-关闭
     */
    private Integer state;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 创建人
     */
    @Column(name = "create_man")
    private String createMan;

    /**
     * 修改人
     */
    @Column(name = "modify_man")
    private String modifyMan;


    /**
     * 作用版本范围 1-大于，2-大于等于，3-等于，4-小于，5-小于等于，6-不等于
     */
    private Integer rangeVersion;

    /**
     * 广告位置翻译
     */
    private String positionStr;

    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取广告位名称
     *
     * @return code_name - 广告位名称
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * 设置广告位名称
     *
     * @param codeName 广告位名称
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * 获取广告代码位ID
     *
     * @return code_id - 广告代码位ID
     */
    public String getCodeId() {
        return codeId;
    }

    /**
     * 设置广告代码位ID
     *
     * @param codeId 广告代码位ID
     */
    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    /**
     * 获取投放类型
     *
     * @return source - 投放类型
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 设置投放类型
     *
     * @param source 投放类型
     */
    public void setSource(Integer source) {
        this.source = source;
    }

    /**
     * 获取选择应用
     *
     * @return app_name - 选择应用
     */
    public Integer getAppName() {
        return appName;
    }

    /**
     * 设置选择应用
     *
     * @param appName 选择应用
     */
    public void setAppName(Integer appName) {
        this.appName = appName;
    }

    /**
     * 获取作用版本号
     *
     * @return version - 作用版本号
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置作用版本号
     *
     * @param version 作用版本号
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获取作用渠道号
     *
     * @return channel - 作用渠道号
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置作用渠道号
     *
     * @param channel 作用渠道号
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取广告位置
     *
     * @return position - 广告位置
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置广告位置
     *
     * @param position 广告位置
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取开关：1-开启 2-关闭
     *
     * @return state - 开关：1-开启 2-关闭
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置开关：1-开启 2-关闭
     *
     * @param state 开关：1-开启 2-关闭
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取创建人
     *
     * @return create_man - 创建人
     */
    public String getCreateMan() {
        return createMan;
    }

    /**
     * 设置创建人
     *
     * @param createMan 创建人
     */
    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    /**
     * 获取修改人
     *
     * @return modify_man - 修改人
     */
    public String getModifyMan() {
        return modifyMan;
    }

    /**
     * 设置修改人
     *
     * @param modifyMan 修改人
     */
    public void setModifyMan(String modifyMan) {
        this.modifyMan = modifyMan;
    }


    public Integer getRangeVersion() {
        return rangeVersion;
    }

    public void setRangeVersion(Integer rangeVersion) {
        this.rangeVersion = rangeVersion;
    }
}