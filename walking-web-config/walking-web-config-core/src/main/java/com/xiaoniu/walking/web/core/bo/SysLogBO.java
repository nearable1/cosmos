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
public class SysLogBO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = -5242137160899414096L;

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
    private String actionType;

    /**
     * 查询时间数组
     */
    private String[] times;

    /**
     * 创建时间
     */
    private String sTime;

    /**
     * 结束时间
     */
    private String eTime;


}
