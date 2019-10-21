package com.xiaoniu.walking.web.api.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 敏感词管理
 *
 * @author lihoujing
 * @date 2019/7/11 16:16
 */
@Data
public class SensitiveWords implements Serializable {

    private static final long serialVersionUID = -364669374653747363L;

    /**
     * 敏感词唯一主键id
     */
    private String id;

    /**
     * 敏感词
     */
    @NotNull(message = "敏感词不能为空")
    private String sensitiveWords;


    /**
     * 敏感词类型：1-全部，2-评论敏感词
     */
    @NotNull(message = "敏感词类型不能为空")
    private Integer sensitiveType;
}
