package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.core.bo.MarketCostBO;
import com.xiaoniu.walking.web.core.model.ext.MarketCostExt;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author liuyinkai
 */
public interface MarketCostExtMapper extends Mapper<MarketCostExt> {
    /**
     * 查询
     *
     * @param marketCostBO
     * @return
     */
    List<MarketCostExt> getAllByPage(@RequestBody MarketCostBO marketCostBO);

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