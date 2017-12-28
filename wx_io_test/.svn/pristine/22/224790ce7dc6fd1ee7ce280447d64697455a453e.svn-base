package com.nssol_sh.controller.wx.test.user;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.entity.wx.test.user.GetUserModel;
import com.nssol_sh.util.log.LogUtils;

@Controller
public class GetController implements Serializable {

	/**
	 * 获取对象
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 注入请求
	 */
	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/cgi-bin/user/get", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String GetUser(@RequestParam(value = "access_token", required = false) String access_token,
			@RequestParam(value = "userid", required = false) String userid) {
		
		//请求参数
		LogUtils.info("员工ID【request.userid：" + request.getParameter("userid") + "】");
		
		//返回结果
		GetUserModel getUser = new GetUserModel();

		if (!StringUtils.isEmpty(userid)) {
			// 孙丽
			if ("YCI0410103".equals(userid)) {
				getUser.userid = "YCI0410103";
				getUser.name = "孙丽";
				getUser.english_name = "孙丽";
				getUser.mobile = "13800000001";
				getUser.telephone="";
				getUser.avatar="";
				getUser.position = "";
				getUser.gender = "2";
				getUser.email = "Sun_Li@ykk.cn";
				getUser.status = 4;
			}

			// 金一
			if ("YCI1110113".equals(userid)) {
				getUser.userid = "YCI1110113";
				getUser.name = "金一";
				getUser.english_name = "金一";
				getUser.mobile = "13800000002";
				getUser.telephone="";
				getUser.avatar="";
				getUser.position = "";
				getUser.gender = "1";
				getUser.email = "jinyi@ykk.cn";
				getUser.status = 4;
			}

			// 杜雯怡
			if ("YCI1310103".equals(userid)) {
				getUser.userid = "YCI1310103";
				getUser.name = "杜雯怡";
				getUser.english_name = "杜雯怡";
				getUser.mobile = "13800000003";
				getUser.telephone="";
				getUser.avatar="";
				getUser.position = "";
				getUser.gender = "2";
				getUser.email = "duwenyi@ykk.cn";
				getUser.status = 4;
			}

			// 叶歆音
			if ("YCI0610121".equals(userid)) {
				getUser.userid = "YCI0610121";
				getUser.name = "叶歆音";
				getUser.english_name = "叶歆音";
				getUser.mobile = "13800000004";
				getUser.telephone="";
				getUser.avatar="";
				getUser.position = "";
				getUser.gender = "2";
				getUser.email = "yexinyin@ykk.cn";
				getUser.status = 4;
			}
		} else {
			getUser.userid = "YCI1234567";
			getUser.name = "默认用户";
			getUser.english_name = "默认用户";
			getUser.mobile = "13800000009";
			getUser.telephone="";
			getUser.avatar="";
			getUser.position = "";
			getUser.gender = "2";
			getUser.email = "default@ykk.cn";
			getUser.status = 4;
		}
		
		System.err.println("【get】返回...");
		
		LogUtils.info("返回消息【get】...");
		
		LogUtils.info("返回结果:【"+JSON.toJSONString(getUser) + "】");
		return JSON.toJSONString(getUser);
	}
}
