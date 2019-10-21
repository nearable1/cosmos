package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.MarketChannelBO;
import com.xiaoniu.walking.web.core.model.ext.MarketChannelExt;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 市场渠道管理
 *
 * @author liuyinkai
 * @date 20190924
 */
public interface MarketChannelService {

        /**
         * 查询
         *
         * @param marketChannelBO
         * @return
         */
        PageResult<MarketChannelExt> getAllByPage(@RequestBody MarketChannelBO marketChannelBO);

        /**
         * 根据主键查找
         *
         * @param id
         * @return
         */
        MarketChannelExt getInfoById(Integer id);

        /**
         * insert
         *
         * @param marketChannelBO
         * @return
         */
        int insertInfo(MarketChannelBO marketChannelBO);

        /**
         * update
         *
         * @param marketChannelBO
         * @return
         */
        int updateInfo(MarketChannelBO marketChannelBO);

        /**
         * 删除
         *
         * @param id
         * @return
         */
        int deleteInfo(Integer id);


}
