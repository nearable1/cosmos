<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib.jsp"%> 
<!DOCTYPE html>
<html>
  <head>
    <title>申请人信息</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/weui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/jquery-weui.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/demos.css">
	
	<script src="${ctxStatic}/js/common/jquery-2.1.4.js"></script>
	<script src="${ctxStatic}/js/common/fastclick.js"></script>
	<script src="${ctxStatic}/js/common/jquery-weui.js"></script>
	
	<script type="text/javascript">
	</script>
</head>
  <body ontouchstart>
    <div class="weui-cells__title">申请者信息</div>
    <div class="weui-cells weui-cells_form">
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">员工ID</label></div>
        <div class="weui-cell__bd">
          <input id="applyAuthUserCode" class="weui-input" type="" pattern="" value="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">员工姓名</label></div>
        <div class="weui-cell__bd">
          <input id="applyAuthUserName" class="weui-input" type="" pattern="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">代理者</label></div>
        <div class="weui-cell__bd">
          <input id="applyExecuteUserName" class="weui-input" type="" pattern="" placeholder="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">员工所属</label></div>
        <div class="weui-cell__bd">
          <input id="" class="weui-input" type="" pattern="" placeholder="">
        </div>
      </div>
    </div>
  </body>
</html>
