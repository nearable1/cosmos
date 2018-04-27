<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	 div.ellipsis {
        text-overflow: ellipsis;
        -moz-text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }
	</style>
	<script type="text/javascript">
	$(document).ready(function() {
		
		initPage();
		function initPage() {
		$("#inputForm").validate({
			submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			},
            ignore:""
		});
		}
		
	});
	
	    var busNo = 0;
        $(function(){
        	//$('#contentDiv').hide();
	        $("#addRowInfo").on("click",function(){
	        	if(busNo ==0){
	        		busNo = busNo + $('#tableHead>tbody').find("tr").length;
	        	}
	        	busNo = busNo+1;
	        	var busOpp = 'busNo_'+busNo;
	        	var html = '<tr>'
        		html += '<td class="text-center"><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td><td class="text-center"><span class="rowNo">1</span></td>';
        		html += '<td><select class="required" name="vrVisitDtlList[0].ifLocal" style="width: 100%;">';
        		html += '<option value=""></option>';
        		html += $('#ifLocal').html();
        		html += '</select></td>';
        		html += '<td><input name="vrVisitDtlList[0].address" maxlength="300" type="text" style="width: 100%;"/></td>';
        		html += '<td>';
        		html += '<input name="vrVisitDtlList[0].customerName" id="customer_'+busNo+'" type="text" value=""  onchange="loadBussinessOp(this,\''+busOpp+'\')" ';
        		html += 'maxlength="50" style="width:100%;"';
        		html += 'data-show="text" class="remote customer required customerId input-medium"/>';
        		html += '</td>';
        		html += '<td><select id="'+busOpp+'" name="vrVisitDtlList[0].businessOppNo" style="width: 100%;">';
        		html += '</select></td>';
        		html += '<td><select class="required" name="vrVisitDtlList[0].purpose" style="width: 100%;">';
        		html += '<option value=""></option>';
        		html += $('#purpostList').html();
        		html += '</select></td>'; 
        		html += '<td>';
        		html += $('#dateTemp').html();
        		html += '</td>';
        		html += '</tr>';
	        	
        		$('#tableHead>tbody').append(html);
        		//$('input[name="customer"]:eq(0)').change();
        		$("select:not(.remote)").select2({allowClear: true});
        		$("#customer_"+busNo).select2(getCustSelectOption());
        		
        		var $tbody = $('#tableHead>tbody');
				$tbody.find("tr").each(function(index){ 
	    			$(this).find("td>input").each(function() {
	    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
	    			});; 
	    			$(this).find("td>select").each(function() {
	    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
	    			});; 
	   			});
        		
        		$(".rowNo").each(function(index){ 
        			$(this).html(index+1); 
       			});
	        	
	        });
        })
       
		 function deleteRow(v){
       		$(v).parent().parent().remove();//删除this的父节点div
       		var $tbody = $('#tableHead>tbody');
			$tbody.find("tr").each(function(index){ 
    			$(this).find("td>input").each(function() {
    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
    			});; 
    			$(this).find("td>select").each(function() {
    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
    			});; 
   			});
       		$(".rowNo").each(function(index){ 
    			$(this).html(index+1); 
   			});
       	}	
	    function loadBussinessOp(obj,childId){
	    	var childObj = $('#'+childId);
	    	  childObj.empty();
	    	//getDictChildList('${ctx}',"businessOpp",obj);
	    	 $.ajax({
	    	    	url: '${ctx}/sys/dict/loadBussinessOp',
	    	        type: "get",
	    	        async: false,
	    	        data: {"parentId":obj.value},
	    	        dataType: "json",
	    	        success: function (data) {
	    	            var optionstring = '<option ></option>';
	    	            for (var i = 0; i < data.length; i++) {
	    	                optionstring += "<option value=\"" + data[i].businessOppNo + "\" >"+data[i].model+'	' + data[i].num + "</option>";
	    	            }
	    	            //$('#responsiblePerson').html(optionstring);
	    	            childObj.html(optionstring);
	    	            childObj.select2({allowClear: true});
	    	        },
	    	        error: function (msg) {
	    	            alert(msg);
	    	        }
	    	    });
	    }
	   
	</script>
