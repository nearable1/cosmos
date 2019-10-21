package com.xiaoniu.walking.web.core.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoniu.architecture.commons.core.util.DateUtils;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.architecture.lending.commons.web.helper.HeaderHelper;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.page.core.repository.PageRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.walking.web.api.vo.OsBannerVO;
import com.xiaoniu.walking.web.core.bo.OsBannerBO;
import com.xiaoniu.walking.web.api.constants.RedisCons;
import com.xiaoniu.walking.web.core.enums.OsBannerStateEnum;
import com.xiaoniu.walking.web.core.mapper.auto.OsBannerMapper;
import com.xiaoniu.walking.web.core.mapper.ext.OsBannerExtMapper;
import com.xiaoniu.walking.web.core.model.auto.OsBanner;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.model.ext.OsBannerExt;
import com.xiaoniu.walking.web.core.service.OsBannerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 运营banner配置管理
 *
 * @author chenguohua
 * @date 2019年5月22日12:10:25
 */
@Service
public class OsBannerServiceImpl implements OsBannerService {

    @Autowired
    private OsBannerExtMapper bannerExtMapper;
    @Autowired
    private OsBannerMapper osBannerMapper;
    @Autowired
    private PageRepository pageRepository;


    /**
     * banner管理
     *
     * @param OsBannerBO
     * @return
     */
    @Override
    public PageResult<OsBannerExt> findOsBannerByPage(OsBannerBO OsBannerBO) {
        PageResult<OsBannerExt> selectOsBannerByPage = pageRepository.selectPaging(OsBannerExtMapper.class, "selectOsBannerByPage", OsBannerBO);
        for (OsBannerExt osBannerExt : selectOsBannerByPage.getRows()) {
            osBannerExt.setStartTimeStr(Objects.isNull(osBannerExt.getStartTime()) ? "" : DateUtils.formatDateTime(osBannerExt.getStartTime()));
            osBannerExt.setEndTimeStr(Objects.isNull(osBannerExt.getEndTime()) ? "" : DateUtils.formatDateTime(osBannerExt.getEndTime()));
            osBannerExt.setCreateTimeStr(Objects.isNull(osBannerExt.getCreateTime()) ? "" : DateUtils.formatDateTime(osBannerExt.getCreateTime()));
            osBannerExt.setModifyTimeStr(Objects.isNull(osBannerExt.getModifyTime()) ? "" : DateUtils.formatDateTime(osBannerExt.getModifyTime()));
        }
        return selectOsBannerByPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertOsBanner(OsBannerExt osBannerExt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            osBannerExt.setStartTime(Objects.isNull(osBannerExt.getStartTimeStr()) ? null : format.parse(osBannerExt.getStartTimeStr().replace("Z", " UTC")));
            osBannerExt.setEndTime(Objects.isNull(osBannerExt.getEndTimeStr()) ? null : format.parse(osBannerExt.getEndTimeStr().replace("Z", " UTC")));
        } catch (Exception exception) {
            exception.getMessage();
        }
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        osBannerExt.setCreateMan(user.getUserId());
        osBannerExt.setCreateTime(new Date());
        osBannerExt.setModifyTime(new Date());
        osBannerExt.setModifyMan(user.getUserId());
        RedisRepository.del(String.format(RedisCons.SYS_OS_BANNER, osBannerExt.getBannerPosition()));
        return bannerExtMapper.insertOsBanner(osBannerExt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOsBanner(OsBannerExt osBannerExt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        try {
            osBannerExt.setStartTime(Objects.isNull(osBannerExt.getStartTimeStr()) ? null : format.parse(osBannerExt.getStartTimeStr().replace("Z", " UTC")));
         } catch (Exception exception) {
            exception.getMessage();
        }
        try {
              osBannerExt.setEndTime(Objects.isNull(osBannerExt.getEndTimeStr()) ? null : format.parse(osBannerExt.getEndTimeStr().replace("Z", " UTC")));
        } catch (Exception exception) {
            exception.getMessage();
        }

        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        osBannerExt.setModifyTime(new Date());
        osBannerExt.setModifyMan(user.getUserId());
        //维护banner缓存
        OsBanner osBanner = new OsBanner();
        osBanner.setBannerId(osBannerExt.getBannerId());
        OsBanner banner = osBannerMapper.selectByPrimaryKey(osBanner);
        if(Objects.nonNull(banner) && !Objects.equals(banner.getBannerPosition(), osBannerExt.getBannerPosition())){
            RedisRepository.del(String.format(RedisCons.SYS_OS_BANNER, banner.getBannerPosition()));
        }
            RedisRepository.del(String.format(RedisCons.SYS_OS_BANNER, osBannerExt.getBannerPosition()));
        return bannerExtMapper.updateOsBanner(osBannerExt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOsBannerByBannerId(Integer bannerId) {
        OsBanner osBanner = osBannerMapper.selectByPrimaryKey(bannerId);
        RedisRepository.del(String.format(RedisCons.SYS_OS_BANNER, osBanner.getBannerPosition()));
        return bannerExtMapper.deleteOsBanner(bannerId);
    }


    /**
     * 获取banner列表
     * @param bannerPosition
     * @return
     */
    @Override
    public List<OsBannerVO> getOsBannerList(String bannerPosition) {
        Date date = new Date();
        //从redis中获取
        String jsonArray = RedisRepository.get(String.format(RedisCons.SYS_OS_BANNER, bannerPosition));
        if(StringUtils.isNoneEmpty(jsonArray)){
            List<OsBannerVO> bannerList = JSONUtils.parseObject(jsonArray, new TypeReference<List<OsBannerVO>>() {});
            List<OsBannerVO> osBannerVOList = bannerList.stream().
                    filter(e -> date.compareTo(e.getStartTime()) >= 0 && date.compareTo(e.getEndTime()) <= 0).
                    collect(Collectors.toList());
            //新增，编辑，删除banner时，会删除缓存
            if(!osBannerVOList.isEmpty()){
                return osBannerVOList;
            }
        }
        OsBannerBO osBannerBO = new OsBannerBO();
        osBannerBO.setBannerPosition(bannerPosition);
        osBannerBO.setState(OsBannerStateEnum.OPEN.getValue());
        osBannerBO.setAppName(HeaderHelper.getAppName());
        List<OsBannerVO> osBannerList = bannerExtMapper.getOsBannerList(osBannerBO);
        if(!osBannerList.isEmpty()){
            //存redis,过期时间1天
            RedisRepository.set(String.format(RedisCons.SYS_OS_BANNER, bannerPosition), JSONUtils.toJSONString(osBannerList),RedisCons.SYS_OS_BANNER_EXPIRE);
        }
        return osBannerList;
    }


}
