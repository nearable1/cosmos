package com.xiaoniu.walking.web.core.model.ext;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统操作日志
 *
 * @author chenguohua
 * @date 2019/04/23 13:34
 */
@Data
public class SysLogExt extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = -2143163620289002301L;
    /**
     * 主键编号
     */
    private Long id;

    /**
     * 系统登录操作账号
     */
    private String userId;

    /**
     * 操作模块
     */
    private String actionModule;

    /**
     * 操作类型
     */
    private String actionUrl;

    /**
     * 操作参数
     */
    private String actionParam;

    /**
     * 操作类型
     */
    private String actionType;

    /**
     * 操作人ip地址
     */
    private String ipAddress;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 创建时间str
     */
    private String createDateStr;

}