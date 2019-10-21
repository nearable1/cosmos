package com.xiaoniu.walking.web.core.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.core.util.DateUtils;
import com.xiaoniu.walking.web.api.bo.ExportExcelColumnDataBO;
import com.xiaoniu.walking.web.api.bo.ExportExcelColumnTitleBO;
import com.xiaoniu.walking.web.api.bo.ExportExcelFileBO;
import com.xiaoniu.walking.web.core.constants.CommonConstants;
import com.xiaoniu.walking.web.core.enums.FileContentTypeEnum;
import com.xiaoniu.walking.web.core.enums.UploadFileEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author luoyanchong
 * @ Description：文件操作工具类
 * @date 2019-04-28 15:42
 */
@Log4j2
@Component
public class FileOperationUtils {

    private static String endPoint;

    private static String accessKeyId;

    private static String accessKeySecret;

    private static String bucketName;

    private static String ossUrl;

    private static String videoAudiobucketName;
    /**
     * 最来电阿里云配置
     */
    private static String accessKeyId_zld;

    private static String accessKeySecret_zld;

    private static String bucketName_zld;

    private static String ossUrl_zld;
    /**
     * 文件上传大小限制（单位：M）
     */
    private static Long fileSize;

    /**
     * 导出excel文件
     *
     * @param response HttpServletResponse
     * @param exportExcelFileBO excel文件属性
     * @param exportExcelColumnTitleBOList excel每一列的标题
     * @param exportExcelColumnDataBOList excel行数据
     */
    public static void exportExcel(HttpServletResponse response, ExportExcelFileBO exportExcelFileBO,
                                   List<ExportExcelColumnTitleBO> exportExcelColumnTitleBOList,
                                   List<List<ExportExcelColumnDataBO>> exportExcelColumnDataBOList) {
        HSSFWorkbook wb = null;
        OutputStream out = null;
        try {
            // 参数简单校验
            if (Objects.isNull(response) || Objects.isNull(exportExcelFileBO) || CollectionUtils.isEmpty(exportExcelColumnTitleBOList)  || CollectionUtils.isEmpty(exportExcelColumnDataBOList) ) {
                log.warn("文件上传缺失必要参数！");
                return;
            }

            // 基本属性设置
            wb = new HSSFWorkbook();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/OCTET-STREAM");
            String fileName = exportExcelFileBO.getFileName() + ".xlsx";
            response.addHeader("Content-Disposition","attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
            HSSFSheet sheet = wb.createSheet(exportExcelFileBO.getSheetName());

            // 加特技
            sheet.setDefaultRowHeightInPoints(12);
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            HSSFFont font = wb.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 12);
            cellStyle.setFont(font);

            // 设置列标题
            HSSFRow row = sheet.createRow(0);
            for (ExportExcelColumnTitleBO columnBO : exportExcelColumnTitleBOList) {
                HSSFCell cell = row.createCell(columnBO.getColumnIndex());
                cell.setCellValue(columnBO.getColumnTitle());
                cell.setCellStyle(cellStyle);
            }

            // 设置行数据
            for (int i = 0; i < exportExcelColumnDataBOList.size(); i++) {
                row = sheet.createRow(i + 1);
                for (ExportExcelColumnDataBO exportExcelColumnDataBO : exportExcelColumnDataBOList.get(i)) {
                    Object data = exportExcelColumnDataBO.getColumnData();
                    if (data instanceof Integer) {
                        row.createCell(exportExcelColumnDataBO.getColumnIndex())
                                .setCellValue((Integer) data);
                    } else if (data instanceof Date) {
                        row.createCell(exportExcelColumnDataBO.getColumnIndex())
                                .setCellValue(DateUtils.format((Date) data, DateUtils.DATE_TIME_PATTERN));
                    } else if (data instanceof Calendar) {
                        Calendar calendar = (Calendar) data;
                        Date date = calendar.getTime();
                        row.createCell(exportExcelColumnDataBO.getColumnIndex())
                                .setCellValue(DateUtils.format(date, DateUtils.DATE_TIME_PATTERN));
                    } else if (data instanceof Double) {
                        row.createCell(exportExcelColumnDataBO.getColumnIndex())
                                .setCellValue((Double) data);
                    } else if (data instanceof Boolean) {
                        row.createCell(exportExcelColumnDataBO.getColumnIndex())
                                .setCellValue((Boolean) data);
                    } else {
                        row.createCell(exportExcelColumnDataBO.getColumnIndex())
                                .setCellValue((String) data);
                    }
                }
            }

            // 设置宽度（可自定义可自适应）
            exportExcelColumnTitleBOList.parallelStream().forEach(column ->{
                if (Objects.nonNull(column.getColumnWidth()) && column.getColumnWidth() > 0) {
                    sheet.setColumnWidth(column.getColumnIndex(), column.getColumnWidth());
                } else {
                    sheet.autoSizeColumn(column.getColumnIndex());
                }
            });

            out = response.getOutputStream();
            wb.write(out);
        } catch (Exception e) {
            log.error("[Excel文件导出]异常！异常信息：{}", e.getMessage(), e);
        } finally {
            try {
                if(wb != null){
                    wb.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("[Excel文件导出]关闭流异常！异常信息：{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 向阿里云的OSS存储中存储文件 --file被InputStream替代
     *
     * @param inputStream  上传文件流
     * @param diskName 上传文件的目录
     * @param fileName 文件名称
     * @param fileName 文件名称
     * @return ossBucketType 阿里云篮子
     */
    public static String uploadInputStreamObject2OSS(InputStream inputStream, String diskName, String fileName, Integer ossBucketType) throws Exception {
        StringBuilder fileUrl = new StringBuilder();
        try {
            OSSClient client = new OSSClient(endPoint, accessKeyId, accessKeySecret);
            if (ossBucketType != null && ossBucketType.equals(CommonConstants.VIDEO_AUDIO_BUCKET_TYPE)) {
                client = new OSSClient(endPoint, accessKeyId_zld, accessKeySecret_zld);
            }
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(inputStream.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("UTF-8");
            metadata.setContentType(getContentType(fileName));
            // 上传文件
            // 上传文件
            if (ossBucketType != null && ossBucketType.equals(CommonConstants.VIDEO_AUDIO_BUCKET_TYPE)) {
                client.putObject(videoAudiobucketName, diskName + "/" + fileName, inputStream, metadata);
             }else {
                client.putObject(bucketName, diskName + "/" + fileName, inputStream, metadata);
            }
        } catch (Exception e) {
            log.error("上传文件到OSS失败", e);
        } finally {
            if (Objects.nonNull(inputStream)) {
                log.info("关闭文件的输入流！");
                try {
                    inputStream.close();
                } catch (Exception e) {
                    log.error("关闭文件的输入流异常", e);
                }
            }
        }
        return fileUrl.append(ossUrl).append("/").append(diskName).append("/").append(fileName).toString();
    }

    /**
     * 向阿里云的OSS存储中存储文件 --file被InputStream替代
     *
     * @param file     上传的文件
     * @param diskName 上传文件的目录
     * @param fileName 文件名称
     * @return String 图片URL
     */
    public static String uploadInputStreamObject2OSS(MultipartFile file, String diskName, String fileName, Integer ossBucketType) throws Exception {
        Assert.notNull(file, "上传的文件不能为空");
        return uploadInputStreamObject2OSS(file.getInputStream(), diskName, fileName, ossBucketType);
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    private static String getContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(CommonConstants.FILE_SPLIT_SYMBOL) + 1);
        return Objects.nonNull(FileContentTypeEnum.resolve(fileExtension)) ? FileContentTypeEnum.resolve(fileExtension).getDesc() : FileContentTypeEnum.FILE__HTML.getDesc();
    }

    /**
     * 文件校验
     *
     * @param file 文件
     * @return 校验结果
     */
    public static ResultBean fileCheck(MultipartFile file) {
        // 非空判断
        if (Objects.isNull(file) || Objects.isNull(file.getOriginalFilename())) {
            return ResultBean.format(UploadFileEnum.UPLOAD_NOT_EMPTY.getValue(), UploadFileEnum.UPLOAD_NOT_EMPTY.getDesc());
        }
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(CommonConstants.FILE_SPLIT_SYMBOL) + 1);
        // 文件类型校验
        if (Objects.isNull(FileContentTypeEnum.resolve(fileType))) {
            return ResultBean.format(UploadFileEnum.UPLOAD_TYPE_ERROR.getValue(), UploadFileEnum.UPLOAD_TYPE_ERROR.getDesc());
        }
        // 文件大小校验
        if (file.getSize() / CommonConstants.FILE_LEVEL / CommonConstants.FILE_LEVEL > fileSize) {
            return ResultBean.format(UploadFileEnum.UPLOAD_SIZE_ERROR.getValue(), UploadFileEnum.UPLOAD_SIZE_ERROR.getDesc());
        }
        return null;
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param logoText 要写入的文字
     * @param inputStream 源图片输入流
     * @param newImagePath 新图片路径
     * @param degree 旋转角度
     * @param color  字体颜色
     * @param formatName 图片后缀
     */
    public static void markImageByText(String logoText, InputStream inputStream, String newImagePath, Integer degree, Color color, String formatName) {
        OutputStream os = null;
        try {
            // 1、源图片
            java.awt.Image srcImg = ImageIO.read(inputStream);
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), java.awt.Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree),  buffImg.getWidth()/2,buffImg.getHeight() /2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(new java.awt.Font("宋体", java.awt.Font.BOLD, buffImg.getHeight() /4));
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.15f));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(logoText,  buffImg.getWidth()/3 , buffImg.getHeight()/2);
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(newImagePath);
            ImageIO.write(buffImg, formatName, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os){
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Value("${file.upload.oss.endPoint}")
    public void setEndPoint(String endPoint) {
        FileOperationUtils.endPoint = endPoint;
    }

    @Value("${file.upload.oss.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        FileOperationUtils.accessKeyId = accessKeyId;
    }

    @Value("${file.upload.oss.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        FileOperationUtils.accessKeySecret = accessKeySecret;
    }

    @Value("${file.upload.oss.bucketName}")
    public void setBucketName(String bucketName) {
        FileOperationUtils.bucketName = bucketName;
    }

    @Value("${file.upload.oss.ossUrl}")
    public void setOssUrl(String ossUrl) {
        FileOperationUtils.ossUrl = ossUrl;
    }

    @Value("${file.upload.file.size}")
    public void setFileSize(Long fileSize) {
        FileOperationUtils.fileSize = fileSize;
    }

    @Value("${xiaoniu.aliyun.video-audio-bucket-name}")
    public void setvideoAudioBucketName(String videoAudiobucketName) {
        FileOperationUtils.videoAudiobucketName = videoAudiobucketName;
    }

    /**
     * 最来电配置
     * @param accessKeyId
     */
    @Value("${xiaoniu.aliyun.access-key-id}")
    public void setAccessKeyId_zld(String accessKeyId) {
        FileOperationUtils.accessKeyId_zld = accessKeyId;
    }

    @Value("${xiaoniu.aliyun.access-key-secret}")
    public void setAccessKeySecret_zld(String accessKeySecret) {
        FileOperationUtils.accessKeySecret_zld = accessKeySecret;
    }

    @Value("${xiaoniu.aliyun.bucket-name}")
    public void setBucketName_zld(String bucketName) {
        FileOperationUtils.bucketName_zld = bucketName;
    }

    @Value("${xiaoniu.aliyun.domain-name}")
    public void setOssUrl_zld(String ossUrl) {
        FileOperationUtils.ossUrl_zld = ossUrl;
    }
}
