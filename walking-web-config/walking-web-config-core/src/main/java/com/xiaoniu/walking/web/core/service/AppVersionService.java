package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.api.vo.AppVersionVO;
import com.xiaoniu.walking.web.api.vo.OsBannerVO;
import com.xiaoniu.walking.web.core.bo.AppVersionBO;
import com.xiaoniu.walking.web.core.bo.OsBannerBO;
import com.xiaoniu.walking.web.core.bo.QueryAppVersionBO;
import com.xiaoniu.walking.web.core.model.ext.AppVersionExt;
import com.xiaoniu.walking.web.core.model.ext.OsBannerExt;

import java.util.List;

/**
 * AppVersion管理
 *
 * @author liuyinkai
 * @date 2019年6月24日15：18
 */
public interface AppVersionService {

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
         * 获取更新
         */
        AppVersionVO getAppVersion(QueryAppVersionBO queryAppVersionBO);

        /**
         * 根据主键查询
         * @param appVersionId
         * @return
         */
        AppVersionExt queryByAppVersionId(Integer appVersionId);

}
