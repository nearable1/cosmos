package com.xiaoniu.call.video.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class VideoSettingRates implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video_setting_rate.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video_setting_rate.video_number
     *
     * @mbggenerated
     */
    private String videoNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video_setting_rate.call_show_setting_rate
     *
     * @mbggenerated
     */
    private BigDecimal callShowSettingRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video_setting_rate.wallpaper_setting_rate
     *
     * @mbggenerated
     */
    private BigDecimal wallpaperSettingRate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column video_setting_rate.ringtone_setting_rate
     *
     * @mbggenerated
     */
    private BigDecimal ringtoneSettingRate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column video_setting_rate.id
     *
     * @return the value of video_setting_rate.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column video_setting_rate.id
     *
     * @param id the value for video_setting_rate.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column video_setting_rate.video_number
     *
     * @return the value of video_setting_rate.video_number
     *
     * @mbggenerated
     */
    public String getVideoNumber() {
        return videoNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column video_setting_rate.video_number
     *
     * @param videoNumber the value for video_setting_rate.video_number
     *
     * @mbggenerated
     */
    public void setVideoNumber(String videoNumber) {
        this.videoNumber = videoNumber == null ? null : videoNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column video_setting_rate.call_show_setting_rate
     *
     * @return the value of video_setting_rate.call_show_setting_rate
     *
     * @mbggenerated
     */
    public BigDecimal getCallShowSettingRate() {
        return callShowSettingRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column video_setting_rate.call_show_setting_rate
     *
     * @param callShowSettingRate the value for video_setting_rate.call_show_setting_rate
     *
     * @mbggenerated
     */
    public void setCallShowSettingRate(BigDecimal callShowSettingRate) {
        this.callShowSettingRate = callShowSettingRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column video_setting_rate.wallpaper_setting_rate
     *
     * @return the value of video_setting_rate.wallpaper_setting_rate
     *
     * @mbggenerated
     */
    public BigDecimal getWallpaperSettingRate() {
        return wallpaperSettingRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column video_setting_rate.wallpaper_setting_rate
     *
     * @param wallpaperSettingRate the value for video_setting_rate.wallpaper_setting_rate
     *
     * @mbggenerated
     */
    public void setWallpaperSettingRate(BigDecimal wallpaperSettingRate) {
        this.wallpaperSettingRate = wallpaperSettingRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column video_setting_rate.ringtone_setting_rate
     *
     * @return the value of video_setting_rate.ringtone_setting_rate
     *
     * @mbggenerated
     */
    public BigDecimal getRingtoneSettingRate() {
        return ringtoneSettingRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column video_setting_rate.ringtone_setting_rate
     *
     * @param ringtoneSettingRate the value for video_setting_rate.ringtone_setting_rate
     *
     * @mbggenerated
     */
    public void setRingtoneSettingRate(BigDecimal ringtoneSettingRate) {
        this.ringtoneSettingRate = ringtoneSettingRate;
    }
}