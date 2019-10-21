package com.xiaoniu.walking.web.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * OSS图片宽高返回对象
 *
 * @author lihoujing
 * @date 2019/8/1 12:59
 */
@Data
public class OssImgInfoVO implements Serializable {
    private static final long serialVersionUID = 2245408161471072916L;

    /**
     * 图片宽
     */
    private String imageWidth;


    /**
     * 图片高度
     */
    private String imageHeight;


}
