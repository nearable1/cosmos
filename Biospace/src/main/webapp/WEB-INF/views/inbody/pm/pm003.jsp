<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>采购订单查询</title>
    <meta name="decorator" content="default"/>
    
    <style type="text/css">
/*      table { */
/*          table-layout: fixed; */
/*      } */
     div.ellipsis {
         text-overflow: ellipsis; 
         -moz-text-overflow: ellipsis; /* for Firefox,mozilla */   
         overflow: hidden;
         white-space: nowrap;
     }
    </style>
    
    <script type="text/javascript">
        $(document).ready(function() {
        });
        function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/pm/purchase/list");
            $("#searchForm").submit();
            return false;
        }
    </script>

</head>
<body>
    <h3 class="text-center page-title">采购订单查询</h3>
    <form:form id="searchForm" modelAttribute="searchForm" action="${ctx}/pm/purchase/list" method="get" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

        <ul class="ul-form">
            <li>
                <label>采购订单号：</label>
                <form:input path="purchaseNo" class="input-medium" type="text" value="" />
            </li>
            <li>
                <label>供应商：</label>
                <form:select path="supplierId" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getSqlDictList('dict_supplier')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>物料名称：</label>
                <form:input path="materialName" class="input-medium" type="text" value="" maxlength="50" />
            </li>
            <li>
                <label>采购类型：</label>
                <form:select path="purchaseType" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0021')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>支付状态：</label>
                <form:select path="paymentStatus" class="input-medium" >
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0022')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>到货状态：</label>
                <form:select path="arrivalStatus" class="input-medium" >
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0023')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();">
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/pm/purchase/list';">
                <input id="export" class="btn btn-primary" type="submit" value="一览导出" onclick="javascript:this.form.action='${ctx}/pm/purchase/list/export';">
            </li>
        </ul>
    </form:form>
    <div class="auto-scroll-x">
        <table id="purTable" class="table table-striped table-bordered table-condensed">
            <thead>
                <tr>
                    <th width="80px">订单号</th>
                    <th width="35px">采购<br>类型</th>
                    <th width="35px">FOC</th>
                    <th>供应商</th>
                    <th>订单状态</th>
                    <th width="60px">支付状态</th>
                    <th>到货状态</th>
                    <th>物料号</th>
                    <th width="100px">物料名称</th>
                    <th>单价</th>
                    <th width="35px">数量</th>
                    <th width="35px">未付<br>数量</th>
                    <th>未付金额</th>
                    <th width="35px">到货<br>数量</th>
                    <th width="40px">未到货<br>数量</th>
                    <th width="100px">发票号码</th>
                    <th width="80px">AX编号</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${!empty page.list}"> 
                    <c:forEach var="item" items="${page.list}" varStatus="status">
                        <tr>
                            <td><a class="link" href="${ctx}/pm/purchase/${item.purchaseType == '1' ? 'mc' : 'acc'}/form?id=${item.id}">${item.purchaseNo}</a></td>
                            <td>${fns:getDictLabel(item.purchaseType, 'DM0021', '')}</td>
                            <td>${fns:getDictLabel(item.ifFoc, 'yes_no', '')}</td>
                            <td>${item.supplierName}</td>
                            <td>${fns:getDictLabel(item.purStatus, 'DM0015', '')}</td>
                            <td>${fns:getDictLabel(item.paymentStatus, 'DM0022', '')}</td>
                            <td>${fns:getDictLabel(item.arrivalStatus, 'DM0023', '')}</td>
                            <td>${item.materialNo}</td>
                            <td title="${item.materialName}"><div style="max-width:100px" class="ellipsis">${item.materialName}</div></td>
                            <td class="text-right"><fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/></td>
                            <td class="text-right">${item.num}</td>
                            <td class="text-right">${item.unpayNum}</td>
                            <td class="text-right"><fmt:formatNumber value="${item.unpayAmount}" pattern="#,##0.00"/></td>
                            <td class="text-right">${item.alArrivalNum}</td>
                            <td class="text-right">${item.unarrivalNum}</td>
                            <td title="${item.invoiceNo}"><div style="max-width:100px" class="ellipsis">${item.invoiceNo}</div></td>
                            <td title="${item.axNo}"><div style="max-width:80px" class="ellipsis">${item.axNo}</div></td>
                        </tr>
                    </c:forEach> 
                </c:if>
                <c:if test="${empty page.list}">
                    <tr><td class="text-center" colspan="17">没有您需要的查询结果</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
    <div class="pagination">${page}</div>
    <sys:message content="${message}"/>
</body>
</html>