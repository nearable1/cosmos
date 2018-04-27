<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同录入</title>
	<meta name="decorator" content="default"/>
	
	<!-- <style type="text/css">
		.table-overflow-order{
			width:3239px;
		}
		.table-overflow-invoice{
			width:2120px;
		}
	</style> -->
	<style type="text/css">
		.input-xxlarge{width:450px}
	</style>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			initPage();
			
			var modalValidate;
			
			function initPage() {
				// 添加画面验证
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
				
				/* if (${fns:toJson(soOrder.soOrderDtlList)} != null && ${fns:toJson(soOrder.soOrderDtlList)} != "") {
					$("#orderDtlTable").addClass("table-overflow-order");
				} */
				
				/* if (${fns:toJson(soOrder.imInvoiceDtlList)} != null && ${fns:toJson(soOrder.imInvoiceDtlList)} != "") {
					$("#iminvoiceDtlInfoTable").addClass("table-overflow-invoice");
				} */
				
			};
			
			// 支付条件变更事件
			$(".paymaentCon").on("change", function() {
				var $paymaentCon = $(this);
				var paymaentCon = $paymaentCon.val();
				if (paymaentCon == '2') {

					$(".conditionRemarks").rules("add", { required: true});
				} else {
					$(".conditionRemarks").rules("remove");
				}
			});

			// 佣金/费用变更事件
			$(".commissionFix").on("change", function() {
				var $commissionFix = $(this);
				var commissionFix = $commissionFix.val();
				if (commissionFix == "" || commissionFix == null) {

					$(".commission").val("");
				} else {
					$(".commission").val(commissionFix.trim().replace(/,/g, ""));
				}
			});
			
			// 合同类型变更事件
			$(".orderType").on("change", function() {
				var $orderType = $(this);
				var orderType = $orderType.val();
				if (orderType == '2') {

					$(".exchangeRate").rules("add", { required: true });
					$(".commission").rules("add", { required: true });
				} else {
					$(".exchangeRate").rules("remove");
					$(".commission").rules("remove");
				}
			});
			
 			// 添加合同明细行
			$("#addOrderRow").on("click", function() {
				$("#soOrderDtlModal").modal('show').css({
                    width: '1024px',
                    'margin-left': '-512px'
                });

				if ($("#employeeNo").val() != "" && $("#employeeNo").val() != null) {
					$("#responsiblePersonId").val($("#employeeNo").val());
					$("#responsiblePersonId").trigger("change");
					$("#commissionPeisonId").val($("#employeeNo").val());
					$("#commissionPeisonId").trigger("change");
				}
				$("#warrantyPeriod").val("1");
				$("#lineNo").val("0");
			});
			
 			// 编辑合同明细行
			$(document).on("click", "table .editOrderRow", function() {
				$("#soOrderDtlModal").modal('show').css({
                    width: '1024px',
                    'margin-left': '-512px'
                });

				var editPackageMertiralNo = $(this).parents("tr").find("input.editPackageMertiralNo").val();
				var editEndCustomerId = $(this).parents("tr").find("input.editEndCustomerId").val();
				var editEndCustomerName = $(this).parents("tr").find("input.editEndCustomerName").val();
				var editCustomerSegmentation = $(this).parents("tr").find("input.editCustomerSegmentation").val();
				var editRegion = $(this).parents("tr").find("input.editRegion").val();
				var editResponsiblePersonId = $(this).parents("tr").find("input.editResponsiblePersonId").val();
				var editCommissionPeisonId = $(this).parents("tr").find("input.editCommissionPeisonId").val();
				var editMaterialNo = $(this).parents("tr").find("input.editMaterialNo").val();
				var editMaterialName = $(this).parents("tr").find("input.editMaterialName").val();
				var editNum = $(this).parents("tr").find("input.editNum").val();
				var editUnitPrice = $(this).parents("tr").find("input.editUnitPrice").val();
				var editStandardPrice = $(this).parents("tr").find("input.editStandardPrice").val();
				var editWarrantyPeriod = $(this).parents("tr").find("input.editWarrantyPeriod").val();
				var editExtendedWarrPeriod = $(this).parents("tr").find("input.editExtendedWarrPeriod").val();
				var editIfEffective = $(this).parents("tr").find("input.editIfEffective").val();
				var editIfReturn = $(this).parents("tr").find("input.editIfReturn").val();
				var editOrganize = $(this).parents("tr").find("input.editOrganize").val();
				var editLineNo = $(this).parents("tr").find("input.editLineNo").val();

				var editDeliverNum = $(this).parents("tr").find("input.editDeliverNumMax").val();
				var editInvoiceNum = $(this).parents("tr").find("input.editInvoiceNumMax").val();
				$("#deliverNum").val(editDeliverNum);
				$("#invoiceNum").val(editInvoiceNum);
				$("#lineNo").val(editLineNo);

				var editPackageMertiralNo = $(this).parents("tr").find("input.editPackageMertiralNo").val();
				
				$("#rowNo").val($(this).html());
				$("#endCustomerId").val(editEndCustomerId);
				$("#endCustomerId").trigger("change");
				$("#endCustomerName").val(editEndCustomerName);
				$("#customerSegmentation").val(editCustomerSegmentation);
				$("#customerSegmentation").trigger("change");
				$("#region").val(editRegion);
				$("#region").trigger("change");
				$("#responsiblePersonId").val(editResponsiblePersonId);
				$("#responsiblePersonId").trigger("change");
				$("#commissionPeisonId").val(editCommissionPeisonId);
				$("#commissionPeisonId").trigger("change");
				$("#organize").val(editOrganize);
				$("#organize").trigger("change");
				
				if (editPackageMertiralNo != null && editPackageMertiralNo != "") {
					$("#materialNo").val(editPackageMertiralNo);
					$("#materialNo").trigger("change");
					
					$.ajax("${ctx}/common/materials/" + editPackageMertiralNo, {
	                    dataType: "json"
	                }).done(function(data) { $("#materialName").val(data.text); });
					
					//$("#endCustomerId").attr("readOnly","true"); 
					$("#materialNo").attr("readOnly","true"); 
					// $(".modalNum").attr("readOnly","true"); 
				} else {
					$("#materialNo").val(editMaterialNo);
					$("#materialNo").trigger("change");

					$("#materialName").val(editMaterialName);
				}

				$(".modalNum").val(editNum);
				$(".modalUnitPrice").val(editUnitPrice);
				$(".modalUnitPriceFix").val(toThousands(editUnitPrice));
				$(".modalUnitPriceFix").trigger("change");
				$(".modalStandardPrice").val(editStandardPrice);
				$(".modalStandardPriceFix").val(toThousands(editStandardPrice));
				$("#warrantyPeriod").val(editWarrantyPeriod);
				$("#extendedWarrPeriod").val(editExtendedWarrPeriod);
				
				if (editIfEffective == "1") {
					$("#ifEffective").attr("checked","true"); 
				}
				if (editIfReturn == "1") {
					$("#ifReturn").attr("checked","true"); 
				}
			});

 			/* // 合同行编辑画面地区负责人变更事件
			$("#responsiblePersonId").on("change", function() {
				
				if ($("#responsiblePersonId").val() == "" || $("#responsiblePersonId").val() == null) {
					$("#organize").val("");
		        	$("#organize").trigger("change");
				} else {

					$.ajax({
				    	url: "${ctx}" + "/sd/soOrder/getOrganize",
				        type: "get",
				        async: false,
				        data: {"commissionPeisonId":$("#responsiblePersonId").val()},
				        dataType: "json",
				        success: function (data) {

				        	$("#organize").val(data.organize);
				        	$("#organize").trigger("change");
				        },
				        error: function (msg) {
				        }
				    });
				}
			}); */
			
			// 合同行编辑画面物料号变更事件
			$(document).on("change", "input.materialNo", function(e) {
				var $noInput = $(this);
				var $nameInput = $noInput.parent().parent().find("input.materialName");
				if ($noInput.val() == "") {
					$nameInput.val("");
		        	$(".modalStandardPrice").val("");
		        	$(".modalStandardPriceFix").val("");
				} else {
					if (typeof(e.added) != "undefined") {
						$nameInput.val(e.added.text);
					}
					
					if ($("#orderType").val() == "1" || $("#businessOppNo").val() == null || $("#businessOppNo").val() == "") {
						
						var customerId = $("#endCustomerId").val();
						var priceSystem = $("#priceSystem").val();
						var materialNo = $("#materialNo").val();
						var region = $("#region").val();
						var industry = $("#industry").val();

						var standardPrice = getStandardPrice(priceSystem, customerId, materialNo, region, industry);

						if (standardPrice != null && standardPrice != "") {
							
							$(".modalStandardPrice").val(standardPrice);
							$(".modalStandardPriceFix").val(toThousands(standardPrice));

							if ($(".modalUnitPrice").val() == null || $(".modalUnitPrice").val() == "") {

					        	$(".modalUnitPrice").val(standardPrice);
					        	$(".modalUnitPriceFix").val(toThousands(standardPrice));
					        	$(".modalUnitPriceFix").trigger("change");
							}
						} else {
							
							$(".modalStandardPrice").val(0);
							$(".modalStandardPriceFix").val(0);

				        	//$(".modalUnitPrice").val(0);
				        	//$(".modalUnitPriceFix").val(0);
				        	//$(".modalUnitPriceFix").trigger("change");
						}
					}
				}
				//e.choice
			});
			$(document).on("change", "input.customerId", function(e) {
				var $noInput = $(this);
				var $nameInput = $noInput.parent().parent().find("input.customerChName");
				if ($noInput.val() == "") {
					$nameInput.val("");
				} else {
					if (typeof(e.added) != "undefined") {
						$nameInput.val(e.added.text);
					}
				}
				//e.choice
			});

			// 合同行编辑画面地区变更事件
			$("#region").on("change", function() {
				$("input.materialNo").trigger("change");
			});
			
			// 合同行编辑画面最终客户变更事件
			$(document).on("change", "input.endCustomerId", function(e) {
				var $noInput = $(this);
				var $nameInput = $noInput.parent().parent().find("input.endCustomerName");

				if ($noInput.val() == "") {
					$nameInput.val("");
		        	$("#industry").val("");
		        	$("#industry").trigger("change");
		        	getDictChildList('${ctx}',"customerSegmentation","DM0002_1","DM0039");

		        	$("#customerDiff").val("");
		        	$("#customerDiff").trigger("change");
		        	$("#province").val("");
		        	$("#province").trigger("change");
		        	$(".modalCity").val("");
		        	$(".modalCity").trigger("change");
		        	
		        	//$(".materialNo").val("");
		        	$(".materialNo").trigger("change");
				} else {
					if (typeof(e.added) != "undefined") {
						$nameInput.val(e.added.text);
					}
					$.ajax({
				    	url: "${ctx}" + "/sd/soOrder/getCustomerInfo",
				        type: "get",
				        async: false,
				        data: {"customerId":$("#endCustomerId").val()},
				        dataType: "json",
				        success: function (data) {
				        	
				        	var customerInfo = data.customerInfo;
				        	
				        	$("#industry").val(customerInfo.industry);
				        	$("#industry").trigger("change");
				        	getDictChildList('${ctx}',"customerSegmentation","DM0002_"+customerInfo.industry,"DM0039");

				        	$("#customerDiff").val(customerInfo.customerDiff);
				        	$("#customerDiff").trigger("change");
				        	$("#province").val(customerInfo.province);
				        	$("#province").trigger("change");
				        	if ($("#region").val() == "") {

					        	$("#region").val(customerInfo.region);
					        	$("#region").trigger("change");
				        	}
				        	$(".modalCity").val(customerInfo.city);
				        	$(".modalCity").trigger("change");

				        	$(".materialNo").trigger("change");
				        },
				        error: function (msg) {
				        }
				    });
				}
				//e.choice
			});

			$("#modalSave").on("click", function() {

				if (!$("#modalForm").valid()) {
					return false;
				} else {
					
					$("#soOrderDtlModal").modal('hide');
					
					//if ($("#endCustomerId").val() != "" && $("#endCustomerId").val() != null) {

						var smPackageInfos;
						$.ajax({
					    	url: "${ctx}" + "/common/smPackageInfos/",
					        type: "get",
					        async: false,
					        data: {"materialNo":$("#materialNo").val()},
					        dataType: "json",
					        success: function (data) {
					        	
					        	smPackageInfos = data.smPackageInfos;
					        },
					        error: function (msg) {
					        }
					    });
						
						var editPackageMertiralNo;
						var editMertiralNo;
						var editEndCustomerId = $("#endCustomerId").val();
						if (smPackageInfos != null) {
							editPackageMertiralNo = $("#materialNo").val();
						} else {
							editMertiralNo = $("#materialNo").val();
						}
						
						var industry = $("#industry").val();
						var customerDiff = $("#customerDiff").val();
						var customerSegmentation = $("#customerSegmentation").val();
						if (customerSegmentation == null) {
							customerSegmentation = "";
						}
						var region = $("#region").val();
						
						//var responsiblePersonId = $("#responsiblePersonId").val();
						//var commissionPeisonId = $("#commissionPeisonId").val();
						var responsiblePersonName = $("#responsiblePersonId").select2("data").text;
						var commissionPeisonName = $("#commissionPeisonId").select2("data").text;
						
						var editDel;
						var deliverNum = $("#deliverNum").val();
						var invoiceNum = $("#invoiceNum").val();
						var lineNo = Number($("#lineNo").val());
						var lineNoStr = $("#lineNo").val();
						if (lineNoStr == "0") {
							lineNoStr = "";
						}
						if ((deliverNum == null || deliverNum == "" || deliverNum == 0) && (invoiceNum == null || invoiceNum == "" || invoiceNum == 0)) {
							editDel = "1";
						} else {
							editDel = "0";
						}

						var rowNum;
						var rowNo = $("#rowNo").val();
						if (rowNo != "") {
							
							rowNum = Number(rowNo);
						} else {
							// 查看现有行数
							rowNum = $("#orderDtlTable>tbody>tr:not(.empty)").length + 1;
						}
						var index = rowNum;
						var html = '';
						for (var i in smPackageInfos) {

							var deliverNumDb;
							var invoiceNumDb;

					    	if (editDel == "0") {

								// 信息取得
								$.ajax({
							    	url: "${ctx}" + "/sd/soOrder/getOrderDtlInfo",
							        type: "get",
							        async: false,
							        data: {"lineNo":lineNoStr,"orderId":$("#id").val()},
							        dataType: "json",
							        success: function (data) {
							        	
							        	var orderDtlInfo = data.orderDtlInfo;
							        	
							        	deliverNumDb = typeof (orderDtlInfo.deliverNum) == "undefined" ? "" : orderDtlInfo.deliverNum;
							        	invoiceNumDb = typeof (orderDtlInfo.invoiceNum) == "undefined" ? "" : orderDtlInfo.invoiceNum;
							        },
							        error: function (msg) {
							        }
							    });
					    	}
							    html += '<tr>'
							    if (i == 0) {

							    	if (editDel == "0") {

										html += '<td class="text-center"></td>'
							    	} else {

										html += '<td class="text-center"><a href="javascript:;" class="delSoOrderDtlRow"><i class="icon-minus-sign"></i></a></td>'
							    	}
									html += '<td class="text-center rowNo"><a href="javascript:;" class="editOrderRow link">'+ index + '</a></td>'
							    } else {

									html += '<td class="text-center"></td>'
									html += '<td class="text-center rowNo">'+ index + '</td>'
							    }
							    html += '<td class="text-center">'+ lineNoStr + '</td>'
								html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].endCustomerId" value="' + $("#endCustomerId").val() + '" class="editEndCustomerId"/>'
								html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].lineNo" value="' + lineNo + '" class="editLineNo"/>'
								html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].deliverNumMax" value="' + $("#deliverNum").val() + '" class="editDeliverNumMax"/>'
								html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].invoiceNumMax" value="' + $("#invoiceNum").val() + '" class="editInvoiceNumMax"/>'
								if (editDel == "0") {
									if (deliverNumDb != "") {
										html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].deliverNum" value="' + deliverNumDb + '" class="editDeliverNum"/>'
									}
									if (invoiceNumDb != "") {
										html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].invoiceNum" value="' + invoiceNumDb + '" class="editInvoiceNum"/>'
									}
								}
								html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].customerChName" value="' + $("#endCustomerName").val() + '" class="editEndCustomerName"/>' + $("#endCustomerName").val() + '</td>'
								html += '<td><input name="soOrderDtlList[' + (index - 1) + '].industry" type="hidden" value="' + industry + '" style="width: 100%" />' + getSelectLable(industry, ${fns:toJson(fns:getDictList('DM0002'))}) + '</td>'
								html += '<td><input name="soOrderDtlList[' + (index - 1) + '].customerDiff" type="hidden" value="' + customerDiff + '" style="width: 100%" />' + getSelectLable(customerDiff, ${fns:toJson(fns:getDictList('DM0003'))}) + '</td>'
								html += '<td><input name="soOrderDtlList[' + (index - 1) + '].customerSegmentation" class="editCustomerSegmentation" type="hidden" value="' + customerSegmentation + '" style="width: 100%" />' + getSelectLable(customerSegmentation, ${fns:toJson(fns:getDictList('DM0039'))}) + '</td>'
								html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].province" value="' + $("#province").find("option:selected").val() + '"/>' + $("#province").find("option:selected").text() + '</td>'
								
								html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].city" value="' + $(".modalCity").find("option:selected").val() + '"/>' + $(".modalCity").find("option:selected").text() + '</td>'
								
								html += '<td><input name="soOrderDtlList[' + (index - 1) + '].region" type="hidden" class="editRegion" value="' + region + '" style="width: 100%" />' + getSelectLable(region, ${fns:toJson(fns:getDictList('DM0049'))}) + '</td>'
								html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].responsiblePersonId" class="editResponsiblePersonId" value="' + $("#responsiblePersonId").val() + '"/>' + responsiblePersonName + '</td>'
								
								html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].commissionPeisonId" class="editCommissionPeisonId" value="' + $("#commissionPeisonId").val() + '"/>' + commissionPeisonName + '</td>'
								
								html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].organize" class="editOrganize" value="' + $("#organize").find("option:selected").val() + '"/>' + $("#organize").find("option:selected").text() + '</td>'
								
								html += '<td><input type="hidden"  name="soOrderDtlList[' + (index - 1) + '].materialNo" class="editMaterialNo" value="' + smPackageInfos[i].materialNo + '"/>'
								html += '<input type="hidden" class="editPackageMertiralNo" name="soOrderDtlList[' + (index - 1) + '].packageMertiralNo" value="' + $("#materialNo").val() + '"/>' + smPackageInfos[i].materialNo + '</td>'
								html += '<td><input name="soOrderDtlList[' + (index - 1) + '].materialName" type="hidden" value="' + smPackageInfos[i].materialName + '" class="editMaterialName" />' + smPackageInfos[i].materialName + '</td>'
								html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].num" class="editNum" type="hidden" value="' + invoiceAmount(smPackageInfos[i].num, $("#num").val()) + '" />' + invoiceAmount(smPackageInfos[i].num, $("#num").val()) + '</td>'
								if (i == 0) {
								
									html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].standardPrice" class="editStandardPrice" type="hidden" value="' + $("#standardPrice").val() + '" />' + toThousands($("#standardPrice").val()) + '</td>'
									html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].unitPrice" class="editUnitPrice" type="hidden" value="' + $("#unitPrice").val() + '" />' + toThousands($("#unitPrice").val()) + '</td>'
									html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].totalAmount" type="hidden" value="' + $("#totalAmount").val() + '" />' + toThousands($("#totalAmount").val()) + '</td>'
								} else {
								
									html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].standardPrice" class="editStandardPrice" type="hidden" value="0" />0</td>'
									html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].unitPrice" class="editUnitPrice" type="hidden" value="0" />0</td>'
									html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].totalAmount" type="hidden" value="0" />0</td>'
								}
								html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].warrantyPeriod" class="editWarrantyPeriod" type="hidden" value="' + $("#warrantyPeriod").val() + '" />' + $("#warrantyPeriod").val() + '</td>'
								html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].extendedWarrPeriod" class="editExtendedWarrPeriod" type="hidden" value="' + $("#extendedWarrPeriod").val() + '" />' + $("#extendedWarrPeriod").val() + '</td>'
								if (document.getElementById("ifEffective").checked) {
									html += '<td class="text-center"><input name="soOrderDtlList[' + (index - 1) + '].ifEffective" class="editIfEffective" type="hidden" value="1"/><input type="checkbox" disabled="disabled" checked="checked" /></td>'
								} else {
									html += '<td class="text-center"><input name="soOrderDtlList[' + (index - 1) + '].ifEffective" class="editIfEffective" type="hidden" value="0" /><input type="checkbox" disabled="disabled" /></td>'
								}
								if (document.getElementById("ifReturn").checked) {
									html += '<td class="text-center"><input name="soOrderDtlList[' + (index - 1) + '].ifReturn" class="editIfReturn" type="hidden" value="1" /><input type="checkbox" disabled="disabled" checked="checked" /></td>'
								} else {
									html += '<td class="text-center"><input name="soOrderDtlList[' + (index - 1) + '].ifReturn" class="editIfReturn" type="hidden" value="0" /><input type="checkbox" disabled="disabled" /></td>'
								}
								html += '</tr>';
								
								index = index + 1;
								
								if (lineNo > 0) {

									lineNo = lineNo + 1;
									lineNoStr = lineNo;
								}
						}
						
						if (html == '') {

						    html += '<tr>'

						    if (editDel == "0") {

								html += '<td class="text-center"></td>'
						    } else {

								html += '<td class="text-center"><a href="javascript:;" class="delSoOrderDtlRow"><i class="icon-minus-sign"></i></a></td>'
						    }
							//html += '<td class="text-center"><a href="javascript:;" class="delSoOrderDtlRow"><i class="icon-minus-sign"></i></a></td>'
							html += '<td class="text-center rowNo"><a href="javascript:;" class="editOrderRow link">'+ index + '</a></td>'
						    html += '<td class="text-center">'+ lineNoStr + '</td>'
							html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].endCustomerId" value="' + $("#endCustomerId").val() + '" class="editEndCustomerId"/>'
							html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].lineNo" value="' + lineNo + '" class="editLineNo"/>'
							html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].deliverNumMax" value="' + $("#deliverNum").val() + '" class="editDeliverNumMax"/>'
							html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].invoiceNumMax" value="' + $("#invoiceNum").val() + '" class="editInvoiceNumMax"/>'
							html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].deliverNum" value="' + $("#deliverNum").val() + '" class="editDeliverNum"/>'
							html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].invoiceNum" value="' + $("#invoiceNum").val() + '" class="editInvoiceNum"/>'
							html += '<input type="hidden" name="soOrderDtlList[' + (index - 1) + '].customerChName" value="' + $("#endCustomerName").val() + '" class="editEndCustomerName"/>' + $("#endCustomerName").val() + '</td>'
							html += '<td><input name="soOrderDtlList[' + (index - 1) + '].industry" type="hidden" value="' + industry + '" style="width: 100%" />' + getSelectLable(industry, ${fns:toJson(fns:getDictList('DM0002'))}) + '</td>'
							html += '<td><input name="soOrderDtlList[' + (index - 1) + '].customerDiff" type="hidden" value="' + customerDiff + '" style="width: 100%" />' + getSelectLable(customerDiff, ${fns:toJson(fns:getDictList('DM0003'))}) + '</td>'
							html += '<td><input name="soOrderDtlList[' + (index - 1) + '].customerSegmentation" class="editCustomerSegmentation" type="hidden" value="' + customerSegmentation + '" style="width: 100%" />' + getSelectLable(customerSegmentation, ${fns:toJson(fns:getDictList('DM0039'))}) + '</td>'
							html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].province" value="' + $("#province").find("option:selected").val() + '"/>' + $("#province").find("option:selected").text() + '</td>'
							html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].city" value="' + $(".modalCity").find("option:selected").val() + '"/>' + $(".modalCity").find("option:selected").text() + '</td>'
							html += '<td><input name="soOrderDtlList[' + (index - 1) + '].region" type="hidden" class="editRegion" value="' + region + '" style="width: 100%" />' + getSelectLable(region, ${fns:toJson(fns:getDictList('DM0049'))}) + '</td>'
							html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].responsiblePersonId" class="editResponsiblePersonId" value="' + $("#responsiblePersonId").val() + '"/>' + responsiblePersonName + '</td>'
							html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].commissionPeisonId" class="editCommissionPeisonId" value="' + $("#commissionPeisonId").val() + '"/>' + commissionPeisonName + '</td>'
							html += '<td><input type="hidden" name="soOrderDtlList[' + (index - 1) + '].organize" class="editOrganize" value="' + $("#organize").find("option:selected").val() + '"/>' + $("#organize").find("option:selected").text() + '</td>'
							html += '<td><input name="soOrderDtlList[' + (index - 1) + '].materialNo" class="editMaterialNo" type="hidden" value="' + $("#materialNo").val() + '" />'
							html += '<input type="hidden" class="editPackageMertiralNo" name="soOrderDtlList[' + (index - 1) + '].packageMertiralNo" value=""/>' + $("#materialNo").val() + '</td>'
							html += '<td><input name="soOrderDtlList[' + (index - 1) + '].materialName" type="hidden" value="' + $("#materialName").val() + '" class="editMaterialName" />' + $("#materialName").val() + '</td>'
							html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].num" class="editNum" type="hidden" value="' + $("#num").val() + '" />' + $("#num").val() + '</td>'
							html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].standardPrice" class="editStandardPrice" type="hidden" value="' + $("#standardPrice").val() + '" />' + toThousands($("#standardPrice").val()) + '</td>'
							html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].unitPrice" class="editUnitPrice" type="hidden" value="' + $("#unitPrice").val() + '" />' + toThousands($("#unitPrice").val()) + '</td>'
							html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].totalAmount" type="hidden" value="' + $("#totalAmount").val() + '" />' + toThousands($("#totalAmount").val()) + '</td>'
							html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].warrantyPeriod" class="editWarrantyPeriod" type="hidden" value="' + $("#warrantyPeriod").val() + '" />' + $("#warrantyPeriod").val() + '</td>'
							html += '<td class="text-right"><input name="soOrderDtlList[' + (index - 1) + '].extendedWarrPeriod" class="editExtendedWarrPeriod" type="hidden" value="' + $("#extendedWarrPeriod").val() + '" />' + $("#extendedWarrPeriod").val() + '</td>'
							if (document.getElementById("ifEffective").checked) {
								html += '<td class="text-center"><input name="soOrderDtlList[' + (index - 1) + '].ifEffective" class="editIfEffective" type="hidden" value="1"/><input type="checkbox" disabled="disabled" checked="checked" /></td>'
							} else {
								html += '<td class="text-center"><input name="soOrderDtlList[' + (index - 1) + '].ifEffective" class="editIfEffective" type="hidden" value="0" /><input type="checkbox" disabled="disabled" /></td>'
							}
							if (document.getElementById("ifReturn").checked) {
								html += '<td class="text-center"><input name="soOrderDtlList[' + (index - 1) + '].ifReturn" class="editIfReturn" type="hidden" value="1" /><input type="checkbox" disabled="disabled" checked="checked" /></td>'
							} else {
								html += '<td class="text-center"><input name="soOrderDtlList[' + (index - 1) + '].ifReturn" class="editIfReturn" type="hidden" value="0" /><input type="checkbox" disabled="disabled" /></td>'
							}
							html += '</tr>'
						}
						if (rowNo != "") {

							editTableRow(html, rowNum);
						} else {

							$("#orderDtlTable>tbody").append(html).find("tr.empty").remove();
							//addTableRow(html,editPackageMertiralNo,editEndCustomerId,editMertiralNo);
						}
					//}
					
					/* if ($("#orderDtlTable>tbody").find("tr").length != 0) {
						$("#orderDtlTable").addClass("table-overflow-order");
					} */
				}
			});
			
			$('#soOrderDtlModal').on('hidden.bs.modal', function () {

				clearForm();
			});
			
			function clearForm() {
				
				modalValidate.resetForm();

				$("#rowNo").val("");
				$("#endCustomerId").val("");
				$("#endCustomerId").trigger("change");
				$("#endCustomerName").val("");
				$("#customerSegmentation").val("");
				$("#customerSegmentation").trigger("change");
				$("#region").val("");
				$("#region").trigger("change");
				$("#responsiblePersonId").val("");
				$("#responsiblePersonId").trigger("change");
				$("#commissionPeisonId").val("");
				$("#commissionPeisonId").trigger("change");
				$("#materialNo").val("");
				$("#materialNo").trigger("change");
				$("#materialName").val("");
				$("#num").val("");
				//$("#unitPrice").val("");
				//$("#unitPrice").trigger("change");
				$(".modalUnitPriceFix").val("");
				$(".modalUnitPriceFix").trigger("change");
				$("#warrantyPeriod").val("");
				$("#extendedWarrPeriod").val("");
				$("#ifEffective").removeAttr("checked");
				$("#ifReturn").removeAttr("checked");
				$("#endCustomerId").removeAttr("readOnly"); 
				$("#materialNo").removeAttr("readOnly"); 
				//$("#num").removeAttr("readOnly"); 
				$("#deliverNum").val("");
				$("#invoiceNum").val("");
				$("#lineNo").val("");
			};
			
			function editTableRow(html, rowNo) {

				var editPackageMertiralNo;
				var editEndCustomerId;
				$("#orderDtlTable>tbody>tr:not(.empty)").each(function(){

					if ($(this).find("td.rowNo").find("a").html() == rowNo) {
						editPackageMertiralNo = $(this).find("input.editPackageMertiralNo").val();
						editEndCustomerId = $(this).find("input.editEndCustomerId").val();

						if (editPackageMertiralNo == null || editPackageMertiralNo == "") {

							$(this).remove();
						}
					}
					
					if (editPackageMertiralNo != null && editPackageMertiralNo != "") {

						$(this).find("input.editPackageMertiralNo").each(function() {
							if (editPackageMertiralNo == $(this).val() && editEndCustomerId == $(this).parents("tr").find("input.editEndCustomerId").val()) {
								$(this).parents("tr").remove();
							}
						});
					}
				});

				
				if ($("#orderDtlTable>tbody").find("tr").length == rowNo || $("#orderDtlTable>tbody").find("tr").length > rowNo) {
					var currentRow;
					if (rowNo == 1) {
						currentRow=$('#orderDtlTable>tbody>tr:eq('+(rowNo-1)+')');
						currentRow.before(html);
					} else {
						currentRow=$('#orderDtlTable>tbody>tr:eq('+(rowNo-2)+')');
						currentRow.after(html);
					}

				} else {

					$("#orderDtlTable>tbody").append(html);

				}

				$("#orderDtlTable>tbody>tr:not(.empty)").each(function(index){
					
					if ($(this).find("a").length != 0) {

						$(this).find("td.rowNo").html('<a href="javascript:;" class="editOrderRow link">' + (index+1) + '</a>');
					} else {

						$(this).find("td.rowNo").html(index+1);
					}
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
			}
			
			/* function addTableRow(html,editPackageMertiralNo,editEndCustomerId,editMertiralNo) {

				$("#orderDtlTable>tbody>tr:not(.empty)").each(function(){
					
					if (editPackageMertiralNo != null && editPackageMertiralNo != "") {
						$(this).find("input.editPackageMertiralNo").each(function() {
							if (editPackageMertiralNo == $(this).val() && editEndCustomerId == $(this).parents("tr").find("input.editEndCustomerId").val()) {
								$(this).parents("tr").remove();
							}
						});
					}

					if (editMertiralNo != null && editMertiralNo != "") {
						$(this).find("input.editMertiralNo").each(function() {
							if (editMertiralNo == $(this).val() && editEndCustomerId == $(this).parents("tr").find("input.editEndCustomerId").val()) {
								$(this).parents("tr").remove();
							}
						});
					}
				});

				$("#orderDtlTable>tbody").append(html).find("tr.empty").remove();

				$("#orderDtlTable>tbody>tr:not(.empty)").each(function(index){
					
					if ($(this).find("a").length != 0) {

						$(this).find("td.rowNo").html('<a href="javascript:;" class="editOrderRow link">' + (index+1) + '</a>');
					} else {

						$(this).find("td.rowNo").html(index+1);
					}
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
			} */
			
 			// 添加收款管理明细行
			$("#addSoGatheringInfoRow").on("click", function() {
				// 查看现有行数
				var rowNum = $("#soGatheringInfoTable>tbody>tr:not(.empty)").length + 1;
				// 创建行
				var html = '<tr>'
					html += '<td class="text-center"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>'
					html += madeSelectList("soGatheringInfoList[" + (rowNum - 1) + "].gatheringType", ${fns:toJson(fns:getDictList('DM0047'))})
					html += '<td><input name="soGatheringInfoList[' + (rowNum - 1) + '].totalAmount" type="hidden" value="" class="text-right required totalAmountSog" maxlength="11"/>'
					html += '<input type="text" value="" class="text-right required totalAmountSogFix" style="width: 100%" onblur="numToStr(this);" onfocus="strToNum(this);" /></td>'
					html += '<td><input name="soGatheringInfoList[' + (rowNum - 1) + '].expDateFrom" type="text" readonly="readonly" maxlength="10" class="input-medium Wdate required" style="width: 100%;" value="" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'});"></td>'
					html += '<td><input name="soGatheringInfoList[' + (rowNum - 1) + '].invoiceTitle" type="text" value="" maxlength="100" class="input-medium" style="width: 100%" /></td>'
					html += '<td><input name="soGatheringInfoList[' + (rowNum - 1) + '].actDateFrom" type="text" readonly="readonly" maxlength="10" class="input-medium Wdate" style="width: 100%;" value="" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'});"></td>'
					html += '<td><input name="soGatheringInfoList[' + (rowNum - 1) + '].newRemarks" type="text" maxlength="300" class="input-medium" value=""/></td>'
					html += '</tr>'

				$("#soGatheringInfoTable>tbody").append(html).find("tr.empty").remove();
			});
			
 			// 添加发票明细行
			$("#addIminvoiceDtlInfoRow").on("click", function() {
				// 查看现有行数
				var rowNum = $("#iminvoiceDtlInfoTable>tbody>tr:not(.empty)").length + 1;
				// 创建行
				var html = '<tr>'
					html += '<td class="text-center"><a href="javascript:;" class="delInvoiceRow"><i class="icon-minus-sign"></i></a></td>'
					html += madeSelectList("imInvoiceDtlList[" + (rowNum - 1) + "].lineNo", ${fns:toJson(soOrder.soOrderDtlList)}, "1")
					html += '<td><input name="materialName" type="text" value="" class="input-xlarge materialName" readonly /></td>'
					html += '<td><input name="unitPrice" type="hidden" value="" class="text-right unitPrice" />'
					html += '<input type="text" value="" class="text-right unitPriceFix" style="width: 100px" readonly /></td>'
					html += '<td><input name="imInvoiceDtlList[' + (rowNum - 1) + '].num" maxlength="3" type="text" value="" class="text-right num required" style="width: 100px" /></td>'
					html += '<td><input name="imInvoiceDtlList[' + (rowNum - 1) + '].invoiceAmount" maxlength="11" type="hidden" value="" class="text-right invoiceAmount" />'
					html += '<input type="text" value="" class="text-right invoiceAmountFix" style="width: 100px" readonly /></td>'
					html += '<td><input name="imInvoiceDtlList[' + (rowNum - 1) + '].tax" maxlength="11" type="hidden" value="" class="text-right tax" />'
					html += '<input type="text" value="" class="text-right taxFix" style="width: 100px" /></td>'
					html += '<td><input name="imInvoiceDtlList[' + (rowNum - 1) + '].invoiceDate" type="text" readonly="readonly" maxlength="10" class="Wdate" style="width: 100px" value="" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'});"></td>'
					html += '<td><input name="imInvoiceDtlList[' + (rowNum - 1) + '].invoiceNo" maxlength="50" type="text" value="" class="input-medium" /></td>'
					/* html += '<td>' + ${fns:toJson(soOrder.imInvoice.invoiceTitle)} + '</td>' */
					html += '<td>' + $(".invoiceTitle").val() + '</td>'
					html += '<td><input name="imInvoiceDtlList[' + (rowNum - 1) + '].expressNo" maxlength="50" type="text" value="" class="input-medium" /></td>'
					html += '<td><input name="imInvoiceDtlList[' + (rowNum - 1) + '].expressCompany" maxlength="100" type="text" value="" class="input-medium" /></td>'
					html += '<td></td>'
					html += '</tr>'

				$("#iminvoiceDtlInfoTable>tbody").append(html).find("tr.empty").remove();
					
				/* if ($("#iminvoiceDtlInfoTable>tbody").find("tr").length != 0) {
					$("#iminvoiceDtlInfoTable").addClass("table-overflow-invoice");
				} */
			});
 			
 			function madeSelectList(name, selectList, type) {
 				
 				var html ='';
 				if (type == '1') {
 					html += '<td><select class="lineNo input-mini required" name="' + name + '">'
 				} else {
 					html += '<td><select class="required" name="' + name + '">'
 				}
 				html += '<option value=""></option>'
 				for (var i in selectList) {
 					if (type == '1') {

 						/* if ((selectList[i].packageMertiralNo == null || selectList[i].packageMertiralNo == "")
 								|| (selectList[i].packageMertiralNo != null && selectList[i].packageMertiralNo != "" && selectList[i].materialType == '1')) { */

 	 	 					html += '<option value="' + selectList[i].lineNo + '">' + selectList[i].lineNo + '</option>'
 						/* } */
 					} else {

 	 					html += '<option value="' + selectList[i].value + '">' + selectList[i].label + '</option>'
 					}
 				}
 				html += '</select></td>'
 				
 				return html;
 			}
 			
 			function getSelectLable(key, selectList) {

 				var label = '';
 				for (var i in selectList) {

 					if (key == selectList[i].value) {
 						label = selectList[i].label
 						//label = '<input name="" type="text" value="' + selectList[i].label + '" style="width: 100%" readonly />'
 						return label;
 					}
 				}
 				
 				return label;
 			}
			
			// 收款管理删除行
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
				
				if ($tbody.find("tr").length == 0) {
					var clmCount = $tbody.parent("table").find("thead>tr>th").length;
					var emptyRow = '<tr class="empty"><td class="text-center" colspan="' + clmCount + '">点击添加按钮添加数据</td></tr>';
					$tbody.append(emptyRow);
				}
			});

			// 发票管理删除行
			$(document).on("click", "table .delInvoiceRow", function() {
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
				
				if ($tbody.find("tr").length == 0) {
					var clmCount = $tbody.parent("table").find("thead>tr>th").length;
					var emptyRow = '<tr class="empty"><td class="text-center" colspan="' + clmCount + '">点击添加按钮添加数据</td></tr>';
					$tbody.append(emptyRow);
					/* $("#iminvoiceDtlInfoTable").removeClass("table-overflow-invoice"); */
				}
			});
			
			// 删除合同明细行
			$(document).on("click", "table .delSoOrderDtlRow", function() {
				var editPackageMertiralNo = $(this).parents("tr").find("input.editPackageMertiralNo").val();
				var editEndCustomerId = $(this).parents("tr").find("input.editEndCustomerId").val();

				var $tbody = $(this).parents("tbody");
				if (editPackageMertiralNo == null || editPackageMertiralNo == "") {
					$(this).parents("tr").remove();
				} else {
					$tbody.find("tr").each(function(index){
		    			$(this).find("input.editPackageMertiralNo").each(function() {
		    				if (editPackageMertiralNo == $(this).val() && editEndCustomerId == $(this).parents("tr").find("input.editEndCustomerId").val()) {
		    					$(this).parents("tr").remove();
		    				}
		    			});
		   			});
				}

				$tbody.find("tr").each(function(index){
					
					if ($(this).find("a").length != 0) {

						$(this).find("td.rowNo").html('<a href="javascript:;" class="editOrderRow link">' + (index+1) + '</a>');
					} else {

						$(this).find("td.rowNo").html(index+1);
					}

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
	    			});; 
	   			});
				
				if ($tbody.find("tr").length == 0) {
					var clmCount = $tbody.parent("table").find("thead>tr>th").length;
					var emptyRow = '<tr class="empty"><td class="text-center" colspan="' + clmCount + '">点击添加按钮添加数据</td></tr>';
					$tbody.append(emptyRow);
					/* $("#orderDtlTable").removeClass("table-overflow-order"); */
				}
			});
			
			// 合同明细行变更事件
			$(document).on("change", "select.lineNo", function(e) {
				var $lineNo = $(this);
				var $row = $lineNo.parent().parent();
				var lineNo = $lineNo.val();
				var $materialName = $lineNo.parent().parent().find("input.materialName");
				var $unitPrice = $lineNo.parent().parent().find("input.unitPrice");
				var $unitPriceFix = $lineNo.parent().parent().find("input.unitPriceFix");
				
				if (lineNo == "") {
					$materialName.val("");
					$unitPrice.val("");
					$unitPriceFix.val("");
				} else {
					var soOrderDtlList = ${fns:toJson(soOrder.soOrderDtlList)};

					for (var i in soOrderDtlList) {
						if (soOrderDtlList[i].lineNo == lineNo) {

							$materialName.val(soOrderDtlList[i].materialName);
							$unitPrice.val(soOrderDtlList[i].unitPrice);
							$unitPriceFix.val(toThousands(soOrderDtlList[i].unitPrice));
						}
					}
					//$materialName.val(soOrderDtlList[lineNo - 1].materialName);
					//$unitPrice.val(soOrderDtlList[lineNo - 1].unitPrice);
					
					$row.find("input.num").trigger("change");
				}
				//e.choice
			});

			// 数量、单价change事件绑定
			$(document).on("change", "input.num,input.unitPrice", function() {
				var _this = $(this);
				var $row = _this.parent().parent();
				var $invoiceAmount = $row.find("input.invoiceAmount");
				var $tax = $row.find("input.tax");
				var $invoiceAmountFix = $row.find("input.invoiceAmountFix");
				var $taxFix = $row.find("input.taxFix");
				var num = $row.find("input.num").val();
				var unitPrice = $row.find("input.unitPrice").val();
				
				if (num == "" || unitPrice == "") {
					$invoiceAmount.val("");
					$invoiceAmountFix.val("");
					$tax.val("");
					$taxFix.val("");
					return false;
				}
				$invoiceAmount.val(invoiceAmount(num, unitPrice).toFixed(2));
				$invoiceAmountFix.val(toThousands($invoiceAmount.val()));
				$tax.val(tax($invoiceAmount.val(), 1.17, 0.17).toFixed(2));
				$taxFix.val(toThousands($tax.val()));

			});

			// 税金change事件绑定
			$(document).on("change", "input.taxFix", function() {
				var _this = $(this);
				var $row = _this.parent().parent();
				var $tax = $row.find("input.tax");
				
				if (_this.val() == null || _this.val() == "") {
					$tax.val("");
				} else {
					$tax.val(_this.val().trim().replace(/,/g, ""));
				}

			});

			// 数量、单价change事件绑定
			$(document).on("change", "input.modalNum,input.modalUnitPriceFix", function() {

				var $invoiceAmount = $("input.modalTotalAmount");
				var num = $("input.modalNum").val();
				var $unitPrice = $("input.modalUnitPrice");
				var unitPriceFix = $("input.modalUnitPriceFix").val();
				var $invoiceAmountFix = $("input.modalTotalAmountFix");
				
				if (unitPriceFix == null || unitPriceFix == "") {
					$unitPrice.val("");
				} else {
					$unitPrice.val(unitPriceFix.trim().replace(/,/g, ""));
				}
				
				unitPrice = $unitPrice.val();
				
				if (num == "" || unitPrice == "") {
					$invoiceAmount.val("");
					$invoiceAmountFix.val("");
					return false;
				}
				$invoiceAmount.val(invoiceAmount(num, unitPrice).toFixed(2));
				$invoiceAmountFix.val(toThousands($invoiceAmount.val()));

			});
			
			function invoiceAmount(arg1, arg2) {  
			    var m=0, s1=arg1.toString(), s2=arg2.toString();  
			    try { m += s1.split(".")[1].length } catch(e){}  
			    try { m += s2.split(".")[1].length } catch(e){}  
			    return Number(s1.replace(".","")) * Number(s2.replace(".","")) / Math.pow(10, m);
			}
			
			function tax(arg1, arg2, arg3) {  
			    var m1=0, m2=0, m3=0, s1=arg1.toString(), s2=arg2.toString(), s3=arg3.toString();
			    
			    try { m1 = s1.split(".")[1].length } catch(e){}  
			    try { m2 = s2.split(".")[1].length } catch(e){}  
			    
			    var m4 = Number(s1.replace(".","")) / Number(s2.replace(".","")) * Math.pow(10, m2-m1);
			    var s4 = m4.toString();
			    
			    try { m3 += s4.split(".")[1].length } catch(e){}  
			    try { m3 += s3.split(".")[1].length } catch(e){}  
			    return Number(s4.replace(".","")) * Number(s3.replace(".","")) / Math.pow(10, m3);
			}

			// 收款管理的收款金额change事件绑定
			$(document).on("change", "input.totalAmountSogFix", function() {
				var _this = $(this);
				var $row = _this.parent().parent();
				var $totalAmountSog = $row.find("input.totalAmountSog");
				
				if (_this.val() == null || _this.val() == "") {
					$totalAmountSog.val("");
				} else {
					$totalAmountSog.val(_this.val().trim().replace(/,/g, ""));
				}

			});
			
			// 合同明细输出
			$("#export").on("click", function() {
				var fHtml = '<form method="get" action="' + '${ctx}' + '/sd/soOrder/orderDtl/export">';
				    fHtml += '<input type="hidden" name="orderId" value="' + '${soOrder.id}' + '">';
				    fHtml += '</form>';
				$(fHtml).appendTo('body').submit().remove();
			});
			
			// 自动获取开票信息
			$("#autoGetIminvoice").on("click", function() {
				
				var customerId = $("input.customerId").val()
				
				if (customerId != "" && customerId != null) {

					// 信息取得
					$.ajax({
				    	url: "${ctx}" + "/sd/soOrder/getCustomerInfo",
				        type: "get",
				        async: false,
				        data: {"customerId":customerId},
				        dataType: "json",
				        success: function (data) {
				        	
				        	var customerInfo = data.customerInfo;
				        	
				        	$(".invoiceType").val(customerInfo.invoiceType);
				        	$(".invoiceType").trigger("change");
				        	$(".invoiceTitle").val(customerInfo.invoiceTitle);
				        	$(".taxpayerIdentNo").val(customerInfo.taxpayerIdentNo);
				        	$(".depositBank").val(customerInfo.depositBank);
				        	$(".bankAccount").val(customerInfo.bankAccount);
				        	$(".invoiceAddress").val(customerInfo.invoiceAddress);
				        	$(".telephone").val(customerInfo.telephone);
				        },
				        error: function (msg) {
				        }
				    });
				}
			});

 			/* // 是否退货选择时，行作废必须选择
			$("#ifReturn").on("change", function() {
				
				if($(this).attr("checked") == "checked") {
					$("#ifEffective").attr("checked","true"); 
				}
			});

 			// 行作废取消时，是否退货取消
			$("#ifEffective").on("change", function() {
				
				if($(this).attr("checked") != "checked") {
					$("#ifReturn").removeAttr("checked");
				}
			}); */
			
			// 收款信息保存
			$("#saveGatheringInfo").on("click", function() {
				if (!$("#inputForm").valid()) {
					return false;
				} else {
		            // 异步数据提交
		            $.ajax({
		                type: "post",
				        async: false,
		                url: "${ctx}" + "/sd/soOrder/saveGatheringInfo",
		                data: $("#inputForm").serialize(),
		                success: function(oData, oStatus) {
				           	if (oData.success) {

			    				var fHtml = '<form action="' + '${ctx}' + '/sd/soOrder/form">';
							    fHtml += '<input type="hidden" name="id" value="' + '${soOrder.id}' + '">';
							    fHtml += '</form>';
								$(fHtml).appendTo('body').submit().remove();
				           	}
		                },
		                error: function(oData, oStatus, eErrorThrow) {
		                }
		            });
				}
			});
			
			// 提交申请以及保存
			$("input[type='button'].opt").on("click", function() {
				if ($(this).val() == "退回") {
					var r=confirm("确认要退回给申请者？请确认！");
					if (r != true) {
						return false;
					}
				}
				if (!$("#inputForm").valid()) {
					return false;
				} else {
					$("#inputForm").append('<input type="hidden" name="opt" value="' + $(this).val() + '">')
		            // 异步数据提交
		            $.ajax({
		                type: "post",
				        async: false,
		                //url: "${ctx}" + "/sd/soOrder/soOrderInfoValidator",
		                url: "${ctx}" + "/sd/soOrder/orderInfo/save",
		                data: $("#inputForm").serialize(),
		                success: function(oData, oStatus) {
		                	if (oData.success) {
		                		//$("#inputForm").submit();
				           		showMessage("操作成功");
				           		if (oData.urlType == '0') {

					           		window.location.href="${ctx}//sd/soOrder/form?id=" + oData.orderId;
				           		} else if (oData.urlType == '1') {

					           		window.location.href="${ctx}/act/task/todo";
				           		}
		                	}
		                },
		                error: function(oData, oStatus, eErrorThrow) {
		                }
		            });
				}
			});
		});
	</script>

