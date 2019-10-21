package com.xiaoniu.call.customer.core.mapper;

import com.xiaoniu.call.customer.api.dto.BigDictDTO;
import com.xiaoniu.call.customer.api.vo.BigDictQueryVO;
import com.xiaoniu.call.customer.api.vo.BigDictVO;
import com.xiaoniu.call.customer.core.entity.Dic;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DicMapper extends Mapper<Dic> {
    List<BigDictDTO> queryDictListByPage(BigDictQueryVO bigDictQueryVO);
    int addDict(BigDictVO bigDictVO);
    int updateDictByPK(BigDictVO bigDictVO);
    int deleteDictById(Long bigId);
    BigDictDTO queryDictByBigCode(String bigCode);
    BigDictDTO queryDictById(Long bigId);
    List<BigDictDTO> queryDictRedis();
    List<BigDictDTO> queryAllDictRedis();
}