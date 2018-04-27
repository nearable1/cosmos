<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>移库</title>
<meta name="decorator" content="default" />
</head>
<body>
	<h3 class="text-center page-title">移库</h3>
	<!-- <div
		style="padding: 5px 5px; border: 1px solid #ddd; width: 98%; margin-bottom: 5px;"> -->
		<!-- <h3 align="center">移库</h3> -->
		<!-- <div
			style="padding: 5px 5px; width: 100%; margin-bottom: 5px; margin: 0 auto;"> -->


<%-- 			<ul class="nav nav-tabs">
				<li><a href="${ctx}/sm/sm001">借出</a></li>
				<li><a href="${ctx}/sm/sm009"> 换货</a></li>
				<li class="active"><a href="${ctx}/sm/sm006"> 移库</a></li>
				<li><a href="${ctx}/sm/sm007">报废/丢失</a></li>
				<li><a href="${ctx}/sm/sm008/">其他</a></li>
				<li><a href="${ctx}/sm/sm010/">借出延长</a></li>
			</ul> --%>
			<div class="group-box group-box-first" style="height: auto;">
			<form:form id="searchForm" modelAttribute="outStorageManagement"
				action="${ctx}/sm/sm001/" method="post"
				class="form-search">
				<ul class="ul-form">
					<li><label>负责人：</label> <input id="responsiblePerson"
						name="responsiblePerson" class="input-medium" type="text"
						readonly="readonly" value="${user.createBy.name}" maxlength="50"></li>
					<li><label style="display: inline-block; width: 100px;">操作日期：</label>
						<input id="lastVisitDate" name="lastVisitDate"
						class="input-small" type="text" value="${date}"
						readonly="readonly" maxlength="50"></li>
					<li class="full-width">
					    <label>备注说明：</label>
					     <textarea
							id="newRemarks" name="newRemarks" maxlength="300"
							class="fill-right" rows="3">${smStorageApp[0].NEW_REMARKS}</textarea>
					</li>
				</ul>
				<%-- <div id="select" style="display: none">
					<form:select path="toWarehouse" class="input-medium required">
						<form:options items="${fns:getDictList('DM0050')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div> --%>

			</form:form>
			</div>
		<!-- </div> -->

		<div class="group-box">
            <div class="group-header" >
                <strong class="group-title">数据查询</strong>
            </div>
			<!-- style="padding: 5px 5px; border: 1px solid #ddd; width: 98%; height: auto; margin-bottom: 5px; margin-left: 0px; margin-top: 30px"> -->
			<!-- <h4>数据查询</h4> -->
			<form:form id="materiaForm" action="" class="breadcrumb form-search">
				<!-- <div style="border: 0px solid #ddd; width: 100%; height: 50px;"> -->
