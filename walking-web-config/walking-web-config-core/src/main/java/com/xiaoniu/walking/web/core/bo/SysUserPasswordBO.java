package com.xiaoniu.walking.web.core.bo;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码
 * @author chenguohua
 * @date 2019/04/28 11:20
 */

@Data
public class SysUserPasswordBO extends DefaultPageParameter implements Serializable {
    private static final long serialVersionUID = 6334001800609485176L;

    /**
     * 用户原始密码
     */
    private String oldPassword;
    /**
     * 用户新密码
     */
    private String newPassword;
    /**
     * 用户再次输入新密码
     */
   private String checkPassword;
}
