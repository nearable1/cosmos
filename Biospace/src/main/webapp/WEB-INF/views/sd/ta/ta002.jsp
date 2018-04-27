<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>招标授权管理</title>
	<meta name="decorator" content="default"/>
    
    <style type="text/css">
    div.ellipsis {
        text-overflow: ellipsis;
        -moz-text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			cloneTableHeader_Width();
			// 导出
			$("#btnExport").click(function() {
				$("#searchForm").attr("action","${ctx}/sd/taTenderAuthor/export");
				$("#searchForm").submit();
				$("#searchForm").attr("action","${ctx}/sd/taTenderAuthor/list");
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function cloneTableHeader_Width(){  
			if ($("#contentTable>tbody>tr:not(.empty)").length > 0) {

		        /* //document.body.clientWidth获得客户区域(浏览器窗口,不包括菜单栏和状态栏,就是内容窗口)的宽度 - 35像素的滚动条宽度。  
		        var myTable_Width = (document.body.clientWidth-35);  
		          
		        //alert(myTable_Width);//测试屏幕宽度  
		        if((document.body.clientWidth-35) < 1030){  
		            myTable_Width=1030; //宽度  
		        } */
		        var myTable_Width = (document.body.clientWidth);  
		        var height = $("#contentTable").height() + 23;
		        //调用 锁定表头和 列 的JS函数  
		        FixTable("contentTable", 4, myTable_Width, height);
			}
         }
		
	</script>
	
	<style>
	table {
	    table-layout:fixed;
	}
	table td{
	    overflow:hidden;
        white-space:nowrap;
        text-overflow:ellipsis;
	}
	
	</style>
</head>
<body>
	<h3 class="text-center page-title">招标授权管理</h3>
	<form:form id="searchForm" modelAttribute="taTenderAuthor" action="${ctx}/sd/taTenderAuthor/list" method="get" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>招标编号：</label>
				<form:input path="authorizationNoSearch" htmlEscape="false" maxlength="50" style="width:120px;"/>
			</li>
			<li><label>项目名称：</label>
				<form:input path="projectNameSearch" htmlEscape="false" maxlength="100" style="width:200px;"/>
			</li>
			<li><label>招标单位：</label>
				<form:input path="tendereeSearch" htmlEscape="false" maxlength="100" style="width:200px;"/>
			</li>
			<li><label>申请状态：</label>
				<form:select path="workflowStatusSearch" style="width:150px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getSqlDictList('dict_workStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>招标结果：</label>
				<form:select path="bidResultSearch" style="width:120px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DM0044')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>授权日期：</label>
				<input name="validityDateFromSearch" id="validityDateFromSearch" class="input-medium Wdate " 
			        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text" style="width:120px;" 
			        maxlength="20" value="<fmt:formatDate value="${taTenderAuthor.validityDateFromSearch}" pattern="yyyy-MM-dd"/>"/>
			    ~
			    <input name="validityDateToSearch" id="validityDateToSearch" class="input-medium Wdate " 
			        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text" style="width:120px;"
			        maxlength="20" value="<fmt:formatDate value="${taTenderAuthor.validityDateToSearch}" pattern="yyyy-MM-dd"/>"/>
			</li>
			<li><label>开标日期：</label>
				<input name="bidOpeningDateFromSearch" id="bidOpeningDateFromSearch" class="input-medium Wdate " 
			        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text" style="width:120px;" 
			        maxlength="20"  value="<fmt:formatDate value="${taTenderAuthor.bidOpeningDateFromSearch}" pattern="yyyy-MM-dd"/>"/>
			    ~
			    <input name="bidOpeningDateToSearch" id="bidOpeningDateToSearch" class="input-medium Wdate " 
			        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text" style="width:120px;" 
			        maxlength="20" value="<fmt:formatDate value="${taTenderAuthor.bidOpeningDateToSearch}" pattern="yyyy-MM-dd"/>"/>
			</li>
		    <li>
		        <label>最终客户：</label>
				<input name="endCustomerIdSearch" type="text" value="${taTenderAuthor.endCustomerIdSearch}" 
				   maxlength="50" style="width:320px;" data-type="1" 
			       data-show="text" class="remote customer required customerId input-medium"/>
		    </li>
		    <li>
		        <label>代理商/经销商：</label>
				<input name="agentIdSearch" type="text" value="${taTenderAuthor.agentIdSearch}" 
				    maxlength="50" style="width:320px;" data-type="2,3"
			        data-show="text" class="remote customer required customerId input-medium"/>
		    </li>
			<li class="btns">
			    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			    <input class="btn btn-primary" id="btnClear" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/sd/taTenderAuthor/list';">
				<input class="btn btn-primary" id="btnExport" type="button" value="一览导出">
			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	 <c:if test="${empty page.list}">
	 <div class="auto-scroll-x">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
	 </c:if>
	 <c:if test="${!empty page.list}">
		<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:2100px;">
	 </c:if>
	 <%-- <c:if test="${empty page.list}">
	 <div class="auto-scroll-x">
	 </c:if>
	<table id="contentTable" class="table table-striped table-bordered table-condensed"> --%>
	       <!-- style="width:2400px;"> -->
		<thead>
			<tr>
				<th style="width:100px;">招标编号</th>
				<th style="width:200px;">项目名称</th>
				<th style="width:200px;">招标单位</th>
				<th style="width:100px;">开标日期</th>
				<th style="width:300px;">最终客户</th>
				<th style="width:100px;">行业</th>
				<th style="width:100px;">招标目的</th>
				<th style="width:100px;">招标性质</th>
				<th style="width:300px;">代理商/经销商</th>
				<th style="width:200px;">授权型号</th>
				<th style="width:200px;">招授权期间</th>
				<th style="width:100px;">申请状态</th>
				<th style="width:100px;">申请人</th>
				<th style="width:100px;">招标结果</th>
				<th style="width:300px;">备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="taTenderAuthor">
			<tr>
				<c:if test="${!empty taTenderAuthor.authorizationNo}">
				<td title="${taTenderAuthor.authorizationNo}"><div class="ellipsis" style="max-width:100px;"><a class="link" href="${ctx}/sd/taTenderAuthor/form?id=${taTenderAuthor.id}">
					${taTenderAuthor.authorizationNo}</a></div></td>
				<td title="${taTenderAuthor.projectName}"><div class="ellipsis" style="max-width:200px;">${taTenderAuthor.projectName}</div></td>
				</c:if>
				<c:if test="${empty taTenderAuthor.authorizationNo}">
				<td title="${taTenderAuthor.authorizationNo}"><div class="ellipsis" style="max-width:100px;">${taTenderAuthor.authorizationNo}</div></td>
				<td title="${taTenderAuthor.projectName}"><div class="ellipsis" style="max-width:200px;"><a class="link" href="${ctx}/sd/taTenderAuthor/form?id=${taTenderAuthor.id}">${taTenderAuthor.projectName}</a></div></td>
				</c:if>
				<td title="${taTenderAuthor.tenderee}"><div class="ellipsis" style="max-width:200px;">${taTenderAuthor.tenderee}</div></td>
				<td><fmt:formatDate value="${taTenderAuthor.bidOpeningDate}" pattern="yyyy-MM-dd"/></td>
			    <td>${taTenderAuthor.endCustomerName}</td>
				<td>${fns:getDictLabel(taTenderAuthor.industry, 'DM0002', '')}</td>
				<td>${fns:getDictLabel(taTenderAuthor.bidPurpose, 'DM0013', '')}</td>
				<td>${fns:getDictLabel(taTenderAuthor.tenderType, 'DM0014', '')}</td>
				<td>${taTenderAuthor.agentName}</td>
				<td>${taTenderAuthor.model}</td>
				<td><fmt:formatDate value="${taTenderAuthor.validityDateFrom}" pattern="yyyy-MM-dd"/>
				&nbsp;~&nbsp;<fmt:formatDate value="${taTenderAuthor.validityDateTo}" pattern="yyyy-MM-dd"/></td>
				<td>${fns:getDictLabel(taTenderAuthor.workflowStatus, 'DM0043', '')}</td>
				<td>${(fns:getUserById(taTenderAuthor.createBy.id)).name}</td>
				<td>${fns:getDictLabel(taTenderAuthor.bidResult, 'DM0044', '')}</td>
				<td><a title="${taTenderAuthor.bidRemarks}">${taTenderAuthor.bidRemarks}</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	 <c:if test="${empty page.list}">
	 </div>
	 </c:if>
	<div class="pagination">${page}</div>
</body>
</html>