</head>
<body>
	<h3 class="text-center page-title">拜访申请</h3>
	<sys:message content="${message}"/>
	<form:form id="inputForm" modelAttribute="vrVisit" action="${ctx}/vr/visitingRecord/saveInfo" method="post" class="form-search" novalidate="true">
		<form:hidden path="act.taskId"/>
		<form:hidden path="id"/>
		<form:hidden path="visitNo"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden id="flag" path="act.flag"/>
		<form:hidden path="workflowStatus"/>
		<form:hidden path="updateDate"/>
		
		<%-- <shiro:hasPermission name="vr:visitingRecord:edit">
            <c:set var="isManagerEdit" value="${viewsts.isApprovalCompleted ? true : false}"></c:set>
        </shiro:hasPermission>
        <shiro:lacksPermission name="vr:visitingRecord:edit">
            <c:set var="isManagerEdit" value="false"></c:set>
        </shiro:lacksPermission> --%>
		
	<div class="group-box group-box-first" style="height: auto;">
		<div style="margin-bottom: 5px;">
			<form:hidden path="responsiblePersonId"/>
			申请人：<label>${(fns:getUserById(vrVisit.responsiblePersonId)).name}</label>
		</div>
		<table id="tableHead" style="width:100%" class="table table-striped table-bordered table-condensed" >
			<c:if test="${viewsts.canEdit}">
				<thead>
					<tr>
						<th class="text-center" width="3%"><a style="cursor:pointer;" class="icon-plus-sign" id = "addRowInfo"></a></th>
						<th class="text-center" width="5%">No</th>
						<th width="10%">地点区分</th>
						<th width="15%">地点</th>
						<th width="25%">计划拜访对象</th>
						<th width="20%">商机</th>
						<th width="15%">拜访目的</th>
						<th width="15%">计划拜访时间</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${!empty vrVisit.procInsId}">
					<c:forEach items="${data}" var="rowModel" varStatus="status">
						<tr>
							<td class="text-center"><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>
							<td class="text-center"><span class="rowNo">${rowModel.lineNo} </span>
							<input type="hidden" name="vrVisitDtlList[${status.index}].id" value="${rowModel.id }" />
							<input type="hidden" id="visitNo" name="vrVisitDtlList[${status.index}].visitNo" value="${rowModel.visitNo}" />
							<input type="hidden" name="vrVisitDtlList[${status.index}].lineNo" value="${rowModel.lineNo }" />
							</td>
							<td>
								<select name="vrVisitDtlList[${status.index}].ifLocal" style="width: 100%;">
								<option></option>
									<c:forEach items="${fns:getDictList('DM0041')}" var="visitList">
										<c:if test="${visitList.value == rowModel.ifLocal}">
										<option value="${visitList.value}" selected="selected">${visitList.label}</option>
										</c:if>
										<c:if test="${visitList.value != rowModel.ifLocal}">
										<option value="${visitList.value}">${visitList.label}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							<td><input maxlength="300" type="text" maxlength="300" name="vrVisitDtlList[${status.index}].address"  style="width: 100%;" value="${rowModel.address}" /></td>
							<td>
								<input name="vrVisitDtlList[${status.index}].customerName" type="text" value="${rowModel.customerName}"  onchange="loadBussinessOp(this,'busNo_${status.index}')" 
								   maxlength="50" style="width:100%;" data-show="text"
							       class="remote customer required customerId input-medium"/>
							</td>
							<td><select id="busNo_${status.index}" name="vrVisitDtlList[${status.index}].businessOppNo" style="width: 100%;">
								<option></option>
								<c:forEach items="${fns:getBussinessOpByParent(rowModel.customerName)}" var="dictList">
									<c:if test="${dictList.businessOppNo ==rowModel.businessOppNo}">
										<option value="${dictList.businessOppNo}" selected="selected">${dictList.model} ${dictList.num}</option>
									</c:if>
									<c:if test="${dictList.businessOppNo !=rowModel.businessOppNo}">
										<option value="${dictList.businessOppNo}">${dictList.model} ${dictList.num}</option>
									</c:if>
								</c:forEach>
								</select>
							</td>
							<td>
								<select name="vrVisitDtlList[${status.index}].purpose" style="width: 100%;">
									<option></option>
									<c:forEach items="${fns:getDictList('DM0042')}" var="dictList">
										<c:if test="${dictList.value == rowModel.purpose}">
										<option value="${dictList.value}" selected="selected">${dictList.label}</option>
										</c:if>
										<c:if test="${dictList.value != rowModel.purpose}">
										<option value="${dictList.value}">${dictList.label}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							<td>
								<input name="vrVisitDtlList[${status.index}].expDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:110px;"
								value="${rowModel.expDateFrom}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
							</td>
						</tr>
					
					</c:forEach>
					</c:if>
					<c:if test="${empty vrVisit.procInsId}">
						<tr>
							<td class="text-center"><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>
							<td class="text-center"><span class="rowNo">1</span></td>
							<td><select class="required" name="vrVisitDtlList[0].ifLocal" style="width: 100%;">
								<option></option>
								<c:forEach items="${fns:getDictList('DM0041')}" var="dictList">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:forEach>
							</select></td>
							<td><input name="vrVisitDtlList[0].address" type="text" style="width: 100%;"/></td>
							<td>
								<input name="vrVisitDtlList[0].customerName" type="text" value=""  onchange="loadBussinessOp(this,'busNo_0')" 
								   maxlength="50" style="width:100%;" data-show="text"
							       class="remote customer required customerId input-medium"/>
							</td>
							<td>
							 <select id="busNo_0" name="vrVisitDtlList[0].businessOppNo" style="width: 100%;">
							</select></td>
							<td><select class="required" name="vrVisitDtlList[0].purpose" style="width: 100%;">
								<option></option>
								<c:forEach items="${fns:getDictList('DM0042')}" var="dictList">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:forEach>
							</select></td>
							<td><input name="vrVisitDtlList[0].expDateFrom" type="text" readonly="readonly" 
							maxlength="20" class="input-medium Wdate required" style="width:110px;"
								value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/></td>
						</tr>
					</c:if>
				</tbody>
			</c:if>
			<c:if test="${!viewsts.canEdit}">
				<thead>
					<tr>
						<th class="text-center" width="5%">No</th>
						<th width="10%">地点区分</th>
						<th width="150px">地点</th>
						<th width="25%">计划拜访对象</th>
						<th width="20%">商机</th>
						<th width="15%">拜访目的</th>
						<th width="15%">计划拜访时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${data}" var="rowModel" varStatus="status">
						<tr>
							<td class="text-center">${rowModel.lineNo}</td>
							<td>
								<c:forEach items="${fns:getDictList('DM0041')}" var="addressList">
									<c:if test="${addressList.value == rowModel.ifLocal}">${addressList.label}</c:if>
								</c:forEach>
							</td>
							<td title="${rowModel.address}">
							<div class="ellipsis" style="max-width:150px;">${rowModel.address}</div></td>
							<td>${rowModel.customerChName }
							</td>
							<td>
							<c:forEach items="${fns:getBussinessOpByParent(rowModel.customerName)}" var="dictList" varStatus="status">
								<c:if test="${dictList.businessOppNo ==rowModel.businessOppNo}">${dictList.model} ${dictList.num}</c:if>
							</c:forEach>
							</td>
							<td>
								<c:forEach items="${fns:getDictList('DM0042')}" var="addressList">
									<c:if test="${addressList.value == rowModel.purpose}">${addressList.label}</c:if>
								</c:forEach>
							</td>
							<td>${rowModel.expDateFrom}</td>
						</tr>
					
					</c:forEach>
				</tbody>
			</c:if>
			
			
		</table>
		
		<c:if test="${!viewsts.canApproval}">
        <div class="text-center" style="margin-top:8px;">
            <c:if test="${viewsts.canApply}">
                <input name="opt" id="btnSubmit" type="submit" class="btn btn-primary" value="提交申请">
            </c:if>
            <c:if test="${viewsts.canReapply}">
                <input name="opt" id="btnSubmit" type="submit" class="btn btn-primary" value="再申请">
            </c:if>
            <c:if test="${viewsts.canBack}">
                <input name="opt" id="btnSubmit" type="submit" class="btn btn-primary" value="撤回申请">
            </c:if>
            <c:if test="${viewsts.canDelete}">
                <input name="opt" type="submit" class="btn btn-primary" value="删除">
            </c:if>
            <c:if test="${!empty vrVisit.procInsId}">
                <input class="btn"  type="button" value="返回" onclick="history.go(-1)"/>
            </c:if>
        </div>
        </c:if>
		
	</div>
	 <c:if test="${viewsts.canApproval}">
            <div class="group-box">
                <div class="group-header" >
                    <strong class="group-title">审批</strong>
                </div>

                <ul class="ul-form">
                    <li class="full-width">
                        <label>审批意见：</label>
                        <div style="overflow: hidden;">
                            <form:textarea path="act.comment" rows="3" maxlength="300" style="width:100%;resize: none;" ></form:textarea>
                        </div>
                    </li>
                </ul>

                <div class="text-center">
                    <input name="opt" id="btnPass" type="submit" class="btn btn-primary" value="同意">
                    <input name="opt" id="btnReturn" type="submit" class="btn btn-default" value="退回">
                </div>
            </div>
        </c:if>
	</form:form>
	<div id="contentDiv" style="display: none;">
	<span id="dateTemp">
		<input name="vrVisitDtlList[0].expDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width:110px;"
		value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
	</span>
	<span id="ifLocal">
		<c:forEach items="${fns:getDictList('DM0041')}" var="dictList">
			<option value="${dictList.value}">${dictList.label}</option>
		</c:forEach>
	</span>
	<span id="purpostList">
		<c:forEach items="${fns:getDictList('DM0042')}" var="dictList">
			<option value="${dictList.value}">${dictList.label}</option>
		</c:forEach>
	</span>
	
	</div>
	
	<c:if test="${!empty vrVisit.act.procInsId}">
        <act:histoicFlow procInsId="${vrVisit.act.procInsId}"/>
    </c:if>
</body>
</html>