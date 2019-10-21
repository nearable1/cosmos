package com.xiaoniu.call.customer.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.call.customer.api.dto.BigDictDTO;
import com.xiaoniu.call.customer.api.dto.SmallDictDTO;
import com.xiaoniu.call.customer.api.vo.BigDictQueryVO;
import com.xiaoniu.call.customer.api.vo.BigDictVO;
import com.xiaoniu.call.customer.api.vo.SmallDictQueryVO;
import com.xiaoniu.call.customer.api.vo.SmallDictVO;
import com.xiaoniu.call.customer.core.mapper.DicMapper;
import com.xiaoniu.call.customer.core.mapper.DicOptMapper;
import com.xiaoniu.call.customer.core.service.DataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DataDictServiceImpl
 * @Author
 * @Date 2019/1/2 18:40
 * @Version 1.0
 * @Description 数据
 */
@Service
public class DataDictServiceImpl implements DataDictService {


    @Autowired

    private DicMapper vcDictMapper;

    @Autowired
    private DicOptMapper vcDictOptMapper;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public PageResult<BigDictDTO> queryBigList(BigDictQueryVO bigDictQueryVO) {
        PageResult<BigDictDTO> pageResult = pageRepository.selectPaging(DicMapper.class, "queryDictListByPage", bigDictQueryVO);
        return pageResult;
    }

    @Override
    public int updateBigDict(BigDictVO bigDictVO) {
        return vcDictMapper.updateDictByPK(bigDictVO);
    }

    @Override
    public int saveBigDict(BigDictVO bigDictVO) {
        return vcDictMapper.addDict(bigDictVO);
    }

    @Override
    public int deleteBigDict(Long bigId) {
        return vcDictMapper.deleteDictById(bigId);
    }

    @Override
    public BigDictDTO queryBigDictById(Long bigId) {
        return vcDictMapper.queryDictById(bigId);
    }

    @Override
    public BigDictDTO queryBigDictByCode(String bigCode) {
        return vcDictMapper.queryDictByBigCode(bigCode);
    }

    @Override
    public List<BigDictDTO> queryBigDictRedis() {
        return vcDictMapper.queryDictRedis();
    }





    /**********************************************************/
    /***********************小类数据****************************/
    /**********************************************************/

    @Override
    public PageResult<SmallDictDTO> querySmallList(SmallDictQueryVO bigDictQueryVO) {
        PageResult<SmallDictDTO> pageResult = pageRepository.selectPaging(DicOptMapper.class, "queryDictListByPage", bigDictQueryVO);
        return pageResult;
    }

    @Override
    public int updateSmallDict(SmallDictVO smallDictVO) {
        return vcDictOptMapper.updateDictByPK(smallDictVO);
    }

    @Override
    public int updatePatchSmallDict(Map param) {
        return vcDictOptMapper.updatePatchSmallDict(param);
    }

    @Override
    public int saveSmallDict(SmallDictVO smallDictVO) {
        return vcDictOptMapper.addDict(smallDictVO);
    }

    @Override
    public int deleteSmallDict(Long smallId) {
        return vcDictOptMapper.deleteDictById(smallId);
    }

    @Override
    public int deletePatchSmallDict(String bigCode) {
        return vcDictOptMapper.deleteDictByBigCode(bigCode);
    }

    @Override
    public SmallDictDTO querySmallDictById(Long smallId) {
        return vcDictOptMapper.querySmallDictById(smallId);
    }

    @Override
    public SmallDictDTO querySmallDictByCode(String bigCode,String smallCode) {
        Map param = new HashMap();
        param.put("bigDicCode",bigCode);
        param.put("smallCode",smallCode);
        return vcDictOptMapper.querySmallDictByCode(param);
    }

    @Override
    public List<SmallDictDTO> querySmallDictRedisByBigCode(String bigDictCode) {
        return vcDictOptMapper.querySmallDictByBigCode(bigDictCode);
    }
}
