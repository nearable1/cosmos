<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同管理一览</title>
	<meta name="decorator" content="default"/>
	
	<style type="text/css">
/* 	table { */
/* 		table-layout: fixed; */
/* 	} */
/* 	td.ellipsis {   */
/* 	    text-overflow: ellipsis; */
/* 	    -moz-text-overflow: ellipsis; /* for Firefox,mozilla */   */
/* 	    overflow: hidden; */
/* 	    white-space: nowrap; */
/* 	} */
	</style>
	
	<script type="text/javascript">
	$(document).ready(function() {
		cloneTableHeader_Width();
		// 导出合同明细
		$("#exportDtl").on("click", function() {
/* 			var fHtml = '<form method="get" action="' + '${ctx}' + '/sd/soOrder/list/exportDtl">';
			    fHtml += '</form>';
			$(fHtml).appendTo('body').submit().remove(); */
			$("#searchForm").attr("action","${ctx}/sd/soOrder/list/exportDtl");
			$("#searchForm").submit();
			$("#searchForm").attr("action","${ctx}/sd/soOrder/list");
		});
		
		// 应收账款一览
		$("#exportReceive").on("click", function() {
/* 			var fHtml = '<form method="get" action="' + '${ctx}' + '/sd/soOrder/list/exportReceive">';
			    fHtml += '</form>';
			$(fHtml).appendTo('body').submit().remove(); */
			$("#searchForm").attr("action","${ctx}/sd/soOrder/list/exportReceive");
			$("#searchForm").submit();
			$("#searchForm").attr("action","${ctx}/sd/soOrder/list");
		});
	});
	function cloneTableHeader_Width(){  
		if ($("#orderDtlTable>tbody>tr:not(.empty)").length > 0) {

	        var myTable_Width = (document.body.clientWidth);  

	        var height = $("#orderDtlTable").height() + 23;

	        FixTable("orderDtlTable", 2, myTable_Width, height);
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
	<h3 class="text-center page-title">合同管理一览</h3>
	<form:form id="searchForm" modelAttribute="searchForm" action="${ctx}/sd/soOrder/list" method="get" class="breadcrumb form-search">
		
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

	    <ul class="ul-form">
			<li>
				<label>最终客户：</label>
				<form:input path="endCustomerId" type="text" data-show="text" value="" maxlength="50" class="remote customer input-medium" style="width: 301px"/>
			</li>
			<li>
				<label>签约方：</label>
				<form:input path="customerId" type="text" data-show="text" value="" maxlength="50" class="remote customer input-medium" style="width: 301px"/>
			</li>
			<li class="clearfix"></li>
	        <li>
	            <label>合同编号：</label>
	            <form:input path="orderNo" class="input-medium" type="text" value="" maxlength="50" />
	        </li>
	        <li>
	            <label>物料名称：</label>
	            <form:input path="materialName" class="input-medium" type="text" maxlength="300"/>
	        </li>
	        <li>
	            <label>订立日期：</label>
	            <form:input path="orderDateBegin" type="text" readonly="readonly" maxlength="10" class="input-medium Wdate"
				value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			    ~
			    <form:input path="orderDateEnd" type="text" readonly="readonly" maxlength="10" class="input-medium Wdate"
				value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
	        </li>
	        <li>
				<label>合同分类：</label>
				<form:select path="orderType" class="input-medium">
					<form:option value="" label=""/>
				    <form:options items="${fns:getDictList('DM0008')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				 </form:select>
			</li>
	        <li>
	            <label>地区：</label>
	            <form:select path="region" class="input-medium">
	                <form:option value="" label=""/>
	                <form:options items="${fns:getDictList('DM0049')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	            </form:select>
	        </li>
			<li>
				<label>业务员：</label>
				<form:input path="employeeNo" class="remote employee input-medium" type="text" data-show="text" data-type="10,20"/>
				<%-- <form:select path="employeeNo" class="input-medium">
					<form:option value="" label="" />
					<form:options items="${fns:getSqlDictList('dict_employee')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select> --%>
			</li>
		</ul>
		<ul class="ul-form">
	        <li>
	            <label>合同状态：</label>
	            <form:select path="workflowStatus" class="input-medium">
	                <form:option value="" label=""/>
	                <form:options items="${fns:getDictList('DM0015')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	            </form:select>
	        </li>
	        <li>
	            <label>收款状态：</label>
	            <form:select path="receiveStatus" class="input-medium" >
	                <form:option value="" label=""/>
	                <form:options items="${fns:getDictList('DM0011')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	            </form:select>
	        </li>
	        <li>
	            <label>开票状态：</label>
	            <form:select path="invoiceStatus" class="input-medium">
	                <form:option value="" label=""/>
	                <form:options items="${fns:getDictList('DM0012')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	            </form:select>
	        </li>
	        <li>
	            <label>发货状态：</label>
	            <form:select path="deliverStatus" class="input-medium">
	                <form:option value="" label=""/>
	                <form:options items="${fns:getDictList('DM0010')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	            </form:select>
	        </li>
	    </ul>
	    <ul class="ul-form">
		    <shiro:hasPermission name="cm:manager:edit">
	            <li class="btns">
	                <input class="btn btn-primary" type="button" value="其他业绩录入" onclick="javascript:window.location.href='${ctx}/sd/soOrder/form?dataType=order';">
				</li>
			</shiro:hasPermission>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();">
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/sd/soOrder/list';">
                <input id="exportDtl" class="btn btn-primary" type="button" value="一览导出">
                <shiro:hasPermission name="cm:manager:edit">
                	<input id="exportReceive" class="btn btn-primary" type="button" value="应收账款导出">
                </shiro:hasPermission>
			</li>
	    </ul>
	</form:form>
	 <c:if test="${empty page.list}">
	 <div class="auto-scroll-x">
		<table id="orderDtlTable" class="table table-striped table-bordered table-condensed">
	 </c:if>
	 <c:if test="${!empty page.list}">
		<table id="orderDtlTable" class="table table-striped table-bordered table-condensed" style="width:2100px;">
	 </c:if>
	 <%-- <c:if test="${empty page.list}">
	 <div class="auto-scroll-x">
	 </c:if>
	<table id="orderDtlTable" class="table table-striped table-bordered table-condensed"> --%>
		<thead>
			<tr>
				<shiro:hasAnyPermissions name="sd:soOrder:edit, cm:manager:edit, sd:imInvoice:edit">
					<th></th>
				</shiro:hasAnyPermissions>
				<th>合同编号</th>
				<th>地区</th>
				<th>城市</th>
				<th>行业</th>
				<th>具体分类</th>
				<th>科室/系</th>
				<th>销售方式</th>
				<th>签约方</th>
				<th>最终客户</th>
				<th>物料名称</th>
				<th>数量</th>
				<th>金额</th>
				<th>订立日期</th>
				<th>开票月</th>
				<th>业务员</th>
				<th>地区负责人</th>
				<th>质保期（年）</th>
				<th>开票状态</th>
			</tr>
		</thead>
		<tbody>
	        <c:if test="${!empty page.list}"> 
	            <c:forEach var="item" items="${page.list}" varStatus="status">
				    <tr>
				        
<%-- 				        <shiro:hasPermission name="sd:soOrder:edit">
				        	<input class="btn btn-primary" type="button" value="合同管理" onclick="javascript:window.location.href='${ctx}/sd/soOrder/form?id=${item.id}&dataType=order';">
				        </shiro:hasPermission> --%>
				        <shiro:hasAnyPermissions name="cm:manager:edit, sd:soOrder:edit, sd:imInvoice:edit">
				        	<td>
				        	<c:if test="${item.workflowStatus == '50'}">
						        <shiro:hasPermission name="cm:manager:edit">
							     	<input class="btn btn-primary" type="button" value="编辑" onclick="javascript:window.location.href='${ctx}/sd/soOrder/form?id=${item.id}';">
						        </shiro:hasPermission>
						        <shiro:hasPermission name="sd:imInvoice:edit">
							    	<input class="btn btn-primary" type="button" value="发票" onclick="javascript:window.location.href='${ctx}/sd/soOrder/form?id=${item.id}&dataType=invoice';">
							    </shiro:hasPermission>
							</c:if>
						        <shiro:hasPermission name="sd:soOrder:edit">
							    	<input class="btn btn-primary" type="button" value="合同" onclick="javascript:window.location.href='${ctx}/sd/soOrder/form?id=${item.id}&dataType=order';">
							    </shiro:hasPermission>
				        	</td>
				        </shiro:hasAnyPermissions>
				        
				        <td>${item.orderNo}</td>
				        <td>${fns:getDictLabel(item.region, 'DM0049', '')}</td>
				        <td>${item.cityName}</td>
						<td>${fns:getDictLabel(item.industry, 'DM0002', '')}</td>
						<td>${fns:getDictLabel(item.customerDiff, 'DM0003', '')}</td>
						<td>${fns:getDictLabel(item.customerSegmentation, 'DM0039', '')}</td>
				        <td>${fns:getDictLabel(item.priceSystem, 'DM0005', '')}</td>
				        <td>${item.customerChName}</td>
				        <td>${item.endCustomerName}</td>
				        <td>${item.materialName}</td>
				        <td class="text-right">${item.num}</td>
				        <td class="text-right "><fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/></td>
				        <td>${item.orderDate}</td>
				        <td><fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM"/></td>
				        <td>${item.employeeName}</td>
				        <td>${item.responsiblePersonName}</td>
				        <td>${item.warrantyPeriod}</td>
				        <td>${fns:getDictLabel(item.invoiceStatus, 'DM0012', '')}</td>
				    </tr>
	            </c:forEach> 
	        </c:if>
	        <c:if test="${empty page.list}">
			    <tr class="empty"><td class="text-center" colspan="19">没有您需要的查询结果</td></tr>
	        </c:if>
		</tbody>
	</table>
	 <c:if test="${empty page.list}">
	 </div>
	 </c:if>
    <div class="pagination">${page}</div>
	<sys:message content="${message}"/>
</body>
</html>