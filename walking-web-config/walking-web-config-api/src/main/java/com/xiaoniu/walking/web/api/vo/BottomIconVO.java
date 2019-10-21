package com.xiaoniu.walking.web.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 底部icon返回对象
 *
 * @author lihoujing
 * @date 2019/6/24 20:20
 */
@Data
public class BottomIconVO implements Serializable {


    private Integer id;

    /**
     * 之前的icon名称
     */
    private String preIconName;

    /**
     * icon名称
     */
    private String iconName;


    /**
     * app名称：1-来这花，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     */
    private Integer appName;

    /**
     * 1-有效，2-无效
     */
    private Integer state;

    /**
     * 点击前icon图片
     */
    private String preIconImg;

    /**
     * 点击后icon图片
     */
    private String aftIconImg;

    /**
     * 跳转链接
     */
    private String linkUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createMan;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private String modifyMan;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间str
     */
    private String createTimeStr;

    /**
     * app版本号
     */
    private String appVersion;

    /**
     * 市场渠道号
     */
    private Integer market;


    /**
     * icon位置
     */
    private Integer position;

    /**
     * 版本作用范围：1-大于，2-大于等于，3-等于，4-小于，5-小于等于，6-不等于
     */
    private Integer rangeVersion;


}
