package com.xiaoniu.call.customer.api.vo;

import java.io.Serializable;
import java.util.Date;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AsmConfigDataVO extends DefaultPageParameter implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String name;

    private String data;

    private String remark;

    private String startTime;

    private String endTime;
}
