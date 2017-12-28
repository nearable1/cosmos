<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title></title>
<link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.css" />
<link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.io.css${JS_SUFFIX}" />
<script src="${RESOURCE_PATH}/js/common/definer.js${JS_SUFFIX}"></script>
<script src="${RESOURCE_PATH}/js/common/zepto.min.js"></script>
<script src="${RESOURCE_PATH}/js/wx/common/cmnUtil.js${JS_SUFFIX}"></script>
<script src="${RESOURCE_PATH}/js/common/weui.io.js${JS_SUFFIX}"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/cmnMsg.js${JS_SUFFIX}"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/ysz/yexp/exp0801/approveview.js${JS_SUFFIX}"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/ysz/yexp/common/applyUserInfo.js${JS_SUFFIX}"></script>
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
			<!-- 01.申请者信息 Start-->
			<%@include file="/WEB-INF/views/wx/ysz/yexp/common/applyUserInfo.jsp"%>
			<!-- 申请者信息 End-->
			<!-- 02.基本信息 -->
			<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
				inm="commonMsg"></div>
			<div class="weui-cells__form weui-cells">
				<!-- 申请日 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="applyDate"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.applyDate}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 项目代码 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="projectCd"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.projectCd}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 项目名称 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="projectName"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.projectName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 项目负责人 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="pmCode"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.pmName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 招待对象公司名称 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="customerCompanyName"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.customerCompanyName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 公司名称 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="companyCd"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.companyName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 与我方关系 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="relationWithUs"></label>
					</div>
					<div class="weui-cell__bd">
						<!-- 1.客户；2.供应商；3.集团公司内部；4.其他 -->
						<c:choose>
							<c:when
								test="${requestScope.pageData.matterUserInfo.relationWithUs == '0'}">
								<p class="weui-input wxio_input_class"
									ins="ysz.yexp.exp0801.approveview" inm="relationWithUs1">
								<p>
							</c:when>
							<c:when
								test="${requestScope.pageData.matterUserInfo.relationWithUs == '1'}">
								<p class="weui-input wxio_input_class"
									ins="ysz.yexp.exp0801.approveview" inm="relationWithUs2">
								<p>
							</c:when>
							<c:when
								test="${requestScope.pageData.matterUserInfo.relationWithUs == '2'}">
								<p class="weui-input wxio_input_class"
									ins="ysz.yexp.exp0801.approveview" inm="relationWithUs3">
								<p>
							</c:when>
							<c:when
								test="${requestScope.pageData.matterUserInfo.relationWithUs == '4'}">
								<p class="weui-input wxio_input_class"
									ins="ysz.yexp.exp0801.approveview" inm="relationWithUs4">
								<p>
							</c:when>
						</c:choose>
					</div>
				</div>
			</div>
			<c:if
				test="${requestScope.pageData.matterUserInfo.relationWithUs == '4'}">
				<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
					inm="remark"></div>
				<div class="weui-cells_form weui-cells">
					<!-- 与我方关系／其他／客户 -->
					<div class="weui-cell">
						<div class="weui-cell__bd">
							<textarea class="weui-textarea wxio_textarea_class" rows="3"
								readonly="readonly">${requestScope.pageData.matterUserInfo.relationWithUsRemarks}</textarea>
						</div>
					</div>
				</div>
			</c:if>
			<div class="weui-cells__form weui-cells">
				<!-- 目的 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="purpose"></label>
					</div>
					<div class="weui-cell__bd">
						<!-- 1.业务宣传（仅限于营业部门）；2.答谢 ；3.祝贺 ；4.其他 -->
						<c:choose>
							<c:when
								test="${requestScope.pageData.matterUserInfo.purpose == '0'}">
								<p class="weui-input wxio_input_class"
									ins="ysz.yexp.exp0801.approveview" inm="purpose1">
								<p>
							</c:when>
							<c:when
								test="${requestScope.pageData.matterUserInfo.purpose == '1'}">
								<p class="weui-input wxio_input_class"
									ins="ysz.yexp.exp0801.approveview" inm="purpose2">
								<p>
							</c:when>
							<c:when
								test="${requestScope.pageData.matterUserInfo.purpose == '2'}">
								<p class="weui-input wxio_input_class"
									ins="ysz.yexp.exp0801.approveview" inm="purpose3">
								<p>
							</c:when>
							<c:when
								test="${requestScope.pageData.matterUserInfo.purpose == '3'}">
								<p class="weui-input wxio_input_class"
									ins="ysz.yexp.exp0801.approveview" inm="purpose4">
								<p>
							</c:when>
						</c:choose>
					</div>
				</div>
			</div>
			<c:if test="${requestScope.pageData.matterUserInfo.purpose == '3'}">
				<!-- 目的／其他／备注 -->
				<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
					inm="purposeRemark"></div>
				<div class="weui-cells__form weui-cells">
					<div class="weui-cell">
						<div class="weui-cell__bd">
							<textarea class="weui-textarea wxio_textarea_class" rows="3"
								readonly="readonly">${requestScope.pageData.matterUserInfo.purposeRemarks}</textarea>
						</div>
					</div>
				</div>
			</c:if>
			<!-- 借款金额 Title -->
			<c:if test="${requestScope.pageData.matterUserInfo.writeOffFlag}">
				<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
					inm="writeOffAmount"></div>
				<div class="weui-cells weui-cells__form">
					<!-- 金额 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
								inm="amount"></label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input wxio_input_class"
								value="${requestScope.pageData.matterUserInfo.writeOffAmount}"
								readonly="readonly" />
						</div>
					</div>
					<!-- 币种 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
								inm="ticketCurrencyCd"></label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input wxio_input_class"
								value="${requestScope.pageData.matterUserInfo.entmCurrencyName}"
								readonly="readonly" />
						</div>
					</div>
					<!-- 支付方式 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
								inm="payMethod"></label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input wxio_input_class"
								value="${requestScope.pageData.matterUserInfo.payMethodName}"
								readonly="readonly" />
						</div>
					</div>
					<!-- 希望支付日 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
								inm="hopePayDate"></label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input wxio_input_class"
								value="${requestScope.pageData.matterUserInfo.hopePayDate}"
								readonly="readonly" />
						</div>
					</div>
				</div>
			</c:if>
			<!-- 有业务招待 -->
			<c:if
				test="${requestScope.pageData.matterUserInfo.hasEntmFlag == '1'}">
				<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
					inm="serviceExpense">
					<i class="weui-icon-success"></i>
				</div>
				<div class="weui-cells__form weui-cells">
					<!-- 付款金额 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
								inm="budgetAmmount"></label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input wxio_input_class"
								value="${requestScope.pageData.matterUserInfo.entmTotalAmount}${requestScope.pageData.matterUserInfo.localCurrencyName}"
								readonly="readonly" />
						</div>
					</div>
					<!-- 平均每人消费金额 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
								inm="perPersonExpense"></label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input wxio_input_class"
								value="${requestScope.pageData.matterUserInfo.entmAmountPerPerson}${requestScope.pageData.matterUserInfo.localCurrencyName}"
								readonly="readonly" />
						</div>
					</div>
                    <!-- 平均每人消费金额 -->
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="ysz.yexp.exp0801.approveview"
                                inm="allPerPersonExpense"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input wxio_input_class"
                                value="${requestScope.pageData.matterUserInfo.allEntmAmountPerPerson}${requestScope.pageData.matterUserInfo.localCurrencyName}"
                                readonly="readonly" />
                        </div>
                    </div>
				</div>
				<!-- 业务招待明细 -->
				<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
					inm="serviceExpenseItem"></div>
				<div class="weui-cells__form weui-cells">
					<c:forEach
						items="${requestScope.pageData.matterUserInfo.entmDetailModelList}"
						var="dto">
						<div class="wxio-record">
							<!--业务招待费用明细：事业部-部门-成本中心- 金额-币种 -->
							<a class="weui-cell weui-cell_access" href="javascript:;">
								<div class="weui-cell__bd">
									<p>${dto.buName}-${dto.deptName}-${dto.costCenterName}-${dto.amount}${dto.entmCurrencyName}</p>
								</div>
								<div class="weui-cell__ft"></div>
							</a>
							<div class="weui-cells wxio-record-content">
								<!-- sub_devider -->
								<div class="weui-cell">
									<br />
									<div class="wxio_subDevider_class"></div>
								</div>
								<!-- 事业部 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="buCd"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.buName}" readonly="readonly" />
									</div>
								</div>
								<!-- 部门 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="deptName"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.deptName}" readonly="readonly" />
									</div>
								</div>
								<!-- 成本中心 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="costCenterCd"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.costCenterName}" readonly="readonly" />
									</div>
								</div>
								<!-- 金额 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="amount"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.amount}" readonly="readonly" />
									</div>
								</div>
								<!-- 币种 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="ticketCurrencyCd"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.entmCurrencyName}" readonly="readonly" />
									</div>
								</div>
								<!-- 汇率 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="accountingExchangeRate"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.exchangeRate}" readonly="readonly" />
									</div>
								</div>
								<!-- 支付方式 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="payMethod"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.modeOfPaymentName}" readonly="readonly" />
									</div>
								</div>
								<!-- 招待日期 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="entmDate"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.entmDate}" readonly="readonly" />
									</div>
								</div>
								<!-- 招待地点 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="entmAddress"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.entmAddress}" readonly="readonly" />
									</div>
								</div>
								<!-- 招待方参加者 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="entmCustomerParticipants"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.entmCustomerParticipants}" readonly="readonly" />
									</div>
								</div>
								<!-- 招待人数 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="entmCustomerNumber"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.entmCustomerNumber}" readonly="readonly" />
									</div>
								</div>
								<!-- 我方参加者 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="entmOurParticipants"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.entmOurParticipants}" readonly="readonly" />
									</div>
								</div>
								<!-- 我方人数 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="entmOurNumber"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.entmOurNumber}" readonly="readonly" />
									</div>
								</div>
								<!-- 备注 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
											inm="communicationName"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.communicationName}" readonly="readonly" />
									</div>
								</div>
								<!-- 备注/其他  -->
								<c:if test="${dto.viewFlag}">
									<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
										inm="communicationOther"></div>
									<div class="weui-cells__form weui-cells">
										<div class="weui-cell">
											<textarea class="weui-textarea wxio_textarea_class" rows="3" readonly="readonly">${dto.remarks}</textarea>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
			<!-- 交际费报销合计 -->
			<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
				inm="entmExpenseTotalAmount"></div>
			<div class="weui-cells__form weui-cells">
				<!-- 借款冲销金额 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="entmTotalAmount"></label>
					</div>
					<div class="weui-cell__bd wxio_line_class">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.writeOffAmount}${requestScope.pageData.matterUserInfo.localCurrencyName}"
							readonly="readonly" id="amount1" />
					</div>
				</div>
				<!-- 报销支付金额 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="paymentAmount"></label>
					</div>
					<div class="weui-cell__bd wxio_line_class">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.paymentAmount}${requestScope.pageData.matterUserInfo.localCurrencyName}"
							readonly="readonly" id="amount2" />
					</div>
				</div>
				<!-- 公司信用卡 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="reditAmount"></label>
					</div>
					<div class="weui-cell__bd wxio_line_class">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.creditAmount}${requestScope.pageData.matterUserInfo.localCurrencyName}"
							readonly="readonly" id="amount3" />
					</div>
				</div>
				<!-- 交际费合计 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
							inm="expTotalAmount1"></label>
					</div>
					<div class="weui-cell__bd wxio_line_class">
						<input class="weui-input wxio_strong_class"
							value="${requestScope.pageData.matterUserInfo.entmTotalAmount}${requestScope.pageData.matterUserInfo.localCurrencyName}"
							readonly="readonly" id="amount4" />
					</div>
				</div>
			</div>
			<!-- 06.财务支付 -->
			<c:if test="${requestScope.pageData.matterUserInfo.accountingFlag}">
				<c:choose>
					<c:when
						test="${requestScope.pageData.matterUserInfo.accountingCmmDto.hopeDisplay}">
						<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
							inm="accountingInfo1"></div>
					</c:when>
					<c:otherwise>
						<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
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
									<!-- sub_devider -->
									<div class="weui-cell">
										<br />
										<div class="wxio_subDevider_class"></div>
									</div>
									<!-- 借贷区分 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
												inm="accountingDebtorFlagName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.debtorFlagName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<!-- 经费大分类 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
											<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
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
				<!-- （合计）个人支付／公司信用卡 -->
				<div class="weui-cells__title" ins="ysz.yexp.exp0801.approveview"
					inm="personalOrReditPay"></div>
	 				<div class="weui-cells__form weui-cells">
					<!-- 含税金额合计 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
								inm="taxTotalAmount"></label>
						</div>
						<div class="weui-cell__bd">
							<p class="wxio_strong_class">
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInP}${requestScope.pageData.matterUserInfo.localCurrencyName}／
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInC}${requestScope.pageData.matterUserInfo.localCurrencyName}
							</p>
						</div>
					</div>
					<!-- 费用金额合计 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
								inm="expTotalAmount"></label>
						</div>
						<div class="weui-cell__bd">
							<p>
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExP}${requestScope.pageData.matterUserInfo.localCurrencyName}／
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExC}${requestScope.pageData.matterUserInfo.localCurrencyName}
							</p>
						</div>
					</div>
					<!-- 税额合计 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="ysz.yexp.exp0801.approveview"
								inm="taxAmountTotal"></label>
						</div>
						<div class="weui-cell__bd">
							<p>
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalTaxAmountP}${requestScope.pageData.matterUserInfo.localCurrencyName}／
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalTaxAmountC}${requestScope.pageData.matterUserInfo.localCurrencyName}
							</p>
						</div>
					</div>
					<div class="weui-cell"></div>
				</div>
			</c:if>

			<!-- 按钮 -->
			<c:if
				test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
				<div>
					<div class="weui-mask" id="pageMask" style="display: none"></div>
					<!-- 财务支付  标志位存储 隐藏  1为有支付-->
					<input type="hidden" name= "accountingFlag" value="${requestScope.pageData.matterUserInfo.accountingFlag}" />
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
		// 页面初始化 加载..
		window.onload = function(){
			// addRMB();
		}
		// 总计 add单位（元）
		function addRMB(){
			var rmb = ychips.i18n.wx.ysz.yexp.exp0801.approveview[window.ychips.locale].CNY;
			var amount1= $("#amount1").val();
			var amount2= $("#amount2").val();
			var amount3= $("#amount3").val();
			var amount4= $("#amount4").val();

			if (amount1 != null && amount1 != '') {
				$("#amount1").val(amount1+' '+rmb);
			}
			if (amount2 != null && amount2 != '') {
				$("#amount2").val(amount2+' '+rmb);
			}
			if (amount3 != null && amount3 != '') {
				$("#amount3").val(amount3+' '+rmb);
			}
			if (amount4 != null && amount4 != '') {
				$("#amount4").val(amount4+' '+rmb);
			}
		}
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