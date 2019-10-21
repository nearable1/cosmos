package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据库管理分页
 *
 * @author chenguohua
 * @date 2019/04/22 22:36
 */
@Data
public class DatabaseBO extends DefaultPageParameter implements Serializable {
    private static final long serialVersionUID = 162686893927064997L;
    /**
     * 数据库名称
     */
    private String tableSchema;
    /**
     * 表名称
     */
    private String tableName;
}
