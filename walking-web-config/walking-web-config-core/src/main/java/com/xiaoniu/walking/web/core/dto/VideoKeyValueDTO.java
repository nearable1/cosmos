package com.xiaoniu.walking.web.core.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class VideoKeyValueDTO {
    private List<KeyValueDTO> tags;

    private List<KeyValueDTO> videoSource;

    private List<KeyValueDTO> category;
}
