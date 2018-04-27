<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商目标</title>
	<meta name="decorator" content="default"/>
	
	<style type="text/css">
/* 	table { */
/* 		table-layout: fixed; */
/* 	} */
/* 	td.ellipsis {   */
/* 	    text-overflow: ellipsis; */
/* 	    -moz-text-overflow: ellipsis; /* for Firefox,mozilla */   */
/* 	    overflow: hidden; */
/* 	    white-space: nowrap; */
/* 	} */
	</style>
	
	<script type="text/javascript">
	$(document).ready(function() {
		
		initPage();
		
		var modalValidate;
		
		function initPage() {
			// 添加画面验证
			modalValidate = $("#modalForm").validate({
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

		$("input[type=checkbox]").change(function(){
			if ($(this).is(':checked')) {
				$("#ifNew").val("1");
			} else {
				$("#ifNew").val("0");
			}
		});

		$(document).on("click", "table .delRow", function() {
			var $tbody = $(this).parents("tbody");
			$(this).parents("tr").remove();
			$tbody.find("tr").each(function(index){ 
				$(this).find("input").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {

						$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));

					}
				});
				$(this).find("td>select").each(function() {
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
		});

		$("#addRow").on("click", function() {
			// 查看现有行数
			var index = $("#atAgentTargetDtlTable>tbody>tr:not(.empty)").length;
			
			var targetType = $("input[name='targetType']:checked").val();
			// 创建行
			var html = '<tr>'
				html += '<td class="text-center"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>'
				html += '<td>' + madePeriodSelectList(null, ${fns:toJson(fns:getDictList('DM0046'))}, "atAgentTargetDtlList[" + index + "].period") + '</td>'
				if (targetType == '1') {

					html += '<td><input name="atAgentTargetDtlList[' + index + '].totalAmount" type="hidden" value="" class="input-medium text-right required totalAmount" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>'
					html += '<input type="text" value="" class="input-medium text-right required totalAmountFix" onblur="numToStr(this);" onfocus="strToNum(this);" /></td>'
					html += '<td><input name="atAgentTargetDtlList[' + index + '].materialNo" type="text" value="" class="input-medium" readonly/></td>'
					html += '<td><input name="atAgentTargetDtlList[' + index + '].num" type="text" value="" class="input-medium" readonly/></td>'
				} else if (targetType == '2') {

					html += '<td><input name="atAgentTargetDtlList[' + index + '].totalAmount" type="text" value="" class="input-medium" readonly/></td>'
					html += '<td><input name="atAgentTargetDtlList[' + index + '].materialNo" type="text" data-type="1,3" data-show="model" value="" class="input-medium remote material required"/></td>'
					html += '<td><input name="atAgentTargetDtlList[' + index + '].num" type="text" value="" maxlength="3" class="input-medium text-right digits required"/></td>'
				}
				html += '</tr>';

			$("#atAgentTargetDtlTable>tbody").append(html).find("tr.empty").remove();
			$(".remote.material").select2(getMatSelectOption());
		});
		
		$('#atAgentTargetDtlModal').on('hidden.bs.modal', function () {
			modalValidate.resetForm();
		});
		
		$("#modalSave").on("click", function() {
			if (!$("#modalForm").valid()) {
				return false;
			} else {
	            // 异步数据提交
	            $.ajax({
	                type: "post",
			        async: false,
	                url: "${ctx}" + "/sd/atAgentTarget/saveAtAgentTarget",
	                data: $("#modalForm").serialize(),
	                success: function(oData, oStatus) {
	                	if (oData.success) {

	        				$("#atAgentTargetDtlModal").modal('hide');
	                		$("#searchForm").submit();
	                	}
	                },
	                error: function(oData, oStatus, eErrorThrow) {
	                }
	            });
			}
		});

		$('#atAgentTargetDtlModal').on('shown.bs.modal', function (e) {

	        // radiobox change椒件绑定
	        $("input[name='targetType']").change(function() {
	        	if ($("#atAgentTargetDtlTable>tbody>tr:not(.empty)").length > 0) {
					top.$.jBox.confirm("将清除明细信息！","系统提示",function(v,h,f){
						if(v=="ok"){
			            	$("#atAgentTargetDtlTable>tbody>tr").each(function() {
			            		$(this).remove();
			            	});
						}
					},{buttonsFocus:1});
					top.$('.jbox-body .jbox-icon').css('top','55px');
	        	}
	        });
		});

		// 阶段总额change事件绑定
		$(document).on("change", "input.totalAmountFix", function() {
			var _this = $(this);
			var $row = _this.parent().parent();
			
			var $totalAmountFix = $row.find("input.totalAmountFix");

			var $totalAmount = $row.find("input.totalAmount");

			if ($totalAmountFix.val() != null && $totalAmountFix.val() != "") {

				$totalAmount.val($totalAmountFix.val().trim().replace(/,/g, ""));
			} else {
				$totalAmount.val("");
			}
		});
	});
		function page(n, s) {
			if (n)
				$("#pageNo").val(n);
			if (s)
				$("#pageSize").val(s);

			$("#searchForm").submit();
			return false;
		}

		function modalOpen(agreementId) {
				$.ajax({
					url : "${ctx}" + "/sd/atAgentTarget/edit",
					type : "get",
					async : false,
					data : {"agreementId" : agreementId},
					dataType : "json",
					success : function(data) {
						$("#atAgentTargetDtlModal").modal('show').css({
		                    width: '1024px',
		                    'margin-left': '-512px'
		                });
						var atAgentTarget = data.atAgentTarget;
						var atAgentTargetDtlList = atAgentTarget.atAgentTargetDtlList;
						$("#agreementPatiesName").val(atAgentTarget.agreementPatiesName);
						$("#agreementPatiesId").val(atAgentTarget.agreementPatiesId);
						$("#agreementId").val(atAgentTarget.agreementId);
						$("#validityDateFrom").val(atAgentTarget.validityDateFromForJsp);
						targetTypeHtml(atAgentTarget.targetType);
						atAgentTargetDtlHtml(atAgentTargetDtlList, atAgentTarget.targetType);
					},
					error : function(msg) {
					}
				});
			}

		function targetTypeHtml(targetType) {
			var checked1 = '';
			var checked2 = '';
			if (targetType == '1') {
				checked1 = 'checked';
			} else if (targetType == '2') {
				checked2 = 'checked';
			} else {
				checked1 = 'checked';
			}
			var html = '';
			html += '<label><span class="help-inline"><font color="red">*</font></span>目标类型：</label>'
			html += '<label class="radio inline">'
			html += '<input type="radio" class="required" name="targetType" value="1"' + checked1 + '> 金额目标</label>'
			html += '<label class="radio inline">'
			html += '<input type="radio" class="required" name="targetType" value="2"' + checked2 + '> 数量目标</label>'
			$("#targetTypeHtml").html(html);
		}

		function atAgentTargetDtlHtml(atAgentTargetDtlList, targetType) {
			var html = '';
			var index = 0;
			for (var i in atAgentTargetDtlList) {
				html += '<tr>'
				html += '<td class="text-center" width="20px"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>'
				html += '<td>' + madePeriodSelectList(atAgentTargetDtlList[i].period, ${fns:toJson(fns:getDictList('DM0046'))}, "atAgentTargetDtlList[" + index + "].period") + '</td>'
				if (targetType == '1') {

					html += '<td><input name="atAgentTargetDtlList[' + index + '].totalAmount" type="hidden" value="' + atAgentTargetDtlList[i].totalAmount + '" class="input-medium text-right required totalAmount" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>'
					html += '<input type="text" value="' + toThousands(atAgentTargetDtlList[i].totalAmount) + '" class="input-medium text-right required totalAmountFix" onblur="numToStr(this);" onfocus="strToNum(this);" /></td>'

					html += '<td><input name="atAgentTargetDtlList[' + index + '].materialNo" type="text" value="" class="input-medium" readonly/></td>'
					html += '<td><input name="atAgentTargetDtlList[' + index + '].num" type="text" value="" class="input-medium" readonly/></td>'
				} else if (targetType == '2') {

					html += '<td><input name="atAgentTargetDtlList[' + index + '].totalAmount" type="text" value="" class="input-medium" readonly/></td>'

					html += '<td><input name="atAgentTargetDtlList[' + index + '].materialNo" data-type="1,3" data-show="model" type="text" value="' + atAgentTargetDtlList[i].materialNo + '" class="input-medium remote material required"/></td>'
					html += '<td><input name="atAgentTargetDtlList[' + index + '].num" type="text" value="' + atAgentTargetDtlList[i].num + '" maxlength="3" class="input-medium text-right digits required"/></td>'
				}
				html += '</tr>';
				index++;
			}
			$("#atAgentTargetDtlHtml").html(html);
			$(".remote.material").select2(getMatSelectOption());
		}
			
		function madePeriodSelectList(value, selectList, name) {

			var html = '';
			html += '<select class="input-medium required" name="' + name + '">'
			html += '<option value=""></option>'
			for ( var i in selectList) {
				if (value == selectList[i].value) {

					html += '<option value="' + selectList[i].value + '" selected>'
					+ selectList[i].label + '</option>'
				} else {

					html += '<option value="' + selectList[i].value + '">'
					+ selectList[i].label + '</option>'
				}
			}
			html += '</select>'

			return html;
		}
	</script>

</head>
<body>
	<h3 class="text-center page-title">代理商目标</h3>
	<form:form id="searchForm" modelAttribute="atAgentTargetSearch" action="${ctx}/sd/atAgentTarget/list" method="get" class="breadcrumb form-search">
		
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

		<div class="group-box group-box-first" style="height:auto;">
		    <ul class="ul-form">
				<li>
					<label>代理商：</label>
					<form:input path="customerId" type="text" class="remote customer input-xxlarge" data-type="2" data-show="text"/>
				</li>
				<li>
					<label style="width:120px">
						<c:if test="${atAgentTargetSearch.ifNew == '1'}">
							<form:input path="ifNew" type="hidden" value="1"/>
							<input type="checkbox" class="inline" checked="checked"/>
						</c:if>
						<c:if test="${atAgentTargetSearch.ifNew == '0' || empty atAgentTargetSearch.ifNew}">
							<form:input path="ifNew" type="hidden" value="0"/>
							<input type="checkbox" class="inline"/>
						</c:if>
						仅显示最新协议
					</label>
				</li>
	            <li class="btns">
	                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();">
	                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/sd/atAgentTarget/list';">
				</li>
			</ul>
		</div>
		<div class="group-box">
			<div style="overflow-x:scroll;">
				<table id="atAgentTargetTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>代理商</th>
							<th>协议开始月</th>
							<%int colspans = 2; %>
							<c:forEach var="period" items="${fns:getDictList('DM0046')}">
								<%colspans++; %>
								<th>第${period.label}阶段目标</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
				        <c:if test="${!empty page.list}"> 
				            <c:forEach var="item" items="${page.list}" varStatus="status">
							    <tr>
			 				        <td><a href="javascript:;" class="link" onclick="modalOpen('${item.agreementId}');">${item.customerName}</a></td>
							        <td><fmt:formatDate value="${item.validityDateFrom}" pattern="yyyy-MM"/></td>
							        <%-- <td rowspan="${item.listSize + 1}">${fns:getDictLabel(item.targetType, 'DM0018', '')}</td> --%>
							        
							        <c:forEach var="period" items="${fns:getDictList('DM0046')}">
							        	<td class="text-center">
								        	<c:choose>
								        		<c:when test="${!empty item.periodList[period.value] && fn:length(item.periodList[period.value]) > 0}">
								        			<c:if test="${item.targetType == '1'}">
								        				${fns:getDictLabel(item.targetType, 'DM0018', '')}:<fmt:formatNumber value="${item.periodTotalAmount[period.value]}" pattern="#,##0.00"/>
								        			</c:if>
								        			<c:if test="${item.targetType == '2'}">
								        				${fns:getDictLabel(item.targetType, 'DM0018', '')}:
								        				<c:forEach var="atAgentTargetList" items="${item.periodList[period.value]}">
								        					<br>${atAgentTargetList.model} ${atAgentTargetList.num}台
								        				</c:forEach>
								        			</c:if>
								        		</c:when>
								        	</c:choose>
							        	</td>
							        </c:forEach>
							    </tr>
				            </c:forEach> 
				        </c:if>
				        <c:if test="${empty page.list}">
						    <tr><td class="text-center" colspan="<%=colspans%>">没有您需要的查询结果</td></tr>
				        </c:if>
					</tbody>
				</table>
			</div>
		</div>
	</form:form>
    <div class="pagination">${page}</div>
			<div class="modal fade" id="atAgentTargetDtlModal" style="display:none;" data-backdrop="static" >
				<div class="modal-dialog">
					<div class="modal-content">
					<form:form id="modalForm" modelAttribute="atAgentTarget" action="${ctx}/sd/atAgentTarget/save" method="post" class="form-search">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" >&times;</button>
							<h4 class="text-center modal-title">代理商目标录入</h4>
						</div>
						<div class="modal-body">
							<div class="group-box group-box-first">
							    <ul class="ul-form">
							        <li>
							            <label>代理商：</label>
							            <input id="agreementPatiesName" class="input-xxlarge" type="text" readonly/>
							            <input id="agreementPatiesId" name="agreementPatiesId" type="hidden"/>
							            <input id="agreementId" name="agreementId" type="hidden"/>
							        </li>
							        <li>
							            <label>协议开始月：</label>
							            <input id="validityDateFrom" type="text" class="input-mini" readonly/>
							        </li>
							        <li class="clearfix"></li>
					                <li>
					                	<div id="targetTypeHtml"></div>
					                </li>
							     </ul>
							</div>
						</div>
						<div class="group-box" style="height:220px;overflow-y:scroll;">
							<table id="atAgentTargetDtlTable" class="table table-striped table-bordered table-condensed" >
								<thead>
									<tr>
										<td class="text-center" width="20px"><a href="javascript:;" id="addRow"><i class="icon-plus-sign"></i></a></td>
										<td>阶段</td>
										<td>阶段总额</td>
										<td>机型</td>	
										<td>台数</td>
									</tr>
								</thead>
								<tbody id="atAgentTargetDtlHtml">
								</tbody>	
							</table>
						</div>
						<div class="modal-footer">
							<div class="text-center">
						        <input id="modalSave" class="btn btn-primary" type="button" value="保存">
							</div>
						</div>
						</form:form>
					</div>
				</div>
			</div>
	<sys:message content="${message}"/>
</body>
</html>