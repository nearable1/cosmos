package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import java.io.Serializable;

/**
 * 敏感词入参
 *
 * @author lihoujing
 * @date 2019/7/11 16:49
 */
@Data
public class SensitiveWordsBO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = 8887471531664231818L;


    /**
     * 敏感词
     */
    private String sensitiveWords;


    /**
     * 敏感词类型：1-全部，2-评论敏感词
     */
    private Integer sensitiveType;
}
