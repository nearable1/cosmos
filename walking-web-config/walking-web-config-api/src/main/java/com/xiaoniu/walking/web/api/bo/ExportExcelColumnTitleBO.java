package com.xiaoniu.walking.web.api.bo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author luoyanchong
 * @ Description：文件导出列属性
 * @date 2019-05-29 16:17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportExcelColumnTitleBO implements Serializable {
    private static final long serialVersionUID = 8571096727614526789L;

    /**
     * 列索引 从0开始
     */
    private Integer columnIndex;

    /**
     * 列标题
     */
    private String columnTitle;

    /**
     * 列宽度（可设可不设，自适应）
     */
    private Integer columnWidth;
}
