package com.xiaoniu.call.customer.api.vo;

import java.io.Serializable;
import java.util.Date;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AsmConfigLogVO extends DefaultPageParameter implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String startTime;

    private String endTime;

    private String rom;

    private String api;

    private String model;

    private String manufacturer;

    private String remark;
}
