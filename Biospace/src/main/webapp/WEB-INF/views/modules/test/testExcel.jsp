<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单据申请</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">  
	$(document).ready(function() {
		$("#btnExport").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#testForm").attr("action","${ctx}/test/testExcel/export");
					$("#testForm").submit();
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
		$("#btnImport").click(function(){
			$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
		});
	});
    </script> 
</head>
<body>
    <div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/test/testExcel/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
		</form>
	</div>
	<form id="testForm"  method="post" >
	<div>
	<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
	<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
	</div>
	</form>
	<sys:message content="${message}"/>
</body>
</html>