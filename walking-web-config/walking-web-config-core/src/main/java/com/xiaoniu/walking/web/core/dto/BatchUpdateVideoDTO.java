package com.xiaoniu.walking.web.core.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BatchUpdateVideoDTO {
    private String[] ids;

    private String categoryNumber;

    private List<String> tags;

}
