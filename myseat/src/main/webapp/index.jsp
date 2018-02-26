<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="login.action" method="post">
		<table align="center">
			<tr>
				<td>用户名:</td>
				<td>
					<input type="text" name="uname"/>
				</td>
			</tr>
			<tr>
				<td>密码:</td>
				<td>
					<input type="text" name="upwd"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="提交"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>