package com.xiaoniu.call.video.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 查询音频分类入参
 *
 * @author liuyinkai
 * @date 2019-07-25
 */
@Getter
@Setter
@ToString
public class AudioClassificationQueryBO implements Serializable {

    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码最小值为0")
    private Integer pageIndex;

    @NotNull(message = "分页条数不能为空")
    @Min(value = 0, message = "分页条数最小值为0")
    private Integer pageSize;

    /**
     * 设备号
     */
    private String deviceId;
}
