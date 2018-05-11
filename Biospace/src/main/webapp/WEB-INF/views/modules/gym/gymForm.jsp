<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存健身知识管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/gym/gym/">保存健身知识列表</a></li>
		<li class="active"><a href="${ctx}/gym/gym/form?id=${gym.id}">保存健身知识<shiro:hasPermission name="gym:gym:edit">${not empty gym.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="gym:gym:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="gym" action="${ctx}/gym/gym/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">视频路径：</label>
			<div class="controls">
				<form:hidden id="videoUrl" path="videoUrl" htmlEscape="false" maxlength="64" class="input-xlarge"/>
				<sys:ckfinder input="videoUrl" type="files" uploadPath="/gym/gym" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">视频名称：</label>
			<div class="controls">
				<form:input path="videoName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商家id：</label>
			<div class="controls">
				<form:select path="sellerId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文章名称：</label>
			<div class="controls">
				<form:input path="articalName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片路径：</label>
			<div class="controls">
				<form:hidden id="pictureUrl" path="pictureUrl" htmlEscape="false" maxlength="64" class="input-xlarge"/>
				<sys:ckfinder input="pictureUrl" type="files" uploadPath="/gym/gym" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">封面路径：</label>
			<div class="controls">
				<form:hidden id="coverUrl" path="coverUrl" htmlEscape="false" maxlength="64" class="input-xlarge"/>
				<sys:ckfinder input="coverUrl" type="files" uploadPath="/gym/gym" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="gym:gym:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>