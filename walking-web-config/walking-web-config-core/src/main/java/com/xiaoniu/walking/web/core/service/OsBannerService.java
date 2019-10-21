package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.api.vo.OsBannerVO;
import com.xiaoniu.walking.web.core.bo.OsBannerBO;
import com.xiaoniu.walking.web.core.model.ext.OsBannerExt;

import java.util.List;

/**
 * 运营Banner管理
 *
 * @author chenguohua
 * @date 2019年5月22日12:05:01
 */
public interface OsBannerService {

        /**
         * Banner配置查询
         * @param OsBannerBO
         * @return Banner查询结果
         */
        PageResult<OsBannerExt> findOsBannerByPage(OsBannerBO OsBannerBO);


        /**
         * 新增
         * @param osBannerExt
         * @return
         */
        int insertOsBanner(OsBannerExt osBannerExt);

        /**
         * 修改
         * @param osBannerExt
         * @return
         */
        int updateOsBanner(OsBannerExt osBannerExt);

        /**
         * 删除
         * @param bannerId
         * @return
         */
        int deleteOsBannerByBannerId(Integer bannerId);


        /**
         * 获取banner列表
         * @param bannerPosition
         * @return
         */
        List<OsBannerVO> getOsBannerList(String bannerPosition);

}
