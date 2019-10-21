package com.xiaoniu.walking.web.core.service.impl;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.walking.web.core.mapper.ext.WhiteListExtMapper;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.model.auto.WhiteList;
import com.xiaoniu.walking.web.core.model.ext.WhiteListExt;
import com.xiaoniu.walking.web.core.service.WhiteListService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 白名单管理
 *
 * @author liuyinkai
 * @date 20190919
 */
@Service
public class WhiteListServiceImpl implements WhiteListService {

    @Autowired
    private WhiteListExtMapper whiteListExtMapper;


    @Autowired
    private PageRepository pageRepository;

    /**
     * 查询列表
     *
     * @param whiteList
     * @return
     */
    @Override
    public PageResult<WhiteListExt> findByPage(WhiteList whiteList) {
        PageResult<WhiteListExt> whiteListExtPageResult = pageRepository.selectPaging(WhiteListExtMapper.class, "finddAll", whiteList);
        return whiteListExtPageResult;
    }

    /**
     * 新增
     *
     * @param whiteList
     * @return
     */
    @Override
    public int insertWhiteList(WhiteList whiteList) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        whiteList.setCreateMan(user.getUserId());
        whiteList.setModifyMan(user.getUserId());
        return whiteListExtMapper.insertWhiteList(whiteList);
    }

    /**
     * 更新
     *
     * @param whiteList
     * @return
     */
    @Override
    public int updateWhiteList(WhiteList whiteList) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        whiteList.setModifyMan(user.getUserId());
        whiteList.setModifyTime(new Date());
        return whiteListExtMapper.updateWhiteList(whiteList);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        return whiteListExtMapper.deleteById(id);
    }


}
