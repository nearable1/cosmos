<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>客户/代理商/经销添加修改</title>
<meta name="decorator" content="default" />
<style type="text/css">
		.upload-input {
	        display: none !important;
	    }
</style>
</head>

<body>
	<!-- <div style="padding: 5px 5px; border: 1px solid #ddd; width: 100%; margin-bottom: 5px;"> -->
		<!-- <h3 align="center">客户代理商经销信息</h3> -->
		<form:form id="searchForm" modelAttribute="customerManagement" action="${ctx}/cm/cm001/list" method="post" class="form-search" enctype="multipart/form-data">
		<% boolean agreementReadonlyFlag = true; %>
		<shiro:hasPermission name="cm:manager:edit">
			<% agreementReadonlyFlag = false; %>
		</shiro:hasPermission>
			<h3 class="text-center page-title">客户代理商经销信息</h3>
		<!-- <div style="padding: 5px 5px; border: 1px solid #ddd; width: 1100px; margin-bottom: 5px; margin: 0 auto;"> -->
			<!-- <h4>基本信息</h4> -->
		<div class="group-box group-box-first" style="height:auto;">
			<div class="group-header" >
				<strong class="group-title">基本信息</strong>
			</div>
				<input type="hidden" name="customerId" value="${customerManagement.customerId}"/>
				<ul class="ul-form">
					<li>
				        <label><span class="help-inline"><font color="red">*</font></span>类型：</label>
				        <c:if test="${empty customerManagement.ifFromBusiness}">
					        <form:select path="customerType" class="input-medium required">
								<option value=""> </option>
				            	<form:options items ="${fns:getDictList('DM0001')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
				        </c:if>
				        <c:if test="${customerManagement.ifFromBusiness eq '1'}">
							<input type="text" class="input-medium" value="${fns:getDictLabel(customerManagement.customerType, 'DM0001', '')}" disabled/>
							<form:hidden path="customerType"/>
				        </c:if>
				        </li>
					<li><label><span class="help-inline"><font color="red">*</font></span>名称：</label> 
						<form:input id="customerChName" path="customerChName" class="input-medium required" type="text" style="width:300px;"  maxlength="50"/>
					</li>
					<li><label><!-- <span class="help-inline"><font color="red">*</font></span> -->发展日期：</label>
						 <!-- <input name="developDate" style="width:150px;" class="input-medium Wdate required"  -->
						 <input name="developDate" style="width:150px;" class="input-medium Wdate" 
			       				onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text" maxlength="20"
			       				value="<fmt:formatDate value="${customerManagement.developDate}" pattern="yyyy-MM-dd"/>" />
					</li>
					
					<li class="clearfix">
					</li>
					<li><label><span class="help-inline"><font color="red">*</font></span>行业：</label> 
						<form:select path="industry"  class="input-medium required">
							<option value="" label=""></option>
							<form:options items="${fns:getDictList('DM0002')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</li>
					<li><label>具体分类：</label> 
					<c:choose>
					<c:when test="${customerManagement.customerType eq '1' && customerManagement.industry eq '1'}">
						<form:select path="customerDiff" class="input-medium">
							<option value="" label=""></option>
							<form:options items="${fns:getDictList('DM0003')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</c:when>
					<c:otherwise>
						<form:select path="customerDiff" class="input-medium" disabled="true">
							<option value="" label=""></option>
							<form:options items="${fns:getDictList('DM0003')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</c:otherwise>
					</c:choose>
					</li>

					<li><label><span class="help-inline"><font color="red">*</font></span>销售负责人：</label> 
						<form:input path="responsiblePersonId" class="remote employee input-medium required" type="text" data-show="text" data-type="10" onchange="getOffice();"/>
						<%-- <form:select path="responsiblePersonId" id="responsiblePersonId" class="input-medium required" onchange="getOffice();">
							<form:option value="" label="" />
							<form:options items="${fns:getSqlDictList('dict_employee')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select> --%>

					<li><label style="display: inline-block; width: 100px;">组：</label>
						<%-- <form:select path="officeId" style="width:130px;"  class="input-medium">
				    		<form:option value="" label="" />
				    		<form:options items="${fns:getSqlDictList('dict_organize')}" itemLabel="label" itemValue="value" htmlEscape="false" />
	            		</form:select> --%>
	            		<input type="hidden" name="officeId" id="officeId" value="${customerManagement.officeId}"/>
	            		<input type="text" name="officeName" id="officeName" value="${customerManagement.officeName}" class="input-medium" readonly />
					</li>

					<li><label>地区：</label> 
						<form:select path="region" class="input-medium" value="">
							<option value=""  label=""></option>
							<form:options items="${fns:getDictList('DM0049')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</li>
					
					<li><label>省市：</label> 
						<select id="province" name="province" class="input-medium">
							<option value='${customerManagement.province}'>${customerManagement.provinceName}</option>
						</select>
					</li>

					<li><label>城市：</label> 
						<select id="city" name="city" class="input-medium">
								<option value="${customerManagement.city}">${customerManagement.cityName}</option>
						</select>
						
					</li>
					<li><label>详细地址：</label> 
						<input id="address" name="address" class="input-medium" type="text" value="${customerManagement.address}"maxlength="50"/>
					</li>
					<li><label>总公司名称：</label> 
						<input id="customerParentCo" name="customerParentCo" value="${customerManagement.customerParentCo}" type="text" class="remote customer input-medium" data-show="text"/>
						<%-- <select id="customerParentCo" name="customerParentCo" class="input-medium">
							<option value= "${customerManagement.customerParentCo}">${customerManagement.customerParentCoName}</option>
						</select> --%>
					</li>
					<li><label style="display: inline-block; width: 100px;">最后拜访日期：</label>
						<input id="lastVisitDate" name="lastVisitDate"
						class="input-medium" type="text" value="<fmt:formatDate value="${customerManagement.lastVisitDate}" pattern="yyyy-MM-dd"/>"
						readonly="readonly" maxlength="50"></li>
					<li><label>AX编号：</label> 
						<form:input id="axCusNo" path="axCusNo" class="input-medium" type="text" maxlength="50"/>
					</li>
					<li><label style="display: inline-block; width: 120px;">
						<c:if test="${customerManagement.ifEffective == '1' || empty customerManagement.ifEffective}">
							<form:input path="ifEffective" type="hidden" value="1"/>
							<input type="checkbox" class="inline ifEffective" checked="checked"/>
						</c:if>
						<c:if test="${customerManagement.ifEffective == '0'}">
							<form:input path="ifEffective" type="hidden" value="0"/>
							<input type="checkbox" class="inline ifEffective"/>
						</c:if>
							<%-- <form:checkbox path="ifEffective" checked="checked"  value="1"  /> --%>有效
						 </label>
					</li>
					
					<li><label style="display: inline-block; width: 120px;">
						<form:checkbox path="ifImportant" value="1" />重点对象 </label></li>
				</ul>
			
		</div>

		<!-- <div style="padding: 5px 5px; border: 1px solid #ddd; width: 600px; margin-bottom: 5px; margin-left: 30px; margin-top: 30px"> -->
		<div class="group-box">
			<strong class="group-title">协议信息</strong>
				<table id="contentTable" style="margin-top: 10px" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<% if (!agreementReadonlyFlag) { %>
								<th class="text-center" width="20px"><a href="javascript:;" id="agreementBtn"><i class="icon-plus-sign"></i></a></th>
							<% } %>
							<!-- <th><a href="javascript:;" id="agreementBtn"><i class="icon-plus-sign"></i></a></th> -->
							<th>协议</th>
							<th>协议开始日期</th>
							<th>协议终止日期</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody Id="list">
						<c:if test="${!empty customerManagement.cmAgreementList}">
							<c:forEach  items="${customerManagement.cmAgreementList}" var="item" varStatus="status">
								<tr class="uploaded" id="agreement${item.id}">
									<% if (!agreementReadonlyFlag) { %>
									   	<td class="text-center" width="20px">
									       <a href="javascript:;" class="delFileRow" data-file-id="${item.id}"><i class="icon-minus-sign"></i></a>
									    </td>
									<% } %>
									<%-- <td>
									<a href="javascript:;" class="delFileRow" data-file-id="${item.id}">
									<i class="icon-minus-sign"></i></a>
									</td> --%>
									<td class="text-center rowNo" width="10"><span>${status.index+1}</span>
									</td>
									<% if (!agreementReadonlyFlag) { %>
										<td>
											<input  name="cmAgreementList[${status.index}].validityDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" value="${item.validityDateFrom }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
										</td>
										<td>
											<input name="cmAgreementList[${status.index}].validityDateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" value="${item.validityDateTo}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
										</td>
										<td>
											<input name="cmAgreementList[${status.index}].newRemarks" type="text" value="${item.newRemarks}" maxlength="50" class="input-small">
										</td>
									<% } else { %>
										<td>
											<input  name="cmAgreementList[${status.index}].validityDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium" value="${item.validityDateFrom }">
										</td>
										<td>
											<input name="cmAgreementList[${status.index}].validityDateTo" type="text" readonly="readonly" maxlength="20" class="input-medium" value="${item.validityDateTo}">
										</td>
										<td>
											<input name="cmAgreementList[${status.index}].newRemarks" type="text" value="${item.newRemarks}" maxlength="50" class="input-small" readonly="readonly">
										</td>
									<% } %>
							</tr>
							</c:forEach>
						</c:if>
						
					</tbody>
				</table>
		</div>

		<!-- <div style="padding: 5px 5px; border: 1px solid #ddd; width: 1100px; margin-bottom: 5px; margin-left: 30px; margin-top: 30px"> -->
		<div class="group-box">
			<strong class="group-title">开票类型</strong>
			
				<ul class="ul-form">
					<li><label>类型：</label> 
						<form:select path="invoiceType" class="input-medium">
							<option value=""></option>
							<form:options items="${fns:getDictList('DM0004')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</li>
					<li><label>发票抬头：</label> <input id="invoiceTitle" name="invoiceTitle" class="input-medium" type="text" value="${customerManagement.invoiceTitle}" maxlength="100">
					</li>
					<li><label>纳税识别号：</label> 
						<form:input id="taxpayerdentNo" path="taxpayerIdentNo" class="input-medium" type="text" value="${customerManagement.taxpayerIdentNo}" maxlength="50"/>
					</li>
					<li><label>开户银行：</label> 
						<form:input id="depositBank " path="depositBank" class="input-medium" type="text" value="${customerManagement.depositBank}" maxlength="100"/>
					</li>
					<li><label>银行帐号：</label> 
						<form:input id="bankAccount" path="bankAccount" class="input-medium" type="text" value="${customerManagement.bankAccount}" maxlength="100"/>
					</li>
					<li><label>开票地址：</label> 
						<form:input id="invoiceAddress"  path="invoiceAddress"  class="input-medium"  type="text" value="${customerManagement.invoiceAddress}" maxlength="100"/>
					</li>
					<li><label>电话：</label> 
						<form:input id="telephone" path="telephone" class="input-medium" type="text" value="${customerManagement.telephone}" maxlength="50"/>
					</li>
					<li><label>邮编：</label>
					    <form:input id="zipCode" path="zipCode" class="input-medium" type="text" value="${customerManagement.zipCode}" maxlength="6"/>
					 </li>
				</ul>
		</div>
		
		<input type="hidden" name="assid" value="${customerManagement.customerId}"/>
		<!-- <div class="group-box group-box-last" style="margin-left:30px;margin-top: 30px;padding: 5px 5px; border: 1px solid #ddd;width:450px;"> -->
		<div class="group-box">
			<div class="group-header">
				<strong class="group-title">相关资料上传</strong><span class="help-inline"><font color="red">必须上传公司三证与医疗器械经营许可证（最大上传文件大小：10M）</font></span>
			</div>
			<div class="upload-select-btn">
				<button type="button" class="btn btn-primary">
					<span>选取文件</span>
				</button>
				<input type="file" multiple="multiple" class="upload-input">
			</div>
			<table id="attachmentTbl" class="table table-striped table-bordered table-condensed" style="width: 50%;">
				<thead>
					<tr>
						<td class="text-center" width="20px"><a href="javascript:;" class="disabled"><i class="icon-plus-sign"></i></a></td>
						<td class="text-center" width="35px">No</td>
						<td>文件名</td>
					</tr>
				</thead>
				<tbody>
						<c:forEach items="${coAttachmentslist}" var="item" varStatus="status">
		                    <tr class="uploaded">
		                        <td class="text-center"><a href="javascript:;" class="delFileRow" data-file-id="${item.id}"><i class="icon-minus-sign"></i></a></td>
		                        <td class="text-center rowNo">${status.index + 1}</td>
		                        <td>
		                            <a href="${ctx}/cm/cm001/download/file/${item.id}">${item.fileName}.${item.fileType}</a>
		                            <span class="pull-right"><i class="icon-ok icon-white"></i></span>
		                        </td>
		                    </tr>
		                </c:forEach>
		                <c:if test="${empty coAttachmentslist}">
		                    <tr class="empty">
		                        <td class="text-center" colspan="3">请选择附件</td>
		                    </tr>
		                </c:if>
				</tbody>
			</table>
		</div>
		</form:form>
		<div class="text-center"
			style="border: 0px solid #ddd; width: 100%; height: auto; margin-bottom: 5px; margin-left: 0px; margin-top: 30px">
			<input id="btnSubmit" class="btn btn-primary" style="text-align: center;" type="button" value="保存">
		</div>
	<sys:message content="${message}" />

	<!-- </div> -->


