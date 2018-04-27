<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
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
        $(function(){
        	$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出拜访信息吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/vr/visitingRecordManager/export");
						$("#searchForm").submit();
						$("#searchForm").attr("action","${ctx}/vr/visitingRecordManager/list");
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
        	$("#btnSubmit").click(function(){
				$("#searchForm").attr("action","${ctx}/vr/visitingRecordManager/list");
				$("#searchForm").submit();
			});
        	//$('#contentDiv').hide();
	        $("#addRowInfo").on("click",function(){
	        	var rowNo = $('.rowNo').length+1;
	        	var html = '<tr>'
        		html += '<td><a style="cursor:pointer;" onclick = "deleteRow(this)">-</a></td><td><span class="rowNo">1</span></td>';
        		html += '<td><select name="ifVisit" style="width: 150px;">';
        		html += '<option value="0">是</option><option value="1">否</option>';
        		html += '</select></td>';
        		html += '<td><input name="address" type="text" style="width: 120px;"/></td>';
        		html += '<td><select name="customer" style="width: 150px;">';
        		html += '<option>11</option><option>12</option>';
        		html += '</select></td>';
        		html += '<td><select name="businessOpp" style="width: 150px;">';
        		html += '<option>13</option><option>14</option>';
        		html += '</select></td>';
        		html += '<td><select name="purpose" style="width: 150px;">';
        		html += '<option value="1">日常拜访</option><option value="2">其他</option>';
        		html += '</select></td>';
        		html += '<td>';
        		html += $('#dateTemp').html();
        		html += '</td>';
        		html += '</tr>';
	        	
        		$('#tableHead>tbody').append(html);
        		
        		$(".rowNo").each(function(index){ 
        			$(this).html(index+1); 
       			});
	        	
	        });
	        
	        $("#btnClear").on("click",function(){
	        	$("#searchForm :input").val("");
	        	//$("#searchForm :select").select2().val(" ").trigger("change");
	        	//$('#searchForm').resetForm();
	        });
	      
        })
		 function deleteRow(v){
       		$(v).parent().parent().remove();//删除this的父节点div
       		$(".rowNo").each(function(index){ 
    			$(this).html(index+1); 
   			});
       	}	
	    
	    function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	    
	</script>
