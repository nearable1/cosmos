<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>维修信息录入</title>
    <meta name="decorator" content="default"/>
    
    <style type="text/css">
    .input-xlarge {
        width: 301px;
    }
    div.ellipsis {
        text-overflow: ellipsis;
        -moz-text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }
    </style>

    <script type="text/javascript">
        $(document).ready(function() {

            // 故障小分类select初始化
            $("#faultType2").select2({
                placeholder:" ",
                allowClear: true,
                data: {
                    results: formatFaultTypeData(${fns:toJson(fns:getDictList('DM0054'))})
                }
            });

            var validator = $("#inputForm").validate({
                submitHandler: function(form){
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
                ignore:"",
                rules: {
                    "repairInfo.statusRemarks": {
                        required: {
                            depends: function(element) {
                                return $("#repairStatus").val() == "1";
                            }
                        }
                    }
                },
                messages: { 
                    "repairInfo.statusRemarks": {
                        required : "处理状态为已取消时，此为必填项"
                    }
                } 
            });

            // 清除不显示textarea框的name
            $("div.mode.hide").find("textarea").each(function(){ 
                $(this).text("").removeAttr("name");
            });

            // 维修分类初始化
            if (${!empty repair.repairInfo.askRepairDate && !empty repair.snInfo.warrantyDateTo && empty repair.repairInfo.repairType}) {
                // 报修日、保修到日期取得
                var askDate = $("#askRepairDate").val(), warrantyDateTo = $("#warrantyDateTo").val();

                // 报修日期<=到期日时，默认为保内, 报修日期>到期日，默认为保外
                if( (new Date(askDate)) <= (new Date(warrantyDateTo)) ){
                    $("#repairType").select2("val", "1");
                } else {
                    //$("#repairType").select2("val", "2");
                	var timeDiff = getTime2Time(new Date(askDate), new Date(warrantyDateTo));
                	if (timeDiff <= 2) {
                		$("#repairType").select2("val", "2");
                	} else if (timeDiff > 2 && timeDiff <= 3) {
                		$("#repairType").select2("val", "5");
                	} else if (timeDiff > 3) {
                		$("#repairType").select2("val", "6");
                	}
                }
            }

            // 处理状态初始化
            if (${empty repair.repairInfo.repairStatus}) {
                // 默认待处理设置
                $("#repairStatus").select2("val", "2");
            }

            // 处理方式change事件绑定
            $("#repairMethod").on("change", function() {
                var methodVal = $(this).val();
                $("div.mode.show").removeClass("show").addClass("hide").find("input,select,textarea").removeAttr("name");
                // 咨询的场合
                var selElm = "";
                if (methodVal == "1") {
                    selElm = "#consultBox";
                } else if (methodVal == "2") {
                    selElm = "#visitBox";
                } else if (methodVal == "3") {
                    selElm = "#remoteBox";
                } else if (methodVal == "4") {
                    selElm = "#factoryBox";
                }
                if (selElm != "") {
                    $(selElm).removeClass("hide").addClass("show");
                    // 还原textarea框的name
                    $(selElm).find("input,select,textarea").each(function(){ 
                        $(this).attr("name", $(this).attr("data-name"));
                    });
                }
            });

            // 故障小分类分组数据
            var faultTypeGroup = {};
            // 故障小分类
            var faultType2 = formatFaultTypeData(${fns:toJson(fns:getDictList('DM0054'))});
            $("#faultType").on("change", function() {
                if ($(this).val() != "" && $(this).val() != null) {
                    var groupItem = faultTypeGroup[$(this).val()];
                    if (groupItem == null || typeof(groupItem) != "undefined") {
                        groupItem = [];
                        for (var i = 0; i < faultType2.length; i++) {
                            var reg = new RegExp("^" + $(this).val() + "[0-9]{2}$");
                            if (reg.test(faultType2[i].id)) {
                                groupItem.push(faultType2[i]);
                            }
                        }
                        faultTypeGroup[$(this).val()] = groupItem;
                    }
                    $("#faultType2").select2("destroy");
                    $("#faultType2").select2({
                        placeholder:" ",
                        allowClear: true,
                        data: groupItem
                    });
                } else {
                    $("#faultType2").select2("destroy");
                    $("#faultType2").select2({
                        placeholder:" ",
                        allowClear: true,
                        data: faultType2
                    });
                }
                $("#faultType2").select2("val", "");
            });

            // 格式化故障小分类数组，转换成select2格式数据
            function formatFaultTypeData(faultType2) {
                if (faultType2 == null || typeof(faultType2) == "undefined") {
                    return [];
                }

                var formated = [];
                for (var i = 0; i < faultType2.length; i++) {
                    formated.push({id: faultType2[i].value, text: faultType2[i].label});
                }

                return formated;
            }

            // 是否有替代样机radiobox change椒件绑定
            $("input[name='repairInfo.ifPrototype']").on("change", function() {
                if ($(this).val() == "0") {
                    $("input.prototype:not(.disabled)").prop("disabled", true);
                    validator.form();
                } else {
                    $("input.prototype:not(.disabled)").prop("disabled", false);
                }
            });

            // 输入样机sn change事件绑定
            $("#prototypeSnNo").on("change", function() {
                var pSn = $(this).val();
                if (pSn == "") {
                    return;
                }
                $.ajax("${ctx}" + "/rm/repair/prototype/" + pSn, {
                    dataType: "json"
                }).done(function(data) { $("#prototypeProductionDate").val(data.prototypeProductionDate) });
            })
        });

        // 报修日期change事件绑定
        function askDateChange(dp) {
            var askDate = dp.cal.getNewDateStr(), warrantyDateTo = $("#warrantyDateTo").val();
            if (askDate == "" || warrantyDateTo == "") {
                return;
            }

            // 维修分类为初期不良忽略
            if ($("#repairType").val() == "4") {
                return;
            }

            // 报修日期<=到期日时，默认为保内, 报修日期>到期日，默认为保外
            if( (new Date(askDate)) <= (new Date(warrantyDateTo)) ) {
                $("#repairType").select2("val", "1");
            } else {
            	//$("#repairType").select2("val", "2");
            	var timeDiff = getTime2Time(new Date(askDate), new Date(warrantyDateTo));
            	if (timeDiff <= 2) {
            		$("#repairType").select2("val", "2");
            	} else if (timeDiff > 2 && timeDiff <= 3) {
            		$("#repairType").select2("val", "5");
            	} else if (timeDiff > 3) {
            		$("#repairType").select2("val", "6");
            	}
            }
        };
        
        function getTime2Time(time1, time2) {
        	var timeS = Date.parse(time1)/1000;
        	var timeE = Date.parse(time2)/1000;
        	var time_ = timeS - timeE;
        	return (time_/(3600*24*365));
        }
    </script>
</head>
<body>
    <h3 class="text-center page-title">维修信息录入</h3>
    <form:form id="inputForm" modelAttribute="repair" action="${ctx}/rm/repair/save" method="post" class="form-search">
        <form:hidden path="repairInfo.snNo"/>
        <form:hidden path="repairInfo.repairNo"/>
        <form:hidden path="repairInfo.id"/>
        <input type="hidden" name="repairInfo.updateDate" value="<fmt:formatDate value="${repair.repairInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss.SSS"/>" />

        <div class="group-box group-box-first">
            <ul class="ul-form">
                <li>
                    <label><span class="help-inline"><font color="red">*</font></span>单位名称：</label>
                    <form:input path="repairInfo.repairCusName" class="input-medium required" type="text" maxlength="300" />
                </li>
                <li>
                    <label><span class="help-inline"><font color="red">*</font></span>联系人：</label>
                    <form:input path="repairInfo.contactsName" class="input-medium required" type="text" maxlength="100" />
                </li>
                <li>
                    <label><span class="help-inline"><font color="red">*</font></span>电话：</label>
                    <form:input path="repairInfo.telephone" class="input-medium phone required" type="text" maxlength="50" />
                </li>
                <li>
                    <label>地址：</label>
                    <form:input path="repairInfo.address" class="input-double" type="text" maxlength="300" />
                </li>
                <li>
                    <label>维修编号：</label>
                    <input value="${repair.repairInfo.repairNo}" class="input-medium" type="text" disabled />
                </li>
                <li>
                    <label>记录人：</label>
                    <input class="input-medium" type="text" value="${(fns:getUserById(repair.repairInfo.createBy.id)).name}" disabled/>
                </li>
                <li>
                    <label>S/N：</label>
                    <div class="input-append input-medium">
                        <input value="${repair.repairInfo.snNo}" class="span2" type="text" disabled />
                        <a href="${ctx}/rm/repair/search" class="btn"><i class="icon-search"></i>&nbsp;</a>
                    </div>
                </li>
                <li>
                    <label>型号：</label>
                    <input value="${repair.snInfo.model}" type="text" class="input-medium" disabled />
                </li>
                <li>
                    <label>生产日期：</label>
                    <input value="<fmt:formatDate value="${repair.snInfo.productionDate}" pattern="yyyy-MM-dd"/>" type="text" class="input-medium" disabled />
                </li>
                <li>
                    <label>LB软件：</label>
                    <form:input path="snInfo.lbSnNo" class="input-medium" type="text" maxlength="50" />
                </li>
                <li>
                    <label>安装日期：</label>
                    <input value="<fmt:formatDate value="${repair.snInfo.actualInstallDate}" pattern="yyyy-MM-dd"/>" type="text" class="input-medium" disabled />
                </li>
                <li>
                    <label>保修到期日：</label>
                    <input id="warrantyDateTo" value="<fmt:formatDate value="${repair.snInfo.warrantyDateTo}" pattern="yyyy-MM-dd"/>" type="text" class="input-medium" disabled />
                </li>
                <li>
                    <label><span class="help-inline"><font color="red">*</font></span>报修日期：</label>
                    <input id="askRepairDate" name="repairInfo.askRepairDate" type="text" maxlength="10" class="input-medium Wdate required"
                        value="<fmt:formatDate value="${repair.repairInfo.askRepairDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd', onpicking:function(dp){askDateChange(dp)}});" />
                </li>
                <li>
                    <label><span class="help-inline"><font color="red">*</font></span>报修方式：</label>
                    <form:select path="repairInfo.repairWay" class="input-medium required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('DM0027')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </li>
                <li class="full-width"><hr style="margin:0px"></li>
                <li>
                    <label><span class="help-inline"><font color="red">*</font></span>维修分类：</label>
                    <form:select path="repairInfo.repairType" id="repairType" class="input-medium required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('DM0026')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </li>
                <li>
                    <label><span class="help-inline"><font color="red">*</font></span>处理状态：</label>
                    <form:select path="repairInfo.repairStatus" id="repairStatus" class="input-medium required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('DM0029')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </li>
                <li>
                    <label>负责工程师：</label>
                    <form:input path="repairInfo.responsiblePersonId" class="input-medium remote employee" data-type="20" data-show="text" data-shiro="1" />
<%--                     <form:select path="repairInfo.responsiblePersonId" class="input-medium"> --%>
<%--                         <form:option value="" label=""/> --%>
<%--                         <form:options items="${fns:getSqlDictList('dict_engineer')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<%--                     </form:select> --%>
                </li>
                <li class="full-width">
                    <label><span class="help-inline"><font color="red">*</font></span>问题描述：</label>
                       <form:textarea path="repairInfo.issueDescribe" rows="3" maxlength="300" class="fill-right required" ></form:textarea>
                </li>
                <li class="full-width">
                    <label>备注：</label>
                    <form:textarea path="repairInfo.newRemarks" rows="3" maxlength="300" class="fill-right"></form:textarea>
                </li>
                <li class="full-width"><hr style="margin:0px"></li>
                <li>
                    <label>故障大分类：</label>
                    <form:select path="repairInfo.faultType" id="faultType" class="input-medium">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('DM0053')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </li>
                <li>
                    <label>故障小分类：</label>
                    <form:input path="repairInfo.faultType2" id="faultType2" class="input-double"/>
                </li>
                <li class="clearfix"></li>
                <li>
                    <label>处理日期：</label>
                    <input name="repairInfo.processDate" type="text" maxlength="10" class="input-medium Wdate" value="<fmt:formatDate value="${repair.repairInfo.processDate}" pattern="yyyy-MM-dd"/>"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                </li>
                <li>
                    <label>处理方式：</label>
                    <form:select path="repairInfo.repairMethod" id="repairMethod" class="input-medium">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('DM0028')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </li>
                <li class="full-width">
                    <label>处理说明：</label>
                    <form:textarea path="repairInfo.statusRemarks" rows="3" maxlength="300" class="fill-right"></form:textarea>
                </li>
                <li class="full-width" style="padding: 0;height:6px"></li>
            </ul>
            <c:if test="${!empty repair.repairInfo.id}">
            <div class="center-btns">
                <a class="btn btn-primary" href="${ctx}/rm/quotation/pre/form?id=${repair.repairInfo.preQuotaId}&snNo=${repair.repairInfo.snNo}&repairNo=${repair.repairInfo.repairNo}">预报价单</a>
                <a class="btn btn-primary" href="${ctx}/rm/quotation/final/form?id=${repair.repairInfo.finalQuotaId}&snNo=${repair.repairInfo.snNo}&repairNo=${repair.repairInfo.repairNo}">最终报价单</a>
            </div>
            </c:if>
        </div>
        <c:set var="ctShow" value="${repair.repairInfo.repairMethod == '1' ? 'show' : 'hide'}" />
        <div class="group-box mode ${ctShow}" id="consultBox">
            <div class="group-header" >
                <strong class="group-title">咨询</strong>
            </div>
            <ul class="ul-form">
                <li>
                    <label>问题分类：</label>
                    <form:select path="repairInfo.consultType" data-name="repairInfo.consultType" class="input-double" >
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('DM0019')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </li>
                <li class="full-width">
                    <label>咨询备注：</label>
                    <form:textarea path="repairInfo.issueDetail" data-name="repairInfo.issueDetail" rows="3" maxlength="300" class="fill-right"></form:textarea>
                </li>
            </ul>
        </div>
        <c:set var="vtShow" value="${repair.repairInfo.repairMethod == '2' ? 'show' : 'hide'}" />
        <div class="group-box mode ${vtShow}" id="visitBox">
            <div class="group-header" >
                <strong class="group-title">上门维修</strong>
            </div>
            <ul class="ul-form">
                <li class="full-width">
                    <label>情况确认：</label>
                    <form:textarea path="repairInfo.issueDetail" data-name="repairInfo.issueDetail" rows="3" maxlength="300" class="fill-right"></form:textarea>
                </li>
                <li class="full-width">
                    <label>处理内容：</label>
                    <form:textarea path="repairInfo.processingContent" data-name="repairInfo.processingContent" rows="3" maxlength="300" class="fill-right"></form:textarea>
                </li>
            </ul>
        </div>
        <c:set var="mtShow" value="${repair.repairInfo.repairMethod == '3' ? 'show' : 'hide'}" />
        <div class="group-box mode ${mtShow}" id="remoteBox">
            <div class="group-header" >
                <strong class="group-title">远程处理</strong>
            </div>
            <ul class="ul-form">
                <li class="full-width">
                    <label>情况确认：</label>
                    <form:textarea path="repairInfo.issueDetail" data-name="repairInfo.issueDetail" rows="3" maxlength="300" class="fill-right"></form:textarea>
                </li>
                <li class="full-width">
                    <label>处理内容：</label>
                    <form:textarea path="repairInfo.processingContent" data-name="repairInfo.processingContent" rows="3" maxlength="300" class="fill-right"></form:textarea>
                </li>
            </ul>
        </div>
        <c:set var="fyShow" value="${repair.repairInfo.repairMethod == '4' ? 'show' : 'hide'}" />
        <div class="group-box mode ${fyShow}" id="factoryBox" style="padding-bottom: 5px;">
            <div class="group-header" >
                <strong class="group-title">返厂维修</strong>
            </div>
            <ul class="ul-form">
                <li class="full-width">
                    <label>情况确认：</label>
                    <form:textarea path="repairInfo.issueDetail" data-name="repairInfo.issueDetail" rows="3" maxlength="300" class="fill-right"></form:textarea>
                </li>
                <li class="full-width">
                    <label>处理内容：</label>
                    <form:textarea path="repairInfo.processingContent" data-name="repairInfo.processingContent" rows="3" maxlength="300" class="fill-right"></form:textarea>
                </li>
                <li>
                    <label>维修机到货：</label>
                    <input name="repairInfo.maintenanceDateFrom" data-name="repairInfo.maintenanceDateFrom" type="text" maxlength="10" class="input-medium Wdate" value="<fmt:formatDate value="${repair.repairInfo.maintenanceDateFrom}" pattern="yyyy-MM-dd"/>"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                </li>
                <li>
                    <label>维修机出库：</label>
                    <input name="repairInfo.maintenanceDateTo" data-name="repairInfo.maintenanceDateTo" type="text" maxlength="10" class="input-medium Wdate" value="<fmt:formatDate value="${repair.repairInfo.maintenanceDateTo}" pattern="yyyy-MM-dd"/>"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
                </li>
                <li class="clearfix"></li>
                <li>
                    <label>替代样机：</label>
                    <label class="radio inline">
                        <input type="radio" name="repairInfo.ifPrototype" data-name="repairInfo.ifPrototype" value="0" ${repair.repairInfo.ifPrototype != '1' ? 'checked' : ''}> 无
                    </label>
                    <label class="radio inline">
                        <input type="radio" name="repairInfo.ifPrototype" data-name="repairInfo.ifPrototype" value="1" ${repair.repairInfo.ifPrototype == '1' ? 'checked' : ''}> 有
                    </label>
                </li>
                <li class="clearfix"></li>
                <!-- 样子是否可编辑 -->
                <c:set var="ptyDisabled" value="${repair.repairInfo.ifPrototype != '1' ? 'disabled' : ''}" />
                <li>
                    <label>样机S/N：</label>
                    <input name="repairInfo.prototypeSnNo" data-name="repairInfo.prototypeSnNo" id="prototypeSnNo" value="${repair.repairInfo.prototypeSnNo}" class="input-medium required prototype" type="text" maxlength="50" ${ptyDisabled} />
                </li>
                <li>
                    <label>生产日期：</label>
                    <input class="input-medium prototype disabled" id="prototypeProductionDate" type="text" value="<fmt:formatDate value="${repair.repairInfo.prototypeProductionDate}" pattern="yyyy-MM-dd"/>" disabled />
                </li>
                <li class="clearfix"></li>
                <li>
                    <label>样机发出日期：</label>
                    <input name="repairInfo.prototypeDateFrom" data-name="repairInfo.prototypeDateFrom" id="prototypeDateFrom" class="input-medium Wdate required prototype" type="text" maxlength="10" value="<fmt:formatDate value="${repair.repairInfo.prototypeDateFrom}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" ${ptyDisabled} />
                </li>
                <li>
                    <label>样机返回日期：</label>
                    <input name="repairInfo.prototypeDateTo" data-name="repairInfo.prototypeDateTo" class="input-medium Wdate prototype" type="text" maxlength="10" value="<fmt:formatDate value="${repair.repairInfo.prototypeDateTo}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" ${ptyDisabled} />
                </li>
                <li class="full-width" style="padding: 0;height:10px"></li>
            </ul>
        </div>
        <div class="group-box">
            <div class="group-header" >
                <strong class="group-title">合同信息</strong>
            </div>
            <ul class="ul-form">
                <li>
                    <label>签约方：</label>
                    <form:input path="snInfo.customerName" class="input-xlarge" type="text" disabled="true" />
                </li>
                <li>
                    <label>最终客户：</label>
                    <form:input path="snInfo.endCustomerName" class="input-xlarge" type="text" disabled="true" />
                </li>
                <li class="clearfix"></li>
                <li>
                    <label>销售方式：</label>
                    <input value="${fns:getDictLabel(repair.snInfo.priceSystem, 'DM0005', '')}" class="input-xlarge" type="text" disabled />
                </li>
                <li>
                    <label>组：</label>
                    <form:input path="snInfo.organizeName" class="input-medium" type="text" disabled="true" />
                </li>
            </ul>
        </div>
        <div class="group-box group-box-last">
            <div class="group-header" >
                <strong class="group-title">历史维修记录</strong>
            </div>
            <div class="auto-scroll-x">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                    <tr>
                        <th>报修客户</th>
                        <th>处理方式</th>
                        <th width="150px">问题描述</th>
                        <th width="150px">情况确认</th>
                        <th width="150px">处理内容</th>
                        <th width="150px">最终报价单配件名称</th>
                        <th>报修日期</th>
                        <th>维修编号</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${repair.repairHisList}" var="item">
                    <tr>
                        <td>${item.repairCusName}</td>
                        <td>${fns:getDictLabel(item.repairMethod, 'DM0028', '')}</td>
                        <td title="${item.issueDescribe}"><div class="ellipsis" style="max-width:150px;">${item.issueDescribe}</div></td>
                        <td title="${item.issueDetail}"><div class="ellipsis" style="max-width:150px;">${item.issueDetail}</div></td>
                        <td title="${item.processingContent}"><div class="ellipsis" style="max-width:150px;">${item.processingContent}</div></td>
                        <td title="${item.materialName}"><div class="ellipsis" style="max-width:150px;">${item.materialName}</div></td>
                        <td><fmt:formatDate value="${item.askRepairDate}" pattern="yyyy-MM-dd"/></td>
                        <td><a class="link" href="${ctx}/rm/repair/form?id=${item.id}">${item.repairNo}</a></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty repair.repairHisList}">
                    <tr><td class="text-center" colspan="8">无历史维修记录</td></tr>
                </c:if>
                </tbody>
            </table>
            </div>
        </div>
        <div class="center-btns">
            <input class="btn btn-primary" id="save" type="submit" value="保 存"/>
            <input class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
        </div>
    </form:form>
    <sys:message content="${message}"/>
</body>
</html>