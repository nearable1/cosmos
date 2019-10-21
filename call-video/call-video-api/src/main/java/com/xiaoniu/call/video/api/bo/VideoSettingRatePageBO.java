package com.xiaoniu.call.video.api.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 音频分页请求参数
 *
 * @author liuyinkai
 * @date 2019-08-02
 */
@Setter
@Getter
@ToString
public class VideoSettingRatePageBO extends DefaultPageParameter implements Serializable {

    /**
     * 排行类型 callShowSettingRate-来电秀 wallpaperSettingRate-壁纸 ringtoneSettingRate-铃声
     */
    private String rankType;

    /**
     * 排序类型 1-正序 2-倒序
     */
    private Integer orderType;

}
