package com.xiaoniu.walking.web.core.service;


import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 在后台加入信安接口，确保账号信息安全。
 *
 * @author chenguohua
 * @date 2019年5月24日21:12:10
 */
@Service
public interface OperationLoginService {

    /***
     * 运维登录校验
     * 1. 系统开关未配置，直接走登录
     * 2. 系统开关配置，未生效，直接走登录
     * 3. 系统开关配置，生效，调用运维接口，返回正常1000，直接走登录
     * 4. 系统开关配置，生效，调用运维接口，异常或返回不是1000，登录不成功
     * @return
     */
    public boolean loginCheck(String userName,String phoneNum,HttpServletRequest request);

}
