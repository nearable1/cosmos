package com.xiaoniu.call.customer.core.service.impl;

import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.customer.api.dto.AccountManageDTO;
import com.xiaoniu.call.customer.api.dto.AsmConfigDTO;
import com.xiaoniu.call.customer.api.dto.AsmConfigDataDTO;
import com.xiaoniu.call.customer.api.dto.AsmConfigLogDTO;
import com.xiaoniu.call.customer.api.vo.AsmConfigDataVO;
import com.xiaoniu.call.customer.api.vo.AsmConfigLogVO;
import com.xiaoniu.call.customer.api.vo.AsmConfigVO;
import com.xiaoniu.call.customer.core.cons.RedisCons;
import com.xiaoniu.call.customer.core.entity.AsmConfig;
import com.xiaoniu.call.customer.core.entity.AsmConfigData;
import com.xiaoniu.call.customer.core.entity.AsmConfigLog;
import com.xiaoniu.call.customer.core.mapper.AsmConfigDataMapper;
import com.xiaoniu.call.customer.core.mapper.AsmConfigLogMapper;
import com.xiaoniu.call.customer.core.mapper.AsmConfigMapper;
import com.xiaoniu.call.customer.core.mapper.CustomerMapper;
import com.xiaoniu.call.customer.core.service.AsmConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName DataDictService
 * @Author
 * @Date 2019/1/2 18:39
 * @Version 1.0
 * @Description TODO
 */
@Service
public class AsmConfigServiceImpl implements AsmConfigService {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private AsmConfigMapper asmConfigMapper;

    @Autowired
    private AsmConfigDataMapper asmConfigDataMapper;

    @Autowired
    private AsmConfigLogMapper asmConfigLogMapper;

    /**
     * 下发权限配置分页查询
     *
     * @param asmConfigVO
     * @return
     */
    @Override
    public PageResult<AsmConfigDTO> queryAsmConfigList(AsmConfigVO asmConfigVO) {
        return pageRepository.selectPaging(AsmConfigMapper.class, "selectListByPage", asmConfigVO);
    }

    /**
     * 新增下发权限配置
     *
     * @param asmConfigVO
     */
    @Override
    public void saveAsmConfig(AsmConfigVO asmConfigVO) {
        AsmConfig asmConfig = new AsmConfig();
        asmConfig.setRom(asmConfigVO.getRom());
        asmConfig.setApi(asmConfigVO.getApi());
        asmConfig.setManufacturer(asmConfigVO.getManufacturer());
        asmConfig.setModel(asmConfigVO.getModel());
        asmConfig.setRemark(asmConfigVO.getRemark());
        asmConfig.setAsmDataId(asmConfigVO.getAsmDataId());
        int result = asmConfigMapper.insertSelective(asmConfig);
    }

    /**
     * 修改下发权限配置
     *
     * @param asmConfigVO
     */
    @Override
    public void updateAsmConfig(AsmConfigVO asmConfigVO) {
        AsmConfig asmConfig = new AsmConfig();
        asmConfig.setId(asmConfigVO.getId());
        asmConfig.setRom(asmConfigVO.getRom());
        asmConfig.setApi(asmConfigVO.getApi());
        asmConfig.setManufacturer(asmConfigVO.getManufacturer());
        asmConfig.setModel(asmConfigVO.getModel());
        asmConfig.setRemark(asmConfigVO.getRemark());
        asmConfig.setAsmDataId(asmConfigVO.getAsmDataId());
        asmConfigMapper.updateByPrimaryKeySelective(asmConfig);
        if (StringUtils.isNotEmpty(asmConfigVO.getRom())) {
            RedisRepository.hdel(RedisCons.ASM_MANUFACTURER_MODEL + asmConfigVO.getManufacturer(), asmConfigVO.getModel() + "@@" + asmConfigVO.getRom());
        } else {
            RedisRepository.hdel(RedisCons.ASM_API + asmConfigVO.getManufacturer(), asmConfigVO.getApi());
        }
    }

