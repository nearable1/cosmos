/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.gendemo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inbody.crm.common.service.TreeService;
import com.inbody.crm.common.utils.IdGen;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.gendemo.dao.TestTreeDao;
import com.inbody.crm.modules.gendemo.entity.TestTree;
import com.inbody.crm.modules.gendemo.entity.TreeNode;
import com.inbody.crm.modules.sys.entity.User;

/**
 * 树结构生成Service
 * @author ThinkGem
 * @version 2015-04-06
 */
@Service
@Transactional(readOnly = true)
public class TestTreeService extends TreeService<TestTreeDao, TestTree> {

	public TestTree get(String id) {
		return super.get(id);
	}
	
	public List<TestTree> findList(TestTree testTree) {
		if (StringUtils.isNotBlank(testTree.getParentIds())){
			testTree.setParentIds(","+testTree.getParentIds()+",");
		}
		return super.findList(testTree);
	}
	//异步加载节点数据
	public List<TreeNode> getZtreeData(String id){
		TestTree testTree = new TestTree();
		TestTree parent = new TestTree();
		if (StringUtils.isNotBlank(id)){
			testTree.setParentIds(","+id+",");
		}else{
			testTree.setParentIds("0,");
			id="0";
		}
		parent.setId(id);
		testTree.setParent(parent);
		List<TestTree> findList = super.findList(testTree);
		List<TreeNode> json = new ArrayList<TreeNode>();
		for (TestTree tree : findList) {
			TreeNode node = new TreeNode();
			node.setId(tree.getId());
			node.setpId(tree.getParentId());
			node.setName(tree.getName());
			node.setIsParent(isParent(tree));
			json.add(node);
		}
		return json;
	}
	
	//判断是否是父节点
	public boolean isParent(TestTree tree){
		List<TestTree> treeList = dao.findByParentId(tree);
		if(treeList.isEmpty()){
			return false;
		}
		return true;
	}
	@Transactional(readOnly = false)
	public void save(TestTree testTree) {
		super.save(testTree);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestTree testTree) {
		super.delete(testTree);
	}
	
	@Transactional(readOnly = false)
	public void renameNode(String name, String id) {
		// TODO Auto-generated method stub
		TestTree tree = new TestTree();
		tree.setId(id);
		tree.setName(name);
		dao.updateName(tree);
	}

	public void removeNode(String id) {
		// TODO Auto-generated method stub
		TestTree tree = new TestTree();
		tree.setId(id);
		//tree.setDelFlag("1");
		dao.deleteOnce(tree);
	}
	@Transactional(readOnly = false)
	public void moveNode(String pId,String id){
		TestTree tree = new TestTree();
		TestTree parent = new TestTree();
		parent.setId(pId);
		List<TestTree> findById2 = dao.findById(parent);
		if(!findById2.isEmpty()){
			TestTree findById = findById2.get(0);
			String parentIds = findById.getParentIds();
			String ids = parentIds+pId+",";
			tree.setParentIds(ids);
		}
		tree.setId(id);
		tree.setParent(parent);
		dao.updateParentIds(tree);
	}
	@Transactional(readOnly = false)
	public void addNode(String name, String pId) {
		// TODO Auto-generated method stub
		TestTree tree = new TestTree();
		TestTree parent = new TestTree();
		parent.setId(pId);
		List<TestTree> findById2 = dao.findById(parent);
		if(!findById2.isEmpty()){
			TestTree findById = findById2.get(0);
			String parentIds = findById.getParentIds();
			String ids = parentIds+pId+",";
			tree.setParentIds(ids);
		}
		String uuid = IdGen.uuid();
		tree.setId(uuid);
		tree.setParent(parent);
		tree.setName(name);
		tree.setCreateBy(new User("1"));
		tree.setCreateDate(new Date());
		tree.setUpdateBy(new User("1"));
		tree.setUpdateDate(new Date());
		tree.setSort(60);
		tree.setDelFlag("0");
		tree.setRemarks("...");
		dao.insert(tree);
		//super.save(tree);
	}
	public TreeNode findNewest(){
		List<TestTree> findNewest = dao.findNewest();
		if(findNewest.isEmpty()){
			return null;
		}
		TestTree testTree = findNewest.get(0);
		TreeNode node = new TreeNode();
		node.setId(testTree.getId());
		node.setpId(testTree.getParentId());
		node.setName(testTree.getName());
		return node;
	}
}