package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
public class AppVersionPatchBO extends DefaultPageParameter implements Serializable {

    /**
     * APP版本主键
     */
    private Integer appVersionId;
}