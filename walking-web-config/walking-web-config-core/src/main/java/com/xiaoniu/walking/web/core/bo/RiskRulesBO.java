package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RiskRulesBO extends DefaultPageParameter implements Serializable {


    /**
     * 规则名称
     */
    private String rulesName;
    /**
     * 审核状态
     */
    private Integer state;


}
