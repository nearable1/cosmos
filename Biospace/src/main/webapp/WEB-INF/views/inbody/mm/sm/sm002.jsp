<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>归还申请</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	     div.ellipsis {
	         text-overflow: ellipsis; 
	         -moz-text-overflow: ellipsis; /* for Firefox,mozilla */   
	         overflow: hidden;
	         white-space: nowrap;
	     }
	</style>
	
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
                    $("#smStorageAppDtlTable>tbody>tr>td>input.num").removeAttr("readOnly");
                    $("#smStorageAppDtlTable>tbody>tr>td>input.num").addClass("required");
                    $("#smStorageAppDtlTable>tbody>tr>td>input.lendingDateTo").attr("onclick", "WdatePicker({dateFmt:'yyyy-MM-dd'});");
                    $("#smStorageAppDtlTable>tbody>tr>td>input.lendingDateTo").addClass("required");
                    $("#smStorageAppDtlTable>tbody>tr>td>select.warehouse").removeAttr("disabled");
                    $("#smStorageAppDtlTable>tbody>tr>td>select.warehouse").addClass("required");
                } else {
                    $("#smStorageAppDtlTable>tbody>tr>td>input.selSingle").prop("checked", false);
                    $("#smStorageAppDtlTable>tbody>tr>td>input.num").val("");
                    $("#smStorageAppDtlTable>tbody>tr>td>input.num").attr("readOnly","true");
                    $("#smStorageAppDtlTable>tbody>tr>td>input.num").removeClass("required");
                    $("#smStorageAppDtlTable>tbody>tr>td>input.lendingDateTo").val("");
                    $("#smStorageAppDtlTable>tbody>tr>td>input.lendingDateTo").removeAttr("onclick");
                    $("#smStorageAppDtlTable>tbody>tr>td>input.lendingDateTo").removeClass("required");
                    $("#smStorageAppDtlTable>tbody>tr>td>select.warehouse").select2("val","");
                    $("#smStorageAppDtlTable>tbody>tr>td>select.warehouse").attr("disabled","true");
                    $("#smStorageAppDtlTable>tbody>tr>td>select.warehouse").removeClass("required");
                }
            });

            // 选择框change事件绑定
            $("#smStorageAppDtlTable .selSingle").on("change", function() {
            	var $row = $(this).parent().parent();
                if ($(this).is(":checked")) {
                	$row.find("input.num").removeAttr("readOnly");
                	$row.find("input.num").addClass("required");
                	$row.find("input.lendingDateTo").attr("onclick", "WdatePicker({dateFmt:'yyyy-MM-dd'});");
                	$row.find("input.lendingDateTo").addClass("required");
                	$row.find("select.warehouse").removeAttr("disabled");
                	$row.find("select.warehouse").addClass("required");
                } else {
                	$row.find("input.num").val("");
                	$row.find("input.num").attr("readOnly","true");
                	$row.find("input.num").removeClass("required");
                	$row.find("input.lendingDateTo").val("");
                	$row.find("input.lendingDateTo").removeAttr("onclick");
                	$row.find("input.lendingDateTo").removeClass("required");
                	$row.find("select.warehouse").select2("val","");
                	$row.find("select.warehouse").attr("disabled","true");
                	$row.find("select.warehouse").removeClass("required");
                }
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
		                url: "${ctx}" + "/mm/inStorageManagement/save/sendBack",
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
	<h3 class="text-center page-title">归还申请</h3>
<%-- 	<ul id="myTab" class="nav nav-tabs">
		<li class="active"><a href="${ctx}/mm/inStorageManagement/init/sendBack">归还</a></li>
		<li><a href="${ctx}/mm/inStorageManagement/init/refund">退货</a></li>
		<li><a href="${ctx}/mm/inStorageManagement/init/exchange">换货</a></li>
		<li><a href="${ctx}/mm/inStorageManagement/init/other">其他</a></li>
	</ul> --%>
	<form:form id="inputForm" modelAttribute="smStorageApp" action="${ctx}/mm/inStorageManagement/save/sendBack" method="post" class="form-search" >
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
			<div class="group-header">
				<strong class="group-title">待归还物品一览</strong>
			</div>
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
							<th>物料号</th>
							<th>物料名称</th>
							<th>S/N</th>
							<th>生产日期</th>
							<th>数量</th>
							<th>借出目的</th>
							<th>客户名称</th>
							<th>行业</th>
							<th width="100px">备注</th>
							<th width="100px">相关配件备注</th>
							<th>借出时间</th>
							<th>借出到期日</th>
							<th>归还数量</th>
							<th>归还日期</th>
							<th>归还仓库</th>
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
												<input type="hidden" name="smStorageAppDtlList[${status.index}].materialNo" value="${item.smLendingMat.materialNo}"/>
												<input type="hidden" name="smStorageAppDtlList[${status.index}].snNo" value="${item.smLendingMat.snNo}"/>
												<input type="hidden" name="smStorageAppDtlList[${status.index}].lendingId" value="${item.smLendingMat.id}"/>
												<input type='checkbox' class="selSingle" name="selectedList" value="${status.index}"/>
											</td>
											<td>${item.smLendingMat.materialNo}</td>
										<% } else { %>
											<td>
												<input type="hidden" name="smStorageAppDtlList[${status.index}].materialNo" value="${item.smLendingMat.materialNo}"/>
												<input type="hidden" name="smStorageAppDtlList[${status.index}].snNo" value="${item.smLendingMat.snNo}"/>
												<input type="hidden" name="smStorageAppDtlList[${status.index}].lendingId" value="${item.smLendingMat.id}"/>
												<input type="hidden" name="smStorageAppDtlList[${status.index}].warehouse" value="${item.warehouse}"/>
												${item.smLendingMat.materialNo}
											</td>
										<% } %>
										<td>${item.smLendingMat.materialName}</td>
										<td>${item.smLendingMat.snNo}</td>
										<td><fmt:formatDate
												value="${item.smLendingMat.productionDate}"
												pattern="yyyy-MM-dd" /></td>
										<td class="text-right">${item.smLendingMat.num}</td>
										<td>${fns:getDictLabel(item.smStorageInfo.lendingType, 'DM0036', '')}</td>
										<td>${item.smLendingMat.lenderName}</td>
										<td>${fns:getDictLabel(item.smStorageInfo.industry, 'DM0002', '')}</td>
										<td title="${item.smStorageInfo.newRemarks}"><div style="max-width:100px" class="ellipsis">${item.smStorageInfo.newRemarks}</div></td>
										<td title="${item.smStorageInfo.accessoriesRemarks}"><div style="max-width:100px" class="ellipsis">${item.smStorageInfo.accessoriesRemarks}</div></td>
										<td><fmt:formatDate
												value="${item.smLendingMat.lendingDateFrom}"
												pattern="yyyy-MM-dd" /></td>
										<td><fmt:formatDate
												value="${item.smLendingMat.lendingDateTo}"
												pattern="yyyy-MM-dd" /></td>
										<td>
											<c:if test="${empty item.smLendingMat.snNo}">
												<input name="smStorageAppDtlList[${status.index}].num" type="text"
													value="${item.num}" class="input-small text-right digits num" max="${item.smLendingMat.num}" min="1"
													readonly="readonly" />
											</c:if>
											<c:if test="${!empty item.smLendingMat.snNo}">
												<input name="smStorageAppDtlList[${status.index}].num" type="text"
													value="${item.num}" class="input-small text-right digits num" max="${item.smLendingMat.num}" min="${item.smLendingMat.num}"
													readonly="readonly" />
											</c:if>
										</td>
										<td><input
											name="smStorageAppDtlList[${status.index}].lendingDateTo"
											type="text"
											value="<fmt:formatDate value="${item.lendingDateTo}" pattern="yyyy-MM-dd"/>"
											readonly="readonly" class="input-small Wdate lendingDateTo"/></td>
										<td><form:select
												path="smStorageAppDtlList[${status.index}].warehouse"
												class="input-small warehouse" disabled="true">
												<form:option value="" label="" />
												<form:options items="${fns:getDictList('DM0050')}"
													itemLabel="label" itemValue="value" htmlEscape="false" />
											</form:select></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center" colspan="15">没有可归还的数据</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
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