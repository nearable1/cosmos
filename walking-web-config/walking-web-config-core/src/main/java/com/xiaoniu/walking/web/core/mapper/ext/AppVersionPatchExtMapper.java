package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.AppVersionPatchBO;
import com.xiaoniu.walking.web.core.model.ext.AppVersionPatchExt;
import tk.mybatis.mapper.common.Mapper;

public interface AppVersionPatchExtMapper extends Mapper<AppVersionPatchExt> {

    /**
     * app补丁版本查询
     * @param appVersionPatchBO
     * @return Banner查询结果
     */
    PageResult<AppVersionPatchExt> findPatchByPage(AppVersionPatchBO appVersionPatchBO);

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

    /**
     * 删除By appVersionId
     * @param appVersionId
     * @return
     */
    int deleteAppVersionPatchByAppVersionId(Integer appVersionId);

    /**
     * 获取补丁更新
     */
    AppVersionPatchExt getAppVersionPatch(Integer appVersionId);

    /**
     * 根据主键查询
     * @param appVersionPatchId
     * @return
     */
    AppVersionPatchExt queryByAppVersionPatchId(Integer appVersionPatchId);
}