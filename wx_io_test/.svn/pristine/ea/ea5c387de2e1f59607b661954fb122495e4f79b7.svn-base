/**
 * 
 */
package com.nssol_sh.controller.wx.test.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.entity.wx.test.user.GetUserList;
import com.nssol_sh.entity.wx.test.user.UserModel;
import com.nssol_sh.util.log.LogUtils;

/**
 * 获取详情
 * 
 * @author S1mple
 *
 */
@Controller
public class ListController implements Serializable {

	/**
	 * 获取详情
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 注入请求
	 */
	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/cgi-bin/user/list", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String GetUserListHandler(@RequestParam(value = "access_token", required = false) String token,
			@RequestParam(value = "department_id", required = false) String department_id,
			@RequestParam(value = "access_token", required = false) String fetch_child) throws Exception {

		//返回结果
		GetUserList getUserList = new GetUserList();

		// 用户数据
		getUserList.userlist = new ArrayList<UserModel>();

/*
		// 部门列表
		List<Integer> deptArray = new ArrayList<Integer>();
		deptArray.add(2);

		// 孙丽(YCI0410103)
		UserModel um = new UserModel();

		um.userid = "YCI0410103";
		um.name = "孙丽";
		um.english_name = "孙丽";
		um.department = deptArray;
		um.position = "";
		um.mobile = "13800000001";
		um.gender = "2";
		um.email = "Sun_Li@ykk.cn";
		um.status = 4;
		getUserList.userlist.add(um);

		// 金一(YCI1110113)
		um = new UserModel();

		um.userid = "YCI1110113";
		um.name = "金一";
		um.english_name = "金一";
		um.department = deptArray;
		um.position = "";
		um.mobile = "13800000002";
		um.gender = "1";
		um.email = "jinyi@ykk.cn";
		um.status = 4;
		getUserList.userlist.add(um);

		// 杜雯怡(YCI1310103)
		um = new UserModel();

		um.userid = "YCI1310103";
		um.name = "杜雯怡";
		um.english_name = "杜雯怡";
		um.department = deptArray;
		um.position = "";
		um.mobile = "13800000003";
		um.gender = "2";
		um.email = "duwenyi@ykk.cn";
		um.status = 4;
		getUserList.userlist.add(um);

		// 叶歆音(YCI0610121)
		um = new UserModel();

		um.userid = "YCI0610121";
		um.name = "叶歆音";
		um.english_name = "叶歆音";
		um.department = deptArray;
		um.position = "";
		um.mobile = "13800000004";
		um.gender = "2";
		um.email = "yexinyin@ykk.cn";
		um.status = 4;
		getUserList.userlist.add(um);
*/
		getUserList.errcode = 0;
		getUserList.errmsg = "ok";

		return JSON.toJSONString(getUserList);
	}
}
