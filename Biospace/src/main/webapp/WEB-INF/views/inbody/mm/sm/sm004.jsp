<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>待归还一览查询</title>
	<meta name="decorator" content="default"/>
	
	<style type="text/css">
     div.ellipsis {
         text-overflow: ellipsis; 
         -moz-text-overflow: ellipsis; /* for Firefox,mozilla */   
         overflow: hidden;
         white-space: nowrap;
     }
	</style>
	
	<script type="text/javascript">
	$(document).ready(function() {
		//cloneTableHeader_Width();
		// 导出待归还一览明细
		$("#exportDtl").on("click", function() {
			$("#searchForm").attr("action","${ctx}/mm/storageManagement/unrestoredList/exportDtl");
			$("#searchForm").submit();
			$("#searchForm").attr("action","${ctx}/mm/storageManagement/unrestoredList");
		});

		$("input[type=checkbox]").change(function(){
			if ($(this).is(':checked')) {
				$("#ifExpired").val("1");
			} else {
				$("#ifExpired").val("0");
			}
		});
	});
	function cloneTableHeader_Width(){  
		if ($("#unrestoredDtlTable>tbody>tr:not(.empty)").length > 0) {

	        var myTable_Width = (document.body.clientWidth);  

	        var height = $("#unrestoredDtlTable").height() + 23;

	        FixTable("unrestoredDtlTable", 2, myTable_Width, height);
		}
     }
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);

			$("#searchForm").submit();
	    	return false;
	    }
	</script>

</head>
<body>
	<h3 class="text-center page-title">待归还一览查询</h3>
	<form:form id="searchForm" modelAttribute="searchForm" action="${ctx}/mm/storageManagement/unrestoredList" method="get" class="breadcrumb form-search">
		
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

	    <ul class="ul-form">
			<li>
				<label>S/N：</label>
				<form:input path="snNo" type="text" class="input-medium"/>
			</li>
			<li>
				<label>物料号：</label>
				<form:input path="materialNo" type="text" class="input-medium remote material materialNo"/>
			</li>
	        <li>
	            <label>物料名称：</label>
	            <form:input path="materialName" class="input-medium" type="text"/>
	        </li>
			<li class="clearfix"></li>
	        <li>
				<label>借出分类：</label>
				<form:select path="lendingType" class="input-mini">
					<form:option value="" label=""/>
				    <form:options items="${fns:getDictList('DM0036')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			</li>
			<li>
				<label style="width:80px">
					<c:if test="${searchForm.ifExpired == '1'}">
						<form:input path="ifExpired" type="hidden" value="1"/>
						<input type="checkbox" class="inline" checked="checked"/>
					</c:if>
					<c:if test="${searchForm.ifExpired == '0' || empty searchForm.ifExpired}">
						<form:input path="ifExpired" type="hidden" value="0"/>
						<input type="checkbox" class="inline"/>
					</c:if>
					已过期
				</label>
			</li>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();">
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/mm/storageManagement/unrestoredList';">
                <input id="exportDtl" class="btn btn-primary" type="button" value="一览导出">
			</li>
	    </ul>
	</form:form>
	<div style="overflow-x:scroll;">
	<table id="unrestoredDtlTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>物料号</th>
				<th width="100px">物料名称</th>
				<th width="80px">S/N</th>
				<th>生产日期</th>
				<th>数量</th>
				<th>借出目的</th>
				<th width="100px">客户名称</th>
				<th width="65px">行业</th>
				<th width="100px">备注</th>
				<th width="100px">相关配件备注</th>
				<th>借出时间</th>
				<th>借出到期日</th>
				<th>借出负责人</th>
			</tr>
		</thead>
		<tbody>
	        <c:if test="${!empty page.list}"> 
	            <c:forEach var="item" items="${page.list}" varStatus="status">
				    <tr>
				        <td>${item.materialNo}</td>
				        <td title="${item.materialName}"><div style="max-width:100px" class="ellipsis">${item.materialName}</div></td>
				        <td title="${item.snNo}"><div style="max-width:80px" class="ellipsis">${item.snNo}</div></td>
				        <td><fmt:formatDate value="${item.productionDate}" pattern="yyyy-MM-dd"/></td>
				        <td class="text-right">${item.num}</td>
				        <td>${fns:getDictLabel(item.lendingType, 'DM0036', '')}</td>
				        <td title="${item.lendingName}"><div style="max-width:100px" class="ellipsis">${item.lendingName}</div></td>
				        <td title="${fns:getDictLabel(item.industry, 'DM0002', '')}"><div style="max-width:65px" class="ellipsis">${fns:getDictLabel(item.industry, 'DM0002', '')}</div></td>
				        <td title="${item.newRemarks}"><div style="max-width:100px" class="ellipsis">${item.newRemarks}</div></td>
				        <td title="${item.accessoriesRemarks}"><div style="max-width:100px" class="ellipsis">${item.accessoriesRemarks}</div></td>
				        <td><fmt:formatDate value="${item.lendingDateFrom}" pattern="yyyy-MM-dd"/></td>
				        <td><fmt:formatDate value="${item.lendingDateTo}" pattern="yyyy-MM-dd"/></td>
				        <td>${item.responsiblePersonName}</td>
				    </tr>
	            </c:forEach> 
	        </c:if>
	        <c:if test="${empty page.list}">
			    <tr class="empty"><td class="text-center" colspan="13">没有您需要的查询结果</td></tr>
	        </c:if>
		</tbody>
	</table>
	</div>
    <div class="pagination">${page}</div>
	<sys:message content="${message}"/>
</body>
</html>