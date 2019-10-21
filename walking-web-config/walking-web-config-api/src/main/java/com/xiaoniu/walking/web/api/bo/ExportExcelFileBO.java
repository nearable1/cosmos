package com.xiaoniu.walking.web.api.bo;

import lombok.*;

import java.io.Serializable;

/**
 * @author luoyanchong
 * @ Description：文件导出文件属性
 * @date 2019-05-29 16:32
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExportExcelFileBO implements Serializable{
    private static final long serialVersionUID = -6400645946454996238L;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 表格sheet名称
     */
    private String sheetName;
}
