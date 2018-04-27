<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商机管理</title>
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
				$("#searchForm").attr("action","${ctx}/sd/boBusinessOpp/export");
				$("#searchForm").submit();
				$("#searchForm").attr("action","${ctx}/sd/boBusinessOpp/list");
			});
			
			$("#btnTurnSalesPlan").click(function(){
				var selectedCount = 0;
				$("input:checkbox[name='selectedBobusinessList']:checked").each(function(){
					selectedCount++;
			    });
				if(selectedCount == 0){
					alertx("请至少选择一条数据。","");
					return false;
				}
				$("#isSalesPlan").val("1");
				$("#searchForm").attr("action","${ctx}/sd/boBusinessOpp/setIfSalePlan");
				$("#searchForm").submit();
				$("#searchForm").attr("action","${ctx}/sd/boBusinessOpp/list");
			});
			$("#btnBackSalesPlan").click(function(){
				var selectedCount = 0;
				$("input:checkbox[name='selectedBobusinessList']:checked").each(function(){
					selectedCount++;
			    });
				if(selectedCount == 0){
					alertx("请至少选择一条数据。","");
					return false;
				}
				$("#isSalesPlan").val("0");
				$("#searchForm").attr("action","${ctx}/sd/boBusinessOpp/setIfSalePlan");
				$("#searchForm").submit();
				$("#searchForm").attr("action","${ctx}/sd/boBusinessOpp/list");
			});
		});
		function cloneTableHeader_Width(){  
			if ($("#contentTable>tbody>tr:not(.empty)").length > 0) {

		        /* //document.body.clientWidth获得客户区域(浏览器窗口,不包括菜单栏和状态栏,就是内容窗口)的宽度 - 35像素的滚动条宽度。  
		        var myTable_Width = (document.body.clientWidth-35);  
		          
		        //alert(myTable_Width);//测试屏幕宽度  
		        if((document.body.clientWidth-35) > 1100){  
		            myTable_Width=1100; //宽度  
		        } */
		        var myTable_Width = (document.body.clientWidth - 55);  
		        var height = $("#contentTable").height() + 23;
		        //调用 锁定表头和 列 的JS函数  
		        FixTable("contentTable", 3, myTable_Width, height);
			}
         }
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function checkBusiness(id){
			$.ajax({
		    	url: "${ctx}" + "/sd/boBusinessOpp/turnTenderAuthor",
		        type: "get",
		        async: false,
		        data: {"id":id},
		        dataType: "json",
		        success: function (data) {
		        	if(data.error != null && data.error == "true"){
		        		showError(data.errorMessage);
		        	}else{
		        		window.location.href="${ctx}/sd/taTenderAuthor/form?businessOppId="+id; 
		        	}
		        },
		        error: function (msg) {
		        }
		    });
		}
	</script>
	<style>
        .form-search .ul-form li label{
            width:120px;
        }
        .form-search .ul-form li{
            width:280px;
        }
	/* table {
	    table-layout:fixed;
	}
	table td{
	    overflow:hidden;
        white-space:nowrap;
        text-overflow:ellipsis;
	} */
    </style>
