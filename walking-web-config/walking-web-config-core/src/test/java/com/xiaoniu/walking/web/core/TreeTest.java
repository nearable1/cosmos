package com.xiaoniu.walking.web.core;


import java.util.ArrayList;
import java.util.List;

public class TreeTest {

    public static void main(String[] args) {

        List<Menu> list = new ArrayList<>();
        //Integer id, String title, String path, Integer pid, Integer level, List<Menu> children
        List<Menu> children = new ArrayList<>();
        children.add(new Menu(2,"用户管理","",1,2,new ArrayList<>()));
        children.add(new Menu(3,"角色管理","/role",1,3,new ArrayList<>()));
        list.add(new Menu(1,"系统管理","",null,0,children));
        //TODO 这里从数据库获取全量菜单后放到list中

        //树形结构数据生成
        List<Menu> result = TreeUtils.parseMenuTree(list);

    }

}
