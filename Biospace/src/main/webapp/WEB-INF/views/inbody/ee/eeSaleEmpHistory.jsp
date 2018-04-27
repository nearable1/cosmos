<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售员工评价管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
			<input name="year" type="text" id="year" value="${eeSaleEmp.year }" readonly="readonly" style="width: 100px"
			 class="input-mini required Wdate" onclick="WdatePicker({dateFmt:'yyyy', onpicking:function(dp){askDateChange(dp)}});"/>
		</li>
		<li><label style="display: inline-block;width: 80px;">工作计划：</label>
			<input name="nextYear" id="nextYear" type="text" value="${eeSaleEmp.year+1 }" readonly="readonly" style="width: 100px" class="input-mini required"/>
		</li>
		<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="打开"/></li> -->
		<li>
			<label style="display: inline-block;width: 180px;">提交人：</label>
			<input type="text" value="${(fns:getUserById(eeSaleEmp.createBy)).name}" readonly="readonly" style="width: 100px" class="input-mini"/>
			<input type="text" value="${eeSaleEmp.organizeName }" readonly="readonly" style="width: 100px" class="input-mini"/>
			<input type="text" value="${eeSaleEmp.workflowStatus == 50 ? '审批完成':'申请中' }" readonly="readonly" style="width: 100px" class="input-mini"/>
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
					<th width="15%">本年度目标</th>
					<th width="15%">实际业绩(预计至12月底)</th>
					<th width="15%">完成率(%)</th>
					<th width="25%">未达成目标 80%的理由？</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="planCount" value="0"></c:set>
				<c:forEach items="${eeSaleEmp.eeEmpPlans}" var="rowModel" varStatus="status">
					<c:if test="${rowModel.showArea == 1 }">
					<tr>
		        		<td><a style="cursor:pointer;" class="icon-minus-sign" onclick = "javascription:(0)"></a></td>
		        		<td>${rowModel.displayItem1 }</td>
	        			<td>${rowModel.displayItem2 }</td>
	        			<td>${rowModel.displayItem3 }</td>
	        			<td>${rowModel.displayItem4 }</td>
	        			<td>${rowModel.displayItem5 }</td>
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
					<td>
						<select class="ygEvaluate" disabled="disabled" style="width: 100px;">
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
						<select class="ygEvaluate" disabled="disabled" style="width: 100px;">
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
						<select class="ygEvaluate" disabled="disabled" style="width: 100px;">
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
						<select class="ygEvaluate" disabled="disabled" style="width: 100px;">
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
						<select class="ygEvaluate" disabled="disabled" style="width: 100px;">
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
						<select class="ygEvaluate" disabled="disabled" style="width: 100px;">
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
						<select class="ygEvaluate" disabled="disabled" style="width: 100px;">
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
						<select class="ygEvaluate" disabled="disabled" style="width: 100px;">
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
			平均分：<input disabled="disabled" id="ygAverage" type="text" value="${evaluationBean.average }" readonly="readonly" style="width: 50px" class="input-mini"/>
			</label>
		</div>
    	<textarea rows="2" cols="200" readonly="readonly" disabled="disabled" style="width:500px;">${evaluationBean.newRemarks}</textarea>
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
					<th width="15%">本年业绩</th>
					<th width="15%">来年目标</th>
					<th width="15%">增加率(%)</th>
					<th width="25%">为了达成目标需使用的策略</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${eeSaleEmp.eeEmpPlans}" var="rowModel" varStatus="status">
					<c:if test="${rowModel.showArea == 2 }">
						<tr>	
						<c:set var="nextYearMemo" value="${rowModel.displayItem6 }"></c:set>
						<td><a style="cursor:pointer;" class="icon-minus-sign" onclick = "javaScription:(0)"></a></td>
		        		<td>${rowModel.displayItem1 }</td>
		        		<td>${rowModel.displayItem2 }</td>
		        		<td>${rowModel.displayItem3 }</td>
		        		<td>${rowModel.displayItem4 }</td>
		        		<td>${rowModel.displayItem5 }</td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table><br>
		2.请描述一下在来年为了使本人负责的市场更好的成长，打算在哪方面集中精力努力改善？ <br>
		<textarea rows="2" cols="200" readonly="readonly" style="width:500px;">${nextYearMemo }</textarea>
    </div>    

	    <div class="group-box">
	         <div class="group-header" >
	             <strong class="group-title">本年度业务评价书 (组长填写部分)&nbsp;&nbsp;&nbsp;&nbsp;
	             	评价人 <input type="text" value="${(fns:getUserById(evaluationBean2.createBy)).name}" readonly="readonly" style="width: 100px" class="input-mini"/> </strong>
	         </div>
	         1. 请具体描述该组员在本年新的成长、成果.<br>
	         <textarea rows="2" disabled="disabled" cols="200" style="width:500px;">${evaluationBean2.describe1 }</textarea><br>
	         2. 请具体描述该组员在本年做的不足的地方.<br>
	         <textarea rows="2" disabled="disabled" cols="200" style="width:500px;">${evaluationBean2.describe2 }</textarea><br>
	         3.请描述该组员在业务上的长处、特点以及可能性.<br>
	         <textarea rows="2" disabled="disabled" cols="200" style="width:500px;">${evaluationBean2.describe3 }</textarea><br>
	         4. 请描述该组员在工作上的短处以及问题点. <br>
	         <textarea rows="2" disabled="disabled" cols="200" <%-- readonly="${viewsts.canEdit ? true:false}"  --%>style="width:500px;">${evaluationBean2.describe4 }</textarea><br>
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
				平均分：<input type="text" id="zzAverage" value="${evaluationBean2.average}" readonly="readonly" style="width: 50px" class="input-mini"/>
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
	         <textarea rows="2" cols="200" disabled="disabled" style="width:500px;">${evaluationBean3.describe4 }</textarea><br>
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
						<td>工作成果量</td><td>工作效率, 工作的产出/成果量${evaluationBean3.id }</td>
						<td>
							<select disabled="disabled" class="jlEvaluate" style="width: 100px;">
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
							<select disabled="disabled" class="jlEvaluate"  style="width: 100px;">
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
							<select disabled="disabled" class="jlEvaluate"  style="width: 100px;">
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
							<select disabled="disabled" class="jlEvaluate"  style="width: 100px;">
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
							<select disabled="disabled" class="jlEvaluate"  style="width: 100px;">
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
							<select disabled="disabled" class="jlEvaluate"  style="width: 100px;">
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
							<select disabled="disabled" class="jlEvaluate"  style="width: 100px;">
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
							<select disabled="disabled" class="jlEvaluate"  style="width: 100px;">
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
				平均分：<input type="text" id="jlAverage" value="${evaluationBean3.average}" readonly="readonly" style="width: 50px" class="input-mini"/>
			</label>
			</div><BR>
	    </div> 
	</form:form>
</body>
</html>