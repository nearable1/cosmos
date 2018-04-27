<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>退货申请</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">

		$(document).ready(function() {
			
			initPage();
			
			function initPage() {
				// 添加画面验证
				$("#inputForm").validate({
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

            // 全选按钮change事件绑定
            $("#smStorageAppDtlTable .selAll").on("change", function() {
                if ($(this).is(":checked")) {
                    $("#smStorageAppDtlTable>tbody>tr>td>input.selSingle").prop("checked", true);
                } else {
                    $("#smStorageAppDtlTable>tbody>tr>td>input.selSingle").prop("checked", false);
                }
            });
			
			// 查询
			$("#search").on("click", function() {
                $("#warehouse").removeClass("required");
				$("#inputForm").attr("action","${ctx}/mm/inStorageManagement/refund/search");
				$("#inputForm").submit();
				$("#inputForm").attr("action","${ctx}/mm/inStorageManagement/save/refund");
			});
			
			// 提交申请以及保存
			$("input[type='button'].opt").on("click", function() {
				if ($(this).val() == "退回") {
					var r=confirm("确认要退回给申请者？请确认！");
					if (r != true) {
						return false;
					}
				}
				if (!$("#inputForm").valid()) {
					return false;
				} else {
					$("#inputForm").append('<input type="hidden" name="opt" value="' + $(this).val() + '">')
		            // 异步数据提交
		            $.ajax({
		                type: "post",
				        async: false,
		                url: "${ctx}" + "/mm/inStorageManagement/save/refund",
		                data: $("#inputForm").serialize(),
		                success: function(oData, oStatus) {
		                	if (oData.success) {
		                		//$("#inputForm").submit();
		                		showMessage("操作成功");
		                		window.location.href="${ctx}/act/task/todo";
		                	}
		                },
		                error: function(oData, oStatus, eErrorThrow) {
		                }
		            });
				}
			});
		});
	</script>

</head>
<body>
	<h3 class="text-center page-title">退货申请</h3>
<%-- 	<ul id="myTab" class="nav nav-tabs">
		<li><a href="${ctx}/mm/inStorageManagement/init/sendBack">归还</a></li>
		<li class="active"><a href="${ctx}/mm/inStorageManagement/init/refund">退货</a></li>
		<li><a href="${ctx}/mm/inStorageManagement/init/exchange">换货</a></li>
		<li><a href="${ctx}/mm/inStorageManagement/init/other">其他</a></li>
	</ul> --%>
	<form:form id="inputForm" modelAttribute="smStorageApp" action="${ctx}/mm/inStorageManagement/save/refund" method="post" class="form-search" >
	    <form:hidden path="id"/>
	    <form:hidden path="procInsId"/>
	    <c:if test="${!empty smStorageApp.updateDate}">
	    	<input type="hidden" name="updateDate" value="<fmt:formatDate value="${smStorageApp.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
	    </c:if>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.procInsId"/>
		<% boolean readonlyFlag = true; %>
		<c:if test="${empty smStorageApp.workflowStatus || (smStorageApp.createBy == user.id && (smStorageApp.workflowStatus == '30' || smStorageApp.workflowStatus == '40'))}">
			<% readonlyFlag = false; %>
		</c:if>
		<% boolean optFlag = true; %>
		<div class="group-box group-box-first" style="height: auto;">
			<ul class="ul-form">
				<li><label>负责人：</label> <form:hidden path="responsiblePersonId" />
					<form:input path="responsiblePersonName" class="input-medium"
						type="text" value="${smStorageApp.responsiblePersonName}"
						readonly="true" /></li>
				<li><label>操作日期：</label> <input id="processDate"
					name="processDate"
					value="<fmt:formatDate value="${smStorageApp.processDate}" pattern="yyyy-MM-dd"/>"
					type="text" readonly class="input-medium" /></li>
				<li><label><span class="help-inline"><font color="red">*</font></span>退回库房：</label>
					<% if (!readonlyFlag) { %>
						<form:select path="warehouse" class="input-small required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('DM0050')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					<% } else { %>
						<input type="hidden" name="warehouse" value="${smStorageApp.warehouse}"/>
						<form:select path="warehouse" class="input-small" disabled="true">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('DM0050')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					<% } %>
				</li>
				<li class="full-width"><label>备注：</label>
				<% if (!readonlyFlag) { %>
					<form:textarea path="newRemarks" value="${smStorageApp.newRemarks}" rows="2"
							class="fill-right" maxlength="300"/>
				<% } else { %>
					<form:textarea path="newRemarks" value="${smStorageApp.newRemarks}" rows="2"
							class="fill-right" maxlength="300" disabled="true"/>
				<% } %>
				</li>
			</ul>
		</div>
		<div class="group-box group-box-last">
			<% if (!readonlyFlag) { %>
				<ul class="ul-form">
					<li><label>合同编号：</label>
						<form:input path="orderNo" class="input-medium"
							type="text" value="${smStorageApp.orderNo}"/>
					</li>
					<li><label>S/N：</label>
						<form:input path="snNo" class="input-medium"
							type="text" value="${smStorageApp.snNo}"/>
					</li>
					<li class="btns">
						<input id="search" class="btn btn-primary" type="button" value="查询">
					</li>
				</ul>
			<%} %>
			<div>
			<div style="overflow-x: scroll;">
				<table id="smStorageAppDtlTable"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<% if (!readonlyFlag) { %>
								<c:if test="${!empty smStorageApp.smStorageAppDtlList}">
									<th class="text-center" width="20px"><input type="checkbox" class="selAll"></th>
								</c:if>
							<% } %>
							<th>合同编号</th>
							<th>签约方</th>
							<th>最终客户</th>
							<th>物料号</th>
							<th>物料名称</th>
							<th>数量</th>
							<th>S/N</th>
							<th>生产日期</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${!empty smStorageApp.smStorageAppDtlList}">
								<% optFlag = false; %>
								<c:forEach var="item"
									items="${smStorageApp.smStorageAppDtlList}" varStatus="status">
									<tr>
										<% if (!readonlyFlag) { %>
											<td class="text-center">
												<input type='checkbox' class="selSingle" name="selectedList" value="${status.index}"/>
											</td>
										<% } %>
										<td>
											<input type="hidden" name="smStorageAppDtlList[${status.index}].orderNo" value="${item.soOrderDtl.orderNo}"/>
											<input type="hidden" name="smStorageAppDtlList[${status.index}].lineNo" value="${item.soOrderDtl.lineNo}"/>
											<input type="hidden" name="smStorageAppDtlList[${status.index}].num" value="${item.soOrderDtl.deliverNum}"/>
											<input type="hidden" name="smStorageAppDtlList[${status.index}].materialNo" value="${item.soOrderDtl.materialNo}"/>
											<input type="hidden" name="smStorageAppDtlList[${status.index}].materialName" value="${item.soOrderDtl.materialName}"/>
											<input type="hidden" name="smStorageAppDtlList[${status.index}].snNo" value="${item.soOrderDtl.snNo}"/>
											<input type="hidden" name="smStorageAppDtlList[${status.index}].productionDate" value="${item.soOrderDtl.productionDate}"/>
											${item.soOrderDtl.orderNo}
										</td>
										<td>${item.soOrderDtl.customerChName}</td>
										<td>${item.soOrderDtl.endCustomerChName}</td>
										<td>${item.soOrderDtl.materialNo}</td>
										<td>${item.soOrderDtl.materialName}</td>
										<td class="text-right">${item.soOrderDtl.deliverNum}</td>
										<td>${item.soOrderDtl.snNo}</td>
										<td><fmt:formatDate value="${item.soOrderDtl.productionDate}" pattern="yyyy-MM-dd" /></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center" colspan="8">没有可退货的数据</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				</div>
			</div>
		</div>
		<c:if test="${smStorageApp.act.assignee == user.loginName && (smStorageApp.workflowStatus == '10' || smStorageApp.workflowStatus == '20')}">
			<div class="group-box">
				<div class="group-header">
					<strong class="group-title">审批</strong>
				</div>

				<ul class="ul-form">
					<li style="width: 100%;"><label>审批意见：</label>
						<div style="overflow: hidden;">
							<form:textarea path="act.comment" rows="3" maxlength="300"
								style="width:100%;"></form:textarea>
						</div></li>
				</ul>

				<div class="text-center">
					<!-- <input name="opt" type="submit" class="btn btn-primary" value="同意">
					<input name="opt" type="submit" class="btn btn-default" value="退回"> -->
					<input name="" type="button" class="btn btn-primary opt" value="同意">
					<input name="" type="button" class="btn btn-default opt" value="退回">
				</div>
			</div>
		</c:if>
		<c:if
			test="${smStorageApp.createBy == user.id && smStorageApp.workflowStatus == '10'}">
				<div class="text-center">
					<!-- <input name="opt" type="submit" class="btn btn-primary" value="撤回"> -->
					<input name="" type="button" class="btn btn-primary opt" value="撤回">
				</div>
		</c:if>
		<c:if test="${empty smStorageApp.workflowStatus || (smStorageApp.createBy == user.id && (smStorageApp.workflowStatus == '40' || smStorageApp.workflowStatus == '30'))}">
			<div class="text-center">
				<%-- <input id="opt" name="opt" type="button" class="btn btn-primary" value="提交申请" <%=optFlag?"disabled='disabled'":"" %>> --%>
				<input name="" type="button" class="btn btn-primary opt" value="提交申请" <%=optFlag?"disabled='disabled'":"" %>>
				<c:if test="${smStorageApp.createBy == user.id && (smStorageApp.workflowStatus == '40' || smStorageApp.workflowStatus == '30')}">
					<!-- <input name="opt" type="submit" class="btn btn-primary" value="删除"> -->
					<input name="" type="button" class="btn btn-primary opt" value="删除">
				</c:if>
			</div>
		</c:if>
		<c:if test="${!empty smStorageApp.procInsId}">
			<act:histoicFlow procInsId="${smStorageApp.procInsId}"/>
		</c:if>
	</form:form>
	<sys:message content="${message}"/>
</body>
</html>