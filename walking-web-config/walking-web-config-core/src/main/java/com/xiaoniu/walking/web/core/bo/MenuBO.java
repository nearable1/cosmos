package com.xiaoniu.walking.web.core.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lihoujing
 * @date: 2019/4/11 15:26
 * @description: 菜单权限
 */
@Getter
@Setter
@ToString
public class MenuBO {

    /**
     * 主键编号
     */
    private Integer permissionId;
    /**
     * 权限名称
     */
    private String names;
    /**
     * 菜单地址
     */
    private String url;
    /**
     * 图标
     */
    private String imgIcon;
    /**
     * 排序(升序)
     */
    private Short orders;

    /**
     * 父菜单编号
     */
    private Integer parentId;
    /**
     * 权限编码
     */
    private String authority;
    /**
     * 权限类型：1-菜单，2-按钮
     */
    private String permissionType;
    /**
     *  状态：1-有效，2-失效
     */
    private String permissionState;

    /**
     * 是否选中,1-选中，2-不选中
     */
    private Integer checked;
    /**
     * 子菜单
     */
    private List<MenuBO> children = new ArrayList<>();


}
