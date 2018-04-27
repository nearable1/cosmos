<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>销售目标</title>
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
		
		function initPage() {
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
		};
		
		// 明细行员工号变更事件
		$(document).on("change", "input.employeeNo", function(e) {
			var $employeeNo = $(this);
			var $row = $employeeNo.parent().parent();
			var employeeNo = $employeeNo.val();
			var $organize = $employeeNo.parent().parent().find("select.organize");
			
			if (employeeNo == "") {
				$organize.val("");
				$organize.trigger("change");
			} else {

				$.ajax({
			    	url: "${ctx}" + "/common/getOrganize",
			        type: "get",
			        async: false,
			        data: {"employeeId":employeeNo},
			        dataType: "json",
			        success: function (data) {

			        	$organize.val(data.organize);
						$organize.trigger("change");
			        },
			        error: function (msg) {
			        }
			    });
			}
			//e.choice
		});

		// 明细行是否分摊变更事件
		$(document).on("change", "input[type=checkbox]", function(e) {
			var $checkbox = $(this);
			var $row = $checkbox.parent().parent();
			
			var $ifAutoApport = $checkbox.parent().parent().find("input.ifAutoApportFix");
			var $totalAmount = $checkbox.parent().parent().find("input.totalAmountFix");
			var $firstQuarter = $checkbox.parent().parent().find("input.firstQuarterFix");
			var $secondQuarter = $checkbox.parent().parent().find("input.secondQuarterFix");
			var $thirdQuarter = $checkbox.parent().parent().find("input.thirdQuarterFix");
			var $fourthQuarter = $checkbox.parent().parent().find("input.fourthQuarterFix");
			
			if ($checkbox.is(':checked')) {
				$ifAutoApport.val("1");

				$totalAmount.val("");
				$totalAmount.addClass("required");
				$totalAmount.removeAttr("readOnly");
				
				$firstQuarter.val("");
				$firstQuarter.removeClass("required");
				$firstQuarter.attr("readOnly","true");
				
				$secondQuarter.val("");
				$secondQuarter.removeClass("required");
				$secondQuarter.attr("readOnly","true");
				
				$thirdQuarter.val("");
				$thirdQuarter.removeClass("required");
				$thirdQuarter.attr("readOnly","true");
				
				$fourthQuarter.val("");
				$fourthQuarter.removeClass("required");
				$fourthQuarter.attr("readOnly","true");
			} else {
				$ifAutoApport.val("0");

				$totalAmount.val("");
				$totalAmount.removeClass("required");
				$totalAmount.attr("readOnly","true");

				$firstQuarter.val("");
				$firstQuarter.addClass("required");
				$firstQuarter.removeAttr("readOnly");

				$secondQuarter.val("");
				$secondQuarter.addClass("required");
				$secondQuarter.removeAttr("readOnly");

				$thirdQuarter.val("");
				$thirdQuarter.addClass("required");
				$thirdQuarter.removeAttr("readOnly");

				$fourthQuarter.val("");
				$fourthQuarter.addClass("required");
				$fourthQuarter.removeAttr("readOnly");
			}
			//e.choice
		});

		// 季度目标change事件绑定
		$(document).on("change", "input.firstQuarterFix,input.secondQuarterFix,input.thirdQuarterFix,input.fourthQuarterFix", function() {
			var _this = $(this);
			var $row = _this.parent().parent();
			var $totalAmountFix = $row.find("input.totalAmountFix");
			var $firstQuarterFix = $row.find("input.firstQuarterFix");
			var $secondQuarterFix = $row.find("input.secondQuarterFix");
			var $thirdQuarterFix = $row.find("input.thirdQuarterFix");
			var $fourthQuarterFix = $row.find("input.fourthQuarterFix");

			var $totalAmount = $row.find("input.totalAmount");
			var $firstQuarter = $row.find("input.firstQuarter");
			var $secondQuarter = $row.find("input.secondQuarter");
			var $thirdQuarter = $row.find("input.thirdQuarter");
			var $fourthQuarter = $row.find("input.fourthQuarter");

			var firstQuarter = 0;
			if ($firstQuarterFix.val() != null && $firstQuarterFix.val() != "") {
				$firstQuarter.val($firstQuarterFix.val().trim().replace(/,/g, ""));
				firstQuarter = parseFloat($firstQuarter.val());
			} else {
				$firstQuarter.val("");
			}

			var secondQuarter = 0;
			if ($secondQuarterFix.val() != null && $secondQuarterFix.val() != "") {
				$secondQuarter.val($secondQuarterFix.val().trim().replace(/,/g, ""));
				secondQuarter = parseFloat($secondQuarter.val());
			} else {
				$secondQuarter.val("");
			}

			var thirdQuarter = 0;
			if ($thirdQuarterFix.val() != null && $thirdQuarterFix.val() != "") {
				$thirdQuarter.val($thirdQuarterFix.val().trim().replace(/,/g, ""));
				thirdQuarter = parseFloat($thirdQuarter.val());
			} else {
				$thirdQuarter.val("");
			}

			var fourthQuarter = 0;
			if ($fourthQuarterFix.val() != null && $fourthQuarterFix.val() != "") {
				$fourthQuarter.val($fourthQuarterFix.val().trim().replace(/,/g, ""));
				fourthQuarter = parseFloat($fourthQuarter.val());
			} else {
				$fourthQuarter.val("");
			}
			
			$totalAmount.val(firstQuarter + secondQuarter + thirdQuarter + fourthQuarter);
			$totalAmountFix.val(toThousands($totalAmount.val()));
		});

		// 季度目标change事件绑定
		$(document).on("change", "input.totalAmountFix", function() {
			var _this = $(this);
			var $row = _this.parent().parent();
			
			var $totalAmountFix = $row.find("input.totalAmountFix");
			var $firstQuarterFix = $row.find("input.firstQuarterFix");
			var $secondQuarterFix = $row.find("input.secondQuarterFix");
			var $thirdQuarterFix = $row.find("input.thirdQuarterFix");
			var $fourthQuarterFix = $row.find("input.fourthQuarterFix");

			var $totalAmount = $row.find("input.totalAmount");
			var $firstQuarter = $row.find("input.firstQuarter");
			var $secondQuarter = $row.find("input.secondQuarter");
			var $thirdQuarter = $row.find("input.thirdQuarter");
			var $fourthQuarter = $row.find("input.fourthQuarter");

			if ($totalAmountFix.val() != null && $totalAmountFix.val() != "") {

				$totalAmount.val($totalAmountFix.val().trim().replace(/,/g, ""));

				$firstQuarter.val(number($totalAmount.val(), ${fns:toJson(fns:getDictLabel('10', 'DM0045', ''))}).toFixed(2));
				$secondQuarter.val(number($totalAmount.val(), ${fns:toJson(fns:getDictLabel('20', 'DM0045', ''))}).toFixed(2));
				$thirdQuarter.val(number($totalAmount.val(), ${fns:toJson(fns:getDictLabel('30', 'DM0045', ''))}).toFixed(2));
				$fourthQuarter.val(number($totalAmount.val(), ${fns:toJson(fns:getDictLabel('40', 'DM0045', ''))}).toFixed(2));
			} else {
				$totalAmount.val("");
				$firstQuarter.val("");
				$secondQuarter.val("");
				$thirdQuarter.val("");
				$fourthQuarter.val("");
			}
			
			$firstQuarterFix.val(toThousands($firstQuarter.val()));
			$secondQuarterFix.val(toThousands($secondQuarter.val()));
			$thirdQuarterFix.val(toThousands($thirdQuarter.val()));
			$fourthQuarterFix.val(toThousands($fourthQuarter.val()));
		});
		
		function number(arg1, arg2) {
		    var m=0, s1=arg1.toString(), s2=arg2.toString();  
		    try { m += s1.split(".")[1].length } catch(e){}  
		    try { m += s2.split(".")[1].length } catch(e){}  
		    return Number(s1.replace(".","")) * Number(s2.replace(".","")) / Math.pow(10, m);
		}

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
		
			// 添加细行
		$("#addRow").on("click", function() {
			// 查看现有行数
			var rowNum = $("#stSalesTargetTable>tbody>tr:not(.empty)").length + 1;
			// 创建行
			var html = '<tr>'
				html += '<td class="text-center"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>'
				//html += madeSelectList("stSalesTargetList[" + (rowNum - 1) + "].employeeNo", ${fns:toJson(fns:getSqlDictList('dict_employee'))}, "employeeNo")
				html += '<td><input name="stSalesTargetList[' + (rowNum - 1) + '].employeeNo" class="remote employee input-small employeeNo required" type="text" data-show="text" data-type="10,20"/></td>'
				html += madeSelectList("stSalesTargetList[" + (rowNum - 1) + "].organize", ${fns:toJson(fns:getSqlDictList('dict_organize'))}, "organize")
				html += '<td class="text-center"><input name="stSalesTargetList[' + (rowNum - 1) + '].ifAutoApport" class="ifAutoApport" type="hidden" value="1"/><input type="checkbox" checked="checked" /></td>'
				html += '<td><input name="stSalesTargetList[' + (rowNum - 1) + '].totalAmount" type="hidden" value="" class="input-small text-right totalAmount" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>'
				html += '<input type="text" value="" class="input-small text-right totalAmountFix" onblur="numToStr(this);" onfocus="strToNum(this);" /></td>'
				html += '<td><input name="stSalesTargetList[' + (rowNum - 1) + '].firstQuarter" type="hidden" value="" class="input-small text-right firstQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>'
				html += '<input type="text" value="" class="input-small text-right firstQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" readonly/></td>'
				html += '<td><input name="stSalesTargetList[' + (rowNum - 1) + '].secondQuarter" type="hidden" value="" class="input-small text-right secondQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>'
				html += '<input type="text" value="" class="input-small text-right secondQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" readonly/></td>'
				html += '<td><input name="stSalesTargetList[' + (rowNum - 1) + '].thirdQuarter" type="hidden" value="" class="input-small text-right thirdQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>'
				html += '<input type="text" value="" class="input-small text-right thirdQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" readonly/></td>'
				html += '<td><input name="stSalesTargetList[' + (rowNum - 1) + '].fourthQuarter" type="hidden" value="" class="input-small text-right fourthQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>'
				html += '<input type="text" value="" class="input-small text-right fourthQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" readonly/></td>'
				html += '</tr>'

			$("#stSalesTargetTable>tbody").append(html).find("tr.empty").remove();
			$(".remote.employee").select2(getEmplSelectOption());
		});
			
		function madeSelectList(name, selectList, type) {
				
			var html ='';
			html += '<td><select class="input-small ' + type + ' required" name="' + name + '">'
			html += '<option value=""></option>'
			for (var i in selectList) {
				html += '<option value="' + selectList[i].value + '">' + selectList[i].label + '</option>'
			}
			html += '</select></td>'
			return html;
		}
		
		$("#save").on("click", function() {
			if (!$("#searchForm").valid()) {
				return false;
			} else {
	            // 异步数据提交
	            $.ajax({
	                type: "post",
			        async: false,
	                url: "${ctx}" + "/sd/stSalesTarget/save",
	                data: $("#searchForm").serialize(),
	                success: function(oData, oStatus) {
	                	if (oData.success) {
	                		$("#searchForm").submit();
	                	}
	                },
	                error: function(oData, oStatus, eErrorThrow) {
	                }
	            });
			}
		});
		
		// 导出合同明细
		$("#exportDtl").on("click", function() {
			$("#searchForm").attr("action","${ctx}/sd/stSalesTarget/export");
			$("#searchForm").submit();
			$("#searchForm").attr("action","${ctx}/sd/stSalesTarget/list");
		});
	});
	</script>

