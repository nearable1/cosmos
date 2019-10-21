package com.xiaoniu.walking.web.core.domain;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author: xiangxianjin
 * @date: 2019/4/15 13:34
 * @description: 树叶
 */
public class TreeNode {

    private Integer id;
    private String label;
    private TreeNode parent;
    private Vector<TreeNode> children = new Vector<TreeNode>();

    public TreeNode(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    /**
     * 添加孩子节点
     */
    public void add(TreeNode node){
        children.add(node);
    }

    /**
     * 删除孩子节点
     * @param node
     */
    public void remove(TreeNode node){
        children.remove(node);
    }

    /**
     * 取得孩子节点
     */
    public Enumeration<TreeNode> getChildren(){
        return children.elements();
    }
}
