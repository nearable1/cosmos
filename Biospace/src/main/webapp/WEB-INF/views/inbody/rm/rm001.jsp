<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>无S/N咨询录入</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
    .input-xlarge {
        width: 301px;
    }
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
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
                },
                ignore:""
            });
			
			// 支付条件变更事件
			$("#consultingStatus").on("change", function() {
				var $consultingStatus = $(this);
				var consultingStatus = $consultingStatus.val();
				if (consultingStatus == '4') {

					$("#responsiblePersonId").rules("add", { required: true});
				} else {
					$("#responsiblePersonId").rules("remove");
				}
			});
        });
    </script>
</head>
<body>
    <h3 class="text-center page-title">无S/N咨询录入</h3>
    <form:form id="inputForm" modelAttribute="consultingInfo" action="${ctx}/rm/consulting/save" method="post" class="form-search">

        <form:hidden path="id"/>
        <form:hidden path="consultingNo"/>
        <form:hidden path="createBy.id"/>
        <input type="hidden" name="updateDate" value="<fmt:formatDate value="${consultingInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />

        <ul class="ul-form">
            <li>
                <label>单位名称：</label>
                <form:input path="customerName" class="input-medium" type="text" maxlength="100" />
            </li>
            <li>
                <label>联系人：</label>
                <form:input path="contactsName" class="input-medium" type="text" maxlength="100" />
            </li>
            <li>
                <label>电话：</label>
                <form:input path="telephone" class="input-medium phone" type="text" maxlength="50" />
            </li>
            <li class="clearfix"></li>
            <li>
                <label><span class="help-inline"><font color="red">*</font></span>咨询型号：</label>
                <form:input path="materialNo" type="text" maxlength="50"  class="remote material input-xlarge required" data-type="1" data-show="text" />
            </li>
            <li>
                <label class="control-label"><span class="help-inline"><font color="red">*</font></span>咨询日期：</label>
                <input name="consultingDate" type="text" maxlength="10" class="input-medium Wdate required"
                    value="<fmt:formatDate value="${consultingInfo.consultingDate}" pattern="yyyy-MM-dd"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
            </li>
            <li>
                <label>咨询编号：</label>
                <input class="input-medium" type="text" value="${consultingInfo.consultingNo}" disabled />
            </li>
            <li class="clearfix"></li>
            <li>
                <label>问题分类：</label>
                <form:select path="consultingType" class="input-xlarge" >
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0019')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>处理状态：</label>
                <form:select path="consultingStatus" class="input-medium" >
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('DM0029')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li>
                <label>负责工程师：</label>
                <c:if test="${consultingInfo.consultingStatus == '4'}">
                	<form:input path="responsiblePersonId" class="input-medium remote employee required" data-type="20" data-show="text" data-shiro="1" />
                </c:if>
                <c:if test="${consultingInfo.consultingStatus != '4'}">
                	<form:input path="responsiblePersonId" class="input-medium remote employee" data-type="20" data-show="text" data-shiro="1" />
                </c:if>
            </li>
            <li>
                <label>记录人：</label>
                <input class="input-medium" type="text" value="${(fns:getUserById(consultingInfo.createBy.id)).name}" disabled />
            </li>
            <li class="full-width">
                <label>备注：</label>
                <form:textarea path="newRemarks" rows="3" maxlength="300" class="fill-right" />
            </li>
        </ul>
        <div class="text-center">
            <input class="btn btn-primary" type="submit" value="保 存"/>
            <c:if test="${!empty consultingInfo.id}">
            <input class="btn btn-primary" type="submit" value="删除" onclick="javascript:this.form.action='${ctx}/rm/consulting/delete';"/>
            </c:if>
            <input class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form:form>
    <sys:message content="${message}"/>
</body>
</html>