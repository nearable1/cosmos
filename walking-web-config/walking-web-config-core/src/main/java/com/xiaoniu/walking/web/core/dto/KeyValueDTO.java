package com.xiaoniu.walking.web.core.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KeyValueDTO {
    private String key;

    private String value;

    public KeyValueDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
