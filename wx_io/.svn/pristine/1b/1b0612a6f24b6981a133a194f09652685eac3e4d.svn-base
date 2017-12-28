<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib.jsp"%> 
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>WeUI</title>
    <link rel="stylesheet" href="${ctxStatic}/css/weui.css"/>
	<link rel="stylesheet" href="${ctxStatic}/css/example.css"/>
	
</head>
<body ontouchstart>

  <div class="container" id="container"></div>
<script type="text/html" id="tpl_iosActionsheetsp">
<div class="page">
	  <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">案件号</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" readonly="true" style="color:grey;" value="${requestScope.list.matterUserInfo.matterNumber}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">案件名</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" readonly="true" style="color:grey;" value="${requestScope.list.matterSystemInfo.matterName}">
        </div>
      </div>
      <div class="weui-cells__title">申请信息</div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">申请人</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" readonly="true" style="color:grey;" value="${requestScope.list.matterSystemInfo.applyAuthUserName}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">申请基准日</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" readonly="true" style="color:grey;" value="${requestScope.list.matterSystemInfo.applyBaseDate}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">申请日</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" readonly="true" style="color:grey;" value="${requestScope.list.matterSystemInfo.applyDate}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">处理者</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" readonly="true" style="color:grey;" value="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">负责部门</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" readonly="true" pattern="" value="">
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
	          <textarea class="weui-textarea" readonly="true" rows="3"></textarea>
	          <div class="weui-textarea-counter"><span>0</span>/200</div>
	        </div>
       </div>
       <div class="weui-btn-area">
	      <a class="weui-btn weui-btn_primary" href="javascript:" id="showTooltips">审批</a>
			<a class="weui-btn weui-btn_primary" href="javascript:home()" style="background:#00E3E3">返回</a>
       </div>
</div>
<script type="text/javascript">
	var resultMsg ="我是测试数据！";
    $(function(){
       $('#showTooltips').on('click', function(){
        $.ajax({
          type: 'POST',
          url: '${ctx}/sys/getAjax',
          data: { 
		 	"resultMsg":resultMsg
		  },
          dataType: 'json',
          success: function(data){
			alert(data[0].resultMsg);
			console.log(data[0]);
          },
		  error: function(){
			alert("我可能出错了！");
		  }
        });
       });
    });
</script>
</script>

<script type="text/html" id="tpl_home">
<div class="page">
   <div class="weui-cells__title">申请者信息</div>
    <div class="weui-cells weui-cells_form">
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">员工ID</label></div>
        <div class="weui-cell__bd">
          <input id="applyAuthUserCode" class="weui-input" readonly="true" style="color:grey;" value="${requestScope.list.matterSystemInfo.applyAuthUserCode}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">员工姓名</label></div>
        <div class="weui-cell__bd">
          <input id="applyAuthUserName" class="weui-input" readonly="true" style="color:grey;" value="${requestScope.list.matterSystemInfo.applyAuthUserName}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">代理者</label></div>
        <div class="weui-cell__bd">
          <input id="applyExecuteUserName" class="weui-input" readonly="true" style="color:grey;" value="${requestScope.list.matterSystemInfo.applyExecuteUserName}">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">员工所属</label></div>
        <div class="weui-cell__bd">
          <input id="" class="weui-input" type="" pattern="" placeholder="">
        </div>
      </div>
    </div>

	<div class="weui-cells__title">休假信息</div>
    <div class="weui-cells weui-cells_form">
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">休假类型</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" readonly="true" style="color:grey;" value="${requestScope.list.matterUserInfo.vacationType}">
        </div>
      </div>
      <div class="weui-cells__title">休假/漏刷卡日期</div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;起：</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" style="color:grey;width:60%;" readonly="true"  value="${requestScope.list.matterUserInfo.vacationStartDate}"><small><em style="color:blue;">上午</em></small>
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;止：</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" style="color:grey;width:60%;" readonly="true" value="${requestScope.list.matterUserInfo.vacationEndDate}"><small><em style="color:blue;">下午</em></small>
        </div>
      </div>
       <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">紧急联络</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" readonly="true" style="color:grey;" value="${requestScope.list.matterUserInfo.emergencyContact}">
        </div>
      </div>
      <div class="weui-cells__title">备注</div>
      <div class="weui-cell">
        <div class="weui-cell__bd">
          <textarea class="weui-textarea" readonly="true" rows="3" style="color:grey;">${requestScope.list.matterUserInfo.vacationReason}</textarea>
          <div class="weui-textarea-counter"><span>0</span>/200</div>
        </div>
      </div>
    </div>
    <div class="page__bd page__bd_spacing">
        <a href="javascript:;" class="weui-btn weui-btn_primary" id="showIOSActionSheet">处理</a>
    </div>

    <div>
        <div class="weui-mask" id="iosMask" style="display: none"></div>
        <div class="weui-actionsheet" id="iosActionsheet">
            <div class="weui-actionsheet__title">
                <p class="weui-actionsheet__title-text">请确认您所选择的处理方式！</p>
            </div>
            <div class="weui-actionsheet__menu">
				<a class="js_item" data-id="iosActionsheetsp" href="javascript:;">
					<div class="weui-actionsheet__cell" style="background:rgb(4, 190, 2);">审批</div>
                </a>
                <div class="weui-actionsheet__cell" style="background:rgb(255, 102, 0);" href="javascript:;">拒绝</div>
                <div class="weui-actionsheet__cell" style="background:#F9F900;" href="javascript:;">保留</div>
                <div class="weui-actionsheet__cell" style="background:rgb(246, 56, 58);" href="javascript:;">退回</div>
            </div>
            <div class="weui-actionsheet__action">
                <div class="weui-actionsheet__cell" id="iosActionsheetCancel">取消</div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    
    $(function(){
        var $iosActionsheet = $('#iosActionsheet');
        var $iosMask = $('#iosMask');
		
		//隐藏
        function hideActionSheet() {
            $iosActionsheet.removeClass('weui-actionsheet_toggle');
            $iosMask.fadeOut(200);
        }
		
        $iosMask.on('click', hideActionSheet);
		
		//跳转
		  $('.js_item').on('click', function(){
            var id = $(this).data('id');
            window.pageManager.go(id);
        });
        $('#iosActionsheetCancel').on('click', hideActionSheet);
		//显现
        $("#showIOSActionSheet").on("click", function(){
            $iosActionsheet.addClass('weui-actionsheet_toggle');
            $iosMask.fadeIn(200);
        });

    });
</script>
</script>
    <script src="${ctxStatic}/js/common/zepto.min.js"></script>
    <script src="${ctxStatic}/js/common/example.js"></script>
</body>
</html>
