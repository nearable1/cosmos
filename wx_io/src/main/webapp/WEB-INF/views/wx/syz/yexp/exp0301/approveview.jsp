<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp"%> 
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title></title>
        <link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.css"/>
        <link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.io.css${JS_SUFFIX}"/>
        <script src="${RESOURCE_PATH}/js/common/definer.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/common/zepto.min.js"></script>
        <script src="${RESOURCE_PATH}/js/wx/common/cmnUtil.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/common/weui.io.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/cmnMsg.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/i18n/wx/syz/yexp/exp0301/approveview.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/i18n/wx/syz/yexp/common/applyUserInfo.js${JS_SUFFIX}"></script>
        <style type="text/css">
            .label_th {
                text-align: center;
                color:#888;
            }
            
            .label_td {
                text-align: center;
                color:blue;
            }
        </style>
        <script type="text/javascript">
            // 设置页面区域
            ychips.wxio.CmnUtil.setLocale('${requestScope.pageData.accountInfo.localId}');
        </script>
    </head>
    <body ontouchstart>
        <div class="container" id="container"></div>
        <script type="text/html" id="tpl_home">
        <div class="page">
            <div class="weui-tab__panel">
                <%@include file="/WEB-INF/views/wx/syz/yexp/common/applyUserInfo.jsp"%>
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="basicInfoTitle"></div>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="applyDate"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.applyDate}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="companyName"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.companyName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="buName"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.buName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="deptName"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.deptName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="costCenterName"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.costCenterName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="projectCd"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.projectCd}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="projectName"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.projectName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="pmName"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.pmName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="bizTripDuration"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <lable style="color:grey;">${requestScope.pageData.matterUserInfo.bizTripStartDate}</lable>～<lable style="color:grey;">${requestScope.pageData.matterUserInfo.bizTripEndDate}</lable>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="overseasFlagName"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.overseasFlagName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                </div>
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="bizTripPurpose"></div>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            <textarea class="weui-textarea" readonly="readonly" rows="3" style="color:grey;">${requestScope.pageData.matterUserInfo.bizTripPurpose}</textarea>
                        </div>
                    </div>
                </div>
				<!--借款-->
				<c:if test="${requestScope.pageData.matterUserInfo.employeeLoanFlag !=null}">
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="borrowingAmountTitle"></div>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="borrowingAmount"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.amount}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="borrowingAmountCurrency"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.currencyName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="borrowingAmountPayMathod"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.payMethodName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="borrowingAmountHopePayDate"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.hopePayDate}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                </div>
				</c:if>
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="bizTripSchedule"></div>
                <div class="weui-cells weui-cells_form">
                    <c:forEach items="${requestScope.pageData.matterUserInfo.scheduleDtoList}" var="dto">
                    <div class="wxio-record">
                        <a class="weui-cell weui-cell_access" href="javascript:;">
                            <div class="weui-cell__bd">
                                <p>${dto.scheduleStartDate}-${dto.scheduleEndDate}（${dto.schedulePlace}）</p>
                            </div>
                            <div class="weui-cell__ft"></div>
                        </a>
                        <div class="weui-cells wxio-record-content">
                            <div class="weui-cell">
								<br />
								<div class="wxio_subDevider_class"></div>
							</div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="bizTripDivName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.bizTripDivName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <c:if test="${requestScope.pageData.matterUserInfo.overseasFlag == 'OFFSHORE_TYPE_001'}">
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="distanceTypeName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.distanceTypeName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            </c:if>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="schedulePlace"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.schedulePlace}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="scheduleStartDate"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.scheduleStartDate}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="scheduleEndDate"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.scheduleEndDate}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="scheduleDays"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.scheduleDays}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="subsidiesDays"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.subsidiesDays}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="flightTicket">
                <c:if test="${requestScope.pageData.matterUserInfo.ticketReserveFlag == '0'}">
                <i class="weui-icon-circle"></i>
                </c:if>
                <c:if test="${requestScope.pageData.matterUserInfo.ticketReserveFlag == '1'}">
                <i class="weui-icon-success"></i>
                </c:if>
                &nbsp;
                </div>
                <c:if test="${requestScope.pageData.matterUserInfo.ticketReserveFlag == '1'}">
                <div class="weui-cells weui-cells_form">
                    <c:forEach items="${requestScope.pageData.matterUserInfo.flightTicketDtoList}" var="dto">
                    <div class="wxio-record">
                        <a class="weui-cell weui-cell_access" href="javascript:;">
                            <div class="weui-cell__bd">
                                <p>${dto.departDate}&nbsp;${dto.trafficStartHour}:${dto.trafficStartMinute}（${dto.transportTypeName}-${dto.ticketNumber}）</p>
                            </div>
                            <div class="weui-cell__ft"></div>
                        </a>
                        <div class="weui-cells wxio-record-content">
                            <!-- 分割线 -->
							<div class="weui-cell">
								<br />
								<div class="wxio_subDevider_class"></div>
							</div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketDepartDate"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.departDate}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTransportTypeName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.transportTypeName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTicketNumber"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.ticketNumber}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTrafficStartTime"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.trafficStartHour}:${dto.trafficStartMinute}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTrafficEndTime"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.trafficEndHour}:${dto.trafficEndMinute}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTransportFrom"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.transportFrom}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTransportTo"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.transportTo}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTicketQuantity"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.ticketQuantity}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTicketPrice"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.ticketPrice}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTicketAmount"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.ticketAmount}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTicketCurrencyName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.ticketCurrencyName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
                </c:if>
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="railwayTicket">
                <c:if test="${requestScope.pageData.matterUserInfo.personalTicketReserveFlag == '0'}">
                <i class="weui-icon-circle"></i>
                </c:if>
                <c:if test="${requestScope.pageData.matterUserInfo.personalTicketReserveFlag == '1'}">
                <i class="weui-icon-success"></i>
                </c:if>
                &nbsp;
                </div>
                <c:if test="${requestScope.pageData.matterUserInfo.personalTicketReserveFlag == '1'}">
                <div class="weui-cells weui-cells_form">
                    <c:forEach items="${requestScope.pageData.matterUserInfo.railwayTicketDtoList}" var="dto">
                    <div class="wxio-record">
                        <a class="weui-cell weui-cell_access" href="javascript:;">
                            <div class="weui-cell__bd">
                                <p>${dto.departDate}&nbsp;${dto.trafficStartHour}:${dto.trafficStartMinute}（${dto.transportTypeName}-${dto.ticketNumber}）</p>
                            </div>
                            <div class="weui-cell__ft"></div>
                        </a>
                        <div class="weui-cells wxio-record-content">
							<div class="weui-cell">
								<br />
								<div class="wxio_subDevider_class"></div>
							</div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketDepartDate"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.departDate}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTransportTypeName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.transportTypeName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTicketNumber"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.ticketNumber}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTrafficStartTime"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.trafficStartHour}:${dto.trafficStartMinute}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTrafficEndTime"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.trafficEndHour}:${dto.trafficEndMinute}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTransportFrom"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.transportFrom}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTransportTo"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.transportTo}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTicketQuantity"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.ticketQuantity}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTicketPrice"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.ticketPrice}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTicketAmount"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.ticketAmount}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="flightTicketTicketCurrencyName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.ticketCurrencyName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
                </c:if>
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="hotelRequired">
                <c:if test="${requestScope.pageData.matterUserInfo.hotelRequiredFlag == '0'}">
                <i class="weui-icon-circle"></i>
                </c:if>
                <c:if test="${requestScope.pageData.matterUserInfo.hotelRequiredFlag == '1'}">
                <i class="weui-icon-success"></i>
                </c:if>
                &nbsp;
                </div>
                <c:if test="${requestScope.pageData.matterUserInfo.hotelRequiredFlag == '1'}">
                <div class="weui-cells weui-cells_form">
                    <c:forEach items="${requestScope.pageData.matterUserInfo.hotelDtoList}" var="dto">
                    <div class="wxio-record">
                        <a class="weui-cell weui-cell_access" href="javascript:;">
                            <div class="weui-cell__bd">
                                <p>${dto.hotelStartDate}-${dto.hotelEndDate}（${dto.hotelName}）</p>
                            </div>
                            <div class="weui-cell__ft"></div>
                        </a>
                        <div class="weui-cells wxio-record-content">
                        	<div class="weui-cell">
								<br />
								<div class="wxio_subDevider_class"></div>
							</div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="hotelDestinationDivName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.destinationDivName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="hotelName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.hotelName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="hotelStartDate"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.hotelStartDate}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="hotelEndDate"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.hotelEndDate}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="hotelDays"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.hotelDays}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="hotelPrice"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.hotelPrice}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="hotelAmount"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.hotelAmount}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="hotelCurrencyName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dto.hotelCurrencyName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
                </c:if>
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="allowance"></div>
                <div class="weui-cells weui-cells_form">
                    <c:forEach items="${requestScope.pageData.matterUserInfo.allowanceDtoList}" var="dto">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            <span class="weui-input wxio_input_class" ins="syz.yexp.exp0301.approveview" inm="dayAllowance"></span><span class="wxio_input_class">${dto.subtotal}${dto.currencyName}（${dto.subtotalLocalCurrency}${requestScope.pageData.matterUserInfo.localCurrencyName}）</span>
                        </div>
                    </div>
                    </c:forEach>
                </div>
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="budgetSummary"></div>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="ticketBudget"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.ticketBudgetTotalP}${requestScope.pageData.matterUserInfo.localCurrencyName}／${requestScope.pageData.matterUserInfo.ticketBudgetTotalC}${requestScope.pageData.matterUserInfo.localCurrencyName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="hotelBudget"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.hotelBudgetTotal}${requestScope.pageData.matterUserInfo.localCurrencyName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="allowanceBudget"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.shceduleBudgetTotal}${requestScope.pageData.matterUserInfo.localCurrencyName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="budgetSubTotal"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.subtotalP}${requestScope.pageData.matterUserInfo.localCurrencyName}／${requestScope.pageData.matterUserInfo.subtotalC}${requestScope.pageData.matterUserInfo.localCurrencyName}" readonly="readonly" style="color:grey;"/>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="budgetTotal"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  value="${requestScope.pageData.matterUserInfo.budgetTotal}${requestScope.pageData.matterUserInfo.localCurrencyName}" readonly="readonly" style="font-weight:bold;"/>
                        </div>
                    </div>
                </div>
                <c:if test="${requestScope.pageData.matterUserInfo.accountingFlag == '1'}">
                <c:choose>
                <c:when test="${requestScope.pageData.matterUserInfo.accountingCmmDto.hopeDisplay}">
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="accountingInfo1"></div>
                </c:when>
                <c:otherwise>
                <div class="weui-cells__title" ins="syz.yexp.exp0301.approveview" inm="accountingInfo2"></div>
                </c:otherwise>
                </c:choose>
                <div class="weui-cells weui-cells_form">
                    <c:forEach items="${requestScope.pageData.matterUserInfo.accountingCmmDto.accountingHeadList}" var="dto">
                    <div class="wxio-record">
                        <a class="weui-cell weui-cell_access" href="javascript:;">
                            <div class="weui-cell__bd">
                                <c:choose>
                                <c:when test="${requestScope.pageData.matterUserInfo.accountingCmmDto.hopeDisplay}">
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
							<div class="weui-cell">
								<br />
								<div class="wxio_subDevider_class"></div>
							</div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingDebtorFlagName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.debtorFlagName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingExpBroadCategoryName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.expBroadCategoryName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingExpSubCategoryName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.expSubCategoryName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingExpItemName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.expItemName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingSubjectName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.accountingSubjectName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingPaymentAmountTaxIn"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.paymentAmountTaxIn}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingTaxRate"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.taxRateName}（${dtl.inputTaxName}）" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingPaymentAmountTaxOut"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.paymentAmountTaxOut}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingTaxAmount"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.taxAmount}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingCurrencyName"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.currencyName}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingExchangeRate"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.origExchangeRate}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingCostCenterCd"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.costCenterCd}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingInvoice"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.invoice}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingPartner"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.partnerCd}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            <div class="weui-cell">
                                <div class="weui-cell__hd">
                                    <label class="weui-label" ins="syz.yexp.exp0301.approveview" inm="accountingSummary"></label>
                                </div>
                                <div class="weui-cell__bd">
                                    <input class="weui-input"  value="${dtl.summary}" readonly="readonly" style="color:grey;"/>
                                </div>
                            </div>
                            </c:forEach>
                        </div>
                    </div>
                    </c:forEach>
                </div>
                </c:if>
                <br />
                <c:if test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
                <div>
                    <div class="weui-mask" id="pageMask" style="display: none"></div>
						<!-- 财务支付  标志位存储 隐藏  1为有支付-->
						<input type="hidden" name= "accountingFlag" value="${requestScope.pageData.matterUserInfo.accountingFlag}" />
                    	<div class="weui-actionsheet" id="processActionsheet">
                        <div class="weui-actionsheet__title">
                            <p class="weui-actionsheet__title-text" ins="common.workflow.cmnMsg" inm="processActionSheetTitle"></p>
                        </div>
                        <div class="weui-actionsheet__menu">
                            <c:forEach items="${requestScope.pageData.matterSystemInfo.processType}" var="entry">
                            <a class="js_item" data-id="${entry.key}" href="javascript:;">
                                <div class="weui-actionsheet__cell" style="color:rgb(4, 190, 2);background:rgb(255, 255, 255)">
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
                    <img src="${RESOURCE_PATH}/img/icon_nav_history.png" alt="" class="weui-tabbar__icon">
                    <p class="weui-tabbar__label" ins="common.workflow.cmnMsg" inm="history"></p>
                </a>
                <c:if test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
                <a href="javascript:;" class="weui-tabbar__item weui-bar__item_on" id="btnProcess">
                    <img src="${RESOURCE_PATH}/img/icon_nav_process.png" alt="" class="weui-tabbar__icon">
                    <p class="weui-tabbar__label" ins="common.workflow.cmnMsg" inm="process"></p>
                </a>
                </c:if>
            </div>
        </div>
     <script type="text/javascript">
        $(function(){
            // 初始化按钮
            if ($('#btnProcess').length > 0) {
                ychips.wxio.CmnUtil.initAtcionSheet(
                    $('#pageMask'),
                    $('#processActionsheet'),
                    $('#btnProcess'));
            }
            // 初始化履历按钮
            ychips.wxio.CmnUtil.initHistory($("#btnHistory"));

            // 展开关闭
            $("div.wxio-record").find("a.weui-cell_access").click(function() {
                // 内容
                var record = $(this).closest("div.wxio-record.record_show");
                if (record && record.length > 0) {
                    $(this).closest("div.wxio-record").removeClass("record_show");
                } else {
                    $(this).closest("div.wxio-record").addClass("record_show");
                }
            });
        });
        </script>
        </script>
        <c:if test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
        <%@include file="/WEB-INF/views/wx/common/workflow/process/approve.jsp"%>
        <%@include file="/WEB-INF/views/wx/common/workflow/process/deny.jsp"%>
        <%@include file="/WEB-INF/views/wx/common/workflow/process/reserve.jsp"%>
        <%@include file="/WEB-INF/views/wx/common/workflow/process/reservecancel.jsp"%>
        <%@include file="/WEB-INF/views/wx/common/workflow/process/sendback.jsp"%>
        <%@include file="/WEB-INF/views/wx/common/workflow/success.jsp"%>
        <%@include file="/WEB-INF/views/wx/common/workflow/error.jsp"%>
        </c:if>
        <%@include file="/WEB-INF/views/wx/common/workflow/unit/history.jsp"%>
    </body>
</html>
