<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>检测记录录入</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					$.ajax({
				    	url: "${ctx}/tm/tmTesting/checkFormData",
				        type: "post",
				        async: false,
				        data: $("#inputForm").serializeArray(), //自动将form表单封装成json
				        dataType: "json",
				        success: function (data) {
				        	if(data.error != null && data.error == "true"){
				        		showError(data.errorMessage);
				        	}else{
				        		// 取得form data对象
				                var formData = new FormData($("#inputForm")[0]);
				                // 获取文件
				                var selFiles = $(".upload-select-btn input[type='file']").data("files");
				                // 如文件存在
				                if (selFiles) {
				                    // 将文件加入form data对象
				                    for (var i = 0; i < selFiles.length; i++) {
				                        formData.append("files", selFiles[i]);
				                    }
				                }

				                // 提交画面数据
				                $.ajax($("#inputForm").attr("action"), {
				                    type: "post",
				                    data: formData,
				                    contentType: false,
				                    processData: false,
				                }).done(function (data) {
				                    //showMessage(data.message);
				                    if (data.success) {
				                        location.href= "${ctx}" + data.url;
				                    }
				                });
				        	}
				        },
				        error: function (msg) {
				        	showError(msg);
				        }
				    });
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
			
		// 选取上传文件按钮点击事件绑定
		$(".upload-select-btn .btn").on("click", function() {
			$(this).parent().find("input[type='file']").trigger("click");
		});

		// file input change事件绑定
		$(".upload-select-btn input[type='file']") .on("change",function(e) {
							// 选择文件数组取得
							var files = $(this).prop('files');
							// 选择的文件大于0，保存选择的文件
							if (files.length > 0) {
								var selFiles = $(this).data("files");
								if (!selFiles) {
									selFiles = [];
								}

								// table body
								var $tbody = $("#attachmentTbl tbody");
								// 生成添加文件行
								var curlength = $tbody.find("tr").length;
								for (var i = 0; i < files.length; i++) {
									var rowid = "file_"
											+ (new Date()).getTime();
									var html = '<tr id="' + rowid + '">';
									html += '<td class="text-center"><a href="javascript:;" class="delFileRow"><i class="icon-minus-sign"></i></a></td>';
									html += '<td class="text-center rowNo" width="35px">'
											+ (curlength + 1) + '</td>';
									html += '<td>' + files[i].name + '</td>';
									html += '</tr>';
									$tbody.append(html);
									$("#" + rowid).data("file", files[i]);
									selFiles.push(files[i]);
									curlength = curlength + 1;
								}

								// 文件数组保存
								$(this).data("files", selFiles);
								// 更新附件table画面显示
								updateAttachmentTbl();
							}
						});

		// 删除附件行
		$(document).on("click","#attachmentTbl .delFileRow",function() {
					var _this = $(this);
					var $tbody = _this.parents("tbody");
					var $delRow = _this.parents("tr");

					// 删除行是否已上传
					if ($delRow.hasClass("uploaded")) {
						// 已上传时
						confirmx("确认要删除该文件吗？", function() {
							$.ajax("${ctx}/tm/tmTesting/delete/file/"+ _this.data("file-id"), {
										type : "post",
										dataType : "json"
									}).done(function(data) {
								$delRow.remove();
								showMessage("文件删除成功");
								// 更新附件table画面显示
								updateAttachmentTbl();
							});
						});
					} else {
						// 未上传时
						// 文件数组取得
						var selFiles = $(
								".upload-select-btn input[type='file']").data("files");
						// 删除对应文件
						selFiles.splice(selFiles.indexOf($delRow.data("file")),1);
						// 删除行
						$delRow.remove();
						// 更新附件table画面显示
						updateAttachmentTbl();
					}
				});

		// 更新附件table画面显示
		function updateAttachmentTbl() {
			// 更新table
			var $tbody = $("#attachmentTbl tbody");
			if ($tbody.find("tr").length == 0) {
				var html = '<tr class="empty">';
				html += '<td class="text-center" colspan="3">请选择附件</td>';
				html += '</tr>';
				$tbody.append(html);
			} else {
				$tbody.find("tr.empty").remove();
			}

			// 重整行号
			$tbody.find("tr").each(function(index) {
				$(this).find("td.rowNo").html(index + 1);
			});
		}
		
		});
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
        		$(this).select2({allowClear: true});
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			$(list + idx + "_materialNo").select2(getMatSelectOption());
			$(".lineNo").each(function(index){ 
    			$(this).val(index+1); 
   			});
			
		}
		function delRow(obj, prefix){
			$(obj).parent().parent().remove();
			
			$("#tmTestingDtlList").find("tr").each(function(index){
				$(this).find("td>input").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {
						$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
					}
				});
				$(this).find("td>label").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {
						$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
					}
                });
				$(this).find("td>select").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {
						$(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
					}
                });
   			});
			$(".lineNo").each(function(index){ 
    			$(this).val(index+1); 
   			});
			
			/* $(obj).parent().parent().hide();
            $(prefix+"_delFlag").val(1);
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			} */
		}
		
		function changeSnNo(rowIndex){
			var rowId = "#tmTestingDtlList"+rowIndex;
			var snNo = $(rowId+"_snNo").val();
			var materialNo = $(rowId+"_materialNo").val();
			if(materialNo == '' || materialNo == null){
				showError("请输入型号。");
				return false;
			}
			$.ajax({
		    	url: "${ctx}/tm/tmTesting/getSnInfo",
		        type: "get",
		        async: false,
		        data: {
		        	materialNo:materialNo,
		        	snNo:snNo
		        },
		        dataType: "json",
		        success: function (data) {
		        	if(data.error != null && data.error == "true"){
		        		showError(data.errorMessage);
		        	}else{
						var snInfo = data.snInfo;
						$(rowId+"_machineTypeName").text(snInfo.machineTypeName);
						$(rowId+"_statusValue").text(snInfo.statusValue);
						$(rowId+"_productionDateValue").text(snInfo.productionDateValue);
						$(rowId+"_snVersion").val(snInfo.snVersion);
			        }
		        },
		        error: function (msg) {
		        	showError(msg);
		        }
		    });
		}

		function changeMaterialNo(rowIndex){
			var rowId = "#tmTestingDtlList"+rowIndex;
			var snNo = $(rowId+"_snNo").val();
			var materialNo = $(rowId+"_materialNo").val();
			if(snNo == '' || snNo == null){
				return false;
			}
			$.ajax({
		    	url: "${ctx}/tm/tmTesting/getSnInfo",
		        type: "get",
		        async: false,
		        data: {
		        	materialNo:materialNo,
		        	snNo:snNo
		        },
		        dataType: "json",
		        success: function (data) {
		        	if(data.error != null && data.error == "true"){
		        		showError(data.errorMessage);
		        	}else{
						var snInfo = data.snInfo;
						$(rowId+"_machineTypeName").text(snInfo.machineTypeName);
						$(rowId+"_statusValue").text(snInfo.statusValue);
						$(rowId+"_productionDateValue").text(snInfo.productionDateValue);
						$(rowId+"_snVersion").val(snInfo.snVersion);
			        }
		        },
		        error: function (msg) {
		        	showError(msg);
		        }
		    });
		}
	</script>
	<style>
	.form-search .ul-form li{
	    width:270px;
	}
    .upload-select-btn {
        margin-bottom: 5px;
    }
    .upload-input {
        display: none !important;
    }
	</style>
