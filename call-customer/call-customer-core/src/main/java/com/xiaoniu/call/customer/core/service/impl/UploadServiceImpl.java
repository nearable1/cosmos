package com.xiaoniu.call.customer.core.service.impl;

import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.spring.boot.autoconfigure.util.AliYunFileUtils;
import com.xiaoniu.call.customer.core.service.UploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Log4j2
public class UploadServiceImpl implements UploadService {

    @Override
    public String upload(MultipartFile file, String diskName) {
        try {
            // 提取文件后缀名
            String fileName = file.getOriginalFilename();
            String extName = fileName.substring(fileName.indexOf("."));
            String imageName = UUID.randomUUID().toString() + extName;
            // 上传
            return AliYunFileUtils.uploadFile2OSS(file.getBytes(), diskName, imageName);
        } catch (Exception e) {
            log.error("文件上传失败，file:{}, error:{}", file, e);
            throw new BusinessException(ResultCodeEnum.SYSTEM_EXCEPTION.getCode(), "文件上传失败");
        }
    }
}
