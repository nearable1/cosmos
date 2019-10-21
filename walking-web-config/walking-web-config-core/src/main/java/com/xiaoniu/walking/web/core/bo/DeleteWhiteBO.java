package com.xiaoniu.walking.web.core.bo;
import	java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * @author :LiuYinkai
 * @date :2019-09-19 21:19.
 */
public class DeleteWhiteBO implements Serializable{

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
