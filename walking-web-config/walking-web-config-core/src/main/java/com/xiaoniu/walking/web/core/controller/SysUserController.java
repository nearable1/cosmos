
package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.core.util.JSONUtils;
import com.xiaoniu.architecture.commons.core.util.StringUtils;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.LoginUser;
import com.xiaoniu.walking.web.core.bo.UserInfoBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.constants.CommonConstants;
import com.xiaoniu.walking.web.api.constants.RedisCons;
import com.xiaoniu.walking.web.core.constants.ShiroCons;
import com.xiaoniu.walking.web.core.enums.CodeTypeEnum;
import com.xiaoniu.walking.web.core.enums.SysUserEnum;
import com.xiaoniu.walking.web.core.enums.WebConfigBusinessEnum;
import com.xiaoniu.walking.web.core.helper.LoginHelper;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.service.OperationLoginService;
import com.xiaoniu.walking.web.core.service.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author: xiangxianjin
 * @date: 2019/3/29 17:37
 * @description:
 */

@RestController
@RequestMapping("/walkingwebapi/portal")
@Log4j2
public class SysUserController {


    /**
     * 验证码失效时间(10分钟)
     */
    @Value("${verifycode.smscode.expire:600}")
    private Integer smsCodeExpire;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private OperationLoginService operationLoginService;

//    @Autowired
//    private SmsSendBusiness smsSendBusiness;

    /**
     * 登录验证码开关
     */

    @Value("${user.check.in.login.code.is.show.switch:false}")
    private String loginCodeIsShowSwitch;

    /**
     * 登录接口
     */

    @PostMapping(value = "/login")
    @ActionLogger(moduleName = "平台入口", actionType = ActionLoggerCons.LOGIN)
    public ResultBean login(LoginUser loginUser, HttpServletRequest request) throws IOException {
        String userId = loginUser.getUsername();
        String password = loginUser.getPassword();
        String code = loginUser.getCode();

        // 用户信息
        SysUser user = sysUserService.findByUserId(userId);

        // 账号不存在、密码错误
        if (user == null || !LoginHelper.match(user, password)) {
            return ResultBean.format(WebConfigBusinessEnum.LOGIN_INVALID.getValue(), WebConfigBusinessEnum.LOGIN_INVALID.getDesc());
        }

        // 验证码开关是否开启
        if (CommonConstants.LOGIN_CODE_IS_SHOW_SWITCH.equals(loginCodeIsShowSwitch)) {
            String redisCode = RedisRepository.get(RedisCons.USER_LOGIN_CODE + user.getPhoneNum() + ":" + CodeTypeEnum.WEB_CONFIG_LOGIN_CODE.getValue());
            if (!code.equals(redisCode)) {
                return ResultBean.format(WebConfigBusinessEnum.LOGIN_CODE_ERROR.getValue(), WebConfigBusinessEnum.LOGIN_CODE_ERROR.getDesc());
            }
        }

        // 增加信安接口，
        if (!operationLoginService.loginCheck(userId, user.getPhoneNum(), request)) {
            return ResultBean.format(WebConfigBusinessEnum.LOGIN_ERROR.getValue(), WebConfigBusinessEnum.LOGIN_ERROR.getDesc());
        }


        // 账号锁定
        if (SysUserEnum.LOCK.matches(user.getStatus())) {
            return ResultBean.format(WebConfigBusinessEnum.LOGIN_LOCK.getValue(), WebConfigBusinessEnum.LOGIN_LOCK.getDesc());
        }

        // 生成token
        String token = StringUtils.generateUUID();
        //保存用戶信息到REDIS
        RedisRepository.set(String.format(RedisCons.SYS_USER_TOKEN, token), JSONUtils.toJSONString(user), RedisCons.SYS_USER_TOKEN_EXPIRE);
        return ResultBean.format(token);
    }


    /**
     * 退出接口
     */

    @PostMapping("/logout")
    @ActionLogger(moduleName = "平台入口", actionType = ActionLoggerCons.LOGIN_OUT)
    public ResultBean logout(String token) {
        SecurityUtils.getSubject().logout();
        RedisRepository.del(String.format(RedisCons.SYS_USER_TOKEN, token));
        return ResultBean.format();
    }


