package com.xiaoniu.walking.web.core.business;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.architecture.lending.commons.api.AppNameEnum;
import com.xiaoniu.architecture.lending.commons.web.helper.HeaderHelper;
import com.xiaoniu.architecture.spring.boot.autoconfigure.mongodb.support.MongodbRepository;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.walking.web.api.bo.FileUploadBO;
import com.xiaoniu.walking.web.api.business.WebConfigFeignBusiness;
import com.xiaoniu.walking.web.api.vo.*;
import com.xiaoniu.walking.web.core.bo.QueryAppVersionBO;
import com.xiaoniu.walking.web.api.constants.RedisCons;
import com.xiaoniu.walking.web.core.enums.KeyWordEnum;
import com.xiaoniu.walking.web.core.model.auto.HotSearchKeyword;
import com.xiaoniu.walking.web.core.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: xiangxianjin
 * @date: 2019/3/27 17:42
 * @description:
 */
@RestController
@RequestMapping("/config")
@Log4j2
public class WebConfigFeignBusinessImpl implements WebConfigFeignBusiness {

    @Autowired
    private SystemDictionaryService systemDictionaryService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private OsBannerService osBannerService;
    @Autowired
    private BottomIconService bottomIconService;
    @Autowired
    private AppVersionService appVersionService;

    /**
     * 获取配置
     *
     * @return
     * @throws BusinessException
     */
    @Override
    @GetMapping("/account")
    public ResultBean getAccountAmount() throws BusinessException {
        return ResultBean.format("10001", "配置");
    }

    /**
     * 文件上传
     *
     * @param uploadBO 文件参数
     * @return ResultBean
     * @throws BusinessException 业务异常
     */
    @Override
    @PostMapping(value = "/upload")
    public ResultBean upload(FileUploadBO uploadBO) throws BusinessException {
        return fileUploadService.uploadFiles(uploadBO);
    }


    /**
     * 从OSS上获取图片信息
     *
     * @param imgName 图片名称
     * @return
     * @throws BusinessException
     */
    @Override
    @GetMapping("/get-img-info")
    public OssImgInfoVO getImgInfoFromOss(@RequestParam String imgName) throws BusinessException {
        return fileUploadService.getImgInfoFromOss(imgName);
    }

    /**
     * 获取商品列表
     *
     * @return
     * @throws BusinessException
     */
    @Override
    @RequestMapping("/goods-list")
    public ResultBean getGoodsList(@RequestParam String type) throws BusinessException{
        List<SysDictVO> sysDictExts = systemDictionaryService.getSysDictNodesByType(type);
        return ResultBean.format(sysDictExts);
    }

    /**
     * 通过type获取数据字典集合
     *
     * @return
     * @throws BusinessException
     */
    @Override
    @RequestMapping("/sys-dict-list")
    public List<SysDictVO> getSysDictList(@RequestParam String type) throws BusinessException{
        List<SysDictVO> sysDictExts = systemDictionaryService.getSysDictNodesByType(type);
        return sysDictExts;
    }

    /***
     * 根据标签值获取标签名
     * @param value
     * @return
     * @throws BusinessException
     */
    @Override
    @GetMapping("/get-sys-by-value")
    public SysDictVO getSysDictByValue(String value) throws BusinessException {
        return systemDictionaryService.getSysDictByValue(value);
    }


    /**
     * 根据大类和小类获取配置对象(credit)
     *
     * @param type 大类类型
     * @param label 小类 标签
     * @return
     * @throws BusinessException
     */
    @Override
    @GetMapping("/sys-credit")
    public SysDictVO getCredit(String type, String label) throws BusinessException {
        SysDictVO sysDictVo = null;
        //从redis中查询
        String jsonSysDict = RedisRepository.hget(String.format(RedisCons.SYS_DICT_DISCONUT, type),label);
        if(StringUtils.isNoneEmpty(jsonSysDict)){
            sysDictVo = JSONUtils.parseObject(jsonSysDict, SysDictVO.class);
        }
        List<SysDictVO> sysDictList = systemDictionaryService.getSysDictNodesByType(type);
        if(Objects.nonNull(sysDictList)){
            for (SysDictVO sysDict : sysDictList){
                //存redis
                RedisRepository.hset(String.format(RedisCons.SYS_DICT_DISCONUT,type),sysDict.getLabel(),JSONUtils.toJSONString(sysDict),RedisCons.SYS_DICT_DISCONUT_EXPIRE);
                if(StringUtils.isNoneEmpty(label) && label.equals(sysDict.getLabel())){
                    sysDictVo = sysDict;
                }
            }
        }
        return sysDictVo;
    }



