<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/taglib.jsp"%>   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
		<script type="text/javascript">
			function turnTohomePage(){
				window.self.location = "${ctx}/sys/home";
			}
		</script>
	</head> 
	<body>
		<h2>测试页面跳转</h2>
		<button style="width:100px;height:100px;" value="home" onclick="turnTohomePage()" value="homePage">homePage</button>
	</body>
</html>
