<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/jsp/common/head.jsp"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
	<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
	<script>
	$(function(){
		//alert("111");
		$("input[id='searchbutton']").click(function(){
			//alert("111");
			$.ajax({
				type:"POST",
				url:"ajaxSearch.action?pname="+$("input[name='queryProductName']").val,
				success:function(message){
					//alert(message);
					//$("input[name='queryProductName']").val(message);
					for(var mm in message){
						alert(mm);
					}
				},
				error:function(){
					alert("error");
				}
			});
		});
	});
		/* $(function(){
			$("input[id='searchbutton']").click(function(){
				//alert("1111");
				$.ajax({
					type:"POST",
					url:"ajaxSearch.action?pname="+$("input[name='queryProductName']").val(),
					success:function(message){
						//alert(message);
						//解析返回的List<HashMap>?
						$("tbody[id='tby']").remove();
						//var $tby=$("<tr><td>110</td></tr>");
						//$("table[id='tt']").append($tby);
						for(var i=0;i<message.length;i++){
							//alert(message[i]);
							var mg=message[i];
							//var $t1=$("<tr></tr>");
							//$("table[id='tt']").append($t1);
							var $tby=$("<tr><td>"+mg["BILLCODE"]+"</td>"+"<td>"+mg["PRODUCTNAME"]+"</td>"+
									"<td>"+mg["PRONAME"]+"</td>"+"<td>"+mg["TOTALPRICE"]+"</td>"+
									"<td>"+mg["ISPAYMENT"]+"</td>"+"<td>"+mg["CREATIONDATE"]+"</td></tr>");
							$("table[id='tt']").append($tby);
							/*for(var j in mg){
								//alert(j+"  "+mg[j]);
								//$("#mydiv").html(mg[j]+"&nbsp;&nbsp;&nbsp;");
								//$("tbody[id='tby']").append("<tr>");
								var $tby=$("<td>"+mg["BILLCODE"]+"</td>"+"<td>"+mg["PRODUCTNAME"]+"</td>"+
										"<td>"+mg["PRONAME"]+"</td>"+"<td>"+mg["TOTALPRICE"]+"</td>"+
										"<td>"+mg["ISPAYMENT"]+"</td>"+"<td>"+mg["CREATIONDATE"]+"</td>");
								$("table[id='tt']").append($tby);
							}
						}
					},
					error:function(){
						alert("error!");
					}
					
				});
			});
			
		}); */
	</script>
		
	
</head>
<div class="right">
       <div class="location">
           <strong>你现在所在的位置是:</strong>
           <span>订单管理页面</span>
       </div>
       <div class="search">
       <form method="post" action="searchShopping">
			<input name="method" value="query" class="input-text" type="hidden">
			<span>商品名称：</span>
			<input name="queryProductName" type="text">
			 
			<span>供应商：</span>
			 <select name="queryProviderId">
				<c:if test="${liststr != null}"> 
				   <option value="">--请选择--</option>
				   <c:forEach var="lst" items="${liststr}">
				   		<option <c:if test="${lst}">selected="selected"</c:if>
				   		value="${lst}">${lst}</option>
				   </c:forEach>
				</c:if>
       		</select> 
			 
			<span>是否付款：</span>
			<select name="queryIsPayment">
				<option value="">--请选择--</option>
				<option value="1">未付款</option>
				<option value="2">已付款</option>
       		</select>
			
			 <input	value="查 询" type="button" id="searchbutton">
			 <a href="#">添加订单</a>
		</form>
       </div>
       <!--账单表格 样式和供应商公用-->
      
      <table class="providerTable" cellpadding="0" cellspacing="0">
          <tr class="firstTr">
              <th width="10%">订单编码</th>
              <th width="20%">商品名称</th>
              <th width="10%">供应商</th>
              <th width="10%">订单金额</th>
              <th width="10%">是否付款</th>
              <th width="10%">创建时间</th>
              <th width="30%">操作</th>
          </tr>
          <c:forEach var="lst" items="${listbill }" varStatus="status">
          <tbody id="tby">
				<tr>
					<td>
<!-- BILLCODE:键 而且必须大写 这是一种固定的格式  lst不仅仅是一个变量，是一个对象（hashmap集合） 在页面中用EL表达式遍历的话需要去用大写的键来找值-->	
					<span>${lst.BILLCODE}</span> 
					</td>
					<td>
					<span>${lst.PRODUCTNAME}</span>
					</td>
					<td>
					<span>${lst.PRONAME}</span>
					</td>
					<td>
					<span>${lst.TOTALPRICE}</span>
					</td>
					<td>
					<span>
						<c:if test="${lst.ISPAYMENT eq '2'}">未付款</c:if>
						<c:if test="${lst.ISPAYMENT eq '1'}">已付款</c:if>
					</span>
					</td>
					<td>
					<span>
					<fmt:formatDate value="${lst.CREATIONDATE}" pattern="yyyy-MM-dd"/>
					</span>
					</td>
					<td>
					<span><a class="viewBill" href="<%=path%>/jsp/billview.jsp?id=${lst.BILLCODE }&name=${lst.PRODUCTNAME}"><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
					<span><a class="modifyBill" href="<%=path%>/jsp/billmodify.jsp?id=${lst.ID }"><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
					<span><a href="<%=path %>/deleteProduct.action?id=${lst.ID }"><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
				</tbody>
			</c:forEach>
      </table>
  </div>
  <div id="mydiv">
  	
  </div>
</section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该订单吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/billlist.js"></script>