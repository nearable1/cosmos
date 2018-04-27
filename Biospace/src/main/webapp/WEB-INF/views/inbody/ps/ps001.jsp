<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>价格查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate({
				//submitHandler: function(form){
				//	loading('正在查询，请稍等...');
				//	form.submit();
				//},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			getAgreementIds();
			if($("#selectAgreementPatiesId").val() != ""){
				$("#agreementPatiesId").select2('val',$("#selectAgreementPatiesId").val());
			}
			
			function getAgreementIds()
			{
				// 组下拉框信息取得
				$.ajax({
			    	url: "${ctx}" + "/ps/psApplyPrice/getProtocols",
			        type: "get",
			        async: false,
			        data: {"priceSystem":""},
			        //dataType: "json",
			        success: function (data) {
			        	$("#agreementPatiesId").empty();
			        	$("#agreementPatiesId").append("<option value=''></option>");
			        	
			            for (var i = 0; i < data.length; i++) {
			            	if(data[i].value != null && data[i].value != ''){
			            		
			            		if(data[i].vaue == $("#selectAgreementPatiesId").val()){
			            			$("#agreementPatiesId").append("<option value='" + data[i].value + "' selected='selected'>" + data[i].label + "</option>");
				        			$("#agreementPatiesId").val(data[i].value);
			            		} else {
			            			$("#agreementPatiesId").append("<option value='" + data[i].value + "'>" + data[i].label + "</option>");
			            		}
			            	}
			            }
	        			$("#agreementPatiesId").trigger("change");
			        },
			        error: function (msg) {
			        }
			    });
			}
			
			//协议方change事件绑定
			$("#agreementPatiesId").change(function() {
				var $agreementPatiesId = $(this);
				var agreementPatiesId = $agreementPatiesId.val();
				
				$("#selectAgreementPatiesId").val(agreementPatiesId);
			});
		});
		function page(n,s){
			$("#searchForm").attr("action","${ctx}/ps/psApplyPrice/");
			$("#searchForm").attr("method","GET"); 
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function doExport(){
			$("#searchForm").attr("action","${ctx}/ps/psApplyPrice/searchExport");
			$("#searchForm").attr("method","GET"); 
			$("#searchForm").submit();
		}
		function doQuery(){
			$("#searchForm").attr("action","${ctx}/ps/psApplyPrice/");
			$("#searchForm").attr("method","GET"); 
			loading('正在查询，请稍等...');
			$("#searchForm").submit();
		}
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ps/psApplyPrice/">价格查询</a></li>
		<shiro:hasPermission name="ps:psApplyPrice:edit"><li><a href="${ctx}/ps/psApplyPrice/form">价格申请</a></li></shiro:hasPermission>
	</ul> --%>
	<h3 class="text-center page-title">价格查询</h3>
	<form:form id="searchForm" modelAttribute="psApplyPriceDtl" action="${ctx}/ps/psApplyPrice/" method="get" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>物料名称：</label>
				<form:input path="materialName" class="input-small" type="text" maxlength="300"/>
			</li>
			<li>
				<label>销售模式：</label>
				<form:select path="priceSystem" onchange="priceSystemChange()" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DM0020')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<label>协议方：</label>
				<form:select path="agreementPatiesId" class="input-large">
					<form:option value="" label=""/>
				</form:select>
			</li>
			<li>
				<form:input path="selectAgreementPatiesId" type="hidden"/>
				<label>申请人：</label>
				<form:input path="createBy.id" class="remote employee input-medium required" type="text" data-show="text" data-type="10"/>
				<!-- 
				<form:select path="createBy.id" class="input-small">
					<form:option value="" label=""/>
					<c:forEach items="${fns:getUserList()}" var="user">
						<form:option value="${user.id }">${user.name} </form:option>
					</c:forEach>
				</form:select>
				-->
			</li>
		</ul>
		<br/>
		<div class="text-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="doQuery()"/>
			<input id="btnClear" class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/ps/psApplyPrice';"/>
			<input id="btnExport" class="btn btn-primary" type="button" value="一览导出" onclick="doExport()"/>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>物料号</th>
				<th>物料名称</th>
				<th>销售方式</th>
				<th>协议方 </th>
				<th>地区 </th>
				<th>行业 </th>
				<th>单价</th>
				<th>申请人</th>
				<th>有效开始日</th>
				<th>有效终止日</th>
			</tr>
		</thead>
		<tbody id="psApplyPriceDtlList">
		<c:forEach items="${page.list}" var="psApplyPriceDtl">
			<tr>
				<td>${psApplyPriceDtl.materialNo}</td>
				<td>${psApplyPriceDtl.materialName}</td>
				<td>${psApplyPriceDtl.priceSystemName}</td>
				<td>${psApplyPriceDtl.cutomerName}</td>
				<td>${psApplyPriceDtl.regionName}</td>
				<td>${psApplyPriceDtl.industryName}</td>
				<td class="text-right "><fmt:formatNumber value="${psApplyPriceDtl.unitPrice}" pattern="#,##0.00"/></td>
				<td>${psApplyPriceDtl.createBy.name}</td>
				<td>${psApplyPriceDtl.startDays}</td>
				<td>${psApplyPriceDtl.endDays}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>