    @GetMapping("/info")
    @ActionLogger(moduleName = "平台入口", actionType = ActionLoggerCons.QUERY)
    public ResultBean info() {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        // 用户权限列表，根据用户拥有的权限标识与如 @permission标注的接口对比，决定是否可以调用接口
        Set<String> rolesSet = sysUserService.findRoles(user.getUserId());
        Set<String> permsSet = null;
        if (rolesSet.contains(ShiroCons.ADMIN) || rolesSet.isEmpty()) {
            permsSet = rolesSet;
        } else {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("roles", rolesSet);
            permsSet = sysUserService.findPermissions(parameters);
        }

        UserInfoBO userInfo = new UserInfoBO();
        userInfo.setUserId(user.getUserId());
        userInfo.setAvatar(user.getHeadImageUrl());
        userInfo.setName(user.getNickName());
        userInfo.setRoles(permsSet);
        userInfo.setStatus(userInfo.getStatus());

        return ResultBean.format(userInfo);
    }


    /**
     * 查询验证码是否显示
     *
     * @return
     */
    @GetMapping("/query-code-is-show")
    @ActionLogger(moduleName = "查询验证码显示", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryCodeIsShow() {
        return ResultBean.format(loginCodeIsShowSwitch);
    }

}

/**
 * 发送验证码
 *
 * @return 后台登录发送验证码
 * @param phoneNum 手机号
 * @param type 验证码类型
 * @return
 *//*

    @PostMapping("/get_valid_code")
    @ActionLogger(moduleName = "发送验证码", actionType = ActionLoggerCons.QUERY)
    public ResultBean getValidCode(@RequestParam(required = false) String username){

        String user = RedisRepository.get(RedisCons.WEB_CONFIG_LOGIN_USER_NULL + username);
        // 用户在redis当中不存在
        if(StringUtils.isBlank(user)){

            SysUser sysUser = sysUserService.findByUserId(username);
            if (Objects.nonNull(sysUser)){
                return getCode(sysUser.getPhoneNum(),CodeTypeEnum.WEB_CONFIG_LOGIN_CODE.getValue());
            }else {
                // 如果用户不存在，把用户登录账号添加到redis当中
                RedisRepository.set(RedisCons.WEB_CONFIG_LOGIN_USER_NULL + username , WebConfigBusinessEnum.LOGIN_USER_IS_NULL.getDesc(), 60);
            }
        }

        return ResultBean.format(WebConfigBusinessEnum.LOGIN_USER_IS_NULL.getValue(),WebConfigBusinessEnum.LOGIN_USER_IS_NULL.getDesc());
    }

    */
/**
 * 后台登录发送验证码
 * @param phoneNum 手机号
 * @param type 验证码类型
 * @return
 *//*

    public ResultBean getCode(String phoneNum, Integer type) {
        log.info("【数据营销管理后台验证码】=======>验证码获取开始，phone={}，codeType={}", phoneNum, type);
        SmsSendBO ssb = new SmsSendBO();
        ssb.setPhone(phoneNum);
        ssb.setType(type);
        if (type.equals(CodeTypeEnum.WEB_CONFIG_LOGIN_CODE.getValue())) {
            // 生成验证码
            int random = (int) ((Math.random() * 9 + 1) * 1000);
            String randomCode = String.valueOf(random);
            Map<String, Object> replaceParam = new HashMap<>(5);
            replaceParam.put("code", randomCode);
            ssb.setReplaceParam(replaceParam);
            SmsStatusEnum smsStatusEnum = smsSendBusiness.sendSmsSync(ssb);
            // 如果短信发送成功，设置有效时间
            if (SmsStatusEnum.SEND_SUCCESS.getValue().equals(smsStatusEnum.getValue())) {
                RedisRepository.set(RedisCons.USER_LOGIN_CODE + phoneNum + ":" + type, randomCode, smsCodeExpire);
                log.info("【数据营销管理后台发送验证码】<======手机号:" + phoneNum + ", 手机验证码:" + randomCode);
                log.info("将验证码存入redis中的key值为:{},失效时间为:{}", RedisCons.USER_LOGIN_CODE + phoneNum + type , smsCodeExpire);
                return ResultBean.format(VerifyCodeResultEnum.SEND_SUCCESS.getValue(),VerifyCodeResultEnum.SEND_SUCCESS.getDesc());
            }
            log.error("【数据营销管理后台发送验证码】短信发送失败, 手机号:" + phoneNum + ", 手机验证码:" + randomCode);
        }
        return ResultBean.format(VerifyCodeResultEnum.SEND_FAILURE.getValue(),VerifyCodeResultEnum.SEND_FAILURE.getDesc());
    }


}
*/
