package com.xiaoniu.walking.web.core.service;


import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.bo.WkUserBO;
import com.xiaoniu.walking.web.core.vo.WkUserVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 白名单管理
 *
 * @author liuyinkai
 * @date 20190919
 */
public interface AccountService {

        /**
         * 查询
         *
         * @param wkUserBO
         * @return
         */
        PageResult<WkUserVO> getAccountList(@RequestBody WkUserBO wkUserBO);




}
