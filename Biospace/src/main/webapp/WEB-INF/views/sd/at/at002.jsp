<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商目标与业绩对比查询</title>
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
		$("input[type=checkbox]").change(function(){
			if ($(this).is(':checked')) {
				$("#ifNew").val("1");
			} else {
				$("#ifNew").val("0");
			}
		});
		
		// 直接导出
		$("#export").on("click", function() {
/* 			var fHtml = '<form method="get" action="' + '${ctx}' + '/sd/atAgentTarget/export">';
			    fHtml += '</form>';
			$(fHtml).appendTo('body').submit().remove(); */
			$("#searchForm").attr("action","${ctx}/sd/atAgentTarget/compare/export");
			$("#searchForm").submit();
			$("#searchForm").attr("action","${ctx}/sd/atAgentTarget/compare/list");
		});
	});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);

			$("#searchForm").submit();
	    	return false;
	    }
	</script>

</head>
<body>
	<h3 class="text-center page-title">代理商目标与业绩对比查询</h3>
	<form:form id="searchForm" modelAttribute="atAgentTargetSearch" action="${ctx}/sd/atAgentTarget/compare/list" method="get" class="breadcrumb form-search">
		
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

	    <ul class="ul-form">
			<li>
				<label>代理商：</label>
				<form:input path="customerId" type="text" class="remote customer input-xxlarge" data-type="2" data-show="text"/>
			</li>
			<li>
				<label style="width:120px">
					<c:if test="${atAgentTargetSearch.ifNew == '1'}">
						<form:input path="ifNew" type="hidden" value="1"/>
						<input type="checkbox" class="inline" checked="checked"/>
					</c:if>
					<c:if test="${atAgentTargetSearch.ifNew == '0' || empty atAgentTargetSearch.ifNew}">
						<form:input path="ifNew" type="hidden" value="0"/>
						<input type="checkbox" class="inline"/>
					</c:if>
					仅显示最新协议
				</label>
			</li>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();">
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/sd/atAgentTarget/compare/list';">
                <input id="export" class="btn btn-primary" type="button" value="一览导出">
			</li>
		</ul>
	</form:form>
	<table id="atAgentTargetDtlTable" class="table table-striped table-bordered table-condensed" style="overflow: auto; width: 100%;">
		<thead>
			<tr>
				<th>代理商</th>
				<th>协议开始期间</th>
				<th>目标类型</th>
				<th>阶段</th>
				<th>型号（目标/业绩）</th>
				<th>台数（目标/业绩）</th>
				<th>金额目标</th>
				<th>金额业绩</th>
				<th>完成率</th>
			</tr>
		</thead>
		<tbody>
	        <c:if test="${!empty page.list}"> 
	            <c:forEach var="item" items="${page.list}" varStatus="status">
				    <tr>
 				        <td rowspan="${item.listSize + 1}">${item.customerName}</td>
				        <td rowspan="${item.listSize + 1}"><fmt:formatDate value="${item.validityDateFrom}" pattern="yyyy-MM"/></td>
				        <td rowspan="${item.listSize + 1}">${fns:getDictLabel(item.targetType, 'DM0018', '')}</td>
				        <c:if test="${!empty item.periodList}">
						    <c:forEach var="periodList" items="${item.periodList}">
						    	<tr>
						    		<td rowspan="${fn:length(periodList.value)}">${fns:getDictLabel(periodList.key, 'DM0046', '')}</td>
						    		<c:forEach var="atAgentTargetList" items="${periodList.value}" begin="0" end="0">
										<td>${atAgentTargetList.model}/${atAgentTargetList.trackModel}</td>
										<td>
											<c:if test="${item.targetType == '2'}">
												${atAgentTargetList.num}
											</c:if>
											/${atAgentTargetList.trackNum}
										</td>
									</c:forEach>
									<td rowspan="${fn:length(periodList.value)}">
										<c:if test="${item.targetType == '1'}">
											<fmt:formatNumber value="${item.periodTotalAmount[periodList.key]}" pattern="#,##0.00"/>
										</c:if>
									</td>
									<c:forEach var="atAgentTargetList" items="${periodList.value}" begin="0" end="0">
										<td><fmt:formatNumber value="${atAgentTargetList.trackAmount}" pattern="#,##0.00"/></td>
									</c:forEach>
									<c:if test="${item.targetType == '1'}">
										<td rowspan="${fn:length(periodList.value)}">${fns:division(item.periodTrackTotalAmount[periodList.key], item.periodTotalAmount[periodList.key])}</td>
									</c:if>
									<c:if test="${item.targetType == '2'}">
										<c:forEach var="atAgentTargetList" items="${periodList.value}" begin="0" end="0">
											<td>${fns:division(atAgentTargetList.trackNum, atAgentTargetList.num)}</td>
										</c:forEach>
									</c:if>
						    	</tr>
						    	<c:if test="${fn:length(periodList.value) > 1}"> 
							    	<c:forEach var="atAgentTargetList" items="${periodList.value}" begin="1">
								    	<tr>
											<td>${atAgentTargetList.model}/${atAgentTargetList.trackModel}</td>
											<td>
												<c:if test="${item.targetType == '2'}">
													${atAgentTargetList.num}
												</c:if>
												/${atAgentTargetList.trackNum}
											</td>
											<td><fmt:formatNumber value="${atAgentTargetList.trackAmount}" pattern="#,##0.00"/></td>
											<c:if test="${item.targetType == '2'}">
												<td>${fns:division(atAgentTargetList.trackNum, atAgentTargetList.num)}</td>
											</c:if>
								     	</tr>
							    	</c:forEach>
						    	</c:if>
						    </c:forEach>
				        </c:if>
				        <c:if test="${empty item.periodList}"> 
				        	<td></td>
				        	<td></td>
				        	<td></td>
				        	<td></td>
				        	<td></td>
				        	<td></td>
				        </c:if>
				    </tr>
	            </c:forEach> 
	        </c:if>
	        <c:if test="${empty page.list}">
			    <tr><td class="text-center" colspan="9">没有您需要的查询结果</td></tr>
	        </c:if>
		</tbody>
	</table>
    <div class="pagination">${page}</div>
	<sys:message content="${message}"/>
</body>
</html>