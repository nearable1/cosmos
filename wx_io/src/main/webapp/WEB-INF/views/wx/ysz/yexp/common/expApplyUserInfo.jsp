<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 申请者信息 -->
<div class="weui-cells__title" ins="ysz.yexp.common.expApplyUserInfo" inm="applyUserInfoTitle"></div>
<div class="weui-cells weui-cells_form">
	<!-- 申请者 -->
    <div class="weui-cell">
        <div class="weui-cell__hd">
            <label class="weui-label" ins="ysz.yexp.common.expApplyUserInfo" inm="applyUser"></label>
        </div>
        <div class="weui-cell__bd">
            <input id="applyAuthUserName" readonly="readonly" class="weui-input"  style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyAuthUserName}">
        </div>
    </div>
    <!-- 代理者 -->
    <div class="weui-cell">
        <div class="weui-cell__hd">
            <label class="weui-label" ins="ysz.yexp.common.expApplyUserInfo" inm="applyExecuteUserName"></label>
        </div>
        <div class="weui-cell__bd">
            <input id="applyExecuteUserName" readonly="readonly" class="weui-input"  style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyExecuteUserName}">
        </div>
    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd">
            <label class="weui-label" ins="ysz.yexp.common.expApplyUserInfo" inm="mainDepartmentCd"></label>
        </div>
        <div class="weui-cell__bd">
            <input id="mainDepartmentCd" readonly="readonly" class="weui-input" style="color:grey;"  value="${requestScope.pageData.matterSystemInfo.authOrgzName}">
        </div>
    </div>
</div>

