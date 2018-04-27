<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
	<title>客户/代理商/经销商查询/一览导出</title>
	<meta name="decorator" content="default"/>
<script type="text/javascript">
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	$(function(){
		cloneTableHeader_Width();
		
		$("#ExportSubmit").click(function(){
				$("#searchForm").attr("action","${ctx}/cm/cm001/export");
				$("#searchForm").submit();
				$("#searchForm").attr("action","${ctx}/cm/cm001/list");
		});

		$("input.ifEffective").change(function(){
			if ($(this).is(':checked')) {
				$("#ifEffective").val("1");
			} else {
				$("#ifEffective").val("0");
			}
		});
	});
	
	function cloneTableHeader_Width(){  
		if ($("#contentTable>tbody>tr:not(.empty)").length > 0) {

	        /* //document.body.clientWidth获得客户区域(浏览器窗口,不包括菜单栏和状态栏,就是内容窗口)的宽度 - 35像素的滚动条宽度。  
	        var myTable_Width = (document.body.clientWidth-35);  
	          
	        //alert(myTable_Width);//测试屏幕宽度  
	        if((document.body.clientWidth-35) > 1150){  
	            myTable_Width=1150; //宽度  
	        } */
	        var myTable_Width = (document.body.clientWidth);  
	        var height = $("#contentTable").height() + 23;
	        //调用 锁定表头和 列 的JS函数  
	        FixTable("contentTable", 3, myTable_Width, height);
		}
     }
	
</script>
</head>
<body>
	<h3 align="center">客户代理商经销查询</h3>
	<form:form id="searchForm" modelAttribute="customerManagement" action="${ctx}/cm/cm001/list" method="get" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form" style="margin-left:-30px;">
			<li><label>类型：</label>
				<form:select path="customerType" class="input-medium" style="width:150px;" >
					<option value=""> </option>
	            	<form:options items ="${fns:getDictList('DM0001')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>名称：</label>
				<form:input path="customerChName" style="width:150px;" class="input-medium" type="text" value="" maxlength="50" />
			</li>
			<li><label>发展日期：</label>
			    <input name="developDateFrom" style="width:150px;" class="input-medium Wdate" 
			       				onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text" maxlength="20"
			       				value="<fmt:formatDate value="${customerManagement.developDateFrom}" pattern="yyyy-MM-dd"/>" />
				~ 
				<input name="developDateTo" style="width:150px;" class="input-medium Wdate" 
			       				onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text" maxlength="20"
			       				value="<fmt:formatDate value="${customerManagement.developDateTo}" pattern="yyyy-MM-dd"/>" />
			</li>
			<li><label>行业：</label>
				<form:select path="industry" class="input-medium" style="width:150px;">
					<option value=""> </option>
	            	<form:options items ="${fns:getDictList('DM0002')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label >销售负责人：</label>
				<form:input path="responsiblePersonId" class="remote employee input-medium" type="text" data-show="text" data-type="10" style="width:150px;"/>
				<%-- <form:select path="responsiblePersonId" class="input-medium" style="width:150px;">
					<form:option value="" label="" />
					<form:options items="${fns:getSqlDictList('dict_employee')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select> --%>
			</li>
			<li><label>组：</label>
				<form:select path="officeId" style="width:150px;"  class="input-medium">
				    <form:option value="" label="" />
				    <form:options items="${fns:getSqlDictList('dict_organize')}" itemLabel="label" itemValue="value" htmlEscape="false" />
	            </form:select>
			</li>
			<li><label>地区：</label>
				<form:select path="region" class="input-medium" style="width:150px;">
					<option value=""> </option>
            	<form:options items ="${fns:getDictList('DM0049')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			</li>
			<li>
				<label style="width:150px;">
				<c:if test="${customerManagement.ifEffective == '1' || empty customerManagement.ifEffective}">
					<form:input path="ifEffective" type="hidden" value="1"/>
					<input type="checkbox" class="inline ifEffective" checked="checked"/>
				</c:if>
				<c:if test="${customerManagement.ifEffective == '0'}">
					<form:input path="ifEffective" type="hidden" value="0"/>
					<input type="checkbox" class="inline ifEffective"/>
				</c:if>
				<%-- <form:checkbox path="ifEffective" checked="checked" value="1" /> --%>仅显示有效</label>
			</li>
			<li>
				<label style="width:150px;">
				<form:checkbox path="ifImportant" value="1" />仅显示重点对象</label>
			</li>
			<li class="btns">
                <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="return page();" value="查询"/>
                <input id="empSubmit" class="btn btn-primary" type="button"  value="清空"  onclick="javascript:window.location.href='${ctx}/cm/cm001//list';"/>
		    	<input id="ExportSubmit" class="btn btn-primary" type="button" value="一览导出"/>
			</li>
			<shiro:hasPermission name="bps:cm001:editManager">
				<li class="btns" style="margin-left:55px;">
					<span style="display: inline-block;">
						<a class="btn btn-primary" href="${ctx}/cm/cm001/newAndEdit" >新建记录</a>
					</span>
				</li>
			</shiro:hasPermission>
			
			
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
	       <!-- style="width:1700px;"> -->
		<thead>
			<tr>
				<th>编号</th>
				<th>类型</th>
				<th>名称</th>
				<th>发展日期</th>
				<th>行业</th>
				<th>销售负责人</th>
				<th>重点对象</th>
				<th>省市</th>
				<th>城市</th>
				<th>地区</th>
				<th>总公司名称</th>
				<th>最后一次拜访时间</th>
				<th>协议开始日期</th>
				<th>协议终止日期</th>
				<th>是否有效</th>
			</tr>
		</thead>
		<tbody>
		<c:if test="${!empty page.list}"> 
		<c:forEach items="${page.list}" var="list">
			<tr>
				<td><a class="link"  href="${ctx}/cm/cm001/newAndEdit?customerId=${list.customerId}">${list.customerId}</a>
					</td>
				<td>
					${list.customerType}
				</td>
				<td>
					${list.customerChName}
				</td>
				<td>
				    <fmt:formatDate value="${list.developDate}" pattern="yyyy-MM-dd"/>
					<%--  ${list.developDateStr} --%>
				</td>
				<td>
					${fns:getDictLabel(list.industry, 'DM0002', '')}
				</td>
				<td>
					${list.responsiblePersonId}
				</td>
				<td>
					${fns:getDictLabel(list.ifImportant, 'yes_no', '')}
				</td>
				<td>
					${list.province }
				</td>
				<td>
					${list.city}
				</td>
				<td>
					${fns:getDictLabel(list.region, 'DM0049', '')}
				</td>
				<td>
					${list.customerParentCo}
				</td>
				<td>
					<fmt:formatDate value="${list.lastVisitDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					 ${list.validityDateFrom}
				</td>
				<td>
					 ${list.validityDateTo}
				</td>
				<td>
					${fns:getDictLabel(list.ifEffective, 'yes_no', '')}
				</td>
			</tr>
		</c:forEach>
		</c:if>
            <c:if test="${empty page.list}">
                <tr class="empty"><td class="text-center" colspan="15">没有您需要的查询结果</td></tr>
            </c:if>
		</tbody>
	</table>
	 <c:if test="${empty page.list}">
	 </div>
	 </c:if>
<div class="pagination">${page}</div>

</body>
</html>