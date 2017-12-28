/**
 * 
 */
package com.nssol_sh.util.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * 共通处理
 * 
 * @author S1mple
 *
 */
public class CommonUtil {

	/**
	 * 获取Mapping关系
	 * 
	 * @author S1mple
	 * @param _mappingUser
	 * @param imUserFlag:ture-map的value值为im值
	 * @return Map
	 */
	public static Map<String, String> GetUserCdMapping(String _mappingUser, boolean imUserFlag) {
		Map<String, String> resultMap = new HashMap<String, String>();

		if (!StringUtils.isEmpty("_mappingUser")) {
			String[] userMappingCouple = _mappingUser.split("\\|");
			for (int i = 0; i < userMappingCouple.length; i++) {
				String[] mappingStr = userMappingCouple[i].split("/");
				if (imUserFlag) {
					// key-WeChatUserCd,Value-IMUserCd;
					resultMap.put(mappingStr[1], mappingStr[0]);
				} else {
					// key-IMUserCd,Value-WeChatUserCd;
					resultMap.put(mappingStr[0], mappingStr[1]);
				}

			}
		} else {
			// 未取得配置文件Mapping关系
			return null;
		}
		return resultMap;
	}

	/**
	 * 根据Mapping关系替换userCd
	 * 
	 * @author S1mple
	 * @param userCd(wx)
	 * @param mappingStr-配置文件mapping关系
	 * @param imUserFlag:ture-map的value值为im值
	 * @return String
	 */
	public static String ReplaceUserCdByMapping(String WX_UserCd, String mappingStr, boolean imUserCdFlag) {
		String newUserCd = "";
		// 获取Mapping关系
		Map<String, String> resultMap = GetUserCdMapping(mappingStr, imUserCdFlag);
		if (resultMap != null) {
			if (!StringUtils.isEmpty(resultMap.get(WX_UserCd))) {
				newUserCd = resultMap.get(WX_UserCd);
				if (!StringUtils.isEmpty(newUserCd)) {
					return newUserCd;
				}

			}
		}
		return WX_UserCd;
	}
}
