package com.nssol_sh.entity.wx.view.ysw.yexp.exp0901;

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
	 * 印章一览（全部）
	 */
	public List<Map<String, Object>> stampList;

	/**
	 * 印章一览（统括事业部所属）
	 */
	public List<Map<String, Object>> stampList01;

	/**
	 * 对应的显示标志位
	 */
	public boolean viewFlag01;
	/**
	 * 印章一览（AP事业部所属）
	 */
	public List<Map<String, Object>> stampList02;

	public boolean viewFlag02;
	/**
	 * 印章一览（苏州研发所属）
	 */
	public List<Map<String, Object>> stampList03;

	public boolean viewFlag03;
	/**
	 * 印章一览（大连研发所属）
	 */
	public List<Map<String, Object>> stampList04;

	public boolean viewFlag04;

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
	 * @return the stampList
	 */
	public List<Map<String, Object>> getStampList() {
		return stampList;
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
	 * @return the stampList01
	 */
	public List<Map<String, Object>> getStampList01() {
		return stampList01;
	}

	/**
	 * @return the stampList02
	 */
	public List<Map<String, Object>> getStampList02() {
		return stampList02;
	}

	/**
	 * @return the stampList03
	 */
	public List<Map<String, Object>> getStampList03() {
		return stampList03;
	}

	/**
	 * @return the stampList04
	 */
	public List<Map<String, Object>> getStampList04() {
		return stampList04;
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
	 * @return the viewFlag04
	 */
	public boolean isViewFlag04() {
		return viewFlag04;
	}

}
