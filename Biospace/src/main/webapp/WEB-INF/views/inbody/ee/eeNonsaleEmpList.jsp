<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>非销售员工评价管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		 var date=new Date;
		 var year=date.getFullYear(); 
		 if($('#currentYear').val() == ''){
			 $('#currentYear').val(year)
			 $('#nextYear').val(Number(year)+Number(1))
		 }
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
			
			 //var busNo = 0;
			 $("#addRowInfo").on("click",function(){
		        	//busNo = busNo+1;
		        	var html = '<tr>'
	        		html += '<td><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem1" type="text" style="width: 180px;"/><input name="eeEmpPlans[0].showArea" type="hidden" value="1"/></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem2" type="text" style="width: 250px;"/></td>';
	        		html += '<td><select name="eeEmpPlans[0].displayItem3" style="width: 150px;">';
	        		html += $('#empEvaluOp').html();
	        		html += '</select></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem4" type="text" style="width: 350px;"/></td>';
	        		html += '</tr>';
		        	
	        		$('#tableHeadCurrent>tbody').append(html);
	        		$("select:not(.remote)").select2({allowClear: true});
	        		
	        		var $tbody = $('#tableHeadCurrent>tbody');
					$tbody.find("tr").each(function(index){ 
		    			$(this).find("td>input").each(function() {
		    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
		    			});; 
		    			$(this).find("td>select").each(function() {
		    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
		    			});; 
		   			});
					
					var $tbody01 = $('#tableHeadSecond>tbody');
	        		var length = $tbody.find("tr").length;
					$tbody01.find("tr").each(function(index){ 
						index = index +length;
		    			$(this).find("td>input").each(function() {
		    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
		    			});; 
		    			$(this).find("td>select").each(function() {
		    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
		    			});; 
		   			});
			 });
			 
			// var busNo2 = 0;
			 $("#addRowInfo01").on("click",function(){
			  		//busNo2 = busNo2+1;
		        	var html = '<tr>'
	        		html += '<td><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem1" type="text" style="width: 200px;"/><input name="eeEmpPlans[0].showArea" type="hidden" value="2"/></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem2" type="text" style="width: 350px;"/></td>';
	        		html += '<td><select name="eeEmpPlans[0].displayItem3" style="width: 150px;">';
	        		html += $('#planRate').html();
	        		html += '</select></td>';
	        		html += '<td>';
	        		html += $('#dateTemp').html();
	        		html += '</td>';
	        		html += '</tr>';
		        	
	        		$('#tableHeadSecond>tbody').append(html);
	        		$("select:not(.remote)").select2({allowClear: true});
	        		
	        		var $tbody = $('#tableHeadSecond>tbody');
	        		var length = $('#tableHeadCurrent>tbody').find("tr").length;
					$tbody.find("tr").each(function(index){ 
						index = index +length;
		    			$(this).find("td>input").each(function() {
		    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
		    			});; 
		    			$(this).find("td>select").each(function() {
		    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
		    			});; 
		   			});
			 });
			 $(".ygEvaluate").change(function(){
				 var evaluateTotal = 0;
				 var evaluateCount = 0;
				 $(".ygEvaluate").each(function(index, element) {
			       if(this.value){
			    	   evaluateTotal = Number(evaluateTotal)+Number(this.value);
			    	   evaluateCount = Number(evaluateCount)+Number(1);
			       }
		        });
				if(evaluateTotal>0){
					$('#ygAverage').val((Number(evaluateTotal)/Number(evaluateCount)).toFixed(2));
				}
			 })
			 $(".jlEvaluate").change(function(){
				 var evaluateTotal = 0;
				 var evaluateCount = 0;
				 $(".jlEvaluate").each(function(index, element) {
			       if(this.value){
			    	   evaluateTotal = Number(evaluateTotal)+Number(this.value);
			    	   evaluateCount = Number(evaluateCount)+Number(1);
			       }
		        });
				if(evaluateTotal>0){
					$('#jlAverage').val((Number(evaluateTotal)/Number(evaluateCount)).toFixed(2));
				}
			 })
			 $(".zzEvaluate").change(function(){
				 var evaluateTotal = 0;
				 var evaluateCount = 0;
				 $(".zzEvaluate").each(function(index, element) {
			       if(this.value){
			    	   evaluateTotal = Number(evaluateTotal)+Number(this.value);
			    	   evaluateCount = Number(evaluateCount)+Number(1);
			       }
		        });
				if(evaluateTotal>0){
					$('#zzAverage').val((Number(evaluateTotal)/Number(evaluateCount)).toFixed(2));
				}
			 })
		});
		function calculateRate(v){
        	var goalVal = $('#goalId_'+v).val();
			var yeJiVal = $('#yeJiId_'+v).val();
        	var resultVal = (Number(yeJiVal)/Number(goalVal))*100;
        	if(resultVal || resultVal == 0){
        		$('#resultId_'+v).val(resultVal.toFixed(2));
        	}
        	
		}
		function calculateRate01(v){
			var goalVal = $('#goalId01_'+v).val();
			var yeJiVal = $('#yeJiId01_'+v).val();
			var resultVal = Number(goalVal)-Number(yeJiVal);
			if(resultVal || resultVal == 0){
			$('#resultId01_'+v).val(resultVal);
			}
		}
	 	function deleteRow(v){
       		$(v).parent().parent().remove();//删除this的父节点div
       		
       		var $tbody = $('#tableHeadCurrent>tbody');
			$tbody.find("tr").each(function(index){ 
    			$(this).find("td>input").each(function() {
    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
    			});; 
    			$(this).find("td>select").each(function() {
    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
    			});; 
   			});
			
			var $tbody01 = $('#tableHeadSecond>tbody');
    		var length = $tbody.find("tr").length;
    		
			$tbody01.find("tr").each(function(index){ 
				index = index +length;
    			$(this).find("td>input").each(function() {
    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
    			});; 
    			$(this).find("td>select").each(function() {
    				$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
    			});; 
   			});
       	}	
		
	 	function askDateChange(dp) {
	 		
			 $('#nextYear').val(Number(dp.cal.getNewDateStr())+Number(1));
			 $('#yearHidden').val(dp.cal.getNewDateStr());
			 $("#searchForm").submit();  
		}
        
	</script>
