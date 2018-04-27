package com.inbody.crm.modules.gendemo.entity;

public class TreeNode {
	private String id;
	private String name;
	private String pId;
	private String icon;
	private boolean isEnable;
	private boolean open;
	private boolean nocheck;
	private boolean isParent;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public TreeNode() {
		super();
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
	
	public void setIsParent(boolean isParent){
		this.isParent = isParent;
	}
	
	public boolean getIsParent() {
		return isParent;
	}
	/*public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}*/

	
	
}
