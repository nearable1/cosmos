package com.xiaoniu.walking.web.core.domain;

import com.xiaoniu.architecture.commons.core.util.JSONUtils;

/**
 * @author: xiangxianjin
 * @date: 2019/4/15 13:34
 * @description: 树
 */
public class Tree {

    TreeNode root = null;

    public Tree(Integer id , String name) {
        root = new TreeNode(id, name);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public static void main(String[] args) {
        Tree tree = new Tree(1, "A");
        TreeNode nodeB = new TreeNode(2,"B");
        TreeNode nodeC = new TreeNode(3,"C");

        nodeB.add(nodeC);
        tree.root.add(nodeB);
        System.out.println(JSONUtils.toJSONString(tree.root));
    }
}
