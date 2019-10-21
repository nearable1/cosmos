package com.xiaoniu.walking.web.api.bo;

import lombok.*;

import java.io.Serializable;

/**
 * @author luoyanchong
 * @ Description：文件上传
 * @date 2019-04-27 15:51
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OsBannerBO implements Serializable {


    private static final long serialVersionUID = 8393123375052205015L;
    /**
     *  banner类型
     */
    private String bannerPosition;


}
