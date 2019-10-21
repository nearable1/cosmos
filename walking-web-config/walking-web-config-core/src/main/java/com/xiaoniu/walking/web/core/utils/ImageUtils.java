package com.xiaoniu.walking.web.core.utils;

import org.apache.shiro.util.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author lihoujing
 * @date 2019/7/17 11:45
 */
public class ImageUtils {

    /**
     * 透明度
     */
    private static final float alpha = 1L;

    /**
     * 格式
     */
    private static final String formatName = "png";


    /**
     * 给图片添加图片水印 平铺方式
     *
     * @param originImgInputStream 原始图片的输入流
     * @param targetImgOutputStream 添加水印后图片的输出流
     * @param markImgInputStream 水印输入流
     * @param marginX 水印之间的水平间距
     * @param marginY 水印之间的垂直间距
     * @throws IOException
     */
    public static void markImage(InputStream originImgInputStream, OutputStream targetImgOutputStream, InputStream markImgInputStream, Integer marginX, Integer marginY) throws IOException {
        Assert.notNull(originImgInputStream, "原始图片的输入流不能为空");
        Assert.notNull(targetImgOutputStream, "添加水印后图片的输出流不能为空");
        Assert.notNull(markImgInputStream, "水印输入流不能为空");
        BufferedImage originImg= ImageIO.read(originImgInputStream);
        BufferedImage markImage = ImageIO.read(markImgInputStream);
        Graphics2D graphics = (Graphics2D) originImg.getGraphics();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        int canvasHeight = originImg.getHeight();
        int canvasWidth = originImg.getWidth();
        int markHeight = markImage.getHeight();
        int markWidth = markImage.getHeight();
        int interval = markWidth+markHeight;
        for(int i=-canvasHeight;i<canvasWidth+canvasHeight;i=i+interval+marginX){
            for(int j=-canvasWidth;j<canvasHeight+canvasWidth;j=j+interval+marginY){
                graphics.drawImage(markImage,i,j,markImage.getWidth(),markImage.getHeight(),null);
            }
        }
        graphics.dispose();
        ImageIO.write(originImg, formatName, targetImgOutputStream);
    }


}
