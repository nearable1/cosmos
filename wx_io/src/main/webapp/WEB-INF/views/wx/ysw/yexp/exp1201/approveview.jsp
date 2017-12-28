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
	src="${RESOURCE_PATH}/js/i18n/wx/ysw/yexp/exp1201/approveview.js${JS_SUFFIX}"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/ysw/yexp/common/applyUserInfo.js${JS_SUFFIX}"></script>
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
			<%@include file="/WEB-INF/views/wx/ysw/yexp/common/applyUserInfo.jsp"%>
			<!-- 申请者信息 End-->
			<!-- 02.基本信息 -->
			<div class="weui-cells__title" ins="ysw.yexp.exp1201.approveview"
				inm="commonMsg"></div>
			<div class="weui-cells_form weui-cells">
				<!-- 申请日 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
							inm="applyDate"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.applyDate}" />
					</div>
				</div>
				<!-- 希望支付日 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
							inm="hopePayDate"> </label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class" type="text"
							readonly="readonly"
							value="${requestScope.pageData.matterUserInfo.hopePayDate}" />
					</div>
				</div>
				<!-- 公司名称 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
							inm="companyCd"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class" type="text"
							readonly="readonly"
							value="${requestScope.pageData.matterUserInfo.companyName}" /> <input
							type="hidden"
							value="${requestScope.pageData.matterUserInfo.companyCd}" />
					</div>
				</div>
			</div>
			<!-- 03.说明（textarea样式需要单独title） -->
			<div class="weui-cells__title" ins="ysw.yexp.exp1201.approveview"
				inm="instruction"></div>
			<div class="weui-cells weui-cells_form">
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea class="weui-textarea wxio_textarea_class" id="purpose"
							readonly="readonly" rows="3">${requestScope.pageData.matterUserInfo.purpose}</textarea>
					</div>
				</div>
			</div>
			<!-- 02.基本信息-->
			<div class="weui-cells weui-cells_form">
				<!-- 金额 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
							inm="payAmount"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class" type="text"
							readonly="readonly"
							value="${requestScope.pageData.matterUserInfo.amount}" />
					</div>
				</div>
				<!-- 币种(元) -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
							inm="currency"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class" type="text"
							readonly="readonly"
							value="${requestScope.pageData.matterUserInfo.currencyName}" />
					</div>
				</div>
				<!-- 支付方式 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
							inm="payMethod"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class" type="text"
							readonly="readonly"
							value="${requestScope.pageData.matterUserInfo.payMethodName}" />
					</div>
				</div>
			</div>
			<!-- 04.财务支付 -->
			<c:if
				test="${requestScope.pageData.matterUserInfo.accountingFlag == '1'}">

				<c:choose>
					<c:when
						test="${requestScope.pageData.matterUserInfo.accountingCmmDto.hopeDisplay}">
						<div class="weui-cells__title" ins="ysw.yexp.exp1201.approveview"
							inm="accountingInfo1"></div>
					</c:when>
					<c:otherwise>
						<div class="weui-cells__title" ins="ysw.yexp.exp1201.approveview"
							inm="accountingInfo2"></div>
					</c:otherwise>
				</c:choose>

				<div class="weui-cells weui-cells_form">
					<c:forEach
						items="${requestScope.pageData.matterUserInfo.accountingCmmDto.accountingHeadList}"
						var="dto">
						<div class="wxio-record">
							<!-- 财务支付header （记账日／希望支付日／凭证抬头文本） -->
							<a class="weui-cell weui-cell_access" href="javascript:;">
								<div class="weui-cell__bd">
									<c:choose>
										<c:when
											test="${requestScope.pageData.matterUserInfo.accountingCmmDto.hopeDisplay}">
											<p id="zk01">
												${dto.bookDate}／${dto.hopePayDate}／${dto.voucherHeader}</p>
										</c:when>
										<c:otherwise>
											<p id="zk01">${dto.bookDate}／${dto.voucherHeader}</p>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="weui-cell__ft"></div>
							</a>
							<div class="wxio-record-content weui-cells">
								<!-- 财务支付-明细row -->
								<c:forEach items="${dto.detailRowModel}" var="detailRowModel">
									<!-- sub_devider -->
									<div class="weui-cell">
										<br />
										<div class="wxio_subDevider_class"></div>
										<!-- <br /> -->
									</div>
									<!-- 借贷区分 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label debtorFlagName"
												ins="ysw.yexp.exp1201.approveview" inm="debtorFlag"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.debtorFlagName}" readonly="readonly">
										</div>
									</div>
									<!-- 经费大分类 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
												inm="expSubCategategory"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.expBroadCategoryName}"
												readonly="readonly">
										</div>
									</div>
									<!-- 经费小分类 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
												inm="expSubCategory"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.expSubCategoryName}"
												readonly="readonly">
										</div>
									</div>
									<!-- 经费项目 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label accountingSubjectName"
												ins="ysw.yexp.exp1201.approveview" inm="expBroadCory"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.accountingSubjectName}"
												readonly="readonly">
										</div>
									</div>
									<!-- 科目 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label accountingSubjectName"
												ins="ysw.yexp.exp1201.approveview" inm="vaAccountingSubject"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.accountingSubjectName}"
												readonly="readonly">
										</div>
									</div>
									<!-- 含税金额 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label paymentAmountTaxIn"
												ins="ysw.yexp.exp1201.approveview" inm="paymentAmountTaxIn"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.paymentAmountTaxIn}"
												readonly="readonly">
										</div>
									</div>
									<!-- 税率 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label taxRateList"
												ins="ysw.yexp.exp1201.approveview" inm="taxRate"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.taxRateName}" readonly="readonly">
										</div>
									</div>
									<!-- 费用金额 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label paymentAmountTaxOut"
												ins="ysw.yexp.exp1201.approveview" inm="paymentAmountTaxOut"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.paymentAmountTaxOut}"
												readonly="readonly">
										</div>
									</div>
									<!-- 税额 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
												inm="taxAmount"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.taxAmount}" readonly="readonly">
										</div>
									</div>
									<!-- 币种 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
												inm="currency"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.currencyName}"
												readonly="readonly">
										</div> 
									</div>
									<!-- 汇率 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
												inm="origExchangeRate"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.origExchangeRate}"
												readonly="readonly">
										</div>
									</div>
									<!-- 成本中心 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
												inm="costCenterCd"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.costCenterCd}" readonly="readonly">
										</div>
									</div>
									<!-- 发票 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
												inm="vaInvoice"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.invoice}" readonly="readonly">
										</div>
									</div>
									<!-- 合作伙伴 -->
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
												inm="vaPartnerCd"></label>
										</div>
										<div class="label_td">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.partnerCd}" readonly="readonly">
										</div>
									</div>
									<!-- 行文摘要 -->
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="ysw.yexp.exp1201.approveview"
												inm="summary"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input wxio_input_class"
												value="${detailRowModel.summary}" readonly="readonly" />
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
			<br />
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