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
        <script src="${RESOURCE_PATH}/js/wx/common/infinite.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/common/weui.io.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/cmnMsg.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/user/processlist.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/wx/common/workflow/user/unprocesslist.js${JS_SUFFIX}"></script>
        <script type="text/javascript">
            // 设置页面区域
            ychips.wxio.CmnUtil.setLocale('${requestScope.pageData.accountInfo.localId}');
        </script>
    </head>
    <body ontouchstart>
        <div class="container" id="container"></div>
        <script type="text/html" id="tpl_home">
        <div class="page">
            <div class="weui-tab__panel" id="unprocessList">
                <div class="weui-cells__title" ins="common.workflow.user.processlist" inm="processListTitle"></div>
                <div class="weui-cells weui-cells_form" id="unprocessListContent">
                    <input type="hidden" name="pageTotal" value="${requestScope.pageData.total}" />
                    <input type="hidden" name="pageOffset" value="${requestScope.pageData.offset}" />
                    <input type="hidden" name="pageCount" value="${requestScope.pageData.count}" />
                    <input type="hidden" name="pageRows" value="${requestScope.pageData.pageRows}" />
                    <input type="hidden" name="processUserCd" value="${requestScope.pageData.accountInfo.userCd}" />
                    <input type="hidden" name="processTenantId" value="${requestScope.pageData.accountInfo.tenantId}" />
                    <input type="hidden" name="sf" value="${requestScope.pageData.sf}" />
                    <input type="hidden" name="nc_token" value="${requestScope.pageData.nc_token}" />
                    <c:forEach items="${requestScope.pageData.unprocessNodeList}" var="obj" varStatus="status">
                    <div class="weui-cell weui-cell_access">
                        <input type="hidden" name="processPath" value="${obj.processPath}" />
                        <input type="hidden" name="processSystemMatterId" value="${obj.systemMatterId}" />
                        <input type="hidden" name="processUserDataId" value="${obj.userDataId}" />
                        <input type="hidden" name="processNodeId" value="${obj.nodeId}" />
                        <div class="weui-cell__bd">
                            <span style="vertical-align: middle">${obj.matterNumber}（${obj.matterName}）${obj.applyDate}-${obj.applyAuthUserName}</span>
                        </div>
                        <div class="weui-cell__ft"></div>
                    </div>
                    </c:forEach>
                </div>
            </div>
            <div class="weui-tabbar">
                <a href="javascript:$('#frmRefresh').submit();" class="weui-tabbar__item">
                    <img src="${RESOURCE_PATH}/img/icon_nav_reservecancel.png" alt="" class="weui-tabbar__icon">
                    <p class="weui-tabbar__label" ins="common.workflow.user.processlist" inm="btnRefresh"></p>
                </a>
            </div>
        </div>
        <form id="frmProcess" name="frmProcess" action="" method="GET">
            <input type="hidden" name="usercd" value="${requestScope.pageData.accountInfo.userCd}" />
            <input type="hidden" name="tenantid" value="${requestScope.pageData.accountInfo.tenantId}" />
            <input type="hidden" name="sf" value="${requestScope.pageData.sf}" />
            <input type="hidden" name="nc_token" value="${requestScope.pageData.nc_token}" />
            <input type="hidden" name="udid" value="" />
            <input type="hidden" name="smid" value="" />
            <input type="hidden" name="nodeid" value="" />
        </form>
        <form id="frmRefresh"  name="frmRefresh" action="${CONTEXT_PATH}/wx/view/cmn/wkf/user/unprocesslist/index" method="GET">
            <input type="hidden" name="usercd" value="${requestScope.pageData.accountInfo.userCd}" />
            <input type="hidden" name="tenantid" value="${requestScope.pageData.accountInfo.tenantId}" />
            <input type="hidden" name="sf" value="${requestScope.pageData.sf}" />
            <input type="hidden" name="nc_token" value="${requestScope.pageData.nc_token}" />
        </form>
        <script type="text/javascript">
            // 禁止回退
            history.pushState(null, null, document.URL);
            window.addEventListener('popstate', function () {
                history.pushState(null, null, document.URL);
            });

            // 翻页请求路径
            var pagingUrl = "${CONTEXT_PATH}/wx/view/cmn/wkf/user/unprocesslist/paging";

            $(function(){
                // 翻页
                $("#unprocessList").infinite(50, function() {
                    ychips.wxio.workflow.user.unprocessList.paging(pagingUrl, $("#unprocessListContent"));
                });

                // 是否有数据
                // 总件数
                var total = parseInt($("#unprocessListContent").find("input[name='pageTotal']").val());

                if (total == 0) {
                    $("#unprocessList").showNoDataItem();
                }

                // 项目点击
                $(".weui-cell_access").on("click", function() {
                    // 跳转路径
                    $("#frmProcess").attr("action", 
                        $(this).find("input[name='processPath']").val());

                    // 用户数据ID
                    $("#frmProcess").find("input[name='udid']").val(
                        $(this).find("input[name='processUserDataId']").val());

                    // 系统案件号
                    $("#frmProcess").find("input[name='smid']").val(
                        $(this).find("input[name='processSystemMatterId']").val());

                    // 节点号
                    $("#frmProcess").find("input[name='nodeid']").val(
                        $(this).find("input[name='processNodeId']").val());

                    // 提交
                    $("#frmProcess").submit();
                });
            });
        </script>
        </scritp>
    </body>
</html>
