package com.xiaoniu.walking.web.core;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {

    /**
     * @方法名: parseMenuTree<br>
     * @描述: 组装菜单<br>
     * @param list 数据库里面获取到的全量菜单列表
     * @return
     */
    public static List<Menu> parseMenuTree(List<Menu> list){
        List<Menu> result = new ArrayList<>();

        // 1、获取第一级节点
        for (Menu menu : list) {
            if(null == menu.getPid()) {
                result.add(menu);
            }
        }

        // 2、递归获取子节点
        for (Menu parent : result) {
            parent = recursiveTree(parent, list);
        }

        return result;
    }

    public static Menu recursiveTree(Menu parent, List<Menu> list) {
        for (Menu menu : list) {
            if(parent.getId() == menu.getPid()) {
                menu = recursiveTree(menu, list);
                parent.getChildren().add(menu);
            }
        }

        return parent;
    }

}
