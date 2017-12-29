package com.nssol_sh.service.wx.job.qy;

import org.springframework.stereotype.Service;

import com.nssol_sh.util.tools.wechat.qy.cache.UserTokenCacher;

/**
 * 过期用户令牌清理
 * 
 * @author liu.yigeng
 *
 */
@Service
public class UserTokenScavengerService {

	/**
	 * 刷新企业号令牌
	 */
	public void clearExpiredToken() {

		// 保存令牌信息
		UserTokenCacher utc = UserTokenCacher.getInstance();

		// 清除过期令牌
		utc.clearExpiredToken();
	}
}
