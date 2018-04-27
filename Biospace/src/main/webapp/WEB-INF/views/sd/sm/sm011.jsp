<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出入库管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		 .upload-select-btn {
        	margin-bottom: 5px;
        	margin-left:45px;
    	}
	    .upload-input {
	        display: none !important;
	    }
	    #attachmentTbl{
	    	margin-left:100px;
	    }
     div.ellipsis {
         text-overflow: ellipsis; 
         -moz-text-overflow: ellipsis; /* for Firefox,mozilla */   
         overflow: hidden;
         white-space: nowrap;
     }
	</style>
	
	<script type="text/javascript">
		$(function(){
			cloneTableHeader_Width();
			var fileModalValidate;
			
			var snModalValidate;
			// 添加画面验证
			fileModalValidate = $("#inputForm").validate({
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
			// 添加画面验证
			snModalValidate = $("#modalForm").validate({
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
			$("#btnExport").click(function(){
				$("#searchForm").attr("action","${ctx}/sd/sm/sm011/export");
				$("#searchForm").submit();
				$("#searchForm").attr("action","${ctx}/sd/sm/sm011/list");
			});
			
			 //修改SN
			 $("#modalSave").on("click",function(){
				/* if ($("#actualSN").val() == null || $("#actualSN").val() == "") {
				    showMessage("请填写实际SN");
					return false;
				}
				 
				if ($("#actualProductionDate").val() == null || $("#actualProductionDate").val() == "") {
				    showMessage("请填写实际生产日期");
					return false;
				} */
				if (!$("#modalForm").valid()) {
					return false;
				}

				var snNo = $("#SnNo").val();
				var actualSN = $("#actualSN").val();
				var actualProductionDate = $("#actualProductionDate").val();
				top.$.jBox.confirm("本操作会影响此SN所有记录，请确认修改无误!","提示",function(v,h,f) {
                    if(v=="ok"){
                    	$.ajax({
	   				    	 url:"${ctx}" + "/sd/sm/sm011/editSn",
	   				    	 data:{"snNo":snNo,"actualSN":actualSN,"updateDate":$("#SNupdateDate").val(),"actualProductionDate":actualProductionDate},
	   	                        type: "post",
	   	                        dataType: "json"
	   	                    }).done(function(data) {
	   	                   	 var message = data.message + "";
	   	                        if(message.indexOf("保存失败：") != -1){
	   	                       	 showMessage(data.message);
	   	                        }else{
	   	                       	 	showMessage(data.message);
	   	                       	 	$("#soOrderDtlModal").hide();
	   		                        refrechpage(); 
	   	                        }
	   	                    });
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
			 });
			 
			 $(document).on("change", "input.materialNo", function(e) {
	                var $noInput = $(this);
	                var $nameInput = $noInput.parent().parent().find("input.materialName");
	                if (e.val == "") {
	                    $nameInput.val("");
	                } else {
	                    $nameInput.val(e.added.text);
	                }
	           });
			
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
	                     $("#fileName").val("");
	                     $("#fileName").val(files[i].name);
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
	         $(document).on("click", "#attachmentTbl .delFileRow", function() {
	             var _this = $(this);
	             var $tbody = _this.parents("tbody");
	             var $delRow = _this.parents("tr");
	             // 删除行是否已上传
	             if ($delRow.hasClass("uploaded")) {
	                 // 已上传时
	                 confirmx("确认要删除该文件吗？", function () {
	                     $.ajax("${ctx}/sd/sm/sm011/delete/file/" + _this.data("file-id"), {
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

	         //录入时间提交
	         $("#save").click(function () {
	        	 	
	        	 	/* if($("#actualInstallDateStr").val() == "" || $("#actualInstallDateStr").val() == null){
	        	 		showMessage("请选择上传时间");
	        	 		return false;
	        	 	}
	        	 	if($("#installPerson").val() == "" || $("#installPerson").val() == null){
	        	 		showMessage("请填写安装人");
	        	 		return false;
	        	 	} */

					if (!$("#inputForm").valid()) {
						return false;
					}
	        	 	
	        	 	if ($("#attachmentTbl>tbody>tr:not(.empty)").length == 0) {

	    				alertx("请上传附件信息。","");
	    				return false;
	        	 	}
	                // 取得form data对象
	                var formData = new FormData($("#inputForm")[0]);
	                // 获取文件
	                var selFiles = $(".upload-select-btn input[type='file']").data("files");
	                // 如查文件存在
	                if (selFiles) {
	                    // 将文件加入form data对象
	                    for (var i = 0; i < selFiles.length; i++) {
	                        formData.append("files", selFiles[i]);
	                    }
	                }else{
	                	/* var $tr = $("#attachmentTbl tbody").find("tr");
	                	if(!$tr.hasClass("uploaded")){
	                		showMessage("请上传附件信息");
		                	 return false;
	                	} */
	                }
	                // 提交画面数据
	                $.ajax($("#inputForm").attr("action"), {
	                    type: "post",
	                    data: formData,
	                    contentType: false,
	                    processData: false,
	                }).done(function (data) {
	                	//showMessage("修改成功");
	                	showMessage(data.message);
	                	$("#soOrderDtlModalY").hide();
	                	refrechpage();
	                });
	                
	            });
				
				$('#soOrderDtlModalY').on('hidden.bs.modal', function () {

					fileModalValidate.resetForm();
				});
				
				$('#soOrderDtlModal').on('hidden.bs.modal', function () {

					snModalValidate.resetForm();
				});
			 
		});
		function cloneTableHeader_Width(){  
			if ($("#contentTable>tbody>tr:not(.empty)").length > 0) {

		        var myTable_Width = (document.body.clientWidth);  

		        var height = $("#contentTable").height() + 23;

		        FixTable("contentTable", 2, myTable_Width, height);
			}
	     }
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		 function modalOpen(materialNo,sn,Name,productionDate){
				 $("#soOrderDtlModal").modal('show').css({
	                width: '780px',
	                'margin-left': '-512px'
	            });
				$("#Mno").val(materialNo);
				$("#Mname").val(Name);
				$("#SnNo").val(sn);
				$("#productionDate").val(productionDate);
				$.ajax({
					url:"${ctx}" + "/sd/sm/sm011/getUpdteDate",
					 type: "get",
				        async: false,
				        data: {"snNo":sn},
				        dataType: "json",
				        success: function (result) {
				        	 $("#SNupdateDate").val(result.date);
				        },
				        error: function (msg) {
				        }
				});
			}
		
		 function modalOpenY(id,snno){
			 $("#soOrderDtlModalY").modal("show").css({
				 width: '780px',
	             'margin-left': '-512px'
			 });
			 var  $tbody = $("#attachmentTbl tbody");
			 $("#id").val(id);
			 $("#no").val(snno);
			 $.ajax({
				 url:"${ctx}" + "/sd/sm/sm011/getAttachments",
				 type: "get",
		         async: false,
		         data: {"assid":id,"snNo":snno},
		         dataType: "json",
		         success: function (result) {
		        	 $tbody.html("");
		        	  $("#installPerson").val(result.installperson);
		        	  $("#actualInstallDateStr").val(result.date);
		        	  var a = result.list;
		        	  var html = "";
       				  if(a == null){
       						   html = "<tr class='empty'>" + 
		  								   "<td class='text-center' colspan='3'>请选择附件</td>"+
		  								   "</tr>";
       						$tbody.append(html);
       				  }
		        	  $.each(a, function (i, item) {  
		                       html = "<tr class='uploaded'>"+
				                       		   "<td class='text-center'><a href='javascript:;' class='delFileRow' data-file-id='"+item.id+"'><i class='icon-minus-sign'></i></a></td>"+
				                        	   "<td class='text-center rowNo'>"+(i+1)+"</td>"+
				                        	   "<td>"+
				                               "<a href='${ctx}/sd/sm/sm011/download/file/" + item.id + "'>"+item.fileName+"."+item.fileType+"</a>"+
				                               "<span class='pull-right'><i class='icon-ok icon-white'></i></span>"+
				                        		"</td>"+
                    		   			 "</tr>";
		                       $tbody.append(html);
		              });
		         },
		         error: function (msg) {
		         }
			 });
		 }
		
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
      
        function refrechpage(){
    		$("#searchForm").attr("action","${ctx}/sd/sm/sm011/list");
    		$("#searchForm").submit();
        }
	</script>
	
	
</head>
<body>
	<h3 class="text-center page-title">出入库记录</h3>
	<form:form id="searchForm" modelAttribute="smStorageInfoManagement" action="${ctx}/sd/sm/sm011/list"  method="get" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
	            	<label>物料名称：</label>
	            	<form:input path="materielName" class="input-medium" type="text" value="" maxlength="50" />
	        </li>
	        
			<li><label>物料号：</label>
				 <form:input path="materialNo" type="text"  maxlength="50" class="remote material required materialNo"  style="width: 170px;"/>
			</li>
			<li>
			<li><label>S/N：</label>
				<form:input path="snNo" class="input-medium" type="text" value="" maxlength="50" />
			</li>
			<li><label>出入库分类：</label>
				<form:select  path="storageType" class="input-medium">
					<form:option value="" label=""/>
				    <form:options items="${fns:getDictList('DM0035')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>物料类型：</label>
				<form:select  path="materialType" class="input-medium">
					<form:option value="" label=""/>
				    <form:options items="${fns:getDictList('DM0058')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li style="margin-left:-9px;"><label style="width: 110px;">合同/报价单编号：</label>
				<form:input path="orderNo" class="input-medium" type="text" value="" maxlength="50" />
			</li>
			<li><label style="width: 106px;">采购订单编号：</label>
				<form:input path="purchaseNo" class="input-medium" type="text" value="" maxlength="50" />
			</li>
			<li>
				<label style="margin-left:11px;">记录日期起：</label>
				<form:input path="processDateFrom" type="text" maxlength="10" class="input-medium Wdate "
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
	        </li>
	        <li>
				<label class="text-center">记录日期止：</label>
				<form:input path="processDateTo" type="text" maxlength="10" class="input-medium Wdate "
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
	        </li>
	        <li style="margin-left:50px;">
					<label style="width:140px;">
					<form:checkbox path="ExceedLatestDate" value="0" />已超过最晚安装日期</label>
			</li>
			
			<li class="btns" style="margin-left:400px;">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
		    	<input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/sd/sm/sm011/list';">
				<input id="btnExport" class="btn btn-primary" type="button" value="一览导出"/>
			</li>
		</ul>
		</form:form>
	 <c:if test="${empty page.list}">
	 <div class="auto-scroll-x">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
	 </c:if>
	 <c:if test="${!empty page.list}">
		<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:2100px;">
	 </c:if>
	<%-- <c:if test="${empty page.list}">
	<div style="overflow-x:scroll;">
	</c:if>
	<!-- <table id="contentTable" style="width:100%;margin-top:5px;" class="table table-striped table-bordered table-condensed"> -->
	<table id="contentTable" class="table table-striped table-bordered table-condensed"> --%>
		<thead>
			<tr>
				<th width="50px">合同/报价单编号</th>
				<th width="100px">采购订单编号</th>
				<th width="100px">区分</th>
				<th width="150px">物料号</th>
				<th width="150px">物料名称</th>
				<th width="150px">物料类型</th>
				<th width="150px">S/N</th>
				<th width="150px">生产日期</th>
				<th width="50px">数量</th>
				<th width="100px">记录日期</th>
				<th width="150px">记录人</th>
				<th width="150px">库房</th>
				<th width="150px">出入库分类</th>
				<th width="100px">联系人</th>
				<th width="100px">联系方式</th>
				<th width="100px">收货地址</th>
				<th width="150px">借用目的</th>
				<th width="100px">借用信息</th>
				<th width="50px">借出日期</th>
				<th width="100px">借出到期日</th>
				<th width="100px">是否需要安装</th>
				<th width="150px">最晚安装日期</th>
				<th width="150px">安装日期</th>
				<th width="150px">安装人</th>
				<th width="50px">出入库编号</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${!empty page.list}"> 
				<c:forEach items="${page.list}" var="rowModel">
					<tr>
						<td>${rowModel.orderNo }</td>
						<td>${rowModel.purchaseNo }</td>
						<td>
								${rowModel.distinguish}
						</td>
						<td>${rowModel.materialNo }</td>
						<td>${rowModel.materielName}</td>
						<td>
						${fns:getDictLabel(rowModel.materialType, 'DM0058', '')}</td>
						<td>
							<c:if test="${rowModel.ifvirtualsn == '1'}"> 
								<shiro:hasPermission name="sm:sm011:edit">
									<a href="javascript:;" style="color:blue;border-bottom: 1px solid blue;" onclick="modalOpen('${rowModel.materialNo }','${rowModel.snNo}','${rowModel.materielName}','${rowModel.productionDateStr}');">${rowModel.snNo}</a>
								</shiro:hasPermission>
								<shiro:lacksPermission name="sm:sm011:edit">
									${rowModel.snNo}
								</shiro:lacksPermission>
							</c:if>
							<c:if test="${rowModel.ifvirtualsn != '1'}"> 
								${rowModel.snNo}
							</c:if>
						</td>
						<td>${rowModel.productionDateStr}</td>
						<td class="text-right">${rowModel.num }</td>
						<td>${rowModel.processDate}</td>
						<td>${rowModel.noteTaker}</td>
						<td>${fns:getDictLabel(rowModel.warehouse, 'DM0050', '')}</td>
						<td>${fns:getDictLabel(rowModel.storageType, 'DM0035', '')}</td>
						<td title="${rowModel.contactsName }"><div style="max-width:100px" class="ellipsis">${rowModel.contactsName }</div></td>
						<td title="${rowModel.telephone }"><div style="max-width:100px" class="ellipsis">${rowModel.telephone }</div></td>
						<td title="${rowModel.address }"><div style="max-width:100px" class="ellipsis">${rowModel.address }</div></td>
						<c:if test="${rowModel.storageType == '22'}">
							<td>${fns:getDictLabel(rowModel.lendingType, 'DM0036', '')}</td>
							<td title="${rowModel.industryForExcel }"><div style="max-width:100px" class="ellipsis">${rowModel.industryForExcel}</div></td>
							<td>${rowModel.lendingDateFrom }</td>
							<td>${rowModel.lendingDateTo }</td>
						</c:if>
						<c:if test="${rowModel.storageType != '22'}">
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</c:if>
						<td style="text-align:center">
								<c:if test="${rowModel.ifInstall =='1'}">
									<shiro:hasPermission name="sm:sm011:edit">
										<a style="color:blue;border-bottom: 1px solid blue;" href="javascript:;" onclick="modalOpenY('${rowModel.id}','${rowModel.snNo}')">是</a>
									</shiro:hasPermission>
									<shiro:lacksPermission name="sm:sm011:edit">是</shiro:lacksPermission>
								</c:if>
								<c:if test="${rowModel.ifInstall == '0'}">否</c:if>
						</td>
						<td>${rowModel.latestInstallDateStr}</td>
						<td>${rowModel.actualInstallDateStr}</td>
						<td>${rowModel.installPersonId}</td>
						<td>${rowModel.storageId }</td>
					</tr>
				</c:forEach>
				</c:if>
		</tbody>
	</table>
	<c:if test="${empty page.list}">
	</div>
	</c:if>
	
	<div class="pagination">${page}</div>
	<sys:message content="${message}"/>
	
	<!-- 修改SN模态框 -->
	<div class="modal fade" id="soOrderDtlModal" style="display:none;" data-backdrop="static" >
				<div class="modal-dialog">
					<div class="modal-content">
					<form:form id="modalForm" class="form-search">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" >&times;</button>
							<h4 class="text-center modal-title">修改SN</h4>
						</div>
						<div class="modal-body">
							<div class="group-box group-box-first">
							    <ul class="ul-form">
							        <li>
							            <label>物料号：</label>
							            <input name="materialNo"  id="Mno" class="input-medium" type="text" readonly="readonly" value="" maxlength="50" data-show="text"/>
							       		<input name="updateDate" id="SNupdateDate" type="hidden"/>
							        </li>
							        <li>
							            <label>物料名称：</label>
							            <input id="Mname" name="materialName" type="text" maxlength="50" readonly="readonly" value=""  class="input-medium" data-show="text"/>
							        </li>
							        <li>
							            <label>虚拟S/N：</label>
							            <input id="SnNo" name="snNo" type="text" maxlength="50" readonly="readonly" data-show="text" value="" class="input-medium"/>
							        </li>
							        <li>
							            <label>虚拟生产日期：</label>
							            <input id="productionDate" name="productionDate" type="text" readonly="readonly" class="input-small"/>
							        </li>
							        <li>
							            <label><span class="help-inline"><font color="red">*</font></span>实际S/N：</label>
							            <input id="actualSN" name="sn" type="text" maxlength="50" class="input-medium required"/>
							        </li>
							        <li>
							            <label><span class="help-inline"><font color="red">*</font></span>实际日期：</label>
							            <input id="actualProductionDate" name="actualProductionDate" type="text" readonly="readonly" class="input-small Wdate required" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
							        </li>
									<li class="clearfix">
									</li>
									<li></li>
							     </ul>
							</div>
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
			<!-- 录入安装日期模态框 -->
			<form:form id="inputForm"   modelAttribute="storage" method="post" action="${ctx}/sd/sm/sm011/save" class="form-search" enctype="multipart/form-data">
			<div class="modal fade" id="soOrderDtlModalY" style="display:none;" data-backdrop="static" >
				<div class="modal-dialog">
					<div class="modal-content">
					<form:form id="resultModal" class="form-search">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" >&times;</button>
							<h4 class="text-center modal-title">录入安装日期</h4>
						</div>
						<div class="modal-body">
							<div class="group-box group-box-first">
							    <ul class="ul-form">
							        <li>
							            <label><span class="help-inline"><font color="red">*</font></span>安装日期：</label>
							            <input name="actualInstallDateStr" id="actualInstallDateStr" class="input-medium Wdate required" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
							        	<input name="id" type="hidden" value="" id="id">
							        	<input name="snNo" type="hidden" value="" id="no">
							        </li>
							        <li>
							            <label><span class="help-inline"><font color="red">*</font></span>安装人：</label>
							            <input  name="installPerson" id="installPerson" type="text" maxlength="50" value="" class="input-medium required" data-show="text"/>
							        </li>
							        <li>
										<div class="upload-select-btn">
											<!-- <span class="help-inline"><font color="red">*</font></span> -->
											<span>文件上传：</span>
											<input type="text" id="fileName" style="width:200px;" value=""  readonly>
							                <button type="button" class="btn btn-primary"><span>浏览</span></button><span class="help-inline"><font color="red">最大上传文件大小：10M</font></span>
							                <input type="file" multiple="multiple" class="upload-input">
							            </div>
							        </li>
							     </ul>
					            <table id="attachmentTbl" class="table table-striped table-bordered table-condensed" style="width:50%;">
					                <thead>
					                    <tr>
					                        <td class="text-center" width="20px"><a href="javascript:;" class="disabled"><i class="icon-plus-sign"></i></a></td>
					                        <td class="text-center" width="35px">No</td>
					                        <td>文件名</td>
					                    </tr>
					                </thead>
					                <tbody>
							                
					                </tbody>
					            </table>
							</div>
						</div>
						<div class="modal-footer">
							<div class="text-center">
						        <input id="save" class="btn btn-primary" type="button" value="保存">
							</div>
						</div>
						</form:form>
					</div>
				</div>
			</div>
			</form:form>
</body>
</html>