</head>
<body>
	<form:form id="inputForm" modelAttribute="tmTesting" action="${ctx}/tm/tmTesting/ajaxsave" method="post" class="form-search">
		<h3 class="text-center page-title">检测记录录入</h3>
		<form:hidden path="id"/>
	    <input name="updateDate" id="updateDate" type="hidden" value="<fmt:formatDate value="${tmTesting.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
	<sys:message content="${message}"/>	
	<div class="group-box group-box-first" style="height:auto;">	
		<ul class="ul-form">
		    <li>
			    <label>检测编号：</label>
				<form:input path="testingNo" htmlEscape="false" style="width:150px;" readonly="true"/>
		    </li>
		    <li>
		        <label><span class="help-inline"><font color="red">*</font></span>检测方式：</label>
				<form:select path="testingType" style="width:150px;"  class="input-medium required">
					<form:options items="${fns:getDictList('DM0024')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		        </form:select>
		    </li>
		    <li>
		        <label><span class="help-inline"><font color="red">*</font></span>检测日期：</label>
			    <input name="testingDate" id="testingDate" class="input-medium Wdate required" style="width:150px;"
			        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text" 
			        maxlength="20" value="<fmt:formatDate value="${tmTesting.testingDate}" pattern="yyyy-MM-dd"/>"/>
			</li>
		    <li>
		        <label>备货日期：</label>
			    <input name="stockingDate" id="stockingDate" class="input-medium Wdate" style="width:150px;" 
			        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" type="text" 
			        maxlength="20" value="<fmt:formatDate value="${tmTesting.stockingDate}" pattern="yyyy-MM-dd"/>"/>
			</li>
		    <li>
		        <label>录入人：</label>
				<form:input path="createPersonName" htmlEscape="false" maxlength="64" style="width:150px;" readonly="true"/>
				<form:input type="hidden" path="createPersonId" htmlEscape="false"/>
		    </li>
		    <li>
		        <label><span class="help-inline"><font color="red">*</font></span>检测人：</label>
		        <form:input path="testingPersonId" class="remote employee required" style="width:150px;" type="text" data-show="text" data-type="20"/>
				<%-- <form:select path="testingPersonId" class="input-medium required" style="width:150px;" >
					<form:option value="" label="" />
					<form:options items="${fns:getSqlDictList('dict_engineer')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select> --%>
			</li>
		    <li>
		        <label><span class="help-inline"><font color="red">*</font></span>检验人：</label>
		        <form:input path="checkPersonId" class="remote employee required" style="width:150px;" type="text" data-show="text" data-type="20"/>
				<%-- <form:select path="checkPersonId" class="input-medium required" style="width:150px;" >
					<form:option value="" label="" />
					<form:options items="${fns:getSqlDictList('dict_engineer')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select> --%>
			</li>
		    <li>
		        <label><span class="help-inline"><font color="red">*</font></span>结果：</label>
				<form:select path="testingResult" class="required" style="width:150px;">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('DM0025')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		        </form:select>
		    </li>
		    <li style="width:1200px;">
		        <label>初期不良：</label>
		        <c:if test="${tmTesting.ifPrimeProblem eq '0' || empty tmTesting.ifPrimeProblem}">
					<input type="checkbox" name="ifPrimeProblem" id="ifPrimeProblem"/>
				</c:if>
		        <c:if test="${tmTesting.ifPrimeProblem eq '1'}">
					<input type="checkbox" name="ifPrimeProblem" id="ifPrimeProblem" checked/>
				</c:if>
				<form:input path="primeProblemComment" htmlEscape="false" maxlength="300" style="width:950px;"/>
		    </li>
		</ul>
	</div>
	<div  class="group-box" >
	<div style="overflow-x:scroll;width:100%;">
				<table id="contentTable" style="width:90%;" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th class="text-center" style="width:10px;">
							<a href="javascript:"
		                       onclick="addRow('#tmTestingDtlList', tmTestingDtlRowIdx, tmTestingDtlTpl);tmTestingDtlRowIdx = tmTestingDtlRowIdx + 1;">
                            <i class="icon-plus-sign"></i></a>
							</th>
							<th style="width:250px;">物料号</th>
							<th style="width:100px;">S/N</th>
							<th style="width:100px;">版本号</th>
							<th style="width:60px;">类型</th>
							<th style="width:80px;">当前状态</th>
							<th style="width:80px;">生产日期</th>
							<th style="width:100px;">包装外观完好</th>
							<th style="width:100px;">仪器外观完好</th>
							<th style="width:120px;">附加说明</th>
							<th style="width:300px;">详细说明</th>
						</tr>
					</thead>
					<tbody id="tmTestingDtlList">
					</tbody>
				</table>
				<script type="text/template" id="tmTestingDtlTpl">//<!--
						<tr id="tmTestingDtlList{{idx}}">
							<td class="text-center" style="width:10px;">
								<input id="tmTestingDtlList{{idx}}_id" name="tmTestingDtlList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tmTestingDtlList{{idx}}_lineNo" name="tmTestingDtlList[{{idx}}].lineNo" type="hidden" class="lineNo" value="{{row.lineNo}}"/>
                                <input id="tmTestingDtlList{{idx}}_delFlag" name="tmTestingDtlList[{{idx}}].delFlag" type="hidden" value="0"/>
                                <a href="javascript:;" onclick="delRow(this, '#tmTestingDtlList{{idx}}')" class="delRow"><i class="icon-minus-sign"></i>
							</td>
							<td>
							    <input id="tmTestingDtlList{{idx}}_materialNo" name="tmTestingDtlList[{{idx}}].materialNo" style="width:250px;"
                                        type="text" value="{{row.materialNo}}" class="remote material required" data-show="text" data-type="1,2,5" onchange="changeMaterialNo({{idx}});"/>
							</td>
							<td>
								<input id="tmTestingDtlList{{idx}}_snNo" name="tmTestingDtlList[{{idx}}].snNo" type="text" value="{{row.snNo}}" 
                                       maxlength="50" style="width:100px;" class="required" onBlur="changeSnNo({{idx}});"/>
							</td>
							<td>
								<input id="tmTestingDtlList{{idx}}_snVersion" name="tmTestingDtlList[{{idx}}].snVersion" type="text" value="{{row.snVersion}}" 
                                       maxlength="50" style="width:100px;" />
							</td>
							<td>
								<label id="tmTestingDtlList{{idx}}_machineTypeName" style="width:60px;" >{{row.machineTypeName}}</label>
							</td>
							<td>
								<label id="tmTestingDtlList{{idx}}_statusValue" style="width:80px;" >{{row.statusValue}}</label>
							</td>
							<td>
								<label id="tmTestingDtlList{{idx}}_productionDateValue" style="width:80px;" >{{row.productionDateValue}}</label>
							</td>
							<td>
                                <select id="tmTestingDtlList{{idx}}_ifPackingGood" name="tmTestingDtlList[{{idx}}].ifPackingGood" style="width:100px;"  data-value="{{row.ifPackingGood}}" class="required">
				                    <option value="" label="" />
					                <c:forEach items="${fns:getDictList('yes_no')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
		                        </select>
							</td>
							<td>
                                <select id="tmTestingDtlList{{idx}}_ifOutlookGood" name="tmTestingDtlList[{{idx}}].ifOutlookGood" style="width:100px;"  data-value="{{row.ifOutlookGood}}" class="input-medium required">
				                    <option value="" label="" />
					                <c:forEach items="${fns:getDictList('yes_no')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
		                        </select>
							</td>
							<td>
                                <select id="tmTestingDtlList{{idx}}_additionalInstructions" name="tmTestingDtlList[{{idx}}].additionalInstructions" style="width:100px;"  data-value="{{row.additionalInstructions}}" class="input-medium">
				                    <option value="" label="" />
					                <c:forEach items="${fns:getDictList('DM0038')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
		                        </select>
							</td>
							<td>
								<input id="tmTestingDtlList{{idx}}_newRemarks" name="tmTestingDtlList[{{idx}}].newRemarks" type="text" value="{{row.newRemarks}}" maxlength="300" style="width:200px;" />
							</td>
						</tr>//-->
					</script>
				<script type="text/template" id="tmTestingDtlTpl1">//<!--
						<tr id="tmTestingDtlList{{idx}}">
							<td class="text-center" style="width:10px;">
								<input id="tmTestingDtlList{{idx}}_id" name="tmTestingDtlList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="tmTestingDtlList{{idx}}_lineNo" name="tmTestingDtlList[{{idx}}].lineNo" type="hidden" class="lineNo" value="{{row.lineNo}}"/>
                                <input id="tmTestingDtlList{{idx}}_delFlag" name="tmTestingDtlList[{{idx}}].delFlag" type="hidden" value="0"/>
                                <a href="javascript:;" onclick="delRow(this, '#tmTestingDtlList{{idx}}')" class="delRow"><i class="icon-minus-sign"></i>
							</td>
							<td>
							    <input id="tmTestingDtlList{{idx}}_materialNo" name="tmTestingDtlList[{{idx}}].materialNo" style="width:250px;"
                                        type="text" value="{{row.materialNo}}" class="remote material required" data-show="text" data-type="1,2,5,7" onchange="changeMaterialNo({{idx}});"/>
							</td>
							<td>
								<input id="tmTestingDtlList{{idx}}_snNo" name="tmTestingDtlList[{{idx}}].snNo" type="text" value="{{row.snNo}}" 
                                       maxlength="50" style="width:100px;" class="required" onBlur="changeSnNo({{idx}});"/>
							</td>
							<td>
								<input id="tmTestingDtlList{{idx}}_snVersion" name="tmTestingDtlList[{{idx}}].snVersion" type="text" value="{{row.snVersion}}" 
                                       maxlength="50" style="width:100px;" />
							</td>
							<td>
								<label id="tmTestingDtlList{{idx}}_machineTypeName" style="width:60px;" >{{row.machineTypeName}}</label>
							</td>
							<td>
								<label id="tmTestingDtlList{{idx}}_statusValue" style="width:80px;" >{{row.statusValue}}</label>
							</td>
							<td>
								<label id="tmTestingDtlList{{idx}}_productionDateValue" style="width:80px;" >{{row.productionDateValue}}</label>
							</td>
							<td>
                                <select id="tmTestingDtlList{{idx}}_ifPackingGood" name="tmTestingDtlList[{{idx}}].ifPackingGood" style="width:100px;"  data-value="{{row.ifPackingGood}}" class="required">
				                    <option value="" label="" />
					                <c:forEach items="${fns:getDictList('yes_no')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
		                        </select>
							</td>
							<td>
                                <select id="tmTestingDtlList{{idx}}_ifOutlookGood" name="tmTestingDtlList[{{idx}}].ifOutlookGood" style="width:100px;"  data-value="{{row.ifOutlookGood}}" class="input-medium required">
				                    <option value="" label="" />
					                <c:forEach items="${fns:getDictList('yes_no')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
		                        </select>
							</td>
							<td>
                                <select id="tmTestingDtlList{{idx}}_additionalInstructions" name="tmTestingDtlList[{{idx}}].additionalInstructions" style="width:100px;"  data-value="{{row.additionalInstructions}}" class="input-medium">
				                    <option value="" label="" />
					                <c:forEach items="${fns:getDictList('DM0038')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
		                        </select>
							</td>
							<td>
								<input id="tmTestingDtlList{{idx}}_newRemarks" name="tmTestingDtlList[{{idx}}].newRemarks" type="text" value="{{row.newRemarks}}" maxlength="300" style="width:200px;" />
							</td>
						</tr>//-->
					</script>
				<script type="text/javascript">
					var tmTestingDtlRowIdx = 0;
					var tmTestingDtlTpl = $("#tmTestingDtlTpl1").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					if ($("#testingType").val() == '2') {
						tmTestingDtlTpl = $("#tmTestingDtlTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					}
					$(document).ready(function() {
						var data = ${fns:toJson(tmTesting.tmTestingDtlList)};
						for (var i=0; i<data.length; i++){
							addRow('#tmTestingDtlList', tmTestingDtlRowIdx, tmTestingDtlTpl, data[i]);
							tmTestingDtlRowIdx = tmTestingDtlRowIdx + 1;
						}
						
						// 检测方式变更事件
						$("#testingType").change(function() {
							var testingType = $("#testingType").val();
							if (testingType == '2') {
								tmTestingDtlTpl = $("#tmTestingDtlTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
								
								$("#contentTable>tbody>tr:not(.empty)").each(function(index){
									$(this).find("td:eq(1)>input").attr("data-type", "1,2,5");
								});
							} else {
								tmTestingDtlTpl = $("#tmTestingDtlTpl1").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
								
								$("#contentTable>tbody>tr:not(.empty)").each(function(index){
									$(this).find("td:eq(1)>input").attr("data-type", "1,2,5,7");
								});
							}
						});
					});
				</script>
		   </div>
		   </div>
	    <div class="group-box" style="padding: 15px 15px;">
			<div class="group-header">
				<strong class="group-title">附件上传</strong>
				<label><font color="red">    最大上传文件大小：10M</font></label>
			</div>
			<div class="upload-select-btn">
				<button type="button" class="btn btn-primary">
					<span>选取文件</span>
				</button>
				<input type="file" multiple="multiple" class="upload-input">
			</div>
			<table id="attachmentTbl" class="table table-striped table-bordered table-condensed" style="width: 50%;">
				<thead>
					<tr>
						<td class="text-center" width="20px"><a href="javascript:;"
							class="disabled"><i class="icon-plus-sign"></i></a></td>
						<td class="text-center" width="35px">No</td>
						<td>文件名</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${tmTesting.attachmentsList}" var="item"
						varStatus="status">
						<tr class="uploaded">
							<td class="text-center"><a href="javascript:;"
								class="delFileRow" data-file-id="${item.id}"><i
									class="icon-minus-sign"></i></a></td>
							<td class="text-center rowNo">${status.index + 1}</td>
							<td><a href="${ctx}/tm/tmTesting/download/file/${item.id}">${item.fileName}.${item.fileType}</a>
								<span class="pull-right"><i class="icon-ok icon-white"></i></span>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty tmTesting.attachmentsList}">
						<tr class="empty">
							<td class="text-center" colspan="3">请选择附件</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
    <div class="text-center"  style="margin-top:8px;">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
	</form:form>
</body>
</html>