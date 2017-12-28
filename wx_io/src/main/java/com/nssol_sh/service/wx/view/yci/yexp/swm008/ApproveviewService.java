package com.nssol_sh.service.wx.view.yci.yexp.swm008;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewResultModel;
import com.nssol_sh.entity.wx.view.yci.yexp.swm008.Swm008ApproveViewModel;
import com.nssol_sh.service.wx.view.common.approveview.ApproveViewCommonService;
import com.nssol_sh.util.tools.DateTimeUtil;

/**
 * 请假审批
 * 
 * @author S1mple
 *
 */
@Service("wx.veiw.yci.yexp.swm008.service")
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
	public Swm008ApproveViewModel getData(String output, String uri) throws Exception {
		// 页面数据对象
		Swm008ApproveViewModel result = new Swm008ApproveViewModel();

		// 使用API获取审批数据
		ApproveViewResultModel apiResult = this.getApproveData(output, uri);

		// 将API的返回内容转换成页面格式
		result = this.createViewObject(apiResult, Swm008ApproveViewModel.class);

		// 如果无错
		if (!result.error) {
			// 如果休假开始日不为空
			if (!StringUtils.isEmpty(result.matterUserInfo.vacationStartDate)) {
				result.matterUserInfo.vacationStartDateDsp = DateTimeUtil
						.formatDate(Long.valueOf(result.matterUserInfo.vacationStartDate));
			}

			// 如果休假终了日不为空
			if (!StringUtils.isEmpty(result.matterUserInfo.vacationEndDate)) {
				result.matterUserInfo.vacationEndDateDsp = DateTimeUtil
						.formatDate(Long.valueOf(result.matterUserInfo.vacationEndDate));
			}
		}
		// 返回值
		return result;
	}

}
