package com.xiaoniu.walking.web.api.bo;

import lombok.*;

import java.io.Serializable;

/**
 * @author luoyanchong
 * @ Description：当前行的一个单元格数据
 * @date 2019-05-29 16:17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportExcelColumnDataBO implements Serializable {
    private static final long serialVersionUID = 8571096727614526789L;

    /**
     * 列索引 从0开始
     */
    private Integer columnIndex;

    /**
     * 列数据
     */
    private Object columnData;

}
