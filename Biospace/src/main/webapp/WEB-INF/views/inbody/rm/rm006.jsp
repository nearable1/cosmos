<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>报价单录入</title>
    <meta name="decorator" content="default"/>

    <style type="text/css">
    .select2-result-repository {
        padding-top: 4px;
        padding-bottom: 3px;
    }
    .select2-result-item {
    }
    .select2-result-item-title {
        word-wrap: break-word;
    }
    .select2-result-item-num,
    .select2-result-item-available,
    .select2-result-item-safe,
    .select2-result-item-warehouse {
        width: auto;
        display: inline-block;
/*         margin-right: 1.8em; */
        color: #777;
        font-size: 13px;
    }
    .select2-result-item-num span,
    .select2-result-item-available span,
    .select2-result-item-safe span,
    .select2-result-item-warehouse span {
        display: inline-block;
        width: 1.8em;
        margin-right: 10px;
    }
    .select2-result-item-warehouse span {
        width: auto;
        margin-right: 0px;
    }
    .select2-results .select2-highlighted .select2-result-item-title {
        color: white;
    }
    .select2-results .select2-highlighted .select2-result-item-num,
    .select2-results .select2-highlighted .select2-result-item-available,
    .select2-results .select2-highlighted .select2-result-item-safe,
    .select2-results .select2-highlighted .select2-result-item-warehouse {
        color: #c6dcef;
    }
    .bigdrop {
        width: 420px !important;
        border: 1px solid #5897fb !important;
    }
    .upload-select-btn {
        margin-bottom: 5px;
    }
    .upload-input {
        display: none !important;
    }
    </style>

    <script type="text/javascript">
        $(document).ready(function() {

            // 添加画面验证
            var validator = $("#inputForm").validate({
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

            //select2 物料号模糊查询option
            var accSelectOpt = {
                placeholder:" ",
                allowClear: true,
                cache: true,
                ajax: {
                    url: "${ctx}/rm/quotation/materials",
                    contentType: 'charset=UTF-8',
                    dataType: 'json',
                    quietMillis: 100,
                    data: function (term, page, context) {
                        var query = {};
                        query.kw = encodeURIComponent(term);
                        query.pageNum = page;
                        query.pageSize = 10;
                        query.snNo = "${quota.snNo}";

                        // 类型
                        var type = $(this[0]).attr("data-type");
                        if (type != "" && type != null && typeof(type) != "undefined") {
                            query.type = type; // 物料类型，机器、配件等
                        }
                        return query;
                    },
                    results: function (data, page) {
                        var more = (page * 10) < data.totalCount;
                        return { results: data.items, more: more };
                    },
                    cache: true
                },
                escapeMarkup: function (markup) { return markup; }, 
                minimumInputLength: 0,
                formatResult: formatResult, 
                formatSelection: function (item) {
                    return item.id;
                },
                dropdownCssClass: function () {
                    if ($(this[0]).attr("data-type") == "2,7") {
                        return "bigdrop";
                    } else {
                        return "";
                    }
                },
                initSelection: function (element, callback) {
                    callback({id: element.val(), text: element.val()});//这里初始化
                }
            };

            //select2 物料号对应的sn信息模糊查询option
            var snNoSelectOpt = {
                placeholder:" ",
                allowClear: true,
                ajax: {
                    url: "${ctx}/rm/quotation/material/sns",
                    contentType: 'charset=UTF-8',
                    dataType: 'json',
                    quietMillis: 100,
                    data: function (term, page, context) {
                        var query = {};
                        query.kw = encodeURIComponent(term);
                        query.pageNum = page;
                        query.pageSize = 10;

                        // 物料号
                        var materialNo = $(this[0]).data("material");
                        // 库房
                        var warehouse = $(this[0]).data("warehouse");
                        if (materialNo != "" && materialNo != null && typeof(materialNo) != "undefined") {
                            query.materialNo = materialNo; // 物料号
                        }
                        if (warehouse != "" && warehouse != null && typeof(warehouse) != "undefined") {
                            query.warehouse = warehouse; // 库房
                        }
                        return query;
                    },
                    results: function (data, page) {
                        var more = (page * 10) < data.totalCount;
                        return { results: data.items, more: more };
                    },
                    cache: true
                },
                escapeMarkup: function (markup) { return markup; }, 
                minimumInputLength: 0,
                formatResult: function (item) {
                    var markup = '<div><div>' + item.id + '</div>' + '<div class="select2-result-item-text">' + item.productionDate + '</div></div>';
                    return markup;
                }, 
                formatSelection: function (item) {
                    return item.id;
                },
                initSelection: function (element, callback) {
                    callback({id: element.val(), text: element.val()});//这里初始化
                }
            };

            // 物料select下拉框选择项渲染
            function formatResult (item) {
                if (item.type == "2") {
                    var markup = '<div class="select2-result-item">';
                        markup += '<div class="select2-result-item-title">' + item.id + " / " + item.text + '</div>';
                        markup += '<div class="select2-result-item-num">数量：<span>' + defaultDispaly(item.num) + '</span></div>';
                        markup += '<div class="select2-result-item-available">可用：<span>' + defaultDispaly(item.availableQuantity) + '</span></div>';
                        markup += '<div class="select2-result-item-safe">安全库存量：<span>' + defaultDispaly(item.safetyStock) + '</span></div>';
                        markup += '<div class="select2-result-item-warehouse">库房：<span>' + defaultDispaly(item.warehouseName) + '</span></div>';
                        markup += '</div>';
                    return markup;
                } else {
                    var markup = '<div><div>' + item.id + '</div>';
                        markup += '<div class="select2-result-item-text">' + item.text + '</div></div>';
                    return markup;
                }
            }

            // 字段的画面默认显示
            function defaultDispaly(arg) {
                if (typeof(arg) == "undefined" || arg == null) {
                    return "";
                }
                return arg;
            }

            // 物料下拉框初始化
            $("#quotaDtlTbl input.materialNo").select2(accSelectOpt);
            // 新sn下拉框初始化
            $("#quotaDtlTbl input.newSnNo").select2(snNoSelectOpt);

            // 报价单明细添加事件绑定
            $("#addQtaDtlRow").on("click", function() {
                var $body = $("#quotaDtlTbl tbody");
                var rowLength = $body.find("tr").length;
                var rowid = "qd_" + (new Date()).getTime();
                var html = template("quotaDtlRow", {
                    rIdx: rowLength,
                    rowNum: (rowLength + 1),
                    id: rowid
                });
                $body.append(html);
                // 更新table
                updateTableRow("quotaDtlTbl");

                // select2初始化
                $("#" + rowid).find("select").select2({allowClear: true});
                // 物料号初始化
                $("#" + rowid).find("input.materialNo").select2(accSelectOpt);
                // 新sn选择框初始化
                $("#" + rowid).find("input.newSnNo").select2(snNoSelectOpt);
            });

            // 删除行
            $(document).on("click", "table .delRow", function() {
                var $tbody = $(this).parents("tbody");
                var $delRow = $(this).parents("tr");
                
                // 删除行
                var rowspan = $delRow.find("td").attr("rowspan");
                if (typeof(rowspan) == "undefined") {
                    // 销毁物料号下拉
                    var $sel2 = $delRow.find("select,input.remote");
                    if ($sel2.length > 0) {
                        $sel2.select2("destroy");
                    }
                    // 删除行
                    $delRow.remove();
                } else {
                    var delRowCount = parseInt(rowspan);
                    for (var i = 0; i < delRowCount; i++) {
                        $delRow.addClass("delete");
                        $delRow = $delRow.next();
                    }
                    // 销毁物料号下拉
                    var $sel2 = $tbody.find("tr.delete select,input.remote");
                    if ($sel2.length > 0) {
                        $sel2.select2("destroy");
                    }
                    // 删除所有带delete css 的行
                    $tbody.find("tr.delete").remove();
                }

                // 更新table
                updateTableRow($tbody.parent("table").attr("id"));
            });

            // table行添加或删除行更新处理
            function updateTableRow(tableid) {
                var $tbody = $("#" + tableid).find("tbody");
                if ($tbody.find("tr").length == 0) {
                    var clmCount = $tbody.parent("table").find("thead>tr>th").length;
                    var emptyRow = '<tr class="empty"><td class="text-center" colspan="' + clmCount + '">点击添加按钮添加数据</tr>';
                    $tbody.append(emptyRow);
                } else {
                    $tbody.find("tr.empty").remove();
                }
                
                // 重整行no序号
                $tbody.find("tr").each(function(index){ 
                    $(this).find("td.rowNo").html(index+1);
                });
                
                // 重整行name下标及no序号
                $tbody.find("tr.active").each(function(index){ 
                    $(this).find("td>input,td>select").each(function() {
    					if (typeof($(this).attr("name")) != "undefined") {
                            $(this).attr("name", $(this).attr("name").replace(/\[.+\]/, "[" + index + "]"));
    					}
                    });
                });
            }

            // 报价单明细【分类】下拉框change事件绑定
            $(document).on("change", "#quotaDtlTbl select.itemType", function(e) {
                var _this = $(this);
                var $row = _this.parent().parent();

                // 物料号类型设定
                if (_this.val() == "1") {
                    // 配件
                    $row.find("input.materialNo").attr("data-type", "2,7");
                    // 重置下拉框
                    $row.find("input.materialNo").select2('destroy').empty().select2(accSelectOpt);
                } else if (_this.val() == "2") {
                    // 服务
                    $row.find("input.materialNo").attr("data-type", "6");
                    // 重置下拉框
                    $row.find("input.materialNo").select2('destroy').empty().select2(accSelectOpt);
                } else {
                    $row.find("input.materialNo").removeAttr("data-type").select2("val", "");
                }

                // 物料号change
                $row.find("input.materialNo").select2("val", "").trigger("change");
            });

            // 报价单明细【物料号】下拉框change事件绑定
            $(document).on("change", "#quotaDtlTbl input.materialNo", function(e) {
                var _this = $(this);
                var $row = _this.parent().parent();
                var itemType = $row.find("select.itemType").val();

                // 物料号类型设定
                if (_this.val() == "") {
                    // 物料名称设定
                    $row.find("input.materialName").val("");
                    // 新sn
                    $row.find("input.newSnNo")
                        .removeData("material")
                        .removeData("warehouse")
                        .prop("disabled", true)
                        .select2("val", "");
                    //validator.element("[name='quotationDtlList[" + $row.index() + "].newSnNo']");
                    // 新sn生产日期
                    $row.find("input.newProductionDate").val("");
                    // 所在库房
                    $row.find("input.warehouse").val("");
                    $row.find("input.warehouseName").val("");
                    // 单价
                    $row.find("input.unitPrice").val("");
                    // 数量
                    $row.find("input.num").val("").prop("disabled", true);
                    // sn非活性
                    $row.find("input.snNo").val("").prop("disabled", true);

                } else {
                    // 物料名称设定
                    $row.find("input.materialName").val(e.added.text);
                    // 配件时
                    if (itemType == "1") {
                        if (e.added.ifSn == "1") {
                            // 为新sn设定查询条件
                            $row.find("input.newSnNo")
                                .data("material", e.added.id)
                                .data("warehouse", e.added.warehouse)
                                .prop("disabled", false)
                                .select2("val", "");
                            //validator.element("[name='quotationDtlList[" + $row.index() + "].newSnNo']");
                            // 新sn生产日期
                            $row.find("input.newProductionDate").val("");
                            // sn活性
                            $row.find("input.snNo").val("").prop("disabled", false);
                        } else {
                            // 新sn
                            $row.find("input.newSnNo")
                                .removeData("material")
                                .removeData("warehouse")
                                .prop("disabled", true)
                                .select2("val", "");
                            //validator.element("[name='quotationDtlList[" + $row.index() + "].newSnNo']");
                            // 新sn生产日期
                            $row.find("input.newProductionDate").val("");
                            // sn非活性
                            $row.find("input.snNo").val("").prop("disabled", true);
                        }
                        // 所在库房
                        $row.find("input.warehouse").val(e.added.warehouse);
                        $row.find("input.warehouseName").val(e.added.warehouseName);
                        // 单价
                        $row.find("input.unitPrice").val(e.added.unitPrice);
                        $row.find("input.unitPriceFix").val(toThousands(e.added.unitPrice));
                        // 数量
                        $row.find("input.num").val("").prop("disabled", false);

                        // 原物料号存在
                        if (e.added.oldMaterialNo) {
                            // 原物料数量大于0
                            if (typeof(e.added.oldMaterialNum) != "undefined" && e.added.oldMaterialNum != null
                                    && e.added.oldMaterialNum > 0) {
                                alertx("选择物料 " + e.added.id + " 对应的原物料 " + e.added.oldMaterialNo + " 还有库存未被消耗，请优先使用原物料！");
                            }
                        }

                        // 选中的物料的数量<安全库存量，提示警告
                        if (typeof(e.added.num) != "undefined" && e.added.num != null
                                && typeof(e.added.safetyStock) != "undefined" && e.added.safetyStock != null) {
                            if (e.added.num < e.added.safetyStock) {
                                alertx("配件 " + e.added.text + " 可用数量低于安全库存量，请及时采购！");
                            }
                        }

                        // 服务时
                    } else if (itemType == "2") {
                        // 新sn
                        $row.find("input.newSnNo")
                            .removeData("material")
                            .removeData("warehouse")
                            .prop("disabled", true)
                            .select2("val", "");
                        //validator.element("[name='quotationDtlList[" + $row.index() + "].newSnNo']");
                        // 新sn生产日期
                        $row.find("input.newProductionDate").val("");
                        // 所在库房
                        $row.find("input.warehouse").val("");
                        $row.find("input.warehouseName").val("");
                        // 单价
                        $row.find("input.unitPrice").val("");
                        // 数量
                        $row.find("input.num").val("").prop("disabled", true);
                    }
                }
                // 金额
                $row.find("input.totalAmount").val("");
                // 生产日期清空
                $row.find("input.productionDate").val("");
            });

            // 报价单明细【新sn】下拉框change事件绑定
            $(document).on("change", "#quotaDtlTbl input.newSnNo", function(e) {
                var _this = $(this);
                var $row = _this.parent().parent();

                // sn选 中为空
                if (_this.val() == "") {
                    // 生产日期设定
                    $row.find("input.newProductionDate").val("");
                } else {
                    // 生产日期设定
                    $row.find("input.newProductionDate").val(e.added.productionDate);
                }
            });

            // 报价单明细【数量】输入框change事件绑定
            $(document).on("change", "#quotaDtlTbl input.num", function(e) {
                var _this = $(this);
                var $row = _this.parent().parent();

                // sn选 中为空
                if (_this.val() == "") {
                    // 金额设定
                    $row.find("input.totalAmount").val("");
                } else {
                    var unitPrice = $row.find("input.unitPrice").val();
                    // 金额设定
                    if (unitPrice == "") {
                        $row.find("input.totalAmount").val("");
                    } else {
                        $row.find("input.totalAmount").val(numMul(_this.val(), unitPrice));
                        /* if ($row.find("select.ifWarranty").val() == '0') {
                            $row.find("input.actAmount").val(numMul(_this.val(), unitPrice));
                        } */
                    }
                }
                $row.find("input.totalAmountFix").val(toThousands($row.find("input.totalAmount").val()));
            });

            // 两数相乘
            function numMul(arg1, arg2) {
                var m=0, s1=arg1.toString(), s2=arg2.toString();
                try { m += s1.split(".")[1].length } catch(e){}
                try { m += s2.split(".")[1].length } catch(e){}
                return Number(s1.replace(".","")) * Number(s2.replace(".","")) / Math.pow(10, m);
            }

            // 报价单明细【是否保内】输入框change事件绑定
            /* $(document).on("change", "#quotaDtlTbl select.ifWarranty", function(e) {
                var _this = $(this);
                var $row = _this.parent().parent();

                // 保内
                if (_this.val() == "1") {
                    // 最终金额0设定
                    $row.find("input.actAmount").val("0");

                    // 保外
                } else if (_this.val() == "0") {
                    // 最终金额=金额
                    $row.find("input.actAmount").val($row.find("input.totalAmount").val());
                }
            }); */

            // 报价单明细【旧sn】输入框change事件绑定
            $(document).on("change", "#quotaDtlTbl input.snNo", function(e) {
                var _this = $(this);
                if (_this.val() == "") {
                    return;
                }

                $.ajax("${ctx}/rm/quotation/sn/dod/" + _this.val(), {
                    dataType: "json"
                }).done(function(data) {
                    if (data) {
                        _this.parent().parent().find("input.productionDate").val(data.productionDate);
                    }
                });
            });

            // 预报价单占用配件库存
            $("#inventory").on("click", function() {
                var fHtml = '<form method="post" action="${ctx}/rm/quotation/occupy" onsubmit="javascript:submitLockView();">';
                    fHtml += '<input type="hidden" name="preQuotaId" value="${quota.id}">';
                    fHtml += '<input type="hidden" name="type" value="1">';
                    fHtml += '</form>';
                $(fHtml).appendTo('body').submit().remove();
            });

            // 预报价单取消配件库存
            $("#cancelInventory").on("click", function() {
                var fHtml = '<form method="post" action="${ctx}/rm/quotation/occupy" onsubmit="javascript:submitLockView();">';
                    fHtml += '<input type="hidden" name="preQuotaId" value="${quota.id}">';
                    fHtml += '<input type="hidden" name="type" value="0">';
                    fHtml += '</form>';
                $(fHtml).appendTo('body').submit().remove();
            });

            // 输出报价单
            $("#exportQuotaDtl").on("click", function() {
                // 画面区分默认预报价单
                var viewCd = "pre";
                // 入口为发票时
                if (${entry == 'invoice'}) {
                    viewCd = "invoice";
                } else {
                    // 预报价单
                    if (${quota.quotationType == '1'}) {
                        viewCd = "pre";

                        // 终报价单
                    } else if (${quota.quotationType == '2'}) {
                        viewCd = "final";
                    }
                }

                var fHtml = '<form method="get" action="${ctx}/rm/quotation/export">';
                    fHtml += '<input type="hidden" name="quotaId" value="${quota.id}">';
                    fHtml += '<input type="hidden" name="viewCd" value="' + viewCd + '">';
                    fHtml += '</form>';
                $(fHtml).appendTo('body').submit().remove();
            });

            // 最终报价单出库
            $("#confirmDelivery").on("click", function() {
                var fHtml = '<form method="post" action="${ctx}/rm/quotation/outstock" class="hide" onsubmit="javascript:submitLockView();">';
                    fHtml += '<input type="hidden" name="finalQuotaId" value="${quota.id}">';
                    fHtml += '</form>';
                $(fHtml).appendTo('body').submit().remove();
            });

            // 自动获取开票信息按钮点击事件绑定
            $("#autoGetIminvoice").on("click", function() {
                // 客户id取得
                var customerId = $("#customerId").val()
                if (customerId == "") {
                    return;
                }
                // 开票信息取得
                $.ajax({
                    url: "${ctx}/rm/quotation/invoice/customer",
                    type: "get",
                    data: {"customerId": customerId},
                    dataType: "json",
                    success: function (data) {
                        if (data) {
                            // 发票类型
                            $("#invoiceType").select2("val", data.invoiceType);
                            // 开票抬头
                            $("#ivcInvoiceTitle").val(data.invoiceTitle).trigger("change");
                            // 纳税人识别号
                            $("#taxpayerIdentNo").val(data.taxpayerIdentNo);
                            // 开户行
                            $("#depositBank").val(data.depositBank);
                            // 银行账号
                            $("#bankAccount").val(data.bankAccount);
                            // 地址
                            $("#invoiceAddress").val(data.invoiceAddress);
                            // 电话
                            $("#ivcTelephone").val(data.telephone);
                        }
                    }
                });
            });

            // 开票抬头change事件绑定
            $("#ivcInvoiceTitle").on("change", function() {
                if ($(this).val() == "") {
                    return;
                }

                $("#invoiceTbl tbody tr.new td.invoiceTitle").html($(this).val());
            });

            // 发票明细添加事件绑定
            $("#addIvcDtlRow").on("click", function() {
                var $body = $("#invoiceTbl tbody");
                var rowLength = $body.find("tr").length;
                var rowid = "ivc_" + (new Date()).getTime();
                var html = template("invoiceDtlRow", {
                    rIdx: rowLength,
                    rowNum: (rowLength + 1),
                    id: rowid,
                    invoiceTitle: $("#ivcInvoiceTitle").val()
                });
                $body.append(html);
                // 更新table
                updateTableRow("invoiceTbl");
            });

    		// 税金change事件绑定
    		$(document).on("change", "input.taxFix", function() {
    			var _this = $(this);
    			var $row = _this.parent().parent();
    			var $taxFix = $row.find("input.taxFix");

    			var $tax = $row.find("input.tax");
    			
    			if ($taxFix.val() == null || $taxFix.val() == "") {
    				
    				$tax.val("");
    			} else {

        			$tax.val($taxFix.val().trim().replace(/,/g, ""));
    			}
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

            // 删除附件行
            $(document).on("click", "#attachmentTbl .delFileRow", function() {
                var _this = $(this);
                var $tbody = _this.parents("tbody");
                var $delRow = _this.parents("tr");

                // 删除行是否已上传
                if ($delRow.hasClass("uploaded")) {
                    // 已上传时
                    confirmx("确认要删除该文件吗？", function () {
                        $.ajax("${ctx}/rm/quotation/delete/file/" + _this.data("file-id"), {
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

            // 报价单提交
            $("#save").click(function () {
                // from验证
                if (!$("#inputForm").valid()) {
                    return false;
                }

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
                    if (data.success) {
                        showMessage(data.message);
                        location.href= "${ctx}" + data.url + "?id=" + data.quota.id;
                    }
                });
            });

            // 发票明细红票checkbox change事件绑定
            $(document).on("change", "#invoiceTbl input.ifRed", function(e) {
                var _this = $(this);
                var $row = _this.parents("tr");
                // 报价单明细总金额
                var totalAmount = "${quota.totalAmount}";
                // 红票是否选 中
                if ($(this).is(":checked")) {
                    // 发票金额
                    //$row.find("td.invoiceAmount").html(parseFloat("-" + totalAmount));
                    $row.find("td.invoiceAmount").html("-" + toThousands(totalAmount));
                    // 税金
                    $row.find("input.tax").val(parseFloat("-" + tax(totalAmount, 1.17, 0.17).toFixed(2)));
                    $row.find("input.taxFix").val("-" + toThousands(tax(totalAmount, 1.17, 0.17).toFixed(2)));
                } else {
                    // 发票金额
                    //$row.find("td.invoiceAmount").html(parseFloat(totalAmount));
                    $row.find("td.invoiceAmount").html(toThousands(totalAmount));
                    // 税金
                    $row.find("input.tax").val(parseFloat(tax(totalAmount, 1.17, 0.17).toFixed(2)));
                    $row.find("input.taxFix").val(toThousands(tax(totalAmount, 1.17, 0.17).toFixed(2)));
                }
            });

            // 税金计算
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

            // 报价单发票提交
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
                        url: "${ctx}/rm/quotation/invoice/apply",
                        data: $("#inputForm").serialize(),
                        success: function(oData, oStatus) {
                            if (oData.success) {
                                // $("#inputForm").submit();
                            	showMessage("操作成功");
                            	window.location.href="${ctx}/act/task/todo";
                            }
                        }
                    });
                }
            });

            // 报价单发票编辑保存
            $("#invoiceSave").on("click", function() {
                if (!$("#inputForm").valid()) {
                    return false;
                } else {
                    // 异步数据提交
                    $.ajax({
                        type: "post",
                        async: false,
                        url: "${ctx}/rm/quotation/invoice/valid",
                        data: $("#inputForm").serialize(),
                        success: function(oData, oStatus) {
                            if (oData.success) {
                                $("#inputForm").submit();
                            }
                        }
                    });
                }
            });
        });
    </script>
</head>
<body>
    <h3 class="text-center page-title">报价单录入</h3>

    <!-- 是否能对发票进行编辑权限判断 -->
<%--     <c:if test="${entry == 'invoice' && ivcMode == 'edit'}"> --%>
<%--         <shiro:hasPermission name="rm:quotaInvoice:edit"> --%>
<%--             <c:set var="isInvoiceEdit" value="${viewsts.isApprovalCompleted ? true : false}"></c:set> --%>
<%--         </shiro:hasPermission> --%>
<%--         <shiro:lacksPermission name="rm:quotaInvoice:edit"> --%>
<%--             <c:set var="isInvoiceEdit" value="false"></c:set> --%>
<%--         </shiro:lacksPermission> --%>
<%--     </c:if> --%>

    <!-- 报价单发票是否能编辑 -->
    <!-- 入口为发票 -->
    <c:if test="${entry == 'invoice'}">
        <!-- 发票可以申请 -->
        <c:if test="${quota.canInvoiceApply}">
            <!-- 申请模式时 -->
            <c:if test="${ivcMode == 'apply'}">
                <shiro:hasPermission name="rm:quotaInvoice:apply">
                    <c:if test="${viewsts.canEdit}">
                        <c:set var="isInvoiceEdit" value="true"></c:set>
                    </c:if>
                    <c:if test="${!viewsts.canEdit}">
                        <c:set var="isInvoiceEdit" value="false"></c:set>
                    </c:if>
                </shiro:hasPermission>
                <shiro:lacksPermission name="rm:quotaInvoice:apply">
                    <c:set var="isInvoiceEdit" value="false"></c:set>
                </shiro:lacksPermission>
            </c:if>
            <!-- 编辑模式 -->
            <c:if test="${ivcMode == 'edit' && fn:length(quota.invoiceDtlListGroup) > 0 }">
                <shiro:hasPermission name="rm:quotaInvoice:edit">
                    <c:set var="isInvoiceEdit" value="true"></c:set>
                </shiro:hasPermission>
                <shiro:lacksPermission name="rm:quotaInvoice:edit">
                    <c:set var="isInvoiceEdit" value="false"></c:set>
                </shiro:lacksPermission>
            </c:if>
            <c:if test="${ivcMode == 'edit' && fn:length(quota.invoiceDtlListGroup) == 0 }">
                <c:set var="isInvoiceEdit" value="false"></c:set>
            </c:if>
        </c:if>
        <!-- 发票不可以申请 -->
        <c:if test="${!quota.canInvoiceApply}">
            <c:if test="${ivcMode == 'apply'}">
                <c:if test="${viewsts.canEdit}">
                    <c:set var="isInvoiceEdit" value="true"></c:set>
                </c:if>
                <c:if test="${!viewsts.canEdit}">
                    <c:set var="isInvoiceEdit" value="false"></c:set>
                </c:if>
            </c:if>
            <c:if test="${ivcMode != 'apply'}">
                <c:set var="isInvoiceEdit" value="false"></c:set>
            </c:if>
        </c:if>
    </c:if>

    <!-- 预报价单保存url -->
    <c:if test="${quota.quotationType == '1'}">
        <c:set var="actionUrl" value="${ctx}/rm/quotation/pre/save" />
    </c:if>

    <!-- 终报价单保存url -->
    <c:if test="${quota.quotationType == '2'}">
        <c:if test="${entry == 'invoice'}">
            <c:if test="${ivcMode == 'edit'}">
                <c:set var="actionUrl" value="${ctx}/rm/quotation/invoice/save" />
            </c:if>
            <c:if test="${ivcMode == 'apply'}">
                <c:set var="actionUrl" value="${ctx}/rm/quotation/invoice/apply" />
            </c:if>
        </c:if>
        <c:if test="${entry != 'invoice'}">
            <c:set var="actionUrl" value="${ctx}/rm/quotation/final/save" />
        </c:if>
    </c:if>

    <form:form id="inputForm" modelAttribute="quota" action="${actionUrl}" method="post" class="form-search" enctype="multipart/form-data">
        <form:hidden path="id" />
        <form:hidden path="repairNo" />
        <form:hidden path="snNo" />
        <form:hidden path="ifOut" />
        <form:hidden path="ifOccupy" />
        <form:hidden path="invoice.id" />
        <form:hidden path="invoice.workflowStatus"/>
        <form:hidden path="act.businessId"/>
        <form:hidden path="act.taskId"/>
        <form:hidden path="act.procInsId"/>
        <input type="hidden" name="updateDate" value="<fmt:formatDate value="${quota.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />
        <input type="hidden" name="invoice.updateDate" value="<fmt:formatDate value="${quota.invoice.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />

        <c:if test="${entry != 'invoice'}">
            <div class="group-box group-box-first">
                <ul class="ul-form">
                    <li>
                        <label>报价单类型：</label>
                        <input value="${fns:getDictLabel(quota.quotationType, 'DM0030', '')}" class="input-medium" type="text" disabled />
                    </li>
                    <li>
                        <label>报价单编号：</label>
                        <input value="${quota.quotationNo}" class="input-medium" type="text" disabled />
                    </li>
                    <li class="clearfix"></li>
                    <li>
                        <label><span class="help-inline"><font color="red">*</font></span>报价负责人：</label>
                        <form:input path="responsiblePersonId" class="input-medium remote employee required" data-type="20" data-show="text" />
<%--                         <form:select path="responsiblePersonId" class="input-medium required"> --%>
<%--                             <form:option value="" label=""/> --%>
<%--                             <form:options items="${fns:getSqlDictList('dict_engineer')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<%--                         </form:select> --%>
                    </li>
                    <li>
                        <label><span class="help-inline"><font color="red">*</font></span>报价日期：</label>
                        <input name="quoteDate" value="<fmt:formatDate value="${quota.quoteDate}" pattern="yyyy-MM-dd"/>" type="text" class="input-medium Wdate required" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
                    </li>
                    <li>
                        <%-- <label><span class="help-inline"><font color="red">*</font></span>客户名称：</label>
                        <form:input path="customerId" id="customerId" class="remote customer input-double required" type="text" data-show="text"  maxlength="300" /> --%>
                        <label>客户名称：</label>
                        <form:input path="customerId" id="customerId" class="remote customer input-double" type="text" data-show="text"  maxlength="300" />
                    </li>
                    <li class="clearfix"></li>
                    <li>
                        <label><span class="help-inline"><font color="red">*</font></span>联系人：</label>
                        <form:input path="contactsName" class="input-medium required" type="text" maxlength="100" />
                    </li>
                    <li>
                        <label><span class="help-inline"><font color="red">*</font></span>电话：</label>
                        <form:input path="telephone" class="input-medium phone required" type="text" maxlength="50" />
                    </li>
                    <li>
                        <label>地址：</label>
                        <form:input path="address" class="input-double" type="text" maxlength="300" />
                    </li>
                    <c:if test="${quota.quotationType == '2'}">
                    <li class="clearfix"></li>
                    <li>
                        <label><span class="help-inline"><font color="red">*</font></span>收款状态：</label>
                        <form:select path="receiveStatus" class="input-medium required">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('DM0011')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </li>
                    <li>
                        <label>收款日期：</label>
                        <input name="actDateFrom" value="<fmt:formatDate value="${quota.actDateFrom}" pattern="yyyy-MM-dd"/>" type="text" class="input-medium Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
                    </li>
                    <li>
                        <label>收款抬头：</label>
                        <form:input path="invoiceTitle" class="input-double" type="text" maxlength="300" />
                    </li>
                    </c:if>
                    <li class="full-width">
                        <label>备注：</label>
                        <form:textarea path="newRemarks" rows="3" maxlength="300" class="fill-right"></form:textarea>
                    </li>
                </ul>
            </div>
            <div class="group-box">
                <div class="group-header" >
                    <strong class="group-title">报价单明细</strong>
					<div class="pull-right">
						<c:if test="${quota.quotationType == '2'}">
							<label>折扣率：</label>
							<c:if test="${quota.ifOccupy == '1' || quota.ifOut == '1'}">
								<form:hidden path="depositRate" />
		                        <label class="text-left">${fns:getDictLabel(quota.depositRate, 'DM0064', '')}</label>
	                        </c:if>
	                        <c:if test="${quota.ifOccupy != '1' && quota.ifOut != '1'}">
		                        <form:select path="depositRate" class="input-mini">
		                            <form:option value="" label=""/>
		                            <form:options items="${fns:getDictList('DM0064')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		                        </form:select>
	                        </c:if>
						</c:if>
						<label>总金额：</label>
						<label class="text-left"><fmt:formatNumber value="${quota.quotationDtlTotalAmount}" pattern="#,##0.00"/></label>
						<c:if test="${quota.quotationType == '2'}">
							<label>最终总金额：</label>
							<label class="text-left"><fmt:formatNumber value="${quota.quotationDtlActTotalAmount}" pattern="#,##0.00"/></label>
						</c:if>
					</div>
                </div>
                <div style="overflow-x: auto;overflow-y: hidden;margin-bottom: 8px;">
                <table id="quotaDtlTbl" class="table table-striped table-bordered table-condensed" style="width:1500px;">
                    <thead>
                        <tr>
                            <c:if test="${quota.ifOccupy == '1' || quota.ifOut == '1'}">
                                <th class="text-center" width="20px"><a href="javascript:;" id="addQtaDtlRow" class="disabled"><i class="icon-plus-sign"></i></a></th>
                            </c:if>
                            <c:if test="${quota.ifOccupy != '1' && quota.ifOut != '1'}">
                                <th class="text-center" width="20px"><a href="javascript:;" id="addQtaDtlRow"><i class="icon-plus-sign"></i></a></th>
                            </c:if>
                            <th class="text-center" width="35px">No</th>
                            <th width="7%">分类</th>
                            <th width="10%">物料号</th>
                            <th width="11%">物料名称</th>
                            <th width="7%">所在库房</th>
                            <th width="10%">新S/N</th>
                            <th>生产日期</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>金额</th>
                            <th width="6%">是否保内</th>
                            <c:if test="${quota.quotationType == '2'}">
                            	<th>最终金额</th>
                                <th>旧S/N</th>
                                <th>生产日期</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${quota.quotationDtlList}" var="item" varStatus="status">
                        <c:if test="${(quota.quotationType == '1' && quota.ifOccupy == '1') || (quota.quotationType == '2' && quota.ifOut == '1')}">
                            <tr>
                                <td class="text-center">
                                    <a href="javascript:;" class="delRow disabled"><i class="icon-minus-sign"></i></a>
                                    <form:hidden path="quotationDtlList[${status.index}].id"/>
                                </td>
                                <td class="text-center rowNo">${status.index + 1}</td>
                                <td>
                                    <select name="quotationDtlList[${status.index}].itemType" class="itemType required" style="width: 100%;" disabled >
                                        <option></option>
                                        <c:forEach items="${fns:getDictList('DM0031')}" var="sItem">
                                            <option value="${sItem.value}" ${sItem.value == item.itemType ? 'selected' : ''} >${sItem.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input name="quotationDtlList[${status.index}].materialNo" type="text" value="${item.materialNo}" class="remote materialNo" style="width: 100%;" disabled /></td>
                                <td><input name="quotationDtlList[${status.index}].materialName" type="text" value="${item.materialName}" class="materialName" style="width: 100%;" disabled /></td>
                                <td>
                                    <input type="text" name="quotationDtlList[${status.index}].warehouseName" value="${fns:getDictLabel(item.warehouse, 'DM0050', '')}" class="warehouseName" style="width: 100%;" disabled />
                                </td>
                                <td><input name="quotationDtlList[${status.index}].newSnNo" type="text" value="${item.newSnNo}" class="newSnNo" data-material="${item.materialNo}" data-warehouse="${item.warehouse}" style="width: 100%;" disabled /></td>
                                <td><input name="quotationDtlList[${status.index}].newProductionDate" type="text" value="<fmt:formatDate value="${item.newProductionDate}" pattern="yyyy-MM-dd"/>" class="newProductionDate" style="width: 100%;" disabled /></td>
                                <td><input name="quotationDtlList[${status.index}].unitPrice" type="hidden" value="<fmt:formatNumber value="${item.unitPrice}" pattern="#.##"/>" class="text-right unitPrice" style="width: 100%;" disabled />
                                	<input type="text" value="<fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/>" class="text-right unitPriceFix" style="width: 100%;" disabled /></td>
                                <td><input name="quotationDtlList[${status.index}].num" type="text" value="${item.num}" class="text-right num digits" style="width: 100%;" disabled /></td>
                                <td><input name="quotationDtlList[${status.index}].totalAmount" type="hidden" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#.##"/>" class="text-right totalAmount" style="width: 100%;" disabled />
                                	<input type="text" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/>" class="text-right totalAmountFix" style="width: 100%;" disabled /></td>
                                <td>
                                    <select name="quotationDtlList[${status.index}].ifWarranty" class="ifWarranty" style="width: 100%;" disabled>
                                        <option></option>
                                        <c:forEach items="${fns:getDictList('yes_no')}" var="sItem">
                                            <option value="${sItem.value}" ${sItem.value == item.ifWarranty ? 'selected' : ''} >${sItem.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <c:if test="${quota.quotationType == '2'}">
                                	<td><input name="quotationDtlList[${status.index}].actAmount" type="hidden" value="<fmt:formatNumber value="${item.actAmount}" pattern="#.##"/>" class="text-right actAmount" style="width: 100%;" disabled />
                                		<input type="text" value="<fmt:formatNumber value="${item.actAmount}" pattern="#,##0.00"/>" class="text-right actAmountFix" style="width: 100%;" disabled /></td>
                                    <td><input name="quotationDtlList[${status.index}].snNo" type="text" value="${item.snNo}" class="snNo" style="width: 100%;" disabled /></td>
                                    <td><input name="quotationDtlList[${status.index}].productionDate" type="text" value="<fmt:formatDate value="${item.productionDate}" pattern="yyyy-MM-dd"/>" class="productionDate" style="width: 100%;" disabled /></td>
                                </c:if>
                            </tr>
                        </c:if>
                        <c:if test="${(quota.quotationType == '1' && quota.ifOccupy != '1') || (quota.quotationType == '2' && quota.ifOut != '1')}">
                            <tr class="active">
                                <td class="text-center">
                                    <a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a>
                                    <form:hidden path="quotationDtlList[${status.index}].id"/>
                                </td>
                                <td class="text-center rowNo">${status.index + 1}</td>
                                <td>
                                    <select name="quotationDtlList[${status.index}].itemType" class="itemType required" style="width: 100%;">
                                        <option></option>
                                        <c:forEach items="${fns:getDictList('DM0031')}" var="sItem">
                                            <option value="${sItem.value}" ${sItem.value == item.itemType ? 'selected' : ''} >${sItem.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <c:if test="${item.itemType == '1'}">
                                    <input name="quotationDtlList[${status.index}].materialNo" type="text" value="${item.materialNo}" class="remote materialNo required" data-type="2,7" style="width: 100%;" />
                                    </c:if>
                                    <c:if test="${item.itemType == '2'}">
                                    <input name="quotationDtlList[${status.index}].materialNo" type="text" value="${item.materialNo}" class="remote materialNo required" data-type="6" style="width: 100%;" />
                                    </c:if>
                                </td>
                                <td><input name="quotationDtlList[${status.index}].materialName" type="text" value="${item.materialName}" class="materialName" style="width: 100%;" disabled /></td>
                                <td>
                                    <input type="hidden" name="quotationDtlList[${status.index}].warehouse" value="${item.warehouse}" class="warehouse" />
                                    <input name="quotationDtlList[${status.index}].warehouseName" type="text" value="${fns:getDictLabel(item.warehouse, 'DM0050', '')}" class="warehouseName" style="width: 100%;" disabled />
                                </td>
                                <td>
                                    <!-- 配件时 -->
                                    <c:if test="${item.itemType == '1'}">
                                        <!-- 配件有sn时 -->
                                        <c:if test="${item.ifSn == '1'}">
                                            <input name="quotationDtlList[${status.index}].newSnNo" type="text" value="${item.newSnNo}" class="newSnNo required" data-material="${item.materialNo}" data-warehouse="${item.warehouse}" style="width: 100%;" />
                                        </c:if>
                                        <!-- 配件无sn时 -->
                                        <c:if test="${item.ifSn != '1'}">
                                            <input name="quotationDtlList[${status.index}].newSnNo" type="text" class="newSnNo required" style="width: 100%;" disabled />
                                        </c:if>
                                        
                                    </c:if>
                                    <!-- 不为配件时 -->
                                    <c:if test="${item.itemType != '1'}">
                                        <input name="quotationDtlList[${status.index}].newSnNo" type="text" class="newSnNo required" style="width: 100%;" disabled />
                                    </c:if>
                                </td>
                                <td><input name="quotationDtlList[${status.index}].newProductionDate" type="text" value="<fmt:formatDate value="${item.newProductionDate}" pattern="yyyy-MM-dd"/>" class="newProductionDate" style="width: 100%;" disabled /></td>
                                <c:if test="${item.itemType == '1'}">
                                    <td><input name="quotationDtlList[${status.index}].unitPrice" type="hidden" value="<fmt:formatNumber value="${item.unitPrice}" pattern="#.##"/>" class="text-right unitPrice" style="width: 100%;" disabled />
                                    	<input type="text" value="<fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/>" class="text-right unitPriceFix" style="width: 100%;" disabled /></td>
                                    <td><input name="quotationDtlList[${status.index}].num" type="text" value="${item.num}" class="text-right num required digits" style="width: 100%;" /></td>
                                    <td><input name="quotationDtlList[${status.index}].totalAmount" type="hidden" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#.##"/>" class="text-right totalAmount" style="width: 100%;" disabled />
                                    	<input type="text" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/>" class="text-right totalAmountFix" style="width: 100%;" disabled /></td>
                                </c:if>
                                <c:if test="${item.itemType != '1'}">
                                    <td><input name="quotationDtlList[${status.index}].unitPrice" type="hidden" class="text-right unitPrice" style="width: 100%;" disabled />
                                    	<input type="text" class="text-right unitPriceFix" style="width: 100%;" disabled /></td>
                                    <td><input name="quotationDtlList[${status.index}].num" type="text" class="text-right num required digits" style="width: 100%;" disabled /></td>
                                    <td><input name="quotationDtlList[${status.index}].totalAmount" type="hidden" class="text-right totalAmount" style="width: 100%;" disabled />
                                    	<input type="text" class="text-right totalAmountFix" style="width: 100%;" disabled /></td>
                                </c:if>
                                <td>
                                    <select name="quotationDtlList[${status.index}].ifWarranty" class="ifWarranty required" style="width: 100%;">
                                        <option></option>
                                        <c:forEach items="${fns:getDictList('yes_no')}" var="sItem">
                                            <option value="${sItem.value}" ${sItem.value == item.ifWarranty ? 'selected' : ''} >${sItem.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <c:if test="${quota.quotationType == '2'}">
                                	<td><input name="quotationDtlList[${status.index}].actAmount" type="hidden" value="<fmt:formatNumber value="${item.actAmount}" pattern="#.##"/>" class="text-right actAmount" style="width: 100%;" disabled/>
                                		<input type="text" value="<fmt:formatNumber value="${item.actAmount}" pattern="#,##0.00"/>" class="text-right actAmountFix" style="width: 100%;" disabled/></td>
                                    <c:if test="${item.ifSn == '1'}">
                                        <td><input name="quotationDtlList[${status.index}].snNo" type="text" value="${item.snNo}" class="snNo" style="width: 100%;" /></td>
                                        <td><input name="quotationDtlList[${status.index}].productionDate" type="text" value="<fmt:formatDate value="${item.productionDate}" pattern="yyyy-MM-dd"/>" class="productionDate" style="width: 100%;" disabled /></td>
                                    </c:if>
                                    <c:if test="${item.ifSn != '1'}">
                                        <td><input name="quotationDtlList[${status.index}].snNo" type="text" value="${item.snNo}" class="snNo" style="width: 100%;" disabled /></td>
                                        <td><input name="quotationDtlList[${status.index}].productionDate" type="text" value="<fmt:formatDate value="${item.productionDate}" pattern="yyyy-MM-dd"/>" class="productionDate" style="width: 100%;" disabled /></td>
                                    </c:if>
                                </c:if>
                            </tr>
                        </c:if>
                    </c:forEach>
                    <c:if test="${empty quota.quotationDtlList}">
                        <tr class="active">
                            <td class="text-center"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>
                            <td class="text-center rowNo">1</td>
                            <td>
                                <select name="quotationDtlList[0].itemType" class="itemType required" style="width: 100%;" >
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('DM0031')}" var="sItem">
                                        <option value="${sItem.value}">${sItem.label}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><input name="quotationDtlList[0].materialNo" type="text" class="remote materialNo required" style="width: 100%;" /></td>
                            <td><input name="quotationDtlList[0].materialName" type="text" class="materialName" style="width: 100%;" disabled /></td>
                            <td>
                                <input name="quotationDtlList[0].warehouse" type="hidden" value="" class="warehouse" />
                                <input name="quotationDtlList[0].warehouseName" type="text" value="${fns:getDictLabel(item.warehouse, 'DM0050', '')}" class="warehouseName" style="width: 100%;" disabled />
                            </td>
                            <td><input name="quotationDtlList[0].newSnNo" type="text" class="newSnNo required" style="width: 100%;" disabled /></td>
                            <td><input name="quotationDtlList[0].newProductionDate" type="text" class="newProductionDate" style="width: 100%;" disabled /></td>
                            <td><input name="quotationDtlList[0].unitPrice" type="hidden" class="text-right unitPrice" style="width: 100%;" disabled />
                            	<input type="text" class="text-right unitPriceFix" style="width: 100%;" disabled /></td>
                            <td><input name="quotationDtlList[0].num" type="text" class="text-right num required digits" style="width: 100%;" disabled /></td>
                            <td><input name="quotationDtlList[0].totalAmount" type="hidden" class="text-right totalAmount required" style="width: 100%;" disabled />
                            	<input type="text" class="text-right totalAmountFix required" style="width: 100%;" disabled /></td>
                            <td>
                                <select name="quotationDtlList[0].ifWarranty" class="ifWarranty required" style="width: 100%;" >
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('yes_no')}" var="sItem">
                                        <option value="${sItem.value}" ${sItem.value == quota.defaultWarranty ? 'selected' : ''}>${sItem.label}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <c:if test="${quota.quotationType == '2'}">
                            	<td><input name="quotationDtlList[0].actAmount" type="hidden" class="text-right actAmount" style="width: 100%;" disabled/>
                            		<input type="text" class="text-right actAmountFix" style="width: 100%;" disabled/></td>
                                <td><input name="quotationDtlList[0].snNo" type="text" class="snNo required" style="width: 100%;" disabled /></td>
                                <td><input name="quotationDtlList[0].productionDate" type="text" class="productionDate" style="width: 100%;" disabled /></td>
                            </c:if>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
                </div>
                <c:if test="${!empty quota.id}">
                    <div class="text-center">
                        <c:if test="${quota.quotationType == '1'}">
                            <shiro:hasAnyPermissions name="rm:quotation:edit">
                                <c:if test="${quota.ifOccupy != '1'}">
                                    <input id="inventory" type="button" class="btn btn-primary" value="占用配件库存" >
                                </c:if>
                                <c:if test="${quota.ifOccupy == '1'}">
                                    <input id="cancelInventory" type="button" class="btn btn-primary" value="取消配件库存" >
                                </c:if>
                            </shiro:hasAnyPermissions>
                        </c:if>
                        <c:if test="${quota.quotationType == '1'}">
                        	<input id="exportQuotaDtl" type="button" class="btn btn-primary" value="输出报价单" >
                        </c:if>
                        <c:if test="${quota.quotationType == '2'}">
                        	<input id="exportQuotaDtl" type="button" class="btn btn-primary" value="输出维修合同" >
                        </c:if>
                        <c:if test="${quota.quotationType == '2' && quota.ifOut == '0'}">
                            <shiro:hasAnyPermissions name="rm:finalQuotation:storage">
                                <input id="confirmDelivery" type="button" class="btn btn-primary" value="确认出库" >
                            </shiro:hasAnyPermissions>
                        </c:if>
                    </div>
                </c:if>
            </div>
            <div class="group-box group-box-last">
                <div class="group-header" >
                    <strong class="group-title">附件上传</strong>
                </div>
                <div class="upload-select-btn">
                    <button type="button" class="btn btn-primary"><span>选取文件</span></button>
                    <input type="file" multiple="multiple" class="upload-input">
                </div>
                <div><font color="red">最大上传文件大小：10M</font></div>
                <table id="attachmentTbl" class="table table-striped table-bordered table-condensed" style="width:50%;">
                    <thead>
                        <tr>
                            <th class="text-center" width="20px"><a href="javascript:;" class="disabled"><i class="icon-plus-sign"></i></a></th>
                            <th class="text-center" width="35px">No</th>
                            <th>文件名</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${quota.attachmentsList}" var="item" varStatus="status">
                        <tr class="uploaded">
                            <td class="text-center"><a href="javascript:;" class="delFileRow" data-file-id="${item.id}"><i class="icon-minus-sign"></i></a></td>
                            <td class="text-center rowNo">${status.index + 1}</td>
                            <td>
                                <a class="link" href="${ctx}/rm/quotation/download/file/${item.id}">${item.fileName}.${item.fileType}</a>
                                <span class="pull-right"><i class="icon-ok icon-white"></i></span>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty quota.attachmentsList}">
                        <tr class="empty">
                            <td class="text-center" colspan="3">请选择附件</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${entry == 'invoice'}">
            <div class="group-box group-box-first">
                <ul class="ul-form">
                    <li>
                        <label>报价单类型：</label>
                        <input value="${fns:getDictLabel(quota.quotationType, 'DM0030', '')}" class="input-medium" type="text" disabled />
                    </li>
                    <li>
                        <label>报价单编号：</label>
                        <input value="${quota.quotationNo}" class="input-medium" type="text" disabled />
                    </li>
                    <li class="clearfix"></li>
                    <li>
                        <label>报价负责人：</label>
                        <form:input path="responsiblePersonId" class="input-medium remote employee required"  data-type="20" data-show="text" disabled="true" />
<%--                         <form:select path="responsiblePersonId" class="input-medium required" disabled="true"> --%>
<%--                             <form:option value="" label=""/> --%>
<%--                             <form:options items="${fns:getSqlDictList('dict_engineer')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<%--                         </form:select> --%>
                    </li>
                    <li>
                        <label>报价日期：</label>
                        <input name="quoteDate" value="<fmt:formatDate value="${quota.quoteDate}" pattern="yyyy-MM-dd"/>" type="text" class="input-medium Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" disabled/>
                    </li>
                    <li>
                        <label>客户名称：</label>
                        <%-- <form:input path="customerId" id="customerId" class="remote customer input-double required" type="text" data-show="text" maxlength="300" disabled="true"/> --%>
                        <form:input path="customerId" id="customerId" class="remote customer input-double" type="text" data-show="text" maxlength="300" disabled="true"/>
                    </li>
                    <li class="clearfix"></li>
                    <li>
                        <label>联系人：</label>
                        <form:input path="contactsName" class="input-medium required" type="text" maxlength="100" disabled="true"/>
                    </li>
                    <li>
                        <label>电话：</label>
                        <form:input path="telephone" class="input-medium phone required" type="text" maxlength="50" disabled="true" />
                    </li>
                    <li>
                        <label>地址：</label>
                        <form:input path="address" class="input-double" type="text" maxlength="300" disabled="true" />
                    </li>
                    <c:if test="${quota.quotationType == '2'}">
                    <li class="clearfix"></li>
                    <li>
                        <label>收款状态：</label>
                        <form:select path="receiveStatus" class="input-medium required" disabled="true">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('DM0011')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </li>
                    <li>
                        <label>收款日期：</label>
                        <input name="actDateFrom" value="<fmt:formatDate value="${quota.actDateFrom}" pattern="yyyy-MM-dd"/>" type="text" class="input-medium Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" disabled />
                    </li>
                    <li>
                        <label>收款抬头：</label>
                        <form:input path="invoiceTitle" class="input-double" type="text" maxlength="300" disabled="true" />
                    </li>
                    </c:if>
                    <li class="full-width">
                        <label>备注：</label>
                        <form:textarea path="newRemarks" rows="3" maxlength="300" class="fill-right" disabled="true"></form:textarea>
                    </li>
                </ul>
            </div>
            <div class="group-box">
                <div class="group-header" >
                    <strong class="group-title">报价单明细</strong>
					<div class="pull-right">
						<c:if test="${quota.quotationType == '2'}">
							<label>折扣率：</label>
							<form:hidden path="depositRate" />
		                    <label class="text-left">${fns:getDictLabel(quota.depositRate, 'DM0064', '')}</label>
						</c:if>
						<label>总金额：</label>
						<label class="text-left"><fmt:formatNumber value="${quota.quotationDtlTotalAmount}" pattern="#,##0.00"/></label>
						<c:if test="${quota.quotationType == '2'}">
							<label>最终总金额：</label>
							<label class="text-left"><fmt:formatNumber value="${quota.quotationDtlActTotalAmount}" pattern="#,##0.00"/></label>
						</c:if>
					</div>
                </div>
                <div class="auto-scroll-x">
                <table id="quotaDtlTbl" class="table table-striped table-bordered table-condensed" style="width:1500px;">
                    <thead>
                        <tr>
                            <th class="text-center" width="20px"><a href="javascript:;" id="addQtaDtlRow" class="disabled"><i class="icon-plus-sign"></i></a></th>
                            <th class="text-center" width="35px">No</th>
                            <th width="7%">分类</th>
                            <th width="10%">物料号</th>
                            <th width="11%">物料名称</th>
                            <th width="7%">所在库房</th>
                            <th width="10%">新S/N</th>
                            <th>生产日期</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>金额</th>
                            <th width="6%">是否保内</th>
                            <c:if test="${quota.quotationType == '2'}">
                            <th>最终金额</th>
                            <th>旧S/N</th>
                            <th>生产日期</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${quota.quotationDtlList}" var="item" varStatus="status">
                        <tr>
                            <td class="text-center"><a href="javascript:;" class="delRow disabled"><i class="icon-minus-sign"></i></a></td>
                            <td class="text-center rowNo">${status.index + 1}</td>
                            <td>
                                <select name="quotationDtlList[${status.index}].itemType" class="itemType required" style="width: 100%;" disabled>
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('DM0031')}" var="sItem">
                                        <option value="${sItem.value}" ${sItem.value == item.itemType ? 'selected' : ''} >${sItem.label}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <c:if test="${item.itemType == '1'}">
                                <input name="quotationDtlList[${status.index}].materialNo" type="text" value="${item.materialNo}" class="remote materialNo required" data-type="2,7" style="width: 100%;" disabled/>
                                </c:if>
                                <c:if test="${item.itemType == '2'}">
                                <input name="quotationDtlList[${status.index}].materialNo" type="text" value="${item.materialNo}" class="remote materialNo required" data-type="6" style="width: 100%;" disabled/>
                                </c:if>
                            </td>
                            <td><input name="quotationDtlList[${status.index}].materialName" type="text" value="${item.materialName}" class="materialName" style="width: 100%;" disabled /></td>
                            <td>
                                <input name="quotationDtlList[${status.index}].warehouseName" type="text" value="${fns:getDictLabel(item.warehouse, 'DM0050', '')}" class="warehouseName" style="width: 100%;" disabled />
                            </td>
                            <td><input name="quotationDtlList[${status.index}].newSnNo" type="text" value="${item.newSnNo}" class="newSnNo required" data-material="${item.materialNo}" data-warehouse="${item.warehouse}" style="width: 100%;" disabled/></td>
                            <td><input name="quotationDtlList[${status.index}].newProductionDate" type="text" value="<fmt:formatDate value="${item.newProductionDate}" pattern="yyyy-MM-dd"/>" class="newProductionDate" style="width: 100%;" disabled /></td>
                            <td><input name="quotationDtlList[${status.index}].unitPrice" type="hidden" value="<fmt:formatNumber value="${item.unitPrice}" pattern="#.##"/>" class="text-right unitPrice" style="width: 100%;" disabled />
                            	<input type="text" value="<fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00"/>" class="text-right unitPriceFix" style="width: 100%;" disabled /></td>
                            <td><input name="quotationDtlList[${status.index}].num" type="text" value="${item.num}" class="text-right num digits required" style="width: 100%;" disabled /></td>
                            <td><input name="quotationDtlList[${status.index}].totalAmount" type="hidden" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#.##"/>" class="text-right totalAmount" style="width: 100%;" disabled />
                            	<input type="text" value="<fmt:formatNumber value="${item.totalAmount}" pattern="#,##0.00"/>" class="text-right totalAmountFix" style="width: 100%;" disabled /></td>
                            <td>
                                <select name="quotationDtlList[${status.index}].ifWarranty" class="ifWarranty required" style="width: 100%;" disabled>
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('yes_no')}" var="sItem">
                                        <option value="${sItem.value}" ${sItem.value == item.ifWarranty ? 'selected' : ''} >${sItem.label}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><input name="quotationDtlList[${status.index}].actAmount" type="hidden" value="<fmt:formatNumber value="${item.actAmount}" pattern="#.##"/>" class="text-right actAmount" style="width: 100%;" disabled />
                            	<input type="text" value="<fmt:formatNumber value="${item.actAmount}" pattern="#,##0.00"/>" class="text-right actAmountFix" style="width: 100%;" disabled /></td>
                            <td><input name="quotationDtlList[${status.index}].snNo" type="text" value="${item.snNo}" class="snNo required" style="width: 100%;" disabled /></td>
                            <td><input name="quotationDtlList[${status.index}].productionDate" type="text" value="<fmt:formatDate value="${item.productionDate}" pattern="yyyy-MM-dd"/>" class="productionDate" style="width: 100%;" disabled/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </div>
                <c:if test="${!empty quota.id}">
                    <div class="text-center">
                        <input id="exportQuotaDtl" type="button" class="btn btn-primary" value="输出报价单" >
                    </div>
                </c:if>
            </div>
            <c:if test="${isInvoiceEdit}">
                <div class="group-box">
                    <div class="group-header" >
                        <strong class="group-title">开票管理</strong>
                        <input id="autoGetIminvoice" type="button" class="btn btn-primary" value="自动获取开票信息">
                    </div>
                    <ul class="ul-form">
                        <li>
                            <label><span class="help-inline"><font color="red">*</font></span>发票类型：</label>
                            <form:select id="invoiceType" path="invoice.invoiceType" class="input-medium required">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('DM0004')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                        </li>
                        <li>
                            <label><span class="help-inline"><font color="red">*</font></span>开票抬头：</label>
                            <form:input id="ivcInvoiceTitle" path="invoice.invoiceTitle" class="input-medium required" type="text"/>
                        </li>
                        <li>
                            <label><span class="help-inline" style="padding-left: 0px;"><font color="red">*</font></span>纳税人识别号：</label>
                            <form:input id="taxpayerIdentNo" path="invoice.taxpayerIdentNo" class="input-medium required" type="text"/>
                        </li>
                        <li class="clearfix"></li>
                        <li>
                            <label><span class="help-inline"><font color="red">*</font></span>开户行：</label>
                            <form:input id="depositBank" path="invoice.depositBank" class="input-medium required" type="text" />
                        </li>
                        <li>
                            <label><span class="help-inline"><font color="red">*</font></span>银行账号：</label>
                            <form:input id="bankAccount" path="invoice.bankAccount" class="input-medium required" type="text" />
                        </li>
                        <li>
                            <label><span class="help-inline"><font color="red">*</font></span>电话：</label>
                            <form:input id="ivcTelephone" path="invoice.telephone" class="input-medium phone required" type="text" />
                        </li>
                        <li>
                            <label>地址：</label>
                            <form:input id="invoiceAddress" path="invoice.invoiceAddress" class="input-medium" type="text" />
                        </li>
                        <li class="clearfix"></li>
                        <li>
                            <label>取票方式：</label>
                            <form:select path="invoice.ticketMethod" class="input-medium">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('DM0048')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                        </li>
                        <li>
                            <label>收件人：</label>
                            <form:input path="invoice.recipients" class="input-medium" type="text" value=""/>
                        </li>
                        <li>
                            <label>联系电话：</label>
                            <form:input path="invoice.repTelephone" class="input-medium phone" type="text" value=""/>
                        </li>
                        <li>
                            <label>收件地址：</label>
                            <form:input path="invoice.address" class="input-medium" type="text" value=""/>
                        </li>
                        <li class="full-width">
                            <label>开票说明：</label>
                            <form:textarea path="invoice.newRemarks" class="fill-right" rows="3" value="" maxlength="300"/>
                        </li>
                    </ul>
                    <hr style="margin:10px 0">
                </div>
	            <div class="group-box">
	                <div class="group-header" >
	                    <strong class="group-title">发票明细</strong>
						<div class="pull-right">
							<label>总金额：</label>
							<label class="text-left"><fmt:formatNumber value="${quota.imInvoiceTotalAmount}" pattern="#,##0.00"/></label>
						</div>
	                </div>
                    <table id="invoiceTbl" class="table table-striped table-bordered table-condensed">
                        <thead>
                            <tr>
                                <!-- 编辑时不可添加新的发票 -->
                                <th class="text-center" width="20px"><a href="javascript:;" id="addIvcDtlRow" class="${ivcMode == 'edit' ? 'disabled' : ''}"><i class="icon-plus-sign"></i></a></th>
                                <th class="text-center" width="35px">No</th>
                                <th width="35px">红票</th>
                                <th width="8%">发票金额</th>
                                <th width="15%">发票抬头</th>
                                <th width="10%">税金</th>
                                <th width="12%">开票日期</th>
                                <th>发票号码</th>
                                <th>快递编号</th>
                                <th width="12%">快递公司</th>
                                <th width="8%">申请状态</th>
                            </tr>
                        </thead>
                        <tbody>
                        <!-- 申请模式，全新申请时，所有的都不能不编辑，只能添加 -->
                        <c:if test="${ivcMode == 'apply' && empty quota.act.procInsId}">
                            <c:set var="rowCount" value="0"></c:set>
                            <c:forEach items="${quota.invoiceDtlListGroup}" var="invoiceDtlList" varStatus="groupsts">
                                <c:forEach items="${invoiceDtlList}" var="item" varStatus="status">
                                    <tr>
                                        <c:if test="${item.lineNo == '1'}">
                                            <td class="text-center" rowspan="${fn:length(invoiceDtlList)}">
                                                <a href="javascript:;" class="delRow disabled"><i class="icon-minus-sign"></i></a>
                                            </td>
                                        </c:if>
                                        <c:set var="rowCount" value="${rowCount + 1}"></c:set>
                                        <td class="text-center rowNo">${rowCount}</td>
                                        <td class="text-center"><input type="checkbox" name="invoiceDtlList[${status.index}].ifRed" class="ifRed" value="1" ${item.ifRed == '1' ? 'checked' : ''} disabled ></td>
                                        <td class="text-right invoiceAmount"><fmt:formatNumber value="${item.invoiceAmount}" pattern="#,##0.00"/></td>
                                        <td class="invoiceTitle">${item.invoiceTitle}</td>
                                        <td><input type="hidden" value="<fmt:formatNumber value="${item.tax}" pattern="#.##"/>" class="text-right tax" style="width: 100%;" disabled />
                                        	<input type="text" value="<fmt:formatNumber value="${item.tax}" pattern="#,##0.00"/>" class="text-right taxFix" style="width: 100%;" disabled /></td>
                                        <td><input type="text" value="<fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM-dd"/>" class="invoiceDate Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 100%;" disabled /></td>
                                        <td><input type="text" value="${item.invoiceNo}" class="invoiceNo" style="width: 100%;" disabled /></td>
                                        <td><input type="text" value="${item.expressNo}" class="expressNo" style="width: 100%;" disabled /></td>
                                        <td><input type="text" value="${item.expressCompany}" class="expressCompany" style="width: 100%;" disabled /></td>
                                        <td>
                                            ${fns:getDictLabel(item.workflowStatus, 'DM0043', '')}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            <!-- 没有申请过发票时，默认一条空的可填 -->
                            <c:if test="${empty quota.invoiceDtlListGroup}">
                                <tr class="active new">
                                    <td class="text-center"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>
                                    <td class="text-center rowNo">${status.index + 1}</td>
                                    <td class="text-center"><input type="checkbox" name="invoiceDtlList[0].ifRed" class="ifRed" value="1"></td>
                                    <td class="text-right invoiceAmount"><fmt:formatNumber value="${quota.totalAmount}" pattern="#,##0.00"/></td>
                                    <td class="invoiceTitle">${quota.invoice.invoiceTitle}</td>
                                    <td><input name="invoiceDtlList[0].tax" type="hidden" class="text-right tax" value="<fmt:formatNumber value="${quota.defaultTax}" pattern="#.##"/>" style="width: 100%;" disabled />
                                    	<input type="text" class="text-right taxFix" value="<fmt:formatNumber value="${quota.defaultTax}" pattern="#,##0.00"/>" style="width: 100%;" disabled /></td>
                                    <td><input name="invoiceDtlList[0].invoiceDate" type="text" class="invoiceDate Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 100%;" /></td>
                                    <td><input name="invoiceDtlList[0].invoiceNo" type="text" class="invoiceNo" style="width: 100%;" /></td>
                                    <td><input name="invoiceDtlList[0].expressNo" type="text" class="expressNo" style="width: 100%;" /></td>
                                    <td><input name="invoiceDtlList[0].expressCompany" type="text" class="expressCompany" style="width: 100%;" /></td>
                                    <td></td>
                                </tr>
                            </c:if>
                        </c:if>
                        <!-- 申请模式，并且处理申请中，已经审核的不能编辑，申请退回时可编辑 -->
                        <c:if test="${ivcMode == 'apply' && !empty quota.act.procInsId}">
                            <c:set var="rowCount" value="0"></c:set>
                            <c:forEach items="${quota.invoiceDtlListGroup}" var="invoiceDtlList" varStatus="group">
                                <c:forEach items="${invoiceDtlList}" var="item" varStatus="status">
                                    <c:if test="${item.workflowStatus == '50'}">
                                         <tr>
                                             <c:if test="${item.lineNo == '1'}">
                                                 <td class="text-center" rowspan="${fn:length(invoiceDtlList)}">
                                                     <a href="javascript:;" class="delRow disabled"><i class="icon-minus-sign"></i></a>
                                                 </td>
                                             </c:if>
                                             <c:set var="rowCount" value="${rowCount + 1}"></c:set>
                                             <td class="text-center rowNo">${rowCount}</td>
                                             <td class="text-center"><input type="checkbox" name="invoiceDtlList[${status.index}].ifRed" class="ifRed" value="1" ${item.ifRed == '1' ? 'checked' : ''} disabled ></td>
                                             <td class="text-right invoiceAmount"><fmt:formatNumber value="${item.invoiceAmount}" pattern="#,##0.00"/></td>
                                             <td class="invoiceTitle">${item.invoiceTitle}</td>
                                             <td><input type="hidden" value="<fmt:formatNumber value="${item.tax}" pattern="#.##"/>" class="text-right tax" style="width: 100%;" disabled />
                                             	<input type="text" value="<fmt:formatNumber value="${item.tax}" pattern="#,##0.00"/>" class="text-right taxFix" style="width: 100%;" disabled /></td>
                                             <td><input type="text" value="<fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM-dd"/>" class="invoiceDate Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 100%;" disabled /></td>
                                             <td><input type="text" value="${item.invoiceNo}" class="invoiceNo" style="width: 100%;" disabled /></td>
                                             <td><input type="text" value="${item.expressNo}" class="expressNo" style="width: 100%;" disabled /></td>
                                             <td><input type="text" value="${item.expressCompany}" class="expressCompany" style="width: 100%;" disabled /></td>
                                             <td>
                                                 ${fns:getDictLabel(item.workflowStatus, 'DM0043', '')}
                                             </td>
                                         </tr>
                                    </c:if>
                                    <c:if test="${item.workflowStatus != '50'}">
                                        <tr class="active">
                                            <c:if test="${item.lineNo == '1'}">
                                                <td class="text-center" rowspan="${fn:length(invoiceDtlList)}">
                                                    <a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a>
                                                </td>
                                            </c:if>
                                            <c:set var="rowCount" value="${rowCount + 1}"></c:set>
                                            <td class="text-center rowNo">${rowCount}</td>
                                            <td class="text-center"><input type="checkbox" name="invoiceDtlList[${status.index}].ifRed" class="ifRed" value="1" ${item.ifRed == '1' ? 'checked' : ''} disabled ></td>
                                            <td class="text-right invoiceAmount"><fmt:formatNumber value="${item.invoiceAmount}" pattern="#,##0.00"/></td>
                                            <td class="invoiceTitle">${item.invoiceTitle}</td>
                                            <td><input name="invoiceDtlList[${status.index}].tax" type="hidden" value="<fmt:formatNumber value="${item.tax}" pattern="#.##"/>" class="text-right tax" style="width: 100%;" disabled />
                                            	<input type="text" value="<fmt:formatNumber value="${item.tax}" pattern="#,##0.00"/>" class="text-right taxFix" style="width: 100%;" disabled /></td>
                                            <td><input name="invoiceDtlList[${status.index}].invoiceDate" type="text" value="<fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM-dd"/>" class="invoiceDate Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 100%;" disabled /></td>
                                            <td><input name="invoiceDtlList[${status.index}].invoiceNo" type="text" value="${item.invoiceNo}" class="invoiceNo" style="width: 100%;" disabled /></td>
                                            <td><input name="invoiceDtlList[${status.index}].expressNo" type="text" value="${item.expressNo}" class="expressNo" style="width: 100%;" disabled /></td>
                                            <td><input name="invoiceDtlList[${status.index}].expressCompany" type="text" value="${item.expressCompany}" class="expressCompany" style="width: 100%;" disabled /></td>
                                            <td>
                                                ${fns:getDictLabel(item.workflowStatus, 'DM0043', '')}
                                                <input type="hidden" name="invoiceDtlList[${status.index}].id" value="${item.id}" >
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </c:if>
                        <c:if test="${ivcMode == 'edit'}">
                            <c:set var="rowCount" value="0"></c:set>
                            <c:forEach items="${quota.invoiceDtlListGroup}" var="invoiceDtlList" varStatus="group">
                                <c:forEach items="${invoiceDtlList}" var="item" varStatus="status">
                                    <tr class="active">
                                        <c:if test="${item.lineNo == '1'}">
                                            <td class="text-center" rowspan="${fn:length(invoiceDtlList)}">
                                                <a href="javascript:;" class="delRow disabled"><i class="icon-minus-sign"></i></a>
                                            </td>
                                        </c:if>
                                        <c:set var="rowCount" value="${rowCount + 1}"></c:set>
                                        <td class="text-center rowNo">${rowCount}</td>
                                        <!-- 编辑时不可改变发票性质 -->
                                        <td class="text-center"><input type="checkbox" name="invoiceDtlList[${rowCount - 1}].ifRed" class="ifRed" value="1" ${item.ifRed == '1' ? 'checked' : ''} disabled ></td>
                                        <td class="text-right invoiceAmount"><fmt:formatNumber value="${item.invoiceAmount}" pattern="#,##0.00"/></td>
                                        <td class="invoiceTitle">${item.invoiceTitle}</td>
                                        <td><input name="invoiceDtlList[${rowCount - 1}].tax" type="hidden" value="<fmt:formatNumber value="${item.tax}" pattern="#.##"/>" class="text-right tax" style="width: 100%;" />
                                        	<input type="text" value="<fmt:formatNumber value="${item.tax}" pattern="#,##0.00"/>" class="text-right taxFix" style="width: 100%;" onblur="numToStr(this);" onfocus="strToNum(this);" /></td>
                                        <td><input name="invoiceDtlList[${rowCount - 1}].invoiceDate" type="text" value="<fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM-dd"/>" class="invoiceDate Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 100%;" /></td>
                                        <td><input name="invoiceDtlList[${rowCount - 1}].invoiceNo" type="text" value="${item.invoiceNo}" class="invoiceNo" style="width: 100%;" /></td>
                                        <td><input name="invoiceDtlList[${rowCount - 1}].expressNo" type="text" value="${item.expressNo}" class="expressNo" style="width: 100%;" /></td>
                                        <td><input name="invoiceDtlList[${rowCount - 1}].expressCompany" type="text" value="${item.expressCompany}" class="expressCompany" style="width: 100%;" /></td>
                                        <td>
                                            ${fns:getDictLabel(item.workflowStatus, 'DM0043', '')}
                                            <input type="hidden" name="invoiceDtlList[${rowCount - 1}].id" value="${item.id}" >
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <c:if test="${!isInvoiceEdit}">
                <div class="group-box">
                    <div class="group-header" >
                        <strong class="group-title">开票管理</strong>
                    </div>
                    <ul class="ul-form">
                        <li>
                            <label>发票类型：</label>
                            <form:select id="invoiceType" path="invoice.invoiceType" class="input-medium required" disabled="true">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('DM0004')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                        </li>
                        <li>
                            <label>开票抬头：</label>
                            <form:input id="ivcInvoiceTitle" path="invoice.invoiceTitle" class="input-medium required" type="text" disabled="true"/>
                        </li>
                        <li>
                            <label>纳税人识别号：</label>
                            <form:input id="taxpayerIdentNo" path="invoice.taxpayerIdentNo" class="input-medium required" type="text" disabled="true"/>
                        </li>
                        <li class="clearfix"></li>
                        <li>
                            <label>开户行：</label>
                            <form:input id="depositBank" path="invoice.depositBank" class="input-medium required" type="text" disabled="true"/>
                        </li>
                        <li>
                            <label>银行账号：</label>
                            <form:input id="bankAccount" path="invoice.bankAccount" class="input-medium required" type="text" disabled="true"/>
                        </li>
                        <li>
                            <label>电话：</label>
                            <form:input id="ivcTelephone" path="invoice.telephone" class="input-medium phone required" type="text" disabled="true"/>
                        </li>
                        <li>
                            <label>地址：</label>
                            <form:input id="invoiceAddress" path="invoice.invoiceAddress" class="input-medium" type="text" disabled="true"/>
                        </li>
                        <li class="clearfix"></li>
                        <li>
                            <label>取票方式：</label>
                            <form:select path="invoice.ticketMethod" class="input-medium" disabled="true">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('DM0048')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                            </form:select>
                        </li>
                        <li>
                            <label>收件人：</label>
                            <form:input path="invoice.recipients" class="input-medium" type="text" value="" disabled="true"/>
                        </li>
                        <li>
                            <label>联系电话：</label>
                            <form:input path="invoice.repTelephone" class="input-medium phone" type="text" value="" disabled="true"/>
                        </li>
                        <li>
                            <label>收件地址：</label>
                            <form:input path="invoice.address" class="input-medium" type="text" value="" disabled="true"/>
                        </li>
                        <li class="full-width">
                            <label>开票说明：</label>
                            <form:textarea path="invoice.newRemarks" class="fill-right" rows="2" value="" maxlength="300" disabled="true"/>
                        </li>
                    </ul>
                </div>
	            <div class="group-box">
	                <div class="group-header" >
	                    <strong class="group-title">发票明细</strong>
						<div class="pull-right">
							<label>总金额：</label>
							<label class="text-left"><fmt:formatNumber value="${quota.imInvoiceTotalAmount}" pattern="#,##0.00"/></label>
						</div>
	                </div>
                    <hr style="margin:10px 0">
                    <table id="invoiceTbl" class="table table-striped table-bordered table-condensed">
                        <thead>
                            <tr>
                                <th class="text-center" width="20px"><a href="javascript:;" id="addIvcDtlRow" class="disabled"><i class="icon-plus-sign"></i></a></th>
                                <th class="text-center" width="35px">No</th>
                                <th width="35px">红票</th>
                                <th width="8%">发票金额</th>
                                <th>发票抬头</th>
                                <th width="10%">税金</th>
                                <th width="10%">开票日期</th>
                                <th width="12%">发票号码</th>
                                <th width="12%">快递编号</th>
                                <th width="12%">快递公司</th>
                                <th width="8%">申请状态</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:set var="rowCount" value="0"></c:set>
                        <c:forEach items="${quota.invoiceDtlListGroup}" var="invoiceDtlList" varStatus="group">
                            <c:forEach items="${invoiceDtlList}" var="item" varStatus="status">
                                <tr>
                                    <c:if test="${item.lineNo == '1'}">
                                        <td class="text-center" rowspan="${fn:length(invoiceDtlList)}">
                                            <a href="javascript:;" class="delRow disabled"><i class="icon-minus-sign"></i></a>
                                        </td>
                                    </c:if>
                                    <c:set var="rowCount" value="${rowCount + 1}"></c:set>
                                    <td class="text-center rowNo">${rowCount}</td>
                                    <td class="text-center"><input type="checkbox" name="invoiceDtlList[${status.index}].ifRed" class="ifRed" value="1" ${item.ifRed == '1' ? 'checked' : ''} disabled></td>
                                    <td class="text-right invoiceAmount"><fmt:formatNumber value="${item.invoiceAmount}" pattern="#,##0.00"/></td>
                                    <td class="invoiceTitle">${item.invoiceTitle}</td>
                                    <td><input name="invoiceDtlList[${status.index}].tax" type="hidden" value="<fmt:formatNumber value="${item.tax}" pattern="#.##"/>" class="text-right tax" style="width: 100%;" disabled />
                                    	<input type="text" value="<fmt:formatNumber value="${item.tax}" pattern="#,##0.00"/>" class="text-right taxFix" style="width: 100%;" disabled /></td>
                                    <td><input name="invoiceDtlList[${status.index}].invoiceDate" type="text" value="<fmt:formatDate value="${item.invoiceDate}" pattern="yyyy-MM-dd"/>" class="invoiceDate Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 100%;" disabled /></td>
                                    <td><input name="invoiceDtlList[${status.index}].invoiceNo" type="text" value="${item.invoiceNo}" class="invoiceNo" style="width: 100%;" disabled /></td>
                                    <td><input name="invoiceDtlList[${status.index}].expressNo" type="text" value="${item.expressNo}" class="expressNo" style="width: 100%;" disabled /></td>
                                    <td><input name="invoiceDtlList[${status.index}].expressCompany" type="text" value="${item.expressCompany}" class="expressCompany" style="width: 100%;" disabled /></td>
                                    <td>${fns:getDictLabel(item.workflowStatus, 'DM0043', '')}</td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
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
                    <c:forEach items="${quota.attachmentsList}" var="item" varStatus="status">
                        <tr class="uploaded">
                            <td class="text-center"><a href="javascript:;" class="delFileRow disabled" data-file-id="${item.id}"><i class="icon-minus-sign"></i></a></td>
                            <td class="text-center rowNo">${status.index + 1}</td>
                            <td>
                                <a class="link" href="${ctx}/rm/quotation/download/file/${item.id}">${item.fileName}.${item.fileType}</a>
                                <span class="pull-right"><i class="icon-ok icon-white"></i></span>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty quota.attachmentsList}">
                        <tr class="empty">
                            <td class="text-center" colspan="3">无附件</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${entry != 'invoice'}">
            <div class="center-btns">
                <input class="btn btn-primary" type="button" id="save" value="保 存"/>
                <input class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
            </div>
        </c:if>
        <c:if test="${entry == 'invoice'}">
            <c:if test="${empty quota.act.procInsId}">
                <div class="text-center" style="margin-top:8px;">
                    <c:if test="${ivcMode == 'edit' && isInvoiceEdit}">
                        <input type="button" class="btn btn-primary" id="invoiceSave" value="保 存"/>
                    </c:if>
                    <c:if test="${ivcMode == 'apply' && isInvoiceEdit}">
                        <input type="button" class="btn btn-primary opt" value="提交申请">
                    </c:if>
                    <input type="button" class="btn" value="返回" onclick="history.go(-1)"/>
                </div>
            </c:if>
            <c:if test="${!empty quota.act.procInsId}">
                <c:if test="${!viewsts.canApproval}">
                    <div class="text-center" style="margin-top:8px;">
                        <c:if test="${viewsts.canApply}">
                            <input type="button" class="btn btn-primary opt" value="提交申请">
                        </c:if>
                        <c:if test="${viewsts.canReapply}">
                            <input type="button" class="btn btn-primary opt" value="再申请">
                        </c:if>
                        <c:if test="${viewsts.canBack}">
                            <!-- <input name="opt" type="submit" class="btn btn-primary" value="撤回申请"> -->
                            <input type="button" class="btn btn-primary opt" value="撤回申请">
                        </c:if>
                        <c:if test="${viewsts.canDelete}">
                            <!-- <input name="opt" type="submit" class="btn btn-primary" value="删除"> -->
                            <input type="button" class="btn btn-primary opt" value="删除">
                        </c:if>
                        <c:if test="${empty quota.act.procInsId && !empty quota.id}">
                            <input type="button" class="btn" value="返回" onclick="history.go(-1)"/>
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
                            <!-- <input name="opt" type="submit" class="btn btn-primary" value="同意">
                            <input name="opt" type="submit" class="btn btn-default" value="退回"> -->
                            <input type="button" class="btn btn-primary opt" value="同意">
                            <input type="button" class="btn btn-default opt" value="退回">
                        </div>
                    </div>
                </c:if>
            </c:if>
        </c:if>
    </form:form>

    <sys:message content="${message}"/>

    <!-- 发票申请流程履历 -->
    <c:if test="${entry == 'invoice' && !empty quota.act.procInsId}">
        <act:histoicFlow procInsId="${quota.act.procInsId}"/>
    </c:if>

    <script type="text/javascript" src="${ctxStatic}/art-template/template-web.js"></script>

    <!-- 报价单明细行生成模板 -->
    <script type="text/template" id="quotaDtlRow">
    <tr id="{{id}}" class="active">
        <td class="text-center"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>
        <td class="text-center rowNo">{{rowNum}}</td>
        <td>
            <select name="quotationDtlList[{{rIdx}}].itemType" class="itemType required" style="width: 100%;" >
                <option></option>
                <c:forEach items="${fns:getDictList('DM0031')}" var="sItem">
                    <option value="${sItem.value}">${sItem.label}</option>
                </c:forEach>
            </select>
        </td>
        <td><input name="quotationDtlList[{{rIdx}}].materialNo" type="text" class="remote materialNo required" style="width: 100%;" /></td>
        <td><input name="quotationDtlList[{{rIdx}}].materialName" type="text" class="materialName" style="width: 100%;" disabled /></td>
        <td>
            <input name="quotationDtlList[{{rIdx}}].warehouse" type="hidden" value="" class="warehouse" />
            <input name="quotationDtlList[{{rIdx}}].warehouseName" type="text" class="warehouseName" style="width: 100%;" disabled />
        </td>
        <td><input name="quotationDtlList[{{rIdx}}].newSnNo" type="text" class="newSnNo required" style="width: 100%;" disabled /></td>
        <td><input name="quotationDtlList[{{rIdx}}].newProductionDate" type="text" class="newProductionDate" style="width: 100%;" disabled /></td>
        <td><input name="quotationDtlList[{{rIdx}}].unitPrice" type="hidden" class="text-right unitPrice" style="width: 100%;" disabled />
			<input type="text" class="text-right unitPriceFix" style="width: 100%;" disabled /></td>
        <td><input name="quotationDtlList[{{rIdx}}].num" type="text" class="text-right num digits required" style="width: 100%;" disabled /></td>
        <td><input name="quotationDtlList[{{rIdx}}].totalAmount" type="hidden" class="text-right totalAmount" style="width: 100%;" disabled />
			<input type="text" class="text-right totalAmountFix" style="width: 100%;" disabled /></td>
        <td>
            <select name="quotationDtlList[{{rIdx}}].ifWarranty" class="ifWarranty required" style="width: 100%;">
                <option></option>
                <c:forEach items="${fns:getDictList('yes_no')}" var="sItem">
                    <option value="${sItem.value}" ${sItem.value == quota.defaultWarranty ? 'selected' : ''}>${sItem.label}</option>
                </c:forEach>
            </select>
        </td>
        <c:if test="${quota.quotationType == '2'}">
        <td><input name="quotationDtlList[{{rIdx}}].actAmount" type="hidden" class="text-right actAmount" style="width: 100%;" disabled />
			<input type="text" class="text-right actAmountFix" style="width: 100%;" disabled /></td>
        <td><input name="quotationDtlList[{{rIdx}}].snNo" type="text" class="snNo required" style="width: 100%;" disabled /></td>
        <td><input name="quotationDtlList[{{rIdx}}].productionDate" type="text" class="productionDate" style="width: 100%;" disabled /></td>
        </c:if>
    </tr>
    </script>

    <!-- 发票明细行生成模板 -->
    <script type="text/template" id="invoiceDtlRow">
    <tr class="active new">
        <td class="text-center"><a href="javascript:;" class="delRow"><i class="icon-minus-sign"></i></a></td>
        <td class="text-center rowNo">{{rowNum}}</td>
        <td class="text-center"><input type="checkbox" name="invoiceDtlList[{{rIdx}}].ifRed" class="ifRed" value="1"></td>
        <td class="text-right invoiceAmount"><fmt:formatNumber value="${quota.totalAmount}" pattern="#,##0.00"/></td>
        <td class="invoiceTitle">{{invoiceTitle}}</td>
        <td><input name="invoiceDtlList[{{rIdx}}].tax" type="hidden" class="text-right tax" value="<fmt:formatNumber value="${quota.defaultTax}" pattern="#.##"/>" style="width: 100%;" disabled />
			<input type="text" class="text-right taxFix" value="<fmt:formatNumber value="${quota.defaultTax}" pattern="#,##0.00"/>" style="width: 100%;" disabled /></td>
        <td><input name="invoiceDtlList[{{rIdx}}].invoiceDate" type="text" class="invoiceDate Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width: 100%;" /></td>
        <td><input name="invoiceDtlList[{{rIdx}}].invoiceNo" type="text" class="invoiceNo" style="width: 100%;" /></td>
        <td><input name="invoiceDtlList[{{rIdx}}].expressNo" type="text" class="expressNo" style="width: 100%;" /></td>
        <td><input name="invoiceDtlList[{{rIdx}}].expressCompany" type="text" class="expressCompany" style="width: 100%;" /></td>
        <td></td>
    </tr>
    </script>
</body>
</html>