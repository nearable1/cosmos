package com.nssol_sh.service.wx.view.ysz.yexp.exp0601;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewResultModel;
import com.nssol_sh.entity.wx.view.ysz.yexp.exp0601.Exp0601AccountingDetailRowModel;
import com.nssol_sh.entity.wx.view.ysz.yexp.exp0601.Exp0601AccountingHeadModel;
import com.nssol_sh.entity.wx.view.ysz.yexp.exp0601.Exp0601ApproveViewModel;
import com.nssol_sh.service.wx.view.common.approveview.ApproveViewCommonService;

@Service("wx.veiw.ysz.yexp.exp0601.service")
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
	public Exp0601ApproveViewModel getData(String output, String uri) throws Exception {
		// 页面数据对象
		Exp0601ApproveViewModel result = new Exp0601ApproveViewModel();

		/*
		 * if (result.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExC.
		 * equals(null)) {
		 * result.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExC =
		 * "0.00"; }
		 */
		// 使用API获取审批数据
		ApproveViewResultModel apiResult = this.getApproveData(output, uri);

		// 将API的返回内容转换成页面格式
		result = this.createViewObject(apiResult, Exp0601ApproveViewModel.class);

		return formatViewData(result);
	}

	private Exp0601ApproveViewModel formatViewData(Exp0601ApproveViewModel model) {
		if (model.matterUserInfo != null) {
			// 是否有记账信息
			model.matterUserInfo.accountingFlag = "0";
			// 本地币种名称
			model.matterUserInfo.currencyName = this.getNameFromList(_localCurrencyCd,
					model.matterUserInfo.currencyList);
			// 记账信息
			if (model.matterUserInfo.accountingCmmDto != null
					&& model.matterUserInfo.accountingCmmDto.accountingHeadList != null
					&& model.matterUserInfo.accountingCmmDto.accountingHeadList.size() > 0) {

				// 是否有记账信息
				model.matterUserInfo.accountingFlag = "1";
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
				for (Exp0601AccountingHeadModel ahm : model.matterUserInfo.accountingCmmDto.accountingHeadList) {
					// 记账明细信息
					if (ahm.detailRowModel != null && ahm.detailRowModel.size() > 0) {
						// 遍历
						for (Exp0601AccountingDetailRowModel adrm : ahm.detailRowModel) {
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
