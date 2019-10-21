package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.api.vo.AppVersionVO;
import com.xiaoniu.walking.web.core.bo.AppVersionBO;
import com.xiaoniu.walking.web.core.bo.QueryAppVersionBO;
import com.xiaoniu.walking.web.core.model.ext.AppVersionExt;
import tk.mybatis.mapper.common.Mapper;

public interface AppVersionExpMapper extends Mapper<AppVersionExt> {
    /**
     * app版本查询
     * @param appVersionBO
     * @return Banner查询结果
     */
    PageResult<AppVersionExt> findAppVersionByPage(AppVersionBO appVersionBO);

    /**
     * 新增
     * @param appVersionExt
     * @return
     */
    int insertAppVersion(AppVersionExt appVersionExt);

    /**
     * 修改
     * @param appVersionExt
     * @return
     */
    int updateAppVersion(AppVersionExt appVersionExt);

    /**
     * 删除
     * @param appVersionId
     * @return
     */
    int deleteAppVersionByAppVersionId(Integer appVersionId);

    /**
     * 获取更新 父版本
     */
    AppVersionVO getAppVersion(QueryAppVersionBO queryAppVersionBO);

    /**
     * 根据主键查询
     * @param appVersionId
     * @return
     */
    AppVersionExt queryByAppVersionId(Integer appVersionId);


}