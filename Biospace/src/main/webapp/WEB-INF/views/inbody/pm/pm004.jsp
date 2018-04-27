<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>采购到货入库</title>
    <meta name="decorator" content="default"/>

    <style type="text/css">
    input[type='file'] {
        display: none !important;
    }
    </style>

    <script type="text/javascript">
        $(document).ready(function() {
            // 添加画面验证
            $("#inputForm").validate({
                submitHandler: function(form){
                    //loading('正在提交，请稍等...');
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
                }
            });

            // 采购明细导出
            $("#export").on("click", function() {
                var fHtml = '<form method="get" action="' + '${ctx}' + '/pm/storage/export">';
                    fHtml += '<input type="hidden" name="purchaseNo" value="' + '${storagePur.purchaseNo}' + '">';
                    fHtml += '</form>';
                $(fHtml).appendTo('body').submit().remove();
            });

            // 确定入库点击事件绑定
            $("#confirm").on("click", function(e) {
                var isDownload = ${isDownload == null? false : isDownload};
                if (isDownload) {
                    $("#file").trigger("click");
                } else {
                    top.$.jBox.confirm("确定入库后将无法修改，请确认信息无误！","提示",function(v,h,f) {
                        if(v=="ok"){
                            $("#inputForm").submit();
                        }
                    },{buttonsFocus:1});
                    top.$('.jbox-body .jbox-icon').css('top','55px');
                }
            });

            // 入库文件导入
            $(document).on("change", "#file", function() {
                if ($(this).val()) {
                    _this = $(this);
                    top.$.jBox.confirm("确定入库后将无法修改，请确认信息无误！","提示",function(v,h,f) {
                        if(v=="ok"){
                            $("#inputForm").submit();
                        }
                        if(v=="cancel"){
                            var file = _this;  
                            file.after(file.clone().val(""));  
                            file.remove(); 
                        }
                    },{buttonsFocus:1});
                    top.$('.jbox-body .jbox-icon').css('top','55px');
                }
            });
        });
    </script>

</head>
<body>
    <h3 class="text-center page-title">采购到货入库</h3>
    <form:form id="searchForm" modelAttribute="storagePur" action="${ctx}/pm/storage/list" method="get" class="breadcrumb form-search">

        <ul class="ul-form">
            <li>
                <label>采购订单号：</label>
                <input name="purchaseNo" class="input-medium" type="text" value="${storagePur.purchaseNo}" />
            </li>
            <li>
                <label>供应商：</label>
                <input class="input-medium" type="text" value="${storagePur.supplierName}" disabled />
            </li>
            <li>
                <label>支付状态：</label>
                <input class="input-medium" type="text" value="${fns:getDictLabel(storagePur.paymentStatus, 'DM0022', '')}" disabled />
            </li>
            <li class="btns">
                <input id="submit" class="btn btn-primary" type="submit" value="查询">
            </li>
        </ul>
    </form:form>
    <form:form id="inputForm" modelAttribute="storagePur" action="${ctx}/pm/storage/save/${storagePur.purchaseNo}" method="post" class="form-search" enctype="multipart/form-data">
        <input type="hidden" name="updateDate" value="<fmt:formatDate value="${storagePur.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
        <c:if test="${isDownload == true}">
            <input id="file" type="file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" >
        </c:if>
        <table id="purTable" class="table table-striped table-bordered table-condensed">
            <thead>
                <tr>
                    <th class="text-center" width="5%">NO</th>
                    <th>采购类型</th>
                    <th>FOC</th>
                    <th>物料号</th>
                    <th>物料名称</th>
                    <th>数量</th>
                    <th width="13%">本次到货数量</th>
                    <th width="13%">库房</th>
                    <th width="13%">入库日期</th>
                    <th>已入库数量</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${!empty storagePur.pmPurchaseOrdDtlList}">
                    <c:set var="idx"  value="0"/>
                    <c:forEach var="item" items="${storagePur.pmPurchaseOrdDtlList}" varStatus="status">
                        <tr>
                            <td class="text-center">${status.index + 1}</td>
                            <td>${fns:getDictLabel(storagePur.purchaseType, 'DM0021', '')}</td>
                            <td>${fns:getDictLabel(item.ifFoc, 'yes_no', '')}</td>
                            <td>${item.materialNo}</td>
                            <td>${item.materialName}</td>
                            <td class="text-right">${item.num}</td>
                            <c:if test="${item.alArrivalNum eq item.num}">
                                <td><input type="text" class="text-right" style="width: 100%;" value="" disabled /></td>
                                <td><select class="input-medium" disabled></select></td>
                                <td><input type="text" class="Wdate" style="width: 100%;" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" disabled /></td>
                            </c:if>
                            <c:if test="${item.alArrivalNum < item.num}">
                                <td>
                                    <input type="hidden" name="storagePurList[${idx}].id" value="${item.id}" >
                                    <input type="hidden" name="storagePurList[${idx}].updateDate" value="<fmt:formatDate value="${item.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" >
                                    <input name="storagePurList[${idx}].arrivalNum" type="text" maxlength="10" min="1" max="${item.num - item.alArrivalNum}" class="text-right digits" style="width: 100%;" value="${item.arrivalNum > 0 ? item.arrivalNum : ''}" />
                                </td>
                                <td>
                                    <select name="storagePurList[${idx}].warehouse" class="input-medium" >
                                        <option value=""></option>
                                        <c:forEach items="${fns:getDictList('DM0050')}" var="sItem">
                                            <option value="${sItem.value}" ${sItem.value == item.warehouse ? 'selected' : ''} >${sItem.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input name="storagePurList[${idx}].entryDate" type="text" maxlength="10" class="Wdate" style="width: 100%;" value="<fmt:formatDate value="${item.entryDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" /></td>
                                <c:set var="idx"  value="${idx + 1}"/>
                            </c:if>
                            <td class="text-right">${item.alArrivalNum}</td>
                        </tr>
                    </c:forEach> 
                </c:if>
                <c:if test="${empty storagePur.pmPurchaseOrdDtlList}">
                    <tr><td class="text-center" colspan="10">请输入采购订单号查询后填写入库信息</td></tr>
                </c:if>
            </tbody>
        </table>
        <c:if test="${!empty storagePur.pmPurchaseOrdDtlList}"> 
            <div class="text-center">
                <input type="submit" class="btn btn-primary" value="保存" onclick="javascript:this.form.action='${ctx}/pm/storage/save/${storagePur.purchaseNo}';">
                <c:if test="${isDownload == true}">
                    <input id="export" type="button" class="btn btn-primary" value="入库Excel下载">
                </c:if>
                <c:if test="${isConfirm == true}">
                    <input id="confirm" type="button" class="btn btn-primary" value="确定入库" onclick="javascript:this.form.action='${ctx}/pm/storage/import/${storagePur.purchaseNo}';">
                </c:if>
            </div>
        </c:if>
    </form:form>
    <sys:message content="${message}"/>
</body>
</html>