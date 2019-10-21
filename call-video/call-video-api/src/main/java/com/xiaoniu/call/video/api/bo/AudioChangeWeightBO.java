package com.xiaoniu.call.video.api.bo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 音频修改权重入参
 *
 * @author :Anchor Liu
 * @date :2019-08-08 18:18.
 */
public class AudioChangeWeightBO implements Serializable {
    /**
     * 权重
     */
    @NotNull(message = "权重不能为空")
    @Min(value = 0, message = "权重最小值为0")
    private Integer weight ;

    /**
     * 音频编号
     */
    @NotNull(message = "音频编号不能为空")
    private String audioNumber;

    /**
     * 音频分类编号
     */
    @NotNull(message = "音频分类编号不能为空")
    private String categoryNumber;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getAudioNumber() {
        return audioNumber;
    }

    public void setAudioNumber(String audioNumber) {
        this.audioNumber = audioNumber;
    }

    public String getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber;
    }
}
