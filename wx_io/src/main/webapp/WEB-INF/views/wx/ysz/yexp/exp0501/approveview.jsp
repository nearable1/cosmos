<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title></title>
<link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.css" />
<link rel="stylesheet"
	href="${RESOURCE_PATH}/css/weui.io.css${JS_SUFFIX}" />
<script src="${RESOURCE_PATH}/js/common/definer.js${JS_SUFFIX}"></script>
<script src="${RESOURCE_PATH}/js/common/zepto.min.js"></script>
<script src="${RESOURCE_PATH}/js/wx/common/cmnUtil.js${JS_SUFFIX}"></script>
<script src="${RESOURCE_PATH}/js/common/weui.io.js${JS_SUFFIX}"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/cmnMsg.js${JS_SUFFIX}"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/ysz/yexp/exp0501/approveview.js${JS_SUFFIX}"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/ysz/yexp/common/applyUserInfo.js${JS_SUFFIX}"></script>
<style type="text/css">
.label_th {
	text-align: center;
	color: #888;
}

.label_td {
	text-align: center;
	color: blue;
}
</style>
<script type="text/javascript">
	// 设置页面区域
	ychips.wxio.CmnUtil
			.setLocale('${requestScope.pageData.accountInfo.localId}');
</script>
</head>
<body ontouchstart>
	<div class="container" id="container"></div>
	<script type="text/html" id="tpl_home">
	<div class="page">
		<div class="weui-tab__panel">
			<!-- 申请者信息 -->
			<%@include file="/WEB-INF/views/wx/ysz/yexp/common/applyUserInfo.jsp"%>
			<div class="weui-cells__title" ins="ysz.yexp.exp0501.approveview"
				inm="basicInfoTitle"></div>
			<!-- 基本信息 -->
			<div class="weui-cells weui-cells_form">
				<!-- 申请日 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
							inm="applyDate"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input"
							value="${requestScope.pageData.matterUserInfo.applyDate}"
							readonly="readonly" style="color: grey;" />
					</div>
				</div>
				<!-- 项目代码 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
							inm="projectCd"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input"
							value="${requestScope.pageData.matterUserInfo.projectCd}"
							readonly="readonly" style="color: grey;" />
					</div>
				</div>
				<!-- 项目名 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
							inm="projectName"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input"
							value="${requestScope.pageData.matterUserInfo.projectName}"
							readonly="readonly" style="color: grey;" />
					</div>
				</div>
				<!-- 项目负责人 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
							inm="pmName"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input"
							value="${requestScope.pageData.matterUserInfo.pmName}"
							readonly="readonly" style="color: grey;" />
					</div>
				</div>
				<!-- 目的 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
							inm="ReimbursePurpose"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input"
							value="${requestScope.pageData.matterUserInfo.reimbursePurpose}"
							readonly="readonly" style="color: grey;" />
					</div>
				</div>
				<!-- 公司名称 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
							inm="companyName"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input"
							value="${requestScope.pageData.matterUserInfo.companyName}"
							readonly="readonly" style="color: grey;" />
					</div>
				</div>
			</div>
			<!-- 借款冲销金额 -->
			<c:forEach
				items="${requestScope.pageData.matterUserInfo.loanDtoList}"
				var="dto">
				<c:if
					test="${requestScope.pageData.matterUserInfo.loanDtoList.size() > 0}">
					<c:if test="${dto.checked.equals('checked')}">
						<div class="weui-cells__title" ins="ysz.yexp.exp0501.approveview"
							inm="writeOffAmount"></div>
						<div class="weui-cells weui-cells_form">
							<div class="wxio-record">
								<a class="weui-cell weui-cell_access" href="javascript:;">
									<div class="weui-cell__bd">
										<p>${dto.applyDate}-${dto.amount}${dto.currencyName}-(${dto.payMethodName})</p>
									</div>
									<div class="weui-cell__ft"></div>
								</a>
								<div class="weui-cells wxio-record-content">
									<div class="weui-cell">
										<br />
										<div class="wxio_subDevider_class"></div>
									</div>
									<!-- 案件号 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="matterId"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dto.matterId}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>

									<!-- 申请日 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="applyDate"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dto.applyDate}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 支付日 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="payDate"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dto.payDate}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 报销支付金额 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="paymentAmount"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dto.amount}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 币种 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingCurrencyName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dto.currencyName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 支付方式 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="payWayName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dto.payMethodName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 说明 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="shuoming"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dto.purpose}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</c:if>
			</c:forEach>
			<!-- 有无其他经费报销 -->
			<div class="weui-cells__title" ins="ysz.yexp.exp0501.approveview"
				inm="hasEexpenseFlag">
				<c:if
					test="${requestScope.pageData.matterUserInfo.hasEexpenseFlag == '0'}">
					<i class="weui-icon-circle"></i>
				</c:if>
				<c:if
					test="${requestScope.pageData.matterUserInfo.hasEexpenseFlag == '1'}">
					<i class="weui-icon-success"></i>
				</c:if>
			</div>
			<div class="weui-cells weui-cells_form">
				<c:forEach
					items="${requestScope.pageData.matterUserInfo.detailDtoList}" var="dto">
					<!-- 其他经费一览 -->
					<div class="wxio-record">
						<a class="weui-cell weui-cell_access" href="javascript:;">
							<div class="weui-cell__bd">
								<p>${dto.expBroadCategoryName}-${dto.expSubCategoryName}-${dto.expItemName}-${dto.amount}${dto.currencyName}</p>
							</div>
							<div class="weui-cell__ft"></div>
						</a>
						<div class="weui-cells wxio-record-content">
							<!-- sub_devider -->
							<div class="weui-cell">
								<br />
								<div class="wxio_subDevider_class"></div>
							</div>
							<!-- 经费大分类 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="accountingExpBroadCategoryName"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.expBroadCategoryName}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 经费小分类 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="accountingExpSubCategoryName"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.expSubCategoryName}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 经费项目 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="accountingExpItemName"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.expItemName}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 事业部 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="buName"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.buName}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!--部门  -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="deptName"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.deptName}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 成本中心 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="costCenterName"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.costCenterName}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 金额 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="amount"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.amount}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 币种 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="accountingCurrencyName"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.currencyName}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 汇率 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="accountingExchangeRate"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.exchangeRate}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 税率 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="accountingTaxRate"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.taxRateName}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 发票 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="accountingInvoice"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.invoice}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 支付方式 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="payWayName"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.payWayName}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 合作伙伴 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="accountingPartner"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.partnerCd}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
							<!-- 用途 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
										inm="purpose"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" value="${dto.remarks}"
										readonly="readonly" style="color: grey;" />
								</div>
							</div>
                            <!-- 购入申请No-->
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="ysz.yexp.exp0501.approveview"
                                        inm="requireCd"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input" value="${dto.requireCd}"
                                        readonly="readonly" style="color: grey;" />
                                </div>
                            </div>
						</div>
					</div>
				</c:forEach>
			</div>
			<!-- 其他经费报销合计 -->
			<div class="weui-cells__title" ins="ysz.yexp.exp0501.approveview"
				inm="Total"></div>
			<div class="weui-cells weui-cells_form">
				<!-- 借款冲销金额 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
							inm="writeOffAmount"></label>
					</div>
					&nbsp;
					<div class="weui-cell__bd">
						<input class="weui-input"
							value="${requestScope.pageData.matterUserInfo.writeOffAmount}${requestScope.pageData.matterUserInfo.detailDtoList[0].currencyList[0].label}"
							readonly="readonly" style="color: grey;" />
					</div>
				</div>
				<!-- 报销支付金额 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
							inm="paymentAmount"></label>
					</div>
					&nbsp;
					<div class="weui-cell__bd">
						<input class="weui-input"
							value="${requestScope.pageData.matterUserInfo.paymentAmount}${requestScope.pageData.matterUserInfo.detailDtoList[0].currencyList[0].label}"
							readonly="readonly" style="color: grey;" />
					</div>
				</div>
				<!-- 公司信用卡 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
							inm="companyAmount"></label>
					</div>
					&nbsp;
					<div class="weui-cell__bd">
						<input class="weui-input"
							value="${requestScope.pageData.matterUserInfo.companyAmount}${requestScope.pageData.matterUserInfo.detailDtoList[0].currencyList[0].label}"
							readonly="readonly" style="color: grey;" />
					</div>
				</div>
				<!-- 报销申请总金额 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
							inm="detailTotal"></label>
					</div>
					&nbsp;
					<div class="weui-cell__bd">
						<input class="weui-input wxio_strong_class"
							value="${requestScope.pageData.matterUserInfo.detailTotal}${requestScope.pageData.matterUserInfo.detailDtoList[0].currencyList[0].label}"
							readonly="readonly" />
					</div>
				</div>
			</div>
			<!-- 财务支付模块 -->
			<c:if
				test="${requestScope.pageData.matterUserInfo.accountingFlag == '1'}">
				<c:choose>
					<c:when
						test="${requestScope.pageData.matterUserInfo.accountingCmmDto.hopeDisplay}">
						<div class="weui-cells__title" ins="ysz.yexp.exp0501.approveview"
							inm="accountingInfo1"></div>
					</c:when>
					<c:otherwise>
						<div class="weui-cells__title" ins="ysz.yexp.exp0501.approveview"
							inm="accountingInfo2"></div>
					</c:otherwise>
				</c:choose>
				<div class="weui-cells weui-cells_form">
					<c:forEach
						items="${requestScope.pageData.matterUserInfo.accountingCmmDto.accountingHeadList}"
						var="dto">
						<div class="wxio-record">
							<a class="weui-cell weui-cell_access" href="javascript:;">
								<div class="weui-cell__bd">
									<c:choose>
										<c:when
											test="${requestScope.pageData.matterUserInfo.accountingCmmDto.hopeDisplay}">
											<p>${dto.bookDate}／${dto.hopePayDate}／${dto.voucherHeader}</p>
										</c:when>
										<c:otherwise>
											<p>${dto.bookDate}／${dto.voucherHeader}</p>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="weui-cell__ft"></div>
							</a>
							<div class="weui-cells wxio-record-content">
								<c:forEach items="${dto.detailRowModel}" var="dtl">
									<!-- 分割线 -->
									<div class="weui-cell">
										<br />
										<div class="wxio_subDevider_class"></div>
									</div>
									<!-- 借贷区分 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingDebtorFlagName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input wxio_input_class"
												value="${dtl.debtorFlagName}" readonly="readonly" />
										</div>
									</div>
									<!-- 经费大分类 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingExpBroadCategoryName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.expBroadCategoryName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 经费小分类 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingExpSubCategoryName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.expSubCategoryName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 经费项目 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingExpItemName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.expItemName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 科目 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingSubjectName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input"
												value="${dtl.accountingSubjectName}" readonly="readonly"
												style="color: grey;" />
										</div>
									</div>
									<!-- 含税金额 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingPaymentAmountTaxIn"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.paymentAmountTaxIn}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 税率 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingTaxRate"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input"
												value="${dtl.taxRateName}（${dtl.inputTaxName}）"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 费用金额 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingPaymentAmountTaxOut"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.paymentAmountTaxOut}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 税额 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingTaxAmount"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.taxAmount}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 币种 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingCurrencyName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.currencyName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 汇率 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingExchangeRate"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.origExchangeRate}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 成本中心 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingCostCenterCd"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.costCenterCd}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 发票 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingInvoice"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.invoice}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 合作伙伴 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingPartner"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.partnerCd}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 行文本摘要 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
												inm="accountingSummary"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.summary}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</div>

				<!-- 合计(个人支付／公司信用卡) -->
				<div class="weui-cells__title" ins="ysz.yexp.exp0501.approveview"
					inm="payment"></div>
				<div class="weui-cells weui-cells_form">
					<!-- 含税金额合计 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
								inm="TotalTax"></label>
						</div>
						&nbsp;
						<div class="weui-cell__bd">
							<input class="weui-input wxio_input_class"
								value="${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExP}${requestScope.pageData.matterUserInfo.accountingCmmDto.currencyName}／${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExC}${requestScope.pageData.matterUserInfo.accountingCmmDto.currencyName}
                            "
								readonly="readonly" style="color: grey;" />
						</div>

					</div>
					<!-- 费用金额合计 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
								inm="TotalCost"></label>
						</div>
						&nbsp;
						<div class="weui-cell__bd">
							<input class="weui-input wxio_input_class"
								value="${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInP}${requestScope.pageData.matterUserInfo.accountingCmmDto.currencyName}／${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInC}${requestScope.pageData.matterUserInfo.accountingCmmDto.currencyName}
                            "
								readonly="readonly" style="color: grey;" />
						</div>

					</div>
					<!-- 税额合计 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0501.approveview"
								inm="Totaltaxamount"></label>
						</div>
						&nbsp;
						<div class="weui-cell__bd">
							<input class="weui-input wxio_strong_class"
								value="${requestScope.pageData.matterUserInfo.accountingCmmDto.totalTaxAmountP}${requestScope.pageData.matterUserInfo.accountingCmmDto.currencyName}／${requestScope.pageData.matterUserInfo.accountingCmmDto.totalTaxAmountC}${requestScope.pageData.matterUserInfo.accountingCmmDto.currencyName}
                            "
								readonly="readonly" />
						</div>

					</div>
				</div>
			</c:if>

			<br />
			<!-- 按钮 -->
			<c:if
				test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
				<div>
					<div class="weui-mask" id="pageMask" style="display: none"></div>
					<!-- 财务支付  标志位存储 隐藏  1为有支付-->
					<input type="hidden" name="accountingFlag"
						value="${requestScope.pageData.matterUserInfo.accountingFlag}" />
					<div class="weui-actionsheet" id="processActionsheet">
						<div class="weui-actionsheet__title">
							<p class="weui-actionsheet__title-text"
								ins="common.workflow.cmnMsg" inm="processActionSheetTitle"></p>
						</div>
						<div class="weui-actionsheet__menu">
							<c:forEach
								items="${requestScope.pageData.matterSystemInfo.processType}"
								var="entry">
								<a class="js_item" data-id="${entry.key}" href="javascript:;">
									<div class="weui-actionsheet__cell"
										style="color: rgb(4, 190, 2); background: rgb(255, 255, 255)">
										<label>${entry.value}</label>
									</div>
								</a>
							</c:forEach>
						</div>
						<div class="weui-actionsheet__action">
							<div class="weui-actionsheet__cell" id="btnCancel">
								<label ins="common.workflow.cmnMsg" inm="cancel"></label>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</div>
		<div class="weui-tabbar">
			<a href="javascript:;" class="weui-tabbar__item" id="btnHistory">
				<img src="${RESOURCE_PATH}/img/icon_nav_history.png" alt=""
				class="weui-tabbar__icon">
				<p class="weui-tabbar__label" ins="common.workflow.cmnMsg"
					inm="history"></p>
			</a>
			<c:if
				test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
				<a href="javascript:;" class="weui-tabbar__item weui-bar__item_on"
					id="btnProcess"> <img
					src="${RESOURCE_PATH}/img/icon_nav_process.png" alt=""
					class="weui-tabbar__icon">
					<p class="weui-tabbar__label" ins="common.workflow.cmnMsg"
						inm="process"></p>
				</a>
			</c:if>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			// 初始化按钮
			if ($('#btnProcess').length > 0) {
				ychips.wxio.CmnUtil.initAtcionSheet($('#pageMask'),
						$('#processActionsheet'), $('#btnProcess'));
			}
			// 初始化履历按钮
			ychips.wxio.CmnUtil.initHistory($("#btnHistory"));

			// 展开关闭
			$("div.wxio-record").find("a.weui-cell_access").click(
					function() {
						// 内容
						var record = $(this).closest(
								"div.wxio-record.record_show");
						if (record && record.length > 0) {
							$(this).closest("div.wxio-record").removeClass(
									"record_show");
						} else {
							$(this).closest("div.wxio-record").addClass(
									"record_show");
						}
					});
		});
	</script>
	</script>
	<c:if
		test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
		<%@include
			file="/WEB-INF/views/wx/common/workflow/process/approve.jsp"%>
		<%@include file="/WEB-INF/views/wx/common/workflow/process/deny.jsp"%>
		<%@include
			file="/WEB-INF/views/wx/common/workflow/process/reserve.jsp"%>
		<%@include
			file="/WEB-INF/views/wx/common/workflow/process/reservecancel.jsp"%>
		<%@include
			file="/WEB-INF/views/wx/common/workflow/process/sendback.jsp"%>
		<%@include file="/WEB-INF/views/wx/common/workflow/success.jsp"%>
		<%@include file="/WEB-INF/views/wx/common/workflow/error.jsp"%>
	</c:if>
	<%@include file="/WEB-INF/views/wx/common/workflow/unit/history.jsp"%>
</body>
</html>
