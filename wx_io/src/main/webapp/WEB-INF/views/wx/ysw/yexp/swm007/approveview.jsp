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
        <script src="${RESOURCE_PATH}/js/i18n/wx/ysw/yexp/swm007/approveview.js${JS_SUFFIX}"></script>
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
                <div class="weui-cells__title" ins="ysw.yexp.swm007.approveview" inm="overtimeInfoTitle"></div>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell">
                    <div class="weui-cell__hd">
                        <label class="weui-label" ins="ysw.yexp.swm007.approveview" inm="overtimeType"></label>
                    </div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="text" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterUserInfo.overtimeTypeName}">
                    </div>
                    </div>
                    <c:if test="${requestScope.pageData.matterUserInfo.overtimeType == '01'}">
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="ysw.yexp.swm007.approveview" inm="overtimeDate"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <lable style="color:grey;">${requestScope.pageData.matterUserInfo.overtimeStartDateDsp}</lable>
                            <c:if test="${requestScope.pageData.matterUserInfo.overtimeStartPeriod == '01'}">
                            <lable ins="ysw.yexp.swm007.approveview" inm="amMsg"></lable>
                            </c:if>
                            <c:if test="${requestScope.pageData.matterUserInfo.overtimeStartPeriod == '02'}">
                            <lable ins="ysw.yexp.swm007.approveview" inm="pmMsg"></lable>
                            </c:if>
                            ~
                            <lable style="color:grey;">${requestScope.pageData.matterUserInfo.overtimeEndDateDsp}</lable>
                            <c:if test="${requestScope.pageData.matterUserInfo.overtimeEndPeriod == '01'}">
                            <lable ins="ysw.yexp.swm007.approveview" inm="amMsg"></lable>
                            </c:if>
                            <c:if test="${requestScope.pageData.matterUserInfo.overtimeEndPeriod == '02'}">
                            <lable ins="ysw.yexp.swm007.approveview" inm="pmMsg"></lable>
                            </c:if>
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <em ins="ysw.yexp.swm007.approveview" inm="summaryMsg"></em>
                            <input class="weui-input" type="text" readonly="readonly" style="text-align:right;color:grey;width:40%;" value="${requestScope.pageData.matterUserInfo.overtimeDaysHoliday}">
                            <em ins="ysw.yexp.swm007.approveview" inm="dayMsg"></em>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="${requestScope.pageData.matterUserInfo.overtimeType == '02'}">
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="ysw.yexp.swm007.approveview" inm="overtimeDate"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input" type="text" style="color:grey;" readonly="readonly"  value="${requestScope.pageData.matterUserInfo.overtimeDateDsp}">
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label" ins="ysw.yexp.swm007.approveview" inm="overtimeTime"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <input class="weui-input" type="text" style="color:grey;width:40%;" readonly="readonly" value="${requestScope.pageData.matterUserInfo.startHhmmDsp}">~
                            <input class="weui-input" type="text" style="color:grey;width:40%;" readonly="readonly" value="${requestScope.pageData.matterUserInfo.endHhmmDsp}">
                        </div>
                    </div>
                    <div class="weui-cell">
                        <div class="weui-cell__hd">
                            <label class="weui-label"></label>
                        </div>
                        <div class="weui-cell__bd">
                            <em ins="ysw.yexp.swm007.approveview" inm="summaryMsg"></em>                            
                            <input class="weui-input" type="text" readonly="readonly" style="text-align:right;color:grey;width:40%;" value="${requestScope.pageData.matterUserInfo.overtimeHoursWorkday}">
                            <em ins="ysw.yexp.swm007.approveview" inm="hourMsg"></em>
                        </div>
                    </div>
                    </c:if>
                </div>
                <div class="weui-cells__title" ins="ysw.yexp.swm007.approveview" inm="overtimeReason"></div>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            <textarea class="weui-textarea" readonly="readonly" rows="3" style="color:grey;">${requestScope.pageData.matterUserInfo.overtimeReason}</textarea>
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
	    </scritp>
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
