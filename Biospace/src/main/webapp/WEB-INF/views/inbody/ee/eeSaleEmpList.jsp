<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售员工评价管理</title>
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
			
			 var busNo = 0;
			 $("#addRowInfo").on("click",function(){
				 	if(busNo ==0){
		        		busNo = busNo + $('#tableHeadCurrent>tbody').find("tr").length;
		        	}
		        	busNo = busNo+1;
		        	var goalId = 'goalId_'+busNo;
		        	var yeJiId = 'yeJiId_'+busNo;
		        	var resultId = 'resultId_'+busNo;
		        	var html = '<tr>'
	        		html += '<td><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem1" type="text" style="width: 150px;"/><input name="eeEmpPlans[0].showArea" type="hidden" value="1"/></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem2" class="text-right required num digits" id="'+goalId+'" onchange="calculateRate(\''+busNo+'\')" type="text" style="width: 100px;"/></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem3" class="text-right required num digits" id="'+yeJiId+'" onchange="calculateRate(\''+busNo+'\')" type="text" style="width: 100px;"/></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem4" id="'+resultId+'" readOnly="readOnly" type="text" style="width: 80px;"/></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem5" type="text" style="width: 450px;"/></td>';
	        		html += '</tr>';
		        	
	        		$('#tableHeadCurrent>tbody').append(html);
	        		
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
			 
			 
			 var busNo2 = 0;
			 $("#addRowInfo01").on("click",function(){
				 	if(busNo2 ==0){
				 		busNo2 = busNo2 + $('#tableHeadSecond>tbody').find("tr").length;
		        	}
			  		busNo2 = busNo2+1;
		        	var yeJiId = 'yeJiId01_'+busNo2;
		        	var goalId = 'goalId01_'+busNo2;
		        	var resultId = 'resultId01_'+busNo2;
		        	var html = '<tr>'
	        		html += '<td><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem1" type="text" style="width: 150px;"/><input name="eeEmpPlans[0].showArea" type="hidden" value="2"/></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem2" class="text-right required num digits" type="text" id="'+yeJiId+'" onchange="calculateRate01(\''+busNo2+'\')" style="width: 150px;"/></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem3" class="text-right required num digits" type="text" id="'+goalId+'" onchange="calculateRate01(\''+busNo2+'\')" style="width: 150px;"/></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem4" type="text" id="'+resultId+'" readOnly="readOnly" style="width: 150px;"/></td>';
	        		html += '<td><input name="eeEmpPlans[0].displayItem5" type="text" style="width: 300px;"/></td>';
	        		html += '</tr>';
		        	
	        		$('#tableHeadSecond>tbody').append(html);
	        		
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
		function calculateRate_1(v){
        	var goalVal = $('#goalId1_'+v).val();
			var yeJiVal = $('#yeJiId1_'+v).val();
        	var resultVal = (Number(yeJiVal)/Number(goalVal))*100;
        	if(resultVal || resultVal == 0){
        		$('#resultId1_'+v).val(resultVal.toFixed(2));
        	}
        	
		}
		function calculateRate01(v){
			var goalVal = $('#goalId01_'+v).val();
			var yeJiVal = $('#yeJiId01_'+v).val();
			var resultVal = (Number(goalVal)-Number(yeJiVal))/Number(yeJiVal)*100;
			if(resultVal || resultVal == 0){
			$('#resultId01_'+v).val(resultVal.toFixed(2));
			}
		}
		function calculateRate01_1(v){
			var goalVal = $('#goalId02_'+v).val();
			var yeJiVal = $('#yeJiId02_'+v).val();
			var resultVal = (Number(goalVal)-Number(yeJiVal))/Number(yeJiVal)*100;
			if(resultVal || resultVal == 0){
			$('#resultId02_'+v).val(resultVal.toFixed(2));
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
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ee/eeNonsaleEmp/">非销售员工评价列表</a></li>
		<shiro:hasPermission name="ee:eeNonsaleEmp:edit"><li><a href="${ctx}/ee/eeNonsaleEmp/form">非销售员工评价添加</a></li></shiro:hasPermission>
	</ul> --%>
	<h3 class="text-center page-title">员工评价</h3>
	<sys:message content="${message}"/>
	<form:form id="searchForm" modelAttribute="eeSaleEmp" action="${ctx}/ee/eeSaleEmp/searchInfo" method="post">
		<input type="hidden" id="yearHidden" name="year" value=""/>
	</form:form>
	<form:form id="inputForm" modelAttribute="eeSaleEmp" action="${ctx}/ee/eeSaleEmp/saveInfo" method="post" class="form-search" novalidate="true">
		<form:hidden path="id"/>
        <form:hidden path="act.taskId"/>
        <form:hidden path="act.procInsId"/>
        <form:hidden path="workflowStatus"/>
        <input type="hidden" name="updateDate" value="<fmt:formatDate value="${eeSaleEmp.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
	    <ul class="ul-form">
			<li><label style="display: inline-block;width: 80px;">业务评价：</label>
				<input name="year" type="text" id="currentYear" value="${eeSaleEmp.year}" readonly="readonly" 
				 style="width: 100px" class="input-mini required Wdate" onclick="WdatePicker({dateFmt:'yyyy', onpicking:function(dp){askDateChange(dp)}});"/>
			</li>
			<li><label style="display: inline-block;width: 80px;">工作计划：</label>
				<input name="nextYear" id="nextYear" type="text" value="${eeSaleEmp.year+1}" readonly="readonly" style="width: 100px" class="input-mini"/>
			</li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="打开"/></li> -->
			<li>
				<label style="display: inline-block;width: 180px;">提交人：</label>
				<input type="text" value="${(fns:getUserById(eeSaleEmp.createBy)).name}" readonly="readonly" style="width: 100px" class="input-mini"/>
				<input type="text" value="${eeSaleEmp.organizeName }" readonly="readonly" style="width: 100px" class="input-mini"/>
				<c:if test="${empty eeSaleEmp.workflowStatus}">
				<input type="text" value="" readonly="readonly" style="width: 100px" class="input-mini"/>
				</c:if>
				<c:if test="${not empty eeSaleEmp.workflowStatus}">
				<input type="text" value="${eeSaleEmp.workflowStatus == 50 ? '审批完成':'申请中' }" readonly="readonly" style="width: 100px" class="input-mini"/>
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
         1. 请对所负责区域的销售业绩进行描述. <br>
         <table id="tableHeadCurrent" style="width:100%" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th width="5%"><a style="cursor:pointer;" class="icon-plus-sign" id = "addRowInfo"></a></th>
					<th width="15%">负责区域</th>
					<th width="10%">本年度目标</th>
					<th width="10%">实际业绩(预计至12月底)</th>
					<th width="10%">完成率(%)</th>
					<th width="35%">未达成目标 80%的理由？</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="planCount" value="0"></c:set>
				<c:forEach items="${eeSaleEmp.eeEmpPlans}" var="rowModel" varStatus="status">
					<c:if test="${rowModel.showArea == 1 }">
					<tr>
		        		<td><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem1" type="text" value="${rowModel.displayItem1 }" style="width: 150px;"/>
		        		<input name="eeEmpPlans[${planCount}].id" type="hidden" value="${rowModel.id }"/>
		        		<input name="eeEmpPlans[${planCount}].showArea" type="hidden" value="1"/></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem2" type="text" id="goalId1_${planCount}" onchange="calculateRate_1('${planCount}')"  value="${rowModel.displayItem2 }" style="width: 100px;"/></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem3" type="text" id="yeJiId1_${planCount}" onchange="calculateRate_1('${planCount}')" value="${rowModel.displayItem3 }" style="width: 100px;"/></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem4" type="text" id="resultId1_${planCount}" readonly="readonly" value="${rowModel.displayItem4 }" style="width: 80px;"/></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem5" type="text" value="${rowModel.displayItem5 }" style="width: 450px;"/></td>
						<c:set var="planCount" value="${planCount+1}"></c:set>
	        		</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table><br>
		2. 本人业务成绩评价表.10分制: A—不良； B—不足； C—普通； D—良好； E—优秀。请选择：
		<c:forEach items="${eeSaleEmp.eeEmpEvaluations}" var="rowModel" varStatus="status">
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
         1.请填写来年负责区域的销售目标和销售策略. 
		 <table id="tableHeadSecond" style="width:100%" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th width="5%"><a style="cursor:pointer;" class="icon-plus-sign" id = "addRowInfo01"></a></th>
					<th width="15%">负责区域</th>
					<th width="10%">本年业绩</th>
					<th width="10%">来年目标</th>
					<th width="10%">增加率(%)</th>
					<th width="35%">为了达成目标需使用的策略</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${eeSaleEmp.eeEmpPlans}" var="rowModel" varStatus="status">
					<c:if test="${rowModel.showArea == 2 }">
						<tr>
						<c:set var="nextYearMemo" value="${rowModel.displayItem6 }"></c:set>
						<td><a style="cursor:pointer;" class="icon-minus-sign" onclick = "deleteRow(this)"></a></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem1" type="text" value="${rowModel.displayItem1 }" style="width: 150px;"/>
		        			<input name="eeEmpPlans[${planCount}].id" type="hidden" value="${rowModel.id }"/>
		        			<input name="eeEmpPlans[${planCount}].showArea" type="hidden" value="2"/></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem2" type="text" id="yeJiId02_${planCount}" onchange="calculateRate01_1('${planCount}')" value="${rowModel.displayItem2 }" style="width: 150px;"/></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem3" type="text" id="goalId02_${planCount}" onchange="calculateRate01_1('${planCount}')" value="${rowModel.displayItem3 }" style="width: 150px;"/></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem4" type="text" id="resultId02_${planCount}" readonly="readonly" value="${rowModel.displayItem4 }" style="width: 150px;"/></td>
		        		<td><input name="eeEmpPlans[${planCount}].displayItem5" type="text" value="${rowModel.displayItem5 }" style="width: 300px;"/></td>
						<c:set var="planCount" value="${planCount+1}"></c:set>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table><br>
		2.请描述一下在来年为了使本人负责的市场更好的成长，打算在哪方面集中精力努力改善？ <br>
		<textarea rows="2" cols="200" name="nextYearMemo" style="width:500px;">${nextYearMemo }</textarea>
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
    <div class="group-box">
         <div class="group-header" >
             <strong class="group-title">本年度业务评价书 (经理填写部分)&nbsp;&nbsp;&nbsp;&nbsp;
             	评价人 <input type="text" value="${(fns:getUserById(evaluationBean3.createBy)).name}" readonly="readonly" style="width: 100px" class="input-mini"/> </strong>
         </div>
         1. 请具体描述该组员在本年新的成长、成果.<br>
         <textarea rows="2" cols="200" disabled="disabled" style="width:500px;">${evaluationBean3.describe1 }</textarea><br>
         2. 请具体描述该组员在本年做的不足的地方.<br>
         <textarea rows="2" cols="200" disabled="disabled" style="width:500px;">${evaluationBean3.describe2 }</textarea><br>
         3.请描述该组员在业务上的长处、特点以及可能性.<br>
         <textarea rows="2" cols="200" disabled="disabled" style="width:500px;">${evaluationBean3.describe3 }</textarea><br>
         4. 请描述该组员在工作上的短处以及问题点. <br>
         <textarea rows="2" cols="200"disabled="disabled" style="width:500px;">${evaluationBean3.describe4 }</textarea><br>
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
						<select class="jlEvaluate" disabled="disabled" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean3.evaluate1 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean3.evaluate1 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>工作质量</td><td>工作完成的精准度,工作成果的质量是否优秀</td>
					<td>
						<select class="jlEvaluate" disabled="disabled" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean3.evaluate2 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean3.evaluate2 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>业务知识</td><td>是否清楚本人所要做的工作内容以及为什么要做?</td>
					<td>
						<select class="jlEvaluate" disabled="disabled"  style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean3.evaluate3 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean3.evaluate3 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>工作责任感</td><td>对自身工作是否有较高的责任感?对新附加的责任是否积极、勇于担当？</td>
					<td>
						<select class="jlEvaluate" disabled="disabled"  style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean3.evaluate4 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean3.evaluate4 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>工作信任度</td><td>是否值得信任，是否能扎实地完成本人的工作任务</td>
					<td>
						<select class="jlEvaluate" disabled="disabled" style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean3.evaluate5 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean3.evaluate5 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>创意力</td><td>在工作的同时是否会主动思考更好的工作方法，和有创意性的改善工作的方法</td>
					<td>
						<select class="jlEvaluate" disabled="disabled"  style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean3.evaluate6 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean3.evaluate6 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>协作精神</td><td>对于同事的请求乐于帮助、解决，与其他同事相互配合默契</td>
					<td>
						<select class="jlEvaluate" disabled="disabled"  style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean3.evaluate7 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean3.evaluate7 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>考勤情况</td><td>无迟到早退现象，工作时间集中度高</td>
					<td>
						<select class="jlEvaluate" disabled="disabled"  style="width: 100px;">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('DM0056')}" var="dictList">
								<c:if test="${evaluationBean3.evaluate8 == dictList.value }">
									<option selected="selected" value="${dictList.value}">${dictList.label}</option>
								</c:if>
								<c:if test="${evaluationBean3.evaluate8 != dictList.value }">
									<option value="${dictList.value}">${dictList.label}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				
			</tbody>
		</table>
		<div>
		<!-- <label style="text-align: center;width: 80%">
			<input name="opt" id="btnSubmit" type="submit" onclick="submit001(10)" class="btn btn-primary" value="提交申请">
		</label> -->
		<label style="float: right;">
			平均分：<input type="text" id="jlAverage" value="${evaluationBean3.average}" disabled="disabled" style="width: 50px" class="input-mini"/>
		</label>
		</div><BR>
    </div>  
     <c:if test="${!viewsts.canApproval && eeSaleEmp.workflowStatus != 50}">
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
	<c:if test="${!empty eeSaleEmp.act.procInsId}">
        <act:histoicFlow procInsId="${eeSaleEmp.act.procInsId}"/>
    </c:if>
</body>
</html>