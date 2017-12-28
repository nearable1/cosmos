package com.nssol_sh.service.wx.view.ysw.yexp.exp0401;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewResultModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0401.Exp0401AccountingDetailRowModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0401.Exp0401AccountingHeadModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0401.Exp0401ApproveViewModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0401.Exp0401HotelModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0401.Exp0401OtherExpenseModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0401.Exp0401RailwayTicketModel;
import com.nssol_sh.entity.wx.view.ysw.yexp.exp0401.Exp0401ScheduleModel;
import com.nssol_sh.service.wx.view.common.approveview.ApproveViewCommonService;

@Service("wx.veiw.ysw.yexp.exp0401.service")

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
	public Exp0401ApproveViewModel getData(String output, String uri) throws Exception {

		// 使用API获取审批数据
		ApproveViewResultModel apiResult = this.getApproveData(output, uri);

		// 将API的返回内容转换成页面格式
		Exp0401ApproveViewModel result = this.createViewObject(apiResult, Exp0401ApproveViewModel.class);

		// 返回值

		return formatViewData(result);
	}

	/**
	 * 格式化返回值
	 */
	private Exp0401ApproveViewModel formatViewData(Exp0401ApproveViewModel model) {

		// 如果有用户数据
		if (model.matterUserInfo != null) {
			// 国内海外区分名
			model.matterUserInfo.overseasFlagName = this.getNameFromList(model.matterUserInfo.overseasFlag,
					model.matterUserInfo.offshoreList);

			// 公司名
			model.matterUserInfo.companyName = this.getNameFromList(model.matterUserInfo.companyCd,
					model.matterUserInfo.companyList);

			// 事业部名
			model.matterUserInfo.buName = this.getNameFromList(model.matterUserInfo.buCd, model.matterUserInfo.buList);

			// 部门名
			model.matterUserInfo.deptName = this.getNameFromList(model.matterUserInfo.deptCd,
					model.matterUserInfo.deptList);

			// 成本中心名
			model.matterUserInfo.costCenterName = this.getNameFromList(model.matterUserInfo.costCenterCd,
					model.matterUserInfo.costCenterList);

			// 本地货币币种
			model.matterUserInfo.currencyName = this.getNameFromList(_localCurrencyCd,
					model.matterUserInfo.currencyList);

			// 支付方式
			model.matterUserInfo.payMethodName = this.getNameFromList(model.matterUserInfo.payMethod,
					model.matterUserInfo.payMethodList);

			// 本土货币代码
			model.matterUserInfo.localCurrencyCd = _localCurrencyCd;

			// 本土货币名
			model.matterUserInfo.localCurrencyName = this.getNameFromList(_localCurrencyCd,
					model.matterUserInfo.currencyList);

			// 出差日程一览
			if (model.matterUserInfo.scheduleDtoList != null && model.matterUserInfo.scheduleDtoList.size() > 0) {
				// 遍历
				for (Exp0401ScheduleModel sm : model.matterUserInfo.scheduleDtoList) {
					// 出差区分名
					sm.bizTripDivName = this.getNameFromList(sm.bizTripDivCd, sm.scheduleDivList);

					// 距离区分名
					sm.distanceTypeName = this.getNameFromList(sm.distanceTypeCd, sm.distanceTypeList);

				}
			}

			// 个人预订
			if (model.matterUserInfo.railwayTicketDtoList != null
					&& model.matterUserInfo.railwayTicketDtoList.size() > 0) {
				// 遍历
				for (Exp0401RailwayTicketModel rtm : model.matterUserInfo.railwayTicketDtoList) {
					// 交通工具名
					rtm.transportTypeName = this.getNameFromList(rtm.transportType, rtm.transportTypeList);

					// 币种名
					rtm.ticketCurrencyName = this.getNameFromList(rtm.ticketCurrencyCd, rtm.currencyList);

					// 支付方式名称
					rtm.ticketSettlPayWayName = this.getNameFromList(rtm.ticketSettlPayWay, rtm.settlPayWayList);

				}
			}

			// 住宿一览
			if (model.matterUserInfo.hotelDtoList != null && model.matterUserInfo.hotelDtoList.size() > 0) {
				// 遍历
				for (Exp0401HotelModel hm : model.matterUserInfo.hotelDtoList) {
					// 住宿地区名
					hm.destinationDivName = this.getNameFromList(hm.destinationDiv, hm.destinationDivList);

					// 住宿支付方式
					hm.settlPayWayName = this.getNameFromList(hm.hotelsettlPayWay, hm.settlPayWayList);
					// 税率名
					hm.taxRateName = this.getNameFromList(hm.taxRateCd, hm.taxRateList);
					// 币种名
					hm.hotelCurrencyName = this.getNameFromList(hm.hotelCurrencyCd, hm.currencyList);

				}
			}
			// 其他出差经费报销一览
			if (model.matterUserInfo.otherExpenseDtoList != null
					&& model.matterUserInfo.otherExpenseDtoList.size() > 0) {
				// 遍历
				for (Exp0401OtherExpenseModel hm : model.matterUserInfo.otherExpenseDtoList) {
					// 经费项目名
					hm.expItemName = this.getNameFromList(hm.expItemCd, hm.expItemList);
					// 支付方法
					hm.modeOfPaymentName = this.getNameFromList(hm.modeOfPayment, hm.settlPayWayList);
					// 经费大分类
					hm.expBroadCategoryName = this.getNameFromList(hm.expBroadCategoryCd, hm.expBroadCategoryList);
					// 经费小分类
					hm.expSubCategoryName = this.getNameFromList(hm.expSubCategoryCd, hm.expSubCategoryList);
					// 币种
					hm.otherExpenseCurrencyName = this.getNameFromList(hm.otherExpenseCurrencyCd, hm.currencyList);

				}
			}
			// 是否有记账信息
			model.matterUserInfo.accountingFlag = "0";

			// 希望支付日无
			// model.matterUserInfo.accountingCmmDto.hopeDisplay = false;

			// 记账信息
			if (model.matterUserInfo.accountingCmmDto != null
					&& model.matterUserInfo.accountingCmmDto.accountingHeadList != null
					&& model.matterUserInfo.accountingCmmDto.accountingHeadList.size() > 0) {
				// 是否有记账信息
				model.matterUserInfo.accountingFlag = "1";

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
				for (Exp0401AccountingHeadModel ahm : model.matterUserInfo.accountingCmmDto.accountingHeadList) {
					// 记账明细信息
					if (ahm.detailRowModel != null && ahm.detailRowModel.size() > 0) {
						// 遍历
						for (Exp0401AccountingDetailRowModel adrm : ahm.detailRowModel) {
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
