package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.MarketCostBO;
import com.xiaoniu.walking.web.core.model.ext.MarketCostExt;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 市场成本管理
 *
 * @author liuyinkai
 * @date 20190922
 */
public interface MarketCostService {

        /**
         * 查询
         *
         * @param marketCostBO
         * @return
         */
        PageResult<MarketCostExt> getAllByPage(@RequestBody MarketCostBO marketCostBO);

        /**
         * 根据主键查找
         *
         * @param id
         * @return
         */
        MarketCostExt getInfoById(Integer id);

        /**
         * insert
         *
         * @param marketCostBO
         * @return
         */
        int insertInfo(MarketCostBO marketCostBO);

        /**
         * update
         *
         * @param marketCostBO
         * @return
         */
        int updateInfo(MarketCostBO marketCostBO);

        /**
         * 删除
         *
         * @param i
         * @return
         */
        int deleteInfo(Integer id);


}
