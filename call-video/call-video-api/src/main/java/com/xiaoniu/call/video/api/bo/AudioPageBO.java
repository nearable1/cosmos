package com.xiaoniu.call.video.api.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 音频分页请求参数
 *
 * @author liuyinkai
 * @date 2019-08-02
 */
@Setter
@Getter
@ToString
public class AudioPageBO extends DefaultPageParameter implements Serializable {


    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    /**
     * 视频编号
     */
    private String audioNumber;

    /**
     * 标题
     */
    private String title;

    /**
     * 视频类型
     */
    private Integer audioType;

    /**
     * 视频来源
     */
    private Integer audioSource;

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
     * 0-审核未通过  1-审核通过
     */
    private Integer check;

}
