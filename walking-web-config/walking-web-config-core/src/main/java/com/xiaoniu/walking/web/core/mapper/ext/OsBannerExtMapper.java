package com.xiaoniu.walking.web.core.mapper.ext;


import com.xiaoniu.walking.web.api.vo.OsBannerVO;
import com.xiaoniu.walking.web.core.bo.OsBannerBO;
import com.xiaoniu.walking.web.core.model.ext.OsBannerExt;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 运营banner配置
 * @author chenguohua
 * @date 2019年5月22日11:33:21
 */
public interface OsBannerExtMapper extends Mapper<OsBannerExt> {

    int insertOsBanner(OsBannerExt osBannerExt);

    int updateOsBanner(OsBannerExt osBannerExt);

    int deleteOsBanner(Integer bannerId);

    List<OsBannerVO> getOsBannerList(OsBannerBO osBannerBO);
}