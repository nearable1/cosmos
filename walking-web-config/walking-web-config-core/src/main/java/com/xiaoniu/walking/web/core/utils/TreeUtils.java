package com.xiaoniu.walking.web.core.utils;

import com.xiaoniu.walking.web.core.bo.MenuBO;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树工具类
 * @author lihoujing
 */
public final class TreeUtils {

    /**
     * @方法名: parseMenuTree<br>
     * @描述: 组装菜单<br>
     * @param list 数据库里面获取到的全量菜单列表
     * @return
     */
    public static List<MenuBO> parseMenuTree(List<MenuBO> list){
        List<MenuBO> result = new ArrayList<>();

        // 1、获取第一级节点
        for (MenuBO menu : list) {
            if(0 == menu.getParentId()) {
                result.add(menu);
            }
        }
        // 2、递归获取子节点
        for (MenuBO parent : result) {
            parent = recursiveTree(parent, list);
        }
        return result;
    }

    public static MenuBO recursiveTree(MenuBO parent, List<MenuBO> list) {
        for (MenuBO menu : list) {
            if(parent.getPermissionId().equals(menu.getParentId())) {
                menu = recursiveTree(menu, list);
                parent.getChildren().add(menu);
            }
        }
        return parent;
    }

}
