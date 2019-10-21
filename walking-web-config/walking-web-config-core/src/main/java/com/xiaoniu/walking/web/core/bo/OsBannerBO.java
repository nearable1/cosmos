package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 运营banner管理
 *
 * @author chenguohua
 * @date 2019年5月22日11:21:20
 */
@Data
public class OsBannerBO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = -1270248386720463617L;


    /**
     * 标题
     */
    private String title;

    /**
     * banner位置
     */
    private String bannerPosition;

    /**
     * 状态：1-开启，2-关闭
     */
    private Integer state;

    /**
     * 查询时间数组
     */
    private String[] times;

    /**
     * 创建时间
     */
    private String sTime;

    /**
     * 结束时间
     */
    private String eTime;

    /**
     * app名称
     */
    private Integer appName;



}