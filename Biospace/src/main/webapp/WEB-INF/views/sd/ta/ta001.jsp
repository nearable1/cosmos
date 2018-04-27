<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>招标授权管理</title>
	<meta name="decorator" content="default"/>

    <style type="text/css">
    .upload-select-btn {
        margin-bottom: 5px;
    }
    .upload-input {
        display: none !important;
    }
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					form.submit();
				},
				/* submitHandler: function(form){

					$.ajax({
				    	url: "${ctx}/sd/taTenderAuthor/checkFormData",
				        type: "post",
				        async: false,
				        data: formData, //自动将form表单封装成json
				        dataType: "json",
				        success: function (data) {
				        	if(data.error != null && data.error == "true"){
				        		showError(data.errorMessage);
				        	}else{
				        		if(data.warning != null && data.warning == "true"){
				        			confirmx(data.warningMessage, function () {
										form.submit();
					                 });
					        	}else{
					        		form.submit();
					        	}
				        	}
				        },
				        error: function (msg) {
				        	showError(msg);
				        }
				   });
				},  */
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
            $(".upload-select-btn input[type='file']").on("change", function(e) {
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
                        var rowid = "file_" + (new Date()).getTime();
                        var html = '<tr id="' + rowid + '">';
                            html += '<td class="text-center"><a href="javascript:;" class="delFileRow"><i class="icon-minus-sign"></i></a></td>';
                            html += '<td class="text-center rowNo" width="35px">' + (curlength + 1) + '</td>';
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

            // 申请提交
            $("input[type='button'].opt1").on("click", function() {
                if (!$("#inputForm").valid()) {
                    return false;
                } else {
                	$("#opt").val($(this).val());

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
					$.ajax({
                        type: "post",
                        contentType: false,
                        processData: false,
                        url: "${ctx}/sd/taTenderAuthor/checkFormData",
                        data: formData,
                        success: function(oData, oStatus) {
                            if (oData.success) {
                            	if (oData.warning != null && oData.warning != "") {
                            		confirmx(oData.warning, function () {
                    					$.ajax({
                                            type: "post",
                                            contentType: false,
                                            processData: false,
                                            url: "${ctx}/sd/taTenderAuthor/save",
                                            data: formData,
                                            success: function(oData, oStatus) {
                    				           	if (oData.success) {
                    				           		showMessage("操作成功");
                    				           		if ("保存" == $("#opt").val()) {

                        				           		window.location.href="${ctx}/sd/taTenderAuthor/form?id=" + $("#id").val();
                    				           		} else {

                        				           		window.location.href="${ctx}/act/task/todo";
                    				           		}
                    				           	}
                    				        }
                    					}); 
					                 });
                            	} else {

                					$.ajax({
                                        type: "post",
                                        contentType: false,
                                        processData: false,
                                        url: "${ctx}/sd/taTenderAuthor/save",
                                        data: formData,
                                        success: function(oData, oStatus) {
                				           	if (oData.success) {
                				           		showMessage("操作成功");
                				           		if ("保存" == $("#opt").val()) {

                    				           		window.location.href="${ctx}/sd/taTenderAuthor/form?id=" + $("#id").val();
                				           		} else {

                    				           		window.location.href="${ctx}/act/task/todo";
                				           		}
                				           	}
                				        }
                					}); 
                            	}
                            }
                        }
                    });
                }
            });
		});

        // 删除附件行
        $(document).on("click", "#attachmentTbl .delFileRow", function() {
            var _this = $(this);
            var $tbody = _this.parents("tbody");
            var $delRow = _this.parents("tr");

            // 删除行是否已上传
            if ($delRow.hasClass("uploaded")) {
                // 已上传时
                confirmx("确认要删除该文件吗？", function () {
                    $.ajax("${ctx}/sd/taTenderAuthor/delete/file/" + _this.data("file-id"), {
                        type: "post",
                        dataType: "json"
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
                var selFiles = $(".upload-select-btn input[type='file']").data("files");
                // 删除对应文件
                selFiles.splice(selFiles.indexOf($delRow.data("file")), 1);

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
		$(document).on("change", "#contentTable input.materialNo",function(e){
			// 取得控件的ID
			var materialId = $(this).attr('id');
			var rowId = materialId.split('_')[0];
			rowId = "#"+rowId;
			$(rowId+"_model").text(e.added.model);
		});
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("#taTenderAuthorDtlList"+idx+"_lineNo").each(function(){
				$(this).val(idx+1);
			});
			$(list + idx + "_materialNo").select2(getMatSelectOption());
		}
		
		function delRow(obj, prefix){
			$(obj).parent().parent().remove();

			$("#taTenderAuthorDtlList").find("tr").each(function(index){
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
   			});
			/*$(obj).parent().parent().hide();
            $(prefix+"_delFlag").val(1);
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
	</script>
	<style>
        .form-search .ul-form li label{
            width:120px;
        }
    </style>
</head>
<body>
	<form:form id="inputForm" modelAttribute="taTenderAuthor" action="${ctx}/sd/taTenderAuthor/save" method="get" class="form-search">
		<h3 class="text-center page-title">招标授权录入</h3>
		<form:hidden path="id"/>
        <form:hidden path="act.taskId"/>
        <form:hidden path="act.procInsId"/>
        <form:hidden path="workflowStatus"/>
        <form:hidden path="businessOppId"/>
	    <input name="updateDate" id="updateDate" type="hidden" 
	           value="<fmt:formatDate value="${taTenderAuthor.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
		<sys:message content="${message}"/>	
		
        <shiro:hasPermission name="cm:manager:edit">
            <c:set var="isManagerEdit" value="${viewsts.isApprovalCompleted ? true : false}"></c:set>
        </shiro:hasPermission>
        <shiro:lacksPermission name="cm:manager:edit">
            <c:set var="isManagerEdit" value="false"></c:set>
        </shiro:lacksPermission>
        
	    <div class="group-box group-box-first" style="height:auto;">	
		<ul class="ul-form">
		<li>
			<label>招标编号：</label>
			<c:if test="${viewsts.canEdit}">
			<form:input path="authorizationNo" htmlEscape="false" 
			        maxlength="50" style="width:150px;"/>
			</c:if>
			<c:if test="${!viewsts.canEdit}">
			<form:input path="authorizationNo" htmlEscape="false" 
			        maxlength="50" style="width:150px;" readonly="true"/>
			</c:if>
		</li>
		<li>
			<label><span class="help-inline"><font color="red">*</font> </span>项目名称：</label>
			<c:if test="${viewsts.canEdit}">
			<form:input path="projectName" htmlEscape="false" maxlength="100" class="required" style="width:350px;"/>
			</c:if>
			<c:if test="${!viewsts.canEdit}">
			<form:input path="projectName" htmlEscape="false" maxlength="100" class="required" style="width:350px;" readonly="true"/>
			</c:if>
		</li>
		<li>
			<label><span class="help-inline"><font color="red">*</font> </span>投标目的：</label>
			<c:if test="${viewsts.canEdit}">
			    <form:select path="bidPurpose"  style="width:150px;" class="required">
					<form:options items="${fns:getDictList('DM0013')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
			</c:if>
			<c:if test="${!viewsts.canEdit}">
			<form:hidden path="bidPurpose" htmlEscape="false"/>
			<input type="text" value="${fns:getDictLabel(taTenderAuthor.bidPurpose, 'DM0013', '')}" style="width:150px;" readonly="readonly"/>
			</c:if>
		</li>
		<li>
			<label><span class="help-inline"><font color="red">*</font> </span>招标性质：</label>
			<c:if test="${viewsts.canEdit}">
			    <form:select path="tenderType"  style="width:150px;" class="required">
				<form:options items="${fns:getDictList('DM0014')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			</c:if>
			<c:if test="${!viewsts.canEdit}">
			<form:hidden path="tenderType" htmlEscape="false"/>
			<input type="text" value="${fns:getDictLabel(taTenderAuthor.tenderType, 'DM0014', '')}" style="width:150px;" readonly="readonly"/>
			</c:if>
		</li>
		<li>
			<label><span class="help-inline"><font color="red">*</font> </span>招标单位：</label>
			<c:if test="${viewsts.canEdit}">
			<form:input path="tenderee" htmlEscape="false" maxlength="100" class="required" style="width:350px;"/>
			</c:if>
			<c:if test="${!viewsts.canEdit}">
			<form:input path="tenderee" htmlEscape="false" maxlength="100" class="required" style="width:350px;" readonly="true"/>
			</c:if>
		</li>
		<li>
			<label><span class="help-inline"><font color="red">*</font> </span>开标日期：</label>
			<c:if test="${viewsts.canEdit}">
			<input name="bidOpeningDate" type="text" 
			        style="width:150px;" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${taTenderAuthor.bidOpeningDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			</c:if>
			<c:if test="${!viewsts.canEdit}">
			<input name="bidOpeningDate" type="text" 
			        style="width:150px;" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${taTenderAuthor.bidOpeningDate}" pattern="yyyy-MM-dd"/>"
					readonly="readonly">
			</c:if>
		</li>
		<li>
			<label><span class="help-inline"><font color="red">*</font> </span>最终客户：</label>
			<%-- <input name="endCustomerId" type="text" value="${taTenderAuthor.endCustomerId}" 
				   maxlength="50" style="width:350px;" data-type="1"
			       data-show="text" class="remote customer required customerId input-medium"/> --%>
			<input type="text" disabled="disabled" style="width:350px;" value="${taTenderAuthor.endCustomerName}" />
			<form:hidden path="endCustomerId"/>
			<form:hidden path="endCustomerName"/>
		</li>
		<li>
		    <label>行业：</label>
		    <form:select path="industry" style="width:150px;" disabled="true" class="input-medium">
				<form:option value=""></form:option>
				<form:options items="${fns:getDictList('DM0002')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</li>
		<li>
			<label>科室/系：</label>
			<%-- <form:select path="customerSegmentation"  style="width:150px;" class="input-medium">
			    <form:option value=""></form:option>
			    <form:options items="${fns:getDictListByParentId(taTenderAuthor.industry,'DM0039')}" itemLabel="label" itemValue="value" htmlEscape="false" />
			</form:select> --%>
			<input type="text" disabled="disabled" style="width:150px;" value="${fns:getDictLabel(taTenderAuthor.customerSegmentation, 'DM0039', '')}" />
			<form:hidden path="customerSegmentation"/>
		</li>
		<li>
			<label>招标结果：</label>
			<c:if test="${viewsts.canEdit || isManagerEdit}">
			<form:select path="bidResult" style="width:150px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DM0044')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			</c:if>
			<c:if test="${!viewsts.canEdit && !isManagerEdit}">
			<form:hidden path="bidResult" htmlEscape="false"/>
			<input type="text" value="${fns:getDictLabel(taTenderAuthor.bidResult, 'DM0044', '')}" style="width:150px;" readonly="readonly"/>
			</c:if>
		</li>
		<li>
			<label>备注：</label>
			<c:if test="${viewsts.canEdit || isManagerEdit}">
			<form:input path="bidRemarks" htmlEscape="false" maxlength="300"  style="width:350px;"/>
		    </c:if>
			<c:if test="${!viewsts.canEdit && !isManagerEdit}">
			<form:input path="bidRemarks" htmlEscape="false" maxlength="300"  style="width:350px;" readonly="true"/>
		    </c:if>
		</li>
		<li>
			<label>申请人：</label>
			<input type="text" disabled="disabled" style="width:150px;" value="${taTenderAuthor.applyName}" />
		</li>
		</ul>
		</div>
	<div class="group-box" style="height:auto;">
	    <h4>授权对象信息</h4>
		<ul class="ul-form">
		<li>
			<label><span class="help-inline"><font color="red">*</font> </span>代理商/经销商：</label>
			<%-- <input id="agentId" name="agentId" type="text" value="${taTenderAuthor.agentId}" maxlength="50"
			       data-show="text" style="width:350px;" data-type="2,3"
			       class="required remote customer customerId input-medium"/> --%>
			<input type="text" disabled="disabled" style="width:350px;" value="${taTenderAuthor.agentName}" />
			<form:hidden path="agentId"/>
			<form:hidden path="agentName"/>
		</li>
		<li>
			<label>联系人：</label>
			<c:if test="${viewsts.canEdit}">
			<form:input path="contactsName" htmlEscape="false" maxlength="100" style="width:150px;"/>
		    </c:if>
			<c:if test="${!viewsts.canEdit}">
			<form:input path="contactsName" htmlEscape="false" maxlength="100" style="width:150px;" readonly="true"/>
		    </c:if>
		</li>
		<li>
			<label>电话：</label>
			<c:if test="${viewsts.canEdit}">
			<form:input path="telephone" htmlEscape="false" maxlength="50" style="width:150px;"/>
		    </c:if>
			<c:if test="${!viewsts.canEdit}">
			<form:input path="telephone" htmlEscape="false" maxlength="50" style="width:150px;" readonly="true"/>
		    </c:if>
		</li>
		<li>
			<label>联系地址：</label>
			<c:if test="${viewsts.canEdit}">
			<form:input path="address" htmlEscape="false" maxlength="300" style="width:350px;"/>
		    </c:if>
			<c:if test="${!viewsts.canEdit}">
			<form:input path="address" htmlEscape="false" maxlength="300" style="width:350px;" readonly="true"/>
		    </c:if>
		</li>
		<li>
			<label><span class="help-inline"><font color="red">*</font> </span>授权有效期间：</label>
			<c:if test="${viewsts.canEdit}">
			<input name="validityDateFrom" type="text" 
			        style="width:150px;" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${taTenderAuthor.validityDateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
		    </c:if>
			<c:if test="${!viewsts.canEdit}">
			<input name="validityDateFrom" type="text" 
			        style="width:150px;" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${taTenderAuthor.validityDateFrom}" pattern="yyyy-MM-dd"/>"
					readonly="readonly"/>
		    </c:if>
			~
			<c:if test="${viewsts.canEdit}">
			<input name="validityDateTo" type="text" 
			        style="width:150px;" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${taTenderAuthor.validityDateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
		    </c:if>
			<c:if test="${!viewsts.canEdit}">
			<input name="validityDateTo" type="text" 
			        style="width:150px;" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${taTenderAuthor.validityDateTo}" pattern="yyyy-MM-dd"/>"
					readonly="readonly"/>
		    </c:if>
		</li>
		<li>
			<label>授权型号：</label>
			<c:if test="${viewsts.canEdit}">
			<table id="contentTable"  style="width:600px;" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th style="width:50px;"><a href="javascript:"
		                             onclick="addRow('#taTenderAuthorDtlList', taTenderAuthorDtlRowIdx, taTenderAuthorDtlTpl);taTenderAuthorDtlRowIdx = taTenderAuthorDtlRowIdx + 1;">
                                     <i class="icon-plus-sign"></i></a>
                                </th>
								<th style="width:200px;">物料号</th>
								<th style="width:350px;">型号</th>
							</tr>
						</thead>
						<tbody id="taTenderAuthorDtlList">
						</tbody>
					</table>
					<script type="text/template" id="taTenderAuthorDtlTpl">
						<tr id="taTenderAuthorDtlList{{idx}}">
							<td>
								<input id="taTenderAuthorDtlList{{idx}}_id" name="taTenderAuthorDtlList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="taTenderAuthorDtlList{{idx}}_lineNo" name="taTenderAuthorDtlList[{{idx}}].lineNo" type="hidden" value="{{row.lineNo}}"/>
								<input id="taTenderAuthorDtlList{{idx}}_delFlag" name="taTenderAuthorDtlList[{{idx}}].delFlag" type="hidden" value="0"/>
                                <a href="javascript:;" onclick="delRow(this, '#taTenderAuthorDtlList{{idx}}')" class="delRow"><i class="icon-minus-sign"></i>							
                            </td>
							<td>
                                <input  id="taTenderAuthorDtlList{{idx}}_materialNo" name="taTenderAuthorDtlList[{{idx}}].materialNo" style="width:90%;"
                                        type="text" value="{{row.materialNo}}" class="remote materialNo" data-type="1,3,4,5,6"/>
							</td>
							<td>
                                <label id="taTenderAuthorDtlList{{idx}}_model" name="taTenderAuthorDtlList[{{idx}}].model" style="width:280px;text-align:left;" >{{row.model}}</label>
							</td>
						</tr>
					</script>
					<script type="text/javascript">
						var taTenderAuthorDtlRowIdx = 0, taTenderAuthorDtlTpl = $("#taTenderAuthorDtlTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(taTenderAuthor.taTenderAuthorDtlList)};
							for (var i=0; i<data.length; i++){
								addRow('#taTenderAuthorDtlList', taTenderAuthorDtlRowIdx, taTenderAuthorDtlTpl, data[i]);
								taTenderAuthorDtlRowIdx = taTenderAuthorDtlRowIdx + 1;
							}
						});
					</script>
			</c:if>
			<c:if test="${!viewsts.canEdit}">
			<table id="contentTable"  style="width:600px;" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th style="width:200px;">物料号</th>
						<th style="width:350px;">型号</th>
					</tr>
				</thead>
				<tbody id="taTenderAuthorDtlList">
				</tbody>
			</table>
			<script type="text/template" id="taTenderAuthorDtlTpl">
						<tr id="taTenderAuthorDtlList{{idx}}">
							<td>
                                <input id="taTenderAuthorDtlList{{idx}}_id" name="taTenderAuthorDtlList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="taTenderAuthorDtlList{{idx}}_lineNo" name="taTenderAuthorDtlList[{{idx}}].lineNo" type="hidden" value="{{row.lineNo}}"/>
								<input id="taTenderAuthorDtlList{{idx}}_delFlag" name="taTenderAuthorDtlList[{{idx}}].delFlag" type="hidden" value="0"/>
                                <input id="taTenderAuthorDtlList{{idx}}_materialNo" name="taTenderAuthorDtlList[{{idx}}].materialNo" style="width:90%;"
                                        type="text" value="{{row.materialNo}}" readonly="readonly"/>
							</td>
							<td>
                                <label id="taTenderAuthorDtlList{{idx}}_model" name="taTenderAuthorDtlList[{{idx}}].model" style="width:280px;text-align:left;" >{{row.model}}</label>
							</td>
						</tr>
					</script>
					<script type="text/javascript">
						var taTenderAuthorDtlRowIdx = 0, taTenderAuthorDtlTpl = $("#taTenderAuthorDtlTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(taTenderAuthor.taTenderAuthorDtlList)};
							for (var i=0; i<data.length; i++){
								addRow('#taTenderAuthorDtlList', taTenderAuthorDtlRowIdx, taTenderAuthorDtlTpl, data[i]);
								taTenderAuthorDtlRowIdx = taTenderAuthorDtlRowIdx + 1;
							}
						});
					</script>
			</c:if>
		</li>
		<li>
			<label><span class="help-inline"><font color="red">*</font> </span>授权提供方式：</label>
			<c:if test="${viewsts.canEdit}">
			    <form:select path="authorOfferType" style="width:150px;" class="required">
					<form:options items="${fns:getDictList('DM0016')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		        <form:input path="offerRemarks" htmlEscape="false" maxlength="300" style="width:760px;"/>
			</c:if>
			<c:if test="${!viewsts.canEdit}">
			    <form:hidden path="authorOfferType" htmlEscape="false" />
		        <input type="text" value="${fns:getDictLabel(taTenderAuthor.authorOfferType, 'DM0016', '')}" style="width:150px;" readonly="readonly"/>
		        <form:input path="offerRemarks" htmlEscape="false" maxlength="300" style="width:760px;" readonly="true"/>
			</c:if>
		</li>
		<li>
			<label><span class="help-inline"><font color="red">*</font> </span>情况说明：</label>
			<c:if test="${viewsts.canEdit}">
			<form:textarea path="newRemarks" style="width:910px;resize:none;" class="required"
				    maxlength="300" rows="5"/>
			</c:if>
			<c:if test="${!viewsts.canEdit}">
			<form:textarea path="newRemarks" style="width:910px;resize:none;" class="required"
				    maxlength="300" rows="5" readonly="true"/>
			</c:if>
		</li>
		<li style="width:100%;">
		</li>
		</ul>
		</div>
		<c:if test="${viewsts.canEdit}">
            <div class="group-box group-box-last">
                <div class="group-header" >
                    <strong class="group-title">附件上传</strong><span class="help-inline"><font color="red">必须上传招标承诺书（最大上传文件大小：10M）</font></span>
                </div>
                <div class="upload-select-btn">
                    <button type="button" class="btn btn-primary"><span>选取文件</span></button>
                    <input type="file" multiple="multiple" class="upload-input">
                </div>
                <table id="attachmentTbl" class="table table-striped table-bordered table-condensed" style="width:50%;">
                    <thead>
                        <tr>
                            <th class="text-center" width="20px"><a href="javascript:;" class="disabled"><i class="icon-plus-sign"></i></a></th>
                            <th class="text-center" width="35px">No</th>
                            <th>文件名</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${taTenderAuthor.attachmentsList}" var="item" varStatus="status">
                        <tr class="uploaded">
                            <td class="text-center"><a href="javascript:;" class="delFileRow" data-file-id="${item.id}"><i class="icon-minus-sign"></i></a></td>
                            <td class="text-center rowNo">${status.index + 1}</td>
                            <td>
                                <a class="link" href="${ctx}/sd/taTenderAuthor/download/file/${item.id}">${item.fileName}.${item.fileType}</a>
                                <span class="pull-right"><i class="icon-ok icon-white"></i></span>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty taTenderAuthor.attachmentsList}">
                        <tr class="empty">
                            <td class="text-center" colspan="3">请选择附件</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${!viewsts.canEdit}">
            <div class="group-box group-box-last">
                <div class="group-header" >
                    <strong class="group-title">附件</strong>
                </div>
                <div class="upload-select-btn">
                    <input type="file" multiple="multiple" class="upload-input" disabled >
                </div>
                <table id="attachmentTbl" class="table table-striped table-bordered table-condensed" style="width:50%;">
                    <thead>
                        <tr>
                            <th class="text-center" width="20px"><a href="javascript:;" class="disabled"><i class="icon-plus-sign"></i></a></th>
                            <th class="text-center" width="35px">No</th>
                            <th>文件名</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${taTenderAuthor.attachmentsList}" var="item" varStatus="status">
                        <tr class="uploaded">
                            <td class="text-center"><a href="javascript:;" class="delFileRow disabled" data-file-id="${item.id}"><i class="icon-minus-sign"></i></a></td>
                            <td class="text-center rowNo">${status.index + 1}</td>
                            <td>
                                <a class="link" href="${ctx}/sd/taTenderAuthor/download/file/${item.id}">${item.fileName}.${item.fileType}</a>
                                <span class="pull-right"><i class="icon-ok icon-white"></i></span>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty taTenderAuthor.attachmentsList}">
                        <tr class="empty">
                            <td class="text-center" colspan="3">无附件</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </c:if>
		<div style="margin-top:8px;">
		    <form:hidden path="opt"/>
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="提交申请"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
			<c:if test="${!viewsts.canApproval}">
				<div class="text-center" style="margin-top: 8px;">
					<c:if test="${viewsts.canApply}">
						<input type="button" class="btn btn-primary opt1" value="提交申请">
						<!-- <input name="opt1" type="submit" class="btn btn-primary" value="提交申请" onclick="this.form.opt.value='提交申请'"> -->
					</c:if>
					<c:if test="${viewsts.canReapply}">
						<input type="button" class="btn btn-primary opt1" value="再申请">
						<!-- <input name="opt1" type="submit" class="btn btn-primary" value="再申请" onclick="this.form.opt.value='再申请'"> -->
					</c:if>
					<c:if test="${viewsts.canBack}">
						<input type="button" class="btn btn-primary opt1" value="撤回申请">
						<!-- <input name="opt1" type="submit" class="btn btn-primary" value="撤回申请" onclick="this.form.opt.value='撤回申请'"> -->
					</c:if>
					<c:if test="${viewsts.canDelete}">
						<input type="button" class="btn btn-primary opt1" value="删除">
						<!-- <input name="opt1" type="submit" class="btn btn-primary" value="删除" onclick="this.form.opt.value='删除'"> -->
					</c:if>
					<c:if test="${isManagerEdit}">
						<input type="button" class="btn btn-primary opt1" value="保存">
					    <%-- <input type="submit" class="btn btn-primary" value="保存" onclick="javascript:this.form.action='${ctx}/sd/taTenderAuthor/manager/save';"> --%>
					</c:if>
					<c:if
						test="${empty taTenderAuthor.act.procInsId && !empty taTenderAuthor.id}">
						<input class="btn" type="button" value="返回"
							onclick="history.go(-1)" />
					</c:if>
				</div>
			</c:if>
			<c:if test="${viewsts.canApproval}">
				<div class="group-box">
					<div class="group-header">
						<strong class="group-title">审批</strong>
					</div>

					<ul class="ul-form">
						<li class="full-width"><label>审批意见：</label> <form:textarea
								path="act.comment" class="fill-right" rows="3" maxlength="300"></form:textarea>
						</li>
					</ul>

					<div class="text-center">
						<input type="button" class="btn btn-primary opt1" value="同意">
						<input type="button" class="btn btn-default opt1" value="退回">
						<!-- <input name="opt1" type="submit" class="btn btn-primary" value="同意" onclick="this.form.opt.value='同意'">
						<input name="opt1" type="submit" class="btn btn-default" value="退回" onclick="this.form.opt.value='退回'"> -->
					</div>
				</div>
			</c:if>
		</div>
	</form:form>
	<sys:message content="${message}"/>
    <c:if test="${!empty taTenderAuthor.act.procInsId}">
        <act:histoicFlow procInsId="${taTenderAuthor.act.procInsId}"/>
    </c:if>
</body>
</html>