package com.nssol_sh.controller.wx.api.qy.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.util.constants.wechat.qy.QYConstant;
import com.nssol_sh.util.tools.wechat.qy.api.QyAPICaller;
import com.nssol_sh.util.tools.wechat.qy.cache.TokenTicketCacher;
import com.nssol_sh.util.tools.wechat.qy.model.api.APIResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.UserModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.create.CreateUserModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.create.CreateUserParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.delete.DeleteUserParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.get.GetUserParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.list.ListUserParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.list.ListUserResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.update.UpdateUserParamsModel;

/**
 * 用户相关API
 * 
 * @author he.jiaqi
 *
 */
@Controller
public class UserApisController {
	/**
	 * 企业ID
	 */
	@Value("${wx.io.corp_id}")
	private String _corpid;

	/**
	 * 应用程序代码
	 */
	@Value("${wx.io.ychips.app.workflow.id}")
	private int _appid;

	/**
	 * 应用的凭证密钥
	 */
	@Value("${wx.io.ychips.app.workflow.secret}")
	private String _corpsecret;

	/**
	 * 内部通信令牌
	 */
	@Value("${wx.io.ychips.internal_token}")
	private String _internalToken;

	/**
	 * 获取用户信息
	 * 
	 * @param token
	 *            内部令牌
	 * @param userid
	 *            用户代码
	 * @return 用户信息（JSON）
	 */
	@RequestMapping(value = "/wx/api/qy/user/get", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUser(@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "test", required = false) String test) {

		// 检查令牌
		if (_internalToken.equals(token)) {

			// 令牌保存类
			TokenTicketCacher ttc = TokenTicketCacher.getInstance();

			// 获取令牌
			String accessToken = ttc.getAccessToken(this._corpid, this._appid);

			// 获取成员信息参数
			GetUserParamsModel pm = new GetUserParamsModel();

			// 设定令牌
			pm.accesstoken = accessToken;

			// 成员ID
			pm.userid = userid;

			// 测试标签
			pm.test = test;

			// 调用获取用户信息API
			UserModel rm = QyAPICaller.getUser(pm);
			return JSON.toJSONString(rm);
		} else {
			// 返回公共错误

			APIResultModel arm = new APIResultModel(QYConstant.API_REQUEST_ERR_CD_CMN,
					QYConstant.API_REQUEST_ERR_MSG_CMN);
			return JSON.toJSONString(arm);
		}
	}

	/**
	 * 新建用户
	 * 
	 * @param token
	 *            内部令牌
	 * @param userinfo
	 *            用户信息JSON字符串
	 * @return 执行结果
	 */
	@RequestMapping(value = "/wx/api/qy/user/create", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String createUser(@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "userinfo", required = true) String userinfo,
			@RequestParam(value = "test", required = false) String test) {

		// 检查令牌
		if (_internalToken.equals(token)) {
			// 用户信息
			CreateUserModel um = JSON.parseObject(userinfo, CreateUserModel.class);

			// 令牌保存类
			TokenTicketCacher ttc = TokenTicketCacher.getInstance();

			// 获取令牌
			String accessToken = ttc.getContactAccessToken(this._corpid);

			// 创建用户参数
			CreateUserParamsModel pm = new CreateUserParamsModel();

			// 设定令牌
			pm.accesstoken = accessToken;

			// 设定用户信息
			pm.userInfo = um;

			// 测试标签
			pm.test = test;

			// 调用创建用户API
			APIResultModel rm = QyAPICaller.createUser(pm);

			return JSON.toJSONString(rm);
		} else {
			// 返回公共错误
			APIResultModel arm = new APIResultModel(QYConstant.API_REQUEST_ERR_CD_CMN,
					QYConstant.API_REQUEST_ERR_MSG_CMN);
			return JSON.toJSONString(arm);
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param token
	 *            内部令牌
	 * @param userinfo
	 *            用户信息JSON字符串
	 * @return 执行结果
	 */
	@RequestMapping(value = "/wx/api/qy/user/update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateUser(@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "userinfo", required = true) String userinfo,
			@RequestParam(value = "test", required = false) String test) {
		// 检查令牌
		if (_internalToken.equals(token)) {
			// 用户信息
			UserModel um = JSON.parseObject(userinfo, UserModel.class);

			// 令牌保存类
			TokenTicketCacher ttc = TokenTicketCacher.getInstance();

			// 获取令牌
			String accessToken = ttc.getContactAccessToken(this._corpid);

			// 创建用户参数
			UpdateUserParamsModel pm = new UpdateUserParamsModel();

			// 设定令牌
			pm.accesstoken = accessToken;

			// 设定用户信息
			pm.userInfo = um;

			// 测试标签
			pm.test = test;

			// 调用更新用户信息API
			APIResultModel rm = QyAPICaller.updateUser(pm);

			return JSON.toJSONString(rm);
		} else {
			// 返回公共错误
			APIResultModel arm = new APIResultModel(QYConstant.API_REQUEST_ERR_CD_CMN,
					QYConstant.API_REQUEST_ERR_MSG_CMN);
			return JSON.toJSONString(arm);
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param token
	 *            内部令牌
	 * @param userid
	 *            用户代码
	 * @return 执行结果
	 */
	@RequestMapping(value = "/wx/api/qy/user/delete", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteUser(@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "test", required = false) String test) {
		// 检查令牌
		if (_internalToken.equals(token)) {
			// 令牌保存类
			TokenTicketCacher ttc = TokenTicketCacher.getInstance();

			// 获取令牌
			String accessToken = ttc.getAccessToken(this._corpid, this._appid);

			// 获取成员信息参数
			DeleteUserParamsModel pm = new DeleteUserParamsModel();

			// 设定令牌
			pm.accesstoken = accessToken;

			// 成员ID
			pm.userid = userid;

			// 测试标签
			pm.test = test;

			// 调用删除用户API
			APIResultModel rm = QyAPICaller.deleteUser(pm);

			return JSON.toJSONString(rm);
		} else {
			// 返回公共错误
			APIResultModel arm = new APIResultModel(QYConstant.API_REQUEST_ERR_CD_CMN,
					QYConstant.API_REQUEST_ERR_MSG_CMN);
			return JSON.toJSONString(arm);
		}

	}

	/**
	 * 新建用户
	 * 
	 * @param token
	 *            内部令牌
	 * @param userinfo
	 *            用户信息JSON字符串
	 * @return 执行结果
	 */
	@RequestMapping(value = "/wx/api/qy/user/list", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String listUser(@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "departmentid", required = true) String departmentid,
			@RequestParam(value = "test", required = false) String test) {

		// 检查令牌
		if (_internalToken.equals(token)) {

			// 令牌保存类
			TokenTicketCacher ttc = TokenTicketCacher.getInstance();

			// 获取令牌
			String accessToken = ttc.getAccessToken(this._corpid, this._appid);

			// 创建用户参数
			ListUserParamsModel pm = new ListUserParamsModel();

			// 设定令牌
			pm.accesstoken = accessToken;

			// 设定部门ID
			pm.department_id = departmentid;

			// 是否递归子部门
			pm.fetch_child = "1";

			// 测试标签
			pm.test = test;

			// 调用创建用户API
			ListUserResultModel rm = QyAPICaller.listUser(pm);

			return JSON.toJSONString(rm);
		} else {
			// 返回公共错误
			APIResultModel arm = new APIResultModel(QYConstant.API_REQUEST_ERR_CD_CMN,
					QYConstant.API_REQUEST_ERR_MSG_CMN);
			return JSON.toJSONString(arm);
		}
	}
}
