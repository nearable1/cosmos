<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>借出延长申请</title>
<meta name="decorator" content="default" />
	<style type="text/css">
	     div.ellipsis {
	         text-overflow: ellipsis; 
	         -moz-text-overflow: ellipsis; /* for Firefox,mozilla */   
	         overflow: hidden;
	         white-space: nowrap;
	     }
	</style>
</head>
<body>

	<!-- <div
		style="padding: 5px 5px; border: 1px solid #ddd; width: 98%; margin-bottom: 5px;"> -->
		<!-- <h3 align="center">借出延长申请</h3> -->
		<h3 class="text-center page-title">借出延长申请</h3>
		<!-- <div
			style="padding: 5px 5px; width: 98%; margin-bottom: 5px; margin: 0 auto;"> -->


			<%-- <ul class="nav nav-tabs">
				<li><a href="${ctx}/sm/sm001">借出</a></li>
				<li><a href="${ctx}/sm/sm009"> 换货</a></li>
				<li><a href="${ctx}/sm/sm006"> 移库</a></li>
				<li><a href="${ctx}/sm/sm007">报废/丢失</a></li>
				<li><a href="${ctx}/sm/sm008/">其他</a></li>
				<li class="active"><a href="${ctx}/sm/sm010/">借出延长</a></li>


			</ul> --%>
			<div class="group-box group-box-first" style="height: auto;">
			<form:form id="searchForm" modelAttribute="outStorageManagement"
				action="${ctx}/sm/sm001/" method="post"
				class="form-search">
				<ul class="ul-form">
					<li><label>负责人：</label> <input id="responsiblePerson"
						name="responsiblePerson" class="input-medium" type="text"
						readonly="readonly" value="${user.createBy.name}" maxlength="50">
					</li>
					<li><label style="display: inline-block; width: 100px;">操作日期：</label>
						<input id="lastVisitDate" name="lastVisitDate"
						class="input-medium" type="text" value="${date}"
						readonly="readonly" maxlength="50"></li>
					<li><label style="display: inline-block; width: 100px;"><font color="red">*</font>延长原因：</label>
						<input id=extendReason name="extendReason" class="input-medium required"
						type="text" value="${smStorageAppDtl[0].EXTEND_REASON}"
						maxlength="300"></li>
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
		<!-- </div> -->

		<!-- <div
			style="padding: 5px 5px; border: 1px solid #ddd; width: 98%; height: auto; margin-bottom: 5px; margin-left: 0x; margin-top: 30px"> -->
			<!-- <h4 style="display: inline-block;">库存数据操作</h4> -->

			<div class="group-box group-box-last">
			<%-- <form id="contentForm" action="${ctx}/sm/sm009/fromStr" class="breadcrumb form-search"> --%>
			<form:form id="contentForm" modelAttribute="outStorageDtl" action="" class="breadcrumb form-search">
                <div class="group-header" >
                    <strong class="group-title">库存数据操作</strong>
                </div>
				<!-- <h4>库存数据操作</h4> -->
				<div style="overflow-x:scroll;">
				<table id="contentTable"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="20px"></th>
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
							<th>延迟到期日</th>
						</tr>
					</thead>
					<tbody Id="addlist">
						<c:if test="${!viewStatus.canApply&&!viewStatus.canReapply}">
							<c:forEach items="${outStorageDtl.smStorageAppDtl}" var="userList"
								varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.id}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" value="${userList.lendingDateTo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][NUM]" value="${userList.num}" type="hidden">
												${status.index+1}
											</td>
											<td>${userList.materialNo}</td>
											<td>${userList.materialName} ${userList.model}</td>
											<td>${userList.snNo}</td>
											<td>${userList.productionDate}</td>
											<td class="text-right">${userList.num}</td>
											<td>${fns:getDictLabel(userList.lendingType, 'DM0036', '')}</td>
											<td>${userList.customerName}</td>
											<td>${fns:getDictLabel(userList.INDUSTRY, 'DM0002', '')}</td>
											<td title="${userList.newRemarks}"><div style="max-width:100px" class="ellipsis">${userList.newRemarks}</div></td>
											<td title="${userList.accessoriesRemarks}"><div style="max-width:100px" class="ellipsis">${userList.accessoriesRemarks}</div></td>
											<td>${userList.lendingDateFrom}</td>
											<td>${userList.lendingDateTo}</td>
											<td><input name="smStorageAppDtl[${status.index}][EXTEND_DATE_TO]" class="input-small" type="text" value="${userList.extendDateTo}" readonly="readonly"></td>
										</tr>

								<%-- <tr id='tr${status.index}">
									<td>${status.index+1}</td>
									<td name='procInsId' id='procInsId'>${userList.materialNo}</td>
									<td name='materialNo' id='materialNo'>${userList.materialName}　${userList.model}</td>
									<td name='num' id='num'>${userList.snNo}</td>
									<td name='warehouse' id='warehouse'>${userList.productionDate}</td>
									<td name='materialNo' id='materialNo'>${userList.num}</td>
									<td>${fns:getDictLabel(userList.lendingType, 'DM0036', '')}</td>
									<td>${userList.contactsName}</td>
									<td>${fns:getDictLabel(userList.industry, 'DM0002', '')}</td>
									<td>${userList.newRemarks}</td>
									<td>${userList.accessoriesRemarks}</td>
									<td>${userList.lendingDateFrom}</td>
									<td>${userList.lendingDateTo}</td>
									<td><input name="extendDateTo" type="text" maxlength="20"
										class="select2-choice select2-default input-small Wdate required"
										readonly="readonly" value="${userList.extendDateTo}" /><label
										id='extendDateToErro${status.index}"
										style="display: none; text-align: center; background-image: url();"
										class="error"></label> <input id="index" name="index"
										class="input-medium" type="hidden" value="${userList.id}"
										readonly="readonly"></td>
								</tr> --%>
							</c:forEach>
						</c:if>
						<c:if test="${viewStatus.canApply}">
							<c:forEach items="${outStorageDtl.smStorageAppDtl}" var="userList"
								varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.id}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" value="${userList.lendingDateTos}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][NUM]" value="${userList.num}" type="hidden">
												<input type='checkbox' class="selSingle" name="selectedList" value="${status.index}"/>
											</td>
											<td>${userList.materialNo}</td>
											<td>${userList.materialName} ${userList.model}</td>
											<td>${userList.snNo}</td>
											<td>${userList.productionDate}</td>
											<td class="text-right">${userList.num}</td>
											<td>${fns:getDictLabel(userList.lendingType, 'DM0036', '')}</td>
											<td>${userList.customerName}</td>
											<td>${fns:getDictLabel(userList.INDUSTRY, 'DM0002', '')}</td>
											<td title="${userList.newRemarks}"><div style="max-width:100px" class="ellipsis">${userList.newRemarks}</div></td>
											<td title="${userList.accessoriesRemarks}"><div style="max-width:100px" class="ellipsis">${userList.accessoriesRemarks}</div></td>
											<td>${userList.lendingDateFrom}</td>
											<td>${userList.lendingDateTos}</td>
											<td><input name="smStorageAppDtl[${status.index}][EXTEND_DATE_TO]" class="input-small Wdate extendDateTo" type="text" value="${userList.extendDateTo}" readonly="readonly"></td>
										</tr>


								<%-- <tr id='tr${status.index}">
									<td><input id="ckb" name="ckb" type="checkbox"
										value="${status.index}"></td>
									<td name='procInsId' id='procInsId'>${userList.materialNo}</td>
									<td name='materialNo' id='materialNo'>${userList.materialName}　${userList.model}</td>
									<td name='num' id='num'>${userList.snNo}</td>
									<td name='warehouse' id='warehouse'>${userList.productionDate}</td>
									<td name='materialNo' id='materialNo'>${userList.num}</td>
									<td>${fns:getDictLabel(userList.lendingType, 'DM0036', '')}</td>
									<td>${userList.contactsName}</td>
									<td>${fns:getDictLabel(userList.industry, 'DM0002', '')}</td>
									<td>${userList.newRemarks}</td>
									<td>${userList.accessoriesRemarks}</td>
									<td>${userList.lendingDateFrom}</td>
									<td>${userList.lendingDateTos}<input id="lendingDateTo" name="lendingDateTo" class="input-medium"
													type="hidden" value="${userList.lendingDateTo}" readonly="readonly"></td>
									<td><input name="extendDateTo" type="text" maxlength="20"
										class="select2-choice select2-default input-small Wdate required"
										value="${userList.extendDateTo}"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
										<label
										id='extendDateToErro${status.index}"
										style="display: none; text-align: center; background-image: url();"
										class="error"></label> <input id="index" name="index"
										class="input-medium" type="hidden" value="${userList.id}"
										readonly="readonly"></td>
								</tr> --%>
							</c:forEach>
						</c:if>
						<c:if test="${viewStatus.canReapply}">
							<c:forEach items="${outStorageDtl.smStorageAppDtl}" var="userList"
								varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.id}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" value="${userList.lendingDateTo}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][NUM]" value="${userList.num}" type="hidden">
												<input type='checkbox' class="selSingle" name="selectedList" value="${status.index}"/>
											</td>
											<td>${userList.materialNo}</td>
											<td>${userList.materialName} ${userList.model}</td>
											<td>${userList.snNo}</td>
											<td>${userList.productionDate}</td>
											<td class="text-right">${userList.num}</td>
											<td>${fns:getDictLabel(userList.lendingType, 'DM0036', '')}</td>
											<td>${userList.customerName}</td>
											<td>${fns:getDictLabel(userList.INDUSTRY, 'DM0002', '')}</td>
											<td title="${userList.newRemarks}"><div style="max-width:100px" class="ellipsis">${userList.newRemarks}</div></td>
											<td title="${userList.accessoriesRemarks}"><div style="max-width:100px" class="ellipsis">${userList.accessoriesRemarks}</div></td>
											<td>${userList.lendingDateFrom}</td>
											<td>${userList.lendingDateTo}</td>
											<td><input name="smStorageAppDtl[${status.index}][EXTEND_DATE_TO]" class="input-small Wdate extendDateTo" type="text" value="${userList.extendDateTo}" readonly="readonly"></td>
										</tr>


								<%-- <tr id='tr${status.index}">
									<td><input id="ckb" name="ckb" type="hidden"
										value="${userList.id}"></td>
									<td name='procInsId' id='procInsId'>${userList.materialNo}</td>
									<td name='materialNo' id='materialNo'>${userList.materialName}　${userList.model}</td>
									<td name='num' id='num'>${userList.snNo}</td>
									<td name='warehouse' id='warehouse'>${userList.productionDate}</td>
									<td name='materialNo' id='materialNo'>${userList.num}</td>
									<td>${fns:getDictLabel(userList.lendingType, 'DM0036', '')}</td>
									<td>${userList.contactsName}</td>
									<td>${fns:getDictLabel(userList.industry, 'DM0002', '')}</td>
									<td>${userList.newRemarks}</td>
									<td>${userList.accessoriesRemarks}</td>
									<td>${userList.lendingDateFrom}</td>
									<td>${userList.lendingDateTo}<input id="lendingDateTo" name="lendingDateTo" class="input-medium"
													type="hidden" value="${userList.lendingDateTo}" readonly="readonly"></td>
									<td><input name="extendDateTo" type="text" maxlength="20"
										class="select2-choice select2-default input-small Wdate required"
										value="${userList.extendDateTo}"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
										<label
										id='extendDateToErro${status.index}"
										style="display: none; text-align: center; background-image: url();"
										class="error"></label> <input id="index" name="index"
										class="input-medium" type="hidden" value="${userList.id}"
										readonly="readonly"></td>
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
	<sys:message content="${message}"/>
	<%-- <c:if test="${message}">
		<div id="messageBox" class="alert alert-success hide"
			style="display: block;">
			<button data-dismiss="alert" class="close">×</button>
			操作成功!
		</div>
	</c:if> --%>
	<c:if test="${!empty user.act.procInsId}">
	    <act:histoicFlow procInsId="${user.act.procInsId}"/>
	</c:if>
	<script type="text/javascript">

	$(document).ready(function() {
		
		initPage();
		
		function initPage() {
			   if("${viewStatus.canApply}"=="true"||"${viewStatus.canReapply}"=="true")
			   {
				   $("#extendReason").removeAttr("readOnly"); 
				   $("#newRemarks").removeAttr("readOnly"); 
					//等于空 申请状态
			   } else {
				   $("#extendReason").attr("readOnly","true");
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

	    // 选择框change事件绑定
	    $("#contentTable .selSingle").on("change", function() {
	    	var $row = $(this).parent().parent();
	        if ($(this).is(":checked")) {
	        	$row.find("input.extendDateTo").attr("onclick", "WdatePicker({dateFmt:'yyyy-MM-dd'});");
	        	$row.find("input.extendDateTo").addClass("required");
	        } else {
	        	$row.find("input.extendDateTo").val("");
	        	$row.find("input.extendDateTo").removeAttr("onclick");
	        	$row.find("input.extendDateTo").removeClass("required");
	        }
	    });
	});
		//var treeData;
		//var sticherList = "${smStorageInfo}";
		var but = "0";
		var newAdd = "3";
		$("#eaaSubmit").click(function() {
			but = "1";
			add();

		})
		$("#refuseSubmit").click(function() {
			var r=confirm("确认要退回给申请者？请确认！");
			if (r == true) {
				but = "2";
				add();
			}

		})
		$("#againSubmit").click(function() {
			but = "3";
			add();

		})
		$("#withdrawSubmit").click(function() {
			but = "4";
			add();

		})
		$("#deteteSubmit").click(function() {
			but = "5";
			add();
		})

		function add() {
			if(but=="0"||but=="3") {
			if (!$("#searchForm").valid()) {
				return false;
			}
			var selectedCount = 0;
			$("input:checkbox[name='selectedList']:checked").each(function(){
				selectedCount++;
		    });
			if(selectedCount == 0){
				alertx("请至少选择一条数据。","");
				return false;
			}
			if (!$("#contentForm").valid()) {
				return false;
			}
			}
			var searchForm = $("#searchForm").serialize();//数据序列化
			var contentForm = $("#contentForm").serialize();
			//var comment = $("#comment").val(); 
			//var param = searchForm + "&" + contentForm + "&comment=" + comment;
			var param=searchForm+"&"+contentForm;
			var comment = $("#comment").val(); 
			if (typeof(comment) != "undefined") {
				param=param+"&comment="+comment;
			}
			/* var yesOrNo = false;
			var ckb = "";
			document.getElementById("SubmitErro").style.display = "none";
			switch (but) {
			case "0":
				//提交申请
				var productionDates = document
						.getElementsByName("extendDateTo");
				var lendingDateTos = document
				.getElementsByName("lendingDateTo");
				$("input[name=ckb]")
						.each(
								function() {
									if (!$("#searchForm").valid()) {
										yesOrNo = true;
									}
									if ($(this).attr("checked")) {
										var i = $(this).val();
										var e = document
												.getElementById("extendDateToErro"
														+ i);
										e.style.display = "none";
										if (productionDates[i].value == ""
												|| productionDates[i].value == null) {
											e.innerHTML = "必填信息";
											e.style.display = "block";
											yesOrNo = true;
										} else {
											var endDate = productionDates[i].value;
											var beginDate = lendingDateTos[i].value;
											var d1 = new Date(beginDate.replace(/\-/g, "\/"));
											var d2 = new Date(endDate.replace(/\-/g, "\/"));
											if (beginDate != ""
													&& endDate != ""
													&& d1 >= d2) {
												e.innerHTML = "延长日期不正确！";
												e.style.display = "block";
												yesOrNo = true;
											}

										}
										ckb += "&ckb=" + i;
									}
								});
				if (ckb.length == 0 || ckb == "") {
					var e = document.getElementById("SubmitErro");
					e.innerHTML = "请选择一条有效数据后提交";
					e.style.display = "block";
					yesOrNo = true;
				}
				break;
			default:
			}

			if (yesOrNo) {
				return;
			} */

			$.ajax({
				type : "post",
				url : "${ctx}/sm/sm010/add?but=" + but + "&newAdd=" + newAdd,
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
				/* success : function(data) {
					document.getElementById("contentForm").submit();
					window.location.href = "${ctx}/act/task/todo";
				},
				error : function(data) {
					//alert("出现错误请联系管理员：" +data.name);
				} */
			});

		}
	</script>

</body>
</html>