package com.xiaoniu.call.customer.core.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String upload(MultipartFile file, String diskName);
}
