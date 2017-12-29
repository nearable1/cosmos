package com.nssol_sh.entity.wx.view.common.approveview.msi;

/**
 * 印章信息实体类
 * 
 * @author liu.yigeng
 *
 */
public class StampInfoModel {
	/**
	 * 是否是默认
	 */
	public String defaultFlag;

	/**
	 * 印章ID
	 */
	public String stampId;

	/**
	 * 印章名
	 */
	public String stampName;

	/**
	 * 第一段字符
	 */
	public String stampStr1;

	/**
	 * 第一段类型
	 */
	public String stampStr1Type;

	/**
	 * 第二段字符
	 */
	public String stampStr2;

	/**
	 * 第二段类型
	 */
	public String stampStr2Type;

	/**
	 * 第三段字符
	 */
	public String stampStr3;

	/**
	 * 第三段类型
	 */
	public String stampStr3Type;

	/**
	 * 用户编码
	 */
	public String userCode;

	/**
	 * 印章类型
	 */
	public String stampType;

	/**
	 * @return the defaultFlag
	 */
	public String getDefaultFlag() {
		return defaultFlag;
	}

	/**
	 * @return the stampId
	 */
	public String getStampId() {
		return stampId;
	}

	/**
	 * @return the stampName
	 */
	public String getStampName() {
		return stampName;
	}

	/**
	 * @return the stampStr1
	 */
	public String getStampStr1() {
		return stampStr1;
	}

	/**
	 * @return the stampStr1Type
	 */
	public String getStampStr1Type() {
		return stampStr1Type;
	}

	/**
	 * @return the stampStr2
	 */
	public String getStampStr2() {
		return stampStr2;
	}

	/**
	 * @return the stampStr2Type
	 */
	public String getStampStr2Type() {
		return stampStr2Type;
	}

	/**
	 * @return the stampStr3
	 */
	public String getStampStr3() {
		return stampStr3;
	}

	/**
	 * @return the stampStr3Type
	 */
	public String getStampStr3Type() {
		return stampStr3Type;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	public String getStampType() {
		return stampType;
	}

	public void setStampType(String stampType) {
		this.stampType = stampType;
	}

}
