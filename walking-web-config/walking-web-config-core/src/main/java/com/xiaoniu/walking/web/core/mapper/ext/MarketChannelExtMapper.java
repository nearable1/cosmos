package com.xiaoniu.walking.web.core.mapper.ext;

import com.xiaoniu.walking.web.core.bo.MarketChannelBO;
import com.xiaoniu.walking.web.core.model.ext.MarketChannelExt;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author liuyinkai
 */
public interface MarketChannelExtMapper extends Mapper<MarketChannelExt> {
    /**
     * 查询
     *
     * @param marketChannelBO
     * @return
     */
    List<MarketChannelExt> getAllByPage(@RequestBody MarketChannelBO marketChannelBO);

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
     * @param i
     * @return
     */
    int deleteInfo(Integer id);
}