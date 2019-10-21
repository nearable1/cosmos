package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.api.vo.SysDictVO;
import com.xiaoniu.walking.web.core.bo.SysDictBO;
import com.xiaoniu.walking.web.core.model.auto.SysDict;
import com.xiaoniu.walking.web.core.model.ext.SysDictExt;
import java.util.List;

/**
 * 系统字典
 *
 * @author: chenguohua
 * @date: 2019/04/20 11:36
 * @description:
 */
public interface SystemDictionaryService {

    /**
     * 根据类型和标签名获取系统的字典的数据
     *
     * @param sysdictBO
     * @return
     */
    PageResult<SysDictExt> query(SysDictBO sysdictBO);


    /**
     * 添加数据字典大类
     *
     * @param sysDictExt 数据字典
     * @return
     */
    int insertParent(SysDictExt sysDictExt);

    /**
     * 修改数据字典大类
     *
     * @param sysDictExt 数据字典
     * @return
     */
    int updateParent(SysDictExt sysDictExt);

    /**
     * 删除数据字典大类
     *
     * @param sysDictExt 编号逐渐Id
     * @return
     */
    int deleteByParentId(SysDictExt sysDictExt);

    /**
     * 获取系统的字典的数据
     *
     * @param parentId 父类Id
     * @return
     */
    List<SysDictExt> getSysDictNodesById(Long parentId);

    /**
     * 添加数据字典的小类
     *
     * @param sysDictExt
     * @return
     */
    int saveChildren(SysDictExt sysDictExt);

    /**
     * 修改数据字典的小类
     *
     * @param sysDictExt
     * @return
     */
    int editChildrenList(SysDictExt sysDictExt);


    /**
     * 根据类型查询所有小类
     *
     * @param type
     * @return
     */
    List<SysDictVO> getSysDictNodesByType(String type);

    /**
     * 根据数据值获取标签名
     *
     * @param value
     * @return
     */
    SysDictVO getSysDictByValue(String value);


    /**
     * 根据id查询对象
     *
     * @param id 编号
     * @return
     */
    SysDict getSysDictById(String id);



    /**
     * 查询数据字典小类详情
     *
     * @param goodsId
     * @return
     */
    SysDictVO getChildrenDetailByLabel(String goodsId);



}
