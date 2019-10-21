package com.xiaoniu.walking.web.core.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author: xiangxianjin
 * @date: 2019/4/11 01:37
 * @description: 用户信息
 *  * @author xiangxianjin
 */
@Getter
@Setter
@NoArgsConstructor
public class RolePermissionBO implements Serializable {
    /**
     * 所有菜单列表
     */
    private List<MenuBO> data;
    /**
     * 选中菜单
     */
    private Set<Integer> id;


    @Override
    public String toString() {
        return "RolePermissionBO{" +
                "data=" + data +
                ", id=" + id +
                '}';
    }
}