    /**
     * 删除下发权限配置
     *
     * @param id
     */
    @Override
    public void deleteAsmConfig(Integer id) {
        AsmConfig asmConfig = asmConfigMapper.selectByPrimaryKey(id);
        if (asmConfig != null) {
            asmConfigMapper.deleteByPrimaryKey(id);
            if (StringUtils.isNotEmpty(asmConfig.getRom())) {
                RedisRepository.hdel(RedisCons.ASM_MANUFACTURER_MODEL + asmConfig.getManufacturer(), asmConfig.getModel() + "@@" + asmConfig.getRom());
            } else {
                RedisRepository.hdel(RedisCons.ASM_API + asmConfig.getManufacturer(), asmConfig.getApi());
            }
        }
    }

    /**
     * 权限详情分页查询
     *
     * @param asmConfigDataVO
     * @return
     */
    @Override
    public PageResult<AsmConfigDataDTO> queryAsmConfigDataList(AsmConfigDataVO asmConfigDataVO) {
        return pageRepository.selectPaging(AsmConfigDataMapper.class, "selectListByPage", asmConfigDataVO);
    }

    /**
     * 新增权限详情
     *
     * @param asmConfigDataVO
     */
    @Override
    public void saveAsmConfigData(AsmConfigDataVO asmConfigDataVO) {
        AsmConfigData asmConfigData = new AsmConfigData();
        asmConfigData.setName(asmConfigDataVO.getName());
        asmConfigData.setData(asmConfigDataVO.getData());
        asmConfigData.setRemark(asmConfigDataVO.getRemark());
        int result = asmConfigDataMapper.insertSelective(asmConfigData);
    }

    /**
     * 修改权限详情
     *
     * @param asmConfigDataVO
     */
    @Override
    public void updateAsmConfigData(AsmConfigDataVO asmConfigDataVO) {
        AsmConfigData asmConfigData = new AsmConfigData();
        asmConfigData.setId(asmConfigDataVO.getId());
        asmConfigData.setName(asmConfigDataVO.getName());
        asmConfigData.setData(asmConfigDataVO.getData());
        asmConfigData.setRemark(asmConfigDataVO.getRemark());
        asmConfigDataMapper.updateByPrimaryKeySelective(asmConfigData);

        List<AsmConfig> list = asmConfigMapper.selectByAsmDataId(asmConfigDataVO.getId());
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(asmConfig -> {
            if (StringUtils.isNotEmpty(asmConfig.getRom())) {
                RedisRepository.hdel(RedisCons.ASM_MANUFACTURER_MODEL + asmConfig.getManufacturer(), asmConfig.getModel() + "@@" + asmConfig.getRom());
            } else {
                RedisRepository.hdel(RedisCons.ASM_API + asmConfig.getManufacturer(), asmConfig.getApi());
            }
        });
    }

    /**
     * 删除权限详情
     *
     * @param id
     */
    @Override
    public void deleteAsmConfigData(Integer id) {
        List<AsmConfig> list = asmConfigMapper.selectByAsmDataId(id);
        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ResultCodeEnum.NO_RESULT, "该权限已被使用，无法删除");
        }
        AsmConfigData asmConfig = asmConfigDataMapper.selectByPrimaryKey(id);
        if (asmConfig != null) {
            asmConfigDataMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 未适配权限上报分页查询
     *
     * @param asmConfigVO
     */
    @Override
    public PageResult<AsmConfigLogDTO> queryAsmConfigLogList(AsmConfigLogVO asmConfigVO) {
        return pageRepository.selectPaging(AsmConfigLogMapper.class, "selectListByPage", asmConfigVO);
    }


    @Override
    public void updateAsmConfigLog(AsmConfigLogVO asmConfigDataVO) {
        AsmConfigLog asmConfigData = new AsmConfigLog();
        asmConfigData.setId(asmConfigDataVO.getId());
        asmConfigData.setRemark(asmConfigDataVO.getRemark());
        asmConfigLogMapper.updateByPrimaryKeySelective(asmConfigData);
    }

    @Override
    public void deleteAsmConfigLog(Integer id) {
        AsmConfigLog asmConfigData = new AsmConfigLog();
        asmConfigData.setId(id);
        asmConfigData.setState(2);
        asmConfigLogMapper.updateByPrimaryKeySelective(asmConfigData);
    }
}
