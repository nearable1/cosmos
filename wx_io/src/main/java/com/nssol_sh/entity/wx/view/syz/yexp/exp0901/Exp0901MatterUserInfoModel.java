package com.nssol_sh.entity.wx.view.syz.yexp.exp0901;

import java.util.List;
import java.util.Map;

/**
 * 印章模块
 * 
 * @author
 *
 */
public class Exp0901MatterUserInfoModel {
	/**
	 * 印章种类
	 */
	public String stampsType;

	/**
	 * 印章一览（分公司）
	 */
	public List<Map<String, Object>> stampListBO;

	/**
	 * 印章一览（上海吉田拉链有限公司）
	 */
	public List<Map<String, Object>> stampListSYZ;

	/**
	 * 对应的显示标志位
	 */
	public boolean viewFlag01;

	public boolean viewFlag02;

	public boolean viewFlag03;

	/**
	 * 印章一览（上海YKK国际贸易有限公司）
	 */
	public List<Map<String, Object>> stampListSYT;

	/**
	 * 内容(概要)
	 */
	public String contents;
	/**
	 * 资料名
	 */
	public String fileName;
	/**
	 * 资料番号/日期
	 */
	public String fileCd;

	/**
	 * @return the stampsType
	 */
	public String getStampsType() {
		return stampsType;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the fileCd
	 */
	public String getFileCd() {
		return fileCd;
	}

	/**
	 * @return the viewFlag01
	 */
	public boolean isViewFlag01() {
		return viewFlag01;
	}

	/**
	 * @return the viewFlag02
	 */
	public boolean isViewFlag02() {
		return viewFlag02;
	}

	/**
	 * @return the viewFlag03
	 */
	public boolean isViewFlag03() {
		return viewFlag03;
	}

	/**
	 * @return the stampListBO
	 */
	public List<Map<String, Object>> getStampListBO() {
		return stampListBO;
	}

	/**
	 * @return the stampListSYZ
	 */
	public List<Map<String, Object>> getStampListSYZ() {
		return stampListSYZ;
	}

	/**
	 * @return the stampListSYT
	 */
	public List<Map<String, Object>> getStampListSYT() {
		return stampListSYT;
	}

}
