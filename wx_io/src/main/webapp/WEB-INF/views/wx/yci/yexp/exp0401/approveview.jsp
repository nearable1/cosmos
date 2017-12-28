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
	src="${RESOURCE_PATH}/js/i18n/wx/yci/yexp/exp0401/approveview.js${JS_SUFFIX}"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/yci/yexp/common/applyUserInfo.js${JS_SUFFIX}"></script>
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
	<!-- 主页 -->
	<script type="text/html" id="tpl_home">
	<div class="page">
		<div class="weui-tab__panel">
			<!-- 01.申请者信息 Start -->
			<%@include file="/WEB-INF/views/wx/yci/yexp/common/applyUserInfo.jsp"%>
			<!-- 申请者信息 End -->

			<!-- 02.基本信息 -->
			<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
				inm="commonMsg"></div>
			<div class="weui-cells weui-cells__form">
				<!-- 申请日  -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="applyDate"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterSystemInfo.applyDate}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 项目代码 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
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
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
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
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="pmCode"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.pmName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 出差期间 -->
				<div class="weui-cell">
					<div class="weui-cell_hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="timeInterval"></label>
					</div>
					<div class="weui-cell__bd">
						<label class="wxio_label_class">${requestScope.pageData.matterUserInfo.bizTripStartDate}</label>～<label
							class="wxio_label_class">${requestScope.pageData.matterUserInfo.bizTripEndDate}</label>
					</div>
				</div>
				<!-- 出差申请案件名 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="bizTripMatterApplyName"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.imwExpApplyMatterName}" />
					</div>
				</div>
				<!-- 海外区分 -->
				<div class="weui-cell">
					<div class="weui-cell_hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="overseasFlag">
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.overseasFlagName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 公司名称 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="companyCd"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.companyName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 事业部 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="buCd"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.buName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 部门 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="deptCd"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.deptName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 成本中心 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="costCenterCd"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.costCenterName}"
							readonly="readonly" />
					</div>
				</div>
			</div>
			<!-- 出差内容和目的  textarea has 1 title-->
			<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
				inm="bizTripPurpose"></div>
			<div class="weui-cells__form weui-cells">
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea class="weui-textarea wxio_textarea_class" rows="3"
							readonly="readonly">${requestScope.pageData.matterUserInfo.bizTripPurpose}</textarea>
					</div>
				</div>
			</div>
			<!-- 借款金额 Title -->
			<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
				inm="writeOffAmount"></div>
			<div class="weui-cells weui-cells__form">
				<!-- 金额 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
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
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="ticketCurrencyCd"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.currencyName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 支付方式 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="payMethod"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class" value="${requestScope.pageData.matterUserInfo.payMethodName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 希望支付日 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="hopePayDate"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.hopePayDate}"
							readonly="readonly" />
					</div>
				</div>
			</div>
			<!-- 03.出差日程	 -->
			<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
				inm="travelSchedule">
				<i class="weui-icon-success"></i>
			</div>
			<div class="weui-cells_form weui-cells">
				<c:forEach
					items="${requestScope.pageData.matterUserInfo.scheduleDtoList}"
					var="dto">
					<div class="wxio-record">
						<!-- 有日程：开始日-结束日（出差地区） -->
						<a class="weui-cell weui-cell_access" href="javascript:;">
							<div class="weui-cell__bd">
								<p>${dto.scheduleStartDate}-${dto.scheduleEndDate}（${dto.schedulePlace}）</p>
							</div>
							<div class="weui-cell__ft"></div>
						</a>
						<!-- 日程明细 -->
						<div class="weui-cells wxio-record-content">
							<!-- sub_devider -->
							<div class="weui-cell">
								<br />
								<div class="wxio_subDevider_class"></div>
								<!-- <br /> -->
							</div>
							<!-- 出差区分 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="yci.yexp.exp0401.approveview"
										inm="bizTripDivCd"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input wxio_input_class"
										value="${dto.bizTripDivName}" readonly="readonly" />
								</div>
							</div>
							<c:if
								test="${requestScope.pageData.matterUserInfo.overseasFlag == 'OFFSHORE_TYPE_001'}">
								<!-- 出差距离 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="distanceTypeCd"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.distanceTypeName}" readonly="readonly" />
									</div>
								</div>
							</c:if>
							<!-- 出差地 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="yci.yexp.exp0401.approveview"
										inm="schedulePlace"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input wxio_input_class"
										value="${dto.schedulePlace}" readonly="readonly" />
								</div>
							</div>
							<!-- 开始日 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="yci.yexp.exp0401.approveview"
										inm="scheduleStartDate"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input wxio_input_class"
										value="${dto.scheduleStartDate}" readonly="readonly" />
								</div>
							</div>
							<!-- 结束日 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="yci.yexp.exp0401.approveview"
										inm="scheduleEndDate"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input wxio_input_class"
										value="${dto.scheduleEndDate}" readonly="readonly" />
								</div>
							</div>
							<!-- 天数 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="yci.yexp.exp0401.approveview"
										inm="scheduleDays"></label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input wxio_input_class"
										value="${dto.scheduleDays}" readonly="readonly" />
								</div>
							</div>
							<!-- 补贴天数 -->
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label" ins="yci.yexp.exp0401.approveview"
										inm="subsidiesDays"></label>
								</div>
								<div class="weui-cell__bd">

									<input class="weui-input wxio_input_class"
										value="${dto.subsidiesDays}" readonly="readonly" />
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			<!-- 04.交通费报销 -->
			<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
				inm="hasTicketRequiredFlag">
				<c:choose>
					<c:when
						test="${requestScope.pageData.matterUserInfo.ticketRequiredFlag}">
						<i class="weui-icon-success"></i>
					</c:when>
					<c:otherwise>
						<i class="weui-icon-circle"></i>
					</c:otherwise>
				</c:choose>
			</div>
			<c:if
				test="${requestScope.pageData.matterUserInfo.ticketRequiredFlag}">
				<div class="weui-cells weui-cells_form">
					<c:forEach
						items="${requestScope.pageData.matterUserInfo.railwayTicketDtoList}"
						var="dto">
						<div class="wxio-record">
							<a class="weui-cell weui-cell_access" href="javascript:;">
								<div class="weui-cell__bd">
									<p>${dto.departDate}&nbsp;${dto.trafficStartHour}:${dto.trafficStartMinute}（${dto.transportTypeName}-${dto.ticketNumber}）</p>
								</div>
								<div class="weui-cell__ft"></div>
							</a>
							<div class="weui-cells wxio-record-content">
								<!-- sub_devider -->
								<div class="weui-cell">
									<br />
									<div class="wxio_subDevider_class"></div>
								</div>
								<!-- 支付方式 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="payMethod"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class" value="${dto.ticketSettlPayWayName}"
											readonly="readonly" />
									</div>
								</div>
								<!-- 日期 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="flightTicketDepartDate"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input" value="${dto.departDate}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="flightTicketTransportTypeName"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input" value="${dto.transportTypeName}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="flightTicketTicketNumber"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input" value="${dto.ticketNumber}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="flightTicketTrafficStartTime"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input"
											value="${dto.trafficStartHour}:${dto.trafficStartMinute}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="flightTicketTrafficEndTime"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input"
											value="${dto.trafficEndHour}:${dto.trafficEndMinute}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="flightTicketTransportFrom"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input" value="${dto.transportFrom}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="flightTicketTransportTo"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input" value="${dto.transportTo}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="flightTicketTicketPrice"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input" value="${dto.ticketPrice}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="ticketAmount"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input" value="${dto.ticketAmount}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="flightTicketTicketCurrencyName"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input" value="${dto.ticketCurrencyName}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="ticketExchangeRate"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input" value="${dto.ticketExchangeRate}"
											readonly="readonly" style="color: grey;" />
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>

				<!-- 05. 住宿报销 -->
				<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
					inm="hashotelRequiredFlag">
				<c:choose>
					<c:when
						test="${requestScope.pageData.matterUserInfo.hotelRequiredFlag == '1'}">
						<i class="weui-icon-success"></i>
					</c:when>
					<c:otherwise>
						<i class="weui-icon-circle"></i>
					</c:otherwise>
				</c:choose>
				</div>
			<c:if
				test="${requestScope.pageData.matterUserInfo.hotelRequiredFlag == '1'}">
				<div class="weui-cells__form weui-cells">
					<c:forEach
						items="${requestScope.pageData.matterUserInfo.hotelDtoList}"
						var="dto">
						<div class="wxio-record">
							<!-- 有住宿：开始日-结束日（酒店名） -->
							<a class="weui-cell weui-cell_access" href="javascript:;">
								<div class="weui-cell__bd">
									<p>${dto.hotelStartDate}-${dto.hotelEndDate}（${dto.hotelName}）</p>
								</div>
								<div class="weui-cell__ft"></div>
							</a>
							<!-- 住宿明细 -->
							<div class="weui-cells wxio-record-content">
								<!-- sub_devider -->
								<div class="weui-cell">
									<br />
									<div class="wxio_subDevider_class"></div>
								</div>
								<!-- 支付方式 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="payMethod"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.settlPayWayName}" readonly="readonly" />
									</div>
								</div>
								<!-- 住宿地区分 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="destinationDiv"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.destinationDivName}" readonly="readonly" />
									</div>
								</div>
								<!-- 酒店名 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="hotelName"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.hotelName}" readonly="readonly" />
									</div>
								</div>
								<!-- 开始日 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="hotelStartDate"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.hotelStartDate}" readonly="readonly" />
									</div>
								</div>
								<!-- 结束日 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="hotelEndDate"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.hotelEndDate}" readonly="readonly" />
									</div>
								</div>
								<!-- 天数 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="hotelDays"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.hotelDays}" readonly="readonly" />
									</div>
								</div>
								<!-- 单价 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="hotelPrice"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.hotelPrice}" readonly="readonly" />
									</div>
								</div>
								<!-- 含税总额 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="hotelAmount"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.hotelAmount}" readonly="readonly" />
									</div>
								</div>
								<!-- 币种 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="ticketCurrencyName"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.hotelCurrencyName}" readonly="readonly" />
									</div>
								</div>
								<!-- 汇率 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="ticketExchangeRate"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.hotelExchangeRate}" readonly="readonly" />
									</div>
								</div>
								<!-- 税率 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="taxRateList"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.taxRateName}" readonly="readonly" />
									</div>
								</div>
								<!-- 发票-->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="partnerCd"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.invoice}" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>

			<!-- 06.出差其他费用报销 -->
				<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
					inm="hasOtherExpenseRequiredFlag">
					<c:choose>
						<c:when
							test="${requestScope.pageData.matterUserInfo.otherExpenseRequiredFlag == '1'}">
							<i class="weui-icon-success"></i>
						</c:when>
						<c:otherwise>
							<i class="weui-icon-circle"></i>
						</c:otherwise>
					</c:choose>
				</div>
			<c:if
				test="${requestScope.pageData.matterUserInfo.otherExpenseRequiredFlag == '1'}">
				<div class="weui-cells__form weui-cells">
					<c:forEach
						items="${requestScope.pageData.matterUserInfo.otherExpenseDtoList}"
						var="dto">
						<div class="wxio-record">
							<!-- 有出差其他费用报销：大分类-小分类-经费项目 -金额-币种 -->
							<a class="weui-cell weui-cell_access" href="javascript:;">
								<div class="weui-cell__bd">
									<p>${dto.expBroadCategoryName}-${dto.expSubCategoryName}-${dto.expItemName}-${dto.otherExpenseAmount}${dto.otherExpenseCurrencyName}</p>
								</div>
								<div class="weui-cell__ft"></div>
							</a>
							<!-- 出差其他费用明细 -->
							<div class="weui-cells wxio-record-content">
								<!-- sub_devider -->
								<div class="weui-cell">
									<br />
									<div class="wxio_subDevider_class"></div>
								</div>
								<!-- 支付方法 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="payMethod"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.modeOfPaymentName}" readonly="readonly" />
									</div>
								</div>
								<!-- 日期 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="departDate"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.occurredDate}" readonly="readonly" />
									</div>
								</div>
								<!-- 经费大分类 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="expBroadCategory"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.expBroadCategoryName}" readonly="readonly" />
									</div>
								</div>
								<!-- 经费小分类 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="expSubCategory"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.expSubCategoryName}" readonly="readonly" />
									</div>
								</div>
								<!-- 经费项目 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="accountingExpItemName"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.expItemName}" readonly="readonly" />
									</div>
								</div>
								<!-- 金额 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="amount"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.otherExpenseAmount}" readonly="readonly" />
									</div>
								</div>
								<!-- 币种 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="ticketCurrencyCd"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.otherExpenseCurrencyName}" readonly="readonly" />
									</div>
								</div>
								<!-- 汇率 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="ticketExchangeRate"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.otherExpenseExchangeRate}" readonly="readonly" />
									</div>
								</div>
								<!-- 备注 -->
								<div class="weui-cell">
									<div class="weui-cell__hd">
										<label class="weui-label" ins="yci.yexp.exp0401.approveview"
											inm="otherExpRemark"></label>
									</div>
									<div class="weui-cell__bd">
										<input class="weui-input wxio_input_class"
											value="${dto.otherExpenseRemarks}" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
			<!-- 07.补贴 -->
			<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
				inm=subsidy></div>
			<div class="weui-cells__form weui-cells">
				<c:forEach
					items="${requestScope.pageData.matterUserInfo.allowanceDtoList}"
					var="dto">
					<div class="weui-cell">
						<div class="weui-cell__bd">
                            <span class="weui-input wxio_input_class" ins="yci.yexp.exp0401.approveview" inm="dayAllowance"></span><span class="wxio_input_class">${dto.subtotal}${dto.currencyName}（${dto.subtotalLocalCurrency}${requestScope.pageData.matterUserInfo.localCurrencyName}）</span>
						</div>
					</div>
				</c:forEach>
			</div>
			<!-- 08.出差报销合计 -->
			<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
				inm="bizTripTotleAmount"></div>
			<div class="weui-cells_form weui-cells">
				<!-- 借款冲销金额 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="wrOffAmount"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.writeOffAmount}${requestScope.pageData.matterUserInfo.currencyName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 报销支付金额 -->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="bizPayAmount"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.paymentAmount}${requestScope.pageData.matterUserInfo.currencyName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 公司信用卡-->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="creditCard"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_input_class"
							value="${requestScope.pageData.matterUserInfo.creditAmount}${requestScope.pageData.matterUserInfo.currencyName}"
							readonly="readonly" />
					</div>
				</div>
				<!-- 本次出差总费用-->
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label" ins="yci.yexp.exp0401.approveview"
							inm="totleAmount"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input wxio_strong_class"
							value="${requestScope.pageData.matterUserInfo.totalAmount}${requestScope.pageData.matterUserInfo.currencyName}"
							readonly="readonly" />
					</div>
				</div>
			</div>
			<!-- 09.财务支付 -->
			<c:if
				test="${requestScope.pageData.matterUserInfo.accountingFlag == '1'}">
				<c:choose>
					<c:when
						test="${requestScope.pageData.matterUserInfo.accountingCmmDto == null}">
						<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
							inm="accountingInfo1"></div>
					</c:when>
					<c:otherwise>
						<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
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
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingDebtorFlagName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.debtorFlagName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingExpBroadCategoryName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.expBroadCategoryName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingExpSubCategoryName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.expSubCategoryName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingExpItemName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.expItemName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingSubjectName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input"
												value="${dtl.accountingSubjectName}" readonly="readonly"
												style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingPaymentAmountTaxIn"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.paymentAmountTaxIn}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingTaxRate"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input"
												value="${dtl.taxRateName}（${dtl.inputTaxName}）"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingPaymentAmountTaxOut"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.paymentAmountTaxOut}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingTaxAmount"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.taxAmount}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingCurrencyName"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.currencyName}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingExchangeRate"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.origExchangeRate}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingCostCenterCd"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.costCenterCd}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingInvoice"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.invoice}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
												inm="accountingPartner"></label>
										</div>
										<div class="weui-cell__bd">
											<input class="weui-input" value="${dtl.partnerCd}"
												readonly="readonly" style="color: grey;" />
										</div>
									</div>
									<div class="weui-cell">
										<div class="weui-cell__hd">
											<label class="weui-label" ins="yci.yexp.exp0401.approveview"
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
				<div class="weui-cells__title" ins="yci.yexp.exp0401.approveview"
					inm="personalOrReditPay"></div>
				<div class="weui-cells__form weui-cells">
					<!-- 含税金额合计 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="yci.yexp.exp0401.approveview"
								inm="taxTotalAmount"></label>
						</div>
						<div class="weui-cell__bd">
							<p>
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInP}${requestScope.pageData.matterUserInfo.currencyName}／
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxInC}${requestScope.pageData.matterUserInfo.currencyName}
							</p>
						</div>
					</div>
					<!-- 费用金额合计 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="yci.yexp.exp0401.approveview"
								inm="expTotalAmount"></label>
						</div>
						<div class="weui-cell__bd">
							<p>
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExP}${requestScope.pageData.matterUserInfo.currencyName}／
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalPaymentAmountTaxExC}${requestScope.pageData.matterUserInfo.currencyName}
							</p>
						</div>
					</div>
					<!-- 税额合计 -->
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label" ins="yci.yexp.exp0401.approveview"
								inm="taxAmountTotal"></label>
						</div>
						<div class="weui-cell__bd">
							<p class="wxio_strong_class">
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalTaxAmountP}${requestScope.pageData.matterUserInfo.currencyName}／
								${requestScope.pageData.matterUserInfo.accountingCmmDto.totalTaxAmountC}${requestScope.pageData.matterUserInfo.currencyName}
							</p>
						</div>
					</div>
					<div class="weui-cell"></div>
				</div>
			</c:if>
			<br />
		<!-- 按钮 -->
		<c:if
			test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
			<div>
				<div class="weui-mask" id="pageMask" style="display: none"></div>
					<!-- 财务支付  标志位存储 隐藏  1为有支付-->
					<input type="hidden" name="accountingFlag" value="${requestScope.pageData.matterUserInfo.accountingFlag}" />
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