<script type="text/javascript">
$(function(){
	
	initPage();
	
	function initPage() {
		// 添加画面验证
		$("#searchForm").validate({
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
		
	}

	$("input.ifEffective").change(function(){
		if ($(this).is(':checked')) {
			$("#ifEffective").val("1");
		} else {
			$("#ifEffective").val("0");
		}
	});

	$("#customerType").change(function(){
		var industry = $("#industry").val();
		var customerType = $(this).val();
		
		if (customerType == "1" && industry == "1") {
			$("#customerDiff").removeAttr("disabled");
		} else {
			$("#customerDiff").select2("val","");
			$("#customerDiff").attr("disabled","true");
		}
	});

	$("#industry").change(function(){
		var customerType = $("#customerType").val();
		var industry = $(this).val();
		
		if (customerType == "1" && industry == "1") {
			$("#customerDiff").removeAttr("disabled");
		} else {
			$("#customerDiff").select2("val","");
			$("#customerDiff").attr("disabled","true");
		}
	});
});


	/* $("#customerType").on('change',function(){
		getParentCo();
	}); */
	
	/* $("#customerParentCo").on('click',function(){
		getParentCo();
	}); */
	
	/* function getParentCo(){
		var customerType = $("#customerType").val();
		var $select = $("#customerParentCo");
		$select.find("option:selected").text();
		$select.html("");
		$select.find("option:selected").text();
		if(customerType == '1'){
			$.ajax("${ctx}/cm/cm001/getParentCo",{
				 type:"get",
				 dataType:"json"
			}).done(function(result){
				$.each(result.list,function(i,item){
					  $option = "<option value="+item.customerId+">"+item.customerChName+"</option>";
					  $select.append($option);
				});
			});
		}
	} */
	
	
	function getOffice(){
		var id = $("#responsiblePersonId").val();
		if (id == null || id == "") {

			$("#officeId").val("");
			$("#officeName").val("");
		} else {

			$.ajax("${ctx}/cm/cm001/getOffice/"+id,{
				type:"get",
				dataType:"json"
			}).done(function(result){
					$("#officeId").val(result.office.officeId);
					$("#officeName").val(result.office.officeName);
			});
		}
	}
	
		var treeData;
		
		//提交客户信息
		$("#btnSubmit").click(function(){
			if(!$("#searchForm").valid()){
				return false;	
			} 
			var $tbody = $("#list");
			$tbody.find("tr").each(function(index) { 
				//var val = $tbody.find("tr").rows[index].find('input[name="validityDateFrom1"]').val();
            });
			var formData = new FormData($("#searchForm")[0]);
			 var selFiles = $(".upload-select-btn input[type='file']").data("files");
             // 如查文件存在
             if (selFiles) {
                 // 将文件加入form data对象
                 for (var i = 0; i < selFiles.length; i++) {
                	 formData.append("files", selFiles[i]);
                 }
             }
             var $tr = $("#list").find("tr");
			$.ajax({
				url:"${ctx}/cm/cm001/save",
				type : "post",
				data : formData,
				dataType : "json",
				contentType: false,
                processData: false,
			//}).done(function (data) {
            //	showMessage("修改成功");
            //	refrechpage();
		        success: function(oData, oStatus) {
		           	if (oData.success) {
		           		showMessage("修改成功");
		           		window.location.href="${ctx}/cm/cm001/list";
		           	}
		        },
		        error: function(oData, oStatus, eErrorThrow) {
		        }
            });
		});
		
		 function refrechpage(){
     		$("#searchForm").attr("action","${ctx}/cm/cm001/list");
    		$("#searchForm").submit(); 
	     }
		
						
		var agreementIndex = 0;
		
		$("#agreementBtn") .click(function() {
			 var $tbody = $("#contentTable tbody");
			var curlength = $tbody.find("tr").length;
					var str = "<tr><td><a href=\"javascript:;"
							+"\" class=\"delFileRow\" data-file-id=\"1\"><i class=\"icon-minus-sign\"></i></a>"
							+"</td><td class=\"text-center rowNo\" width=\"10\">"
								+ (curlength + 1)
							+"</td>"
							+"<td><input name='cmAgreementList[" + curlength + "].validityDateFrom' type=\"text\" readonly=\"readonly\" maxlength=\"20\" class=\"input-medium Wdate required\" value=\"\" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\">"
							+"</td>"
							+"<td>"
								+"<input  name='cmAgreementList[" + curlength + "].validityDateTo' type=\"text\" readonly=\"readonly\" maxlength=\"20\" class=\"input-medium Wdate required\" value=\"\" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\">"
							+"</td>"
							+"<td>"
								+"<input name='cmAgreementList[" + curlength + "].newRemarks' type=\"text\" value=\"\" maxlength=\"50\" class=\"input-small\"></td></tr>";
					$("#list").append(str);
		  });

		
		$(document).on('click',"#contentTable .delFileRow",function(){
			var $tbody = $(this).parents("tbody");
			$(this).parents("tr").remove();
			$tbody.find("tr").each(function(index){
				$(this).find("td.rowNo").html(index+1);

				$(this).find("input").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {

						$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));

					}
				});
    			$(this).find("td>input").each(function() {
    				if (typeof($(this).attr("name")) != "undefined") {
	    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
    				}
    			});
   			});
			
			  /* var _this = $(this);
			  var $tbody = _this.parents("tbody");
			  var $delRow = _this.parents("tr");
			  if($delRow.hasClass("uploaded")){
				  $.ajax("${ctx}/cm/cm001/deleteAgreement/"+ _this.data("file-id"),{
					  type: "post",
                      dataType: "json"
				  }).done(function(result){
					  $delRow.remove();
                      showMessage("协议明细删除成功");
                      addAreementTbl();
				  });
			  }else{
				  $delRow.remove();
				  addAreementTbl();
			  } */
			  
		});
		
		 /* function addAreementTbl() {
             // 更新table
             var $tbody = $("#contentTable tbody");
             $tbody.find("tr.empty").remove();
             // 重整行号
             $tbody.find("tr").each(function(index) { 
                $(this).find("td.rowNo").html(index + 1);
				$(this).find("td>input").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {
						$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
					}
				});
             });
         } */
		
		
		function getProvincesAndCities() {
			var province = "";
			$.ajax({
				type : "get",
				url : "${ctx}/sys/area/treeData",
				dataType : "json",
				success : function(data) {
					treeData = data;
					for ( var pi in treeData) {
						if (treeData[pi].pId == 1) {
							province += "<option value='"+treeData[pi].code+"'>" + treeData[pi].name + "</option>"
						}
					}
					$("#province").append(province);
				},
			});

		}
		$("#province").click(function() {
			getCity();
		});

		function getCity() {
			$("#city").empty();
			$("#province").find("option:selected").text();
			var index = -1;
			var cityStr = "";
			var city = $("#province").find("option:selected").text();

			for ( var ct in treeData) {

				if (treeData[ct].name == city && treeData[ct].pId == 1) {
					index = treeData[parseInt(ct) + parseInt(1)].pId;
				}
				if (index == treeData[ct].pId) {
					cityStr += "<option  value='"+treeData[ct].code+"' >"
							+ treeData[ct].name + "</option>";
				}
			}
			var checkText = $("#select_id").find("option:selected").text("  ");
			$("#city").append(cityStr);

		}
		window.onload = function() {
			getProvincesAndCities();
		}

	

		// 选取上传文件按钮点击事件绑定
		$(".upload-select-btn .btn").on("click", function() {
			$(this).parent().find("input[type='file']").trigger("click");
		});

		// file input change事件绑定
		 $(".upload-select-btn input[type='file']").on("change", function(e) {
	             // 选择文件数组取得
	             var files = $(this).prop('files');
	             // 选择的文件大于0，保存选择的文件
	             if (files.length > 0) {
	                 var selFiles = $(this).data("files");
	                 if (!selFiles) {
	                     selFiles = [];
	                 }

	                 // table body
	                 var $tbody = $("#attachmentTbl tbody");
	                 // 生成添加文件行
	                 var curlength = $tbody.find("tr").length;
	                 for (var i = 0; i < files.length; i++) {
	                     var rowid = "file_" + (new Date()).getTime();
	                     var html = '<tr id="' + rowid + '">';
	                         html += '<td class="text-center"><a href="javascript:;" class="delFileRow"><i class="icon-minus-sign"></i></a></td>';
	                         html += '<td class="text-center rowNo" width="35px">' + (curlength + 1) + '</td>';
	                         html += '<td>' + files[i].name + '</td>';
	                         html += '</tr>';
	                     $tbody.append(html);
	                     $("#" + rowid).data("file", files[i]);
	                     selFiles.push(files[i]);
	                     curlength = curlength + 1;
	                 }

	                 // 文件数组保存
	                 $(this).data("files", selFiles);
	                 // 更新附件table画面显示
	                 updateAttachmentTbl();
	             }
	         });

		// 删除附件行
		$(document).on("click","#attachmentTbl .delFileRow",function() {
					var _this = $(this);
					var $tbody = _this.parents("tbody");
					var $delRow = _this.parents("tr");

					// 删除行是否已上传
					if ($delRow.hasClass("uploaded")) {
						// 已上传时
						confirmx("确认要删除该文件吗？", function() {
							$.ajax("${ctx}/cm/cm001/delete/file/"+ _this.data("file-id"), {
										type : "post",
										dataType : "json"
									}).done(function(data) {
								$delRow.remove();
								showMessage("文件删除成功");
								// 更新附件table画面显示
								updateAttachmentTbl();
							});
						});
					} else {
						// 未上传时
						// 文件数组取得
						var selFiles = $(".upload-select-btn input[type='file']").data("files");
						// 删除对应文件
						selFiles.splice(selFiles.indexOf($delRow.data("file")),1);
						// 删除行
						$delRow.remove();
						// 更新附件table画面显示
						updateAttachmentTbl();
					}
				});

		// 更新附件table画面显示
        function updateAttachmentTbl() {
            // 更新table
            var $tbody = $("#attachmentTbl tbody");
            if ($tbody.find("tr").length == 0) {
                var html = '<tr class="empty">';
                    html += '<td class="text-center" colspan="3">请选择附件</td>';
                    html += '</tr>';
                $tbody.append(html);
            } else {
                $tbody.find("tr.empty").remove();
            }

            // 重整行号
            $tbody.find("tr").each(function(index) { 
                $(this).find("td.rowNo").html(index + 1);
            });
        }
	</script>
</body>
</html>