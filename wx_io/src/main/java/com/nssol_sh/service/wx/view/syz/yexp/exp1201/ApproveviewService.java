package com.nssol_sh.service.wx.view.syz.yexp.exp1201;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nssol_sh.entity.wx.view.common.approveview.ApproveViewResultModel;
import com.nssol_sh.entity.wx.view.syz.yexp.exp1201.Exp1201AccountingDetailRowModel;
import com.nssol_sh.entity.wx.view.syz.yexp.exp1201.Exp1201AccountingHeadModel;
import com.nssol_sh.entity.wx.view.syz.yexp.exp1201.Exp1201ApproveViewModel;
import com.nssol_sh.service.wx.view.common.approveview.ApproveViewCommonService;

@Service("wx.veiw.syz.yexp.exp1201.service")
public class ApproveviewService extends ApproveViewCommonService {

	/**
	 * 本土货币代码
	 */
	@Value("${wx.io.ychips.local_currency_cd}")
	private String _localCurrencyCd;

	/**
	 * 获取员工借款页面数据
	 * 
	 * @param output
	 *            POST体
	 * @param uri
	 *            请求路径
	 * @return 页面数据
	 * @throws Exception
	 */
	public Exp1201ApproveViewModel getData(String output, String uri) throws Exception {
		// 页面数据对象
		Exp1201ApproveViewModel result = new Exp1201ApproveViewModel();
		// 使用API获取审批数据
		ApproveViewResultModel apiResult = this.getApproveData(output, uri);

		// 将API的返回内容转换成页面格式
		result = this.createViewObject(apiResult, Exp1201ApproveViewModel.class);

		// 返回值

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
	public Exp1201ApproveViewModel forMatterData(Exp1201ApproveViewModel info) throws Exception {
		// 如果有用户数据
		if (info.matterUserInfo != null) {
			/**
			 * 公司名称
			 */
			info.matterUserInfo.companyName = this.getNameFromList("", info.matterUserInfo.getCompanyList());
			/**
			 * 支付方式
			 */
			info.matterUserInfo.payMethodName = this.getNameFromList(info.matterUserInfo.payMethod,
					info.matterUserInfo.getPayMethodList());
					/**
					 * 金额(币种)
					 */

			/**
			 * 财务支付标志
			 */
			// 初始化
			info.matterUserInfo.accountingFlag = "0";
			// 如果有支付信息，更改标志位
			if (info.matterUserInfo.accountingCmmDto != null
					&& info.matterUserInfo.accountingCmmDto.accountingHeadList != null
					&& info.matterUserInfo.accountingCmmDto.accountingHeadList.size() > 0) {

				info.matterUserInfo.accountingFlag = "1";
				/**
				 * 希望支付日需要显示.
				 */
				info.matterUserInfo.accountingCmmDto.hopeDisplay = true;
				// 解析财务经费审批
				// 遍历记账头部信息
				for (Exp1201AccountingHeadModel ahm : info.matterUserInfo.accountingCmmDto.accountingHeadList) {
					// 记账明细信息
					if (ahm.detailRowModel != null && ahm.detailRowModel.size() > 0) {
						// 遍历
						for (Exp1201AccountingDetailRowModel adrm : ahm.detailRowModel) {
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
		return info;
	}
}
