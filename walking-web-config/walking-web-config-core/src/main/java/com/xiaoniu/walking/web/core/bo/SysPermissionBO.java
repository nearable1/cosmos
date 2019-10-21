package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SysPermissionBO extends DefaultPageParameter implements Serializable {

    /**
     * 主键编号
     */
    private Integer permissionId;

    /**
     * 权限名称
     */
    private String names;
    /**
     * 父菜单编号
     */
    private Integer parentId;


}
