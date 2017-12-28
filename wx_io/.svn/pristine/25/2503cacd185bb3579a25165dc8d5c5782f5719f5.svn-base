<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib.jsp"%> 
<!DOCTYPE html>
<html>
  <head>
    <title>请假审批</title>
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
	  var state="";
	  $(function() {
	    FastClick.attach(document.body);
	    $("#empCd").val('${requestScope.list.matterSystemInfo.userDataId}');
	    $("#applyAuthUserName").val('${requestScope.list.matterSystemInfo.applyAuthUserName}');
	    $("#applyExecuteUserName").val('${requestScope.list.matterSystemInfo.applyExecuteUserName}');
	  });
	  
	  function getState(state){
		  $("#result").val('${requestScope.list.result}');
		  $("#state").val(state);
		  $("#form").submit();
// 		  $.post("${ctx}/proApl/postProcessApproval",{"result":result});
	  };
	  
	  $(document).on("click", "#show-actions", function() {
	        $.actions({
	          title: "选择操作",
	          onClose: function() {
	            console.log("close");
	          },
	          actions: [
	            {
	              text: "审批",
	              className: "color-primary",
	              onClick: function() {
	            	  getState(1);
	              }
	            },
	            {
	              text: "拒绝",
	              className: "color-warning",
	              onClick: function() {
	                $.alert("你选择了“拒绝”");
	              }
	            },
	            {
	              text: "保留",
	              className: 'color-danger',
	              onClick: function() {
	                $.alert("你选择了“保留”");
	              }
	            },
	            {
	              text: "退回",
	              className: 'color-danger',
	              onClick: function() {
	                $.alert("你选择了“退回”");
	              }
	            }
	          ]
	        });
	      });

	  	  //设置按钮样式
	      $(document).on("click", "#show-actions-bg", function() {
	        $.actions({
	          actions: [
	            {
	              text: "审批",
	              className: "bg-primary",
	            },
	            {
	              text: "拒绝",
	              className: "bg-warning",
	            },
	            {
	              text: "保留",
	              className: 'bg-danger',
	            },
	            {
	              text: "退回",
	              className: 'bg-danger',
	            }
	          ]
	        });
	      });
	</script>
</head>
  <body ontouchstart>
  	<%@include file="../../headPage.jsp"%>
    <div class="weui-cells__title">休假信息</div>
    <div class="weui-cells weui-cells_form">
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">休假类型</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" value="${requestScope.list.matterUserInfo.vacationType}">
        </div>
      </div>
      <div class="weui-cells__title">休假/漏刷卡日期</div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;起：</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="" style="width:60%;" value="${requestScope.list.matterUserInfo.vacationStartDate}">上午
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;止：</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="" style="width:60%;" value="${requestScope.list.matterUserInfo.vacationEndDate}">下午
        </div>
      </div>
       <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">紧急联络</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="" value="${requestScope.list.matterUserInfo.emergencyContact}">
        </div>
      </div>
      <div class="weui-cells__title">备注</div>
      <div class="weui-cell">
        <div class="weui-cell__bd">
          <textarea class="weui-textarea" placeholder="" rows="3" style="color:red;">${requestScope.list.matterUserInfo.vacationReason}</textarea>
          <div class="weui-textarea-counter"><span>0</span>/200</div>
        </div>
      </div>
    </div>
    <div class='demos-content-padded'>
      <a href="javascript:;" id='show-actions' class="weui-btn weui-btn_primary">处理</a>
    </div>
  </body>
  	<form id="form" method="post" action="${ctx}/proApl/postProcessApproval" style="display: none;">
		<textarea id="result" type="hidden"  name="result" />
		<input id="state" type="hidden"  name="state" >
		<button type='submit'>
	</form>
</html>
