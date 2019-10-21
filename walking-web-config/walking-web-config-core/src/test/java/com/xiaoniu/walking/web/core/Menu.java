package com.xiaoniu.walking.web.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {

    private static final long serialVersionUID = -5990021029947688358L;

    private Integer id;
    private String title;//菜单标题
    private String path;//路径
    private Integer pid;//父菜单ID 一级菜单pid为null
    private Integer level;//级别，排序用

    private List<Menu> children = new ArrayList<>();


    public Menu(Integer id, String title, String path, Integer pid, Integer level, List<Menu> children) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.pid = pid;
        this.level = level;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
