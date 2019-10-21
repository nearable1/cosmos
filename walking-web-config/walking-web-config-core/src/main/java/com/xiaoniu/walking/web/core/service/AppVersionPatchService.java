package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.AppVersionBO;
import com.xiaoniu.walking.web.core.bo.AppVersionPatchBO;
import com.xiaoniu.walking.web.core.model.auto.AppVersionPatch;
import com.xiaoniu.walking.web.core.model.ext.AppVersionExt;
import com.xiaoniu.walking.web.core.model.ext.AppVersionPatchExt;

/**
 * AppVersion管理
 *
 * @author liuyinkai
 * @date 2019年6月25日15：18
 */
public interface AppVersionPatchService {

        /**
         * app补丁版本查询
         * @param appVersionPatchBO
         * @return Banner查询结果
         */
        PageResult<AppVersionPatchExt> findAppVersionPatchByPage(AppVersionPatchBO appVersionPatchBO);

        /**
         * 新增
         * @param appVersionPatchExt
         * @return
         */
        int insertAppVersionPatch(AppVersionPatchExt appVersionPatchExt);

        /**
         * 修改
         * @param appVersionPatchExt
         * @return
         */
        int updateAppVersionPatch(AppVersionPatchExt appVersionPatchExt);

        /**
         * 删除
         * @param appVersionPatchId
         * @return
         */
        int deleteAppVersionPatchByAppVersionPatchId(Integer appVersionPatchId);

}
