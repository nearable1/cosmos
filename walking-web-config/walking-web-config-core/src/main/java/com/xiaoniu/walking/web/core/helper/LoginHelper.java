package com.xiaoniu.walking.web.core.helper;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.core.util.CryptoUtils;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.walking.web.core.enums.WebConfigBusinessEnum;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: xiangxianjin
 * @date: 2019/4/4 18:33
 * @description: 辅助类
 */
@Log4j2
public class LoginHelper {
    /**
     * 提示去登录
     * @param response
     */
    public static void toLoginPage(HttpServletResponse response, WebConfigBusinessEnum webConfigBusinessEnum){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            ResultBean resultBean = ResultBean.format(String.valueOf(webConfigBusinessEnum.getValue()),webConfigBusinessEnum.getDesc());
            writer.write(JSONUtils.toJSONString(resultBean));
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            if(writer != null){
                writer.close();
            }
        }
    }

    /**
     * 验证用户密码
     * @param user
     * @param password
     * @return
     */
    public static boolean match(SysUser user, String password) {
        return user.getPassword().equals(CryptoUtils.md5(password));
    }

}
