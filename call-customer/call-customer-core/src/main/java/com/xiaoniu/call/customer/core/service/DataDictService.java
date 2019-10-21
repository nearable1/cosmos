package com.xiaoniu.call.customer.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.BigDictDTO;
import com.xiaoniu.call.customer.api.dto.SmallDictDTO;
import com.xiaoniu.call.customer.api.vo.BigDictQueryVO;
import com.xiaoniu.call.customer.api.vo.BigDictVO;
import com.xiaoniu.call.customer.api.vo.SmallDictQueryVO;
import com.xiaoniu.call.customer.api.vo.SmallDictVO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DataDictService
 * @Author
 * @Date 2019/1/2 18:39
 * @Version 1.0
 * @Description TODO
 */
public interface DataDictService {
    PageResult<BigDictDTO> queryBigList(BigDictQueryVO bigDictQueryVO);

    int updateBigDict(BigDictVO bigDictVO);

    int saveBigDict(BigDictVO bigDictVO);

    int deleteBigDict(Long bigId);

    BigDictDTO queryBigDictById(Long bigId);

    BigDictDTO queryBigDictByCode(String bigCode);

    List<BigDictDTO> queryBigDictRedis();


    /**********************************************************/
    /***********************小类数据****************************/
    /**********************************************************/
    PageResult<SmallDictDTO> querySmallList(SmallDictQueryVO bigDictQueryVO);

    int updateSmallDict(SmallDictVO smallDictVO);

    int updatePatchSmallDict(Map param);

    int saveSmallDict(SmallDictVO smallDictVO);

    int deleteSmallDict(Long smallId);

    int deletePatchSmallDict(String smallCode);

    SmallDictDTO querySmallDictById(Long smallId);

    SmallDictDTO querySmallDictByCode(String bigCode, String smallCode);

    List<SmallDictDTO> querySmallDictRedisByBigCode(String bigDictCode);

}
