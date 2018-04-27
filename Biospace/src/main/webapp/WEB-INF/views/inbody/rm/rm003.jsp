<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新建维修记录-信息查询</title>
    <meta name="decorator" content="default"/>

    <style type="text/css">
    .ul-tab-header {
        float: left;
        list-style: none;
        margin: 8px 0px 8px 6px;
    }
    .ul-tab-header li {
        float: left;
        padding-right: 20px;
    }
    .ul-tab-header li a {
        color: #555;
    }
    .ul-tab-header li a.curr{
        padding-bottom: 0;
        border-bottom: 2px solid #555;
        font-weight: 700;
        cursor: pointer;
        text-decoration: none;
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
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
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

            // 切换查询类型事件绑定
            $(".ul-tab-header li a").on("click", function(){
                $("#searchType").val($(this).attr("data-type"));
                $("#searchForm").submit();
            });
            
            $("#close").on("click", function() {
            	$("#snInfoModal").modal('hide');
            });
        });

        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/rm/repair/search");
            $("#searchForm").submit();
            return false;
        }
		
		 function modalOpen(sn){
				 $("#snInfoModal").modal('show').css({
	                width: '780px',
	                'margin-left': '-512px'
	            });

				$.ajax({
					url:"${ctx}" + "/rm/repair/getSnInfo",
					 type: "get",
				        async: false,
				        data: {"snNo":sn},
				        dataType: "json",
				        success: function (result) {
				        	 $("#snNoM").val(result.snNo);
				        	 $("#modelM").val(result.model);
				        	 $("#snVersionM").val(result.snVersion);
				        	 $("#productionDateStrM").val(result.productionDateStr);
				        	 $("#actualInstallDateStrM").val(result.actualInstallDateStr);
				        	 $("#warrantyDateFromStrM").val(result.warrantyDateFromStr);
				        	 $("#warrantyDateToStrM").val(result.warrantyDateToStr);
				        },
				        error: function (msg) {
				        }
				});
			}
    </script>
