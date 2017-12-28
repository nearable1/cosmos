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

@Controller
public class UpdateController implements Serializable {

	/**
	 * 创建员工
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 注入请求
	 */
	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/cgi-bin/user/update", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateHandler(@RequestParam(value = "access_token", required = false) String token) throws Exception {
		
		//请求参数
		LogUtils.info("更新员工【userid：" + request.getParameter("userid") + "】");
		LogUtils.info("更新员工【name：" + request.getParameter("name") + "】");
		LogUtils.info("更新员工【department：" + request.getParameter("department") + "】");
		System.err.println("接受【Update】请求...");
		
		//返回结果
		ResultModel errModel = new ResultModel();
		errModel.errcode = 0;
		errModel.errmsg = "Update_succeed";
		LogUtils.info("【Update】成功");
		System.err.println("【Update】返回消息...");
		
		LogUtils.info("返回结果:【"+JSON.toJSONString(errModel) + "】");
		return JSON.toJSONString(errModel);
	}
}
