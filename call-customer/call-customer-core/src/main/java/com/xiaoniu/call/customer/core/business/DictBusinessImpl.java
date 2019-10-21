package com.xiaoniu.call.customer.core.business;

import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.customer.api.business.DictBusiness;
import com.xiaoniu.call.customer.api.dto.BigDictDTO;
import com.xiaoniu.call.customer.api.dto.SmallDictDTO;
import com.xiaoniu.call.customer.api.vo.BigDictQueryVO;
import com.xiaoniu.call.customer.api.vo.BigDictVO;
import com.xiaoniu.call.customer.api.vo.SmallDictQueryVO;
import com.xiaoniu.call.customer.api.vo.SmallDictVO;
import com.xiaoniu.call.customer.core.service.DataDictService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@Log4j2
public class DictBusinessImpl implements DictBusiness {

    @Autowired
    private DataDictService dataDictService;

    @Override
    @PostMapping("/queryBigList")
    public PageResult<BigDictDTO> queryBigList(@Valid @RequestBody BigDictQueryVO bigDictQueryVO) {
        PageResult<BigDictDTO> resultWeb = dataDictService.queryBigList(bigDictQueryVO);
        return resultWeb;
    }

    @Override
    @PutMapping("/updateBigDict")
    public void updateBigDict(@Valid @RequestBody BigDictVO bigDictVO) {
        BigDictDTO bigDict = dataDictService.queryBigDictById(bigDictVO.getBigId());

        // 先修改小类数据字典
        if (!bigDict.getBigCode().equals(bigDictVO.getBigCode())) {
            Map<String, Object> bigCodeMap = new HashMap<>();
            bigCodeMap.put("newBigCode", bigDictVO.getBigCode());
            bigCodeMap.put("oldBigCode", bigDict.getBigCode());
            dataDictService.updatePatchSmallDict(bigCodeMap);
        }
        // 后修改大类数据字典
        int update = dataDictService.updateBigDict(bigDictVO);
        if (update > 0) {
            if (!StringUtils.equals(bigDictVO.getBigCode(), bigDict.getBigCode())) {
                // 删除redis
                delBigDictFromRedis(bigDict.getBigCode());
            }
            if (bigDictVO.getBigState() == 1) {
                // 添加到redis
                addBigDictToRedis(bigDictVO.getBigCode());
            }
            log.info("修改大类数据字典成功");
        }
    }