<!-- 				<h4>数据查询</h4> -->
				<ul class="ul-form">
					<li><label>物料号：</label>
					<input id="qureyMaterialNo" type="text" value=""
						class="remote material input-medium" data-type="1,2,4,5,7"/></li>
					<li><label>S/N：</label> <input
						id="querySnNo" type="text" value="" class="input-medium"/></li>
					<li class="btns"><input id="snNoBtn" class="btn btn-primary" type="button" value="查找"></li><!-- <span
						id='text'></span> -->
				</ul>
				<!-- </div> -->
			</form:form>
			
                <table id="materiaTable"
                    class="table table-striped table-bordered table-condensed">
                    <thead>
                        <tr>
                            <th width="20px"></th>
                            <th>物料号</th>
                            <th>物料名称</th>
                            <th>库存数量</th>
                            <th>库房</th>
                            <th>S/N</th>
                            <th>生产日期</th>
                        </tr>
                    </thead>
                    <tbody Id="list">
                    <!-- <thead id="list">

                    </thead> -->
                    </tbody>

                </table>

			<div class="text-center">
				<input class="btn btn-primary" id="listBtn" type="button" value="选择" onclick="Storage()">
				<input class="btn btn-primary" id="MoveBtn" type="button" value="清空" onclick="move()"><!--  <label
					id='listBtnErro' for="listBtn"
					style="display: none; text-align: center; background-image: url();"
					class="error"></label> -->
			</div>
		</div>

		<div class="group-box group-box-last">
			<!-- style="padding: 5px 5px; border: 1px solid #ddd; width: 98%; overflow-y: scroll; height: 225px; margin-bottom: 5px; margin-left: 0x; margin-top: 30px"> -->
			<!-- <h4 style="display: inline-block;">库存数据操作</h4> -->
			<form:form id="contentForm" modelAttribute="outStorageDtl" action="" class="form-search">
                <div class="group-header" >
                    <strong class="group-title">库存数据操作</strong>
                </div>
				<table id="contentTable"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="20px"></th>
							<th>物料号</th>
							<th>物料名称</th>
							<th>S/N</th>
							<th>生产日期</th>
							<th>库存数量</th>
							<th>库房</th>
							<th width="12%">出库数量</th>
							<th width="12%">移动后库房</th>
						</tr>
					</thead>
					<tbody Id="addlist">
					</tbody>
				</table>
			</form:form>

		</div>
	<!-- </div> -->

	<div class="text-center">
		<!-- style="padding: 5px 5px; border: 0px solid #ddd; width: 98%; height: auto; margin-bottom: 5px; margin-left: 0px; margin-top: 30px"> -->

		<input id="btnSubmit" class="btn btn-primary" type="button" value="确认移库"><!--  <label
			id='SubmitErro' for="listBtn"
			style="display: none; text-align: center; background-image: url();"
			class="error"></label> -->
	</div>
	<script type="text/javascript">

	$(document).ready(function() {
		
		initPage();
		
		function initPage() {
			// 添加画面验证
			$("#contentForm").validate({
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
	});
		//var treeData;
		//var sticherList = "${list}";
		$("#btnSubmit").click(
				function() {
					//erro1();
					if (!$("#contentForm").valid()) {
						return false;
					}
					var searchForm = $("#searchForm").serialize();//数据序列化
					var contentForm = $("#contentForm").serialize();
					//var lendingType = $("#lendingType").val();
					var param = searchForm + "&" + contentForm;//"lendingDateTo="+lendingDateTo+"&contacts="+contacts+"&telephone="+telephone+"&address="+address+"&snNo="+snNo+"&accessoriesRemarks="+accessoriesRemarks+"&index="+index;
					/* var toWarehouse = document
							.getElementsByName("toWarehouse1");
					var warehouse = document.getElementsByName("warehouse");
					var num = document.getElementsByName("num"); */
					//str = "";

					/* if (num == "" || num == null || num.length == 0) {
						erro("请选择一条数据", "SubmitErro");
						return;
					}
					for (i = 0; i < num.length; i++) {
						//alert(warehouse[i].innerText);
						//alert(toWarehouse[i].text());
						var index = toWarehouse[i].selectedIndex;
						var text = toWarehouse[i].options[index].text;

						// 选中文本
						if (toWarehouse[i].value == ""
								|| toWarehouse[i].value == null) {
							erro("库房不能为空", "SubmitErro");
							return;
						} else if (warehouse[i].innerHTML == text) {
							erro("库房不能重复", "SubmitErro");
							return;
						}
						if (num[i].value == "" || num[i].value == null) {
							erro("数量不能为空", "SubmitErro");
							return;
						}
						var warehouseNum = document
								.getElementsByName("warehouseNum");
						var sum = parseInt(warehouseNum[i].value);
						if (num[i].value < 0 || sum < num[i].value
								|| num[i].value == "0") {
							erro("出库数量不能大于库存数量", "SubmitErro");
							return;
						}
					} */
					$.ajax({
						type : "post",
						url : "${ctx}/sm/sm006/Add",
						data : param,
						dataType : "json",
					    success: function(oData, oStatus) {
				           	if (oData.success) {
				           		showMessage("操作成功");
				           		window.location.reload();
				           	}
				        },
					    error: function(oData, oStatus, eErrorThrow) {
					    }
/* 						success : function(data) {
							document.getElementById("contentForm").submit();
							erro("录入成功", "SubmitErro");
							window.location.reload();
						},
						error : function(data) {
							erro("录入失败", "SubmitErro");

						} */
					});
				})

		//var agreementIndex = 0;
		var arr;
		$("#snNoBtn").click(function() {
							/* document.getElementById("text").innerHTML = '&nbsp&nbsp&nbsp&nbsp'; */
							var snNo = $("#querySnNo").val();
							var qureyMaterialNo = $("#qureyMaterialNo").val();
							if ((snNo == null || snNo == "") && (qureyMaterialNo == null || qureyMaterialNo == "")) {
								return;
							}
							/* if (str == null || str == "") {
								document.getElementById("text").innerHTML = '&nbsp&nbsp&nbspS/N不能为空';
								document.getElementById("text").style.color = 'red';
								return;
							} */
							$.ajax({
										type : "get",
										url : "${ctx}/sm/sm006/queryBySnNo?snNo=" + snNo + "&materialNo=" + qureyMaterialNo,
										success : function(data) {

											var str = "";
											arr = new Array();
											if (data == null || data.length == 0) {
												/* document.getElementById("text").innerHTML = '&nbsp&nbsp&nbsp&nbsp没找到S/N对应的机器';
												document.getElementById("text").style.color = 'red'; */
												$("#list").find("tr").each(function(){
													$(this).remove();
											    });
												$("#list").find("tr.empty").remove();
							        			str = '<tr class="empty"><td class="text-center" colspan="7">没有可操作数据</td></tr>';
							        			$("#list").append(str);
											} else {
												//var num = 0;
												/* for ( var i in arr) {
													if (arr[i].materialNo == data[0].materialNo) {
														document
																.getElementById("text").innerHTML = '&nbsp&nbsp&nbsp&nbspS/N已存在';
														document
																.getElementById("text").style.color = 'red';
														return;
													}
												} */
												for ( var i in data) {
													/* str += "<tr><td><input id=\"ckb\" name=\"ckb\" type=\"checkbox\" value=\""+arr.length+"\"></td>"; */
													str += "<tr><td class=\"text-center\"><input id=\"ckb\" name=\"ckb\" type=\"checkbox\" value=\""+i+"\"></td>";
													str += "<td>"
															+ data[i].materialNo
															+ "</td>";
													str += "<td>"
															+ data[i].materialName
															+ "　" + data[i].model
															+ "</td>";
													/* str += "<td id='tdNum"+arr.length+"'>" */
													str += "<td id='tdNum"+i+"' class='text-right'>"
															+ data[i].num + "</td>";
													str += "<td>"
															+ data[i].warehouseName
															+ "</td>";
 													str += "<td>"
															+ (typeof (data[i].snNo) == "undefined" ? ""
																	: data[i].snNo)
															+ "</td>";
													str += "<td>"
															+ (typeof (data[i].productionDate) == "undefined" ? ""
																	: data[i].productionDate)
															+ "</td>";
													arr.push(data[i]);
													//num++;
												}
												//str+="<td style=\"padding: 2px 2px 2px 2px;\"><input  style=\" width:60px;height:25px; margin: 0px 0px 0px 5px\" id=\"num"+i+"\" type=\"number\"/></td>";
												str += "</tr>";

												//queryIndex++;
												/* $("#list").append(str); */
												$("#list").find("tr").each(function(){
													$(this).remove();
											    });
												$("#list").append(str);
											}
										},
										error : function(data) {
											/* document.getElementById("text").innerHTML = '&nbsp&nbsp&nbsp&nbsp没找到S/N对应的机器';
											document.getElementById("text").style.color = 'red'; */
										}
									});
						})

		function delRow(strId) {
			$("#contentTable>tbody>tr:eq(" + strId + ")").remove();
			//$("#tr" + strId).remove();
			listData.splice(strId, 1);
			$("#contentTable>tbody").find("tr").each(function(index){ 
				$(this).find("td>a.delRow").attr('href','javascript:delRow(' + index + ');'); 
				$(this).find("input").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {

						$(this).attr("name", $(this).attr("name").replace(/\[[0-9]+\]/, "[" + index + "]"));

					}
				});
				$(this).find("td>select").each(function() {
					if (typeof($(this).attr("name")) != "undefined") {

						$(this).attr("name", $(this).attr("name").replace(/\[[0-9]+\]/, "[" + index + "]"));

					}
				});
    			$(this).find("td>input").each(function() {
    				if (typeof($(this).attr("name")) != "undefined") {
	    				$(this).attr("name", $(this).attr("name").replace(/\[[0-9]+\]/, "[" + index + "]"));
    				}
    			});
   			});
		}

		var listData = new Array();
		var trIndex = 0;
		function Storage() {
			var selectedCount = 0;
			$("input[name=ckb]:checked").each(function(){
				selectedCount++;
		    });
			if(selectedCount == 0){
				alertx("请至少选择一条物料信息。","");
				return false;
			}

			//var str ="<tr id=\"agreement"+agreementIndex+"\"><td><a href=\"javascript:delRow("+agreementIndex+");\" class=\"delRow\"><i class=\"icon-minus-sign\"></i></a></td><td class=\"text-center\" width=\"10\"><span name=\"agreementNo\" id=\"agreementNo\">"+agreementIndex+"</span></td><td><input id=\"validityDateFrom\" name=\"validityDateFrom\" type=\"text\" readonly=\"readonly\" maxlength=\"20\" class=\"input-medium Wdate \" value=\"\" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});\"></td><td><input id=\"validityDateTo\" name=\"validityDateTo\" type=\"text\" readonly=\"readonly\" maxlength=\"20\" class=\"input-medium Wdate\" value=\"\" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});\"></td><td><input id=\"newRemarks\" name=\"newRemarks\" type=\"text\" value=\"\" maxlength=\"50\" class=\"input-small required\"></td></tr>";
			var str = "";
			//var YseOrNo = true;
			//document.getElementById("listBtnErro").style.display = "none";
			$("input[name=ckb]").each(function() {
								if ($(this).attr("checked")) {

									var i = $(this).val();
									/* var tdobj = document.getElementById('tdNum'
											+ i).innerText; */
									/* ar num = $("#num" + i).val(); */
									var data = arr[i];
									for ( var li in listData) {
										if (listData[li].id == data.id) {
											//erro("数据重复","listBtnErro");
											return;
										}
									}
									listData.push(data);
									str += '<tr>'
									str += '<td class="text-center"><a href="javascript:delRow(' + trIndex + ');" class="delRow"><i class="icon-minus-sign"></i></a>'
									str += '<input name="smStorageAppDtl[' + trIndex + '][ID]" value="'+data.id+'" type="hidden">'
									str += '<input name="smStorageAppDtl[' + trIndex + '][NUM]" value="'+data.num+'" type="hidden">'
									str += '<input name="smStorageAppDtl[' + trIndex + '][MATERIAL_NO]" value="'+data.materialNo+'" type="hidden">'
									str += '<input name="smStorageAppDtl[' + trIndex + '][SN_NO]" value="'+(typeof (data.snNo) == "undefined" ? "" : data.snNo)+'" type="hidden">'
									str += '<input name="smStorageAppDtl[' + trIndex + '][WAREHOUSE]" value="'+data.warehouse+'" type="hidden"></td>'
									str += '<td>'+data.materialNo+'</td>'
									str += '<td>'+data.materialName+'　'+data.model+'</td>'
									str += '<td>'+(typeof (data.snNo) == "undefined" ? "" : data.snNo) +'</td>'
									str += '<td>'+(typeof (data.productionDate) == "undefined" ? "" : data.productionDate) +'</td>'
									str += '<td class="text-right">'+data.num+'</td>'
									str += '<td>'+data.warehouseName+'</td>'
									str += '<td><input name="smStorageAppDtl[' + trIndex + '][TO_NUM]" type="text" class="full-width required text-right digits" min="1" max="' + data.num + '"></td>'
									str += '<td>'+madeSelectList("smStorageAppDtl[" + trIndex + "][TO_WAREHOUSE]", ${fns:toJson(fns:getDictList('DM0050'))})+'</td>'
									str += '</tr>'
									
									//str += "<tr id='tr"+trIndex+"'><td><a href=\"javascript:delRow("
									//		+ trIndex
									//		+ ");\" class=\"delRow\"><i class=\"icon-minus-sign\"></i></a></td>";
									//str += "<td name='procInsId' id='procInsId'>"
									//		+ data.materialNo + "</td>";
									//str += "<td name='materialNo' id='materialNo'>"
									//		+ data.materialName
									//		+ "　"
									//		+ data.model + "</td>";
									//str += "<td name='sn_no' id='sn_no'>"
									//		+ (typeof (data.snNo) == "undefined" ? ""
									//				: data.snNo) + "</td>";
									//str += "<td name='date' id='date'>"
									//		+ (typeof (data.productionDate) == "undefined" ? ""
									//				: data.productionDate)
									//		+ "</td>";
									//str += "<td name='' id=''>" + data.num
									//		+ "</td>";
									//str += "<td name='warehouse' id='warehouse'>"
									//		+ data.warehouseName + "</td>";
									//str += "<td><input id='num' name='num' type=\"number\" value='1' style=\" width:60px;height:25px; margin: 0px 0px 0px 5px\" id=\"num"+trIndex+"\" max='"+data.num+"' min='1' /></td>";
									//str += "<td>" + selects + "";
									//str += "<input id = 'warehouseNum' name='warehouseNum' type=\"text\" value='"+data.num+"' style=\"display:none\" />";
									//str += "<input id=\"index\" name=\"index\" class=\"input-medium\" type=\"hidden\" value='"+data.id+"' readonly=\"readonly\"><input id=\"warehouseId\" name=\"warehouseId\" class=\"input-medium\" type=\"hidden\" value='"+data.warehouse+"' readonly=\"readonly\"></td>";
									//str += "</tr>";
									trIndex++;

								}
							});
			/* if (str == "") {
				erro("请输入查询条件", "listBtnErro");
				return;
			} */
			// $("#listBtn").attr("disabled", true);
			$("#addlist").append(str);

			//$("#agreement"+index).remove();
		}
			
			function madeSelectList(name, selectList) {
				var html = '';
				html += '<select class="full-width required" name="' + name + '">'
				html += '<option value=""></option>'
				for (var i in selectList) {
					html += '<option value="' + selectList[i].value + '">' + selectList[i].label + '</option>'
				}
				html += '</select>'
				
				return html;
			}

		//var queryIndex = 0;
		/* $("#qureyMaterialNo")
				.click(
						function() {
							var qureyMaterialNo = $("#qureyMaterialNo").val();
							$
									.ajax({
										type : "get",
										url : "${ctx}/sm/sm006/queryByMaterial?qureyMaterialNo="
												+ qureyMaterialNo,
										dataType : "json",
										success : function(data) {

											var str = "";
											var num = 0;

											for ( var i in data) {
												var yse = false;
												for ( var c in arr) {
													if (arr[c].snNo == data[i].snNo &&  data[i].snNo!=""&& data[i].snNo!=null)
														yse = true;
												}
												if (yse)
													continue;
												str += "<tr><td><input id=\"ckb\" name=\"ckb\" type=\"checkbox\" value=\""+arr.length+"\"></td>";
												str += "<td>"
														+ data[i].materialNo
														+ "</td>";
												str += "<td>"
														+ data[i].materialName
														+ "　" + data[i].model
														+ "</td>";
												str += "<td id='tdNum"+arr.length+"'>"
														+ data[i].num + "</td>";
												str += "<td>"
														+ data[i].warehouseName
														+ "</td>";
												str += "<td>" + (typeof (data[i].snNo) == "undefined" ? "-"
														: data[i].snNo)
														+ "</td>";
												str += "<td>"
														+ (typeof (data[i].productionDate) == "undefined" ? "-"
																: data[i].productionDate)
														+ "</td>";
												arr.push(data[i]);
												num++;
											}
											//str+="<td style=\"padding: 2px 2px 2px 2px;\"><input  style=\" width:60px;height:25px; margin: 0px 0px 0px 5px\" id=\"num"+i+"\" type=\"number\"/></td>";
											str += "</tr>";

											queryIndex++;
											$("#list").append(str);
										},
										error : function(data) {
											alert("录入成功：" + data.name);
										}
									});
							agreementIndex++;
						}); */

		/* function getCity() {
			$("#city").empty();
			$("#province").find("option:selected").text();
			var index = -1;
			var cityStr = "";
			var city = $("#province").find("option:selected").text();

			for ( var ct in treeData) {

				if (treeData[ct].name == city && treeData[ct].pId == 1) {
					index = treeData[parseInt(ct) + parseInt(1)].pId;
				}
				if (index == treeData[ct].pId) {
					cityStr += "<option  value='"+treeData[ct].id+"' >"
							+ treeData[ct].name + "</option>";
				}

			}
			var checkText = $("#select_id").find("option:selected").text("  ");
			$("#city").append(cityStr);
		} */

		/* var selects = "";
		window.onload = function() {
			selects = "<select id='warehouse' name='toWarehouse1'><option value=''> </option>";
			// var select= $("#toWarehouse").html();
			$("#toWarehouse option").each(function() {
				var oValue = $(this).val().toString();
				var oText = $(this).text().toString();
				selects += "<option value='"+oValue+"'>" + oText + "</option>";
				//var option = $("<option>").val(oValue).text(oText);	
				//alert(option);
			});
			selects += "</select>";

			//alert(selects);
		} */

		function move() {
			/* $("#list").html("<thead id =\"list\"></thead>"); */
			$("#list").html("");
			//arr = new Array();
			//$("#listBtn").attr("disabled", false);
			$("#querySnNo").val("");
			$("#qureyMaterialNo").select2('val', "");
			//	$("#addlist").html("<tbody Id=\"addlist\"></tbody>");
		}
		/* function erro(erro, id) {
			var e = document.getElementById(id);
			e.innerHTML = erro;
			e.style.display = "block";
		} */

		/* function erro1() {

			//document.getElementById("listBtnErro").style.display = "none";
			document.getElementById("SubmitErro").style.display = "none";

		} */
	</script>

</body>
</html>