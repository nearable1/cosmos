package com.xiaoniu.call.video.api.dto;

import java.io.Serializable;

/**
 * @author :LiuYinkai
 * @date :2019-07-24 14:01.
 */
public class BatchOperateIdsDto implements Serializable {

    /**
     * 批量操作数组
     */
    private  String[] ids ;


    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
