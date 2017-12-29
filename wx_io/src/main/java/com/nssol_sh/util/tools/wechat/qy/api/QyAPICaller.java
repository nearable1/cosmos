package com.nssol_sh.util.tools.wechat.qy.api;

import com.nssol_sh.util.constants.wechat.qy.QYConstant;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.GetToken;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.message.send.SendNews;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.message.send.SendText;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.user.CreateUser;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.user.DeleteUser;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.user.GetUser;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.user.GetUserInfoByCode;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.user.ListUser;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.user.UpdateUser;
import com.nssol_sh.util.tools.wechat.qy.cache.TokenTicketCacher;
import com.nssol_sh.util.tools.wechat.qy.model.api.APIResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.gettoken.GetTokenParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.gettoken.GetTokenResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.message.send.MsgSendResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.message.send.sendnews.SendNewsParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.message.send.sendtext.SendTextParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.UserModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.create.CreateUserParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.delete.DeleteUserParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.get.GetUserParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.getuserinfo.GetUserInfoByCodeParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.getuserinfo.GetUserInfoByCodeResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.list.ListUserParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.list.ListUserResultModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.user.update.UpdateUserParamsModel;

/**
 * 企业微信API调用类
 * 
 * @author liu.yigeng
 *
 */
public class QyAPICaller {

	/**
	 * 获得令牌
	 *
	 * @return 令牌返回值
	 */
	public static GetTokenResultModel getToken(GetTokenParamsModel params) {
		// 返回值
		GetTokenResultModel result = new GetTokenResultModel();

		// 默认错误码
		result.errcode = QYConstant.API_REQUEST_ERR_CD_CMN;

		// 默认错误消息
		result.errmsg = QYConstant.API_REQUEST_ERR_MSG_CMN;

		// 执行API
		TokenTicketCacher ttc = TokenTicketCacher.getInstance();

		// 判断能否请求(如果请求超过一定次数，则不予执行)
		if (!ttc.requestOverflow()) {
			result = APIObjectFactory.createObject(GetToken.class).setParams(params).run();
		}

		// 返回
		return result;
	}

	/**
	 * 发送文本信息
	 *
	 * @return 发送信息返回值
	 */
	public static MsgSendResultModel sendTextMessage(SendTextParamsModel params) {

		// 返回
		return APIObjectFactory.createObject(SendText.class).setParams(params).run();
	}

	/**
	 * 发送图文信息
	 *
	 * @return 发送信息返回值
	 */
	public static MsgSendResultModel sendNewsMessage(SendNewsParamsModel params) {

		// 返回
		return APIObjectFactory.createObject(SendNews.class).setParams(params).run();

	}

	/**
	 * 根据code获取成员信息
	 *
	 * @return 返回值
	 */
	public static GetUserInfoByCodeResultModel getUserInfoByCode(GetUserInfoByCodeParamsModel params) {

		// 返回
		return APIObjectFactory.createObject(GetUserInfoByCode.class).setParams(params).run();

	}

	/**
	 * 根据成员ID获取成员信息
	 *
	 * @return 返回值
	 */
	public static UserModel getUser(GetUserParamsModel params) {

		// 返回
		return APIObjectFactory.createObject(GetUser.class).setParams(params).run();

	}

	/**
	 * 创建成员
	 *
	 * @return 返回值
	 */
	public static APIResultModel createUser(CreateUserParamsModel params) {

		// 返回
		return APIObjectFactory.createObject(CreateUser.class).setParams(params).run();
	}

	/**
	 * 更新成员
	 *
	 * @return 返回值
	 */
	public static APIResultModel updateUser(UpdateUserParamsModel params) {

		// 返回
		return APIObjectFactory.createObject(UpdateUser.class).setParams(params).run();
	}

	/**
	 * 删除成员
	 *
	 * @return 返回值
	 */
	public static APIResultModel deleteUser(DeleteUserParamsModel params) {

		// 返回
		return APIObjectFactory.createObject(DeleteUser.class).setParams(params).run();
	}

	/**
	 * 获取用户列表
	 *
	 * @return 返回值
	 */
	public static ListUserResultModel listUser(ListUserParamsModel params) {

		// 返回
		return APIObjectFactory.createObject(ListUser.class).setParams(params).run();
	}
}
