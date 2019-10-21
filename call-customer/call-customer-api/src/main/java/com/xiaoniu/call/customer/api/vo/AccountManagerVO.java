package com.xiaoniu.call.customer.api.vo;

import java.io.Serializable;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AccountManagerVO extends DefaultPageParameter implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer accountType;

    private String startTime;

    private String endTime;

    private String nickname;

}
