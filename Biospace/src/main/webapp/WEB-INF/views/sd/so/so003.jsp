<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发货申请</title>
	<meta name="decorator" content="default"/>
	
	<!-- <style type="text/css">
		.table-overflow-soApplyDeliverDtl{
			width:1640px;
		}
	</style> -->
	
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

			// 数量、单价change事件绑定
			$(document).on("change", "input.num", function() {
				var _this = $(this);
				var $row = _this.parent().parent();
				var rowNo = Number($row.find(".rowNo").html());
				
				var soApplyDeliverDtlList = ${fns:toJson(soApplyDeliver.soApplyDeliverDtlList)};
				
				var num = Number(_this.val());
				var totalNum = Number(soApplyDeliverDtlList[rowNo-1].totalNum);
				var deliverNum;
				if (typeof(soApplyDeliverDtlList[rowNo-1].deliverNum) == "undefined") {
					deliverNum = 0;
				} else {
					deliverNum = Number(soApplyDeliverDtlList[rowNo-1].deliverNum);
				}
				
				if ((num+deliverNum) > totalNum) {
					_this.val(totalNum - deliverNum);
					alert("发货数量超出了总数量，请确认！");
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
		                url: "${ctx}" + "/sd/soOrder/soApplyDeliver/save",
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
	<form:form id="inputForm" modelAttribute="soApplyDeliver" action="${ctx}/sd/soOrder/soApplyDeliver/save" method="post" class="form-search" >
	    <form:hidden path="id"/>
	    <form:hidden path="procInsId"/>
	    <c:if test="${!empty soApplyDeliver.updateDate}">
	    	<input type="hidden" name="updateDate" value="<fmt:formatDate value="${soApplyDeliver.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
	    </c:if>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.procInsId"/>
		<% boolean readonlyFlag = true; %>
		<c:if test="${empty soApplyDeliver.workflowStatus || (soApplyDeliver.createBy == user.id && (soApplyDeliver.workflowStatus == '30' || soApplyDeliver.workflowStatus == '40'))}">
			<% readonlyFlag = false; %>
		</c:if>
		<% boolean optFlag = true; %>
			<h3 class="text-center page-title">发货申请</h3>
			<div class="group-box group-box-first" style="height:auto;">
			    <ul class="ul-form">
			        <li>
			            <label><span class="help-inline"><font color="red">*</font></span>合同编号：</label>
			            <% if (!readonlyFlag) { %>
			            	<form:input path="orderNo" class="input-medium required" type="text" />
			            <% } else { %>
			            	<form:input path="orderNo" class="input-medium" type="text" readonly="readonly"/>
			            <% } %>
			        </li>
			        <li>
			            <label>签约方：</label>
			            <input class="input-medium" type="text" value="${soApplyDeliver.customerName}" readonly/>
			        </li>
			        <li>
			            <label>收款状态：</label>
			            <input class="input-medium" type="text" value="${fns:getDictLabel(soApplyDeliver.receiveStatus, 'DM0011', '')}" readonly />
			        </li>
			        <li>
			            <label>开票状态：</label>
			            <input class="input-medium" type="text" value="${fns:getDictLabel(soApplyDeliver.invoiceStatus, 'DM0012', '')}" readonly />
			        </li>
			        <li>
			            <label>要求发货日：</label>
			            <% if (!readonlyFlag) { %>
			            	<input id="expectDate" name="expectDate" value="<fmt:formatDate value="${soApplyDeliver.expectDate}" pattern="yyyy-MM-dd"/>" type="text" readonly maxlength="10" class="input-medium Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			            <% } else { %>
			            	<input id="expectDate" name="expectDate" value="<fmt:formatDate value="${soApplyDeliver.expectDate}" pattern="yyyy-MM-dd"/>" type="text" readonly maxlength="10" class="input-medium"/>
			            <% } %>
			        </li>
		            <li class="btns">
						<input id="search" class="btn btn-primary" type="submit" value="查找" onclick="javascript:this.form.action='${ctx}/sd/soOrder/soApplyDeliver/search';" <%=readonlyFlag?"disabled='disabled'":"" %>>
					</li>
			    </ul>
			</div>
			<div class="group-box">
				<div style="overflow-x:scroll;">
					<table id="soApplyDeliverDtlTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="text-center">No</th>
								<th>最终客户</th>
								<th>套餐名称</th>
								<th>物料号</th>
								<th>物料名称</th>
								<th>总数量</th>
								<th>已发货数量</th>
								<th>申请发货数量</th>
								<th>联系人</th>
								<th>联系方式</th>
								<th>收件地址</th>
								<th>是否需要安装</th>
							</tr>
						</thead>
						<tbody>
						    <c:choose>
						        <c:when test="${!empty soApplyDeliver.soApplyDeliverDtlList}"> 
						        <% optFlag = false; %>
						            <c:forEach var="item" items="${soApplyDeliver.soApplyDeliverDtlList}" varStatus="status">
									    <tr>
								           <form:hidden path="soApplyDeliverDtlList[${status.index}].id"/>
								           <form:hidden path="soApplyDeliverDtlList[${status.index}].orderNo"/>
								           <form:hidden path="soApplyDeliverDtlList[${status.index}].lineNo"/>
								           <form:hidden path="soApplyDeliverDtlList[${status.index}].soOrderDtlId"/>
								           <form:hidden path="soApplyDeliverDtlList[${status.index}].totalNum"/>
								           <form:hidden path="soApplyDeliverDtlList[${status.index}].deliverNum"/>
								           <form:hidden path="soApplyDeliverDtlList[${status.index}].materialNo"/>
								           <form:hidden path="soApplyDeliverDtlList[${status.index}].packageMertiralNo"/>
								           <form:hidden path="soApplyDeliverDtlList[${status.index}].packageMertiralName"/>
									    	<td class="text-center rowNo">
									           ${status.index + 1}
									        </td>
									        <td>${item.customerChName}</td>
									        <td>${item.packageMertiralName}</td>
										    <td>${item.materialNo}</td>
										    <td>${item.materialName}</td>
										    <td class="text-right">${item.totalNum}</td>
										    <td class="text-right">${item.deliverNum}</td>
										    <td><input name="soApplyDeliverDtlList[${status.index}].num" maxlength="3" type="text" value="${item.num}" class="input-mini text-right num" <%=readonlyFlag?"readonly":"" %>/></td>
										    <td><input name="soApplyDeliverDtlList[${status.index}].contactsName" maxlength="100" type="text" class="input-medium" value="${item.contactsName}" <%=readonlyFlag?"readonly":"" %>/></td>
										    <td><input name="soApplyDeliverDtlList[${status.index}].telephone" maxlength="50" type="text" class="input-medium" value="${item.telephone}" class="input-medium phone" <%=readonlyFlag?"readonly":"" %>/></td>
										    <td><input name="soApplyDeliverDtlList[${status.index}].address" maxlength="300" type="text" class="input-medium" value="${item.address}" <%=readonlyFlag?"readonly":"" %>/></td>
										    <td>
										    	<% if (!readonlyFlag) { %>
											      	<select name="soApplyDeliverDtlList[${status.index}].ifInstall" class="input-mini">
											       		<option value=""></option>
														<c:forEach items="${fns:getDictList('yes_no')}" var="ifInstalls">
															<option value="${ifInstalls.value}" ${ifInstalls.value==item.ifInstall?'selected':''}>${ifInstalls.label}</option>
														</c:forEach>
											       	</select>
										    	<% } else { %>
										    		 <form:hidden path="soApplyDeliverDtlList[${status.index}].ifInstall"/>
											      	<select name="soApplyDeliverDtlList[${status.index}].ifInstall" class="input-mini" disabled="disabled">
											       		<option value=""></option>
														<c:forEach items="${fns:getDictList('yes_no')}" var="ifInstalls">
															<option value="${ifInstalls.value}" ${ifInstalls.value==item.ifInstall?'selected':''}>${ifInstalls.label}</option>
														</c:forEach>
											       	</select>
										    	<% } %>
					            			</td>
									    </tr>
						            </c:forEach> 
						        </c:when>
						    </c:choose>
						</tbody>	
					</table>
				</div>
			</div>
		<c:if
			test="${soApplyDeliver.act.assignee == user.loginName && (soApplyDeliver.workflowStatus == '10' || soApplyDeliver.workflowStatus == '20')}">
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
			test="${soApplyDeliver.createBy == user.id && soApplyDeliver.workflowStatus == '10'}">
			<div class="group-box">
				<div class="text-center">
					<!-- <input name="opt" type="submit" class="btn btn-primary" value="撤回"> -->
					<input name="" type="button" class="btn btn-primary opt" value="撤回">
				</div>
			</div>
		</c:if>
		<c:if test="${empty soApplyDeliver.workflowStatus || (soApplyDeliver.createBy == user.id && (soApplyDeliver.workflowStatus == '40' || soApplyDeliver.workflowStatus == '30'))}">
			<div class="text-center">
				<%-- <input id="opt" name="opt" type="button" class="btn btn-primary" value="提交申请" <%=optFlag?"disabled='disabled'":"" %>> --%>
				<input name="" type="button" class="btn btn-primary opt" value="提交申请" <%=optFlag?"disabled='disabled'":"" %>>
				<c:if test="${soApplyDeliver.createBy == user.id && (soApplyDeliver.workflowStatus == '40' || soApplyDeliver.workflowStatus == '30')}">
					<!-- <input name="opt" type="submit" class="btn btn-primary" value="删除"> -->
					<input name="" type="button" class="btn btn-primary opt" value="删除">
				</c:if>
			</div>
		</c:if>
		<c:if test="${!empty soApplyDeliver.procInsId}">
			<act:histoicFlow procInsId="${soApplyDeliver.procInsId}"/>
		</c:if>
	</form:form>
	<sys:message content="${message}"/>
</body>
</html>