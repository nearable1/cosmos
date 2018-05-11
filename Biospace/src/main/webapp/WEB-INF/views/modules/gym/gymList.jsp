<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存健身知识管理</title>
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
		<li class="active"><a href="${ctx}/gym/gym/">保存健身知识列表</a></li>
		<shiro:hasPermission name="gym:gym:edit"><li><a href="${ctx}/gym/gym/form">保存健身知识添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="gym" action="${ctx}/gym/gym/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>视频名称：</label>
				<form:input path="videoName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>商家id：</label>
				<form:select path="sellerId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>视频路径</th>
				<th>视频名称</th>
				<th>商家id</th>
				<th>文章名称</th>
				<th>图片路径</th>
				<th>封面路径</th>
				<th>update_date</th>
				<th>备注</th>
				<shiro:hasPermission name="gym:gym:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="gym">
			<tr>
				<td><a href="${ctx}/gym/gym/form?id=${gym.id}">
					${gym.videoUrl}
				</a></td>
				<td>
					${gym.videoName}
				</td>
				<td>
					${fns:getDictLabel(gym.sellerId, '', '')}
				</td>
				<td>
					${gym.articalName}
				</td>
				<td>
					${gym.pictureUrl}
				</td>
				<td>
					${gym.coverUrl}
				</td>
				<td>
					<fmt:formatDate value="${gym.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${gym.remarks}
				</td>
				<shiro:hasPermission name="gym:gym:edit"><td>
    				<a href="${ctx}/gym/gym/form?id=${gym.id}">修改</a>
					<a href="${ctx}/gym/gym/delete?id=${gym.id}" onclick="return confirmx('确认要删除该保存健身知识吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>