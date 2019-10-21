package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.model.auto.WhiteList;
import com.xiaoniu.walking.web.core.model.ext.WhiteListExt;
import tk.mybatis.mapper.common.Mapper;

public interface WhiteListExtMapper extends Mapper<WhiteList> {
    /**
     * 查询列表
     *
     * @param whiteList
     * @return
     */
    PageResult<WhiteListExt> finddAll(WhiteList whiteList);

    /**
     * 新增
     *
     * @param whiteList
     * @return
     */
    int insertWhiteList(WhiteList whiteList);

    /**
     * 更新
     *
     * @param whiteList
     * @return
     */
    int updateWhiteList(WhiteList whiteList);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);
}