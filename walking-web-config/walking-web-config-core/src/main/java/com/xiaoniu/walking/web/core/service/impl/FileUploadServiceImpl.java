package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.web.http.HttpUtils;
import com.xiaoniu.walking.web.api.bo.FileUploadBO;
import com.xiaoniu.walking.web.api.vo.OssImgInfoVO;
import com.xiaoniu.walking.web.core.configuration.VideoCDNProperties;
import com.xiaoniu.walking.web.core.constants.CommonConstants;
import com.xiaoniu.walking.web.core.enums.ImageTypeEnum;
import com.xiaoniu.walking.web.core.enums.UploadFileEnum;
import com.xiaoniu.walking.web.core.service.FileUploadService;
import com.xiaoniu.walking.web.core.utils.FileOperationUtils;
import com.xiaoniu.walking.web.core.utils.ImageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author: xiangxianjin
 * @date: 2019/5/16 01:57
 * @description:
 */
@Service
@Log4j2
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private VideoCDNProperties videoCDNProperties;

    /**
     * 水印图片
     */
    private static final String WATER_MARK = "/watermark.png";

    /**
     * OSS请求图片宽高路径
     */
    private static final String BASE_URL = "?x-oss-process=image/info";

    /**
     * 图片高
     */
    private static final String IMAGE_HEIGHT = "ImageHeight";

    /**
     * 图片宽
     */
    private static final String IMAGE_WIDTH = "ImageWidth";
    /**
     * 图片的key
     */
    private static final String VALUE = "value";

    /**
     * 文件路径
     */
    @Value("${water.logo.filepath}")
    private String[] filePath;

    /**
     * 带有水印图片路径
     */
    @Value("${new.image.path}")
    private  String newImagePath;

    /**
     *  水印之间的水平间距
     */
    @Value("${margin.x}")
    private Integer marginX;

    /**
     * 水印之间的垂直间距
     */
    @Value("${margin.y}")
    private Integer marginY;

    @Override
    public ResultBean upload(FileUploadBO uploadBO) throws BusinessException {
        MultipartFile[] files = uploadBO.getFile();
        if (Objects.isNull(files)) {
            return ResultBean.format(UploadFileEnum.UPLOAD_NOT_EMPTY.getValue(), UploadFileEnum.UPLOAD_NOT_EMPTY.getDesc());
        }
        String urls = "";
        MultipartFile file = files[0];
        FileOutputStream targetImgOutputStream = null;
        InputStream inputStream = null;
        // 文件上传地址
        try {
            // 文件上传校验
            ResultBean checkResult = FileOperationUtils.fileCheck(file);
            if (Objects.nonNull(checkResult)) {
                // 校验不通过
                return checkResult;
            }
            // 文件类型
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(CommonConstants.FILE_SPLIT_SYMBOL) + 1);
            // 设置文件名
            StringBuilder fileName = new StringBuilder();
            fileName.append(System.currentTimeMillis())
                    .append(CommonConstants.FILE_SPLIT_SYMBOL)
                    .append(fileType);
            inputStream = file.getInputStream();
            //判断路径是否存在,不存在就创建
            File existFile = new File(newImagePath);
            if(!existFile.isDirectory()){
                existFile.mkdirs();
            }
            //是图片并且符合指定路径就添加图片水印
            String newImage = newImagePath + File.separator + fileName;
            targetImgOutputStream = new FileOutputStream(newImage);
            if(Objects.nonNull(ImageTypeEnum.resolve(fileType)) && Arrays.asList(filePath).contains(uploadBO.getFilePath())){
                InputStream markImgInputStream = FileUploadServiceImpl.class.getResourceAsStream(WATER_MARK);
                ImageUtils.markImage(inputStream, targetImgOutputStream, markImgInputStream, marginX, marginY);
                inputStream = new FileInputStream(newImage);
            }
            urls = FileOperationUtils.uploadInputStreamObject2OSS(inputStream , uploadBO.getFilePath(), fileName.toString(), uploadBO.getOssBucketType());
            if (uploadBO.getOssBucketType() != null && uploadBO.getOssBucketType().equals(CommonConstants.VIDEO_AUDIO_BUCKET_TYPE) && StringUtils.isNotBlank(urls)) {
                String cdn = videoCDNProperties.getCdn().get(0);
                String trimCdn = cdn.replace("\"", "").trim();
                urls = urls.substring(urls.indexOf("zuilaidian"), urls.length());
                urls = new StringBuilder().append(trimCdn).append(urls).toString();
            }




        } catch (Exception e) {
            // mytodo throw or return ?
            log.error("文件上传异常！异常信息：{}", e.getMessage(), e);
            return ResultBean.format(UploadFileEnum.UPLOAD_EXCEPTION.getValue(), UploadFileEnum.UPLOAD_EXCEPTION.getDesc());
        }finally {
            if(targetImgOutputStream != null){
                try {
                    targetImgOutputStream.close();
                } catch (IOException e) {
                    log.info("关闭流异常, 异常信息：{}", e.getMessage());
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.info("关闭流异常,异常信息：{}", e.getMessage());
                }
            }
        }
        return ResultBean.format(urls);
    }



    /**
     * 支持批量上传
     *
     * @param uploadBO
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultBean uploadFiles(FileUploadBO uploadBO) throws BusinessException {
        if (Objects.isNull(uploadBO.getFile())) {
            return ResultBean.format(UploadFileEnum.UPLOAD_NOT_EMPTY.getValue(), UploadFileEnum.UPLOAD_NOT_EMPTY.getDesc());
        }
        String[] urls = new String[uploadBO.getFile().length];
        int i = 0;
        for(MultipartFile file : uploadBO.getFile()){
            FileOutputStream targetImgOutputStream = null;
            InputStream inputStream = null;
            // 文件上传地址
            try {
                // 文件上传校验
                ResultBean checkResult = FileOperationUtils.fileCheck(file);
                if (Objects.nonNull(checkResult)) {
                    // 校验不通过
                    return checkResult;
                }
                // 文件类型
                String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(CommonConstants.FILE_SPLIT_SYMBOL) + 1);
                // 设置文件名
                StringBuilder fileName = new StringBuilder();
                fileName.append(System.currentTimeMillis())
                        .append(CommonConstants.FILE_SPLIT_SYMBOL)
                        .append(fileType);
                inputStream = file.getInputStream();
                //判断路径是否存在,不存在就创建
                File existFile = new File(newImagePath);
                if(!existFile.isDirectory()){
                    existFile.mkdirs();
                }
                //是图片并且符合指定路径就添加图片水印
                String newImage = newImagePath + File.separator + fileName;
                targetImgOutputStream = new FileOutputStream(newImage);
                if(Objects.nonNull(ImageTypeEnum.resolve(fileType)) && Arrays.asList(filePath).contains(uploadBO.getFilePath())){
                    InputStream markImgInputStream = FileUploadServiceImpl.class.getResourceAsStream(WATER_MARK);
                    ImageUtils.markImage(inputStream, targetImgOutputStream, markImgInputStream, marginX, marginY);
                    inputStream = new FileInputStream(newImage);
                }
                String url = FileOperationUtils.uploadInputStreamObject2OSS(inputStream, uploadBO.getFilePath(), fileName.toString(),0);
                urls[i] = url;
            } catch (Exception e) {
                // mytodo throw or return ?
                log.error("文件上传异常！异常信息：{}", e.getMessage(), e);
                return ResultBean.format(UploadFileEnum.UPLOAD_EXCEPTION.getValue(), UploadFileEnum.UPLOAD_EXCEPTION.getDesc());
            }finally {
                if(targetImgOutputStream != null){
                    try {
                        targetImgOutputStream.close();
                    } catch (IOException e) {
                        log.info("关闭流异常,异常信息：{}", e.getMessage());
                    }
                }
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        log.info("关闭流异常,异常信息：{}", e.getMessage());
                    }
                }
            }
            i++;
        }
        return ResultBean.format(urls);
    }


    /**
     * 从OSS上获取图片信息
     *
     * @param imgName 图片名称
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public OssImgInfoVO getImgInfoFromOss(String imgName) {
        String width = null;
        String height = null;
        Map<String, Map<String, String>> map = HttpUtils.doGet(imgName + BASE_URL, Map.class);
        //获取图片宽高的map
        Map<String, String> imageWidthMap = map.get(IMAGE_WIDTH);
        Map<String, String> imageHeightMap = map.get(IMAGE_HEIGHT);
        if(Objects.nonNull(imageWidthMap)){
             width = imageWidthMap.get(VALUE);
        }
        if(Objects.nonNull(imageHeightMap)){
             height = imageHeightMap.get(VALUE);
        }
        OssImgInfoVO ossImgInfoVO = new OssImgInfoVO();
        ossImgInfoVO.setImageWidth(width);
        ossImgInfoVO.setImageHeight(height);
        return ossImgInfoVO;
    }

}