</head>
<body>
	<h3 class="text-center page-title">员工评价</h3>
	<sys:message content="${message}"/>
	<form:form id="searchForm" modelAttribute="eeNonsaleEmp" action="${ctx}/ee/eeNonsaleEmp/searchInfo" method="post">
		<input type="hidden" id="yearHidden" name="year" value=""/>
	</form:form>
	<form:form id="inputForm" modelAttribute="eeNonsaleEmp" action="${ctx}/ee/eeNonsaleEmp/saveInfo" method="post" class="form-search" novalidate="true">
		<form:hidden path="id"/>
        <form:hidden path="act.taskId"/>
        <form:hidden path="act.procInsId"/>
        <form:hidden path="workflowStatus"/>
        <input type="hidden" name="updateDate" value="<fmt:formatDate value="${eeNonsaleEmp.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
	    <ul class="ul-form">
			<li><label style="display: inline-block;width: 80px;">业务评价：</label>
				<input name="year" type="text" id="currentYear" value="${eeNonsaleEmp.year}" readonly="readonly" 
				 style="width: 100px" class="input-mini required Wdate" onclick="WdatePicker({dateFmt:'yyyy', onpicking:function(dp){askDateChange(dp)}});"/>
			</li>
			<li><label style="display: inline-block;width: 80px;">工作计划：</label>
				<input name="nextYear" id="nextYear" type="text" value="${eeNonsaleEmp.year+1}" readonly="readonly" style="width: 100px" class="input-mini"/>
			</li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="打开"/></li> -->
			<li>
				<label style="display: inline-block;width: 180px;">提交人：</label>
				<input type="text" value="${(fns:getUserById(eeNonsaleEmp.createBy)).name}" readonly="readonly" style="width: 100px" class="input-mini"/>
				<input type="text" value="${eeNonsaleEmp.organizeName }" readonly="readonly" style="width: 100px" class="input-mini"/>
				<c:if test="${empty eeNonsaleEmp.workflowStatus}">
				<input type="text" value="" readonly="readonly" style="width: 100px" class="input-mini"/>
				</c:if>
				<c:if test="${not empty eeNonsaleEmp.workflowStatus}">
				<input type="text" value="${eeNonsaleEmp.workflowStatus == 50 ? '审批完成':'申请中' }" readonly="readonly" style="width: 100px" class="input-mini"/>
				</c:if>
			</li>
		</ul>   
	  
	<div class="group-box group-box-first">
			
			业务评价书填写理由: 根据本业务评价书及计划书，将会对本年度业务成果进行评价，并且以此为根据，对薪资情况进行评级，成为下一年度月薪调整的指标。<BR>
			1. 所有职员需填写本年度业务评价书和明年工作计划书（本人填写部分，所有字段必填，没有的话，请标注“无”）.<BR>
			2. 组长收到组员的业务评价书，请完成业务评价书组长填写部分. <BR>
			3. 请整合组长本人以及组员的业务评价书后提交给部长.<BR>
			4. 部长收到组员的业务评价书,请完成业务评价书部长填写部分.<BR>
	</div>
	<div class="group-box">
         <div class="group-header" >
             <strong class="group-title">本年度业务评价(本人填写部分)</strong>
         </div>
         1. 请整理本人今年的工作内容。请在下方说明本年度工作计划的完成情况以及总结评分。<br>
         （如有许多个目标，可以在下方追加进行说明.)   10分制: A—不良； B—不足； C—普通； D—良好； E—优秀<br>
         <table id="tableHeadCurrent" style="width:100%" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th width="5%"><a style="cursor:pointer;" class="icon-plus-sign" id = "addRowInfo"></a></th>
					<th width="15%">本年度目标/计划</th>
					<th width="25%">详细内容</th>
					<th width="10%">评分</th>
					<th width="35%">年度总结汇报 (完成情况)</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="planCount" value="0"></c:set>
				<c:forEach items="${eeNonsaleEmp.eeEmpPlans}" var="rowModel" varStatus="status">
					<c:if test="${rowModel.showArea == 1 }">
					<tr>
		        		<td><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem1" type="text" value="${rowModel.displayItem1 }" style="width: 180px;"/>
		        		<input name="eeEmpPlans[${planCount}].id" type="hidden" value="${rowModel.id }"/>
		        		<input name="eeEmpPlans[${planCount}].showArea" type="hidden" value="1"/></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem2" type="text" value="${rowModel.displayItem2 }" style="width: 250px;"/></td>
		        		<td>
		        		<select name="eeEmpPlans[${planCount}].displayItem3" style="width: 150px;">
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${rowModel.displayItem3 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean.evaluate1 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
		        		</td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem4" type="text" value="${rowModel.displayItem4 }" style="width: 350px;"/></td>
						<c:set var="planCount" value="${planCount+1}"></c:set>
	        		</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table><br>
		2. 本人业务成绩评价表.10分制: A—不良； B—不足； C—普通； D—良好； E—优秀。请选择：
		<c:forEach items="${eeNonsaleEmp.eeEmpEvaluations}" var="rowModel" varStatus="status">
			<c:if test="${rowModel.evaluateType == 1 }">
				<c:set var="evaluationBean" value="${rowModel }"></c:set>
			</c:if>
			<c:if test="${rowModel.evaluateType == 2 }">
				<c:set var="evaluationBean2" value="${rowModel }"></c:set>
			</c:if>
			<c:if test="${rowModel.evaluateType == 3 }">
				<c:set var="evaluationBean3" value="${rowModel }"></c:set>
			</c:if>
		</c:forEach>
		<table id="tableHead" style="width:100%" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th width="20%">业务指标</th>
					<th width="40%">说明</th>
					<th width="25%">评分</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>工作成果量</td><td>工作效率, 工作的产出/成果量</td>
					<td><input type="hidden" name="eeEmpEvaluations[0].evaluateType" value="1" />
						<input type="hidden" name="eeEmpEvaluations[0].id" value="${evaluationBean.id}" />
						<select name="eeEmpEvaluations[0].evaluate1" class="ygEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean.evaluate1 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean.evaluate1 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>工作质量</td><td>工作完成的精准度,工作成果的质量是否优秀</td>
					<td>
						<select name="eeEmpEvaluations[0].evaluate2" class="ygEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean.evaluate2 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean.evaluate2 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>业务知识</td><td>是否清楚本人所要做的工作内容以及为什么要做?</td>
					<td>
						<select name="eeEmpEvaluations[0].evaluate3" class="ygEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean.evaluate3 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean.evaluate3 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>工作责任感</td><td>对自身工作是否有较高的责任感?对新附加的责任是否积极、用于担当？</td>
					<td>
						<select name="eeEmpEvaluations[0].evaluate4" class="ygEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean.evaluate4 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean.evaluate4 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>工作信任度</td><td>是否值得信任，是否能扎实地完成本人的工作任务</td>
					<td>
						<select name="eeEmpEvaluations[0].evaluate5" class="ygEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean.evaluate5 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean.evaluate5 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>创意力</td><td>在工作的同时是否会主动思考更好的工作方法，和有创意性的改善工作的方法</td>
					<td>
						<select name="eeEmpEvaluations[0].evaluate6" class="ygEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean.evaluate6 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean.evaluate6 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>协作精神</td><td>对于同事的请求乐于帮助、解决，与其他同事相互配合默契</td>
					<td>
						<select name="eeEmpEvaluations[0].evaluate7" class="ygEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean.evaluate7 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean.evaluate7 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>考勤情况</td><td>无迟到早退现象，工作时间集中度高</td>
					<td>
						<select name="eeEmpEvaluations[0].evaluate8" class="ygEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean.evaluate8 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean.evaluate8 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				
			</tbody>
		</table><br>
		<div>
			3. 本年度的工作中，与之前相比是否有改善的点，并能看到明显的成效？如有，请说明. 
			<label style="float: right;">
			平均分：<input name="eeEmpEvaluations[0].average" id="ygAverage" type="text" value="${evaluationBean.average }" readonly="readonly" style="width: 50px" class="input-mini"/>
			</label>
		</div>
    	<textarea rows="2" cols="200" name="eeEmpEvaluations[0].newRemarks" style="width:500px;">${evaluationBean.newRemarks}</textarea>
    </div> 
    <div class="group-box">
         <div class="group-header" >
             <strong class="group-title">明年工作计划 (本人填写部分)</strong>
         </div>
         1.请在下方列出明年工作中打算集中精力，争取哪些成果，以及其具体通过什么方式如何实现，预计完成时间等内容。<BR>
		（如有许多个目标，可以在下方追加进行说明.)    <BR>
		 <table id="tableHeadSecond" style="width:100%" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th width="5%"><a style="cursor:pointer;" class="icon-plus-sign" id = "addRowInfo01"></a></th>
					<th width="15%">来年目标/计划</th>
					<th width="30%">详细内容</th>
					<th width="15%">业务重要度</th>
					<th width="15%">预计完成时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${eeNonsaleEmp.eeEmpPlans}" var="rowModel" varStatus="status">
					<c:if test="${rowModel.showArea == 2 }">
						<tr>
						<c:set var="nextYearMemo" value="${rowModel.displayItem6 }"></c:set>
						<td><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem1" type="text" value="${rowModel.displayItem1 }" style="width: 200px;"/>
		        			<input name="eeEmpPlans[${planCount}].id" type="hidden" value="${rowModel.id }"/>
		        			<input name="eeEmpPlans[${planCount}].showArea" type="hidden" value="2"/></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem2" type="text" value="${rowModel.displayItem2 }" style="width: 350px;"/></td>
		        		<td>
		        		<select name="eeEmpPlans[${planCount}].displayItem3" style="width: 150px;">
							<c:forEach items="${fns:getDictList('DM0040')}" var="dictList">
								<c:if test="${rowModel.displayItem3 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean.evaluate1 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
		        		</td>
		        		<td>
		        		<input name="eeEmpPlans[${planCount}].displayItem4" type="text" readonly="readonly" 
							maxlength="20" class="input-medium Wdate required" style="width:150px;"
							value="${rowModel.displayItem4 }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
		        		</td>
						<c:set var="planCount" value="${planCount+1}"></c:set>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table><br>
		<%-- 2.请描述一下在来年为了使本人负责的市场更好的成长，打算在哪方面集中精力努力改善？ <br>
		<textarea rows="2" cols="200" name="nextYearMemo" style="width:500px;">${nextYearMemo }</textarea> --%>
    </div>  
    <div class="group-box">
         <div class="group-header" >
             <strong class="group-title">本年度业务评价书 (组长填写部分)&nbsp;&nbsp;&nbsp;&nbsp;
             	评价人 <input type="text" value="${(fns:getUserById(evaluationBean2.createBy)).name}" readonly="readonly" style="width: 100px" class="input-mini"/> </strong>
         </div>
         1. 请具体描述该组员在本年新的成长、成果.<br>
         <textarea rows="2" cols="200" disabled="disabled" style="width:500px;">${evaluationBean2.describe1 }</textarea><br>
         2. 请具体描述该组员在本年做的不足的地方.<br>
         <textarea rows="2" cols="200" disabled="disabled" style="width:500px;">${evaluationBean2.describe2 }</textarea><br>
         3.请描述该组员在业务上的长处、特点以及可能性.<br>
         <textarea rows="2" cols="200" disabled="disabled" style="width:500px;">${evaluationBean2.describe3 }</textarea><br>
         4. 请描述该组员在工作上的短处以及问题点. <br>
         <textarea rows="2" cols="200"  disabled="disabled" style="width:500px;">${evaluationBean2.describe4 }</textarea><br>
         5.组员业务成绩评价表.10分制: A—不良； B—不足； C—普通； D—良好； E—优秀。请选择：
		<table id="tableHead" style="width:100%" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th width="20%">业务指标</th>
					<th width="40%">说明</th>
					<th width="25%">评分</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>工作成果量</td><td>工作效率, 工作的产出/成果量</td>
					<td>
						<select disabled="disabled" class="zzEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean2.evaluate1 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean2.evaluate1 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>工作质量</td><td>工作完成的精准度,工作成果的质量是否优秀</td>
					<td>
						<select disabled="disabled" class="zzEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean2.evaluate2 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean2.evaluate2 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>业务知识</td><td>是否清楚本人所要做的工作内容以及为什么要做?</td>
					<td>
						<select disabled="disabled" class="zzEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean2.evaluate3 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean2.evaluate3 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>工作责任感</td><td>对自身工作是否有较高的责任感?对新附加的责任是否积极、勇于担当？</td>
					<td>
						<select disabled="disabled" class="zzEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean2.evaluate4 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean2.evaluate4 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>工作信任度</td><td>是否值得信任，是否能扎实地完成本人的工作任务</td>
					<td>
						<select disabled="disabled" class="zzEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean2.evaluate5 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean2.evaluate5 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>创意力</td><td>在工作的同时是否会主动思考更好的工作方法，和有创意性的改善工作的方法</td>
					<td>
						<select disabled="disabled" class="zzEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean2.evaluate6 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean2.evaluate6 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>协作精神</td><td>对于同事的请求乐于帮助、解决，与其他同事相互配合默契</td>
					<td>
						<select disabled="disabled" class="zzEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean2.evaluate7 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean2.evaluate7 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>考勤情况</td><td>无迟到早退现象，工作时间集中度高</td>
					<td>
						<select disabled="disabled" class="zzEvaluate" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean2.evaluate8 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean2.evaluate8 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				
			</tbody>
		</table>
		<div>
		<label style="float: right;">
			平均分：<input type="text" id="zzAverage" value="${evaluationBean2.average}" disabled="disabled" style="width: 50px" class="input-mini"/>
		</label>
		</div><br>
    </div>    

     <c:if test="${!viewsts.canApproval&&eeNonsaleEmp.workflowStatus != 50}">
        <div class="text-center" style="margin-top:8px;">
            <c:if test="${viewsts.canSave}">
                <input name="opt" type="submit" class="btn btn-primary" value="临时保存">
            </c:if>
            <c:if test="${viewsts.canApply}">
                <input name="opt" type="submit" class="btn btn-primary" value="提交申请">
            </c:if>
            <c:if test="${viewsts.canReapply}">
                <input name="opt" type="submit" class="btn btn-primary" value="再申请">
            </c:if>
            <c:if test="${viewsts.canBack}">
                <input name="opt" type="submit" class="btn btn-primary" value="撤回申请">
            </c:if>
            <c:if test="${viewsts.canDelete}">
                <input name="opt" type="submit" class="btn btn-primary" value="删除">
            </c:if>
            <c:if test="${isManagerEdit}">
                <input type="submit" class="btn btn-primary" value="保存" onclick="javascript:this.form.action='${ctx}/pm/purchase/mc/manager/save';">
            </c:if>
            <c:if test="${empty pmPurchaseOrd.act.procInsId && !empty pmPurchaseOrd.id}">
                <input class="btn" type="button" value="返回" onclick="history.go(-1)"/>
            </c:if>
        </div>
        </c:if>
        <c:if test="${viewsts.canApproval}">
            <div class="group-box">
                <div class="group-header" >
                    <strong class="group-title">审批</strong>
                </div>

                <ul class="ul-form">
                    <li class="full-width">
                        <label>审批意见：</label>
                        <form:textarea path="act.comment" class="fill-right" rows="3" maxlength="300" ></form:textarea>
                    </li>
                </ul>

                <div class="text-center">
                    <input name="opt" type="submit" class="btn btn-primary" value="同意">
                    <input name="opt" type="submit" class="btn btn-default" value="退回">
                </div>
            </div>
        </c:if>     
	</form:form>
	<c:if test="${!empty eeNonsaleEmp.act.procInsId}">
        <act:histoicFlow procInsId="${eeNonsaleEmp.act.procInsId}"/>
    </c:if>
    <div id="contentDiv" style="display: none;">
	<span id="empEvaluOp">
		<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
			<option value="${dictList.value}">${dictList.label}</option>
		</c:forEach>
	</span>
	<span id="planRate">
		<c:forEach items="${fns:getDictList('DM0040')}" var="dictList">
			<option value="${dictList.value}">${dictList.label}</option>
		</c:forEach>
	</span>
	<span id="dateTemp">
		<input name="eeEmpPlans[0].displayItem4" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" style="width:150px;"
		value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
	</span>
	</div>
</body>
</html>