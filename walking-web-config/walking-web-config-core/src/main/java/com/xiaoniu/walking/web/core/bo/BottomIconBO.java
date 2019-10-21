package com.xiaoniu.walking.web.core.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 底部icon参数实体
 *
 * @author lihoujing
 * @date 2019/6/25 15:52
 */
@Data
public class BottomIconBO implements Serializable {

    private static final long serialVersionUID = -127024838672046317L;


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
     * icon位置
     */
    private Integer iconPosition;

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
     * 展示渠道
     */
    private String clearChannel;

    /**
     * 屏蔽渠道
     */
    private String blockChannel;

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


}
