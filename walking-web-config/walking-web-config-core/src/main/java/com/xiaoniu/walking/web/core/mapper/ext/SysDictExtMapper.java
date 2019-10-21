package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.api.vo.SysDictVO;
import com.xiaoniu.walking.web.core.model.auto.SysDict;
import com.xiaoniu.walking.web.core.model.ext.SysDictExt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * 系统字典
 *
 * @author chenguohua
 * @date 2019/04/20 19:24
 */
public interface SysDictExtMapper extends Mapper<SysDictExt> {

    int insertParent(SysDictExt sysDictExt);

    int updateParent(SysDictExt sysDictExt);

    int deleteById(@Param(value = "id") Long id);

    List<SysDictExt> getSysDictNodesById(@Param(value = "parentId") Long parentId);

    int saveChildren(@Param(value = "label") String label, @Param(value = "description") String description, @Param(value = "createBy") String createBy, @Param(value = "createDate") Date createDate, @Param(value = "delFlag") String delFlag, @Param(value = "sort") Integer sort, @Param(value = "parentId") String parentId, @Param(value = "value") String value);

    int editChildren(@Param(value = "label") String label, @Param(value = "description") String description, @Param(value = "updateBy") String updateBy, @Param(value = "updateDate") Date updateDate, @Param(value = "delFlag") String delFlag, @Param(value = "sort") Integer sort, @Param(value = "value") String value, @Param(value = "id") Long id);

    List<SysDictVO> getSysDictNodesByType(@Param("type") String type);

    SysDictVO getSysDictByValue(@Param("value") String value);

    SysDict getSysDictById(@Param("id") String id);
    
    SysDictVO getChildrenDetailByLabel(String goodsId);


}