</head> 
<body>
	<h3 class="text-center page-title">销售目标</h3>
	<form:form id="searchForm" modelAttribute="stSalesTargetSearch" action="${ctx}/sd/stSalesTarget/list" method="get" class="breadcrumb form-search">
		<form:hidden path="year"/>
		<% boolean readonlyFlag = true; %>
		<shiro:hasPermission name="sd:stSalesTarget:edit">
			<c:if test="${stSalesTargetSearch.ifPassYear == '0'}">
				<% readonlyFlag = false; %>
			</c:if>
		</shiro:hasPermission>
		<div class="group-box group-box-first" style="height:auto;">
		    <ul class="ul-form">
				<li>
					<label><span class="help-inline"><font color="red">*</font></span>目标年度：</label>
					<form:input path="yearSearch" type="text" value="" readonly="true" style="width: 100px" class="input-mini required Wdate" onclick="WdatePicker({dateFmt:'yyyy'});"/>
				</li>
	            <li class="btns">
	                <input class="btn btn-primary" type="submit" value="查询">
                	<input id="exportDtl" class="btn btn-primary" type="button" value="一览导出">
				</li>
				<li class="clearfix">
				</li>
				<li></li>
			</ul>
		</div>
		<div class="group-box">
			<div style="overflow-x:scroll;">
				<table id="stSalesTargetTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<% if (!readonlyFlag) { %>
								<th class="text-center" width="20px"><a href="javascript:;" id="addRow"><i class="icon-plus-sign"></i></a></th>
							<% } %>
							<th>销售员</th>
							<th>组别</th>
							<th>自动分摊</th>
							<th>总额</th>
							<th>第一季度</th>
							<th>第二季度</th>
							<th>第三季度</th>
							<th>第四季度</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${!empty stSalesTargetSearch.stSalesTargetList}"> 
								<c:forEach var="item" items="${stSalesTargetSearch.stSalesTargetList}" varStatus="status">
									<tr>
										<% if (!readonlyFlag) { %>
											<td class="text-center" width="20px"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>
										<% } %>
										<% if (!readonlyFlag) { %>
											<td>
												<input name="stSalesTargetList[${status.index}].employeeNo" value="${item.employeeNo}" class="remote employee input-small employeeNo required" data-show="text" type="text" data-type="10,20"/>
												<%-- <select name="stSalesTargetList[${status.index}].employeeNo" class="input-small employeeNo required">
													<option value=""></option>
													<c:forEach items="${fns:getSqlDictList('dict_employee')}" var="employeeNos">
														<option value="${employeeNos.value}" ${employeeNos.value==item.employeeNo?'selected':''}>${employeeNos.label}</option>
													</c:forEach>
												</select> --%>
											</td>
											<td>
												<select name="stSalesTargetList[${status.index}].organize" class="input-small organize required">
													<option value=""></option>
													<c:forEach items="${fns:getSqlDictList('dict_organize')}" var="organizes">
														<option value="${organizes.value}" ${organizes.value==item.organize?'selected':''}>${organizes.label}</option>
													</c:forEach>
												</select>
											</td>
											<td class="text-center">
												<input name="stSalesTargetList[${status.index}].ifAutoApport" class="ifAutoApport" type="hidden" value="${item.ifAutoApport}" />
												<c:if test="${item.ifAutoApport eq '0' || empty item.ifAutoApport}">
													<input type="checkbox"/>
												</c:if>
												<c:if test="${item.ifAutoApport eq '1'}">
													<input type="checkbox" checked="checked" />
												</c:if>
											</td>
										<% } else { %>
											<td>
												<input name="stSalesTargetList[${status.index}].employeeNo" value="${item.employeeNo}" class="remote employee input-small" type="text" data-show="text" data-type="10,20" readonly="readonly"/>
												<%-- <select name="stSalesTargetList[${status.index}].employeeNo" disabled="disabled" class="input-small">
													<option value=""></option>
													<c:forEach items="${fns:getSqlDictList('dict_employee')}" var="employeeNos">
														<option value="${employeeNos.value}" ${employeeNos.value==item.employeeNo?'selected':''}>${employeeNos.label}</option>
													</c:forEach>
												</select> --%>
											</td>
											<td>
												<select name="stSalesTargetList[${status.index}].organize" disabled="disabled" class="input-small">
													<option value=""></option>
													<c:forEach items="${fns:getSqlDictList('dict_organize')}" var="organizes">
														<option value="${organizes.value}" ${organizes.value==item.organize?'selected':''}>${organizes.label}</option>
													</c:forEach>
												</select>
											</td>
											<td class="text-center">
												<input name="stSalesTargetList[${status.index}].ifAutoApport" type="hidden" value="${item.ifAutoApport}" />
												<c:if test="${item.ifAutoApport eq '0' || empty item.ifAutoApport}">
													<input type="checkbox" disabled="disabled" />
												</c:if>
												<c:if test="${item.ifAutoApport eq '1'}">
													<input type="checkbox" disabled="disabled" checked="checked" />
												</c:if>
											</td>
										<% } %>
										<c:if test="${item.ifAutoApport eq '0' || empty item.ifAutoApport}">
							            	<td>
							            		<input name="stSalesTargetList[${status.index}].totalAmount" type="hidden" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#.##"/>" class="input-small text-right totalAmount" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>
							            		<input type="text" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/>" class="input-small text-right totalAmountFix" onblur="numToStr(this);" onfocus="strToNum(this);" readonly/>
							            	</td>
							            	<td>
							            		<input name="stSalesTargetList[${status.index}].firstQuarter" type="hidden" value="<fmt:formatNumber value="${item.firstQuarter}" pattern="#.##"/>" class="input-small text-right firstQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
							            		<input type="text" value="<fmt:formatNumber value="${item.firstQuarter}" pattern="#,##0.00"/>" class="input-small text-right firstQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" <%=readonlyFlag?"readonly":"" %>/>
							            	</td>
							            	<td>
							            		<input name="stSalesTargetList[${status.index}].secondQuarter" type="hidden" value="<fmt:formatNumber value="${item.secondQuarter}" pattern="#.##"/>" class="input-small text-right secondQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
							            		<input type="text" value="<fmt:formatNumber value="${item.secondQuarter}" pattern="#,##0.00"/>" class="input-small text-right secondQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" <%=readonlyFlag?"readonly":"" %>/>
							            	</td>
							            	<td>
							            		<input name="stSalesTargetList[${status.index}].thirdQuarter" type="hidden" value="<fmt:formatNumber value="${item.thirdQuarter}" pattern="#.##"/>" class="input-small text-right thirdQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
							            		<input type="text" value="<fmt:formatNumber value="${item.thirdQuarter}" pattern="#,##0.00"/>" class="input-small text-right thirdQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" <%=readonlyFlag?"readonly":"" %>/>
							            	</td>
							            	<td>
							            		<input name="stSalesTargetList[${status.index}].fourthQuarter" type="hidden" value="<fmt:formatNumber value="${item.fourthQuarter}" pattern="#.##"/>" class="input-small text-right fourthQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
							            		<input type="text" value="<fmt:formatNumber value="${item.fourthQuarter}" pattern="#,##0.00"/>" class="input-small text-right fourthQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" <%=readonlyFlag?"readonly":"" %>/>
							            	</td>
										</c:if>
										<c:if test="${item.ifAutoApport eq '1'}">
							            	<td>
							            		<input name="stSalesTargetList[${status.index}].totalAmount" type="hidden" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#.##"/>" class="input-small text-right totalAmount" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
							            		<input type="text" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/>" class="input-small text-right totalAmountFix" onblur="numToStr(this);" onfocus="strToNum(this);" <%=readonlyFlag?"readonly":"" %>/>
							            	</td>
							            	<td>
							            		<input name="stSalesTargetList[${status.index}].firstQuarter" type="hidden" value="<fmt:formatNumber value="${item.firstQuarter}" pattern="#.##"/>" class="input-small text-right firstQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
							            		<input type="text" value="<fmt:formatNumber value="${item.firstQuarter}" pattern="#,##0.00"/>" class="input-small text-right firstQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" readonly/>
							            	</td>
							            	<td>
							            		<input name="stSalesTargetList[${status.index}].secondQuarter" type="hidden" value="<fmt:formatNumber value="${item.secondQuarter}" pattern="#.##"/>" class="input-small text-right secondQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
							            		<input type="text" value="<fmt:formatNumber value="${item.secondQuarter}" pattern="#,##0.00"/>" class="input-small text-right secondQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" readonly/>
							            	</td>
							            	<td>
							            		<input name="stSalesTargetList[${status.index}].thirdQuarter" type="hidden" value="<fmt:formatNumber value="${item.thirdQuarter}" pattern="#.##"/>" class="input-small text-right thirdQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
							            		<input type="text" value="<fmt:formatNumber value="${item.thirdQuarter}" pattern="#,##0.00"/>" class="input-small text-right thirdQuarterFix" onblur="numToStr(this);" onfocus="strToNum(this);" readonly/>
							            	</td>
							            	<td>
							            		<input name="stSalesTargetList[${status.index}].fourthQuarter" type="hidden" value="<fmt:formatNumber value="${item.fourthQuarter}" pattern="#.##"/>" class="input-small text-right fourthQuarter" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>
							            		<input type="text" value="<fmt:formatNumber value="${item.fourthQuarter}" pattern="#,##0.00"/>" class="input-small text-right fourthQuarterFix"  onblur="numToStr(this);" onfocus="strToNum(this);" readonly/>
							            	</td>
										</c:if>
									</tr>
								</c:forEach>
							</c:when>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
		<div class="group-box">
			<div class="text-center">
				<% if (!readonlyFlag) { %>
					<input id="save" class="btn btn-primary" type="button" value="保存">
				<% } else { %>
					<input id="save" class="btn btn-primary" type="button" value="保存" disabled="disabled">
				<% } %>
			</div>
		</div>
	</form:form>
	<sys:message content="${message}"/>
</body>
</html>