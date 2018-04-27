<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>维修记录管理</title>
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
        });
		function cloneTableHeader_Width(){  
			if ($("#contentTable>tbody>tr:not(.empty)").length > 0) {

		        var myTable_Width = (document.body.clientWidth);  

		        var height = $("#contentTable").height() + 23;

		        FixTable("contentTable", 3, myTable_Width, height);
			}
	     }
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/rm/repair/list");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
    <h3 class="text-center page-title">维修记录管理</h3>
    <form:form id="searchForm" modelAttribute="search" action="${ctx}/rm/repair/list" method="get" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <ul class="ul-form">
            <li>
                <label>单位名称：</label>
                <form:input path="repairCusName" class="input-medium" type="text" />
            </li>
            <li>
                <label>S/N：</label>
                <form:input path="snNo" class="input-medium" type="text" />
            </li>
            <li>
                <label>机器型号：</label>
                <form:input path="mcModel" type="text" class="remote material input-medium" data-type="1" data-show="text" />
            </li>
            <li>
                <label>配件型号：</label>
                <form:input path="acModel" type="text" class="remote material input-medium" data-type="2" data-show="text" />
            </li>
            <li class="clearfix"></li>
            <li>
                <label>处理状态：</label>
                <form:select path="repairStatus" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0029')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>处理方式：</label>
                <form:select path="repairMethod" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0028')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>维修分类：</label>
                <form:select path="repairType" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0026')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>是否占用：</label>
                <form:select path="ifOccupy" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li class="clearfix"></li>
            <li>
                <label>负责工程师：</label>
                <form:input path="responsiblePersonId" class="input-medium remote employee" data-type="20" data-show="text" />
<%--                 <form:select path="responsiblePersonId" class="input-medium"> --%>
<!--                     <option value="" label=""/> -->
<%--                     <form:options items="${fns:getSqlDictList('dict_engineer')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<%--                 </form:select> --%>
            </li>
            <li>
                <label>收款情况：</label>
                <form:select path="receiveStatus" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0011')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>开票情况：</label>
                <form:select path="invoiceStatus" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0012')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>维修编号：</label>
                <form:input path="repairNo" class="input-medium" type="text" />
            </li>
            <li class="clearfix"></li>
            <li>
                <label>报修日起：</label>
                <input name="askRepairDateFrom" type="text" class="input-medium Wdate"
                    value="<fmt:formatDate value="${search.askRepairDateFrom}" pattern="yyyy-MM-dd"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
            </li>
            <li>
                <label>报修日止：</label>
                <input name="askRepairDateTo" type="text" class="input-medium Wdate"
                    value="<fmt:formatDate value="${search.askRepairDateTo}" pattern="yyyy-MM-dd"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
            </li>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/rm/repair/list';">
                <input class="btn btn-primary" type="submit" value="一览导出" onclick="javascript:this.form.action='${ctx}/rm/repair/export';">
            </li>
        </ul>
    </form:form>
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
            <thead>
                <tr>
                    <shiro:hasAnyPermissions name="rm:quotaInvoice:apply,rm:quotaInvoice:edit">
                        <th></th>
                    </shiro:hasAnyPermissions>
                    <th>单位名称</th>
                    <th>型号</th>
                    <th>S/N</th>
                    <th>生产日期</th>
                    <th>处理状态</th>
                    <th>维修分类</th>
                    <th width="150px">问题描述</th>
                    <th width="150px">情况确认</th>
                    <th width="150px">处理内容</th>
                    <th>是否占用</th>
                    <th width="150px">最终使用配件</th>
                    <th>处理方式</th>
                    <th>安装日期</th>
                    <th>收款状态</th>
                    <th>开票状态</th>
                    <th>报修日期</th>
                    <th>维修编号</th>
    <!--                 <th>最终报价单</th> -->
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.list}" var="item">
                <tr>
                    <shiro:hasAnyPermissions name="rm:quotaInvoice:apply,rm:quotaInvoice:edit">
                        <td>
                            <shiro:hasPermission name="rm:quotaInvoice:apply">
                                <input class="btn btn-primary" type="button" value="发票申请" ${(empty item.quotationId || item.ifOut != '1') ? 'disabled' : ''} onclick="javascript:window.location.href='${ctx}/rm/quotation/invoice/form?id=${item.quotationId}&ivcMode=apply';">
                            </shiro:hasPermission>
                            <shiro:hasPermission name="rm:quotaInvoice:edit">
                                <input class="btn btn-primary" type="button" value="发票编辑" ${(empty item.quotationId || item.ifOut != '1') ? 'disabled' : ''} onclick="javascript:window.location.href='${ctx}/rm/quotation/invoice/form?id=${item.quotationId}&ivcMode=edit';">
                            </shiro:hasPermission>
                            
                        </td>
                    </shiro:hasAnyPermissions>
                    <td>${item.repairCusName}</td>
                    <td>${item.mcModel}</td>
                    <td>${item.snNo}</td>
                    <td><fmt:formatDate value="${item.productionDate}" pattern="yyyy-MM-dd"/></td>
                    <td>${fns:getDictLabel(item.repairStatus, 'DM0029', '')}</td>
                    <td>${fns:getDictLabel(item.repairType, 'DM0026', '')}</td>
                    <td title="${item.issueDescribe}"><div class="ellipsis" style="max-width:150px;">${item.issueDescribe}</div></td>
                    <td title="${item.issueDetail}"><div class="ellipsis" style="max-width:150px;">${item.issueDetail}</div></td>
                    <td title="${item.processingContent}"><div class="ellipsis" style="max-width:150px;">${item.processingContent}</div></td>
                    <td>${fns:getDictLabel(item.ifOccupy, 'yes_no', '')}</td>
                    <td title="${item.acMaterialName}"><div class="ellipsis" style="max-width:150px;">${item.acMaterialName}</div></td>
                    <td>${fns:getDictLabel(item.repairMethod, 'DM0028', '')}</td>
                    <td><fmt:formatDate value="${item.actualInstallDate}" pattern="yyyy-MM-dd"/></td>
                    <td>${fns:getDictLabel(item.receiveStatus, 'DM0011', '')}</td>
                    <td>${fns:getDictLabel(item.invoiceStatus, 'DM0012', '')}</td>
                    <td><fmt:formatDate value="${item.askRepairDate}" pattern="yyyy-MM-dd"/></td>
                    <td><a class="link" href="${ctx}/rm/repair/form?id=${item.id}">${item.repairNo}</a></td>
    <%--                 <td><a href="${ctx}/rm/quotation/invoice/form?id=${item.quotationId}">${item.quotationNo}</a></td> --%>
                </tr>
            </c:forEach>
            <c:if test="${empty page.list}">
                <tr class="empty"><td class="text-center" colspan="18">没有您需要的查询结果</td></tr>
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