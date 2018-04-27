<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>其他出库申请</title>
<meta name="decorator" content="default" />
</head>
<body>

	<h3 class="text-center page-title">其他出库申请</h3>

	<div class="group-box group-box-first" style="height: auto;">
		<form:form id="searchForm" modelAttribute="outStorageManagement"
			action="${ctx}/sm/sm001/" method="post" class="form-search">
			<ul class="ul-form">
				<li><label>负责人：</label> <input id="responsiblePerson"
					name="responsiblePerson" class="input-medium" type="text"
					readonly="readonly"
					value="${swifm.createBy.name==null?smStorageApp[0].name:swifm.createBy.name}"
					maxlength="50"></li>
				<li><label style="display: inline-block; width: 100px;">操作日期：</label>
					<input id="lastVisitDate" name="lastVisitDate" class="input-medium"
					type="text" value="${date}" readonly="readonly" maxlength="50"></li>
				<li class="full-width">
				    <label>备注说明：</label>
				     <textarea
						id="newRemarks" name="newRemarks" maxlength="300"
						class="fill-right" rows="3">${smStorageApp[0].NEW_REMARKS}</textarea>
				</li>
				<%-- <li style="width: 100%;"><label>备注说明：</label> <textarea
						id="newRemarks" name="newRemarks" maxlength="300"
						style="width: calc(80%);" rows="3">${smStorageApp[0].NEW_REMARKS}</textarea> --%>
			</ul>
		</form:form>
	</div>
	<c:if test="${viewStatus.canApply||viewStatus.canReapply}">
		<div class="group-box">
			<div class="group-header" >
			   <strong class="group-title">数据添加</strong>
			</div>
			<form:form id="materiaForm" action="" class="breadcrumb form-search">
				<!-- <h4>数据添加</h4> -->
				<ul class="ul-form">
					<li><label>物料号：</label> <input id="qureyMaterialNo"
						type="text" value="" class="remote material input-medium"
						data-type="1,2,4,5,7" /></li>
					<li><label>S/N：</label> <input id="querySnNo" type="text"
						value="" class="input-medium" /></li>
					<li class="btns"><input id="snNoBtn" class="btn btn-primary" type="button"
						value="查找"></li>
				</ul>
				<table id="materiaTable"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="20px"></th>
							<th>物料号</th>
							<th>物料名称</th>
							<th>库存数量</th>
							<th>库房</th>
							<th>S/N</th>
							<th>生产日期</th>
						</tr>
					</thead>
					<tbody id="list">
					</tbody>

				</table>
			</form:form>
			<div class="text-center">
				<input class="btn btn-primary" id="listBtn" type="button" value="选择"
					onclick="Storage()">
			</div>
		</div>
	</c:if>
	<div class="group-box group-box-last">
		<form:form id="contentForm" modelAttribute="outStorageDtl" action=""
			class="breadcrumb form-search">
            <div class="group-header" >
               <strong class="group-title">库存数据操作</strong>
            </div>
			<!-- <h4>库存数据操作</h4> -->
			<div style="overflow-x: scroll;">
				<table id="contentTable"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="20px"></th>
							<th>物料号</th>
							<th>物料名称</th>
							<th>S/N</th>
							<th>生产日期</th>
							<th>库存数量</th>
							<th>库房</th>
							<th>出库数量</th>
						</tr>
					</thead>
					<tbody Id="addlist">
						<c:forEach items="${outStorageDtl.smStorageAppDtl}" var="userList"
							varStatus="status">
							<c:if test="${!viewStatus.canApply && !viewStatus.canReapply}">
								<tr>
									<td class="text-center"><input name="smStorageAppDtl[${status.index}][ID]" value="${userList.wId}" type="hidden">
										${status.index+1}</td>
									<td>${userList.MATERIAL_NO}</td>
									<td>${userList.MATERIAL_NAME}${userList.MODEL}</td>
									<td>${userList.SN_NO}</td>
									<td>${userList.productionDate}</td>
									<c:choose>
										<c:when test="${empty userList.SN_NO}">
											<td class="text-right">${userList.wNum}</td>
										</c:when>
										<c:otherwise>
											<td class="text-right">1</td>
										</c:otherwise>
									</c:choose>
									<td>${userList.warehouseName}</td>
									<td><input name="smStorageAppDtl[${status.index}][TO_NUM]"
										class="input-small text-right" type="text"
										value="${userList.NUM}" readonly="readonly"></td>
								</tr>
							</c:if>
							<c:if test="${viewStatus.canApply || viewStatus.canReapply}">
								<tr>
									<td class="text-center"><input
										name="smStorageAppDtl[${status.index}][ID]"
										value="${userList.wId}" type="hidden">
										<a href="javascript:delRow(${status.index});" class="delRow"><i
											class="icon-minus-sign"></i></a></td>
									<td>${userList.MATERIAL_NO}</td>
									<td>${userList.MATERIAL_NAME}${userList.MODEL}</td>
									<td>${userList.SN_NO}</td>
									<td>${userList.productionDate}</td>
									<c:choose>
										<c:when test="${empty userList.SN_NO}">
											<td class="text-right">${userList.wNum}</td>
										</c:when>
										<c:otherwise>
											<td class="text-right">1</td>
										</c:otherwise>
									</c:choose>
									<td>${userList.warehouseName}</td>
									<td><input name="smStorageAppDtl[${status.index}][TO_NUM]"
										type="text" class="input-small required text-right digits"
										min="1" max="${empty userList.SN_NO ? userList.wNum : 1}"
										value="${userList.NUM}"></td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</form:form>

	</div>
	<div class="text-center">
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
				<textarea id="comment" name="comment" maxlength="300"
					style="resize: none; width: calc(80%);" rows="3"></textarea>
			</div>

			<div class="text-center">
				<input id="eaaSubmit" class="btn btn-primary" type="button"
					value="同意" style="text-align: center;"> <input
					id="refuseSubmit" class="btn btn-default" type="button" value="退回"
					style="text-align: center;">
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

	</div>
	<sys:message content="${message}" />
	<c:if test="${!empty user1.act.procInsId}">
		<act:histoicFlow procInsId="${user1.act.procInsId}" />
	</c:if>
	<script type="text/javascript">
		$(document).ready(
				function() {

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
						$("#searchForm").validate(
								{
									submitHandler : function(form) {
										//loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									},
									ignore : ""
								});
						// 添加画面验证
						$("#contentForm").validate(
								{
									submitHandler : function(form) {
										//loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									},
									ignore : ""
								});
						// 添加画面验证
						$("#materiaForm").validate(
								{
									submitHandler : function(form) {
										//loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									},
									ignore : ""
								});
					}
					;
				});

		var but = "0";
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
			if (but == "0" || but == "3") {
				if (!$("#contentForm").valid()) {
					return false;
				}
			}
			//document.getElementById("SubmitErro").style.display = "none";
			var searchForm = $("#searchForm").serialize();//数据序列化
			var contentForm = $("#contentForm").serialize();
			//var comment = $("#comment").val();
			//var param = searchForm + "&" + contentForm + "&comment=" + comment;
			var param=searchForm+"&"+contentForm;
			var comment = $("#comment").val(); 
			if (typeof(comment) != "undefined") {
				param=param+"&comment="+comment;
			}
			$.ajax({
				url : "${ctx}/sm/sm008/Add?but=" + but,
				type : "post",
				data : param,
				dataType : "json",
				success : function(oData, oStatus) {
					if (oData.success) {
						showMessage("操作成功");
						window.location.href = "${ctx}/act/task/todo";
					}
				},
				error : function(oData, oStatus, eErrorThrow) {
				}
			});
		}

		var listData = new Array();
		var trIndex = 0;
		function Storage() {
			var selectedCount = 0;
			$("input[name=ckb]:checked").each(function() {
				selectedCount++;
			});
			if (selectedCount == 0) {
				alertx("请至少选择一条物料信息。", "");
				return false;
			}
			var str = "";
			// document.getElementById("addlist").innerText = " ";
			var trIndex = $("#contentTable>tbody>tr:not(.empty)").length;
			$("input[name=ckb]")
					.each(
							function() {
								if ($(this).attr("checked")) {

									var i = $(this).val();
									var data = arr[i];
									for ( var li in listData) {
										if (listData[li].id == data.id) {
											return;
										}
									}
									listData.push(data);
									str += '<tr>'
									str += '<td class="text-center"><a href="javascript:delRow('
											+ trIndex
											+ ');" class="delRow"><i class="icon-minus-sign"></i></a>'
									str += '<input name="smStorageAppDtl[' + trIndex + '][ID]" value="'+data.id+'" type="hidden">'
									/* str += '<input name="smStorageAppDtl[' + trIndex + '][NUM]" value='+data.num+' type="hidden">'
									str += '<input name="smStorageAppDtl[' + trIndex + '][MATERIAL_NO]" value='+data.materialNo+' type="hidden">'
									str += '<input name="smStorageAppDtl[' + trIndex + '][SN_NO]" value='+(typeof (data.snNo) == "undefined" ? "" : data.snNo)+' type="hidden">'
									str += '<input name="smStorageAppDtl[' + trIndex + '][WAREHOUSE]" value='+data.warehouse+' type="hidden">' */
									str += '</td>'
									str += '<td>' + data.materialNo + '</td>'
									str += '<td>' + data.materialName + '　'
											+ data.model + '</td>'
									str += '<td>'
											+ (typeof (data.snNo) == "undefined" ? ""
													: data.snNo) + '</td>'
									str += '<td>'
											+ (typeof (data.productionDate) == "undefined" ? ""
													: data.productionDate)
											+ '</td>'
									str += '<td class="text-right">' + data.num + '</td>'
									str += '<td>' + data.warehouseName
											+ '</td>'
									str += '<td><input name="smStorageAppDtl[' + trIndex + '][TO_NUM]" type="text" class="input-small required text-right digits" min="1" max="' + data.num + '"></td>'
									str += '</tr>'
									trIndex++;

								}
							});
			$("#addlist").append(str);
		}

		var arr;
		$("#snNoBtn")
				.click(
						function() {
							var snNo = $("#querySnNo").val();
							var qureyMaterialNo = $("#qureyMaterialNo").val();
							if ((snNo == null || snNo == "")
									&& (qureyMaterialNo == null || qureyMaterialNo == "")) {
								return;
							}
							$
									.ajax({
										type : "get",
										url : "${ctx}/sm/sm006/queryBySnNo?snNo="
												+ snNo
												+ "&materialNo="
												+ qureyMaterialNo,
										success : function(data) {

											var str = "";
											arr = new Array();
											if (data == null
													|| data.length == 0) {
												$("#list").find("tr").each(
														function() {
															$(this).remove();
														});
												$("#list").find("tr.empty")
														.remove();
												str = '<tr class="empty"><td class="text-center" colspan="7">没有可操作数据</td></tr>';
												$("#list").append(str);
											} else {
												for ( var i in data) {
													str += "<tr><td class=\"text-center\"><input id=\"ckb\" name=\"ckb\" type=\"checkbox\" value=\""+i+"\"></td>";
													str += "<td>"
															+ data[i].materialNo
															+ "</td>";
													str += "<td>"
															+ data[i].materialName
															+ "　"
															+ data[i].model
															+ "</td>";
													str += "<td class=\"text-right\" id='tdNum"+i+"'>"
															+ data[i].num
															+ "</td>";
													str += "<td>"
															+ data[i].warehouseName
															+ "</td>";
													str += "<td>"
															+ (typeof (data[i].snNo) == "undefined" ? ""
																	: data[i].snNo)
															+ "</td>";
													str += "<td>"
															+ (typeof (data[i].productionDate) == "undefined" ? ""
																	: data[i].productionDate)
															+ "</td>";
													arr.push(data[i]);
												}
												str += "</tr>";

												$("#list").find("tr").each(
														function() {
															$(this).remove();
														});
												$("#list").append(str);
											}
										},
										error : function(data) {
										}
									});
						})

		function delRow(strId) {
			$("#contentTable>tbody>tr:eq(" + strId + ")").remove();
			listData.splice(strId, 1);
			$("#contentTable>tbody")
					.find("tr")
					.each(
							function(index) {
								$(this).find("td>a.delRow").attr('href',
										'javascript:delRow(' + index + ');');
								$(this)
										.find("input")
										.each(
												function() {
													if (typeof ($(this)
															.attr("name")) != "undefined") {

														$(this)
																.attr(
																		"name",
																		$(this)
																				.attr(
																						"name")
																				.replace(
																						/\[[0-9]+\]/,
																						"["
																								+ index
																								+ "]"));

													}
												});
								$(this)
										.find("td>select")
										.each(
												function() {
													if (typeof ($(this)
															.attr("name")) != "undefined") {

														$(this)
																.attr(
																		"name",
																		$(this)
																				.attr(
																						"name")
																				.replace(
																						/\[[0-9]+\]/,
																						"["
																								+ index
																								+ "]"));

													}
												});
								$(this)
										.find("td>input")
										.each(
												function() {
													if (typeof ($(this)
															.attr("name")) != "undefined") {
														$(this)
																.attr(
																		"name",
																		$(this)
																				.attr(
																						"name")
																				.replace(
																						/\[[0-9]+\]/,
																						"["
																								+ index
																								+ "]"));
													}
												});
							});
		}
	</script>

</body>
</html>