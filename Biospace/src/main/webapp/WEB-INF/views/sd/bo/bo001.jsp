<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商机录入</title>
	<meta name="decorator" content="default"/>
    
    <style type="text/css">
    .form-search .ul-form li{
        width:280px;
    }
    </style>
    <script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					$.ajax({
				    	url: "${ctx}/sd/boBusinessOpp/checkFormData",
				        type: "get",
				        async: false,
				        data: $("#inputForm").serializeArray(), //自动将form表单封装成json
				        dataType: "json",
				        success: function (data) {
				        	if(data.error != null && data.error == "true"){
				        		showError(data.errorMessage);
				        	}else{
								form.submit();
				        	}
				        },
				        error: function (msg) {
				        	showError(msg);
				        }
				    });
					//closeLoading();
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
			
			$("#priceSystem").change(function(){
				// 价格体系为 经销商时， 新建代理商经销商的按钮可以用
				var saleTypeValue = $("#priceSystem").val();
				if(saleTypeValue == 4 || saleTypeValue == 5){
					$("#newAgentInfo").show();
				}else{
					$("#newAgentInfo").hide();
				}
				// 价格体系，最终客户（或者经销商），物料号 三个值变更时，重新取得明细中的标准价
				getNewPrice();
			});
			
			// 转为销售计划变更事件
			$("#ifSalesPlan").on("change", function() {
				var $ifSalesPlan = $(this);
				var ifSalesPlan = $ifSalesPlan.val();
				var ifSalesPlanOld = $("#ifSalesPlanOld").val();
				if (ifSalesPlanOld == '1' && ifSalesPlan == '0') {

					$("#ifFail").rules("add", { required: true});
					$("#failRemarks").rules("add", { required: true});
				} else {
					$("#ifFail").rules("remove");
					$("#failRemarks").rules("remove");
		        	$("#ifFail").val("");
		        	$("#ifFail").trigger("change");
		        	$("#failRemarks").val("");
				}
				//$("#ifSalesPlanOld").val(ifSalesPlan);
			});
			
			//最终客户选择时
			$("#endCustomerId").change(function(){
				
				// 设置行业地区的值，根据行业的值更新科室/系的内容。
				if ($("#endCustomerId").val() == "") {
		        	$("#industry").val("");
		        	$("#industry").trigger("change");
		        	$("#area").val("");
		        	$("#area").trigger("change");

		        	$("#customerSegmentation").val("");
		        	$("#customerSegmentation").trigger("change");
		        	$("#customerSegmentation").attr("disabled","true");
		        	$("#contactsName").val("");
		        	$("#contactsName").attr("readOnly","true");
		        	$("#position").val("");
		        	$("#position").attr("readOnly","true");
		        	$("#telephone").val("");
		        	$("#telephone").attr("readOnly","true");
		        	$("#email").val("");
		        	$("#email").attr("readOnly","true");
		        	$("#address").val("");
		        	$("#address").attr("readOnly","true");
				} else {
					$.ajax({
				    	url: "${ctx}" + "/sd/boBusinessOpp/getCustomerInfo",
				        type: "get",
				        async: false,
				        data: {"customerId":$("#endCustomerId").val()},
				        dataType: "json",
				        success: function (data) {
				        	var customerInfo = data.customerInfo;
				        	$("#area").val(customerInfo.region);
				        	$("#area").trigger("change");
				        	$("#industry").val(customerInfo.industry);
				        	$("#industry").trigger("change");
				        	getDictChildList('${ctx}',"customerSegmentation","DM0002_"+customerInfo.industry,"DM0039");

				        	$("#customerSegmentation").removeAttr("disabled");
				        	$("#contactsName").removeAttr("readOnly");
				        	$("#position").removeAttr("readOnly"); 
				        	$("#telephone").removeAttr("readOnly"); 
				        	$("#email").removeAttr("readOnly"); 
				        	$("#address").removeAttr("readOnly"); 
				        },
				        error: function (msg) {
				        }
				    });
				}
				// 价格体系，最终客户（或者经销商），物料号 三个值变更时，重新取得明细中的标准价
				getNewPrice();
			});
			
			//代理商变更事件
			$("#agentId").change(function(){
				
				// 设置行业地区的值，根据行业的值更新科室/系的内容。
				if ($("#agentId").val() == "") {

		        	$("#agentContactsName").val("");
		        	$("#agentContactsName").attr("readOnly","true");
		        	$("#agentPosition").val("");
		        	$("#agentPosition").attr("readOnly","true");
		        	$("#agentTelephone").val("");
		        	$("#agentTelephone").attr("readOnly","true");
		        	$("#agentEmail").val("");
		        	$("#agentEmail").attr("readOnly","true");
		        	$("#agentAddress").val("");
		        	$("#agentAddress").attr("readOnly","true");
				} else {
		        	
		        	$("#agentContactsName").removeAttr("readOnly");
		        	$("#agentPosition").removeAttr("readOnly"); 
		        	$("#agentTelephone").removeAttr("readOnly"); 
		        	$("#agentEmail").removeAttr("readOnly"); 
		        	$("#agentAddress").removeAttr("readOnly"); 
				}
				// 价格体系，最终客户（或者经销商），物料号 三个值变更时，重新取得明细中的标准价
				getNewPrice();
			});

			
			//代理商变更事件
			/* $(".ifFail").change(function() {
				$("#failRemarks").removeClass("required");
				if ($(".ifFail").is(':checked')) {
					$("#failRemarks").rules("add", { required: true});
				} else {
					$("#failRemarks").rules("remove");
				}
			}); */
		});

		// 价格体系，最终客户（或者经销商），物料号 三个值变更时，重新取得明细中的标准价
		function getNewPrice(){
			var customerId = null;
			var priceSystem = $("#priceSystem").val();
			if (priceSystem == "1" || priceSystem == "5") {
				customerId = $("#agentId").val();
			} else if (priceSystem == "3") {
				customerId = $("#endCustomerId").val();
			}
			
			$("#boBusinessOppDtlList tr").each(function(){
				var rowId = "#"+$(this).attr('id');
				var materialNo = $(rowId+"_materialNo").val();
				var standardPrice = getStandardPrice(priceSystem, customerId, materialNo, $("#area").val(), $("#industry").val());
	        	if(standardPrice != null && standardPrice != ''){
	        		var unitPrice = $(rowId+"_unitPrice").val();
	        		unitPrice = Number(unitPrice).toFixed(2);
	        	    if(Number(unitPrice) < Number(standardPrice)){
		        		$(rowId+"_standardPrice").val(standardPrice);
						$(rowId+"_ifSpecialOfferLabel").text("特价");
						$(rowId+"_ifSpecialOffer").val("1");
	        	    }else{
		        		$(rowId+"_standardPrice").val(standardPrice);
						$(rowId+"_ifSpecialOfferLabel").text("标准价");
						$(rowId+"_ifSpecialOffer").val("0");
					}
	        	}else{
					$(rowId+"_standardPrice").val(0);
					$(rowId+"_ifSpecialOfferLabel").text("特价");
					$(rowId+"_ifSpecialOffer").val("1");
	        	}
				
			});
		}
		
		$(document).on("change", "#contentTable input.materialNo",function(e){
			var materialNo = $(this).val();
			// 取得控件的ID
			var materialId = $(this).attr('id');
			var rowId = materialId.split('_')[0];
			rowId = "#"+rowId;
			$(rowId+"_model").text(e.added.model);
			var customerId = null;
			var priceSystem = $("#priceSystem").val();
			if (priceSystem == "1" || priceSystem == "5") {
				customerId = $("#agentId").val();
			} else if (priceSystem == "3") {
				customerId = $("#endCustomerId").val();
			}

			var standardPrice = getStandardPrice(priceSystem, customerId, materialNo, $("#area").val(), $("#industry").val());
        	if(standardPrice != null && standardPrice != ''){
        		$(rowId+"_unitPrice").val(standardPrice);
				$(rowId+"_standardPrice").val(standardPrice);
        		$(rowId+"_unitPriceFix").val(toThousands(standardPrice));
				$(rowId+"_ifSpecialOfferLabel").text("标准价");
				$(rowId+"_ifSpecialOffer").val("0");
	    		$(rowId+"_num").val(1);
	    		$(rowId+"_totalAmount").val(standardPrice);
        		$(rowId+"_totalAmountFix").val(toThousands(standardPrice));
        	}else{
        		$(rowId+"_unitPrice").val(0);
				$(rowId+"_standardPrice").val(0);
				$(rowId+"_ifSpecialOfferLabel").text("特价");
				$(rowId+"_ifSpecialOffer").val("1");
	    		$(rowId+"_num").val(1);
	    		$(rowId+"_totalAmount").val(0);
        		$(rowId+"_totalAmountFix").val(0);
        	}
		});
		
		// 点击明细部的新增按钮，新增一条记录。
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			$(list + idx + "_materialNo").select2(getMatSelectOption());
       		$(".rowno").each(function(index){ 
    			$(this).html(index+1); 
   			});
       		
       		$(list + idx + "_unitPriceFix").blur();
       		$(list + idx + "_totalAmountFix").blur();
		}

		// 点击明细部的删除按钮，删除一条记录。
		$(document).on("click", "table .delRow", function() {
            var $delRow = $(this).parent().parent();
            // 销毁物料号下拉
            var $sel2 = $delRow.find(".remote");
            if ($sel2.length > 0) {
                $sel2.select2("destroy");
            }
			$delRow.remove();

       		var $tbody = $(this).parents("tbody");
			$("#boBusinessOppDtlList").find("tr").each(function(index){
				$(this).find("td>input").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {
						$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
					}
				});
				$(this).find("td>label").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {
						$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
					}
                });
				$(this).find("td>select").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {
						$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
					}
                });
   			});
       		$(".rowno").each(function(index){ 
    			$(this).html(index+1); 
   			});
		});
		
		//数量或者单价变更后，重新计算总金额。
		//单价变更的时候，如果低于标准价，显示特价。其他，显示标准价。
		function totalAmount(type,idx){
			var num = $("#boBusinessOppDtlList"+idx+"_num").val();
			var unitPrice = $("#boBusinessOppDtlList"+idx+"_unitPriceFix").val();
			if (unitPrice == null || unitPrice == "") {
				unitPrice = "0";
			}
			unitPrice = unitPrice.trim().replace(/,/g, "");
    		unitPrice = Number(unitPrice).toFixed(2);
    		$("#boBusinessOppDtlList"+idx+"_unitPrice").val(unitPrice)
			var standardPrice = $("#boBusinessOppDtlList"+idx+"_standardPrice").val();
			$("#boBusinessOppDtlList"+idx+"_totalAmount").val(num*unitPrice);
			$("#boBusinessOppDtlList"+idx+"_totalAmountFix").val(toThousands($("#boBusinessOppDtlList"+idx+"_totalAmount").val()));
			
			if(type == 1){
				if(Number(standardPrice) == 0 || Number(unitPrice) < Number(standardPrice)){
					$("#boBusinessOppDtlList"+idx+"_ifSpecialOfferLabel").text("特价");
					$("#boBusinessOppDtlList"+idx+"_ifSpecialOffer").val("1");
				}else{
					$("#boBusinessOppDtlList"+idx+"_ifSpecialOfferLabel").text("标准价");
					$("#boBusinessOppDtlList"+idx+"_ifSpecialOffer").val("0");
				}
			}
		}
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="boBusinessOpp" action="${ctx}/sd/boBusinessOpp/save" method="get" class="form-search">
	<form:input path="id" type="hidden"/>
	<form:input path="businessOppNo" type="hidden"/>
	<input name="updateDate" id="updateDate" type="hidden" value="<fmt:formatDate value="${boBusinessOpp.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
	<h3 class="text-center page-title">商机录入</h3>
	<div class="group-box group-box-first">
	<ul class="ul-form">
		<li>
			<label><span class="help-inline"><font color="red">*</font></span>来源：</label>
			<form:select path="customerSource" class="input-medium required" style="width:150px;">
				<form:option value=""></form:option>
				<form:options items="${fns:getDictList('DM0006')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select>
		</li>
		<li>
		    <label><span class="help-inline"><font color="red">*</font></span>销售方式：</label>
		    <form:select path="priceSystem" style="width:150px;" class="input-medium required">
				<form:option value=""></form:option>
				<form:options items="${fns:getDictList('DM0005')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select>
		</li>
        <li>
			<label>销售员：</label>
			<form:input path="responsiblePersonName" value="${boBusinessOpp.responsiblePersonName}" 
			    style="width:150px;" disabled="true" maxlength="50" class="required" />
			<form:hidden path="responsiblePersonId"/>
		</li>
		<li>
	        <label>组：</label>
		    <form:select path="organize" style="width:150px;"  class="input-medium">
				<form:option value=""></form:option>
				<form:options items="${fns:getSqlDictList('dict_organize')}" itemLabel="label" itemValue="value" htmlEscape="false" />
	        </form:select>
		</li>
		<li style="width:560px;">
		    <label>最终客户：</label>
			<input id="endCustomerId" name="endCustomerId" type="text" value="${boBusinessOpp.endCustomerId}"
			       data-show="text" style="width:350px;" data-type="1" class="remote customer customerId input-medium"/>
			<a href="${ctx}/cm/cm001/newAndEdit?ifFromBusiness=1&customerType=1" id="newEndCustomerInfo" target="_blank" style="margin-left:10px;">
                <img border="0" src="../../../static/images/addCustomer.png" height="30px" width="30px"/>
            </a>
		</li>
		<li>
			<label>地区：</label>
			<form:select path="area"  style="width:150px;"  disabled="true"  class="input-medium">
				<form:option value=""></form:option>
				<form:options items="${fns:getDictList('DM0049')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</li>
		<li>
		    <label>行业：</label>
		    <form:select path="industry" style="width:150px;" disabled="true" class="input-medium">
				<form:option value=""></form:option>
				<form:options items="${fns:getDictList('DM0002')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</li>
		<c:if test="${!empty boBusinessOpp.endCustomerId}">
		<li>
			<label>科室/系：</label>
			<form:select path="customerSegmentation"  style="width:150px;" class="input-medium">
				<%-- <form:options items="${fns:getDictList('DM0039')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			     --%>
			    <form:option value=""></form:option>
			    <form:options items="${fns:getDictListByParentId(boBusinessOpp.industryCode,'DM0039')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select>
		</li>
		<li>
			<label>联系人：</label>
			<form:input path="contactsName" style="width:150px;" type="text" maxlength="100"/>
		</li>
		<li>
			<label>职位：</label>
			<form:input path="position" type="text" style="width:150px;" maxlength="100"/>
		</li>
		<li>
			<label>电话：</label>
			<form:input path="telephone" type="text" class="input-medium phone valid" style="width:150px;" maxlength="50"/>
		</li>
		<li>
			<label>邮件：</label>
			<form:input path="email" type="email" style="width:150px;" maxlength="100"/>
		</li>
		<li>
			<label>地址：</label>
			<form:input path="address" type="text" style="width:150px;" maxlength="300"/>
		</li>
		</c:if>
		<c:if test="${empty boBusinessOpp.endCustomerId}">
		<li>
			<label>科室/系：</label>
			<form:select path="customerSegmentation"  style="width:150px;" class="input-medium" disabled="true">
				<%-- <form:options items="${fns:getDictList('DM0039')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			     --%>
			    <form:option value=""></form:option>
			    <form:options items="${fns:getDictListByParentId(boBusinessOpp.industryCode,'DM0039')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select>
		</li>
		<li>
			<label>联系人：</label>
			<form:input path="contactsName" style="width:150px;" type="text" maxlength="100" readonly="true"/>
		</li>
		<li>
			<label>职位：</label>
			<form:input path="position" type="text" style="width:150px;" maxlength="100" readonly="true"/>
		</li>
		<li>
			<label>电话：</label>
			<form:input path="telephone" type="text" class="input-medium phone valid" style="width:150px;" maxlength="50" readonly="true"/>
		</li>
		<li>
			<label>邮件：</label>
			<form:input path="email" type="email" style="width:150px;" maxlength="100" readonly="true"/>
		</li>
		<li>
			<label>地址：</label>
			<form:input path="address" type="text" style="width:150px;" maxlength="300" readonly="true"/>
		</li>
		</c:if>
		<li class="clearfix"></li>
		<li style="width:560px;">
		    <label>代理商/经销商：</label>
			<input id="agentId" name="agentId" type="text" value="${boBusinessOpp.agentId}" maxlength="50"
			       data-show="text" style="width:350px;" data-type="2,3" class="remote customer customerId input-medium"/>
			<a href="${ctx}/cm/cm001/newAndEdit?ifFromBusiness=1&customerType=3" id="newAgentInfo" target="_blank" style="display:none;margin-left:10px;">
                <img border="0" src="../../../static/images/addCustomer.png" height="30px" width="30px"/></a>
		</li>
		<c:if test="${!empty boBusinessOpp.agentId}">
		<li>
			<label>联系人：</label>
			<form:input path="agentContactsName" style="width:150px;" type="text" maxlength="100"/>
		</li>
		<li>
			<label>职位：</label>
			<form:input path="agentPosition" type="text" style="width:150px;" maxlength="100"/>
		</li>
		<li>
			<label>电话：</label>
			<form:input path="agentTelephone" type="text" class="input-medium phone valid" style="width:150px;" maxlength="50"/>
		</li>
		<li>
			<label>邮件：</label>
			<form:input path="agentEmail" type="email" style="width:150px;" maxlength="100"/>
		</li>
		<li>
			<label>地址：</label>
			<form:input path="agentAddress" type="text" style="width:150px;" maxlength="300"/>
		</li>
		</c:if>
		<c:if test="${empty boBusinessOpp.agentId}">
		<li>
			<label>联系人：</label>
			<form:input path="agentContactsName" style="width:150px;" type="text" maxlength="100" readonly="true"/>
		</li>
		<li>
			<label>职位：</label>
			<form:input path="agentPosition" type="text" style="width:150px;" maxlength="100" readonly="true"/>
		</li>
		<li>
			<label>电话：</label>
			<form:input path="agentTelephone" type="text" class="input-medium phone valid" style="width:150px;" maxlength="50" readonly="true"/>
		</li>
		<li>
			<label>邮件：</label>
			<form:input path="agentEmail" type="email" style="width:150px;" maxlength="100" readonly="true"/>
		</li>
		<li>
			<label>地址：</label>
			<form:input path="agentAddress" type="text" style="width:150px;" maxlength="300" readonly="true"/>
		</li>
		</c:if>
		<li class="clearfix"></li>
		<li>
			<label><span class="help-inline"><font color="red">*</font></span>预计成交率：</label>
			<form:select path="expTurnover"  style="width:150px;" class="input-medium required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('DM0040')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select>
		</li>
		<li>
			<label><span class="help-inline"><font color="red">*</font></span>预计成交月：</label>
			<input name="expTurnoverMonth" style="width:150px;" class="input-medium Wdate required" 
			       onclick="WdatePicker({dateFmt:'yyyy-MM'});" type="text" maxlength="20"
			       value="<fmt:formatDate value="${boBusinessOpp.expTurnoverMonth}" pattern="yyyy-MM"/>" />
		</li>
		<li>
		    <label>转为销售计划：</label>
		    <input name="ifSalesPlanOld" id="ifSalesPlanOld" type="hidden" value="${boBusinessOpp.ifSalesPlan}" />
		    <form:select path="ifSalesPlan"  style="width:100px;" class="input-medium">
		        <form:option value=""/>
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select>
		</li>
		<li>
			<label>计划撤回原因：</label>
		    <%-- <c:choose>
		    <c:when test="${boBusinessOpp.ifFail == '1'}">
			<form:checkbox path="ifFail" value="1" checked="checked" class="ifFail"/>
		    </c:when>
		    <c:otherwise>
		    <form:checkbox path="ifFail" value="1" class="ifFail"/>
		    </c:otherwise>
		    </c:choose> --%>
		    <form:select path="ifFail" class="input-small">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('DM0063')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</li>
		<li style="width:560px;">
			<label>商机备注：</label>
			<form:textarea path="newRemarks" style="width:430px;resize:none;"
				    maxlength="300" rows="5"/>
		</li>
		<li style="width:560px;">
			<label>计划撤回备注：</label>
			<form:textarea path="failRemarks" style="width:430px;resize:none;"
				    maxlength="300" rows="5"/>
		    <%-- <c:choose>
		    <c:when test="${boBusinessOpp.ifFail == '1'}">
			<form:textarea path="failRemarks" style="width:340px;resize:none;"
				    maxlength="300" rows="5" class="required"/>
		    </c:when>
		    <c:otherwise>
			<form:textarea path="failRemarks" style="width:340px;resize:none;"
				    maxlength="300" rows="5"/>
		    </c:otherwise>
		    </c:choose> --%>
		</li>
		<li>
		</li>
	</ul>
	</div>
	<div class="group-box">
			<table id="contentTable" class="table table-striped table-bordered table-condensed" >
			       <!-- style="margin-left:10px;width:1100px;"> -->
				<thead>
					<tr>
						<th class="hide"></th>
						<th class="text-center" style="width:10px;">
                            <a href="javascript:"
		                       onclick="addRow('#boBusinessOppDtlList', boBusinessOppDtlRowIdx, boBusinessOppDtlTpl);boBusinessOppDtlRowIdx = boBusinessOppDtlRowIdx + 1;">
                            <i class="icon-plus-sign"></i></a>
                        </th>
						<th style="width:50px;">行号</th>
						<th style="width:180px;">物料号</th>
						<th style="width:220px;">型号</th>
						<th style="width:70px;">数量</th>
						<th style="width:100px;">单价</th>
						<th style="width:150px;">总金额</th>
						<th style="width:100px;"></th>
					</tr>
				</thead>
				<tbody id="boBusinessOppDtlList">
				</tbody>
			</table>
		<script type="text/template" id="boBusinessOppDtlTpl">
						<tr id="boBusinessOppDtlList{{idx}}">
							<td class="hide">
							</td>
                            <td class="text-center" style="width:10px;">
                                <a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i>
							</td>
							<td style="width:50px;" class="rowno">{{row.lineNo}}</td>
							<td style="width:180px;">
                                <input  id="boBusinessOppDtlList{{idx}}_materialNo" name="boBusinessOppDtlList[{{idx}}].materialNo" style="width:90%;"
                                        type="text" value="{{row.materialNo}}" class="remote materialNo required" data-type="1,3,4,5,6"/>
							</td>
							<td style="width:220px;">
                                <label id="boBusinessOppDtlList{{idx}}_model" name="boBusinessOppDtlList[{{idx}}].model" style="width:90%;" >{{row.model}}</label>
							</td>
							<td style="width:70px;">
								<input id="boBusinessOppDtlList{{idx}}_num" name="boBusinessOppDtlList[{{idx}}].num" type="text" value="{{row.num}}" maxlength="3" style="width:90%;" class="required number" onchange="totalAmount(0,{{idx}})"/>
							</td>
							<td style="width:100px;">
								<input id="boBusinessOppDtlList{{idx}}_unitPrice" name="boBusinessOppDtlList[{{idx}}].unitPrice" type="hidden" value="{{row.unitPrice}}" style="width:90%;" class="required number"/>
								<input id="boBusinessOppDtlList{{idx}}_unitPriceFix" type="text" value="{{row.unitPrice}}" style="width:90%;" class="required" onblur="numToStr(this);" onfocus="strToNum(this);" onchange="totalAmount(1,{{idx}})"/>
                                <input id="boBusinessOppDtlList{{idx}}_standardPrice" name="boBusinessOppDtlList[{{idx}}].standardPrice" type="hidden" value="{{row.standardPrice}}"/>
							</td>
							<td style="width:150px;">
								<input id="boBusinessOppDtlList{{idx}}_totalAmount" name="boBusinessOppDtlList[{{idx}}].totalAmount" type="hidden" value="{{row.totalAmount}}" />
								<input id="boBusinessOppDtlList{{idx}}_totalAmountFix" type="text" value="{{row.totalAmount}}" style="width:90%;" onblur="numToStr(this);" onfocus="strToNum(this);" readonly/>
							</td>
							<td style="width:100px;">
								<input id="boBusinessOppDtlList{{idx}}_ifSpecialOffer" name="boBusinessOppDtlList[{{idx}}].ifSpecialOffer" type="hidden" value="{{row.ifSpecialOffer}}"/>
                                <label id="boBusinessOppDtlList{{idx}}_ifSpecialOfferLabel" name="boBusinessOppDtlList[{{idx}}].ifSpecialOfferLabel" style="width:90%;" >{{row.ifSpecialOfferLabel}}</label>
							</td>
						</tr>
					</script>
			<script type="text/javascript">
				var boBusinessOppDtlRowIdx = 0, boBusinessOppDtlTpl = $("#boBusinessOppDtlTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(boBusinessOpp.boBusinessOppDtlList)};
					for (var i=0; i<data.length; i++){
						addRow('#boBusinessOppDtlList', boBusinessOppDtlRowIdx, boBusinessOppDtlTpl, data[i]);
						boBusinessOppDtlRowIdx = boBusinessOppDtlRowIdx + 1;
					}
				});
			</script>
	</div>
    <div class="text-center" style="margin-top:8px;">
			<shiro:hasPermission name="sd:boBusinessOpp:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>