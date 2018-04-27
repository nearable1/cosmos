<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>无S/N咨询查询</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
    .input-xlarge {
        width: 301px;
    }
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/rm/consulting/list");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
    <h3 class="text-center page-title">无S/N咨询查询</h3>
    <form:form id="searchForm" modelAttribute="search" action="${ctx}/rm/consulting/list" method="get" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <ul class="ul-form">
            <li>
                <label>单位名称：</label>
                <form:input path="customerName" class="input-medium" type="text" />
            </li>
            <li>
                <label>咨询型号：</label>
                <form:input path="materialNo" type="text" class="remote material input-xlarge" data-type="1" data-show="text" />
            </li>
            <li>
                <label>问题分类：</label>
                <form:select path="consultingType" class="input-xlarge">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0019')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li class="clearfix"></li>
            <li>
                <label>处理状态：</label>
                <form:select path="consultingStatus" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0029')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>负责工程师：</label>
                <form:input path="responsiblePersonId" class="input-medium remote employee" data-type="20" data-show="text" data-shiro="1" />
            </li>
            <li>
                <label>咨询日期起：</label>
                <input name="consultingDateFrom" type="text" class="input-medium Wdate "
                    value="<fmt:formatDate value="${search.consultingDateFrom}" pattern="yyyy-MM-dd"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
            </li>
            <li>
                <label class="text-center">咨询日期止：</label>
                <input name="consultingDateTo" type="text" class="input-medium Wdate "
                    value="<fmt:formatDate value="${search.consultingDateTo}" pattern="yyyy-MM-dd"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
            </li>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/rm/consulting/list';">
                <input class="btn btn-primary" type="submit" value="一览导出" onclick="javascript:this.form.action='${ctx}/rm/consulting/export';">
            </li>
        </ul>
    </form:form>
    <div class="auto-scroll-x">
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
            <tr>
                <th>单位名称</th>
                <th>联系人</th>
                <th>电话</th>
                <th>咨询型号</th>
                <th>咨询问题分类</th>
                <th>处理状态</th>
                <th>负责工程师</th>
                <th>咨询日期</th>
                <th>记录人</th>
                <th>编号</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="item">
            <tr>
                <td>${item.customerName}</td>
                <td>${item.contactsName}</td>
                <td>${item.telephone}</td>
                <td>${item.materialName}</td>
                <td>${fns:getDictLabel(item.consultingType, 'DM0019', '')}</td>
                <td>${fns:getDictLabel(item.consultingStatus, 'DM0029', '')}</td>
                <td>${item.responsiblePersonName}</td>
                <td><fmt:formatDate value="${item.consultingDate}" pattern="yyyy-MM-dd"/></td>
                <td>${item.createBy.name}</td>
                <td><a class="link" href="${ctx}/rm/consulting/form?id=${item.id}">${item.consultingNo}</a></td>
            </tr>
        </c:forEach>
        <c:if test="${empty page.list}">
            <tr><td class="text-center" colspan="10">没有您需要的查询结果</td></tr>
        </c:if>
        </tbody>
    </table>
    </div>
    <div class="pagination">${page}</div>
    <sys:message content="${message}"/>
</body>
</html>