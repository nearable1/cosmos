package com.xiaoniu.walking.web.api.bo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
public class FileUploadBO implements Serializable {

    private static final long serialVersionUID = 781248237810519257L;

    /**
     * 上传文件
     */
    private MultipartFile[] file;

    /**
     * 上传路径
     */
    private String filePath;

    /**
     * OSS存储地址
     */
    private Integer ossBucketType;

}