</head>
<body>
    <h3 class="text-center page-title">新建维修记录-信息查询</h3>
    <form:form id="searchForm" modelAttribute="search" action="${ctx}/rm/repair/search" method="get" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <form:hidden path="searchType"/>
        <ul class="ul-form">
            <li>
                <label>最终客户：</label>
                <form:input path="endCustomerName" class="input-medium" type="text" />
            </li>
            <li>
                <label>签约方名称：</label>
                <form:input path="customerChName" class="input-medium" type="text" />
            </li>
            <li>
                <label>电话：</label>
                <form:input path="telephone" class="input-medium" type="text" />
            </li>
            <li class="clearfix"></li>
            <li>
                <label>S/N：</label>
                <form:input path="snNo" class="input-medium" type="text" />
            </li>
            <li>
                <label>机器型号：</label>
                <form:input path="model" class="input-medium" type="text" />
            </li>
            <li class="btns">
                <input class="btn btn-primary" type="submit" value="查询" onclick="return page();">
                <input class="btn btn-primary" type="button" value="清空" onclick="javascript:window.location.href='${ctx}/rm/repair/search';">
            </li>
        </ul>
    </form:form>

    <ul class="ul-tab-header" >
        <li><a href="javascript:;" data-type="2" class="${search.searchType == '2' ? 'curr' : ''}">维修信息</a></li>
        <li><a href="javascript:;" data-type="3" class="${search.searchType == '3' ? 'curr' : ''}">最新检测信息</a></li>
        <li><a href="javascript:;" data-type="1" class="${search.searchType == '1' ? 'curr' : ''}">合同信息</a></li>
    </ul>
    <shiro:hasPermission name="rm:consulting:view">
    <input class="btn pull-right" type="button" value="无S/N咨询录入" onclick="javascript:window.location.href='${ctx}/rm/consulting/form';"/>
    </shiro:hasPermission>

    <c:if test="${search.searchType == '2'}">
        <div class="auto-scroll-x">
        <table class="table table-striped table-bordered table-condensed">
            <thead>
                <tr>
                	<th></th>
                    <th>单位名称</th>
                    <th>机器型号</th>
                    <th width="350px">问题描述</th>
                    <th>S/N</th>
                    <th>生产日期</th>
                    <th>保修到期日</th>
                    <th>维修编号</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.list}" var="item">
                    <tr>
                    	<td><input class="btn btn-primary" type="button" value="新建" onclick="javascript:window.location.href='${ctx}/rm/repair/form?snNo=${item.snNo}';"></td>
                        <td>${item.repairCusName}</td>
                        <td>${item.model}</td>
                        <td title="${item.issueDescribe}"><div class="ellipsis" style="max-width:350px;">${item.issueDescribe}</div></td>
                        <td><a href="javascript:;" class="link" onclick="modalOpen('${item.snNo}');">${item.snNo}</a></td>
                        <td><fmt:formatDate value="${item.productionDate}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${item.warrantyDateTo}" pattern="yyyy-MM-dd"/></td>
                        <td>${item.repairNo}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty page.list}">
                    <tr><td class="text-center" colspan="8">没有您需要的查询结果</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
        <div class="pagination">${page}</div>
    </c:if>

    <c:if test="${search.searchType == '3'}">
        <div class="auto-scroll-x">
        <table class="table table-striped table-bordered table-condensed">
            <thead>
                <tr>
                	<th></th>
                    <th>机器型号</th>
                    <th>S/N</th>
                    <th>版本号</th>
                    <th>LB软件</th>
                    <th>机器状态</th>
                    <th>生产日期</th>
                    <th>检测日期</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.list}" var="item">
                    <tr>
                    	<td><input class="btn btn-primary" type="button" value="新建" onclick="javascript:window.location.href='${ctx}/rm/repair/form?snNo=${item.snNo}';"></td>
                        <td>${item.model}</td>
                        <td><a href="javascript:;" class="link" onclick="modalOpen('${item.snNo}');">${item.snNo}</a></td>
                        <td>${item.snVersion}</td>
                        <td>${item.lbSnNo}</td>
                        <td>${fns:getDictLabel(item.macStatus, 'DM0033', '')}</td>
                        <td><fmt:formatDate value="${item.productionDate}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${item.testingDate}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty page.list}">
                    <tr><td class="text-center" colspan="6">没有您需要的查询结果</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
        <div class="pagination">${page}</div>
    </c:if>

    <c:if test="${search.searchType == '1'}">
        <div class="auto-scroll-x">
        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <thead>
                <tr>
                	<th></th>
                    <th>最终客户</th>
                    <th>签订方</th>
                    <th>机器型号</th>
                    <th>S/N</th>
                    <th>生产日期</th>
                    <th>保修到期日</th>
                    <th>合同编号</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.list}" var="item">
                    <tr>
                    	<td><input class="btn btn-primary" type="button" value="新建" onclick="javascript:window.location.href='${ctx}/rm/repair/form?snNo=${item.snNo}';"></td>
                        <td>${item.endCustomerName}</td>
                        <td>${item.customerChName}</td>
                        <td>${item.model}</td>
                        <td><a href="javascript:;" class="link" onclick="modalOpen('${item.snNo}');">${item.snNo}</a></td>
                        <td><fmt:formatDate value="${item.productionDate}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${item.warrantyDateTo}" pattern="yyyy-MM-dd"/></td>
                        <td>${item.orderNo}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty page.list}">
                    <tr><td class="text-center" colspan="8">没有您需要的查询结果</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
        <div class="pagination">${page}</div>
    </c:if>
    <sys:message content="${message}"/>
	
	<!-- SN模态框 -->
	<div class="modal fade" id="snInfoModal" style="display:none;" data-backdrop="static" >
				<div class="modal-dialog">
					<div class="modal-content">
					<form:form id="modalForm" class="form-search">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" >&times;</button>
							<h4 class="text-center modal-title">SN信息</h4>
						</div>
						<div class="modal-body">
							<div class="group-box group-box-first">
							    <ul class="ul-form">
					                <li>
					                    <label>S/N：</label>
					                    <input name="snNoM" id="snNoM" type="text" value="" class="input-medium" disabled/>
					                </li>
					                <li>
					                    <label>型号：</label>
					                    <input name="modelM" id="modelM" type="text" value="" class="input-medium" disabled/>
					                </li>
					                <li>
					                    <label>版本号：</label>
					                    <input name="snVersionM" id="snVersionM" type="text" value="" class="input-medium" disabled/>
					                </li>
					                <li class="clearfix"></li>
					                <li>
					                    <label>生产日期：</label>
					                    <input name="productionDateStrM" id="productionDateStrM" type="text" value="" class="input-medium" disabled/>
					                </li>
					                <li>
					                    <label>安装日期：</label>
					                    <input name="actualInstallDateStrM" id="actualInstallDateStrM" type="text" value="" class="input-medium" disabled/>
					                </li>
					                <li class="clearfix"></li>
					                <li>
					                    <label>保修开始日：</label>
					                    <input name="warrantyDateFromStrM" id="warrantyDateFromStrM" type="text" value="" class="input-medium" disabled/>
					                </li>
					                <li>
					                    <label>保修到期日：</label>
					                    <input name="warrantyDateToStrM" id="warrantyDateToStrM" type="text" value="" class="input-medium" disabled/>
					                </li>
							     </ul>
							</div>
						</div>
						<div class="modal-footer">
							<div class="text-center">
						        <input id="close" class="btn" type="button" value="关闭"/>
							</div>
						</div>
						</form:form>
					</div>
				</div>
			</div>
</body>
</html>