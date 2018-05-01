<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/student/demoClasses/">班级信息列表</a></li>
		<shiro:hasPermission name="student:demoClasses:edit"><li><a href="${ctx}/student/demoClasses/form">班级信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="demoClasses" action="${ctx}/student/demoClasses/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>classname：</label>
				<form:input path="classname" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>classname</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="student:demoClasses:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="demoClasses">
			<tr>
				<td><a href="${ctx}/student/demoClasses/form?id=${demoClasses.id}">
					${demoClasses.classname}
				</a></td>
				<td>
					<fmt:formatDate value="${demoClasses.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${demoClasses.remarks}
				</td>
				<shiro:hasPermission name="student:demoClasses:edit"><td>
    				<a href="${ctx}/student/demoClasses/form?id=${demoClasses.id}">修改</a>
					<a href="${ctx}/student/demoClasses/delete?id=${demoClasses.id}" onclick="return confirmx('确认要删除该班级信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>