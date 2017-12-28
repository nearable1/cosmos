package com.nssol_sh.service.wx.job.qy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nssol_sh.util.tools.wechat.qy.api.QyAPICaller;
import com.nssol_sh.util.tools.wechat.qy.cache.TokenTicketCacher;
import com.nssol_sh.util.tools.wechat.qy.model.api.gettoken.GetTokenParamsModel;
import com.nssol_sh.util.tools.wechat.qy.model.api.gettoken.GetTokenResultModel;

/**
 * 取得企业号令牌
 * 
 * @author he.jiaqi
 *
 */
@Service
public class AccessTokenRefresherService {

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
	 * 通讯录密钥
	 */
	@Value("${wx.io.contact.secret}")
	private String _contactsecret;

	/**
	 * 刷新企业号令牌
	 */
	public void refreshToken() {
		// 令牌API参数
		GetTokenParamsModel pm = new GetTokenParamsModel();

		// 设置企业ID
		pm.corpid = this._corpid;

		// 设置应用的凭证密钥
		pm.corpsecret = this._corpsecret;
		// 调用获得令牌API
		GetTokenResultModel rm = QyAPICaller.getToken(pm);

		// 保存令牌信息
		TokenTicketCacher ttc = TokenTicketCacher.getInstance();

		ttc.setAccessToken(this._corpid, this._appid, rm.access_token, rm.expires_in);

		// 令牌API参数初始化
		pm = new GetTokenParamsModel();

		// 设置企业ID
		pm.corpid = this._corpid;

		// 设置通讯录的凭证密钥
		pm.corpsecret = this._contactsecret;
		// 调用获得令牌API
		rm = QyAPICaller.getToken(pm);

		// 保存通讯录令牌信息
		ttc = TokenTicketCacher.getInstance();

		ttc.setContactAccessToken(this._corpid, rm.access_token, rm.expires_in);
	}
}
