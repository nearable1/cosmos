<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	.auto-scroll-x {
	width: 100%;
	overflow-x: auto;
	overflow-y: hidden;
	margin-bottom: 8px;
	}
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
		
		// 物料号change事件绑定
        $(document).on("change", "select.ifVisit", function(e) {
        	var $noInput = $(this);
            var $nameInput = $noInput.parent().parent().find("input.actDateFrom");
            if (e.val == 1) {
                $nameInput.attr("required",true);
            } else {
            	$nameInput.removeAttr("required");
            } 
        });
		
	});
        $(function(){
        	 //调用 锁定表头和 列 的JS函数  
	       //FixTable("tableHead", 3, 1030, 200);
        	// 删除添加行
			$(document).on("click", "table .delRow", function() {
				var $tbody = $(this).parents("tbody");
				$(this).parents("tr").remove();
				$(".rowNo").each(function(index){ 
        			$(this).html(index+1); 
        			$('.lineNo').eq(index).val(index+2);
       			}); 
				$tbody.find("tr").each(function(index){ 
	    			$(this).find("td>input").each(function() {
	    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
	    			});; 
	    			$(this).find("td>select").each(function() {
	    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
	    			});; 
	   			});
			});
			var busNo = 0;
        	//$('#contentDiv').hide();
	        $("#addRowInfo").on("click",function(){
	        	if(busNo ==0){
	        		busNo = busNo + $('#tableHead>tbody').find("tr").length;
	        	}
	        	busNo = busNo+1;
	        	var busOpp = 'busNo_'+busNo;
	        	var html = '<tr>'
        		html += '<td class="text-center" class="delRow"><a style="cursor:pointer;" onclick = "deleteRow(this)" class="icon-minus-sign "></a></td>';
        		html += '<td class="text-center"><span class="rowNo">1</span><input type="hidden" name="vrVisitDtlList[0].visitNo" value="'+$('#visitNo').val()+'" /><input type="hidden" class="lineNo" name="vrVisitDtlList[0].lineNo" value="" /></td>';
        		html += '<td><select class="required" name="vrVisitDtlList[0].ifLocal" style="width: 80px;">';
        		html += '<option value=""></option>';
        		html += $('#ifLocal').html();
        		html += '</select></td>';
        		html += '<td><input name="vrVisitDtlList[0].address" maxlength="300" type="text" style="width: 100px;"/></td>';
        		html += '<td>';
        		html += '<input name="vrVisitDtlList[0].customerName" id="customer_'+busNo+'" type="text" value=""  onchange="loadBussinessOp(this,\''+busOpp+'\')" ';
        		html += 'maxlength="50" style="width:230px;"';
        		html += 'data-show="text" class="remote customer required customerId input-medium"/>';
        		html += '</td>';
        		html += '<td><select name="vrVisitDtlList[0].businessOppNo" id="'+busOpp+'" style="width: 200px;">';
        		html += '</select></td>';
        		html += '<td><select class="required" name="vrVisitDtlList[0].purpose" style="width: 150px;">';
        		html += '<option></option>';
        		html += $('#purpostList').html();
        		html += '</select></td>';
        		html += '<td><select class="required ifVisit" name="vrVisitDtlList[0].ifVisit" style="width: 65px;">';
        		html += '<option></option>';
        		html += $('#yesNoList').html();
        		html += '</select></td>';
        		html += '<td>';
        		html += $('#dateTemp').html();
        		html += '</td>';
        		html += '<td><input maxlength="300" class="required" name="vrVisitDtlList[0].newRemarks" type="text" style="width: 150px;"/></td>';
        		html += '<td></td>';
        		html += '<td></td>';
        		html += '</tr>';
	        	
        		$('#tableHead>tbody').append(html);
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
        			$('.lineNo').eq(index).val(index+2);
       			}); 
        		//FixTable("tableHead", 3, 1030, $('#tableHead').height()+50);
	        	
	        });
	      
        })
        
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
	    	            var optionstring = '<option></option>';
	    	            for (var i = 0; i < data.length; i++) {
	    	                optionstring += "<option value=\"" + data[i].businessOppNo + "\" >" + data[i].model +'	' + data[i].num+ "</option>";
	    	            }
	    	            //$('#responsiblePerson').html(optionstring);
	    	            childObj.html(optionstring);
	    	        },
	    	        error: function (msg) {
	    	            alert(msg);
	    	        }
	    	    });
	    }
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
        
	</script>
