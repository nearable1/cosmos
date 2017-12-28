package com.nssol_sh.util.tools.wechat.qy.api;

import com.nssol_sh.util.log.LogUtils;
import com.nssol_sh.util.tools.wechat.qy.api.excutor.APIExcutor;

/**
 * API执行类对象工厂
 *
 * @author he.jiaqi
 *
 */
public class APIObjectFactory {
	/**
	 * API对象生成器
	 * 
	 * @param API执行类
	 * @return API对象
	 */
	public final static <T extends APIExcutor> T createObject(Class<T> c) {
		// 返回值
		T result = null;

		// 生成对象
		try {
			result = (T) Class.forName(c.getName()).newInstance();
		} catch (Exception e) {
			LogUtils.error("APIObjectFactory-生成对象发生异常...", e);
		}

		// 返回
		return result;
	}
}
