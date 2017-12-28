package com.nssol_sh.service.wx.view.yci.yexp.swm007;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewResultModel;
import com.nssol_sh.entity.wx.view.yci.yexp.swm007.Swm007ApproveViewModel;
import com.nssol_sh.service.wx.view.common.approveview.ApproveViewCommonService;
import com.nssol_sh.util.tools.DateTimeUtil;

/**
 * 加班审批
 * 
 */
@Service("wx.veiw.yci.yexp.swm007.service")
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
	public Swm007ApproveViewModel getData(String output, String uri) throws Exception {
		// 页面数据对象
		Swm007ApproveViewModel result = new Swm007ApproveViewModel();

		// 使用API获取审批数据
		ApproveViewResultModel apiResult = this.getApproveData(output, uri);

		// 将API的返回内容转换成页面格式
		result = this.createViewObject(apiResult, Swm007ApproveViewModel.class);

		// 如果无错
		if (!result.error) {
			// 格式化数据

			// 加班日期（平日）
			if (!StringUtils.isEmpty(result.matterUserInfo.overtimeDate)) {
				result.matterUserInfo.overtimeDateDsp = DateTimeUtil
						.formatDate(Long.valueOf(result.matterUserInfo.overtimeDate));
			}

			// 加班日期开始（周末加班）
			if (!StringUtils.isEmpty(result.matterUserInfo.overtimeStartDate)) {
				result.matterUserInfo.overtimeStartDateDsp = DateTimeUtil
						.formatDate(Long.valueOf(result.matterUserInfo.overtimeStartDate));
			}

			// 加班日期结束（周末加班）
			if (!StringUtils.isEmpty(result.matterUserInfo.overtimeEndDate)) {
				result.matterUserInfo.overtimeEndDateDsp = DateTimeUtil
						.formatDate(Long.valueOf(result.matterUserInfo.overtimeEndDate));
			}

			// 加班开始时间（平日）
			if (!StringUtils.isEmpty(result.matterUserInfo.startHhmm)) {
				result.matterUserInfo.startHhmmDsp = DateTimeUtil.formatTime(result.matterUserInfo.startHhmm);
			}

			// 加班结束时间（平日）
			if (!StringUtils.isEmpty(result.matterUserInfo.endHhmm)) {
				result.matterUserInfo.endHhmmDsp = DateTimeUtil.formatTime(result.matterUserInfo.endHhmm);
			}
		}

		// 返回值
		return result;
	}

}
