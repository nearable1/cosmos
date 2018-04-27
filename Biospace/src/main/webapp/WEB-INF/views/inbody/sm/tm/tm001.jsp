<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>检测记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			cloneTableHeader_Width();
			
			// 导出
			$("#btnExport").click(function() {
				$("#searchForm").attr("action","${ctx}/tm/tmTesting/export");
				$("#searchForm").submit();
				$("#searchForm").attr("action","${ctx}/tm/tmTesting/list");
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
		        FixTable("contentTable", 3, myTable_Width, height);
			}
         }
		
	</script>
	<style>
	.form-search .ul-form li{
	    width:250px;
	}
	
	.form-search .ul-form li label{
	    width:80px;
	}
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
	<h3 class="text-center page-title">检测记录查询</h3>
	<form:form id="searchForm" modelAttribute="tmTesting" action="${ctx}/tm/tmTesting/list" method="get" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    <li style="width:350px;">
		        <label>检测日期：</label>
			    <input name="testingDateFromSearch" id="testingDateFromSearch" class="input-medium Wdate " 
			        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text"  style="width:120px;"
			        maxlength="20" value="<fmt:formatDate value="${tmTesting.testingDateFromSearch}" pattern="yyyy-MM-dd"/>"/>
			    ~
			    <input name="testingDateToSearch" id="testingDateToSearch" class="input-medium Wdate " 
			        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text"  style="width:120px;"
			        maxlength="20" value="<fmt:formatDate value="${tmTesting.testingDateToSearch}" pattern="yyyy-MM-dd"/>"/>
		    </li>
		    <li>
		        <label>S/N：</label>
			    <form:input path="snNoSearch" style="width:150px;" maxlength="50"/>
		    </li>
		    <li>
		        <label>型号：</label>
			    <form:input path="materialNoSearch"
				    style="width:150px;" maxlength="50"/>
		    </li>
		    <li>
		        <label>当前状态：</label>
			    <form:select path="statusSearch" style="width:150px;"  class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DM0033')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		        </form:select>
		    </li>
		    <li style="width:350px;">
		        <label>类型：</label>
			    <form:select path="machineTypeSearch" style="width:150px;"  class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DM0032')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		        </form:select>
		    </li>
		    <li>
		        <label>检测方式：</label>
			    <form:select path="testingTypeSearch" style="width:150px;"  class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DM0024')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		        </form:select>
		    </li>
		    <li>
		        <label>检测结果：</label>
			    <form:select path="testingResultSearch" style="width:150px;"  class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DM0025')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		        </form:select>
		    </li>
		    <li>
		        <label>附件说明：</label>
			    <form:select path="additionalInstructionsSearch" style="width:150px;"  class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DM0038')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		        </form:select>
		    </li>
		    <li style="width:350px;">
		        <c:if test="${tmTesting.ifPrimeProblemSearch eq '0' || empty tmTesting.ifPrimeProblemSearch}">
					<label style="width:150px;"><input type="checkbox" name="ifPrimeProblemSearch" id="ifPrimeProblemSearch"/>仅抽取初期不良</label>
				</c:if>
		        <c:if test="${tmTesting.ifPrimeProblemSearch eq '1'}">
					<label style="width:150px;"><input type="checkbox" name="ifPrimeProblemSearch" id="ifPrimeProblemSearch" checked/>仅抽取初期不良</label>
				</c:if>
		        <c:if test="${tmTesting.ifFullResultSearch eq '0' || empty tmTesting.ifFullResultSearch}">
                <label style="width:150px;"><input type="checkbox" name="ifFullResultSearch" id="ifFullResultSearch"/>显示全部检测记录</label>
				</c:if>
		        <c:if test="${tmTesting.ifFullResultSearch eq '1'}">
                <label style="width:150px;"><input type="checkbox" name="ifFullResultSearch" id="ifFullResultSearch" checked/>显示全部检测记录</label>
				</c:if>
		    </li>
		    <li class="btns">
			    <input class="btn btn-primary" id="btnSubmit" onclick="return page();" type="submit" value="查询">
				<input class="btn btn-primary" id="btnClear" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/tm/tmTesting/list';">
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
		<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:1700px;">
	 </c:if>
	 <%-- <c:if test="${empty page.list}">
	 <div class="auto-scroll-x">
	 </c:if>
	<table id="contentTable" class="table table-striped table-bordered table-condensed"> --%>
	       <!-- style="width:1700px;" > -->
		<thead>
			<tr>
			    <th style="width:120px;"></th>
				<th style="width:200px;">型号</th>
				<th style="width:120px;">S/N</th>
				<th style="width:100px;">生产日期</th>
				<th style="width:100px;">当前状态</th>
				<th style="width:100px;">附加说明</th>
				<th style="width:300px;">详细说明</th>
				<th style="width:100px;">初期不良</th>
				<th style="width:100px;">类型</th>
				<th style="width:100px;">结果</th>
				<th style="width:100px;">检测日期</th>
				<th style="width:100px;">检测人</th>
				<th style="width:100px;">物料号</th>
				<th style="width:100px;">检测编号</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tmTesting">
			<tr>
			    <td>
                    <input class="btn btn-primary" type="button" value="新建" onclick="javascript:window.location.href='${ctx}/tm/tmTesting/form?materialNo=${tmTesting.materialNo}&snNo=${tmTesting.snNo}';">
			        <c:if test="${!empty tmTesting.id}">
			        <input class="btn btn-primary" type="button" value="编辑" onclick="javascript:window.location.href='${ctx}/tm/tmTesting/form?id=${tmTesting.id}';">
			        </c:if>
                </td>
			    <td>${tmTesting.model}</td>
			    <td>${tmTesting.snNo}</td>
			    <td><fmt:formatDate value="${tmTesting.productionDate}" pattern="yyyy-MM-dd"/></td>
			    <td>${fns:getDictLabel(tmTesting.status, 'DM0033', '')}</td>
			    <td>${fns:getDictLabel(tmTesting.additionalInstructions, 'DM0038', '')}</td>
			    <td><a title="${tmTesting.detailNewRemarks}">${tmTesting.detailNewRemarks}</a></td>
			    <td>${fns:getDictLabel(tmTesting.ifPrimeProblem, 'yes_no', '')}</td>
			    <td>${fns:getDictLabel(tmTesting.machineType, 'DM0032', '')}</td>
			    <td>${fns:getDictLabel(tmTesting.testingResult, 'DM0025', '')}</td>
			    <td><fmt:formatDate value="${tmTesting.testingDate}" pattern="yyyy-MM-dd"/></td>
			    <td>${tmTesting.testingPersonName}</td>
			    <td>${tmTesting.materialNo}</td>
			    <td>${tmTesting.testingNo}</td>
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