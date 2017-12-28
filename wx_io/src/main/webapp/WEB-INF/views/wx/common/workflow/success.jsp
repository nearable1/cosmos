<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 成功界面 -->
<script type="text/html" id="tpl_page_success">
<div class="page">
    <div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title" ins="common.workflow.cmnMsg" inm="msgProcessSuccessTitle"></h2>
            <p class="weui-msg__desc" ins="common.workflow.cmnMsg" inm="msgProcessSuccessInfo"></p>
        </div>
        <div class="weui-msg__opr-area">
            <p class="weui-btn-area">
                <a href="javascript:$('#frmUnprocessList').submit();" class="weui-btn weui-btn_primary" ins="common.workflow.cmnMsg" inm="btnUnprocessList"></a>
            </p>
        </div>
    </div>
</div>
<form id="frmUnprocessList"  name="frmUnprocessList" action="${CONTEXT_PATH}/wx/view/cmn/wkf/user/unprocesslist/index" method="GET">
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
    $(function(){
        
    });
</script>
</script>