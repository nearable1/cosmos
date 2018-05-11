<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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
		<li class="active"><a href="${ctx}/goods/goods/">商品列表</a></li>
		<shiro:hasPermission name="goods:goods:edit"><li><a href="${ctx}/goods/goods/form">商品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goods" action="${ctx}/goods/goods/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类型id：</label>
				<form:select path="typeid" class="input-medium">
					<form:option value="" label="全部商品"/>
					<form:options items="${family}" itemLabel="typeName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>商家id：</label>
				<form:select path="sellerid" class="input-medium">
					<form:option value="${sellerid }" label="${sellerName }"/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>价格：</label>
				<form:input path="price" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>剩余数量：</label>
				<form:input path="count" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>类型id</th>
				<th>商家id</th>
				<th>价格</th>
				<th>剩余数量</th>
				<th>折扣价</th>
				<th>已购买数量</th>
				<th>被收藏数</th>
				<th>图片</th>
				<th>尺寸</th>
				<th>update_date</th>
				<th>备注</th>
				<shiro:hasPermission name="goods:goods:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goods">
			<tr>
				<td><a href="${ctx}/goods/goods/form?id=${goods.id}">
					${goods.typename}
				</a></td>
				<td>
					${sellerName}
				</td>
				<td>
					${goods.price}
				</td>
				<td>
					${goods.count}
				</td>
				<td>
					${goods.discount}
				</td>
				<td>
					${goods.purchased}
				</td>
				<td>
					${goods.liked}
				</td>
				<td>
					${goods.picture}
				</td>
				<td>
					${goods.size}
				</td>
				<td>
					<fmt:formatDate value="${goods.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goods.remarks}
				</td>
				<shiro:hasPermission name="goods:goods:edit"><td>
    				<a href="${ctx}/goods/goods/form?id=${goods.id}">修改</a>
					<a href="${ctx}/goods/goods/delete?id=${goods.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>