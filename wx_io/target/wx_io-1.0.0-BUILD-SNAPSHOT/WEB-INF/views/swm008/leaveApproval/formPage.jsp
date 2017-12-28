<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib.jsp"%> 
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
	
	<script src="${ctxStatic}/js/jquery-2.1.4.js"></script>
	<script src="${ctxStatic}/js/fastclick.js"></script>
	<script src="${ctxStatic}/js/jquery-weui.js"></script>
	
	<script type="text/javascript">
	</script>
</head>
  <body>
  	
  </body>
  	<form id="form" method="post" action="${ctx}/proApl/postProcessApproval" style="display: none;">
		<input id="result" type="hidden"  name="result">
		<input id="state" type="hidden"  name="state" >
		<button type='submit'>
	</form>
</html>