</head>
<body>
	<form:form id="inputForm" modelAttribute="soOrder" action="${ctx}/sd/soOrder/orderInfo/save" method="post" class="form-search" >
	    <form:hidden path="id"/>
	    <form:hidden path="procInsId"/>
		<form:hidden path="dataType"/>
	    
	    <c:if test="${!empty soOrder.workflowStatus}">
	    <form:hidden path="workflowStatus"/>
	    <form:hidden path="deliverWorkflowStatus"/>
	    </c:if>
	    <c:if test="${!empty soOrder.receiveStatus}">
	    <form:hidden path="receiveStatus"/>
	    </c:if>
	    <c:if test="${!empty soOrder.invoiceStatus}">
	    <form:hidden path="invoiceStatus"/>
	    </c:if>
	    <c:if test="${!empty soOrder.deliverStatus}">
	    <form:hidden path="deliverStatus"/>
	    </c:if>
	    <c:if test="${!empty soOrder.updateDate}">
	    	<input type="hidden" name="updateDate" value="<fmt:formatDate value="${soOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
	    </c:if>
	    <form:hidden path="businessOppNo"/>

		<form:hidden path="act.taskId"/>
		<form:hidden path="act.procInsId"/>

	    <form:hidden path="imInvoice.id"/>
	    <form:hidden path="imInvoice.procInsId"/>
	    <form:hidden path="imInvoice.orderNo"/>
		<form:hidden path="imInvoice.workflowStatus"/>
	    <c:if test="${!empty soOrder.imInvoice.updateDate}">
	    	<input type="hidden" name="imInvoice.updateDate" value="<fmt:formatDate value="${soOrder.imInvoice.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
	    </c:if>
			<% boolean readonlyFlag = true; %>
			<% boolean imInvoiceReadonlyFlag = true; %>
			<% boolean imInvoiceAddReadonlyFlag = true; %>
			<% boolean imInvoiceDisableFlag = true; %>
			<% boolean gatheringReadonlyFlag = true; %>
			<%-- <shiro:hasPermission name="sd:soOrder:edit"> --%>
			<c:if test="${soOrder.dataType == 'order'}">
				<c:if test="${empty soOrder.workflowStatus || (soOrder.createBy == user.id && (soOrder.workflowStatus == '30' || soOrder.workflowStatus == '40')) || (soOrder.workflowStatus == '50' && ((empty soOrder.imInvoice || empty soOrder.imInvoice.id) || (!empty soOrder.imInvoice && soOrder.imInvoice.workflowStatus == '50')) && (empty soOrder.deliverWorkflowStatus || soOrder.deliverWorkflowStatus == '50') && !empty soOrder.businessOppNo)}">
				<% readonlyFlag = false; %>
				</c:if>
			</c:if>
			<%-- </shiro:hasPermission> --%>
			<%-- <shiro:hasPermission name="sd:imInvoice:edit"> --%>
			<c:if test="${soOrder.dataType == 'invoice'}">
				<c:if test="${empty soOrder.imInvoice.workflowStatus || (soOrder.imInvoice.createBy == user.id && (soOrder.imInvoice.workflowStatus == '30' || soOrder.imInvoice.workflowStatus == '40'))}">
					<% imInvoiceReadonlyFlag = false; %>
				</c:if>
				<c:if test="${empty soOrder.imInvoice.workflowStatus || (soOrder.imInvoice.createBy == user.id && (soOrder.imInvoice.workflowStatus == '30' || soOrder.imInvoice.workflowStatus == '40')) || soOrder.imInvoice.workflowStatus == '50'}">
					<% imInvoiceAddReadonlyFlag = false; %>
				</c:if>
			</c:if>
			<%-- </shiro:hasPermission> --%>
			<shiro:hasPermission name="cm:manager:edit">
				<c:if test="${empty soOrder.dataType}">
					<% gatheringReadonlyFlag = false; %>
					<c:if test="${soOrder.workflowStatus == '50' && ((empty soOrder.imInvoice || empty soOrder.imInvoice.id) || (!empty soOrder.imInvoice && soOrder.imInvoice.workflowStatus == '50')) && (empty soOrder.deliverWorkflowStatus || soOrder.deliverWorkflowStatus == '50')}">
						<% readonlyFlag = false; %>
					</c:if>
					<c:if test="${soOrder.workflowStatus == '50' && !empty soOrder.imInvoice && soOrder.imInvoice.workflowStatus == '50'}">
						<% imInvoiceReadonlyFlag = false; %>
						<% imInvoiceAddReadonlyFlag = false; %>
					</c:if>
					<c:if test="${empty soOrder.imInvoice || empty soOrder.imInvoice.id}">
						<% imInvoiceDisableFlag = false; %>
					</c:if>
				</c:if>
			</shiro:hasPermission>
			<h3 class="text-center page-title">合同录入</h3>
			<div class="group-box group-box-first" style="height:auto;">
			    <ul class="ul-form">
			        <li>
			            <label>合同号：</label>
			            <form:input path="orderNo" class="input-medium" type="text" value="${soOrder.orderNo}" readonly="true"/>
			        </li>
			        <li>
			            <label>录入日期：</label>
			            <input name="createDate" class="input-small" type="text" value="<fmt:formatDate value="${soOrder.createDate}" pattern="yyyy-MM-dd"/>" readonly />
			        </li>
			        <li>
			            <label>录入人：</label>
			            <form:input path="createBy.name" class="input-small" type="text" value="${soOrder.createBy.name}" readonly="true"/>
			        </li>
			        <li>
			            <label><span class="help-inline"><font color="red">*</font></span>订立日期：</label>
			            <% if (!readonlyFlag) { %>
			            <input id="orderDate" name="orderDate" type="text" readonly maxlength="10" class="input-small Wdate required" value="<fmt:formatDate value="${soOrder.orderDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			            <% } else { %>
			            <input id="orderDate" name="orderDate" type="text" readonly maxlength="10" class="input-small" value="<fmt:formatDate value="${soOrder.orderDate}" pattern="yyyy-MM-dd"/>"/>
			            <% } %>
			        </li>
				    
				     <% if (!readonlyFlag) { %>
				        <li>
				            <label><span class="help-inline"><font color="red">*</font></span>业务员：</label>
				            <form:input path="employeeNo" class="remote employee input-medium required" type="text" data-show="text" data-type="10,20"/>
				            <%-- <form:select path="employeeNo" class="input-medium required" >
				                <form:option value="" label=""/>
				                <form:options items="${fns:getSqlDictList('dict_employee')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				            </form:select> --%>
				        </li>
				        <li>
				            <label><span class="help-inline"><font color="red">*</font></span>签约地：</label>
				            <form:select path="city" class="input-small required">
				                <form:option value="" label=""/>
				                <form:options items="${fns:getSqlDictList('dict_city_01')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				            </form:select>
				        </li>
				    <% } else { %>
				        <li>
				            <label><span class="help-inline"><font color="red">*</font></span>业务员：</label>
				            <form:input path="employeeNo" class="remote employee input-medium" type="text" data-show="text" data-type="10,20" readonly="true"/>
				            <%-- <form:select path="employeeNo" class="input-medium required" disabled="true">
				                <form:option value="" label=""/>
				                <form:options items="${fns:getSqlDictList('dict_employee')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				            </form:select> --%>
				        </li>
				        <li>
				            <label><span class="help-inline"><font color="red">*</font></span>签约地：</label>
				            <form:select path="city" class="input-small required" disabled="true">
				                <form:option value="" label=""/>
				                <form:options items="${fns:getSqlDictList('dict_city_01')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				            </form:select>
				        </li>
				    <% } %>
			        <li>
			            <label><span class="help-inline"><font color="red">*</font></span>签约方：</label>
			            <input name="customerId" type="text" value="${soOrder.customerId}" maxlength="50" class="remote customer required customerId input-small" <%=readonlyFlag?"readonly":"" %>/>
			        </li>
			        <li>
			            <input name="customerChName" type="text" value="${soOrder.customerChName}" class="customerChName" style="width:270px;" readonly/>
			        </li>
			        <c:if test="${!empty soOrder.businessOppNo}">
				     <% if (!readonlyFlag) { %>
				        <li>
				            <label><span class="help-inline"><font color="red">*</font></span>合同分类：</label>
				            <form:select path="orderType" class="input-small required orderType">
				                <form:option value="" label=""/>
				                <form:options items="${fns:getDictList('DM0008')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				            </form:select>
				        </li>
				    <% } else { %>
				        <li>
				            <label><span class="help-inline"><font color="red">*</font></span>合同分类：</label>
				            <form:select path="orderType" class="input-small required orderType" disabled="true">
				                <form:option value="" label=""/>
				                <form:options items="${fns:getDictList('DM0008')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				            </form:select>
				        </li>
				    <% } %>
				    </c:if>
				    <c:if test="${empty soOrder.businessOppNo}">
				        <li>
				            <label><span class="help-inline"><font color="red">*</font></span>合同分类：</label>
				            <form:select path="orderType" class="input-small required orderType" disabled="true">
				                <form:option value="" label=""/>
				                <form:options items="${fns:getDictList('DM0008')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				            </form:select>
				        </li>
				    </c:if>
				    
			     <% if (!readonlyFlag) { %>
			        <li>
			            <label><span class="help-inline"><font color="red">*</font></span>销售方式：</label>
			            <form:select path="priceSystem" class="input-small required">
			                <form:option value="" label=""/>
			                <form:options items="${fns:getDictList('DM0005')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			            </form:select>
			        </li>
			        <li>
			            <label>币种：</label>
			            <form:select path="currency" class="input-mini">
			                <form:option value="" label=""/>
			                <form:options items="${fns:getDictList('DM0009')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			            </form:select>
			        </li>
			     <% } else { %>
			        <li>
			            <label><span class="help-inline"><font color="red">*</font></span>销售方式：</label>
			            <form:select path="priceSystem" class="input-small required" disabled="true">
			                <form:option value="" label=""/>
			                <form:options items="${fns:getDictList('DM0005')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			            </form:select>
			        </li>
			        <li>
			            <label>币种：</label>
			            <form:select path="currency" class="input-mini" disabled="true">
			                <form:option value="" label=""/>
			                <form:options items="${fns:getDictList('DM0009')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			            </form:select>
			        </li>
			     <% } %>
			        <li>
			            <label>汇率：</label>
			            <input id="exchangeRate" name="exchangeRate" value="<fmt:formatNumber value="${soOrder.exchangeRate}" pattern="#.########" maxFractionDigits="8"/>" maxlength="13"  pattern="^[0-9]{1,4}(\.[0-9]{1,8})?$"  class="input-small text-right exchangeRate" type="text" <%=readonlyFlag?"readonly":"" %>/>
			        </li>
			        <li>
			            <label>佣金/费用：</label>
			            <input id="commission" name="commission" value="<fmt:formatNumber value="${soOrder.commission}" pattern="#.##"/>" class="input-mini text-right commission" type="hidden" maxlength="11" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
			            <input value="<fmt:formatNumber value="${soOrder.commission}" pattern="#,##0.00"/>" onblur="numToStr(this);" onfocus="strToNum(this);" class="input-mini text-right commissionFix" type="text" <%=readonlyFlag?"readonly":"" %>/>
			        </li>
				    
			        <li>
			            <label><span class="help-inline"><font color="red">*</font></span>支付条件：</label>
			            <% if (!readonlyFlag) { %>
				            <form:select path="paymaentCon" class="input-small paymaentCon required">
				                <form:option value="" label=""/>
				                <form:options items="${fns:getDictList('DM0057')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				            </form:select>
			            <% } else { %>
				            <form:select path="paymaentCon" class="input-small paymaentCon" disabled="true">
				                <form:option value="" label=""/>
				                <form:options items="${fns:getDictList('DM0057')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				            </form:select>
			            <% } %>
			        </li>
			        <li>
			            <label>合同状态：</label>
			            <input class=input-small type="text" value="${fns:getDictLabel(soOrder.workflowStatus, 'DM0043', '')}" readonly />
			        </li>
			        <li>
			            <label>收款状态：</label>
			            <input class="input-mini" type="text" value="${fns:getDictLabel(soOrder.receiveStatus, 'DM0011', '')}" readonly />
			        </li>
			        <li>
			            <label>开票状态：</label>
			            <input class="input-small" type="text" value="${fns:getDictLabel(soOrder.invoiceStatus, 'DM0012', '')}" readonly />
			        </li>
				    
			        <li class="full-width">
			        	<label>支付条件/备注：</label>
			        <% if (!readonlyFlag) { %>
			            <form:textarea path="conditionRemarks" value="${soOrder.conditionRemarks}" rows="2" class="fill-right conditionRemarks" maxlength="300"/>
			        <% } else { %>
			            <form:textarea path="conditionRemarks" value="${soOrder.conditionRemarks}" rows="2" class="fill-right conditionRemarks" maxlength="300" disabled="true"/>
			        <% } %>
			        </li>
				    
			        <li class="full-width">
			            <label>备注：</label>
			            <% if (!readonlyFlag) { %>
			            	<form:textarea path="newRemarks" value="${soOrder.newRemarks}" rows="2" class="fill-right" maxlength="300"/>
			            <% } else { %>
			            	<form:textarea path="newRemarks" value="${soOrder.newRemarks}" rows="2" class="fill-right" maxlength="300" disabled="true"/>
			            <% } %>
			        </li>
			    </ul>
			</div>
			<div class="group-box">
			    <div class="group-header" >
			        <strong class="group-title">合同明细</strong>
			        <c:if test="${empty soOrder.soOrderDtlList || empty soOrder.id}">
			            <input id="export" class="btn btn-primary" type="button" value="输出报价单" disabled>
			        </c:if>
			        <c:if test="${!empty soOrder.soOrderDtlList && !empty soOrder.id}">
			            <input id="export" class="btn btn-primary" type="button" value="输出报价单">
			        </c:if>
					<div class="pull-right">
						<label>总金额：</label>
						<label class="text-left"><fmt:formatNumber value="${soOrder.orderDtlTotalAmount}" pattern="#,##0.00"/></label>
					</div>
			    </div>
				<div style="overflow-x:scroll;">
					<table id="orderDtlTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
							<% if (!readonlyFlag) { %>
								<th class="text-center" width="20px"><a href="javascript:;" id="addOrderRow"><i class="icon-plus-sign"></i></a></th>
							<% } %>
								<th class="text-center">No</th>
								<th class="text-center">明细行号</th>
								<th style="word-break:keep-all;">最终客户</th>
								<th>行业</th>
								<th>具体分类</th>	
								<th>科室/系</th>
								<th>省市</th>
								<th>城市</th>
								<th>地区</th>
								<th>地区负责人</th>
								<th>提成人</th>
								<th>组</th>
								<th>物料号</th>
								<th>物料名称</th>
								<th>数量</th>
								<th>标准价</th>
								<th>单价</th>
								<th>金额</th>
								<th>质保期（年）</th>
								<th>延保（年）</th>
								<th>行作废</th>
								<th>是否退货</th>
							</tr>
						</thead>
						<tbody>
						    <c:choose>
						        <c:when test="${!empty soOrder.soOrderDtlList}"> 
						            <c:forEach var="item" items="${soOrder.soOrderDtlList}" varStatus="status">
									    <tr>
										<form:hidden
											path="soOrderDtlList[${status.index}].id"/>
										<form:hidden
											path="soOrderDtlList[${status.index}].lineNo"
											class="editLineNo"/>
										<form:hidden
											path="soOrderDtlList[${status.index}].endCustomerId"
											class="editEndCustomerId" />
										<form:hidden
											path="soOrderDtlList[${status.index}].customerChName"
											class="editEndCustomerName" />
										<form:hidden
											path="soOrderDtlList[${status.index}].packageMertiralNo"
											class="editPackageMertiralNo" />
										<form:hidden
											path="soOrderDtlList[${status.index}].customerSegmentation"
											class="editCustomerSegmentation" />
										<form:hidden path="soOrderDtlList[${status.index}].region"
											class="editRegion" />
										<form:hidden
											path="soOrderDtlList[${status.index}].responsiblePersonId"
											class="editResponsiblePersonId" />
										<form:hidden
											path="soOrderDtlList[${status.index}].commissionPeisonId"
											class="editCommissionPeisonId" />
										<form:hidden path="soOrderDtlList[${status.index}].materialNo"
											class="editMaterialNo" />
										<form:hidden
											path="soOrderDtlList[${status.index}].materialName"
											class="editMaterialName" />
										<form:hidden path="soOrderDtlList[${status.index}].num"
											class="editNum" />
										<form:hidden
											path="soOrderDtlList[${status.index}].deliverNumMax"
											class="editDeliverNumMax" />
										<form:hidden
											path="soOrderDtlList[${status.index}].invoiceNumMax"
											class="editInvoiceNumMax" />
										<form:hidden
											path="soOrderDtlList[${status.index}].deliverNum"
											class="editDeliverNum" />
										<form:hidden
											path="soOrderDtlList[${status.index}].invoiceNum"
											class="editInvoiceNum" />
										<form:hidden path="soOrderDtlList[${status.index}].unitPrice"
											class="editUnitPrice" />
										<form:hidden
											path="soOrderDtlList[${status.index}].warrantyPeriod"
											class="editWarrantyPeriod" />
										<form:hidden
											path="soOrderDtlList[${status.index}].extendedWarrPeriod"
											class="editExtendedWarrPeriod" />
										<form:hidden
											path="soOrderDtlList[${status.index}].ifEffective"
											class="editIfEffective" />
										<form:hidden path="soOrderDtlList[${status.index}].ifReturn"
											class="editIfReturn" />
										<form:hidden path="soOrderDtlList[${status.index}].industry" />
										<form:hidden
											path="soOrderDtlList[${status.index}].customerDiff" />
										<form:hidden path="soOrderDtlList[${status.index}].province" />
										<form:hidden path="soOrderDtlList[${status.index}].city" />
										<form:hidden path="soOrderDtlList[${status.index}].organize" 
											class="editOrganize" />
										<form:hidden
											path="soOrderDtlList[${status.index}].standardPrice" 
											class="editStandardPrice"/>
										<form:hidden
											path="soOrderDtlList[${status.index}].totalAmount" />
										<% if (!readonlyFlag) { %>
									        <td class="text-center" width="20px">
									           <c:if test="${(!empty item.packageMertiralNo && item.materialType == '1') || empty item.packageMertiralNo}">
									           	<c:if test="${(empty item.deliverNumMax || item.deliverNumMax == '0') && (empty item.invoiceNumMax || item.invoiceNumMax == '0')}">
									           		<a href="javascript:;" class="delSoOrderDtlRow"><i class="icon-minus-sign"></i></a>
									           	</c:if>
									           </c:if>
									        </td>
									        <c:if test="${(!empty item.packageMertiralNo && item.materialType == '1') || empty item.packageMertiralNo}">
									        	<td class="text-center rowNo"><a href="javascript:;" class="editOrderRow link">${status.index + 1}</a></td>
									        </c:if>
									        <c:if test="${!empty item.packageMertiralNo && item.materialType != '1'}">
									        	<td class="text-center rowNo">${status.index + 1}</td>
									        </c:if>
									    <% } %>
									    <% if (readonlyFlag) { %>
									        <td class="text-center rowNo">${status.index + 1}</td>
									    <% } %>
									    	<td class="text-center">${item.lineNo}</td>
									        <td>${item.customerChName}</td>
									        <td>${fns:getDictLabel(item.industry, 'DM0002', '')}</td>
									        <td>${fns:getDictLabel(item.customerDiff, 'DM0003', '')}</td>
									        <td>${fns:getDictLabel(item.customerSegmentation, 'DM0039', '')}</td>
									        <td>${item.provinceName}</td>
									        <td>${item.cityName}</td>
									        <td>${fns:getDictLabel(item.region, 'DM0049', '')}</td>
									        <td>${item.responsiblePersonName}</td>
									        <td>${item.commissionPeisonName}</td>
									        <td>${item.organizeName}</td>
									        <td>${item.materialNo}</td>
									        <td>${item.materialName}</td>
									        <td class="text-right">${item.num}</td>
									        <td class="text-right"><fmt:formatNumber value="${item.standardPrice}" pattern="#,##0.00"/></td>
									        <td class="text-right"><fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/></td>
									        <td class="text-right "><fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/></td>
									        <td class="text-right ">${item.warrantyPeriod}</td>
									        <td class="text-right ">${item.extendedWarrPeriod}</td>
									        <c:if test="${item.ifEffective eq '0' || empty item.ifEffective}">
									        	<td class="text-center"><input type="checkbox" disabled="disabled" /></td>
									        </c:if>
									        <c:if test="${item.ifEffective eq '1'}">
									        	<td class="text-center"><input type="checkbox" disabled="disabled" checked="checked" /></td>
									        </c:if>
									        <c:if test="${item.ifReturn eq '0' || empty item.ifReturn}">
									        	<td class="text-center"><input type="checkbox" disabled="disabled" /></td>
									        </c:if>
									        <c:if test="${item.ifReturn eq '1'}">
									        	<td class="text-center"><input type="checkbox" disabled="disabled" checked="checked" /></td>
									        </c:if>
									    </tr>
						            </c:forEach> 
						        </c:when>
						    </c:choose>
						</tbody>	
					</table>
				</div>
			</div>
			<c:if test="${!empty soOrder.orderNo}">
				<div class="group-box">
				    <div class="group-header" >
				        <strong class="group-title">收款管理</strong>
						<input id=saveGatheringInfo class="btn btn-primary" type="button" value="保存" <%=gatheringReadonlyFlag?"disabled":"" %>>
						<div class="pull-right">
							<label>总金额：</label>
							<label class="text-left"><fmt:formatNumber value="${soOrder.gatheringTotalAmount}" pattern="#,##0.00"/></label>
						</div>
				    </div>
		
					<table id="soGatheringInfoTable" class="table table-striped table-bordered table-condensed" >
						<thead>
							<tr>
							<% if (!gatheringReadonlyFlag) { %>
								<th class="text-center" width="20px"><a href="javascript:;" id="addSoGatheringInfoRow"><i class="icon-plus-sign"></i></a></th>
							<% } %>
								<th>收款方式</th>
								<th>收款金额</th>
								<th>收款到期日</th>	
								<th>收款抬头</th>
								<th>实际收款日期</th>
								<th>支付条件/备注</th>
							</tr>
						</thead>
						<tbody>
						    <c:choose>
						        <c:when test="${!empty soOrder.soGatheringInfoList}"> 
						            <c:forEach var="item" items="${soOrder.soGatheringInfoList}" varStatus="status">
									    <tr>
									           <form:hidden path="soGatheringInfoList[${status.index}].id"/>
									    <% if (!gatheringReadonlyFlag) { %>
									    	<td class="text-center" width="20px">
									           <a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a>
									        </td>
									        <td>
									        	<select name="soGatheringInfoList[${status.index}].gatheringType"  class="input-medium"  style="width: 100%">
									        		<option value=""></option>
													<c:forEach items="${fns:getDictList('DM0047')}" var="gatheringTypes">
														<option value="${gatheringTypes.value}" ${gatheringTypes.value==item.gatheringType?'selected':''}>${gatheringTypes.label}</option>
													</c:forEach>
									        	</select>
				            				</td>
									    <% } else { %>
									        <td>
									        	<form:hidden path="soGatheringInfoList[${status.index}].gatheringType"/>
									        	<select name="soGatheringInfoList[${status.index}].gatheringType"  class="input-medium"  style="width: 100%" disabled="disabled">
									        		<option value=""></option>
													<c:forEach items="${fns:getDictList('DM0047')}" var="gatheringTypes">
														<option value="${gatheringTypes.value}" ${gatheringTypes.value==item.gatheringType?'selected':''}>${gatheringTypes.label}</option>
													</c:forEach>
									        	</select>
				            				</td>
				            			<% } %>
				            				<td>
				            					<input name="soGatheringInfoList[${status.index}].totalAmount" type="hidden" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#.##"/>" maxlength="11" class="text-right required totalAmountSog" />
				            					<input type="text" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/>" onblur="numToStr(this);" onfocus="strToNum(this);" class="text-right required totalAmountSogFix" style="width: 100%" <%=gatheringReadonlyFlag?"readonly":"" %>/>
				            				</td>
				            				<td>
				            				<% if (!gatheringReadonlyFlag) { %>
				            					<input name="soGatheringInfoList[${status.index}].expDateFrom" type="text" value="<fmt:formatDate value="${item.expDateFrom}" pattern="yyyy-MM-dd"/>" readonly  style="width: 100%" class="Wdate required" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				            				<% } else { %>
				            					<input name="soGatheringInfoList[${status.index}].expDateFrom" type="text" value="<fmt:formatDate value="${item.expDateFrom}" pattern="yyyy-MM-dd"/>" readonly  style="width: 100%"/>
				            				<% } %>
				            				</td>
				            				<td>
				            					<input name="soGatheringInfoList[${status.index}].invoiceTitle" maxlength="100" class="input-medium" type="text" value="${item.invoiceTitle}"  style="width: 100%" <%=gatheringReadonlyFlag?"readonly":"" %>/>
				            				</td>
				            				<td>
				            				<% if (!gatheringReadonlyFlag) { %>
				            					<input name="soGatheringInfoList[${status.index}].actDateFrom" type="text" value="<fmt:formatDate value="${item.actDateFrom}" pattern="yyyy-MM-dd"/>" readonly  style="width: 100%" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				            				<% } else { %>
				            					<input name="soGatheringInfoList[${status.index}].actDateFrom" type="text" value="<fmt:formatDate value="${item.actDateFrom}" pattern="yyyy-MM-dd"/>" readonly  style="width: 100%" />
				            				<% } %>
				            				</td>
				            				<td>
				            				<% if (!gatheringReadonlyFlag) { %>
				            					<input name="soGatheringInfoList[${status.index}].newRemarks" maxlength="300" class="input-medium" type="text" value="${item.newRemarks}"  style="width: 100%" />
				            				<% } else { %>
				            					<input name="soGatheringInfoList[${status.index}].newRemarks" class="input-medium" type="text" value="${item.newRemarks}"  style="width: 100%" readonly/>
				            				<% } %>
				            				</td>
									    </tr>
						            </c:forEach> 
						        </c:when>
						    </c:choose>
						</tbody>	
					</table>
				</div>
			</c:if>
			<!-- 财务专用 -->
			<% if (imInvoiceDisableFlag) { %>
			<c:if test="${soOrder.workflowStatus == '50'}">
				<%-- <shiro:hasPermission name="sd:imInvoice:edit"> --%>
				<c:if test="${soOrder.dataType == 'invoice' || empty soOrder.dataType}">
					<div class="group-box">
					    <div class="group-header" >
					        <strong class="group-title">开票管理</strong>
					        <input id="autoGetIminvoice" class="btn btn-primary" type="button" value="自动获取开票信息" <%=imInvoiceReadonlyFlag?"disabled":"" %>>
					        <span class="help-inline"><font color="red">申请人只需要1.填写开票头信息，2.选择合同明细行后填数量，不要填写其他</font></span>
					    </div>
					    <ul class="ul-form">
					        <li>
					            <label><span class="help-inline"><font color="red">*</font></span>发票类型：</label>
					            <% if (!imInvoiceAddReadonlyFlag) { %>
						            <form:select path="imInvoice.invoiceType" class="input-medium required invoiceType">
						                <form:option value="" label=""/>
						                <form:options items="${fns:getDictList('DM0004')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						            </form:select>
					            <% } else { %>
						            <form:select path="imInvoice.invoiceType" class="input-medium invoiceType" disabled="true">
						                <form:option value="" label=""/>
						                <form:options items="${fns:getDictList('DM0004')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						            </form:select>
					            <% } %>
					        </li><li class="clearfix"></li>
					        <% if (!imInvoiceAddReadonlyFlag) { %>
						        <li>
						            <label><span class="help-inline"><font color="red">*</font></span>开票抬头：</label>
						            <form:input path="imInvoice.invoiceTitle" maxlength="100" class="input-xxlarge required invoiceTitle" type="text" value=""/>
						        </li>
						        <li>
						            <label style="width: 100">纳税人识别号：</label>
						            <form:input path="imInvoice.taxpayerIdentNo" maxlength="50" class="input-xxlarge taxpayerIdentNo" type="text" value=""/>
						        </li>
				        		
						        <li>
						            <label>开户行：</label>
						            <form:input path="imInvoice.depositBank" maxlength="100" class="input-xxlarge depositBank" type="text" value=""/>
						        </li>
						        <li>
						            <label>银行账号：</label>
						            <form:input path="imInvoice.bankAccount" maxlength="100" class="input-xxlarge bankAccount" type="text" value=""/>
						        </li>
						        <li>
						            <label>地址：</label>
						            <form:input path="imInvoice.invoiceAddress" maxlength="100" class="input-xxlarge invoiceAddress" type="text" value=""/>
						        </li>
						        <li>
						            <label>电话：</label>
						            <form:input path="imInvoice.telephone" maxlength="50" class="input-medium phone telephone" type="text" value=""/>
						        </li>
					        <% } else { %>
						        <li>
						            <label><span class="help-inline"><font color="red">*</font></span>开票抬头：</label>
						            <form:input path="imInvoice.invoiceTitle" class="input-xxlarge required invoiceTitle" type="text" value="" readonly="true"/>
						        </li>
						        <li>
						            <label style="width: 100">纳税人识别号：</label>
						            <form:input path="imInvoice.taxpayerIdentNo" class="input-xxlarge taxpayerIdentNo" type="text" value="" readonly="true"/>
						        </li>
				        		
						        <li>
						            <label>开户行：</label>
						            <form:input path="imInvoice.depositBank" class="input-xxlarge depositBank" type="text" value="" readonly="true"/>
						        </li>
						        <li>
						            <label>银行账号：</label>
						            <form:input path="imInvoice.bankAccount" class="input-xxlarge bankAccount" type="text" value="" readonly="true"/>
						        </li>
						        <li>
						            <label>地址：</label>
						            <form:input path="imInvoice.invoiceAddress" class="input-xxlarge invoiceAddress" type="text" value="" readonly="true"/>
						        </li>
						        <li>
						            <label>电话：</label>
						            <form:input path="imInvoice.telephone" class="input-medium phone telephone" type="text" value="" readonly="true"/>
						        </li>
					         <% } %>
					        <li class="clearfix"></li><li>
					            <label>取票方式：</label>
					            <% if (!imInvoiceAddReadonlyFlag) { %>
						            <form:select path="imInvoice.ticketMethod" class="input-medium">
						                <form:option value="" label=""/>
						                <form:options items="${fns:getDictList('DM0048')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						            </form:select>
					            <% } else { %>
						            <form:select path="imInvoice.ticketMethod" class="input-medium" disabled="true">
						                <form:option value="" label=""/>
						                <form:options items="${fns:getDictList('DM0048')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						            </form:select>
					            <% } %>
					        </li>
					        <% if (!imInvoiceAddReadonlyFlag) { %>
						        <li>
						            <label>联系电话：</label>
						            <form:input path="imInvoice.repTelephone" maxlength="50" class="input-medium phone" type="text" value=""/>
						        </li><li class="clearfix">
						        <li>
						            <label>收件人：</label>
						            <form:input path="imInvoice.recipients" maxlength="50" class="input-xxlarge" type="text" value=""/>
						        </li>
						        <li>
						            <label>收件地址：</label>
						            <form:input path="imInvoice.address" maxlength="300" class="input-xxlarge" type="text" value=""/>
						        </li>
						        <li class="full-width">
						            <label>开票说明：</label>
						            <form:textarea path="imInvoice.newRemarks" class="fill-right" rows="2" value="" maxlength="300"/>
						        </li>
					         <% } else { %>
						        <li>
						            <label>联系电话：</label>
						            <form:input path="imInvoice.repTelephone" class="input-medium phone" type="text" value="" readonly="true"/>
						        </li>
						        <li>
						            <label>收件人：</label>
						            <form:input path="imInvoice.recipients" class="input-xxlarge" type="text" value="" readonly="true"/>
						        </li><li class="clearfix">
						        <li>
						            <label>收件地址：</label>
						            <form:input path="imInvoice.address" class="input-xxlarge" type="text" value="" readonly="true"/>
						        </li>
						        <li class="full-width">
						            <label>开票说明：</label>
						            <form:textarea path="imInvoice.newRemarks" class="fill-right" rows="2" value="" maxlength="300" readonly="true"/>
						        </li>
					         <% } %>
					     </ul>
					</div>
					<div class="group-box">
					    <div class="group-header" >
					        <strong class="group-title">发票明细</strong>
							<div class="pull-right">
								<label>总金额：</label>
								<label class="text-left"><fmt:formatNumber value="${soOrder.imInvoiceTotalAmount}" pattern="#,##0.00"/></label>
							</div>
					    </div>
						<div style="overflow-x:scroll;">
							<table id="iminvoiceDtlInfoTable" class="table table-striped table-bordered table-condensed" >
								<thead>
									<tr>
										<% if (!imInvoiceAddReadonlyFlag) { %>
											<th class="text-center" width="20px"><a href="javascript:;" id="addIminvoiceDtlInfoRow"><i class="icon-plus-sign"></i></a></th>
										<% } %>
										<th>合同明细行</th>
										<th>物料名称</th>
										<th>单价</th>	
										<th>数量</th>
										<th>开票金额</th>
										<th>税金</th>
										<th>开票日期</th>
										<th>发票号码</th>
										<th>开票抬头</th>
										<th>快递编号</th>	
										<th>快递公司</th>
										<th>申请状态</th>
									</tr>
								</thead>
								<tbody>
								    <c:choose>
								        <c:when test="${!empty soOrder.imInvoiceDtlList}"> 
								            <c:forEach var="item" items="${soOrder.imInvoiceDtlList}" varStatus="status">
											    <tr>

												<form:hidden path="imInvoiceDtlList[${status.index}].id" />
												<form:hidden path="imInvoiceDtlList[${status.index}].orderNo" />
												<form:hidden path="imInvoiceDtlList[${status.index}].workflowStatus" />
												<%
													if (!imInvoiceAddReadonlyFlag) {
												%>
											    	<td class="text-center" width="20px">
											    	<c:if test="${item.workflowStatus != '50'}">
											           <a href="javascript:;" class="delInvoiceRow"><i class="icon-minus-sign"></i></a>
											    	</c:if>
											        </td>
											    <% } %>
											        <td>
											        <% if (!imInvoiceReadonlyFlag) { %>
											        <c:if test="${item.workflowStatus != '50'}">
											        	<select name="imInvoiceDtlList[${status.index}].lineNo"  class="input-mini lineNo required">
											        		<option value=""></option>
															<c:forEach items="${soOrder.soOrderDtlList}" var="lineNos">
																<option value="${lineNos.lineNo}" ${lineNos.lineNo==item.lineNo?'selected':''}>${lineNos.lineNo}</option>
															</c:forEach>
											        	</select>
											        </c:if>
											        <c:if test="${item.workflowStatus == '50'}">
											        	<form:hidden path="imInvoiceDtlList[${status.index}].lineNo" />
											        	<select name="imInvoiceDtlList[${status.index}].lineNo"  class="input-mini lineNo" disabled="disabled">
											        		<option value=""></option>
															<c:forEach items="${soOrder.soOrderDtlList}" var="lineNos">
																<option value="${lineNos.lineNo}" ${lineNos.lineNo==item.lineNo?'selected':''}>${lineNos.lineNo}</option>
															</c:forEach>
											        	</select>
											        </c:if>
											        <% } else { %>
											        	<form:hidden path="imInvoiceDtlList[${status.index}].lineNo" />
											        	<select name="imInvoiceDtlList[${status.index}].lineNo"  class="input-mini lineNo" disabled="disabled">
											        		<option value=""></option>
															<c:forEach items="${soOrder.soOrderDtlList}" var="lineNos">
																<option value="${lineNos.lineNo}" ${lineNos.lineNo==item.lineNo?'selected':''}>${lineNos.lineNo}</option>
															</c:forEach>
											        	</select>
											        <% } %>
						            				</td>
											        <td>
											        	<c:forEach items="${soOrder.soOrderDtlList}" var="soOrderDtlList">
															<c:if test="${soOrderDtlList.lineNo == item.lineNo}"><input name="" type="text" value="${soOrderDtlList.materialName}" class="input-xlarge materialName" readonly /></c:if>
														</c:forEach>
													</td>
											        <td>
											        	<c:forEach items="${soOrder.soOrderDtlList}" var="soOrderDtlList">
															<c:if test="${soOrderDtlList.lineNo == item.lineNo}"><input name="" type="hidden" value="<fmt:formatNumber value="${soOrderDtlList.unitPrice}" pattern="#.##"/>" class="text-right unitPrice" />
																													<input type="text" value="<fmt:formatNumber value="${soOrderDtlList.unitPrice}" pattern="#,##0.00"/>" class="text-right unitPriceFix" style="width: 100px" readonly /></c:if>
														</c:forEach>
													</td>
											        <td>
											        <c:if test="${item.workflowStatus != '50'}">
											        	<input name="imInvoiceDtlList[${status.index}].num" type="text" maxlength="3" value="${item.num}" class="text-right num required" style="width: 100px" <%=imInvoiceReadonlyFlag?"readonly":"" %>/>
											        </c:if>
						            				<c:if test="${item.workflowStatus == '50'}">
											        	<input name="imInvoiceDtlList[${status.index}].num" type="text" value="${item.num}" class="text-right num" style="width: 100px" readonly/>
						            				</c:if>
											        </td>
						            				<td>
						            					<input name="imInvoiceDtlList[${status.index}].invoiceAmount" maxlength="11" type="hidden" value="<fmt:formatNumber value="${item.invoiceAmount}" pattern="#.##"/>" class="text-right invoiceAmount" />
						            					<input type="text" value="<fmt:formatNumber value="${item.invoiceAmount}" pattern="#,##0.00"/>" class="text-right invoiceAmountFix" style="width: 100px" readonly />
						            				</td>
						            				<td>
<%-- 						            				<c:if test="${item.workflowStatus != '50'}"> --%>
						            					<input name="imInvoiceDtlList[${status.index}].tax" type="hidden" maxlength="11" value="<fmt:formatNumber value="${item.tax}" pattern="#.##"/>" class="text-right tax"/>
						            					<input type="text" value="<fmt:formatNumber value="${item.tax}" pattern="#,##0.00"/>" class="text-right taxFix" onblur="numToStr(this);" onfocus="strToNum(this);" style="width: 100px" <%=imInvoiceReadonlyFlag?"readonly":"" %>/>
<%-- 						            				</c:if>
						            				<c:if test="${item.workflowStatus == '50'}">
						            					<input name="imInvoiceDtlList[${status.index}].tax" type="text" value="<fmt:formatNumber value="${item.tax}" pattern="#.##"/>" class="text-right tax" style="width: 100px" readonly/>
						            				</c:if> --%>
						            				</td>
						            				<td>
						            				<% if (!imInvoiceReadonlyFlag) { %>
<%-- 						            				<c:if test="${item.workflowStatus != '50'}"> --%>
						            					<input name="imInvoiceDtlList[${status.index}].invoiceDate" type="text" value="<fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM-dd"/>" readonly  style="width: 100px" class="input-medium Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
<%-- 						            				</c:if>
						            				<c:if test="${item.workflowStatus == '50'}">
						            					<input name="imInvoiceDtlList[${status.index}].invoiceDate" type="text" value="<fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM-dd"/>" readonly  style="width: 100px"/>
						            				</c:if> --%>
						            				<% } else { %>
						            					<input name="imInvoiceDtlList[${status.index}].invoiceDate" type="text" value="<fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM-dd"/>" readonly  style="width: 100px"/>
						            				<% } %>
						            				</td>
						            				<td>
<%-- 						            				<c:if test="${item.workflowStatus != '50'}"> --%>
						            					<input name="imInvoiceDtlList[${status.index}].invoiceNo" maxlength="50" class="input-medium" type="text" value="${item.invoiceNo}" <%=imInvoiceReadonlyFlag?"readonly":"" %>/>
<%-- 						            				</c:if>
						            				<c:if test="${item.workflowStatus == '50'}">
						            					<input name="imInvoiceDtlList[${status.index}].invoiceNo" class="input-medium" type="text" value="${item.invoiceNo}" readonly/>
						            				</c:if> --%>
						            				</td>
						            				<td>${item.invoiceTitle}</td>
						            				<td>
<%-- 						            				<c:if test="${item.workflowStatus != '50'}"> --%>
						            					<input name="imInvoiceDtlList[${status.index}].expressNo" maxlength="50" class="input-medium" type="text" value="${item.expressNo}" <%=imInvoiceReadonlyFlag?"readonly":"" %>/>
<%-- 						            				</c:if>
						            				<c:if test="${item.workflowStatus == '50'}">
						            					<input name="imInvoiceDtlList[${status.index}].expressNo" class="input-medium" type="text" value="${item.expressNo}" readonly/>
						            				</c:if> --%>
						            				</td>
						            				<td>
<%-- 						            				<c:if test="${item.workflowStatus != '50'}"> --%>
						            					<input name="imInvoiceDtlList[${status.index}].expressCompany" maxlength="100" class="input-medium" type="text" value="${item.expressCompany}" <%=imInvoiceReadonlyFlag?"readonly":"" %>/>
<%-- 						            				</c:if>
						            				<c:if test="${item.workflowStatus == '50'}">
						            					<input name="imInvoiceDtlList[${status.index}].expressCompany" class="input-medium" type="text" value="${item.expressCompany}" readonly/>
						            				</c:if> --%>
						            				</td>
						            				<td>${fns:getDictLabel(item.workflowStatus, 'DM0043', '')}</td>
											    </tr>
								            </c:forEach> 
								        </c:when>
								    </c:choose>
								</tbody>	
							</table>
						</div>
					</div>
				<%-- </shiro:hasPermission> --%>
				</c:if>
			</c:if>
			<% } %>
			
			<c:if test="${(empty soOrder.imInvoice.workflowStatus || soOrder.imInvoice.workflowStatus == '50') && (empty soOrder.deliverWorkflowStatus || soOrder.deliverWorkflowStatus == '50') && !empty soOrder.businessOppNo}">
				<%-- <shiro:hasPermission name="sd:soOrder:edit"> --%>
				<c:if test="${soOrder.dataType == 'order'}">
					<!-- <input type="hidden" name="dataType" value="order"/> -->
					<c:if test="${soOrder.act.assignee == user.loginName && (soOrder.workflowStatus == '10' || soOrder.workflowStatus == '20')}">
						<div class="group-box">
						    <div class="group-header" >
						        <strong class="group-title">审批</strong>
						    </div>
			
							<ul class="ul-form">
						        <li style="width:100%;">
						            <label>审批意见：</label>
						            <div style="overflow: hidden;">
						                <form:textarea path="act.comment" rows="3" maxlength="300" style="width:100%;" ></form:textarea>
						            </div>
						        </li>
						    </ul>
			
						    <div class="text-center">
						        <!-- <input name="opt" type="submit" class="btn btn-primary" value="同意">
						        <input name="opt" type="submit" class="btn btn-default" value="退回"> -->
						        <input name="" type="button" class="btn btn-primary opt" value="同意">
						        <input name="" type="button" class="btn btn-default opt" value="退回">
						    </div>
						</div>
					</c:if>
					<c:if test="${soOrder.createBy == user.id && soOrder.workflowStatus == '10'}">
						<div class="group-box">
						    <div class="text-center">
						        <!-- <input name="opt" type="submit" class="btn btn-primary" value="撤回"> -->
						        <input name="" type="button" class="btn btn-primary opt" value="撤回">
						    </div>
						</div>
					</c:if>
					<c:if test="${empty soOrder.workflowStatus || (soOrder.createBy == user.id && (soOrder.workflowStatus == '40' || soOrder.workflowStatus == '30'))}">
						<div class="text-center">
							<!-- <input name="opt" type="hidden" value="提交申请"> -->
					        <!-- <input id="opt" name="opt" type="button" class="btn btn-primary" value="提交申请"> -->
					        <input name="" type="button" class="btn btn-primary opt" value="提交申请">
					        <c:if test="${soOrder.createBy == user.id && (soOrder.workflowStatus == '40' || soOrder.workflowStatus == '30')}">
						        <!-- <input name="opt" type="submit" class="btn btn-primary" value="删除"> -->
						        <input name="" type="button" class="btn btn-primary opt" value="删除">
					        </c:if>
						</div>
					</c:if>
					<c:if test="${soOrder.workflowStatus == '50'}">
						<div class="text-center">
							<!-- <input name="opt" type="hidden" value="再申请">
					        <input id="opt" name="opt" type="button" class="btn btn-primary" value="再申请"> -->
					        <input name="" type="button" class="btn btn-primary opt" value="再申请">
						</div>
					</c:if>
				</c:if>
				<%-- </shiro:hasPermission> --%>
			</c:if>
<%--  			<c:if test="${empty soOrder.imInvoice.workflowStatus && empty soOrder.businessOppNo && soOrder.workflowStatus != '50'}">
				<div class="group-box">
					<div class="text-center">
						<input name="opt" type="submit" class="btn btn-primary" value="baocun">
					</div>
				</div>
			</c:if> --%>
			<c:if test="${soOrder.workflowStatus == '50'}">
				<%-- <shiro:hasPermission name="sd:imInvoice:edit"> --%>
				<c:if test="${soOrder.dataType == 'invoice'}">
					<!-- <input type="hidden" name="dataType" value="invoice"/> -->
						<c:if test="${soOrder.act.assignee == user.loginName && (soOrder.imInvoice.workflowStatus == '10' || soOrder.imInvoice.workflowStatus == '20')}">
							<div class="group-box">
							    <div class="group-header" >
							        <strong class="group-title">审批</strong>
							    </div>
				
								<ul class="ul-form">
							        <li style="width:100%;">
							            <label>审批意见：</label>
							            <div style="overflow: hidden;">
							                <form:textarea path="act.comment" rows="3" maxlength="300" style="width:100%;" ></form:textarea>
							            </div>
							        </li>
							    </ul>
				
							    <div class="text-center">
							       <!-- <input name="opt" type="submit" class="btn btn-primary" value="同意">
							       <input name="opt" type="submit" class="btn btn-default" value="退回"> -->
							       <input name="" type="button" class="btn btn-primary opt" value="同意">
							       <input name="" type="button" class="btn btn-default opt" value="退回">
							    </div>
							</div>
						</c:if>
						<c:if test="${soOrder.imInvoice.createBy == user.id && soOrder.imInvoice.workflowStatus == '10'}">
							<div class="group-box">
							    <div class="text-center">
							        <!-- <input name="opt" type="submit" class="btn btn-primary" value="撤回"> -->
							        <input name="" type="button" class="btn btn-primary opt" value="撤回">
							    </div>
							</div>
						</c:if>
						<c:if test="${empty soOrder.imInvoice.workflowStatus || (soOrder.imInvoice.createBy == user.id && (soOrder.imInvoice.workflowStatus == '40' || soOrder.imInvoice.workflowStatus == '30'))}">
							<div class="text-center">
								<!-- <input name="opt" type="hidden" value="提交申请"> -->
						        <!-- <input id="opt" name="opt" type="button" class="btn btn-primary" value="提交申请"> -->
						        <input name="" type="button" class="btn btn-primary opt" value="提交申请">
					        <c:if test="${soOrder.imInvoice.createBy == user.id && (soOrder.imInvoice.workflowStatus == '40' || soOrder.imInvoice.workflowStatus == '30')}">
						        <!-- <input name="opt" type="submit" class="btn btn-primary" value="删除"> -->
						        <input name="" type="button" class="btn btn-primary opt" value="删除">
					        </c:if>
							</div>
						</c:if>
						<c:if test="${soOrder.imInvoice.workflowStatus == '50'}">
							<div class="text-center">
								<!-- <input name="opt" type="hidden" value="再申请">
						        <input id="opt" name="opt" type="button" class="btn btn-primary" value="再申请"> -->
						        <input name="" type="button" class="btn btn-primary opt" value="再申请">
							</div>
						</c:if>
					</c:if>
				<%-- </shiro:hasPermission> --%>
			</c:if>
			<shiro:hasPermission name="cm:manager:edit">
				<c:if test="${(empty soOrder.dataType && soOrder.workflowStatus == '50' && (empty soOrder.imInvoice.workflowStatus || soOrder.imInvoice.workflowStatus == '50') && (empty soOrder.deliverWorkflowStatus || soOrder.deliverWorkflowStatus == '50')) || (soOrder.dataType == 'order' && empty soOrder.businessOppNo)}">
					<div class="group-box">
						<div class="text-center">
							<!-- <input name="opt" type="hidden" value="保存">
							<input id="opt" name="opt" type="button" class="btn btn-primary" value="保存"> -->
							<input name="" type="button" class="btn btn-primary opt" value="保存">
						</div>
					</div>
				</c:if>
			</shiro:hasPermission>
			<c:if test="${soOrder.dataType == 'order' && !empty soOrder.procInsId}">
				<act:histoicFlow procInsId="${soOrder.procInsId}"/>
			</c:if>
			<c:if test="${soOrder.dataType == 'invoice' && !empty soOrder.imInvoice.procInsId}">
				<act:histoicFlow procInsId="${soOrder.imInvoice.procInsId}"/>
			</c:if>
	</form:form>
			<div class="modal fade" id="soOrderDtlModal" style="display:none;" data-backdrop="static" >
				<div class="modal-dialog">
					<div class="modal-content">
					<form:form id="modalForm" class="form-search">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" >&times;</button>
							<h4 class="text-center modal-title">合同行编辑</h4>
						</div>
						<div class="modal-body">
							<div class="group-box group-box-first">
							    <ul class="ul-form">
							        <li>
							            <label>最终客户：</label>
							            <input id="endCustomerId" name="endCustomerId" type="text" value="" maxlength="50" data-show="text" class="remote customer endCustomerId input-medium"/>
							            <input id="endCustomerName" type="hidden" value="" class="endCustomerName"/>
							            <input id="rowNo" type="hidden"/>
							            <input id="deliverNum" type="hidden"/>
							            <input id="invoiceNum" type="hidden"/>
							            <input id="lineNo" type="hidden"/>
							        </li>
							        <li>
							            <label>行业：</label>
								            <select id="industry" class="input-medium"  disabled=disabled>
								                <option value=""></option>
												<c:forEach items="${fns:getDictList('DM0002')}" var="items">
													<option value="${items.value}">${items.label}</option>
												</c:forEach>
								            </select>
							        </li>
					   				
							        <li>
							            <label>具体分类：</label>
								            <select id="customerDiff" class="input-medium"  disabled="disabled">
								                <option value=""></option>
												<c:forEach items="${fns:getDictList('DM0003')}" var="items">
													<option value="${items.value}">${items.label}</option>
												</c:forEach>
								            </select>
							        </li>
							        <li>
							            <label>科室/系：</label>
								            <select id="customerSegmentation" class="input-medium">
								                <option value=""></option>
												<c:forEach items="${fns:getDictListByParentId('DM0002_1','DM0039')}" var="items">
													<option value="${items.value}">${items.label}</option>
												</c:forEach>
								            </select>
							        </li>
					   				
							        <li>
							            <label>省市：</label>
							            <select id="province" class="input-medium"  disabled="disabled">
							                <option value=""></option>
												<c:forEach items="${fns:getSqlDictList('dict_province')}" var="items">
													<option value="${items.value}">${items.label}</option>
												</c:forEach>
							            </select>
							        </li>
							        <li>
							            <label>城市：</label>
							            <select id="city" class="input-medium modalCity"  disabled="disabled">
							                <option value=""></option>
												<c:forEach items="${fns:getSqlDictList('dict_city')}" var="items">
													<option value="${items.value}">${items.label}</option>
												</c:forEach>
							            </select>
							        </li>
					   				
							        <li>
							            <label>地区：</label>
								            <select id="region" class="input-medium region" >
								                <option value=""></option>
												<c:forEach items="${fns:getDictList('DM0049')}" var="items">
													<option value="${items.value}">${items.label}</option>
												</c:forEach>
								            </select>
							        </li>
							        <li>
							            <label>地区负责人：</label>
							            <input id="responsiblePersonId" name="responsiblePersonId"  type="text" data-show="text" data-type="10,20" class="remote employee input-medium employeeId"/>
							            <%-- <select id="responsiblePersonId" class="input-medium employeeId">
							                <option value=""></option>
												<c:forEach items="${fns:getSqlDictList('dict_employee')}" var="items">
													<option value="${items.value}">${items.label}</option>
												</c:forEach>
							            </select> --%>
							        </li>
					   				
							        <li>
							            <label>提成人：</label>
							            <input id="commissionPeisonId" name="commissionPeisonId"  type="text" data-show="text" data-type="10,20" class="remote employee input-medium"/>
							            <%-- <select id="commissionPeisonId" class="input-medium">
							                <option value=""></option>
												<c:forEach items="${fns:getSqlDictList('dict_employee')}" var="items">
													<option value="${items.value}">${items.label}</option>
												</c:forEach>
							            </select> --%>
							        </li>
							        <li>
							            <label>组：</label>
							            <select id="organize" class="input-medium organize">
							                <option value=""></option>
												<c:forEach items="${fns:getSqlDictList('dict_organize')}" var="items">
													<option value="${items.value}">${items.label}</option>
												</c:forEach>
							            </select>
							        </li>
					   				
							        <li>
							            <label><span class="help-inline"><font color="red">*</font></span>物料号：</label>
							            <input id="materialNo" name="materialNo" type="text" maxlength="50" class="input-medium remote material materialNo required"/>
							        </li>
							        <li>
							            <label>物料名：</label>
							            <input id="materialName" type="text" class="input-medium materialName" readonly />
							        </li>
					   				
							        <li>
							            <label><span class="help-inline"><font color="red">*</font></span>数量：</label>
							            <input id="num" name="num" type="text" maxlength="3" min="1" class="input-medium text-right modalNum digits required"/>
							        </li>
							        <li>
							            <label>标准价：</label>
							            <input id="standardPrice" class="input-medium modalStandardPrice" type="hidden"/>
							            <input class="input-medium modalStandardPriceFix" type="text" readonly/>
							        </li>
					   				
							        <li>
							            <label><span class="help-inline"><font color="red">*</font></span>单价：</label>
							            <input id="unitPrice" name="unitPrice" type="hidden" maxlength="11" class="input-medium text-right modalUnitPrice required" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>
							            <input type="text" class="input-medium text-right modalUnitPriceFix required" onblur="numToStr(this);" onfocus="strToNum(this);"/>
							        </li>
							        <li>
							            <label>金额：</label>
							            <input id="totalAmount" type="hidden" class="input-medium text-right modalTotalAmount"/>
							            <input type="text" class="input-medium text-right modalTotalAmountFix" readonly/>
							        </li>
					   				
							        <li>
							            <label><span class="help-inline"><font color="red">*</font></span>质保期(年)：</label>
							            <input id="warrantyPeriod" name="warrantyPeriod" maxlength="2" class="input-medium text-right required" type="text"/>
							        </li>
							        <li>
							            <label>延保(年)：</label>
							            <input id="extendedWarrPeriod" name="extendedWarrPeriod" maxlength="2" class="input-medium text-right" type="text" />
							        </li>
							        <li>
							            <label>行作废：</label>
							            <input id="ifEffective" type="checkbox" value="" />
							        </li>
							        <li>
							            <label>是否退货：</label>
							            <input id="ifReturn" type="checkbox" value="" />
							        </li>
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
	<sys:message content="${message}"/>
</body>
</html>