<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>借出申请</title>
<meta name="decorator" content="default" />
</head>
<!-- <body style="overflow: -Scroll; overflow-x: hidden"> -->
<body>
	<h3 class="text-center page-title">借出申请</h3>
	<!-- <div
		style="padding: 5px 5px; border: 1px solid #ddd; width: 98%; margin-bottom: 5px;"> -->
		<!-- <h3 align="center">借出申请</h3> -->
		<!-- <div
			style="padding: 5px 5px; width: 98%; margin-bottom: 5px; margin: 0 auto;"> -->


<%-- 			<ul class="nav nav-tabs">
				<li class="active"><a href="${ctx}/sm/sm001">借出</a></li>
				<li><a href="${ctx}/sm/sm009"> 换货</a></li>
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
					<li><label>负责人：</label> <input id="responsiblePerson1"
						name="responsiblePerson1" class="input-medium" type="hidden"
						value="${smStorageApp[0].RESPONSIBLE_PERSON_ID}"
						readonly="readonly"> <input
						id="responsiblePerson" name="responsiblePerson"
						class="input-medium" type="text" readonly="readonly"
						value="${user.createBy.name==null?smStorageApp[0].name:user.createBy.name}"
						maxlength="50"></li>
					<li><label style="display: inline-block; width: 100px;">操作日期：</label>
						<input id="" name="" class="input-medium" type="text"
						value="${date}" readonly="readonly" maxlength="50"></li>
					<li><label><font color="red">*</font>借用目的：</label> <input id="lendingType1"
						name="responsiblePerson1" class="select2-choice select2-default"
						type="hidden" value="${smStorageApp[0].LENDING_TYPE}"
						readonly="readonly"> <form:select
							path="lendingType" class="input-medium required">
							<option value=""> </option>
							<form:options items="${fns:getDictList('DM0036')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select> <!-- <label id='lendingTypeErro' style="display: none;" class="error">必填信息</label> -->

					</li>
					<li><label style="display: inline-block; width: 100px;"><font color="red">*</font>客户名称：</label>
						<input id="customerId" name="customerId" class="input-medium required"
						type="text" value="${smStorageApp[0].CUSTOMER_NAME}"
						maxlength="100"></li>
					<li><label>行业：</label> <input id="industry1"
						name="responsiblePerson1"
						class="select2-choice select2-default input-medium" type="hidden"
						value="${smStorageApp[0].INDUSTRY}" readonly="readonly"> <form:select path="industry"
							class="input-medium">
							<option value=""> </option>
							<form:options items="${fns:getDictList('DM0002')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select> <!-- <label id='industryErro' style="display: none;" class="error">必填信息</label> -->

					</li>

					<li><label><font color="red">*</font>借出日期：</label> <input id='lendingDateFrom'
						name="lendingDateFrom" type="text" maxlength="20"
						class="select2-choice select2-default input-medium Wdate required"
						value="${smStorageApp[0].LENDING_DATE_FROM}" readonly="readonly"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd '});" />
						<!-- <label id='lendingDateFromErro' for="lendingDateFrom"
						style="display: none;" class="error">必填信息</label> --></li>
					<li class="full-width">
					    <label>备注说明：</label>
					     <textarea
							id="newRemarks" name="newRemarks" maxlength="300"
							class="fill-right" rows="3">${smStorageApp[0].NEW_REMARKS}</textarea>
					</li>
					<%-- <li style="width: 100%;"><label>备注说明：</label> <textarea
							id="newRemarks" name="newRemarks" maxlength="300"
							style="resize: none; width: calc(80%);" rows="3">${smStorageApp[0].NEW_REMARKS}</textarea>
					</li> --%>
				</ul>
			</form:form>
			</div>
		<!-- </div> -->
		<!-- <div
			style="width: 98%; height: auto; background-color: #000; background: rgba(0, 0, 0, 0);"> -->
			<!-- <div id="listUi"
				style="padding: 5px 5px; border: 1px solid #ddd; width: 100%; height: auto; margin-bottom: 5px; margin-left: 0px; margin-top: 30px; display: none;"> -->
			<c:if test="${viewStatus.canApply||viewStatus.canReapply}">
			<!-- <div id="listUi" class="group-box"> -->
			<div class="group-box">
	            <div class="group-header" >
	                <strong class="group-title">数据添加</strong>
	            </div>
				<!-- <h4>数据添加</h4> -->
				<form:form id="materiaForm" action="" class="breadcrumb form-search">
					<!-- <h4>数据添加</h4> -->
					<ul class="ul-form">
						<li>
							<label>物料号/名称：</label> <input id="qureyMaterialNo" type="text"
								value="" class="remote material input-medium" data-type="1,2,4,5,7"/>
						</li>
					</ul>

					<table id="materiaTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="20px"></th>
								<th>物料号</th>
								<th>物料名称</th>
								<!-- <th>库存数量</th>
								<th>新机</th>
								<th>样机</th>
								<th>库房</th> -->
								<th width="10%">出库数量</th>
							</tr>
						</thead>
						<tbody id="list">

						</tbody>


					</table>
					<div class="text-center">
						<input class="btn btn-primary" id="listBtn" type="button" value="选择" onclick="Storage()">
						<!--  <label id='listBtnErro'
							style="display: none; text-align: center; background-image: url();"
							class="error"></label> -->
					</div>
				</form:form>

			</div>
			</c:if>
		<!-- </div> -->

		<!-- <div
			style="padding: 5px 5px; overflow: scroll; border: 1px solid #ddd; width: 98%; height: auto; margin-bottom: 5px; margin-left: 0x; margin-top: 30px"> -->
			<!-- <h4 style="display: inline-block;">库存数据操作</h4> -->
			<div class="group-box group-box-last">
			<form:form id="contentForm" modelAttribute="contentForm" action="" class="breadcrumb form-search">
                <div class="group-header" >
                    <strong class="group-title">库存数据操作</strong>
                </div>
				<!-- <h4>库存数据操作</h4> -->
				<div style="overflow-x:scroll;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="20px"></th>
							<th>物料号</th>
							<th>物料名称</th>
							<!-- <th>库存数量</th>
							<th>库房</th> -->
							<th>出库数量</th>
							<th>借出到期日</th>
							<th>联系人</th>
							<th>联系方式</th>
							<th width="100px">收货地址</th>
							<th>S/N</th>
							<th>生产日期</th>
							<th width="100px">相关配件备注</th>
						</tr>
					</thead>

					<tbody id="arrList">

						<c:if test="${viewStatus.canApproval}">
							<c:if test="${endApprover}">
								<c:forEach items="${contentForm.smStorageAppDtl}" var="userList"
									varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.ID}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][wId]" value="${userList.wId}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][MATERIAL_NO]" value="${userList.MATERIAL_NO}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][WAREHOUSE]" value="${userList.WAREHOUSE}" type="hidden">
												${status.index+1}
											</td>
											<td>${userList.MATERIAL_NO}</td>
											<td>${userList.MATERIAL_NAME} ${userList.MODEL}</td>
											<%-- <td class="text-right">${userList.wNum}</td>
											<td>${userList.warehouseName}</td> --%>
											<td><input name="smStorageAppDtl[${status.index}][NUM]" class="input-small text-right" type="text" value="${userList.NUM}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" class="input-small" type="text" value="${userList.LENDING_DATE_TO}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][CONTACTS_NAME]" class="input-medium" type="text" value="${userList.CONTACTS_NAME}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][TELEPHONE]" class="input-medium" type="text" value="${userList.TELEPHONE}" readonly="readonly"></td>
											<td title="${userList.ADDRESS}"><input name="smStorageAppDtl[${status.index}][ADDRESS]" class="input-medium" type="text" value="${userList.ADDRESS}" readonly="readonly"></td>
											<td>
												<c:if test="${!empty userList.snYesOrNo}">
													<input name="smStorageAppDtl[${status.index}][SN_NO]" maxlength="50" class="input-medium snNo required" type="text" value="${userList.SN_NO}">
												</c:if>
												<c:if test="${empty userList.snYesOrNo}">
													<input name="smStorageAppDtl[${status.index}][SN_NO]" class="input-medium" type="text" value="${userList.SN_NO}" readonly="readonly">
												</c:if>
											</td>
											<td><input name="smStorageAppDtl[${status.index}][PRODUCTION_DATE]" class="input-small productionDate" type="text" value="${userList.productionDate}" readonly="readonly"></td>
											<td title="${userList.ACCESSORIES_REMARKS}"><input name="smStorageAppDtl[${status.index}][ACCESSORIES_REMARKS]" class="input-medium" type="text" value="${userList.ACCESSORIES_REMARKS}" readonly="readonly"></td>
										</tr>
									<%-- <tr id='tr${status.index}">
										<td>${status.index+1}</td>
										<td id='procInsId' name='procInsId' id='procInsId'>${userList.MATERIAL_NO}</td>
										<td name='materialNo' id='materialNo'>${userList.MATERIAL_NAME}
											${userList.MODEL}</td>
										<td name='num' id='num'>${userList.wNum}</td>
										<td name='warehouse' id='warehouse'>${userList.warehouseName}</td>
										<td name='outNum' id='outNum'>${userList.NUM}<input
											id="toNum" name="toNum" class="input-medium" type="hidden"
											value="${userList.NUM}" readonly="readonly"></td>
										<td><input name="lendingDateTo" type="text"
											readonly="readonly" maxlength="20" class="input-medium Wdate"
											value="${userList.LENDING_DATE_TO}"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" /></td>
										<td><input id="contacts" name="contacts"
											readonly="readonly" class="input-medium" type="text"
											value="${userList.CONTACTS_NAME}" maxlength="30"></td>
										<td><input id="telephone" name="telephone"
											readonly="readonly" class="input-medium" type="text"
											value="${userList.TELEPHONE}" maxlength="30"></td>
										<td><input id="address" name="address"
											readonly="readonly" class="input-medium" type="text"
											value="${userList.ADDRESS}" maxlength="30"></td>
										<td><c:if
												test="${userList.snYesOrNo!=null&&userList.snYesOrNo!=''}">
												<input id="snNo" name="snNo"
													class="input-medium test required" type="text"
													value="${userList. SN_NO}" maxlength="30">
											</c:if> <c:if
												test="${userList.snYesOrNo==null||userList.snYesOrNo==''}">
												<input id="snNo" name="snNo"
													class="input-medium test required" readonly="readonly"
													type="text" value="${userList. SN_NO}" maxlength="30">
											</c:if> <input id="snYesOrNo" name="snYesOrNo"
											class="input-medium test" readonly="readonly" type="hidden"
											type="text" value="${userList. snYesOrNo}">
											<label id='snNoErro${status.index}" style="display: none;"
											class="error">必填信息</label></td>
										<td><input id="productionDate" name="productionDate"
											class="input-medium" type="text" value="${userList.PRODUCTION_DATE}" readonly="readonly"
											maxlength="30"></td>
										<td><input id="accessoriesRemarks"
											name="accessoriesRemarks" class="input-medium" type="text"
											value="${userList.ACCESSORIES_REMARKS}" maxlength="30"></td>
										<td><input id="index" name="index" class="input-medium"
											type="hidden" value="${userList.ID}" readonly="readonly"><input id="wId" name="wId"
											class="input-medium" type="hidden" value="${userList.wId}"
											readonly="readonly"></td>
									</tr> --%>
								</c:forEach>
							</c:if>
							<c:if test="${!endApprover}">
								<c:forEach items="${contentForm.smStorageAppDtl}" var="userList"
									varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.ID}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][wId]" value="${userList.wId}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][MATERIAL_NO]" value="${userList.MATERIAL_NO}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][WAREHOUSE]" value="${userList.WAREHOUSE}" type="hidden">
												${status.index+1}
											</td>
											<td>${userList.MATERIAL_NO}</td>
											<td>${userList.MATERIAL_NAME} ${userList.MODEL}</td>
											<%-- <td class="text-right">${userList.wNum}</td>
											<td>${userList.warehouseName}</td> --%>
											<td><input name="smStorageAppDtl[${status.index}][NUM]" class="input-small text-right" type="text" value="${userList.NUM}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" class="input-small" type="text" value="${userList.LENDING_DATE_TO}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][CONTACTS_NAME]" class="input-medium" type="text" value="${userList.CONTACTS_NAME}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][TELEPHONE]" class="input-medium" type="text" value="${userList.TELEPHONE}" readonly="readonly"></td>
											<td title="${userList.ADDRESS}"><input name="smStorageAppDtl[${status.index}][ADDRESS]" class="input-medium" type="text" value="${userList.ADDRESS}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][SN_NO]" class="input-medium" type="text" value="${userList.SN_NO}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][PRODUCTION_DATE]" class="input-small" type="text" value="${userList.productionDate}" readonly="readonly"></td>
											<td title="${userList.ACCESSORIES_REMARKS}"><input name="smStorageAppDtl[${status.index}][ACCESSORIES_REMARKS]" class="input-medium" type="text" value="${userList.ACCESSORIES_REMARKS}" readonly="readonly"></td>
										</tr>
									<%-- <tr id='tr${status.index}">
										<td>${status.index+1}</td>
										<td id='procInsId' name='procInsId' id='procInsId'>${userList.MATERIAL_NO}</td>
										<td name='materialNo' id='materialNo'>${userList.MATERIAL_NAME}
											${userList.MODEL}</td>
										<td name='num' id='num'>${userList.wNum}</td>
										<td name='warehouse' id='warehouse'>${userList.warehouseName}</td>
										<td name='outNum' id='outNum'>${userList.NUM}<input
											id="toNum" name="toNum" class="input-medium" type="hidden"
											value="${userList.NUM}" readonly="readonly"></td>
										<td><input name="lendingDateTo" type="text"
											readonly="readonly" maxlength="20" class="input-medium Wdate"
											value="${userList.LENDING_DATE_TO}"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd '});" /></td>
										<td><input id="contacts" name="contacts"
											readonly="readonly" class="input-medium" type="text"
											value="${userList.CONTACTS_NAME}" maxlength="30"></td>
										<td><input id="telephone" name="telephone"
											readonly="readonly" class="input-medium" type="text"
											value="${userList.TELEPHONE}" maxlength="30"></td>
										<td><input id="address" name="address"
											readonly="readonly" class="input-medium" type="text"
											value="${userList.ADDRESS}" maxlength="30"></td>
										<td><input id="snNo" name="snNo"
											class="input-medium test" type="text"
											value="${userList. SN_NO}" readonly="readonly" maxlength="30"></td>
										<td><input id="productionDate" name="productionDate"
											class="input-medium" type="text" value="${userList.PRODUCTION_DATE}" readonly="readonly"
											maxlength="30"></td>
										<td><input id="accessoriesRemarks"
											name="accessoriesRemarks" class="input-medium" type="text"
											readonly="readonly" value="${userList.ACCESSORIES_REMARKS}"
											maxlength="30"></td>
										<td><input id="wId" name="wId" class="input-medium"
											type="hidden" value="${userList.ID}" readonly="readonly"><input id="index" name="index"
											class="input-medium" type="hidden" value="${userList.wId}"
											readonly="readonly"></td>
									</tr> --%>
								</c:forEach>
							</c:if>

						</c:if>



						<c:if test="${viewStatus.canReapply}">

							<c:forEach items="${contentForm.smStorageAppDtl}" var="userList"
								varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.ID}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][wId]" value="${userList.wId}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][MATERIAL_NO]" value="${userList.MATERIAL_NO}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][WAREHOUSE]" value="${userList.WAREHOUSE}" type="hidden">
												<%-- ${status.index+1} --%>
												<a href="javascript:delRow(${status.index});" class="delRow"><i class="icon-minus-sign"></i></a>
											</td>
											<td>${userList.MATERIAL_NO}</td>
											<td>${userList.MATERIAL_NAME} ${userList.MODEL}</td>
											<%-- <td class="text-right">${userList.wNum}</td>
											<td>${userList.warehouseName}</td> --%>
											<td><input name="smStorageAppDtl[${status.index}][NUM]" class="input-small text-right" type="text" value="${userList.NUM}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" class="input-small Wdate required" type="text" value="${userList.LENDING_DATE_TO}" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd '});"></td>
											<td><input name="smStorageAppDtl[${status.index}][CONTACTS_NAME]" class="input-medium" maxlength="100" type="text" value="${userList.CONTACTS_NAME}"></td>
											<td><input name="smStorageAppDtl[${status.index}][TELEPHONE]" class="input-medium" maxlength="50" type="text" value="${userList.TELEPHONE}"></td>
											<td title="${userList.ADDRESS}"><input name="smStorageAppDtl[${status.index}][ADDRESS]" maxlength="300" class="input-medium" type="text" value="${userList.ADDRESS}"></td>
											<td><input name="smStorageAppDtl[${status.index}][SN_NO]" class="input-medium" type="text" value="${userList.SN_NO}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][PRODUCTION_DATE]" class="input-small" type="text" value="${userList.productionDate}" readonly="readonly"></td>
											<td title="${userList.ACCESSORIES_REMARKS}"><input name="smStorageAppDtl[${status.index}][ACCESSORIES_REMARKS]" maxlength="300" class="input-medium" type="text" value="${userList.ACCESSORIES_REMARKS}"></td>
										</tr>

								<%-- <tr id='tr${status.index}">
									<td>${status.index+1}</td>
									<td id='procInsId' name='procInsId' id='procInsId'>${userList.MATERIAL_NO}</td>
									<td name='materialNo' id='materialNo'>${userList.MATERIAL_NAME}
										${userList.MODEL}</td>
									<td name='num' id='num'>${userList.wNum}</td>
									<td name='warehouse' id='warehouse'>${userList.warehouseName}</td>
									<td name='outNum' id='outNum'>${userList.NUM}<input
										id="toNum" name="toNum" class="input-medium" type="hidden"
										value="${userList.NUM}"></td>
									<td><input name="lendingDateTo" type="text" maxlength="20"
										class="input-medium Wdate required"
										value="${userList.LENDING_DATE_TO}"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd '});" /><label
										id='lendingDateToErro${status.index}" style="display: none;"
										class="error">必填信息</label></td>
									<td><input id="contacts" name="contacts"
										class="input-medium " type="text"
										value="${userList.CONTACTS_NAME}" maxlength="30"></td>
									<td><input id="telephone" name="telephone"
										class="input-medium" type="text" value="${userList.TELEPHONE}"
										maxlength="30"></td>
									<td><input id="address" name="address"
										class="input-medium" type="text" value="${userList.ADDRESS}"
										maxlength="30"></td>
									<td><input id="snNo" name="snNo" readonly="readonly"
										class="input-medium test" type="text" value="" maxlength="30"></td>
									<td><input id="productionDate" name="productionDate"
										class="input-medium" type="text" value="${userList.PRODUCTION_DATE}" readonly="readonly"
										maxlength="30"></td>
									<td><input id="accessoriesRemarks"
										name="accessoriesRemarks" class="input-medium" type="text"
										readonly="readonly" value="${userList.ACCESSORIES_REMARKS}"
										maxlength="30"></td>
									<td><input id="index" name="index" class="input-medium"
										type="hidden" value="${userList.wId}" readonly="readonly"><input id="wId" name="wId"
										class="input-medium" type="hidden" value="${userList.ID}"
										readonly="readonly"></td>
								</tr> --%>




							</c:forEach>
						</c:if>
						<c:if test="${viewStatus.canBack}">

							<c:forEach items="${contentForm.smStorageAppDtl}" var="userList"
								varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.ID}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][wId]" value="${userList.wId}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][MATERIAL_NO]" value="${userList.MATERIAL_NO}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][WAREHOUSE]" value="${userList.WAREHOUSE}" type="hidden">
												${status.index+1}
											</td>
											<td>${userList.MATERIAL_NO}</td>
											<td>${userList.MATERIAL_NAME} ${userList.MODEL}</td>
											<%-- <td class="text-right">${userList.wNum}</td>
											<td>${userList.warehouseName}</td> --%>
											<td><input name="smStorageAppDtl[${status.index}][NUM]" class="input-small text-right" type="text" value="${userList.NUM}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" class="input-small" type="text" value="${userList.LENDING_DATE_TO}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][CONTACTS_NAME]" class="input-medium" type="text" value="${userList.CONTACTS_NAME}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][TELEPHONE]" class="input-medium" type="text" value="${userList.TELEPHONE}" readonly="readonly"></td>
											<td title="${userList.ADDRESS}"><input name="smStorageAppDtl[${status.index}][ADDRESS]" class="input-medium" type="text" value="${userList.ADDRESS}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][SN_NO]" class="input-medium" type="text" value="${userList.SN_NO}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][PRODUCTION_DATE]" class="input-small" type="text" value="${userList.productionDate}" readonly="readonly"></td>
											<td title="${userList.ACCESSORIES_REMARKS}"><input name="smStorageAppDtl[${status.index}][ACCESSORIES_REMARKS]" class="input-medium" type="text" value="${userList.ACCESSORIES_REMARKS}" readonly="readonly"></td>
										</tr>

								<%-- <tr id='tr${status.index}">
									<td>${status.index+1}</td>
									<td id='procInsId' name='procInsId' id='procInsId'>${userList.MATERIAL_NO}</td>
									<td name='materialNo' id='materialNo'>${userList.MATERIAL_NAME}
										${userList.MODEL}</td>
									<td name='num' id='num'>${userList.wNum}</td>
									<td name='warehouse' id='warehouse'>${userList.warehouseName}</td>
									<td name='outNum' id='outNum'>${userList.NUM}<input
										id="toNum" name="toNum" class="input-medium" type="hidden"
										value="${userList.NUM}" readonly="readonly"></td>
									<td><input name="lendingDateTo" type="text"
										readonly="readonly" maxlength="20" class="input-medium Wdate"
										value="${userList.LENDING_DATE_TO}"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd '});" /></td>
									<td><input id="contacts" name="contacts"
										readonly="readonly" class="input-medium" type="text"
										value="${userList.CONTACTS_NAME}" maxlength="30"></td>
									<td><input id="telephone" name="telephone"
										readonly="readonly" class="input-medium" type="text"
										value="${userList.TELEPHONE}" maxlength="30"></td>
									<td><input id="address" name="address" readonly="readonly"
										class="input-medium" type="text" value="${userList.ADDRESS}"
										maxlength="30"></td>
									<td><input id="snNo" name="snNo" class="input-medium test"
										type="text" value="${userList.SN_NO}" readonly="readonly"
										maxlength="30"></td>
									<td><input id="productionDate" name="productionDate"
										class="input-medium" type="text" value="${userList.PRODUCTION_DATE}" readonly="readonly"
										maxlength="30"></td>
									<td><input id="accessoriesRemarks"
										name="accessoriesRemarks" class="input-medium" type="text"
										readonly="readonly" value="${userList.ACCESSORIES_REMARKS}"
										maxlength="30"></td>
									<td><input id="wId" name="wId" class="input-medium"
										type="hidden" value="${userList.ID}" readonly="readonly"><input id="index" name="index"
										class="input-medium" type="hidden" value="${userList.wId}"
										readonly="readonly"></td>
								</tr> --%>
							</c:forEach>
						</c:if>
						<c:if test="${!viewStatus.canEdit}">
							<c:if test="${!viewStatus.canReapply}">
								<c:if test="${!viewStatus.canApproval}">
									<c:if test="${!viewStatus.canBack}">

										<c:forEach items="${contentForm.smStorageAppDtl}" var="userList"
											varStatus="status">
										<tr>
											<td class="text-center">
												<input name="smStorageAppDtl[${status.index}][ID]" value="${userList.ID}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][wId]" value="${userList.wId}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][MATERIAL_NO]" value="${userList.MATERIAL_NO}" type="hidden">
												<input name="smStorageAppDtl[${status.index}][WAREHOUSE]" value="${userList.WAREHOUSE}" type="hidden">
												${status.index+1}
											</td>
											<td>${userList.MATERIAL_NO}</td>
											<td>${userList.MATERIAL_NAME} ${userList.MODEL}</td>
											<%-- <td class="text-right">${userList.wNum}</td>
											<td>${userList.warehouseName}</td> --%>
											<td><input name="smStorageAppDtl[${status.index}][NUM]" class="input-small text-right" value="${userList.NUM}" type="text" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][LENDING_DATE_TO]" class="input-small" type="text" value="${userList.LENDING_DATE_TO}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][CONTACTS_NAME]" class="input-medium" type="text" value="${userList.CONTACTS_NAME}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][TELEPHONE]" class="input-medium" type="text" value="${userList.TELEPHONE}" readonly="readonly"></td>
											<td title="${userList.ADDRESS}"><input name="smStorageAppDtl[${status.index}][ADDRESS]" class="input-medium" type="text" value="${userList.ADDRESS}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][SN_NO]" class="input-medium" type="text" value="${userList.SN_NO}" readonly="readonly"></td>
											<td><input name="smStorageAppDtl[${status.index}][PRODUCTION_DATE]" class="input-small" type="text" value="${userList.productionDate}" readonly="readonly"></td>
											<td title="${userList.ACCESSORIES_REMARKS}"><input name="smStorageAppDtl[${status.index}][ACCESSORIES_REMARKS]" class="input-medium" type="text" value="${userList.ACCESSORIES_REMARKS}" readonly="readonly"></td>
										</tr>

											<%-- <tr id='tr${status.index}">
												<td>${status.index+1}</td>
												<td id='procInsId' name='procInsId' id='procInsId'>${userList.MATERIAL_NO}</td>
												<td name='materialNo' id='materialNo'>${userList.MATERIAL_NAME}
													${userList.MODEL}</td>
												<td name='num' id='num'>${userList.wNum}</td>
												<td name='warehouse' id='warehouse'>${userList.warehouseName}</td>
												<td name='outNum' id='outNum'>${userList.NUM}<input
													id="toNum" name="toNum" class="input-medium" type="hidden"
													value="${userList.NUM}" readonly="readonly"></td>
												<td><input name="lendingDateTo" type="text"
													readonly="readonly" maxlength="20"
													class="input-medium Wdate"
													value="${userList.LENDING_DATE_TO}" readonly="readonly"
													onclick="WdatePicker({dateFmt:'yyyy-MM-dd '});" /></td>
												<td><input id="contacts" name="contacts"
													readonly="readonly" class="input-medium" type="text"
													value="${userList.CONTACTS_NAME}" readonly="readonly"
													maxlength="30"></td>
												<td><input id="telephone" name="telephone"
													readonly="readonly" class="input-medium" type="text"
													value="${userList.TELEPHONE}" readonly="readonly"
													maxlength="30"></td>
												<td><input id="address" name="address"
													readonly="readonly" class="input-medium" type="text"
													value="${userList.ADDRESS}" readonly="readonly"
													maxlength="30"></td>
												<td><input id="snNo" name="snNo"
													class="input-medium test" type="text"
													value="${userList. SN_NO}" readonly="readonly"
													maxlength="30"></td>
												<td><input id="productionDate" name="productionDate"
													class="input-medium" type="text" value="${userList.PRODUCTION_DATE}"
													readonly="readonly" maxlength="30"></td>
												<td><input id="accessoriesRemarks"
													name="accessoriesRemarks" class="input-medium" type="text"
													readonly="readonly" value="${userList.ACCESSORIES_REMARKS}"
													maxlength="30"></td>
												<td><input id="wId" name="wId" class="input-medium"
													type="hidden" value="${userList.ID}" readonly="readonly"><input id="index" name="index"
													class="input-medium" type="hidden" value="${userList.wId}"
													readonly="readonly"></td>
											</tr> --%>
										</c:forEach>
									</c:if>
								</c:if>
							</c:if>
						</c:if>
					</tbody>
				</table>
				</div>
			</form:form>
			</div>
		<!-- </div> -->
	<!-- </div> -->
	<!-- <div id="messageBox" class="alert alert-error hide"
		style="display: none;">
		<button data-dismiss="alert" class="close">×</button>
	</div> -->
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
<!-- 	<div id="messageBox" class="alert alert-success hide"
		style="display: none; width: 98%;">
		<button data-dismiss="alert" class="close">×</button>
	</div> -->
	<sys:message content="${message}"/>
	<c:if test="${!empty user1.act.procInsId}">
		<act:histoicFlow procInsId="${user1.act.procInsId}" />
	</c:if>
	<script type="text/javascript">

	$(document).ready(function() {
		
		initPage();
		
		function initPage() {
			var responsiblePerson =$("#responsiblePerson").val();
			var responsiblePerson1 =$("#responsiblePerson1").val();
		    var btnSubmit = document.getElementById("btnSubmit");
		    var eaaSubmit = document.getElementById("eaaSubmit");
		    var refuseSubmit = document.getElementById("refuseSubmit");
		    var lendingType =$("#lendingType1").val();
		    var industry = $("#industry1").val();
		   $("#lendingType").select2('val',lendingType);
		   $("#industry").select2('val',industry);

		   //var ui =document.getElementById("listUi");
		   if("${viewStatus.canApply}"=="true"||"${viewStatus.canReapply}"=="true")
		   {
			   //ui.style.display="block";
		  
			   $("#customerId").removeAttr("readOnly"); 
			   $("#newRemarks").removeAttr("readOnly"); 
			   $("#industry").removeAttr("disabled"); 
			   $("#lendingType").removeAttr("disabled"); 
			   $("#lendingDateFrom").attr("onclick", "WdatePicker({dateFmt:'yyyy-MM-dd'});");
				//等于空 申请状态
		   } else {
			   //ui.style.display="none";
			   $("#customerId").attr("readOnly","true");
			   $("#newRemarks").attr("readOnly","true");
			   $("#industry").attr("disabled","true");
			   $("#lendingType").attr("disabled","true");
			   $("#lendingDateFrom").removeAttr("onclick");
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
			// 添加画面验证
			$("#materiaForm").validate({
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
	top.$.jBox.closeTip();
	var treeData;
	var but = "0";
	
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
				var yseOrNo = false;
				if(but=="0"||but=="3") {

					if (!$("#searchForm").valid()) {
						yseOrNo=true;
					}
					if (!$("#contentForm").valid()) {
						yseOrNo=true;
					}
				} else if (but=="1") {

					if (!$("#contentForm").valid()) {
						yseOrNo=true;
					}
				}
				if(yseOrNo)
			    	return;
				//erro1();
				//alert($("#searchForm").valid());
				//if(!$("#searchForm").valid())
				//{
				//	//alert("为空");
				//	return;
				//}
				var searchForm= $("#searchForm").serialize();//数据序列化
				var contentForm = $("#contentForm").serialize(); 
/* 				var lendingType = $("#lendingType").val();
				var industry = $("#industry").val(); 
				var lendingDateFrom =$("#lendingDateFrom").val();  */
				var param=searchForm+"&"+contentForm;
				var comment = $("#comment").val(); 
				if (typeof(comment) != "undefined") {
					param=param+"&comment="+comment;
				}
				//var param=searchForm+"&"+contentForm+"&comment="+comment;//"lendingDateTo="+lendingDateTo+"&contacts="+contacts+"&telephone="+telephone+"&address="+address+"&snNo="+snNo+"&accessoriesRemarks="+accessoriesRemarks+"&index="+index;
				/* if(lendingType==""||lendingType==null)
				{
					erro("必填信息","lendingTypeErro");
					yseOrNo=true;
				}
				if(industry==""||industry==null)
				{
					erro("必填信息","industryErro");
					yseOrNo=true;
				}
				if(lendingDateFrom==""||lendingDateFrom==null)
				{
					erro("必填信息","lendingDateFromErro");
					yseOrNo=true;
				}
				if(yseOrNo)
			    	return; */
				
				  /* var lendingDateTo = document.getElementsByName("lendingDateTo");
				  var contacts = document.getElementsByName("contacts");
				  var telephone = document.getElementsByName("telephone");
				  var address = document.getElementsByName("address");
				  var snNos = document.getElementsByName("snNo");
				  var snYesOrNo= document.getElementsByName("snYesOrNo");

				  var sn = new Array();
				  	for(var i=0;i<snNos.length;i++)
				  	{
				  		sn.push(snNos[i].value);
				  	}
				  	var nary=sn.sort();
				  	
				  	for(var i=0;i<sn.length;i++){
				  		if (nary[i]==nary[i+1]){
				  			if(nary[i]!=""){
						 erro("S/N："+nary[i]+"重复","SubmitErro");
						 yseOrNo=true;
				  			
				  		}
				  		}	
				  	}
				  	
				    str = "";
				    
				    if(but=="0"||but=="2"||but=="3"||but=="4"||but=="5")
				    {
				    	if(but=="0"||but=="3")
				    	{
				    		for(var i=0;i<lendingDateTo.length;i++)
						  	{
				    			erroNone("lendingDateToErro"+i);
					    		if(lendingDateTo[i].value==""||lendingDateTo[i].lenght==0)
					    		{
									 erro("不能为空","lendingDateToErro"+i);
									 yseOrNo=true;
					    			
					    		}
						  	}
				    	}
				    }else if("${endApprover}"=="true")
				    {
				    	for(var i=0;i<snNos.length;i++)
					  	{
				    		document.getElementById("snNoErro"+i).style.display = "none";
				    		if(snNos[i].value==""||snNos[i].lenght==0)
				    		{
				    			if(snYesOrNo[i].value!=""&&snYesOrNo.lenght!=0){

								 erro("不能为空","snNoErro"+i);
								 yseOrNo=true;
				    			}
				    		}
					  	}
				    }
				    if(yseOrNo)
				    	return; */
					//loading('请稍等');
					$.ajax({
						url: "${ctx}/sm/sm001/Add?but="+but,
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
				    /* $.ajax({
			             type: "get",
			             url: "${ctx}/sm/sm001/snNoYseOrNo?but="+but,
			             data: param,
			             dataType: "json",
			             success: function(data){
								if(data||but=="0"||but=="2"||but=="3"||but=="4"||but=="5")
								{
									butTrue(); 
									$.ajax({
							             type: "post",
							             url: "${ctx}/sm/sm001/Add?but="+but,
							             data: param,
							             dataType: "json",
							             success: function(data){
												
							            	 	loading('操作成功');
												//$("#messageBox").text("保存成功");
											//	$("#messageBox").show();
							            //	 document.getElementById("contentForm").submit();
							            	 //alert("操作成功");
							            	 if(but!=0){
							            		  window.location.href = "${ctx}/act/task/todo";
							            	 }else
							            	 {
							            		  window.location.href = "${ctx}/act/task/todo";
							            	 }
							            	

							                      },
							             error:function(data) {
												$("#btnSubmit").attr("disabled", false);
												butFalse();
												loading('录入失败');
												$("#messageBox").text("操作失败！请检查数据的正确性");
												$("#messageBox").show();

												setTimeout(function(){
													 var div= $('#jbox', parent.document)
													 div.hide();
													},1000);
												
												
							                }
							         });
									
								}else
								{
				
									 erro("SN已借出或被占用！","SubmitErro");

								}
			                      },
			             error:function(data) {
								loading('录入失败');
								$("#messageBox").text("操作失败！请检查数据的正确性");
								$("#messageBox").show();
								 setTimeout(function(){
									 var div= $('#jbox', parent.document)
									 div.hide();
									},1000);
								//var div1=window.parent.document.getElementById("jbox");
								//var i =document.getElementById("jbox");
								//div.hide();
								//div1.style.display="none";
			                }
			         }); */
				    
				
			}
			
	var agreementIndex = 0;
	//查询下标
	var queryIndex = 0;
	var arr = new Array();
	$("#qureyMaterialNo").click(function()
			{
		var qureyMaterialNo = $("#qureyMaterialNo").val();
		if (qureyMaterialNo == null || qureyMaterialNo == "") {
			return;
		}
		$.ajax({
            type: "get",
            url: "${ctx}/sm/sm001/queryByMaterial?qureyMaterialNo="+qureyMaterialNo,
            dataType: "json",
			success: function(data){
				
				var str = "";
				var num=0;
				if (data != null && data.length > 0) {
				for(var i in arr)
				{
					if(arr[i].material==data[0].material){
						return;
					}
				}
				}
				var arrData = new Array();
        		for(var i in data)
        		{	
        			str+="<tr><td class=\"text-center\"><input id=\"ckb\" name=\"ckb\" type=\"checkbox\" value=\""+arr.length+"\"></td>";
    				str+="<td>"+data[i].material+"</td>";
    				str+="<td>"+data[i].materialName+"　"+data[i].model+"</td>";
/*     				str+="<td class=\"text-right\" id='tdNum"+arr.length+"'>"+data[i].allNum+"</td>";
    				str+="<td class=\"text-right\">"+(data[i].newNum==0?'-':data[i].newNum)+"</td>";
    				str+="<td class=\"text-right\">"+(data[i].usedNum==0?'-':data[i].usedNum)+"</td>";
    				str+="<td>"+data[i].warehouseName+"</td>"; */
    				str+="<td><input readOnly=\"true\" type=\"text\" class=\"full-width text-right digits\" name=\"num"+arr.length+"\" id=\"num"+arr.length+"\" max='"+data[i].allNum+"' min='1' /></td>";
    			str+="</tr>";
    			arr.push(data[i]);
        		queryIndex++;
        			
        			}

        			
        		if (str == "") {
        			$("#list").find("tr.empty").remove();
        			str = '<tr class="empty"><td class="text-center" colspan="8">物料：' + qureyMaterialNo + '没有可操作数据</td></tr>';
        			$("#list").append(str);
        		} else {
        			$("#list").append(str).find("tr.empty").remove();
        		}
                     },
            error:function(data) {
				loading('');
               }
        });
		agreementIndex++;
			});
	
			
			
	//var trIndex =0;
	/* function delRow(strId)
	{
		$("#"+strId).remove();
	} */
	
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
				 var snNos = document.getElementsByName("snNo");
				 var productionDates = document.getElementsByName("productionDate");
				 for(var i = 0;i<snNos.length;i++)
				 {
					 if(snNos[i].value!=""&&snNos[i].value!=null&&"${viewStatus.canApproval}"=="true")
					 {
						 
					 }else{
						 continue;
					 }
					 if(snNos[i].value==snNo)
					 {
						 if(data.str!=null&&data.str!=""&&data.str!="undefined"){
							 productionDates[i].value=data.str;
							 continue;
						 }else
						 {
							 
							 erro("输入SN有误，请确认","SubmitErro");
							 continue;
						 }
						
					 }
				 }
			 },error:function(data) {
				 erro("录入失败：","SubmitErro");

             }
				});
			
    }); */
	
	$(document).on("change", "input[name=ckb]", function(e) {
		var i = $(this).val();
		var $row = $(this).parent().parent();
		if ($(this).attr("checked")) {
        	$row.find(("#num"+i)).addClass("required");
        	$row.find(("#num"+i)).removeAttr("readOnly");
		} else {
			$row.find(("#num"+i)).val("");
			$row.find(("#num"+i)).removeClass("required");
        	$row.find(("#num"+i)).attr("readOnly","true");
		}
	});
	
    var listData = new Array();
	function Storage()
		{
			var selectedCount = 0;
			$("input[name=ckb]:checked").each(function(){
				selectedCount++;
		    });
			if(selectedCount == 0){
				alertx("请至少选择一条物料信息。","");
				return false;
			}
			if (!$("#materiaForm").valid()) {
				return false;
			}
			var str= "";
			// var YseOrNo = true;
			//var tdobj;
			//document.getElementById("arrList").innerText=" ";
			// document.getElementById("listBtnErro").style.display = "none";
			var trIndex = $("#contentTable>tbody>tr:not(.empty)").length;
		        $("input[name=ckb]").each(function() {  
		            if ($(this).attr("checked")) {  

		            	var i = $(this).val();
		            	//tdobj = document.getElementById('tdNum'+i).innerText;
		            	var num = parseInt($("#num"+i).val());
		                var data = arr[i];
		                /* if(num==null||num==""||isNaN(num))
		                {
		                	erro("请输入出库数量","listBtnErro");
		                	YseOrNo=true;
		                	return ;
		                }
		                if(parseInt(num)<1||parseInt(num)>parseInt(tdobj))
		                {
		                	erro("出库数量不能大于库存数量","listBtnErro");
		                	YseOrNo =true;
		                	return;
		                } */
						for ( var li in listData) {
							if (listData[li].id == data.id) {
								return;
							}
						}
						listData.push(data);
		                // YseOrNo=false;
		                if(data.snNo==null||data.snNo==""||data.snNo=="0"){
		                	
							 /* str+="<tr><td>"+(trIndex+1)+"</td><td name='procInsId' id='procInsId'>"+data.material+"</td>";
							 str+="<td name='materialNo' id='materialNo'>"+data.materialName+"　"+data.model+"</td>";
							 str+="<td name='num' id='num'>"+data.allNum+"</td>";
							 str+="<td name='warehouse' id='warehouse'>"+data.warehouseName+"</td>";
							 str+="<td name='materialNo' id='materialNo'>"+num+"</td>";
							 str+="<td><input name=\"lendingDateTo\" type=\"text\"  maxlength=\"20\" class=\"input-medium Wdate required\"value=\"\"onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd '});\"/><label id='lendingDateToErro"+trIndex+"'  style=\"display: none;\" class=\"error\">必填信息</label></td>";
							 str+="<td>	<input id=\"contacts\" name=\"contacts\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\"  maxlength=\"30\"></td>";
							 str+="<td>	<input id=\"telephone\" name=\"telephone\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\"  maxlength=\"30\"></td>";
							 str+="<td>	<input id=\"address\" name=\"address\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\"  maxlength=\"30\"></td>";
							 str+="<td>	<input id=\"snNo\" name=\"snNo\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\"  readonly=\"readonly\" maxlength=\"30\"></td>";
							 str+="<td>	<input id=\"\" name=\"\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\" readonly=\"readonly\" maxlength=\"30\"></td>";
							 str+="<td>	<input id=\"accessoriesRemarks\" name=\"accessoriesRemarks\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\"readonly=\"readonly\"  maxlength=\"30\"></td>";
							 str+="<td>	<input id=\"index\" name=\"index\" class=\"input-medium\" type=\"hidden\" value='"+data.id+"' readonly=\"readonly\"><input id=\"toNum\" name=\"toNum\" class=\"input-medium\" type=\"hidden\" value='"+num+"' readonly=\"readonly\"></td>";
							 str+="</tr>"; */
		                	
							 str+='<tr><td class="text-center"><a href="javascript:delRow(' + trIndex + ');" class="delRow"><i class="icon-minus-sign"></i></a>';
							 str+='<input name="smStorageAppDtl[' + trIndex + '][ID]" value="'+data.id+'" type="hidden">';
							 str+='<input name="smStorageAppDtl[' + trIndex + '][MATERIAL_NO]" value="'+data.material+'" type="hidden">';
							 str+='<input name="smStorageAppDtl[' + trIndex + '][WAREHOUSE]" value="'+data.warehouse+'" type="hidden"></td><td>'+data.material+'</td>';
							 str+='<td>'+data.materialName+'　'+data.model+'</td>';
							 // str+='<td class="text-right">'+data.allNum+'</td>';
							 // str+='<td>'+data.warehouseName+'</td>';
							 str+='<td><input name="smStorageAppDtl[' + trIndex + '][NUM]" type="text" class="input-small text-right" value="'+num+'" readonly="readonly"></td>';
							 str+='<td><input name="smStorageAppDtl[' + trIndex + '][LENDING_DATE_TO]" type="text" class="input-small Wdate required" readonly="readonly" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'});"></td>';
							 str+='<td><input name="smStorageAppDtl[' + trIndex + '][CONTACTS_NAME]" maxlength="100" type="text" class="input-medium"></td>';
							 str+='<td><input name="smStorageAppDtl[' + trIndex + '][TELEPHONE]" maxlength="50" type="text" class="input-medium"></td>';
							 str+='<td><input name="smStorageAppDtl[' + trIndex + '][ADDRESS]" maxlength="300" type="text" class="input-medium"></td>';
							 str+='<td><input name="smStorageAppDtl[' + trIndex + '][SN_NO]" type="text" class="input-medium" readonly="readonly"></td>';
							 str+='<td><input name="smStorageAppDtl[' + trIndex + '][PRODUCTION_DATE]" type="text" class="input-small" readonly="readonly"></td>';
							 str+='<td><input name="smStorageAppDtl[' + trIndex + '][ACCESSORIES_REMARKS]" type="text" maxlength="300" class="input-medium"></td>';
							 str+="</tr>";
							 trIndex++;
							 }else
							 {
								for(var j = 0;j<num;j++)
								{

									 str+='<tr><td class="text-center"><a href="javascript:delRow(' + trIndex + ');" class="delRow"><i class="icon-minus-sign"></i></a>';
									 str+='<input name="smStorageAppDtl[' + trIndex + '][ID]" value="'+data.id+'" type="hidden">';
									 str+='<input name="smStorageAppDtl[' + trIndex + '][MATERIAL_NO]" value="'+data.material+'" type="hidden">';
									 str+='<input name="smStorageAppDtl[' + trIndex + '][WAREHOUSE]" value="'+data.warehouse+'" type="hidden"></td><td>'+data.material+'</td>';
									 str+='<td>'+data.materialName+'　'+data.model+'</td>';
									 //str+='<td class="text-right">'+data.allNum+'</td>';
									 //str+='<td>'+data.warehouseName+'</td>';
									 str+='<td><input name="smStorageAppDtl[' + trIndex + '][NUM]" class="input-small text-right" value="1" type="text" readonly="readonly"></td>';
									 str+='<td><input name="smStorageAppDtl[' + trIndex + '][LENDING_DATE_TO]" type="text" class="input-small Wdate required" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'});"></td>';
									 str+='<td><input name="smStorageAppDtl[' + trIndex + '][CONTACTS_NAME]" maxlength="100" type="text" class="input-medium"></td>';
									 str+='<td><input name="smStorageAppDtl[' + trIndex + '][TELEPHONE]" maxlength="50" type="text" class="input-medium"></td>';
									 str+='<td><input name="smStorageAppDtl[' + trIndex + '][ADDRESS]" maxlength="300" type="text" class="input-medium"></td>';
									 str+='<td><input name="smStorageAppDtl[' + trIndex + '][SN_NO]" type="text" class="input-medium" readonly="readonly"></td>';
									 str+='<td><input name="smStorageAppDtl[' + trIndex + '][PRODUCTION_DATE]" type="text" class="input-small" readonly="readonly"></td>';
									 str+='<td><input name="smStorageAppDtl[' + trIndex + '][ACCESSORIES_REMARKS]" maxlength="300" type="text" class="input-medium"></td>';
									 str+="</tr>";
									 
									 /* str+="<tr><td>"+(trIndex+1)+"</td><td name='procInsId' id='procInsId'>"+data.material+"</td>";
									 str+="<td name='materialNo' id='materialNo'>"+data.materialName+"　"+data.model+"</td>";
									 str+="<td name='num' id='num'>"+data.allNum+"</td>";
									 str+="<td name='warehouse' id='warehouse'>"+data.warehouseName+"</td>";
									 str+="<td name='materialNo' id='materialNo'>1</td>";
									 str+="<td><input name=\"lendingDateTo\" type=\"text\"  maxlength=\"20\" class=\"input-medium Wdate required\"value=\"\"onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd '});\"/><label id='lendingDateToErro"+trIndex+"' style=\"display: none;\"  for=\"lendingDateTo\" class=\"error\">必填信息</label></td>";
									 str+="<td>	<input id=\"contacts\" name=\"contacts\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\"  maxlength=\"30\"></td>";
									 str+="<td>	<input id=\"telephone\" name=\"telephone\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\"  maxlength=\"30\"></td>";
									 str+="<td>	<input id=\"address\" name=\"address\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\"  maxlength=\"30\"></td>";
									 str+="<td>	<input id=\"snNo\" name=\"snNo\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\"  readonly=\"readonly\" maxlength=\"30\"></td>";
									 str+="<td>	<input id=\"\" name=\"\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\" readonly=\"readonly\" maxlength=\"30\"></td>";
									 str+="<td>	<input id=\"accessoriesRemarks\" name=\"accessoriesRemarks\" class=\"input-medium\" type=\"text\" value=\"${cm.lastVisitDate}\" readonly=\"readonly\" maxlength=\"30\"></td>";
									 str+="<td>	<input id=\"index\" name=\"index\" class=\"input-medium\" type=\"hidden\" value='"+data.id+"' readonly=\"readonly\"><input id=\"toNum\" name=\"toNum\" class=\"input-medium\" type=\"hidden\" value='1' readonly=\"readonly\"></td>";
									 str+="</tr>"; */
									 trIndex++;
								}
							 }
							
		            }  
		        });
			
		    /* if(tdobj==null){
            	erro("请选择需要操作的物料信息","listBtnErro");
 		
				return
		   	} */
				//var str ="<tr id=\"agreement"+agreementIndex+"\"><td><a href=\"javascript:delRow("+agreementIndex+");\" class=\"delRow\"><i class=\"icon-minus-sign\"></i></a></td><td class=\"text-center\" width=\"10\"><span name=\"agreementNo\" id=\"agreementNo\">"+agreementIndex+"</span></td><td><input id=\"validityDateFrom\" name=\"validityDateFrom\" type=\"text\" readonly=\"readonly\" maxlength=\"20\" class=\"input-medium Wdate \" value=\"\" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\"></td><td><input id=\"validityDateTo\" name=\"validityDateTo\" type=\"text\" readonly=\"readonly\" maxlength=\"20\" class=\"input-medium Wdate\" value=\"\" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\"></td><td><input id=\"newRemarks\" name=\"newRemarks\" type=\"text\" value=\"\" maxlength=\"50\" class=\"input-small required\"></td></tr>";
			//if(!YseOrNo){
			//$("#listBtn").attr("disabled", true);
			$("#arrList").append(str);
			//}
				
        //$("#agreement"+index).remove();
        }
	
	function delRow(strId) {
		$("#contentTable>tbody>tr:eq(" + strId + ")").remove();
		listData.splice(strId, 1);
		$("#contentTable>tbody").find("tr").each(function(index){ 
			$(this).find("td>a.delRow").attr('href','javascript:delRow(' + index + ');'); 
			$(this).find("input").each(function() {
				if (typeof($(this).attr("name")) != "undefined") {

					$(this).attr("name", $(this).attr("name").replace(/\[[0-9]+\]/, "[" + index + "]"));

				}
			});
			$(this).find("td>select").each(function() {
				if (typeof($(this).attr("name")) != "undefined") {

					$(this).attr("name", $(this).attr("name").replace(/\[[0-9]+\]/, "[" + index + "]"));

				}
			});
			$(this).find("td>input").each(function() {
				if (typeof($(this).attr("name")) != "undefined") {
    				$(this).attr("name", $(this).attr("name").replace(/\[[0-9]+\]/, "[" + index + "]"));
				}
			});
		});
	}

	/* function getCity()
	{
		$("#city").empty();
		$("#province").find("option:selected").text();
		var index=-1;
		var cityStr ="";
		var city=$("#province").find("option:selected").text();

			for(var ct in treeData)
			{
				
				if(treeData[ct].name==city&&treeData[ct].pId==1)
				{
					index=treeData[parseInt(ct)+parseInt(1)].pId;
				}
				if(index==treeData[ct].pId){
					cityStr+="<option  value='"+treeData[ct].id+"' >"+treeData[ct].name+"</option>";
				}
			}
			var checkText=$("#select_id").find("option:selected").text("  ");
			$("#city").append(cityStr);
	} */
	
	/* window.onload=function(){ 
		var responsiblePerson =$("#responsiblePerson").val();
		var responsiblePerson1 =$("#responsiblePerson1").val();
	    var btnSubmit = document.getElementById("btnSubmit");
	    var eaaSubmit = document.getElementById("eaaSubmit");
	    var refuseSubmit = document.getElementById("refuseSubmit");
	    var lendingType =$("#lendingType1").val();
	    var industry = $("#industry1").val();
	   $("#lendingType").select2('val',lendingType);
	   $("#industry").select2('val',industry);
	   
	   if("${viewStatus.canApply}"=="true"||"${viewStatus.canReapply}"=="true")
	   {
		   var ui =document.getElementById("listUi");
		   ui.style.display="block";
	  
		   $("#customerId").removeAttr("readOnly"); 
		   $("#newRemarks").removeAttr("readOnly"); 
		   $("#industry").removeAttr("disabled"); 
		   $("#lendingType").removeAttr("disabled"); 
		   $("#lendingDateFrom").attr("onclick", "WdatePicker({dateFmt:'yyyy-MM-dd'});");
			//等于空 申请状态
	   } else {
		   $("#customerId").attr("readOnly","true");
		   $("#newRemarks").attr("readOnly","true");
		   $("#industry").attr("disabled","true");
		   $("#lendingType").attr("disabled","true");
		   $("#lendingDateFrom").removeAttr("onclick");
	   }
	} */
	
	/* function erro(erro,id)
	{
		if(but=="2"||but=="4"||but=="5")
		{
			return;
		}else
		{
			var e = document.getElementById(id);
			e.innerHTML=erro;
			e.style.display = "block";
		}
		
	} */
	
	/* function erroNone(id)
	{
		document.getElementById(id).style.display = "none";
	} */
	
	/* function erro1()
	{
		document.getElementById("lendingTypeErro").style.display = "none";
		document.getElementById("industryErro").style.display = "none";
		document.getElementById("lendingDateFromErro").style.display = "none";
	//	document.getElementById("listBtnErro").style.display = "none";
		document.getElementById("SubmitErro").style.display = "none";
	} */
	
	
	/* function butTrue()
	{
		//alert("锁定按键");
		$("#btnSubmit").attr("disabled", true);
		$("#eaaSubmit").attr("disabled", true);
		$("#refuseSubmit").attr("disabled", true);
		$("#withdrawSubmit").attr("disabled", true);
		$("#againSubmit").attr("disabled", true);
		$("#deteteSubmit").attr("disabled", true);
	} */
	
	/* function butFalse()
	{
		//alert("按键");
		$("#btnSubmit").attr("disabled", false);
		$("#eaaSubmit").attr("disabled", false);
		$("#refuseSubmit").attr("disabled", false);
		$("#withdrawSubmit").attr("disabled", false);
		$("#againSubmit").attr("disabled", false);
		$("#deteteSubmit").attr("disabled", false);
	} */
		
	
	</script>

</body>
</html>