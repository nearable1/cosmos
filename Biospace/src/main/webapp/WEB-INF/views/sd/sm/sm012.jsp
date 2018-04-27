<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>库存查询</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
        	cloneTableHeader_Width();
        });
        function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/sm/storage/list");
            $("#searchForm").submit();
            return false;
        }
		function cloneTableHeader_Width(){  
			if ($("#purTable>tbody>tr:not(.empty)").length > 0) {

		        var myTable_Width = (document.body.clientWidth);  

		        var height = $("#purTable").height() + 23;

		        FixTable("purTable", 2, myTable_Width, height);
			}
	     }
    </script>
</head>
<body>
    <h3 class="text-center page-title">库存查询一览-库存数量一览</h3>
    <form:form id="searchForm" modelAttribute="searchForm" action="${ctx}/sm/storage/list" method="get" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

        <ul class="ul-form">
            <li>
                <label>产品名称：</label>
                <form:input path="materialName" class="input-medium" type="text" />
            </li>
            <li>
                <label>型号：</label>
                <form:input path="model" class="input-medium" type="text" />
            </li>
            <li class="clearfix"></li>
            <li>
                <label>物料号：</label>
                <form:input path="materialNo" class="input-medium" type="text" />
            </li>
            <li>
                <label>类型：</label>
                <form:select path="materialType" class="input-medium">
                    <option value=""></option>
                    <c:forEach items="${fns:getDictList('DM0058')}" var="sItem">
                        <c:if test="${sItem.value == '1' || sItem.value == '2' || sItem.value == '4' || sItem.value == '5' || sItem.value == '7'}">
                            <option value="${sItem.value}" ${sItem.value == searchForm.materialType ? 'selected' : ''}>${sItem.label}</option>
                        </c:if>
                    </c:forEach>
                </form:select>
            </li>
            <li style="margin-left: 15px;">
                <label class="checkbox">
                    <form:checkbox path="showLowStock" value="true" /> 仅显示库存不足配件
                </label>
            </li>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();">
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/sm/storage/list';">
                <input id="export" class="btn btn-primary" type="submit" value="一览导出" onclick="javascript:this.form.action='${ctx}/sm/storage/export';">
            </li>
        </ul>
    </form:form>
    <c:set var="wareHouseList" value="${fns:getDictList('DM0050')}"></c:set>
    <c:if test="${empty page.list}">
    <div style="overflow-x:scroll;">
    <table id="purTable" class="table table-striped table-bordered table-condensed">
    </c:if>
    <c:if test="${!empty page.list}">
    <table id="purTable" class="table table-striped table-bordered table-condensed" style="width:1700px;">
    </c:if>
        <thead>
            <tr>
                <th width="35px">类型</th>
                <th>物料号</th>
                <th>产品名称</th>
                <th>型号</th>
                <th>总数量</th>
                <th>在库<br>数量</th>
            <c:forEach items="${wareHouseList}" var="sItem">
                <th>${sItem.label}</th>
            </c:forEach>
                <th>已检<br>数量</th>
                <th>可用<br>数量</th>
                <th>报价单<br>占用</th>
                <th>已开票<br>未发货</th>
                <th>不在库<br>数量</th>
                <th>借出<br>数量</th>
                <th>已发货<br>未开票</th>
                <th>安全<br>库存</th>
                <th>未到货<br>数量</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${!empty page.list}"> 
                <c:forEach var="item" items="${page.list}" varStatus="status">
                    <tr>
                        <td>${fns:getDictLabel(item.materialType, 'DM0058', '')}</td>
                        <td>${item.materialNo}</td>
                        <td>${item.materialName}</td>
                        <td>${item.model}</td>
                        <td class="text-right">${item.totalNum}</td>
                        <td class="text-right">${item.inStockNum}</td>
                    <c:forEach items="${wareHouseList}" var="whItem">
                        <c:set var="pName" value="wh_${whItem.value}"></c:set>
                        <td class="text-right">${item.whMap[pName]}</td>
                    </c:forEach>
                        <td class="text-right">${item.testedNum}</td>
                        <td class="text-right">${item.availableNum}</td>
                        <td class="text-right">${item.quotaOccupyNum}</td>
                        <td class="text-right">${item.invoicedUndeliveredNum}</td>
                        <td class="text-right">${item.outStockNum}</td>
                        <td class="text-right">${item.lendingNum}</td>
                        <td class="text-right">${item.unInvoicedDeliveredNum}</td>
                        <td class="text-right">${item.safetyStockNum}</td>
                        <td class="text-right">${item.notArrivedNum}</td>
                    </tr>
                </c:forEach> 
            </c:if>
            <c:if test="${empty page.list}">
                <tr class="empty"><td class="text-center" colspan="${fn:length(wareHouseList) + 15}">没有您需要的查询结果</td></tr>
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