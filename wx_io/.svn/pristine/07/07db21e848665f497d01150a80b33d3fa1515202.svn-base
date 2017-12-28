/**
 * 
 */
package com.nssol_sh.service.wx.view.ysz.yexp.exp0701;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewResultModel;
import com.nssol_sh.entity.wx.view.ysz.yexp.exp0701.Exp0701AccountingDetailRowModel;
import com.nssol_sh.entity.wx.view.ysz.yexp.exp0701.Exp0701AccountingHeadModel;
import com.nssol_sh.entity.wx.view.ysz.yexp.exp0701.Exp0701ApproveViewModel;
import com.nssol_sh.entity.wx.view.ysz.yexp.exp0701.Exp0701EntmDetailModel;
import com.nssol_sh.service.wx.view.common.approveview.ApproveViewCommonService;

/**
 * 交际费申请
 * 
 * @author S1mple
 *
 */
@Service("wx.veiw.ysz.yexp.exp0701.service")
public class ApproveviewService extends ApproveViewCommonService {

	/**
	 * 本土货币代码
	 */
	@Value("${wx.io.ychips.local_currency_cd}")
	private String _localCurrencyCd;

	/**
	 * 
	 */
	public static final String COMMUNICATE_TYPE_REMARK = "CCT06";

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
	public Exp0701ApproveViewModel getData(String output, String uri) throws Exception {

		// 使用API获取审批数据
		ApproveViewResultModel apiResult = this.getApproveData(output, uri);
		// 将API的返回内容转换成页面格式
		Exp0701ApproveViewModel result = this.createViewObject(apiResult, Exp0701ApproveViewModel.class);

		// 返回值

		return formatViewData(result);
	}

	/**
	 * 格式化返回值
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private Exp0701ApproveViewModel formatViewData(Exp0701ApproveViewModel model) throws UnsupportedEncodingException {

		// 如果有用户数据
		if (model.matterUserInfo != null) {
			// 公司名称
			model.matterUserInfo.companyName = this.getNameFromList(model.matterUserInfo.companyCd,
					model.matterUserInfo.companyNameList);
			// 借款金额-支付方式
			model.matterUserInfo.payMethodName = this.getNameFromList(model.matterUserInfo.payMethod,
					model.matterUserInfo.writeOffPaymentList);
			// 总计-币种
			model.matterUserInfo.localCurrencyName = this.getNameFromList(_localCurrencyCd,
					model.matterUserInfo.entmCurrencyCdList);

			// 业务招待费用明细
			for (Exp0701EntmDetailModel details : model.matterUserInfo.entmDetailModelList) {
				// 备注内容是否显示初始化
				details.remarkFlag = false;
				// 事业部名称
				details.buName = this.getNameFromList(details.buCd, details.buList);
				// 部门
				details.deptName = this.getNameFromList(details.deptCd, details.deptList);
				// 成本中心
				details.costCenterName = this.getNameFromList(details.costCenterCd, details.costCenterList);
				// 币种
				details.entmCurrencyName = this.getNameFromList(details.entmCurrencyCd, details.entmCurrencyCdList);
				// 备注类型名称
				details.communicationName = this.getNameFromList(details.communicationType,
						details.communicationTypeList);
				// 备注类型判断-其他-需要显示内容
				if (COMMUNICATE_TYPE_REMARK.equals(details.communicationType)) {
					details.remarkFlag = true;
				}
			}

			// 财务支付标记
			model.matterUserInfo.accountingFlag = false;
			if (model.matterUserInfo.accountingCmmDto != null
					&& model.matterUserInfo.accountingCmmDto.accountingHeadList != null
					&& model.matterUserInfo.accountingCmmDto.accountingHeadList.size() > 0) {
				// 是否有记账信息
				model.matterUserInfo.accountingFlag = true;

				// 遍历记账头部信息
				for (Exp0701AccountingHeadModel ahm : model.matterUserInfo.accountingCmmDto.accountingHeadList) {
					// 记账明细信息
					if (ahm.detailRowModel != null && ahm.detailRowModel.size() > 0) {
						// 遍历
						for (Exp0701AccountingDetailRowModel adrm : ahm.detailRowModel) {
							// 经费大分类名
							adrm.expBroadCategoryName = this.getNameFromList(adrm.expBroadCategoryCd,
									adrm.expBroadCategoryList);

							// 经费小分类名
							adrm.expSubCategoryName = this.getNameFromList(adrm.expSubCategoryCd,
									adrm.expSubCategoryList);

							// 经费项目名
							adrm.expItemName = this.getNameFromList(adrm.expItemCd, adrm.expItemList);

							// 税代码名
							adrm.taxRateName = this.getNameFromList(adrm.taxRateCd, adrm.taxRateList);

							// 税项目名
							adrm.inputTaxName = this.getNameFromList(null, adrm.inputTaxList);

							// 币种名
							adrm.currencyName = this.getNameFromList(adrm.currencyCd, adrm.currencyList);

							// 如果币种名为空
							if (StringUtils.isEmpty(adrm.currencyName)) {
								adrm.currencyName = this.getNameFromList(_localCurrencyCd, adrm.currencyList);
							}
						}
					}
				}
			}
		}
		return model;
	}
}
