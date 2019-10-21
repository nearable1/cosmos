package com.xiaoniu.walking.web.core.service.impl;


import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.lending.commons.web.okhttp.OkHttpRequestFactory;
import com.xiaoniu.walking.web.core.service.OperationLoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 在后台加入信安接口，确保账号信息安全。
 *
 * @author chenguohua
 * @date 2019年5月24日21:12:10
 */
@Service
public class OperationLoginServiceImpl implements OperationLoginService {

    private static final Logger LOGGER = LogManager.getLogger(OperationLoginServiceImpl.class);
    /**
     * 运维登录校验
     */
    @Value("${user.check.in.login.url}")
    private String url;

    /**
     * 信安接口增加开关
     */
    @Value("${user.check.in.login.url.switch}")
    private String urlSwitch;

    private static final Integer USER_CHECK_IN_LOGIN = 1000;

    /**
     * 信安接口开关为开启状态
     */
    private static final String USER_CHECK_IN_URL_SWITCH_TRUE = "true";

    /***
     * 运维登录校验
     * 1. 系统开关未配置，直接走登录
     * 2. 系统开关配置，未生效，直接走登录
     * 3. 系统开关配置，生效，调用运维接口，返回正常1000，直接走登录
     * 4. 系统开关配置，生效，调用运维接口，异常或返回不是1000，登录不成功
     * @return
     */
    @Override
    public boolean loginCheck(String userName,String phoneNum,HttpServletRequest request) {
        //获取IP地址
        String ipAddress = request.getRemoteAddr();

        if(USER_CHECK_IN_URL_SWITCH_TRUE.equals(urlSwitch) ){
            try{
                Map<String, Object> param = new HashMap<>();
                param.put("X-REAL-IP", ipAddress);
                param.put("phone", phoneNum);
                param.put("namepinyin", userName);
                param.put("project", 404);
                String result = OkHttpRequestFactory.getInstance().doPost(url, JSONUtils.toJSONString(param), String.class);

                Map<String, Object> jsonMap = JSONUtils.parseObject(result, Map.class);
                if (USER_CHECK_IN_LOGIN.equals(jsonMap.get("Code")) ) {
                    return true;
                }else{
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("[OperationLoginService],异常信息---->{}", e.getMessage(),e);
            }
        }
        return true;
    }




}
