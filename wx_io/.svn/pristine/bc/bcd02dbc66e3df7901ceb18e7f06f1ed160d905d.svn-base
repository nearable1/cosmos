package com.nssol_sh.service.wx.view.syz.yexp.exp0901;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewResultModel;
import com.nssol_sh.entity.wx.view.syz.yexp.exp0901.Exp0901ApproveViewModel;
import com.nssol_sh.service.wx.view.common.approveview.ApproveViewCommonService;

@Service("wx.veiw.syz.yexp.exp0901.service")
public class ApproveviewService extends ApproveViewCommonService {

	/**
	 * 获取审批页面数据
	 * 
	 * @param output
	 *            POST体
	 * @param uri
	 *            请求路径
	 * @return 页面数据
	 * @throws Exception
	 */
	public Exp0901ApproveViewModel getData(String output, String uri) throws Exception {
		// 页面数据对象
		Exp0901ApproveViewModel result = new Exp0901ApproveViewModel();

		// 使用API获取审批数据
		ApproveViewResultModel apiResult = this.getApproveData(output, uri);

		// 将API的返回内容转换成页面格式
		result = this.createViewObject(apiResult, Exp0901ApproveViewModel.class);

		return forMatterData(result);
	}

	/**
	 * appriveView的处理逻辑
	 * 
	 * @param output
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public Exp0901ApproveViewModel forMatterData(Exp0901ApproveViewModel info) throws Exception {
		// 如果有用户数据
		if (info.matterUserInfo != null) {

			/**
			 * 分公司（印章）
			 */
			info.matterUserInfo.viewFlag01 = hasChecked(info.matterUserInfo.stampListBO);

			/**
			 * 上海吉田拉链有限公司（印章）
			 */
			info.matterUserInfo.viewFlag02 = hasChecked(info.matterUserInfo.stampListSYZ);

			/**
			 * 上海YKK国际贸易有限公司（印章）
			 */
			info.matterUserInfo.viewFlag03 = hasChecked(info.matterUserInfo.stampListSYT);

		}
		return info;
	}

	/**
	 * 查询印章列表是否有选中项
	 * 
	 * @param stampList
	 * @return true 有
	 */
	public boolean hasChecked(List<Map<String, Object>> stampList) {
		if (stampList != null && stampList.size() > 0) {
			for (Map<String, Object> map : stampList) {
				// 如果存在选中项
				if (map.containsKey("checked") && (Boolean) map.get("checked")) {
					// 返回选中项
					return true;
				}
			}
		}
		return false;
	}
}
