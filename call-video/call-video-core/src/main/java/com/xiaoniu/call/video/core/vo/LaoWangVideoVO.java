package com.xiaoniu.call.video.core.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 老王视频列表接口请求
 *
 * @author wuwen
 * @date 2019-07-06 14:22
 */
@Getter
@Setter
@ToString
public class LaoWangVideoVO implements Serializable {

    private static final long serialVersionUID = -4378614023006318354L;

    @NotNull(message = "分页条数不能为空")
    @Min(value = 1, message = "分页条数最小值为1")
    private Integer pageSize;

    @NotBlank(message = "视频类型不能为空")
    private String typeNumber;

    /**
     * 当前页最后一条视频的ID，初次进来的时候传空
     */
    private String lastVideoId;
}
