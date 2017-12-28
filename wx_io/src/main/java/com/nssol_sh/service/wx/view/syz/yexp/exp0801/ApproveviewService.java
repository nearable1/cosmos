/**
 * 
 */
package com.nssol_sh.service.wx.view.syz.yexp.exp0801;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewResultModel;
import com.nssol_sh.entity.wx.view.syz.yexp.exp0801.Exp0801AccountingDetailRowModel;
import com.nssol_sh.entity.wx.view.syz.yexp.exp0801.Exp0801AccountingHeadModel;
import com.nssol_sh.entity.wx.view.syz.yexp.exp0801.Exp0801ApproveViewModel;
import com.nssol_sh.entity.wx.view.syz.yexp.exp0801.Exp0801EntmDetailModel;
import com.nssol_sh.service.wx.view.common.approveview.ApproveViewCommonService;

/**
 * 交际费报销
 * 
 * @author S1mple
 *
 */
@Service("wx.veiw.syz.yexp.exp0801.service")
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
	public Exp0801ApproveViewModel getData(String output, String uri) throws Exception {

		// 使用API获取审批数据
		ApproveViewResultModel apiResult = this.getApproveData(output, uri);

		// 将API的返回内容转换成页面格式
		Exp0801ApproveViewModel result = this.createViewObject(apiResult, Exp0801ApproveViewModel.class);

		// 返回值

		return formatViewData(result);
	}

	/**
	 * 格式化返回值
	 */
	private Exp0801ApproveViewModel formatViewData(Exp0801ApproveViewModel model) {

		// 如果有用户数据
		if (model.matterUserInfo != null) {
			// 初始化 借款金额标志位
			model.matterUserInfo.writeOffFlag = false;
			// 借款标志位 重新赋值 如果借款金额不是0 即有借款金额
			if (!("0.00".equals(model.matterUserInfo.writeOffAmount) || "0".equals(model.matterUserInfo.writeOffAmount)
					|| (StringUtils.isEmpty(model.matterUserInfo.writeOffAmount)))) {
				model.matterUserInfo.writeOffFlag = true;
			}
			// 本地货币
			model.matterUserInfo.localCurrencyName = this.getNameFromList(_localCurrencyCd,
					model.matterUserInfo.entmCurrencyCdList);
			// 公司名称
			model.matterUserInfo.companyName = this.getNameFromList(model.matterUserInfo.companyCd,
					model.matterUserInfo.companyNameList);
			// 借款金额-支付方式
			model.matterUserInfo.payMethodName = this.getNameFromList(model.matterUserInfo.writeOffPayment,
					model.matterUserInfo.writeOffPaymentList);
			// 业务招待费用明细
			for (Exp0801EntmDetailModel details : model.matterUserInfo.entmDetailModelList) {
				// 备注补充内容标志位初始化
				details.viewFlag = false;
				// 事业部名称
				details.buName = this.getNameFromList(details.buCd, details.buList);
				// 部门
				details.deptName = this.getNameFromList(details.deptCd, details.deptList);
				// 成本中心
				details.costCenterName = this.getNameFromList(details.costCenterCd, details.costCenterList);
				// 币种
				details.entmCurrencyName = this.getNameFromList(details.entmCurrencyCd, details.entmCurrencyCdList);
				// 支付方式名称
				details.modeOfPaymentName = this.getNameFromList(details.modeOfPayment, details.payWayList);
				// 备注类型名称
				details.communicationName = this.getNameFromList(details.communicationType,
						details.communicationTypeList);
				// 备注-其他 是否有补充内容
				if (!StringUtils.isEmpty(details.remarks)) {
					details.viewFlag = true;
				}
			}

			// 财务支付标记
			model.matterUserInfo.accountingFlag = false;
			if (model.matterUserInfo.accountingCmmDto != null
					&& model.matterUserInfo.accountingCmmDto.accountingHeadList != null
					&& model.matterUserInfo.accountingCmmDto.accountingHeadList.size() > 0) {
				// 是否有记账信息
				model.matterUserInfo.accountingFlag = true;
				// 合计相关处理 ---------- Start
				if (StringUtils.isEmpty(model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInP)) {
					model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInP = "0.00";
				}
				if (StringUtils.isEmpty(model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInC)) {
					model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInC = "0.00";
				}
				if (StringUtils.isEmpty(model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExP)) {
					model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExP = "0.00";
				}
				if (StringUtils.isEmpty(model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExC)) {
					model.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExC = "0.00";
				}
				if (StringUtils.isEmpty(model.matterUserInfo.accountingCmmDto.totalTaxAmountP)) {
					model.matterUserInfo.accountingCmmDto.totalTaxAmountP = "0.00";
				}
				if (StringUtils.isEmpty(model.matterUserInfo.accountingCmmDto.totalTaxAmountC)) {
					model.matterUserInfo.accountingCmmDto.totalTaxAmountC = "0.00";
				}
				// 合计相关处理 ---------- End
				// 遍历记账头部信息
				for (Exp0801AccountingHeadModel ahm : model.matterUserInfo.accountingCmmDto.accountingHeadList) {
					// 记账明细信息
					if (ahm.detailRowModel != null && ahm.detailRowModel.size() > 0) {
						// 遍历
						for (Exp0801AccountingDetailRowModel adrm : ahm.detailRowModel) {
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