</head>
<body>
	<h3 class="text-center page-title">拜访管理</h3>
	<sys:message content="${message}"/>
	
	<form:form id="searchForm" modelAttribute="vrVisitDtl" action="${ctx}/vr/visitingRecordManager/list" method="get" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		
		<ul class="ul-form">
			<li><label style="display: inline-block;width: 80px;">申请人：</label>
				<form:input path="responsiblePersonId" class="remote employee input-medium required" type="text" data-show="text" data-type="10,20"/>
				<%-- <form:select path="responsiblePersonId" class="input-medium">
					<form:option value="" label=""/>
					<c:forEach items="${fns:getUserList()}" var="user">
						<form:option value="${user.id }">${user.name} </form:option>
					</c:forEach>
				</form:select> --%>
			</li>
			<li><label style="display: inline-block;width: 80px;">拜访对象：</label>
				<input name="customerName" type="text"
				   maxlength="50" style="width:165px;" value="${vrVisitDtl.customerName }"
			       data-show="text" class="remote customer required customerId input-medium"/>
			</li>
			<li>
			<label>拜访时间：</label>
			<form:input id="beginDate" path="expDateFrom"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				　--　
			<form:input id="endDate" path="expDateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			</li>
			<li><label style="display: inline-block;width: 80px;">申请状态：</label>
				<form:select  path="workflowStatus" class="input-medium">
					<form:option value="" label=""/>
				    <form:options items="${fns:getDictList('DM0015')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label style="display: inline-block;width: 80px;">报告状态：</label>
				<form:select path="workflowStatus2" class="input-medium">
					<form:option value="" label=""/>
				    <form:options items="${fns:getDictList('DM0015')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form">
		<li class="btns">
			<span style="display: inline-block;width: 750px;">
				<shiro:hasPermission name="vr:visitingRecord:view">
				<a class="btn btn-primary" href="${ctx}/vr/visitingRecord/list" >新建拜访申请</a>
				</shiro:hasPermission>
			</span>
			<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="return page();"/>
		    <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/vr/visitingRecordManager/list';">
			<input id="btnExport" class="btn btn-primary" type="button" value="查询一览导出"/>
		</li>
		</ul>
	</form:form>
	<div id="contentDiv">
	<div id="dateTemp" style="display: none;">
		<input name="expDateForm" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:140px;"
		value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
	</div>
	<table id="contentTable" style="width:100%;" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="50px">No</th>
				<th width="80px">地点区分</th>
				<th width="150px">拜访对象</th>
				<th width="120px">计划拜访时间</th>
				<th width="50px">已拜访</th>
				<th width="120px">实际拜访时间</th>
				<th width="100px">申请人</th>
				<th width="100px">申请状态</th>
				<th width="100px">报告状态</th>
				<th>操作</th>
				<%-- <shiro:hasPermission name="sys:order:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rowModel">
			<tr>
				<td><%-- <a href="${ctx}/vr/visitingRecordManager/form?procInsId=${rowModel.procInsId}"> --%>${rowModel.visitNo}</td>
				<td>
					<c:forEach items="${fns:getDictList('DM0041')}" var="addressList">
						<c:if test="${addressList.value == rowModel.ifLocal}">${addressList.label}</c:if>
					</c:forEach>
				</td>
				<td title="${rowModel.customerChName}">
				<div class="ellipsis" style="max-width:250px;">${rowModel.customerChName}</div>
				</td>  
				<td>${rowModel.expDateFrom}</td>
				<td>
					<c:forEach items="${fns:getDictList('yes_no')}" var="dictList">
						<c:if test="${dictList.value == rowModel.ifVisit}">${dictList.label}</c:if>
					</c:forEach>
				</td>
				<%--<td>
				 <c:forEach items="${fns:getDictList('DM0042')}" var="dictList">
						<c:if test="${dictList.value == rowModel.purpose}">${dictList.label}</c:if>
					</c:forEach>
				</td> --%>
				<%-- <td><fmt:formatDate value="${rowModel.expDateFrom}" pattern="yyyy-MM-dd"/></td> --%>
				<td>${rowModel.actDateFrom}</td>
				<td>${(fns:getUserById(rowModel.createBy)).name}</td>
				<td>
					<c:choose>
						<c:when test="${rowModel.workflowStatus == 50 }">
							已审核
						</c:when>
						<c:otherwise>
							申请中
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${empty rowModel.workflowStatus2}">
							-
						</c:when>
						<c:otherwise>
							<c:if test="${rowModel.workflowStatus2 == 50 }">
							已审核
							</c:if>
							<c:if test="${rowModel.workflowStatus2 != 50 }">
							申请中
							</c:if>
						</c:otherwise>
					</c:choose>
				</td>				
				<td>
					<c:if test="${empty rowModel.workflowStatus2&&rowModel.workflowStatus != 50}">
						<shiro:hasPermission name="vr:visitingRecord:view">
						<a class="btn btn-primary" href="${ctx}/vr/visitingRecord/list?id=${rowModel.headRowId }">申请查看</a>
						</shiro:hasPermission>
					</c:if>
					<c:if test="${rowModel.workflowStatus == 50 && empty rowModel.workflowStatus2}">	
						<c:choose>
						<c:when test="${rowModel.createBy == user.id}">
							<a class="btn btn-primary" href="${ctx}/vr/visitingRecordManager/reportForm?id=${rowModel.headRowId }&opFlag=1">报告申请</a>
						</c:when>
						<c:otherwise>
							<a class="btn btn-primary" href="${ctx}/vr/visitingRecord/list?id=${rowModel.headRowId }">申请查看</a>
						</c:otherwise>
						</c:choose>
				    </c:if>
				    <c:if test="${rowModel.workflowStatus == 50 && not empty rowModel.workflowStatus2}">
						<shiro:hasPermission name="vr:visitingRecordManager:view">
						<a class="btn btn-primary" href="${ctx}/vr/visitingRecordManager/reportForm?id=${rowModel.headRowId }">报告查看</a>
						</shiro:hasPermission>
				    </c:if>
					<%--  <c:if test="${isManagerEdit}">
						<a class="btn btn-primary" href="${ctx}/vr/visitingRecordManager/reportForm?act.procInsId=${rowModel.procInsId}">评价</a>
					</c:if> --%>
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>