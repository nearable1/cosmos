package com.xiaoniu.call.customer.core.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

/**
 * 字典请求对象
 *
 * @author Sunju
 * @date 2019/1/13
 */
@Data
@ToString
public class AsmVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * MODEL 设备机型
     */
    @NotNull(message = "model不能为空")
    private String model;

    /**
     * api
     */
    @NotNull(message = "api不能为空")
    private String api;

    /**
     * rom
     */
    private String rom;

    /**
     * 厂商
     */
    @NotNull(message = "manufacturer不能为空")
    private String manufacturer;
}
