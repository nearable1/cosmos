<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/unit/history.js${JS_SUFFIX}"></script>
<script type="text/html" id="tpl_history">
<div class="page">
    <div class="weui-tab__panel">
        <div class="weui-cells__title" ins="common.workflow.unit.history" inm="historyTitle"></div>
        <div class="weui-cells weui-cells_form">
            <c:forEach items="${requestScope.pageData.matterSystemInfo.modelLatesterInfo}" var="history" varStatus="status">
            <div class="weui-cell">
                <div class="weui-cell__bd" style="font-size:9px;">
                    ${history.processTime}
                </div>
                <div class="weui-cell__bd" style="margin-left:10px;font-size:9px;">
                    ${history.authOrgzName}
                </div>
                <div class="weui-cell__bd" style="font-size:9px;">
                    ${history.authUserName}
                </div>
                <div class="weui-cell__bd" style="font-size:9px;">
                    ${history.processTypeName}
                </div>
                <div class="weui-cell__bd" style="font-size:9px;">
                    ${history.processComment}
                </div>
            </div>
            </c:forEach>
        </div>
    </div>
    <div class="weui-tabbar">
        <a href="javascript:home();" class="weui-tabbar__item">
            <img src="${RESOURCE_PATH}/img/icon_nav_back.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label" ins="common.workflow.unit.history" inm="btnBack"></p>
        </a>
    </div>
</div>
</script>