<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>换货出库申请</title>
<meta name="decorator" content="default" />
</head>
<body>

	<!-- <div
		style="padding: 5px 5px; border: 1px solid #ddd; width: 98%; margin-bottom: 5px;"> -->
		<!-- <h3 align="center">换货出库申请</h3> -->
		<h3 class="text-center page-title">换货出库申请</h3>
		<!-- <div
			style="padding: 5px 5px; width: 98%; margin-bottom: 5px; margin: 0 auto;"> -->


<%-- 			<ul class="nav nav-tabs">
				<li><a href="${ctx}/sm/sm001">借出</a></li>
				<li class="active"><a href="${ctx}/sm/sm009"> 换货</a></li>
				<li><a href="${ctx}/sm/sm006"> 移库</a></li>
				<li><a href="${ctx}/sm/sm007">报废/丢失</a></li>
				<li><a href="${ctx}/sm/sm008/">其他</a></li>
				<li><a href="${ctx}/sm/sm010/">借出延长</a></li>


			</ul> --%>
			<div class="group-box group-box-first" style="height: auto;">
			<form:form id="searchForm" modelAttribute="outStorageManagement"
				action="${ctx}/sm/sm001/" method="post"
				class="form-search">
				<ul class="ul-form">


					<li><label>负责人：</label> <input id="responsiblePerson"
						name="responsiblePerson" class="input-medium" type="text"
						readonly="readonly" value="${osm.createBy.name}" maxlength="50"></li>
					<li><label style="display: inline-block; width: 100px;">操作日期：</label>
						<input id="lastVisitDate" name="lastVisitDate"
						class="input-medium" type="text" value="${cm.CREATE_DATE}${date}"
						readonly="readonly" maxlength="50"></li>

					<li class="full-width">
					    <label>备注说明：</label>
					     <textarea
							id="newRemarks" name="newRemarks" maxlength="300"
							class="fill-right" rows="3">${smStorageApp[0].NEW_REMARKS}</textarea>
					</li>
					<%-- <li style="width: 100%;"><label>备注说明：</label> <textarea
							id="newRemarks" name="newRemarks" maxlength="300"
							style="width: calc(80%); resize: none;" rows="3">${smStorageApp[0].NEW_REMARKS}</textarea>
					</li> --%>
				</ul>
			</form:form>
		</div>

		<!-- <div
			style="padding: 5px 5px; border: 1px solid #ddd; width: 98%; height: auto; margin-bottom: 5px; margin-left: 0x; margin-top: 30px"> -->
			<!-- <h4 style="display: inline-block;">库存数据操作</h4> -->
