package com.xiaoniu.call.video.api.bo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author :LiuYinkai
 * @date :2019-08-06 23:02.
 */
public class DeleteCategoryBO implements Serializable {

    /**
     * 主键
     */
    @NotNull(message = "主键ID不能为空")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
