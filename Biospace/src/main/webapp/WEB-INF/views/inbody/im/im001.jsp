<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发票查询一览</title>
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
			// 导出一览
			$("#export").on("click", function() {

				$("#searchForm").attr("action","${ctx}/im/invoice/list/export");
				$("#searchForm").submit();
				$("#searchForm").attr("action","${ctx}/im/invoice/list");
			});
			
			// AX销售发票导出
			$("#axExport").on("click", function() {

				$("#searchForm").attr("action","${ctx}/im/invoice/list/axExport");
				$("#searchForm").submit();
				$("#searchForm").attr("action","${ctx}/im/invoice/list");
			});

		});
		function cloneTableHeader_Width(){  
			if ($("#purTable>tbody>tr:not(.empty)").length > 0) {

		        var myTable_Width = (document.body.clientWidth);  

		        var height = $("#purTable").height() + 23;

		        FixTable("purTable", 2, myTable_Width, height);
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
	<h3 class="text-center page-title">发票查询一览</h3>
	<form:form id="searchForm" modelAttribute="searchForm" action="${ctx}/im/invoice/list" method="get" class="breadcrumb form-search">
		
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

	    <ul class="ul-form">
	        <li>
	            <label>发票来源：</label>
	            <form:select path="invoiceSource" class="input-medium">
	                <form:option value="" label=""/>
	                <form:options items="${fns:getDictList('DM0052')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	            </form:select>
	        </li>
	        <li>
	            <label>单据编号：</label>
	            <form:input path="orderNo" class="input-medium" type="text" value="" maxlength="50" />
	        </li>
	        <li>
	            <label>开票日期：</label>
	            <form:input path="invoiceDateBegin" type="text" readonly="readonly" maxlength="10" class="input-medium Wdate"
				value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			    ~
			    <form:input path="invoiceDateEnd" type="text" readonly="readonly" maxlength="10" class="input-medium Wdate"
				value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
	        </li>
	        <li>
	            <label>物料名称：</label>
	            <form:input path="materialName" class="input-medium" type="text" maxlength="300"/>
	        </li>
	        <li>
	            <label>发票类型：</label>
	            <form:select path="invoiceType" class="input-medium">
	                <form:option value="" label=""/>
	                <form:options items="${fns:getDictList('DM0004')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	            </form:select>
	        </li>
	        <li>
	            <label>发票抬头：</label>
	            <form:input path="invoiceTitle" class="input-medium" type="text" value="" maxlength="100" />
	        </li>
	        <li>
	            <label>发票编号：</label>
	            <form:input path="invoiceNo" class="input-medium" type="text" value="" maxlength="50" />
	        </li>
	        <li>
	            <label>组：</label>
	            <form:select path="organize" class="input-medium" >
			    	<form:option value="" label=""/>
			    	<form:options items="${fns:getSqlDictList('dict_organize')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	            </form:select>
	        </li>
	        <li>
	            <label>提成人/负责人：</label>
	            <form:input path="commissionPeison" class="remote employee input-medium" type="text" data-show="text" data-type="10,20"/>
	            <%-- <form:select path="commissionPeison" class="input-medium" >
			    	<form:option value="" label=""/>
			    	<form:options items="${fns:getSqlDictList('dict_employee')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	            </form:select> --%>
	            <!-- <div id="commissionPeisonHtml"></div> -->
	            <%-- <form:select path="commissionPeison" class="input-medium" >
	            </form:select>
	            <form:input path="selectCommissionPeison" type="hidden"/> --%>
	        </li>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();">
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/im/invoice/list';">
                <input id="export" class="btn btn-primary" type="button" value="一览导出">
                <input id="axExport" class="btn btn-primary" type="button" value="AX销售发票导出">
			</li>
	    </ul>
	</form:form>
	 <c:if test="${empty page.list}">
	 <div class="auto-scroll-x">
		<table id="purTable" class="table table-striped table-bordered table-condensed">
	 </c:if>
	 <c:if test="${!empty page.list}">
		<table id="purTable" class="table table-striped table-bordered table-condensed" style="width:2100px;">
	 </c:if>
<%-- 	<c:if test="${empty page.list}">
	<div style="overflow-x:scroll;">
	</c:if>
	<table id="purTable" class="table table-striped table-bordered table-condensed"> --%>
		<thead>
			<tr>
				<th>发票来源</th>
				<th>对应编号</th>
				<th>签约方</th>
				<th>物料名称</th>
				<th>业务员</th>
				<th>地区负责人</th>
				<th>提成人</th>
				<th>组</th>
				<th>明细行号</th>
				<th>单价</th>
				<th>数量</th>
				<th>发票类型</th>
				<th>开票金额</th>
				<th>税金</th>
				<th>开票日期</th>
				<th>发票编号</th>
				<th>开票抬头</th>
				<th>取票方式</th>
				<th>收件人</th>
				<th>联系电话</th>
				<th>收件地址</th>
				<th>快递编号</th>
				<th>快递公司</th>
			</tr>
		</thead>
		<tbody>
	        <c:if test="${!empty page.list}"> 
	            <c:forEach var="item" items="${page.list}" varStatus="status">
				    <tr>
				        <td>${fns:getDictLabel(item.invoiceSource, 'DM0052', '')}</td>
				        <td>${item.orderNo}</td>
				        <td>${item.customerName}</td>
				        <td>${item.materialName}</td>
				        <td>${item.employeeName}</td>
				        <td>${item.responsiblePersonName}</td>
				        <td>${item.commissionPeison}</td>
				        <td>${item.organize}</td>
				        <td>${item.lineNo}</td>
				        <td class="text-right"><fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/></td>
				        <td class="text-right"><fmt:formatNumber value="${item.num}" pattern="#,##0"/></td>
				        <td>${fns:getDictLabel(item.invoiceType, 'DM0004', '')}</td>
				        <td class="text-right"><fmt:formatNumber value="${item.invoiceAmount}" pattern="#,##0.00"/></td>
				        <td class="text-right"><fmt:formatNumber value="${item.tax}" pattern="#,##0.00"/></td>
				        <td>${item.invoiceDate}</td>
				        <td>${item.invoiceNo}</td>
				        <td>${item.invoiceTitle}</td>
				        <td>${fns:getDictLabel(item.ticketMethod, 'DM0048', '')}</td>
				        <td>${item.recipients}</td>
				        <td>${item.repTelephone}</td>
				        <td>${item.address}</td>
				        <td>${item.expressNo}</td>
				        <td>${item.expressCompany}</td>
				    </tr>
	            </c:forEach> 
	        </c:if>
	        <c:if test="${empty page.list}">
			    <tr class="empty"><td class="text-center" colspan="23">没有您需要的查询结果</td></tr>
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