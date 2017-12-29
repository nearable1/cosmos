package com.nssol_sh.service.wx.view.common.route;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nssol_sh.util.tools.properties.CnfPropsUtil;

/**
 * 页面数据获取路径解析类
 *
 * @author liu.yigeng
 *
 */
@Service("wx.veiw.common.route.service")
public class RouteAnaliyzerService {
	/**
	 * 常量-Y-CHIPS的API路径前缀
	 */
	private static final String YCHIPS_API_DOMAIN_PREFIX = "domain";

	/**
	 * 常量-Y-CHIPS的API路径前缀
	 */
	private static final String YCHIPS_API_PREFIX = "wx.io.ychips.api.";

	/**
	 * 常量-Y-CHIPS的API路径值前缀
	 */
	private static final String YCHIPS_API_PATH_PREFIX_VALUE = "wx.io.ychips.api.prefix";

	/**
	 * 常量-页面数据获取API路径后缀
	 */
	private static final String APPROVE_VIEW_API_SUFFIX = ".approveview";

	/**
	 * 常量-工作流API路径前缀
	 */
	private static final String WKF_CMN_API_PREFIX = "wx.io.ychips.api.cmn.wkf.";

	/**
	 * 获取业务数据的API路径
	 * 
	 * @param key
	 *            页面ID
	 * @param sf
	 *            服务器标签
	 * @param tenantId
	 *            Tenant ID
	 * @return API的URL
	 */
	public String getApproveViewURL(String key, String sf, String tenantId) {
		// 域名获取
		String domain = CnfPropsUtil.getProperty(YCHIPS_API_PREFIX + YCHIPS_API_DOMAIN_PREFIX);

		// 如果有服务器标签
		if (!StringUtils.isEmpty(sf)) {
			domain = CnfPropsUtil.getProperty(YCHIPS_API_PREFIX + YCHIPS_API_DOMAIN_PREFIX + '.' + sf);
		}

		// 路径
		String path = CnfPropsUtil.getProperty(YCHIPS_API_PATH_PREFIX_VALUE);

		// 路径中Tenant添加
		path += tenantId;

		// 路径中相应的技能添加
		path += CnfPropsUtil.getProperty(YCHIPS_API_PREFIX + key + APPROVE_VIEW_API_SUFFIX);

		return domain + path;
	}

	/**
	 * 工作流处理API路径
	 * 
	 * @param key
	 *            页面ID
	 * @param sf
	 *            服务器标签
	 * @param tenantId
	 *            Tenant ID
	 * @return API的URL
	 */
	public String getWkfProcessURL(String key, String sf) {
		// 域名获取
		String domain = CnfPropsUtil.getProperty(YCHIPS_API_PREFIX + YCHIPS_API_DOMAIN_PREFIX);

		// 如果有服务器标签
		if (!StringUtils.isEmpty(sf)) {
			domain = CnfPropsUtil.getProperty(YCHIPS_API_PREFIX + YCHIPS_API_DOMAIN_PREFIX + '.' + sf);
		}

		// 路径
		String path = CnfPropsUtil.getProperty(YCHIPS_API_PATH_PREFIX_VALUE);

		// 路径中相应的处理添加
		path += CnfPropsUtil.getProperty(WKF_CMN_API_PREFIX + key);

		return domain + path;
	}
}
