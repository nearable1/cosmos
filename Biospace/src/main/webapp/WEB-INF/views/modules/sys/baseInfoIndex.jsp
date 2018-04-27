<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	   $(document).ready(cloneTableHeader_Width);  
	    //调整 浏览器 表格的显示宽度  以及 调用  锁定 表头和列  的JS函数。  
	    function cloneTableHeader_Width(){  
	        //document.body.clientWidth获得客户区域(浏览器窗口,不包括菜单栏和状态栏,就是内容窗口)的宽度 - 35像素的滚动条宽度。  
	        var myTable_Width = (document.body.clientWidth-35);  
	          
	        //alert(myTable_Width);//测试屏幕宽度  
	        if((document.body.clientWidth-35)<1030){  
	            myTable_Width=1030; //宽度  
	        }  
	        //调用 锁定表头和 列 的JS函数  
	        $(document).ready(function () {  
	        	FixTable("contentTable", 3, myTable_Width, 400);
	            FixTable("MyTable", 3, myTable_Width, 400);  
	               
             });  
         }   
		
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/base/index");
			$("#searchForm").submit();
	    	return false;
	    }
			
	</script>
</head>
<body>
	
	<sys:message content="${message}"/>
	<table id="contentTable" style="width:2047px;" class="table table-striped table-bordered table-condensed">
		<thead><tr><th width="50px">归属公司</th><th width="150px">归属部门</th><th  width="150px" class="sort-column login_name">登录名</th>
		<th width="150px" class="sort-column name">姓名</th><th>电话</th><th>手机</th><th>角色</th>
		<%-- <shiro:hasPermission name="sys:order:edit"><th>操作</th></shiro:hasPermission> --%>
		<th>角色1</th><th>角色2</th><th>角色3</th><th>角色4</th><th>角色5</th><th>角色6</th><th>角色7</th><th>角色8</th><th>角色9</th><th>角色10</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.company.name}</td>
				<td>${user.office.name}</td>
				<td><a href="${ctx}/sys/order/form?id=${user.id}">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td>
				<td>${user.roleNames}</td>
				<td>1</td>
				<td>2</td>
				<td>3</td>
				<td>4</td>
				<td>5</td>
				<td>6</td>
				<td>7</td>
				<td>8</td>
				<td>9</td>
				<td>10</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="pagination">${page}</div>
</body>
</html>