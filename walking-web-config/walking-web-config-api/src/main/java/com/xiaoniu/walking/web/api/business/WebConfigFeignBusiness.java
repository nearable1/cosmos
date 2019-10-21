package com.xiaoniu.walking.web.api.business;


import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.api.exception.BusinessException;
import com.xiaoniu.walking.web.api.bo.FileUploadBO;
import com.xiaoniu.walking.web.api.vo.OssImgInfoVO;
import com.xiaoniu.walking.web.api.vo.SysDictVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 配置接口
 *
 * @author xiangxianjin
 * @date 2019-01-03 14:02
 */
@FeignClient(name = "walking-web-center", path = "/config")
public interface WebConfigFeignBusiness {

    /**
     * 获取
     *
     * @return
     * @throws BusinessException
     */
    @GetMapping("/account")
    ResultBean getAccountAmount() throws BusinessException;

    /**
     * 文件上传
     *
     * @param uploadBO 文件参数
     * @return ResultBean
     * @throws BusinessException 业务异常
     */
    @PostMapping(value = "/upload")
    ResultBean upload(FileUploadBO uploadBO) throws BusinessException;

    /**
     * 从OSS上获取图片信息
     *
     * @param imgName 图片名称
     * @return
     * @throws BusinessException
     */
    @GetMapping("/get-img-info")
    OssImgInfoVO getImgInfoFromOss(@RequestParam String imgName) throws BusinessException;

    /**
     * 获取商品列表
     *
     * @param type
     * @return
     * @throws BusinessException
     */
    @GetMapping("/goods-list")
    ResultBean getGoodsList(@RequestParam String type) throws BusinessException;

    /**
     * 通过type获取数据字典集合
     *
     * @return
     * @throws BusinessException
     */
    @GetMapping("/sys-dict-list")
    List<SysDictVO> getSysDictList(@RequestParam String type) throws BusinessException;

    /**
     * 根据数据值获取标签名
     *
     * @param value
     * @return
     */
    @GetMapping("/get-sys-by-value")
    SysDictVO getSysDictByValue(@RequestParam String value) throws BusinessException;



    /**
     * 根据大类和小类获取配置对象(credit)
     *
     * @param type 大类类型
     * @param label 小类天数
     * @return
     * @throws BusinessException
     */
    @GetMapping("/sys-credit")
    SysDictVO getCredit(@RequestParam String type, @RequestParam String label) throws BusinessException;


    /**
     * 获取商品明细
     * @param goodsId
     * @return
     * @throws BusinessException
     */
    @GetMapping("/goods-detail")
    SysDictVO getGoodsDetail(@RequestParam String goodsId) throws BusinessException;



    /**
     * 获取banner
     * @param bannerPosition
     * @return
     * @throws BusinessException
     */
    @GetMapping("/os-banner-list")
    ResultBean getOsBannerList(@RequestParam String bannerPosition) throws BusinessException;



    /**
     * 获取底部icon配置
     *
     * @return
     * @throws BusinessException
     */
    @GetMapping("/bottom-icon-list")
    ResultBean getBottomIconList() throws BusinessException;

    /**
     * 获取APP更新
     *
     * @param appName APP名称
     * @param appType APP类型
     * @param channel 渠道
     * @return
     * @throws BusinessException
     */
    @GetMapping("/get-app-version")
    ResultBean getAppVersion(@RequestParam Integer appName,
                             @RequestParam Integer appType,
                             @RequestParam String channel) throws BusinessException;




    /**
     * 获取数据字典
     *
     * @param type 类型
     * @return
     * @throws BusinessException
     */
    @GetMapping("/dict-list")
    ResultBean getDictList(@RequestParam String type) throws BusinessException;



    /**
     * 获取热搜词
     *
     * @return
     * @throws BusinessException
     */
    @GetMapping("/hot-search-list")
    ResultBean getHotSearchKeyWord() throws BusinessException;



}