</head>
<body>
    <h3 class="text-center page-title">商机一览</h3>
	<form:form id="searchForm" modelAttribute="boBusinessOpp" action="${ctx}/sd/boBusinessOpp/list" method="get" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="isSalesPlan" name="isSalesPlan" type="hidden" value="${boBusinessOpp.isSalesPlan}"/>
		<ul class="ul-form" >
		    <li style="width:560px;">
		        <label>最终客户：</label>
				<input name="endCustomerId" type="text" value="${boBusinessOpp.endCustomerId}" 
				   maxlength="50" style="width:350px;" data-type="1" 
			       data-show="text" class="remote customer required customerId input-medium"/>
		    </li>
		    <li style="width:560px;">
		        <label>代理商/经销商：</label>
				<input name="agentId" type="text" value="${boBusinessOpp.agentId}" 
				    maxlength="50" style="width:350px;" data-type="2,3"
			        data-show="text" class="remote customer required customerId input-medium"/>
		    </li>
		    <li>
			    <label>是否转为销售计划：</label>
			    <form:select path="ifSalesPlan" style="width:130px;"  class="input-medium">
					<form:option label="" value=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
		    </li>
		    <li>
		        <label>是否生成招标授权：</label>
			    <form:select path="ifTenderAuthor" style="width:130px;"  class="input-medium">
					<form:option label="" value=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
		    </li>
		    <li>
		        <label>是否生成合同：</label>
			    <form:select path="ifContractGeneration" style="width:130px;"  class="input-medium">
					<form:option label="" value=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
		    </li>
		    <li>
		        <label>商机失败：</label>
			    <form:select path="ifFail" style="width:130px;"  class="input-medium">
					<form:option label="" value=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
		    </li>
		    <li>
		        <label>预计成交月：</label>
			    <input name="expTurnoverMonth" id="expTurnoverMonth" class="input-medium Wdate " 
			        onclick="WdatePicker({dateFmt:'yyyy-MM'});" type="text" 
			        maxlength="20" readonly="readonly" value='<fmt:formatDate value="${boBusinessOpp.expTurnoverMonth}" pattern="yyyy-MM"/>' style="width:130px;"/>
		    </li>
		    <li>
		        <label>销售员：</label>
		        <form:input path="responsiblePersonId" class="remote employee" style="width:130px;" type="text" data-show="text" data-type="10,20"/>
				<%-- <form:select path="responsiblePersonId" class="input-medium" style="width:130px;" >
					<form:option value="" label="" />
					<form:options items="${fns:getSqlDictList('dict_employee')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select> --%>
		    </li>
		    <li>
		        <label>组：</label>
			    <form:select path="organize" style="width:130px;"  class="input-medium">
				    <form:option value="" label="" />
				    <form:options items="${fns:getSqlDictList('dict_organize')}" itemLabel="label" itemValue="value" htmlEscape="false" />
	            </form:select>
			</li>
			<li class="btns">
			    <input class="btn btn-primary" id="btnSubmit" onclick="return page();" type="submit" value="查询">
				<input class="btn btn-primary" id="btnClear" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/sd/boBusinessOpp/list';">
				<input class="btn btn-primary" id="btnExport" type="button" value="一览导出">
		    </li>
		</ul>
	<sys:message content="${message}"/>
	<div class="group-box">
	<div id="btnContent">
	    <input class="btn btn-primary" id="btnTurnSalesPlan" type="button" value="转为销售计划">
	    <!-- <input class="btn btn-primary" id="btnBackSalesPlan" type="button" value="从销售计划撤回">
	    <input class="btn btn-primary" id="btnTurnContract" type="button" value="转为合同">
	    <input class="btn btn-primary" id="btnCreateTender" type="button" value="生成招标授权"> -->
	</div>
	<!-- <table id="contentTable" style="width:2100px;"  -->
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
	<table id="contentTable"
	       class="table table-striped table-bordered table-condensed"> --%>
		<thead>
			<tr>
				<th style="width:30px;"></th>
				<th style="width:100px;">编号</th>
				<th style="width:30px;">行</th>
				<th style="width:200px;">代理商/经销商</th>
				<th style="width:200px;">最终客户</th>
				<th style="width:100px;">科室/系</th>
				<th style="width:100px;">型号</th>
				<th style="width:50px;">数量</th>
				<th style="width:100px;">金额</th>
				<th style="width:100px;">成交预定月</th>
				<th style="width:100px;">预计成交率</th>
				<th style="width:100px;">商机失败</th>
				<th style="width:200px;">商机备注</th>
				<th style="width:100px;">组</th>
				<th style="width:100px;">销售员</th>
				<th style="width:100px;">已转为销售计划</th>
				<th style="width:100px;">合同状态</th>
				<th style="width:100px;">招标授权</th>
				<th style="width:100px;">招标授权操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="boBusinessOpp">
			<tr>
			    <%-- <td><form:checkbox path="selectedBobusinessList" value="${boBusinessOpp.businessOppNo}"/></td> --%>
			    <td><input type='checkbox' name="selectedBobusinessList" value="${boBusinessOpp.businessOppNo}"/></td>
				<td><a class="link" href="${ctx}/sd/boBusinessOpp/form?id=${boBusinessOpp.id}" >${boBusinessOpp.businessOppNo}
				</a></td>
				<td>${boBusinessOpp.lineNo}</td>
				<td title="${boBusinessOpp.agentName}"><div class="ellipsis" style="max-width:200px;">${boBusinessOpp.agentName}</div></td>
				<td title="${boBusinessOpp.endCustomerName}"><div class="ellipsis" style="max-width:200px;">${boBusinessOpp.endCustomerName}</div></td>
				<td>${fns:getDictLabel(boBusinessOpp.customerSegmentation, 'DM0039', '')}</td>
				<td title="${boBusinessOpp.model}"><div class="ellipsis" style="max-width:100px;">${boBusinessOpp.model}</div></td>
				<td>${boBusinessOpp.num}</td>
				<td class="text-right "><fmt:formatNumber value="${boBusinessOpp.totalAmount}" pattern="#,##0.00"/></td>
				<td><fmt:formatDate value="${boBusinessOpp.expTurnoverMonth}" pattern="yyyy-MM"/></td>
				<td>${fns:getDictLabel(boBusinessOpp.expTurnover, 'DM0040', '')}</td>
				<td>${fns:getDictLabel(boBusinessOpp.ifFail, 'yes_no', '')}</td>
				<td><a title="${boBusinessOpp.newRemarks}">${boBusinessOpp.newRemarks}</a></td>
				<td>${boBusinessOpp.organizeName}</td>
				<td>${boBusinessOpp.responsiblePersonName}</td>
				<td>${fns:getDictLabel(boBusinessOpp.ifSalesPlan, 'yes_no', '')}</td>
				<td>
					<c:if test="${boBusinessOpp.ifContractGeneration == '0'}">
						<input class="btn btn-primary" id="btnTurnContract" type="button" value="转为合同" onclick="javascript:window.location.href='${ctx}/sd/soOrder/form?businessOppNo=${boBusinessOpp.businessOppNo}';">
					</c:if>
					<c:if test="${boBusinessOpp.ifContractGeneration == '1'}">
						${fns:getDictLabel(boBusinessOpp.ifContractGeneration, 'yes_no', '')}
					</c:if>
				</td>
				<td>${fns:getDictLabel(boBusinessOpp.ifTenderAuthor, 'yes_no', '')}
				</td>
				<td><input class="btn btn-primary" id="btnTurnContract" type="button" value="生成招标授权" onclick="checkBusiness('${boBusinessOpp.id}');">
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	 <c:if test="${empty page.list}">
	 </div>
	 </c:if>
	</div>
	</form:form>
	<div class="pagination">${page}</div>
</body>
</html>