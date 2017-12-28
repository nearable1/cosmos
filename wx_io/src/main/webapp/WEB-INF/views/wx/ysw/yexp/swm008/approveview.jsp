<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title></title>
        <link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.css"/>
        <link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.io.css"/>
        <script src="${RESOURCE_PATH}/js/common/definer.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/common/zepto.min.js"></script>
        <script src="${RESOURCE_PATH}/js/wx/common/cmnUtil.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/common/weui.io.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/cmnMsg.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/i18n/wx/ysw/yexp/swm008/approveview.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/i18n/wx/ysw/yexp/common/applyUserInfo.js${JS_SUFFIX}"></script>
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
            <%@include file="/WEB-INF/views/wx/ysw/yexp/common/applyUserInfo.jsp"%>
                <div class="weui-cells__title" ins="ysw.yexp.swm008.approveview" inm="vacationInfoTitle"></div>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label" ins="ysw.yexp.swm008.approveview" inm="vacationType"></label></div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterUserInfo.vacationTypeName}">
                        </div>
                    </div>
                    <div class="weui-cells__title"></div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label" ins="ysw.yexp.swm008.approveview" inm="startDate"></label></div>
                        <div class="weui-cell__bd">
                            <lable style="color:grey;">${requestScope.pageData.matterUserInfo.vacationStartDateDsp}</lable>
                            <c:if test="${requestScope.pageData.matterUserInfo.vacationStartPeriod == '01'}">
                            <lable ins="ysw.yexp.swm008.approveview" inm="amMsg"></lable>
                            </c:if>
                            <c:if test="${requestScope.pageData.matterUserInfo.vacationStartPeriod == '02'}">
                            <lable ins="ysw.yexp.swm008.approveview" inm="pmMsg"></lable>
                            </c:if>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label" ins="ysw.yexp.swm008.approveview" inm="endDate"></label></div>
                        <div class="weui-cell__bd">
                            <lable style="color:grey;">${requestScope.pageData.matterUserInfo.vacationEndDateDsp}</lable>
                            <c:if test="${requestScope.pageData.matterUserInfo.vacationEndPeriod == '01'}">
                            <lable ins="ysw.yexp.swm008.approveview" inm="amMsg"></lable>
                            </c:if>
                            <c:if test="${requestScope.pageData.matterUserInfo.vacationEndPeriod == '02'}">
                            <lable ins="ysw.yexp.swm008.approveview" inm="pmMsg"></lable>
                            </c:if>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd"><label class="weui-label" ins="ysw.yexp.swm008.approveview" inm="emergencyContact"></label></div>
                        <div class="weui-cell__bd">
                            <input class="weui-input"  readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterUserInfo.emergencyContact}">
                        </div>
                    </div>
                </div>
                <div class="weui-cells__title" ins="ysw.yexp.swm008.approveview" inm="vacationReason"></div>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            <textarea class="weui-textarea" readonly="readonly" rows="3" style="color:grey;">${requestScope.pageData.matterUserInfo.vacationReason}</textarea>
                        </div>
                    </div>
                </div>
                <br />
                <c:if test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
                <div>
                    <div class="weui-mask" id="pageMask" style="display: none"></div>
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
