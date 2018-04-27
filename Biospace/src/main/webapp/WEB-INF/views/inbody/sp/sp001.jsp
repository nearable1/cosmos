<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>销售计划履历</title>
    <meta name="decorator" content="default"/>

    <style type="text/css">
    </style>

    <script type="text/javascript">
        $(document).ready(function() {
            // 添加画面验证
            $("#inputForm").validate({
                submitHandler: function(form){
                    form.submit();
                },
                invalidHandler: function(form, validator) {
                    showError("输入有误，请先更正。");
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            // 手动执行点击事件绑定
            $("#execute").on("click", function() {
                var fHtml = '<form method="post" action="${ctx}/sp/resume/execute" onsubmit="javascript:submitLockView();">';
                    fHtml += '<input type="hidden" name="expTurnoverMonth" value="' + $("#expTurnoverMonth").val() + '">';
                    fHtml += '</form>';
                $(fHtml).appendTo('body').submit().remove();
            });
        });

        function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/sp/resume/list");
            $("#searchForm").submit();
            return false;
        }
    </script>

</head>
<body>
    <h3 class="text-center page-title">销售计划履历</h3>
    <form:form id="searchForm" modelAttribute="searchForm" action="${ctx}/sp/resume/list" method="get" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <ul class="ul-form">
            <li>
                <label>成交月：</label>
                <input name="expTurnoverMonth" id="expTurnoverMonth" class="input-medium Wdate" 
                    onclick="WdatePicker({dateFmt:'yyyy-MM'});" type="text" value="<fmt:formatDate value="${searchForm.expTurnoverMonth}" pattern="yyyy-MM"/>"/>
            </li>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();">
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/sp/resume/list';">
                <input class="btn btn-primary" type="button" value="手动执行" id="execute" >
            </li>
        </ul>
    </form:form>
    
    <table id="purTable" class="table table-striped table-bordered table-condensed">
        <thead>
            <tr>
                <th>成交月</th>
                <th>自动/手动</th>
                <th>文件名</th>
                <th>执行人</th>
                <th>执行时间</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${!empty page.list}"> 
                <c:forEach var="item" items="${page.list}" varStatus="status">
                    <tr>
                        <td><fmt:formatDate value="${item.expTurnoverMonth}" pattern="yyyy-MM"/></td>
                        <td>${fns:getDictLabel(item.method, 'DM0061', '')}</td>
                        <td><a class="link" href="${ctx}/sp/resume/download/file/${item.assId}">${item.fileName}</a></td>
                        <td>${item.executor}</td>
                        <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                    </tr>
                </c:forEach> 
            </c:if>
            <c:if test="${empty page.list}">
                <tr><td class="text-center" colspan="5">没有您需要的查询结果</td></tr>
            </c:if>
        </tbody>
    </table>
    <div class="pagination">${page}</div>
    <sys:message content="${message}"/>
</body>
</html>