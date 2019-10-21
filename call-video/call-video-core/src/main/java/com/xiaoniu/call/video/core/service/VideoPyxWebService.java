package com.xiaoniu.call.video.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.video.api.dto.VideoPyxWebDTO;
import com.xiaoniu.call.video.api.vo.VideoPyxPageVO;

/**
 * 视频管理端业务
 *
 * @author wuwen
 * @date 2019-07-15 16:36
 */
public interface VideoPyxWebService {

    /**
     * 分页查询视频
     *
     * @return
     */
    PageResult<VideoPyxWebDTO> pageList(VideoPyxPageVO videoPageVO);

    /**
     * 修改视频数据
     */
    void update(VideoPyxWebDTO videoWebDTO);

    /**
     * 批量上线
     *
     * @param ids
     */
    void batchOnline(String[] ids);

    /**
     * 批量上线
     *
     * @param ids
     */
    void batchOffline(String[] ids);

    /**
     * 删除视频数据
     */
    void delete(String videoNumber);


    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDelete(String[] ids);


    void addVideoPool(String videoNumber);

}
