package com.nssol_sh.service.wx.view.ysw.yexp.exp0501;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewResultModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0501.Exp0501AccountingDetailRowModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0501.Exp0501AccountingHeadModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0501.Exp0501ApproveViewModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0501.Exp0501DetailModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0501.Exp0501LoanModel;
import com.nssol_sh.service.wx.view.common.approveview.ApproveViewCommonService;

@Service("wx.veiw.ysw.yexp.exp0501.service")
public class ApproveviewService extends ApproveViewCommonService {

	/**
	 * 本土货币代码
	 */
	@Value("${wx.io.ychips.local_currency_cd}")
	private String _localCurrencyCd;

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
	public Exp0501ApproveViewModel getData(String output, String uri) throws Exception {

		// 使用API获取审批数据
		ApproveViewResultModel apiResult = this.getApproveData(output, uri);

		// 将API的返回内容转换成页面格式
		Exp0501ApproveViewModel result = this.createViewObject(apiResult, Exp0501ApproveViewModel.class);

		// 返回值

		return formatViewData(result);
	}

	/**
	 * 格式化返回值
	 */
	private Exp0501ApproveViewModel formatViewData(Exp0501ApproveViewModel model) {

		// 如果有用户数据
		if (model.matterUserInfo != null) {
			// 公司名
			model.matterUserInfo.companyName = this.getNameFromList(model.matterUserInfo.companyCd,
					model.matterUserInfo.companyList);
			// 支付方式
			if (model.matterUserInfo.loanDtoList != null && model.matterUserInfo.loanDtoList.size() > 0) {
				// 遍历
				for (Exp0501LoanModel ftm : model.matterUserInfo.loanDtoList) {
					ftm.payMethodName = this.getNameFromList(ftm.payMethodCd, ftm.payMethodList);

				}
			}
			// 其他经费报销一览
			if (model.matterUserInfo.detailDtoList != null && model.matterUserInfo.detailDtoList.size() > 0) {
				// 遍历
				for (Exp0501DetailModel ftm : model.matterUserInfo.detailDtoList) {
					// 经费大分类
					ftm.expBroadCategoryName = this.getNameFromList(ftm.expBroadCategoryCd, ftm.expBroadCategoryList);
					// 经费小分类名
					ftm.expSubCategoryName = this.getNameFromList(ftm.expSubCategoryCd, ftm.expSubCategoryList);
					// 经费项目
					ftm.expItemName = this.getNameFromList(ftm.expItemCd, ftm.expItemList);
					// 事业部
					ftm.buName = this.getNameFromList(ftm.buCd, ftm.buList);
					// 部门
					ftm.deptName = this.getNameFromList(ftm.deptCd, ftm.deptList);
					// 成本中心
					ftm.costCenterName = this.getNameFromList(ftm.costCenterCd, ftm.costCenterList);
					// 币种
					ftm.currencyName = this.getNameFromList(ftm.currencyCd, ftm.currencyList);
					// 税率
					ftm.taxRateName = this.getNameFromList(ftm.taxRateCd, ftm.taxRateList);
					// 支付方式
					ftm.payWayName = this.getNameFromList(ftm.payWayCd, ftm.payWayList);

				}
			}
			// 是否有记账信息
			model.matterUserInfo.accountingFlag = "0";

			// 记账信息
			if (model.matterUserInfo.accountingCmmDto != null
					&& model.matterUserInfo.accountingCmmDto.accountingHeadList != null
					&& model.matterUserInfo.accountingCmmDto.accountingHeadList.size() > 0) {

				// 是否有记账信息
				model.matterUserInfo.accountingFlag = "1";

				// 个人支付和公司信用卡
				if (model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInP == null
						|| "".equals(model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInP)) {
					model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInP = "0.00";
				}
				if (model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInC == null
						|| "".equals(model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInC)) {
					model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInC = "0.00";
				}
				if (model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExP == null
						|| "".equals(model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExP)) {
					model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExP = "0.00";
				}
				if (model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExC == null
						|| "".equals(model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExC)) {
					model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExC = "0.00";
				}
				if (model.matterUserInfo.accountingCmmDto.totalTaxAmountP == null
						|| "".equals(model.matterUserInfo.accountingCmmDto.totalTaxAmountP)) {
					model.matterUserInfo.accountingCmmDto.totalTaxAmountP = "0.00";
				}
				if (model.matterUserInfo.accountingCmmDto.totalTaxAmountC == null
						|| "".equals(model.matterUserInfo.accountingCmmDto.totalTaxAmountC)) {
					model.matterUserInfo.accountingCmmDto.totalTaxAmountC = "0.00";
				}
				// 遍历记账头部信息
				for (Exp0501AccountingHeadModel ahm : model.matterUserInfo.accountingCmmDto.accountingHeadList) {
					// 记账明细信息
					if (ahm.detailRowModel != null && ahm.detailRowModel.size() > 0) {
						// 遍历
						for (Exp0501AccountingDetailRowModel adrm : ahm.detailRowModel) {
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
