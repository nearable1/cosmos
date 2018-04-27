<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>机器采购订单申请</title>
    <meta name="decorator" content="default"/>

    <style type="text/css">
    </style>

    <script type="text/javascript">
        $(document).ready(function() {

            initPage();

            function initPage() {
                // 添加画面验证
                $("#inputForm").validate({
                    submitHandler: function(form) {
                        form.submit();
                    },
                    invalidHandler: function(form, validator) {
                        showError("输入有误，请先更正。");
                    },
                    errorContainer: "#messageBox",
                    errorPlacement: function(error, element) {
                        if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                            error.appendTo(element.parent().parent());
                        } else {
                            error.insertAfter(element);
                        }
                    },
                    ignore:""
                });

                // 支付状态默认值
                if (!'${pmPurchaseOrd.paymentStatus}') {
                    $("#paymentStatus").select2("val", 0)
                }
            };

            // 支付状态change事件绑定
            $("#paymentStatus").on("change", function() {
                // 已支付时，将未付数量置为0，未付金额置为0
                if ($(this).val() == "2") {
                    $("#purTable>tbody>tr>td>input.unpayNum").val("0");
                    $("#purTable>tbody>tr>td>input.unpayAmount").val("0");
                    $("#purTable>tbody>tr>td>input.unpayAmountFix").val("0");
                }
            });

            // 添加采购明细行
            $("#addPurRow").on("click", function() {
                // 查看现有行数
                var rowNum = $("#purTable>tbody>tr:not(.empty)").length + 1;
                // 创建行
                var html = '<tr>'
                    html += '<td class="text-center"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>'
                    html += '<td class="text-center rowNo">'+ rowNum + '</td>'
                    html += '<td><input name="pmPurchaseOrdDtlList[' + (rowNum - 1) + '].materialNo" id="purDtl' + (rowNum - 1) + '_materialNo" type="text" value="" maxlength="50" class="required materialNo" data-type="1,2,4,5,7" style="width: 100%" /></td>'
                    html += '<td><input name="pmPurchaseOrdDtlList[' + (rowNum - 1) + '].materialName" type="text" value="" class="materialName" style="width: 100%" readonly="true" /></td>'
                    html += '<td><input name="pmPurchaseOrdDtlList[' + (rowNum - 1) + '].num" type="text" value="" maxlength="3" min="1" class="text-right required num digits" style="width: 100%" /></td>'
                    html += '<td><input name="pmPurchaseOrdDtlList[' + (rowNum - 1) + '].unitPrice" type="hidden" value="" maxlength="11" class="text-right required unitPrice" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$"/>'
                    html += '<input type="text" value="" class="text-right required unitPriceFix" style="width: 100%" onblur="numToStr(this);" onfocus="strToNum(this);" /></td>'
                    html += '<td><input name="pmPurchaseOrdDtlList[' + (rowNum - 1) + '].totalAmount" type="hidden" value="" class="text-right totalAmount" />'
                    html += '<input type="text" value="" class="text-right totalAmountFix" style="width: 100%" readonly="true" /></td>'
                    html += '<td><input name="pmPurchaseOrdDtlList[' + (rowNum - 1) + '].unpayNum" type="text" value="" maxlength="10" min="0" class="text-right unpayNum digits" style="width: 100%" /></td>'
                    html += '<td><input name="pmPurchaseOrdDtlList[' + (rowNum - 1) + '].unpayAmount" type="hidden" value="" class="text-right unpayAmount" />'
                    html += '<input type="text" value="" class="text-right unpayAmountFix" style="width: 100%" readonly="true" /></td>'
                    html += '<td><input name="pmPurchaseOrdDtlList[' + (rowNum - 1) + '].arrivalNum" type="text" value="" class="text-right arrivalNum" style="width: 100%" readonly="true" /></td>'
                    html += '<td><input name="pmPurchaseOrdDtlList[' + (rowNum - 1) + '].unarrivalNum" type="text" value="" class="text-right unarrivalNum" style="width: 100%" readonly="true" /></td>'
                    html += '</tr>';

                $("#purTable>tbody").append(html).find("tr.empty").remove();
                $("#purDtl" + (rowNum - 1) + "_materialNo").select2(getMatSelectOption());
            });

            // 添加发票明细行
            $("#addInvoiceRow").on("click", function() {
                // 查看现有行数
                var rowNum = $("#invoiceTable>tbody>tr:not(.empty)").length + 1;
                // 创建行
                var html = '<tr>'
                    html += '<td class="text-center"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>'
                    html += '<td class="text-center rowNo">' + rowNum + '</td>'
                    html += '<td><input name="pmPurInvoiceList[' + (rowNum - 1) + '].invoiceNo" type="text" value="" maxlength="50" class="required" style="width: 100%"></td>'
                    html += '<td><input name="pmPurInvoiceList[' + (rowNum - 1) + '].invoiceDate" type="text" maxlength="10" class="required input-medium Wdate" style="width: 100%;" value="" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'});"></td>'
                    html += '</tr>'

                $("#invoiceTable>tbody").append(html).find("tr.empty").remove();
            });

            // 删除添加行
            $(document).on("click", "table .delRow", function() {
                var $tbody = $(this).parents("tbody");
                var $delRow = $(this).parents("tr");
                // 销毁物料号下拉
                var $sel2 = $delRow.find(".remote");
                if ($sel2.length > 0) {
                    $sel2.select2("destroy");
                }
                // 删除行
                $delRow.remove();
                // 重整行name下标
                $tbody.find("tr").each(function(index){ 
                    $(this).find("td.rowNo").html(index+1);
                    $(this).find("td>input").each(function() {
    					if (typeof($(this).attr("name")) != "undefined") {
                            $(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
    					}
                    });
                   });

                if ($tbody.find("tr").length == 0) {
                    var clmCount = $tbody.parent("table").find("thead>tr>th").length;
                    var emptyRow = '<tr class="empty"><td class="text-center" colspan="' + clmCount + '">点击添加按钮添加数据</tr>';
                    $tbody.append(emptyRow);
                }
            });

            // 物料号change事件绑定
            $(document).on("change", "input.materialNo", function(e) {
                var $noInput = $(this);
                var $nameInput = $noInput.parent().parent().find("input.materialName");
                if (e.val == "") {
                    $nameInput.val("");
                } else {
                    $nameInput.val(e.added.text);
                }
            });

            // 数量、单价change事件绑定
            $(document).on("change", "input.num,input.unitPriceFix", function() {
                var _this = $(this);
                var $row = _this.parent().parent();
                var $totalInput = $row.find("input.totalAmount");
                var num = $row.find("input.num").val();
                var $unitPrice = $row.find("input.unitPrice");
                var $totalInputFix = $row.find("input.totalAmountFix");
                var unitPriceFix = $row.find("input.unitPriceFix").val();
                
                if (unitPriceFix == null || unitPriceFix == "") {
                	$unitPrice.val("");
                } else {
                	$unitPrice.val(unitPriceFix.trim().replace(/,/g, ""));
                }
                
                if (num == "" || unitPriceFix == "") {
                    $totalInput.val("");
                    $totalInputFix.val("");
                    return false;
                }
                $totalInput.val(accMul(num, $unitPrice.val()).toFixed(2));
                $totalInputFix.val(toThousands($totalInput.val()));

                if (_this.hasClass("unitPriceFix")) {
                    $row.find("input.unpayNum").trigger("change");
                }

            });

            function accMul(arg1, arg2) {  
                var m=0, s1=arg1.toString(), s2=arg2.toString();  
                try { m += s1.split(".")[1].length } catch(e){}  
                try { m += s2.split(".")[1].length } catch(e){}  
                return Number(s1.replace(".","")) * Number(s2.replace(".","")) / Math.pow(10, m);
            }

            // 未付数量change事件绑定
            $(document).on("change", "input.unpayNum", function() {
                var _this = $(this);
                var $row = _this.parent().parent();
                var $unpayInput = $row.find("input.unpayAmount");
                var $unpayInputFix = $row.find("input.unpayAmountFix");
                var unitPrice = $row.find("input.unitPrice").val();
                var unpayNum = _this.val();
                
                if (unitPrice == "" || unpayNum == "") {
                    $unpayInput.val("");
                    $unpayInputFix.val("");
                    return false;
                }
                $unpayInput.val(accMul(unpayNum, unitPrice).toFixed(2));
                $unpayInputFix.val(toThousands($unpayInput.val()));
            });

            // 采购明细导出
            $("#export").on("click", function() {
                var fHtml = '<form method="get" action="${ctx}/pm/purchase/machine/export">';
                    fHtml += '<input type="hidden" name="purchaseNo" value="${pmPurchaseOrd.purchaseNo}">';
                    fHtml += '</form>';
                $(fHtml).appendTo('body').submit().remove();
            });
        });
    </script>

