package com.xiaoniu.call.customer.core.controller;

import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.call.customer.core.cons.CustomerCons;
import com.xiaoniu.call.customer.core.service.UploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@Log4j2
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping(value = "/upload")
    public String upload(HttpServletRequest request) {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiRequest.getFile("file");
        String fileType = request.getParameter("fileType");
        log.info("上传文件 fileType = " + fileType + ", fileNames = " + file.getOriginalFilename() + ",size="
                + file.getSize());

        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM.getCode(), "文件为空");
        }
        // 文件大小限制15M
        if (file.getSize() > 1024 * 1024 * 15) {
            throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM.getCode(), "上传的文件过大");
        }
        if (!CustomerCons.OSS_PATH_MAPPING.containsKey(fileType)) {
            throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM.getCode(), "上传文件类型错误");
        }
        // 文件上传到服务器上的路径
        return uploadService.upload(file, CustomerCons.OSS_PATH_MAPPING.get(fileType));
    }
}
