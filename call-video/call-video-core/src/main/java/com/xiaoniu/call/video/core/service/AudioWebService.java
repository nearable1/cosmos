package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.bo.AudioChangeWeightBO;
import com.xiaoniu.call.video.api.bo.AudioOnlineOfflineBO;
import com.xiaoniu.call.video.api.bo.AudioPageBO;
import com.xiaoniu.call.video.api.vo.AudioVO;

/**
 * 音频管理端业务
 *
 * @author liuyinkai
 * @date 2019-08-02
 */
public interface AudioWebService {

    /**
     * 分页查询视频
     *
     * @return
     */
    PageResult<AudioVO> pageList(AudioPageBO audioPageBO);

    /**
     * 修改视频数据
     */
    void update(AudioVO audioVO);

    /**
     * 批量上线
     *
     * @param ids
     */
    void audioBatchOnline(String[] ids);

    /**
     * 批量下线
     *
     * @param ids
     */
    void audioBatchOffline(String[] ids);

    /**
     * 批量删除
     *
     * @param ids
     */
    void audioBatchDelete(String[] ids);

    /**
     * 批量过审
     *
     * @param
     */
    void audioBatchCheckPass(String[] ids);

    /**
     * 批量过审失败
     *
     * @param
     */
    void audioBatchCheckNotPass(String[] ids);

    /**
     * 权重更新
     *
     * @param
     */
    void audioChangeWeight(AudioChangeWeightBO audioChangeWeightBO);

    /**
     * 单行上下线
     *
     * @param
     */
    void singleOnlineOffline(AudioOnlineOfflineBO audioOnlineOfflineBO);
}