    /**
     * 获取商品明细
     * @param goodsId
     * @return
     * @throws BusinessException
     */
    @Override
    @GetMapping("/goods-detail")
    public SysDictVO getGoodsDetail(@RequestParam String goodsId) throws BusinessException {
        return systemDictionaryService.getChildrenDetailByLabel(goodsId);
    }


    /**
     * 获取banner
     * @param bannerPosition
     * @return
     * @throws BusinessException
     */
    @Override
    @GetMapping("/os-banner-list")
    public ResultBean getOsBannerList(String bannerPosition) throws BusinessException {
        List<OsBannerVO> osBannerList = osBannerService.getOsBannerList(bannerPosition);
        log.info("返回的banner列表为：{}",osBannerList);
        return ResultBean.format(osBannerList);
    }

    /**
     * 获取底部icon配置
     * @param
     * @return
     * @throws BusinessException
     */
    @Override
    @GetMapping("/bottom-icon-list")
    public ResultBean getBottomIconList() throws BusinessException {
        List<BottomIconVO> bottomIconList = bottomIconService.getBottomIconList();
        return ResultBean.format(bottomIconList);
    }

    /**
     * 获取APP更新
     *
     * @param appType APP类型
     * @param channel 渠道
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultBean getAppVersion(@RequestParam Integer appName, @RequestParam Integer appType, @RequestParam String channel) throws BusinessException {
        log.info("======【获取APP更新】======in.data：appName={}, appType={}, channel={}", appName, appType, channel);
        QueryAppVersionBO queryAppVersionBO = new QueryAppVersionBO();
        queryAppVersionBO.setAppName(appName);
        queryAppVersionBO.setAppType(appType);
        queryAppVersionBO.setChannel(StringUtils.isNotEmpty(channel) ? channel : "");
        AppVersionVO appVersion = appVersionService.getAppVersion(queryAppVersionBO);
        return ResultBean.format(appVersion);
    }




    /**
     * 获取数据字典
     *
     * @param type 类型
     * @return
     * @throws BusinessException
     */
    @Override
    @GetMapping("/dict-list")
    public ResultBean getDictList(String type) throws BusinessException {
        List<SysDictVO> sysDictExts = systemDictionaryService.getSysDictNodesByType(type);
        return ResultBean.format(sysDictExts);
    }



    /**
     * 获取热搜词
     *
     * @return
     * @throws BusinessException
     */
    @Override
    @GetMapping("/hot-search-list")
    public ResultBean getHotSearchKeyWord() throws BusinessException {
        Integer appName = HeaderHelper.getAppName();
        AppNameEnum appNameEnum = AppNameEnum.resolve(appName);
        log.info("获取appName为：{}，名称为：{}", appName, (appNameEnum != null) ? appNameEnum.getDesc(): null);
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("appName").is(appName);
        criteria.and("state").is(KeyWordEnum.VALID.getValue());
        List<Sort.Order> orders = new ArrayList<>();
        //根据orders排序
        orders.add(new Sort.Order(Sort.Direction.ASC, "orders"));
        query.addCriteria(criteria).with(Sort.by(orders));
        List<HotSearchKeyword> hotSearchKeyword = MongodbRepository.findListByClazz(query, HotSearchKeyword.class);
        List<HotSearchKeywordVO> resultList = new ArrayList<>();
        for (HotSearchKeyword hotSearch : hotSearchKeyword) {
            HotSearchKeywordVO hotSearchVO = new HotSearchKeywordVO();
            BeanUtils.copyProperties(hotSearch, hotSearchVO);
            resultList.add(hotSearchVO);
        }
        return ResultBean.format(resultList);
    }

}
