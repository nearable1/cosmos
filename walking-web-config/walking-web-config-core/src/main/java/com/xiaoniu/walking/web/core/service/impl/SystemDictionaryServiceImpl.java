package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.walking.web.api.vo.SysDictVO;
import com.xiaoniu.walking.web.core.bo.SysDictBO;
import com.xiaoniu.walking.web.api.constants.RedisCons;
import com.xiaoniu.walking.web.core.mapper.ext.SysDictExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysDict;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.model.ext.SysDictExt;
import com.xiaoniu.walking.web.core.service.SystemDictionaryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统字典数据
 *
 * @author chenguohua
 * @date 2019/04/20 17：32
 */
@Service
public class SystemDictionaryServiceImpl implements SystemDictionaryService {
    @Autowired
    private SysDictExtMapper sysDictExtMapper;
    @Autowired
    private PageRepository pageRepository;



    @Override
    public PageResult<SysDictExt> query(SysDictBO sysdictBO) {
        return pageRepository.selectPaging(SysDictExtMapper.class, "querySysDictByPage", sysdictBO);
    }


    @Override
    public int insertParent(SysDictExt sysDictExt) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();

        // 创建日期
        sysDictExt.setCreateDate(new Date());
        // 创建人
        sysDictExt.setCreateBy(user.getUserId());
        return sysDictExtMapper.insertParent(sysDictExt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(SysDictExt sysDictExt) {
        //删除缓存数据
        RedisRepository.del(String.format(RedisCons.SYS_DICT_DISCONUT,sysDictExt.getType()));
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        // 修改日期
        sysDictExt.setUpdateDate(new Date());
        // 修改人
        sysDictExt.setUpdateBy(user.getUserId());
        return sysDictExtMapper.updateParent(sysDictExt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByParentId(SysDictExt sysDict) {
        //删除缓存数据
        RedisRepository.del(String.format(RedisCons.SYS_DICT_DISCONUT,sysDict.getType()));
        Long parentId = sysDict.getId();
        List<SysDictExt> sysDictNodes = sysDictExtMapper.getSysDictNodesById(parentId);
        if (Objects.nonNull(sysDictNodes)) {
            for (SysDictExt sysDictExt : sysDictNodes) {
                sysDictExtMapper.deleteById(sysDictExt.getId());
            }
        }
        return sysDictExtMapper.deleteById(parentId);
    }

    @Override
    public List<SysDictExt> getSysDictNodesById(Long parentId) {
        return sysDictExtMapper.getSysDictNodesById(parentId);
    }

    @Override
    public int saveChildren(SysDictExt sysDictExt) {
        //清除缓存数据
        SysDict sysDict = sysDictExtMapper.getSysDictById(sysDictExt.getParentId());
        if(Objects.nonNull(sysDict)){
            RedisRepository.del(String.format(RedisCons.SYS_DICT_DISCONUT,sysDict.getType()));
        }
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();

        // 创建日期
        sysDictExt.setCreateDate(new Date());
        // 创建人
        sysDictExt.setCreateBy(user.getUserId());
        return sysDictExtMapper.saveChildren(sysDictExt.getLabel(), sysDictExt.getDescription(), sysDictExt.getCreateBy(), sysDictExt.getCreateDate(), sysDictExt.getDelFlag(), sysDictExt.getSort(), sysDictExt.getParentId(), sysDictExt.getValue());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editChildrenList(SysDictExt sysDictExt) {
        //清除缓存数据
        SysDict sysDict = sysDictExtMapper.getSysDictById(sysDictExt.getParentId());
        if(Objects.nonNull(sysDict)){
           RedisRepository.del(String.format(RedisCons.SYS_DICT_DISCONUT,sysDict.getType()));
        }
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();

        // 修改日期
        sysDictExt.setUpdateDate(new Date());
        // 修改人
        sysDictExt.setUpdateBy(user.getUserId());

        //删除同一个父下字典列表所有缓存数据


        return sysDictExtMapper.editChildren(sysDictExt.getLabel(), sysDictExt.getDescription(), sysDictExt.getUpdateBy(), sysDictExt.getUpdateDate(), sysDictExt.getDelFlag(), sysDictExt.getSort(), sysDictExt.getValue(), sysDictExt.getId());
    }


    @Override
    public List<SysDictVO> getSysDictNodesByType(String type) {
        //查缓存
        Map<String, String> dicts = RedisRepository.hgetall(String.format(RedisCons.SYS_DICT_DISCONUT, type));
        if(dicts != null && dicts.size() > 0){
            List<SysDictVO> dictLists = new ArrayList<>();
            dicts.forEach( (k, v) -> dictLists.add(JSONUtils.parseObject(v, SysDictVO.class)));
            dictLists.sort(Comparator.comparing(SysDictVO::getSort));
            return dictLists;
        }else{
            List<SysDictVO> queryLists = sysDictExtMapper.getSysDictNodesByType(type);

            for (SysDictVO sysDict : queryLists){
                //存redis
                RedisRepository.hset(String.format(RedisCons.SYS_DICT_DISCONUT,type),sysDict.getLabel(),JSONUtils.toJSONString(sysDict),RedisCons.SYS_DICT_DISCONUT_EXPIRE);
            }

            return queryLists;
        }

    }

    /**
     * 根据标签值获取标签名
     *
     * @param value
     * @return
     */
    @Override
    public SysDictVO getSysDictByValue(String value) {
        return sysDictExtMapper.getSysDictByValue(value);
    }


    /**
     * 根据id查询对象
     *
     * @param id 编号
     * @return
     */
    @Override
    public SysDict getSysDictById(String id) {
        return sysDictExtMapper.getSysDictById(id);
    }

    @Override
    public SysDictVO getChildrenDetailByLabel(String goodsId) {
        return sysDictExtMapper.getChildrenDetailByLabel(goodsId);
    }

}
