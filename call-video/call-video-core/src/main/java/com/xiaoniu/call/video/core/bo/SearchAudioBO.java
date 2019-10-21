package com.xiaoniu.call.video.core.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 搜索音频入参
 *
 * @author liuyinkai
 * @date 2019-07-26
 */
@Getter
@Setter
@ToString
public class SearchAudioBO implements Serializable {

    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码最小值为0")
    private Integer pageIndex;

    @NotNull(message = "分页条数不能为空")
    @Min(value = 0, message = "分页条数最小值为0")
    private Integer pageSize;

    /**
     * 搜索关键词
     */
    @NotNull(message = "搜索关键词不能为空")
    private String keyWord;
}
