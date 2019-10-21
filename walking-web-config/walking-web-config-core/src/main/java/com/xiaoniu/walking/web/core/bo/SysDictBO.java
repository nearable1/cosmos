package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据字典大类分页
 *
 * @author chenguohua
 * @date 2019/04/22 23:15
 */
@Data
public class SysDictBO extends DefaultPageParameter implements Serializable {
    private static final long serialVersionUID = -3296358742926917813L;

    /**
     * 类型
     */
    private String type;
    /**
     * 标签名
     */
    private String label;

}
