<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>价格申请</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			priceSystemChange();
			if($("#protocolsCopy").val() != ""){
				$("#protocols").select2('val',$("#protocolsCopy").val());
			}
			onProtocolChange();
			if($("#startdaysCopy").val() != ""){
				$("#startdays").select2('val',$("#startdaysCopy").val());
			}
			
			
			$("#inputForm").validate({
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
			
			$("#btnUpload").click(function(){
				$('#priceSystemCopy').val($('#selectPriceSystem').val());
				$('#protocolsCopy').val($('#protocols').val());
				$('#startdaysCopy').val($('#startdays').val());
				$.jBox($("#uploadBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			$("#btnDownload").click(function(){
				$('#selectPriceSystem').attr("required",true);
				$("#inputForm").attr("action","${ctx}/ps/psApplyPrice/export");
				$("#inputForm").attr("method","GET"); 
				$("#inputForm").submit();
			});
		});
		function addRow(list, idx, tpl, row, user){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row, user : user
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
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
			}
		}
		
		function priceSystemChange(){
			var psSelect = document.getElementById("selectPriceSystem");
			if(psSelect != null && psSelect != ""){
				var ps = psSelect.options[psSelect.selectedIndex].value;
				if(ps == "1" || ps == "3" || ps == "5"){
					document.getElementById("haveProtocol").checked = true;
					$('#protocols').attr("required",true);
					$('#startdays').attr("required",true);
				} else{
					document.getElementById("haveProtocol").checked = false;
					$('#protocols').removeAttr("required");
					$('#startdays').attr("required",true);
				}
				haveProtocolChange(ps);
			}
		}
		function haveProtocolChange(ps){
			var havept = document.getElementById("haveProtocol");
			if(havept.checked == true){
				document.getElementById("protocols").disabled = "";
				document.getElementById("startdays").disabled = "";
				getSelectListByAjax("${ctx}/ps/psApplyPrice/getProtocols","protocols",{"priceSystem":ps});
				$("#protocols").trigger("change");
			}
			else{
				$("#select2-chosen-2").html("");
				$("#select2-chosen-3").html("");
				document.getElementById("protocols").disabled = "disabled";
				document.getElementById("startdays").disabled = "disabled";
			}
		}
		function onProtocolChange(){
			var url = "${ctx}/ps/psApplyPrice/getStartDays";
			var inputdata = {"parentId":$("#protocols").val()};
			getSelectListByAjax(url,"startdays",inputdata);
		}
		
		function approve(v){
	    	if(v == 20){
	    		$('#flag').val(1);
	    	}else{
	    		var r=confirm("确认要退回给申请者？请确认！");
	    		if (r != true) {
	    			return false;
	    		}
	    		$('#flag').val(0);
	    	}
	    	$('#workflowStatus').val(v);
	    	
	    	$("#inputForm").attr("action","${ctx}/ps/psApplyPrice/audit");
			$("#inputForm").attr("method","POST"); 
			$("#inputForm").submit();
	    }
		function doSave(v){
			$('#workflowStatus').val(v);
			if(v == 40){
				$('#flag').val(0);
				$("#inputForm").attr("action","${ctx}/ps/psApplyPrice/applyback");
				$("#inputForm").attr("method","POST"); 
				$("#inputForm").submit();
			}
			else{
				$('#flag').val(1);
			}
			return;
		}
		     
	</script>
</head>
<body>
	<h3 class="text-center page-title">价格申请</h3>
	<div id="uploadBox" class="hide">
		<form:form id="uploadForm" modelAttribute="psApplyImport" action="${ctx}/ps/psApplyPrice/upload" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px" accept="xls,xlsx"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   上    传    "/>
			<form:hidden path="id"/>
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden path="priceSystemCopy"/>
			<form:hidden path="protocolsCopy"/>
			<form:hidden path="startdaysCopy"/>
		</form:form>
	</div>
	<%-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/ps/psApplyPrice/">价格查询</a></li>
		<li class="active"><a href="${ctx}/ps/psApplyPrice/form?id=${psApplyPrice.id}">价格申请</a></li>
	</ul><br/>  --%>
	<form:form id="inputForm" modelAttribute="psApplyPrice" action="${ctx}/ps/psApplyPrice/save" method="get" class="breadcrumb form-search">
		<form:hidden path="id"/>
		<form:hidden path="workflowStatus"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<form:hidden path="priceSystem"/>
		<c:if test="${viewsts.canApply||viewsts.canReapply}">
		<ul class="ul-form" style="width:1010px">
			<li>
				<label><span class="help-inline"><font color="red">*</font></span>价格体系： </label>
				<form:select path="selectPriceSystem" onchange="priceSystemChange()" style="width:140px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DM0020')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<label><input type="checkbox" id="haveProtocol" name="haveProtocol" disabled="disabled">有协议</label>
			</li>
			<li>
				<label>协议方：</label>
				<select id="protocols" name="protocols" class="input-xlarge" disabled="disabled" onchange="onProtocolChange()">
				</select>
			</li>
			<li>
				<label>协议开始日：</label>
				<select id="startdays" name="startdays" class="input-xlarge" disabled="disabled" style="width:140px;">
				</select>
			</li>
			<li class="btns">
				<input id="btnDownload" class="btn btn-primary" type="button" value="文件下载"/>
				<input id="btnUpload" class="btn btn-primary" type="button" value="文件上传"/>
			</li>
		</ul>
		<br/>
		<%-- <div class="control-group">
			<label class="control-label">价格体系  </label>
			<div class="controls">
				<form:select path="priceSystem" class="input-xlarge required" onchange="priceSystemChange()" style="width:140px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('DM0020')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
				<input type="checkbox" id="haveProtocol" name="haveProtocol" disabled="disabled"><label>有协议</label>
				<label>协议方</label><select id="protocols" name="protocols" class="input-xlarge" disabled="disabled" onchange="onProtocolChange()"></select>
				<label>协议开始日</label><select id="startdays" name="startdays" class="input-xlarge" disabled="disabled" style="width:140px;"></select>
				<input id="btnDownload" class="btn btn-primary" type="button" value="文件下载"/>
				<input id="btnUpload" class="btn btn-primary" type="button" value="文件上传"/>
			</div>
		</div> --%>
		</c:if>
			<div class="control-group">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th class="hide"></th>
							<th>物料号</th>
							<th>物料名称</th>
							<th>价格体系</th>
							<th>协议方 </th>
							<th>地区 </th>
							<th>行业 </th>
							<th>单价</th>
							<th>申请人</th>
							<th>有效开始日</th>
							<th>有效终止日</th>
							<%-- 
							<shiro:hasPermission name="ps:psApplyPrice:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							--%>
						</tr>
					</thead>
					<tbody id="psApplyPriceDtlList">
					</tbody>
				</table>
				<script type="text/template" id="psApplyPriceDtlTpl">
						<tr id="psApplyPriceDtlList{{idx}}">
							<td class="hide">
								
							</td>
							<td>
								{{row.materialNo}}
							</td>
							<td>
								{{row.materialName}}
							</td>
							<td>
								{{row.priceSystemName}}
							</td>
							<td>
								{{row.cutomerName}}
							</td>
							<td>
								{{row.regionName}}
							</td>
							<td>
								{{row.industryName}}
							</td>
							<td>
								{{row.unitPrice}}
							</td>
							<td>
								{{user.name}}
							</td>
							<td>
								{{row.startDays}}
							</td>
							<td>
								{{row.endDays}}
							</td>
							<%--
							<shiro:hasPermission name="ps:psApplyPrice:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#psApplyPriceDtlList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
							--%>
						</tr>
					</script>
				<script type="text/javascript">
					var psApplyPriceDtlRowIdx = 0, psApplyPriceDtlTpl = $("#psApplyPriceDtlTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					$(document).ready(function() {
						var data = ${fns:toJson(psApplyPrice.psApplyPriceDtlList)};
						var userName = ${fns:toJson(psApplyPrice.createBy)};
						//alert(JSON.stringify(userName));
						for (var i=0; i<data.length; i++){
							addRow('#psApplyPriceDtlList', psApplyPriceDtlRowIdx, psApplyPriceDtlTpl, data[i], userName);
							psApplyPriceDtlRowIdx = psApplyPriceDtlRowIdx + 1;
						}
					});
				</script>
			</div>
		
		<c:if test="${!viewsts.canApproval}">
        <div class="text-center" style="margin-top:8px;">
            <c:if test="${viewsts.canApply}">
                <input name="opt" id="btnSubmit" type="submit" onclick="doSave(10)" class="btn btn-primary" value="提交申请">
            </c:if>
            <c:if test="${viewsts.canReapply}">
                <input name="opt" id="btnSubmit" type="submit" class="btn btn-primary"  onclick="doSave(10)" value="再申请">
            </c:if>
            <c:if test="${viewsts.canBack}">
                <input name="opt" id="btnSubmit" type="submit" class="btn btn-primary"  onclick="doSave(40)" value="撤回申请">
            </c:if>
            <c:if test="${viewsts.canDelete}">
                <input name="opt" type="submit" class="btn btn-primary" onclick="doSave(70)" value="删除">
            </c:if>
            <c:if test="${!empty vrVisit.procInsId}">
                <input class="btn"  type="button" value="返回" onclick="history.go(-1)"/>
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
                        <div style="overflow: hidden;">
                            <form:textarea path="act.comment" rows="3" maxlength="300" style="width:100%;" ></form:textarea>
                        </div>
                    </li>
                </ul>

                <div class="text-center">
                    <input name="opt" id="btnPass" type="submit" class="btn btn-primary"  onclick="approve(20)" value="同意">
                    <input name="opt" id="btnReturn" type="button" class="btn btn-default"  onclick="approve(30)" value="退回">
                </div>
            </div>
        </c:if>
		<c:if test="${!empty psApplyPrice.act.procInsId}">
	        <act:histoicFlow procInsId="${psApplyPrice.act.procInsId}"/>
	    </c:if>
	    <br/>
	    <sys:message content="${message}"/>	
	</form:form>
</body>
</html>