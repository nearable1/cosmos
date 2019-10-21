package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import java.io.Serializable;

/**
 * 运营icon管理
 * @author lihoujing
 */
@Data
public class BottomIconPageBO extends DefaultPageParameter implements Serializable {

    private static final long serialVersionUID = -1270248386720463617L;


    /**
     * icon名称
     */
    private String iconName;

    /**
     * app名称：1-来这花，2-贷款管家，3-爱米多，4-抢单大师，5-医美
     */
    private Integer appName;

    /**
     * 1-有效，2-无效
     */
    private Integer state;


}