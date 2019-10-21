package com.xiaoniu.call.customer.core.mapper;

import com.xiaoniu.call.customer.api.dto.SmallDictDTO;
import com.xiaoniu.call.customer.api.vo.SmallDictQueryVO;
import com.xiaoniu.call.customer.api.vo.SmallDictVO;
import com.xiaoniu.call.customer.core.entity.DicOpt;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface DicOptMapper extends Mapper<DicOpt> {
    List<DicOpt> selectByDicCode(@Param("dicCode") String dicCode, @Param("code") String code);

    List<SmallDictDTO> queryDictListByPage(SmallDictQueryVO vo);
    int addDict(SmallDictVO smallDictVO);
    int updateDictByPK(SmallDictVO smallDictVO);
    int updatePatchSmallDict(Map param);
    int deleteDictById(Long smallId);
    int deleteDictByBigCode(String bigDictCode);
    SmallDictDTO querySmallDictById(Long smallId);
    SmallDictDTO querySmallDictByCode(Map param);
    List<SmallDictDTO> querySmallDictByBigCode(String bigDicCode);
    SmallDictDTO queryDictDetailByCode(String smallCode);
    Integer updateValueByCode(Map<String, Object> map);
}