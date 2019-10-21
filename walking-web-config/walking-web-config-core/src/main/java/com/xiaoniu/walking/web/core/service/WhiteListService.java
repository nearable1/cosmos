package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.model.auto.WhiteList;
import com.xiaoniu.walking.web.core.model.ext.WhiteListExt;

/**
 * 白名单管理
 *
 * @author liuyinkai
 * @date 20190919
 */
public interface WhiteListService {

        /**
         * 查询
         *
         * @param whiteList
         * @return
         */
        PageResult<WhiteListExt> findByPage(WhiteList whiteList);

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
