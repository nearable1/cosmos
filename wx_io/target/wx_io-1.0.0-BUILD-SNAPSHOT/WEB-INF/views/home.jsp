<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@include file="../../common/taglib.jsp"%> 
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script type="text/javascript">
		function turnToWeui(){
			window.self.location = "${ctx}/sys/weui";
		}
	</script>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<button style="width:100px;height:100px;" onclick="turnToWeui()">WEUI</button>
</body>
</html>