</head>
<body>
	<h3 class="text-center page-title">拜访报告</h3>
	<form:form id="inputForm" modelAttribute="vrVisit" action="${ctx}/vr/visitingRecordManager/saveInfo" method="post" class="form-search">
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.assignee"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden id="flag" path="act.flag"/> 
		<form:hidden path="id"/>
		<form:hidden path="visitNo"/>
		<form:hidden path="workflowStatus2"/>
		<form:hidden path="updateDate"/>
		
		<shiro:hasPermission name="vr:visitingReport:edit">
            <c:set var="isManagerEdit" value="${viewsts.isApprovalCompleted ? true : false}"></c:set>
        </shiro:hasPermission>
        <shiro:lacksPermission name="vr:visitingReport:edit">
            <c:set var="isManagerEdit" value="false"></c:set>
        </shiro:lacksPermission> 
	<div class="group-box group-box-first" style="height: auto;">
		<label style="font-family: sans-serif;font-size: 16px;font-weight: bolder;">拜访申请</label>
		<div style="margin-bottom: 5px;">
			申请人：<label>${(fns:getUserById(vrVisit.responsiblePersonId)).name}</label>
		</div>
		<table style="width:72%;" class="table table-striped table-bordered table-condensed" >
				<thead>
					<tr>
						<th class="text-center" width="5%">No</th><th width="10%">地点区分</th><th width="15%">地点</th>
						<th width="25%">计划拜访对象</th><th width="20%">商机</th><th width="15%">拜访目的</th>
						<th width="10%">计划拜访时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${vrVisit.vrVisitDtlList}" var="rowModel" varStatus="status">
					<c:if test="${not empty rowModel.expDateFrom}">
					<tr>
						<td class="text-center">${status.count}</td>
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
							<c:forEach items="${fns:getBussinessOpByParent(rowModel.customerName)}" var="dictList">
								<c:if test="${dictList.businessOppNo ==rowModel.businessOppNo}">
									${dictList.model} ${dictList.num}
								</c:if>
							</c:forEach>
						</td>
						<td>
							<c:forEach items="${fns:getDictList('DM0042')}" var="addressList">
								<c:if test="${addressList.value == rowModel.purpose}">${addressList.label}</c:if>
							</c:forEach>
						</td>
						<td>${rowModel.expDateFrom}</td>
					</tr>
					</c:if>
					</c:forEach>
				</tbody>
			
		</table><br/>
		<label style="font-family: sans-serif;font-size: 16px;font-weight: bolder;">拜访报告</label>
		<div class="auto-scroll-x">
		<table id="tableHead" class="table table-striped table-bordered table-condensed" >
				<c:if test="${viewsts.canEdit}">
					<thead>
						<tr><th width="2%" class="text-center"><a style="cursor:pointer;" class="icon-plus-sign" id ="addRowInfo"></a></th>
							<th width="2%" class="text-center">No</th><th width="7%">地点区分</th><th width="10%">地点</th>
							<th width="10%">计划拜访对象</th><th width="10%">商机</th><th width="12%">拜访目的</th>
							<th width="5%">已拜访</th><th width="8%">实际拜访时间</th>
							<th width="15%">拜访结果</th><th width="15%">组长评价</th>
							<th width="15%">总监评价</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${vrVisit.vrVisitDtlList}" var="rowModel" varStatus="status">
						<tr>
							<c:if test="${not empty rowModel.expDateFrom}">
							<td></td>
							<td class="text-center"><span class="rowNo">${status.count}</span>
								<input type="hidden" name="vrVisitDtlList[${status.index}].id" value="${rowModel.id }" />
								<input type="hidden" id="visitNo" name="vrVisitDtlList[${status.index}].visitNo" value="${rowModel.visitNo}" />
								<input type="hidden" name="vrVisitDtlList[${status.index}].lineNo" value="${rowModel.lineNo }" />
								<input type="hidden" name="vrVisitDtlList[${status.index}].procInsId" value="${rowModel.procInsId}" />
								<input type="hidden" name="vrVisitDtlList[${status.index}].expDateFrom" value="${rowModel.expDateFrom}" />
							</td>
							<td>
								<c:forEach items="${fns:getDictList('DM0041')}" var="dictList">
								<c:if test="${dictList.value == rowModel.ifLocal}">
								${dictList.label}
								<input readonly="readonly" name="vrVisitDtlList[${status.index}].ifLocal" style="width: 80px;" type="hidden" value="${rowModel.ifLocal}" />
								</c:if>
								</c:forEach>
							</td>
							<td title="${rowModel.address}">
							<div class="ellipsis" style="max-width:150px;">${rowModel.address}</div></td>
							<input readonly="readonly" name="vrVisitDtlList[${status.index}].address" style="width: 100px;" type="hidden" value="${rowModel.address}" /></td>
							<td>${rowModel.customerChName }
							<input style="width: 200px;"name="vrVisitDtlList[${status.index}].customerName" type="hidden" value="${rowModel.customerName}" />
							</td>
							<td>
								<c:forEach items="${fns:getBussinessOpByParent(rowModel.customerName)}" var="dictList">
									<c:if test="${dictList.businessOppNo ==rowModel.businessOppNo}">
										${dictList.model} ${dictList.num}
									</c:if>
								</c:forEach>
								<input style="width: 200px;" name="vrVisitDtlList[${status.index}].businessOppNo" type="hidden" value="${rowModel.businessOppNo}" />
							</td>
							<td>
								<c:forEach items="${fns:getDictList('DM0042')}" var="dictList">
									<c:if test="${dictList.value == rowModel.purpose}">${dictList.label}
									<input style="width: 150px;" readonly="readonly" name="vrVisitDtlList[${status.index}].purpose" type="hidden" value="${rowModel.purpose}" />
									</c:if>
								</c:forEach>
							</td>
							</c:if>
							<c:if test="${empty rowModel.expDateFrom}">
								<td class="text-center"><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>
								<td class="text-center"><span class="rowNo">${status.count}</span>
									<input type="hidden" name="vrVisitDtlList[${status.index}].id" value="${rowModel.id }" />
									<input type="hidden" id="visitNo" name="vrVisitDtlList[${status.index}].visitNo" value="${rowModel.visitNo}" />
									<input type="hidden" name="vrVisitDtlList[${status.index}].lineNo" value="${rowModel.lineNo }" />
									<input type="hidden" name="vrVisitDtlList[${status.index}].procInsId" value="${rowModel.procInsId}" />
									<input type="hidden" name="vrVisitDtlList[${status.index}].expDateFrom" value="${rowModel.expDateFrom}" />
								</td>
								<td>
								<select name="vrVisitDtlList[${status.index}].ifLocal" style="width: 80px;">
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
								<td><input type="text" name="vrVisitDtlList[${status.index}].address"  style="width: 100px;" value="${rowModel.address}" /></td>
								<td><input name="vrVisitDtlList[${status.index}].customerName" type="text" value="${rowModel.customerName}"  onchange="loadBussinessOp(this,'busNo_${status.count}')" 
								   maxlength="50" style="width:220px;" data-type="" data-show="text" 
							       class="remote customer required customerId"/>
								</td>
								<td>
									<select name="vrVisitDtlList[${status.index}].businessOppNo" id="busNo_${status.count}" style="width: 150px;">
										<%-- <option value="${rowModel.businessOppNo}">${rowModel.businessOppNo}</option> --%>
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
									<select name="vrVisitDtlList[${status.index}].purpose" style="width: 150px;">
										<option value=""></option>
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
							</c:if>
							<td>
							<select class="required ifVisit" name="vrVisitDtlList[${status.index}].ifVisit" style="width: 65px;">
								<option value=""></option>
								<c:forEach items="${fns:getDictList('yes_no')}" var="dictList">
									<c:if test="${dictList.value == rowModel.ifVisit}">
									<option value="${dictList.value}" selected="selected">${dictList.label}</option>
									</c:if>
									<c:if test="${dictList.value != rowModel.ifVisit}">
									<option value="${dictList.value}">${dictList.label}</option>
									</c:if>
								</c:forEach>
							</select></td>
							<td><input name="vrVisitDtlList[${status.index}].actDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate actDateFrom" style="width:100px;background-color:#ffffff;"
							value="${rowModel.actDateFrom}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/></td>
							<td><input type="text" maxlength="300" name="vrVisitDtlList[${status.index}].newRemarks" class="required" value="${rowModel.newRemarks}" style="width: 200px;width:100%" /></td>
							<td title="${rowModel.leaderOpinion}">
								<div class="ellipsis" style="max-width:150px;">${rowModel.leaderOpinion}</div>
								<input style="width: 200px;" name="vrVisitDtlList[${status.index}].leaderOpinion" type="hidden" value="${rowModel.leaderOpinion}" />
							</td>
							<td title="${rowModel.directorOpinion}">
								<div class="ellipsis" style="max-width:150px;">${rowModel.directorOpinion}</div>
								<input style="width: 200px;" name="vrVisitDtlList[${status.index}].directorOpinion" type="hidden" value="${rowModel.directorOpinion}" />
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</c:if>
				<c:if test="${!viewsts.canEdit}">
					<thead>
						<tr>
							<th class="text-center" width="80px">No</th><th width="8%">地点区分</th><th width="150px">地点</th>
							<th width="150px">计划拜访对象</th><th width="150px">商机</th><th width="150px">拜访目的</th>
							<th width="60px">已拜访</th><th width="150px">实际拜访时间</th>
							<th width="250px">拜访结果</th><th width="250px">组长评价</th>
							<th width="250px">总监评价</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${vrVisit.vrVisitDtlList}" var="rowModel" varStatus="status">
						<tr>
							<td class="text-center"><span class="rowNo">${status.count}</span>
								<input type="hidden" name="vrVisitDtlList[${status.index}].id" value="${rowModel.id }" />
								<input type="hidden" id="visitNo" name="vrVisitDtlList[${status.index}].visitNo" value="${rowModel.visitNo}" />
								<input type="hidden" name="vrVisitDtlList[${status.index}].lineNo" value="${rowModel.lineNo }" />
							</td>
							<td>
								<c:forEach items="${fns:getDictList('DM0041')}" var="addressList">
									<c:if test="${addressList.value == rowModel.ifLocal}">${addressList.label}</c:if>
								</c:forEach>
							</td>
							<td title="${rowModel.address}">
							<div class="ellipsis" style="max-width:150px;">${rowModel.address}</div></td>
							<td>${rowModel.customerChName }</td>
							<td>
								<c:forEach items="${fns:getBussinessOpByParent(rowModel.customerName)}" var="dictList">
									<c:if test="${dictList.businessOppNo ==rowModel.businessOppNo}">
										${dictList.model} ${dictList.num}
									</c:if>
								</c:forEach>
							</td>
							<td>
								<c:forEach items="${fns:getDictList('DM0042')}" var="addressList">
									<c:if test="${addressList.value == rowModel.purpose}">${addressList.label}</c:if>
								</c:forEach>
							</td>
							<td>
								<c:forEach items="${fns:getDictList('yes_no')}" var="dictList">
									<c:if test="${dictList.value == rowModel.ifVisit}">${dictList.label}</c:if>
								</c:forEach>
							</td>
							<td>${rowModel.actDateFrom}<%-- <fmt:formatDate value="${rowModel.actDateFrom}" pattern="yyyy-MM-dd"/> --%></td>
							<td title="${rowModel.newRemarks}">
							<div class="ellipsis" style="max-width:150px;">${rowModel.newRemarks}</div></td>
							<td title="${rowModel.leaderOpinion}">
							<c:choose>
								<c:when test="${viewsts.canApproval}">
									<input type="text" maxlength="300" name="vrVisitDtlList[${status.index}].leaderOpinion" value="${rowModel.leaderOpinion}" style="width: 200px;width:100%" />
								</c:when>
								<c:otherwise>
									<div class="ellipsis" style="max-width:150px;">${rowModel.leaderOpinion}</div>
								</c:otherwise>
							</c:choose>
							</td>
							<td title="${rowModel.directorOpinion}">
								<c:choose>
								<c:when test="${isManagerEdit}">
									<input type="text" maxlength="300" name="vrVisitDtlList[${status.index}].directorOpinion" value="${rowModel.directorOpinion}" style="width: 200px;width:100%" />
								</c:when>
								<c:otherwise>
									<div class="ellipsis" style="max-width:150px;">${rowModel.directorOpinion}</div>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</c:if>
		</table>
		</div>
		<c:if test="${!viewsts.canApproval}">
        <div class="text-center" style="margin-top:8px;">
            <c:if test="${viewsts.canApply}">
                <input name="opt" id="btnSubmit" type="submit" class="btn btn-primary" value="提交申请"/>
            </c:if>
            <c:if test="${viewsts.canReapply}">
                <input name="opt" id="btnSubmit" type="submit" class="btn btn-primary" value="再申请"/>
            </c:if>
            <c:if test="${viewsts.canBack}">
                <input name="opt" id="btnSubmit" type="submit" class="btn btn-primary" value="撤回申请"/>
            </c:if>
            <c:if test="${viewsts.canDelete}">
                <input name="opt" type="submit" class="btn btn-primary" value="删除">
            </c:if>
            <c:if test="${isManagerEdit}">
	            <input name="opt" type="submit" class="btn btn-primary" value="保存" />
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
                <input name="opt" id="btnPass" type="submit" class="btn btn-primary"  value="同意">
                <input name="opt" id="btnReturn" type="submit" class="btn btn-default"  value="退回">
            </div>
        </div>
    </c:if>
	</form:form>
	<div id="contentDiv" style="display: none;">
	<span id="dateTemp">
		<input name="vrVisitDtlList[0].actDateFrom" type="text" readonly="readonly" maxlength="20"
		 class="input-medium Wdate actDateFrom" style="width:100px;background-color:#ffffff;"
		 value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
	</span>
	<span id="ifLocal">
		<c:forEach items="${fns:getDictList('DM0041')}" var="visitList">
			<option value="${visitList.value}">${visitList.label}</option>
		</c:forEach>
	</span>
	<span id="purpostList">
		<c:forEach items="${fns:getDictList('DM0042')}" var="purpostList">
			<option value="${purpostList.value}">${purpostList.label}</option>
		</c:forEach>
	</span>
	<span id="customerList">
	    <c:forEach items="${fns:getCustomerList()}" var="dictList">
			<option value="${dictList.customer_ch_name}">${dictList.customer_ch_name}</option>
		</c:forEach>
	</span>
	<span id="yesNoList">
	    <c:forEach items="${fns:getDictList('yes_no')}" var="dictList">
			<option value="${dictList.value}">${dictList.label}</option>
		</c:forEach>
	</span>
	
	</div>
	<sys:message content="${message}"/>
	<c:if test="${!empty vrVisit.act.procInsId}">
        <act:histoicFlow procInsId="${vrVisit.act.procInsId}"/>
    </c:if>
</body>
</html>