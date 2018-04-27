<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售业绩与目标查询-组</title>
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
			initPage();
			
			function initPage() {
				// 添加画面验证
				$("#searchForm").validate({
					submitHandler: function(form){
						//loading('正在提交，请稍等...');
						form.submit();
					},
					errorContainer: "#messageBox",
					errorPlacement: function(error, element) {
						$("#messageBox").text("输入有误，请先更正。");
						if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
							error.appendTo(element.parent().parent());
						} else {
							error.insertAfter(element);
						}
					},
					ignore:""
				});
			};
			
			// 导出一览
			$("#export").on("click", function() {
				$("#searchForm").attr("action","${ctx}/sd/stSalesTarget/compare/organizeList/export");
				$("#searchForm").submit();
				$("#searchForm").attr("action","${ctx}/sd/stSalesTarget/compare/organizeList");
			});

		});
		function cloneTableHeader_Width(){  
			if ($("#purTable>tbody>tr:not(.empty)").length > 0) {

		        var myTable_Width = (document.body.clientWidth);

		        FixTable("purTable", 2, myTable_Width, 400, "0");
			}
	     }
	</script>

</head>
<body>
	<h3 class="text-center page-title">销售业绩与目标查询-组</h3>
	<form:form id="searchForm" modelAttribute="stSalesTargetSearch" action="${ctx}/sd/stSalesTarget/compare/organizeList" method="get" class="breadcrumb form-search">

	    <ul class="ul-form">
			<li>
				<label><span class="help-inline"><font color="red">*</font></span>目标年度：</label>
				<form:input path="year" type="text" value="" readonly="true" style="width: 100px" class="input-mini required Wdate" onclick="WdatePicker({dateFmt:'yyyy'});"/>
			</li>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询">
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/sd/stSalesTarget/compare/organizeList';">
                <input id="export" class="btn btn-primary" type="button" value="一览导出">
			</li>
			<li class="clearfix">
			</li>
			<li></li>
	    </ul>
	</form:form>
	 <c:if test="${empty stSalesTargetSearch.stSalesTargetCompareList}">
	 <div class="auto-scroll-x">
		<table id="purTable" class="table table-striped table-bordered table-condensed">
	 </c:if>
	 <c:if test="${!empty stSalesTargetSearch.stSalesTargetCompareList}">
		<table id="purTable" class="table table-striped table-bordered table-condensed" style="width:2100px;">
	 </c:if>
	<%-- <c:if test="${empty stSalesTargetSearch.stSalesTargetCompareList}"> 
	<div style="overflow-x:scroll;">
	</c:if>
	<table id="purTable" class="table table-striped table-bordered table-condensed"> --%>
		<thead>
			<tr>
				<th>组别</th>
				<th width="40px"></th>
				<th>1月</th>
				<th>2月</th>
				<th>3月</th>
				<th>4月</th>
				<th>5月</th>
				<th>6月</th>
				<th>7月</th>
				<th>8月</th>
				<th>9月</th>
				<th>10月</th>
				<th>11月</th>
				<th>12月</th>
				<th>合计</th>
				<th>已发货未开票</th>
				<th>未发货未开票</th>
				<th>实际完成</th>
				<th>目标</th>
				<th>扣除部分</th>
				<th>实际完成率</th>
				<th>完成率</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${!empty stSalesTargetSearch.stSalesTargetCompareList}"> 
		            <c:forEach var="item" items="${stSalesTargetSearch.stSalesTargetCompareList}" varStatus="status">
					    <tr>
					    	<c:if test="${!empty item.organizeName}">
						    	<td>${item.organizeName}</td>
					    	</c:if>
					    	<c:if test="${empty item.organizeName}">
					    		<td>合计</td>
					    	</c:if>
						    <td><c:if test="${item.type eq '1'}">税前</c:if><c:if test="${item.type eq '2'}">税后</c:if></td>
						    <c:if test="${item.type eq '1'}">
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=1" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly1}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=2" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly2}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=3" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly3}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=4" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly4}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=5" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly5}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=6" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly6}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=7" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly7}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=8" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly8}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=9" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly9}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=10" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly10}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=11" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly11}" pattern="#,##0.00"/></a></td>
							    <td class="text-right"><a href="${ctx}/im/invoice/listPopu?organize=${item.organize}&year=${stSalesTargetSearch.year}&month=12" target="_blank" class="link"><fmt:formatNumber value="${item.preTotalMonthly12}" pattern="#,##0.00"/></a></td>
						    </c:if>
						    <c:if test="${item.type eq '2'}">
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly1}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly2}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly3}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly4}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly5}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly6}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly7}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly8}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly9}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly10}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly11}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalMonthly12}" pattern="#,##0.00"/></td>
						    </c:if>
						    <td class="text-right"><fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/></td>
						    <c:if test="${item.type eq '1'}">
							    <td class="text-right"><fmt:formatNumber value="${item.preTotalDelivered}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.preTotalUndelivered}" pattern="#,##0.00"/></td>
						    </c:if>
						    <c:if test="${item.type eq '2'}">
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalDelivered}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.afterTotalUndelivered}" pattern="#,##0.00"/></td>
						    </c:if>
						    <td class="text-right"><fmt:formatNumber value="${item.totalActualFinish}" pattern="#,##0.00"/></td>
						    <c:if test="${item.type eq '1'}">
							    <td class="text-right"><fmt:formatNumber value="${item.totalTarget}" pattern="#,##0.00"/></td>
							    <td class="text-right"><fmt:formatNumber value="${item.commission}" pattern="#,##0.00"/></td>
							    <td>${item.completionRateActual}</td>
							    <td>${item.completionRate}</td>
						    </c:if>
						    <c:if test="${item.type eq '2'}">
						    	<td></td>
						    	<td></td>
						    	<td></td>
						    	<td></td>
						    </c:if>
					    </tr>
		            </c:forEach> 
				</c:when>
				<c:otherwise>
					<tr class="empty"><td class="text-center" colspan="22">没有您需要的查询结果</td></tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<c:if test="${empty stSalesTargetSearch.stSalesTargetCompareList}"> 
	</div>
	</c:if>
	<sys:message content="${message}"/>
</body>
</html>