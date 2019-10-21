package com.xiaoniu.walking.web.core.model.auto;

import lombok.Data;

import java.io.Serializable;

/**
 * 热搜关键词
 *
 * @author lihoujing
 * @date 2019/7/8 15:42
 */
@Data
public class HotSearchKeyword implements Serializable {

    private static final long serialVersionUID = -4036667329007686829L;

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 热搜关键词
     */
    private String keyWord;

    /**
     * 排序值
     */
    private Integer orders;

    /**
     * 状态：1-有效，2-无效
     */
    private Integer state;

    /**
     * app名称：1-来这记，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     */
    private Integer appName;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 创建人
     */
    private String createMan;

    /**
     * 更新时间
     */
    private Long modifyTime;

    /**
     * 更新人
     */
    private String modifyMan;

    /**
     * 备注
     */
    private String remark;
}