<%-- 			<c:if test="${viewStatus.canApply||viewStatus.canReapply}">
				<div
					style="padding: 5px 8px 5px 5px; border: 0px solid #ddd; width: 98%;">
					<label style="width: 150px; padding: 5px 0px 0px 0px;">合同号：</label>
					<input id="querySnNo" type="text" value=""
						style="width: 165px; padding: 5px 5px 5px 0px;" /> <input
						id="snNoBtn" class="btn btn-primary" type="button" value="查询"><span
						id='text'></span>
				</div>
			</c:if> --%>

			<div class="group-box group-box-last">
			<%-- <form id="contentForm" action="${ctx}/sm/sm009/fromStr" class="breadcrumb form-search"> --%>
			<form:form id="contentForm" modelAttribute="outStorageDtl" action="" class="breadcrumb form-search">
                <div class="group-header" >
                    <strong class="group-title">库存数据操作</strong>
                </div>
				<!-- <h4>库存数据操作</h4> -->
				<c:if test="${viewStatus.canApply || viewStatus.canReapply}">
				<ul class="ul-form">
					<li>
						<label>合同号：</label> <input id="querySnNo" type="text" value="" class="input-medium"/>
					</li>
					<li><input id="snNoBtn" class="btn btn-primary" type="button" value="查找"></li>
				</ul>
				</c:if>
				<div style="overflow-x:scroll;">
				<table id="contentTable"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="20px"></th>
							<th>合同编号</th>
							<th>签约方</th>
							<th>最终客户</th>
							<th>物料号</th>
							<th>物料名称</th>
							<th>数量</th>
							<th>当前S/N</th>
							<th>生产日期</th>
							<th>保修到期日</th>
							<th>出库S/N</th>
							<th>生产日期</th>
							<th>新保修到期日</th>
						</tr>
					</thead>
					<tbody Id="addlist">
						<c:if test="${viewStatus.canApproval}">
							<c:if test="${endApprover}">
								<c:forEach items="${outStorageDtl.smStorageAppDtl}" var="userList"
									varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.id}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][ORDER_NO]" value="${userList.orderNo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][LINE_NO]" value="${userList.lineNo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][MATERIAL_NO]" value="${userList.materialNo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][SN_NO]" value="${userList.snNo}" type="hidden">
												${status.index+1}
											</td>
											<td>${userList.orderNo}</td>
											<td>${userList.name}</td>
											<td>${userList.customerChName}</td>
											<td>${userList.materialNo}</td>
											<td>${userList.materialName} ${userList.model}</td>
											<td class="text-right">1</td>
											<td>${userList.snNo}</td>
											<td>${userList.productionDate}</td>
											<td>${userList.warrantyDateTo}</td>
											<td><input name="smStorageAppDtl[${status.index}][NEW_SN_NO]" class="input-medium snNo required" maxlength="50" type="text" value="${userList.NEW_SN_NO}"></td>
											<td><input name="smStorageAppDtl[${status.index}][NEW_DATE]" class="input-small productionDate" type="text" value="${userList.newProductionDate}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" class="input-small Wdate required" type="text" value="${userList.LENDING_DATE_TO}" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"></td>
										</tr>
									<%-- <tr id='tr${status.index}">
									<tr>
										<td>${status.index+1}</td>
										<td name='procInsId' id='procInsId'>${userList.orderNo}</td>
										<td name='materialNo' id='materialNo'>${userList.name}</td>
										<td name='num' id='num'>${userList.customerChName}</td>
										<td name='warehouse' id='warehouse'>${userList.materialNo}</td>
										<td name='materialNo' id='materialNo'>${userList.materialName}
											${userList.model}</td>
										<td>1</td>
										<td>${userList.snNo}</td>
										<td>${userList.productionDate}</td>
										<td>${userList.warrantyDateTo}</td>
										<td><input id="newSnNo" name="newSnNo"
											class="input-medium test required" type="text"
											value="${userList.NEW_SN_NO}" maxlength="30"><label
											id='snNoErro${status.index}"
											style="display: none; text-align: center; background-image: url();"
											class="error"></label></td>
										<td><input id="newDate" name="newDate"
											class="input-medium" type="text" readonly="readonly"
											value="${userList.newProductionDate}" maxlength="30"></td>
										<td><input id="productionDate" name="productionDate"
											type="text" maxlength="20"
											class="input-medium Wdate required"
											value="${userList.LENDING_DATE_TO}"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd '});" />
											<label id='productionDateErro${status.index}"
											style="display: none; text-align: center; background-image: url();"
											class="error"></label></td>
										<td><input id="materialNos" name="materialNos"
											class="input-medium" type="hidden"
											value="${userList.materialNo}" readonly="readonly"> <input id="index" name="index"
											class="input-medium" type="hidden" value="${userList.id}"
											readonly="readonly"></td>
									</tr> --%>
								</c:forEach>
							</c:if>
							<c:if test="${!endApprover}">
								<c:forEach items="${outStorageDtl.smStorageAppDtl}" var="userList"
									varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.id}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][ORDER_NO]" value="${userList.orderNo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][LINE_NO]" value="${userList.lineNo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][MATERIAL_NO]" value="${userList.materialNo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][SN_NO]" value="${userList.snNo}" type="hidden">
												${status.index+1}
											</td>
											<td>${userList.orderNo}</td>
											<td>${userList.name}</td>
											<td>${userList.customerChName}</td>
											<td>${userList.materialNo}</td>
											<td>${userList.materialName} ${userList.model}</td>
											<td class="text-right">1</td>
											<td>${userList.snNo}</td>
											<td>${userList.productionDate}</td>
											<td>${userList.warrantyDateTo}</td>
											<td><input name="smStorageAppDtl[${status.index}][NEW_SN_NO]" class="input-medium" type="text" value="${userList.NEW_SN_NO}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][NEW_DATE]" class="input-small" type="text" value="${userList.newProductionDate}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" class="input-small" type="text" value="${userList.LENDING_DATE_TO}" readonly="readonly"></td>
										</tr>
									<%-- <tr id='tr${status.index}">
									<tr>
										<td>${status.index+1}</td>
										<td name='procInsId' id='procInsId'>${userList.orderNo}</td>
										<td name='materialNo' id='materialNo'>${userList.name}</td>
										<td name='num' id='num'>${userList.customerChName}</td>
										<td name='warehouse' id='warehouse'>${userList.materialNo}</td>
										<td name='materialNo' id='materialNo'>${userList.materialName}
											${userList.model}</td>
										<td>1</td>
										<td>${userList.snNo}</td>
										<td>${userList.productionDate}</td>
										<td>${userList.warrantyDateTo}</td>
										<td><input id="newSnNo" name="newSnNo"
											class="input-medium test" type="text"
											readonly="readonly" value="${userList.NEW_SN_NO}"
											maxlength="30"><label id='snNoErro${status.index}"
											style="display: none; text-align: center; background-image: url();"
											class="error"></label></td>
										<td><input id="newDate" name="newDate"
											class="input-medium" type="text" readonly="readonly"
											value="${userList.newProductionDate}" maxlength="30"></td>
										<td><input id="productionDate" name="productionDate"
											type="text" maxlength="20"
											class="input-medium "
											value="${userList.LENDING_DATE_TO}" readonly="readonly" />
											<label id='productionDateErro${status.index}"
											style="display: none; text-align: center; background-image: url();"
											class="error"></label></td>
										<td><input id="materialNos" name="materialNos"
											class="input-medium" type="hidden"
											value="${userList.materialNo}" readonly="readonly"> <input id="index" name="index"
											class="input-medium" type="hidden" value="${userList.id}"
											readonly="readonly"></td>
									</tr> --%>
								</c:forEach>
							</c:if>
						</c:if>
						<c:if test="${!viewStatus.canApproval}">
							<c:forEach items="${outStorageDtl.smStorageAppDtl}" var="userList"
								varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.id}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][ORDER_NO]" value="${userList.orderNo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][LINE_NO]" value="${userList.lineNo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][MATERIAL_NO]" value="${userList.materialNo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][SN_NO]" value="${userList.snNo}" type="hidden">
												${status.index+1}
											</td>
											<td>${userList.orderNo}</td>
											<td>${userList.name}</td>
											<td>${userList.customerChName}</td>
											<td>${userList.materialNo}</td>
											<td>${userList.materialName} ${userList.model}</td>
											<td class="text-right">1</td>
											<td>${userList.snNo}</td>
											<td>${userList.productionDate}</td>
											<td>${userList.warrantyDateTo}</td>
											<td><input name="smStorageAppDtl[${status.index}][NEW_SN_NO]" class="input-medium" type="text" value="${userList.NEW_SN_NO}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][NEW_DATE]" class="input-small" type="text" value="${userList.newProductionDate}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" class="input-small" type="text" value="${userList.LENDING_DATE_TO}" readonly="readonly"></td>
										</tr>


								<%-- <tr id='tr${status.index}">
								<tr>
									<td>${status.index+1}</td>
									<td name='procInsId' id='procInsId'>${userList.orderNo}</td>
									<td name='materialNo' id='materialNo'>${userList.name}</td>
									<td name='num' id='num'>${userList.customerChName}</td>
									<td name='warehouse' id='warehouse'>${userList.materialNo}</td>
									<td name='materialNo' id='materialNo'>${userList.materialName}
										${userList.model}</td>
									<td>1</td>
									<td>${userList.snNo}</td>
									<td>${userList.productionDate}</td>
									<td>${userList.warrantyDateTo}</td>
									<td><input id="newSnNo" name="newSnNo"
										class="input-medium" type="text"
										value="${userList.NEW_SN_NO}" readonly="readonly"
										maxlength="30"></td>
									<td><input id="newDate" name="newDate"
										class="input-medium" type="text" readonly="readonly"
										value="${userList.newProductionDate}" maxlength="30"></td>
									<td><input name="productionDate" type="text"
										readonly="readonly" maxlength="20"
										class="input-medium"
										value="${userList.LENDING_DATE_TO}"/></td>
									<td><input id="index" name="index" class="input-medium"
										type="hidden" value="${userList.id}" readonly="readonly"></td>
								</tr> --%>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				</div>
			</form:form>

		</div>
	<!-- </div> -->
	<div class="text-center">
	<!-- <div class="text-center"
		style="padding: 5px 5px; border: 0px solid #ddd; width: 98%; height: auto; margin-bottom: 5px; margin-left: 0px; margin-top: 30px"> -->

		<c:if test="${viewStatus.canApply}">
			<input id="btnSubmit" class="btn btn-primary" type="button"
				value="提交申请" onclick="add()" style="text-align: center;">
		</c:if>

		<c:if test="${viewStatus.canApproval}">

                <div class="text-center">
					<strong class="group-title">审批</strong>
                </div>

                <div class="text-center">
					<label>审批意见：</label>
		             <textarea id="comment" name="comment" maxlength="300" style="resize: none; width: calc(80%);" rows="3"></textarea>
                </div>

                <div class="text-center">
					<input id="eaaSubmit" class="btn btn-primary" type="button"
						value="同意" style="text-align: center;">
					<input id="refuseSubmit" class="btn btn-default" type="button"
						value="退回" style="text-align: center;">
				</div>
		</c:if>

		<c:if test="${viewStatus.canBack}">
			<input id="withdrawSubmit" class="btn btn-primary" type="button"
				value="撤回" style="text-align: center;">
		</c:if>

		<c:if test="${viewStatus.canReapply}">
			<input id="againSubmit" class="btn btn-primary" type="button"
				value="再申请" style="text-align: center;">
			<input id="deteteSubmit" class="btn btn-primary" type="button"
				value="删除" style="text-align: center;">

		</c:if>
		<!-- <label id='SubmitErro'
			style="display: none; text-align: center; background-image: url();"
			class="error"></label> -->

	</div>
	<%-- <c:if test="${message}">
		<div id="messageBox" class="alert alert-success hide"
			style="display: block;">
			<button data-dismiss="alert" class="close">×</button>
			操作成功!
		</div>
	</c:if> --%>
	<sys:message content="${message}"/>
	<c:if test="${!empty osm.act.procInsId}">
		<act:histoicFlow procInsId="${osm.act.procInsId}" />
	</c:if>
	<script type="text/javascript">
	$(document).ready(function() {
		
		initPage();
		
		function initPage() {
			if("${viewStatus.canApply}"=="true"||"${viewStatus.canReapply}"=="true")
			{
				$("#newRemarks").removeAttr("readOnly"); 
				//等于空 申请状态
			} else {
				$("#newRemarks").attr("readOnly","true");
			}
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
			// 添加画面验证
			$("#contentForm").validate({
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
	});
	//var treeData;
	//var sticherList="${list}"; 
	var but = "0";
	var newAdd = "3";
	$("#eaaSubmit").click(function()
			{
				but="1";
				add();

			})
	$("#refuseSubmit").click(function()
			{
				var r=confirm("确认要退回给申请者？请确认！");
				if (r == true) {
					but="2";
					add();
				}
			})
	$("#againSubmit").click(function()
			{
				but="3";
				add();

			})
	$("#withdrawSubmit").click(function()
			{
				but="4";
				add();

			})
	$("#deteteSubmit").click(function()
			{
				but="5";
				add();
			})

			
	
				
function add()
		{
			if(but=="0"||but=="3") {
				if (!$("#searchForm").valid()) {
					return false;
				}
				if(newAdd=="0") {
					var selectedCount = 0;
					$("input:checkbox[name='selectedList']:checked").each(function(){
						selectedCount++;
				    });
					if(selectedCount == 0){
						alertx("请至少选择一条数据。","");
						return false;
					}
				}
			}
			if(but=="1") {
				if("${endApprover}"=="true"){
					if (!$("#contentForm").valid()) {
						return false;
					}
				}
			}
			var searchForm= $("#searchForm").serialize();//数据序列化
			var contentForm = $("#contentForm").serialize(); 
			//var comment = $("#comment").val(); 
			//var param=searchForm+"&"+contentForm+"&comment="+comment;
			var param=searchForm+"&"+contentForm;
			var comment = $("#comment").val(); 
			if (typeof(comment) != "undefined") {
				param=param+"&comment="+comment;
			}

			$.ajax({
				url: "${ctx}/sm/sm009/add?but="+but+"&newAdd="+newAdd,
				type : "post",
				data : param,
				dataType : "json",
			    success: function(oData, oStatus) {
		           	if (oData.success) {
		           		showMessage("操作成功");
		           		window.location.href="${ctx}/act/task/todo";
		           	}
		        },
			    error: function(oData, oStatus, eErrorThrow) {
			    }
	        });
		/* var yesOrNo = false;
		var ckb = "";
		document.getElementById("SubmitErro").style.display = "none"; */
		/* switch(but)
		{
			case "0":
				//提交申请
				$("input[name=ckb]").each(function() {  
	     	       if ($(this).attr("checked")) {  
	     	    	   var i=$(this).val();
						ckb+="&ckb="+i;
	       	     }  
	      	  });
				if(ckb.length==0||ckb=="")
				{
				  	var e = document.getElementById("SubmitErro");
					e.innerHTML="请选择一条有效数据后提交";
					e.style.display = "block";
					yesOrNo=true;
				}
		 	 break;
			case "1":
				//审批
				if("${endApprover}"=="true"){
				document.getElementById("SubmitErro").style.display = "none";
				  var snNos = document.getElementsByName("newSnNo");
				  var productionDates = document.getElementsByName("productionDate");

				  if(snNos.length==0)
				  {
					  	var e = document.getElementById("SubmitErro");
						e.innerHTML="请选择一条有效数据后提交";
						e.style.display = "block";
						yesOrNo=true;
			 	 }
				  

				  var sn = new Array();
				  	for(var i=0;i<snNos.length;i++)
				  	{
				  		if(snNos[i].length==0||snNos[i].value==null||snNos[i].value=="")
				  		{
				  			var e = document.getElementById("snNoErro"+i);
							e.innerHTML="必填信息";
							e.style.display = "block";
							yesOrNo=true;	
				  		}else
				  		{
				  			document.getElementById("snNoErro"+i).style.display = "none";
				  		}
				  		if(productionDates[i].length==0||productionDates[i].value==null||productionDates[i].value=="")
				  		{
				  			
				  			var e = document.getElementById("productionDateErro"+i);
							e.innerHTML="必填信息";
							e.style.display = "block";
							yesOrNo=true;	
				  		}else
				  		{
				  			document.getElementById("productionDateErro"+i).style.display = "none";

				  		}
				  		sn.push(snNos[i].value);
				  	}
				  	var nary=sn.sort();
				  for(var i=0;i<sn.length;i++){
				  		if (nary[i]==nary[i+1]){
				  			if(nary[i]!=""){
							var e = document.getElementById("SubmitErro");
							e.innerHTML="S/N："+nary[i]+"重复";
							e.style.display = "block";
							yesOrNo=true;				  			
				  		}
				  		}	
				  	}
				 if(snNoYseOrNo){
					var e = document.getElementById("SubmitErro");
					e.innerHTML="信息有误";
					e.style.display = "block";
					yesOrNo=true;
				 }
				}
			  break;
			case "3":
				if(newAdd=="0")
				{
					
					$("input[name=ckb]").each(function() {  
			     	       if ($(this).attr("checked")) {  
			     	    	   var i=$(this).val();
								ckb+="&ckb="+i;
			       	     }  
			      	  });
						if(ckb.length==0||ckb=="")
						{
						  	var e = document.getElementById("SubmitErro");
							e.innerHTML="请选择一条有效数据后提交";
							e.style.display = "block";
							yesOrNo=true;
						}
				}
				break;
			default:
			}			
		
				if(yesOrNo)
					return; */
				
				/* $.ajax({
		             type: "post",
		             url: "${ctx}/sm/sm009/add?but="+but+"&newAdd="+newAdd,
		             data:param,
		             dataType: "json",
		             success: function(data){
		            	 document.getElementById("contentForm").submit();
		            	// if(but=="0")
		            	// {
			            //	 window.location.href="${ctx}/sm/sm009/message"; 

		            	// }else
		            	 //{
		            		 
		            	// }
		            	  window.location.href = "${ctx}/act/task/todo";
		                      },
		             error:function(data) {
		                    //alert("出现错误请联系管理员：" +data.name);
		                }
		         }); */
			}
			
	
	
	
	
			//var agreementIndex = 0;
			//var arr = new Array();

	$("#snNoBtn").click(function()
			{
		//document.getElementById("addlist").innerHTML=' ';  
		//var arr=new Array();
		//document.getElementById("text").innerHTML='&nbsp&nbsp&nbsp&nbsp';  
		var querySnNo = $("#querySnNo").val();
		if(querySnNo == null || querySnNo == "")
    	{
    	//	document.getElementById("text").innerHTML='&nbsp&nbsp&nbsp合同号不能为空';  
    	//	document.getElementById("text").style.color='red';  
    		return;
    	}
		$.ajax({
            type: "get",
            url: "${ctx}/sm/sm009/query?orderNo="+querySnNo,
            success: function(data){
            	/* if(data.length==0)
            	{
            		document.getElementById("text").innerHTML='&nbsp&nbsp&nbsp&nbsp没找到对应的合同号';  
            		document.getElementById("text").style.color='red';  
            	} */
            	var str = "";
				if (data == null || data.length == 0) {
					$("#addlist").find("tr").each(function(){
						$(this).remove();
				    });
					$("#addlist").find("tr.empty").remove();
        			str = '<tr class="empty"><td class="text-center" colspan="13">没有可操作数据</td></tr>';
        			$("#addlist").append(str);
				} else {
					var trIndex = 0;
	        		for(var i in data)
	        		{
	        			str+= '<tr>';
	        			str+= '<td class="text-center"><input type="checkbox" class="selSingle" name="selectedList" value="' + trIndex + '"/>';
	        			str+= '<input name="smStorageAppDtl[' + trIndex + '][ID]" value="' + data[i].id + '" type="hidden">';
	        			str+= '<input name="smStorageAppDtl[' + trIndex + '][ORDER_NO]" value="' + data[i].orderNo + '" type="hidden">';
	        			str+= '<input name="smStorageAppDtl[' + trIndex + '][LINE_NO]" value="' + data[i].lineNo + '" type="hidden">';
	        			str+= '<input name="smStorageAppDtl[' + trIndex + '][MATERIAL_NO]" value="' + data[i].materialNo + '" type="hidden">';
	        			str+= '<input name="smStorageAppDtl[' + trIndex + '][SN_NO]" value="' + data[i].snNo + '" type="hidden">';
	        			str+= '<input name="smStorageAppDtl[' + trIndex + '][WAREHOUSE]" value="' + (typeof (data[i].warehouse) == "undefined" ? "" : data[i].warehouse) + '" type="hidden"></td>';
	        			str+= '<td>' + data[i].orderNo + '</td>';
	        			str+= '<td>' + data[i].name + '</td>';
	        			str+= '<td>' + (typeof (data[i].customerChName) == "undefined" ? "" : data[i].customerChName) + '</td>';
	        			str+= '<td>' + data[i].materialNo + '</td>';
	        			str+= '<td>' + data[i].materialName + '　' + data[i].model + '</td>';
	        			str+= '<td class="text-right">1</td>';
	        			str+= '<td>' + data[i].snNo + '</td>';
	        			str+= '<td>' + (typeof (data[i].productionDate) == "undefined" ? "" : data[i].productionDate) + '</td>';
	        			str+= '<td>' + (typeof (data[i].warrantyDateTo) == "undefined" ? "" : data[i].warrantyDateTo) + '</td>';
	        			str+= '<td><input name="smStorageAppDtl[' + trIndex + '][NEW_SN_NO]" class="input-medium" type="text" readonly="readonly"></td>';
	        			str+= '<td><input name="smStorageAppDtl[' + trIndex + '][NEW_DATE]" class="input-small" type="text" readonly="readonly"></td>';
	        			str+= '<td><input name="smStorageAppDtl[' + trIndex + '][LENDING_DATE_TO]" class="input-small" type="text" readonly="readonly"></td>';
	        			str+= '</tr>';
	        			
	        			trIndex++;
	        			 /* str+= "<tr><td><input id=\"ckb\" name=\"ckb\" type=\"checkbox\" value=\""+i+"\"></td>";
						 str+="<td name='procInsId' id='procInsId'>"+data[i].orderNo+"</td>";
						 str+="<td name='materialNo' id='materialNo'>"+data[i].name+"</td>";
						 str+="<td name='num' id='num'>"+data[i].customerChName+"</td>";
						 str+="<td name='warehouse' id='warehouse'>"+data[i].materialNo+"</td>";
						 str+="<td name='materialNo' id='materialNo'>"+data[i].materialName+"　"+data[i].model+"</td>";
						// str+="<td>"+data[i].materialName+"</td>";
						 str+="<td>1</td>";
						 str+="<td>"+data[i].snNo+"</td>";
						 str+="<td>"+data[i].productionDate+"</td>";
						 str+="<td>"+data[i].warrantyDateTo+"</td>";
						 str+="<td>	<input id=\"newSnNo\" name=\"newSnNo\" class=\"input-medium required\" type=\"text\" value=\"${cm.lastVisitDate}\"  readonly=\"readonly\"   maxlength=\"30\"></td>";
						 str+="<td>	<input id=\"newDate\" name=\"newDate\" class=\"input-medium\" type=\"text\"  readonly=\"readonly\"  value=\"${cm.lastVisitDate}\"  maxlength=\"30\"></td>";
						 str+="<td>	<input id=\"productionDate\" name=\"productionDate\" class=\"input-medium required\" type=\"text\"  readonly=\"readonly\"  value=\"${cm.lastVisitDate}\"  maxlength=\"30\"></td>";
						 str+="<td>	<input id=\"index\" name=\"index\" class=\"input-medium\" type=\"hidden\" value='"+data[i].id+"' readonly=\"readonly\"></td>";
						 str+="</tr>"; */
			    		//arr.push(data[i]);
	        		}
	        				//str+="<td style=\"padding: 2px 2px 2px 2px;\"><input  style=\" width:60px;height:25px; margin: 0px 0px 0px 5px\" id=\"num"+i+"\" type=\"number\"/></td>";
	        		//str+="</tr>";
					//$("#addlist").append(str);
					$("#addlist").find("tr").each(function(){
						$(this).remove();
					});
					$("#addlist").append(str);
					newAdd = "0";
				}
            },
            error:function(data) {
                  // alert("出现错误请联系管理员：" +data.name);
               }
        });
			});
	
	//var snNoYseOrNo = false;
	
	$(document).on("change", "input.snNo", function(e) {
		var $snNo = $(this);
		var $row = $snNo.parent().parent();
		var snNo = $snNo.val();
		var $productionDate = $row.find("input.productionDate");
		
		if (snNo == "") {
			$productionDate.val("");
		} else {

			$.ajax({
		    	url: "${ctx}/sm/sm001/selectSnNoBySn?snNo="+snNo,
		        type: "get",
		        async: false,
		        dataType: "json",
		        success: function (data) {
		        	if (data != null) {
			        	$productionDate.val(data.str);
		        	}
		        },
		        error: function (msg) {
		        }
		    });
		}
		//e.choice
	});
	
	/* $('.test').live('blur',function(){　　
		document.getElementById("SubmitErro").style.display = "none";
        var snNo = $(this).val();
        if(snNo==null||snNo==""){
        	return;
        }
        
        $.ajax({
			type:"get",
			url:"${ctx}/sm/sm001/selectSnNoBySn?snNo="+snNo,
			dataType:"json",
			 success: function(data)
			 {
				 var snNos = document.getElementsByName("newSnNo");
				 var materialNos = document.getElementsByName("materialNos");
				 var productionDates = document.getElementsByName("newDate");
				 for(var i = 0;i<snNos.length;i++)
				 {
					 if(snNos[i].value!=""&&snNos[i].value!=null&&"${viewStatus.canApproval}"=="true")
					 {
						 
					 }else{
						 return;
					 }
					 if(snNos[i].value==snNo)
					 {
						 if(data.str!=null&&data.str!=""&&data.str!="undefined"){
							 if(data.materialNo==materialNos[i].value)
							 {
								 snNoYseOrNo=false;
								 productionDates[i].value=data.str;
								 //return;
								 
							 }else{
								 var e = document.getElementById("SubmitErro");
									e.innerHTML="物料号不符合";
									e.style.display = "block";
									snNoYseOrNo = true;
								 //return;
							 }
							
						 }else
						 {
							 var e = document.getElementById("SubmitErro");
								e.innerHTML="未查询到S/N";
								e.style.display = "block";
								snNoYseOrNo = true;
							 //return;
						 }
						 
						
					 }
				 }
			 },error:function(data) {
				 erro("录入失败：","SubmitErro");

             }
				});
	
	}); */
	
	
	

	
	
	
	</script>

</body>
</html>