package com.xiaoniu.walking.web.core.vo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 视频分页请求参数
 *
 * @author wuwen
 * @date 2019-07-15 16:40
 */
@Setter
@Getter
@ToString
public class VideoPageVO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = -3024591026234637818L;

//    @NotNull(message = "开始时间不能为空")
    private Date startTime;

//    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    /**
     * 视频编号
     */
    private String videoNumber;

    /**
     * 标题
     */
    private String title;

    /**
     * 视频类型
     */
    private Integer videoType;

    /**
     * 视频来源
     */
    private Integer videoSource;

    /**
     * 分类编号
     */
    private String categoryNumber;

    /**
     * 标签编号
     */
    private String tag;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 视频库标签编号
     */
    private String videoDbTags;

    /**
     * 视频类型标签编号
     */
    private String videoTypeTags;

    /**
     * 运营推荐标签
     */
    private String videoDbOperTags;
}
