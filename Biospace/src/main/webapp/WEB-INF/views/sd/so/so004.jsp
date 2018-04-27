<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同出库</title>
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
			
			// 合同明细行变更事件
			$(document).on("change", "input.snNo", function(e) {
				var $snNo = $(this);
				var $row = $snNo.parent().parent();
				var snNo = $snNo.val();
				
				var $materialNo = $row.find("input.materialNo");
				var materialNo = $materialNo.val();
				
				var $productionDate = $row.find("input.productionDate");
				var $warehouse = $row.find("input.warehouse");
				var $disWarehouse = $row.find("select.disWarehouse");
				
				if (snNo == null || snNo == "") {

		        	$productionDate.val("");
		        	$warehouse.val("");
		        	$disWarehouse.val("");
		        	$disWarehouse.trigger("change");
				} else {

					$.ajax({
				    	url: "${ctx}" + "/sd/soOrder/smStorageInfo/getSnInfo",
				        type: "get",
				        async: false,
				        data: {"snNo":snNo, "materialNo":materialNo},
				        dataType: "json",
				        success: function (data) {
				        	
				        	if (data != null) {

					        	$productionDate.val(data.productionDate);
					        	$warehouse.val(data.warehouse);
					        	$disWarehouse.val(data.warehouse);
					        	$disWarehouse.trigger("change");
				        	}
				        },
				        error: function (msg) {
				        }
				    });
				}
			});
			
			// 导出一览
			$("#exportDtl").on("click", function() {
	            // 异步数据提交
	            $.ajax({
	                type: "post",
			        async: false,
	                url: "${ctx}" + "/sd/soOrder/smStorageInfo/valid",
	                data: $("#inputForm").serialize(),
	                success: function(oData, oStatus) {
	                	if (oData.success) {
							$("#inputForm").attr("action","${ctx}/sd/soOrder/smStorageInfo/exportDtl");
							$("#inputForm").submit();
							$("#inputForm").attr("action","${ctx}/sd/soOrder/smStorageInfo/save");
	                	}
	                },
	                error: function(oData, oStatus, eErrorThrow) {
	                }
	            });
			});
			
			// 提交申请以及保存
			$("#opt").on("click", function() {
				if (!$("#inputForm").valid()) {
					return false;
				} else {
		            // 异步数据提交
		            $.ajax({
		                type: "post",
				        async: false,
		                url: "${ctx}" + "/sd/soOrder/smStorageInfo/save",
		                data: $("#inputForm").serialize(),
		                success: function(oData, oStatus) {
		                	if (oData.success) {
		                		//$("#inputForm").submit();
		                		showMessage("操作成功");
		                		window.location.href="${ctx}/sd/soOrder/smStorageInfo/form";
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
	<form:form id="inputForm" modelAttribute="smStorageInfoSearch" action="${ctx}/sd/soOrder/smStorageInfo/save" method="post" class="form-search" >
			<form:hidden path="deliverId"/>
			<% boolean readonlyFlag = true; %>
			<c:if test="${!empty smStorageInfoSearch.smStorageInfoList}">
				<% readonlyFlag = false; %>
			</c:if>
			<h3 class="text-center page-title">合同出库</h3>
			<div class="group-box group-box-first" style="height:auto;">
			    <ul class="ul-form">
			        <li>
			            <label><span class="help-inline"><font color="red">*</font></span>合同编号：</label>
			            <form:input path="orderNo" class="input-medium required" type="text" />
			        </li>
			        <li>
			            <label>签约方：</label>
			            <input class="input-medium" type="text" value="${smStorageInfoSearch.customerName}" readonly/>
			        </li>
		            <li class="btns">
						<input id="search" class="btn btn-primary" type="submit" value="查找" onclick="javascript:this.form.action='${ctx}/sd/soOrder/smStorageInfo/search';">
						<% if (readonlyFlag) { %>
							<input id="exportDtl" type="button" class="btn btn-primary" value="一览导出" disabled="disabled">
						<% } else { %>
							<input id="exportDtl" type="button" class="btn btn-primary" value="一览导出">
						<% } %>
					</li>
					<li class="clearfix">
					</li>
					<li></li>
			    </ul>
			</div>
			<div class="group-box" style="height:auto;">
				<div style="overflow-x:scroll;">
					<table id="smStorageInfoDtlTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="text-center">No</th>
								<th>最终客户</th>
								<th>套餐名称</th>
								<th>物料号</th>
								<th>物料名称</th>
								<th>发货数量</th>
								<th>要求发货日</th>
								<th>联系人</th>
								<th>联系方式</th>
								<th>收件地址</th>
								<th>快递编号</th>
								<th>快递公司</th>
								<th>S/N</th>
								<th>生产日期</th>
								<th>所在库房</th>
								<th>出库日期</th>
								<th>是否需要安装</th>
								<th>最晚安装日期</th>
							</tr>
						</thead>
						<tbody>
						    <c:choose>
						        <c:when test="${!empty smStorageInfoSearch.smStorageInfoList}">
						            <c:forEach var="item" items="${smStorageInfoSearch.smStorageInfoList}" varStatus="status">
									    <tr>
								           <form:hidden path="smStorageInfoList[${status.index}].orderNo"/>
								           <form:hidden path="smStorageInfoList[${status.index}].orderLineNo"/>
								           <form:hidden path="smStorageInfoList[${status.index}].ifSn"/>
								           <form:hidden path="smStorageInfoList[${status.index}].ifInstall"/>
								           <form:hidden path="smStorageInfoList[${status.index}].deliverNum"/>
								           <form:hidden path="smStorageInfoList[${status.index}].materialNo" class="materialNo"/>
								           <form:hidden path="smStorageInfoList[${status.index}].period"/>
								           
								           <form:hidden path="smStorageInfoList[${status.index}].customerName"/>
								           <form:hidden path="smStorageInfoList[${status.index}].materialName"/>
								           <form:hidden path="smStorageInfoList[${status.index}].packageMertiralNo"/>
								           <form:hidden path="smStorageInfoList[${status.index}].packageMertiralName"/>
								           <form:hidden path="smStorageInfoList[${status.index}].materialType"/>
								           <form:hidden path="smStorageInfoList[${status.index}].expectDate"/>
								           <form:hidden path="smStorageInfoList[${status.index}].deliverContactsName"/>
								           <form:hidden path="smStorageInfoList[${status.index}].deliverTelephone"/>
								           <form:hidden path="smStorageInfoList[${status.index}].deliverAddress"/>
									    	<td class="text-center rowNo">
									           ${status.index + 1}
									        </td>
									        <td>${item.customerName}</td>
									        <td>${item.packageMertiralName}</td>
										    <td>${item.materialNo}</td>
										    <td>${item.materialName}</td>
										    <td class="text-right">${item.deliverNum}</td>
										    <td><fmt:formatDate value="${item.expectDate}" pattern="yyyy-MM-dd"/></td>
										    <td>${item.deliverContactsName}</td>
										    <td>${item.deliverTelephone}</td>
										    <td>${item.deliverAddress}</td>
										    <td><input name="smStorageInfoList[${status.index}].expressNo" type="text" maxlength="50" class="input-medium" value="${item.expressNo}"/></td>
										    <td><input name="smStorageInfoList[${status.index}].expressCompany" type="text" maxlength="100" class="input-medium" value="${item.expressCompany}"/></td>
										    <td>
										    	<c:if test="${item.ifSn == '1'}">
										    		<input name="smStorageInfoList[${status.index}].snNo" type="text" maxlength="50" class="input-medium snNo" value="${item.snNo}"/>
										    	</c:if>
										    	<c:if test="${item.ifSn == '0'}">
										    		<input name="smStorageInfoList[${status.index}].snNo" type="text" class="input-medium" value="" readonly/>
										    	</c:if>
										    </td>
										    <td><input name="smStorageInfoList[${status.index}].productionDate" type="text" class="input-medium productionDate" readonly/></td>
										    <td>
										    	<c:if test="${item.ifSn == '1'}">
										    		<form:hidden path="smStorageInfoList[${status.index}].warehouse" class="warehouse"/>
										            <select class="input-medium disWarehouse" disabled="disabled">
										                <option value=""></option>
															<c:forEach items="${fns:getDictList('DM0050')}" var="items">
																<option value="${items.value}">${items.label}</option>
															</c:forEach>
										            </select>
										    	</c:if>
										    	<c:if test="${item.ifSn == '0'}">
										            <form:select path="smStorageInfoList[${status.index}].warehouse" class="input-medium">
										                <form:option value="" label=""/>
										                <form:options items="${fns:getDictList('DM0050')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
										            </form:select>
										    	</c:if>
					            			</td>
										    <td><input name="smStorageInfoList[${status.index}].processDate" type="text" value="<fmt:formatDate value="${item.processDate}" pattern="yyyy-MM-dd"/>" readonly class="input-medium Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/></td>
										    <td>
										      	<select name="smStorageInfoList[${status.index}].ifInstall"  class="input-medium" disabled="disabled">
										       		<option value=""></option>
													<c:forEach items="${fns:getDictList('yes_no')}" var="ifInstalls">
														<option value="${ifInstalls.value}" ${ifInstalls.value==item.ifInstall?'selected':''}>${ifInstalls.label}</option>
													</c:forEach>
										       	</select>
					            			</td>
										    <td>
										    	<c:if test="${item.ifInstall == '1'}">
										    		<input name="smStorageInfoList[${status.index}].latestInstallDate" type="text" value="<fmt:formatDate value="${item.latestInstallDate}" pattern="yyyy-MM-dd"/>" readonly class="input-medium Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
										    	</c:if>
										    	<c:if test="${item.ifInstall == '0'}">
										    		<input name="smStorageInfoList[${status.index}].latestInstallDate" type="text" value="<fmt:formatDate value="${item.latestInstallDate}" pattern="yyyy-MM-dd"/>" readonly class="input-medium"/>
										    	</c:if>
										    </td>
									    </tr>
						            </c:forEach> 
						        </c:when>
						    </c:choose>
						</tbody>	
					</table>
				</div>
			</div>
			<div class="group-box">
				<div class="text-center">
					<% if (readonlyFlag) { %>
						<input id="opt" name="opt" type="button" class="btn btn-primary" value="确认出库" disabled="disabled">
					<% } else { %>
						<input id="opt" name="opt" type="button" class="btn btn-primary" value="确认出库">
					<% } %>
				</div>
			</div>
		</form:form>
	<sys:message content="${message}"/>
</body>
</html>