package com.xiaoniu.walking.web.core.model.ext;

public class SysPermissionVO {
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
     * 状态：1-有效，2-失效
     */
    private Byte permissionState;

    /**
     * 是否选中：1-选中，0-未选中
     */
    private String checked;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    /**
     * 获取主键编号
     *
     * @return permission_id - 主键编号
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * 设置主键编号
     *
     * @param permissionId 主键编号
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * 获取权限名称
     *
     * @return names - 权限名称
     */
    public String getNames() {
        return names;
    }

    /**
     * 设置权限名称
     *
     * @param names 权限名称
     */
    public void setNames(String names) {
        this.names = names;
    }

    /**
     * 获取菜单地址
     *
     * @return url - 菜单地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置菜单地址
     *
     * @param url 菜单地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取图标
     *
     * @return img_icon - 图标
     */
    public String getImgIcon() {
        return imgIcon;
    }

    /**
     * 设置图标
     *
     * @param imgIcon 图标
     */
    public void setImgIcon(String imgIcon) {
        this.imgIcon = imgIcon;
    }

    /**
     * 获取排序(升序)
     *
     * @return orders - 排序(升序)
     */
    public Short getOrders() {
        return orders;
    }

    /**
     * 设置排序(升序)
     *
     * @param orders 排序(升序)
     */
    public void setOrders(Short orders) {
        this.orders = orders;
    }

    /**
     * 获取父菜单编号
     *
     * @return parent_id - 父菜单编号
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父菜单编号
     *
     * @param parentId 父菜单编号
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取权限编码
     *
     * @return authority - 权限编码
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * 设置权限编码
     *
     * @param authority 权限编码
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    /**
     * 获取权限类型：1-菜单，2-按钮
     *
     * @return permission_type - 权限类型：1-菜单，2-按钮
     */
    public String getPermissionType() {
        return permissionType;
    }

    /**
     * 设置权限类型：1-菜单，2-按钮
     *
     * @param permissionType 权限类型：1-菜单，2-按钮
     */
    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * 获取状态：1-有效，2-失效
     *
     * @return permission_state - 状态：1-有效，2-失效
     */
    public Byte getPermissionState() {
        return permissionState;
    }

    /**
     * 设置状态：1-有效，2-失效
     *
     * @param permissionState 状态：1-有效，2-失效
     */
    public void setPermissionState(Byte permissionState) {
        this.permissionState = permissionState;
    }
}
