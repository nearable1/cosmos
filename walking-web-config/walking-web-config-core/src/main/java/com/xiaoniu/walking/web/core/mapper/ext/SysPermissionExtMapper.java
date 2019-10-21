package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.core.bo.MenuBO;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

/**
 * @author: xiangxianjin
 * @date: 2019/3/29 17:37
 * @description:
 */
public interface SysPermissionExtMapper extends Mapper<MenuBO> {

    /**
     * 根据角色编号查询所有菜单信息（标记是否选中）
     * @param roleCode
     * @return
     */
    List<MenuBO> findSysPermission(String roleCode);

}