package com.xiaoniu.walking.web.core.service;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.walking.web.api.bo.FileUploadBO;
import com.xiaoniu.walking.web.api.vo.OssImgInfoVO;

/**
 * @author: xiangxianjin
 * @date: 2019/5/16 01:57
 * @description:
 */
public interface FileUploadService {

    /**
     * 单个上传
     * @param uploadBO
     * @return
     * @throws BusinessException
     */
    ResultBean upload(FileUploadBO uploadBO) throws BusinessException;

    /**
     * 支持批量上传
     * @param uploadBO
     * @return
     * @throws BusinessException
     */
    ResultBean uploadFiles(FileUploadBO uploadBO) throws BusinessException;


    /**
     * 从OSS上获取图片信息
     *
     * @param imgName 图片名称
     * @return
     */
    OssImgInfoVO getImgInfoFromOss(String imgName);


}
