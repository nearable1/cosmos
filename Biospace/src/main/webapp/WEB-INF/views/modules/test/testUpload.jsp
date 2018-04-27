<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文件上传</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
	</script>
</head>
<body>  
<h2>文件上传实例</h2>  
  
<form action="${ctx}/test/testUpload/fileUpload" method="post" enctype="multipart/form-data">  
    选择文件:<input type="file" name="file">  
    <input type="submit" value="提交">   
</form>  
  
</body>
</html>