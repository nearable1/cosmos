package com.xiaoniu.walking.web.core.controller;

import com.xiaoniu.architecture.commons.api.ResultBean;
import com.xiaoniu.architecture.commons.api.ResultCodeEnum;
import com.xiaoniu.architecture.commons.core.util.CryptoUtils;
import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.walking.web.core.aspect.ActionLogger;
import com.xiaoniu.walking.web.core.bo.SysUserPasswordBO;
import com.xiaoniu.walking.web.core.bo.UserInfoBO;
import com.xiaoniu.walking.web.core.constants.ActionLoggerCons;
import com.xiaoniu.walking.web.core.constants.CommonConstants;
import com.xiaoniu.walking.web.core.enums.PasswordVerifyEnum;
import com.xiaoniu.walking.web.core.enums.SysUserEnum;
import com.xiaoniu.walking.web.core.enums.WebConfigBusinessEnum;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.service.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author: lihoujing
 * @date: 2019/4/11 15:26
 * @description: 用户信息控制器
 */
@RestController
@RequestMapping("/walkingwebapi/user")
@Log4j2
public class UserInfoController {

    @Autowired
    private SysUserService sysUserService;


    @GetMapping("/list")
    @ActionLogger(moduleName = "用户信息", actionType = ActionLoggerCons.QUERY)
    public ResultBean queryList(
            @RequestParam(required = false) Integer pageIndex,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String userId){
        UserInfoBO userInfoBO = new UserInfoBO();
        if (pageIndex != null) {
            userInfoBO.setPageIndex(pageIndex);
        }
        if (pageSize != null) {
            userInfoBO.setPageSize(pageSize);
        }
        userInfoBO.setUserId(userId);
        PageResult<SysUser> sysUsers = sysUserService.findBySysUser(userInfoBO);
        return ResultBean.format(sysUsers);

    }


    /**
     * 新增用户信息
     * @param sysUser
     */
    @PostMapping("/sysuser")
    @ActionLogger(moduleName = "用户信息", actionType = ActionLoggerCons.ADD)
    public ResultBean saveUserInfo(SysUser sysUser){
        sysUser.setStatus(SysUserEnum.NORMAL.getValue());
        sysUser.setPassword(CryptoUtils.md5(sysUser.getPassword()));
        Date date = new Date();
        sysUser.setCreateTime(date);
        sysUser.setModifyTime(date);

        SysUser findSysUser = sysUserService.findByUserId(sysUser.getUserId());
        //查询到说明用户名相同
        if(findSysUser != null){
            return ResultBean.format(WebConfigBusinessEnum.USER_EXSIT.getValue(),WebConfigBusinessEnum.USER_EXSIT.getDesc());
        }
        sysUserService.saveSysUser(sysUser);
        return ResultBean.format(sysUser);
    }


    /**
     * 修改用户信息
     * @param sysUser
     */
    @PutMapping("/sysuser")
    @ActionLogger(moduleName = "用户信息", actionType = ActionLoggerCons.MODIFY)
    public ResultBean editUserInfo(SysUser sysUser){
        sysUser.setModifyTime(new Date());
        sysUserService.updateSysUser(sysUser);
        return ResultBean.format();
    }

    /**
     * 重置用户密码
     *
     * @param sysUser
     * @return
     */
    @PutMapping("/reset-user-password")
    @ActionLogger(moduleName = "重置用户密码", actionType = ActionLoggerCons.MODIFY)
    public ResultBean resetUserPassword(SysUser sysUser){
        // 重置用户密码
        sysUser.setPassword(CryptoUtils.md5(CommonConstants.RESET_USER_PASSWORD));
        sysUser.setModifyTime(new Date());
        sysUserService.updateSysUser(sysUser);
        return ResultBean.format();
    }

    /**
     * 删除用户信息
     * @param sysUser
     * @return
     */
    @DeleteMapping("/sysuser")
    @ActionLogger(moduleName = "用户信息", actionType = ActionLoggerCons.DELETE)
    public ResultBean deleteUserInfo(SysUser sysUser){
        sysUserService.deleteSysUser(sysUser);
        return ResultBean.format(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getDescription());
    }

    /**
     * 修改用户密码
     *
     * @param passwordBO
     * @return
     */
    @PutMapping("/password-edit")
    @ActionLogger(moduleName = "用户修改密码", actionType = ActionLoggerCons.MODIFY)
    public ResultBean editUserPasswordInfo(SysUserPasswordBO passwordBO) {
        if (passwordBO.getOldPassword().equals(passwordBO.getNewPassword())) {
            return ResultBean.format(PasswordVerifyEnum.NEW_PASSWORD_OLD_PASSWORD_ERROR.getValue(),PasswordVerifyEnum.NEW_PASSWORD_OLD_PASSWORD_ERROR.getDesc());
        }
        if (passwordBO.getCheckPassword().equals(passwordBO.getNewPassword())) {
            if ((passwordBO.getNewPassword().matches(CommonConstants.REGULAR_PASSWORD) ? 1 : 0) < 1) {
                return ResultBean.format(PasswordVerifyEnum.PASSWORD_FORMAT_ERROR.getValue(),PasswordVerifyEnum.PASSWORD_FORMAT_ERROR.getDesc());
            }
            Subject subject = SecurityUtils.getSubject();
            SysUser user = (SysUser) subject.getPrincipal();
            // 查询到说明该用户存在
            if (user != null && CryptoUtils.md5(passwordBO.getOldPassword()).equals(user.getPassword())) {
                user.setPassword(CryptoUtils.md5(passwordBO.getNewPassword()));
                user.setModifyTime(new Date());
                int i = sysUserService.updateSysUser(user);
                if (i>0){
                    return ResultBean.format(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getDescription());
                }
                return ResultBean.format(PasswordVerifyEnum.PASSWORD_UPDATE_ERROR.getValue(),PasswordVerifyEnum.PASSWORD_UPDATE_ERROR.getDesc());
            }
            return ResultBean.format(PasswordVerifyEnum.OLD_PASSWORD_ERROR.getValue(),PasswordVerifyEnum.OLD_PASSWORD_ERROR.getDesc());
        }
        return ResultBean.format(PasswordVerifyEnum.PASSWORD_ERROR.getValue(),PasswordVerifyEnum.PASSWORD_ERROR.getDesc());

    }

    /**
     * 修改用户头像
     */
    @PutMapping("/edit-user-photo")
    @ActionLogger(moduleName = "修改头像", actionType = ActionLoggerCons.MODIFY)
    public ResultBean editUserPhoto(String imageUrl) {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        user.setHeadImageUrl(imageUrl);
        user.setModifyTime(new Date());
        int i = sysUserService.updateSysUser(user);
        return ResultBean.format(i);

    }



}
