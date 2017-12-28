/**
 * 
 */
package com.nssol_sh.controller.wx.test.user;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.entity.wx.test.common.ResultModel;
import com.nssol_sh.util.log.LogUtils;

/**
 * 删除员工C
 * 
 * @author S1mple
 *
 */

@Controller
public class DeteleController implements Serializable {
	/**
	 * 删除员工
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 注入请求
	 */
	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/cgi-bin/user/delete", method = RequestMethod.GET)
	@ResponseBody
	public String DeteleHandler(@RequestParam(value = "access_token", required = false) String token,
			@RequestParam(value = "userid", required = false) String userid) throws Exception {
		
		//请求参数
		LogUtils.info("删除员工【userid：" + request.getParameter("userid") + "】");
		
		System.err.println("接受【delete】请求...");
		
		//返回结果
		ResultModel errModel = new ResultModel();
		errModel.errcode = 0;
		errModel.errmsg = "delete_succeed";
		LogUtils.info("【delete】成功");
		
		System.err.println("【delete】返回消息...");
		
	    LogUtils.info("返回结果:【"+JSON.toJSONString(errModel) + "】");
		return JSON.toJSONString(errModel);
	}
}
