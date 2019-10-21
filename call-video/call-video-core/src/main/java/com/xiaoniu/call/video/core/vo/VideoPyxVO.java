package com.xiaoniu.call.video.core.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 视频实体
 *
 * @author wuwen
 * @date 2019-07-02 17:42
 */
@Getter
@Setter
@ToString
public class VideoPyxVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码最小值为0")
    private Integer pageIndex;

    @NotNull(message = "分页条数不能为空")
    @Min(value = 0, message = "分页条数最小值为0")
    private Integer pageSize;

    /**
     * 类别编号
     */
    private String videoType;

}
