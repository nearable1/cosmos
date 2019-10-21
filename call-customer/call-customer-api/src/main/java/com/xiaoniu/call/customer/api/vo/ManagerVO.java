package com.xiaoniu.call.customer.api.vo;

import java.io.Serializable;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ManagerVO extends DefaultPageParameter implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nickname;

    private Long id;

    private Boolean status;

}