    @Override
    @PostMapping("/saveBigDict")
    public void saveBigDict(@Valid @RequestBody BigDictVO bigDictVO) {
        BigDictDTO bigDictVO1 = dataDictService.queryBigDictByCode(bigDictVO.getBigCode());
        if (bigDictVO1 == null) {
            bigDictVO.setBigCreateTime(new Date());
            bigDictVO.setBigModifyTime(new Date());
            int update = dataDictService.saveBigDict(bigDictVO);
            if (update > 0) {
                log.info("添加大类数据字典成功,bigCode:{}", bigDictVO.getBigCode());
            } else {
                throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM, "新增大类失败");
            }
        } else {
            throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM, "大类已存在");
        }
    }

    @Override
    @DeleteMapping("/deleteBigDict")
    public void deleteBigDict(@RequestParam(value = "bigId") Long bigId) {
        BigDictDTO bigDictVO1 = dataDictService.queryBigDictById(bigId);
        // 删除小类
        if (bigDictVO1 != null) {
            // 删除redis
            dataDictService.deletePatchSmallDict(bigDictVO1.getBigCode());
            delBigDictFromRedis(bigDictVO1.getBigCode());
        }
        // 删除大类
        int update = dataDictService.deleteBigDict(bigId);
        if (update > 0) {
            log.info("删除大类数据字典成功, id:{}", bigId);
        } else {
            throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM, "删除大类失败");
        }
    }

    /*********************************************************************/
    /**************************** 小类管理 **********************************/
    /*********************************************************************/

    @Override
    @PostMapping("/smallList")
    public PageResult<SmallDictDTO> smallList(@Valid @RequestBody SmallDictQueryVO smallDictQueryVO) {
        return dataDictService.querySmallList(smallDictQueryVO);
    }

    @Override
    @PutMapping("/updateSmallDict")
    public void updateSmallDict(@Valid @RequestBody SmallDictVO smallDictVO) {
        // 删除redis
        SmallDictDTO smallDict = dataDictService.querySmallDictById(smallDictVO.getSmallId());
        delSmallDictFromRedis(smallDict.getBigDicCode(), smallDict.getSmallCode());
        // 修改
        int update = dataDictService.updateSmallDict(smallDictVO);
        if (update > 0) {
            // 是否需要到添加redis
            BigDictDTO bigDictVO = dataDictService.queryBigDictByCode(smallDictVO.getBigDicCode());
            SmallDictDTO smallDictVO1 = dataDictService.querySmallDictByCode(smallDictVO.getBigDicCode(),
                    smallDictVO.getSmallCode());
            if (bigDictVO != null && smallDictVO1 != null) {
                if (bigDictVO.getBigState() == 1 && smallDictVO1.getSmallState() == 1) {
                    addSmallDictFromRedis(smallDictVO.getBigDicCode(), smallDictVO.getSmallCode(),
                            smallDictVO.getSmallValue());
                }
            }
            log.info("修改小类数据字典成功");
        }
    }

    @Override
    @PostMapping("/saveSmallDict")
    public void saveSmallDict(@Valid @RequestBody SmallDictVO smallDictVO) {
        SmallDictDTO smallDict = dataDictService.querySmallDictByCode(smallDictVO.getBigDicCode(),
                smallDictVO.getSmallCode());
        if (smallDict != null) {
            throw new BusinessException(ResultCodeEnum.ILLEGAL_PARAM, "小类已存在");
        }
        int update = dataDictService.saveSmallDict(smallDictVO);
        if (update > 0) {
            // 添加redis
            BigDictDTO bigDictVO = dataDictService.queryBigDictByCode(smallDictVO.getBigDicCode());
            SmallDictDTO smallDictVO1 = dataDictService.querySmallDictByCode(smallDictVO.getBigDicCode(),
                    smallDictVO.getSmallCode());
            if (bigDictVO != null && smallDictVO1 != null) {
                if (bigDictVO.getBigState() == 1 && smallDictVO1.getSmallState() == 1) {
                    addSmallDictFromRedis(smallDictVO.getBigDicCode(), smallDictVO.getSmallCode(),
                            smallDictVO.getSmallValue());
                }
            }
            log.info("添加小类数据字典成功");
        }
    }

    @Override
    @DeleteMapping("/deleteSmallDict")
    public void deleteSmallDict(@RequestParam(value = "smallId") Long smallId) {
        // 删除redis
        SmallDictDTO smallDict = dataDictService.querySmallDictById(smallId);
        delSmallDictFromRedis(smallDict.getBigDicCode(), smallDict.getSmallCode());
        // 删除数据
        int update = dataDictService.deleteSmallDict(smallId);
        if (update > 0) {
            log.info("删除小类数据字典成功");
        }
    }

    /**
     * 根据数据字典大类ID查询小类
     *
     * @param type
     * @return
     */
    @Override
    public List<SmallDictDTO> querySubclassByBigCode(String type) {
        return dataDictService.querySmallDictRedisByBigCode(type);
    }

    @Override
    public SmallDictDTO queryBySmallCode(String bigCode, String smallCode) {
        return dataDictService.querySmallDictByCode(bigCode,smallCode);
    }


    /*********************************************************************/
    /**************************** redis缓存 ********************************/
    /*********************************************************************/
    /**
     * 把单个大类缓存的数据从redis删除
     */
    public void delBigDictFromRedis(String bigCode) {
        RedisRepository.del(bigCode);
    }

    /**
     * 把单个大类缓存的数据添加到redis
     */
    public void addBigDictToRedis(String bigCode) {
        /** 小类数据字典缓存 **/
        List<SmallDictDTO> smallDicts = dataDictService.querySmallDictRedisByBigCode(bigCode);
        for (SmallDictDTO smallDict : smallDicts) {
            RedisRepository.hset(bigCode, smallDict.getSmallCode(), smallDict.getSmallValue());
        }
    }

    /**
     * 把单个小类缓存的数据从redis删除
     */
    public void delSmallDictFromRedis(String bigCode, String smallCode) {
        RedisRepository.hdel(bigCode, smallCode);
    }

    /**
     * 把单个小类缓存的数据添加到redis
     */
    public void addSmallDictFromRedis(String bigCode, String smallCode, String value) {
        RedisRepository.hset(bigCode, smallCode, value);
    }

}
