package com.xiaoniu.call.customer.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.customer.core.cons.RedisCons;
import com.xiaoniu.call.customer.core.dto.AsmConfigDTO;
import com.xiaoniu.call.customer.core.dto.DicDTO;
import com.xiaoniu.call.customer.core.entity.AsmConfigLog;
import com.xiaoniu.call.customer.core.entity.DicOpt;
import com.xiaoniu.call.customer.core.mapper.AsmConfigDataMapper;
import com.xiaoniu.call.customer.core.mapper.AsmConfigLogMapper;
import com.xiaoniu.call.customer.core.mapper.AsmConfigMapper;
import com.xiaoniu.call.customer.core.mapper.DicOptMapper;
import com.xiaoniu.call.customer.core.service.DicService;
import com.xiaoniu.call.customer.core.vo.AsmVO;
import com.xiaoniu.call.customer.core.vo.DicVO;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Log4j2
public class DicServiceImpl implements DicService {

    @Autowired
    private DicOptMapper dicOptMapper;

    @Autowired
    private AsmConfigMapper asmConfigMapper;

    @Autowired
    private AsmConfigLogMapper asmConfigLogMapper;

    @Autowired
    private AsmConfigDataMapper asmConfigDataMapper;

    @Override
    public Map<String, String> query(DicVO param) {
        // 小类是否为空
        List<DicDTO> dicOpts = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isEmpty(param.getCode())) {
            // 按大类查字典
            map = RedisRepository.hgetall(param.getDicCode());
            if (map.isEmpty()) {
                // 按大类加小类查字典
                List<DicOpt> opts = dicOptMapper.selectByDicCode(param.getDicCode(), param.getCode());
                if (!CollectionUtils.isEmpty(opts)) {
                    for (DicOpt opt : opts) {
                        RedisRepository.hset(opt.getDicCode(), opt.getCode(), opt.getValue());
                    }
                    map = RedisRepository.hgetall(param.getDicCode());
                }
            }
        } else {
            // 大类加小类加字典
            String value = RedisRepository.hget(param.getDicCode(), param.getCode());
            if (StringUtils.isNotEmpty(value)) {
                map.put(param.getCode(), value);
            } else {
                List<DicOpt> opts = dicOptMapper.selectByDicCode(param.getDicCode(), param.getCode());
                if (!CollectionUtils.isEmpty(opts)) {
                    for (DicOpt opt : opts) {
                        RedisRepository.hset(opt.getDicCode(), opt.getCode(), opt.getValue());
                        map.put(opt.getCode(), opt.getValue());
                    }
                }
            }
        }
        return map;
    }

    /**
     * 客户端权限路径配置
     * 先按rom查，rom查不到按api查，api查不到按model查
     *
     * @param param
     * @return
     */
    @Override
    public JSONArray asm(AsmVO param) {
        if (StringUtils.isEmpty(param.getRom())) {
            return asmByApi(param);
        }
        String value = RedisRepository.hget(RedisCons.ASM_MANUFACTURER_MODEL + param.getManufacturer(), param.getModel() + "@@" + param.getRom());
        if (StringUtils.isEmpty(value)) {
            List<AsmConfigDTO> opts = asmConfigMapper.selectByParam(param.getRom(), null, param.getManufacturer(), param.getModel());
            if (CollectionUtils.isEmpty(opts)) {
                // 插入匹配失败上报记录表
                reportAsmLog(param, 2);
                return asmByLast(param);
            }
            AsmConfigDTO opt = opts.get(0);
            RedisRepository.hset(RedisCons.ASM_MANUFACTURER_MODEL + param.getManufacturer(), param.getModel() + "@@" + param.getRom(), opt.getData());
            return JSON.parseArray(opt.getData());
        }
        return JSON.parseArray(value);
    }


    /**
     * 查最新配置
     *
     * @param param
     * @return
     */
    private JSONArray asmByLast(AsmVO param) {
        String value = RedisRepository.hget(RedisCons.PHONE_AUTH, param.getManufacturer());
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return JSON.parseArray(value);
    }


    private JSONArray asmByApi(AsmVO param) {
        String value = RedisRepository.hget(RedisCons.ASM_API + param.getManufacturer(), param.getApi());
        if (StringUtils.isEmpty(value)) {
            List<AsmConfigDTO> opts = asmConfigMapper.selectByParam(null, param.getApi(), param.getManufacturer(), param.getModel());
            if (CollectionUtils.isEmpty(opts)) {
                // 插入匹配失败上报记录表
                reportAsmLog(param, 2);
                return null;
            }
            AsmConfigDTO opt = opts.get(0);
            RedisRepository.hset(RedisCons.ASM_API + param.getManufacturer(), param.getApi(), opt.getData());
            return JSON.parseArray(opt.getData());
        }
        return JSON.parseArray(value);
    }


    /**
     * 上报匹配失败记录
     */
    @Override
    public void reportAsmLog(AsmVO param, Integer reportType) {
        try {
            AsmConfigLog asmConfigLog = new AsmConfigLog();
            asmConfigLog.setApi(param.getApi());
            asmConfigLog.setManufacturer(param.getManufacturer());
            asmConfigLog.setModel(param.getModel());
            asmConfigLog.setRom(param.getRom());
            asmConfigLog.setReportType(reportType);
            asmConfigLogMapper.insertSelective(asmConfigLog);
        } catch (Exception e) {
            log.error("上报权限匹配失败记录发生异常, param:{}", param);
        }
    }
}