</head>
<body>

    <form:form id="inputForm" modelAttribute="pmPurchaseOrd" action="${ctx}/pm/purchase/mc/save" method="post" class="form-search" novalidate="true">
        <form:hidden path="id"/>
        <form:hidden path="act.taskId"/>
        <form:hidden path="act.procInsId"/>
        <form:hidden path="workflowStatus"/>
        <input type="hidden" name="updateDate" value="<fmt:formatDate value="${pmPurchaseOrd.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />

        <shiro:hasPermission name="cm:manager:edit">
            <c:set var="isManagerEdit" value="${viewsts.isApprovalCompleted ? true : false}"></c:set>
        </shiro:hasPermission>
        <shiro:lacksPermission name="cm:manager:edit">
            <c:set var="isManagerEdit" value="false"></c:set>
        </shiro:lacksPermission>

        <h3 class="text-center page-title">机器采购订单</h3>
        <c:if test="${viewsts.canEdit || isManagerEdit}">
            <div class="group-box group-box-first" style="height:auto;">
                <ul class="ul-form">
                    <li>
                        <label>创建人：</label>
                        <form:input path="createName" class="input-medium" type="text" value="" readonly="true"/>
                    </li>
                    <li>
                        <label>创建日期：</label>
                        <input name="createDate" class="input-medium" type="text" value="<fmt:formatDate value="${pmPurchaseOrd.createDate}" pattern="yyyy-MM-dd"/>" readonly />
                    </li>
                    <li>
                        <label>订单号：</label>
                        <form:input path="purchaseNo" name="purchaseOrderNo" class="input-medium" type="text" value="" maxlength="50" readonly="true"/>
                    </li>
                    <li>
                        <label>到货状态：</label>
                        <input class="input-medium" type="text" value="${fns:getDictLabel(pmPurchaseOrd.arrivalStatus, 'DM0023', '')}" readonly />
                        <form:hidden path="arrivalStatus"/>
                    </li>
                    <li class="clearfix"></li>
                    <li>
                        <label><span class="help-inline"><font color="red">*</font></span>供应商：</label>
                        <form:select path="supplierId" class="input-medium required">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getSqlDictList('dict_supplier')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </li>
                    <li>
                        <label><span class="help-inline"><font color="red">*</font></span>支付状态：</label>
                        <form:select path="paymentStatus" class="input-medium required" >
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('DM0022')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </li>
                    <li>
                        <label>AX订单号：</label>
                        <form:input path="axNo" class="input-medium" type="text" value="" maxlength="50" />
                    </li>
                    <li>
                        <label>订单状态：</label>
                        <c:choose>
                            <c:when test="${empty pmPurchaseOrd.workflowStatus || pmPurchaseOrd.workflowStatus == '60'}">
                                <input class="input-medium" type="text" disabled />
                            </c:when>
                            <c:otherwise>
                                <input class="input-medium" type="text" value="${fns:getDictLabel(pmPurchaseOrd.workflowStatus, 'DM0015', '申请中')}" disabled />
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="full-width">
                        <label>备注：</label>
                        <form:textarea path="newRemarks" rows="3" maxlength="300" class="fill-right" />
<!--                         <div style="overflow: hidden;"> -->
<%--                             <form:textarea path="newRemarks" rows="3" maxlength="300" style="width:100%;" ></form:textarea> --%>
<!--                         </div> -->
                    </li>
                </ul>
            </div>
            <div class="group-box">
                <div class="group-header" >
                    <strong class="group-title">采购明细</strong>
                    <c:if test="${empty pmPurchaseOrd.purchaseNo}">
                        <input id="export" class="btn btn-primary" type="button" value="明细导出" disabled>
                    </c:if>
                    <c:if test="${not empty pmPurchaseOrd.purchaseNo}">
                        <input id="export" class="btn btn-primary" type="button" value="明细导出">
                    </c:if>
                </div>
    
                <table id="purTable" class="table table-striped table-bordered table-condensed" >
                    <thead>
                        <tr>
                            <th class="text-center" width="20px"><a href="javascript:;" id="addPurRow"><i class="icon-plus-sign"></i></a></th>
                            <th class="text-center" width="35px">No</th>
                            <th width="14%">物料号</th>
                            <th>物料名称</th>
                            <th width="8%">数量</th>
                            <th width="10%">单价</th>
                            <th width="10%">总金额</th>
                            <th width="8%">未付数量</th>
                            <th width="10%">未付金额</th>
                            <th width="8%">到货数量</th>
                            <th width="8%">未到货数量</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${!empty pmPurchaseOrd.pmPurchaseOrdDtlList}"> 
                                <c:forEach var="item" items="${pmPurchaseOrd.pmPurchaseOrdDtlList}" varStatus="status">
                                    <tr>
                                        <c:choose>
                                            <c:when test="${item.alArrivalNum > 0}">
                                                 <td class="text-center">
                                                     <form:hidden path="pmPurchaseOrdDtlList[${status.index}].id"/>
                                                     <a href="javascript:;" class="delRow disabled"><i class="icon-minus-sign"></i></a>
                                                 </td>
                                                 <td class="text-center rowNo">${status.index + 1}</td>
                                                 <td><input name="pmPurchaseOrdDtlList[${status.index}].materialNo" type="text" value="${item.materialNo}" maxlength="50" class="remote material required materialNo" data-type="1,2,4,5,7" style="width: 100%" readonly /></td>
                                            </c:when>
                                            <c:otherwise>
                                                 <td class="text-center">
                                                     <form:hidden path="pmPurchaseOrdDtlList[${status.index}].id"/>
                                                     <a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a>
                                                 </td>
                                                 <td class="text-center rowNo">${status.index + 1}</td>
                                                 <td><input name="pmPurchaseOrdDtlList[${status.index}].materialNo" type="text" value="${item.materialNo}" maxlength="50" class="remote material required materialNo" data-type="1,2,4,5,7" style="width: 100%" /></td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td><input name="pmPurchaseOrdDtlList[${status.index}].materialName" type="text" value="${item.materialName}" class="materialName" style="width: 100%;" readonly /></td>
                                        <td><input name="pmPurchaseOrdDtlList[${status.index}].num" type="text" value="${item.num}" maxlength="3" min="1" class="text-right required num digits" style="width: 100%" /></td>
                                        <td><input name="pmPurchaseOrdDtlList[${status.index}].unitPrice" type="hidden" value="<fmt:formatNumber value="${item.unitPrice}" pattern="#.##"/>" maxlength="11" class="text-right required unitPrice" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
                                        	<input type="text" value="<fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/>" class="text-right required unitPriceFix" onblur="numToStr(this);" onfocus="strToNum(this);" style="width: 100%" /></td>
                                        <td><input name="pmPurchaseOrdDtlList[${status.index}].totalAmount" type="hidden" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#.##"/>" class="text-right totalAmount"/>
                                        	<input type="text" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/>" class="text-right totalAmountFix" style="width: 100%" readonly /></td>
                                        <td><input name="pmPurchaseOrdDtlList[${status.index}].unpayNum" type="text" value="${item.unpayNum}" maxlength="10" min="0" class="text-right unpayNum digits" style="width: 100%" /></td>
                                        <td><input name="pmPurchaseOrdDtlList[${status.index}].unpayAmount" type="hidden" value="<fmt:formatNumber value="${item.unpayAmount}" pattern="#.##"/>" class="text-right unpayAmount"/>
                                        	<input type="text" value="<fmt:formatNumber value="${item.unpayAmount}" pattern="#,##0.00"/>" class="text-right unpayAmountFix" style="width: 100%" readonly /></td>
                                        <td><input name="pmPurchaseOrdDtlList[${status.index}].alArrivalNum" type="text" value="${item.alArrivalNum}" class="text-right arrivalNum" style="width: 100%" readonly /></td>
                                        <td><input name="pmPurchaseOrdDtlList[${status.index}].unarrivalNum" type="text" value="${item.unarrivalNum}" class="text-right unarrivalNum" style="width: 100%" readonly /></td>
                                    </tr>
                                </c:forEach> 
                            </c:when>
                            <c:otherwise>
                                    <tr>
                                        <td class="text-center"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>
                                        <td class="text-center rowNo">1</td>
                                        <td><input name="pmPurchaseOrdDtlList[0].materialNo" type="text" value="" maxlength="50" class="remote material required materialNo" data-type="1,2,4,5,7" style="width: 100%;" /></td>
                                        <td><input name="pmPurchaseOrdDtlList[0].materialName" type="text" value="" class="materialName" style="width: 100%" readonly /></td>
                                        <td><input name="pmPurchaseOrdDtlList[0].num" type="text" value="" maxlength="3" min="1" class="text-right required num digits" style="width: 100%" /></td>
                                        <td><input name="pmPurchaseOrdDtlList[0].unitPrice" type="hidden" value="" maxlength="11" class="text-right required unitPrice" pattern="^[0-9]{1,8}(\.[0-9]{1,2})?$" />
                                        	<input type="text" value="" class="text-right required unitPriceFix" onblur="numToStr(this);" onfocus="strToNum(this);" style="width: 100%"  /></td>
                                        <td><input name="pmPurchaseOrdDtlList[0].totalAmount" type="hidden" value="" class="text-right totalAmount" />
                                        	<input type="text" value="" class="text-right totalAmountFix" style="width: 100%" readonly /></td>
                                        <td><input name="pmPurchaseOrdDtlList[0].unpayNum" type="text" value="" maxlength="10" min="0" class="text-right unpayNum digits" style="width: 100%" /></td>
                                        <td><input name="pmPurchaseOrdDtlList[0].unpayAmount" type="hidden" value="" class="text-right unpayAmount" />
                                        	<input type="text" value="" class="text-right unpayAmountFix" style="width: 100%" readonly /></td>
                                        <td><input name="pmPurchaseOrdDtlList[0].arrivalNum" type="text" value="" class="text-right arrivalNum" style="width: 100%" readonly /></td>
                                        <td><input name="pmPurchaseOrdDtlList[0].unarrivalNum" type="text" value="" class="text-right unarrivalNum" style="width: 100%" readonly /></td>
                                    </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>    
                </table>
            </div>
            <div class="group-box">
                <div class="group-header" >
                    <strong class="group-title">发票明细</strong>
                </div>
                <table id="invoiceTable" class="table table-striped table-bordered table-condensed" style="width: 40%">
                    <thead>
                        <tr>
                            <th class="text-center" width="20px"><a href="javascript:;" id="addInvoiceRow"><i class="icon-plus-sign"></i></a></th>
                            <th class="text-center" width="35px">No</th>
                            <th>发票编号</th>
                            <th>发票日期</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${!empty pmPurchaseOrd.pmPurInvoiceList}"> 
                            <c:forEach var="item" items="${pmPurchaseOrd.pmPurInvoiceList}" varStatus="status">
                                <tr>
                                    <td class="text-center">
                                        <a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a>
                                        <form:hidden path="pmPurInvoiceList[${status.index}].id"/>
                                    </td>
                                    <td class="text-center rowNo">${status.index + 1}</td>
                                    <td><form:input path="pmPurInvoiceList[${status.index}].invoiceNo" type="text" value="" maxlength="50" class="required" style="width: 100%" /></td>
                                    <td>
                                        <input name="pmPurInvoiceList[${status.index}].invoiceDate" type="text" maxlength="10" class="required input-medium Wdate" style="width: 100%;" value="<fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty pmPurchaseOrd.pmPurInvoiceList}">
                            <tr class="empty"><td class="text-center" colspan="4">点击添加按钮添加数据</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${!viewsts.canEdit && !isManagerEdit}">
            <div class="group-box group-box-first" style="height:auto;">
                <ul class="ul-form">
                    <li>
                        <label>创建人：</label>
                        <form:input path="createName" class="input-medium" type="text" value="" disabled="true"/>
                    </li>
                    <li>
                        <label>创建日期：</label>
                        <input name="createDate" class="input-medium" type="text" value="<fmt:formatDate value="${pmPurchaseOrd.createDate}" pattern="yyyy-MM-dd"/>" disabled />
                    </li>
                    <li>
                        <label>订单号：</label>
                        <form:input path="purchaseNo" name="purchaseOrderNo" class="input-medium" type="text" value="" maxlength="50" disabled="true" />
                    </li>
                    <li>
                        <label>供应商：</label>
                        <form:select path="supplierId" class="input-medium required" disabled="true">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getSqlDictList('dict_supplier')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </li>
                    <li>
                        <label>支付状态：</label>
                        <form:select path="paymentStatus" class="input-medium required" disabled="true">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('DM0022')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </li>
                    <li>
                        <label>到货状态：</label>
                        <input class="input-medium" type="text" value="${fns:getDictLabel(pmPurchaseOrd.arrivalStatus, 'DM0023', '')}" disabled />
                    </li>
                    <li>
                        <label>AX订单号：</label>
                        <form:input path="axNo" class="input-medium" type="text" value="" maxlength="50" disabled="true" />
                    </li>
                    <li>
                        <label>订单状态：</label>
                        <c:choose>
                            <c:when test="${empty pmPurchaseOrd.workflowStatus || pmPurchaseOrd.workflowStatus == '60'}">
                                <input class="input-medium" type="text" disabled />
                            </c:when>
                            <c:otherwise>
                                <input class="input-medium" type="text" value="${fns:getDictLabel(pmPurchaseOrd.workflowStatus, 'DM0015', '申请中')}" disabled />
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="full-width">
                        <label>备注：</label>
                        <form:textarea path="newRemarks" rows="3" maxlength="300" class="fill-right" disabled="true" />
                    </li>
                </ul>
            </div>
            <div class="group-box">
                <div class="group-header" >
                    <strong class="group-title">采购明细</strong>
                    <input id="export" class="btn btn-primary" type="button" value="明细导出">
                </div>
                <table id="purTable" class="table table-striped table-bordered table-condensed" >
                    <thead>
                        <tr>
                            <th class="text-center" width="20px"><a href="javascript:;" id="addPurRow" class="disabled"><i class="icon-plus-sign"></i></a></th>
                            <th class="text-center" width="35px">No</th>
                            <th width="14%">物料号</th>
                            <th>物料名称</th>
                            <th width="8%">数量</th>
                            <th width="10%">单价</th>
                            <th width="10%">总金额</th>
                            <th width="8%">未付数量</th>
                            <th width="10%">未付金额</th>
                            <th width="8%">到货数量</th>
                            <th width="8%">未到货数量</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${pmPurchaseOrd.pmPurchaseOrdDtlList}" varStatus="status">
                            <tr>
                                <td class="text-center"><a href="javascript:;" class="delRow disabled"><i class="icon-minus-sign"></i></a></td>
                                <td class="text-center rowNo">${status.index + 1}</td>
                                <td><input type="text" value="${item.materialNo}" style="width: 100%" disabled /></td>
                                <td><input type="text" value="${item.materialName}" class="materialName" style="width: 100%" disabled /></td>
                                <td><input type="text" value="${item.num}" class="text-right " style="width: 100%" disabled /></td>
                                <td><input type="text" value="<fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/>" class="text-right" style="width: 100%" disabled /></td>
                                <td><input type="text" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/>" class="text-right" style="width: 100%" disabled /></td>
                                <td><input type="text" value="${item.unpayNum}" class="text-right" style="width: 100%" disabled /></td>
                                <td><input type="text" value="<fmt:formatNumber value="${item.unpayAmount}" pattern="#,##0.00"/>" class="text-right" style="width: 100%" disabled /></td>
                                <td><input type="text" value="${item.alArrivalNum}" class="text-right" style="width: 100%" disabled /></td>
                                <td><input type="text" value="${item.unarrivalNum}" class="text-right" style="width: 100%" disabled /></td>
                            </tr>
                        </c:forEach>
                    </tbody>    
                </table>
            </div>
            <div class="group-box">
                <div class="group-header" >
                    <strong class="group-title">发票明细</strong>
                </div>
                <table id="invoiceTable" class="table table-striped table-bordered table-condensed" style="width: 40%">
                    <thead>
                        <tr>
                            <th class="text-center" width="20px"><a href="javascript:;" id="addInvoiceRow" class="disabled"><i class="icon-plus-sign"></i></a></th>
                            <th class="text-center" width="35px">No</th>
                            <th>发票编号</th>
                            <th>发票日期</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${!empty pmPurchaseOrd.pmPurInvoiceList}"> 
                            <c:forEach var="item" items="${pmPurchaseOrd.pmPurInvoiceList}" varStatus="status">
                                <tr>
                                    <td class="text-center"><a href="javascript:;" class="delRow disabled"><i class="icon-minus-sign"></i></a></td>
                                    <td class="text-center rowNo">${status.index + 1}</td>
                                    <td><input type="text" value="${item.invoiceNo}" style="width: 100%" disabled /></td>
                                    <td><input type="text" class="input-medium Wdate" style="width: 100%;" value="<fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" disabled /></td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty pmPurchaseOrd.pmPurInvoiceList}">
                            <tr class="empty"><td class="text-center" colspan="4">无记录</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${!viewsts.canApproval}">
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
    <sys:message content="${message}"/>
    <c:if test="${!empty pmPurchaseOrd.procInsId}">
        <act:histoicFlow procInsId="${pmPurchaseOrd.procInsId}"/>
    </c:if>
</body>
</html>