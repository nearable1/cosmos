package com.xiaoniu.walking.web.core.model.ext;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "white_list")
public class WhiteListExt {
    @Id
    private Integer id;

    /**
     * 姓名
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 手机号
     */
    @Column(name = "phone_num")
    private String phoneNum;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_man")
    private String createMan;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 修改人
     */
    @Column(name = "modify_man")
    private String modifyMan;

    /**
     * appname
     */
    @Column(name = "app_name")
    private int appName;

    /**
     * 类型：1-登录白名单，2-产品白名单
     */
    private Byte type;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return nick_name - 姓名
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置姓名
     *
     * @param nickName 姓名
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    /**
     * 获取类型：1-登录白名单，2-产品白名单
     *
     * @return type - 类型：1-登录白名单，2-产品白名单
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置类型：1-登录白名单，2-产品白名单
     *
     * @param type 类型：1-登录白名单，2-产品白名单
     */
    public void setType(Byte type) {
        this.type = type;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getAppName() {
        return appName;
    }

    public void setAppName(int appName) {
        this.appName = appName;
    }
}