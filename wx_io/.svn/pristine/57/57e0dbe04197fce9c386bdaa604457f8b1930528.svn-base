<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib.jsp"%> 
<!DOCTYPE html>
<html>
  <head>
    <title>审批界面</title>
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
	  $(function() {
	    FastClick.attach(document.body);
	  });
	  
	</script>
</head>
  <body ontouchstart>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">案件号</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" value="${requestScope.data.matterUserInfo.matterNumber}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">案件名</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" value="${requestScope.data.matterUserInfo.matterName}">
        </div>
      </div>
      <div class="weui-cells__title">申请信息</div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">申请人</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" value="${requestScope.data.matterUserInfo.applyAuthUserName}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">申请基准日</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" value="${requestScope.data.matterUserInfo.applyBaseDate}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">申请日</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" value="${requestScope.data.matterUserInfo.applyDate}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">处理者</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" value="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">负责部门</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" value="">
        </div>
      </div>
      <div class="weui-cells__title">电子印章</div>
       <div class="weui-cell">
    		<div class="weui-cell weui-cell_select">
     		 <div class="weui-cell__hd"><label class="weui-label">限定关键字</label></div>
	        <div class="weui-cell__bd">
	          <select class="weui-select" name="select1">
	            <option selected="" value="1">jobs</option>
	            <option value="2">bill</option>
	          </select>
        	</div>
      	</div>
       </div>
       <div class="weui-cell">
       		<div class="weui-cell__hd"><label class="weui-label"></label></div>
       		<div class="weui-cell__bd">
       			<img src="${ctxStatic}/img/icon.png" alt="" style="width:100px;margin-right:5px;display:block">
       		</div>
       </div>
   	   <div class="weui-cell">
	   	   <div class="weui-cell__hd"><label class="weui-label">注解</label></div>
	        <div class="weui-cell__bd">
	          <textarea class="weui-textarea" placeholder="" rows="2"></textarea>
	          <div class="weui-textarea-counter"><span>0</span>/200</div>
	        </div>
       </div>
       <div class="weui-btn-area">
	      <a class="weui-btn weui-btn_primary" href="javascript:" id="showTooltips">审批</a>
       </div>
  </body>
</html>
