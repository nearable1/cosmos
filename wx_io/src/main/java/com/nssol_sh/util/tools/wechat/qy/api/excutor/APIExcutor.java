package com.nssol_sh.util.tools.wechat.qy.api.excutor;

import com.nssol_sh.util.tools.wechat.qy.model.api.APIResultModel;

/**
 * API执行器父类
 * 
 * @author he.jiaqi
 *
 */
public abstract class APIExcutor {

	/**
	 * 执行抽象类
	 * 
	 * @return API返回值
	 */
	public abstract APIResultModel run();

	/**
	 * API执行返回值解析类 return API解析返回值
	 */
	protected abstract APIResultModel analysisResult(String s);

}
