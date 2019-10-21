package com.xiaoniu.call.customer.core.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AsmConfigDTO {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * rom
     */
    private String rom;

    /**
     * api
     */
    private String api;

    /**
     * model
     */
    private String model;

    /**
     * model
     */
    private String manufacturer;

    private Long asmDataId;

    private String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getAsmDataId() {
        return asmDataId;
    }

    public void setAsmDataId(Long asmDataId) {
        this.asmDataId = asmDataId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}